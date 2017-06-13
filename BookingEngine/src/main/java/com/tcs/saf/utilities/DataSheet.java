package com.tcs.saf.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;

import com.tcs.saf.exceptions.DataSheetException;
import com.tcs.saf.exceptions.InvalidBrowserException;
import com.tcs.saf.base.BaseTest;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class DataSheet {

    Logger logger = Log.getInstance(Thread.currentThread().getStackTrace()[1]
            .getClassName());

    /**
     * @param destFile -This argument is for passing the location of the input
     * data sheet
     * @param sheetname -This argument is for passing the sheet name in the
     * input data sheet
     * @return-This method checks for the headings in the sheet and returns true
     * if the headings are present
     * @throws
     * @throws IOException
     */
    public boolean validateHeading(String destFile, String sheetname)
            throws IOException, BiffException {
        FileInputStream input = new FileInputStream(destFile);
        Workbook wb = Workbook.getWorkbook(input);

        Sheet sheet = wb.getSheet(sheetname);

        if ((sheet.getCell(0, 0).getContents().equalsIgnoreCase("BROWSER"))
                && (sheet.getCell(1, 0).getContents()
                .equalsIgnoreCase("EXECUTION STATUS"))) {
            return true;

        }
        input.close();
        return false;

    }

    /**
     * @param destFile -This argument is for passing the location of the input
     * data sheet
     * @param sheetname -This argument is for passing the sheet name in the
     * input data sheet
     * @return-This method returns column count in the data sheet
     * @throws
     * @throws IOException
     * @throws DataSheetException
     */
    public int getColumnCount(String destFile, String sheetname)
            throws IOException, DataSheetException,
            FileNotFoundException, BiffException {
        int column = 0;
        try {

            FileInputStream input = new FileInputStream(destFile);

            Workbook wb = Workbook.getWorkbook(input);

            Sheet sheet = wb.getSheet(sheetname);

            column = sheet.getColumns();

            if (column != 0) {
                return column;
            } else {
                logger.error("The input data sheet is blank");
                throw new DataSheetException("The input data sheet is blank");

            }

        } catch (FileNotFoundException fe) {
            logger.error("Please provide a valid sheet path:" + destFile + " "
                    + "can not be found");
            throw new DataSheetException("Please provide a valid sheet path:" + destFile + " "
                    + "can not be found");
        } catch (NullPointerException e) {
            logger.error("No sheet found with the class name " + sheetname);
            throw new DataSheetException("No sheet found with the class name " + sheetname);
        }

    }

    /**
     * @param destFile -This argument is for passing the location of the input
     * data sheet
     * @param sheetname -This argument is for passing the sheet name in the
     * input data sheet
     * @return-This method returns the row count in the sheet
     * @throws
     * @throws IOException
     */
    public int getRowCount(String destFile, String sheetname)
            throws IOException, BiffException {
        FileInputStream input = new FileInputStream(destFile);
        Workbook wb = Workbook.getWorkbook(input);

        Sheet sheet = wb.getSheet(sheetname);
        int row = sheet.getRows();
        input.close();
        return row;

    }

    /**
     * @param destFile -This argument is for passing the location of the input
     * data sheet
     * @param sheetname -This argument is for passing the sheet name in the
     * input data sheet
     * @return -This method returns the valid rows considering execution status
     * @throws
     * @throws IOException
     * @throws DataSheetException
     */
    public int getValidRows(String destFile, String sheetname)
            throws IOException, DataSheetException, BiffException {
        FileInputStream input = new FileInputStream(destFile);
        Workbook wb = Workbook.getWorkbook(input);
        Sheet sheet = wb.getSheet(sheetname);
        int columns = getColumnCount(destFile, sheetname);
        int rows = getRowCount(destFile, sheetname);
        int count = 0;
        for (int row = 1; row < rows; row++) {

            if ((sheet.getCell(0, row).getContents().contains(","))) {
                if (sheet.getCell(1, row).getContents().equalsIgnoreCase("Y")) {
                    String values = sheet.getCell(0, row).getContents();
                    String[] cellarray = values.trim().split(",");
                    count = cellarray.length + count;
                }
            } else {
                if (sheet.getCell(1, row).getContents().equalsIgnoreCase("Y")) {
                    count++;
                }
            }

        }
        input.close();
        return count;
    }

    /**
     * @param destFile -This argument is for passing the location of the input
     * data sheet
     * @param sheetname -This argument is for passing the sheet name in the
     * input data sheet
     * @return -Returns data object array which reads rows from the input data
     * sheet
     * @throws
     * @throws InvalidBrowserException
     * @throws java.io.FileNotFoundException
     * @throws IOException
     * @throws jxl.read.biff.BiffException
     * @throws DataSheetException
     */
    public Object[][] readFromSheet(String destFile, String sheetname)
            throws IOException,
            DataSheetException,
            FileNotFoundException,
            BiffException {

        int columns = getColumnCount(destFile, sheetname);
        int rows = getRowCount(destFile, sheetname);
        int dataObjectArraySize = getValidRows(destFile, sheetname);
        Object[][] dataObjectArray = new Object[dataObjectArraySize][1];

        int index = 0;
        boolean headingStatus = validateHeading(destFile, sheetname);
        FileInputStream input = new FileInputStream(destFile);
        Workbook wb = Workbook.getWorkbook(input);
        Sheet sheet = wb.getSheet(sheetname);
        String globalBrowserFlag = BaseTest.getGlobalBrowserFlag();

        // Validating for the heading status 
        if (headingStatus) {

            // Iterates through each row in excel 
            for (int row = 1; row < rows; row++) {
                String executionStatus = sheet.getCell(1, row).getContents();

                //Checking if execution status is 'Y' 
                if (executionStatus.trim().equalsIgnoreCase("Y")) {

                    // Checking if the browser value is defined globally 
                    if (globalBrowserFlag.equalsIgnoreCase("Y")) {
                        String[] globalBrowserArray = BaseTest.getBrowserName();
                        for (String browserValue : globalBrowserArray) {
                            LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
                            dataMap.put("Browser", browserValue);

                            for (int globalCount = 1; globalCount < columns; globalCount++) {
                                dataMap.put(sheet.getCell(globalCount, 0)
                                        .getContents(),
                                        sheet.getCell(globalCount, row)
                                        .getContents());
                            }
                            dataObjectArray[index] = new Object[]{dataMap};

                            index++;

                        }
                    } else {
                        // Checking if a cell has coma separated browser names
                        if ((sheet.getCell(0, row).getContents()
                                .contains(","))) {
                            String comaSeparatedBrowsers = sheet.getCell(0,
                                    row).getContents();
                            String[] browserArray = comaSeparatedBrowsers
                                    .trim().split(",");

                            for (String browser : browserArray) {

                                /*/ * This hash map stores the coma
                                 * separated values.This stores the
                                 * browser and its values
                                 */
                                LinkedHashMap<String, String> linkedMap1 = new LinkedHashMap<String, String>();
                                linkedMap1.put(sheet.getCell(0, 0)
                                        .getContents(), browser);
                                for (int column = 1; column < columns; column++) {

                                    // Stores the rest of the columns 
                                    linkedMap1.put(
                                            sheet.getCell(column, 0)
                                            .getContents(),
                                            sheet.getCell(column, row)
                                            .getContents());

                                }

                                dataObjectArray[index] = new Object[]{linkedMap1};

                                index++;

                            }

                        } else {

                            LinkedHashMap<String, String> linkedMap1 = new LinkedHashMap<String, String>();
                            for (int column = 0; column < columns; column++) {
                                linkedMap1.put(sheet.getCell(column, 0)
                                        .getContents(),
                                        sheet.getCell(column, row)
                                        .getContents());
                            }
                            dataObjectArray[index] = new Object[]{linkedMap1};

                            index++;
                        }

                    }

                } else if (executionStatus.trim().equalsIgnoreCase("N")) {

                } else if (sheet.getCell(1, row).getContents()
                        .equalsIgnoreCase("")) {
                    logger.error("Please enter either Y or N for execution status");
                    throw new NullPointerException("Please enter either Y or N for execution status");
                } else {
                    logger.error("Please enter either Y or N for execution status");
                    throw new DataSheetException(
                            "Please enter either Y or N for execution status");
                }

            }
        } else {
            logger.error("The sheet headings are invalid:The headings should be Browser and Execution Status");
            throw new DataSheetException(
                    "The sheet headings are invalid:The headings should be Browser and Execution Status");
        }

        input.close();
        return dataObjectArray;
    }
}
