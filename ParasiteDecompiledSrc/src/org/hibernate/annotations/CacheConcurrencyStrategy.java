/*  1:   */ package org.hibernate.annotations;
/*  2:   */ 
/*  3:   */ import org.hibernate.cache.spi.access.AccessType;
/*  4:   */ 
/*  5:   */ public enum CacheConcurrencyStrategy
/*  6:   */ {
/*  7:34 */   NONE(null),  READ_ONLY(AccessType.READ_ONLY),  NONSTRICT_READ_WRITE(AccessType.NONSTRICT_READ_WRITE),  READ_WRITE(AccessType.READ_WRITE),  TRANSACTIONAL(AccessType.TRANSACTIONAL);
/*  8:   */   
/*  9:   */   private final AccessType accessType;
/* 10:   */   
/* 11:   */   private CacheConcurrencyStrategy(AccessType accessType)
/* 12:   */   {
/* 13:43 */     this.accessType = accessType;
/* 14:   */   }
/* 15:   */   
/* 16:   */   private boolean isMatch(String name)
/* 17:   */   {
/* 18:47 */     return ((this.accessType != null) && (this.accessType.getExternalName().equalsIgnoreCase(name))) || (name().equalsIgnoreCase(name));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static CacheConcurrencyStrategy fromAccessType(AccessType accessType)
/* 22:   */   {
/* 23:52 */     switch (1.$SwitchMap$org$hibernate$cache$spi$access$AccessType[accessType.ordinal()])
/* 24:   */     {
/* 25:   */     case 1: 
/* 26:54 */       return READ_ONLY;
/* 27:   */     case 2: 
/* 28:57 */       return READ_WRITE;
/* 29:   */     case 3: 
/* 30:60 */       return NONSTRICT_READ_WRITE;
/* 31:   */     case 4: 
/* 32:63 */       return TRANSACTIONAL;
/* 33:   */     }
/* 34:66 */     return NONE;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static CacheConcurrencyStrategy parse(String name)
/* 38:   */   {
/* 39:72 */     if (READ_ONLY.isMatch(name)) {
/* 40:73 */       return READ_ONLY;
/* 41:   */     }
/* 42:75 */     if (READ_WRITE.isMatch(name)) {
/* 43:76 */       return READ_WRITE;
/* 44:   */     }
/* 45:78 */     if (NONSTRICT_READ_WRITE.isMatch(name)) {
/* 46:79 */       return NONSTRICT_READ_WRITE;
/* 47:   */     }
/* 48:81 */     if (TRANSACTIONAL.isMatch(name)) {
/* 49:82 */       return TRANSACTIONAL;
/* 50:   */     }
/* 51:84 */     if (NONE.isMatch(name)) {
/* 52:85 */       return NONE;
/* 53:   */     }
/* 54:88 */     return null;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public AccessType toAccessType()
/* 58:   */   {
/* 59:93 */     return this.accessType;
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.CacheConcurrencyStrategy
 * JD-Core Version:    0.7.0.1
 */