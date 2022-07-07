package model.persons;

import model.exception.AdministrationException;

import java.util.ArrayList;

public class Teacher extends Person {
    private final ArrayList<Student> mentorStudents = new ArrayList<>();
    private final String initials;

    public Teacher(String lastName, String firstName, int yearOfBirth) {
        super(lastName, firstName, yearOfBirth);
        initials = generateInitials(lastName, firstName);
    }

    /**
     * Get teacher initials.
     * @return the initials of the teacher
     */
    public String getInitials() {
        return initials;
    }

    /**
     * Get the number of students assigned to this teacher/mentor.
     * @return the number of students
     */
    public int getNrOfMentorStudents() {
        return mentorStudents.size();
    }

    /**
     * Add the given student to this teacher.
     * @param student the student to add
     */
    public void addMentorStudent(Student student) throws AdministrationException {
        if (mentorStudents.contains(student)) {
            throw new AdministrationException("This student is already assigned to this teacher!");
        }

        mentorStudents.add(student);
    }

    /**
     * Print all students assigned to this teacher/mentor.
     * @return String with all students
     */
    public String printMentorStudents() throws AdministrationException {
        if (mentorStudents.isEmpty()) {
            throw new AdministrationException("List of assigned students is empty");
        }

        StringBuilder builder = new StringBuilder();

        for (Student student : mentorStudents) {
            builder.append(student).append("\n");
        }

        builder.deleteCharAt(builder.length() - 1); // Remove last \n

        return builder.toString();
    }

    // Helper method to generate the initials automatically
    private String generateInitials(String lastName, String firstName) {
        String initialFirst = String.valueOf(firstName.charAt(0));
        String initialLast = String.valueOf(lastName.charAt(0)) + lastName.charAt(1);
        String result = initialFirst + initialLast;

        return result.toUpperCase();
    }

    @Override
    public String toString() {
        return (super.toString() + " (Initials: " + initials + ")");
    }
}
