/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.controller.notification;

import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.utils.CustomMapRowMapper;
import mz.sisden.sisden.utils.Texter;
import mz.sisden.sisden.zkoss.ZkListModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public class NotificationListModel extends ZkListModel {

    public NotificationListModel() {
        super();
    }

    public NotificationListModel(String search) {
        super(search);
    }

    public String getDataQuery() {
        return """
                select n.id, n.title, n.description, n.viewed, n.created_at
                from master.notification n
                where public.unaccent(n.description) ilike public.unaccent(concat('%', :search_term, '%'))
                or public.unaccent(n.title) ilike public.unaccent(concat('%', :search_term, '%'))
                ORDER BY n.created_at desc
                limit :limit offset :offset;
                """;
    }

    public String getCountQuery() {
        return """
                select count(n)
                from master.notification n
                where public.unaccent(n.description) ilike public.unaccent(concat('%', :search_term, '%'))
                or public.unaccent(n.title) ilike public.unaccent(concat('%', :search_term, '%'))
                """;
    }

    @Override
    public Function<Integer, List<CustomMap>> getPageFunction() {
        return null;
    }

    @Override
    public Supplier<Integer> getSizeSupplier() {
        return null;
    }

    @Override
    public CustomMap getElementAt(int index) {
        Map<Integer, CustomMap> cacheMap = this.getCache();

        CustomMap target = cacheMap.get(index);
        if (isNull(target)) {

            Map<String, Object> paramMap = this.createParamMap(index);

            List<CustomMap> mapList = this.namedParameterJdbcTemplate.query(this.getDataQuery(), paramMap, new CustomMapRowMapper());

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

    @Override
    public int getSize() {
        if (isNull(this.size)) {
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("search_term", this.searchTerm);
            return this.size = Objects.requireNonNullElse(this.namedParameterJdbcTemplate.queryForObject(this.getCountQuery(), paramMap, Integer.class), 0);
        }
        return size;
    }
}
