/*  1:   */ package org.apache.commons.lang.builder;
/*  2:   */ 
/*  3:   */ final class IDKey
/*  4:   */ {
/*  5:   */   private final Object value;
/*  6:   */   private final int id;
/*  7:   */   
/*  8:   */   public IDKey(Object _value)
/*  9:   */   {
/* 10:42 */     this.id = System.identityHashCode(_value);
/* 11:   */     
/* 12:   */ 
/* 13:   */ 
/* 14:46 */     this.value = _value;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int hashCode()
/* 18:   */   {
/* 19:54 */     return this.id;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean equals(Object other)
/* 23:   */   {
/* 24:63 */     if (!(other instanceof IDKey)) {
/* 25:64 */       return false;
/* 26:   */     }
/* 27:66 */     IDKey idKey = (IDKey)other;
/* 28:67 */     if (this.id != idKey.id) {
/* 29:68 */       return false;
/* 30:   */     }
/* 31:71 */     return this.value == idKey.value;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.builder.IDKey
 * JD-Core Version:    0.7.0.1
 */