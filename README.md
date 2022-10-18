# modern-backend-meetup

## What is this?
this is a repository for the modern backend meetup <br>
link to the meetup: https://www.meetup.com/dev-online-helpers-israel/events/288802121/ <br>
Created by [Niv Itzhaky] - Mentor & Backend expert <br>
linked in- https://www.linkedin.com/in/nivitzhaky/ <br>
email- niv.itzhaky@gmail.com <br>
phone: 0525236451<br>

## Follow the steps below to run the project

### GIT

clone me:
```
git clone git@github.com:nivitzhaky/modern-backend-meetup.git
```
### START a new project from intellij:
use: start.spring.io <br>
use: spring boot latest version <br>
use: java 11 <br>
use group: com.handson <br>
use artifact: backend <br>
use: maven <br>
use: spring web <br>
use: lombok <br>
use: spring actuator <br>
after project downloaded open it in intellij and copy src and pom.xml to the project you cloned from git <br>
change spring version in pom.xml to 2.5.2 <br>
run the project <br>
check: http://localhost:8080/actuator/health <br>
commit - initial version
### hello world
controller/StudentsController.java
```java
@RestController
@RequestMapping("/api/students")
public class StudentsController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> hello()
    {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

}
```

pom.xml
```
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.6.1</version>
		</dependency><!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.6.1</version>
		</dependency>
```
config/SwaggerConfig.java
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
```
check: http://localhost:8080/swagger-ui.html#<br>
commit - hello world
### DOCKER
```
docker run -d -p 5432:5432 -v postgresdata:/var/lib/postgresql/data -e POSTGRES_PASSWORD=postgres postgres
docker ps
docker logs [containerid]
```
docker-compose.yml
```
version: "3"
services:
  db:
    image: postgres
    environment:
      POSTGRES_PASSWORD: postgres
    ports:
    - 5432:5432
    volumes:
      - ./data:/var/lib/postgresql/data
    privileged: true
```
docker-compose up -d
<br>
connect with tableplus - hostname postgres
<br>
cat /etc/hosts
<br>
commit - with docker compose
### SPRING DATA
#### Spring DATA
pom.xml
```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-validator</artifactId>
			<version>6.1.5.Final</version>
		</dependency>

```
application.properties
```
spring.datasource.url=jdbc:postgresql://postgres:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
model/Student.java
```java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="student")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @NotEmpty
    @Length(max = 60)
    private String fullname;


    @Min(100)
    @Max(800)
    private Integer satScore;

    @Min(30)
    @Max(110)
    private Double graduationScore;

    @Length(max = 20)
    private String phone;

    @Length(max = 500)
    private String profilePicture;
}

```
commit - with spring data
