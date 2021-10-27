package com.Nikita.service;

import java.util.List;
import java.util.Scanner;

public class Menu {

    SqlGenerator sqlGenerator = new SqlGenerator();
    SqlFileExecutor sqlFileExecutor = new SqlFileExecutor();
    DataGenerator dataGenerator = new DataGenerator();

    public void mainMenu() {
        System.out.println("1. Find all groups with less or equals student count");
        System.out.println("2. Write course to find student from it");
        System.out.println("3. Add new student");
        System.out.println("4. Delete student by id");
        System.out.println("5. Add a student to the course (from a list)");
        System.out.println("6 Remove the student from one of his or her courses");
        System.out.println("7 Exit");
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        sqlFileExecutor.sqlExecutor();
        dataGenerator.startScript();

        while (true) {
            mainMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("1. Select groups where less then");
                    System.out.println("2. Select groups where equals student:");
                    int numberOfAction = scanner.nextInt();
                    if (numberOfAction == 1) {
                        System.out.println("Write number of students: ");
                        int numberOfStudents = scanner.nextInt();
                        List<Integer> groupsId = sqlGenerator.getGroupsLessStudents(numberOfStudents);
                        groupsId.forEach(System.out::println);
                    } else {
                        System.out.println("Write number of students: ");
                        int numberOfStudents = scanner.nextInt();
                        List<Integer> groupsId = sqlGenerator.getGroupsEqualsStudents(numberOfStudents);
                        groupsId.forEach(System.out::println);
                    }
                    break;
                case "2":
                    System.out.println("Write course: ");
                    String course = scanner.nextLine();
                    List<String> students = sqlGenerator.findStudentsRelatedToCourses(course);
                    students.forEach(System.out::println);
                    break;
                case "3":
                    System.out.println("Write name");
                    String firstName = scanner.nextLine();
                    System.out.println("Write lastname");
                    String lastName = scanner.nextLine();
                    sqlGenerator.addNewStudent(firstName, lastName);
                    System.out.println("Студент добавлен");
                    break;
                case "4":
                    System.out.println("Write id of student: ");
                    int id = scanner.nextInt();
                    sqlGenerator.deleteStudentById(id);
                    System.out.println("Student was deleted");
                    break;
                case "5":
                    System.out.println("Write id of student");
                    int studentId = Integer.parseInt(scanner.nextLine());
                    List<String> courses = sqlGenerator.findAllCourses();
                    courses.forEach(System.out::println);
                    System.out.println("Select course");
                    String courseName = scanner.nextLine();
                    sqlGenerator.insertCourseForStudent(studentId, courseName);
                    System.out.println("Course was added");
                    break;
                case "6":
                    System.out.println("Select id of student");
                    int studentIdToRemoveCourse = Integer.parseInt(scanner.nextLine());
                    System.out.println("Courses of student");
                    List<String> listStudentCourses = sqlGenerator.getStudentCourses(studentIdToRemoveCourse);
                    listStudentCourses.forEach(System.out::println);
                    System.out.println("Select course to delete: ");
                    String studentCourses = scanner.nextLine();
                    sqlGenerator.removeStudentFromCourse(studentCourses, studentIdToRemoveCourse);
                    System.out.println("Course was removed");
                case "7":
                    System.exit(0);
            }
        }
    }
}
