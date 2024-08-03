package com.example.demo2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student implements Serializable {
    @Id
    @NotBlank(message = "Khong duoc de trong name")
    private String name;
    @NotBlank(message = "Khong duoc de trong email")
    @Email(message = "Phai dung dinh dang email")
    private String email;
    @NotNull(message = "Khong duoc de trong mark")
    @PositiveOrZero(message = "Khong duoc nho hon 0")
    @Max(value = 10, message = "Khong duoc lon hon 10")
    private double marks;
    @NotNull(message = "Khong duoc de trong country")
    private String country;
    @NotNull(message = "Khong duoc de trong gender")
    private boolean gender;

    @Temporal(TemporalType.DATE)
    private Date date;

    private boolean active;
}
