package com.target.api.busstop.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.Future;

@Service
public class APICall
{
    @Autowired
    private CloseableHttpAsyncClient client;
    @Autowired
    private RestTemplate restTemplate;
    private ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(APICall.class);
    private static HttpHeaders getHeaders()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public <T> T fetchData1(String URL, final Class<T> type)
    {
        LOG.info("Requesting {} for data of type {}",URL,type);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, getHeaders());
        ResponseEntity<T> responseEntity=null;
        try
        {
            responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, type);
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


    public <T> T fetchData(String URL, final Class<T> type) {
        LOG.info("Requesting {} for data of type {}", URL, type);
        HttpGet get = new HttpGet(URL);
        get.setHeader("ContentType", "application/json");
        get.setHeader("Accept", "application/json");

        try {
            Future<HttpResponse> future = client.execute(get, null);
            return mapper.readValue(EntityUtils.toString(future.get().getEntity()), type);
        } catch (Exception exception) {
            LOG.error("Exception {}", exception);
            return null;
        }
    }


}