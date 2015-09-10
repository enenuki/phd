/*   1:    */ package org.apache.http.util;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.Reader;
/*   7:    */ import org.apache.http.Header;
/*   8:    */ import org.apache.http.HeaderElement;
/*   9:    */ import org.apache.http.HttpEntity;
/*  10:    */ import org.apache.http.NameValuePair;
/*  11:    */ import org.apache.http.ParseException;
/*  12:    */ 
/*  13:    */ public final class EntityUtils
/*  14:    */ {
/*  15:    */   public static void consume(HttpEntity entity)
/*  16:    */     throws IOException
/*  17:    */   {
/*  18: 61 */     if (entity == null) {
/*  19: 62 */       return;
/*  20:    */     }
/*  21: 64 */     if (entity.isStreaming())
/*  22:    */     {
/*  23: 65 */       InputStream instream = entity.getContent();
/*  24: 66 */       if (instream != null) {
/*  25: 67 */         instream.close();
/*  26:    */       }
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static byte[] toByteArray(HttpEntity entity)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 82 */     if (entity == null) {
/*  34: 83 */       throw new IllegalArgumentException("HTTP entity may not be null");
/*  35:    */     }
/*  36: 85 */     InputStream instream = entity.getContent();
/*  37: 86 */     if (instream == null) {
/*  38: 87 */       return null;
/*  39:    */     }
/*  40:    */     try
/*  41:    */     {
/*  42: 90 */       if (entity.getContentLength() > 2147483647L) {
/*  43: 91 */         throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
/*  44:    */       }
/*  45: 93 */       int i = (int)entity.getContentLength();
/*  46: 94 */       if (i < 0) {
/*  47: 95 */         i = 4096;
/*  48:    */       }
/*  49: 97 */       ByteArrayBuffer buffer = new ByteArrayBuffer(i);
/*  50: 98 */       byte[] tmp = new byte[4096];
/*  51:    */       int l;
/*  52:100 */       while ((l = instream.read(tmp)) != -1) {
/*  53:101 */         buffer.append(tmp, 0, l);
/*  54:    */       }
/*  55:103 */       return buffer.toByteArray();
/*  56:    */     }
/*  57:    */     finally
/*  58:    */     {
/*  59:105 */       instream.close();
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String getContentCharSet(HttpEntity entity)
/*  64:    */     throws ParseException
/*  65:    */   {
/*  66:118 */     if (entity == null) {
/*  67:119 */       throw new IllegalArgumentException("HTTP entity may not be null");
/*  68:    */     }
/*  69:121 */     String charset = null;
/*  70:122 */     if (entity.getContentType() != null)
/*  71:    */     {
/*  72:123 */       HeaderElement[] values = entity.getContentType().getElements();
/*  73:124 */       if (values.length > 0)
/*  74:    */       {
/*  75:125 */         NameValuePair param = values[0].getParameterByName("charset");
/*  76:126 */         if (param != null) {
/*  77:127 */           charset = param.getValue();
/*  78:    */         }
/*  79:    */       }
/*  80:    */     }
/*  81:131 */     return charset;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static String getContentMimeType(HttpEntity entity)
/*  85:    */     throws ParseException
/*  86:    */   {
/*  87:145 */     if (entity == null) {
/*  88:146 */       throw new IllegalArgumentException("HTTP entity may not be null");
/*  89:    */     }
/*  90:148 */     String mimeType = null;
/*  91:149 */     if (entity.getContentType() != null)
/*  92:    */     {
/*  93:150 */       HeaderElement[] values = entity.getContentType().getElements();
/*  94:151 */       if (values.length > 0) {
/*  95:152 */         mimeType = values[0].getName();
/*  96:    */       }
/*  97:    */     }
/*  98:155 */     return mimeType;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static String toString(HttpEntity entity, String defaultCharset)
/* 102:    */     throws IOException, ParseException
/* 103:    */   {
/* 104:173 */     if (entity == null) {
/* 105:174 */       throw new IllegalArgumentException("HTTP entity may not be null");
/* 106:    */     }
/* 107:176 */     InputStream instream = entity.getContent();
/* 108:177 */     if (instream == null) {
/* 109:178 */       return null;
/* 110:    */     }
/* 111:    */     try
/* 112:    */     {
/* 113:181 */       if (entity.getContentLength() > 2147483647L) {
/* 114:182 */         throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
/* 115:    */       }
/* 116:184 */       int i = (int)entity.getContentLength();
/* 117:185 */       if (i < 0) {
/* 118:186 */         i = 4096;
/* 119:    */       }
/* 120:188 */       String charset = getContentCharSet(entity);
/* 121:189 */       if (charset == null) {
/* 122:190 */         charset = defaultCharset;
/* 123:    */       }
/* 124:192 */       if (charset == null) {
/* 125:193 */         charset = "ISO-8859-1";
/* 126:    */       }
/* 127:195 */       Reader reader = new InputStreamReader(instream, charset);
/* 128:196 */       CharArrayBuffer buffer = new CharArrayBuffer(i);
/* 129:197 */       char[] tmp = new char[1024];
/* 130:    */       int l;
/* 131:199 */       while ((l = reader.read(tmp)) != -1) {
/* 132:200 */         buffer.append(tmp, 0, l);
/* 133:    */       }
/* 134:202 */       return buffer.toString();
/* 135:    */     }
/* 136:    */     finally
/* 137:    */     {
/* 138:204 */       instream.close();
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static String toString(HttpEntity entity)
/* 143:    */     throws IOException, ParseException
/* 144:    */   {
/* 145:221 */     return toString(entity, null);
/* 146:    */   }
/* 147:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.util.EntityUtils
 * JD-Core Version:    0.7.0.1
 */