package com.target.api.busstop.utility;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;

public class Utility
{
    private static final Logger LOG = LoggerFactory.getLogger(Utility.class);
    public enum DIRECTION
    {
       NORTH("NORTHBOUND") ,
       SOUTH("SOUTHBOUND"),
       EAST("EASTBOUND"),
       WEST("WESTBOUND");

       private String value;
        DIRECTION(String value)
       {
           this.value=value;
       }
       public  String getValue()
       {
            return value;
       }
       public static DIRECTION getDirection(String direction)
       {
           if(direction.equalsIgnoreCase("north"))
           {
               return NORTH;
           }
           else if(direction.equalsIgnoreCase("south"))
           {
               return SOUTH;
           }
           else if(direction.equalsIgnoreCase("east"))
           {
               return EAST;
           }
           else if (direction.equalsIgnoreCase("west"))
           {
               return WEST;
           }
           else
           {
               return null;
           }
       }
    };
    public static final String ROUTE_URL="http://svc.metrotransit.org/NexTrip/Routes";
    public static final String DIRECTIONS_URL="http://svc.metrotransit.org/NexTrip/Directions/%s";
    public static final String STOPS_URL="http://svc.metrotransit.org/NexTrip/Stops/%s/%s";
    public static final String NEXT_TRIP_URL="http://svc.metrotransit.org/NexTrip/%s/%s/%s";
    public static final String getMinutes(String dateString)
    {
        LocalDateTime now=LocalDateTime.now();
        LocalDateTime nextTravelTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(dateString.substring(6, 19)).longValue()),
                                                               ZoneId.systemDefault());
        LOG.info("Now {}",now);
        LOG.info("Next Trip @ {}",nextTravelTime);
        double minutes=Duration.between(now, nextTravelTime).getSeconds()/60 ;
        if (minutes==0.0d)
        {
            return "";
        }
        else
        {
            return minutes + " minute|s";
        }
    }
    public static boolean match(String src, String what)
    {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--)
        {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;
            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }
        return false;
    }
}
