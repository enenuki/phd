/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public enum MetadataSourceType
/*  6:   */ {
/*  7:37 */   HBM("hbm"),  CLASS("class");
/*  8:   */   
/*  9:   */   private final String name;
/* 10:   */   
/* 11:   */   private MetadataSourceType(String name)
/* 12:   */   {
/* 13:46 */     this.name = name;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String toString()
/* 17:   */   {
/* 18:51 */     return this.name;
/* 19:   */   }
/* 20:   */   
/* 21:   */   static MetadataSourceType parsePrecedence(String value)
/* 22:   */   {
/* 23:55 */     if (HBM.name.equalsIgnoreCase(value)) {
/* 24:56 */       return HBM;
/* 25:   */     }
/* 26:59 */     if (CLASS.name.equalsIgnoreCase(value)) {
/* 27:60 */       return CLASS;
/* 28:   */     }
/* 29:63 */     throw new HibernateException("Unknown metadata source type value [" + value + "]");
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.MetadataSourceType
 * JD-Core Version:    0.7.0.1
 */