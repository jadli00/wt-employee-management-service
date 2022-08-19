package com.wissen.dto;

import com.wissen.entity.AccountDetails;
import com.wissen.entity.Client;
import com.wissen.entity.Employee;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    int id;
    String firstName;
    String pan;
    String lastName;
    String email;
    String dateOfJoining;
    double yearExperience;
    String manager;
    boolean active;
    int role;
    int designation;
    AccountDetails accountDetails;
    Client client;
    public EmployeeDTO(Employee employee){
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.pan = employee.getPan();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.dateOfJoining = employee.getDateOfJoining();
        this.yearExperience = employee.getYearExperience();
        this.manager = employee.getManager();
        this.active = employee.isActive();
        this.role = employee.getRole();
        this.designation = employee.getDesignation();
        this.accountDetails = employee.getAccountDetails();
        this.client = employee.getClient();
    }
}
