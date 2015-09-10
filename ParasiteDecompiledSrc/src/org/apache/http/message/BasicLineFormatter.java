/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import org.apache.http.FormattedHeader;
/*   4:    */ import org.apache.http.Header;
/*   5:    */ import org.apache.http.ProtocolVersion;
/*   6:    */ import org.apache.http.RequestLine;
/*   7:    */ import org.apache.http.StatusLine;
/*   8:    */ import org.apache.http.util.CharArrayBuffer;
/*   9:    */ 
/*  10:    */ public class BasicLineFormatter
/*  11:    */   implements LineFormatter
/*  12:    */ {
/*  13: 57 */   public static final BasicLineFormatter DEFAULT = new BasicLineFormatter();
/*  14:    */   
/*  15:    */   protected CharArrayBuffer initBuffer(CharArrayBuffer buffer)
/*  16:    */   {
/*  17: 73 */     if (buffer != null) {
/*  18: 74 */       buffer.clear();
/*  19:    */     } else {
/*  20: 76 */       buffer = new CharArrayBuffer(64);
/*  21:    */     }
/*  22: 78 */     return buffer;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static final String formatProtocolVersion(ProtocolVersion version, LineFormatter formatter)
/*  26:    */   {
/*  27: 95 */     if (formatter == null) {
/*  28: 96 */       formatter = DEFAULT;
/*  29:    */     }
/*  30: 97 */     return formatter.appendProtocolVersion(null, version).toString();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public CharArrayBuffer appendProtocolVersion(CharArrayBuffer buffer, ProtocolVersion version)
/*  34:    */   {
/*  35:104 */     if (version == null) {
/*  36:105 */       throw new IllegalArgumentException("Protocol version may not be null");
/*  37:    */     }
/*  38:110 */     CharArrayBuffer result = buffer;
/*  39:111 */     int len = estimateProtocolVersionLen(version);
/*  40:112 */     if (result == null) {
/*  41:113 */       result = new CharArrayBuffer(len);
/*  42:    */     } else {
/*  43:115 */       result.ensureCapacity(len);
/*  44:    */     }
/*  45:118 */     result.append(version.getProtocol());
/*  46:119 */     result.append('/');
/*  47:120 */     result.append(Integer.toString(version.getMajor()));
/*  48:121 */     result.append('.');
/*  49:122 */     result.append(Integer.toString(version.getMinor()));
/*  50:    */     
/*  51:124 */     return result;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected int estimateProtocolVersionLen(ProtocolVersion version)
/*  55:    */   {
/*  56:138 */     return version.getProtocol().length() + 4;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static final String formatRequestLine(RequestLine reqline, LineFormatter formatter)
/*  60:    */   {
/*  61:154 */     if (formatter == null) {
/*  62:155 */       formatter = DEFAULT;
/*  63:    */     }
/*  64:156 */     return formatter.formatRequestLine(null, reqline).toString();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public CharArrayBuffer formatRequestLine(CharArrayBuffer buffer, RequestLine reqline)
/*  68:    */   {
/*  69:163 */     if (reqline == null) {
/*  70:164 */       throw new IllegalArgumentException("Request line may not be null");
/*  71:    */     }
/*  72:168 */     CharArrayBuffer result = initBuffer(buffer);
/*  73:169 */     doFormatRequestLine(result, reqline);
/*  74:    */     
/*  75:171 */     return result;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void doFormatRequestLine(CharArrayBuffer buffer, RequestLine reqline)
/*  79:    */   {
/*  80:185 */     String method = reqline.getMethod();
/*  81:186 */     String uri = reqline.getUri();
/*  82:    */     
/*  83:    */ 
/*  84:189 */     int len = method.length() + 1 + uri.length() + 1 + estimateProtocolVersionLen(reqline.getProtocolVersion());
/*  85:    */     
/*  86:191 */     buffer.ensureCapacity(len);
/*  87:    */     
/*  88:193 */     buffer.append(method);
/*  89:194 */     buffer.append(' ');
/*  90:195 */     buffer.append(uri);
/*  91:196 */     buffer.append(' ');
/*  92:197 */     appendProtocolVersion(buffer, reqline.getProtocolVersion());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static final String formatStatusLine(StatusLine statline, LineFormatter formatter)
/*  96:    */   {
/*  97:214 */     if (formatter == null) {
/*  98:215 */       formatter = DEFAULT;
/*  99:    */     }
/* 100:216 */     return formatter.formatStatusLine(null, statline).toString();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public CharArrayBuffer formatStatusLine(CharArrayBuffer buffer, StatusLine statline)
/* 104:    */   {
/* 105:223 */     if (statline == null) {
/* 106:224 */       throw new IllegalArgumentException("Status line may not be null");
/* 107:    */     }
/* 108:228 */     CharArrayBuffer result = initBuffer(buffer);
/* 109:229 */     doFormatStatusLine(result, statline);
/* 110:    */     
/* 111:231 */     return result;
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected void doFormatStatusLine(CharArrayBuffer buffer, StatusLine statline)
/* 115:    */   {
/* 116:246 */     int len = estimateProtocolVersionLen(statline.getProtocolVersion()) + 1 + 3 + 1;
/* 117:    */     
/* 118:248 */     String reason = statline.getReasonPhrase();
/* 119:249 */     if (reason != null) {
/* 120:250 */       len += reason.length();
/* 121:    */     }
/* 122:252 */     buffer.ensureCapacity(len);
/* 123:    */     
/* 124:254 */     appendProtocolVersion(buffer, statline.getProtocolVersion());
/* 125:255 */     buffer.append(' ');
/* 126:256 */     buffer.append(Integer.toString(statline.getStatusCode()));
/* 127:257 */     buffer.append(' ');
/* 128:258 */     if (reason != null) {
/* 129:259 */       buffer.append(reason);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static final String formatHeader(Header header, LineFormatter formatter)
/* 134:    */   {
/* 135:276 */     if (formatter == null) {
/* 136:277 */       formatter = DEFAULT;
/* 137:    */     }
/* 138:278 */     return formatter.formatHeader(null, header).toString();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public CharArrayBuffer formatHeader(CharArrayBuffer buffer, Header header)
/* 142:    */   {
/* 143:285 */     if (header == null) {
/* 144:286 */       throw new IllegalArgumentException("Header may not be null");
/* 145:    */     }
/* 146:289 */     CharArrayBuffer result = null;
/* 147:291 */     if ((header instanceof FormattedHeader))
/* 148:    */     {
/* 149:293 */       result = ((FormattedHeader)header).getBuffer();
/* 150:    */     }
/* 151:    */     else
/* 152:    */     {
/* 153:295 */       result = initBuffer(buffer);
/* 154:296 */       doFormatHeader(result, header);
/* 155:    */     }
/* 156:298 */     return result;
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected void doFormatHeader(CharArrayBuffer buffer, Header header)
/* 160:    */   {
/* 161:313 */     String name = header.getName();
/* 162:314 */     String value = header.getValue();
/* 163:    */     
/* 164:316 */     int len = name.length() + 2;
/* 165:317 */     if (value != null) {
/* 166:318 */       len += value.length();
/* 167:    */     }
/* 168:320 */     buffer.ensureCapacity(len);
/* 169:    */     
/* 170:322 */     buffer.append(name);
/* 171:323 */     buffer.append(": ");
/* 172:324 */     if (value != null) {
/* 173:325 */       buffer.append(value);
/* 174:    */     }
/* 175:    */   }
/* 176:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicLineFormatter
 * JD-Core Version:    0.7.0.1
 */