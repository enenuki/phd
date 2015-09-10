/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ public abstract class AbstractSelectExpression
/*  7:   */   extends HqlSqlWalkerNode
/*  8:   */   implements SelectExpression
/*  9:   */ {
/* 10:   */   private String alias;
/* 11:38 */   private int scalarColumnIndex = -1;
/* 12:   */   
/* 13:   */   public final void setAlias(String alias)
/* 14:   */   {
/* 15:41 */     this.alias = alias;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public final String getAlias()
/* 19:   */   {
/* 20:45 */     return this.alias;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean isConstructor()
/* 24:   */   {
/* 25:49 */     return false;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean isReturnableEntity()
/* 29:   */     throws SemanticException
/* 30:   */   {
/* 31:53 */     return false;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public FromElement getFromElement()
/* 35:   */   {
/* 36:57 */     return null;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean isScalar()
/* 40:   */     throws SemanticException
/* 41:   */   {
/* 42:63 */     Type type = getDataType();
/* 43:64 */     return (type != null) && (!type.isAssociationType());
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setScalarColumn(int i)
/* 47:   */     throws SemanticException
/* 48:   */   {
/* 49:68 */     this.scalarColumnIndex = i;
/* 50:69 */     setScalarColumnText(i);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public int getScalarColumnIndex()
/* 54:   */   {
/* 55:73 */     return this.scalarColumnIndex;
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.AbstractSelectExpression
 * JD-Core Version:    0.7.0.1
 */