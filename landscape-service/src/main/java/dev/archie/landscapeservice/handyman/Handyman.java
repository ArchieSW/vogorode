package dev.archie.landscapeservice.handyman;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.archie.landscapeservice.account.Account;
import dev.archie.landscapeservice.account.skill.Skill;
import dev.archie.landscapeservice.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "handyman_user")
public class Handyman extends User {

    @Type(type="org.hibernate.type.BinaryType")
    private byte[] photo;

    @OneToMany(mappedBy = "handyman", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnoreProperties("handyman")
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "handyman", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnoreProperties("handyman")
    private List<Skill> skills = new ArrayList<>();

}
