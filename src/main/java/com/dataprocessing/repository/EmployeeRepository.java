package com.dataprocessing.repository;

import com.dataprocessing.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author Rushin
 * @since 20-03-21
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /** Query to find Employee by Name */
    List<Employee> findByName(String name);

    /** Query to find All Employee Ids */
    @Query("select e.employeeId from Employee e")
    Set<Integer> getAllEmployeeIds();

    /** Query to update Employee Record based on Employee Id */
    @Transactional
    @Modifying
    @Query("update Employee e set e.name = :name, e.age = :age, e.country = :country where e.employeeId = :employeeId")
    void updateEmployee(@Param(value = "employeeId") Integer employeeId, @Param(value = "name") String name, @Param(value = "age") Integer age, @Param(value = "country") String country);
}
