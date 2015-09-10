/*   1:    */ package org.apache.http.auth;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.http.annotation.Immutable;
/*   5:    */ import org.apache.http.util.LangUtils;
/*   6:    */ 
/*   7:    */ @Immutable
/*   8:    */ public class AuthScope
/*   9:    */ {
/*  10: 50 */   public static final String ANY_HOST = null;
/*  11:    */   public static final int ANY_PORT = -1;
/*  12: 60 */   public static final String ANY_REALM = null;
/*  13: 65 */   public static final String ANY_SCHEME = null;
/*  14: 72 */   public static final AuthScope ANY = new AuthScope(ANY_HOST, -1, ANY_REALM, ANY_SCHEME);
/*  15:    */   private final String scheme;
/*  16:    */   private final String realm;
/*  17:    */   private final String host;
/*  18:    */   private final int port;
/*  19:    */   
/*  20:    */   public AuthScope(String host, int port, String realm, String scheme)
/*  21:    */   {
/*  22:106 */     this.host = (host == null ? ANY_HOST : host.toLowerCase(Locale.ENGLISH));
/*  23:107 */     this.port = (port < 0 ? -1 : port);
/*  24:108 */     this.realm = (realm == null ? ANY_REALM : realm);
/*  25:109 */     this.scheme = (scheme == null ? ANY_SCHEME : scheme.toUpperCase(Locale.ENGLISH));
/*  26:    */   }
/*  27:    */   
/*  28:    */   public AuthScope(String host, int port, String realm)
/*  29:    */   {
/*  30:127 */     this(host, port, realm, ANY_SCHEME);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public AuthScope(String host, int port)
/*  34:    */   {
/*  35:142 */     this(host, port, ANY_REALM, ANY_SCHEME);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public AuthScope(AuthScope authscope)
/*  39:    */   {
/*  40:150 */     if (authscope == null) {
/*  41:151 */       throw new IllegalArgumentException("Scope may not be null");
/*  42:    */     }
/*  43:153 */     this.host = authscope.getHost();
/*  44:154 */     this.port = authscope.getPort();
/*  45:155 */     this.realm = authscope.getRealm();
/*  46:156 */     this.scheme = authscope.getScheme();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getHost()
/*  50:    */   {
/*  51:163 */     return this.host;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getPort()
/*  55:    */   {
/*  56:170 */     return this.port;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getRealm()
/*  60:    */   {
/*  61:177 */     return this.realm;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getScheme()
/*  65:    */   {
/*  66:184 */     return this.scheme;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int match(AuthScope that)
/*  70:    */   {
/*  71:195 */     int factor = 0;
/*  72:196 */     if (LangUtils.equals(this.scheme, that.scheme)) {
/*  73:197 */       factor++;
/*  74:199 */     } else if ((this.scheme != ANY_SCHEME) && (that.scheme != ANY_SCHEME)) {
/*  75:200 */       return -1;
/*  76:    */     }
/*  77:203 */     if (LangUtils.equals(this.realm, that.realm)) {
/*  78:204 */       factor += 2;
/*  79:206 */     } else if ((this.realm != ANY_REALM) && (that.realm != ANY_REALM)) {
/*  80:207 */       return -1;
/*  81:    */     }
/*  82:210 */     if (this.port == that.port) {
/*  83:211 */       factor += 4;
/*  84:213 */     } else if ((this.port != -1) && (that.port != -1)) {
/*  85:214 */       return -1;
/*  86:    */     }
/*  87:217 */     if (LangUtils.equals(this.host, that.host)) {
/*  88:218 */       factor += 8;
/*  89:220 */     } else if ((this.host != ANY_HOST) && (that.host != ANY_HOST)) {
/*  90:221 */       return -1;
/*  91:    */     }
/*  92:224 */     return factor;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean equals(Object o)
/*  96:    */   {
/*  97:232 */     if (o == null) {
/*  98:233 */       return false;
/*  99:    */     }
/* 100:235 */     if (o == this) {
/* 101:236 */       return true;
/* 102:    */     }
/* 103:238 */     if (!(o instanceof AuthScope)) {
/* 104:239 */       return super.equals(o);
/* 105:    */     }
/* 106:241 */     AuthScope that = (AuthScope)o;
/* 107:242 */     return (LangUtils.equals(this.host, that.host)) && (this.port == that.port) && (LangUtils.equals(this.realm, that.realm)) && (LangUtils.equals(this.scheme, that.scheme));
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String toString()
/* 111:    */   {
/* 112:254 */     StringBuilder buffer = new StringBuilder();
/* 113:255 */     if (this.scheme != null)
/* 114:    */     {
/* 115:256 */       buffer.append(this.scheme.toUpperCase(Locale.ENGLISH));
/* 116:257 */       buffer.append(' ');
/* 117:    */     }
/* 118:259 */     if (this.realm != null)
/* 119:    */     {
/* 120:260 */       buffer.append('\'');
/* 121:261 */       buffer.append(this.realm);
/* 122:262 */       buffer.append('\'');
/* 123:    */     }
/* 124:    */     else
/* 125:    */     {
/* 126:264 */       buffer.append("<any realm>");
/* 127:    */     }
/* 128:266 */     if (this.host != null)
/* 129:    */     {
/* 130:267 */       buffer.append('@');
/* 131:268 */       buffer.append(this.host);
/* 132:269 */       if (this.port >= 0)
/* 133:    */       {
/* 134:270 */         buffer.append(':');
/* 135:271 */         buffer.append(this.port);
/* 136:    */       }
/* 137:    */     }
/* 138:274 */     return buffer.toString();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int hashCode()
/* 142:    */   {
/* 143:282 */     int hash = 17;
/* 144:283 */     hash = LangUtils.hashCode(hash, this.host);
/* 145:284 */     hash = LangUtils.hashCode(hash, this.port);
/* 146:285 */     hash = LangUtils.hashCode(hash, this.realm);
/* 147:286 */     hash = LangUtils.hashCode(hash, this.scheme);
/* 148:287 */     return hash;
/* 149:    */   }
/* 150:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.AuthScope
 * JD-Core Version:    0.7.0.1
 */