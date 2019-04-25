# Scheduling supporter 


Make sure you have Java 1.8 installed and Gradle added to path.

### Run
```sh
gradle build
gradle run
```

### REST
### Test endpoints
```sh
127.0.0.1:8080 
127.0.0.1:8080/bagieta
127.0.0.1:8080/bagieta/{id}
```
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
localhost:8080/schedule                 -- all lessons
localhost:8080/schedule/{id}            -- specific lesson
localhost:8080/schedule/name/{name}     -- specific schedule
```

Endpoints returning schedule in XLS file:
```sh
localhost:8080/schedule/file            -- all lessons
localhost:8080/schedule/file/{name}     -- specific schedule
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
