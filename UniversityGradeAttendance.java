import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Student {

    String name;
    int rollNo;
    String department;
    int semester;

    String[] subjects = {
        "Java",
        "Digital Logic",
        "Mathematics",
        "English",
        "Computer Architecture"
    };

    int[] marks = new int[5];
    int[] totalClasses = new int[5];
    int[] attendedClasses = new int[5];

    void displayStudent() {
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollNo);
        System.out.println("Department: " + department);
        System.out.println("Semester: " + semester);
    }
}

class UniversityStudent extends Student {

    String getGrade(int mark) {

        if (mark >= 90) {
            return "A+";
        } else if (mark >= 80) {
            return "A";
        } else if (mark >= 70) {
            return "B";
        } else if (mark >= 60) {
            return "C";
        } else if (mark >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    double getAttendance(int total, int attended) {
        return ((double) attended / total) * 100;
    }

    void displayReport() {

        System.out.println("\n========== UNIVERSITY REPORT ==========");

        displayStudent();

        System.out.println("\n--------------- SUBJECTS ---------------");

        for (int i = 0; i < subjects.length; i++) {

            double attendance =
                    getAttendance(totalClasses[i], attendedClasses[i]);

            System.out.println("\nSubject: " + subjects[i]);
            System.out.println("Marks: " + marks[i]);
            System.out.println("Grade: " + getGrade(marks[i]));
            System.out.printf("Attendance: %.2f%%%n", attendance);

            if (attendance >= 75) {
                System.out.println("Attendance Status: Eligible");
            } else {
                System.out.println("Attendance Status: Not Eligible");
            }
        }

        System.out.println("\n========================================");
    }

    void saveReport() {

        try (FileWriter file = new FileWriter("student_report.txt")) {

            file.write("========== UNIVERSITY REPORT ==========\n");
            file.write("Name: " + name + "\n");
            file.write("Roll No: " + rollNo + "\n");
            file.write("Department: " + department + "\n");
            file.write("Semester: " + semester + "\n\n");

            for (int i = 0; i < subjects.length; i++) {

                double attendance =
                        getAttendance(totalClasses[i], attendedClasses[i]);

                file.write("Subject: " + subjects[i] + "\n");
                file.write("Marks: " + marks[i] + "\n");
                file.write("Grade: " + getGrade(marks[i]) + "\n");
                file.write(String.format(
                        "Attendance: %.2f%%%n", attendance
                ));

                if (attendance >= 75) {
                    file.write("Status: Eligible\n\n");
                } else {
                    file.write("Status: Not Eligible\n\n");
                }
            }

            file.write("========================================\n");

            System.out.println("\nReport generated successfully.");
            System.out.println("Saved as: student_report.txt");

        } catch (IOException e) {
            System.out.println("Unable to create report.");
        }
    }
}

public class UniversityGradeAttendance {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UniversityStudent s = new UniversityStudent();

        try {

            System.out.println(
                "===== UNIVERSITY GRADE & ATTENDANCE TRACKER ====="
            );

            System.out.print("Enter student name: ");
            s.name = sc.nextLine().trim();

            if (s.name.isEmpty()) {
                throw new Exception("Student name cannot be empty.");
            }

            System.out.print("Enter roll number: ");
            s.rollNo = Integer.parseInt(sc.nextLine());

            if (s.rollNo <= 0) {
                throw new Exception("Roll number must be positive.");
            }

            System.out.print("Enter department: ");
            s.department = sc.nextLine().trim();

            if (s.department.isEmpty()) {
                throw new Exception("Department cannot be empty.");
            }

            System.out.print("Enter semester: ");
            s.semester = Integer.parseInt(sc.nextLine());

            if (s.semester <= 0) {
                throw new Exception("Semester must be positive.");
            }

            for (int i = 0; i < s.subjects.length; i++) {

                System.out.println("\n--- " + s.subjects[i] + " ---");

                System.out.print("Enter marks: ");
                s.marks[i] = Integer.parseInt(sc.nextLine());

                if (s.marks[i] < 0 || s.marks[i] > 100) {
                    throw new Exception(
                        "Marks must be between 0 and 100."
                    );
                }

                System.out.print("Enter total classes: ");
                s.totalClasses[i] = Integer.parseInt(sc.nextLine());

                System.out.print("Enter attended classes: ");
                s.attendedClasses[i] =
                        Integer.parseInt(sc.nextLine());

                if (s.totalClasses[i] <= 0) {
                    throw new Exception(
                        "Total classes must be greater than 0."
                    );
                }

                if (s.attendedClasses[i] < 0 ||
                    s.attendedClasses[i] > s.totalClasses[i]) {

                    throw new Exception(
                        "Attended classes cannot be negative or greater than total classes."
                    );
                }
            }

            s.displayReport();
            s.saveReport();

        } catch (NumberFormatException e) {

            System.out.println(
                "Please enter valid numeric values."
            );

        } catch (Exception e) {

            System.out.println(
                "Error: " + e.getMessage()
            );

        } finally {
            sc.close();
        }
    }
}