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
import java.util.stream.Collectors;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private final EmployeeServiceImpl employeeServiceImpl;
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    ReportingStructureServiceImpl(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @Override
    public ReportingStructure get(String employeeId) {
        LOG.debug("Creating reportingStructure [{}]", employeeId);

        ReportingStructure reportingStructure = new ReportingStructure();

        reportingStructure.setEmployee(employeeServiceImpl.read(employeeId));

        List<Employee> allReports = listAllReports(reportingStructure.getEmployee().getDirectReports(), null);
        reportingStructure.setNumberOfReports(allReports.size());

        return reportingStructure;
    }

    private List<Employee> listAllReports(List<String> directReportIds, List<Employee> allReports) {
        if(allReports == null) { allReports = new ArrayList<>(); }

        if (directReportIds != null && !directReportIds.isEmpty()) {
            List<Employee> currentReports = new ArrayList<>();
            for (String directReportId : directReportIds) {
                currentReports.add(employeeServiceImpl.read(directReportId));
            }
            allReports.addAll(currentReports);

            List<String> currentReportIds = currentReports.stream()
                    .filter(employee -> employee.getDirectReports() != null && !employee.getDirectReports().isEmpty())
                    .map(Employee::getDirectReports)
                    .flatMap(List::stream)
                    .distinct()
                    .collect(Collectors.toList());

            listAllReports(currentReportIds, allReports);
        }
        return allReports;
    }
}
