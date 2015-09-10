/*   1:    */ package org.hibernate.internal.util.xml;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import org.hibernate.internal.CoreMessageLogger;
/*   6:    */ import org.hibernate.internal.util.ConfigHelper;
/*   7:    */ import org.jboss.logging.Logger;
/*   8:    */ import org.xml.sax.EntityResolver;
/*   9:    */ import org.xml.sax.InputSource;
/*  10:    */ 
/*  11:    */ public class DTDEntityResolver
/*  12:    */   implements EntityResolver, Serializable
/*  13:    */ {
/*  14: 59 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DTDEntityResolver.class.getName());
/*  15:    */   private static final String HIBERNATE_NAMESPACE = "http://www.hibernate.org/dtd/";
/*  16:    */   private static final String OLD_HIBERNATE_NAMESPACE = "http://hibernate.sourceforge.net/";
/*  17:    */   private static final String USER_NAMESPACE = "classpath://";
/*  18:    */   
/*  19:    */   public InputSource resolveEntity(String publicId, String systemId)
/*  20:    */   {
/*  21: 66 */     InputSource source = null;
/*  22: 67 */     if (systemId != null)
/*  23:    */     {
/*  24: 68 */       LOG.debugf("Trying to resolve system-id [%s]", systemId);
/*  25: 69 */       if (systemId.startsWith("http://www.hibernate.org/dtd/"))
/*  26:    */       {
/*  27: 70 */         LOG.debug("Recognized hibernate namespace; attempting to resolve on classpath under org/hibernate/");
/*  28: 71 */         source = resolveOnClassPath(publicId, systemId, "http://www.hibernate.org/dtd/");
/*  29:    */       }
/*  30: 73 */       else if (systemId.startsWith("http://hibernate.sourceforge.net/"))
/*  31:    */       {
/*  32: 74 */         LOG.recognizedObsoleteHibernateNamespace("http://hibernate.sourceforge.net/", "http://www.hibernate.org/dtd/");
/*  33: 75 */         LOG.debug("Attempting to resolve on classpath under org/hibernate/");
/*  34: 76 */         source = resolveOnClassPath(publicId, systemId, "http://hibernate.sourceforge.net/");
/*  35:    */       }
/*  36: 78 */       else if (systemId.startsWith("classpath://"))
/*  37:    */       {
/*  38: 79 */         LOG.debug("Recognized local namespace; attempting to resolve on classpath");
/*  39: 80 */         String path = systemId.substring("classpath://".length());
/*  40: 81 */         InputStream stream = resolveInLocalNamespace(path);
/*  41: 82 */         if (stream == null)
/*  42:    */         {
/*  43: 83 */           LOG.debugf("Unable to locate [%s] on classpath", systemId);
/*  44:    */         }
/*  45:    */         else
/*  46:    */         {
/*  47: 86 */           LOG.debugf("Located [%s] in classpath", systemId);
/*  48: 87 */           source = new InputSource(stream);
/*  49: 88 */           source.setPublicId(publicId);
/*  50: 89 */           source.setSystemId(systemId);
/*  51:    */         }
/*  52:    */       }
/*  53:    */     }
/*  54: 93 */     return source;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private InputSource resolveOnClassPath(String publicId, String systemId, String namespace)
/*  58:    */   {
/*  59: 97 */     InputSource source = null;
/*  60: 98 */     String path = "org/hibernate/" + systemId.substring(namespace.length());
/*  61: 99 */     InputStream dtdStream = resolveInHibernateNamespace(path);
/*  62:100 */     if (dtdStream == null)
/*  63:    */     {
/*  64:101 */       LOG.debugf("Unable to locate [%s] on classpath", systemId);
/*  65:102 */       if (systemId.substring(namespace.length()).indexOf("2.0") > -1) {
/*  66:103 */         LOG.usingOldDtd();
/*  67:    */       }
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71:107 */       LOG.debugf("Located [%s] in classpath", systemId);
/*  72:108 */       source = new InputSource(dtdStream);
/*  73:109 */       source.setPublicId(publicId);
/*  74:110 */       source.setSystemId(systemId);
/*  75:    */     }
/*  76:112 */     return source;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected InputStream resolveInHibernateNamespace(String path)
/*  80:    */   {
/*  81:116 */     return getClass().getClassLoader().getResourceAsStream(path);
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected InputStream resolveInLocalNamespace(String path)
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:121 */       return ConfigHelper.getUserResourceAsStream(path);
/*  89:    */     }
/*  90:    */     catch (Throwable t) {}
/*  91:124 */     return null;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.xml.DTDEntityResolver
 * JD-Core Version:    0.7.0.1
 */