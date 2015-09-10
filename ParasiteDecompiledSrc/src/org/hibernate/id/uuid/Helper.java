/*   1:    */ package org.hibernate.id.uuid;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.UnknownHostException;
/*   6:    */ import org.hibernate.internal.util.BytesHelper;
/*   7:    */ 
/*   8:    */ public class Helper
/*   9:    */ {
/*  10:    */   private static final byte[] ADDRESS_BYTES;
/*  11:    */   private static final int ADDRESS_INT;
/*  12:    */   private static final String ADDRESS_HEX_STRING;
/*  13:    */   
/*  14:    */   public static byte[] getAddressBytes()
/*  15:    */   {
/*  16: 58 */     return ADDRESS_BYTES;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static int getAddressInt()
/*  20:    */   {
/*  21: 62 */     return ADDRESS_INT;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static String getAddressHexString()
/*  25:    */   {
/*  26: 66 */     return ADDRESS_HEX_STRING;
/*  27:    */   }
/*  28:    */   
/*  29:    */   static
/*  30:    */   {
/*  31:    */     byte[] address;
/*  32:    */     try
/*  33:    */     {
/*  34: 47 */       address = InetAddress.getLocalHost().getAddress();
/*  35:    */     }
/*  36:    */     catch (Exception e)
/*  37:    */     {
/*  38: 50 */       address = new byte[4];
/*  39:    */     }
/*  40: 52 */     ADDRESS_BYTES = address;
/*  41: 53 */     ADDRESS_INT = BytesHelper.toInt(ADDRESS_BYTES);
/*  42: 54 */     ADDRESS_HEX_STRING = format(ADDRESS_INT);
/*  43:    */     
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65: 77 */     JVM_IDENTIFIER_INT = (int)(System.currentTimeMillis() >>> 8);
/*  66:    */   }
/*  67:    */   
/*  68: 78 */   private static final byte[] JVM_IDENTIFIER_BYTES = BytesHelper.fromInt(JVM_IDENTIFIER_INT);
/*  69:    */   private static final int JVM_IDENTIFIER_INT;
/*  70: 79 */   private static final String JVM_IDENTIFIER_HEX_STRING = format(JVM_IDENTIFIER_INT);
/*  71:    */   
/*  72:    */   public static byte[] getJvmIdentifierBytes()
/*  73:    */   {
/*  74: 83 */     return JVM_IDENTIFIER_BYTES;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static int getJvmIdentifierInt()
/*  78:    */   {
/*  79: 87 */     return JVM_IDENTIFIER_INT;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static String getJvmIdentifierHexString()
/*  83:    */   {
/*  84: 91 */     return JVM_IDENTIFIER_HEX_STRING;
/*  85:    */   }
/*  86:    */   
/*  87: 97 */   private static short counter = 0;
/*  88:    */   
/*  89:    */   public static short getCountShort()
/*  90:    */   {
/*  91:104 */     synchronized (Helper.class)
/*  92:    */     {
/*  93:105 */       if (counter < 0) {
/*  94:106 */         counter = 0;
/*  95:    */       }
/*  96:108 */       return counter++;
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static byte[] getCountBytes()
/* 101:    */   {
/* 102:113 */     return BytesHelper.fromShort(getCountShort());
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static String format(int value)
/* 106:    */   {
/* 107:120 */     String formatted = Integer.toHexString(value);
/* 108:121 */     StringBuffer buf = new StringBuffer("00000000".intern());
/* 109:122 */     buf.replace(8 - formatted.length(), 8, formatted);
/* 110:123 */     return buf.toString();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static String format(short value)
/* 114:    */   {
/* 115:127 */     String formatted = Integer.toHexString(value);
/* 116:128 */     StringBuffer buf = new StringBuffer("0000");
/* 117:129 */     buf.replace(4 - formatted.length(), 4, formatted);
/* 118:130 */     return buf.toString();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static void main(String[] args)
/* 122:    */     throws UnknownHostException
/* 123:    */   {
/* 124:135 */     byte[] addressBytes = InetAddress.getLocalHost().getAddress();
/* 125:136 */     System.out.println("Raw ip address bytes : " + addressBytes.toString());
/* 126:    */     
/* 127:138 */     int addressInt = BytesHelper.toInt(addressBytes);
/* 128:139 */     System.out.println("ip address int : " + addressInt);
/* 129:    */     
/* 130:141 */     String formatted = Integer.toHexString(addressInt);
/* 131:142 */     StringBuffer buf = new StringBuffer("00000000");
/* 132:143 */     buf.replace(8 - formatted.length(), 8, formatted);
/* 133:144 */     String addressHex = buf.toString();
/* 134:145 */     System.out.println("ip address hex : " + addressHex);
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.uuid.Helper
 * JD-Core Version:    0.7.0.1
 */