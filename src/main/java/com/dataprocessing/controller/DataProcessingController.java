package com.dataprocessing.controller;

import com.dataprocessing.dto.EmployeeDTO;
import com.dataprocessing.exception.DataProcessingException;
import com.dataprocessing.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dataprocessing.constant.DataProcessingConstant.TIMETAKEN_LABEL;
import static com.dataprocessing.constant.DataProcessingConstant.ERROR_LABEL;

/**
 * @author Rushin
 * @since 20-03-21
 */
@RestController
@Api(value = "Data Processing Operations", tags = "Bulk Data Processing")
public class DataProcessingController {

    /** The Employee Service */
    @Autowired
    EmployeeService employeeService;

    /**
     * API for Application health check
     *
     * @return
     */
    @GetMapping(path = "/")
    @ApiOperation(value = "Application Health Check")
    public String welcome() {
        return "Welcome to DataProcessing Application";
    }

    /**
     * API for Process CSV file
     * @param request
     * @param file
     * @return
     * @throws IOException
     * @throws DataProcessingException
     */
    @PostMapping(path = "/processCSV")
    @ApiOperation(value = "Process Employee CSV data")
    public ResponseEntity processCSV(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException, DataProcessingException {

        long startTime = System.currentTimeMillis();
        try {
            Map<String, Object> response = new HashMap<>();
            response.putAll(employeeService.processCSV(file));
            response.put(TIMETAKEN_LABEL, System.currentTimeMillis()-startTime);
            return new ResponseEntity(response,HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put(ERROR_LABEL, e.getMessage());
            errorResponse.put(TIMETAKEN_LABEL, System.currentTimeMillis()-startTime);
            return new ResponseEntity(errorResponse,HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * API for Insert Employee Record
     *
     * @param employeeDTO
     * @return
     */
    @PostMapping(path = "/insertEmployee")
    @ApiOperation(value = "Insert Employee Record")
    public String insertEmployee(@ApiParam(value = "Fill employee basic detail") @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.insertEmployee(employeeDTO);
    }

    /**
     * API for find Employee by Name
     *
     * @param name
     * @return
     */
    @GetMapping(path = "/findByName")
    @ApiOperation(value = "Find employee by name")
    public List<EmployeeDTO> findByName(@ApiParam(value = "Enter employee name") @RequestParam String name) {
        return employeeService.findByName(name);
    }

    /**
     * API for find All the Employees
     *
     * @return
     */
    @GetMapping(path = "/findAll")
    @ApiOperation(value = "Find all employees")
    public List<EmployeeDTO> findAll() {
        return employeeService.findAll();
    }

}
