/*  1:   */ package org.apache.log4j.lf5;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.URL;
/*  5:   */ import org.apache.log4j.PropertyConfigurator;
/*  6:   */ import org.apache.log4j.spi.Configurator;
/*  7:   */ import org.apache.log4j.spi.LoggerRepository;
/*  8:   */ 
/*  9:   */ public class DefaultLF5Configurator
/* 10:   */   implements Configurator
/* 11:   */ {
/* 12:   */   public static void configure()
/* 13:   */     throws IOException
/* 14:   */   {
/* 15:78 */     String resource = "/org/apache/log4j/lf5/config/defaultconfig.properties";
/* 16:   */     
/* 17:80 */     URL configFileResource = DefaultLF5Configurator.class.getResource(resource);
/* 18:83 */     if (configFileResource != null) {
/* 19:84 */       PropertyConfigurator.configure(configFileResource);
/* 20:   */     } else {
/* 21:86 */       throw new IOException("Error: Unable to open the resource" + resource);
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void doConfigure(URL configURL, LoggerRepository repository)
/* 26:   */   {
/* 27:97 */     throw new IllegalStateException("This class should NOT be instantiated!");
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.DefaultLF5Configurator
 * JD-Core Version:    0.7.0.1
 */