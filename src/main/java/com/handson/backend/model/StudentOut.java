package com.handson.backend.model;

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
