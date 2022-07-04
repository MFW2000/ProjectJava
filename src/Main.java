import model.SchoolAdministration;
import model.exception.AdministrationException;
import model.persons.Person;
import model.persons.Student;
import model.persons.Teacher;

import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SchoolAdministration administration = new SchoolAdministration();
        String input;

        // Sort teachers by last name and randomize all students
        Collections.sort(administration.getTeachers());
        Collections.shuffle(administration.getStudents());

        // Assign all students to mentors equally
        administration.assignStudentsToMentor(administration.getStudents());

        System.out.println("All persons in the system:");

        for (Person person : administration.getPersons()) {
            if (person instanceof Student) {
                System.out.println("Student: " + person);
            } else {
                System.out.println("Teacher: " + person);
            }
        }

        System.out.println();

        do {
            System.out.println("Teachers: ");

            for (Teacher teacher : administration.getTeachers()) {
                System.out.println(teacher + " has " + teacher.getNrOfMentorStudents() + " mentor students.");
            }

            System.out.println();
            System.out.print("Please enter a teacher code to view their students: ");
            input = readValidString();

            boolean found = false;

            for (Teacher teacher : administration.getTeachers()) {
                if (input.equalsIgnoreCase(teacher.getInitials())) {
                    try {
                        System.out.println(teacher.printMentorStudents());
                    } catch (AdministrationException e) {
                        System.err.println(e.getMessage());
                    }

                    found = true;
                    break;
                }
            }

            if (!found) {
                System.err.println("This teacher does not exist!");
            }

            System.out.println();
            System.out.print("To exit type 'exit' or press enter to continue: ");
            input = readString();

            System.out.println();
        } while (!input.equalsIgnoreCase("exit"));
    }

    /**
     * Reads user input as string.
     * @return the user input
     */
    public static String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Reads user input as string where extra whitespaces are not allowed.
     * @return the user input
     */
    public static String readValidString() {
        while (true) {
            String result = readString();

            if (isNullOrWhitespace(result)) {
                System.err.println("Invalid input, may not be empty.");
                System.out.print("Try again: ");
            } else {
                return result;
            }
        }
    }

    private static boolean isNullOrWhitespace(String string) {
        return string == null || string.length() == 0 || isWhiteSpace(string);
    }

    private static boolean isWhiteSpace(String string) {
        int length = string.length();

        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(string.charAt(i))) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
