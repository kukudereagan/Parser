package com.lig.parser.common.entity;

import java.util.ArrayList;
import java.util.List;

/**
     * [A/B/C  D  E]  TABLE[AA.*]  TBC[BB.*]
     *
     * 每一次 select * from  全部转化成
     * TABLE.FIELD  TAC[CC.B]  [TAB[TT.C],TAC[CC.B]]
     *
     *private column
     *     name：
     *     alis:
     *     expr:
     *     columns:[]
     *     sourceTable:[]
     *
     *
     *
     * 提取出  TAB[字段1,字段2，字段3，字段4]
     *
     */
public  class Column {
    private String name;//字面名称
    private String fName;//实字段名称
    private String alis;//别名
    private String expr;//表达式形式的
    private Integer rightIndexCount = null;
    private Column etlTargetColumn;//ETL目标字段
    private String databaseName;//库名称
    private List<Column> columns = new ArrayList<Column>();
    private List<String> sourceTable = new ArrayList<>();

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", fName='" + fName + '\'' +
                ", alis='" + alis + '\'' +
                ", expr='" + expr + '\'' +
                ", columns=" + columns +
                ", rightIndexCount=" + rightIndexCount +
                ", sourceTable=" + sourceTable +
                '}';
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getName() {
            return name;
        }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlis() {
        return alis;
    }

    public void setAlis(String alis) {
        this.alis = alis;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<String> getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(List<String> sourceTable) {
        this.sourceTable = sourceTable;
    }

    public Integer getRightIndexCount() {
        return rightIndexCount;
    }

    public void setRightIndexCount(Integer rightIndexCount) {
        this.rightIndexCount = rightIndexCount;
    }

    public Column getEtlTargetColumn() {
        return etlTargetColumn;
    }

    public void setEtlTargetColumn(Column etlTargetColumn) {
        this.etlTargetColumn = etlTargetColumn;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}