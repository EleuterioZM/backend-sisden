/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.controller.notification;

import lombok.Getter;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Notification;
import mz.sisden.sisden.entities.NotificationType;
import mz.sisden.sisden.utils.DateTime;
import mz.sisden.sisden.utils.MapUtils;
import mz.sisden.sisden.zkoss.ZkAlerts;
import mz.sisden.sisden.zkoss.ZkController;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Getter
@ZkComponent
public class NotificationController extends ZkController {
    @Wire
    private Listbox listboxNotification;

    @Wire
    private Textbox textboxSearchTerm;


    @Override
    public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
        return super.doBeforeCompose(page, parent, compInfo);
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        this.listboxNotification.setModel(new NotificationListModel());
        this.textboxSearchTerm.addEventListener(Events.ON_CHANGE, event -> {
            this.listboxNotification.setModel(new NotificationListModel(this.textboxSearchTerm.getValue()));
        });
    }

    @Listen("onClick = #buttonSearch; onChange = #textboxSearchTerm")
    public void onClickButtonSearch() {
        this.listboxNotification.setModel(new NotificationListModel(this.textboxSearchTerm.getValue()));
    }

    @Listen("onView = #listboxNotification")
    public void onView_listboxNotification(ForwardEvent forwardEvent) {
        Map<String, Object> notificationMap = (Map<String, Object>) forwardEvent.getData();
        Optional<Notification> notificationOptional = this.getBeans().getNotificationRepository().findById(Objects.requireNonNull(MapUtils.getLong(notificationMap, "id")));
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();

            if (isTrue(notification.getViewed()) && nonNull(notification.getUserViewer())) {
                ZkAlerts.builder()
                        .info(
                                "Notificação aberta por {} no dia {}",
                                notification.getUserViewer().getName(),
                                DateTime.formatDateTime(notification.getViewedAt())
                        )
                        .show();
            }

            NotificationType notificationType = notification.getNotificationType();
        }
    }

    public String timeAgo(LocalDateTime localDateTime) {
        return DateTime.timeAgo(localDateTime);
    }
}
