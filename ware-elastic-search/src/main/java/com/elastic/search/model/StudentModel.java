package com.elastic.search.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "student",type = "_doc")
public class StudentModel {
    @Id
    private String id;
    private String name;
    private int age;
    private String birthday;
    private String interest;
    private String address;
    private String school;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StudentModel() {
    }

    public StudentModel(String address, String school, String content) {
        this.address = address;
        this.school = school;
        this.content = content;
    }

    public StudentModel(String id, String name, int age, String birthday, String interest, String address, String school, String content) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.interest = interest;
        this.address = address;
        this.school = school;
        this.content = content;
    }
}
