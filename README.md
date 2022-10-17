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
check: http://localhost:8080/actuator/health

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
check: http://localhost:8080/swagger-ui.html#
### DOCKER

