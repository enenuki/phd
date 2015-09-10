/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.FilterWriter;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Writer;
/*   6:    */ 
/*   7:    */ public class ProxyWriter
/*   8:    */   extends FilterWriter
/*   9:    */ {
/*  10:    */   public ProxyWriter(Writer proxy)
/*  11:    */   {
/*  12: 42 */     super(proxy);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public Writer append(char c)
/*  16:    */     throws IOException
/*  17:    */   {
/*  18:    */     try
/*  19:    */     {
/*  20: 56 */       beforeWrite(1);
/*  21: 57 */       this.out.append(c);
/*  22: 58 */       afterWrite(1);
/*  23:    */     }
/*  24:    */     catch (IOException e)
/*  25:    */     {
/*  26: 60 */       handleIOException(e);
/*  27:    */     }
/*  28: 62 */     return this;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Writer append(CharSequence csq, int start, int end)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 77 */       beforeWrite(end - start);
/*  37: 78 */       this.out.append(csq, start, end);
/*  38: 79 */       afterWrite(end - start);
/*  39:    */     }
/*  40:    */     catch (IOException e)
/*  41:    */     {
/*  42: 81 */       handleIOException(e);
/*  43:    */     }
/*  44: 83 */     return this;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Writer append(CharSequence csq)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:    */     try
/*  51:    */     {
/*  52: 96 */       int len = 0;
/*  53: 97 */       if (csq != null) {
/*  54: 98 */         len = csq.length();
/*  55:    */       }
/*  56:101 */       beforeWrite(len);
/*  57:102 */       this.out.append(csq);
/*  58:103 */       afterWrite(len);
/*  59:    */     }
/*  60:    */     catch (IOException e)
/*  61:    */     {
/*  62:105 */       handleIOException(e);
/*  63:    */     }
/*  64:107 */     return this;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void write(int idx)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72:118 */       beforeWrite(1);
/*  73:119 */       this.out.write(idx);
/*  74:120 */       afterWrite(1);
/*  75:    */     }
/*  76:    */     catch (IOException e)
/*  77:    */     {
/*  78:122 */       handleIOException(e);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void write(char[] chr)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:134 */       int len = 0;
/*  88:135 */       if (chr != null) {
/*  89:136 */         len = chr.length;
/*  90:    */       }
/*  91:139 */       beforeWrite(len);
/*  92:140 */       this.out.write(chr);
/*  93:141 */       afterWrite(len);
/*  94:    */     }
/*  95:    */     catch (IOException e)
/*  96:    */     {
/*  97:143 */       handleIOException(e);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void write(char[] chr, int st, int len)
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:157 */       beforeWrite(len);
/* 107:158 */       this.out.write(chr, st, len);
/* 108:159 */       afterWrite(len);
/* 109:    */     }
/* 110:    */     catch (IOException e)
/* 111:    */     {
/* 112:161 */       handleIOException(e);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void write(String str)
/* 117:    */     throws IOException
/* 118:    */   {
/* 119:    */     try
/* 120:    */     {
/* 121:173 */       int len = 0;
/* 122:174 */       if (str != null) {
/* 123:175 */         len = str.length();
/* 124:    */       }
/* 125:178 */       beforeWrite(len);
/* 126:179 */       this.out.write(str);
/* 127:180 */       afterWrite(len);
/* 128:    */     }
/* 129:    */     catch (IOException e)
/* 130:    */     {
/* 131:182 */       handleIOException(e);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void write(String str, int st, int len)
/* 136:    */     throws IOException
/* 137:    */   {
/* 138:    */     try
/* 139:    */     {
/* 140:196 */       beforeWrite(len);
/* 141:197 */       this.out.write(str, st, len);
/* 142:198 */       afterWrite(len);
/* 143:    */     }
/* 144:    */     catch (IOException e)
/* 145:    */     {
/* 146:200 */       handleIOException(e);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void flush()
/* 151:    */     throws IOException
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:211 */       this.out.flush();
/* 156:    */     }
/* 157:    */     catch (IOException e)
/* 158:    */     {
/* 159:213 */       handleIOException(e);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void close()
/* 164:    */     throws IOException
/* 165:    */   {
/* 166:    */     try
/* 167:    */     {
/* 168:224 */       this.out.close();
/* 169:    */     }
/* 170:    */     catch (IOException e)
/* 171:    */     {
/* 172:226 */       handleIOException(e);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   protected void beforeWrite(int n)
/* 177:    */     throws IOException
/* 178:    */   {}
/* 179:    */   
/* 180:    */   protected void afterWrite(int n)
/* 181:    */     throws IOException
/* 182:    */   {}
/* 183:    */   
/* 184:    */   protected void handleIOException(IOException e)
/* 185:    */     throws IOException
/* 186:    */   {
/* 187:273 */     throw e;
/* 188:    */   }
/* 189:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.ProxyWriter
 * JD-Core Version:    0.7.0.1
 */