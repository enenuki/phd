/*  1:   */ package org.apache.http.conn.ssl;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ 
/*  5:   */ @Immutable
/*  6:   */ public class AllowAllHostnameVerifier
/*  7:   */   extends AbstractVerifier
/*  8:   */ {
/*  9:   */   public final void verify(String host, String[] cns, String[] subjectAlts) {}
/* 10:   */   
/* 11:   */   public final String toString()
/* 12:   */   {
/* 13:51 */     return "ALLOW_ALL";
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.ssl.AllowAllHostnameVerifier
 * JD-Core Version:    0.7.0.1
 */