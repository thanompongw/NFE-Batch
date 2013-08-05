package co.th.ktc.nfe.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import co.th.ktc.nfe.batch.exception.CommonException;
import co.th.ktc.nfe.constants.NFEBatchConstants;

@Component(value = "commonPOI")
public class CommonPOI {
	
	private static Logger LOG = Logger.getLogger(CommonPOI.class);

    Workbook wb;
    
    /**
     * Buffer size for transferring process
     */
    public static final int BUFFER_SIZE = 4096;

    public CommonPOI() {
    }

    public CommonPOI(String templateName, 
    		         String pathTemplate) throws CommonException {
        this.wb = prepareWorkbook(templateName, pathTemplate);
    }

    /**
     * used to prepare workbook for Online Report
     * @param templateName
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws Exception
     */
    private Workbook prepareWorkbook(String templateName, 
    		                         String pathTemplate) throws CommonException {

    	StringBuilder filePath = new StringBuilder();
    	
    	filePath.append(pathTemplate);
    	filePath.append(templateName);
    	filePath.append("_Template");
    	filePath.append(NFEBatchConstants.XLS_REPORT_EXTENTION);
    	
    	if (!isExistFile(filePath.toString())) {
        	//If not exists then look for .xlsx type
    		filePath.setLength(0);
        	filePath.append(pathTemplate);
        	filePath.append(templateName);
        	filePath.append("_Template");
        	filePath.append(NFEBatchConstants.XLSX_REPORT_EXTENTION);
        	if (!isExistFile(filePath.toString())){
        		throw ErrorUtil.generateError("MSTD0013AERR", filePath.toString());
        	}
        }

        InputStream is = null;
        File template = new File(filePath.toString());
        try {
            is = new FileInputStream(template);
            wb = WorkbookFactory.create(is);
        } catch (FileNotFoundException fne) {
        	LOG.fatal(fne.getMessage(), fne);
        	throw ErrorUtil.generateError("MSTD0038AERR", filePath.toString());
		} catch (InvalidFormatException ife) {
        	LOG.fatal(ife.getMessage(), ife);
        	throw ErrorUtil.generateError("MSTD0038AERR", filePath.toString());
		} catch (IOException ioe) {
        	LOG.fatal(ioe.getMessage(), ioe);
        	throw ErrorUtil.generateError("MSTD0038AERR", filePath.toString());
		} finally {
            template = null;
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                	//Do Nothing.
                }
            }
        }

        return wb;
    }

    /**
     * @return Returns the workbook.
     */
    public Workbook getWorkBook() {
        return wb;
    }

    /**
     * Function to create new cell base on parameter.
     * (in default POI will return null to unformatted cell.)
     * <p/>
     * @param sheet
     * @param intRow
     * @param intCol
     * <p/>
     * @return
     */
    public Cell createCell(Sheet sheet, int intRow, int intCol) {

        Row row = sheet.getRow(intRow);
        if (row == null) {
            row = sheet.createRow(intRow);
        }

        Cell cell = row.getCell(intCol);
        if (cell == null) {
            cell = row.createCell(intCol);
        }
        return cell;
    }

    /**
     * Function to create new cell with format base on cellToCopy.
     * <p/>
     * @param sheet
     * @param cellToCopy
     * @param intRow
     * @param intCol
     * <p/>
     * @return
     */
    public Cell createCell(Sheet sheet,
                           Cell cellToCopy,
                           int intRow,
                           int intCol) {

        Cell newCell = createCell(sheet,
                                  intRow,
                                  intCol);
        if (cellToCopy != null) {
            try {
                newCell.setCellStyle(cellToCopy.getCellStyle());
                newCell.setCellType(cellToCopy.getCellType());
            } catch (Exception e) {
            }
        }
        return newCell;
    }

    /**
     * Function to assign current cell with object value.
     * <p/>
     * @param cell
     * @param obj
     * <p/>
     * @return
     * <p/>
     * @throws Exception
     */
    public Cell setObject(Cell cell, Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                cell.setCellValue((String) obj);
            } else if (obj instanceof Number) {
                cell.setCellValue(((Number) obj).doubleValue());
            } else if (obj instanceof Date) {
                cell.setCellValue((Date) obj);
            }
        }
        return cell;
    }

    public Cell setObject(Sheet sheet,
                          int row,
                          int col,
                          Object obj) {
        Cell cell = createCell(sheet, row, col);

        cell = setObject(cell, obj);

        return cell;

    }

    public Cell setObject(Sheet sheet,
                          int row,
                          int col,
                          Object obj,
                          CellStyle cellStyle,
                          int width) {
        Cell cell = createCell(sheet, row, col);
        cell = setObject(cell, obj);
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
        if (width != 0) {
            cell.getSheet().setColumnWidth(col, width);
        }
        return cell;

    }

    public Sheet writeRow(Sheet sheet,
                          Sheet sheetCopy,
                          int row,
                          int rowCopy,
                          int col,
                          int copyCol,
                          Object[] objData) {
        Row rowToCopy = sheetCopy.getRow(rowCopy);

        for (int i = 0; i < objData.length; i++) {
            Cell cellToCopy = rowToCopy.getCell((copyCol + i));
            Cell newCell = createCell(sheet, cellToCopy, row, (col + i));
            newCell = setObject(newCell, objData[i]);
        }

        return sheet;
    }

    /**
     * Method to insert new row to destination.
     * Method to copy format from source to destination.
     * this method used especially to insert total/ summary in tabular data
     * because the position is depend on row size.
     *
     * @param intSheet      sheet destination
     * @param intSheetCopy  sheet source
     * @param row           row destination
     * @param rowCopy       row copy
     * @param colStart      column start source copy
     * @param colEnd        column End Source copy
     * @param insertRowFlag insert new row before copy row
     * <p/>
     * @throws Exception
     */
    public void copyRow(int intSheet,
                        int intSheetCopy,
                        int row,
                        int rowCopy,
                        int colStart,
                        int colEnd,
                        boolean insertRowFlag) {
        if (insertRowFlag) {
            Sheet sheet = wb.getSheetAt(intSheet);
            sheet.shiftRows(row, sheet.getLastRowNum(), 1);
        }
        this.copyRow(intSheet, intSheetCopy, row, rowCopy, colStart, colEnd);
    }

    /**
     * Method to copy format from source to destination.
     * this method used especially to insert total/ summary in tabular data
     * because the position is depend on row size.
     *
     * @param intSheet     sheet destination
     * @param intSheetCopy sheet source
     * @param row          row destination
     * @param rowCopy      row copy
     * @param colStart     column start source copy
     * @param colEnd       column End Source copy
     * <p/>
     * @throws Exception
     */
    public void copyRow(int intSheet,
                        int intSheetCopy,
                        int row,
                        int rowCopy,
                        int colStart,
                        int colEnd) {
        Sheet sheetCopy = wb.getSheetAt(intSheetCopy);
        Sheet sheet = wb.getSheetAt(intSheet);
        Row rowToCopy = sheetCopy.getRow(rowCopy);
        for (int i = colStart; i <= colEnd; i++) {
            Cell cellToCopy = rowToCopy.getCell(i);
            Cell newCell = createCell(sheet, cellToCopy, row, i);
            if (cellToCopy.getCellType() != 3) {
                Object obj = null;
                if (cellToCopy.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    obj = Integer.valueOf((int) cellToCopy.getNumericCellValue());
                } else if (cellToCopy.getCellType() == Cell.CELL_TYPE_STRING) {
                    obj = cellToCopy.getStringCellValue();
                }
                newCell = setObject(newCell, obj);
            }
        }
    }

    public void copyRow(Sheet sheet,
                        Sheet sheetCopy,
                        int row,
                        int rowCopy,
                        int colStart,
                        int colEnd) {
        Row rowToCopy = sheetCopy.getRow(rowCopy);
        for (int i = colStart; i <= colEnd; i++) {
            Cell cellToCopy = rowToCopy.getCell((i));
            Cell newCell = createCell(sheet, cellToCopy, row, i);
            if (cellToCopy.getCellType() != 3) {
                Object obj = null;
                if (cellToCopy.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    obj = Integer.valueOf((int) cellToCopy.getNumericCellValue());
                } else if (cellToCopy.getCellType() == Cell.CELL_TYPE_STRING) {
                    obj = cellToCopy.getStringCellValue();
                }
                newCell = setObject(newCell, obj);
            }
        }
    }

    public void copyRowSpec(int intSheet,
                            int intSheetCopy,
                            int row,
                            int rowCopy,
                            int colStartCopy,
                            int colEndCopy,
                            int colStartDest)
            throws Exception {
        Sheet sheetCopy = wb.getSheetAt(intSheetCopy);
        Sheet sheet = wb.getSheetAt(intSheet);
        Row rowToCopy = sheetCopy.getRow(rowCopy);
        for (int i = colStartCopy; i <= colEndCopy; i++) {
            int j = 0;
            Cell cellToCopy = rowToCopy.getCell(i);

            Cell newCell = createCell(sheet, cellToCopy, row, colStartDest + j);
            if (cellToCopy.getCellType() != 3) {
                Object obj = null;
                if (cellToCopy.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    obj = Integer.valueOf((int) cellToCopy.getNumericCellValue());
                } else if (cellToCopy.getCellType() == Cell.CELL_TYPE_STRING) {
                    obj = cellToCopy.getStringCellValue();
                }
                newCell = setObject(newCell, obj);
            }
            j++;
        }
    }

    public void copyFormat(int sheetNum,
                           int startRow,
                           int startCol,
                           int sheetCopyNum,
                           int rowCopy,
                           int startColCopy,
                           int rowColumn,
                           int numColumn,
                           boolean copyValue) {
        Sheet sheet = wb.getSheetAt(sheetNum);
        Sheet sheetCopy = wb.getSheetAt(sheetCopyNum);
        copyFormat(sheet,
                   startRow,
                   startCol,
                   sheetCopy,
                   rowCopy,
                   startColCopy,
                   rowColumn,
                   numColumn,
                   copyValue);
    }

    public void copyFormat(Sheet sheet,
                           int startRow,
                           int startCol,
                           Sheet sheetCopy,
                           int rowCopy,
                           int startColCopy,
                           int rowColumn,
                           int numColumn,
                           boolean copyValue) {
        Row rowToCopy = null;
        for (int j = 0; j < rowColumn; j++) {
            rowToCopy = sheetCopy.getRow(rowCopy + j);
            if (rowToCopy == null) {
                continue;
            }
            for (int i = 0; i < numColumn; i++) {
                Cell cellToCopy = rowToCopy.getCell((startColCopy + i));
                Cell newCell = createCell(sheet,
                                          cellToCopy,
                                          startRow + j,
                                          (startCol + i));

                if (copyValue) {
                    copyValue(newCell, cellToCopy);
                }
            }
        }
    }

    private void copyValue(Cell cell, Cell cellToCopy) {

        if (cellToCopy != null) {
            switch (cellToCopy.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    cell.setCellValue(cellToCopy.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cell.setCellValue(cellToCopy.getDateCellValue());
                    } else {
                        cell.setCellValue(cellToCopy.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cell.setCellValue(cell.getNumericCellValue());
            }
        }
    }

    /**
     * method to Shift row / insert row by move the rowFrom until rowTo to the
     * bottom by size of rowSize.
     * <p/>
     * @param sheet
     * @param rowFrom
     * @param rowTo
     * @param rowSize
     */
    public void shiftRow(int sheet,
                         int rowFrom,
                         int rowTo,
                         int rowSize) {
        wb.getSheetAt(sheet).shiftRows(rowFrom, rowTo, rowSize);
    }

    /**
     * Method used to transfer Input Stream into Output Stream.
     * used to write excel file To HTTPResponse.
     * <p/>
     * @param is
     * @param os
     * <p/>
     * @throws IOException
     */
    public static void transfer(InputStream is, OutputStream os)
            throws IOException {
        byte buffer[] = new byte[BUFFER_SIZE];
        int l = 0;
        while ((l = is.read(buffer)) > -1) {
            if (l > 0) {
                os.write(buffer, 0, l);
            }
        }
        os.flush();
        buffer = null;
    }

    public boolean isExistFile(String filePath) {
        boolean result;
        File file = new File(filePath);
        if (file.isFile()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public short calculateTheCellWidth(String columnValue) {

        short shWidth = 0;
        if (columnValue != null) {
            shWidth = (short) ((338) * (columnValue.length() >= 32 ? 32 : columnValue.length()));
        }
        return shWidth;
    }

    /**
     * Method to set style into row and column.
     * <p/>
     * @param intSheet
     * @param rowStart
     * @param rowEnd
     * @param colStart
     * @param colEnd
     * @param style
     */
    public void setStyleRow(int intSheet,
                            int rowStart,
                            int rowEnd,
                            int colStart,
                            int colEnd,
                            CellStyle style) {
        Sheet sheet = wb.getSheetAt(intSheet);
        setStyleRow(sheet,
                    rowStart,
                    rowEnd,
                    colStart,
                    colEnd,
                    style);
    }

    /**
     * Method to set style into row and column.
     * <p/>
     * @param intSheet
     * @param rowStart
     * @param rowEnd
     * @param colStart
     * @param colEnd
     * @param style
     */
    public void setStyleRow(Sheet sheet,
                            int rowStart,
                            int rowEnd,
                            int colStart,
                            int colEnd,
                            CellStyle style) {
        Cell cell;
        Row row;
        if (style != null) {
            for (int i = rowStart; i <= rowEnd; i++) {
                for (int j = colStart; j <= colEnd; j++) {
                    row = sheet.getRow(i);
                    if (row == null) {
                        row = sheet.createRow(i);
                    }
                    cell = row.getCell(j);
                    if (cell == null) {
                        cell = row.createCell(j);
                    }
                    cell.setCellStyle(style);
                }
            }
        }
    }

    public void copyFormatWithMerge(Sheet sheet,
                                    int startRow,
                                    int startCol,
                                    Sheet sheetCopy,
                                    int rowCopy,
                                    int startColCopy,
                                    int rowColumn,
                                    int numColumn,
                                    boolean copyValue) {
        copyFormat(sheet,
                   startRow,
                   startCol,
                   sheetCopy,
                   rowCopy,
                   startColCopy,
                   rowColumn,
                   numColumn,
                   copyValue);
        if (rowColumn != 0) {
            rowColumn = rowColumn - 1;
        }
        if (numColumn != 0) {
            numColumn = numColumn - 1;
        }
        copyMerge(sheet,
                  startRow,
                  startCol,
                  sheetCopy,
                  rowCopy,
                  startColCopy,
                  rowColumn,
                  numColumn);
    }

    public void copyMerge(Sheet sheet,
                          int startRow,
                          int startCol,
                          Sheet sheetCopy,
                          int rowCopy,
                          int startColCopy,
                          int rowColumn,
                          int numColumn) {
        int regionSize = sheetCopy.getNumMergedRegions();
        CellRangeAddress[] regions = null;
        if (regionSize > 0) {
            regions = new CellRangeAddress[regionSize];
            for (int i = 0; i < regions.length; i++) {
                regions[i] = sheet.getMergedRegion(i);
            }
            int rowTo = rowCopy + rowColumn;
            int colTo = startColCopy + numColumn;
            for (int j = 0; j < regions.length; j++) {
                if (rowCopy <= regions[j].getFirstRow() && regions[j].getFirstRow() <= rowTo &&
                        startColCopy <= regions[j].getFirstColumn() && regions[j].getFirstColumn() <= colTo) {
                    int firstRow = (regions[j].getFirstRow() - rowCopy) + startRow;
                    int lastRow = (regions[j].getLastRow() - rowCopy) + startRow;
                    int firstCol = (regions[j].getFirstColumn() - startColCopy) + startCol;
                    int lastCol = (regions[j].getLastColumn() - startColCopy) + startCol;
                    CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
                    sheet.addMergedRegion(region);
                }
            }
        }
    }

    public void mergeCell(Sheet sheet,
                          CellRangeAddress region,
                          Integer firstRow,
                          Integer lastRow,
                          Integer firstCol,
                          Integer lastCol) {
        boolean isMerge = false;
        if (region == null) {
            if (firstRow != null && lastRow != null 
            		&& firstCol != null && lastCol != null) {
                region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
                isMerge = true;
            }
        } else {
            if (firstRow != null && lastRow != null 
            		&& firstCol != null && lastCol != null) {
                region.setFirstRow(firstRow);
                region.setLastRow(lastRow);
                region.setFirstColumn(firstCol);
                region.setLastColumn(lastCol);
                isMerge = true;
            }
        }
        if (isMerge) {
            sheet.addMergedRegion(region);
        }
    }

    public void removeRow(Sheet sheet,
                          int rowStart,
                          int rowEnd) {
        Row removeRow = null;
        for (int i = rowStart; i <= rowEnd; i++) {
            removeRow = sheet.getRow(i);
            if (removeRow != null) {
                sheet.removeRow(removeRow);
            }
        }
    }
    
    public String writeFile(Workbook report, 
	            			String fileName, 
	            			String dirPath) throws CommonException {
		String reportFilePath = this.writeFile(report, 
											   fileName, 
											   dirPath, 
											   null);
		return reportFilePath;
	}
	
	/**
	* Method to create excel file to destination.
	* @param report   workbook of data
	* @param fileName report file name
	* @param dirPath  path for create report file
	* @param date     notnull : system will append date value to end of report file name
	*                 null : system will skip step for append date value
	* @throws Exception
	*/
	public String writeFile(Workbook report,
							String fileName,
							String dirPath,
							String date) 
						throws CommonException {
		StringBuilder reportFileName = new StringBuilder();
		String templateExtention = NFEBatchConstants.XLS_REPORT_EXTENTION;
		if(report instanceof XSSFWorkbook){
			templateExtention = NFEBatchConstants.XLSX_REPORT_EXTENTION;
		}
		
		if (date == null || date.isEmpty()) {
			reportFileName.append(fileName);
			reportFileName.append(templateExtention);
		} else {
			reportFileName.append(fileName);
			reportFileName.append("_");
			reportFileName.append(date);
			reportFileName.append(templateExtention);
		}
		
		String reportFilePath = new File(dirPath, 
				reportFileName.toString()).getAbsolutePath();
		 
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(reportFilePath);
			report.write(fos);
        } catch (FileNotFoundException fne) {
        	LOG.fatal(fne.getMessage(), fne);
        	throw ErrorUtil.generateError("MSTD0038AERR", fileName);
		} catch (IOException ioe) {
        	LOG.fatal(ioe.getMessage(), ioe);
        	throw ErrorUtil.generateError("MSTD0038AERR", fileName); 
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//Do Nothing
				}				
			}
		}
		
		return reportFilePath;
	}
}