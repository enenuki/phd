/*   1:    */ package org.apache.http.util;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ 
/*   5:    */ public final class EncodingUtils
/*   6:    */ {
/*   7:    */   public static String getString(byte[] data, int offset, int length, String charset)
/*   8:    */   {
/*   9: 59 */     if (data == null) {
/*  10: 60 */       throw new IllegalArgumentException("Parameter may not be null");
/*  11:    */     }
/*  12: 63 */     if ((charset == null) || (charset.length() == 0)) {
/*  13: 64 */       throw new IllegalArgumentException("charset may not be null or empty");
/*  14:    */     }
/*  15:    */     try
/*  16:    */     {
/*  17: 68 */       return new String(data, offset, length, charset);
/*  18:    */     }
/*  19:    */     catch (UnsupportedEncodingException e) {}
/*  20: 70 */     return new String(data, offset, length);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static String getString(byte[] data, String charset)
/*  24:    */   {
/*  25: 85 */     if (data == null) {
/*  26: 86 */       throw new IllegalArgumentException("Parameter may not be null");
/*  27:    */     }
/*  28: 88 */     return getString(data, 0, data.length, charset);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static byte[] getBytes(String data, String charset)
/*  32:    */   {
/*  33:101 */     if (data == null) {
/*  34:102 */       throw new IllegalArgumentException("data may not be null");
/*  35:    */     }
/*  36:105 */     if ((charset == null) || (charset.length() == 0)) {
/*  37:106 */       throw new IllegalArgumentException("charset may not be null or empty");
/*  38:    */     }
/*  39:    */     try
/*  40:    */     {
/*  41:110 */       return data.getBytes(charset);
/*  42:    */     }
/*  43:    */     catch (UnsupportedEncodingException e) {}
/*  44:112 */     return data.getBytes();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static byte[] getAsciiBytes(String data)
/*  48:    */   {
/*  49:124 */     if (data == null) {
/*  50:125 */       throw new IllegalArgumentException("Parameter may not be null");
/*  51:    */     }
/*  52:    */     try
/*  53:    */     {
/*  54:129 */       return data.getBytes("US-ASCII");
/*  55:    */     }
/*  56:    */     catch (UnsupportedEncodingException e)
/*  57:    */     {
/*  58:131 */       throw new Error("HttpClient requires ASCII support");
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static String getAsciiString(byte[] data, int offset, int length)
/*  63:    */   {
/*  64:147 */     if (data == null) {
/*  65:148 */       throw new IllegalArgumentException("Parameter may not be null");
/*  66:    */     }
/*  67:    */     try
/*  68:    */     {
/*  69:152 */       return new String(data, offset, length, "US-ASCII");
/*  70:    */     }
/*  71:    */     catch (UnsupportedEncodingException e)
/*  72:    */     {
/*  73:154 */       throw new Error("HttpClient requires ASCII support");
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static String getAsciiString(byte[] data)
/*  78:    */   {
/*  79:167 */     if (data == null) {
/*  80:168 */       throw new IllegalArgumentException("Parameter may not be null");
/*  81:    */     }
/*  82:170 */     return getAsciiString(data, 0, data.length);
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.util.EncodingUtils
 * JD-Core Version:    0.7.0.1
 */