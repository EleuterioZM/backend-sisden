/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.User;
import org.zkoss.bind.BindUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Stack;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Slf4j
public class Navigator {
    private final Stack<ZkPageAccess> zkPageAccessStack = new Stack<>();
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private ZkPage currZkPage;
    @Setter
    private User user;

    public Navigator(ZkPage currZkPage, User user) {
        this.zkPageAccessStack.push(ZkPageAccess.builder().zkPage(currZkPage).accessedAt(LocalDateTime.now()).build());
        this.setCurrZkPage(currZkPage);
        this.user = user;
    }

    public void go(ZkPage zkPage, Boolean push) {
        log.debug(
                "Change current page from {} ({}) to {} ({}). User: {}",
                Optional.ofNullable(this.currZkPage).map(ZkPage::getDesignation).orElse(""),
                Optional.ofNullable(this.currZkPage).map(ZkPage::getPath).orElse(""),
                Optional.ofNullable(zkPage).map(ZkPage::getDesignation).orElse(""),
                Optional.ofNullable(zkPage).map(ZkPage::getPath).orElse(""),
                user.getName()
        );

        this.currZkPage = zkPage;

        if (push && nonNull(this.currZkPage)) {
            ZkPageAccess zkPageAccess = ZkPageAccess.builder()
                    .zkPage(zkPage)
                    .accessedAt(LocalDateTime.now())
                    .build();

            this.zkPageAccessStack.push(zkPageAccess);
        }

        try {
            BindUtils.postNotifyChange(this, "currZkPage");
        } catch (Exception e) {
            log.error("Error setCurrentCurrentPage ", e);
        }
    }

    public void back() {
        ZkPage zkPage = null;

        while (!zkPageAccessStack.empty() && (zkPage = zkPageAccessStack.pop().getZkPage()).equals(this.currZkPage)) {
            log.debug("Current zkPage {} is equals to the previous zkPage, back until find different", zkPage);
        }

        if (isNull(zkPage)) {
            this.go(ZkPage.HOME, true);
        } else {
            go(zkPage, true);
        }
    }
}
