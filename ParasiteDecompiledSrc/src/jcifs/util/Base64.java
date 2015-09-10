/*  1:   */ package jcifs.util;
/*  2:   */ 
/*  3:   */ public class Base64
/*  4:   */ {
/*  5:   */   private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
/*  6:   */   
/*  7:   */   public static String encode(byte[] bytes)
/*  8:   */   {
/*  9:34 */     int length = bytes.length;
/* 10:35 */     if (length == 0) {
/* 11:35 */       return "";
/* 12:   */     }
/* 13:36 */     StringBuffer buffer = new StringBuffer((int)Math.ceil(length / 3.0D) * 4);
/* 14:   */     
/* 15:38 */     int remainder = length % 3;
/* 16:39 */     length -= remainder;
/* 17:   */     
/* 18:41 */     int i = 0;
/* 19:42 */     while (i < length)
/* 20:   */     {
/* 21:43 */       int block = (bytes[(i++)] & 0xFF) << 16 | (bytes[(i++)] & 0xFF) << 8 | bytes[(i++)] & 0xFF;
/* 22:   */       
/* 23:45 */       buffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(block >>> 18));
/* 24:46 */       buffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(block >>> 12 & 0x3F));
/* 25:47 */       buffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(block >>> 6 & 0x3F));
/* 26:48 */       buffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(block & 0x3F));
/* 27:   */     }
/* 28:50 */     if (remainder == 0) {
/* 29:50 */       return buffer.toString();
/* 30:   */     }
/* 31:51 */     if (remainder == 1)
/* 32:   */     {
/* 33:52 */       int block = (bytes[i] & 0xFF) << 4;
/* 34:53 */       buffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(block >>> 6));
/* 35:54 */       buffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(block & 0x3F));
/* 36:55 */       buffer.append("==");
/* 37:56 */       return buffer.toString();
/* 38:   */     }
/* 39:58 */     int block = ((bytes[(i++)] & 0xFF) << 8 | bytes[i] & 0xFF) << 2;
/* 40:59 */     buffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(block >>> 12));
/* 41:60 */     buffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(block >>> 6 & 0x3F));
/* 42:61 */     buffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(block & 0x3F));
/* 43:62 */     buffer.append("=");
/* 44:63 */     return buffer.toString();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static byte[] decode(String string)
/* 48:   */   {
/* 49:73 */     int length = string.length();
/* 50:74 */     if (length == 0) {
/* 51:74 */       return new byte[0];
/* 52:   */     }
/* 53:75 */     int pad = string.charAt(length - 1) == '=' ? 1 : string.charAt(length - 2) == '=' ? 2 : 0;
/* 54:   */     
/* 55:77 */     int size = length * 3 / 4 - pad;
/* 56:78 */     byte[] buffer = new byte[size];
/* 57:   */     
/* 58:80 */     int i = 0;
/* 59:81 */     int index = 0;
/* 60:82 */     while (i < length)
/* 61:   */     {
/* 62:83 */       int block = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(string.charAt(i++)) & 0xFF) << 18 | ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(string.charAt(i++)) & 0xFF) << 12 | ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(string.charAt(i++)) & 0xFF) << 6 | "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(string.charAt(i++)) & 0xFF;
/* 63:   */       
/* 64:   */ 
/* 65:   */ 
/* 66:87 */       buffer[(index++)] = ((byte)(block >>> 16));
/* 67:88 */       if (index < size) {
/* 68:88 */         buffer[(index++)] = ((byte)(block >>> 8 & 0xFF));
/* 69:   */       }
/* 70:89 */       if (index < size) {
/* 71:89 */         buffer[(index++)] = ((byte)(block & 0xFF));
/* 72:   */       }
/* 73:   */     }
/* 74:91 */     return buffer;
/* 75:   */   }
/* 76:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.util.Base64
 * JD-Core Version:    0.7.0.1
 */