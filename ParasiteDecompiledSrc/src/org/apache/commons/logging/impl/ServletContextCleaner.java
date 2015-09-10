/*   1:    */ package org.apache.commons.logging.impl;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import javax.servlet.ServletContextEvent;
/*   7:    */ import javax.servlet.ServletContextListener;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ 
/*  10:    */ public class ServletContextCleaner
/*  11:    */   implements ServletContextListener
/*  12:    */ {
/*  13: 54 */   private Class[] RELEASE_SIGNATURE = { ClassLoader.class };
/*  14:    */   
/*  15:    */   public void contextDestroyed(ServletContextEvent sce)
/*  16:    */   {
/*  17: 62 */     ClassLoader tccl = Thread.currentThread().getContextClassLoader();
/*  18:    */     
/*  19: 64 */     Object[] params = new Object[1];
/*  20: 65 */     params[0] = tccl;
/*  21:    */     
/*  22:    */ 
/*  23:    */ 
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52: 97 */     ClassLoader loader = tccl;
/*  53: 98 */     while (loader != null) {
/*  54:    */       try
/*  55:    */       {
/*  56:103 */         Class logFactoryClass = loader.loadClass("org.apache.commons.logging.LogFactory");
/*  57:104 */         Method releaseMethod = logFactoryClass.getMethod("release", this.RELEASE_SIGNATURE);
/*  58:105 */         releaseMethod.invoke(null, params);
/*  59:106 */         loader = logFactoryClass.getClassLoader().getParent();
/*  60:    */       }
/*  61:    */       catch (ClassNotFoundException ex)
/*  62:    */       {
/*  63:110 */         loader = null;
/*  64:    */       }
/*  65:    */       catch (NoSuchMethodException ex)
/*  66:    */       {
/*  67:113 */         System.err.println("LogFactory instance found which does not support release method!");
/*  68:114 */         loader = null;
/*  69:    */       }
/*  70:    */       catch (IllegalAccessException ex)
/*  71:    */       {
/*  72:117 */         System.err.println("LogFactory instance found which is not accessable!");
/*  73:118 */         loader = null;
/*  74:    */       }
/*  75:    */       catch (InvocationTargetException ex)
/*  76:    */       {
/*  77:121 */         System.err.println("LogFactory instance release method failed!");
/*  78:122 */         loader = null;
/*  79:    */       }
/*  80:    */     }
/*  81:129 */     LogFactory.release(tccl);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void contextInitialized(ServletContextEvent sce) {}
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.impl.ServletContextCleaner
 * JD-Core Version:    0.7.0.1
 */