/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration.envers;


import mz.sisden.sisden.configuration.AppContext;
import mz.sisden.sisden.configuration.security.Securities;

import java.util.Optional;

public class RevisionListener implements org.hibernate.envers.RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        Optional.of(AppContext.getByClass(Securities.class))
                .ifPresent(securities -> {
                    if (revisionEntity instanceof Revision revision) {
                        revision.setUser(securities.getUser());
                    }
                });
    }
}
