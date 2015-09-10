/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import org.hibernate.QueryException;
/*  4:   */ 
/*  5:   */ public class InsertStatement
/*  6:   */   extends AbstractStatement
/*  7:   */ {
/*  8:   */   public int getStatementType()
/*  9:   */   {
/* 10:40 */     return 29;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean needsExecutor()
/* 14:   */   {
/* 15:47 */     return true;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void validate()
/* 19:   */     throws QueryException
/* 20:   */   {
/* 21:56 */     getIntoClause().validateTypes(getSelectClause());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public IntoClause getIntoClause()
/* 25:   */   {
/* 26:65 */     return (IntoClause)getFirstChild();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public SelectClause getSelectClause()
/* 30:   */   {
/* 31:74 */     return ((QueryNode)getIntoClause().getNextSibling()).getSelectClause();
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.InsertStatement
 * JD-Core Version:    0.7.0.1
 */