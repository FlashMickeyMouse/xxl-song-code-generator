package com.xxl.codegenerator.core;

import com.xxl.codegenerator.core.model.ClassInfo;
import com.xxl.codegenerator.core.model.FieldInfo;
import com.xxl.codegenerator.core.util.TableParseUtil;

import java.io.IOException;
import java.util.List;

/**
 * code generate tool
 *
 * @author xuxueli 2018-04-25 16:29:58
 */
public class CodeGeneratorTool {

    /**
     * process Table Into ClassInfo
     *
     * @param tableSql
     * @return
     */
    public static ClassInfo processTableIntoClassInfo(String tableSql) throws IOException {
        return TableParseUtil.processTableIntoClassInfo(tableSql);
    }


    /**
     *
     * @param selectSql
     * @return
     * @throws IOException
     */
    public static List<FieldInfo> processSelectSqlIntoClassInfo(String selectSql) throws IOException {
        return TableParseUtil.processSelectSQLIntoClassInfo(selectSql);
    }
}