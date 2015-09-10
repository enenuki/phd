/*  1:   */ package org.apache.http.entity.mime.content;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ 
/*  6:   */ public class ByteArrayBody
/*  7:   */   extends AbstractContentBody
/*  8:   */ {
/*  9:   */   private final byte[] data;
/* 10:   */   private final String filename;
/* 11:   */   
/* 12:   */   public ByteArrayBody(byte[] data, String mimeType, String filename)
/* 13:   */   {
/* 14:60 */     super(mimeType);
/* 15:61 */     if (data == null) {
/* 16:62 */       throw new IllegalArgumentException("byte[] may not be null");
/* 17:   */     }
/* 18:64 */     this.data = data;
/* 19:65 */     this.filename = filename;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public ByteArrayBody(byte[] data, String filename)
/* 23:   */   {
/* 24:75 */     this(data, "application/octet-stream", filename);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getFilename()
/* 28:   */   {
/* 29:79 */     return this.filename;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void writeTo(OutputStream out)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:83 */     out.write(this.data);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getCharset()
/* 39:   */   {
/* 40:87 */     return null;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getTransferEncoding()
/* 44:   */   {
/* 45:91 */     return "binary";
/* 46:   */   }
/* 47:   */   
/* 48:   */   public long getContentLength()
/* 49:   */   {
/* 50:95 */     return this.data.length;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.content.ByteArrayBody
 * JD-Core Version:    0.7.0.1
 */