package dev.archie.stress.tester.entities;

import lombok.Data;

import java.util.List;


@Data
public class HandymanUser {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private byte[] photo;

    private List<Account> accounts;

    private List<Skill> skills;

}