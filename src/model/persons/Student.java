package model.persons;

public class Student extends Person {
    private static int NEXT_ID = 100000;
    private final int studentNumber;

    public Student(String lastName, String firstName, int yearOfBirth) {
        super(lastName, firstName, yearOfBirth);
        studentNumber = NEXT_ID++;
    }

    @Override
    public String toString() {
        return (super.toString() + " (Student number: " + studentNumber + ")");
    }
}
