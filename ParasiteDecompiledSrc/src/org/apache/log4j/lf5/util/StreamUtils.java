/*   1:    */ package org.apache.log4j.lf5.util;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ 
/*   8:    */ public abstract class StreamUtils
/*   9:    */ {
/*  10:    */   public static final int DEFAULT_BUFFER_SIZE = 2048;
/*  11:    */   
/*  12:    */   public static void copy(InputStream input, OutputStream output)
/*  13:    */     throws IOException
/*  14:    */   {
/*  15: 66 */     copy(input, output, 2048);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static void copy(InputStream input, OutputStream output, int bufferSize)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 78 */     byte[] buf = new byte[bufferSize];
/*  22: 79 */     int bytesRead = input.read(buf);
/*  23: 80 */     while (bytesRead != -1)
/*  24:    */     {
/*  25: 81 */       output.write(buf, 0, bytesRead);
/*  26: 82 */       bytesRead = input.read(buf);
/*  27:    */     }
/*  28: 84 */     output.flush();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static void copyThenClose(InputStream input, OutputStream output)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34: 94 */     copy(input, output);
/*  35: 95 */     input.close();
/*  36: 96 */     output.close();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static byte[] getBytes(InputStream input)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42:106 */     ByteArrayOutputStream result = new ByteArrayOutputStream();
/*  43:107 */     copy(input, result);
/*  44:108 */     result.close();
/*  45:109 */     return result.toByteArray();
/*  46:    */   }
/*  47:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.util.StreamUtils
 * JD-Core Version:    0.7.0.1
 */