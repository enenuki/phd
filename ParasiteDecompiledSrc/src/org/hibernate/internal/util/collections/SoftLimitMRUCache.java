/*   1:    */ package org.hibernate.internal.util.collections;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.lang.ref.ReferenceQueue;
/*   7:    */ import java.lang.ref.SoftReference;
/*   8:    */ 
/*   9:    */ public class SoftLimitMRUCache
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   public static final int DEFAULT_STRONG_REF_COUNT = 128;
/*  13:    */   public static final int DEFAULT_SOFT_REF_COUNT = 2048;
/*  14:    */   private final int strongRefCount;
/*  15:    */   private final int softRefCount;
/*  16:    */   private transient LRUMap strongRefCache;
/*  17:    */   private transient LRUMap softRefCache;
/*  18:    */   private transient ReferenceQueue referenceQueue;
/*  19:    */   
/*  20:    */   public SoftLimitMRUCache()
/*  21:    */   {
/*  22: 92 */     this(128, 2048);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public SoftLimitMRUCache(int strongRefCount, int softRefCount)
/*  26:    */   {
/*  27:105 */     if ((strongRefCount < 1) || (softRefCount < 1)) {
/*  28:106 */       throw new IllegalArgumentException("Reference counts must be greater than zero");
/*  29:    */     }
/*  30:108 */     if (strongRefCount > softRefCount) {
/*  31:109 */       throw new IllegalArgumentException("Strong reference count cannot exceed soft reference count");
/*  32:    */     }
/*  33:112 */     this.strongRefCount = strongRefCount;
/*  34:113 */     this.softRefCount = softRefCount;
/*  35:114 */     init();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public synchronized Object get(Object key)
/*  39:    */   {
/*  40:125 */     if (key == null) {
/*  41:126 */       throw new NullPointerException("Key to get cannot be null");
/*  42:    */     }
/*  43:129 */     clearObsoleteReferences();
/*  44:    */     
/*  45:131 */     SoftReference ref = (SoftReference)this.softRefCache.get(key);
/*  46:132 */     if (ref != null)
/*  47:    */     {
/*  48:133 */       Object refValue = ref.get();
/*  49:134 */       if (refValue != null)
/*  50:    */       {
/*  51:136 */         this.strongRefCache.put(key, refValue);
/*  52:137 */         return refValue;
/*  53:    */       }
/*  54:    */     }
/*  55:141 */     return null;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public synchronized Object put(Object key, Object value)
/*  59:    */   {
/*  60:153 */     if ((key == null) || (value == null)) {
/*  61:154 */       throw new NullPointerException(getClass().getName() + "does not support null key [" + key + "] or value [" + value + "]");
/*  62:    */     }
/*  63:159 */     clearObsoleteReferences();
/*  64:    */     
/*  65:161 */     this.strongRefCache.put(key, value);
/*  66:162 */     SoftReference ref = (SoftReference)this.softRefCache.put(key, new KeyedSoftReference(key, value, this.referenceQueue, null));
/*  67:    */     
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:167 */     return ref != null ? ref.get() : null;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public synchronized int size()
/*  75:    */   {
/*  76:176 */     clearObsoleteReferences();
/*  77:177 */     return this.strongRefCache.size();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public synchronized int softSize()
/*  81:    */   {
/*  82:186 */     clearObsoleteReferences();
/*  83:187 */     return this.softRefCache.size();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public synchronized void clear()
/*  87:    */   {
/*  88:194 */     this.strongRefCache.clear();
/*  89:195 */     this.softRefCache.clear();
/*  90:    */   }
/*  91:    */   
/*  92:    */   private void init()
/*  93:    */   {
/*  94:199 */     this.strongRefCache = new LRUMap(this.strongRefCount);
/*  95:200 */     this.softRefCache = new LRUMap(this.softRefCount);
/*  96:201 */     this.referenceQueue = new ReferenceQueue();
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void readObject(ObjectInputStream in)
/* 100:    */     throws IOException, ClassNotFoundException
/* 101:    */   {
/* 102:205 */     in.defaultReadObject();
/* 103:206 */     init();
/* 104:    */   }
/* 105:    */   
/* 106:    */   private void clearObsoleteReferences()
/* 107:    */   {
/* 108:    */     KeyedSoftReference obsoleteRef;
/* 109:212 */     while ((obsoleteRef = (KeyedSoftReference)this.referenceQueue.poll()) != null)
/* 110:    */     {
/* 111:213 */       Object key = obsoleteRef.getKey();
/* 112:214 */       this.softRefCache.remove(key);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   private static class KeyedSoftReference
/* 117:    */     extends SoftReference
/* 118:    */   {
/* 119:    */     private final Object key;
/* 120:    */     
/* 121:    */     private KeyedSoftReference(Object key, Object value, ReferenceQueue q)
/* 122:    */     {
/* 123:223 */       super(q);
/* 124:224 */       this.key = key;
/* 125:    */     }
/* 126:    */     
/* 127:    */     private Object getKey()
/* 128:    */     {
/* 129:228 */       return this.key;
/* 130:    */     }
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.SoftLimitMRUCache
 * JD-Core Version:    0.7.0.1
 */