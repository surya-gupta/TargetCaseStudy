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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
