package com.mindex.challenge.data;

import java.time.LocalDate;

public class Compensation {
    private float salary;
    private LocalDate effectiveDate;
    private Employee employee;

    public Compensation() {
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {    // TOD: Make this a LocalDate or LocalDateTime?
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = (effectiveDate != null) ? effectiveDate : LocalDate.now();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployeeId(Employee employee) { this.employee = employee; }
}
