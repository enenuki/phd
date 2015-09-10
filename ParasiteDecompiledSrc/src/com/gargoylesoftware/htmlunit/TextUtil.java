/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.io.OutputStreamWriter;
/*   8:    */ import java.io.UnsupportedEncodingException;
/*   9:    */ 
/*  10:    */ public final class TextUtil
/*  11:    */ {
/*  12:    */   public static final String DEFAULT_CHARSET = "ISO-8859-1";
/*  13:    */   
/*  14:    */   public static InputStream toInputStream(String content)
/*  15:    */   {
/*  16:    */     try
/*  17:    */     {
/*  18: 49 */       return toInputStream(content, "ISO-8859-1");
/*  19:    */     }
/*  20:    */     catch (UnsupportedEncodingException e)
/*  21:    */     {
/*  22: 52 */       throw new IllegalStateException("ISO-8859-1 is an unsupported encoding!  You may have a corrupted installation of java.");
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static InputStream toInputStream(String content, String encoding)
/*  27:    */     throws UnsupportedEncodingException
/*  28:    */   {
/*  29:    */     try
/*  30:    */     {
/*  31: 71 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(content.length() * 2);
/*  32: 72 */       OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream, encoding);
/*  33: 73 */       writer.write(content);
/*  34: 74 */       writer.flush();
/*  35:    */       
/*  36: 76 */       byte[] byteArray = byteArrayOutputStream.toByteArray();
/*  37: 77 */       return new ByteArrayInputStream(byteArray);
/*  38:    */     }
/*  39:    */     catch (UnsupportedEncodingException e)
/*  40:    */     {
/*  41: 80 */       throw e;
/*  42:    */     }
/*  43:    */     catch (IOException e)
/*  44:    */     {
/*  45: 85 */       throw new IllegalStateException("Exception when converting a string to an input stream: '" + e + "'", e);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static byte[] stringToByteArray(String content)
/*  50:    */   {
/*  51: 96 */     return content != null ? stringToByteArray(content, "ISO-8859-1") : null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static byte[] stringToByteArray(String content, String charset)
/*  55:    */   {
/*  56:    */     byte[] contentBytes;
/*  57:    */     try
/*  58:    */     {
/*  59:109 */       contentBytes = content.getBytes(charset);
/*  60:    */     }
/*  61:    */     catch (UnsupportedEncodingException e)
/*  62:    */     {
/*  63:112 */       contentBytes = new byte[0];
/*  64:    */     }
/*  65:114 */     return contentBytes;
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.TextUtil
 * JD-Core Version:    0.7.0.1
 */