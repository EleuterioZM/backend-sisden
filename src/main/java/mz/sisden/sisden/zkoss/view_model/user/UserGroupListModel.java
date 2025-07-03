package mz.sisden.sisden.zkoss.view_model.user;

import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.zkoss.ZkListModel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class UserGroupListModel extends ZkListModel {

    public UserGroupListModel() {
    }

    public UserGroupListModel(String searchTerm) {
        super(searchTerm);
    }

    @Override
    public Function<Integer, List<CustomMap>> getPageFunction() {
        return offset -> {
            Map<String, Object> paramMap = this.createParamMap(offset);
            String query = """
                                SELECT ug.id,
                                       ug.code,
                                       ug.name,
                                       ug.description,
                                       TO_CHAR(ug.created_at, 'DD/MM/YYYY HH24:MI') AS created_at
                                FROM master.user_group ug
                                WHERE unaccent(ug.name) ILIKE CONCAT('%', unaccent(:search_term), '%')
                                   OR unaccent(ug.code) ILIKE CONCAT('%', unaccent(:search_term), '%')
                                   OR unaccent(ug.description) ILIKE CONCAT('%', unaccent(:search_term), '%')
                                ORDER BY ug.created_at DESC
                                LIMIT :limit OFFSET :offset
                            """;

            return this.queryForList(query, paramMap);
        };
    }

    @Override
    public Supplier<Integer> getSizeSupplier() {
        return () -> {
            Map<String, Object> paramMap = this.createParamMap();
            String query = """
                SELECT COUNT(ug)
                FROM master.user_group ug
                WHERE unaccent(ug.name) ILIKE CONCAT('%', unaccent(:search_term), '%')
                   OR unaccent(ug.code) ILIKE CONCAT('%', unaccent(:search_term), '%')
                   OR unaccent(ug.description) ILIKE CONCAT('%', unaccent(:search_term), '%')
            """;
            return this.queryForInteger(query, paramMap);
        };
    }
} 