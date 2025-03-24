package org.codingwallah.em_project;

import java.util.List;
import javax.print.DocFlavor.STRING;

public interface EmployeeService {
  String createEmployee(Employee employee);

  List<Employee> readEmployees();

  Boolean deleteEmployee(Long id);

  String updateEmployee(Long id, Employee employee);

  Employee readEmployee(Long id);
}
