/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss;

import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.utils.Texter;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zul.Messagebox;

import static java.util.Objects.nonNull;
import static org.zkoss.zk.ui.util.Notification.TYPE_ERROR;
import static org.zkoss.zk.ui.util.Notification.TYPE_WARNING;

@Slf4j
public class ZkAlerts {
    public static void info(String message, Object... objects) {
        log.info(Texter.format(message, objects));
        Notification.show(Texter.format(message, objects), false);
    }

    public static void warn(String message, Object... objects) {
        log.warn(Texter.format(message, objects));
        Notification.show(Texter.format(message, objects), TYPE_WARNING, null, null, -1, false);
    }

    public static void error(String message, Object... objects) {
        log.error(Texter.format(message, objects));
        Notification.show(Texter.format(message, objects), TYPE_ERROR, null, null, -1, false);
    }

    public static void question(String message, EventListener<Messagebox.ClickEvent> eventListener) {
        Messagebox.show(
                message,
                "Questão",
                new Messagebox.Button[]{Messagebox.Button.NO, Messagebox.Button.YES},
                new String[]{"Não", "Sim"},
                Messagebox.QUESTION,
                Messagebox.Button.YES,
                event -> {
                    if (nonNull(event.getButton())) {
                        eventListener.onEvent(event);
                    }
                });
    }

    public static AlertBuilder builder() {
        return new AlertBuilder();
    }

    public static class AlertBuilder {
        String message;
        boolean info, warn, error, question;
        EventListener<Messagebox.ClickEvent> eventListener, onYesListener, onNoListener;

        public AlertBuilder info(String message, Object... objects) {
            this.message = Texter.format(message, objects);
            this.info = true;
            return this;
        }

        public AlertBuilder warn(String message, Object... objects) {
            this.message = Texter.format(message, objects);
            this.warn = true;
            return this;
        }

        public AlertBuilder error(String message, Object... objects) {
            this.message = Texter.format(message, objects);
            this.error = true;
            return this;
        }

        public AlertBuilder question(String message, Object... objects) {
            this.message = Texter.format(message, objects);
            this.question = true;
            return this;
        }

        public AlertBuilder eventListener(EventListener<Messagebox.ClickEvent> eventListener) {
            this.eventListener = eventListener;
            return this;
        }

        public AlertBuilder onYesListener(EventListener<Messagebox.ClickEvent> onYesListener) {
            this.onYesListener = onYesListener;
            return this;
        }

        public void onYes(EventListener<Messagebox.ClickEvent> onYesListener) {
            this.onYesListener = onYesListener;
            this.show();
        }

        public AlertBuilder onNoListener(EventListener<Messagebox.ClickEvent> onNoListener) {
            this.onNoListener = onNoListener;
            return this;
        }

        public void show() {
            if (this.info) {
                ZkAlerts.info(message);
            }
            if (this.warn) {
                ZkAlerts.warn(message);
            }
            if (this.error) {
                ZkAlerts.error(message);
            }
            if (this.question) {
                ZkAlerts.question(message, eventListener -> {
                    if (nonNull(this.onYesListener) && Messagebox.Button.YES.equals(eventListener.getButton())) {
                        this.onYesListener.onEvent(eventListener);
                    } else if (nonNull(this.onNoListener) && Messagebox.Button.NO.equals(eventListener.getButton())) {
                        this.onNoListener.onEvent(eventListener);
                    } else if (nonNull(this.eventListener)) {
                        this.eventListener.onEvent(eventListener);
                    }
                });
            }
        }
    }
}
