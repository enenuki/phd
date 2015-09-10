/*  1:   */ package org.apache.http.entity.mime.content;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ 
/*  7:   */ public class InputStreamBody
/*  8:   */   extends AbstractContentBody
/*  9:   */ {
/* 10:   */   private final InputStream in;
/* 11:   */   private final String filename;
/* 12:   */   
/* 13:   */   public InputStreamBody(InputStream in, String mimeType, String filename)
/* 14:   */   {
/* 15:46 */     super(mimeType);
/* 16:47 */     if (in == null) {
/* 17:48 */       throw new IllegalArgumentException("Input stream may not be null");
/* 18:   */     }
/* 19:50 */     this.in = in;
/* 20:51 */     this.filename = filename;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public InputStreamBody(InputStream in, String filename)
/* 24:   */   {
/* 25:55 */     this(in, "application/octet-stream", filename);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public InputStream getInputStream()
/* 29:   */   {
/* 30:59 */     return this.in;
/* 31:   */   }
/* 32:   */   
/* 33:   */   @Deprecated
/* 34:   */   public void writeTo(OutputStream out, int mode)
/* 35:   */     throws IOException
/* 36:   */   {
/* 37:67 */     writeTo(out);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void writeTo(OutputStream out)
/* 41:   */     throws IOException
/* 42:   */   {
/* 43:71 */     if (out == null) {
/* 44:72 */       throw new IllegalArgumentException("Output stream may not be null");
/* 45:   */     }
/* 46:   */     try
/* 47:   */     {
/* 48:75 */       byte[] tmp = new byte[4096];
/* 49:   */       int l;
/* 50:77 */       while ((l = this.in.read(tmp)) != -1) {
/* 51:78 */         out.write(tmp, 0, l);
/* 52:   */       }
/* 53:80 */       out.flush();
/* 54:   */     }
/* 55:   */     finally
/* 56:   */     {
/* 57:82 */       this.in.close();
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String getTransferEncoding()
/* 62:   */   {
/* 63:87 */     return "binary";
/* 64:   */   }
/* 65:   */   
/* 66:   */   public String getCharset()
/* 67:   */   {
/* 68:91 */     return null;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public long getContentLength()
/* 72:   */   {
/* 73:95 */     return -1L;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public String getFilename()
/* 77:   */   {
/* 78:99 */     return this.filename;
/* 79:   */   }
/* 80:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.content.InputStreamBody
 * JD-Core Version:    0.7.0.1
 */