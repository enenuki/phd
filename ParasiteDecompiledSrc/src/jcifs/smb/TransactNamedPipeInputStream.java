/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.MalformedURLException;
/*   5:    */ import java.net.UnknownHostException;
/*   6:    */ import jcifs.util.LogStream;
/*   7:    */ 
/*   8:    */ class TransactNamedPipeInputStream
/*   9:    */   extends SmbFileInputStream
/*  10:    */ {
/*  11:    */   private static final int INIT_PIPE_SIZE = 4096;
/*  12: 30 */   private byte[] pipe_buf = new byte[4096];
/*  13:    */   private int beg_idx;
/*  14:    */   private int nxt_idx;
/*  15:    */   private int used;
/*  16:    */   private boolean dcePipe;
/*  17:    */   Object lock;
/*  18:    */   
/*  19:    */   TransactNamedPipeInputStream(SmbNamedPipe pipe)
/*  20:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  21:    */   {
/*  22: 38 */     super(pipe, pipe.pipeType & 0xFFFF00FF | 0x20);
/*  23: 39 */     this.dcePipe = ((pipe.pipeType & 0x600) != 1536);
/*  24: 40 */     this.lock = new Object();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int read()
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 43 */     int result = -1;
/*  31: 45 */     synchronized (this.lock)
/*  32:    */     {
/*  33:    */       try
/*  34:    */       {
/*  35: 47 */         while (this.used == 0) {
/*  36: 48 */           this.lock.wait();
/*  37:    */         }
/*  38:    */       }
/*  39:    */       catch (InterruptedException ie)
/*  40:    */       {
/*  41: 51 */         throw new IOException(ie.getMessage());
/*  42:    */       }
/*  43: 53 */       result = this.pipe_buf[this.beg_idx] & 0xFF;
/*  44: 54 */       this.beg_idx = ((this.beg_idx + 1) % this.pipe_buf.length);
/*  45:    */     }
/*  46: 56 */     return result;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int read(byte[] b)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52: 59 */     return read(b, 0, b.length);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int read(byte[] b, int off, int len)
/*  56:    */     throws IOException
/*  57:    */   {
/*  58: 62 */     int result = -1;
/*  59: 65 */     if (len <= 0) {
/*  60: 66 */       return 0;
/*  61:    */     }
/*  62: 68 */     synchronized (this.lock)
/*  63:    */     {
/*  64:    */       try
/*  65:    */       {
/*  66: 70 */         while (this.used == 0) {
/*  67: 71 */           this.lock.wait();
/*  68:    */         }
/*  69:    */       }
/*  70:    */       catch (InterruptedException ie)
/*  71:    */       {
/*  72: 74 */         throw new IOException(ie.getMessage());
/*  73:    */       }
/*  74: 76 */       int i = this.pipe_buf.length - this.beg_idx;
/*  75: 77 */       result = len > this.used ? this.used : len;
/*  76: 78 */       if ((this.used > i) && (result > i))
/*  77:    */       {
/*  78: 79 */         System.arraycopy(this.pipe_buf, this.beg_idx, b, off, i);
/*  79: 80 */         off += i;
/*  80: 81 */         System.arraycopy(this.pipe_buf, 0, b, off, result - i);
/*  81:    */       }
/*  82:    */       else
/*  83:    */       {
/*  84: 83 */         System.arraycopy(this.pipe_buf, this.beg_idx, b, off, result);
/*  85:    */       }
/*  86: 85 */       this.used -= result;
/*  87: 86 */       this.beg_idx = ((this.beg_idx + result) % this.pipe_buf.length);
/*  88:    */     }
/*  89:    */     int i;
/*  90: 89 */     return result;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int available()
/*  94:    */     throws IOException
/*  95:    */   {
/*  96: 92 */     if (LogStream.level >= 3) {
/*  97: 93 */       SmbFile.log.println("Named Pipe available() does not apply to TRANSACT Named Pipes");
/*  98:    */     }
/*  99: 94 */     return 0;
/* 100:    */   }
/* 101:    */   
/* 102:    */   int receive(byte[] b, int off, int len)
/* 103:    */   {
/* 104: 99 */     if (len > this.pipe_buf.length - this.used)
/* 105:    */     {
/* 106:103 */       int new_size = this.pipe_buf.length * 2;
/* 107:104 */       if (len > new_size - this.used) {
/* 108:105 */         new_size = len + this.used;
/* 109:    */       }
/* 110:107 */       byte[] tmp = this.pipe_buf;
/* 111:108 */       this.pipe_buf = new byte[new_size];
/* 112:109 */       int i = tmp.length - this.beg_idx;
/* 113:110 */       if (this.used > i)
/* 114:    */       {
/* 115:111 */         System.arraycopy(tmp, this.beg_idx, this.pipe_buf, 0, i);
/* 116:112 */         System.arraycopy(tmp, 0, this.pipe_buf, i, this.used - i);
/* 117:    */       }
/* 118:    */       else
/* 119:    */       {
/* 120:114 */         System.arraycopy(tmp, this.beg_idx, this.pipe_buf, 0, this.used);
/* 121:    */       }
/* 122:116 */       this.beg_idx = 0;
/* 123:117 */       this.nxt_idx = this.used;
/* 124:118 */       tmp = null;
/* 125:    */     }
/* 126:121 */     int i = this.pipe_buf.length - this.nxt_idx;
/* 127:122 */     if (len > i)
/* 128:    */     {
/* 129:123 */       System.arraycopy(b, off, this.pipe_buf, this.nxt_idx, i);
/* 130:124 */       off += i;
/* 131:125 */       System.arraycopy(b, off, this.pipe_buf, 0, len - i);
/* 132:    */     }
/* 133:    */     else
/* 134:    */     {
/* 135:127 */       System.arraycopy(b, off, this.pipe_buf, this.nxt_idx, len);
/* 136:    */     }
/* 137:129 */     this.nxt_idx = ((this.nxt_idx + len) % this.pipe_buf.length);
/* 138:130 */     this.used += len;
/* 139:131 */     return len;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int dce_read(byte[] b, int off, int len)
/* 143:    */     throws IOException
/* 144:    */   {
/* 145:134 */     return super.read(b, off, len);
/* 146:    */   }
/* 147:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.TransactNamedPipeInputStream
 * JD-Core Version:    0.7.0.1
 */