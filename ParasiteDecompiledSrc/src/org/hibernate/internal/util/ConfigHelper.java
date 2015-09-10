/*   1:    */ package org.hibernate.internal.util;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.Reader;
/*   7:    */ import java.net.MalformedURLException;
/*   8:    */ import java.net.URL;
/*   9:    */ import java.util.Properties;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.cfg.Environment;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.jboss.logging.Logger;
/*  14:    */ 
/*  15:    */ public final class ConfigHelper
/*  16:    */ {
/*  17: 49 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ConfigHelper.class.getName());
/*  18:    */   
/*  19:    */   public static URL locateConfig(String path)
/*  20:    */   {
/*  21:    */     try
/*  22:    */     {
/*  23: 61 */       return new URL(path);
/*  24:    */     }
/*  25:    */     catch (MalformedURLException e) {}
/*  26: 64 */     return findAsResource(path);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static URL findAsResource(String path)
/*  30:    */   {
/*  31: 77 */     URL url = null;
/*  32:    */     
/*  33:    */ 
/*  34:    */ 
/*  35: 81 */     ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/*  36: 82 */     if (contextClassLoader != null) {
/*  37: 83 */       url = contextClassLoader.getResource(path);
/*  38:    */     }
/*  39: 85 */     if (url != null) {
/*  40: 86 */       return url;
/*  41:    */     }
/*  42: 89 */     url = ConfigHelper.class.getClassLoader().getResource(path);
/*  43: 90 */     if (url != null) {
/*  44: 91 */       return url;
/*  45:    */     }
/*  46: 94 */     url = ClassLoader.getSystemClassLoader().getResource(path);
/*  47:    */     
/*  48:    */ 
/*  49: 97 */     return url;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static InputStream getConfigStream(String path)
/*  53:    */     throws HibernateException
/*  54:    */   {
/*  55:109 */     URL url = locateConfig(path);
/*  56:111 */     if (url == null)
/*  57:    */     {
/*  58:112 */       String msg = LOG.unableToLocateConfigFile(path);
/*  59:113 */       LOG.error(msg);
/*  60:114 */       throw new HibernateException(msg);
/*  61:    */     }
/*  62:    */     try
/*  63:    */     {
/*  64:118 */       return url.openStream();
/*  65:    */     }
/*  66:    */     catch (IOException e)
/*  67:    */     {
/*  68:121 */       throw new HibernateException("Unable to open config file: " + path, e);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static Reader getConfigStreamReader(String path)
/*  73:    */     throws HibernateException
/*  74:    */   {
/*  75:135 */     return new InputStreamReader(getConfigStream(path));
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static Properties getConfigProperties(String path)
/*  79:    */     throws HibernateException
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:146 */       Properties properties = new Properties();
/*  84:147 */       properties.load(getConfigStream(path));
/*  85:148 */       return properties;
/*  86:    */     }
/*  87:    */     catch (IOException e)
/*  88:    */     {
/*  89:151 */       throw new HibernateException("Unable to load properties from specified config file: " + path, e);
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static InputStream getResourceAsStream(String resource)
/*  94:    */   {
/*  95:158 */     String stripped = resource.startsWith("/") ? resource.substring(1) : resource;
/*  96:    */     
/*  97:    */ 
/*  98:161 */     InputStream stream = null;
/*  99:162 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 100:163 */     if (classLoader != null) {
/* 101:164 */       stream = classLoader.getResourceAsStream(stripped);
/* 102:    */     }
/* 103:166 */     if (stream == null) {
/* 104:167 */       stream = Environment.class.getResourceAsStream(resource);
/* 105:    */     }
/* 106:169 */     if (stream == null) {
/* 107:170 */       stream = Environment.class.getClassLoader().getResourceAsStream(stripped);
/* 108:    */     }
/* 109:172 */     if (stream == null) {
/* 110:173 */       throw new HibernateException(resource + " not found");
/* 111:    */     }
/* 112:175 */     return stream;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static InputStream getUserResourceAsStream(String resource)
/* 116:    */   {
/* 117:180 */     boolean hasLeadingSlash = resource.startsWith("/");
/* 118:181 */     String stripped = hasLeadingSlash ? resource.substring(1) : resource;
/* 119:    */     
/* 120:183 */     InputStream stream = null;
/* 121:    */     
/* 122:185 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 123:186 */     if (classLoader != null)
/* 124:    */     {
/* 125:187 */       stream = classLoader.getResourceAsStream(resource);
/* 126:188 */       if ((stream == null) && (hasLeadingSlash)) {
/* 127:189 */         stream = classLoader.getResourceAsStream(stripped);
/* 128:    */       }
/* 129:    */     }
/* 130:193 */     if (stream == null) {
/* 131:194 */       stream = Environment.class.getClassLoader().getResourceAsStream(resource);
/* 132:    */     }
/* 133:196 */     if ((stream == null) && (hasLeadingSlash)) {
/* 134:197 */       stream = Environment.class.getClassLoader().getResourceAsStream(stripped);
/* 135:    */     }
/* 136:200 */     if (stream == null) {
/* 137:201 */       throw new HibernateException(resource + " not found");
/* 138:    */     }
/* 139:204 */     return stream;
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.ConfigHelper
 * JD-Core Version:    0.7.0.1
 */