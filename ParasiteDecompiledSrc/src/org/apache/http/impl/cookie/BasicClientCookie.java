/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Locale;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.apache.http.annotation.NotThreadSafe;
/*   9:    */ import org.apache.http.cookie.ClientCookie;
/*  10:    */ import org.apache.http.cookie.SetCookie;
/*  11:    */ 
/*  12:    */ @NotThreadSafe
/*  13:    */ public class BasicClientCookie
/*  14:    */   implements SetCookie, ClientCookie, Cloneable, Serializable
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = -3869795591041535538L;
/*  17:    */   private final String name;
/*  18:    */   private Map<String, String> attribs;
/*  19:    */   private String value;
/*  20:    */   private String cookieComment;
/*  21:    */   private String cookieDomain;
/*  22:    */   private Date cookieExpiryDate;
/*  23:    */   private String cookiePath;
/*  24:    */   private boolean isSecure;
/*  25:    */   private int cookieVersion;
/*  26:    */   
/*  27:    */   public BasicClientCookie(String name, String value)
/*  28:    */   {
/*  29: 59 */     if (name == null) {
/*  30: 60 */       throw new IllegalArgumentException("Name may not be null");
/*  31:    */     }
/*  32: 62 */     this.name = name;
/*  33: 63 */     this.attribs = new HashMap();
/*  34: 64 */     this.value = value;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getName()
/*  38:    */   {
/*  39: 73 */     return this.name;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getValue()
/*  43:    */   {
/*  44: 82 */     return this.value;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setValue(String value)
/*  48:    */   {
/*  49: 91 */     this.value = value;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getComment()
/*  53:    */   {
/*  54:103 */     return this.cookieComment;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setComment(String comment)
/*  58:    */   {
/*  59:115 */     this.cookieComment = comment;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getCommentURL()
/*  63:    */   {
/*  64:123 */     return null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Date getExpiryDate()
/*  68:    */   {
/*  69:139 */     return this.cookieExpiryDate;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setExpiryDate(Date expiryDate)
/*  73:    */   {
/*  74:154 */     this.cookieExpiryDate = expiryDate;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isPersistent()
/*  78:    */   {
/*  79:166 */     return null != this.cookieExpiryDate;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getDomain()
/*  83:    */   {
/*  84:178 */     return this.cookieDomain;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setDomain(String domain)
/*  88:    */   {
/*  89:189 */     if (domain != null) {
/*  90:190 */       this.cookieDomain = domain.toLowerCase(Locale.ENGLISH);
/*  91:    */     } else {
/*  92:192 */       this.cookieDomain = null;
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getPath()
/*  97:    */   {
/*  98:205 */     return this.cookiePath;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setPath(String path)
/* 102:    */   {
/* 103:217 */     this.cookiePath = path;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isSecure()
/* 107:    */   {
/* 108:225 */     return this.isSecure;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setSecure(boolean secure)
/* 112:    */   {
/* 113:241 */     this.isSecure = secure;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int[] getPorts()
/* 117:    */   {
/* 118:249 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getVersion()
/* 122:    */   {
/* 123:263 */     return this.cookieVersion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setVersion(int version)
/* 127:    */   {
/* 128:275 */     this.cookieVersion = version;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isExpired(Date date)
/* 132:    */   {
/* 133:285 */     if (date == null) {
/* 134:286 */       throw new IllegalArgumentException("Date may not be null");
/* 135:    */     }
/* 136:288 */     return (this.cookieExpiryDate != null) && (this.cookieExpiryDate.getTime() <= date.getTime());
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setAttribute(String name, String value)
/* 140:    */   {
/* 141:293 */     this.attribs.put(name, value);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getAttribute(String name)
/* 145:    */   {
/* 146:297 */     return (String)this.attribs.get(name);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean containsAttribute(String name)
/* 150:    */   {
/* 151:301 */     return this.attribs.get(name) != null;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Object clone()
/* 155:    */     throws CloneNotSupportedException
/* 156:    */   {
/* 157:306 */     BasicClientCookie clone = (BasicClientCookie)super.clone();
/* 158:307 */     clone.attribs = new HashMap(this.attribs);
/* 159:308 */     return clone;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String toString()
/* 163:    */   {
/* 164:313 */     StringBuilder buffer = new StringBuilder();
/* 165:314 */     buffer.append("[version: ");
/* 166:315 */     buffer.append(Integer.toString(this.cookieVersion));
/* 167:316 */     buffer.append("]");
/* 168:317 */     buffer.append("[name: ");
/* 169:318 */     buffer.append(this.name);
/* 170:319 */     buffer.append("]");
/* 171:320 */     buffer.append("[value: ");
/* 172:321 */     buffer.append(this.value);
/* 173:322 */     buffer.append("]");
/* 174:323 */     buffer.append("[domain: ");
/* 175:324 */     buffer.append(this.cookieDomain);
/* 176:325 */     buffer.append("]");
/* 177:326 */     buffer.append("[path: ");
/* 178:327 */     buffer.append(this.cookiePath);
/* 179:328 */     buffer.append("]");
/* 180:329 */     buffer.append("[expiry: ");
/* 181:330 */     buffer.append(this.cookieExpiryDate);
/* 182:331 */     buffer.append("]");
/* 183:332 */     return buffer.toString();
/* 184:    */   }
/* 185:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BasicClientCookie
 * JD-Core Version:    0.7.0.1
 */