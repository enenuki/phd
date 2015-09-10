/*  1:   */ package org.hibernate.internal.util.collections;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.ObjectInputStream;
/*  5:   */ import java.io.Serializable;
/*  6:   */ import org.apache.commons.collections.map.LRUMap;
/*  7:   */ 
/*  8:   */ public class SimpleMRUCache
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   public static final int DEFAULT_STRONG_REF_COUNT = 128;
/* 12:   */   private final int strongReferenceCount;
/* 13:   */   private transient LRUMap cache;
/* 14:   */   
/* 15:   */   public SimpleMRUCache()
/* 16:   */   {
/* 17:50 */     this(128);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public SimpleMRUCache(int strongReferenceCount)
/* 21:   */   {
/* 22:54 */     this.strongReferenceCount = strongReferenceCount;
/* 23:55 */     init();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public synchronized Object get(Object key)
/* 27:   */   {
/* 28:59 */     return this.cache.get(key);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public synchronized Object put(Object key, Object value)
/* 32:   */   {
/* 33:63 */     return this.cache.put(key, value);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public synchronized int size()
/* 37:   */   {
/* 38:67 */     return this.cache.size();
/* 39:   */   }
/* 40:   */   
/* 41:   */   private void init()
/* 42:   */   {
/* 43:71 */     this.cache = new LRUMap(this.strongReferenceCount);
/* 44:   */   }
/* 45:   */   
/* 46:   */   private void readObject(ObjectInputStream in)
/* 47:   */     throws IOException, ClassNotFoundException
/* 48:   */   {
/* 49:75 */     in.defaultReadObject();
/* 50:76 */     init();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public synchronized void clear()
/* 54:   */   {
/* 55:80 */     this.cache.clear();
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.SimpleMRUCache
 * JD-Core Version:    0.7.0.1
 */