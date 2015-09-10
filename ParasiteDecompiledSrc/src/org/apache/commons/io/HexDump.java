/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ 
/*   6:    */ public class HexDump
/*   7:    */ {
/*   8:    */   public static void dump(byte[] data, long offset, OutputStream stream, int index)
/*   9:    */     throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException
/*  10:    */   {
/*  11: 78 */     if ((index < 0) || (index >= data.length)) {
/*  12: 79 */       throw new ArrayIndexOutOfBoundsException("illegal index: " + index + " into array of length " + data.length);
/*  13:    */     }
/*  14: 83 */     if (stream == null) {
/*  15: 84 */       throw new IllegalArgumentException("cannot write to nullstream");
/*  16:    */     }
/*  17: 86 */     long display_offset = offset + index;
/*  18: 87 */     StringBuilder buffer = new StringBuilder(74);
/*  19: 89 */     for (int j = index; j < data.length; j += 16)
/*  20:    */     {
/*  21: 90 */       int chars_read = data.length - j;
/*  22: 92 */       if (chars_read > 16) {
/*  23: 93 */         chars_read = 16;
/*  24:    */       }
/*  25: 95 */       dump(buffer, display_offset).append(' ');
/*  26: 96 */       for (int k = 0; k < 16; k++)
/*  27:    */       {
/*  28: 97 */         if (k < chars_read) {
/*  29: 98 */           dump(buffer, data[(k + j)]);
/*  30:    */         } else {
/*  31:100 */           buffer.append("  ");
/*  32:    */         }
/*  33:102 */         buffer.append(' ');
/*  34:    */       }
/*  35:104 */       for (int k = 0; k < chars_read; k++) {
/*  36:105 */         if ((data[(k + j)] >= 32) && (data[(k + j)] < 127)) {
/*  37:106 */           buffer.append((char)data[(k + j)]);
/*  38:    */         } else {
/*  39:108 */           buffer.append('.');
/*  40:    */         }
/*  41:    */       }
/*  42:111 */       buffer.append(EOL);
/*  43:112 */       stream.write(buffer.toString().getBytes());
/*  44:113 */       stream.flush();
/*  45:114 */       buffer.setLength(0);
/*  46:115 */       display_offset += chars_read;
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:122 */   public static final String EOL = System.getProperty("line.separator");
/*  51:124 */   private static final char[] _hexcodes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*  52:129 */   private static final int[] _shifts = { 28, 24, 20, 16, 12, 8, 4, 0 };
/*  53:    */   
/*  54:    */   private static StringBuilder dump(StringBuilder _lbuffer, long value)
/*  55:    */   {
/*  56:142 */     for (int j = 0; j < 8; j++) {
/*  57:143 */       _lbuffer.append(_hexcodes[((int)(value >> _shifts[j]) & 0xF)]);
/*  58:    */     }
/*  59:146 */     return _lbuffer;
/*  60:    */   }
/*  61:    */   
/*  62:    */   private static StringBuilder dump(StringBuilder _cbuffer, byte value)
/*  63:    */   {
/*  64:157 */     for (int j = 0; j < 2; j++) {
/*  65:158 */       _cbuffer.append(_hexcodes[(value >> _shifts[(j + 6)] & 0xF)]);
/*  66:    */     }
/*  67:160 */     return _cbuffer;
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.HexDump
 * JD-Core Version:    0.7.0.1
 */