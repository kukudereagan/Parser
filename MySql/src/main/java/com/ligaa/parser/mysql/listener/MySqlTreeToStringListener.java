package com.ligaa.parser.mysql.listener;

import com.ligaa.parser.mysql.parser.MySqlParserBaseListener;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * @author WANGYI
 * @className com.clinbrain.parser.mysql.listener.MySqlTreeToStringListener
 * @createdDate 2019/3/11 15:10
 * @description TODO
 * @e-mail WANGYI@clinbrain.com
 * @group bigdata develop group (HybridDataProviderServer)
 */
public class MySqlTreeToStringListener extends MySqlParserBaseListener {
    private static final String SPACE = " ";
    private StringBuilder builder;
    public MySqlTreeToStringListener(){}
    public MySqlTreeToStringListener(StringBuilder builder){this.builder=builder;}

    @Override
    public void visitTerminal(TerminalNode node){
        builder.append(SPACE).append(node.getText());
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(StringBuilder builder) {
        this.builder = builder;
    }
}
