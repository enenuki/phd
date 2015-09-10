/*  1:   */ package org.apache.http.entity;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.FileInputStream;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.io.InputStream;
/*  7:   */ import java.io.OutputStream;
/*  8:   */ 
/*  9:   */ public class FileEntity
/* 10:   */   extends AbstractHttpEntity
/* 11:   */   implements Cloneable
/* 12:   */ {
/* 13:   */   protected final File file;
/* 14:   */   
/* 15:   */   public FileEntity(File file, String contentType)
/* 16:   */   {
/* 17:47 */     if (file == null) {
/* 18:48 */       throw new IllegalArgumentException("File may not be null");
/* 19:   */     }
/* 20:50 */     this.file = file;
/* 21:51 */     setContentType(contentType);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean isRepeatable()
/* 25:   */   {
/* 26:55 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public long getContentLength()
/* 30:   */   {
/* 31:59 */     return this.file.length();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public InputStream getContent()
/* 35:   */     throws IOException
/* 36:   */   {
/* 37:63 */     return new FileInputStream(this.file);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void writeTo(OutputStream outstream)
/* 41:   */     throws IOException
/* 42:   */   {
/* 43:67 */     if (outstream == null) {
/* 44:68 */       throw new IllegalArgumentException("Output stream may not be null");
/* 45:   */     }
/* 46:70 */     InputStream instream = new FileInputStream(this.file);
/* 47:   */     try
/* 48:   */     {
/* 49:72 */       byte[] tmp = new byte[4096];
/* 50:   */       int l;
/* 51:74 */       while ((l = instream.read(tmp)) != -1) {
/* 52:75 */         outstream.write(tmp, 0, l);
/* 53:   */       }
/* 54:77 */       outstream.flush();
/* 55:   */     }
/* 56:   */     finally
/* 57:   */     {
/* 58:79 */       instream.close();
/* 59:   */     }
/* 60:   */   }
/* 61:   */   
/* 62:   */   public boolean isStreaming()
/* 63:   */   {
/* 64:89 */     return false;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public Object clone()
/* 68:   */     throws CloneNotSupportedException
/* 69:   */   {
/* 70:95 */     return super.clone();
/* 71:   */   }
/* 72:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.FileEntity
 * JD-Core Version:    0.7.0.1
 */