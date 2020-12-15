package com.qmth.wuda.teaching.util;

import com.qmth.wuda.teaching.annotation.ExcelNotNull;
import com.qmth.wuda.teaching.annotation.ExcelNote;
import com.qmth.wuda.teaching.bean.excel.ExcelCallback;
import com.qmth.wuda.teaching.bean.excel.ExcelError;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.enums.ExceptionResultEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description: excel util
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/19
 */
public class ExcelUtil {
    private final static Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * excel读取
     *
     * @param inputStream
     * @param clazz
     * @param callback
     * @return
     * @throws NoSuchFieldException
     * @throws IOException
     */
    public static List<LinkedMultiValueMap<Integer, Object>> excelReader(InputStream inputStream, List<Class<?>> clazz, ExcelCallback callback) throws NoSuchFieldException, IOException {
        Object o = null;
        try {
            log.info("开始读取excel里的数据");
            long start = System.currentTimeMillis();
            //用流的方式先读取到你想要的excel的文件
            //获取整个excel
            XSSFWorkbook xb = new XSSFWorkbook(inputStream);
            int sheets = xb.getNumberOfSheets();
            List<LinkedMultiValueMap<Integer, Object>> finalOList = new ArrayList<>();
            List<LinkedMultiValueMap<Integer, String>> finalColumnNameList = new ArrayList<>();
            for (int y = 0; y < sheets; y++) {
                //获取第一个表单sheet
                XSSFSheet sheet = xb.getSheetAt(y);
                //获取最后一行
                int lastrow = sheet.getLastRowNum();
                //循环行数依次获取列数
                LinkedMultiValueMap<Integer, Object> oList = new LinkedMultiValueMap<>();
                LinkedMultiValueMap<Integer, String> columnNameList = new LinkedMultiValueMap<>();
                for (int i = 0; i < lastrow + 1; i++) {
                    //获取哪一行i
                    Row row = sheet.getRow(i);
                    if (Objects.nonNull(row)) {
                        //获取这一行的第一列
                        int firstcell = row.getFirstCellNum();//从第二行开始获取
                        //获取这一行的最后一列
                        int lastcell = row.getLastCellNum();
                        o = clazz.get(y).newInstance();
                        Field[] fields = o.getClass().getDeclaredFields();
                        boolean extend = fields[fields.length - 1].getName().contains(SystemConstant.EXTEND_COLUMN);
                        for (int j = firstcell; j < lastcell; j++) {
                            //获取第j列
                            Cell cell = row.getCell(j);
                            if (i == 0) {
                                columnNameList.add(y, String.valueOf(cell));
                            } else {
                                if (Objects.nonNull(cell)) {
                                    Object obj = convert(cell);
                                    if (extend) {
                                        if (j < fields.length - 1) {
                                            fields[j].setAccessible(true);
                                            fields[j].set(o, obj);
                                        } else {
                                            Field field = null;
                                            if (j == fields.length - 1) {
                                                field = fields[j];
                                            } else {
                                                field = fields[fields.length - 1];
                                            }
                                            field.setAccessible(true);
                                            Map map = (Map) field.get(o);
                                            if (Objects.isNull(map)) {
                                                map = new LinkedHashMap<>();
                                            }
                                            map.put(columnNameList.get(y).get(j), obj);
                                            field.set(o, map);
                                        }
                                    } else {
                                        fields[j].setAccessible(true);
                                        fields[j].set(o, obj);
                                    }
                                }
                            }
                        }
                        if (i > 0) {
                            oList.add(y, o);
                        }
                    }
                }
                if (oList.size() == 0 && finalOList.size() == 0) {
                    throw new BusinessException(ExceptionResultEnum.EXCEL_NO);
                }
                if (oList.size() > 0) {
                    finalOList.add(oList);
                    finalColumnNameList.add(columnNameList);
                }
                log.info("读取了{}条数据", oList.get(y).size());
            }
            long end = System.currentTimeMillis();
            log.info("读取excel里的数据结束,============耗时============:{}秒", (end - start) / 1000);
            return callback.callback(finalOList, finalColumnNameList);
        } catch (Exception e) {
            log.error("excel读取报错", e);
            if (e instanceof IllegalArgumentException) {
                String errorColumn = e.getMessage();
                if (errorColumn.indexOf("Can not set java.lang.String field") != -1 && errorColumn.indexOf("to java.lang.Long") != -1) {
                    String column = errorColumn.substring(errorColumn.indexOf("Can not set java.lang.String field") + 1, errorColumn.indexOf("to java.lang.Long"));
                    column = column.substring(column.lastIndexOf(".") + 1, column.length());
                    Field field = o.getClass().getDeclaredField(column.trim());
                    ExcelNote note = field.getAnnotation(ExcelNote.class);
                    throw new BusinessException("excel列[" + note.value() + "]为非文本格式");
                } else {
                    throw new BusinessException(e.getMessage());
                }
            } else {
                throw new BusinessException(e.getMessage());
            }
        } finally {
            if (Objects.nonNull(inputStream)) {
                inputStream.close();
            }
        }
    }

    /**
     * 类型转换
     *
     * @param cell
     * @return
     */
    private static Object convert(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                return cell.getStringCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case ERROR:
                return cell.getErrorCellValue();
        }
        return null;
    }

    /**
     * 校验属性
     *
     * @param obj
     * @param index
     * @param sheetIndex
     * @return
     * @throws IllegalAccessException
     */
    public static List<ExcelError> checkExcelField(Object obj, int index, int sheetIndex) throws IllegalAccessException {
        List<ExcelError> excelErrorList = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Annotation annotation = field.getAnnotation(ExcelNotNull.class);
            ExcelNote note = field.getAnnotation(ExcelNote.class);
            if (Objects.isNull(field.get(obj)) && Objects.nonNull(annotation)) {
                excelErrorList.add(new ExcelError(index + 1, "excel第" + (sheetIndex + 1) + "个sheet第" + (index + 1) + "行[" + note.value() + "]为空"));
            }
        }
        return excelErrorList;
    }
}