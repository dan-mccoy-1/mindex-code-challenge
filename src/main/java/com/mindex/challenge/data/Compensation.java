package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    private float salary;
    private Date effectiveDate;
    private String employeeId;

    public Compensation() {
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {    // TOD: Make this a LocalDate or LocalDateTime?
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = (effectiveDate != null) ? effectiveDate : new Date();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
