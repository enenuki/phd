/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ 
/*   5:    */ public class KeyDataPair
/*   6:    */   extends NameValuePair
/*   7:    */ {
/*   8:    */   private final File fileObject_;
/*   9:    */   private final String contentType_;
/*  10:    */   private final String charset_;
/*  11:    */   private byte[] data_;
/*  12:    */   
/*  13:    */   public KeyDataPair(String key, File file, String contentType, String charset)
/*  14:    */   {
/*  15: 45 */     super(key, file.getName());
/*  16: 47 */     if (file.exists()) {
/*  17: 48 */       this.fileObject_ = file;
/*  18:    */     } else {
/*  19: 51 */       this.fileObject_ = null;
/*  20:    */     }
/*  21: 54 */     this.contentType_ = contentType;
/*  22: 55 */     this.charset_ = charset;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean equals(Object object)
/*  26:    */   {
/*  27: 66 */     return super.equals(object);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int hashCode()
/*  31:    */   {
/*  32: 77 */     return super.hashCode();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public File getFile()
/*  36:    */   {
/*  37: 84 */     return this.fileObject_;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getCharset()
/*  41:    */   {
/*  42: 92 */     return this.charset_;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getContentType()
/*  46:    */   {
/*  47:100 */     return this.contentType_;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public byte[] getData()
/*  51:    */   {
/*  52:108 */     return this.data_;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setData(byte[] data)
/*  56:    */   {
/*  57:116 */     this.data_ = data;
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.KeyDataPair
 * JD-Core Version:    0.7.0.1
 */