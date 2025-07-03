package mz.sisden.sisden.zkoss.view_model.instituition;

import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.zkoss.ZkListModel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class InstituitionListModel extends ZkListModel {

    public InstituitionListModel() {
    }

    public InstituitionListModel(String searchTerm) {
        super(searchTerm);
    }

    @Override
    public Function<Integer, List<CustomMap>> getPageFunction() {
        return offset -> {
            Map<String, Object> paramMap = this.createParamMap(offset);
            String query = """
                                SELECT i.id,
                                       i.name,
                                       TO_CHAR(i.created_at, 'DD/MM/YYYY HH24:MI') AS created_at
                                FROM master.instituition i
                                WHERE unaccent(i.name) ILIKE CONCAT('%', unaccent(:search_term), '%')
                                ORDER BY i.created_at DESC
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
                SELECT COUNT(i)
                FROM master.instituition i
                WHERE unaccent(i.name) ILIKE CONCAT('%', unaccent(:search_term), '%')
            """;
            return this.queryForInteger(query, paramMap);
        };
    }
}
