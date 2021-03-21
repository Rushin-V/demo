package com.dataprocessing.dao;

import com.dataprocessing.entity.Employee;
import com.dataprocessing.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dataprocessing.constant.DataProcessingConstant.SUCCESS_RESPONSE;

/**
 * @author Rushin
 * @since 20-03-21
 */
@Component
public class EmployeeDAO {

    /** The Employee Repository */
    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * Insert Employee Detail
     * @param employee
     * @return
     */
    public String insertEmployee(Employee employee) {
        employeeRepository.save(employee);
        return SUCCESS_RESPONSE;
    }

    /**
     * Find Employee by Name
     * @param name
     * @return
     */
    public List<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    /**
     * Find All Employees
     * @return
     */
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    /**
     * Save All Employees
     * @param employees
     * @return
     */
    public Integer saveAll(List<Employee> employees) {
        return employeeRepository.saveAll(employees).size();
    }

    /**
     * Update Employee
     * @param employee
     */
    public void updateEmployee(Employee employee) {
        employeeRepository.updateEmployee(employee.getEmployeeId(), employee.getName(), employee.getAge(), employee.getCountry());
    }

    /**
     * Get All Employee Id's
     * @return
     */
    public Map<Integer, Integer> getAllEmployeeIds() {
        return employeeRepository.getAllEmployeeIds().stream().collect(Collectors.toMap(x->x,x->x));
    }
}
