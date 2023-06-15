package com.digitaldesign.murashkina.repositories.dao;



import java.util.UUID;


public class SearchEmployeeFilter {
    private String lastName;
    private String firstName;
    private String middleName;
    private String account;
    private String email;
    private UUID member;
    private String role;
    private UUID project;

    public SearchEmployeeFilter(String lastName, String firstName, String middleName, String account, String email, UUID member, String role, UUID project) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.account = account;
        this.email = email;
        this.member = member;
        this.role = role;
        this.project = project;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getMember() {
        return member;
    }

    public void setMember(UUID member) {
        this.member = member;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UUID getProject() {
        return project;
    }

    public void setProject(UUID project) {
        this.project = project;
    }
}
