/*  1:   */ package org.apache.commons.collections.keyvalue;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.KeyValue;
/*  4:   */ 
/*  5:   */ public abstract class AbstractKeyValue
/*  6:   */   implements KeyValue
/*  7:   */ {
/*  8:   */   protected Object key;
/*  9:   */   protected Object value;
/* 10:   */   
/* 11:   */   protected AbstractKeyValue(Object key, Object value)
/* 12:   */   {
/* 13:48 */     this.key = key;
/* 14:49 */     this.value = value;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Object getKey()
/* 18:   */   {
/* 19:58 */     return this.key;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object getValue()
/* 23:   */   {
/* 24:67 */     return this.value;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String toString()
/* 28:   */   {
/* 29:76 */     return getKey() + '=' + getValue();
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.keyvalue.AbstractKeyValue
 * JD-Core Version:    0.7.0.1
 */