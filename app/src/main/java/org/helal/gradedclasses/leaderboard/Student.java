package org.helal.gradedclasses.leaderboard;

import java.util.Objects;

public final class Student {
    private final String id;
    private final String name;
    private final String grade;
    private final double points;

    public Student(String id, String name, String grade, double points) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.points = points;
    }


    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String grade() {
        return grade;
    }

    public double points() {
        return points;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Student that = (Student) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.grade, that.grade) &&
                Double.doubleToLongBits(this.points) == Double.doubleToLongBits(that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade, points);
    }

    @Override
    public String toString() {
        return "Student[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "grade=" + grade + ", " +
                "points=" + points + ']';
    }

}
