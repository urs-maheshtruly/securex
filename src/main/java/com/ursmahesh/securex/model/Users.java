package com.ursmahesh.securex.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;



@Entity
@Getter
@Setter
@Table(name = "users")
public class Users  {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true , nullable = false)
    @Id
    private Integer user_id;

    @Column(unique = true ,nullable = false)
    private String username;

    @Column(unique = true , nullable = false)
    private String mobile_number;

    private String Pin;



}
