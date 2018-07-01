package com.target.api.busstop.controller;

import com.target.api.busstop.process.ProcessTransitRequest;
import com.target.api.busstop.utility.Utility.DIRECTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/trip", method = RequestMethod.POST)
public class NextTripTime {
    private static final Logger LOG = LoggerFactory.getLogger(NextTripTime.class);
    @Autowired
    private ProcessTransitRequest processTransitRequest;

    @RequestMapping(value = "/getTime", method = RequestMethod.POST)
    public @ResponseBody
    String getTime(@RequestBody HashMap<String, Object> request) {
        LOG.info("Process begins to get time of next transit.... ");
        String busRoute = ((String) request.get("BusRoute")).trim();
        String busStopName = ((String) request.get("BusStopName")).trim();
        DIRECTION direction = DIRECTION.getDirection(((String) request.get("Direction")).trim());
        Assert.isTrue(busRoute != null && !busRoute.isEmpty(), "Bus Route Should not be null or empty!!!");
        Assert.isTrue(busStopName != null && !busStopName.isEmpty(), "Bus Stop Name Should not be null or empty!!!");
        Assert.isTrue(direction != null, "Invalid Direction!!!");
        LOG.info("Data to be processed .... {} , {} , {} ", busRoute, busStopName, direction.getValue());
        return processTransitRequest.getNextTripTimeInMinutes(busRoute, busStopName, direction);
    }
}
