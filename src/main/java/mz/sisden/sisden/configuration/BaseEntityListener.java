/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.security.Securities;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.utils.DateTime;

import java.util.Optional;

@Slf4j
public class BaseEntityListener {

    @PrePersist
    public void prePersist(BaseEntity baseEntity) {
        Optional.ofNullable(AppContext.getByClass(Securities.class).getUser())
                .map(User::getId)
                .ifPresent(baseEntity::setCreatedByUserId);

        String userName = Optional.ofNullable(AppContext.getByClass(Securities.class).getUser())
                .map(User::getName)
                .orElse("System");

        log.debug(
                "PERSISTENCE DETECTED {ENTITY: {}, DATE-TIME: {}, USER: {}, OBJECT: {}}",
                baseEntity.getClass(),
                DateTime.currDateTime(),
                userName,
                baseEntity
        );
    }

    @PreUpdate
    public void preUpdate(BaseEntity baseEntity) {
        Optional.ofNullable(AppContext.getByClass(Securities.class).getUser())
                .map(User::getId)
                .ifPresent(baseEntity::setUpdatedByUserId);

        String userName = Optional.ofNullable(AppContext.getByClass(Securities.class).getUser())
                .map(User::getName)
                .orElse("System");

        log.debug(
                "UPDATE DETECTED {ENTITY: {}, DATE-TIME: {}, USER: {}, OBJECT: {}}",
                baseEntity.getClass(),
                DateTime.currDateTime(),
                userName,
                baseEntity
        );
    }

    @PreRemove
    public void preRemove(BaseEntity baseEntity) {

        String userName = Optional.ofNullable(AppContext.getByClass(Securities.class).getUser())
                .map(User::getName)
                .orElse("System");

        log.debug(
                "DELETE DETECTED {ENTITY: {}, DATE-TIME: {}, USER: {}, OBJECT: {}}",
                baseEntity.getClass(),
                DateTime.currDateTime(),
                userName,
                baseEntity
        );
    }
}