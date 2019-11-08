package com.ligaa.parser.mysql.utils;

import com.lig.parser.common.entity.Table;
import com.ligaa.parser.mysql.lexer.MySqlLexer;
import com.ligaa.parser.mysql.listener.MySqlParserBaseListenerAdapter;
import com.ligaa.parser.mysql.listener.MySqlSqlTrimListener;
import com.ligaa.parser.mysql.listener.MySqlTreeToStringListener;
import com.ligaa.parser.mysql.parser.MySqlParser;
import com.ligaa.parser.mysql.parser.MySqlParserBaseListener;
import com.ligaa.parser.mysql.walker.ParseTreeWalker;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class ParseUtil {
    public static List<Table> parseSelect(InputStreamReader inputStreamReader) throws IOException {
        CharStream input = CharStreams.fromReader(inputStreamReader);
        MySqlLexer lexer=new MySqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MySqlParser parser = new MySqlParser(tokens);
        MySqlParser.SelectStatementContext parserRuleContext = parser.selectStatement();
        return parseSimpleSelect(parserRuleContext);
    }
    public static MySqlParser.SelectStatementContext parse(String sqlStatement) throws IOException {
        CharStream input = CharStreams.fromString(sqlStatement.toUpperCase());
        MySqlLexer lexer=new MySqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MySqlParser parser = new MySqlParser(tokens);
        return parser.selectStatement();
    }
    public static List<Table> parseSelect(String sqlStatement) throws IOException {
        CharStream input = CharStreams.fromString(sqlStatement.toUpperCase());
        MySqlLexer lexer=new MySqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MySqlParser parser = new MySqlParser(tokens);
        MySqlParser.SelectStatementContext parserRuleContext = parser.selectStatement();

        return parseSimpleSelect(parserRuleContext);
    }
    private static List<Table> parseSimpleSelect(MySqlParser.SelectStatementContext simpleSelectContext){
        MySqlParserBaseListenerAdapter listener =new MySqlParserBaseListenerAdapter();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener,simpleSelectContext);
        List<Table> tables = listener.getSelectTables();
        //System.out.println(JSONObject.toJSONString(tables) );
        return tables;
    }
    public static String tree2Sql(ParseTree tree){
        MySqlParserBaseListener listener = new MySqlTreeToStringListener(new StringBuilder());
        ParseTreeWalker.DEFAULT.walkLMR(listener,tree);
        return ((MySqlTreeToStringListener) listener).getBuilder().toString();
    }
    public static String trimSql(String sql,Map<Integer,Object> params) throws IOException {
        MySqlParser.SelectStatementContext context = parse(sql);
        MySqlSqlTrimListener listener = new MySqlSqlTrimListener(params);
        ParseTreeWalker.DEFAULT.walkLMR(listener, context);
        return tree2Sql(context);
    }
    public static ParseTree trimSqlTree(String sql,Map<Integer,Object> params) throws IOException {
        MySqlParser.SelectStatementContext context = parse(sql);
        MySqlSqlTrimListener listener = new MySqlSqlTrimListener(params);
        ParseTreeWalker.DEFAULT.walkLMR(listener, context);
        return context;
    }
    public static ParseTree trimSqlTree(ParseTree context,Map<Integer,Object> params) throws IOException {
        MySqlSqlTrimListener listener = new MySqlSqlTrimListener(params);
        ParseTreeWalker.DEFAULT.walkLMR(listener, context);
        return context;
    }
}
