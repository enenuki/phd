/*  1:   */ package org.apache.commons.collections.keyvalue;
/*  2:   */ 
/*  3:   */ import java.util.Map.Entry;
/*  4:   */ import org.apache.commons.collections.KeyValue;
/*  5:   */ 
/*  6:   */ public final class DefaultMapEntry
/*  7:   */   extends AbstractMapEntry
/*  8:   */ {
/*  9:   */   public DefaultMapEntry(Object key, Object value)
/* 10:   */   {
/* 11:44 */     super(key, value);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public DefaultMapEntry(KeyValue pair)
/* 15:   */   {
/* 16:54 */     super(pair.getKey(), pair.getValue());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public DefaultMapEntry(Map.Entry entry)
/* 20:   */   {
/* 21:64 */     super(entry.getKey(), entry.getValue());
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.keyvalue.DefaultMapEntry
 * JD-Core Version:    0.7.0.1
 */