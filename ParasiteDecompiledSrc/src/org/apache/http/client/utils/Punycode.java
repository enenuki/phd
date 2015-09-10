/*  1:   */ package org.apache.http.client.utils;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ 
/*  5:   */ @Immutable
/*  6:   */ public class Punycode
/*  7:   */ {
/*  8:   */   private static final Idn impl;
/*  9:   */   
/* 10:   */   static
/* 11:   */   {
/* 12:   */     Idn _impl;
/* 13:   */     try
/* 14:   */     {
/* 15:47 */       _impl = new JdkIdn();
/* 16:   */     }
/* 17:   */     catch (Exception e)
/* 18:   */     {
/* 19:49 */       _impl = new Rfc3492Idn();
/* 20:   */     }
/* 21:51 */     impl = _impl;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static String toUnicode(String punycode)
/* 25:   */   {
/* 26:55 */     return impl.toUnicode(punycode);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.utils.Punycode
 * JD-Core Version:    0.7.0.1
 */