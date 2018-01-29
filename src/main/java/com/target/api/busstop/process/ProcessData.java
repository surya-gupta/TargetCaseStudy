package com.target.api.busstop.process;

import com.target.api.busstop.gateway.APICall;
import com.target.api.busstop.model.NextTripDeparture;
import com.target.api.busstop.model.NextTripRoute;
import com.target.api.busstop.model.TextAndValue;
import static com.target.api.busstop.utility.Utility.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ProcessData
{
    private static final Logger LOG = LoggerFactory.getLogger(ProcessData.class);
    @Autowired
    APICall apiCall;
    public String getNextTripTimeInMinutes(String busRoute, String busStopName, DIRECTION direction)
    {
        NextTripRoute[] nextTripRoutes=apiCall.fetchData(ROUTE_URL, NextTripRoute[].class);
        Optional<NextTripRoute> optionalNextTripRoute=Arrays.stream(nextTripRoutes).parallel().filter(record -> match(record.getDescription(), busRoute)).findFirst();
        Assert.isTrue(optionalNextTripRoute.isPresent(),"No Matching Route "+busRoute+" found .!!!!");
        NextTripRoute nextTripRoute=optionalNextTripRoute.get();
        LOG.info("Matching Route --> {}" , nextTripRoute);

        TextAndValue[] routeDirections =apiCall.fetchData(String.format(DIRECTIONS_URL, nextTripRoute.getRoute()), TextAndValue[].class);
        Optional<TextAndValue> optionalDirection=Arrays.stream(routeDirections).parallel().filter(record-> match(record.getText(), direction.getValue())).findFirst();
        Assert.isTrue(optionalDirection.isPresent(),"No Matching Direction "+direction.getValue()+" found.!!!!");
        TextAndValue routeDirection=optionalDirection.get();
        LOG.info("Matching Direction --> {}", routeDirection);

        TextAndValue[] stops =apiCall.fetchData(String.format(STOPS_URL, nextTripRoute.getRoute(), routeDirection.getValue()), TextAndValue[].class);
        Optional<TextAndValue> optionalStop=Arrays.stream(stops).parallel().filter(record-> match(record.getText(), busStopName)).findFirst();
        Assert.isTrue(optionalStop.isPresent(),"No Matching Stop for  "+busStopName);
        TextAndValue stop=optionalStop.get();
        LOG.info("Matching Direction --> {}", stop);

        NextTripDeparture[] nextTripDepartures=apiCall.fetchData(String.format(NEXT_TRIP_URL, nextTripRoute.getRoute(), routeDirection.getValue(), stop.getValue()), NextTripDeparture[].class);
        Optional<NextTripDeparture> optionalNextTripDeparture=Arrays.stream(nextTripDepartures).findFirst();

        if (!optionalNextTripDeparture.isPresent())
        {
            LOG.info("Looks like no next transit available for the day.");
            return "";
        }
        else
        {
            LOG.info("Matched Record {}",optionalNextTripDeparture.get());
            return getMinutes(optionalNextTripDeparture.get().getDepartureTime());
        }
    }
}
