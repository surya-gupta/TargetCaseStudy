package com.target.api.busstop.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class APICall
{
    private static final Logger LOG = LoggerFactory.getLogger(APICall.class);
    private static HttpHeaders getHeaders()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
    public <T> T fetchData(String URL, final Class<T> type)
    {
        LOG.info("Requesting {} for data of type {}",URL,type);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, getHeaders());
        ResponseEntity<T> responseEntity=null;
        try
        {
            responseEntity = new RestTemplate().exchange(URL, HttpMethod.GET, requestEntity,type);
            if(responseEntity.getStatusCode().equals(HttpStatus.OK))
            {
                LOG.info("Request Successful ...");
                return responseEntity.getBody();
            }
            return null;
        }
        catch (Exception exception)
        {
            LOG.error("Exception {}",exception);
            throw exception;
        }
    }
}