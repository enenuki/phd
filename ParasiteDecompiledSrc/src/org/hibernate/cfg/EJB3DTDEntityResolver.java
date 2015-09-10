/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import org.hibernate.internal.CoreMessageLogger;
/*   5:    */ import org.hibernate.internal.util.xml.DTDEntityResolver;
/*   6:    */ import org.jboss.logging.Logger;
/*   7:    */ import org.xml.sax.EntityResolver;
/*   8:    */ import org.xml.sax.InputSource;
/*   9:    */ 
/*  10:    */ public class EJB3DTDEntityResolver
/*  11:    */   extends DTDEntityResolver
/*  12:    */ {
/*  13: 42 */   public static final EntityResolver INSTANCE = new EJB3DTDEntityResolver();
/*  14: 44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, EJB3DTDEntityResolver.class.getName());
/*  15: 46 */   boolean resolved = false;
/*  16:    */   
/*  17:    */   public boolean isResolved()
/*  18:    */   {
/*  19: 53 */     return this.resolved;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public InputSource resolveEntity(String publicId, String systemId)
/*  23:    */   {
/*  24: 58 */     LOG.tracev("Resolving XML entity {0} : {1}", publicId, systemId);
/*  25: 59 */     InputSource is = super.resolveEntity(publicId, systemId);
/*  26: 60 */     if (is == null)
/*  27:    */     {
/*  28: 61 */       if (systemId != null) {
/*  29: 62 */         if (systemId.endsWith("orm_1_0.xsd"))
/*  30:    */         {
/*  31: 63 */           InputStream dtdStream = getStreamFromClasspath("orm_1_0.xsd");
/*  32: 64 */           InputSource source = buildInputSource(publicId, systemId, dtdStream, false);
/*  33: 65 */           if (source != null) {
/*  34: 65 */             return source;
/*  35:    */           }
/*  36:    */         }
/*  37: 67 */         else if (systemId.endsWith("orm_2_0.xsd"))
/*  38:    */         {
/*  39: 68 */           InputStream dtdStream = getStreamFromClasspath("orm_2_0.xsd");
/*  40: 69 */           InputSource source = buildInputSource(publicId, systemId, dtdStream, false);
/*  41: 70 */           if (source != null) {
/*  42: 70 */             return source;
/*  43:    */           }
/*  44:    */         }
/*  45: 72 */         else if (systemId.endsWith("persistence_1_0.xsd"))
/*  46:    */         {
/*  47: 73 */           InputStream dtdStream = getStreamFromClasspath("persistence_1_0.xsd");
/*  48: 74 */           InputSource source = buildInputSource(publicId, systemId, dtdStream, true);
/*  49: 75 */           if (source != null) {
/*  50: 75 */             return source;
/*  51:    */           }
/*  52:    */         }
/*  53: 77 */         else if (systemId.endsWith("persistence_2_0.xsd"))
/*  54:    */         {
/*  55: 78 */           InputStream dtdStream = getStreamFromClasspath("persistence_2_0.xsd");
/*  56: 79 */           InputSource source = buildInputSource(publicId, systemId, dtdStream, true);
/*  57: 80 */           if (source != null) {
/*  58: 80 */             return source;
/*  59:    */           }
/*  60:    */         }
/*  61:    */       }
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65: 85 */       this.resolved = true;
/*  66: 86 */       return is;
/*  67:    */     }
/*  68: 89 */     return null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private InputSource buildInputSource(String publicId, String systemId, InputStream dtdStream, boolean resolved)
/*  72:    */   {
/*  73: 93 */     if (dtdStream == null)
/*  74:    */     {
/*  75: 94 */       LOG.tracev("Unable to locate [{0}] on classpath", systemId);
/*  76: 95 */       return null;
/*  77:    */     }
/*  78: 97 */     LOG.tracev("Located [{0}] in classpath", systemId);
/*  79: 98 */     InputSource source = new InputSource(dtdStream);
/*  80: 99 */     source.setPublicId(publicId);
/*  81:100 */     source.setSystemId(systemId);
/*  82:101 */     this.resolved = resolved;
/*  83:102 */     return source;
/*  84:    */   }
/*  85:    */   
/*  86:    */   private InputStream getStreamFromClasspath(String fileName)
/*  87:    */   {
/*  88:106 */     LOG.trace("Recognized JPA ORM namespace; attempting to resolve on classpath under org/hibernate/ejb");
/*  89:107 */     String path = "org/hibernate/ejb/" + fileName;
/*  90:108 */     InputStream dtdStream = resolveInHibernateNamespace(path);
/*  91:109 */     return dtdStream;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.EJB3DTDEntityResolver
 * JD-Core Version:    0.7.0.1
 */