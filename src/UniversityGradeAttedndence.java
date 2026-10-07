import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Student {

    String name;
    int rollNo;
    String department;
    int semester;

    final String[] subjects = {
        "Java",
        "Digital Logic",
        "Mathematics",
        "English",
        "Computer Architecture"
    };

    int[] marks = new int[subjects.length];
    int[] totalClasses = new int[subjects.length];
    int[] attendedClasses = new int[subjects.length];

    String studentDetails() {
        return String.format(
            "Name: %s%nRoll No: %d%nDepartment: %s%nSemester: %d%n",
            name, rollNo, department, semester
        );
    }
}

class UniversityStudent extends Student {

    static final double MIN_ATTENDANCE = 75.0;

    String getGrade(int mark) {
        if (mark >= 90) return "A+";
        if (mark >= 80) return "A";
        if (mark >= 70) return "B";
        if (mark >= 60) return "C";
        if (mark >= 50) return "D";
        return "F";
    }

    double getAttendance(int total, int attended) {
        if (total <= 0) {
            return 0.0;
        }
        return ((double) attended / total) * 100;
    }

    double getAverageMarks() {
        int sum = 0;
        for (int m : marks) {
            sum += m;
        }
        return (double) sum / marks.length;
    }

    // Single source of truth for the report (used for console AND file)
    String buildReport() {

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("========== UNIVERSITY REPORT ==========%n"));
        sb.append(studentDetails());
        sb.append(String.format("%n--------------- SUBJECTS ---------------%n"));

        for (int i = 0; i < subjects.length; i++) {

            double attendance =
                getAttendance(totalClasses[i], attendedClasses[i]);

            sb.append(String.format("%nSubject: %s%n", subjects[i]));
            sb.append(String.format("Marks: %d%n", marks[i]));
            sb.append(String.format("Grade: %s%n", getGrade(marks[i])));
            sb.append(String.format("Attendance: %.2f%%%n", attendance));
            sb.append(String.format(
                "Attendance Status: %s%n",
                attendance >= MIN_ATTENDANCE ? "Eligible" : "Not Eligible"
            ));
        }

        sb.append(String.format("%n--------------- SUMMARY ----------------%n"));
        sb.append(String.format("Average Marks: %.2f%n", getAverageMarks()));
        sb.append(String.format("========================================%n"));

        return sb.toString();
    }

    void displayReport() {
        System.out.println();
        System.out.print(buildReport());
    }

    void saveReport(String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(buildReport());
        } catch (IOException e) {
            System.out.println("Unable to create report: " + e.getMessage());
            return;
        }
        System.out.println("\nReport generated successfully.");
        System.out.println("Saved as: " + fileName);
    }
}

class UniversityGradeAttendance {

    // Keeps asking until a non-empty string is entered
    static String readText(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    // Keeps asking until an integer within [min, max] is entered
    static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(
                    "Value must be between " + min + " and " + max + "."
                );
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {

            UniversityStudent s = new UniversityStudent();

            System.out.println(
                "===== UNIVERSITY GRADE & ATTENDANCE TRACKER ====="
            );

            s.name = readText(sc, "Enter student name: ");
            s.rollNo = readInt(sc, "Enter roll number: ", 1, Integer.MAX_VALUE);
            s.department = readText(sc, "Enter department: ");
            s.semester = readInt(sc, "Enter semester: ", 1, 12);

            for (int i = 0; i < s.subjects.length; i++) {

                System.out.println("\n--- " + s.subjects[i] + " ---");

                s.marks[i] = readInt(sc, "Enter marks (0-100): ", 0, 100);

                s.totalClasses[i] =
                    readInt(sc, "Enter total classes: ", 1, Integer.MAX_VALUE);

                s.attendedClasses[i] =
                    readInt(sc, "Enter attended classes: ",
                            0, s.totalClasses[i]);
            }

            s.displayReport();
            s.saveReport("student_report.txt");
        }
    }
}
