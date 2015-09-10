/*   1:    */ package org.apache.james.mime4j.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.james.mime4j.util.ByteArrayBuffer;
/*   6:    */ 
/*   7:    */ public class LineReaderInputStreamAdaptor
/*   8:    */   extends LineReaderInputStream
/*   9:    */ {
/*  10:    */   private final LineReaderInputStream bis;
/*  11:    */   private final int maxLineLen;
/*  12: 37 */   private boolean used = false;
/*  13: 38 */   private boolean eof = false;
/*  14:    */   
/*  15:    */   public LineReaderInputStreamAdaptor(InputStream is, int maxLineLen)
/*  16:    */   {
/*  17: 43 */     super(is);
/*  18: 44 */     if ((is instanceof LineReaderInputStream)) {
/*  19: 45 */       this.bis = ((LineReaderInputStream)is);
/*  20:    */     } else {
/*  21: 47 */       this.bis = null;
/*  22:    */     }
/*  23: 49 */     this.maxLineLen = maxLineLen;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public LineReaderInputStreamAdaptor(InputStream is)
/*  27:    */   {
/*  28: 54 */     this(is, -1);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int read()
/*  32:    */     throws IOException
/*  33:    */   {
/*  34: 59 */     int i = this.in.read();
/*  35: 60 */     this.eof = (i == -1);
/*  36: 61 */     this.used = true;
/*  37: 62 */     return i;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int read(byte[] b, int off, int len)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 67 */     int i = this.in.read(b, off, len);
/*  44: 68 */     this.eof = (i == -1);
/*  45: 69 */     this.used = true;
/*  46: 70 */     return i;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int readLine(ByteArrayBuffer dst)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52:    */     int i;
/*  53:    */     int i;
/*  54: 76 */     if (this.bis != null) {
/*  55: 77 */       i = this.bis.readLine(dst);
/*  56:    */     } else {
/*  57: 79 */       i = doReadLine(dst);
/*  58:    */     }
/*  59: 81 */     this.eof = (i == -1);
/*  60: 82 */     this.used = true;
/*  61: 83 */     return i;
/*  62:    */   }
/*  63:    */   
/*  64:    */   private int doReadLine(ByteArrayBuffer dst)
/*  65:    */     throws IOException
/*  66:    */   {
/*  67: 87 */     int total = 0;
/*  68:    */     int ch;
/*  69: 89 */     while ((ch = this.in.read()) != -1)
/*  70:    */     {
/*  71: 90 */       dst.append(ch);
/*  72: 91 */       total++;
/*  73: 92 */       if ((this.maxLineLen > 0) && (dst.length() >= this.maxLineLen)) {
/*  74: 93 */         throw new MaxLineLimitException("Maximum line length limit exceeded");
/*  75:    */       }
/*  76: 95 */       if (ch == 10) {
/*  77:    */         break;
/*  78:    */       }
/*  79:    */     }
/*  80: 99 */     if ((total == 0) && (ch == -1)) {
/*  81:100 */       return -1;
/*  82:    */     }
/*  83:102 */     return total;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean eof()
/*  87:    */   {
/*  88:107 */     return this.eof;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isUsed()
/*  92:    */   {
/*  93:111 */     return this.used;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String toString()
/*  97:    */   {
/*  98:116 */     return "[LineReaderInputStreamAdaptor: " + this.bis + "]";
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.io.LineReaderInputStreamAdaptor
 * JD-Core Version:    0.7.0.1
 */