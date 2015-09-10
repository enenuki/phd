/*  1:   */ package org.apache.commons.collections.keyvalue;
/*  2:   */ 
/*  3:   */ import java.util.Map.Entry;
/*  4:   */ 
/*  5:   */ public abstract class AbstractMapEntry
/*  6:   */   extends AbstractKeyValue
/*  7:   */   implements Map.Entry
/*  8:   */ {
/*  9:   */   protected AbstractMapEntry(Object key, Object value)
/* 10:   */   {
/* 11:42 */     super(key, value);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Object setValue(Object value)
/* 15:   */   {
/* 16:57 */     Object answer = this.value;
/* 17:58 */     this.value = value;
/* 18:59 */     return answer;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean equals(Object obj)
/* 22:   */   {
/* 23:71 */     if (obj == this) {
/* 24:72 */       return true;
/* 25:   */     }
/* 26:74 */     if (!(obj instanceof Map.Entry)) {
/* 27:75 */       return false;
/* 28:   */     }
/* 29:77 */     Map.Entry other = (Map.Entry)obj;
/* 30:78 */     return (getKey() == null ? other.getKey() == null : getKey().equals(other.getKey())) && (getValue() == null ? other.getValue() == null : getValue().equals(other.getValue()));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int hashCode()
/* 34:   */   {
/* 35:91 */     return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.keyvalue.AbstractMapEntry
 * JD-Core Version:    0.7.0.1
 */