/*   1:    */ package org.apache.http.entity;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ 
/*   7:    */ public class BasicHttpEntity
/*   8:    */   extends AbstractHttpEntity
/*   9:    */ {
/*  10:    */   private InputStream content;
/*  11:    */   private long length;
/*  12:    */   
/*  13:    */   public BasicHttpEntity()
/*  14:    */   {
/*  15: 52 */     this.length = -1L;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public long getContentLength()
/*  19:    */   {
/*  20: 56 */     return this.length;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public InputStream getContent()
/*  24:    */     throws IllegalStateException
/*  25:    */   {
/*  26: 69 */     if (this.content == null) {
/*  27: 70 */       throw new IllegalStateException("Content has not been provided");
/*  28:    */     }
/*  29: 72 */     return this.content;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean isRepeatable()
/*  33:    */   {
/*  34: 82 */     return false;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setContentLength(long len)
/*  38:    */   {
/*  39: 92 */     this.length = len;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setContent(InputStream instream)
/*  43:    */   {
/*  44:102 */     this.content = instream;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void writeTo(OutputStream outstream)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:106 */     if (outstream == null) {
/*  51:107 */       throw new IllegalArgumentException("Output stream may not be null");
/*  52:    */     }
/*  53:109 */     InputStream instream = getContent();
/*  54:    */     try
/*  55:    */     {
/*  56:112 */       byte[] tmp = new byte[2048];
/*  57:    */       int l;
/*  58:113 */       while ((l = instream.read(tmp)) != -1) {
/*  59:114 */         outstream.write(tmp, 0, l);
/*  60:    */       }
/*  61:    */     }
/*  62:    */     finally
/*  63:    */     {
/*  64:117 */       instream.close();
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isStreaming()
/*  69:    */   {
/*  70:122 */     return this.content != null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   /**
/*  74:    */    * @deprecated
/*  75:    */    */
/*  76:    */   public void consumeContent()
/*  77:    */     throws IOException
/*  78:    */   {
/*  79:132 */     if (this.content != null) {
/*  80:133 */       this.content.close();
/*  81:    */     }
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.BasicHttpEntity
 * JD-Core Version:    0.7.0.1
 */