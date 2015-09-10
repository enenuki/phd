/*   1:    */ package org.apache.http;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public final class HttpVersion
/*   6:    */   extends ProtocolVersion
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = -5856653513894415344L;
/*  10:    */   public static final String HTTP = "HTTP";
/*  11: 54 */   public static final HttpVersion HTTP_0_9 = new HttpVersion(0, 9);
/*  12: 57 */   public static final HttpVersion HTTP_1_0 = new HttpVersion(1, 0);
/*  13: 60 */   public static final HttpVersion HTTP_1_1 = new HttpVersion(1, 1);
/*  14:    */   
/*  15:    */   public HttpVersion(int major, int minor)
/*  16:    */   {
/*  17: 72 */     super("HTTP", major, minor);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ProtocolVersion forVersion(int major, int minor)
/*  21:    */   {
/*  22: 86 */     if ((major == this.major) && (minor == this.minor)) {
/*  23: 87 */       return this;
/*  24:    */     }
/*  25: 90 */     if (major == 1)
/*  26:    */     {
/*  27: 91 */       if (minor == 0) {
/*  28: 92 */         return HTTP_1_0;
/*  29:    */       }
/*  30: 94 */       if (minor == 1) {
/*  31: 95 */         return HTTP_1_1;
/*  32:    */       }
/*  33:    */     }
/*  34: 98 */     if ((major == 0) && (minor == 9)) {
/*  35: 99 */       return HTTP_0_9;
/*  36:    */     }
/*  37:103 */     return new HttpVersion(major, minor);
/*  38:    */   }
/*  39:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.HttpVersion
 * JD-Core Version:    0.7.0.1
 */