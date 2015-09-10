/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ 
/*  5:   */ class TransactNamedPipeOutputStream
/*  6:   */   extends SmbFileOutputStream
/*  7:   */ {
/*  8:   */   private String path;
/*  9:   */   private SmbNamedPipe pipe;
/* 10:28 */   private byte[] tmp = new byte[1];
/* 11:   */   private boolean dcePipe;
/* 12:   */   
/* 13:   */   TransactNamedPipeOutputStream(SmbNamedPipe pipe)
/* 14:   */     throws IOException
/* 15:   */   {
/* 16:32 */     super(pipe, false, pipe.pipeType & 0xFFFF00FF | 0x20);
/* 17:33 */     this.pipe = pipe;
/* 18:34 */     this.dcePipe = ((pipe.pipeType & 0x600) == 1536);
/* 19:35 */     this.path = pipe.unc;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void close()
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:39 */     this.pipe.close();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void write(int b)
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:42 */     this.tmp[0] = ((byte)b);
/* 32:43 */     write(this.tmp, 0, 1);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void write(byte[] b)
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:46 */     write(b, 0, b.length);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void write(byte[] b, int off, int len)
/* 42:   */     throws IOException
/* 43:   */   {
/* 44:49 */     if (len < 0) {
/* 45:50 */       len = 0;
/* 46:   */     }
/* 47:53 */     if ((this.pipe.pipeType & 0x100) == 256)
/* 48:   */     {
/* 49:54 */       this.pipe.send(new TransWaitNamedPipe(this.path), new TransWaitNamedPipeResponse());
/* 50:   */       
/* 51:56 */       this.pipe.send(new TransCallNamedPipe(this.path, b, off, len), new TransCallNamedPipeResponse(this.pipe));
/* 52:   */     }
/* 53:58 */     else if ((this.pipe.pipeType & 0x200) == 512)
/* 54:   */     {
/* 55:60 */       ensureOpen();
/* 56:61 */       TransTransactNamedPipe req = new TransTransactNamedPipe(this.pipe.fid, b, off, len);
/* 57:62 */       if (this.dcePipe) {
/* 58:63 */         req.maxDataCount = 1024;
/* 59:   */       }
/* 60:65 */       this.pipe.send(req, new TransTransactNamedPipeResponse(this.pipe));
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.TransactNamedPipeOutputStream
 * JD-Core Version:    0.7.0.1
 */