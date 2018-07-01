package com.target.api.busstop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NextTripRoute
{
    @JsonProperty("Description")
    private String description;
    @JsonProperty("ProviderID")
    private String providerId;
    @JsonProperty("Route")
    private String route;
}
