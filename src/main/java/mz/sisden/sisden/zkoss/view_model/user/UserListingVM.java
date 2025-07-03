package mz.sisden.sisden.zkoss.view_model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.User;
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
public class UserListingVM extends ZkViewModel {
    UserListModel userListModel;
    List<CustomMap> selectedUserList = new ArrayList<>();
    String searchTerm;

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.userListModel = new UserListModel(this.searchTerm);

        List<User> userList = ZkUtils.getAttribute(ZkArgument.USER_LIST.name());
        if (CollectionUtils.isNotEmpty(userList)) {
            userList.stream()
                    .map(User::getId)
                    .peek(this.userListModel.getPriorityIds()::add)
                    .map(CustomMap::ofId)
                    .forEach(this.selectedUserList::add);
        }
    }

    @Command
    public void search() {
        this.userListModel = new UserListModel(this.searchTerm);

        selectedUserList.stream()
                .map(CustomMap::getId)
                .forEach(this.userListModel.getPriorityIds()::add);

        BindUtils.postNotifyChange(this, "userListModel");
    }

    @Command
    public void edit(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.USER_FORM)
                .withArgument(ZkArgument.USER, this.getBeans().getUserRepository().findById(id).orElseThrow())
                .update()
                .onClose(event -> {
                    this.userListModel = new UserListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "userListModel");
                });
    }

    @Command
    public void view(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.USER_FORM)
                .withArgument(ZkArgument.USER, this.getBeans().getUserRepository().findById(id).orElseThrow())
                .readonly()
                .show();
    }

    @Command
    public void delete(@BindingParam Long id) {
        User user = this.getBeans().getUserRepository().findById(id).orElseThrow();
        ZkAlerts.builder()
                .question("Deseja apagar o Utilizador {}?", user.getUsername())
                .onYesListener(event -> {
                    this.getBeans().getUserRepository().delete(user);
                    this.userListModel = new UserListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "userListModel");
                })
                .show();
    }

    @Command
    public void select() {
        Map<String, Object> arguments = ZkArgument.USER.put(this.selectedUserList.get(0));
        ZkArgument.USER_LIST.putIn(arguments, this.selectedUserList);
        ZkArgument.USER_ARRAY.putIn(arguments, this.selectedUserList.toArray(new User[0]));

        ZkEvent.onUserSelected.post(this.getComponent(), arguments);
        ZkUtils.back(this.getComponent());
    }
} 