/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.net.URL;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.apache.commons.lang.math.NumberUtils;
/*  10:    */ import org.apache.http.impl.cookie.DateParseException;
/*  11:    */ import org.apache.http.impl.cookie.DateUtils;
/*  12:    */ import org.w3c.dom.css.CSSStyleSheet;
/*  13:    */ 
/*  14:    */ public class Cache
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:    */   private int maxSize_;
/*  18:    */   private final Map<String, Entry> entries_;
/*  19:    */   
/*  20:    */   public Cache()
/*  21:    */   {
/*  22: 40 */     this.maxSize_ = 40;
/*  23:    */     
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31: 49 */     this.entries_ = Collections.synchronizedMap(new HashMap(this.maxSize_));
/*  32:    */   }
/*  33:    */   
/*  34:    */   private static class Entry
/*  35:    */     implements Comparable<Entry>, Serializable
/*  36:    */   {
/*  37:    */     private final String key_;
/*  38:    */     private final Object value_;
/*  39:    */     private long lastAccess_;
/*  40:    */     
/*  41:    */     Entry(String key, Object value)
/*  42:    */     {
/*  43: 60 */       this.key_ = key;
/*  44: 61 */       this.value_ = value;
/*  45: 62 */       this.lastAccess_ = System.currentTimeMillis();
/*  46:    */     }
/*  47:    */     
/*  48:    */     public int compareTo(Entry other)
/*  49:    */     {
/*  50: 69 */       return NumberUtils.compare((float)this.lastAccess_, (float)other.lastAccess_);
/*  51:    */     }
/*  52:    */     
/*  53:    */     public void touch()
/*  54:    */     {
/*  55: 76 */       this.lastAccess_ = System.currentTimeMillis();
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void cacheIfPossible(WebRequest request, WebResponse response, Object toCache)
/*  60:    */   {
/*  61: 90 */     if (isCacheable(request, response))
/*  62:    */     {
/*  63: 91 */       String url = response.getWebRequest().getUrl().toString();
/*  64: 92 */       Entry entry = new Entry(url, toCache);
/*  65: 93 */       this.entries_.put(entry.key_, entry);
/*  66: 94 */       deleteOverflow();
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void cache(String css, CSSStyleSheet styleSheet)
/*  71:    */   {
/*  72:110 */     Entry entry = new Entry(css, styleSheet);
/*  73:111 */     this.entries_.put(entry.key_, entry);
/*  74:112 */     deleteOverflow();
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void deleteOverflow()
/*  78:    */   {
/*  79:119 */     synchronized (this.entries_)
/*  80:    */     {
/*  81:120 */       while (this.entries_.size() > this.maxSize_)
/*  82:    */       {
/*  83:121 */         Entry oldestEntry = (Entry)Collections.min(this.entries_.values());
/*  84:122 */         this.entries_.remove(oldestEntry.key_);
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected boolean isCacheable(WebRequest request, WebResponse response)
/*  90:    */   {
/*  91:135 */     return (HttpMethod.GET == response.getWebRequest().getHttpMethod()) && (!isDynamicContent(response));
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected boolean isDynamicContent(WebResponse response)
/*  95:    */   {
/*  96:155 */     Date lastModified = parseDateHeader(response, "Last-Modified");
/*  97:156 */     Date expires = parseDateHeader(response, "Expires");
/*  98:    */     
/*  99:158 */     long delay = 600000L;
/* 100:159 */     long now = getCurrentTimestamp();
/* 101:    */     
/* 102:161 */     boolean cacheableContent = ((expires != null) && (expires.getTime() - now > 600000L)) || ((expires == null) && (lastModified != null) && (now - lastModified.getTime() > 600000L));
/* 103:    */     
/* 104:    */ 
/* 105:164 */     return !cacheableContent;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected long getCurrentTimestamp()
/* 109:    */   {
/* 110:172 */     return System.currentTimeMillis();
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected Date parseDateHeader(WebResponse response, String headerName)
/* 114:    */   {
/* 115:185 */     String value = response.getResponseHeaderValue(headerName);
/* 116:186 */     if (value == null) {
/* 117:187 */       return null;
/* 118:    */     }
/* 119:189 */     if (value.matches("-?\\d+")) {
/* 120:190 */       return new Date();
/* 121:    */     }
/* 122:    */     try
/* 123:    */     {
/* 124:193 */       return DateUtils.parseDate(value);
/* 125:    */     }
/* 126:    */     catch (DateParseException e) {}
/* 127:196 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Object getCachedObject(WebRequest request)
/* 131:    */   {
/* 132:208 */     if (HttpMethod.GET != request.getHttpMethod()) {
/* 133:209 */       return null;
/* 134:    */     }
/* 135:211 */     Entry cachedEntry = (Entry)this.entries_.get(request.getUrl().toString());
/* 136:212 */     if (cachedEntry == null) {
/* 137:213 */       return null;
/* 138:    */     }
/* 139:215 */     synchronized (this.entries_)
/* 140:    */     {
/* 141:216 */       cachedEntry.touch();
/* 142:    */     }
/* 143:218 */     return cachedEntry.value_;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public CSSStyleSheet getCachedStyleSheet(String css)
/* 147:    */   {
/* 148:229 */     Entry cachedEntry = (Entry)this.entries_.get(css);
/* 149:230 */     if (cachedEntry == null) {
/* 150:231 */       return null;
/* 151:    */     }
/* 152:233 */     synchronized (this.entries_)
/* 153:    */     {
/* 154:234 */       cachedEntry.touch();
/* 155:    */     }
/* 156:236 */     return (CSSStyleSheet)cachedEntry.value_;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int getMaxSize()
/* 160:    */   {
/* 161:246 */     return this.maxSize_;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setMaxSize(int maxSize)
/* 165:    */   {
/* 166:256 */     if (maxSize < 0) {
/* 167:257 */       throw new IllegalArgumentException("Illegal value for maxSize: " + maxSize);
/* 168:    */     }
/* 169:259 */     this.maxSize_ = maxSize;
/* 170:260 */     deleteOverflow();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int getSize()
/* 174:    */   {
/* 175:269 */     return this.entries_.size();
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void clear()
/* 179:    */   {
/* 180:276 */     synchronized (this.entries_)
/* 181:    */     {
/* 182:277 */       this.entries_.clear();
/* 183:    */     }
/* 184:    */   }
/* 185:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.Cache
 * JD-Core Version:    0.7.0.1
 */