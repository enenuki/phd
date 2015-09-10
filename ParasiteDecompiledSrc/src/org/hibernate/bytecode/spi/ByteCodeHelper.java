/*   1:    */ package org.hibernate.bytecode.spi;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.util.zip.ZipInputStream;
/*  10:    */ 
/*  11:    */ public class ByteCodeHelper
/*  12:    */ {
/*  13:    */   public static byte[] readByteCode(InputStream inputStream)
/*  14:    */     throws IOException
/*  15:    */   {
/*  16: 55 */     if (inputStream == null) {
/*  17: 56 */       throw new IOException("null input stream");
/*  18:    */     }
/*  19: 59 */     byte[] buffer = new byte[409600];
/*  20: 60 */     classBytes = new byte[0];
/*  21:    */     try
/*  22:    */     {
/*  23: 63 */       int r = inputStream.read(buffer);
/*  24: 64 */       while (r >= buffer.length)
/*  25:    */       {
/*  26: 65 */         byte[] temp = new byte[classBytes.length + buffer.length];
/*  27:    */         
/*  28: 67 */         System.arraycopy(classBytes, 0, temp, 0, classBytes.length);
/*  29:    */         
/*  30: 69 */         System.arraycopy(buffer, 0, temp, classBytes.length, buffer.length);
/*  31: 70 */         classBytes = temp;
/*  32:    */         
/*  33: 72 */         r = inputStream.read(buffer);
/*  34:    */       }
/*  35:    */       byte[] temp;
/*  36: 74 */       if (r != -1)
/*  37:    */       {
/*  38: 75 */         temp = new byte[classBytes.length + r];
/*  39:    */         
/*  40: 77 */         System.arraycopy(classBytes, 0, temp, 0, classBytes.length);
/*  41:    */         
/*  42: 79 */         System.arraycopy(buffer, 0, temp, classBytes.length, r);
/*  43:    */       }
/*  44: 80 */       return temp;
/*  45:    */     }
/*  46:    */     finally
/*  47:    */     {
/*  48:    */       try
/*  49:    */       {
/*  50: 85 */         inputStream.close();
/*  51:    */       }
/*  52:    */       catch (IOException ignore) {}
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static byte[] readByteCode(File file)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:105 */     return readByteCode(new FileInputStream(file));
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static byte[] readByteCode(ZipInputStream zip)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65:118 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/*  66:119 */     InputStream in = new BufferedInputStream(zip);
/*  67:    */     int b;
/*  68:121 */     while ((b = in.read()) != -1) {
/*  69:122 */       bout.write(b);
/*  70:    */     }
/*  71:124 */     return bout.toByteArray();
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.spi.ByteCodeHelper
 * JD-Core Version:    0.7.0.1
 */