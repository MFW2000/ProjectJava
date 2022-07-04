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
    private final ArrayList<Student> students = studentCSVReader();
    private final ArrayList<Person> persons = createListOfAllPersons(students);
    private static final ArrayList<Teacher> teachers = new ArrayList<>();

    public SchoolAdministration() {
        // Because teachers are regular employees they will remain unlike students
        teachers.add(new Teacher("Jackson", "Dannielle", generateRandomNumber(1950, 1990)));
        teachers.add(new Teacher("Mcdaniel", "Donald", generateRandomNumber(1950, 1990)));
        teachers.add(new Teacher("Perez", "Jad", generateRandomNumber(1950, 1990)));
        teachers.add(new Teacher("Ford", "Ada", generateRandomNumber(1950, 1990)));
        teachers.add(new Teacher("Hoover", "Nile", generateRandomNumber(1950, 1990)));
    }

    // Getters
    public ArrayList<Student> getStudents() {
        return students;
    }
    public ArrayList<Person> getPersons() {
        return persons;
    }
    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    /**
     * Creates a list of all persons in the system, including teachers and students.
     * @param students list of students to add
     * @return list of all persons
     */
    public static ArrayList<Person> createListOfAllPersons(ArrayList<Student> students) {
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
            for (Teacher teacher : teachers) {
                for (int i = 0; i < dividedStudents; i++) {
                    teacher.addMentorStudent(students.get(0));
                    students.remove(0);
                }
            }

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
