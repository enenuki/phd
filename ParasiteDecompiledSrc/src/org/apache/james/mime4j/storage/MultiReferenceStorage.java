/*   1:    */ package org.apache.james.mime4j.storage;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ 
/*   6:    */ public class MultiReferenceStorage
/*   7:    */   implements Storage
/*   8:    */ {
/*   9:    */   private final Storage storage;
/*  10:    */   private int referenceCounter;
/*  11:    */   
/*  12:    */   public MultiReferenceStorage(Storage storage)
/*  13:    */   {
/*  14: 61 */     if (storage == null) {
/*  15: 62 */       throw new IllegalArgumentException();
/*  16:    */     }
/*  17: 64 */     this.storage = storage;
/*  18: 65 */     this.referenceCounter = 1;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void addReference()
/*  22:    */   {
/*  23: 76 */     incrementCounter();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void delete()
/*  27:    */   {
/*  28: 91 */     if (decrementCounter()) {
/*  29: 92 */       this.storage.delete();
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public InputStream getInputStream()
/*  34:    */     throws IOException
/*  35:    */   {
/*  36:102 */     return this.storage.getInputStream();
/*  37:    */   }
/*  38:    */   
/*  39:    */   private synchronized void incrementCounter()
/*  40:    */   {
/*  41:112 */     if (this.referenceCounter == 0) {
/*  42:113 */       throw new IllegalStateException("storage has been deleted");
/*  43:    */     }
/*  44:115 */     this.referenceCounter += 1;
/*  45:    */   }
/*  46:    */   
/*  47:    */   private synchronized boolean decrementCounter()
/*  48:    */   {
/*  49:126 */     if (this.referenceCounter == 0) {
/*  50:127 */       throw new IllegalStateException("storage has been deleted");
/*  51:    */     }
/*  52:129 */     return --this.referenceCounter == 0;
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.storage.MultiReferenceStorage
 * JD-Core Version:    0.7.0.1
 */