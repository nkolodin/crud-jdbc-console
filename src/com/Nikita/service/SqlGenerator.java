package com.Nikita.service;

import com.Nikita.config.DBConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlGenerator {

    DBConfiguration dbConfiguration = new DBConfiguration();
    Connection connection = dbConfiguration.getConnection();

    public void addGroupsToDB(String group) {
        String groupsScript = "INSERT INTO university_groups(group_name) Values('" + group + "')";
        try {
            Statement statement = connection.createStatement();
            statement.execute(groupsScript);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCoursesToDB(String course) {
        String courseScript = "INSERT INTO courses(course_name) VALUES('" + course + "')";
        try {
            Statement statement = connection.createStatement();
            statement.execute(courseScript);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudentToDB(String firstName, String lastName) {
        String addStudentScript = "INSERT INTO students(first_name, last_name) VALUES(?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addStudentScript);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignGroupsToStudents(int groupId, int studentId) {
        String getGroups = "UPDATE students set groupd_id = ? where student_id = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getGroups);
            preparedStatement.setInt(1, groupId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignCoursesToStudents(int studentId, int courseId) {
        String coursesAndStudents = "insert into university.student_courses(student_id, course_id) values(?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(coursesAndStudents);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getGroupsLessStudents(int numberOfStudents) {
        String getGroupsLessThen = "select groupd_id from students group by groupd_id having count(groupd_id) > ?";
        List<Integer> idGroups = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getGroupsLessThen);
            preparedStatement.setInt(1, numberOfStudents);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idGroups.add(resultSet.getInt("groupd_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGroups;
    }

    public List<Integer> getGroupsEqualsStudents(int numberOfStudents) {
        String getGroupsEquals = "select groupd_id from students group by groupd_id having count(groupd_id) = ?";
        List<Integer> idGroups = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getGroupsEquals);
            preparedStatement.setInt(1, numberOfStudents);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idGroups.add(resultSet.getInt("groupd_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGroups;
    }

    public List<String> findStudentsRelatedToCourses(String course) {
        String query = "select first_name, last_name from university.students s " +
                "join university.student_courses uc on s.student_id = uc.student_id " +
                "join university.courses c on c.course_id = uc.course_id " +
                "where course_name = ? ";

        List<String> students = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, course);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void addNewStudent(String firstName, String lastName) {
        String addStudent = "insert into students(first_name, last_name) values (?,?) ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addStudent);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentById(int id) {
        String deleteStudent = "delete from students where student_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStudent);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> findAllCourses() {
        String coursesSql = "Select course_name from courses";
        List<String> coursesFromBase = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(coursesSql);
            while (resultSet.next()) {
                coursesFromBase.add(resultSet.getString("course_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesFromBase;
    }

    public void insertCourseForStudent(int studentId, String course) {
        String courseForStudent = "insert into student_courses(student_id, course_id) " +
                "values( ? ,(select course_id from courses where course_name = ?))";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(courseForStudent);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, course);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getStudentCourses(int studentId) {
        String studentCourses = "Select course_name from university.courses co " +
                "join university.student_courses sc on sc.course_id = co.course_id " +
                "join university.students s on s.student_id = sc.student_id where s.student_id = ?";
        List<String> courses = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(studentCourses);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courses.add(resultSet.getString("course_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public void removeStudentFromCourse(String course, int studentId) {
        String query = "delete from university.student_courses " +
                "where course_id = (Select course_id from university.courses where course_name = ? ) " +
                "and student_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, course);
            preparedStatement.setInt(2, studentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
