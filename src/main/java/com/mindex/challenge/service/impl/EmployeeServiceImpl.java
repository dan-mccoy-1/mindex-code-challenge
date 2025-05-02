package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Retrieving employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        populateAllReports(employee, null);

        return employee;
    }

    // This function provides two added pieces of functionality:
    // - It replaces the incomplete directReport employee objects (which include their employeeId only) from the
    //   database (see resources/static/employee_database.json) with complete employee objects
    // - It Cascades down the direct report tree to return the full reports hierarchy (instead of just the first level)
    private List<Employee> populateAllReports(Employee employee, List<Employee> allReports) {
        if(allReports == null) {
            allReports = new ArrayList<>();
        }

        if (employee.getDirectReports() != null && !employee.getDirectReports().isEmpty()) {
            List<String> directReportIds = employee.getDirectReports().stream().map(Employee::getEmployeeId).toList();
            List<Employee> directReportsComplete = new ArrayList<>();
            for (String directReportId : directReportIds) {
                Employee completeDirectReport = employeeRepository.findByEmployeeId(directReportId);
                populateAllReports(completeDirectReport, allReports);
                directReportsComplete.add(completeDirectReport);
            }
            employee.setDirectReports(directReportsComplete);
        }

        return allReports;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
}
