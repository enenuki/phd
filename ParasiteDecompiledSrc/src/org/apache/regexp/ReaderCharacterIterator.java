/*   1:    */ package org.apache.regexp;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Reader;
/*   5:    */ 
/*   6:    */ public final class ReaderCharacterIterator
/*   7:    */   implements CharacterIterator
/*   8:    */ {
/*   9:    */   private final Reader reader;
/*  10:    */   private final StringBuffer buff;
/*  11:    */   private boolean closed;
/*  12:    */   
/*  13:    */   public ReaderCharacterIterator(Reader paramReader)
/*  14:    */   {
/*  15: 81 */     this.reader = paramReader;
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
/*  50:119 */       ensure(paramInt);
/*  51:120 */       return this.buff.charAt(paramInt);
/*  52:    */     }
/*  53:    */     catch (IOException localIOException)
/*  54:    */     {
/*  55:124 */       throw new StringIndexOutOfBoundsException(localIOException.getMessage());
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isEnd(int paramInt)
/*  60:    */   {
/*  61:131 */     if (this.buff.length() > paramInt) {
/*  62:133 */       return false;
/*  63:    */     }
/*  64:    */     try
/*  65:    */     {
/*  66:139 */       ensure(paramInt);
/*  67:140 */       return this.buff.length() <= paramInt;
/*  68:    */     }
/*  69:    */     catch (IOException localIOException)
/*  70:    */     {
/*  71:144 */       throw new StringIndexOutOfBoundsException(localIOException.getMessage());
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   private int read(int paramInt)
/*  76:    */     throws IOException
/*  77:    */   {
/*  78:152 */     if (this.closed) {
/*  79:154 */       return 0;
/*  80:    */     }
/*  81:157 */     char[] arrayOfChar = new char[paramInt];
/*  82:158 */     int i = 0;
/*  83:159 */     int j = 0;
/*  84:    */     do
/*  85:    */     {
/*  86:163 */       j = this.reader.read(arrayOfChar);
/*  87:164 */       if (j < 0)
/*  88:    */       {
/*  89:166 */         this.closed = true;
/*  90:167 */         break;
/*  91:    */       }
/*  92:169 */       i += j;
/*  93:170 */       this.buff.append(arrayOfChar, 0, j);
/*  94:172 */     } while (i < paramInt);
/*  95:174 */     return i;
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void readAll()
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:180 */     while (!this.closed) {
/* 102:182 */       read(1000);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   private void ensure(int paramInt)
/* 107:    */     throws IOException
/* 108:    */   {
/* 109:189 */     if (this.closed) {
/* 110:191 */       return;
/* 111:    */     }
/* 112:194 */     if (paramInt < this.buff.length()) {
/* 113:196 */       return;
/* 114:    */     }
/* 115:198 */     read(paramInt + 1 - this.buff.length());
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.ReaderCharacterIterator
 * JD-Core Version:    0.7.0.1
 */