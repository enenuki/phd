/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import org.hibernate.sql.ANSIJoinFragment;
/*  4:   */ import org.hibernate.sql.JoinFragment;
/*  5:   */ 
/*  6:   */ public class Oracle10gDialect
/*  7:   */   extends Oracle9iDialect
/*  8:   */ {
/*  9:   */   public JoinFragment createOuterJoinFragment()
/* 10:   */   {
/* 11:46 */     return new ANSIJoinFragment();
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.Oracle10gDialect
 * JD-Core Version:    0.7.0.1
 */