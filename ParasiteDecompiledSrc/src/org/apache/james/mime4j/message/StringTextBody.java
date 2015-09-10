/*  1:   */ package org.apache.james.mime4j.message;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ import java.io.OutputStreamWriter;
/*  6:   */ import java.io.Reader;
/*  7:   */ import java.io.StringReader;
/*  8:   */ import java.io.Writer;
/*  9:   */ import java.nio.charset.Charset;
/* 10:   */ import org.apache.james.mime4j.util.CharsetUtil;
/* 11:   */ 
/* 12:   */ class StringTextBody
/* 13:   */   extends TextBody
/* 14:   */ {
/* 15:   */   private final String text;
/* 16:   */   private final Charset charset;
/* 17:   */   
/* 18:   */   public StringTextBody(String text, Charset charset)
/* 19:   */   {
/* 20:41 */     this.text = text;
/* 21:42 */     this.charset = charset;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getMimeCharset()
/* 25:   */   {
/* 26:47 */     return CharsetUtil.toMimeCharset(this.charset.name());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Reader getReader()
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:52 */     return new StringReader(this.text);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void writeTo(OutputStream out)
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:57 */     if (out == null) {
/* 39:58 */       throw new IllegalArgumentException();
/* 40:   */     }
/* 41:60 */     Reader reader = new StringReader(this.text);
/* 42:61 */     Writer writer = new OutputStreamWriter(out, this.charset);
/* 43:   */     
/* 44:63 */     char[] buffer = new char[1024];
/* 45:   */     for (;;)
/* 46:   */     {
/* 47:65 */       int nChars = reader.read(buffer);
/* 48:66 */       if (nChars == -1) {
/* 49:   */         break;
/* 50:   */       }
/* 51:69 */       writer.write(buffer, 0, nChars);
/* 52:   */     }
/* 53:72 */     reader.close();
/* 54:73 */     writer.flush();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public StringTextBody copy()
/* 58:   */   {
/* 59:78 */     return new StringTextBody(this.text, this.charset);
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.StringTextBody
 * JD-Core Version:    0.7.0.1
 */