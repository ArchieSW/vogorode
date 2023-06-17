package dev.archie.landscapeservice.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.archie.landscapeservice.account.skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "handyman_user")
public class HandymanUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "photo")
    private byte[] photo;

    @JsonIgnoreProperties("handymanUser")
    @OneToMany(mappedBy = "handymanUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Account> accounts;

    @JsonIgnoreProperties("handymanUser")
    @OneToMany(mappedBy = "handymanUser", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Skill> skills;

}