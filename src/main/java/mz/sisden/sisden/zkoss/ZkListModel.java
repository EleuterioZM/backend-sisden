/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.AppConstants;
import mz.sisden.sisden.configuration.AppContext;
import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.utils.CustomMapRowMapper;
import mz.sisden.sisden.utils.DateTime;
import mz.sisden.sisden.utils.Texter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

@Slf4j
public abstract class ZkListModel extends AbstractListModel<CustomMap> {
    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected final AppConstants appConstants;
    protected Integer size = null;
    protected String searchTerm;

    @Getter
    protected List<Long> priorityIds = new ArrayList<>(List.of(-1L));

    public ZkListModel() {
        this.namedParameterJdbcTemplate = AppContext.getByClass(NamedParameterJdbcTemplate.class);
        this.appConstants = AppContext.getByClass(AppConstants.class);
        this.setMultiple(true);
        this.searchTerm = "";
        this.resetCache();
    }

    public ZkListModel(String searchTerm) {
        this();
        this.searchTerm = trimToEmpty(searchTerm);
    }

    @Getter
    protected String cacheKey = this.getClass().getSimpleName() + " - " + UUID.randomUUID();

    public abstract Function<Integer, List<CustomMap>> getPageFunction();

    public abstract Supplier<Integer> getSizeSupplier();

    protected int calculateOffset(int index) {
        return index / this.getPageSize() * this.getPageSize();
    }

    @Override
    public CustomMap getElementAt(int index) {
        Map<Integer, CustomMap> cacheMap = this.getCache();

        CustomMap target = cacheMap.get(index);
        if (isNull(target)) {

            List<CustomMap> mapList = this.getPageFunction().apply(index);

            int indexTemp = index;
            for (CustomMap map : mapList) {
                cacheMap.put(indexTemp++, map);
            }
        } else {
            return target;
        }

        target = cacheMap.get(index);

        if (isNull(target)) {
            throw new RuntimeException(Texter.format("O elemento no índice {} não foi encontrado.", index));
        } else {
            return target;
        }
    }

    @SuppressWarnings({"unchecked"})
    protected Map<Integer, CustomMap> getCache() {
        Desktop desktop = Executions.getCurrent().getDesktop();
        Map<Integer, CustomMap> cache = (Map<Integer, CustomMap>) desktop.getAttribute(this.getCacheKey());
        if (isNull(cache)) {
            cache = new HashMap<>();
            desktop.setAttribute(this.getCacheKey(), cache);
        }
        return cache;
    }

    public void resetCache() {
        Desktop desktop = Executions.getCurrent().getDesktop();
        desktop.removeAttribute(this.getCacheKey());
        desktop.setAttribute(this.getCacheKey(), null);
        this.size = null;
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
    }

    public Map<String, Object> createParamMap(int index) {
        Map<String, Object> paramMap = this.createParamMap();
        paramMap.put("limit", this.getPageSize());
        paramMap.put("offset", this.calculateOffset(index));
        return paramMap;
    }

    public Map<String, Object> createParamMap() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("search_term", this.searchTerm);
        paramMap.put("priority_ids", this.priorityIds);
        AppConstants.fillMap(paramMap);
        DateTime.fillMap(paramMap);
        return paramMap;
    }

    @Override
    public int getSize() {
        if (isNull(this.size)) {
            return this.size = this.getSizeSupplier().get();
        }
        return size;
    }

    public List<CustomMap> queryForList(String query, Map<String, Object> paramMap) {
        return this.namedParameterJdbcTemplate.query(query, paramMap, new CustomMapRowMapper());
    }

    public Integer queryForInteger(String query, Map<String, Object> paramMap) {
        return this.namedParameterJdbcTemplate.queryForObject(query, paramMap, Integer.class);
    }
}
