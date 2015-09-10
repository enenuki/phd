/*   1:    */ package org.apache.james.mime4j.storage;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.SequenceInputStream;
/*   7:    */ import org.apache.james.mime4j.util.ByteArrayBuffer;
/*   8:    */ 
/*   9:    */ public class ThresholdStorageProvider
/*  10:    */   extends AbstractStorageProvider
/*  11:    */ {
/*  12:    */   private final StorageProvider backend;
/*  13:    */   private final int thresholdSize;
/*  14:    */   
/*  15:    */   public ThresholdStorageProvider(StorageProvider backend)
/*  16:    */   {
/*  17: 52 */     this(backend, 2048);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ThresholdStorageProvider(StorageProvider backend, int thresholdSize)
/*  21:    */   {
/*  22: 68 */     if (backend == null) {
/*  23: 69 */       throw new IllegalArgumentException();
/*  24:    */     }
/*  25: 70 */     if (thresholdSize < 1) {
/*  26: 71 */       throw new IllegalArgumentException();
/*  27:    */     }
/*  28: 73 */     this.backend = backend;
/*  29: 74 */     this.thresholdSize = thresholdSize;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public StorageOutputStream createStorageOutputStream()
/*  33:    */   {
/*  34: 78 */     return new ThresholdStorageOutputStream();
/*  35:    */   }
/*  36:    */   
/*  37:    */   private final class ThresholdStorageOutputStream
/*  38:    */     extends StorageOutputStream
/*  39:    */   {
/*  40:    */     private final ByteArrayBuffer head;
/*  41:    */     private StorageOutputStream tail;
/*  42:    */     
/*  43:    */     public ThresholdStorageOutputStream()
/*  44:    */     {
/*  45: 88 */       int bufferSize = Math.min(ThresholdStorageProvider.this.thresholdSize, 1024);
/*  46: 89 */       this.head = new ByteArrayBuffer(bufferSize);
/*  47:    */     }
/*  48:    */     
/*  49:    */     public void close()
/*  50:    */       throws IOException
/*  51:    */     {
/*  52: 94 */       super.close();
/*  53: 96 */       if (this.tail != null) {
/*  54: 97 */         this.tail.close();
/*  55:    */       }
/*  56:    */     }
/*  57:    */     
/*  58:    */     protected void write0(byte[] buffer, int offset, int length)
/*  59:    */       throws IOException
/*  60:    */     {
/*  61:103 */       int remainingHeadSize = ThresholdStorageProvider.this.thresholdSize - this.head.length();
/*  62:104 */       if (remainingHeadSize > 0)
/*  63:    */       {
/*  64:105 */         int n = Math.min(remainingHeadSize, length);
/*  65:106 */         this.head.append(buffer, offset, n);
/*  66:107 */         offset += n;
/*  67:108 */         length -= n;
/*  68:    */       }
/*  69:111 */       if (length > 0)
/*  70:    */       {
/*  71:112 */         if (this.tail == null) {
/*  72:113 */           this.tail = ThresholdStorageProvider.this.backend.createStorageOutputStream();
/*  73:    */         }
/*  74:115 */         this.tail.write(buffer, offset, length);
/*  75:    */       }
/*  76:    */     }
/*  77:    */     
/*  78:    */     protected Storage toStorage0()
/*  79:    */       throws IOException
/*  80:    */     {
/*  81:121 */       if (this.tail == null) {
/*  82:122 */         return new MemoryStorageProvider.MemoryStorage(this.head.buffer(), this.head.length());
/*  83:    */       }
/*  84:125 */       return new ThresholdStorageProvider.ThresholdStorage(this.head.buffer(), this.head.length(), this.tail.toStorage());
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   private static final class ThresholdStorage
/*  89:    */     implements Storage
/*  90:    */   {
/*  91:    */     private byte[] head;
/*  92:    */     private final int headLen;
/*  93:    */     private Storage tail;
/*  94:    */     
/*  95:    */     public ThresholdStorage(byte[] head, int headLen, Storage tail)
/*  96:    */     {
/*  97:138 */       this.head = head;
/*  98:139 */       this.headLen = headLen;
/*  99:140 */       this.tail = tail;
/* 100:    */     }
/* 101:    */     
/* 102:    */     public void delete()
/* 103:    */     {
/* 104:144 */       if (this.head != null)
/* 105:    */       {
/* 106:145 */         this.head = null;
/* 107:146 */         this.tail.delete();
/* 108:147 */         this.tail = null;
/* 109:    */       }
/* 110:    */     }
/* 111:    */     
/* 112:    */     public InputStream getInputStream()
/* 113:    */       throws IOException
/* 114:    */     {
/* 115:152 */       if (this.head == null) {
/* 116:153 */         throw new IllegalStateException("storage has been deleted");
/* 117:    */       }
/* 118:155 */       InputStream headStream = new ByteArrayInputStream(this.head, 0, this.headLen);
/* 119:156 */       InputStream tailStream = this.tail.getInputStream();
/* 120:157 */       return new SequenceInputStream(headStream, tailStream);
/* 121:    */     }
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.storage.ThresholdStorageProvider
 * JD-Core Version:    0.7.0.1
 */