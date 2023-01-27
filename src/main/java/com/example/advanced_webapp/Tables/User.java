package com.example.advanced_webapp.Tables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36)")
    private String userId;
    @NotBlank
    private String firstName;
    private String middleName;
    @NotBlank
    private String lastName;
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;
    @NotBlank
    private String password;
    private boolean verified;

    public User() {
    }
    @OneToMany(mappedBy = "user")
    private java.util.List<Tag> tagList;

    @OneToMany(mappedBy = "user")
    private java.util.List<List> listList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public java.util.List<Tag> getTagsList() {
        return tagList;
    }

    public void setTagsList(java.util.List<Tag> tagList) {
        this.tagList = tagList;
    }

    public java.util.List<List> getListsList() {
        return listList;
    }

    public void setListsList(java.util.List<List> listList) {
        this.listList = listList;
    }
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public java.util.List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(java.util.List<Tag> tagList) {
        this.tagList = tagList;
    }

    public java.util.List<List> getListList() {
        return listList;
    }

    public void setListList(java.util.List<List> listList) {
        this.listList = listList;
    }
}
