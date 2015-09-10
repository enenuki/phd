/*   1:    */ package org.apache.http.entity.mime.content;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.io.OutputStream;
/*   8:    */ 
/*   9:    */ public class FileBody
/*  10:    */   extends AbstractContentBody
/*  11:    */ {
/*  12:    */   private final File file;
/*  13:    */   private final String filename;
/*  14:    */   private final String charset;
/*  15:    */   
/*  16:    */   public FileBody(File file, String filename, String mimeType, String charset)
/*  17:    */   {
/*  18: 55 */     super(mimeType);
/*  19: 56 */     if (file == null) {
/*  20: 57 */       throw new IllegalArgumentException("File may not be null");
/*  21:    */     }
/*  22: 59 */     this.file = file;
/*  23: 60 */     if (filename != null) {
/*  24: 61 */       this.filename = filename;
/*  25:    */     } else {
/*  26: 63 */       this.filename = file.getName();
/*  27:    */     }
/*  28: 64 */     this.charset = charset;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public FileBody(File file, String mimeType, String charset)
/*  32:    */   {
/*  33: 73 */     this(file, null, mimeType, charset);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public FileBody(File file, String mimeType)
/*  37:    */   {
/*  38: 77 */     this(file, mimeType, null);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public FileBody(File file)
/*  42:    */   {
/*  43: 81 */     this(file, "application/octet-stream");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public InputStream getInputStream()
/*  47:    */     throws IOException
/*  48:    */   {
/*  49: 85 */     return new FileInputStream(this.file);
/*  50:    */   }
/*  51:    */   
/*  52:    */   @Deprecated
/*  53:    */   public void writeTo(OutputStream out, int mode)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56: 93 */     writeTo(out);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void writeTo(OutputStream out)
/*  60:    */     throws IOException
/*  61:    */   {
/*  62: 97 */     if (out == null) {
/*  63: 98 */       throw new IllegalArgumentException("Output stream may not be null");
/*  64:    */     }
/*  65:100 */     InputStream in = new FileInputStream(this.file);
/*  66:    */     try
/*  67:    */     {
/*  68:102 */       byte[] tmp = new byte[4096];
/*  69:    */       int l;
/*  70:104 */       while ((l = in.read(tmp)) != -1) {
/*  71:105 */         out.write(tmp, 0, l);
/*  72:    */       }
/*  73:107 */       out.flush();
/*  74:    */     }
/*  75:    */     finally
/*  76:    */     {
/*  77:109 */       in.close();
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getTransferEncoding()
/*  82:    */   {
/*  83:114 */     return "binary";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getCharset()
/*  87:    */   {
/*  88:118 */     return this.charset;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public long getContentLength()
/*  92:    */   {
/*  93:122 */     return this.file.length();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getFilename()
/*  97:    */   {
/*  98:126 */     return this.filename;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public File getFile()
/* 102:    */   {
/* 103:130 */     return this.file;
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.content.FileBody
 * JD-Core Version:    0.7.0.1
 */