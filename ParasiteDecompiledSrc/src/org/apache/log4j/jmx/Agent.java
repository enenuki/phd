/*   1:    */ package org.apache.log4j.jmx;
/*   2:    */ 
/*   3:    */ import java.io.InterruptedIOException;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import javax.management.JMException;
/*   7:    */ import javax.management.MBeanServer;
/*   8:    */ import javax.management.MBeanServerFactory;
/*   9:    */ import javax.management.ObjectName;
/*  10:    */ import org.apache.log4j.Category;
/*  11:    */ import org.apache.log4j.Logger;
/*  12:    */ 
/*  13:    */ /**
/*  14:    */  * @deprecated
/*  15:    */  */
/*  16:    */ public class Agent
/*  17:    */ {
/*  18:    */   /**
/*  19:    */    * @deprecated
/*  20:    */    */
/*  21: 45 */   static Logger log = Logger.getLogger(Agent.class);
/*  22:    */   
/*  23:    */   private static Object createServer()
/*  24:    */   {
/*  25: 62 */     Object newInstance = null;
/*  26:    */     try
/*  27:    */     {
/*  28: 64 */       newInstance = Class.forName("com.sun.jdmk.comm.HtmlAdapterServer").newInstance();
/*  29:    */     }
/*  30:    */     catch (ClassNotFoundException ex)
/*  31:    */     {
/*  32: 67 */       throw new RuntimeException(ex.toString());
/*  33:    */     }
/*  34:    */     catch (InstantiationException ex)
/*  35:    */     {
/*  36: 69 */       throw new RuntimeException(ex.toString());
/*  37:    */     }
/*  38:    */     catch (IllegalAccessException ex)
/*  39:    */     {
/*  40: 71 */       throw new RuntimeException(ex.toString());
/*  41:    */     }
/*  42: 73 */     return newInstance;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private static void startServer(Object server)
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49: 84 */       server.getClass().getMethod("start", new Class[0]).invoke(server, new Object[0]);
/*  50:    */     }
/*  51:    */     catch (InvocationTargetException ex)
/*  52:    */     {
/*  53: 87 */       Throwable cause = ex.getTargetException();
/*  54: 88 */       if ((cause instanceof RuntimeException)) {
/*  55: 89 */         throw ((RuntimeException)cause);
/*  56:    */       }
/*  57: 90 */       if (cause != null)
/*  58:    */       {
/*  59: 91 */         if (((cause instanceof InterruptedException)) || ((cause instanceof InterruptedIOException))) {
/*  60: 93 */           Thread.currentThread().interrupt();
/*  61:    */         }
/*  62: 95 */         throw new RuntimeException(cause.toString());
/*  63:    */       }
/*  64: 97 */       throw new RuntimeException();
/*  65:    */     }
/*  66:    */     catch (NoSuchMethodException ex)
/*  67:    */     {
/*  68:100 */       throw new RuntimeException(ex.toString());
/*  69:    */     }
/*  70:    */     catch (IllegalAccessException ex)
/*  71:    */     {
/*  72:102 */       throw new RuntimeException(ex.toString());
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   /**
/*  77:    */    * @deprecated
/*  78:    */    */
/*  79:    */   public void start()
/*  80:    */   {
/*  81:113 */     MBeanServer server = MBeanServerFactory.createMBeanServer();
/*  82:114 */     Object html = createServer();
/*  83:    */     try
/*  84:    */     {
/*  85:117 */       log.info("Registering HtmlAdaptorServer instance.");
/*  86:118 */       server.registerMBean(html, new ObjectName("Adaptor:name=html,port=8082"));
/*  87:119 */       log.info("Registering HierarchyDynamicMBean instance.");
/*  88:120 */       HierarchyDynamicMBean hdm = new HierarchyDynamicMBean();
/*  89:121 */       server.registerMBean(hdm, new ObjectName("log4j:hiearchy=default"));
/*  90:    */     }
/*  91:    */     catch (JMException e)
/*  92:    */     {
/*  93:123 */       log.error("Problem while registering MBeans instances.", e);
/*  94:124 */       return;
/*  95:    */     }
/*  96:    */     catch (RuntimeException e)
/*  97:    */     {
/*  98:126 */       log.error("Problem while registering MBeans instances.", e);
/*  99:127 */       return;
/* 100:    */     }
/* 101:129 */     startServer(html);
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.jmx.Agent
 * JD-Core Version:    0.7.0.1
 */