/*   1:    */ package org.apache.http.params;
/*   2:    */ 
/*   3:    */ public final class HttpConnectionParams
/*   4:    */   implements CoreConnectionPNames
/*   5:    */ {
/*   6:    */   public static int getSoTimeout(HttpParams params)
/*   7:    */   {
/*   8: 49 */     if (params == null) {
/*   9: 50 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  10:    */     }
/*  11: 52 */     return params.getIntParameter("http.socket.timeout", 0);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public static void setSoTimeout(HttpParams params, int timeout)
/*  15:    */   {
/*  16: 62 */     if (params == null) {
/*  17: 63 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  18:    */     }
/*  19: 65 */     params.setIntParameter("http.socket.timeout", timeout);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static boolean getSoReuseaddr(HttpParams params)
/*  23:    */   {
/*  24: 79 */     if (params == null) {
/*  25: 80 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  26:    */     }
/*  27: 82 */     return params.getBooleanParameter("http.socket.reuseaddr", false);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static void setSoReuseaddr(HttpParams params, boolean reuseaddr)
/*  31:    */   {
/*  32: 94 */     if (params == null) {
/*  33: 95 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  34:    */     }
/*  35: 97 */     params.setBooleanParameter("http.socket.reuseaddr", reuseaddr);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static boolean getTcpNoDelay(HttpParams params)
/*  39:    */   {
/*  40:108 */     if (params == null) {
/*  41:109 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  42:    */     }
/*  43:111 */     return params.getBooleanParameter("http.tcp.nodelay", true);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static void setTcpNoDelay(HttpParams params, boolean value)
/*  47:    */   {
/*  48:122 */     if (params == null) {
/*  49:123 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  50:    */     }
/*  51:125 */     params.setBooleanParameter("http.tcp.nodelay", value);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static int getSocketBufferSize(HttpParams params)
/*  55:    */   {
/*  56:136 */     if (params == null) {
/*  57:137 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  58:    */     }
/*  59:139 */     return params.getIntParameter("http.socket.buffer-size", -1);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static void setSocketBufferSize(HttpParams params, int size)
/*  63:    */   {
/*  64:151 */     if (params == null) {
/*  65:152 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  66:    */     }
/*  67:154 */     params.setIntParameter("http.socket.buffer-size", size);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static int getLinger(HttpParams params)
/*  71:    */   {
/*  72:165 */     if (params == null) {
/*  73:166 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  74:    */     }
/*  75:168 */     return params.getIntParameter("http.socket.linger", -1);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void setLinger(HttpParams params, int value)
/*  79:    */   {
/*  80:178 */     if (params == null) {
/*  81:179 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  82:    */     }
/*  83:181 */     params.setIntParameter("http.socket.linger", value);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static int getConnectionTimeout(HttpParams params)
/*  87:    */   {
/*  88:192 */     if (params == null) {
/*  89:193 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  90:    */     }
/*  91:195 */     return params.getIntParameter("http.connection.timeout", 0);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static void setConnectionTimeout(HttpParams params, int timeout)
/*  95:    */   {
/*  96:207 */     if (params == null) {
/*  97:208 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  98:    */     }
/*  99:210 */     params.setIntParameter("http.connection.timeout", timeout);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static boolean isStaleCheckingEnabled(HttpParams params)
/* 103:    */   {
/* 104:222 */     if (params == null) {
/* 105:223 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 106:    */     }
/* 107:225 */     return params.getBooleanParameter("http.connection.stalecheck", true);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static void setStaleCheckingEnabled(HttpParams params, boolean value)
/* 111:    */   {
/* 112:237 */     if (params == null) {
/* 113:238 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/* 114:    */     }
/* 115:240 */     params.setBooleanParameter("http.connection.stalecheck", value);
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.params.HttpConnectionParams
 * JD-Core Version:    0.7.0.1
 */