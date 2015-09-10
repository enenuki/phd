/*  1:   */ package jcifs.netbios;
/*  2:   */ 
/*  3:   */ import java.io.FilterOutputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ 
/*  7:   */ class SocketOutputStream
/*  8:   */   extends FilterOutputStream
/*  9:   */ {
/* 10:   */   SocketOutputStream(OutputStream out)
/* 11:   */   {
/* 12:28 */     super(out);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public synchronized void write(byte[] b, int off, int len)
/* 16:   */     throws IOException
/* 17:   */   {
/* 18:32 */     if (len > 65535) {
/* 19:33 */       throw new IOException("write too large: " + len);
/* 20:   */     }
/* 21:34 */     if (off < 4) {
/* 22:35 */       throw new IOException("NetBIOS socket output buffer requires 4 bytes available before off");
/* 23:   */     }
/* 24:38 */     off -= 4;
/* 25:   */     
/* 26:40 */     b[(off + 0)] = 0;
/* 27:41 */     b[(off + 1)] = 0;
/* 28:42 */     b[(off + 2)] = ((byte)(len >> 8 & 0xFF));
/* 29:43 */     b[(off + 3)] = ((byte)(len & 0xFF));
/* 30:   */     
/* 31:45 */     this.out.write(b, off, 4 + len);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.SocketOutputStream
 * JD-Core Version:    0.7.0.1
 */