/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.FilterReader;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Reader;
/*   6:    */ import java.nio.CharBuffer;
/*   7:    */ 
/*   8:    */ public abstract class ProxyReader
/*   9:    */   extends FilterReader
/*  10:    */ {
/*  11:    */   public ProxyReader(Reader proxy)
/*  12:    */   {
/*  13: 44 */     super(proxy);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public int read()
/*  17:    */     throws IOException
/*  18:    */   {
/*  19:    */     try
/*  20:    */     {
/*  21: 56 */       beforeRead(1);
/*  22: 57 */       int c = this.in.read();
/*  23: 58 */       afterRead(c != -1 ? 1 : -1);
/*  24: 59 */       return c;
/*  25:    */     }
/*  26:    */     catch (IOException e)
/*  27:    */     {
/*  28: 61 */       handleIOException(e);
/*  29:    */     }
/*  30: 62 */     return -1;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int read(char[] chr)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36:    */     try
/*  37:    */     {
/*  38: 75 */       beforeRead(chr != null ? chr.length : 0);
/*  39: 76 */       int n = this.in.read(chr);
/*  40: 77 */       afterRead(n);
/*  41: 78 */       return n;
/*  42:    */     }
/*  43:    */     catch (IOException e)
/*  44:    */     {
/*  45: 80 */       handleIOException(e);
/*  46:    */     }
/*  47: 81 */     return -1;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int read(char[] chr, int st, int len)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53:    */     try
/*  54:    */     {
/*  55: 96 */       beforeRead(len);
/*  56: 97 */       int n = this.in.read(chr, st, len);
/*  57: 98 */       afterRead(n);
/*  58: 99 */       return n;
/*  59:    */     }
/*  60:    */     catch (IOException e)
/*  61:    */     {
/*  62:101 */       handleIOException(e);
/*  63:    */     }
/*  64:102 */     return -1;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int read(CharBuffer target)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72:116 */       beforeRead(target != null ? target.length() : 0);
/*  73:117 */       int n = this.in.read(target);
/*  74:118 */       afterRead(n);
/*  75:119 */       return n;
/*  76:    */     }
/*  77:    */     catch (IOException e)
/*  78:    */     {
/*  79:121 */       handleIOException(e);
/*  80:    */     }
/*  81:122 */     return -1;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public long skip(long ln)
/*  85:    */     throws IOException
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:135 */       return this.in.skip(ln);
/*  90:    */     }
/*  91:    */     catch (IOException e)
/*  92:    */     {
/*  93:137 */       handleIOException(e);
/*  94:    */     }
/*  95:138 */     return 0L;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean ready()
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:150 */       return this.in.ready();
/* 104:    */     }
/* 105:    */     catch (IOException e)
/* 106:    */     {
/* 107:152 */       handleIOException(e);
/* 108:    */     }
/* 109:153 */     return false;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void close()
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:164 */       this.in.close();
/* 118:    */     }
/* 119:    */     catch (IOException e)
/* 120:    */     {
/* 121:166 */       handleIOException(e);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public synchronized void mark(int idx)
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:    */     try
/* 129:    */     {
/* 130:178 */       this.in.mark(idx);
/* 131:    */     }
/* 132:    */     catch (IOException e)
/* 133:    */     {
/* 134:180 */       handleIOException(e);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public synchronized void reset()
/* 139:    */     throws IOException
/* 140:    */   {
/* 141:    */     try
/* 142:    */     {
/* 143:191 */       this.in.reset();
/* 144:    */     }
/* 145:    */     catch (IOException e)
/* 146:    */     {
/* 147:193 */       handleIOException(e);
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean markSupported()
/* 152:    */   {
/* 153:203 */     return this.in.markSupported();
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected void beforeRead(int n)
/* 157:    */     throws IOException
/* 158:    */   {}
/* 159:    */   
/* 160:    */   protected void afterRead(int n)
/* 161:    */     throws IOException
/* 162:    */   {}
/* 163:    */   
/* 164:    */   protected void handleIOException(IOException e)
/* 165:    */     throws IOException
/* 166:    */   {
/* 167:257 */     throw e;
/* 168:    */   }
/* 169:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.ProxyReader
 * JD-Core Version:    0.7.0.1
 */