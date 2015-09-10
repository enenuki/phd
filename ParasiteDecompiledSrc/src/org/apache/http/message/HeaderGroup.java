/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Locale;
/*   7:    */ import org.apache.http.Header;
/*   8:    */ import org.apache.http.HeaderIterator;
/*   9:    */ import org.apache.http.util.CharArrayBuffer;
/*  10:    */ 
/*  11:    */ public class HeaderGroup
/*  12:    */   implements Cloneable, Serializable
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = 2608834160639271617L;
/*  15:    */   private final List headers;
/*  16:    */   
/*  17:    */   public HeaderGroup()
/*  18:    */   {
/*  19: 58 */     this.headers = new ArrayList(16);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void clear()
/*  23:    */   {
/*  24: 65 */     this.headers.clear();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void addHeader(Header header)
/*  28:    */   {
/*  29: 75 */     if (header == null) {
/*  30: 76 */       return;
/*  31:    */     }
/*  32: 78 */     this.headers.add(header);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void removeHeader(Header header)
/*  36:    */   {
/*  37: 87 */     if (header == null) {
/*  38: 88 */       return;
/*  39:    */     }
/*  40: 90 */     this.headers.remove(header);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void updateHeader(Header header)
/*  44:    */   {
/*  45:101 */     if (header == null) {
/*  46:102 */       return;
/*  47:    */     }
/*  48:104 */     for (int i = 0; i < this.headers.size(); i++)
/*  49:    */     {
/*  50:105 */       Header current = (Header)this.headers.get(i);
/*  51:106 */       if (current.getName().equalsIgnoreCase(header.getName()))
/*  52:    */       {
/*  53:107 */         this.headers.set(i, header);
/*  54:108 */         return;
/*  55:    */       }
/*  56:    */     }
/*  57:111 */     this.headers.add(header);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setHeaders(Header[] headers)
/*  61:    */   {
/*  62:122 */     clear();
/*  63:123 */     if (headers == null) {
/*  64:124 */       return;
/*  65:    */     }
/*  66:126 */     for (int i = 0; i < headers.length; i++) {
/*  67:127 */       this.headers.add(headers[i]);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Header getCondensedHeader(String name)
/*  72:    */   {
/*  73:143 */     Header[] headers = getHeaders(name);
/*  74:145 */     if (headers.length == 0) {
/*  75:146 */       return null;
/*  76:    */     }
/*  77:147 */     if (headers.length == 1) {
/*  78:148 */       return headers[0];
/*  79:    */     }
/*  80:150 */     CharArrayBuffer valueBuffer = new CharArrayBuffer(128);
/*  81:151 */     valueBuffer.append(headers[0].getValue());
/*  82:152 */     for (int i = 1; i < headers.length; i++)
/*  83:    */     {
/*  84:153 */       valueBuffer.append(", ");
/*  85:154 */       valueBuffer.append(headers[i].getValue());
/*  86:    */     }
/*  87:157 */     return new BasicHeader(name.toLowerCase(Locale.ENGLISH), valueBuffer.toString());
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Header[] getHeaders(String name)
/*  91:    */   {
/*  92:172 */     ArrayList headersFound = new ArrayList();
/*  93:174 */     for (int i = 0; i < this.headers.size(); i++)
/*  94:    */     {
/*  95:175 */       Header header = (Header)this.headers.get(i);
/*  96:176 */       if (header.getName().equalsIgnoreCase(name)) {
/*  97:177 */         headersFound.add(header);
/*  98:    */       }
/*  99:    */     }
/* 100:181 */     return (Header[])headersFound.toArray(new Header[headersFound.size()]);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Header getFirstHeader(String name)
/* 104:    */   {
/* 105:193 */     for (int i = 0; i < this.headers.size(); i++)
/* 106:    */     {
/* 107:194 */       Header header = (Header)this.headers.get(i);
/* 108:195 */       if (header.getName().equalsIgnoreCase(name)) {
/* 109:196 */         return header;
/* 110:    */       }
/* 111:    */     }
/* 112:199 */     return null;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Header getLastHeader(String name)
/* 116:    */   {
/* 117:212 */     for (int i = this.headers.size() - 1; i >= 0; i--)
/* 118:    */     {
/* 119:213 */       Header header = (Header)this.headers.get(i);
/* 120:214 */       if (header.getName().equalsIgnoreCase(name)) {
/* 121:215 */         return header;
/* 122:    */       }
/* 123:    */     }
/* 124:219 */     return null;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Header[] getAllHeaders()
/* 128:    */   {
/* 129:228 */     return (Header[])this.headers.toArray(new Header[this.headers.size()]);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean containsHeader(String name)
/* 133:    */   {
/* 134:241 */     for (int i = 0; i < this.headers.size(); i++)
/* 135:    */     {
/* 136:242 */       Header header = (Header)this.headers.get(i);
/* 137:243 */       if (header.getName().equalsIgnoreCase(name)) {
/* 138:244 */         return true;
/* 139:    */       }
/* 140:    */     }
/* 141:248 */     return false;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public HeaderIterator iterator()
/* 145:    */   {
/* 146:259 */     return new BasicListHeaderIterator(this.headers, null);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public HeaderIterator iterator(String name)
/* 150:    */   {
/* 151:273 */     return new BasicListHeaderIterator(this.headers, name);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public HeaderGroup copy()
/* 155:    */   {
/* 156:284 */     HeaderGroup clone = new HeaderGroup();
/* 157:285 */     clone.headers.addAll(this.headers);
/* 158:286 */     return clone;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Object clone()
/* 162:    */     throws CloneNotSupportedException
/* 163:    */   {
/* 164:290 */     HeaderGroup clone = (HeaderGroup)super.clone();
/* 165:291 */     clone.headers.clear();
/* 166:292 */     clone.headers.addAll(this.headers);
/* 167:293 */     return clone;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String toString()
/* 171:    */   {
/* 172:297 */     return this.headers.toString();
/* 173:    */   }
/* 174:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.HeaderGroup
 * JD-Core Version:    0.7.0.1
 */