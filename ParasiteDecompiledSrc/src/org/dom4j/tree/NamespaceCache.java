/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.lang.ref.WeakReference;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.dom4j.Namespace;
/*   7:    */ 
/*   8:    */ public class NamespaceCache
/*   9:    */ {
/*  10:    */   private static final String CONCURRENTREADERHASHMAP_CLASS = "EDU.oswego.cs.dl.util.concurrent.ConcurrentReaderHashMap";
/*  11:    */   protected static Map cache;
/*  12:    */   protected static Map noPrefixCache;
/*  13:    */   
/*  14:    */   static
/*  15:    */   {
/*  16:    */     try
/*  17:    */     {
/*  18: 47 */       Class clazz = Class.forName("java.util.concurrent.ConcurrentHashMap");
/*  19:    */       
/*  20: 49 */       Constructor construct = clazz.getConstructor(new Class[] { Integer.TYPE, Float.TYPE, Integer.TYPE });
/*  21:    */       
/*  22: 51 */       cache = (Map)construct.newInstance(new Object[] { new Integer(11), new Float(0.75F), new Integer(1) });
/*  23:    */       
/*  24: 53 */       noPrefixCache = (Map)construct.newInstance(new Object[] { new Integer(11), new Float(0.75F), new Integer(1) });
/*  25:    */     }
/*  26:    */     catch (Throwable t1)
/*  27:    */     {
/*  28:    */       try
/*  29:    */       {
/*  30: 58 */         Class clazz = Class.forName("EDU.oswego.cs.dl.util.concurrent.ConcurrentReaderHashMap");
/*  31: 59 */         cache = (Map)clazz.newInstance();
/*  32: 60 */         noPrefixCache = (Map)clazz.newInstance();
/*  33:    */       }
/*  34:    */       catch (Throwable t2)
/*  35:    */       {
/*  36: 63 */         cache = new ConcurrentReaderHashMap();
/*  37: 64 */         noPrefixCache = new ConcurrentReaderHashMap();
/*  38:    */       }
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Namespace get(String prefix, String uri)
/*  43:    */   {
/*  44: 80 */     Map uriCache = getURICache(uri);
/*  45: 81 */     WeakReference ref = (WeakReference)uriCache.get(prefix);
/*  46: 82 */     Namespace answer = null;
/*  47: 84 */     if (ref != null) {
/*  48: 85 */       answer = (Namespace)ref.get();
/*  49:    */     }
/*  50: 88 */     if (answer == null) {
/*  51: 89 */       synchronized (uriCache)
/*  52:    */       {
/*  53: 90 */         ref = (WeakReference)uriCache.get(prefix);
/*  54: 92 */         if (ref != null) {
/*  55: 93 */           answer = (Namespace)ref.get();
/*  56:    */         }
/*  57: 96 */         if (answer == null)
/*  58:    */         {
/*  59: 97 */           answer = createNamespace(prefix, uri);
/*  60: 98 */           uriCache.put(prefix, new WeakReference(answer));
/*  61:    */         }
/*  62:    */       }
/*  63:    */     }
/*  64:103 */     return answer;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Namespace get(String uri)
/*  68:    */   {
/*  69:115 */     WeakReference ref = (WeakReference)noPrefixCache.get(uri);
/*  70:116 */     Namespace answer = null;
/*  71:118 */     if (ref != null) {
/*  72:119 */       answer = (Namespace)ref.get();
/*  73:    */     }
/*  74:122 */     if (answer == null) {
/*  75:123 */       synchronized (noPrefixCache)
/*  76:    */       {
/*  77:124 */         ref = (WeakReference)noPrefixCache.get(uri);
/*  78:126 */         if (ref != null) {
/*  79:127 */           answer = (Namespace)ref.get();
/*  80:    */         }
/*  81:130 */         if (answer == null)
/*  82:    */         {
/*  83:131 */           answer = createNamespace("", uri);
/*  84:132 */           noPrefixCache.put(uri, new WeakReference(answer));
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88:137 */     return answer;
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected Map getURICache(String uri)
/*  92:    */   {
/*  93:150 */     Map answer = (Map)cache.get(uri);
/*  94:152 */     if (answer == null) {
/*  95:153 */       synchronized (cache)
/*  96:    */       {
/*  97:154 */         answer = (Map)cache.get(uri);
/*  98:156 */         if (answer == null)
/*  99:    */         {
/* 100:157 */           answer = new ConcurrentReaderHashMap();
/* 101:158 */           cache.put(uri, answer);
/* 102:    */         }
/* 103:    */       }
/* 104:    */     }
/* 105:163 */     return answer;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected Namespace createNamespace(String prefix, String uri)
/* 109:    */   {
/* 110:177 */     return new Namespace(prefix, uri);
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.NamespaceCache
 * JD-Core Version:    0.7.0.1
 */