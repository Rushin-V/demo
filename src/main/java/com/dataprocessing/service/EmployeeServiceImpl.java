package com.dataprocessing.service;

import com.dataprocessing.dao.EmployeeDAO;
import com.dataprocessing.dto.EmployeeDTO;
import com.dataprocessing.entity.Employee;
import com.dataprocessing.exception.DataProcessingException;
import com.dataprocessing.validation.DataProcessingValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.dataprocessing.constant.DataProcessingConstant.COMMA;
import static com.dataprocessing.constant.DataProcessingConstant.SAVERECORDS_LABEL;
import static com.dataprocessing.constant.DataProcessingConstant.SKIPRECORDS_LABEL;

/**
 * @author Rushin
 * @since 20-03-21
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService{

    /** The Employee DAO */
    @Autowired
    EmployeeDAO employeeDAO;

    /** The Employee Validation Util */
    @Autowired
    DataProcessingValidationUtil validationUtil;

    @Override
    public Map<String, Integer> processCSV(MultipartFile file) throws IOException, DataProcessingException {

        String originalFileName = file.getOriginalFilename();
        long fileSize = file.getSize();
        if(validationUtil.preValidationOfCSV(originalFileName, fileSize)) {

            Map<Integer, Integer> dbEntries = employeeDAO.getAllEmployeeIds();
            List<Employee> employeeList = new ArrayList<>();
            InputStream fileStream = file.getInputStream();
            Integer savedRecords = 0;
            Integer skipRecords = 0;
            try(Scanner scanner = new Scanner(fileStream);) {
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String[] empData = null;
                    try {
                        empData = scanner.nextLine().split(COMMA);
                        Integer employeeId = Integer.valueOf(empData[0]);
                        Employee employee = new Employee(employeeId,empData[1], Integer.valueOf(empData[2]), empData[3]);
                        if(dbEntries.containsKey(employeeId)) {
                            employeeDAO.updateEmployee(employee);
                            savedRecords++;
                        } else {
                            employeeList.add(employee);
                        }
                    }catch (Exception e) {
                        skipRecords++;
                        log.warn("Skipped CSV record for processing of Employee={}: "+e.getMessage(),empData,e);
                        continue;
                    }
                }
            }
            Integer savedRecordsAll =  employeeDAO.saveAll(employeeList);
            Map<String, Integer> response = new HashMap<>();
            response.put(SAVERECORDS_LABEL, savedRecords+savedRecordsAll);
            response.put(SKIPRECORDS_LABEL, skipRecords);
            return response;
        }
        return null;
    }

    @Override
    public String insertEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setName(employeeDTO.getName());
        employee.setAge(employeeDTO.getAge());
        employee.setCountry(employeeDTO.getCountry());
        return employeeDAO.insertEmployee(employee);
    }

    @Override
    public List<EmployeeDTO> findByName(String name) {

        List<Employee> employees = employeeDAO.findByName(name);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.stream().forEach(i->{
            EmployeeDTO dto = new EmployeeDTO();
            dto.setEmployeeId(i.getEmployeeId());
            dto.setName(i.getName());
            dto.setAge(i.getAge());
            dto.setCountry(i.getCountry());
            employeeDTOS.add(dto);
        });
        return employeeDTOS;
    }

    @Override
    public List<EmployeeDTO> findAll() {

        List<Employee> allEmployees = employeeDAO.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        allEmployees.stream().forEach(i->{
            employeeDTOS.add(new EmployeeDTO(i.getEmployeeId(),i.getName(),i.getAge(),i.getCountry()));
        });
        return employeeDTOS;
    }
}
