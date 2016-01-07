package org.seanano.coop.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * An interface representing an object with an identifier.
 */
@ApiModel
public interface Identifiable {
    /**
     * Gets the identifier of this object.
     * 
     * @return identifier of this object
     */
    @ApiModelProperty(value = "Identifier of the object", required = true)
    public Integer getId();
}
