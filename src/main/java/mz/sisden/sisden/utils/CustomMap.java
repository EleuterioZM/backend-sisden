/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class CustomMap extends HashMap<String, Object> {

    public CustomMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public CustomMap(int initialCapacity) {
        super(initialCapacity);
    }

    public CustomMap() {
    }

    public CustomMap(Map<String, Object> m) {
        super(m);
    }

    public static CustomMap of(String k1, Object v1) {
        CustomMap customMap = new CustomMap();
        customMap.put(k1, v1);
        return customMap;
    }

    public static CustomMap ofId(Object v1) {
        CustomMap customMap = new CustomMap();
        customMap.put("id", v1);
        return customMap;
    }

    public Long getId() {
        return (Long) this.get("id");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomMap that = (CustomMap) o;

        Object thisId = this.get("id");
        Object thatId = that.get("id");
        if (nonNull(thisId) && nonNull(thatId) && thisId.equals(thatId)) {
            return true;
        }

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        Object thisId = this.get("id");
        if (nonNull(thisId)) {
            return thisId.hashCode();
        }

        return super.hashCode();
    }

    //getters
    public LocalDateTime getLocalDateTime(String key) {
        return MapUtils.getLocalDateTime(this, key);
    }

    public LocalDate getLocalDate(String key) {
        return MapUtils.getLocalDate(this, key);
    }

    public String getString(String key) {
        return MapUtils.getString(this, key);
    }

    public Boolean getBoolean(String key) {
        return MapUtils.getBoolean(this, key);
    }
}
