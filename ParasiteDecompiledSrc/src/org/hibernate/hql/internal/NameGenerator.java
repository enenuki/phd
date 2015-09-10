/*  1:   */ package org.hibernate.hql.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public final class NameGenerator
/*  8:   */ {
/*  9:   */   public static String[][] generateColumnNames(Type[] types, SessionFactoryImplementor f)
/* 10:   */     throws MappingException
/* 11:   */   {
/* 12:43 */     String[][] columnNames = new String[types.length][];
/* 13:44 */     for (int i = 0; i < types.length; i++)
/* 14:   */     {
/* 15:45 */       int span = types[i].getColumnSpan(f);
/* 16:46 */       columnNames[i] = new String[span];
/* 17:47 */       for (int j = 0; j < span; j++) {
/* 18:48 */         columnNames[i][j] = scalarName(i, j);
/* 19:   */       }
/* 20:   */     }
/* 21:51 */     return columnNames;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static String scalarName(int x, int y)
/* 25:   */   {
/* 26:55 */     return "col_" + x + '_' + y + '_';
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.NameGenerator
 * JD-Core Version:    0.7.0.1
 */