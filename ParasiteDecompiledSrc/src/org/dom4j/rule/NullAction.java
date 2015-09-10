/*  1:   */ package org.dom4j.rule;
/*  2:   */ 
/*  3:   */ import org.dom4j.Node;
/*  4:   */ 
/*  5:   */ public class NullAction
/*  6:   */   implements Action
/*  7:   */ {
/*  8:22 */   public static final NullAction SINGLETON = new NullAction();
/*  9:   */   
/* 10:   */   public void run(Node node)
/* 11:   */     throws Exception
/* 12:   */   {}
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.rule.NullAction
 * JD-Core Version:    0.7.0.1
 */