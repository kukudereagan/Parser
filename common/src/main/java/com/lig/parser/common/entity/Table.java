package com.lig.parser.common.entity;

import java.util.ArrayList;
import java.util.List;

public class Table {
        private String name;
        private String alis;
        private List<Column> columns = new ArrayList<>();
        private List<Column> outColumns = new ArrayList<>();

    public List<Column> getOutColumns() {
        return outColumns;
    }

    public void setOutColumns(List<Column> outColumns) {
        this.outColumns = outColumns;
    }

    @Override
        public String toString() {
            return "Table{" +
                    "name='" + name + '\'' +
                    ", alis='" + alis + '\'' +
                    ", columns=" + columns +
                    '}';
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

        public List<Column> getColumns() {
            return columns;
        }

        public void setColumns(List<Column> columns) {
            this.columns = columns;
        }
    }