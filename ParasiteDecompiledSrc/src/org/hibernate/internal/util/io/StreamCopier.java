/*  1:   */ package org.hibernate.internal.util.io;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ 
/*  8:   */ public class StreamCopier
/*  9:   */ {
/* 10:   */   public static final int BUFFER_SIZE = 4096;
/* 11:39 */   public static final byte[] BUFFER = new byte[4096];
/* 12:   */   
/* 13:   */   public static long copy(InputStream from, OutputStream into)
/* 14:   */   {
/* 15:   */     try
/* 16:   */     {
/* 17:43 */       long totalRead = 0L;
/* 18:   */       for (;;)
/* 19:   */       {
/* 20:45 */         synchronized (BUFFER)
/* 21:   */         {
/* 22:46 */           int amountRead = from.read(BUFFER);
/* 23:47 */           if (amountRead == -1) {
/* 24:   */             break;
/* 25:   */           }
/* 26:50 */           into.write(BUFFER, 0, amountRead);
/* 27:51 */           totalRead += amountRead;
/* 28:52 */           if (amountRead < 4096) {
/* 29:   */             break;
/* 30:   */           }
/* 31:   */         }
/* 32:   */       }
/* 33:58 */       return totalRead;
/* 34:   */     }
/* 35:   */     catch (IOException e)
/* 36:   */     {
/* 37:61 */       throw new HibernateException("Unable to copy stream content", e);
/* 38:   */     }
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.io.StreamCopier
 * JD-Core Version:    0.7.0.1
 */