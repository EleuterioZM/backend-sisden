/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Notification;
import mz.sisden.sisden.entities.SystemFile;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.entities.UserGroup;
import mz.sisden.sisden.utils.INotificatorObserver;
import mz.sisden.sisden.utils.Texter;
import mz.sisden.sisden.zkoss.ZkArgument;
import mz.sisden.sisden.zkoss.ZkController;
import mz.sisden.sisden.zkoss.ZkPage;
import mz.sisden.sisden.zkoss.ZkUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static mz.sisden.sisden.utils.DateTime.currDateTime;
import static mz.sisden.sisden.utils.DateTime.formatDateTime;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Getter
@ZkComponent
public class HeaderController extends ZkController implements INotificatorObserver {
    @Wire
    private Div divNotificationBell;

    @Wire
    private A aMyAccount;

    @Wire
    private Image imageUserPhoto;

    @Wire
    private Html htmlUsername, htmlUserGroups;

    @Wire
    private Label labelNotificationNumber;

    Boolean active = true;

    String identification = "HeaderController";

    @Override
    public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
        return super.doBeforeCompose(page, parent, compInfo);
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        this.identification = this.getClass().getSimpleName() + " - " + Optional.ofNullable(this.getBeans().getSecurities().getUser()).map(User::toString).orElse("Anonymous") + " - " + formatDateTime(currDateTime());

        this.getBeans().getNotificator().subscribe(this);
        this.checkNotifications();

        User user = this.getBeans().getSecurities().getUser();
        this.htmlUsername.setContent("Anónimo");
        if (nonNull(user)) {
            this.htmlUsername.setContent(user.getName());
            this.htmlUserGroups.setContent(Texter.commaSeparated(user.getUserGroupList(), UserGroup::getName));

            byte[] avatarByteData = new ClassPathResource("avatar.png").getContentAsByteArray();
            SystemFile photo = SystemFile.builder().fileName("avatar.png").byteData(avatarByteData).build();

            AImage aImage = new AImage(photo.getFileName(), photo.getByteData());
            this.imageUserPhoto.setContent(aImage);
            SystemFile finalPhoto = photo;
            this.imageUserPhoto.addEventListener(Events.ON_CLICK, event -> ZkUtils.showFile(this.getComponent(), finalPhoto));

            this.aMyAccount.addEventListener(
                    Events.ON_CLICK, event ->
                            ZkUtils.getModalBuilder()
                                    .zkPage(ZkPage.USER_FORM)
                                    .withArgument(ZkArgument.USER, user)
                                    .update()
                                    .show()
            );
        }
    }

    private void checkNotifications() {
        try {
            User user = this.getBeans().getSecurities().getUser();
            List<String> authorityList = user.getAuthorityList().stream().map(SimpleGrantedAuthority::getAuthority).toList();
            List<Notification> notificationList = this.getBeans()
                    .getNotificationRepository()
                    .findAllByAuthorityListAndViewed(authorityList, false, Sort.by("createdAt").descending());

            int size = notificationList.size();

            this.labelNotificationNumber.setValue(Objects.toString(size));
            if (RandomUtils.nextBoolean()) {
                this.divNotificationBell.setStyle("animation: danger-pulse 1.5s infinite; background-color: rgba(255, 231, 230, 0.7); display: flex; gap: .5rem; align-items: center; justify-content: center; padding: .3rem; border-radius: .5rem; border: .5px solid #000;");
            } else if (!notificationList.isEmpty()) {
                this.divNotificationBell.setStyle("animation: warning-pulse 1.5s infinite; background-color: rgba(255, 242, 226, 0.7); display: flex; gap: .5rem; align-items: center; justify-content: center; padding: .3rem; border-radius: .5rem; border: .5px solid #000;");
            } else {
                this.divNotificationBell.setStyle("background-color: #fff; display: flex; gap: .5rem; align-items: center; justify-content: center; padding: .3rem; border-radius: .5rem; border: .5px solid #000;");
            }
        } catch (Exception e) {
            log.warn("Failed to check notifications: {}", e.getMessage());
        }
    }

    @Override
    public void publish(Notification notification) {
        try {
            if (isFalse(this.getDesktop().isAlive())) {
                return;
            }

            if (isFalse(Events.inEventListener())) {
                Executions.activate(this.getDesktop());
            }

            this.checkNotifications();
        } catch (Exception e) {
            log.debug("Failed to publish notification {} in {}, cause: {}", notification, this.getIdentification(), e.getMessage());
            this.getBeans().getNotificator().unsubscribe(this);
        } finally {
            Executions.deactivate(this.getDesktop());
        }
    }

    @Override
    public String getIdentification() {
        return this.identification;
    }

    @Override
    public Boolean isActive() {
        return this.active;
    }

    @Override
    public void refresh() {
        this.checkNotifications();
    }

    @Listen("onClick = #divNotificationBell")
    public void onClick_divNotificationBell() {
        ZkUtils.getModalBuilder()
                .zkPage(ZkPage.NOTIFICATION)
                .parentComponent(this.getComponent())
                .show()
                .addEventListener(Events.ON_CLOSE, event -> this.checkNotifications());
    }
}
