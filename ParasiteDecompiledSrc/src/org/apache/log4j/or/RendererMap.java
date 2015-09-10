/*   1:    */ package org.apache.log4j.or;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import org.apache.log4j.helpers.Loader;
/*   5:    */ import org.apache.log4j.helpers.LogLog;
/*   6:    */ import org.apache.log4j.helpers.OptionConverter;
/*   7:    */ import org.apache.log4j.spi.RendererSupport;
/*   8:    */ 
/*   9:    */ public class RendererMap
/*  10:    */ {
/*  11:    */   Hashtable map;
/*  12: 35 */   static ObjectRenderer defaultRenderer = new DefaultRenderer();
/*  13:    */   
/*  14:    */   public RendererMap()
/*  15:    */   {
/*  16: 39 */     this.map = new Hashtable();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static void addRenderer(RendererSupport repository, String renderedClassName, String renderingClassName)
/*  20:    */   {
/*  21: 49 */     LogLog.debug("Rendering class: [" + renderingClassName + "], Rendered class: [" + renderedClassName + "].");
/*  22:    */     
/*  23: 51 */     ObjectRenderer renderer = (ObjectRenderer)OptionConverter.instantiateByClassName(renderingClassName, ObjectRenderer.class, null);
/*  24: 55 */     if (renderer == null)
/*  25:    */     {
/*  26: 56 */       LogLog.error("Could not instantiate renderer [" + renderingClassName + "].");
/*  27: 57 */       return;
/*  28:    */     }
/*  29:    */     try
/*  30:    */     {
/*  31: 60 */       Class renderedClass = Loader.loadClass(renderedClassName);
/*  32: 61 */       repository.setRenderer(renderedClass, renderer);
/*  33:    */     }
/*  34:    */     catch (ClassNotFoundException e)
/*  35:    */     {
/*  36: 63 */       LogLog.error("Could not find class [" + renderedClassName + "].", e);
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String findAndRender(Object o)
/*  41:    */   {
/*  42: 77 */     if (o == null) {
/*  43: 78 */       return null;
/*  44:    */     }
/*  45: 80 */     return get(o.getClass()).doRender(o);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ObjectRenderer get(Object o)
/*  49:    */   {
/*  50: 89 */     if (o == null) {
/*  51: 90 */       return null;
/*  52:    */     }
/*  53: 92 */     return get(o.getClass());
/*  54:    */   }
/*  55:    */   
/*  56:    */   public ObjectRenderer get(Class clazz)
/*  57:    */   {
/*  58:148 */     ObjectRenderer r = null;
/*  59:149 */     for (Class c = clazz; c != null; c = c.getSuperclass())
/*  60:    */     {
/*  61:151 */       r = (ObjectRenderer)this.map.get(c);
/*  62:152 */       if (r != null) {
/*  63:153 */         return r;
/*  64:    */       }
/*  65:155 */       r = searchInterfaces(c);
/*  66:156 */       if (r != null) {
/*  67:157 */         return r;
/*  68:    */       }
/*  69:    */     }
/*  70:159 */     return defaultRenderer;
/*  71:    */   }
/*  72:    */   
/*  73:    */   ObjectRenderer searchInterfaces(Class c)
/*  74:    */   {
/*  75:165 */     ObjectRenderer r = (ObjectRenderer)this.map.get(c);
/*  76:166 */     if (r != null) {
/*  77:167 */       return r;
/*  78:    */     }
/*  79:169 */     Class[] ia = c.getInterfaces();
/*  80:170 */     for (int i = 0; i < ia.length; i++)
/*  81:    */     {
/*  82:171 */       r = searchInterfaces(ia[i]);
/*  83:172 */       if (r != null) {
/*  84:173 */         return r;
/*  85:    */       }
/*  86:    */     }
/*  87:176 */     return null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public ObjectRenderer getDefaultRenderer()
/*  91:    */   {
/*  92:182 */     return defaultRenderer;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void clear()
/*  96:    */   {
/*  97:188 */     this.map.clear();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void put(Class clazz, ObjectRenderer or)
/* 101:    */   {
/* 102:196 */     this.map.put(clazz, or);
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.or.RendererMap
 * JD-Core Version:    0.7.0.1
 */