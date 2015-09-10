/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import org.apache.commons.lang.builder.EqualsBuilder;
/*  10:    */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*  11:    */ import org.apache.http.impl.cookie.BasicClientCookie;
/*  12:    */ 
/*  13:    */ public class Cookie
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16:    */   private final String name_;
/*  17:    */   private final String value_;
/*  18:    */   private final String domain_;
/*  19:    */   private final String path_;
/*  20:    */   private final Date expires_;
/*  21:    */   private final boolean secure_;
/*  22:    */   
/*  23:    */   public Cookie(String name, String value)
/*  24:    */   {
/*  25: 61 */     this(null, name, value);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Cookie(String domain, String name, String value)
/*  29:    */   {
/*  30: 72 */     this(domain, name, value, null, null, false);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Cookie(String domain, String name, String value, String path, Date expires, boolean secure)
/*  34:    */   {
/*  35: 87 */     this.domain_ = domain;
/*  36: 88 */     this.name_ = name;
/*  37: 89 */     this.value_ = (value != null ? value : "");
/*  38: 90 */     this.path_ = path;
/*  39: 91 */     this.expires_ = expires;
/*  40: 92 */     this.secure_ = secure;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Cookie(String domain, String name, String value, String path, int maxAge, boolean secure)
/*  44:    */   {
/*  45:109 */     this.domain_ = domain;
/*  46:110 */     this.name_ = name;
/*  47:111 */     this.value_ = (value != null ? value : "");
/*  48:112 */     this.path_ = path;
/*  49:113 */     this.secure_ = secure;
/*  50:115 */     if (maxAge < -1) {
/*  51:116 */       throw new IllegalArgumentException("invalid max age:  " + maxAge);
/*  52:    */     }
/*  53:118 */     if (maxAge >= 0) {
/*  54:119 */       this.expires_ = new Date(System.currentTimeMillis() + maxAge * 1000L);
/*  55:    */     } else {
/*  56:122 */       this.expires_ = null;
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getName()
/*  61:    */   {
/*  62:131 */     return this.name_;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getValue()
/*  66:    */   {
/*  67:139 */     return this.value_;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getDomain()
/*  71:    */   {
/*  72:147 */     return this.domain_;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getPath()
/*  76:    */   {
/*  77:155 */     return this.path_;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Date getExpires()
/*  81:    */   {
/*  82:163 */     return this.expires_;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isSecure()
/*  86:    */   {
/*  87:171 */     return this.secure_;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String toString()
/*  91:    */   {
/*  92:179 */     return this.name_ + "=" + this.value_ + (this.domain_ != null ? ";domain=" + this.domain_ : "") + (this.path_ != null ? ";path=" + this.path_ : "") + (this.expires_ != null ? ";expires=" + this.expires_ : "") + (this.secure_ ? ";secure" : "");
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean equals(Object o)
/*  96:    */   {
/*  97:191 */     if (!(o instanceof Cookie)) {
/*  98:192 */       return false;
/*  99:    */     }
/* 100:194 */     Cookie other = (Cookie)o;
/* 101:195 */     return new EqualsBuilder().append(this.name_, other.name_).append(this.domain_, other.domain_).append(this.path_, other.path_).isEquals();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int hashCode()
/* 105:    */   {
/* 106:204 */     return new HashCodeBuilder().append(this.name_).append(this.domain_).append(this.path_).toHashCode();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public org.apache.http.cookie.Cookie toHttpClient()
/* 110:    */   {
/* 111:212 */     BasicClientCookie cookie = new BasicClientCookie(this.name_, this.value_);
/* 112:    */     
/* 113:214 */     cookie.setDomain(this.domain_);
/* 114:215 */     cookie.setPath(this.path_);
/* 115:216 */     cookie.setExpiryDate(this.expires_);
/* 116:217 */     cookie.setSecure(this.secure_);
/* 117:218 */     return cookie;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static org.apache.http.cookie.Cookie[] toHttpClient(Collection<Cookie> cookies)
/* 121:    */   {
/* 122:227 */     org.apache.http.cookie.Cookie[] array = new org.apache.http.cookie.Cookie[cookies.size()];
/* 123:228 */     Iterator<Cookie> it = cookies.iterator();
/* 124:229 */     for (int i = 0; i < cookies.size(); i++) {
/* 125:230 */       array[i] = ((Cookie)it.next()).toHttpClient();
/* 126:    */     }
/* 127:232 */     return array;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static List<Cookie> fromHttpClient(List<org.apache.http.cookie.Cookie> cookies)
/* 131:    */   {
/* 132:241 */     List<Cookie> list = new ArrayList(cookies.size());
/* 133:242 */     for (org.apache.http.cookie.Cookie c : cookies)
/* 134:    */     {
/* 135:243 */       Cookie cookie = new Cookie(c.getDomain(), c.getName(), c.getValue(), c.getPath(), c.getExpiryDate(), c.isSecure());
/* 136:    */       
/* 137:245 */       list.add(cookie);
/* 138:    */     }
/* 139:247 */     return list;
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.Cookie
 * JD-Core Version:    0.7.0.1
 */