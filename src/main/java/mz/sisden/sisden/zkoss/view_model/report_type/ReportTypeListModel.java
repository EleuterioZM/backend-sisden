package mz.sisden.sisden.zkoss.view_model.report_type;

import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.zkoss.ZkListModel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReportTypeListModel extends ZkListModel {

    public ReportTypeListModel() {
    }

    public ReportTypeListModel(String searchTerm) {
        super(searchTerm);
    }

    @Override
    public Function<Integer, List<CustomMap>> getPageFunction() {
        return offset -> {
            Map<String, Object> paramMap = this.createParamMap(offset);
            String query = """
                    SELECT rt.id,
                           rt.name,
                           TO_CHAR(rt.created_at, 'DD/MM/YYYY HH24:MI') AS created_at,
                           i.name AS instituition_name
                    FROM master.report_type rt
                    LEFT JOIN master.instituition i ON rt.instituition_id = i.id
                    WHERE unaccent(rt.name) ILIKE CONCAT('%', unaccent(:search_term), '%')
                    ORDER BY rt.created_at DESC
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
                SELECT COUNT(rt)
                FROM master.report_type rt
                WHERE unaccent(rt.name) ILIKE CONCAT('%', unaccent(:search_term), '%')
            """;
            return this.queryForInteger(query, paramMap);
        };
    }
}
