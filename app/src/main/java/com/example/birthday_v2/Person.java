package com.example.birthday_v2;

import java.util.ArrayList;

public class Person {
    String surname;
    String name;
    String patronymic;
    int idPerson;
    String telephone;
    static ArrayList<Person> arrayListPerson = new ArrayList<Person>();


    public Person() {
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
//        return "Person{" +
//                "surname='" + surname + '\'' +
//                ", name='" + name + '\'' +
//                ", patronymic='" + patronymic + '\'' +
//                ", idPerson=" + idPerson +
//                ", telephone=" + telephone +
//                '}';
        return idPerson + " " + surname + " " + name + " " + patronymic + " " + telephone;
    }
}

