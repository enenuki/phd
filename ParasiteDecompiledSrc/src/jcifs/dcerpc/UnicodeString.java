/*  1:   */ package jcifs.dcerpc;
/*  2:   */ 
/*  3:   */ public class UnicodeString
/*  4:   */   extends rpc.unicode_string
/*  5:   */ {
/*  6:   */   boolean zterm;
/*  7:   */   
/*  8:   */   public UnicodeString(boolean zterm)
/*  9:   */   {
/* 10:27 */     this.zterm = zterm;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public UnicodeString(rpc.unicode_string rus, boolean zterm)
/* 14:   */   {
/* 15:30 */     this.length = rus.length;
/* 16:31 */     this.maximum_length = rus.maximum_length;
/* 17:32 */     this.buffer = rus.buffer;
/* 18:33 */     this.zterm = zterm;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public UnicodeString(String str, boolean zterm)
/* 22:   */   {
/* 23:37 */     this.zterm = zterm;
/* 24:   */     
/* 25:39 */     int len = str.length();
/* 26:40 */     int zt = zterm ? 1 : 0;
/* 27:   */     
/* 28:42 */     this.length = (this.maximum_length = (short)((len + zt) * 2));
/* 29:43 */     this.buffer = new short[len + zt];
/* 30:46 */     for (int i = 0; i < len; i++) {
/* 31:47 */       this.buffer[i] = ((short)str.charAt(i));
/* 32:   */     }
/* 33:49 */     if (zterm) {
/* 34:50 */       this.buffer[i] = 0;
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String toString()
/* 39:   */   {
/* 40:55 */     int len = this.length / 2 - (this.zterm ? 1 : 0);
/* 41:56 */     char[] ca = new char[len];
/* 42:57 */     for (int i = 0; i < len; i++) {
/* 43:58 */       ca[i] = ((char)this.buffer[i]);
/* 44:   */     }
/* 45:60 */     return new String(ca, 0, len);
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.UnicodeString
 * JD-Core Version:    0.7.0.1
 */