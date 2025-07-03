package mz.sisden.sisden.zkoss.view_model.team;

import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.zkoss.ZkListModel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class TeamListModel extends ZkListModel {

    public TeamListModel() {
    }

    public TeamListModel(String searchTerm) {
        super(searchTerm);
    }

    @Override
    public Function<Integer, List<CustomMap>> getPageFunction() {
        return offset -> {
            Map<String, Object> paramMap = this.createParamMap(offset);
            String query = """
                SELECT t.id,
                       t.name,
                       TO_CHAR(t.created_at, 'DD/MM/YYYY HH24:MI') AS created_at
                FROM master.team t
                WHERE unaccent(t.name) ILIKE CONCAT('%', unaccent(:search_term), '%')
                ORDER BY t.created_at DESC
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
                SELECT COUNT(t)
                FROM master.team t
                WHERE unaccent(t.name) ILIKE CONCAT('%', unaccent(:search_term), '%')
            """;
            return this.queryForInteger(query, paramMap);
        };
    }
}
