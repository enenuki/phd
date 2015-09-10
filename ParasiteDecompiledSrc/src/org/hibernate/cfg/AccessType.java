/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ public enum AccessType
/*  4:   */ {
/*  5:35 */   DEFAULT("property"),  PROPERTY("property"),  FIELD("field");
/*  6:   */   
/*  7:   */   private final String accessType;
/*  8:   */   
/*  9:   */   private AccessType(String type)
/* 10:   */   {
/* 11:50 */     this.accessType = type;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getType()
/* 15:   */   {
/* 16:54 */     return this.accessType;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static AccessType getAccessStrategy(String type)
/* 20:   */   {
/* 21:58 */     if (type == null) {
/* 22:59 */       return DEFAULT;
/* 23:   */     }
/* 24:61 */     if (FIELD.getType().equals(type)) {
/* 25:62 */       return FIELD;
/* 26:   */     }
/* 27:64 */     if (PROPERTY.getType().equals(type)) {
/* 28:65 */       return PROPERTY;
/* 29:   */     }
/* 30:69 */     return DEFAULT;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static AccessType getAccessStrategy(javax.persistence.AccessType type)
/* 34:   */   {
/* 35:74 */     if (javax.persistence.AccessType.PROPERTY.equals(type)) {
/* 36:75 */       return PROPERTY;
/* 37:   */     }
/* 38:77 */     if (javax.persistence.AccessType.FIELD.equals(type)) {
/* 39:78 */       return FIELD;
/* 40:   */     }
/* 41:81 */     return DEFAULT;
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.AccessType
 * JD-Core Version:    0.7.0.1
 */