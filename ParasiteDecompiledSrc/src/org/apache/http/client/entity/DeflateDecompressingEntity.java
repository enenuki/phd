/*   1:    */ package org.apache.http.client.entity;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.PushbackInputStream;
/*   6:    */ import java.util.zip.DataFormatException;
/*   7:    */ import java.util.zip.Inflater;
/*   8:    */ import java.util.zip.InflaterInputStream;
/*   9:    */ import org.apache.http.Header;
/*  10:    */ import org.apache.http.HttpEntity;
/*  11:    */ 
/*  12:    */ public class DeflateDecompressingEntity
/*  13:    */   extends DecompressingEntity
/*  14:    */ {
/*  15:    */   public DeflateDecompressingEntity(HttpEntity entity)
/*  16:    */   {
/*  17: 62 */     super(entity);
/*  18:    */   }
/*  19:    */   
/*  20:    */   InputStream getDecompressingInputStream(InputStream wrapped)
/*  21:    */     throws IOException
/*  22:    */   {
/*  23:100 */     byte[] peeked = new byte[6];
/*  24:    */     
/*  25:102 */     PushbackInputStream pushback = new PushbackInputStream(wrapped, peeked.length);
/*  26:    */     
/*  27:104 */     int headerLength = pushback.read(peeked);
/*  28:106 */     if (headerLength == -1) {
/*  29:107 */       throw new IOException("Unable to read the response");
/*  30:    */     }
/*  31:111 */     byte[] dummy = new byte[1];
/*  32:    */     
/*  33:113 */     Inflater inf = new Inflater();
/*  34:    */     try
/*  35:    */     {
/*  36:    */       int n;
/*  37:117 */       while ((n = inf.inflate(dummy)) == 0)
/*  38:    */       {
/*  39:118 */         if (inf.finished()) {
/*  40:121 */           throw new IOException("Unable to read the response");
/*  41:    */         }
/*  42:124 */         if (inf.needsDictionary()) {
/*  43:    */           break;
/*  44:    */         }
/*  45:130 */         if (inf.needsInput()) {
/*  46:131 */           inf.setInput(peeked);
/*  47:    */         }
/*  48:    */       }
/*  49:135 */       if (n == -1) {
/*  50:136 */         throw new IOException("Unable to read the response");
/*  51:    */       }
/*  52:143 */       pushback.unread(peeked, 0, headerLength);
/*  53:144 */       return new InflaterInputStream(pushback);
/*  54:    */     }
/*  55:    */     catch (DataFormatException e)
/*  56:    */     {
/*  57:149 */       pushback.unread(peeked, 0, headerLength);
/*  58:    */     }
/*  59:150 */     return new InflaterInputStream(pushback, new Inflater(true));
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Header getContentEncoding()
/*  63:    */   {
/*  64:161 */     return null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public long getContentLength()
/*  68:    */   {
/*  69:171 */     return -1L;
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.entity.DeflateDecompressingEntity
 * JD-Core Version:    0.7.0.1
 */