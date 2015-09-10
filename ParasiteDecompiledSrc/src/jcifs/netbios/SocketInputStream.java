/*   1:    */ package jcifs.netbios;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ 
/*   6:    */ class SocketInputStream
/*   7:    */   extends InputStream
/*   8:    */ {
/*   9:    */   private static final int TMP_BUFFER_SIZE = 256;
/*  10:    */   private InputStream in;
/*  11:    */   private SessionServicePacket ssp;
/*  12:    */   private int tot;
/*  13:    */   private int bip;
/*  14:    */   private int n;
/*  15:    */   private byte[] header;
/*  16:    */   private byte[] tmp;
/*  17:    */   
/*  18:    */   SocketInputStream(InputStream in)
/*  19:    */   {
/*  20: 34 */     this.in = in;
/*  21: 35 */     this.header = new byte[4];
/*  22: 36 */     this.tmp = new byte[256];
/*  23:    */   }
/*  24:    */   
/*  25:    */   public synchronized int read()
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 40 */     if (read(this.tmp, 0, 1) < 0) {
/*  29: 41 */       return -1;
/*  30:    */     }
/*  31: 43 */     return this.tmp[0] & 0xFF;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public synchronized int read(byte[] b)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 46 */     return read(b, 0, b.length);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public synchronized int read(byte[] b, int off, int len)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 54 */     if (len == 0) {
/*  44: 55 */       return 0;
/*  45:    */     }
/*  46: 57 */     this.tot = 0;
/*  47:    */     for (;;)
/*  48:    */     {
/*  49: 60 */       if (this.bip > 0)
/*  50:    */       {
/*  51: 61 */         this.n = this.in.read(b, off, Math.min(len, this.bip));
/*  52: 62 */         if (this.n == -1) {
/*  53: 63 */           return this.tot > 0 ? this.tot : -1;
/*  54:    */         }
/*  55: 65 */         this.tot += this.n;
/*  56: 66 */         off += this.n;
/*  57: 67 */         len -= this.n;
/*  58: 68 */         this.bip -= this.n;
/*  59: 69 */         if (len == 0) {
/*  60: 70 */           return this.tot;
/*  61:    */         }
/*  62:    */       }
/*  63:    */       else
/*  64:    */       {
/*  65: 74 */         switch (SessionServicePacket.readPacketType(this.in, this.header, 0))
/*  66:    */         {
/*  67:    */         case 133: 
/*  68:    */           break;
/*  69:    */         case 0: 
/*  70: 78 */           this.bip = SessionServicePacket.readLength(this.header, 0);
/*  71: 79 */           break;
/*  72:    */         case -1: 
/*  73: 81 */           if (this.tot > 0) {
/*  74: 82 */             return this.tot;
/*  75:    */           }
/*  76: 84 */           return -1;
/*  77:    */         }
/*  78:    */       }
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public synchronized long skip(long numbytes)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85: 89 */     if (numbytes <= 0L) {
/*  86: 90 */       return 0L;
/*  87:    */     }
/*  88: 92 */     long n = numbytes;
/*  89: 93 */     while (n > 0L)
/*  90:    */     {
/*  91: 94 */       int r = read(this.tmp, 0, (int)Math.min(256L, n));
/*  92: 95 */       if (r < 0) {
/*  93:    */         break;
/*  94:    */       }
/*  95: 98 */       n -= r;
/*  96:    */     }
/*  97:100 */     return numbytes - n;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int available()
/* 101:    */     throws IOException
/* 102:    */   {
/* 103:103 */     if (this.bip > 0) {
/* 104:104 */       return this.bip;
/* 105:    */     }
/* 106:106 */     return this.in.available();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void close()
/* 110:    */     throws IOException
/* 111:    */   {
/* 112:109 */     this.in.close();
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.SocketInputStream
 * JD-Core Version:    0.7.0.1
 */