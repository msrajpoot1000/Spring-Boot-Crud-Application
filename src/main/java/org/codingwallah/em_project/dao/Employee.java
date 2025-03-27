package org.codingwallah.em_project.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @NoArgsConstructor
// @AllArgsConstructor
public class Employee {

  private Long id;
  private String name;
  private String phone;
  private String email;
  //   Employee(String name, String phone, String email) {
  //     this.name = name;
  //     this.phone = phone;
  //     this.email = email;
  //   }

  // public String getName() {
  //     return name;
  // }

  // public void setName(String name) {
  //     this.name = name;
  // }

}
