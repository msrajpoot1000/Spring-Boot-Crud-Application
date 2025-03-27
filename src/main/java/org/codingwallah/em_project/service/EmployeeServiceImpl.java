package org.codingwallah.em_project.service;

import java.util.ArrayList;
import java.util.List;

import org.codingwallah.em_project.dao.Employee;
import org.codingwallah.em_project.entity.EmployeeEntity;
import org.codingwallah.em_project.repository.EmployeeRespository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRespository employeeRespsitory;

  @Override
  public String createEmployee(Employee employee) {
    EmployeeEntity employeeEntity = new EmployeeEntity();
    BeanUtils.copyProperties(employee, employeeEntity);
    employeeRespsitory.save(employeeEntity);
    // employees.add(employee);
    return "Data saved successfully";
  }

  @Override
  public Employee readEmployee(Long id) {
    EmployeeEntity employeeEntity = employeeRespsitory.findById(id).get();
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeEntity, employee);
    return employee;
  }

  @Override
  public List<Employee> readEmployees() {
    List<EmployeeEntity> employeesList = employeeRespsitory.findAll();
    List<Employee> employees = new ArrayList<>();

    for (EmployeeEntity employeeEntity : employeesList) {
      // Convert EmployeeEntity to Employee
      Employee employee = new Employee();
      employee.setId(employeeEntity.getId());
      employee.setName(employeeEntity.getName());
      employee.setPhone(employeeEntity.getPhone());
      employee.setEmail(employeeEntity.getEmail());

      employees.add(employee);
    }
    return employees;
  }

  @Override
  public Boolean deleteEmployee(Long id) {
    EmployeeEntity emp = employeeRespsitory.findById(id).get();
    employeeRespsitory.delete(emp);
    return true;
  }

  public String updateEmployee(Long id, Employee employee) {
    EmployeeEntity existingEmploy = employeeRespsitory.findById(id).get();
    existingEmploy.setEmail(employee.getEmail());
    existingEmploy.setName(employee.getName());
    existingEmploy.setPhone(employee.getPhone());
    employeeRespsitory.save(existingEmploy);
    return "Your Data Updated Successfully";
  }
}
