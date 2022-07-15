package com.mfw.projectjava.model.persons;

import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Person implements Comparable<Person> {
    private final String lastName;
    private final String firstName;
    private final int yearOfBirth;

    protected Person(String lastName, String firstName, int yearOfBirth) {
        // Check if name is valid and does not contain weird characters
        if (isInvalidName(lastName) || isInvalidName(firstName)) {
            throw new IllegalArgumentException("Invalid name!");
        }

        // Check if invalid birth year, age does not really matter except for impossible old age
        if (yearOfBirth < (Year.now().getValue() - 100) || yearOfBirth >= Year.now().getValue()) {
            throw new IllegalArgumentException("Invalid age!");
        }

        this.lastName = lastName;
        this.firstName = firstName;
        this.yearOfBirth = yearOfBirth;
    }

    // Helper method to check if the given name is valid or not by regex
    private static boolean isInvalidName(String name) {
        String regex = "[A-Z][a-z]*";
        Pattern pattern = Pattern.compile(regex);

        if (name == null) {
            return true;
        }

        Matcher matcher = pattern.matcher(name);

        return !matcher.matches();
    }

    @Override
    public int compareTo(Person comparePerson) {
        return lastName.compareTo(comparePerson.lastName);
    }

    @Override
    public String toString() {
        return (firstName + " " + lastName + " (Year of birth: " + yearOfBirth + ")");
    }
}
