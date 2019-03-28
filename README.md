# Scheduling supporter 


Make sure you have Java 1.8 installed and Gradle added to path.


### REST
### Test endpoints
```sh
127.0.0.1:8080 
127.0.0.1:8080/bagieta
127.0.0.1:8080/bagieta/{id}
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