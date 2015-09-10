/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.http.HeaderElement;
/*   6:    */ import org.apache.http.NameValuePair;
/*   7:    */ import org.apache.http.ParseException;
/*   8:    */ import org.apache.http.annotation.Immutable;
/*   9:    */ import org.apache.http.message.BasicHeaderElement;
/*  10:    */ import org.apache.http.message.BasicNameValuePair;
/*  11:    */ import org.apache.http.message.ParserCursor;
/*  12:    */ import org.apache.http.protocol.HTTP;
/*  13:    */ import org.apache.http.util.CharArrayBuffer;
/*  14:    */ 
/*  15:    */ @Immutable
/*  16:    */ public class NetscapeDraftHeaderParser
/*  17:    */ {
/*  18: 51 */   public static final NetscapeDraftHeaderParser DEFAULT = new NetscapeDraftHeaderParser();
/*  19:    */   
/*  20:    */   public HeaderElement parseHeader(CharArrayBuffer buffer, ParserCursor cursor)
/*  21:    */     throws ParseException
/*  22:    */   {
/*  23: 60 */     if (buffer == null) {
/*  24: 61 */       throw new IllegalArgumentException("Char array buffer may not be null");
/*  25:    */     }
/*  26: 63 */     if (cursor == null) {
/*  27: 64 */       throw new IllegalArgumentException("Parser cursor may not be null");
/*  28:    */     }
/*  29: 66 */     NameValuePair nvp = parseNameValuePair(buffer, cursor);
/*  30: 67 */     List<NameValuePair> params = new ArrayList();
/*  31: 68 */     while (!cursor.atEnd())
/*  32:    */     {
/*  33: 69 */       NameValuePair param = parseNameValuePair(buffer, cursor);
/*  34: 70 */       params.add(param);
/*  35:    */     }
/*  36: 72 */     return new BasicHeaderElement(nvp.getName(), nvp.getValue(), (NameValuePair[])params.toArray(new NameValuePair[params.size()]));
/*  37:    */   }
/*  38:    */   
/*  39:    */   private NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor)
/*  40:    */   {
/*  41: 79 */     boolean terminated = false;
/*  42:    */     
/*  43: 81 */     int pos = cursor.getPos();
/*  44: 82 */     int indexFrom = cursor.getPos();
/*  45: 83 */     int indexTo = cursor.getUpperBound();
/*  46:    */     
/*  47:    */ 
/*  48: 86 */     String name = null;
/*  49: 87 */     while (pos < indexTo)
/*  50:    */     {
/*  51: 88 */       char ch = buffer.charAt(pos);
/*  52: 89 */       if (ch == '=') {
/*  53:    */         break;
/*  54:    */       }
/*  55: 92 */       if (ch == ';')
/*  56:    */       {
/*  57: 93 */         terminated = true;
/*  58: 94 */         break;
/*  59:    */       }
/*  60: 96 */       pos++;
/*  61:    */     }
/*  62: 99 */     if (pos == indexTo)
/*  63:    */     {
/*  64:100 */       terminated = true;
/*  65:101 */       name = buffer.substringTrimmed(indexFrom, indexTo);
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69:103 */       name = buffer.substringTrimmed(indexFrom, pos);
/*  70:104 */       pos++;
/*  71:    */     }
/*  72:107 */     if (terminated)
/*  73:    */     {
/*  74:108 */       cursor.updatePos(pos);
/*  75:109 */       return new BasicNameValuePair(name, null);
/*  76:    */     }
/*  77:113 */     String value = null;
/*  78:114 */     int i1 = pos;
/*  79:116 */     while (pos < indexTo)
/*  80:    */     {
/*  81:117 */       char ch = buffer.charAt(pos);
/*  82:118 */       if (ch == ';')
/*  83:    */       {
/*  84:119 */         terminated = true;
/*  85:120 */         break;
/*  86:    */       }
/*  87:122 */       pos++;
/*  88:    */     }
/*  89:125 */     int i2 = pos;
/*  90:127 */     while ((i1 < i2) && (HTTP.isWhitespace(buffer.charAt(i1)))) {
/*  91:128 */       i1++;
/*  92:    */     }
/*  93:131 */     while ((i2 > i1) && (HTTP.isWhitespace(buffer.charAt(i2 - 1)))) {
/*  94:132 */       i2--;
/*  95:    */     }
/*  96:134 */     value = buffer.substring(i1, i2);
/*  97:135 */     if (terminated) {
/*  98:136 */       pos++;
/*  99:    */     }
/* 100:138 */     cursor.updatePos(pos);
/* 101:139 */     return new BasicNameValuePair(name, value);
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.NetscapeDraftHeaderParser
 * JD-Core Version:    0.7.0.1
 */