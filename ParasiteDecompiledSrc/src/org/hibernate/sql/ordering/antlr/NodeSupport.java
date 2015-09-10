/*  1:   */ package org.hibernate.sql.ordering.antlr;
/*  2:   */ 
/*  3:   */ import antlr.CommonAST;
/*  4:   */ 
/*  5:   */ public class NodeSupport
/*  6:   */   extends CommonAST
/*  7:   */   implements Node
/*  8:   */ {
/*  9:   */   public String getDebugText()
/* 10:   */   {
/* 11:38 */     return getText();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getRenderableText()
/* 15:   */   {
/* 16:45 */     return getText();
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.NodeSupport
 * JD-Core Version:    0.7.0.1
 */