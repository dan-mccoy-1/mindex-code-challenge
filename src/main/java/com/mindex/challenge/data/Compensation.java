package com.mindex.challenge.data;

import java.time.LocalDate;

public class Compensation {
    private float salary;
    private LocalDate effectiveDate;
    private String employeeId;

    public Compensation() {
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = (effectiveDate != null) ? effectiveDate : LocalDate.now();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
