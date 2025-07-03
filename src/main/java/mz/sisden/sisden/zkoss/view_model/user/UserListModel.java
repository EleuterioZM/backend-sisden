package mz.sisden.sisden.zkoss.view_model.user;

import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.zkoss.ZkListModel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class UserListModel extends ZkListModel {

    public UserListModel() {}

    public UserListModel(String searchTerm) {
        super(searchTerm);
    }

    @Override
    public Function<Integer, List<CustomMap>> getPageFunction() {
        return offset -> {
            Map<String, Object> paramMap = this.createParamMap(offset);

            String query = """
                    SELECT u.id,
                           u.username,
                           TO_CHAR(u.created_at, 'DD/MM/YYYY HH24:MI') AS created_at
                    FROM master."user" u
                    WHERE (:search_term IS NULL OR :search_term = '' OR 
                           LOWER(u.username) LIKE LOWER(CONCAT('%', :search_term, '%')))
                    ORDER BY u.created_at DESC
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
                SELECT COUNT(u)
                FROM master."user" u
                WHERE (:search_term IS NULL OR :search_term = '' OR 
                       LOWER(u.username) LIKE LOWER(CONCAT('%', :search_term, '%')))
            """;
            return this.queryForInteger(query, paramMap);
        };
    }
} 