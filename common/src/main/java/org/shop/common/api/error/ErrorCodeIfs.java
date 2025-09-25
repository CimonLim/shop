package org.shop.common.api.error;

import java.io.Serializable;

public interface ErrorCodeIfs extends Serializable {

    Integer getHttpStatusCode();

    Integer getErrorCodeValue();

    String getDescription();
}
