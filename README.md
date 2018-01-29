# Target Case Study

Application is Designed on Spring Boot (Maven Build) with 2 modules Bus-Stop and File-Manager. 

    1. Below are the classes handling core programming logic    
   [Get Transit Time](https://github.com/suryagup/TargetCaseStudy/blob/master/src/main/java/com/target/api/busstop/process/ProcessTransitRequest.java)
   [Get File Details](https://github.com/suryagup/TargetCaseStudy/blob/master/src/main/java/com/target/api/filemanager/process/ProcessFileRequest.java)
       
    2. Run Application [https://github.com/suryagup/TargetCaseStudy/blob/master/src/main/java/com/target/api/BusStopApplication.java]
    3. JUnits  
    
   [JUnits Test](https://github.com/suryagup/TargetCaseStudy/blob/master/src/test/java/com/target/api/busstop/BusStopApplicationTests.java) 
    

API details :



1. ***Get Next Transit Time <br>***

    To Get Next Trip Time for Destination. 

    *ENDPOINT*: `localhost:8080/trip/getTime` <br>
    *REQUEST METHOD*: `POST` <br>
    *BODY*: 
        
        {
        	"BusRoute":"METRO Blue Line",
        	"BusStopName":"Target Field Station Platform 1",
        	"Direction":"south"
        }
        
    *APPLICATION TYPE*: `JSON`
    
    *Response*:
    
            5 minute|s
            
    *Fields*:
     * **BusRoute** : Bus Route
     * **BusStopName** : Bus Stop Name
     * **Direction** : Travel Direction
     
1. ***Get All Files in a provided Directory <br>***

    List of Files and its Size in a input directory

    *ENDPOINT*: `localhost:8080/file/getDetailFromDirectory` <br>
    *REQUEST METHOD*: `POST` <br>
    *BODY*: 
        
        {
        	"dir":"/Users/surya/Downloads/"
        }
        
    *APPLICATION TYPE*: `JSON`
    
    *Response*:
    
              [{
                    "name": "7069281-mi-microservices-architecture-design-whitepaper-in.pdf",
                    "size": 314825
               },
               {
                    "name": "AWS_Serverless_Multi-Tier_Architectures.pdf",
                    "size": 696806
               }]
            
    *Fields*:
     * **BusRoute** : Bus Route
     * **BusStopName** : Bus Stop Name
     * **Direction** : Travel Direction