/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;
/*  5:   */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*  6:   */ import org.hibernate.type.StandardBasicTypes;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class LiteralNode
/* 10:   */   extends AbstractSelectExpression
/* 11:   */   implements HqlSqlTokenTypes
/* 12:   */ {
/* 13:   */   public void setScalarColumnText(int i)
/* 14:   */     throws SemanticException
/* 15:   */   {
/* 16:41 */     ColumnHelper.generateSingleScalarColumn(this, i);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Type getDataType()
/* 20:   */   {
/* 21:45 */     switch (getType())
/* 22:   */     {
/* 23:   */     case 124: 
/* 24:47 */       return StandardBasicTypes.INTEGER;
/* 25:   */     case 96: 
/* 26:49 */       return StandardBasicTypes.FLOAT;
/* 27:   */     case 97: 
/* 28:51 */       return StandardBasicTypes.LONG;
/* 29:   */     case 95: 
/* 30:53 */       return StandardBasicTypes.DOUBLE;
/* 31:   */     case 98: 
/* 32:55 */       return StandardBasicTypes.BIG_INTEGER;
/* 33:   */     case 99: 
/* 34:57 */       return StandardBasicTypes.BIG_DECIMAL;
/* 35:   */     case 125: 
/* 36:59 */       return StandardBasicTypes.STRING;
/* 37:   */     case 20: 
/* 38:   */     case 49: 
/* 39:62 */       return StandardBasicTypes.BOOLEAN;
/* 40:   */     }
/* 41:64 */     return null;
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.LiteralNode
 * JD-Core Version:    0.7.0.1
 */