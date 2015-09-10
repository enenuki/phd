/*  1:   */ package org.apache.james.mime4j.message;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ import org.apache.james.mime4j.codec.CodecUtil;
/*  7:   */ import org.apache.james.mime4j.storage.MultiReferenceStorage;
/*  8:   */ 
/*  9:   */ class StorageBinaryBody
/* 10:   */   extends BinaryBody
/* 11:   */ {
/* 12:   */   private MultiReferenceStorage storage;
/* 13:   */   
/* 14:   */   public StorageBinaryBody(MultiReferenceStorage storage)
/* 15:   */   {
/* 16:38 */     this.storage = storage;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public InputStream getInputStream()
/* 20:   */     throws IOException
/* 21:   */   {
/* 22:43 */     return this.storage.getInputStream();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void writeTo(OutputStream out)
/* 26:   */     throws IOException
/* 27:   */   {
/* 28:48 */     if (out == null) {
/* 29:49 */       throw new IllegalArgumentException();
/* 30:   */     }
/* 31:51 */     InputStream in = this.storage.getInputStream();
/* 32:52 */     CodecUtil.copy(in, out);
/* 33:53 */     in.close();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public StorageBinaryBody copy()
/* 37:   */   {
/* 38:58 */     this.storage.addReference();
/* 39:59 */     return new StorageBinaryBody(this.storage);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void dispose()
/* 43:   */   {
/* 44:69 */     if (this.storage != null)
/* 45:   */     {
/* 46:70 */       this.storage.delete();
/* 47:71 */       this.storage = null;
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.StorageBinaryBody
 * JD-Core Version:    0.7.0.1
 */