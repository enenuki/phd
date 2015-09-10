/*  1:   */ package org.apache.commons.collections.keyvalue;
/*  2:   */ 
/*  3:   */ import java.util.Map.Entry;
/*  4:   */ import org.apache.commons.collections.KeyValue;
/*  5:   */ import org.apache.commons.collections.Unmodifiable;
/*  6:   */ 
/*  7:   */ public final class UnmodifiableMapEntry
/*  8:   */   extends AbstractMapEntry
/*  9:   */   implements Unmodifiable
/* 10:   */ {
/* 11:   */   public UnmodifiableMapEntry(Object key, Object value)
/* 12:   */   {
/* 13:42 */     super(key, value);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public UnmodifiableMapEntry(KeyValue pair)
/* 17:   */   {
/* 18:52 */     super(pair.getKey(), pair.getValue());
/* 19:   */   }
/* 20:   */   
/* 21:   */   public UnmodifiableMapEntry(Map.Entry entry)
/* 22:   */   {
/* 23:62 */     super(entry.getKey(), entry.getValue());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object setValue(Object value)
/* 27:   */   {
/* 28:73 */     throw new UnsupportedOperationException("setValue() is not supported");
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.keyvalue.UnmodifiableMapEntry
 * JD-Core Version:    0.7.0.1
 */