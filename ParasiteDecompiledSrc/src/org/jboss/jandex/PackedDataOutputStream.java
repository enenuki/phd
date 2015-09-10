/*  1:   */ package org.jboss.jandex;
/*  2:   */ 
/*  3:   */ import java.io.DataOutputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ 
/*  7:   */ class PackedDataOutputStream
/*  8:   */   extends DataOutputStream
/*  9:   */ {
/* 10:   */   static final int MAX_1BYTE = 127;
/* 11:   */   static final int MAX_2BYTE = 16383;
/* 12:   */   static final int MAX_3BYTE = 2097151;
/* 13:   */   static final int MAX_4BYTE = 268435455;
/* 14:   */   
/* 15:   */   public PackedDataOutputStream(OutputStream out)
/* 16:   */   {
/* 17:47 */     super(out);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void writePackedU32(int i)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:65 */     if ((i & 0xFFFFFF80) == 0)
/* 24:   */     {
/* 25:66 */       writeByte(i);
/* 26:   */     }
/* 27:67 */     else if ((i & 0xFFFFC000) == 0)
/* 28:   */     {
/* 29:68 */       writeByte(i >>> 7 & 0x7F | 0x80);
/* 30:69 */       writeByte(i & 0x7F);
/* 31:   */     }
/* 32:70 */     else if ((i & 0xFFE00000) == 0)
/* 33:   */     {
/* 34:71 */       writeByte(i >>> 14 & 0x7F | 0x80);
/* 35:72 */       writeByte(i >>> 7 & 0x7F | 0x80);
/* 36:73 */       writeByte(i & 0x7F);
/* 37:   */     }
/* 38:74 */     else if ((i & 0xF0000000) == 0)
/* 39:   */     {
/* 40:75 */       writeByte(i >>> 21 & 0x7F | 0x80);
/* 41:76 */       writeByte(i >>> 14 & 0x7F | 0x80);
/* 42:77 */       writeByte(i >>> 7 & 0x7F | 0x80);
/* 43:78 */       writeByte(i & 0x7F);
/* 44:   */     }
/* 45:   */     else
/* 46:   */     {
/* 47:80 */       writeByte(i >>> 28 & 0x7F | 0x80);
/* 48:81 */       writeByte(i >>> 21 & 0x7F | 0x80);
/* 49:82 */       writeByte(i >>> 14 & 0x7F | 0x80);
/* 50:83 */       writeByte(i >>> 7 & 0x7F | 0x80);
/* 51:84 */       writeByte(i & 0x7F);
/* 52:   */     }
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.PackedDataOutputStream
 * JD-Core Version:    0.7.0.1
 */