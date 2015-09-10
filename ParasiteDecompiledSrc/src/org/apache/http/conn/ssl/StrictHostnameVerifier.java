/*  1:   */ package org.apache.http.conn.ssl;
/*  2:   */ 
/*  3:   */ import javax.net.ssl.SSLException;
/*  4:   */ import org.apache.http.annotation.Immutable;
/*  5:   */ 
/*  6:   */ @Immutable
/*  7:   */ public class StrictHostnameVerifier
/*  8:   */   extends AbstractVerifier
/*  9:   */ {
/* 10:   */   public final void verify(String host, String[] cns, String[] subjectAlts)
/* 11:   */     throws SSLException
/* 12:   */   {
/* 13:61 */     verify(host, cns, subjectAlts, true);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public final String toString()
/* 17:   */   {
/* 18:66 */     return "STRICT";
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.ssl.StrictHostnameVerifier
 * JD-Core Version:    0.7.0.1
 */