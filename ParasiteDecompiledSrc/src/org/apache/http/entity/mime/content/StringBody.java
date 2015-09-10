/*   1:    */ package org.apache.http.entity.mime.content;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.io.OutputStream;
/*   8:    */ import java.io.Reader;
/*   9:    */ import java.io.UnsupportedEncodingException;
/*  10:    */ import java.nio.charset.Charset;
/*  11:    */ 
/*  12:    */ public class StringBody
/*  13:    */   extends AbstractContentBody
/*  14:    */ {
/*  15:    */   private final byte[] content;
/*  16:    */   private final Charset charset;
/*  17:    */   
/*  18:    */   public static StringBody create(String text, String mimeType, Charset charset)
/*  19:    */     throws IllegalArgumentException
/*  20:    */   {
/*  21:    */     try
/*  22:    */     {
/*  23: 58 */       return new StringBody(text, mimeType, charset);
/*  24:    */     }
/*  25:    */     catch (UnsupportedEncodingException ex)
/*  26:    */     {
/*  27: 60 */       throw new IllegalArgumentException("Charset " + charset + " is not supported", ex);
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static StringBody create(String text, Charset charset)
/*  32:    */     throws IllegalArgumentException
/*  33:    */   {
/*  34: 69 */     return create(text, null, charset);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static StringBody create(String text)
/*  38:    */     throws IllegalArgumentException
/*  39:    */   {
/*  40: 76 */     return create(text, null, null);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public StringBody(String text, String mimeType, Charset charset)
/*  44:    */     throws UnsupportedEncodingException
/*  45:    */   {
/*  46: 92 */     super(mimeType);
/*  47: 93 */     if (text == null) {
/*  48: 94 */       throw new IllegalArgumentException("Text may not be null");
/*  49:    */     }
/*  50: 96 */     if (charset == null) {
/*  51: 97 */       charset = Charset.forName("US-ASCII");
/*  52:    */     }
/*  53: 99 */     this.content = text.getBytes(charset.name());
/*  54:100 */     this.charset = charset;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public StringBody(String text, Charset charset)
/*  58:    */     throws UnsupportedEncodingException
/*  59:    */   {
/*  60:113 */     this(text, "text/plain", charset);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public StringBody(String text)
/*  64:    */     throws UnsupportedEncodingException
/*  65:    */   {
/*  66:126 */     this(text, "text/plain", null);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Reader getReader()
/*  70:    */   {
/*  71:130 */     return new InputStreamReader(new ByteArrayInputStream(this.content), this.charset);
/*  72:    */   }
/*  73:    */   
/*  74:    */   @Deprecated
/*  75:    */   public void writeTo(OutputStream out, int mode)
/*  76:    */     throws IOException
/*  77:    */   {
/*  78:140 */     writeTo(out);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void writeTo(OutputStream out)
/*  82:    */     throws IOException
/*  83:    */   {
/*  84:144 */     if (out == null) {
/*  85:145 */       throw new IllegalArgumentException("Output stream may not be null");
/*  86:    */     }
/*  87:147 */     InputStream in = new ByteArrayInputStream(this.content);
/*  88:148 */     byte[] tmp = new byte[4096];
/*  89:    */     int l;
/*  90:150 */     while ((l = in.read(tmp)) != -1) {
/*  91:151 */       out.write(tmp, 0, l);
/*  92:    */     }
/*  93:153 */     out.flush();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getTransferEncoding()
/*  97:    */   {
/*  98:157 */     return "8bit";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getCharset()
/* 102:    */   {
/* 103:161 */     return this.charset.name();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public long getContentLength()
/* 107:    */   {
/* 108:165 */     return this.content.length;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getFilename()
/* 112:    */   {
/* 113:169 */     return null;
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.content.StringBody
 * JD-Core Version:    0.7.0.1
 */