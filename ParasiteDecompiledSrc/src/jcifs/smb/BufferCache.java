/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.Config;
/*  4:   */ 
/*  5:   */ public class BufferCache
/*  6:   */ {
/*  7:25 */   private static final int MAX_BUFFERS = Config.getInt("jcifs.smb.maxBuffers", 16);
/*  8:27 */   static Object[] cache = new Object[MAX_BUFFERS];
/*  9:28 */   private static int freeBuffers = 0;
/* 10:   */   
/* 11:   */   public static byte[] getBuffer()
/* 12:   */   {
/* 13:31 */     synchronized (cache)
/* 14:   */     {
/* 15:34 */       if (freeBuffers > 0) {
/* 16:35 */         for (int i = 0; i < MAX_BUFFERS; i++) {
/* 17:36 */           if (cache[i] != null)
/* 18:   */           {
/* 19:37 */             byte[] buf = (byte[])cache[i];
/* 20:38 */             cache[i] = null;
/* 21:39 */             freeBuffers -= 1;
/* 22:40 */             return buf;
/* 23:   */           }
/* 24:   */         }
/* 25:   */       }
/* 26:45 */       byte[] buf = new byte[65535];
/* 27:   */       
/* 28:47 */       return buf;
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   static void getBuffers(SmbComTransaction req, SmbComTransactionResponse rsp)
/* 33:   */   {
/* 34:51 */     synchronized (cache)
/* 35:   */     {
/* 36:52 */       req.txn_buf = getBuffer();
/* 37:53 */       rsp.txn_buf = getBuffer();
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static void releaseBuffer(byte[] buf)
/* 42:   */   {
/* 43:57 */     synchronized (cache)
/* 44:   */     {
/* 45:58 */       if (freeBuffers < MAX_BUFFERS) {
/* 46:59 */         for (int i = 0; i < MAX_BUFFERS; i++) {
/* 47:60 */           if (cache[i] == null)
/* 48:   */           {
/* 49:61 */             cache[i] = buf;
/* 50:62 */             freeBuffers += 1;
/* 51:63 */             return;
/* 52:   */           }
/* 53:   */         }
/* 54:   */       }
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.BufferCache
 * JD-Core Version:    0.7.0.1
 */