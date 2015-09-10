/*  1:   */ package org.apache.james.mime4j.message;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.InputStreamReader;
/*  6:   */ import java.io.OutputStream;
/*  7:   */ import java.io.Reader;
/*  8:   */ import java.nio.charset.Charset;
/*  9:   */ import org.apache.james.mime4j.codec.CodecUtil;
/* 10:   */ import org.apache.james.mime4j.storage.MultiReferenceStorage;
/* 11:   */ import org.apache.james.mime4j.util.CharsetUtil;
/* 12:   */ 
/* 13:   */ class StorageTextBody
/* 14:   */   extends TextBody
/* 15:   */ {
/* 16:   */   private MultiReferenceStorage storage;
/* 17:   */   private Charset charset;
/* 18:   */   
/* 19:   */   public StorageTextBody(MultiReferenceStorage storage, Charset charset)
/* 20:   */   {
/* 21:42 */     this.storage = storage;
/* 22:43 */     this.charset = charset;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getMimeCharset()
/* 26:   */   {
/* 27:48 */     return CharsetUtil.toMimeCharset(this.charset.name());
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Reader getReader()
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:53 */     return new InputStreamReader(this.storage.getInputStream(), this.charset);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void writeTo(OutputStream out)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:58 */     if (out == null) {
/* 40:59 */       throw new IllegalArgumentException();
/* 41:   */     }
/* 42:61 */     InputStream in = this.storage.getInputStream();
/* 43:62 */     CodecUtil.copy(in, out);
/* 44:63 */     in.close();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public StorageTextBody copy()
/* 48:   */   {
/* 49:68 */     this.storage.addReference();
/* 50:69 */     return new StorageTextBody(this.storage, this.charset);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void dispose()
/* 54:   */   {
/* 55:79 */     if (this.storage != null)
/* 56:   */     {
/* 57:80 */       this.storage.delete();
/* 58:81 */       this.storage = null;
/* 59:   */     }
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.StorageTextBody
 * JD-Core Version:    0.7.0.1
 */