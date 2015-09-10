/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ 
/*   5:    */ public class CharEncoding
/*   6:    */ {
/*   7:    */   public static final String ISO_8859_1 = "ISO-8859-1";
/*   8:    */   public static final String US_ASCII = "US-ASCII";
/*   9:    */   public static final String UTF_16 = "UTF-16";
/*  10:    */   public static final String UTF_16BE = "UTF-16BE";
/*  11:    */   public static final String UTF_16LE = "UTF-16LE";
/*  12:    */   public static final String UTF_8 = "UTF-8";
/*  13:    */   
/*  14:    */   public static boolean isSupported(String name)
/*  15:    */   {
/*  16:142 */     if (name == null) {
/*  17:143 */       return false;
/*  18:    */     }
/*  19:    */     try
/*  20:    */     {
/*  21:146 */       new String(ArrayUtils.EMPTY_BYTE_ARRAY, name);
/*  22:    */     }
/*  23:    */     catch (UnsupportedEncodingException e)
/*  24:    */     {
/*  25:148 */       return false;
/*  26:    */     }
/*  27:150 */     return true;
/*  28:    */   }
/*  29:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.CharEncoding
 * JD-Core Version:    0.7.0.1
 */