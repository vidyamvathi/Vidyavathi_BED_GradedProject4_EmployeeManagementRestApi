package com.greatlearning.emrestapi.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greatlearning.emrestapi.repository.EmployeeRepository;
import com.greatlearning.emrestapi.repository.RoleRepository;
import com.greatlearning.emrestapi.repository.UserRepository;
import com.greatlearning.emrestapi.entity.Employee;
import com.greatlearning.emrestapi.entity.Role;
import com.greatlearning.emrestapi.entity.User;
import com.greatlearning.emrestapi.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
		employeeRepository = theEmployeeRepository;
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee findById(int theId) {
		Optional<Employee> result = employeeRepository.findById(theId);

		Employee theEmployee = null;

		if (result.isPresent()) {
			theEmployee = result.get();
		} else {
			return theEmployee;
		}

		return theEmployee;
	}

	@Override
	public void save(Employee theEmployee) {
		employeeRepository.save(theEmployee);
	}

	@Override
	public void deleteById(int theId) {
		employeeRepository.deleteById(theId);
	}

	@Override
	public List<Employee> searchByFirstName(String firstName) {

		return employeeRepository.findByFirstNameContainsAllIgnoreCase(firstName);
	}

	@Override
	public List<Employee> sortByFirstNameAsc() {

		return employeeRepository.findAllByOrderByFirstNameAsc();
	}

	@Override
	public User saveUser(User user) {

		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {

		return roleRepository.save(role);
	}

	@Override
	public List<Employee> getEmployeesSortedByName(String direction) {
		Direction sortDirection = replaceOrderStringThroughDirection(direction);
		return employeeRepository.findAll(Sort.by(sortDirection, "firstName"));
	}

	private Direction replaceOrderStringThroughDirection(String sortDirection) {
		if (sortDirection.equalsIgnoreCase("asc")) {
			return Direction.ASC;
		} else {
			return Direction.DESC;
		}
	}
}