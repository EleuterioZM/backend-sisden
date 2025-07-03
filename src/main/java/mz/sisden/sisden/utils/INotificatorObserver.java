/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils;

import mz.sisden.sisden.entities.Notification;

public interface INotificatorObserver {
    void publish(Notification notification);

    String getIdentification();

    Boolean isActive();

    void refresh();
}
