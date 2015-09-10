/*   1:    */ package org.apache.http;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Locale;
/*   5:    */ import org.apache.http.util.CharArrayBuffer;
/*   6:    */ import org.apache.http.util.LangUtils;
/*   7:    */ 
/*   8:    */ public final class HttpHost
/*   9:    */   implements Cloneable, Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = -7529410654042457626L;
/*  12:    */   public static final String DEFAULT_SCHEME_NAME = "http";
/*  13:    */   protected final String hostname;
/*  14:    */   protected final String lcHostname;
/*  15:    */   protected final int port;
/*  16:    */   protected final String schemeName;
/*  17:    */   
/*  18:    */   public HttpHost(String hostname, int port, String scheme)
/*  19:    */   {
/*  20: 78 */     if (hostname == null) {
/*  21: 79 */       throw new IllegalArgumentException("Host name may not be null");
/*  22:    */     }
/*  23: 81 */     this.hostname = hostname;
/*  24: 82 */     this.lcHostname = hostname.toLowerCase(Locale.ENGLISH);
/*  25: 83 */     if (scheme != null) {
/*  26: 84 */       this.schemeName = scheme.toLowerCase(Locale.ENGLISH);
/*  27:    */     } else {
/*  28: 86 */       this.schemeName = "http";
/*  29:    */     }
/*  30: 88 */     this.port = port;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public HttpHost(String hostname, int port)
/*  34:    */   {
/*  35: 99 */     this(hostname, port, null);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public HttpHost(String hostname)
/*  39:    */   {
/*  40:108 */     this(hostname, -1, null);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public HttpHost(HttpHost httphost)
/*  44:    */   {
/*  45:117 */     this(httphost.hostname, httphost.port, httphost.schemeName);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getHostName()
/*  49:    */   {
/*  50:126 */     return this.hostname;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getPort()
/*  54:    */   {
/*  55:135 */     return this.port;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getSchemeName()
/*  59:    */   {
/*  60:144 */     return this.schemeName;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String toURI()
/*  64:    */   {
/*  65:153 */     CharArrayBuffer buffer = new CharArrayBuffer(32);
/*  66:154 */     buffer.append(this.schemeName);
/*  67:155 */     buffer.append("://");
/*  68:156 */     buffer.append(this.hostname);
/*  69:157 */     if (this.port != -1)
/*  70:    */     {
/*  71:158 */       buffer.append(':');
/*  72:159 */       buffer.append(Integer.toString(this.port));
/*  73:    */     }
/*  74:161 */     return buffer.toString();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toHostString()
/*  78:    */   {
/*  79:171 */     if (this.port != -1)
/*  80:    */     {
/*  81:173 */       CharArrayBuffer buffer = new CharArrayBuffer(this.hostname.length() + 6);
/*  82:174 */       buffer.append(this.hostname);
/*  83:175 */       buffer.append(":");
/*  84:176 */       buffer.append(Integer.toString(this.port));
/*  85:177 */       return buffer.toString();
/*  86:    */     }
/*  87:179 */     return this.hostname;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String toString()
/*  91:    */   {
/*  92:185 */     return toURI();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean equals(Object obj)
/*  96:    */   {
/*  97:190 */     if (this == obj) {
/*  98:190 */       return true;
/*  99:    */     }
/* 100:191 */     if ((obj instanceof HttpHost))
/* 101:    */     {
/* 102:192 */       HttpHost that = (HttpHost)obj;
/* 103:193 */       return (this.lcHostname.equals(that.lcHostname)) && (this.port == that.port) && (this.schemeName.equals(that.schemeName));
/* 104:    */     }
/* 105:197 */     return false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int hashCode()
/* 109:    */   {
/* 110:205 */     int hash = 17;
/* 111:206 */     hash = LangUtils.hashCode(hash, this.lcHostname);
/* 112:207 */     hash = LangUtils.hashCode(hash, this.port);
/* 113:208 */     hash = LangUtils.hashCode(hash, this.schemeName);
/* 114:209 */     return hash;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Object clone()
/* 118:    */     throws CloneNotSupportedException
/* 119:    */   {
/* 120:213 */     return super.clone();
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.HttpHost
 * JD-Core Version:    0.7.0.1
 */