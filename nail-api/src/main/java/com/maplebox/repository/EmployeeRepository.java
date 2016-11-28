package com.maplebox.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.maplebox.table.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	List<Employee> findTop100ByNameContainingOrderByIdDesc(String name);
	
	@Query("SELECT e FROM Employee e WHERE e.name LIKE CONCAT('%',:name,'%') ORDER BY id DESC")
	List<Employee> readAll(@Param("name") String name, Pageable page);
}
