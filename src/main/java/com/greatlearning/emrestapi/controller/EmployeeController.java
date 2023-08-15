package com.greatlearning.emrestapi.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.emrestapi.entity.Employee;
import com.greatlearning.emrestapi.entity.Role;
import com.greatlearning.emrestapi.entity.User;
import com.greatlearning.emrestapi.service.EmployeeService;

@RestController
@RequestMapping("api/employees")

//http://localhost:8080/emrestapi/api/employees

public class EmployeeController {

	private EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}

	@PostMapping("/user")
	public User saveUser(@RequestBody User user) {
		return employeeService.saveUser(user);
	}

	@PostMapping("/role")
	public Role saveRole(@RequestBody Role role) {
		return employeeService.saveRole(role);
	}

	@GetMapping("/employeeslist")
	public List<Employee> findAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> currentPrincipalName = authentication.getAuthorities();
		System.out.println(currentPrincipalName);
		return employeeService.findAll();
	}

	@GetMapping("/employeeslist/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		Employee employee = employeeService.findById(employeeId);
		if (employee == null) {
			throw new RuntimeException("Couldn't find employee id : " + employeeId);
		}

		return employee;
	}

	@PostMapping("/employeeslist")
	public Employee addEmployee(@RequestBody Employee employee) {
		employee.setId(0);
		employeeService.save(employee);
		return employee;
	}

	@PutMapping("/employeeslist")
	public Employee updateEmployee(@RequestBody Employee employee) {

		employeeService.save(employee);

		return employee;
	}

	@DeleteMapping("/employeeslist/{employeeId}")

	public String deleteEmployee(@PathVariable int employeeId) {

		Employee temp = employeeService.findById(employeeId);

		if (temp == null) {

			return "Couldn't find employee id : " + employeeId;
		}

		employeeService.deleteById(employeeId);

		return "Deleted employee id - " + employeeId;
	}

	@GetMapping("/employeeslist/search/{firstName}")
	public List<Employee> searchByFirstName(@PathVariable String firstName) {
		return employeeService.searchByFirstName(firstName);
	}

	@GetMapping("/employeeslist/sort")
	public List<Employee> getEmployeesSortedByName(@RequestParam String order) {
		order = order.replaceAll("\"", "");
		return employeeService.getEmployeesSortedByName(order);
	}

}