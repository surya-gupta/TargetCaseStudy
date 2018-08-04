package com.target.api.busstop;

import com.target.api.BusStopApplication;
import com.target.api.busstop.process.ProcessTransitRequest;
import com.target.api.filemanager.model.FileDetail;
import com.target.api.filemanager.process.ProcessFileRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static com.target.api.busstop.utility.Utility.DIRECTION.NORTH;
import static com.target.api.busstop.utility.Utility.DIRECTION.SOUTH;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BusStopApplication.class)
public class BusStopApplicationTests {
    @Autowired
    private ProcessFileRequest processFileRequest;
    @Autowired
    private ProcessTransitRequest processTransitRequest;

    @Test
    public void successConditionBusApp() {
        String result = processTransitRequest.getNextTripTimeInMinutes("METRO Blue Line", "Target Field Station Platform 1", NORTH);
        IntStream.range(1, 100).parallel().forEach(i -> {
            processTransitRequest.getNextTripTimeInMinutes("METRO Blue Line", "Target Field Station Platform 1", NORTH);
        });
        System.out.println(result);
    }

    @Test
    public void failedConditionBusApp() {
        String result = processTransitRequest.getNextTripTimeInMinutes("METRO Blue Line ABCD",
                "Target Field Station Platform 1",
                SOUTH);
        System.out.println(result);
    }

    @Ignore
    @Test
    public void testSuccessFileManager() {
        List<FileDetail> fileDetails = processFileRequest.getFiles("/Users/sgupta/Downloads/");
        Optional<FileDetail> file = fileDetails.parallelStream()
                .filter(record -> record.getName().equalsIgnoreCase("bus-stop.zip"))
                .findFirst();
        System.out.println(file.get());
    }

    @Ignore

    @Test
    public void testFailedFileManager() {
        List<FileDetail> fileDetails = processFileRequest.getFiles("/Users/sgupta/Downloads/");
        Optional<FileDetail> file = fileDetails.parallelStream()
                .filter(record -> record.getName().equalsIgnoreCase("test"))
                .findAny();
        System.out.println(file.get());
    }

    @Test
    public void asyncConcurrentRequest() throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
        try(CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            httpclient.start();
            final HttpGet[] requests = new HttpGet[]{
                    new HttpGet("http://httpbin.org/ip"),
                    new HttpGet("https://httpbin.org/ip"),
                    new HttpGet("http://httpbin.org/headers")
            };
            final CountDownLatch latch = new CountDownLatch(requests.length);
            for (final HttpGet request : requests) {
                httpclient.execute(request, new FutureCallback<HttpResponse>() {

                    @Override
                    public void completed(final HttpResponse response) {
                        latch.countDown();
                        System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
                    }

                    @Override
                    public void failed(final Exception ex) {
                        latch.countDown();
                        System.out.println(request.getRequestLine() + "->" + ex);
                    }

                    @Override
                    public void cancelled() {
                        latch.countDown();
                        System.out.println(request.getRequestLine() + " cancelled");
                    }

                });
            }
            latch.await();
            System.out.println("Shutting down");
        }
        System.out.println("Done");
    }
}
