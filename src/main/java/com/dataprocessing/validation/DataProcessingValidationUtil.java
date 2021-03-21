package com.dataprocessing.validation;

import com.dataprocessing.exception.DataProcessingException;
import org.springframework.stereotype.Component;

import static com.dataprocessing.constant.DataProcessingConstant.*;

/**
 * @author Rushin
 * @since 20-03-21
 */
@Component
public class DataProcessingValidationUtil {

    /**
     * Pre Validation of CSV file Processing
     * It checks for file extension is CSV only
     * It checks for file size should not exceed 10 MB
     *
     * @param fileName
     * @param fileSize
     * @return
     * @throws DataProcessingException
     */
    public boolean preValidationOfCSV(String fileName, long fileSize) throws DataProcessingException {

        String[] fileComponent = fileName.split(DOT);
        String extension = fileComponent[1];
        if(!CSV.equalsIgnoreCase(extension)) {
            throw new DataProcessingException("File extension is not CSV");
        }
        if(fileSize > FILE_SIZE_10MB) {
            throw new DataProcessingException("File size is greater then 10 MB");
        }
        return true;
    }
}
