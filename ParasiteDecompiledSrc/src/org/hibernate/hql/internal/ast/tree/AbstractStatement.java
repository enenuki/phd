/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.Set;
/*  5:   */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  6:   */ 
/*  7:   */ public abstract class AbstractStatement
/*  8:   */   extends HqlSqlWalkerNode
/*  9:   */   implements DisplayableNode, Statement
/* 10:   */ {
/* 11:   */   public String getDisplayText()
/* 12:   */   {
/* 13:41 */     StringBuffer buf = new StringBuffer();
/* 14:42 */     if (getWalker().getQuerySpaces().size() > 0)
/* 15:   */     {
/* 16:43 */       buf.append(" querySpaces (");
/* 17:44 */       for (Iterator iterator = getWalker().getQuerySpaces().iterator(); iterator.hasNext();)
/* 18:   */       {
/* 19:45 */         buf.append(iterator.next());
/* 20:46 */         if (iterator.hasNext()) {
/* 21:47 */           buf.append(",");
/* 22:   */         }
/* 23:   */       }
/* 24:50 */       buf.append(")");
/* 25:   */     }
/* 26:52 */     return buf.toString();
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.AbstractStatement
 * JD-Core Version:    0.7.0.1
 */