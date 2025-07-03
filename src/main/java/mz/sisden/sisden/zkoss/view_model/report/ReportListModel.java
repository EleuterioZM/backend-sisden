package mz.sisden.sisden.zkoss.view_model.report;

import mz.sisden.sisden.enums.Status;
import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.zkoss.ZkListModel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReportListModel extends ZkListModel {

    private String status;

    public ReportListModel() {}

    public ReportListModel(String searchTerm, String status) {
        super(searchTerm);
        // Converter status em português para o valor do banco
        if (status == null || status.isEmpty() || status.equalsIgnoreCase("Todos")) {
            this.status = "";
        } else if (status.equalsIgnoreCase("Abertos")) {
            this.status = "OPEN";
        } else if (status.equalsIgnoreCase("Em andamento")) {
            this.status = "IN_PROGRESS";
        } else if (status.equalsIgnoreCase("Finalizados")) {
            this.status = "RESOLVED";
        } else {
            this.status = status;
        }
    }

    @Override
    public Function<Integer, List<CustomMap>> getPageFunction() {
        return offset -> {
            Map<String, Object> paramMap = this.createParamMap(offset);
            if (status != null && !status.isEmpty()) {
                paramMap.put("status", status);
            }

            StringBuilder query = new StringBuilder();
            query.append("SELECT r.id,\n");
            query.append("       r.name,\n");
            query.append("       rt.name AS report_type_name,\n");
            query.append("       r.status,\n");
            query.append("       (\n");
            query.append("           SELECT rpn.phone_number\n");
            query.append("           FROM master.report_phone_numbers rpn\n");
            query.append("           WHERE rpn.report_id = r.id\n");
            query.append("           LIMIT 1\n");
            query.append("       ) AS phone_number,\n");
            query.append("       TO_CHAR(r.created_at, 'DD/MM/YYYY HH24:MI') AS created_at,\n");
            query.append("       t.name AS team_name\n");
            query.append("FROM master.report r\n");
            query.append("LEFT JOIN master.report_type rt ON rt.id = r.report_type_id\n");
            query.append("LEFT JOIN master.team t ON t.id = r.responsible_team_id\n");
            query.append("WHERE unaccent(r.name) ILIKE CONCAT('%', unaccent(:search_term), '%')\n");
            if (status != null && !status.isEmpty()) {
                query.append(" AND r.status = :status\n");
            }
            query.append("ORDER BY r.created_at DESC\n");
            query.append("LIMIT :limit OFFSET :offset\n");

            List<CustomMap> list = this.queryForList(query.toString(), paramMap);

            for (CustomMap map : list) {
                String statusStr = map.getString("status");
                if (statusStr != null) {
                    try {
                        Status statusEnum = Status.valueOf(statusStr);
                        map.put("status", statusEnum.getLabel());
                    } catch (IllegalArgumentException e) {
                        map.put("status", statusStr);
                    }
                }

                String phone = map.getString("phone_number");
                map.put("phone_number", phone != null ? phone.trim() : "");
            }
            return list;
        };
    }

    @Override
    public Supplier<Integer> getSizeSupplier() {
        return () -> {
            Map<String, Object> paramMap = this.createParamMap();
            if (status != null && !status.isEmpty()) {
                paramMap.put("status", status);
            }
            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(r)\n");
            query.append("FROM master.report r\n");
            query.append("WHERE unaccent(r.name) ILIKE CONCAT('%', unaccent(:search_term), '%')\n");
            if (status != null && !status.isEmpty()) {
                query.append(" AND r.status = :status\n");
            }
            return this.queryForInteger(query.toString(), paramMap);
        };
    }
}
