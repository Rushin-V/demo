package com.dataprocessing.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rushin
 * @since 20-03-21
 */
@Entity
@Table(name = "employee")
@Data
public class Employee implements Serializable {

    /** The Column Id */
    @Id
    @GeneratedValue(generator = "sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence", allocationSize = 10)
    @Column(name = "id")
    private long id;

    /** The Column Employee Id */
    @CsvBindByName(column = "Employee Id")
    @Column(name = "employeeId")
    private Integer employeeId;

    /** The Column Employee Name */
    @CsvBindByName(column = "Employee Name")
    @Column(name = "name")
    private String name;

    /** The Column Employee Age */
    @CsvBindByName(column = "Age")
    @Column(name = "age")
    private Integer age;

    /** The Column Employee Country */
    @CsvBindByName(column = "Country")
    @Column(name = "country")
    private String country;

    /** Employee Default Constructor */
    public Employee() {}

    /**
     * Employee Parameterized Constructor
     * @param employeeId
     * @param name
     * @param age
     * @param country
     */
    public Employee(Integer employeeId, String name, Integer age, String country) {
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.country = country;
    }

}
