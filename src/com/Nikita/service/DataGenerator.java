package com.Nikita.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    SqlGenerator sqlGenerator = new SqlGenerator();

    Random random = new Random();
    String alphabet = "qwertyuiopasdfghjklzxcvbnm";
    String[] courses = new String[]{"biology", "math", "pe", "algebra", "geometry", "history", "science", "english",
            "geography", "music"};
    String[] names = new String[]{"Jora", "Ivan", "Nikita", "Nastya", "Tolya", "Jeka", "Gerasim", "Tema",
            "Leha", "Maksim", "Danya", "Deian", "Rusik", "Gazik", "Basuh", "Alina", "Alevtina",
            "Kostya", "Dmitry", "Boroda"};
    String[] lastNames = new String[]{"Boban", "Kolodin", "Bryant", "Miller", "Allen", "Bell", "Cook",
            "Baker", "Jackson", "Perez", "Edwards", "Mitchell", "Washington", "Hernandez",
            "Stewart", "Rodriguez", "Henderson", "Bennett", "Adams", "Barnes"};

    char[] alphabetArray = alphabet.toCharArray();

    public void startScript() {
        generateGroups();
        generateCourses();
        generateStudent();
        setGroupToStudent();
        setCoursesToStudents();
    }

    public void generateGroups() {
        List<String> groups = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            groups.add(alphabetArray[random.nextInt(26)]
                    + Character.toString(alphabetArray[random.nextInt(26)])
                    + "-"
                    + Integer.toString(random.nextInt(9))
                    + random.nextInt(9));
            sqlGenerator.addGroupsToDB(groups.get(i));
        }
    }

    public void generateCourses() {
        for (int i = 0; i < 10; i++) {
            sqlGenerator.addCoursesToDB(courses[i]);
        }
    }

    public void generateStudent() {
        for (int i = 0; i < 200; i++) {
            sqlGenerator.addStudentToDB(names[random.nextInt(19)], lastNames[random.nextInt(19)]);
        }
    }

    public void setGroupToStudent() {
        int idOfStudent = 200;
        int firstBound = 10;
        int lastBound = 30;
        for (int i = 1; i < 21; i++) {
            if (idOfStudent == 0) {
                break;
            } else {
                for (int m = random.nextInt(lastBound - firstBound) + firstBound; m != 0; m--) {
                    sqlGenerator.assignGroupsToStudents(i, idOfStudent);
                    idOfStudent--;
                }
            }
        }
    }

    public void setCoursesToStudents() {
        for (int i = 0; i < 200; i++) {
            sqlGenerator.assignCoursesToStudents(i + 1, random.nextInt((10 - 1) + 1) + 1);
        }
    }
}
