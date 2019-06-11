# Scheduling supporter 


Make sure you have Java 1.8 installed and Gradle added to path.

### Run
```sh
gradle build
gradle run
```

### REST

### Import endpoints
```sh
localhost:8080/schedule/file
localhost:8080/schedule/files
```
Required keys for:  
 ```file``` -> ```file```  
 ```files``` -> ```files```
#####Response message
```json
{
    "fileName": "file.jpg",
    "fileDownloadUri": "http://127.0.0.1:8080/downloadFile/file.jpg",
    "fileType": "filetype",
    "size": 123
}
```
### Export endpoints
Endpoints returning schedule in JSON:
```sh
localhost:8080/schedule                             -- all lessons
localhost:8080/schedule/{id}                        -- specific lesson
localhost:8080/schedule/name/{name}                 -- specific schedule
localhost:8080/schedule/lecturer/{surname}          -- lessons for lecturer surname
localhost:8080/schedule/lecturer/{name}/{surname}   -- lessons for lecturer name and surname
```

Endpoints returning schedule in XLS file:
```sh
localhost:8080/schedule/xls                 -- all lessons
localhost:8080/schedule/xls/{name}          -- schedule for given schedule name
localhost:8080/schedule/xls/group/{name}    -- schedule for given group name
```
### Test in-memory db
H2 & Hibernate
Auto executing ```/resources/init.sql``` to populate db

### Test database
db console
``` http://localhost:8080/h2-console/``` 
##### JDBC URL
``` jdbc:h2:mem:iobagiety``` 
##### User name
``` admin``` 
