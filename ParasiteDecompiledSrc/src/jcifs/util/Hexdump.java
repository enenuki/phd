/*   1:    */ package jcifs.util;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ public class Hexdump
/*   6:    */ {
/*   7: 30 */   private static final String NL = System.getProperty("line.separator");
/*   8: 31 */   private static final int NL_LENGTH = NL.length();
/*   9: 33 */   private static final char[] SPACE_CHARS = { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };
/*  10: 39 */   public static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*  11:    */   
/*  12:    */   public static void hexdump(PrintStream ps, byte[] src, int srcIndex, int length)
/*  13:    */   {
/*  14: 58 */     if (length == 0) {
/*  15: 59 */       return;
/*  16:    */     }
/*  17: 62 */     int s = length % 16;
/*  18: 63 */     int r = s == 0 ? length / 16 : length / 16 + 1;
/*  19: 64 */     char[] c = new char[r * (74 + NL_LENGTH)];
/*  20: 65 */     char[] d = new char[16];
/*  21:    */     
/*  22: 67 */     int si = 0;
/*  23: 68 */     int ci = 0;
/*  24:    */     do
/*  25:    */     {
/*  26: 71 */       toHexChars(si, c, ci, 5);
/*  27: 72 */       ci += 5;
/*  28: 73 */       c[(ci++)] = ':';
/*  29:    */       do
/*  30:    */       {
/*  31: 75 */         if (si == length)
/*  32:    */         {
/*  33: 76 */           int n = 16 - s;
/*  34: 77 */           System.arraycopy(SPACE_CHARS, 0, c, ci, n * 3);
/*  35: 78 */           ci += n * 3;
/*  36: 79 */           System.arraycopy(SPACE_CHARS, 0, d, s, n);
/*  37: 80 */           break;
/*  38:    */         }
/*  39: 82 */         c[(ci++)] = ' ';
/*  40: 83 */         int i = src[(srcIndex + si)] & 0xFF;
/*  41: 84 */         toHexChars(i, c, ci, 2);
/*  42: 85 */         ci += 2;
/*  43: 86 */         if ((i < 0) || (Character.isISOControl((char)i))) {
/*  44: 87 */           d[(si % 16)] = '.';
/*  45:    */         } else {
/*  46: 89 */           d[(si % 16)] = ((char)i);
/*  47:    */         }
/*  48: 91 */         si++;
/*  49: 91 */       } while (si % 16 != 0);
/*  50: 92 */       c[(ci++)] = ' ';
/*  51: 93 */       c[(ci++)] = ' ';
/*  52: 94 */       c[(ci++)] = '|';
/*  53: 95 */       System.arraycopy(d, 0, c, ci, 16);
/*  54: 96 */       ci += 16;
/*  55: 97 */       c[(ci++)] = '|';
/*  56: 98 */       NL.getChars(0, NL_LENGTH, c, ci);
/*  57: 99 */       ci += NL_LENGTH;
/*  58:100 */     } while (si < length);
/*  59:102 */     ps.println(c);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static String toHexString(int val, int size)
/*  63:    */   {
/*  64:111 */     char[] c = new char[size];
/*  65:112 */     toHexChars(val, c, 0, size);
/*  66:113 */     return new String(c);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static String toHexString(long val, int size)
/*  70:    */   {
/*  71:116 */     char[] c = new char[size];
/*  72:117 */     toHexChars(val, c, 0, size);
/*  73:118 */     return new String(c);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static String toHexString(byte[] src, int srcIndex, int size)
/*  77:    */   {
/*  78:121 */     char[] c = new char[size];
/*  79:122 */     size = size % 2 == 0 ? size / 2 : size / 2 + 1;
/*  80:123 */     int i = 0;
/*  81:123 */     for (int j = 0; i < size; i++)
/*  82:    */     {
/*  83:124 */       c[(j++)] = HEX_DIGITS[(src[i] >> 4 & 0xF)];
/*  84:125 */       if (j == c.length) {
/*  85:    */         break;
/*  86:    */       }
/*  87:128 */       c[(j++)] = HEX_DIGITS[(src[i] & 0xF)];
/*  88:    */     }
/*  89:130 */     return new String(c);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static void toHexChars(int val, char[] dst, int dstIndex, int size)
/*  93:    */   {
/*  94:139 */     while (size > 0)
/*  95:    */     {
/*  96:140 */       int i = dstIndex + size - 1;
/*  97:141 */       if (i < dst.length) {
/*  98:142 */         dst[i] = HEX_DIGITS[(val & 0xF)];
/*  99:    */       }
/* 100:144 */       if (val != 0) {
/* 101:145 */         val >>>= 4;
/* 102:    */       }
/* 103:147 */       size--;
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static void toHexChars(long val, char[] dst, int dstIndex, int size)
/* 108:    */   {
/* 109:151 */     while (size > 0)
/* 110:    */     {
/* 111:152 */       dst[(dstIndex + size - 1)] = HEX_DIGITS[((int)(val & 0xF))];
/* 112:153 */       if (val != 0L) {
/* 113:154 */         val >>>= 4;
/* 114:    */       }
/* 115:156 */       size--;
/* 116:    */     }
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.util.Hexdump
 * JD-Core Version:    0.7.0.1
 */