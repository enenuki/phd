/*   1:    */ package org.apache.http.entity;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import java.io.UnsupportedEncodingException;
/*   8:    */ 
/*   9:    */ public class StringEntity
/*  10:    */   extends AbstractHttpEntity
/*  11:    */   implements Cloneable
/*  12:    */ {
/*  13:    */   protected final byte[] content;
/*  14:    */   
/*  15:    */   public StringEntity(String string, String mimeType, String charset)
/*  16:    */     throws UnsupportedEncodingException
/*  17:    */   {
/*  18: 61 */     if (string == null) {
/*  19: 62 */       throw new IllegalArgumentException("Source string may not be null");
/*  20:    */     }
/*  21: 64 */     if (mimeType == null) {
/*  22: 65 */       mimeType = "text/plain";
/*  23:    */     }
/*  24: 67 */     if (charset == null) {
/*  25: 68 */       charset = "ISO-8859-1";
/*  26:    */     }
/*  27: 70 */     this.content = string.getBytes(charset);
/*  28: 71 */     setContentType(mimeType + "; charset=" + charset);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public StringEntity(String string, String charset)
/*  32:    */     throws UnsupportedEncodingException
/*  33:    */   {
/*  34: 86 */     this(string, null, charset);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public StringEntity(String string)
/*  38:    */     throws UnsupportedEncodingException
/*  39:    */   {
/*  40:102 */     this(string, null);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean isRepeatable()
/*  44:    */   {
/*  45:106 */     return true;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public long getContentLength()
/*  49:    */   {
/*  50:110 */     return this.content.length;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public InputStream getContent()
/*  54:    */     throws IOException
/*  55:    */   {
/*  56:114 */     return new ByteArrayInputStream(this.content);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void writeTo(OutputStream outstream)
/*  60:    */     throws IOException
/*  61:    */   {
/*  62:118 */     if (outstream == null) {
/*  63:119 */       throw new IllegalArgumentException("Output stream may not be null");
/*  64:    */     }
/*  65:121 */     outstream.write(this.content);
/*  66:122 */     outstream.flush();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean isStreaming()
/*  70:    */   {
/*  71:131 */     return false;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object clone()
/*  75:    */     throws CloneNotSupportedException
/*  76:    */   {
/*  77:135 */     return super.clone();
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.StringEntity
 * JD-Core Version:    0.7.0.1
 */