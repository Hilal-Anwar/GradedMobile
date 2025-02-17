package org.helal.gradedclasses;


import android.os.Build;

import androidx.annotation.RequiresApi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.helal.gradedclasses.leaderboard.Student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;


public class StudentDataLoader {
    private File filepath;
    private final LinkedHashMap<String, Student> studentTreeMap = new LinkedHashMap<>(20);

    public LinkedHashMap<String, Student> getStudentTreeMap() {
        return studentTreeMap;
    }


    public List<Student> getStudentStream() {
        List<Student> list = new ArrayList<>();
        for (Student student : studentTreeMap.values()) {
            list.add(student);
        }
        list.sort(Comparator.comparing(Student::points, Comparator.reverseOrder()));
        System.out.println(list);
        return list;
    }

    public StudentDataLoader(File filepath) {
        this.filepath = filepath;
        readFile();
    }

    String getAsRequired(Cell c) {
        try {
            int x = (int) c.getNumericCellValue();
            return "" + x;
        } catch (IllegalStateException e) {
            return c.getStringCellValue();
        }
    }

    private void readFile() {
        FileInputStream _file;
        try {
            _file = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(_file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        XSSFSheet sheet = workbook.getSheetAt(0);
        int k=0;

        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++)
        {
            Row row = sheet.getRow(rowNum);
            if (row != null)
            {
                Student stu = new Student(row.getCell(0).getStringCellValue(),
                        getAsRequired(row.getCell(1)), getAsRequired(row.getCell(2)),
                        row.getCell(row.getLastCellNum() - 1).getNumericCellValue());
                studentTreeMap.put(row.getCell(0).getStringCellValue(), stu);
                k++;
            }
        }
    }

}
