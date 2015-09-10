/*  1:   */ package jcifs.util;
/*  2:   */ 
/*  3:   */ public class RC4
/*  4:   */ {
/*  5:   */   byte[] s;
/*  6:   */   int i;
/*  7:   */   int j;
/*  8:   */   
/*  9:   */   public RC4() {}
/* 10:   */   
/* 11:   */   public RC4(byte[] key)
/* 12:   */   {
/* 13:31 */     init(key, 0, key.length);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void init(byte[] key, int ki, int klen)
/* 17:   */   {
/* 18:36 */     this.s = new byte[256];
/* 19:38 */     for (this.i = 0; this.i < 256; this.i += 1) {
/* 20:39 */       this.s[this.i] = ((byte)this.i);
/* 21:   */     }
/* 22:41 */     for (this.i = (this.j = 0); this.i < 256; this.i += 1)
/* 23:   */     {
/* 24:42 */       this.j = (this.j + key[(ki + this.i % klen)] + this.s[this.i] & 0xFF);
/* 25:43 */       byte t = this.s[this.i];
/* 26:44 */       this.s[this.i] = this.s[this.j];
/* 27:45 */       this.s[this.j] = t;
/* 28:   */     }
/* 29:48 */     this.i = (this.j = 0);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void update(byte[] src, int soff, int slen, byte[] dst, int doff)
/* 33:   */   {
/* 34:54 */     int slim = soff + slen;
/* 35:55 */     while (soff < slim)
/* 36:   */     {
/* 37:56 */       this.i = (this.i + 1 & 0xFF);
/* 38:57 */       this.j = (this.j + this.s[this.i] & 0xFF);
/* 39:58 */       byte t = this.s[this.i];
/* 40:59 */       this.s[this.i] = this.s[this.j];
/* 41:60 */       this.s[this.j] = t;
/* 42:61 */       dst[(doff++)] = ((byte)(src[(soff++)] ^ this.s[(this.s[this.i] + this.s[this.j] & 0xFF)]));
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.util.RC4
 * JD-Core Version:    0.7.0.1
 */