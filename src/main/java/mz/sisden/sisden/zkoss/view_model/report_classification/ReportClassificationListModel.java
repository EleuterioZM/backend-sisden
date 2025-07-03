package mz.sisden.sisden.zkoss.view_model.report_classification;

import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.zkoss.ZkListModel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReportClassificationListModel extends ZkListModel {

    public ReportClassificationListModel() {
    }

    public ReportClassificationListModel(String searchTerm) {
        super(searchTerm);
    }

    @Override
    public Function<Integer, List<CustomMap>> getPageFunction() {
        return offset -> {
            Map<String, Object> paramMap = this.createParamMap(offset);
            String query = """
                SELECT rc.id,
                       rc.gravity,
                       TO_CHAR(rc.created_at, 'DD/MM/YYYY HH24:MI') AS created_at
                FROM master.report_classification rc
                WHERE unaccent(rc.gravity) ILIKE CONCAT('%', unaccent(:search_term), '%')
                ORDER BY rc.created_at DESC
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
                SELECT COUNT(rc)
                FROM master.report_classification rc
                WHERE unaccent(rc.gravity) ILIKE CONCAT('%', unaccent(:search_term), '%')
            """;
            return this.queryForInteger(query, paramMap);
        };
    }
}
