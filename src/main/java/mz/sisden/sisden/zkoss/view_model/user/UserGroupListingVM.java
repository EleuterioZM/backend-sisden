/*
 * Copyright (c) 2025
 * 
 * All rights reserved.
 * Created by Eleuterio Zacarias Mabecuane  */

package mz.sisden.sisden.zkoss.view_model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.UserGroup;
import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.zkoss.*;
import org.apache.commons.collections4.CollectionUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import java.util.*;

@Slf4j
@Getter
@Setter
@ZkComponent
public class UserGroupListingVM extends ZkViewModel {
    UserGroupListModel userGroupListModel;
    List<CustomMap> selectedUserGroupList = new ArrayList<>();
    String searchTerm;

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.userGroupListModel = new UserGroupListModel(this.searchTerm);

        List<UserGroup> userGroupList = ZkUtils.getAttribute(ZkArgument.USER_GROUP_LIST.name());
        if (CollectionUtils.isNotEmpty(userGroupList)) {
            userGroupList.stream()
                    .map(UserGroup::getId)
                    .peek(this.userGroupListModel.getPriorityIds()::add)
                    .map(CustomMap::ofId)
                    .forEach(this.selectedUserGroupList::add);
        }
    }

    @Command
    public void search() {
        this.userGroupListModel = new UserGroupListModel(this.searchTerm);

        selectedUserGroupList.stream()
                .map(CustomMap::getId)
                .forEach(this.userGroupListModel.getPriorityIds()::add);

        BindUtils.postNotifyChange(this, "userGroupListModel");
    }

    @Command
    public void newUserGroup() {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.USER_GROUP_FORM)
                .onClose(event -> {
                    this.userGroupListModel = new UserGroupListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "userGroupListModel");
                });
    }

    @Command
    public void edit(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.USER_GROUP_FORM)
                .withArgument(ZkArgument.USER_GROUP, this.getBeans().getUserGroupService().findById(id).orElseThrow())
                .update()
                .onClose(event -> {
                    this.userGroupListModel = new UserGroupListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "userGroupListModel");
                });
    }

    @Command
    public void view(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.USER_GROUP_FORM)
                .withArgument(ZkArgument.USER_GROUP, this.getBeans().getUserGroupService().findById(id).orElseThrow())
                .readonly()
                .show();
    }

    @Command
    public void delete(@BindingParam Long id) {
        UserGroup userGroup = this.getBeans().getUserGroupService().findById(id).orElseThrow();
        ZkAlerts.builder()
                .question("Deseja apagar o Grupo de Utilizadores {}?", userGroup.getName())
                .onYesListener(event -> {
                    this.getBeans().getUserGroupService().deleteById(userGroup.getId());
                    this.userGroupListModel = new UserGroupListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "userGroupListModel");
                })
                .show();
    }

    @Command
    public void select() {
        Map<String, Object> arguments = ZkArgument.USER_GROUP.put(this.selectedUserGroupList.get(0));
        ZkArgument.USER_GROUP_LIST.putIn(arguments, this.selectedUserGroupList);
        ZkArgument.USER_GROUP_ARRAY.putIn(arguments, this.selectedUserGroupList.toArray(new UserGroup[0]));

        ZkEvent.onUserGroupSelected.post(this.getComponent(), arguments);
        ZkUtils.back(this.getComponent());
    }
} 