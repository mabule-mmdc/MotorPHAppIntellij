package org.motorph;

import java.util.ArrayList;

public class Employee {
    private String id;
    private String first_name;
    private String last_name;
    private float hourly_rate;
    private ArrayList<Attendance> time_records = new ArrayList<>();

    public Employee(String id, String firstName, String lastName, float hourlyRate) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.hourly_rate = hourlyRate;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public float getHourlyRate() {
        return hourly_rate;
    }

    public void addTimeRecord(Attendance timeRecord) {
        this.time_records.add(timeRecord);
    }

    public int getTimeRecordsCount() {
        return this.time_records.size();
    }

    public int getMonthlyTotalHours(int monthNumber) {
        ArrayList<Attendance> filteredTimeRecords = new ArrayList<>();
        int monthlyTotalHours = 0;

        for (int index = 0; index < this.time_records.size(); index++) {
            Attendance timeRecord = this.time_records.get(index);

            if (timeRecord.getMonth() == monthNumber) {
                monthlyTotalHours += timeRecord.getHours();
            }
        }

        return monthlyTotalHours;
    }

    public float getMonthlyGrossSalary(int monthNumber) {
        int monthlyHours = this.getMonthlyTotalHours(monthNumber);
        return this.hourly_rate * monthlyHours;
    }
}
