/*   1:    */ package org.hibernate.internal.util.jndi;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Map.Entry;
/*   6:    */ import java.util.Properties;
/*   7:    */ import javax.naming.Context;
/*   8:    */ import javax.naming.InitialContext;
/*   9:    */ import javax.naming.Name;
/*  10:    */ import javax.naming.NameNotFoundException;
/*  11:    */ import javax.naming.NameParser;
/*  12:    */ import javax.naming.NamingException;
/*  13:    */ 
/*  14:    */ @Deprecated
/*  15:    */ public final class JndiHelper
/*  16:    */ {
/*  17:    */   public static Properties extractJndiProperties(Map configurationValues)
/*  18:    */   {
/*  19: 57 */     Properties jndiProperties = new Properties();
/*  20: 59 */     for (Map.Entry entry : configurationValues.entrySet()) {
/*  21: 60 */       if (String.class.isInstance(entry.getKey()))
/*  22:    */       {
/*  23: 63 */         String propertyName = (String)entry.getKey();
/*  24: 64 */         Object propertyValue = entry.getValue();
/*  25: 65 */         if (propertyName.startsWith("hibernate.jndi")) {
/*  26: 68 */           if ("hibernate.jndi.class".equals(propertyName))
/*  27:    */           {
/*  28: 69 */             if (propertyValue != null) {
/*  29: 70 */               jndiProperties.put("java.naming.factory.initial", propertyValue);
/*  30:    */             }
/*  31:    */           }
/*  32: 73 */           else if ("hibernate.jndi.url".equals(propertyName))
/*  33:    */           {
/*  34: 74 */             if (propertyValue != null) {
/*  35: 75 */               jndiProperties.put("java.naming.provider.url", propertyValue);
/*  36:    */             }
/*  37:    */           }
/*  38:    */           else
/*  39:    */           {
/*  40: 79 */             String passThruPropertyname = propertyName.substring("hibernate.jndi".length() + 1);
/*  41: 80 */             jndiProperties.put(passThruPropertyname, propertyValue);
/*  42:    */           }
/*  43:    */         }
/*  44:    */       }
/*  45:    */     }
/*  46: 85 */     return jndiProperties;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static InitialContext getInitialContext(Properties props)
/*  50:    */     throws NamingException
/*  51:    */   {
/*  52: 89 */     Hashtable hash = extractJndiProperties(props);
/*  53: 90 */     return hash.size() == 0 ? new InitialContext() : new InitialContext(hash);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static void bind(Context ctx, String name, Object val)
/*  57:    */     throws NamingException
/*  58:    */   {
/*  59:    */     try
/*  60:    */     {
/*  61:106 */       ctx.rebind(name, val);
/*  62:    */     }
/*  63:    */     catch (Exception e)
/*  64:    */     {
/*  65:109 */       Name n = ctx.getNameParser("").parse(name);
/*  66:110 */       while (n.size() > 1)
/*  67:    */       {
/*  68:111 */         String ctxName = n.get(0);
/*  69:    */         
/*  70:113 */         Context subctx = null;
/*  71:    */         try
/*  72:    */         {
/*  73:115 */           subctx = (Context)ctx.lookup(ctxName);
/*  74:    */         }
/*  75:    */         catch (NameNotFoundException ignore) {}
/*  76:120 */         if (subctx != null) {
/*  77:121 */           ctx = subctx;
/*  78:    */         } else {
/*  79:124 */           ctx = ctx.createSubcontext(ctxName);
/*  80:    */         }
/*  81:126 */         n = n.getSuffix(1);
/*  82:    */       }
/*  83:128 */       ctx.rebind(n, val);
/*  84:    */     }
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.jndi.JndiHelper
 * JD-Core Version:    0.7.0.1
 */