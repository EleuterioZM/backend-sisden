package mz.sisden.sisden.zkoss.view_model.instituition;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Instituition;
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
public class InstituitionListingVM extends ZkViewModel {
    InstituitionListModel instituitionListModel;
    List<CustomMap> selectedInstituitionList = new ArrayList<>();
    String searchTerm;

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.instituitionListModel = new InstituitionListModel(this.searchTerm);

        List<Instituition> instituitionList = ZkUtils.getAttribute(ZkArgument.INSTITUITION_LIST.name());
        if (CollectionUtils.isNotEmpty(instituitionList)) {
            instituitionList.stream()
                    .map(Instituition::getId)
                    .peek(this.instituitionListModel.getPriorityIds()::add)
                    .map(CustomMap::ofId)
                    .forEach(this.selectedInstituitionList::add);
        }
    }

    @Command
    public void search() {
        this.instituitionListModel = new InstituitionListModel(this.searchTerm);

        selectedInstituitionList.stream()
                .map(CustomMap::getId)
                .forEach(this.instituitionListModel.getPriorityIds()::add);

        BindUtils.postNotifyChange(this, "instituitionListModel");
    }

    @Command
    public void edit(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.INSTITUITION_FORM)
                .withArgument(ZkArgument.INSTITUITION, this.getBeans().getInstituitionRepository().findById(id).orElseThrow())
                .update()
                .onClose(event -> {
                    this.instituitionListModel = new InstituitionListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "instituitionListModel");
                });
    }

    @Command
    public void view(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.INSTITUITION_FORM)
                .withArgument(ZkArgument.INSTITUITION, this.getBeans().getInstituitionRepository().findById(id).orElseThrow())
                .show();
    }

    @Command
    public void delete(@BindingParam Long id) {
        Instituition instituition = this.getBeans().getInstituitionRepository().findById(id).orElseThrow();
        ZkAlerts.builder()
                .question("Deseja apagar a Instituição {}?", instituition.getName())
                .onYesListener(event -> {
                    this.getBeans().getInstituitionRepository().delete(instituition);
                    this.instituitionListModel = new InstituitionListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "instituitionListModel");
                })
                .show();
    }

    @Command
    public void select() {
        Map<String, Object> arguments = ZkArgument.INSTITUITION.put(this.selectedInstituitionList.get(0));
        ZkArgument.INSTITUITION_LIST.putIn(arguments, this.selectedInstituitionList);
        ZkArgument.INSTITUITION_ARRAY.putIn(arguments, this.selectedInstituitionList.toArray(new Instituition[0]));

        ZkEvent.onInstituitionSelected.post(this.getComponent(), arguments);
        ZkUtils.back(this.getComponent());
    }
}
