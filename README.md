# modern-backend-meetup

## What is this?
this is a repository for the modern backend meetup <br>
link to the meetup recording: https://youtu.be/0nc9PsfzrkA <br>
Created by [Niv Itzhaky] - Mentor & Backend expert <br>
linked in- https://www.linkedin.com/in/nivitzhaky/ <br>
email- niv.itzhaky@gmail.com <br>
phone: 0525236451<br>

## Follow the steps below to run the project

### GIT - YOU NEED TO FORK THE REPO AND CLONE IT TO YOUR LOCAL MACHINE

FORK AND THEN CLONE:
```
https://github.com/nivitzhaky/modern-backend-meetup.git
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
### CRUD
repo/StudentRepository.java
```java
public interface StudentRepository extends CrudRepository<Student,Long> {
}
```
service/StudentService.java
```java
@Service
public class StudentService {

    @Autowired
    StudentRepository repository;

    public Iterable<Student> all() {
        return repository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }


    public Student save(Student student) {
        return repository.save(student);
    }

    public void delete(Student student) {
        repository.delete(student);
    }

}
```
model/StudentIn.java
```java
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Data
public class StudentIn implements Serializable {

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


    public Student toStudent() {
        return Student.builder().createdAt(new Date()).fullname(fullname)
                .satScore(satScore).graduationScore(graduationScore)
                .phone(phone)
                .build();
    }

    public void updateStudent(Student student) {
        student.setFullname(fullname);
        student.setSatScore(satScore);
        student.setGraduationScore(graduationScore);
        student.setPhone(phone);
    }

}
```
controller/StudentsController.java
```java
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStudents()
    {
        return new ResponseEntity<>(studentService.all(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOneStudent(@PathVariable Long id)
    {
        return new ResponseEntity<>(studentService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> insertStudent(@RequestBody StudentIn studentIn)
    {
        Student student = studentIn.toStudent();
        student = studentService.save(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentIn student)
    {
        Optional<Student> dbStudent = studentService.findById(id);
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + id + " not found");
        student.updateStudent(dbStudent.get());
        Student updatedStudent = studentService.save(dbStudent.get());
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudent(@PathVariable Long id)
    {
        Optional<Student> dbStudent = studentService.findById(id);
        if (dbStudent.isEmpty()) throw new RuntimeException("Student with id: " + id + " not found");
        studentService.delete(dbStudent.get());
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }
```
commit - with students CRUD
####simple filter
StudentRepository.java
```java
    List<Student> findAllBySatScoreGreaterThan(Integer satScore);
```


StudentService.java
```java
    public List<Student> getStudentWithSatHigherThan(Integer sat) {
        return repository.findAllBySatScoreGreaterThan(sat);
    }
```


StudentController.java
```java
    @RequestMapping(value = "/highSat", method = RequestMethod.GET)
    public ResponseEntity<?> getHighSatStudents(@RequestParam Integer sat)
    {
        return new ResponseEntity<>(studentService.getStudentWithSatHigherThan(sat), HttpStatus.OK);
    }
```

####FPS
apply fps.patch
<br>
model/StudentOut:
```java
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import java.util.Date;

@Data
@Entity
@SqlResultSetMapping(name = "StudentOut")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentOut {
    @Id
    private Long id;
    private Date createdat;
    private String fullname;
    private Integer satscore;
    private Double graduationscore;
    private String phone;
    private String profilepicture;
}

```

model/StudentSortField.java
```java
`public enum StudentSortField {
    id("id") ,
    createdAt ("created_at"),
    fullName ("fullname"),
    birthDate ("birth_date"),
    satScore ("sat_score"),
    graduationScore ("graduation_score"),
    phone ("phone"),
    profilepicture ("profile_picture");

    public final String fieldName;
    private StudentSortField(String fieldName) {
        this.fieldName = fieldName;
    }
}
````
StudentsController.java
```java
    @Autowired
    EntityManager em;

    @Autowired
    ObjectMapper om;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PaginationAndList> search(@RequestParam(required = false) String fullName,
                                                    @RequestParam(required = false) Integer fromGraduationScore,
                                                    @RequestParam(required = false) Integer toGraduationScore,
                                                    @RequestParam(required = false) Integer fromSatScore,
                                                    @RequestParam(required = false) Integer toSatScore,
                                                    @RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "50") @Min(1) Integer count,
                                                    @RequestParam(defaultValue = "id") StudentSortField sort, @RequestParam(defaultValue = "asc") SortDirection sortDirection) throws JsonProcessingException {

        var res =aFPS().select(List.of(
                        aFPSField().field("id").alias("id").build(),
                        aFPSField().field("created_at").alias("createdat").build(),
                        aFPSField().field("fullname").alias("fullname").build(),
                        aFPSField().field("sat_score").alias("satscore").build(),
                        aFPSField().field("graduation_score").alias("graduationscore").build(),
                        aFPSField().field("phone").alias("phone").build(),
                        aFPSField().field("profile_picture").alias("profilepicture").build()
                ))
                .from(List.of(" student s"))
                .conditions(List.of(
                        aFPSCondition().condition("( lower(fullname) like :fullName )").parameterName("fullName").value(likeLowerOrNull(fullName)).build(),
                        aFPSCondition().condition("( graduation_score >= :fromGraduationScore )").parameterName("fromGraduationScore").value(fromGraduationScore).build(),
                        aFPSCondition().condition("( graduation_score <= :toGraduationScore )").parameterName("toGraduationScore").value(toGraduationScore).build(),
                        aFPSCondition().condition("( sat_score >= :fromSatScore )").parameterName("fromSatScore").value(fromSatScore).build(),
                        aFPSCondition().condition("( sat_score <= :toSatScore )").parameterName("toSatScore").value(toSatScore).build()
                )).sortField(sort.fieldName).sortDirection(sortDirection).page(page).count(count)
                .itemClass(StudentOut.class)
                .build().exec(em, om);
        return ResponseEntity.ok(res);
    }

```
commit - with FPS
### OneToMany grades
apply one_to_many_grades.patch
<br>
Student.java
```java
    @OneToMany(mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Collection<StudentGrade> studentGrades = new ArrayList<>();
```
StudentOut.java
```java
    private Double avgscore;
```
StudentsController.java
```java
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PaginationAndList> search(@RequestParam(required = false) String fullName,
                                                    @RequestParam(required = false) Integer fromGraduationScore,
                                                    @RequestParam(required = false) Integer toGraduationScore,
                                                    @RequestParam(required = false) Integer fromSatScore,
                                                    @RequestParam(required = false) Integer toSatScore,
                                                    @RequestParam(required = false) Integer fromAvgScore,
                                                    @RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "50") @Min(1) Integer count,
                                                    @RequestParam(defaultValue = "id") StudentSortField sort, @RequestParam(defaultValue = "asc") SortDirection sortDirection) throws JsonProcessingException {

        var res =aFPS().select(List.of(
                        aFPSField().field("s.id").alias("id").build(),
                        aFPSField().field("s.created_at").alias("createdat").build(),
                        aFPSField().field("s.fullname").alias("fullname").build(),
                        aFPSField().field("s.sat_score").alias("satscore").build(),
                        aFPSField().field("s.graduation_score").alias("graduationscore").build(),
                        aFPSField().field("s.phone").alias("phone").build(),
                        aFPSField().field("s.profile_picture").alias("profilepicture").build(),
                        aFPSField().field("(select avg(sg.course_score) from  student_grade sg where sg.student_id = s.id ) ").alias("avgscore").build()
                ))
                .from(List.of(" student s"))
                .conditions(List.of(
                        aFPSCondition().condition("( lower(fullname) like :fullName )").parameterName("fullName").value(likeLowerOrNull(fullName)).build(),
                        aFPSCondition().condition("( graduation_score >= :fromGraduationScore )").parameterName("fromGraduationScore").value(fromGraduationScore).build(),
                        aFPSCondition().condition("( graduation_score <= :toGraduationScore )").parameterName("toGraduationScore").value(toGraduationScore).build(),
                        aFPSCondition().condition("( sat_score >= :fromSatScore )").parameterName("fromSatScore").value(fromSatScore).build(),
                        aFPSCondition().condition("( sat_score <= :toSatScore )").parameterName("toSatScore").value(toSatScore).build(),
                        aFPSCondition().condition("( (select avg(sg.course_score) from  student_grade sg where sg.student_id = s.id ) >= :fromAvgScore )").parameterName("fromAvgScore").value(fromAvgScore).build()
                )).sortField(sort.fieldName).sortDirection(sortDirection).page(page).count(count)
                .itemClass(StudentOut.class)
                .build().exec(em, om);
        return ResponseEntity.ok(res);
    }

```
StudentSortField.java
```java
    id("s.id") ,
    createdAt ("s.created_at"),
    fullName ("s.fullname"),
    satScore ("s.at_score"),
    graduationScore ("s.graduation_score"),
    phone ("s.phone"),
    profilepicture ("s.profile_picture"),
    avgScore (" (select avg(sg.course_score) from  student_grade sg where sg.student_id = s.id ) ");
```
commit - with one to many
### Dockerize
apply dockerize.patch<br>
run maven package ->SKIP THE TESTS<br>
docker build . -t backend
<br>
in repository settings -> secrets, add <br>
DOCKERHUB_USERNAME = nivitzhaky<br>
DOCKERHUB_TOKEN = dckr_pat_wNsuA4lJiuBnc4iCsNCmxjCVjc4<br>
docker login
<br>
nivitzhaky
<br>
Jul201789#
<br>
docker tag backend  nivitzhaky/backend:002
<br>
docker-compose -f docker-compose-aws.yml up -d
### EC2
```
sudo yum update -y
sudo yum install -y docker
sudo service docker start
sudo curl -L https://github.com/docker/compose/releases/download/1.22.0/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```
docker-compose
```
echo "
version: \"3\"
services:
  appserver:
    container_name: server
    hostname: localhost
    image: nivitzhaky/backend:001
    ports:
      - "8080:8080"
  postgres:
    image: postgres
    environment:
      POSTGRES_PASSWORD: postgres
    ports:
    - 5432:5432
    volumes:
      - ./data:/var/lib/postgresql/data
    privileged: true
" >>  docker-compose-aws.yml
```
sudo /usr/local/bin/docker-compose -f docker-compose-aws.yml up -d <br>
if needed allow all traffic in security group <br>
### kubernetes
apply patch kubernetes.patch<br>
https://labs.play-with-k8s.com/ <br>
yum install git -y <br>
git clone https://github.com/nivitzhaky/modern-backend-meetup.git <br>
cd modern-backend-meetup/src/main/resources/k8s <br>
kubectl apply -f deployment-postgres.yml <br>
kubectl apply -f postgres-service.yml <br>
kubectl apply -f configmap.yml <br>       
kubectl apply -f deployment.yml <br>
kubectl apply -f service.yml  <br>


