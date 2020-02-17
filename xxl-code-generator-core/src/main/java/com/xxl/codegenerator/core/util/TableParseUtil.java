package com.xxl.codegenerator.core.util;

import com.xxl.codegenerator.core.exception.CodeGenerateException;
import com.xxl.codegenerator.core.model.ClassInfo;
import com.xxl.codegenerator.core.model.FieldInfo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author xuxueli 2018-05-02 21:10:45
 */
public class TableParseUtil {

    /**
     * 解析建表SQL生成代码（model-dao-xml）
     *
     * @param tableSql
     * @return
     */
    public static ClassInfo processTableIntoClassInfo(String tableSql) throws IOException {
        if (tableSql == null || tableSql.trim().length() == 0) {
            throw new CodeGenerateException("Table structure can not be empty.");
        }
        tableSql = tableSql.trim();

        // table Name
        String tableName = null;
        if (tableSql.contains("TABLE") && tableSql.contains("(")) {
            tableName = tableSql.substring(tableSql.indexOf("TABLE") + 5, tableSql.indexOf("("));
        } else if (tableSql.contains("table") && tableSql.contains("(")) {
            tableName = tableSql.substring(tableSql.indexOf("table") + 5, tableSql.indexOf("("));
        } else {
            throw new CodeGenerateException("Table structure anomaly.");
        }

        if (tableName.contains("`")) {
            tableName = tableName.substring(tableName.indexOf("`") + 1, tableName.lastIndexOf("`"));
        }

        // class Name
        String className = StringUtils.upperCaseFirst(StringUtils.underlineToCamelCase(tableName));
        if (className.contains("_")) {
            className = className.replaceAll("_", "");
        }

        // class Comment
        String classComment = null;
        if (tableSql.contains("COMMENT=")) {
            String classCommentTmp = tableSql.substring(tableSql.lastIndexOf("COMMENT=") + 8).trim();
            if (classCommentTmp.contains("'") || classCommentTmp.indexOf("'") != classCommentTmp.lastIndexOf("'")) {
                classCommentTmp = classCommentTmp.substring(classCommentTmp.indexOf("'") + 1, classCommentTmp.lastIndexOf("'"));
            }
            if (classCommentTmp != null && classCommentTmp.trim().length() > 0) {
                classComment = classCommentTmp;
            }
        }

        // field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();

        String fieldListTmp = tableSql.substring(tableSql.indexOf("(") + 1, tableSql.lastIndexOf(")"));
        String[] fieldLineList = fieldListTmp.split(",");
        if (fieldLineList.length > 0) {
            for (String columnLine : fieldLineList) {
                columnLine = columnLine.trim();                                                // `userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                if (columnLine.startsWith("`")) {

                    // column Name
                    columnLine = columnLine.substring(1);                                    // userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                    String columnName = columnLine.substring(0, columnLine.indexOf("`"));    // userid

                    // field Name
                    String fieldName = StringUtils.lowerCaseFirst(StringUtils.underlineToCamelCase(columnName));
                    if (fieldName.contains("_")) {
                        fieldName = fieldName.replaceAll("_", "");
                    }

                    // field class
                    columnLine = columnLine.substring(columnLine.indexOf("`") + 1).trim();    // int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                    String fieldClass = Object.class.getSimpleName();
                    if (columnLine.startsWith("int") || columnLine.startsWith("tinyint") || columnLine.startsWith("smallint")) {
                        fieldClass = Integer.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("bigint")) {
                        fieldClass = Long.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("float")) {
                        fieldClass = Float.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("double")) {
                        fieldClass = Double.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("datetime") || columnLine.startsWith("timestamp")) {
                        fieldClass = Date.class.getSimpleName();
                    } else if (columnLine.startsWith("varchar") || columnLine.startsWith("text")) {
                        fieldClass = String.class.getSimpleName();
                    } else if (columnLine.startsWith("decimal")) {
                        fieldClass = BigDecimal.class.getSimpleName();
                    }

                    // field comment
                    String fieldComment = null;
                    if (columnLine.contains("COMMENT")) {
                        String commentTmp = fieldComment = columnLine.substring(columnLine.indexOf("COMMENT") + 7).trim();    // '用户ID',
                        if (commentTmp.contains("'") || commentTmp.indexOf("'") != commentTmp.lastIndexOf("'")) {
                            commentTmp = commentTmp.substring(commentTmp.indexOf("'") + 1, commentTmp.lastIndexOf("'"));
                        }
                        fieldComment = commentTmp;
                    }

                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfo.setColumnName(columnName);
                    fieldInfo.setFieldName(fieldName);
                    fieldInfo.setFieldClass(fieldClass);
                    fieldInfo.setFieldComment(fieldComment);

                    fieldList.add(fieldInfo);
                }
            }
        }

        if (fieldList.size() < 1) {
            throw new CodeGenerateException("Table structure anomaly.");
        }

        ClassInfo codeJavaInfo = new ClassInfo();
        codeJavaInfo.setTableName(tableName);
        codeJavaInfo.setClassName(className);
        codeJavaInfo.setClassComment(classComment);
        codeJavaInfo.setFieldList(fieldList);

        return codeJavaInfo;
    }

    //"columnName": "user_id",
    //			"fieldClass": "int",
    //			"fieldComment": "用户ID",
    //			"fieldName": "userId"
    public static List<FieldInfo> processSelectSQLIntoClassInfo(String selectSQL) {
        selectSQL  = selectSQL.toLowerCase();
        System.out.println(selectSQL.split("select")[1].split("from")[0].trim());
        String[] select_fields = selectSQL.split("select")[1].split("from")[0].trim().split(",");
        ArrayList<FieldInfo> fieldInfos = new ArrayList<>();
        for (int i = 0; i < select_fields.length; i++) {
            System.out.println(Arrays.toString(select_fields[i].split("\n")));
            String[] fullField = select_fields[i].split("\n");
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setFieldClass("String");
            if (fullField.length == 1) {
                String fieldSource = fullField[0];
                String[] asSplit = fieldSource.split(" as ");
                if(asSplit.length==1){
                    String columnName = asSplit[0].split("\\.")[1];
                    fieldInfo.setColumnName(columnName.replaceAll("`",""));
                }else if (asSplit.length==2){
                    String columnName = asSplit[1].split("\\.")[1];
                    fieldInfo.setColumnName(columnName.replaceAll("`",""));
                }

            }else if(fullField.length == 2){
                String fieldSource = fullField[1];
                String[] asSplit = fieldSource.split(" as ");
                if(asSplit.length==1){
                    String columnName = asSplit[0].split("\\.")[1];
                    fieldInfo.setColumnName(columnName.replaceAll("`",""));
                }else if (asSplit.length==2){
                    String columnName = asSplit[1];
                    fieldInfo.setColumnName(columnName.replaceAll("`",""));
                }
            }else if(fullField.length == 3){
                fieldInfo.setFieldComment(fullField[1].split("-- ")[1]);
                String fieldSource = fullField[2];
                String[] asSplit = fieldSource.split(" as ");
                if(asSplit.length==1){
                    String columnName = asSplit[0].split("\\.")[1];
                    fieldInfo.setColumnName(columnName.replaceAll("`",""));
                }else if (asSplit.length==2){
                    String columnName = asSplit[1];
                    fieldInfo.setColumnName(columnName.replaceAll("`",""));
                }
            }
            fieldInfo.setFieldName(StringUtils.underlineToCamelCase(fieldInfo.getColumnName()));
            fieldInfos.add(fieldInfo);
        }
        System.out.println(Arrays.toString(fieldInfos.toArray()));
        return fieldInfos;
    }

    public static void main(String[] args) {

        processSelectSQLIntoClassInfo("SELECT\n" +
                "\tbreed_place_breed_record.id,\n" +
                "\tpm_base_cow.id AS base_cow_id,\n" +
                "\tpm_cattle.id AS cattle_id,\n" +
                "\t-- 品种\n" +
                "\tpm_cattle.breed AS hehe,\n" +
                "\t-- 防疫耳标号\n" +
                "\tpm_cattle.fyebh,\n" +
                "\t-- 电子耳标号\n" +
                "\tpm_base_cow.dzebh,\n" +
                "\t-- 月龄\n" +
                "\tpm_cattle.months,\n" +
                "\t-- 已孕月龄\n" +
                "\tpm_base_cow.pregnant_months,\n" +
                "\tpm_cattle.owner_id,\n" +
                "\tpm_cattle.owner_type,\n" +
                "\tpm_cattle.owner_name,\n" +
                "\tbreed_place_breed_record.breed_state,\n" +
                "\tbreed_place_breed_record.place_id,\n" +
                "\thm_field.`name` AS place_name,\n" +
                "\tbreed_place_breed_record.begin_breed_time,\n" +
                "\tbreed_place_breed_record.breed_reason,\n" +
                "\tpm_cattle.height,\n" +
                "\tpm_cattle.dip_length,\n" +
                "\tpm_cattle.weight,\n" +
                "\tpm_cattle.`comment` \n" +
                "\t\n" +
                "FROM\n" +
                "\tbreed_place_breed_record\n" +
                "\tLEFT JOIN hm_field ON hm_field.id = breed_place_breed_record.place_id\n" +
                "\tLEFT JOIN `pm_base_cow` ON pm_base_cow.id = breed_place_breed_record.base_cow_id\n" +
                "\tLEFT JOIN pm_cattle ON pm_base_cow.cattle_id = pm_cattle.id\n" +
                "\tLEFT JOIN deal_bull_info ON pm_cattle.id = deal_bull_info.cattle_id\n" +
                "\tLEFT JOIN `hm_base`.sys_depart ON deal_bull_info.place_id = sys_depart.id");
    }
}
