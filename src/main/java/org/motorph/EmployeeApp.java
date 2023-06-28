package org.motorph;

import java.io.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author micahbule
 */
public class EmployeeApp {
    /**
     * For Windows users, it might look like this one:
     * String employeeDetailsPath = "C:\Users\\username\Downloads\whatever-the-file-name-is.csv";
     */
    String employeeDetailsPath = "/Users/micahbule/Downloads/MotorPH Employee Data - Employee Details.csv";
    String employeeAttendancePath = "/Users/micahbule/Downloads/MotorPH Employee Data - Attendance Record.csv";

    ArrayList<Employee> employees = new ArrayList<Employee>();

    public EmployeeApp() {

        BufferedReader br = null;
        try {
            /**
             * Open CSV File
             */
            br = new BufferedReader(new FileReader(this.employeeDetailsPath));
            /**
             * Read next line
             */
            String row = br.readLine();
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

                Employee newEmployee = new Employee(processedRow.get(0), processedRow.get(2), processedRow.get(1), Float.parseFloat(processedRow.get(18)), Float.parseFloat(processedRow.get(13)));
                this.employees.add(newEmployee);

                row = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmployeeApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(EmployeeApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Employee getEmployeeByNumber(String employeeNumber) {
        Employee selectedEmployee = new Employee("", "", "", 0, 0);

        for (int index = 0; index < this.employees.size(); index++) {
            Employee currentEmployee = this.employees.get(index);
            if (currentEmployee.getId().equals(employeeNumber)) {
                selectedEmployee = currentEmployee;
                break;
            }
        }

        return selectedEmployee;
    }

    public void loadAttendanceData(Employee selectedEmployee) {
        BufferedReader br2 = null;
        try {
            /**
             * Open CSV File
             */
            br2 = new BufferedReader(new FileReader(employeeAttendancePath));
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

                if (!rowData[0].equals(selectedEmployee.getId())) {
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmployeeApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(EmployeeApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br2.close();
            } catch (IOException ex) {
                Logger.getLogger(EmployeeApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
