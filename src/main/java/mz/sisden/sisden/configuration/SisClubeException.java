/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.helpers.MessageFormatter;

@Getter
@Setter
public class SisClubeException extends RuntimeException {
    public SisClubeException(String message) {
        super(message);
    }

    public SisClubeException(String message, Object... objects) {
        super(MessageFormatter.arrayFormat(message, objects).getMessage());
    }
}
