/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.RollBackTransactional;
import mz.sisden.sisden.entities.Notification;
import mz.sisden.sisden.repositories.NotificationRepository;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class Notificator {
    private final List<INotificatorObserver> INotificatorObserverList = new ArrayList<>();
    private final NotificationRepository notificationRepository;

    @RollBackTransactional
    public void publish(Notification notification) {
        this.refreshObservers();

        this.notificationRepository.save(notification);
        for (INotificatorObserver INotificatorObserver : this.INotificatorObserverList) {
            log.debug("Notifying {} with {}", this.INotificatorObserverList.size(), notification);
            INotificatorObserver.publish(notification);
        }
    }

    public void refresh() {
        log.debug("Refreshing {} observers.", this.INotificatorObserverList.size());
        this.INotificatorObserverList.forEach(INotificatorObserver::refresh);
        log.debug("Refreshed observers.");
    }

    public void subscribe(INotificatorObserver INotificatorObserver) {
        log.debug("Subscribing observer {}.", INotificatorObserver.getIdentification());
        this.INotificatorObserverList.add(INotificatorObserver);
        this.refreshObservers();
    }

    private void refreshObservers() {
        List<INotificatorObserver> noToDelete = new ArrayList<>();

        for (INotificatorObserver INotificatorObserver : this.INotificatorObserverList) {
            if (INotificatorObserver instanceof Component component) {
                Desktop desktop = component.getDesktop();
                if (isNull(desktop) || !desktop.isAlive()) {
                    noToDelete.add(INotificatorObserver);
                }
            } else if (!INotificatorObserver.isActive()) {
                noToDelete.add(INotificatorObserver);
            }
        }

        noToDelete.forEach(this::unsubscribe);
    }

    public void unsubscribe(INotificatorObserver INotificatorObserver) {
        log.debug("Unsubscribing observer {}.", INotificatorObserver.getIdentification());
        this.INotificatorObserverList.remove(INotificatorObserver);
    }
}
