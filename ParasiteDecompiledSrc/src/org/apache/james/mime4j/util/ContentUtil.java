/*   1:    */ package org.apache.james.mime4j.util;
/*   2:    */ 
/*   3:    */ import java.nio.ByteBuffer;
/*   4:    */ import java.nio.CharBuffer;
/*   5:    */ import java.nio.charset.Charset;
/*   6:    */ 
/*   7:    */ public class ContentUtil
/*   8:    */ {
/*   9:    */   public static ByteSequence encode(String string)
/*  10:    */   {
/*  11: 43 */     return encode(CharsetUtil.US_ASCII, string);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public static ByteSequence encode(Charset charset, String string)
/*  15:    */   {
/*  16: 57 */     ByteBuffer encoded = charset.encode(CharBuffer.wrap(string));
/*  17: 58 */     ByteArrayBuffer bab = new ByteArrayBuffer(encoded.remaining());
/*  18: 59 */     bab.append(encoded.array(), encoded.position(), encoded.remaining());
/*  19: 60 */     return bab;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static String decode(ByteSequence byteSequence)
/*  23:    */   {
/*  24: 72 */     return decode(CharsetUtil.US_ASCII, byteSequence, 0, byteSequence.length());
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static String decode(Charset charset, ByteSequence byteSequence)
/*  28:    */   {
/*  29: 87 */     return decode(charset, byteSequence, 0, byteSequence.length());
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static String decode(ByteSequence byteSequence, int offset, int length)
/*  33:    */   {
/*  34:104 */     return decode(CharsetUtil.US_ASCII, byteSequence, offset, length);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static String decode(Charset charset, ByteSequence byteSequence, int offset, int length)
/*  38:    */   {
/*  39:123 */     if ((byteSequence instanceof ByteArrayBuffer))
/*  40:    */     {
/*  41:124 */       ByteArrayBuffer bab = (ByteArrayBuffer)byteSequence;
/*  42:125 */       return decode(charset, bab.buffer(), offset, length);
/*  43:    */     }
/*  44:127 */     byte[] bytes = byteSequence.toByteArray();
/*  45:128 */     return decode(charset, bytes, offset, length);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private static String decode(Charset charset, byte[] buffer, int offset, int length)
/*  49:    */   {
/*  50:134 */     return charset.decode(ByteBuffer.wrap(buffer, offset, length)).toString();
/*  51:    */   }
/*  52:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.util.ContentUtil
 * JD-Core Version:    0.7.0.1
 */