package org.codingwallah.em_project.repository;

import org.codingwallah.em_project.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRespository
  extends JpaRepository<EmployeeEntity, Long> {}
