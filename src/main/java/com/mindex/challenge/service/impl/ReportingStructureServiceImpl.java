package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private final EmployeeServiceImpl employeeServiceImpl;
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    ReportingStructureServiceImpl(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @Override
    public ReportingStructure generate(String employeeId) {
        LOG.debug("Creating reportingStructure [{}]", employeeId);

        ReportingStructure reportingStructure = new ReportingStructure();

        Employee employee = employeeServiceImpl.read(employeeId);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        reportingStructure.setEmployee(employee);

        int numberOfReports ;
        if(employee.getDirectReports() != null && !employee.getDirectReports().isEmpty()) {
            reportingStructure.setNumberOfReports(listAllReports(employee.getDirectReports(), null).size());
        } else {
            reportingStructure.setNumberOfReports(0);
        }

        return reportingStructure;
    }

    private List<Employee> listAllReports(List<Employee> directReports, List<Employee> allReports) {
        if(allReports == null) {
            allReports = new ArrayList<>();
        }

        if (directReports != null && !directReports.isEmpty()) {
            allReports.addAll(directReports);

            List<Employee> subordinateReports = directReports.stream()
                    .filter(employee -> employee.getDirectReports() != null && !employee.getDirectReports().isEmpty())
                    .map(Employee::getDirectReports)
                    .flatMap(List::stream)
                    .distinct()
                    .toList();

            listAllReports(subordinateReports, allReports);
        }
        return allReports;
    }
}
