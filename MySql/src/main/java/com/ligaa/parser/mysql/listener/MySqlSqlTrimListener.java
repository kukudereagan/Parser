package com.ligaa.parser.mysql.listener;

import com.ligaa.parser.mysql.parser.MySqlParser;
import com.ligaa.parser.mysql.parser.MySqlParserBaseListener;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

/**
 * @author WANGYI
 * @className PACKAGE_NAME.SqlTrimVisitor
 * @createdDate 2019/5/14 19:08
 * @description TODO
 * @e-mail WANGYI@clinbrain.com
 * @group bigdata develop group (TestSql)
 */
public class MySqlSqlTrimListener extends MySqlParserBaseListener {
    private int countIndex = 0;
    private final static String specelString = "'?";
    private List<MySqlParser.PredicateExpressionContext> predicateExpressionContext = new ArrayList<MySqlParser.PredicateExpressionContext>();
    private List<MySqlParser.LogicalExpressionContext> logicalExpressionContexts = new ArrayList<MySqlParser.LogicalExpressionContext>();
    private Stack<Boolean> booleanStack = new Stack<Boolean>();
    private Map<Integer,Object> params = new HashMap<Integer, Object>();
    public MySqlSqlTrimListener(){}
    public MySqlSqlTrimListener(Map<Integer,Object> params){
        this.params = params;
    }
    private void exitPredicate(RuleContext ctx){
        RuleContext p =  ctx.parent;
        if(p instanceof MySqlParser.PredicateExpressionContext){
            MySqlParser.PredicateExpressionContext parent = (MySqlParser.PredicateExpressionContext) p;
            //如果？是没有值的
            if(booleanStack.pop()){
                predicateExpressionContext.add(parent);
            }
        }
    }
    private void enterPredicate(RuleContext ctx){
        booleanStack.push(false);
    }
    /*
    *@author WANGYI
    *@createdDate 2019/5/17 14:12
    *@description TODO 退出占位符时计数，记录当前操作的占位符编号，从左到右的编号
    *@class SqlTrimVisitor
    *@group bigdata develop group ()
    *@params
    *@return
    *@updatedDate
    *@updatedBy
    */
    @Override
    public void exitConstant(MySqlParser.ConstantContext ctx) {
        if(ctx.getText()!=null&&ctx.getText().contains(specelString)){
            countIndex++;
            if((!booleanStack.peek())&&countIndex>0 && params.get(countIndex)==null){
                booleanStack.pop();
                booleanStack.push(true);
            }
        }
    }

    @Override
    public void exitInPredicate(MySqlParser.InPredicateContext ctx) {
        exitPredicate(ctx);
    }
    @Override
    public void exitBinaryComparasionPredicate(MySqlParser.BinaryComparasionPredicateContext ctx) {
        exitPredicate(ctx);
    }

    @Override
    public void exitLikePredicate(MySqlParser.LikePredicateContext ctx) {
        exitPredicate(ctx);
    }

    @Override
    public void exitBetweenPredicate(MySqlParser.BetweenPredicateContext ctx) {
        exitPredicate(ctx);
    }

    @Override
    public void enterInPredicate(MySqlParser.InPredicateContext ctx) {
        enterPredicate(ctx);
    }
    @Override
    public void enterBinaryComparasionPredicate(MySqlParser.BinaryComparasionPredicateContext ctx) {
        enterPredicate(ctx);
    }

    @Override
    public void enterLikePredicate(MySqlParser.LikePredicateContext ctx) {
        enterPredicate(ctx);
    }
    @Override
    public void enterBetweenPredicate(MySqlParser.BetweenPredicateContext ctx) {
        enterPredicate(ctx);
    }

    @Override
    public void exitExpressionAtomPredicate(MySqlParser.ExpressionAtomPredicateContext ctx) {
        RuleContext p =  ctx.parent;
        if(p instanceof MySqlParser.PredicateExpressionContext){
            MySqlParser.PredicateExpressionContext parent = (MySqlParser.PredicateExpressionContext) p;
            //如果？是没有值的
            ParseTree parseTree = null;
            if((parseTree = ctx.getChild(0)) instanceof MySqlParser.NestedExpressionAtomContext){
                if(parseTree.getChildCount()<=0)
                    predicateExpressionContext.add(parent);
            }
        }
    }

    /**
    *@author WANGYI
    *@createdDate 2019/5/17 14:30
    *@description TODO 退出逻辑表达式的时候裁剪
    *@class SqlTrimVisitor
    *@group bigdata develop group ()
    *@params [ctx]
    *@return void
    *@updatedDate
    *@updatedBy
    */
    @Override
    public void exitLogicalExpression(MySqlParser.LogicalExpressionContext ctx) {
        List<ParseTree> parseTrees = new ArrayList<ParseTree>();
        //检查子节点
        if(!ctx.children.isEmpty()){
            for(ParseTree t:ctx.children){
                if(t.getChildCount()==0){
                    parseTrees.add(t);
                }
            }
        }
        if(!parseTrees.isEmpty()){
            ctx.children.removeAll(parseTrees);
        }

        if(!logicalExpressionContexts.isEmpty()){
            ctx.children.removeAll(logicalExpressionContexts);
            logicalExpressionContexts.clear();
        }
        if(!predicateExpressionContext.isEmpty()){
            ctx.children.remove(ctx.logicalOperator());
            ctx.children.removeAll(predicateExpressionContext);
            if(ctx.children.isEmpty()){
                logicalExpressionContexts.add(ctx);
            }
        }

        if(ctx.parent instanceof MySqlParser.FromClauseContext && ctx.children.isEmpty()){
            ((MySqlParser.FromClauseContext) ctx.parent).children.remove (((MySqlParser.FromClauseContext) ctx.parent).WHERE());
        }
        predicateExpressionContext.clear();
    }

    @Override
    public void exitNestedExpressionAtom(MySqlParser.NestedExpressionAtomContext ctx) {
        if(!logicalExpressionContexts.isEmpty()){
            ctx.children.clear();
        }
        logicalExpressionContexts.clear();
    }
}
