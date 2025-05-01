package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationEmployeeIdUrl;
    
    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationEmployeeIdUrl = "http://localhost:" + port + "/compensation/{employeeId}";
    }

    @Test
    public void testCreateRead() {
        String EmployeeId = UUID.randomUUID().toString();

        Compensation testCompensation = new Compensation();
        testCompensation.setSalary(100000);
        testCompensation.setEffectiveDate(LocalDate.now());
        testCompensation.setEmployeeId(EmployeeId);

        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        assert createdCompensation != null;
        assertNotNull(createdCompensation.getEmployeeId());
        assertCompensationEquivalence(testCompensation, createdCompensation);


        // Read checks
//        Employee readEmployee = restTemplate.getForEntity(compensationUrl, Compensation.class, createdEmployee.getEmployeeId()).getBody();
//        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
//        assertEmployeeEquivalence(createdEmployee, readEmployee);
//
//
//        // Update checks
//        readEmployee.setPosition("Development Manager");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Employee updatedEmployee =
//                restTemplate.exchange(compensationEmployeeIdUrl,
//                        HttpMethod.PUT,
//                        new HttpEntity<Employee>(readEmployee, headers),
//                        Employee.class,
//                        readEmployee.getEmployeeId()).getBody();
//
//        assertEmployeeEquivalence(readEmployee, updatedEmployee);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getSalary(), actual.getSalary(), 0);
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
    }
}