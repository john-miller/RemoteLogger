
## VLogger
>*VLogger is a RESTful logging service that can be used for various applications that require centralized database logging.*

### Adding a log entry
User account privileges: 'EDITOR' OR 'ADMIN'
Request Type: POST
http://[IPADDRESS]:[PORT]/v1/log

### Reading a log entry
User account privileges: 'READER' OR 'ADMIN'
Request Type: GET
http://[IPADDRESS]:[PORT]/v1/log/[logId]

### Deleting a log entry
User account privileges: 'ADMIN'
Request Type: DELETE
http://[IPADDRESS]:[PORT]/v1/log/[logId]

### Adding a logging user
Logging users can only be added by an API call coming from the host (localhost or 127.0.0.1)
http://localhost:[PORT]/v1/users

### VLogger Event Queue
When a log event is submitted, it is submitted to an internal queue that is repeatedly checked.  
This is done to speed up response time for the clients so that logging can appear more passive. 

### Event Queue Timing Algorithm
As the size of the internal log event queue increases, so will the frequency of which they are entered into the database.
This is to keep memory usage at a threshold limit *(CEIL)* when little to no logging is being done.
*CEIL = Time in milliseconds until the next queue check even.*
*QSIZE = Number of log events in the queue* 

> Time in milliseconds till next queue cycle = (CEIL/QSIZE) OR (1/QSIZE)*(CEIL)
> Time in milliseconds till next queue cycle (QSIZE == 0) = CEIL

### cUrl REST Consumption Sample
```
curl -H "Content-Type: application/json" -d '{"databaseId":"database001","tableName":"table001","values":{ "Col1":"Val1", "Col2":"Val2" }}' http://localhost:8080/v1/log
```

### Java REST Consumption Sample
```Java
public static void logEvent(String databaseName, String tableName, Map<String, ?> columnValueMapping)
{

}
```


