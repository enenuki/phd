/*   1:    */ package org.apache.regexp;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ 
/*   6:    */ public final class StreamCharacterIterator
/*   7:    */   implements CharacterIterator
/*   8:    */ {
/*   9:    */   private final InputStream is;
/*  10:    */   private final StringBuffer buff;
/*  11:    */   private boolean closed;
/*  12:    */   
/*  13:    */   public StreamCharacterIterator(InputStream paramInputStream)
/*  14:    */   {
/*  15: 81 */     this.is = paramInputStream;
/*  16: 82 */     this.buff = new StringBuffer(512);
/*  17: 83 */     this.closed = false;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String substring(int paramInt1, int paramInt2)
/*  21:    */   {
/*  22:    */     try
/*  23:    */     {
/*  24: 91 */       ensure(paramInt1 + paramInt2);
/*  25: 92 */       return this.buff.toString().substring(paramInt1, paramInt2);
/*  26:    */     }
/*  27:    */     catch (IOException localIOException)
/*  28:    */     {
/*  29: 96 */       throw new StringIndexOutOfBoundsException(localIOException.getMessage());
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String substring(int paramInt)
/*  34:    */   {
/*  35:    */     try
/*  36:    */     {
/*  37:105 */       readAll();
/*  38:106 */       return this.buff.toString().substring(paramInt);
/*  39:    */     }
/*  40:    */     catch (IOException localIOException)
/*  41:    */     {
/*  42:110 */       throw new StringIndexOutOfBoundsException(localIOException.getMessage());
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public char charAt(int paramInt)
/*  47:    */   {
/*  48:    */     try
/*  49:    */     {
/*  50:120 */       ensure(paramInt);
/*  51:121 */       return this.buff.charAt(paramInt);
/*  52:    */     }
/*  53:    */     catch (IOException localIOException)
/*  54:    */     {
/*  55:125 */       throw new StringIndexOutOfBoundsException(localIOException.getMessage());
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isEnd(int paramInt)
/*  60:    */   {
/*  61:132 */     if (this.buff.length() > paramInt) {
/*  62:134 */       return false;
/*  63:    */     }
/*  64:    */     try
/*  65:    */     {
/*  66:140 */       ensure(paramInt);
/*  67:141 */       return this.buff.length() <= paramInt;
/*  68:    */     }
/*  69:    */     catch (IOException localIOException)
/*  70:    */     {
/*  71:145 */       throw new StringIndexOutOfBoundsException(localIOException.getMessage());
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   private int read(int paramInt)
/*  76:    */     throws IOException
/*  77:    */   {
/*  78:153 */     if (this.closed) {
/*  79:155 */       return 0;
/*  80:    */     }
/*  81:159 */     int j = paramInt;
/*  82:    */     do
/*  83:    */     {
/*  84:162 */       int i = this.is.read();
/*  85:163 */       if (i < 0)
/*  86:    */       {
/*  87:165 */         this.closed = true;
/*  88:166 */         break;
/*  89:    */       }
/*  90:168 */       this.buff.append((char)i);j--;
/*  91:160 */     } while (j >= 0);
/*  92:170 */     return paramInt - j;
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void readAll()
/*  96:    */     throws IOException
/*  97:    */   {
/*  98:176 */     while (!this.closed) {
/*  99:178 */       read(1000);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   private void ensure(int paramInt)
/* 104:    */     throws IOException
/* 105:    */   {
/* 106:185 */     if (this.closed) {
/* 107:187 */       return;
/* 108:    */     }
/* 109:190 */     if (paramInt < this.buff.length()) {
/* 110:192 */       return;
/* 111:    */     }
/* 112:195 */     read(paramInt + 1 - this.buff.length());
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.StreamCharacterIterator
 * JD-Core Version:    0.7.0.1
 */