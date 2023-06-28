package org.motorph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainEmployeeApp extends JFrame {
    private EmployeeApp empData;
    private Employee selectedEmployee;
    public MainEmployeeApp() {
        setContentPane(mainPanel);

        pack();

        this.empData = new EmployeeApp();
        loadEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedEmployee = empData.getEmployeeByNumber(employeeNumber.getText());

                employeeName.setText(selectedEmployee.getFullName());
                hourlyRate.setText(String.valueOf(selectedEmployee.getHourlyRate()));
                basicSalary.setText(String.valueOf(selectedEmployee.getBasicSalary()));

                empData.loadAttendanceData(selectedEmployee);
            }
        });
        selectedMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Date parsedDate = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(String.valueOf(selectedMonth.getSelectedItem()));
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(parsedDate);
                    int monthNumber = cal.get(Calendar.MONTH) + 1;

                    System.out.println("The selected month is " + monthNumber);

                    totalHours.setText(String.valueOf(selectedEmployee.getMonthlyTotalHours(monthNumber)));
                    grossSalary.setText(String.valueOf(selectedEmployee.getMonthlyGrossSalary(monthNumber)));
                    netSalary.setText(String.valueOf(selectedEmployee.getMonthlyNetSalary(monthNumber)));
                } catch (ParseException ex) {
                    Logger.getLogger(MainEmployeeApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainEmployeeApp().setVisible(true);
            }
        });
    }
    private JTextField employeeNumber;
    private JButton loadEmployee;
    private JComboBox selectedMonth;
    private JLabel employeeName;
    private JLabel hourlyRate;
    private JLabel basicSalary;
    private JLabel totalHours;
    private JLabel grossSalary;
    private JLabel netSalary;
    private JPanel mainPanel;
}
