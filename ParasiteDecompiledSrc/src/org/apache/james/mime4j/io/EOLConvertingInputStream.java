/*   1:    */ package org.apache.james.mime4j.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.PushbackInputStream;
/*   6:    */ 
/*   7:    */ public class EOLConvertingInputStream
/*   8:    */   extends InputStream
/*   9:    */ {
/*  10:    */   public static final int CONVERT_CR = 1;
/*  11:    */   public static final int CONVERT_LF = 2;
/*  12:    */   public static final int CONVERT_BOTH = 3;
/*  13: 39 */   private PushbackInputStream in = null;
/*  14: 40 */   private int previous = 0;
/*  15: 41 */   private int flags = 3;
/*  16:    */   
/*  17:    */   public EOLConvertingInputStream(InputStream in)
/*  18:    */   {
/*  19: 51 */     this(in, 3);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public EOLConvertingInputStream(InputStream in, int flags)
/*  23:    */   {
/*  24: 64 */     this.in = new PushbackInputStream(in, 2);
/*  25: 65 */     this.flags = flags;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void close()
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 75 */     this.in.close();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int read()
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 83 */     int b = this.in.read();
/*  38: 85 */     if (b == -1) {
/*  39: 86 */       return -1;
/*  40:    */     }
/*  41: 89 */     if (((this.flags & 0x1) != 0) && (b == 13))
/*  42:    */     {
/*  43: 90 */       int c = this.in.read();
/*  44: 91 */       if (c != -1) {
/*  45: 92 */         this.in.unread(c);
/*  46:    */       }
/*  47: 94 */       if (c != 10) {
/*  48: 95 */         this.in.unread(10);
/*  49:    */       }
/*  50:    */     }
/*  51: 97 */     else if (((this.flags & 0x2) != 0) && (b == 10) && (this.previous != 13))
/*  52:    */     {
/*  53: 98 */       b = 13;
/*  54: 99 */       this.in.unread(10);
/*  55:    */     }
/*  56:102 */     this.previous = b;
/*  57:    */     
/*  58:104 */     return b;
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.io.EOLConvertingInputStream
 * JD-Core Version:    0.7.0.1
 */