/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ import java.io.Closeable;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.io.OutputStream;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.Enumeration;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.jar.JarEntry;
/*  13:    */ import java.util.jar.JarFile;
/*  14:    */ import java.util.zip.ZipEntry;
/*  15:    */ import java.util.zip.ZipOutputStream;
/*  16:    */ 
/*  17:    */ public class JarIndexer
/*  18:    */ {
/*  19:    */   public static Result createJarIndex(File jarFile, Indexer indexer, boolean modify, boolean newJar, boolean verbose)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22: 31 */     File tmpCopy = null;
/*  23: 32 */     ZipOutputStream zo = null;
/*  24: 33 */     OutputStream out = null;
/*  25: 34 */     File outputFile = null;
/*  26:    */     
/*  27: 36 */     JarFile jar = new JarFile(jarFile);
/*  28: 38 */     if (modify)
/*  29:    */     {
/*  30: 39 */       tmpCopy = File.createTempFile(jarFile.getName().substring(0, jarFile.getName().lastIndexOf('.')) + "00", "jmp");
/*  31: 40 */       out = zo = new ZipOutputStream(new FileOutputStream(tmpCopy));
/*  32:    */     }
/*  33: 41 */     else if (newJar)
/*  34:    */     {
/*  35: 42 */       outputFile = new File(jarFile.getAbsolutePath().replace(".jar", "-jandex.jar"));
/*  36: 43 */       out = zo = new ZipOutputStream(new FileOutputStream(outputFile));
/*  37:    */     }
/*  38:    */     else
/*  39:    */     {
/*  40: 46 */       outputFile = new File(jarFile.getAbsolutePath().replace(".jar", "-jar") + ".idx");
/*  41: 47 */       out = new FileOutputStream(outputFile);
/*  42:    */     }
/*  43:    */     try
/*  44:    */     {
/*  45: 51 */       Enumeration<JarEntry> entries = jar.entries();
/*  46: 52 */       while (entries.hasMoreElements())
/*  47:    */       {
/*  48: 53 */         JarEntry entry = (JarEntry)entries.nextElement();
/*  49: 54 */         if (modify)
/*  50:    */         {
/*  51: 55 */           zo.putNextEntry(entry);
/*  52: 56 */           copy(jar.getInputStream(entry), zo);
/*  53:    */         }
/*  54: 59 */         if (entry.getName().endsWith(".class")) {
/*  55:    */           try
/*  56:    */           {
/*  57: 61 */             ClassInfo info = indexer.index(jar.getInputStream(entry));
/*  58: 62 */             if ((verbose) && (info != null)) {
/*  59: 63 */               printIndexEntryInfo(info);
/*  60:    */             }
/*  61:    */           }
/*  62:    */           catch (Exception e)
/*  63:    */           {
/*  64: 65 */             String message = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
/*  65: 66 */             System.err.println("ERROR: Could not index " + entry.getName() + ": " + message);
/*  66: 66 */             if (verbose) {
/*  67: 67 */               e.printStackTrace(System.err);
/*  68:    */             }
/*  69:    */           }
/*  70:    */         }
/*  71:    */       }
/*  72: 72 */       if ((modify) || (newJar)) {
/*  73: 73 */         zo.putNextEntry(new ZipEntry("META-INF/jandex.idx"));
/*  74:    */       }
/*  75: 76 */       IndexWriter writer = new IndexWriter(out);
/*  76: 77 */       Index index = indexer.complete();
/*  77: 78 */       int bytes = writer.write(index);
/*  78:    */       
/*  79: 80 */       out.flush();
/*  80: 81 */       out.close();
/*  81: 82 */       jar.close();
/*  82: 84 */       if (modify)
/*  83:    */       {
/*  84: 85 */         jarFile.delete();
/*  85: 86 */         tmpCopy.renameTo(jarFile);
/*  86: 87 */         tmpCopy = null;
/*  87:    */       }
/*  88: 89 */       return new Result(index, modify ? "META-INF/jandex.idx" : outputFile.getPath(), bytes);
/*  89:    */     }
/*  90:    */     finally
/*  91:    */     {
/*  92: 91 */       safeClose(out);
/*  93: 92 */       safeClose(jar);
/*  94: 93 */       if (tmpCopy != null) {
/*  95: 94 */         tmpCopy.delete();
/*  96:    */       }
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   private static void printIndexEntryInfo(ClassInfo info)
/* 101:    */   {
/* 102: 99 */     System.out.println("Indexed " + info.name() + " (" + info.annotations().size() + " annotations)");
/* 103:    */   }
/* 104:    */   
/* 105:    */   private static void copy(InputStream in, OutputStream out)
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:102 */     byte[] buf = new byte[8192];
/* 109:    */     int len;
/* 110:104 */     while ((len = in.read(buf)) > 0) {
/* 111:105 */       out.write(buf, 0, len);
/* 112:    */     }
/* 113:107 */     out.flush();
/* 114:    */   }
/* 115:    */   
/* 116:    */   private static void safeClose(JarFile close)
/* 117:    */   {
/* 118:    */     try
/* 119:    */     {
/* 120:112 */       close.close();
/* 121:    */     }
/* 122:    */     catch (Exception ignore) {}
/* 123:    */   }
/* 124:    */   
/* 125:    */   private static void safeClose(Closeable close)
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:119 */       close.close();
/* 130:    */     }
/* 131:    */     catch (Exception ignore) {}
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.JarIndexer
 * JD-Core Version:    0.7.0.1
 */