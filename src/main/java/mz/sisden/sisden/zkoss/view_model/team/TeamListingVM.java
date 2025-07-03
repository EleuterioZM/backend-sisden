package mz.sisden.sisden.zkoss.view_model.team;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Team;
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
public class TeamListingVM extends ZkViewModel {
    private TeamListModel teamListModel;
    private List<CustomMap> selectedTeamList = new ArrayList<>();
    private String searchTerm;

    @Init(superclass = true)
    public void init() {}

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.teamListModel = new TeamListModel(this.searchTerm);

        List<Team> teamList = ZkUtils.getAttribute(ZkArgument.TEAM_LIST.name());
        if (CollectionUtils.isNotEmpty(teamList)) {
            teamList.stream()
                    .map(Team::getId)
                    .peek(this.teamListModel.getPriorityIds()::add)
                    .map(CustomMap::ofId)
                    .forEach(this.selectedTeamList::add);
        }
    }

    @Command
    public void search() {
        this.teamListModel = new TeamListModel(this.searchTerm);

        selectedTeamList.stream()
                .map(CustomMap::getId)
                .forEach(this.teamListModel.getPriorityIds()::add);

        BindUtils.postNotifyChange(this, "teamListModel");
    }

    @Command
    public void edit(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.TEAM_FORM)
                .withArgument(ZkArgument.TEAM, this.getBeans().getTeamRepository().findById(id).orElseThrow())
                .update()
                .onClose(event -> {
                    this.teamListModel = new TeamListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "teamListModel");
                });
    }

    @Command
    public void view(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.TEAM_FORM)
                .withArgument(ZkArgument.TEAM, this.getBeans().getTeamRepository().findById(id).orElseThrow())
                .readonly()
                .show();
    }

    @Command
    public void delete(@BindingParam Long id) {
        Team team = this.getBeans().getTeamRepository().findById(id).orElseThrow();
        ZkAlerts.builder()
                .question("Deseja apagar a Equipa {}?", team.getName())
                .onYesListener(event -> {
                    this.getBeans().getTeamRepository().delete(team);
                    this.teamListModel = new TeamListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "teamListModel");
                })
                .show();
    }

    @Command
    public void select() {
        Map<String, Object> arguments = ZkArgument.TEAM.put(this.selectedTeamList.get(0));
        ZkArgument.TEAM_LIST.putIn(arguments, this.selectedTeamList);
        ZkArgument.TEAM_ARRAY.putIn(arguments, this.selectedTeamList.toArray(new Team[0]));

        ZkEvent.onTeamSelected.post(this.getComponent(), arguments);
        ZkUtils.back(this.getComponent());
    }
}
