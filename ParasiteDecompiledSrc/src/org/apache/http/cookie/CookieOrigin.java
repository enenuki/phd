/*   1:    */ package org.apache.http.cookie;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.http.annotation.Immutable;
/*   5:    */ 
/*   6:    */ @Immutable
/*   7:    */ public final class CookieOrigin
/*   8:    */ {
/*   9:    */   private final String host;
/*  10:    */   private final int port;
/*  11:    */   private final String path;
/*  12:    */   private final boolean secure;
/*  13:    */   
/*  14:    */   public CookieOrigin(String host, int port, String path, boolean secure)
/*  15:    */   {
/*  16: 49 */     if (host == null) {
/*  17: 50 */       throw new IllegalArgumentException("Host of origin may not be null");
/*  18:    */     }
/*  19: 53 */     if (host.trim().length() == 0) {
/*  20: 54 */       throw new IllegalArgumentException("Host of origin may not be blank");
/*  21:    */     }
/*  22: 57 */     if (port < 0) {
/*  23: 58 */       throw new IllegalArgumentException("Invalid port: " + port);
/*  24:    */     }
/*  25: 60 */     if (path == null) {
/*  26: 61 */       throw new IllegalArgumentException("Path of origin may not be null.");
/*  27:    */     }
/*  28: 64 */     this.host = host.toLowerCase(Locale.ENGLISH);
/*  29: 65 */     this.port = port;
/*  30: 66 */     if (path.trim().length() != 0) {
/*  31: 67 */       this.path = path;
/*  32:    */     } else {
/*  33: 69 */       this.path = "/";
/*  34:    */     }
/*  35: 71 */     this.secure = secure;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getHost()
/*  39:    */   {
/*  40: 75 */     return this.host;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getPath()
/*  44:    */   {
/*  45: 79 */     return this.path;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getPort()
/*  49:    */   {
/*  50: 83 */     return this.port;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean isSecure()
/*  54:    */   {
/*  55: 87 */     return this.secure;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String toString()
/*  59:    */   {
/*  60: 92 */     StringBuilder buffer = new StringBuilder();
/*  61: 93 */     buffer.append('[');
/*  62: 94 */     if (this.secure) {
/*  63: 95 */       buffer.append("(secure)");
/*  64:    */     }
/*  65: 97 */     buffer.append(this.host);
/*  66: 98 */     buffer.append(':');
/*  67: 99 */     buffer.append(Integer.toString(this.port));
/*  68:100 */     buffer.append(this.path);
/*  69:101 */     buffer.append(']');
/*  70:102 */     return buffer.toString();
/*  71:    */   }
/*  72:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.cookie.CookieOrigin
 * JD-Core Version:    0.7.0.1
 */