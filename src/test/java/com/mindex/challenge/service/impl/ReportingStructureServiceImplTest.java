package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureUrl;
    private String employeeUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reportingstructure/{employeeId}";
        employeeUrl = "http://localhost:" + port + "/employee";
    }

    @Test
    public void testGenerate() {
        // Test data
        Employee testEmployee1 = new Employee();
        testEmployee1.setFirstName("John");
        testEmployee1.setLastName("Doe");
        testEmployee1.setDepartment("Engineering");
        testEmployee1.setPosition("Developer");
        testEmployee1.setDirectReports(new ArrayList<>());
        Employee createdEmployee1 = restTemplate.postForEntity(employeeUrl, testEmployee1, Employee.class).getBody();

        Employee testEmployee2 = new Employee();
        testEmployee2.setFirstName("Jane");
        testEmployee2.setLastName("Doe");
        testEmployee2.setDepartment("Engineering");
        testEmployee2.setPosition("Developer");
        testEmployee2.setDirectReports(new ArrayList<>(List.of(createdEmployee1)));
        Employee createdEmployee2 = restTemplate.postForEntity(employeeUrl, testEmployee2, Employee.class).getBody();

        Employee testEmployee3 = new Employee();
        testEmployee3.setFirstName("Joe");
        testEmployee3.setLastName("Doe");
        testEmployee3.setDepartment("Engineering");
        testEmployee3.setPosition("Developer");
        testEmployee1.setDirectReports(new ArrayList<>());
        Employee createdEmployee3 = restTemplate.postForEntity(employeeUrl, testEmployee3, Employee.class).getBody();

        Employee testEmployee4 = new Employee();
        testEmployee4.setFirstName("Jules");
        testEmployee4.setLastName("Doe");
        testEmployee4.setDepartment("Engineering");
        testEmployee4.setPosition("Developer");
        testEmployee4.setDirectReports(new ArrayList<>(List.of(createdEmployee3, createdEmployee2)));
        Employee createdEmployee4 = restTemplate.postForEntity(employeeUrl, testEmployee4, Employee.class).getBody();


        // Generate checks
        ReportingStructure testReportingStructure = new ReportingStructure();
        testReportingStructure.setEmployee(createdEmployee4);
        testReportingStructure.setNumberOfReports(3);

        ReportingStructure generatedReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, createdEmployee4.getEmployeeId()).getBody();

        assertNotNull(generatedReportingStructure);
        assertReportingStructureEquivalence(testReportingStructure, generatedReportingStructure);
    }

    private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
        assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
    }
}