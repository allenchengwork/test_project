package com.maplebox.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.maplebox.repository.EmployeeRepository;
import com.maplebox.table.Employee;

@Service
@Transactional(rollbackFor = Throwable.class, timeout = 10)
public class EmployeeService extends BaseService {
	static Logger log = LoggerFactory.getLogger(EmployeeService.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Employee findOne(Integer id) {
		return employeeRepository.findOne(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Page<Employee> findAll() {
		return employeeRepository.findAll(new PageRequest(0, 1000));
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Employee> findList() {
		//return employeeRepository.findTop100ByNameContainingOrderByIdDesc("d4ad");
		return employeeRepository.readAll("d4ad", new PageRequest(0, 100));
	}
	
	public Employee save(Employee item) throws Exception {
		Employee employee = employeeRepository.save(item);
		return employee;
	}
	
	public List<Employee> save(Iterable<Employee> entities) throws Exception {
		List<Employee> list = employeeRepository.save(entities);
		return list;
	}
}
