/*  1:   */ package javassist.bytecode;
/*  2:   */ 
/*  3:   */ public class ByteArray
/*  4:   */ {
/*  5:   */   public static int readU16bit(byte[] code, int index)
/*  6:   */   {
/*  7:26 */     return (code[index] & 0xFF) << 8 | code[(index + 1)] & 0xFF;
/*  8:   */   }
/*  9:   */   
/* 10:   */   public static int readS16bit(byte[] code, int index)
/* 11:   */   {
/* 12:33 */     return code[index] << 8 | code[(index + 1)] & 0xFF;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static void write16bit(int value, byte[] code, int index)
/* 16:   */   {
/* 17:40 */     code[index] = ((byte)(value >>> 8));
/* 18:41 */     code[(index + 1)] = ((byte)value);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static int read32bit(byte[] code, int index)
/* 22:   */   {
/* 23:48 */     return code[index] << 24 | (code[(index + 1)] & 0xFF) << 16 | (code[(index + 2)] & 0xFF) << 8 | code[(index + 3)] & 0xFF;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static void write32bit(int value, byte[] code, int index)
/* 27:   */   {
/* 28:56 */     code[index] = ((byte)(value >>> 24));
/* 29:57 */     code[(index + 1)] = ((byte)(value >>> 16));
/* 30:58 */     code[(index + 2)] = ((byte)(value >>> 8));
/* 31:59 */     code[(index + 3)] = ((byte)value);
/* 32:   */   }
/* 33:   */   
/* 34:   */   static void copy32bit(byte[] src, int isrc, byte[] dest, int idest)
/* 35:   */   {
/* 36:71 */     dest[idest] = src[isrc];
/* 37:72 */     dest[(idest + 1)] = src[(isrc + 1)];
/* 38:73 */     dest[(idest + 2)] = src[(isrc + 2)];
/* 39:74 */     dest[(idest + 3)] = src[(isrc + 3)];
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ByteArray
 * JD-Core Version:    0.7.0.1
 */