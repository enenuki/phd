/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.dialect.function.SQLFunctionRegistry;
/*  6:   */ import org.hibernate.sql.Template;
/*  7:   */ 
/*  8:   */ public class Formula
/*  9:   */   implements Selectable, Serializable
/* 10:   */ {
/* 11:36 */   private static int formulaUniqueInteger = 0;
/* 12:   */   private String formula;
/* 13:   */   private int uniqueInteger;
/* 14:   */   
/* 15:   */   public Formula()
/* 16:   */   {
/* 17:42 */     this.uniqueInteger = (formulaUniqueInteger++);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getTemplate(Dialect dialect, SQLFunctionRegistry functionRegistry)
/* 21:   */   {
/* 22:46 */     return Template.renderWhereStringTemplate(this.formula, dialect, functionRegistry);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getText(Dialect dialect)
/* 26:   */   {
/* 27:49 */     return getFormula();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getText()
/* 31:   */   {
/* 32:52 */     return getFormula();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getAlias(Dialect dialect)
/* 36:   */   {
/* 37:55 */     return "formula" + Integer.toString(this.uniqueInteger) + '_';
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getAlias(Dialect dialect, Table table)
/* 41:   */   {
/* 42:58 */     return getAlias(dialect);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String getFormula()
/* 46:   */   {
/* 47:61 */     return this.formula;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setFormula(String string)
/* 51:   */   {
/* 52:64 */     this.formula = string;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean isFormula()
/* 56:   */   {
/* 57:67 */     return true;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String toString()
/* 61:   */   {
/* 62:71 */     return getClass().getName() + "( " + this.formula + " )";
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Formula
 * JD-Core Version:    0.7.0.1
 */