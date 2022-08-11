package com.example.manageemployee.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user", catalog = "db_security")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullname;
    private String dateofbirth;
    private String address;
    private String username;
    @NotEmpty(message = "Thiếu password")
    private String password;
    @Email(message = "Email khong hop le!")
    private String email;
    private int codecheckin;
    private Date datecreated;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name="role_id")
    private Role role;
    @ManyToMany
    @JoinTable(name = "projectmember", joinColumns = @JoinColumn(name="member_id"),inverseJoinColumns = @JoinColumn(name="project_id"))
    @JsonIgnore
    private Set<Project> projects;
    public User(String fullname){
        this.fullname = fullname;
    }

}
