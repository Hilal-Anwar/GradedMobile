package org.helal.gradedclasses;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.helal.gradedclasses.seating_plan.SeatingPlanInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class TimeTableLoader {
    private final LinkedList<SeatingPlanInfo> timeTableLinkedList = new LinkedList<>();

    public LinkedList<SeatingPlanInfo> getTimeTableLinkedList() {
        return timeTableLinkedList;
    }

    public TimeTableLoader(File f) {

        FileInputStream file;

        try {
            file = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        XSSFSheet sheet = workbook.getSheetAt(getIndex(new Date()));

        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                SeatingPlanInfo stu = new SeatingPlanInfo(row.getCell(0).getLocalDateTimeCellValue().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a")),
                        row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(),
                        "" + row.getCell(3));
                timeTableLinkedList.add(stu);
            }
        }
    }

    private int getIndex(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_WEEK);
    }
}
