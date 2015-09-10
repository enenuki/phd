/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ 
/*  6:   */ public class PrimaryKey
/*  7:   */   extends Constraint
/*  8:   */ {
/*  9:   */   public String sqlConstraintString(Dialect dialect)
/* 10:   */   {
/* 11:36 */     StringBuffer buf = new StringBuffer("primary key (");
/* 12:37 */     Iterator iter = getColumnIterator();
/* 13:38 */     while (iter.hasNext())
/* 14:   */     {
/* 15:39 */       buf.append(((Column)iter.next()).getQuotedName(dialect));
/* 16:40 */       if (iter.hasNext()) {
/* 17:40 */         buf.append(", ");
/* 18:   */       }
/* 19:   */     }
/* 20:42 */     return ')';
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String sqlConstraintString(Dialect dialect, String constraintName, String defaultCatalog, String defaultSchema)
/* 24:   */   {
/* 25:46 */     StringBuffer buf = new StringBuffer(dialect.getAddPrimaryKeyConstraintString(constraintName)).append('(');
/* 26:   */     
/* 27:   */ 
/* 28:49 */     Iterator iter = getColumnIterator();
/* 29:50 */     while (iter.hasNext())
/* 30:   */     {
/* 31:51 */       buf.append(((Column)iter.next()).getQuotedName(dialect));
/* 32:52 */       if (iter.hasNext()) {
/* 33:52 */         buf.append(", ");
/* 34:   */       }
/* 35:   */     }
/* 36:54 */     return ')';
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.PrimaryKey
 * JD-Core Version:    0.7.0.1
 */