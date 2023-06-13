package org.motorph;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Attendance {
    private LocalDateTime time_in;
    private LocalDateTime time_out;

    public Attendance(Date timeIn, Date timeOut) {
        this.time_in = timeIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.time_out = timeOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public LocalDateTime getTimeIn() {
        return this.time_in;
    }

    public int getMonth() {
        return this.time_in.getMonthValue();
    }

    public int getHours() {
        return this.time_out.getHour() - this.time_in.getHour();
    }
}
