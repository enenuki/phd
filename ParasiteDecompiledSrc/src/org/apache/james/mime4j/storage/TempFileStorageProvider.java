/*   1:    */ package org.apache.james.mime4j.storage;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.FileOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.io.OutputStream;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.Set;
/*  13:    */ 
/*  14:    */ public class TempFileStorageProvider
/*  15:    */   extends AbstractStorageProvider
/*  16:    */ {
/*  17:    */   private static final String DEFAULT_PREFIX = "m4j";
/*  18:    */   private final String prefix;
/*  19:    */   private final String suffix;
/*  20:    */   private final File directory;
/*  21:    */   
/*  22:    */   public TempFileStorageProvider()
/*  23:    */   {
/*  24: 59 */     this("m4j", null, null);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public TempFileStorageProvider(File directory)
/*  28:    */   {
/*  29: 67 */     this("m4j", null, directory);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public TempFileStorageProvider(String prefix, String suffix, File directory)
/*  33:    */   {
/*  34: 91 */     if ((prefix == null) || (prefix.length() < 3)) {
/*  35: 92 */       throw new IllegalArgumentException("invalid prefix");
/*  36:    */     }
/*  37: 94 */     if ((directory != null) && (!directory.isDirectory()) && (!directory.mkdirs())) {
/*  38: 96 */       throw new IllegalArgumentException("invalid directory");
/*  39:    */     }
/*  40: 98 */     this.prefix = prefix;
/*  41: 99 */     this.suffix = suffix;
/*  42:100 */     this.directory = directory;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public StorageOutputStream createStorageOutputStream()
/*  46:    */     throws IOException
/*  47:    */   {
/*  48:104 */     File file = File.createTempFile(this.prefix, this.suffix, this.directory);
/*  49:105 */     file.deleteOnExit();
/*  50:    */     
/*  51:107 */     return new TempFileStorageOutputStream(file);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private static final class TempFileStorageOutputStream
/*  55:    */     extends StorageOutputStream
/*  56:    */   {
/*  57:    */     private File file;
/*  58:    */     private OutputStream out;
/*  59:    */     
/*  60:    */     public TempFileStorageOutputStream(File file)
/*  61:    */       throws IOException
/*  62:    */     {
/*  63:116 */       this.file = file;
/*  64:117 */       this.out = new FileOutputStream(file);
/*  65:    */     }
/*  66:    */     
/*  67:    */     public void close()
/*  68:    */       throws IOException
/*  69:    */     {
/*  70:122 */       super.close();
/*  71:123 */       this.out.close();
/*  72:    */     }
/*  73:    */     
/*  74:    */     protected void write0(byte[] buffer, int offset, int length)
/*  75:    */       throws IOException
/*  76:    */     {
/*  77:129 */       this.out.write(buffer, offset, length);
/*  78:    */     }
/*  79:    */     
/*  80:    */     protected Storage toStorage0()
/*  81:    */       throws IOException
/*  82:    */     {
/*  83:135 */       return new TempFileStorageProvider.TempFileStorage(this.file);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   private static final class TempFileStorage
/*  88:    */     implements Storage
/*  89:    */   {
/*  90:    */     private File file;
/*  91:143 */     private static final Set<File> filesToDelete = new HashSet();
/*  92:    */     
/*  93:    */     public TempFileStorage(File file)
/*  94:    */     {
/*  95:146 */       this.file = file;
/*  96:    */     }
/*  97:    */     
/*  98:    */     public void delete()
/*  99:    */     {
/* 100:158 */       synchronized (filesToDelete)
/* 101:    */       {
/* 102:159 */         if (this.file != null)
/* 103:    */         {
/* 104:160 */           filesToDelete.add(this.file);
/* 105:161 */           this.file = null;
/* 106:    */         }
/* 107:164 */         Iterator<File> iterator = filesToDelete.iterator();
/* 108:165 */         while (iterator.hasNext())
/* 109:    */         {
/* 110:166 */           File file = (File)iterator.next();
/* 111:167 */           if (file.delete()) {
/* 112:168 */             iterator.remove();
/* 113:    */           }
/* 114:    */         }
/* 115:    */       }
/* 116:    */     }
/* 117:    */     
/* 118:    */     public InputStream getInputStream()
/* 119:    */       throws IOException
/* 120:    */     {
/* 121:175 */       if (this.file == null) {
/* 122:176 */         throw new IllegalStateException("storage has been deleted");
/* 123:    */       }
/* 124:178 */       return new BufferedInputStream(new FileInputStream(this.file));
/* 125:    */     }
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.storage.TempFileStorageProvider
 * JD-Core Version:    0.7.0.1
 */