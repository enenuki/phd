/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ 
/*   7:    */ public class TeeInputStream
/*   8:    */   extends ProxyInputStream
/*   9:    */ {
/*  10:    */   private final OutputStream branch;
/*  11:    */   private final boolean closeBranch;
/*  12:    */   
/*  13:    */   public TeeInputStream(InputStream input, OutputStream branch)
/*  14:    */   {
/*  15: 60 */     this(input, branch, false);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public TeeInputStream(InputStream input, OutputStream branch, boolean closeBranch)
/*  19:    */   {
/*  20: 76 */     super(input);
/*  21: 77 */     this.branch = branch;
/*  22: 78 */     this.closeBranch = closeBranch;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void close()
/*  26:    */     throws IOException
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 91 */       super.close();
/*  31:    */     }
/*  32:    */     finally
/*  33:    */     {
/*  34: 93 */       if (this.closeBranch) {
/*  35: 94 */         this.branch.close();
/*  36:    */       }
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int read()
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:108 */     int ch = super.read();
/*  44:109 */     if (ch != -1) {
/*  45:110 */       this.branch.write(ch);
/*  46:    */     }
/*  47:112 */     return ch;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int read(byte[] bts, int st, int end)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53:127 */     int n = super.read(bts, st, end);
/*  54:128 */     if (n != -1) {
/*  55:129 */       this.branch.write(bts, st, n);
/*  56:    */     }
/*  57:131 */     return n;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int read(byte[] bts)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:144 */     int n = super.read(bts);
/*  64:145 */     if (n != -1) {
/*  65:146 */       this.branch.write(bts, 0, n);
/*  66:    */     }
/*  67:148 */     return n;
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.TeeInputStream
 * JD-Core Version:    0.7.0.1
 */