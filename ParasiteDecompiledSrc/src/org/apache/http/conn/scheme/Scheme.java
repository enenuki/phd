/*   1:    */ package org.apache.http.conn.scheme;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.apache.http.annotation.Immutable;
/*   5:    */ import org.apache.http.util.LangUtils;
/*   6:    */ 
/*   7:    */ @Immutable
/*   8:    */ public final class Scheme
/*   9:    */ {
/*  10:    */   private final String name;
/*  11:    */   private final SchemeSocketFactory socketFactory;
/*  12:    */   private final int defaultPort;
/*  13:    */   private final boolean layered;
/*  14:    */   private String stringRep;
/*  15:    */   
/*  16:    */   public Scheme(String name, int port, SchemeSocketFactory factory)
/*  17:    */   {
/*  18: 85 */     if (name == null) {
/*  19: 86 */       throw new IllegalArgumentException("Scheme name may not be null");
/*  20:    */     }
/*  21: 88 */     if ((port <= 0) || (port > 65535)) {
/*  22: 89 */       throw new IllegalArgumentException("Port is invalid: " + port);
/*  23:    */     }
/*  24: 91 */     if (factory == null) {
/*  25: 92 */       throw new IllegalArgumentException("Socket factory may not be null");
/*  26:    */     }
/*  27: 94 */     this.name = name.toLowerCase(Locale.ENGLISH);
/*  28: 95 */     this.socketFactory = factory;
/*  29: 96 */     this.defaultPort = port;
/*  30: 97 */     this.layered = (factory instanceof LayeredSchemeSocketFactory);
/*  31:    */   }
/*  32:    */   
/*  33:    */   @Deprecated
/*  34:    */   public Scheme(String name, SocketFactory factory, int port)
/*  35:    */   {
/*  36:118 */     if (name == null) {
/*  37:119 */       throw new IllegalArgumentException("Scheme name may not be null");
/*  38:    */     }
/*  39:122 */     if (factory == null) {
/*  40:123 */       throw new IllegalArgumentException("Socket factory may not be null");
/*  41:    */     }
/*  42:126 */     if ((port <= 0) || (port > 65535)) {
/*  43:127 */       throw new IllegalArgumentException("Port is invalid: " + port);
/*  44:    */     }
/*  45:131 */     this.name = name.toLowerCase(Locale.ENGLISH);
/*  46:132 */     if ((factory instanceof LayeredSocketFactory))
/*  47:    */     {
/*  48:133 */       this.socketFactory = new LayeredSchemeSocketFactoryAdaptor((LayeredSocketFactory)factory);
/*  49:    */       
/*  50:135 */       this.layered = true;
/*  51:    */     }
/*  52:    */     else
/*  53:    */     {
/*  54:137 */       this.socketFactory = new SchemeSocketFactoryAdaptor(factory);
/*  55:138 */       this.layered = false;
/*  56:    */     }
/*  57:140 */     this.defaultPort = port;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final int getDefaultPort()
/*  61:    */   {
/*  62:149 */     return this.defaultPort;
/*  63:    */   }
/*  64:    */   
/*  65:    */   @Deprecated
/*  66:    */   public final SocketFactory getSocketFactory()
/*  67:    */   {
/*  68:164 */     if ((this.socketFactory instanceof SchemeSocketFactoryAdaptor)) {
/*  69:165 */       return ((SchemeSocketFactoryAdaptor)this.socketFactory).getFactory();
/*  70:    */     }
/*  71:167 */     if (this.layered) {
/*  72:168 */       return new LayeredSocketFactoryAdaptor((LayeredSchemeSocketFactory)this.socketFactory);
/*  73:    */     }
/*  74:171 */     return new SocketFactoryAdaptor(this.socketFactory);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public final SchemeSocketFactory getSchemeSocketFactory()
/*  78:    */   {
/*  79:186 */     return this.socketFactory;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public final String getName()
/*  83:    */   {
/*  84:195 */     return this.name;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final boolean isLayered()
/*  88:    */   {
/*  89:205 */     return this.layered;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final int resolvePort(int port)
/*  93:    */   {
/*  94:218 */     return port <= 0 ? this.defaultPort : port;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final String toString()
/*  98:    */   {
/*  99:228 */     if (this.stringRep == null)
/* 100:    */     {
/* 101:229 */       StringBuilder buffer = new StringBuilder();
/* 102:230 */       buffer.append(this.name);
/* 103:231 */       buffer.append(':');
/* 104:232 */       buffer.append(Integer.toString(this.defaultPort));
/* 105:233 */       this.stringRep = buffer.toString();
/* 106:    */     }
/* 107:235 */     return this.stringRep;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public final boolean equals(Object obj)
/* 111:    */   {
/* 112:240 */     if (this == obj) {
/* 113:240 */       return true;
/* 114:    */     }
/* 115:241 */     if ((obj instanceof Scheme))
/* 116:    */     {
/* 117:242 */       Scheme that = (Scheme)obj;
/* 118:243 */       return (this.name.equals(that.name)) && (this.defaultPort == that.defaultPort) && (this.layered == that.layered);
/* 119:    */     }
/* 120:247 */     return false;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int hashCode()
/* 124:    */   {
/* 125:253 */     int hash = 17;
/* 126:254 */     hash = LangUtils.hashCode(hash, this.defaultPort);
/* 127:255 */     hash = LangUtils.hashCode(hash, this.name);
/* 128:256 */     hash = LangUtils.hashCode(hash, this.layered);
/* 129:257 */     return hash;
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.scheme.Scheme
 * JD-Core Version:    0.7.0.1
 */