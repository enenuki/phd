/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class ANTLRStringBuffer
/*  4:   */ {
/*  5:14 */   protected char[] buffer = null;
/*  6:15 */   protected int length = 0;
/*  7:   */   
/*  8:   */   public ANTLRStringBuffer()
/*  9:   */   {
/* 10:19 */     this.buffer = new char[50];
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ANTLRStringBuffer(int paramInt)
/* 14:   */   {
/* 15:23 */     this.buffer = new char[paramInt];
/* 16:   */   }
/* 17:   */   
/* 18:   */   public final void append(char paramChar)
/* 19:   */   {
/* 20:29 */     if (this.length >= this.buffer.length)
/* 21:   */     {
/* 22:31 */       int i = this.buffer.length;
/* 23:32 */       while (this.length >= i) {
/* 24:33 */         i *= 2;
/* 25:   */       }
/* 26:36 */       char[] arrayOfChar = new char[i];
/* 27:37 */       for (int j = 0; j < this.length; j++) {
/* 28:38 */         arrayOfChar[j] = this.buffer[j];
/* 29:   */       }
/* 30:40 */       this.buffer = arrayOfChar;
/* 31:   */     }
/* 32:42 */     this.buffer[this.length] = paramChar;
/* 33:43 */     this.length += 1;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public final void append(String paramString)
/* 37:   */   {
/* 38:47 */     for (int i = 0; i < paramString.length(); i++) {
/* 39:48 */       append(paramString.charAt(i));
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public final char charAt(int paramInt)
/* 44:   */   {
/* 45:53 */     return this.buffer[paramInt];
/* 46:   */   }
/* 47:   */   
/* 48:   */   public final char[] getBuffer()
/* 49:   */   {
/* 50:57 */     return this.buffer;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public final int length()
/* 54:   */   {
/* 55:61 */     return this.length;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public final void setCharAt(int paramInt, char paramChar)
/* 59:   */   {
/* 60:65 */     this.buffer[paramInt] = paramChar;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public final void setLength(int paramInt)
/* 64:   */   {
/* 65:69 */     if (paramInt < this.length) {
/* 66:70 */       this.length = paramInt;
/* 67:   */     } else {
/* 68:73 */       while (paramInt > this.length) {
/* 69:74 */         append('\000');
/* 70:   */       }
/* 71:   */     }
/* 72:   */   }
/* 73:   */   
/* 74:   */   public final String toString()
/* 75:   */   {
/* 76:80 */     return new String(this.buffer, 0, this.length);
/* 77:   */   }
/* 78:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ANTLRStringBuffer
 * JD-Core Version:    0.7.0.1
 */