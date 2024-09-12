**pressArticle-task**

pressArticle task is a simple article storage with options to load all article, create/update a article or delete a article. 
You can use REST to communicate with application.

**Tech/frameworks used**

- Spring;
- Hibernate;
- H2 Database;
- JUnit 5;
- mockito;
- maven.

**Installation**

- JDK 11;
- Apache Maven 3.x.

**Build and Run**

- mvn clean package
- mvn exec:java

**Database setup**

Persistence layer of the application is H2 database.

    spring.h2.console.enabled=true
    spring.datasource.username=root
    spring.datasource.password=root
    spring.jpa.defer-datasource-initialization=true

**API**

Application is available on localhost:8080. You can use the api with POSTMAN or another http client. 

1. Find all sort articles - returns paginated sort articles.
   
    Endpoint: GET http://localhost:8080/articles/
    Produces: application/json
    Params:
    - page - number of result's page which you want to retrieve. Default value is 0;
    - size - number of results you want to retrieve per a single page. Default value is 25;
    - sortBy - it's sort by dataPublication.
      
2. Save article - saves new article to a database and returns the newly saved article.

    POST http://localhost:8080/articles
    Content-Type: application/json
    Accept: application/json

3. Update Article

   PUT http://localhost:8080/articles/1
   Content-Type: application/json
   Accept: application/json

4. Delete article

   Endpoint: DELETE http://localhost:8080/articles/{id}
   Produces: application/json