/*   1:    */ package org.apache.james.mime4j.codec;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ 
/*   7:    */ public class CodecUtil
/*   8:    */ {
/*   9:    */   static final int DEFAULT_ENCODING_BUFFER_SIZE = 1024;
/*  10:    */   
/*  11:    */   public static void copy(InputStream in, OutputStream out)
/*  12:    */     throws IOException
/*  13:    */   {
/*  14: 40 */     byte[] buffer = new byte[1024];
/*  15:    */     int inputLength;
/*  16: 42 */     while (-1 != (inputLength = in.read(buffer))) {
/*  17: 43 */       out.write(buffer, 0, inputLength);
/*  18:    */     }
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static void encodeQuotedPrintableBinary(InputStream in, OutputStream out)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 57 */     QuotedPrintableEncoder encoder = new QuotedPrintableEncoder(1024, true);
/*  25: 58 */     encoder.encode(in, out);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static void encodeQuotedPrintable(InputStream in, OutputStream out)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 70 */     QuotedPrintableEncoder encoder = new QuotedPrintableEncoder(1024, false);
/*  32: 71 */     encoder.encode(in, out);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static void encodeBase64(InputStream in, OutputStream out)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 82 */     Base64OutputStream b64Out = new Base64OutputStream(out);
/*  39: 83 */     copy(in, b64Out);
/*  40: 84 */     b64Out.close();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static OutputStream wrapQuotedPrintable(OutputStream out, boolean binary)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46: 94 */     return new QuotedPrintableOutputStream(out, binary);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static OutputStream wrapBase64(OutputStream out)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52:104 */     return new Base64OutputStream(out);
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.codec.CodecUtil
 * JD-Core Version:    0.7.0.1
 */