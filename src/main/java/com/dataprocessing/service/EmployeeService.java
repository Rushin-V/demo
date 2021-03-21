package com.dataprocessing.service;

import com.dataprocessing.dto.EmployeeDTO;
import com.dataprocessing.exception.DataProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Rushin
 * @since 20-03-21
 */
public interface EmployeeService {

    /**
     * This Service method process CSV comes from Request and store to database
     *
     * @param file
     * @return
     * @throws IOException
     * @throws DataProcessingException
     */
    Map<String, Integer> processCSV(MultipartFile file) throws IOException, DataProcessingException;

    /**
     * This method insert single employee record to database
     *
     * @param employeeDTO
     * @return
     */
    String insertEmployee(EmployeeDTO employeeDTO);

    /**
     * This method search Employee based on Name
     *
     * @param name
     * @return
     */
    List<EmployeeDTO> findByName(String name);

    /**
     * This method returns all the employees available in records
     *
     * @return
     */
    List<EmployeeDTO> findAll();

}
