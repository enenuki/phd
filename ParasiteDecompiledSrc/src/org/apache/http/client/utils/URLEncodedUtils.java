/*   1:    */ package org.apache.http.client.utils;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.net.URI;
/*   6:    */ import java.net.URLDecoder;
/*   7:    */ import java.net.URLEncoder;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Scanner;
/*  12:    */ import org.apache.http.Header;
/*  13:    */ import org.apache.http.HeaderElement;
/*  14:    */ import org.apache.http.HttpEntity;
/*  15:    */ import org.apache.http.NameValuePair;
/*  16:    */ import org.apache.http.annotation.Immutable;
/*  17:    */ import org.apache.http.message.BasicNameValuePair;
/*  18:    */ import org.apache.http.util.EntityUtils;
/*  19:    */ 
/*  20:    */ @Immutable
/*  21:    */ public class URLEncodedUtils
/*  22:    */ {
/*  23:    */   public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
/*  24:    */   private static final String PARAMETER_SEPARATOR = "&";
/*  25:    */   private static final String NAME_VALUE_SEPARATOR = "=";
/*  26:    */   
/*  27:    */   public static List<NameValuePair> parse(URI uri, String encoding)
/*  28:    */   {
/*  29: 76 */     List<NameValuePair> result = Collections.emptyList();
/*  30: 77 */     String query = uri.getRawQuery();
/*  31: 78 */     if ((query != null) && (query.length() > 0))
/*  32:    */     {
/*  33: 79 */       result = new ArrayList();
/*  34: 80 */       parse(result, new Scanner(query), encoding);
/*  35:    */     }
/*  36: 82 */     return result;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static List<NameValuePair> parse(HttpEntity entity)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 99 */     List<NameValuePair> result = Collections.emptyList();
/*  43:    */     
/*  44:101 */     String contentType = null;
/*  45:102 */     String charset = null;
/*  46:    */     
/*  47:104 */     Header h = entity.getContentType();
/*  48:105 */     if (h != null)
/*  49:    */     {
/*  50:106 */       HeaderElement[] elems = h.getElements();
/*  51:107 */       if (elems.length > 0)
/*  52:    */       {
/*  53:108 */         HeaderElement elem = elems[0];
/*  54:109 */         contentType = elem.getName();
/*  55:110 */         NameValuePair param = elem.getParameterByName("charset");
/*  56:111 */         if (param != null) {
/*  57:112 */           charset = param.getValue();
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61:117 */     if ((contentType != null) && (contentType.equalsIgnoreCase("application/x-www-form-urlencoded")))
/*  62:    */     {
/*  63:118 */       String content = EntityUtils.toString(entity, "ASCII");
/*  64:119 */       if ((content != null) && (content.length() > 0))
/*  65:    */       {
/*  66:120 */         result = new ArrayList();
/*  67:121 */         parse(result, new Scanner(content), charset);
/*  68:    */       }
/*  69:    */     }
/*  70:124 */     return result;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static boolean isEncoded(HttpEntity entity)
/*  74:    */   {
/*  75:132 */     Header h = entity.getContentType();
/*  76:133 */     if (h != null)
/*  77:    */     {
/*  78:134 */       HeaderElement[] elems = h.getElements();
/*  79:135 */       if (elems.length > 0)
/*  80:    */       {
/*  81:136 */         String contentType = elems[0].getName();
/*  82:137 */         return contentType.equalsIgnoreCase("application/x-www-form-urlencoded");
/*  83:    */       }
/*  84:139 */       return false;
/*  85:    */     }
/*  86:142 */     return false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void parse(List<NameValuePair> parameters, Scanner scanner, String encoding)
/*  90:    */   {
/*  91:164 */     scanner.useDelimiter("&");
/*  92:165 */     while (scanner.hasNext())
/*  93:    */     {
/*  94:166 */       String[] nameValue = scanner.next().split("=");
/*  95:167 */       if ((nameValue.length == 0) || (nameValue.length > 2)) {
/*  96:168 */         throw new IllegalArgumentException("bad parameter");
/*  97:    */       }
/*  98:170 */       String name = decode(nameValue[0], encoding);
/*  99:171 */       String value = null;
/* 100:172 */       if (nameValue.length == 2) {
/* 101:173 */         value = decode(nameValue[1], encoding);
/* 102:    */       }
/* 103:174 */       parameters.add(new BasicNameValuePair(name, value));
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static String format(List<? extends NameValuePair> parameters, String encoding)
/* 108:    */   {
/* 109:188 */     StringBuilder result = new StringBuilder();
/* 110:189 */     for (NameValuePair parameter : parameters)
/* 111:    */     {
/* 112:190 */       String encodedName = encode(parameter.getName(), encoding);
/* 113:191 */       String value = parameter.getValue();
/* 114:192 */       String encodedValue = value != null ? encode(value, encoding) : "";
/* 115:193 */       if (result.length() > 0) {
/* 116:194 */         result.append("&");
/* 117:    */       }
/* 118:195 */       result.append(encodedName);
/* 119:196 */       result.append("=");
/* 120:197 */       result.append(encodedValue);
/* 121:    */     }
/* 122:199 */     return result.toString();
/* 123:    */   }
/* 124:    */   
/* 125:    */   private static String decode(String content, String encoding)
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:204 */       return URLDecoder.decode(content, encoding != null ? encoding : "ISO-8859-1");
/* 130:    */     }
/* 131:    */     catch (UnsupportedEncodingException problem)
/* 132:    */     {
/* 133:207 */       throw new IllegalArgumentException(problem);
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   private static String encode(String content, String encoding)
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:213 */       return URLEncoder.encode(content, encoding != null ? encoding : "ISO-8859-1");
/* 142:    */     }
/* 143:    */     catch (UnsupportedEncodingException problem)
/* 144:    */     {
/* 145:216 */       throw new IllegalArgumentException(problem);
/* 146:    */     }
/* 147:    */   }
/* 148:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.utils.URLEncodedUtils
 * JD-Core Version:    0.7.0.1
 */