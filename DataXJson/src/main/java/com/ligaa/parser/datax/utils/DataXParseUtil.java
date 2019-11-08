package com.ligaa.parser.datax.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lig.parser.common.entity.Column;
import com.lig.parser.common.entity.Table;
import com.ligaa.parser.mysql.utils.ParseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataXParseUtil {
    public static List<Table> parseDataxJson(String json) throws IOException {
        //解析json
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject readerConnection = jsonObject.getJSONObject("job")
                .getJSONArray("content").getJSONObject(0)
                .getJSONObject("reader").getJSONObject("parameter")
                .getJSONArray("connection").getJSONObject(0);
        JSONObject writerConnection = jsonObject.getJSONObject("job")
                .getJSONArray("content").getJSONObject(0)
                .getJSONObject("writer").getJSONObject("parameter")
                .getJSONArray("connection").getJSONObject(0);
        JSONObject readerParameter = jsonObject.getJSONObject("job")
                .getJSONArray("content").getJSONObject(0)
                .getJSONObject("reader").getJSONObject("parameter");
        JSONObject writerParameter = jsonObject.getJSONObject("job")
                .getJSONArray("content").getJSONObject(0)
                .getJSONObject("writer").getJSONObject("parameter");
        JSONArray querySql = readerConnection.getJSONArray("querySql");
        JSONArray src = readerParameter.getJSONArray("column");
        JSONArray tar = writerParameter.getJSONArray("column");
        List<String> columns = tar.toJavaList(String.class);
        List<String> tarTable = writerConnection.getJSONArray("table").toJavaList(String.class);

        //如果没有配置sql
        if(querySql==null||querySql.isEmpty()){
            if(src==null||src.isEmpty()) return new ArrayList<Table>();
        }else{//如果配置了sql
            String sql = querySql.getString(0);
            //解析Sql
            List<Table> srcTable = ParseUtil.parseSelect(sql);
            //如果是配置的列
            for(Table st:srcTable){
                List<Column> columnList = null;
                if(st.getColumns()!=null&&!(columnList = st.getColumns()).isEmpty()){
                    for(Column c:columnList){
                        String cloumnName = columns.get(columns.size()-1 - c.getRightIndexCount());
                        Column tarCol = new Column();
                        tarCol.setfName(cloumnName);
                        tarCol.setSourceTable(tarTable);
                        tarCol.setDatabaseName("");

                        c.setEtlTargetColumn(tarCol);
                    }
                }
            }
            System.out.println(JSONObject.toJSONString(srcTable));
            return srcTable;
        }
        //如果是column到column抽取，将column对应上即可
        List<String> srcTable = readerConnection.getJSONArray("table").toJavaList(String.class);
        List<String> srcColumns = src.toJavaList(String.class);
        Table table = new Table();
        table.setName("_TEMP_");
        for(int i =0;i<srcColumns.size();i++){
            Column srcCol = new Column();
            srcCol.setName(srcColumns.get(i));
            srcCol.setSourceTable(srcTable);
            srcCol.setDatabaseName("");

            Column tarCol = new Column();
            tarCol.setName(columns.get(i));
            tarCol.setSourceTable(tarTable);
            tarCol.setDatabaseName("");

            srcCol.setEtlTargetColumn(tarCol);
            table.getColumns().add(srcCol);
        }

        List<Table> tables = new ArrayList<Table>();
        tables.add(table);
        System.out.println(JSONObject.toJSONString(tables));
        return tables;
    }

}
