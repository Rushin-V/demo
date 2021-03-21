package com.dataprocessing.dto;

import lombok.Data;

/**
 * @author Rushin
 * @since 20-03-21
 */
@Data
public class EmployeeDTO {

    /** The Employee Id */
    private Integer employeeId;

    /** The Employee Name */
    private String name;

    /** The Employee Age */
    private Integer age;

    /** The Employee Country resides in */
    private String country;

    /** Default Constructor */
    public EmployeeDTO(){};

    /**
     * Parameterized Constructor
     * @param employeeId
     * @param name
     * @param age
     * @param country
     */
    public EmployeeDTO(Integer employeeId, String name, Integer age, String country){
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.country = country;
    };
}
