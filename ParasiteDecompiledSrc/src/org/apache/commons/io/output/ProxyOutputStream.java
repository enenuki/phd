/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.FilterOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ 
/*   7:    */ public class ProxyOutputStream
/*   8:    */   extends FilterOutputStream
/*   9:    */ {
/*  10:    */   public ProxyOutputStream(OutputStream proxy)
/*  11:    */   {
/*  12: 43 */     super(proxy);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public void write(int idx)
/*  16:    */     throws IOException
/*  17:    */   {
/*  18:    */     try
/*  19:    */     {
/*  20: 55 */       beforeWrite(1);
/*  21: 56 */       this.out.write(idx);
/*  22: 57 */       afterWrite(1);
/*  23:    */     }
/*  24:    */     catch (IOException e)
/*  25:    */     {
/*  26: 59 */       handleIOException(e);
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void write(byte[] bts)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33:    */     try
/*  34:    */     {
/*  35: 71 */       int len = bts != null ? bts.length : 0;
/*  36: 72 */       beforeWrite(len);
/*  37: 73 */       this.out.write(bts);
/*  38: 74 */       afterWrite(len);
/*  39:    */     }
/*  40:    */     catch (IOException e)
/*  41:    */     {
/*  42: 76 */       handleIOException(e);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void write(byte[] bts, int st, int end)
/*  47:    */     throws IOException
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51: 90 */       beforeWrite(end);
/*  52: 91 */       this.out.write(bts, st, end);
/*  53: 92 */       afterWrite(end);
/*  54:    */     }
/*  55:    */     catch (IOException e)
/*  56:    */     {
/*  57: 94 */       handleIOException(e);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void flush()
/*  62:    */     throws IOException
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66:105 */       this.out.flush();
/*  67:    */     }
/*  68:    */     catch (IOException e)
/*  69:    */     {
/*  70:107 */       handleIOException(e);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void close()
/*  75:    */     throws IOException
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79:118 */       this.out.close();
/*  80:    */     }
/*  81:    */     catch (IOException e)
/*  82:    */     {
/*  83:120 */       handleIOException(e);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void beforeWrite(int n)
/*  88:    */     throws IOException
/*  89:    */   {}
/*  90:    */   
/*  91:    */   protected void afterWrite(int n)
/*  92:    */     throws IOException
/*  93:    */   {}
/*  94:    */   
/*  95:    */   protected void handleIOException(IOException e)
/*  96:    */     throws IOException
/*  97:    */   {
/*  98:167 */     throw e;
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.ProxyOutputStream
 * JD-Core Version:    0.7.0.1
 */