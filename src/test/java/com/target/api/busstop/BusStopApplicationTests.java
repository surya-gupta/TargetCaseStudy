package com.target.api.busstop;

import com.target.api.BusStopApplication;
import com.target.api.busstop.process.ProcessData;
import static com.target.api.busstop.utility.Utility.DIRECTION.NORTH;

import com.target.api.filemanager.model.FileDetail;
import com.target.api.filemanager.process.ProcessDataRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BusStopApplication.class)
public class BusStopApplicationTests
{
	@Autowired
	ProcessDataRequest processDataRequest;
	@Autowired
	ProcessData processData;
	@Test
	public void contextLoads()
	{
	}
	@Test
	public void successConditionBusApp()
	{
       String result=processData.getNextTripTimeInMinutes("METRO Blue Line",
														  "Target Field Station Platform 1",
														  NORTH);
       System.out.println(result);
	}
	@Test
	public void testSuccessFileManager()
	{
		List<FileDetail> fileDetails=processDataRequest.getFiles("/Users/sgupta/Downloads/");
		Optional<FileDetail> file= fileDetails.parallelStream()
											  .filter(record->record.getName().equalsIgnoreCase("bus-stop.zip"))
											  .findFirst();
		System.out.println(file.get());
	}
	@Test
	public void testFailedFileManager()
	{
		List<FileDetail> fileDetails=processDataRequest.getFiles("/Users/sgupta/Downloads/");
		Optional<FileDetail> file= fileDetails.parallelStream()
											  .filter(record->record.getName().equalsIgnoreCase("test"))
											  .findAny();
		System.out.println(file.get());
	}
}
