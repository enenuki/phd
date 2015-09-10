/*   1:    */ package com.gargoylesoftware.htmlunit.html.applets;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.io.OutputStream;
/*   9:    */ import java.util.Enumeration;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Set;
/*  14:    */ import java.util.jar.JarEntry;
/*  15:    */ import java.util.jar.JarFile;
/*  16:    */ import org.apache.commons.io.IOUtils;
/*  17:    */ import org.apache.commons.logging.Log;
/*  18:    */ import org.apache.commons.logging.LogFactory;
/*  19:    */ 
/*  20:    */ public class AppletClassLoader
/*  21:    */   extends ClassLoader
/*  22:    */ {
/*  23: 45 */   private static final Log LOG = LogFactory.getLog(AppletClassLoader.class);
/*  24: 47 */   private final Set<String> definedClasses_ = new HashSet();
/*  25: 48 */   private final Map<String, JarFile> jarFiles_ = new HashMap();
/*  26:    */   
/*  27:    */   public AppletClassLoader()
/*  28:    */   {
/*  29: 54 */     super(AppletClassLoader.class.getClassLoader());
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Class<?> loadClass(String name)
/*  33:    */     throws ClassNotFoundException
/*  34:    */   {
/*  35: 62 */     if ((!this.definedClasses_.contains(name)) && (this.jarFiles_.containsKey(name))) {
/*  36: 63 */       defineClass(name);
/*  37:    */     }
/*  38: 65 */     return super.loadClass(name);
/*  39:    */   }
/*  40:    */   
/*  41:    */   private void defineClass(String name)
/*  42:    */   {
/*  43: 69 */     if (LOG.isDebugEnabled()) {
/*  44: 70 */       LOG.debug("Defining class " + name);
/*  45:    */     }
/*  46: 72 */     String classFileName = name.replace('.', '/') + ".class";
/*  47: 73 */     JarFile jarFile = (JarFile)this.jarFiles_.get(name);
/*  48:    */     try
/*  49:    */     {
/*  50: 75 */       InputStream is = jarFile.getInputStream(jarFile.getEntry(classFileName));
/*  51: 76 */       byte[] bytes = IOUtils.toByteArray(is);
/*  52: 77 */       defineClass(name, bytes, 0, bytes.length);
/*  53: 78 */       this.definedClasses_.add(name);
/*  54:    */     }
/*  55:    */     catch (IOException e)
/*  56:    */     {
/*  57: 81 */       throw new RuntimeException(e);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void addToClassPath(WebResponse webResponse)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64: 94 */     readClassesFromJar(webResponse);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void readClassesFromJar(WebResponse webResponse)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70: 98 */     File tmpFile = File.createTempFile("HtmlUnit", "jar");
/*  71: 99 */     tmpFile.deleteOnExit();
/*  72:100 */     OutputStream output = new FileOutputStream(tmpFile);
/*  73:101 */     IOUtils.copy(webResponse.getContentAsStream(), output);
/*  74:102 */     output.close();
/*  75:103 */     JarFile jarFile = new JarFile(tmpFile);
/*  76:104 */     Enumeration<JarEntry> entries = jarFile.entries();
/*  77:105 */     while (entries.hasMoreElements())
/*  78:    */     {
/*  79:106 */       JarEntry entry = (JarEntry)entries.nextElement();
/*  80:107 */       String name = entry.getName();
/*  81:108 */       if (name.endsWith(".class"))
/*  82:    */       {
/*  83:109 */         String className = name.replace('/', '.').substring(0, name.length() - 6);
/*  84:110 */         this.jarFiles_.put(className, jarFile);
/*  85:111 */         if (LOG.isTraceEnabled()) {
/*  86:112 */           LOG.trace("Jar entry: " + className);
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static String readClassName(WebResponse webResponse)
/*  93:    */   {
/*  94:    */     try
/*  95:    */     {
/*  96:125 */       return readClassName(IOUtils.toByteArray(webResponse.getContentAsStream()));
/*  97:    */     }
/*  98:    */     catch (IOException e) {}
/*  99:128 */     return null;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static String readClassName(byte[] bytes)
/* 103:    */   {
/* 104:139 */     StringBuilder sb = new StringBuilder();
/* 105:140 */     int i = 16;
/* 106:141 */     byte b = bytes[16];
/* 107:142 */     while (b != 7)
/* 108:    */     {
/* 109:143 */       sb.append((char)b);
/* 110:144 */       b = bytes[(++i)];
/* 111:    */     }
/* 112:146 */     return sb.toString().replace('/', '.');
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.applets.AppletClassLoader
 * JD-Core Version:    0.7.0.1
 */