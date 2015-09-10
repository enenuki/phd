/*  1:   */ package org.hibernate.cache.spi.access;
/*  2:   */ 
/*  3:   */ public enum AccessType
/*  4:   */ {
/*  5:32 */   READ_ONLY("read-only"),  READ_WRITE("read-write"),  NONSTRICT_READ_WRITE("nonstrict-read-write"),  TRANSACTIONAL("transactional");
/*  6:   */   
/*  7:   */   private final String externalName;
/*  8:   */   
/*  9:   */   private AccessType(String externalName)
/* 10:   */   {
/* 11:40 */     this.externalName = externalName;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getExternalName()
/* 15:   */   {
/* 16:44 */     return this.externalName;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String toString()
/* 20:   */   {
/* 21:48 */     return "AccessType[" + this.externalName + "]";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static AccessType fromExternalName(String externalName)
/* 25:   */   {
/* 26:52 */     if (externalName == null) {
/* 27:53 */       return null;
/* 28:   */     }
/* 29:55 */     for (AccessType accessType : values()) {
/* 30:56 */       if (accessType.getExternalName().equals(externalName)) {
/* 31:57 */         return accessType;
/* 32:   */       }
/* 33:   */     }
/* 34:60 */     throw new UnknownAccessTypeException(externalName);
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.access.AccessType
 * JD-Core Version:    0.7.0.1
 */