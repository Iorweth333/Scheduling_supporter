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
localhost:8080/files
localhost:8080/filse/multiple
localhost:8080/files/{filename}
```
While using ```uploadFile``` i ```uploadMultipleFiles``` you should add file(s) in body.
#####Response message
```json
{
    "fileName": "file.jpg",
    "fileDownloadUri": "http://127.0.0.1:8080/downloadFile/file.jpg",
    "fileType": "filetype",
    "size": 123
}
```
###Conflicts endpoint
localhost:8080/conflicts

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
