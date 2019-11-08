package com.ligaa.parser.mysql.listener;

import com.lig.parser.common.entity.Column;
import com.lig.parser.common.entity.Table;
import com.ligaa.parser.mysql.parser.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 深度优先后序遍历树，每一个查询子查询返回 columns;
 * 将columns压栈 ，父查询中再弹栈
 *Table.字段 。。。Table.字段 。。。Table.*
 */
public class MySqlParserBaseListenerAdapter extends MySqlParserBaseListener {
    //放每一个查询语句产生的column
    //private Stack<List<Column>> stackColumn = new Stack<List<Column>>();
    //每一个查询生成的表
    private Stack<List<Table>> stackTables = new Stack<List<Table>>();
    //聚合函数上下文
    private Stack<Column> functionContextHolderStack = new Stack<Column>();
    //数学表达式
    private Stack<Column> expertionContextHolderStack = new Stack<Column>();
    //记录列编号 从0开始n 从右向左
    private Stack<Integer> countContextHolderStack = new Stack<Integer>();

    //放每一个查询的直接子查询残生的表的集合
    private List<Table> selectTables = null;

    public List<Table> getSelectTables() {
        return selectTables;
    }

    private void initStack(){
        List<Table> tables = new ArrayList<Table>();
        List<Table> subQueryTables = new ArrayList<Table>();
        List<Column> columns = new ArrayList<Column>();
        stackTables.push(tables);
        functionContextHolderStack.push(null);
        expertionContextHolderStack.push(null);
        countContextHolderStack.push(0);
    }
    private void popStack(){
        selectTables = stackTables.pop();
        expertionContextHolderStack.pop();
        functionContextHolderStack.pop();
        countContextHolderStack.pop();
    }
    //进入时将上下文放入栈
    @Override
    public void enterSimpleSelect(MySqlParser.SimpleSelectContext ctx) {
        initStack();
    }

    /**
     * 简单查询以及查询体中的子查询
     * @param ctx
     */
    @Override
    public void exitSimpleSelect(MySqlParser.SimpleSelectContext ctx) {
        //出栈
        if (stackTables.size()<1) return;
        for(Table t:stackTables.peek()){
            if(t.getName().startsWith("_TEMPORARY_")&&!(t.getAlis()==null||"".equalsIgnoreCase(t.getAlis().trim()))){
                List<Column> columns = t.getOutColumns();
                t.setColumns(columns);
                t.setOutColumns(new ArrayList<Column>());
            }
        }
        popStack();
        //出完栈之后将子查询的结果加入父查询中
        //合并查询结果
        if(!stackTables.isEmpty()){
            //如果不在函数当中，直接加入,如果在函数当中
            Column column = null;
            if(expertionContextHolderStack.isEmpty()||expertionContextHolderStack.peek()==null){
                for(Table t:selectTables){
                    if((t.getAlis()==null||"".equalsIgnoreCase(t.getAlis()))&&t.getName().startsWith("_TEMPORARY_"))
                         stackTables.peek().add(t);
                }
            }else if((column = expertionContextHolderStack.peek())!=null){
                for(Table t:selectTables){
                    if((t.getAlis()==null||"".equalsIgnoreCase(t.getAlis()))&&t.getName().startsWith("_TEMPORARY_"))
                        if(t.getColumns().size()>0)
                            column.getColumns().addAll(t.getColumns());
                }
            }
        }
    }
    @Override
    public void enterQuerySpecificationNointo(MySqlParser.QuerySpecificationNointoContext ctx) {
        initStack();
    }
    @Override
    public void exitQuerySpecificationNointo(MySqlParser.QuerySpecificationNointoContext ctx) {
        //出栈
        if (stackTables.size()<1) return;
        for(Table t:stackTables.peek()){
            if(t.getName().startsWith("_TEMPORARY_")&&!(t.getAlis()==null||"".equalsIgnoreCase(t.getAlis().trim()))){
                List<Column> columns = t.getOutColumns();
                t.setColumns(columns);
                t.setOutColumns(new ArrayList<Column>());
            }
        }
        popStack();
        //出完栈之后将子查询的结果加入父查询中
        //合并查询结果
        if(!stackTables.isEmpty()){
            //如果不在函数当中，直接加入,如果在函数当中
            Column column = null;
            if(expertionContextHolderStack.isEmpty()||expertionContextHolderStack.peek()==null){
                for(Table t:selectTables){
                    if((t.getAlis()==null||"".equalsIgnoreCase(t.getAlis()))&&t.getName().startsWith("_TEMPORARY_"))
                        stackTables.peek().add(t);
                }
            }else if((column = expertionContextHolderStack.peek())!=null){
                for(Table t:selectTables){
                    if((t.getAlis()==null||"".equalsIgnoreCase(t.getAlis()))&&t.getName().startsWith("_TEMPORARY_"))
                        if(t.getColumns().size()>0)
                            column.getColumns().addAll(t.getColumns());
                }
            }
        }
    }
    //进入selectelements时压栈,普通字段 ，函数（子查询），表达式
    @Override
    public void enterSelectElements(MySqlParser.SelectElementsContext ctx) {

    }

    /**
     * from 中的子查询
     * 将子查询分析结果压栈
     * @param ctx
     */
    @Override
    public void exitParenthesisSelect(MySqlParser.ParenthesisSelectContext ctx) {
        //将临时表的output换到columns
        if (stackTables.size()<1) return;
        for(Table t:stackTables.peek()){
            if(t.getName().startsWith("_TEMPORARY_")){
                List<Column> columns = t.getOutColumns();
                t.setColumns(columns);
                t.setOutColumns(new ArrayList<Column>());
            }
        }
        popStack();
    }
    //进入子查询将参数清空
    @Override
    public void enterParenthesisSelect(MySqlParser.ParenthesisSelectContext ctx) {
        initStack();
    }

    /**
     * 处理普通select字段，直接字段名称的
     * 字段来源  有可能是from表的字段，有可能是子查询返回的字段columns
     * @param ctx
     */
    public void enterSelectColumnElement(MySqlParser.SelectColumnElementContext ctx){
        List<Table> tables = stackTables.peek();
        Column column = new Column();
        column.setAlis((ctx.uid()==null?null:ctx.uid().getText()));  //别名
        column.setName(ctx.fullColumnName().getText());  //字面名称  X.XXX  XXX （表别名+字段名）
        column.setfName(ctx.fullColumnName().getText());  //对应字段名
        Integer count = countContextHolderStack.pop();
        column.setRightIndexCount(count);
        countContextHolderStack.push(++count);
        boolean hasTabAlis = column.getName().indexOf(".")>0;
        boolean isFinded = false;
        String tabAlis = hasTabAlis?column.getName().substring(0,column.getName().indexOf(".")):"";
        String colName = hasTabAlis?column.getName().substring(1+column.getName().indexOf(".")):column.getName();
        for(Table t:tables){
            //如果有表别名并且表别名与table匹配
            if(hasTabAlis&&(tabAlis.equalsIgnoreCase(t.getAlis())||tabAlis.equalsIgnoreCase(t.getName()))){
                //如果是子查询表
                if(t.getName().startsWith("_TEMPORARY_")){
                    List<Column> columns = t.getColumns();
                    for(Column c:columns){
                        if(colName.equalsIgnoreCase(c.getAlis())||(colName.equalsIgnoreCase(c.getName())&&c.getAlis()==null)){
                            /*修改字段别名为及时更新的问题 将原来column深拷贝，更改别名在加入*/
                            Column nCol = new Column();
                            nCol.setAlis(column.getAlis()==null?colName:column.getAlis());
                            nCol.setColumns(c.getColumns());
                            nCol.setExpr(c.getExpr());
                            nCol.setfName(c.getfName());
                            nCol.setName(c.getName());
                            nCol.setSourceTable(c.getSourceTable());
                            nCol.setRightIndexCount(column.getRightIndexCount());

                            t.getOutColumns().add(nCol);//此处存在问题，别名没有更新。
                            isFinded=true;
                            break;
                        }
                    }
                    if (isFinded) break;
                }
                column.setfName(colName);
                column.getSourceTable().add(t.getName());
                t.getColumns().add(column);
                isFinded=true;
                break;
            }else if(!hasTabAlis){
                if(t.getName().startsWith("_TEMPORARY_")){
                    List<Column> columns = t.getColumns();
                    for(Column c:columns){
                        if(colName.equalsIgnoreCase(c.getAlis())||(colName.equalsIgnoreCase(c.getName())&&c.getAlis()==null)){
                            /*修改字段别名为及时更新的问题 将原来column深拷贝，更改别名在加入*/
                            Column nCol = new Column();
                            nCol.setAlis(column.getAlis()==null?colName:column.getAlis());
                            nCol.setColumns(c.getColumns());
                            nCol.setExpr(c.getExpr());
                            nCol.setfName(c.getfName());
                            nCol.setName(c.getName());
                            nCol.setSourceTable(c.getSourceTable());
                            nCol.setRightIndexCount(column.getRightIndexCount());

                            t.getOutColumns().add(nCol);
                            isFinded=true;
                            break;
                        }
                    }
                    if (isFinded) break;
                }
            }
        }
        if(!isFinded) {
            for (Table t : tables) {
                if (tableHasColumn(t) && !t.getName().startsWith("_TEMPORARY_")) {
                    column.getSourceTable().add(t.getName());
                    t.getColumns().add(column);
                    //应该要break;
                }
            }
        }

    }
    //处理表达式
    /**
     * 处理表达式组成的字段
     * @param ctx
     */
    public void enterSelectExpressionElement(MySqlParser.SelectExpressionElementContext ctx){
        //表达式本身是一列，
        Column column = new Column();
        column.setAlis((ctx.uid()==null?null:ctx.uid().getText()));
        column.setName(ctx.expression().getText());
        column.setExpr(ctx.expression().getText());
        Integer count = countContextHolderStack.pop();
        column.setRightIndexCount(count);
        countContextHolderStack.push(++count);
        expertionContextHolderStack.push(column);
    }
    public void exitSelectExpressionElement(MySqlParser.SelectExpressionElementContext ctx){
        Column column = expertionContextHolderStack.pop();
        //出栈制造临时table
        Table table = new Table();
        table.setName("_TEMPORARY_");
        table.setAlis("");
        table.getColumns().add(column);
        stackTables.peek().add(table);
    }

    /**
     * 处理聚合函数组成的字段
     * @param ctx
     */
    public void enterSelectFunctionElement(MySqlParser.SelectFunctionElementContext ctx){
        //表达式本身是一列，
        Column column = new Column();
        column.setAlis((ctx.uid()==null?null:ctx.uid().getText()));
        column.setName(ctx.functionCall().getText());
        column.setExpr(ctx.functionCall().getText());
        Integer count = countContextHolderStack.pop();
        column.setRightIndexCount(count);
        countContextHolderStack.push(++count);
        functionContextHolderStack.push(column);
    }
    public void exitSelectFunctionElement(MySqlParser.SelectFunctionElementContext ctx){
        Column col = functionContextHolderStack.pop();
        //出栈制造临时table
        Table table = new Table();
        table.setName("_TEMPORARY_");
        table.setAlis("");
        table.getColumns().add(col);
        stackTables.peek().add(table);
    }

    public void enterFullColumnName(MySqlParser.FullColumnNameContext ctx){
        List<Table> tables = stackTables.peek();
        Column column = new Column();
        column.setName(ctx.getText());
        column.setfName(ctx.getText());
        /*Integer count = countContextHolderStack.pop();
        column.setRightIndexCount(count);
        countContextHolderStack.push(++count);*/
        //表达式的处理内部字段
        if(((!functionContextHolderStack.isEmpty())&&(functionContextHolderStack.peek()!=null))||
                ((!expertionContextHolderStack.isEmpty())&&(expertionContextHolderStack.peek()!=null))) {
            boolean hasTabAlis = column.getName().indexOf(".")>0;
            boolean isFinded = false;
            String tabAlis = hasTabAlis?column.getName().substring(0,column.getName().indexOf(".")):"";
            String colName = hasTabAlis?column.getName().substring(1+column.getName().indexOf(".")):column.getName();
            for(Table t:tables){
                //如果有表别名并且表别名与table匹配
                if(hasTabAlis&&(tabAlis.equalsIgnoreCase(t.getAlis())||tabAlis.equalsIgnoreCase(t.getName()))){
                    //如果是子查询表
                    if(t.getName().startsWith("_TEMPORARY_")){
                        List<Column> columns = t.getColumns();
                        for(Column c:columns){
                            if(colName.equalsIgnoreCase(c.getAlis())||(colName.equalsIgnoreCase(c.getName())&&c.getAlis()==null)){
                                //t.getOutColumns().add(c);
                                /*if((!functionContextHolderStack.isEmpty())&&(functionContextHolderStack.peek()!=null)){
                                    functionContextHolderStack.peek().getColumns().add(column);
                                }
                                if((!expertionContextHolderStack.isEmpty())&&(expertionContextHolderStack.peek()!=null)){
                                    expertionContextHolderStack.peek().getColumns().add(column);
                                }*/
                                column.setfName(colName);
                                column.setSourceTable(c.getSourceTable());
                                isFinded=true;
                                break;
                            }
                        }
                        if (isFinded) break;
                    }
                    column.setfName(colName);
                    column.getSourceTable().add(t.getName());
                    //t.getColumns().add(column);
                    /*if((!functionContextHolderStack.isEmpty())&&(functionContextHolderStack.peek()!=null)){
                        functionContextHolderStack.peek().getColumns().add(column);
                    }
                    if((!expertionContextHolderStack.isEmpty())&&(expertionContextHolderStack.peek()!=null)){
                        expertionContextHolderStack.peek().getColumns().add(column);
                    }*/
                    isFinded=true;
                    break;
                }else if(!hasTabAlis){
                    if(t.getName().startsWith("_TEMPORARY_")){
                        List<Column> columns = t.getColumns();
                        for(Column c:columns){
                            if(colName.equalsIgnoreCase(c.getAlis())||(colName.equalsIgnoreCase(c.getName())&&c.getAlis()==null)){
                                //t.getOutColumns().add(c);
                                column.setSourceTable(c.getSourceTable());
                                /*if((!functionContextHolderStack.isEmpty())&&(functionContextHolderStack.peek()!=null)){
                                    functionContextHolderStack.peek().getColumns().add(column);
                                }
                                if((!expertionContextHolderStack.isEmpty())&&(expertionContextHolderStack.peek()!=null)){
                                    expertionContextHolderStack.peek().getColumns().add(column);
                                }*/
                                isFinded=true;
                                break;
                            }
                        }
                        if (isFinded) break;
                    }
                }
            }
            //此处不应该返回，应该继续下走
            if(!isFinded) {
                for(Table t:tables){
                    if(tableHasColumn(t)&&!t.getName().startsWith("_TEMPORARY_")){
                        column.getSourceTable().add(t.getName());
                        //t.getColumns().add(column);
                        //应该要break;
                    }
                }
            }
        }

        if((!functionContextHolderStack.isEmpty())&&(functionContextHolderStack.peek()!=null)){
            functionContextHolderStack.peek().getColumns().add(column);
        }
        if((!expertionContextHolderStack.isEmpty())&&(expertionContextHolderStack.peek()!=null)){
            expertionContextHolderStack.peek().getColumns().add(column);
        }
    }

    private boolean tableHasColumn(Table t) {
        return true;
    }
    /**
     * 子查询产生虚拟表
     * @param ctx
     */
    @Override
    public void exitSubqueryTableItem(MySqlParser.SubqueryTableItemContext ctx) {
        Table table =new Table();
        table.setAlis(ctx.uid().getText());
        table.setName("_TEMPORARY_" + ctx.uid().getText());
        //List<Table> tables = new ArrayList<Table>();
        for(Table t:selectTables){
            if(t.getColumns().size()>0)
            table.getColumns().addAll(t.getColumns());
        }
        //给tab添加字段拷贝出栈的
        stackTables.peek().add(table);
    }

    @Override
    public void enterAtomTableItem(MySqlParser.AtomTableItemContext ctx){
        Table table =new Table();
        table.setAlis((ctx.uid()==null?ctx.tableName().getText():ctx.uid().getText()));
        table.setName(ctx.tableName().getText());
        stackTables.peek().add(table);
    }

}
