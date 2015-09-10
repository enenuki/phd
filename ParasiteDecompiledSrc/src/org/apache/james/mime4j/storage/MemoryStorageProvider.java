/*  1:   */ package org.apache.james.mime4j.storage;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ import org.apache.james.mime4j.util.ByteArrayBuffer;
/*  7:   */ 
/*  8:   */ public class MemoryStorageProvider
/*  9:   */   extends AbstractStorageProvider
/* 10:   */ {
/* 11:   */   public StorageOutputStream createStorageOutputStream()
/* 12:   */   {
/* 13:47 */     return new MemoryStorageOutputStream(null);
/* 14:   */   }
/* 15:   */   
/* 16:   */   private static final class MemoryStorageOutputStream
/* 17:   */     extends StorageOutputStream
/* 18:   */   {
/* 19:52 */     ByteArrayBuffer bab = new ByteArrayBuffer(1024);
/* 20:   */     
/* 21:   */     protected void write0(byte[] buffer, int offset, int length)
/* 22:   */       throws IOException
/* 23:   */     {
/* 24:57 */       this.bab.append(buffer, offset, length);
/* 25:   */     }
/* 26:   */     
/* 27:   */     protected Storage toStorage0()
/* 28:   */       throws IOException
/* 29:   */     {
/* 30:62 */       return new MemoryStorageProvider.MemoryStorage(this.bab.buffer(), this.bab.length());
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   static final class MemoryStorage
/* 35:   */     implements Storage
/* 36:   */   {
/* 37:   */     private byte[] data;
/* 38:   */     private final int count;
/* 39:   */     
/* 40:   */     public MemoryStorage(byte[] data, int count)
/* 41:   */     {
/* 42:71 */       this.data = data;
/* 43:72 */       this.count = count;
/* 44:   */     }
/* 45:   */     
/* 46:   */     public InputStream getInputStream()
/* 47:   */       throws IOException
/* 48:   */     {
/* 49:76 */       if (this.data == null) {
/* 50:77 */         throw new IllegalStateException("storage has been deleted");
/* 51:   */       }
/* 52:79 */       return new ByteArrayInputStream(this.data, 0, this.count);
/* 53:   */     }
/* 54:   */     
/* 55:   */     public void delete()
/* 56:   */     {
/* 57:83 */       this.data = null;
/* 58:   */     }
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.storage.MemoryStorageProvider
 * JD-Core Version:    0.7.0.1
 */