/*   1:    */ package org.apache.james.mime4j.field.structured.parser;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.io.UnsupportedEncodingException;
/*   6:    */ import java.util.Vector;
/*   7:    */ 
/*   8:    */ public class StructuredFieldParser
/*   9:    */   implements StructuredFieldParserConstants
/*  10:    */ {
/*  11: 28 */   private boolean preserveFolding = false;
/*  12:    */   public StructuredFieldParserTokenManager token_source;
/*  13:    */   SimpleCharStream jj_input_stream;
/*  14:    */   public Token token;
/*  15:    */   public Token jj_nt;
/*  16:    */   private int jj_ntk;
/*  17:    */   private int jj_gen;
/*  18:    */   
/*  19:    */   public boolean isFoldingPreserved()
/*  20:    */   {
/*  21: 34 */     return this.preserveFolding;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setFoldingPreserved(boolean preserveFolding)
/*  25:    */   {
/*  26: 41 */     this.preserveFolding = preserveFolding;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String parse()
/*  30:    */     throws ParseException
/*  31:    */   {
/*  32:    */     try
/*  33:    */     {
/*  34: 50 */       return doParse();
/*  35:    */     }
/*  36:    */     catch (TokenMgrError e)
/*  37:    */     {
/*  38: 55 */       throw new ParseException(e);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   private final String doParse()
/*  43:    */     throws ParseException
/*  44:    */   {
/*  45: 61 */     StringBuffer buffer = new StringBuffer(50);
/*  46: 62 */     boolean whitespace = false;
/*  47: 63 */     boolean first = true;
/*  48:    */     for (;;)
/*  49:    */     {
/*  50: 66 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  51:    */       {
/*  52:    */       case 11: 
/*  53:    */       case 12: 
/*  54:    */       case 13: 
/*  55:    */       case 14: 
/*  56:    */       case 15: 
/*  57:    */         break;
/*  58:    */       default: 
/*  59: 75 */         this.jj_la1[0] = this.jj_gen;
/*  60: 76 */         break;
/*  61:    */       }
/*  62:    */       Token t;
/*  63: 78 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  64:    */       {
/*  65:    */       case 15: 
/*  66: 80 */         t = jj_consume_token(15);
/*  67: 81 */         if (first)
/*  68:    */         {
/*  69: 82 */           first = false;
/*  70:    */         }
/*  71: 83 */         else if (whitespace)
/*  72:    */         {
/*  73: 84 */           buffer.append(" ");
/*  74: 85 */           whitespace = false;
/*  75:    */         }
/*  76: 87 */         buffer.append(t.image);
/*  77: 88 */         break;
/*  78:    */       case 11: 
/*  79: 90 */         t = jj_consume_token(11);
/*  80: 91 */         buffer.append(t.image);
/*  81: 92 */         break;
/*  82:    */       case 13: 
/*  83: 94 */         t = jj_consume_token(13);
/*  84: 95 */         if (first)
/*  85:    */         {
/*  86: 96 */           first = false;
/*  87:    */         }
/*  88: 97 */         else if (whitespace)
/*  89:    */         {
/*  90: 98 */           buffer.append(" ");
/*  91: 99 */           whitespace = false;
/*  92:    */         }
/*  93:101 */         buffer.append(t.image);
/*  94:102 */         break;
/*  95:    */       case 12: 
/*  96:104 */         t = jj_consume_token(12);
/*  97:105 */         if (this.preserveFolding) {
/*  98:105 */           buffer.append("\r\n");
/*  99:    */         }
/* 100:    */         break;
/* 101:    */       case 14: 
/* 102:108 */         t = jj_consume_token(14);
/* 103:109 */         whitespace = true;
/* 104:    */       }
/* 105:    */     }
/* 106:112 */     this.jj_la1[1] = this.jj_gen;
/* 107:113 */     jj_consume_token(-1);
/* 108:114 */     throw new ParseException();
/* 109:    */     
/* 110:    */ 
/* 111:117 */     return buffer.toString();
/* 112:    */   }
/* 113:    */   
/* 114:126 */   private final int[] jj_la1 = new int[2];
/* 115:    */   private static int[] jj_la1_0;
/* 116:    */   
/* 117:    */   private static void jj_la1_0()
/* 118:    */   {
/* 119:132 */     jj_la1_0 = new int[] { 63488, 63488 };
/* 120:    */   }
/* 121:    */   
/* 122:    */   public StructuredFieldParser(InputStream stream)
/* 123:    */   {
/* 124:136 */     this(stream, null);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public StructuredFieldParser(InputStream stream, String encoding)
/* 128:    */   {
/* 129:    */     try
/* 130:    */     {
/* 131:139 */       this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
/* 132:    */     }
/* 133:    */     catch (UnsupportedEncodingException e)
/* 134:    */     {
/* 135:139 */       throw new RuntimeException(e);
/* 136:    */     }
/* 137:140 */     this.token_source = new StructuredFieldParserTokenManager(this.jj_input_stream);
/* 138:141 */     this.token = new Token();
/* 139:142 */     this.jj_ntk = -1;
/* 140:143 */     this.jj_gen = 0;
/* 141:144 */     for (int i = 0; i < 2; i++) {
/* 142:144 */       this.jj_la1[i] = -1;
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void ReInit(InputStream stream)
/* 147:    */   {
/* 148:148 */     ReInit(stream, null);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void ReInit(InputStream stream, String encoding)
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:151 */       this.jj_input_stream.ReInit(stream, encoding, 1, 1);
/* 156:    */     }
/* 157:    */     catch (UnsupportedEncodingException e)
/* 158:    */     {
/* 159:151 */       throw new RuntimeException(e);
/* 160:    */     }
/* 161:152 */     this.token_source.ReInit(this.jj_input_stream);
/* 162:153 */     this.token = new Token();
/* 163:154 */     this.jj_ntk = -1;
/* 164:155 */     this.jj_gen = 0;
/* 165:156 */     for (int i = 0; i < 2; i++) {
/* 166:156 */       this.jj_la1[i] = -1;
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public StructuredFieldParser(Reader stream)
/* 171:    */   {
/* 172:160 */     this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
/* 173:161 */     this.token_source = new StructuredFieldParserTokenManager(this.jj_input_stream);
/* 174:162 */     this.token = new Token();
/* 175:163 */     this.jj_ntk = -1;
/* 176:164 */     this.jj_gen = 0;
/* 177:165 */     for (int i = 0; i < 2; i++) {
/* 178:165 */       this.jj_la1[i] = -1;
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void ReInit(Reader stream)
/* 183:    */   {
/* 184:169 */     this.jj_input_stream.ReInit(stream, 1, 1);
/* 185:170 */     this.token_source.ReInit(this.jj_input_stream);
/* 186:171 */     this.token = new Token();
/* 187:172 */     this.jj_ntk = -1;
/* 188:173 */     this.jj_gen = 0;
/* 189:174 */     for (int i = 0; i < 2; i++) {
/* 190:174 */       this.jj_la1[i] = -1;
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   public StructuredFieldParser(StructuredFieldParserTokenManager tm)
/* 195:    */   {
/* 196:178 */     this.token_source = tm;
/* 197:179 */     this.token = new Token();
/* 198:180 */     this.jj_ntk = -1;
/* 199:181 */     this.jj_gen = 0;
/* 200:182 */     for (int i = 0; i < 2; i++) {
/* 201:182 */       this.jj_la1[i] = -1;
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void ReInit(StructuredFieldParserTokenManager tm)
/* 206:    */   {
/* 207:186 */     this.token_source = tm;
/* 208:187 */     this.token = new Token();
/* 209:188 */     this.jj_ntk = -1;
/* 210:189 */     this.jj_gen = 0;
/* 211:190 */     for (int i = 0; i < 2; i++) {
/* 212:190 */       this.jj_la1[i] = -1;
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   private final Token jj_consume_token(int kind)
/* 217:    */     throws ParseException
/* 218:    */   {
/* 219:    */     Token oldToken;
/* 220:195 */     if ((oldToken = this.token).next != null) {
/* 221:195 */       this.token = this.token.next;
/* 222:    */     } else {
/* 223:196 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 224:    */     }
/* 225:197 */     this.jj_ntk = -1;
/* 226:198 */     if (this.token.kind == kind)
/* 227:    */     {
/* 228:199 */       this.jj_gen += 1;
/* 229:200 */       return this.token;
/* 230:    */     }
/* 231:202 */     this.token = oldToken;
/* 232:203 */     this.jj_kind = kind;
/* 233:204 */     throw generateParseException();
/* 234:    */   }
/* 235:    */   
/* 236:    */   public final Token getNextToken()
/* 237:    */   {
/* 238:208 */     if (this.token.next != null) {
/* 239:208 */       this.token = this.token.next;
/* 240:    */     } else {
/* 241:209 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 242:    */     }
/* 243:210 */     this.jj_ntk = -1;
/* 244:211 */     this.jj_gen += 1;
/* 245:212 */     return this.token;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public final Token getToken(int index)
/* 249:    */   {
/* 250:216 */     Token t = this.token;
/* 251:217 */     for (int i = 0; i < index; i++) {
/* 252:218 */       if (t.next != null) {
/* 253:218 */         t = t.next;
/* 254:    */       } else {
/* 255:219 */         t = t.next = this.token_source.getNextToken();
/* 256:    */       }
/* 257:    */     }
/* 258:221 */     return t;
/* 259:    */   }
/* 260:    */   
/* 261:    */   private final int jj_ntk()
/* 262:    */   {
/* 263:225 */     if ((this.jj_nt = this.token.next) == null) {
/* 264:226 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* 265:    */     }
/* 266:228 */     return this.jj_ntk = this.jj_nt.kind;
/* 267:    */   }
/* 268:    */   
/* 269:231 */   private Vector<int[]> jj_expentries = new Vector();
/* 270:    */   private int[] jj_expentry;
/* 271:233 */   private int jj_kind = -1;
/* 272:    */   
/* 273:    */   public ParseException generateParseException()
/* 274:    */   {
/* 275:236 */     this.jj_expentries.removeAllElements();
/* 276:237 */     boolean[] la1tokens = new boolean[18];
/* 277:238 */     for (int i = 0; i < 18; i++) {
/* 278:239 */       la1tokens[i] = false;
/* 279:    */     }
/* 280:241 */     if (this.jj_kind >= 0)
/* 281:    */     {
/* 282:242 */       la1tokens[this.jj_kind] = true;
/* 283:243 */       this.jj_kind = -1;
/* 284:    */     }
/* 285:245 */     for (int i = 0; i < 2; i++) {
/* 286:246 */       if (this.jj_la1[i] == this.jj_gen) {
/* 287:247 */         for (int j = 0; j < 32; j++) {
/* 288:248 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 289:249 */             la1tokens[j] = true;
/* 290:    */           }
/* 291:    */         }
/* 292:    */       }
/* 293:    */     }
/* 294:254 */     for (int i = 0; i < 18; i++) {
/* 295:255 */       if (la1tokens[i] != 0)
/* 296:    */       {
/* 297:256 */         this.jj_expentry = new int[1];
/* 298:257 */         this.jj_expentry[0] = i;
/* 299:258 */         this.jj_expentries.addElement(this.jj_expentry);
/* 300:    */       }
/* 301:    */     }
/* 302:261 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 303:262 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 304:263 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* 305:    */     }
/* 306:265 */     return new ParseException(this.token, exptokseq, tokenImage);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public final void enable_tracing() {}
/* 310:    */   
/* 311:    */   public final void disable_tracing() {}
/* 312:    */   
/* 313:    */   static {}
/* 314:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.structured.parser.StructuredFieldParser
 * JD-Core Version:    0.7.0.1
 */