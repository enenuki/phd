/*   1:    */ package org.apache.http.entity;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ 
/*   7:    */ public class InputStreamEntity
/*   8:    */   extends AbstractHttpEntity
/*   9:    */ {
/*  10:    */   private static final int BUFFER_SIZE = 2048;
/*  11:    */   private final InputStream content;
/*  12:    */   private final long length;
/*  13:    */   
/*  14:    */   public InputStreamEntity(InputStream instream, long length)
/*  15:    */   {
/*  16: 49 */     if (instream == null) {
/*  17: 50 */       throw new IllegalArgumentException("Source input stream may not be null");
/*  18:    */     }
/*  19: 52 */     this.content = instream;
/*  20: 53 */     this.length = length;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean isRepeatable()
/*  24:    */   {
/*  25: 57 */     return false;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public long getContentLength()
/*  29:    */   {
/*  30: 61 */     return this.length;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public InputStream getContent()
/*  34:    */     throws IOException
/*  35:    */   {
/*  36: 65 */     return this.content;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void writeTo(OutputStream outstream)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 69 */     if (outstream == null) {
/*  43: 70 */       throw new IllegalArgumentException("Output stream may not be null");
/*  44:    */     }
/*  45: 72 */     InputStream instream = this.content;
/*  46:    */     try
/*  47:    */     {
/*  48: 74 */       byte[] buffer = new byte[2048];
/*  49: 76 */       if (this.length < 0L)
/*  50:    */       {
/*  51:    */         int l;
/*  52: 78 */         while ((l = instream.read(buffer)) != -1) {
/*  53: 79 */           outstream.write(buffer, 0, l);
/*  54:    */         }
/*  55:    */       }
/*  56: 83 */       long remaining = this.length;
/*  57: 84 */       while (remaining > 0L)
/*  58:    */       {
/*  59: 85 */         int l = instream.read(buffer, 0, (int)Math.min(2048L, remaining));
/*  60: 86 */         if (l == -1) {
/*  61:    */           break;
/*  62:    */         }
/*  63: 89 */         outstream.write(buffer, 0, l);
/*  64: 90 */         remaining -= l;
/*  65:    */       }
/*  66:    */     }
/*  67:    */     finally
/*  68:    */     {
/*  69: 94 */       instream.close();
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean isStreaming()
/*  74:    */   {
/*  75: 99 */     return true;
/*  76:    */   }
/*  77:    */   
/*  78:    */   /**
/*  79:    */    * @deprecated
/*  80:    */    */
/*  81:    */   public void consumeContent()
/*  82:    */     throws IOException
/*  83:    */   {
/*  84:109 */     this.content.close();
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.InputStreamEntity
 * JD-Core Version:    0.7.0.1
 */