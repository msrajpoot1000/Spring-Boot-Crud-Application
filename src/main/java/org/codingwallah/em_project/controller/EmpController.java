package org.codingwallah.em_project.controller;

import java.util.ArrayList;
import java.util.List;
import org.codingwallah.em_project.dao.Employee;
import org.codingwallah.em_project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// @CrossOrigin(origins = "*")
@RestController
public class EmpController {

  // List<Employee> employees = new ArrayList<>();
  // EmployeeService employeeService = new EmployeeServiceImpl();

  // we can't create object direclty using new beacause it allocate memory all the time so i want to
  // create object usign IOC using dependecny injection

  @Autowired
  EmployeeService employeeService;

  @GetMapping("employees")
  public List<Employee> getAllEmployees() {
    return employeeService.readEmployees();
  }

  @GetMapping("employees/{id}")
  public ResponseEntity<?> getEmployeesById(@PathVariable Long id) {
    Employee employee = employeeService.readEmployee(id);

    if (employee != null) {
      return ResponseEntity.ok(employee);
    } else {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Employee not found with id: " + id);
    }
  }

  // @RequestBody
  // because we get all data
  @PostMapping("employees")
  public String addEmployee(@RequestBody Employee employee) {
    // this employee create create the object and it call th
    // function addEmployee() and it have data in object it pass the value and save the data;)
    employeeService.createEmployee(employee);
    return "Your Data Saved successfully";
  }

  @DeleteMapping("employees/{id}")
  public String deleteEmp(@PathVariable Long id) {
    if (employeeService.deleteEmployee(id)) {
      return "Employee with id " + id + " deleted successfully";
    }
    return "not found";
  }

  @PutMapping("employees/{id}")
  public String updateEmployee(
    @PathVariable Long id,
    @RequestBody Employee employee
  ) {
    return employeeService.updateEmployee(id, employee);
  }
}
