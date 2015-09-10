/*  1:   */ package org.jboss.jandex;
/*  2:   */ 
/*  3:   */ import java.io.DataInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ 
/*  7:   */ class PackedDataInputStream
/*  8:   */   extends DataInputStream
/*  9:   */ {
/* 10:   */   static final int MAX_1BYTE = 127;
/* 11:   */   
/* 12:   */   public PackedDataInputStream(InputStream in)
/* 13:   */   {
/* 14:44 */     super(in);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int readPackedU32()
/* 18:   */     throws IOException
/* 19:   */   {
/* 20:58 */     int i = 0;
/* 21:   */     byte b;
/* 22:   */     do
/* 23:   */     {
/* 24:61 */       b = readByte();
/* 25:62 */       i = i << 7 | b & 0x7F;
/* 26:63 */     } while ((b & 0x80) == 128);
/* 27:65 */     return i;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.PackedDataInputStream
 * JD-Core Version:    0.7.0.1
 */