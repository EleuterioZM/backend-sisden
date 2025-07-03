/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.io.File;

import static java.util.Objects.nonNull;

@Getter
@Setter
@Builder
public class Attachment {
    @Builder.Default
    public String ContentId = "<header>";
    private String fileName;
    private File file;
    @Builder.Default
    private Disposition disposition = Disposition.ATTACHMENT;

    @Override
    public String toString() {
        if (StringUtils.isBlank(this.fileName)) {
            if (nonNull(this.file)) {
                return this.file.getName();
            } else {
                return MessageFormatter.arrayFormat("Attachment{disposition: {}, contentId: {}", new String[]{this.disposition.getName(), this.ContentId}).getMessage();
            }
        } else {
            return this.fileName;
        }
    }

    public enum Disposition {
        ATTACHMENT,
        INLINE;

        public String getName() {
            return this.name().toLowerCase();
        }
    }
}
