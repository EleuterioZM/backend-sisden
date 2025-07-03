/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.BaseEntity;
import mz.sisden.sisden.utils.Texter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import java.io.IOException;

import static java.nio.file.Files.readAllBytes;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride
@Audited
@Entity
@Table
public class SystemFile extends BaseEntity {

    @Id
    @SequenceGenerator(name = "system_file_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "system_file_id_sequence")
    private Long id;
    private String fileName;
    private byte[] byteData;
    private String path;

    @OneToOne
    @Fetch(FetchMode.SELECT)
    private SinglePerson singlePerson;

    public String toString() {
        return Texter.format("{}{fileName={}, path={}}", this.getHibernateClass().getSimpleName(), getFileName(), getPath());
    }

    public String getExtension() {
        if (isNotBlank(this.fileName)) {
            return this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
        }
        return null;
    }

    public byte[] tryGetBytes() {
        byte[] fileBytes = null;

        try {
            byte[] byteData = this.getByteData();
            if (ArrayUtils.isNotEmpty(byteData)) {
                fileBytes = byteData;
            } else if (StringUtils.isNotBlank(this.getPath())) {
                fileBytes = readAllBytes(java.nio.file.Paths.get(this.getPath()));
            }
        } catch (IOException e) {
            log.error("Failed to read file bytes from path {} : {}", this.getPath(), e.getMessage());
            return null;
        }

        return fileBytes;
    }

    public String getAsBase64Image() {
        return Texter.format("data:image;base64,{}", Texter.encodeBase64(this.tryGetBytes()));
    }
}
