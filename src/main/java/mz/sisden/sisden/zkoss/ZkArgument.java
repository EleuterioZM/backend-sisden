/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss;

import lombok.Getter;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.ext.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum ZkArgument {
    popup, read, update, singleSelection, multipleSelection, selectable,

    ME, USER, FROM_ZK_PAGE, ZK_PAGE,

    INCLUDE_COMPONENT,

    NAME, BYTE_DATA, FORMAT,

    NAVIGATOR, SYSTEM_FILE, CUSTOM_FILE_LIST, FILE_WRAPPER,

    //

    REPORT_TYPE, REPORT_TYPE_ARRAY, REPORT_TYPE_LIST,

    REPORT_CLASSIFICATION, REPORT_CLASSIFICATION_ARRAY, REPORT_CLASSIFICATION_LIST,

    INSTITUITION, INSTITUITION_ARRAY, INSTITUITION_LIST,

    TEAM, TEAM_ARRAY, TEAM_LIST,

    REPORT, REPORT_ARRAY, REPORT_LIST,

    USER_ARRAY, USER_LIST,

    USER_GROUP, USER_GROUP_ARRAY, USER_GROUP_LIST,

    PERMISSION, MODULE
    ;

    public Map<String, Object> put(Object argument) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(this.name(), argument);
        return arguments;
    }

    public Map<String, Object> put(Map<String, Object> arguments, Object argument) {
        return this.putIn(arguments, argument);
    }

    public Map<String, Object> putIn(Map<String, Object> arguments, Object argument) {
        if (Objects.isNull(arguments)) {
            return this.put(argument);
        }
        arguments.put(this.name(), argument);
        return arguments;
    }

    @SuppressWarnings({"unchecked"})
    public <T> T get(Map<String, Object> arguments) {
        return (T) arguments.get(this.name());
    }

    @SuppressWarnings({"unchecked"})
    public <T> Optional<T> get(Scope scope) {
        return Optional.ofNullable((T) scope.getAttribute(this.name()));
    }

    @SuppressWarnings({"unchecked"})
    public <T> T get(Event event) {
        return this.get((Map<String, Object>) event.getData());
    }

    public static ArgumentsBuilder add(ZkArgument zkArgument, Object value) {
        return new ArgumentsBuilder(zkArgument, value);
    }

    @Getter
    public static class ArgumentsBuilder {
        private final Map<String, Object> arguments = new HashMap<>();

        public ArgumentsBuilder(ZkArgument zkArgument, Object value) {
            zkArgument.put(arguments, value);
        }

        public ArgumentsBuilder add(ZkArgument zkArgument, Object value) {
            zkArgument.put(arguments, value);
            return ArgumentsBuilder.this;
        }

        public Map<String, Object> build() {
            return this.arguments;
        }
    }

}
