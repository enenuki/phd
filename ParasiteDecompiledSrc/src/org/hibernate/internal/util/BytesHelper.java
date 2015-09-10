/*   1:    */ package org.hibernate.internal.util;
/*   2:    */ 
/*   3:    */ public final class BytesHelper
/*   4:    */ {
/*   5:    */   public static int toInt(byte[] bytes)
/*   6:    */   {
/*   7: 42 */     int result = 0;
/*   8: 43 */     for (int i = 0; i < 4; i++) {
/*   9: 44 */       result = (result << 8) - -128 + bytes[i];
/*  10:    */     }
/*  11: 46 */     return result;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public static byte[] fromShort(int shortValue)
/*  15:    */   {
/*  16: 57 */     byte[] bytes = new byte[2];
/*  17: 58 */     bytes[0] = ((byte)(shortValue >> 8));
/*  18: 59 */     bytes[1] = ((byte)(shortValue << 8 >> 8));
/*  19: 60 */     return bytes;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static byte[] fromInt(int intValue)
/*  23:    */   {
/*  24: 71 */     byte[] bytes = new byte[4];
/*  25: 72 */     bytes[0] = ((byte)(intValue >> 24));
/*  26: 73 */     bytes[1] = ((byte)(intValue << 8 >> 24));
/*  27: 74 */     bytes[2] = ((byte)(intValue << 16 >> 24));
/*  28: 75 */     bytes[3] = ((byte)(intValue << 24 >> 24));
/*  29: 76 */     return bytes;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static byte[] fromLong(long longValue)
/*  33:    */   {
/*  34: 87 */     byte[] bytes = new byte[8];
/*  35: 88 */     bytes[0] = ((byte)(int)(longValue >> 56));
/*  36: 89 */     bytes[1] = ((byte)(int)(longValue << 8 >> 56));
/*  37: 90 */     bytes[2] = ((byte)(int)(longValue << 16 >> 56));
/*  38: 91 */     bytes[3] = ((byte)(int)(longValue << 24 >> 56));
/*  39: 92 */     bytes[4] = ((byte)(int)(longValue << 32 >> 56));
/*  40: 93 */     bytes[5] = ((byte)(int)(longValue << 40 >> 56));
/*  41: 94 */     bytes[6] = ((byte)(int)(longValue << 48 >> 56));
/*  42: 95 */     bytes[7] = ((byte)(int)(longValue << 56 >> 56));
/*  43: 96 */     return bytes;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static long asLong(byte[] bytes)
/*  47:    */   {
/*  48:107 */     if (bytes == null) {
/*  49:108 */       return 0L;
/*  50:    */     }
/*  51:110 */     if (bytes.length != 8) {
/*  52:111 */       throw new IllegalArgumentException("Expecting 8 byte values to construct a long");
/*  53:    */     }
/*  54:113 */     long value = 0L;
/*  55:114 */     for (int i = 0; i < 8; i++) {
/*  56:115 */       value = value << 8 | bytes[i] & 0xFF;
/*  57:    */     }
/*  58:117 */     return value;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static String toBinaryString(byte value)
/*  62:    */   {
/*  63:121 */     String formatted = Integer.toBinaryString(value);
/*  64:122 */     if (formatted.length() > 8) {
/*  65:123 */       formatted = formatted.substring(formatted.length() - 8);
/*  66:    */     }
/*  67:125 */     StringBuffer buf = new StringBuffer("00000000");
/*  68:126 */     buf.replace(8 - formatted.length(), 8, formatted);
/*  69:127 */     return buf.toString();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static String toBinaryString(int value)
/*  73:    */   {
/*  74:131 */     String formatted = Long.toBinaryString(value);
/*  75:132 */     StringBuffer buf = new StringBuffer(StringHelper.repeat('0', 32));
/*  76:133 */     buf.replace(64 - formatted.length(), 64, formatted);
/*  77:134 */     return buf.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static String toBinaryString(long value)
/*  81:    */   {
/*  82:138 */     String formatted = Long.toBinaryString(value);
/*  83:139 */     StringBuffer buf = new StringBuffer(StringHelper.repeat('0', 64));
/*  84:140 */     buf.replace(64 - formatted.length(), 64, formatted);
/*  85:141 */     return buf.toString();
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.BytesHelper
 * JD-Core Version:    0.7.0.1
 */