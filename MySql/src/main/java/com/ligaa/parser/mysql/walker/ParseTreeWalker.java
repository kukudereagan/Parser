package com.ligaa.parser.mysql.walker;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;

public class ParseTreeWalker {
    public static final ParseTreeWalker DEFAULT = new ParseTreeWalker();

    public ParseTreeWalker() {
    }

    public void walk(ParseTreeListener listener, ParseTree t) {
        if (t instanceof ErrorNode) {
            listener.visitErrorNode((ErrorNode)t);
        } else if (t instanceof TerminalNode) {
            listener.visitTerminal((TerminalNode)t);
        } else {
            RuleNode r = (RuleNode)t;
            this.enterRule(listener, r);
            int n = r.getChildCount();
            for(int i = n; i >0;i--) {
                this.walk(listener, r.getChild(i-1));
            }

            this.exitRule(listener, r);
        }
    }
    /**
     *@author WANGYI
     *@createdDate 2019/3/11 15:06
     *@description TODO 后序遍历
     *@class ParseTreeWalker
     *@group bigdata develop group ()
     *@params [listener, t]
     *@return void
     *@updatedDate
     *@updatedBy
     */
    public void walkRML(ParseTreeListener listener, ParseTree t) {
        if (t instanceof ErrorNode) {
            listener.visitErrorNode((ErrorNode)t);
        } else if (t instanceof TerminalNode) {
            listener.visitTerminal((TerminalNode)t);
        } else {
            RuleNode r = (RuleNode)t;
            this.enterRule(listener, r);
            int n = r.getChildCount();
            for(int i = n; i >0;i--) {
                this.walkRML(listener, r.getChild(i-1));
            }

            this.exitRule(listener, r);
        }
    }
    /**
     *@author WANGYI
     *@createdDate 2019/3/11 15:03
     *@description TODO 先序遍历
     *@class ParseTreeWalker
     *@group bigdata develop group ()
     *@params [listener, t]
     *@return void
     *@updatedDate
     *@updatedBy
     */
    public void walkLMR(ParseTreeListener listener, ParseTree t) {
        if (t instanceof ErrorNode) {
            listener.visitErrorNode((ErrorNode)t);
        } else if (t instanceof TerminalNode) {
            listener.visitTerminal((TerminalNode)t);
        } else {
            RuleNode r = (RuleNode)t;
            this.enterRule(listener, r);
            int n = r.getChildCount();
            for(int i = 0; i <n;i++) {
                this.walkLMR(listener, r.getChild(i));
            }
            this.exitRule(listener, r);
        }
    }

    protected void enterRule(ParseTreeListener listener, RuleNode r) {
        ParserRuleContext ctx = (ParserRuleContext) r.getRuleContext();
        listener.enterEveryRule(ctx);
        ctx.enterRule(listener);
    }

    protected void exitRule(ParseTreeListener listener, RuleNode r) {
        ParserRuleContext ctx = (ParserRuleContext)r.getRuleContext();
        ctx.exitRule(listener);
        listener.exitEveryRule(ctx);
    }
}