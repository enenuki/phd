/*  1:   */ package org.apache.log4j.xml;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayInputStream;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import org.apache.log4j.helpers.LogLog;
/*  6:   */ import org.xml.sax.EntityResolver;
/*  7:   */ import org.xml.sax.InputSource;
/*  8:   */ 
/*  9:   */ public class Log4jEntityResolver
/* 10:   */   implements EntityResolver
/* 11:   */ {
/* 12:   */   private static final String PUBLIC_ID = "-//APACHE//DTD LOG4J 1.2//EN";
/* 13:   */   
/* 14:   */   public InputSource resolveEntity(String publicId, String systemId)
/* 15:   */   {
/* 16:38 */     if ((systemId.endsWith("log4j.dtd")) || ("-//APACHE//DTD LOG4J 1.2//EN".equals(publicId)))
/* 17:   */     {
/* 18:39 */       Class clazz = getClass();
/* 19:40 */       InputStream in = clazz.getResourceAsStream("/org/apache/log4j/xml/log4j.dtd");
/* 20:41 */       if (in == null)
/* 21:   */       {
/* 22:42 */         LogLog.warn("Could not find [log4j.dtd] using [" + clazz.getClassLoader() + "] class loader, parsed without DTD.");
/* 23:   */         
/* 24:44 */         in = new ByteArrayInputStream(new byte[0]);
/* 25:   */       }
/* 26:46 */       return new InputSource(in);
/* 27:   */     }
/* 28:48 */     return null;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.xml.Log4jEntityResolver
 * JD-Core Version:    0.7.0.1
 */