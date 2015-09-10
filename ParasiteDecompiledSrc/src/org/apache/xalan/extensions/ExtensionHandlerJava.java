/*  1:   */ package org.apache.xalan.extensions;
/*  2:   */ 
/*  3:   */ import java.util.Hashtable;
/*  4:   */ 
/*  5:   */ public abstract class ExtensionHandlerJava
/*  6:   */   extends ExtensionHandler
/*  7:   */ {
/*  8:36 */   protected String m_className = "";
/*  9:39 */   private Hashtable m_cachedMethods = new Hashtable();
/* 10:   */   
/* 11:   */   protected ExtensionHandlerJava(String namespaceUri, String scriptLang, String className)
/* 12:   */   {
/* 13:59 */     super(namespaceUri, scriptLang);
/* 14:   */     
/* 15:61 */     this.m_className = className;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Object getFromCache(Object methodKey, Object objType, Object[] methodArgs)
/* 19:   */   {
/* 20:79 */     return this.m_cachedMethods.get(methodKey);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object putToCache(Object methodKey, Object objType, Object[] methodArgs, Object methodObj)
/* 24:   */   {
/* 25:98 */     return this.m_cachedMethods.put(methodKey, methodObj);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.ExtensionHandlerJava
 * JD-Core Version:    0.7.0.1
 */