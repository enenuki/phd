/*   1:    */ package javassist.scopedpool;
/*   2:    */ 
/*   3:    */ import java.lang.ref.ReferenceQueue;
/*   4:    */ import java.lang.ref.SoftReference;
/*   5:    */ import java.util.AbstractMap;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ 
/*  10:    */ public class SoftValueHashMap
/*  11:    */   extends AbstractMap
/*  12:    */   implements Map
/*  13:    */ {
/*  14:    */   private Map hash;
/*  15:    */   
/*  16:    */   private static class SoftValueRef
/*  17:    */     extends SoftReference
/*  18:    */   {
/*  19:    */     public Object key;
/*  20:    */     
/*  21:    */     private SoftValueRef(Object key, Object val, ReferenceQueue q)
/*  22:    */     {
/*  23: 37 */       super(q);
/*  24: 38 */       this.key = key;
/*  25:    */     }
/*  26:    */     
/*  27:    */     private static SoftValueRef create(Object key, Object val, ReferenceQueue q)
/*  28:    */     {
/*  29: 43 */       if (val == null) {
/*  30: 44 */         return null;
/*  31:    */       }
/*  32: 46 */       return new SoftValueRef(key, val, q);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Set entrySet()
/*  37:    */   {
/*  38: 55 */     processQueue();
/*  39: 56 */     return this.hash.entrySet();
/*  40:    */   }
/*  41:    */   
/*  42: 63 */   private ReferenceQueue queue = new ReferenceQueue();
/*  43:    */   
/*  44:    */   private void processQueue()
/*  45:    */   {
/*  46:    */     SoftValueRef ref;
/*  47: 71 */     while ((ref = (SoftValueRef)this.queue.poll()) != null) {
/*  48: 72 */       if (ref == (SoftValueRef)this.hash.get(ref.key)) {
/*  49: 75 */         this.hash.remove(ref.key);
/*  50:    */       }
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public SoftValueHashMap(int initialCapacity, float loadFactor)
/*  55:    */   {
/*  56: 97 */     this.hash = new HashMap(initialCapacity, loadFactor);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public SoftValueHashMap(int initialCapacity)
/*  60:    */   {
/*  61:111 */     this.hash = new HashMap(initialCapacity);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public SoftValueHashMap()
/*  65:    */   {
/*  66:119 */     this.hash = new HashMap();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public SoftValueHashMap(Map t)
/*  70:    */   {
/*  71:132 */     this(Math.max(2 * t.size(), 11), 0.75F);
/*  72:133 */     putAll(t);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int size()
/*  76:    */   {
/*  77:145 */     processQueue();
/*  78:146 */     return this.hash.size();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isEmpty()
/*  82:    */   {
/*  83:153 */     processQueue();
/*  84:154 */     return this.hash.isEmpty();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean containsKey(Object key)
/*  88:    */   {
/*  89:165 */     processQueue();
/*  90:166 */     return this.hash.containsKey(key);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Object get(Object key)
/*  94:    */   {
/*  95:180 */     processQueue();
/*  96:181 */     SoftReference ref = (SoftReference)this.hash.get(key);
/*  97:182 */     if (ref != null) {
/*  98:183 */       return ref.get();
/*  99:    */     }
/* 100:184 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Object put(Object key, Object value)
/* 104:    */   {
/* 105:203 */     processQueue();
/* 106:204 */     Object rtn = this.hash.put(key, SoftValueRef.create(key, value, this.queue));
/* 107:205 */     if (rtn != null) {
/* 108:206 */       rtn = ((SoftReference)rtn).get();
/* 109:    */     }
/* 110:207 */     return rtn;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Object remove(Object key)
/* 114:    */   {
/* 115:221 */     processQueue();
/* 116:222 */     return this.hash.remove(key);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void clear()
/* 120:    */   {
/* 121:229 */     processQueue();
/* 122:230 */     this.hash.clear();
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.scopedpool.SoftValueHashMap
 * JD-Core Version:    0.7.0.1
 */