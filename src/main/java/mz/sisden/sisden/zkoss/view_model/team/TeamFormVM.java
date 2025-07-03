package mz.sisden.sisden.zkoss.view_model.team;

import lombok.Getter;
import lombok.Setter;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Team;
import mz.sisden.sisden.entities.TeamUser;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.zkoss.ZkAlerts;
import mz.sisden.sisden.zkoss.ZkArgument;
import mz.sisden.sisden.zkoss.ZkUtils;
import mz.sisden.sisden.zkoss.ZkViewModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@ZkComponent
public class TeamFormVM extends ZkViewModel {

    private Team team;
    private List<User> userList;
    private List<User> selectedUsers = new ArrayList<>();
    private Long leaderUserId;

    @Init(superclass = true)
    public void init() {}

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.team = ZkUtils.getAttribute(ZkArgument.TEAM.name());
        this.userList = this.getBeans().getUserRepository().findAll();

        if (this.team != null && CollectionUtils.isNotEmpty(this.team.getTeamUsers())) {
            this.selectedUsers = this.team.getTeamUsers().stream()
                    .map(TeamUser::getUser)
                    .collect(Collectors.toList());

            this.leaderUserId = this.team.getTeamUsers().stream()
                    .filter(TeamUser::isLeader)
                    .map(tu -> tu.getUser().getId())
                    .findFirst()
                    .orElse(null);
        }

        if (Objects.isNull(this.team)) {
            this.team = new Team();
        }
    }

    @Command
    public void saveOrUpdate() {
        if (this.getCreate()) {
            saveOrUpdate("Criar", "Criada");
        } else if (this.getUpdate()) {
            saveOrUpdate("Actualizar", "Actualizada");
        }
    }

    public void saveOrUpdate(String message, String alertInfo) {
        ZkAlerts.builder()
                .question("Deseja " + message + " a Equipa {}?", this.team.getName())
                .onYesListener(event -> {

                    if (Objects.isNull(this.team.getId())) {
                        this.getBeans().getTeamRepository().save(this.team);
                    } else {

                        this.getBeans().getTeamRepository().deleteTeamUsersByTeamId(this.team.getId());
                    }

                    List<TeamUser> teamUsers = selectedUsers.stream()
                            .map(user -> TeamUser.builder()
                                    .team(team)
                                    .user(user)
                                    .isLeader(Objects.equals(leaderUserId, user.getId()))
                                    .build())
                            .collect(Collectors.toList());

                    team.setTeamUsers(teamUsers);

                    this.getBeans().getTeamRepository().save(team);

                    ZkAlerts.info("Equipa " + alertInfo + " com sucesso!");
                    ZkUtils.back(this.getComponent());
                }).show();
    }

}
