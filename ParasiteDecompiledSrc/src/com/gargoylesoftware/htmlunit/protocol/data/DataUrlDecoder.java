/*   1:    */ package com.gargoylesoftware.htmlunit.protocol.data;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.net.URL;
/*   5:    */ import org.apache.commons.codec.DecoderException;
/*   6:    */ import org.apache.commons.codec.binary.Base64;
/*   7:    */ import org.apache.commons.codec.net.URLCodec;
/*   8:    */ import org.apache.commons.lang.StringUtils;
/*   9:    */ 
/*  10:    */ public class DataUrlDecoder
/*  11:    */ {
/*  12:    */   private static final String DEFAULT_CHARSET = "US-ASCII";
/*  13:    */   private static final String DEFAULT_MEDIA_TYPE = "text/plain";
/*  14:    */   private final String mediaType_;
/*  15:    */   private final String charset_;
/*  16:    */   private byte[] content_;
/*  17:    */   
/*  18:    */   protected DataUrlDecoder(byte[] data, String mediaType, String charset)
/*  19:    */   {
/*  20: 45 */     this.content_ = data;
/*  21: 46 */     this.mediaType_ = mediaType;
/*  22: 47 */     this.charset_ = charset;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static DataUrlDecoder decode(URL url)
/*  26:    */     throws UnsupportedEncodingException, DecoderException
/*  27:    */   {
/*  28: 59 */     return decodeDataURL(url.toExternalForm());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static DataUrlDecoder decodeDataURL(String url)
/*  32:    */     throws UnsupportedEncodingException, DecoderException
/*  33:    */   {
/*  34: 72 */     if (!url.startsWith("data")) {
/*  35: 73 */       throw new IllegalArgumentException("Not a data url: " + url);
/*  36:    */     }
/*  37: 75 */     int comma = url.indexOf(',');
/*  38: 76 */     String beforeData = url.substring("data:".length(), comma);
/*  39: 77 */     String mediaType = extractMediaType(beforeData);
/*  40: 78 */     String charset = extractCharset(beforeData);
/*  41:    */     
/*  42: 80 */     boolean base64 = beforeData.endsWith(";base64");
/*  43: 81 */     byte[] data = url.substring(comma + 1).getBytes(charset);
/*  44: 82 */     if (base64) {
/*  45: 83 */       data = Base64.decodeBase64(URLCodec.decodeUrl(data));
/*  46:    */     } else {
/*  47: 86 */       data = URLCodec.decodeUrl(data);
/*  48:    */     }
/*  49: 89 */     return new DataUrlDecoder(data, mediaType, charset);
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static String extractCharset(String beforeData)
/*  53:    */   {
/*  54: 94 */     return "US-ASCII";
/*  55:    */   }
/*  56:    */   
/*  57:    */   private static String extractMediaType(String beforeData)
/*  58:    */   {
/*  59: 98 */     if (beforeData.contains("/"))
/*  60:    */     {
/*  61: 99 */       if (beforeData.contains(";")) {
/*  62:100 */         return StringUtils.substringBefore(beforeData, ";");
/*  63:    */       }
/*  64:102 */       return beforeData;
/*  65:    */     }
/*  66:104 */     return "text/plain";
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getMediaType()
/*  70:    */   {
/*  71:112 */     return this.mediaType_;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getCharset()
/*  75:    */   {
/*  76:120 */     return this.charset_;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public byte[] getBytes()
/*  80:    */   {
/*  81:128 */     return this.content_;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getDataAsString()
/*  85:    */     throws UnsupportedEncodingException
/*  86:    */   {
/*  87:138 */     return new String(this.content_, this.charset_);
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.protocol.data.DataUrlDecoder
 * JD-Core Version:    0.7.0.1
 */