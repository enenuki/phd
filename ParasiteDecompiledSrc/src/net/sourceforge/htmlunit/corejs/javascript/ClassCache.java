/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ public class ClassCache
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = -8866246036237312215L;
/*  11: 57 */   private static final Object AKEY = "ClassCache";
/*  12: 58 */   private volatile boolean cachingIsEnabled = true;
/*  13:    */   private transient HashMap<Class<?>, JavaMembers> classTable;
/*  14:    */   private transient HashMap<JavaAdapter.JavaAdapterSignature, Class<?>> classAdapterCache;
/*  15:    */   private transient HashMap<Class<?>, Object> interfaceAdapterCache;
/*  16:    */   private int generatedClassSerial;
/*  17:    */   private Scriptable associatedScope;
/*  18:    */   
/*  19:    */   public static ClassCache get(Scriptable scope)
/*  20:    */   {
/*  21: 80 */     ClassCache cache = (ClassCache)ScriptableObject.getTopScopeValue(scope, AKEY);
/*  22: 82 */     if (cache == null) {
/*  23: 83 */       throw new RuntimeException("Can't find top level scope for ClassCache.get");
/*  24:    */     }
/*  25: 86 */     return cache;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean associate(ScriptableObject topScope)
/*  29:    */   {
/*  30:102 */     if (topScope.getParentScope() != null) {
/*  31:104 */       throw new IllegalArgumentException();
/*  32:    */     }
/*  33:106 */     if (this == topScope.associateValue(AKEY, this))
/*  34:    */     {
/*  35:107 */       this.associatedScope = topScope;
/*  36:108 */       return true;
/*  37:    */     }
/*  38:110 */     return false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public synchronized void clearCaches()
/*  42:    */   {
/*  43:118 */     this.classTable = null;
/*  44:119 */     this.classAdapterCache = null;
/*  45:120 */     this.interfaceAdapterCache = null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final boolean isCachingEnabled()
/*  49:    */   {
/*  50:129 */     return this.cachingIsEnabled;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public synchronized void setCachingEnabled(boolean enabled)
/*  54:    */   {
/*  55:152 */     if (enabled == this.cachingIsEnabled) {
/*  56:153 */       return;
/*  57:    */     }
/*  58:154 */     if (!enabled) {
/*  59:155 */       clearCaches();
/*  60:    */     }
/*  61:156 */     this.cachingIsEnabled = enabled;
/*  62:    */   }
/*  63:    */   
/*  64:    */   Map<Class<?>, JavaMembers> getClassCacheMap()
/*  65:    */   {
/*  66:163 */     if (this.classTable == null) {
/*  67:164 */       this.classTable = new HashMap();
/*  68:    */     }
/*  69:166 */     return this.classTable;
/*  70:    */   }
/*  71:    */   
/*  72:    */   Map<JavaAdapter.JavaAdapterSignature, Class<?>> getInterfaceAdapterCacheMap()
/*  73:    */   {
/*  74:171 */     if (this.classAdapterCache == null) {
/*  75:172 */       this.classAdapterCache = new HashMap();
/*  76:    */     }
/*  77:174 */     return this.classAdapterCache;
/*  78:    */   }
/*  79:    */   
/*  80:    */   /**
/*  81:    */    * @deprecated
/*  82:    */    */
/*  83:    */   public boolean isInvokerOptimizationEnabled()
/*  84:    */   {
/*  85:184 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   /**
/*  89:    */    * @deprecated
/*  90:    */    */
/*  91:    */   public synchronized void setInvokerOptimizationEnabled(boolean enabled) {}
/*  92:    */   
/*  93:    */   public final synchronized int newClassSerialNumber()
/*  94:    */   {
/*  95:206 */     return ++this.generatedClassSerial;
/*  96:    */   }
/*  97:    */   
/*  98:    */   Object getInterfaceAdapter(Class<?> cl)
/*  99:    */   {
/* 100:211 */     return this.interfaceAdapterCache == null ? null : this.interfaceAdapterCache.get(cl);
/* 101:    */   }
/* 102:    */   
/* 103:    */   synchronized void cacheInterfaceAdapter(Class<?> cl, Object iadapter)
/* 104:    */   {
/* 105:218 */     if (this.cachingIsEnabled)
/* 106:    */     {
/* 107:219 */       if (this.interfaceAdapterCache == null) {
/* 108:220 */         this.interfaceAdapterCache = new HashMap();
/* 109:    */       }
/* 110:222 */       this.interfaceAdapterCache.put(cl, iadapter);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   Scriptable getAssociatedScope()
/* 115:    */   {
/* 116:227 */     return this.associatedScope;
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ClassCache
 * JD-Core Version:    0.7.0.1
 */