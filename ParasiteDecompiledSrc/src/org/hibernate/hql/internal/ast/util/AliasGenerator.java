/*  1:   */ package org.hibernate.hql.internal.ast.util;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.StringHelper;
/*  4:   */ 
/*  5:   */ public class AliasGenerator
/*  6:   */ {
/*  7:36 */   private int next = 0;
/*  8:   */   
/*  9:   */   private int nextCount()
/* 10:   */   {
/* 11:39 */     return this.next++;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String createName(String name)
/* 15:   */   {
/* 16:43 */     return StringHelper.generateAlias(name, nextCount());
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.AliasGenerator
 * JD-Core Version:    0.7.0.1
 */