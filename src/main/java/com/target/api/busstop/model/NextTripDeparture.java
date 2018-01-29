package com.target.api.busstop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NextTripDeparture
{
    @JsonProperty("Actual")
    private boolean actual;
    @JsonProperty("BlockNumber")
    private int blockNumber;
    @JsonProperty("DepartureText")
    private String departureText;
    @JsonProperty("DepartureTime")
    private String departureTime;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Gate")
    private String gate;
    @JsonProperty("Route")
    private String route;
    @JsonProperty("RouteDirection")
    private String routeDirection;
    @JsonProperty("Terminal")
    private String terminal;
    @JsonProperty("VehicleHeading")
    private int vehicleHeading;
    @JsonProperty("VehicleLatitude")
    private int vehicleLatitude;
    @JsonProperty("VehicleLongitude")
    private int vehicleLongitude;
}
