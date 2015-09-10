/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ public class FirebirdDialect
/*  4:   */   extends InterbaseDialect
/*  5:   */ {
/*  6:   */   public String getDropSequenceString(String sequenceName)
/*  7:   */   {
/*  8:35 */     return "drop generator " + sequenceName;
/*  9:   */   }
/* 10:   */   
/* 11:   */   public String getLimitString(String sql, boolean hasOffset)
/* 12:   */   {
/* 13:39 */     return new StringBuilder(sql.length() + 20).append(sql).insert(6, hasOffset ? " first ? skip ?" : " first ?").toString();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean bindLimitParametersFirst()
/* 17:   */   {
/* 18:46 */     return true;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean bindLimitParametersInReverseOrder()
/* 22:   */   {
/* 23:50 */     return true;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.FirebirdDialect
 * JD-Core Version:    0.7.0.1
 */