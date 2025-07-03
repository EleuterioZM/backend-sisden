/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.utils.Texter;
import org.zkoss.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Getter
@Setter
@ZkComponent
@RequiredArgsConstructor
public class IndexVm extends ZkViewModel {

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
    }

    @Command
    @GlobalCommand
    public void navigate(@BindingParam("page") String pageName) throws Exception {
        Optional<ZkPage> pageOptional = ZkPage.findByName(pageName);

        ZkPage zkPage = pageOptional.orElseThrow(() -> new IllegalArgumentException(Texter.format("Página com o nome {} não existe.", pageName)));

        ZkUtils.getNavigateBuilder()
                .ZkPage(zkPage)
                .go();
    }

    @Command
    @GlobalCommand
    public void modal(@BindingParam("page") String pageName) throws Exception {
        Optional<ZkPage> pageOptional = ZkPage.findByName(pageName);

        ZkPage zkPage = pageOptional.orElseThrow(() -> new IllegalArgumentException(Texter.format("Página com o nome {} não existe.", pageName)));

        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(zkPage)
                .go();
    }

    public Navigator getNavigator() {
        return ZkUtils.getNavigator();
    }
}
