package org.codingwallah.em_project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRespsitory
  extends JpaRepository<EmployeeEntity, Long> {}
