/*  1:   */ package org.hibernate.id;
/*  2:   */ 
/*  3:   */ import java.net.InetAddress;
/*  4:   */ import org.hibernate.internal.util.BytesHelper;
/*  5:   */ 
/*  6:   */ public abstract class AbstractUUIDGenerator
/*  7:   */   implements IdentifierGenerator
/*  8:   */ {
/*  9:   */   private static final int IP;
/* 10:   */   
/* 11:   */   static
/* 12:   */   {
/* 13:   */     int ipadd;
/* 14:   */     try
/* 15:   */     {
/* 16:45 */       ipadd = BytesHelper.toInt(InetAddress.getLocalHost().getAddress());
/* 17:   */     }
/* 18:   */     catch (Exception e)
/* 19:   */     {
/* 20:48 */       ipadd = 0;
/* 21:   */     }
/* 22:50 */     IP = ipadd;
/* 23:   */   }
/* 24:   */   
/* 25:52 */   private static short counter = 0;
/* 26:53 */   private static final int JVM = (int)(System.currentTimeMillis() >>> 8);
/* 27:   */   
/* 28:   */   protected int getJVM()
/* 29:   */   {
/* 30:63 */     return JVM;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected short getCount()
/* 34:   */   {
/* 35:71 */     synchronized (AbstractUUIDGenerator.class)
/* 36:   */     {
/* 37:72 */       if (counter < 0) {
/* 38:72 */         counter = 0;
/* 39:   */       }
/* 40:73 */       return counter++;
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   protected int getIP()
/* 45:   */   {
/* 46:81 */     return IP;
/* 47:   */   }
/* 48:   */   
/* 49:   */   protected short getHiTime()
/* 50:   */   {
/* 51:88 */     return (short)(int)(System.currentTimeMillis() >>> 32);
/* 52:   */   }
/* 53:   */   
/* 54:   */   protected int getLoTime()
/* 55:   */   {
/* 56:91 */     return (int)System.currentTimeMillis();
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.AbstractUUIDGenerator
 * JD-Core Version:    0.7.0.1
 */