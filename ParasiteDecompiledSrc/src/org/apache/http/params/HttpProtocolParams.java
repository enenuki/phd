/*   1:    */ package org.apache.http.params;
/*   2:    */ 
/*   3:    */ import org.apache.http.HttpVersion;
/*   4:    */ import org.apache.http.ProtocolVersion;
/*   5:    */ 
/*   6:    */ public final class HttpProtocolParams
/*   7:    */   implements CoreProtocolPNames
/*   8:    */ {
/*   9:    */   public static String getHttpElementCharset(HttpParams params)
/*  10:    */   {
/*  11: 55 */     if (params == null) {
/*  12: 56 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  13:    */     }
/*  14: 58 */     String charset = (String)params.getParameter("http.protocol.element-charset");
/*  15: 60 */     if (charset == null) {
/*  16: 61 */       charset = "US-ASCII";
/*  17:    */     }
/*  18: 63 */     return charset;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static void setHttpElementCharset(HttpParams params, String charset)
/*  22:    */   {
/*  23: 73 */     if (params == null) {
/*  24: 74 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  25:    */     }
/*  26: 76 */     params.setParameter("http.protocol.element-charset", charset);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static String getContentCharset(HttpParams params)
/*  30:    */   {
/*  31: 87 */     if (params == null) {
/*  32: 88 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  33:    */     }
/*  34: 90 */     String charset = (String)params.getParameter("http.protocol.content-charset");
/*  35: 92 */     if (charset == null) {
/*  36: 93 */       charset = "ISO-8859-1";
/*  37:    */     }
/*  38: 95 */     return charset;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void setContentCharset(HttpParams params, String charset)
/*  42:    */   {
/*  43:105 */     if (params == null) {
/*  44:106 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  45:    */     }
/*  46:108 */     params.setParameter("http.protocol.content-charset", charset);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static ProtocolVersion getVersion(HttpParams params)
/*  50:    */   {
/*  51:119 */     if (params == null) {
/*  52:120 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  53:    */     }
/*  54:122 */     Object param = params.getParameter("http.protocol.version");
/*  55:124 */     if (param == null) {
/*  56:125 */       return HttpVersion.HTTP_1_1;
/*  57:    */     }
/*  58:127 */     return (ProtocolVersion)param;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static void setVersion(HttpParams params, ProtocolVersion version)
/*  62:    */   {
/*  63:137 */     if (params == null) {
/*  64:138 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  65:    */     }
/*  66:140 */     params.setParameter("http.protocol.version", version);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static String getUserAgent(HttpParams params)
/*  70:    */   {
/*  71:151 */     if (params == null) {
/*  72:152 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  73:    */     }
/*  74:154 */     return (String)params.getParameter("http.useragent");
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static void setUserAgent(HttpParams params, String useragent)
/*  78:    */   {
/*  79:164 */     if (params == null) {
/*  80:165 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  81:    */     }
/*  82:167 */     params.setParameter("http.useragent", useragent);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static boolean useExpectContinue(HttpParams params)
/*  86:    */   {
/*  87:178 */     if (params == null) {
/*  88:179 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  89:    */     }
/*  90:181 */     return params.getBooleanParameter("http.protocol.expect-continue", false);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static void setUseExpectContinue(HttpParams params, boolean b)
/*  94:    */   {
/*  95:192 */     if (params == null) {
/*  96:193 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  97:    */     }
/*  98:195 */     params.setBooleanParameter("http.protocol.expect-continue", b);
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.params.HttpProtocolParams
 * JD-Core Version:    0.7.0.1
 */