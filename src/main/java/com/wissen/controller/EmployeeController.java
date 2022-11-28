package com.wissen.controller;

import com.wissen.dto.EmployeeDetailDTO;
import com.wissen.dto.EmployeeSearchDTO;
import com.wissen.entity.Employee;
import com.wissen.helper.ExcelHelper;
import com.wissen.response.EmployeeSaveResponse;
import com.wissen.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping("/saveEmployeeDetails")
    @ApiOperation("Saves the details of an Employee")
    public ResponseEntity<List<EmployeeSaveResponse>> saveEmployeeDetails(@RequestBody @NotEmpty(message = "Details list is empty") final List<EmployeeDetailDTO> employeeDetails){
        List<EmployeeSaveResponse> employeeSaveResponses = this.service.saveEmployeeDetails(employeeDetails);

        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeSaveResponses);
    }
    @PostMapping
    @ApiOperation("Creates new Employee")
    public ResponseEntity<String> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(service.createEmployee(employee), HttpStatus.OK);
    }
    @PostMapping("/upload")
    @ApiOperation("Creates bulk employees from Excel")
    public ResponseEntity<String> createAll(@RequestParam("file") MultipartFile file){
        String message = "";
        if(ExcelHelper.hasExcelFormat(file)){
            try {
                return new ResponseEntity<>(service.createEmployeeFromExcel(file), HttpStatus.OK);
            } catch (IOException e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
            }
        }
        message = "Please upload an excel file!";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Update an employee with id")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Employee employee){
        return new ResponseEntity<>(service.updateEmployee(employee, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete an employee with id")
    public ResponseEntity<String> delete(@PathVariable int id){
        return new ResponseEntity<>(service.deleteEmployee(id), HttpStatus.OK);
    }

    @GetMapping("/employees")
    @ApiOperation("Get all employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        log.info("START : Getting all employees");
        List<Employee> employees = this.service.getEmployees();
        log.info("END : Getting all employees");
        return ResponseEntity.status(HttpStatus.OK)
                .body(employees);
    }

    /**
     * @autor Vishal Tomar
     * @description Method to search employee.
     * @param searchString
     * @return list of employeeSearch dto
     */
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeSearchDTO> > searchEmployee(@RequestParam final String searchString) {
        log.info("START : Searching all employees");
        List<EmployeeSearchDTO> employeesSearchDTOs = this.service.searchEmployee(searchString);
        log.info("END : Searching all employees");
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeesSearchDTOs);
    }
}