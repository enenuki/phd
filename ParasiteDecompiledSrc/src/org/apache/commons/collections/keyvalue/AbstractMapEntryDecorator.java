/*  1:   */ package org.apache.commons.collections.keyvalue;
/*  2:   */ 
/*  3:   */ import java.util.Map.Entry;
/*  4:   */ import org.apache.commons.collections.KeyValue;
/*  5:   */ 
/*  6:   */ public abstract class AbstractMapEntryDecorator
/*  7:   */   implements Map.Entry, KeyValue
/*  8:   */ {
/*  9:   */   protected final Map.Entry entry;
/* 10:   */   
/* 11:   */   public AbstractMapEntryDecorator(Map.Entry entry)
/* 12:   */   {
/* 13:44 */     if (entry == null) {
/* 14:45 */       throw new IllegalArgumentException("Map Entry must not be null");
/* 15:   */     }
/* 16:47 */     this.entry = entry;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected Map.Entry getMapEntry()
/* 20:   */   {
/* 21:56 */     return this.entry;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Object getKey()
/* 25:   */   {
/* 26:61 */     return this.entry.getKey();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Object getValue()
/* 30:   */   {
/* 31:65 */     return this.entry.getValue();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Object setValue(Object object)
/* 35:   */   {
/* 36:69 */     return this.entry.setValue(object);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean equals(Object object)
/* 40:   */   {
/* 41:73 */     if (object == this) {
/* 42:74 */       return true;
/* 43:   */     }
/* 44:76 */     return this.entry.equals(object);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int hashCode()
/* 48:   */   {
/* 49:80 */     return this.entry.hashCode();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String toString()
/* 53:   */   {
/* 54:84 */     return this.entry.toString();
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.keyvalue.AbstractMapEntryDecorator
 * JD-Core Version:    0.7.0.1
 */