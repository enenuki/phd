/*  1:   */ package org.apache.xalan.templates;
/*  2:   */ 
/*  3:   */ import org.apache.xpath.ExpressionOwner;
/*  4:   */ import org.apache.xpath.XPathVisitor;
/*  5:   */ import org.apache.xpath.axes.LocPathIterator;
/*  6:   */ import org.apache.xpath.functions.FuncCurrent;
/*  7:   */ import org.apache.xpath.functions.FuncExtFunction;
/*  8:   */ import org.apache.xpath.functions.Function;
/*  9:   */ import org.apache.xpath.operations.Variable;
/* 10:   */ 
/* 11:   */ public class AbsPathChecker
/* 12:   */   extends XPathVisitor
/* 13:   */ {
/* 14:37 */   private boolean m_isAbs = true;
/* 15:   */   
/* 16:   */   public boolean checkAbsolute(LocPathIterator path)
/* 17:   */   {
/* 18:48 */     this.m_isAbs = true;
/* 19:49 */     path.callVisitors(null, this);
/* 20:50 */     return this.m_isAbs;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean visitFunction(ExpressionOwner owner, Function func)
/* 24:   */   {
/* 25:62 */     if (((func instanceof FuncCurrent)) || ((func instanceof FuncExtFunction))) {
/* 26:64 */       this.m_isAbs = false;
/* 27:   */     }
/* 28:65 */     return true;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean visitVariableRef(ExpressionOwner owner, Variable var)
/* 32:   */   {
/* 33:77 */     this.m_isAbs = false;
/* 34:78 */     return true;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.AbsPathChecker
 * JD-Core Version:    0.7.0.1
 */