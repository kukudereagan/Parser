package com.ligaa;

import static org.junit.Assert.assertTrue;

import com.ligaa.parser.datax.utils.DataXParseUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testDataXjson() throws IOException {
        DataXParseUtil.parseDataxJson("{\n" +
                "\t\"job\": {\n" +
                "\t\t\"setting\": {\n" +
                "\t\t\t\"speed\": {\n" +
                "\t\t\t\t\"channel\": 3\n" +
                "\t\t\t},\n" +
                "\t\t\t\"errorLimit\": {\n" +
                "\t\t\t\t\"record\": 0,\n" +
                "\t\t\t\t\"percentage\": 0.02\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"content\": [{\n" +
                "\t\t\t\t\"reader\": {\n" +
                "\t\t\t\t\t\"name\": \"mysqlreader\",\n" +
                "\t\t\t\t\t\"parameter\": {\n" +
                "\t\t\t\t\t\t\"username\": \"root\",\n" +
                "\t\t\t\t\t\t\"password\": \"root\",\n" +
                "\t\t\t\t\t\t\"column\": [\n" +
                "\t\t\t\t\t\t\t\"id\",\n" +
                "\t\t\t\t\t\t\t\"name\"\n" +
                "\t\t\t\t\t\t],\n" +
                "\t\t\t\t\t\t\"splitPk\": \"db_id\",\n" +
                "\t\t\t\t\t\t\"connection\": [{\n" +
                "\t\t\t\t\t\t\t\t\"table\": [\n" +
                "\t\t\t\t\t\t\t\t\t\"table\"\n" +
                "\t\t\t\t\t\t\t\t],\n" +
                "\t\t\t\t\t\t\t\t\"jdbcUrl\": [\n" +
                "\t\t\t\t\t\t\t\t\t\"jdbc:mysql://127.0.0.1:3306/database\"\n" +
                "\t\t\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"writer\": {\n" +
                "\t\t\t\t\t\"name\": \"mysqlwriter\",\n" +
                "\t\t\t\t\t\"parameter\": {\n" +
                "\t\t\t\t\t\t\"writeMode\": \"insert\",\n" +
                "\t\t\t\t\t\t\"username\": \"root\",\n" +
                "\t\t\t\t\t\t\"password\": \"root\",\n" +
                "\t\t\t\t\t\t\"column\": [\n" +
                "\t\t\t\t\t\t\t\"id\",\n" +
                "\t\t\t\t\t\t\t\"name\"\n" +
                "\t\t\t\t\t\t],\n" +
                "\t\t\t\t\t\t\"session\": [\n" +
                "\t\t\t\t\t\t\t\"set session sql_mode='ANSI'\"\n" +
                "\t\t\t\t\t\t],\n" +
                "\t\t\t\t\t\t\"preSql\": [\n" +
                "\t\t\t\t\t\t\t\"delete from test\"\n" +
                "\t\t\t\t\t\t],\n" +
                "\t\t\t\t\t\t\"connection\": [{\n" +
                "\t\t\t\t\t\t\t\t\"jdbcUrl\": \"jdbc:mysql://127.0.0.1:3306/datax?useUnicode=true&characterEncoding=gbk\",\n" +
                "\t\t\t\t\t\t\t\t\"table\": [\n" +
                "\t\t\t\t\t\t\t\t\t\"test\"\n" +
                "\t\t\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}");
    }
    @Test
    public void testDataXjsonHasquerysql() throws IOException {
        DataXParseUtil.parseDataxJson("{\n" +
                "\t\"job\": {\n" +
                "\t\t\"setting\": {\n" +
                "\t\t\t\"speed\": {\n" +
                "\t\t\t\t\"channel\": 3\n" +
                "\t\t\t},\n" +
                "\t\t\t\"errorLimit\": {\n" +
                "\t\t\t\t\"record\": 0,\n" +
                "\t\t\t\t\"percentage\": 0.02\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"content\": [{\n" +
                "\t\t\t\t\"reader\": {\n" +
                "\t\t\t\t\t\"name\": \"mysqlreader\",\n" +
                "\t\t\t\t\t\"parameter\": {\n" +
                "\t\t\t\t\t\t\"username\": \"root\",\n" +
                "\t\t\t\t\t\t\"password\": \"root\",\n" +
                "\t\t\t\t\t\t\"column\": [\n" +
                "\t\t\t\t\t\t\t\"id\",\n" +
                "\t\t\t\t\t\t\t\"name\"\n" +
                "\t\t\t\t\t\t],\n" +
                "\t\t\t\t\t\t\"splitPk\": \"db_id\",\n" +
                "\t\t\t\t\t\t\"connection\": [{\n" +
                "\t\t\t\t\t\t\t\t\"querySql\": [\n" +
                "                                    \"select db_id,on_line_flag from db_info where db_id < 10;\"\n" +
                "                                ],\n" +
                "\t\t\t\t\t\t\t\t\"jdbcUrl\": [\n" +
                "\t\t\t\t\t\t\t\t\t\"jdbc:mysql://127.0.0.1:3306/database\"\n" +
                "\t\t\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"writer\": {\n" +
                "\t\t\t\t\t\"name\": \"mysqlwriter\",\n" +
                "\t\t\t\t\t\"parameter\": {\n" +
                "\t\t\t\t\t\t\"writeMode\": \"insert\",\n" +
                "\t\t\t\t\t\t\"username\": \"root\",\n" +
                "\t\t\t\t\t\t\"password\": \"root\",\n" +
                "\t\t\t\t\t\t\"column\": [\n" +
                "\t\t\t\t\t\t\t\"id\",\n" +
                "\t\t\t\t\t\t\t\"name\"\n" +
                "\t\t\t\t\t\t],\n" +
                "\t\t\t\t\t\t\"session\": [\n" +
                "\t\t\t\t\t\t\t\"set session sql_mode='ANSI'\"\n" +
                "\t\t\t\t\t\t],\n" +
                "\t\t\t\t\t\t\"preSql\": [\n" +
                "\t\t\t\t\t\t\t\"delete from test\"\n" +
                "\t\t\t\t\t\t],\n" +
                "\t\t\t\t\t\t\"connection\": [{\n" +
                "\t\t\t\t\t\t\t\t\"jdbcUrl\": \"jdbc:mysql://127.0.0.1:3306/datax?useUnicode=true&characterEncoding=gbk\",\n" +
                "\t\t\t\t\t\t\t\t\"table\": [\n" +
                "\t\t\t\t\t\t\t\t\t\"test\"\n" +
                "\t\t\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t\t]\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}");
    }
}
