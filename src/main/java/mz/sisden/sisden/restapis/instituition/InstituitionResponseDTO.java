package mz.sisden.sisden.restapis.instituition;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstituitionResponseDTO {
    private Long id;
    private String name;
    private String createdAt;
} 