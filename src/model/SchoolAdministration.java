package model;

import model.exception.AdministrationException;
import model.persons.Person;
import model.persons.Student;
import model.persons.Teacher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SchoolAdministration {
    private final ArrayList<Teacher> teachers = new ArrayList<>();
    private final ArrayList<Student> students;
    private final ArrayList<Person> persons;

    public SchoolAdministration() {
        students = studentCSVReader();
        persons = createListOfAllPersons(students);
    }

    // Getters
    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }
    public ArrayList<Student> getStudents() {
        return students;
    }
    public ArrayList<Person> getPersons() {
        return persons;
    }

    /**
     * Add teacher to list
     * @param lastName last name of the teacher
     * @param firstName first name of the teacher
     */
    public void addTeacher(String lastName, String firstName) {
        // Teacher age is not important so it is generated
        teachers.add(new Teacher(lastName, firstName, generateRandomNumber(1950, 1990)));
    }

    /**
     * Creates a list of all persons in the system, including teachers and students.
     * @param students list of students to add
     * @return list of all persons
     */
    public ArrayList<Person> createListOfAllPersons(ArrayList<Student> students) {
        ArrayList<Person> result = new ArrayList<>();

        result.addAll(students);
        result.addAll(teachers);

        return result;
    }

    /**
     * Assign students equally to a mentor.
     * @param students list of students to assign
     * @throws IllegalArgumentException if the student cannot be assigned
     */
    public void assignStudentsToMentor(ArrayList<Student> students) {
        int dividedStudents = students.size() / teachers.size();

        try {
            // Assign all eually assignable students
            for (Teacher teacher : teachers) {
                for (int i = 0; i < dividedStudents; i++) {
                    teacher.addMentorStudent(students.get(0));
                    students.remove(0);
                }
            }

            // Assign leftover students one by one to one teacher until none are left
            for (Teacher teacher : teachers) {
                if (students.size() != 0) {
                    teacher.addMentorStudent(students.get(0));
                    students.remove(0);
                } else {
                    break;
                }
            }
        } catch (AdministrationException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Add students to an ArrayList using a CSV file.
     * @return list of students
     */
    public static ArrayList<Student> studentCSVReader() {
        ArrayList<Student> students = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("resources/data.csv"));
            scanner.nextLine(); // Skip first row

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] splits = line.split(";");

                String lastName = splits[0];
                String firstName = splits[1];

                // Student age is not important so it is generated
                Student student = new Student(lastName, firstName, generateRandomNumber(1990, 2005));
                students.add(student);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find CSV file: " + e);
        }

        return students;
    }

    private static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(min, max);
    }
}
