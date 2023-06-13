package org.motorph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class EmployeeApp {
    public static void main(String[] args) {
        /**
         * For Windows users, it might look like this one:
         * String employeeDetailsPath = "C:\Users\\username\Downloads\whatever-the-file-name-is.csv";
         */
        String employeeDetailsPath = "/Users/micahbule/Downloads/MotorPH Employee Data - Employee Details.csv";
        String employeeAttendancePath = "/Users/micahbule/Downloads/MotorPH Employee Data - Attendance Record.csv";

        try {
            /**
             * Open CSV File
             */
            BufferedReader br = new BufferedReader(new FileReader(employeeDetailsPath));

            /**
             * Read next line
             */
            String row = br.readLine();
            ArrayList<Employee> employees = new ArrayList<Employee>();
            Boolean pastFirstLine = false;

            /**
             * Is it the last line?
             * If no, do the following:
             */
            while (row != null) {
                /**
                 * Is it the first line?
                 * If yes, do the following:
                 */
                if (!pastFirstLine) {
                    pastFirstLine = true;
                    /** Read the next line */
                    row = br.readLine();
                    /** Skip the rest of the code and go back to the top of the loop */
                    continue;
                }

                /**
                 * Split row data by comma delimiter
                 */
                String[] rowData = row.split(",");
                /**
                 * Declare array list of strings for processed rows.
                 *
                 * This is to cater data with commas such as addresses and amounts.
                 */
                ArrayList<String> processedRow = new ArrayList<String>();

                /**
                 * Declare a string to collate data with commas
                 */
                String concatenator = "";
                /**
                 * Declare a boolean as flag that our concatenator is open
                 */
                Boolean concatOpen = false;

                /**
                 * Go through
                 */
                for (int index = 0; index < rowData.length; index++) {
                    if (!rowData[index].contains("\"") && !concatOpen) {
                        processedRow.add(rowData[index]);
                    } else {
                        if (rowData[index].charAt(0) == '"' && !concatOpen) {
                            concatOpen = true;
                        } else if (rowData[index].charAt(rowData[index].length() - 1) == '"' && concatOpen) {
                            concatOpen = false;
                        }

                        concatenator += rowData[index].replace("\"", "");

                        if (!concatOpen) {
                            processedRow.add(concatenator);
                            concatenator = "";
                        }
                    }
                }

                Employee newEmployee = new Employee(processedRow.get(0), processedRow.get(2), processedRow.get(1), Float.parseFloat(processedRow.get(18)));
                employees.add(newEmployee);

                row = br.readLine();
            }

            /**
             * After last line, do the following:
             */
            System.out.print("Enter Employee Number: ");
            Scanner scanner = new Scanner(System.in);
            String empNumberInput = scanner.nextLine();

            System.out.print("Enter Month Number (ex. 1 = January): ");
            String monthNumber = scanner.nextLine();

            Employee selectedEmployee = new Employee("", "", "", 0);

            for (int index = 0; index < employees.size(); index++) {
                Employee currentEmployee = employees.get(index);
                if (currentEmployee.getId().equals(empNumberInput)) {
                    selectedEmployee = currentEmployee;
                    break;
                }
            }

            System.out.println(selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName() + " with hourly rate of " + selectedEmployee.getHourlyRate());

            /**
             * Open CSV File
             */
            BufferedReader br2 = new BufferedReader(new FileReader(employeeAttendancePath));

            /**
             * Read next line
             */
            String row2 = br2.readLine();
            Boolean pastFirstLine2 = false;

            /**
             * Is it the last line?
             * If no, do the following:
             */
            while (row2 != null) {
                /**
                 * Is it the first line?
                 * If yes, do the following:
                 */
                if (!pastFirstLine2) {
                    pastFirstLine2 = true;
                    /** Read the next line */
                    row2 = br2.readLine();
                    /** Skip the rest of the code and go back to the top of the loop */
                    continue;
                }

                /**
                 * Split row data by comma delimiter
                 */
                String[] rowData = row2.split(",");

                if (!rowData[0].equals(empNumberInput)) {
                    row2 = br2.readLine();
                    continue;
                }

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm");
                String timeInString = rowData[3] + " " + rowData[4];
                String timeOutString = rowData[3] + " " + rowData[5];



                Date timeIn = formatter.parse(timeInString);
                Date timeOut = formatter.parse(timeOutString);

                Attendance attendanceRecord = new Attendance(timeIn, timeOut);

                selectedEmployee.addTimeRecord(attendanceRecord);

                row2 = br2.readLine();
            }

            System.out.println(selectedEmployee.getFirstName() + " has " + selectedEmployee.getTimeRecordsCount() + " time records available");

            int monthlyTotalHours = selectedEmployee.getMonthlyTotalHours(parseInt(monthNumber));

            System.out.println("Monthly total hour for month of " + monthNumber + " is " + monthlyTotalHours + " hours.");
            System.out.println("The gross monthly salary for " + selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName() + " is " + selectedEmployee.getMonthlyGrossSalary(parseInt(monthNumber)));

            System.out.println("Printing this for our new branch");
            System.out.println("New line");
        } catch (FileNotFoundException ex) {
            System.out.println("No file found");
        } catch (IOException ex) {
            System.out.println("Cannot read lines from CSV file");
        } catch (ParseException ex) {
            System.out.println("Invalid dates");
        }
    }
}
