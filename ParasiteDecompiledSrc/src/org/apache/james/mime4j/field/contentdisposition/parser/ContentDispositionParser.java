/*   1:    */ package org.apache.james.mime4j.field.contentdisposition.parser;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.io.UnsupportedEncodingException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Vector;
/*   9:    */ 
/*  10:    */ public class ContentDispositionParser
/*  11:    */   implements ContentDispositionParserConstants
/*  12:    */ {
/*  13:    */   private String dispositionType;
/*  14: 28 */   private List<String> paramNames = new ArrayList();
/*  15: 29 */   private List<String> paramValues = new ArrayList();
/*  16:    */   public ContentDispositionParserTokenManager token_source;
/*  17:    */   SimpleCharStream jj_input_stream;
/*  18:    */   public Token token;
/*  19:    */   public Token jj_nt;
/*  20:    */   private int jj_ntk;
/*  21:    */   private int jj_gen;
/*  22:    */   
/*  23:    */   public String getDispositionType()
/*  24:    */   {
/*  25: 32 */     return this.dispositionType;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List<String> getParamNames()
/*  29:    */   {
/*  30: 36 */     return this.paramNames;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<String> getParamValues()
/*  34:    */   {
/*  35: 40 */     return this.paramValues;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static void main(String[] args)
/*  39:    */     throws ParseException
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43:    */       for (;;)
/*  44:    */       {
/*  45: 46 */         ContentDispositionParser parser = new ContentDispositionParser(System.in);
/*  46:    */         
/*  47: 48 */         parser.parseLine();
/*  48:    */       }
/*  49: 51 */       return;
/*  50:    */     }
/*  51:    */     catch (Exception x)
/*  52:    */     {
/*  53: 50 */       x.printStackTrace();
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public final void parseLine()
/*  58:    */     throws ParseException
/*  59:    */   {
/*  60: 57 */     parse();
/*  61: 58 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  62:    */     {
/*  63:    */     case 1: 
/*  64: 60 */       jj_consume_token(1);
/*  65: 61 */       break;
/*  66:    */     default: 
/*  67: 63 */       this.jj_la1[0] = this.jj_gen;
/*  68:    */     }
/*  69: 66 */     jj_consume_token(2);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public final void parseAll()
/*  73:    */     throws ParseException
/*  74:    */   {
/*  75: 70 */     parse();
/*  76: 71 */     jj_consume_token(0);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public final void parse()
/*  80:    */     throws ParseException
/*  81:    */   {
/*  82: 76 */     Token dispositionType = jj_consume_token(20);
/*  83: 77 */     this.dispositionType = dispositionType.image;
/*  84:    */     for (;;)
/*  85:    */     {
/*  86: 80 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  87:    */       {
/*  88:    */       case 3: 
/*  89:    */         break;
/*  90:    */       default: 
/*  91: 85 */         this.jj_la1[1] = this.jj_gen;
/*  92: 86 */         break;
/*  93:    */       }
/*  94: 88 */       jj_consume_token(3);
/*  95: 89 */       parameter();
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public final void parameter()
/* 100:    */     throws ParseException
/* 101:    */   {
/* 102: 96 */     Token attrib = jj_consume_token(20);
/* 103: 97 */     jj_consume_token(4);
/* 104: 98 */     String val = value();
/* 105: 99 */     this.paramNames.add(attrib.image);
/* 106:100 */     this.paramValues.add(val);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public final String value()
/* 110:    */     throws ParseException
/* 111:    */   {
/* 112:    */     Token t;
/* 113:105 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 114:    */     {
/* 115:    */     case 20: 
/* 116:107 */       t = jj_consume_token(20);
/* 117:108 */       break;
/* 118:    */     case 19: 
/* 119:110 */       t = jj_consume_token(19);
/* 120:111 */       break;
/* 121:    */     case 18: 
/* 122:113 */       t = jj_consume_token(18);
/* 123:114 */       break;
/* 124:    */     default: 
/* 125:116 */       this.jj_la1[2] = this.jj_gen;
/* 126:117 */       jj_consume_token(-1);
/* 127:118 */       throw new ParseException();
/* 128:    */     }
/* 129:120 */     return t.image;
/* 130:    */   }
/* 131:    */   
/* 132:129 */   private final int[] jj_la1 = new int[3];
/* 133:    */   private static int[] jj_la1_0;
/* 134:    */   
/* 135:    */   private static void jj_la1_0()
/* 136:    */   {
/* 137:135 */     jj_la1_0 = new int[] { 2, 8, 1835008 };
/* 138:    */   }
/* 139:    */   
/* 140:    */   public ContentDispositionParser(InputStream stream)
/* 141:    */   {
/* 142:139 */     this(stream, null);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public ContentDispositionParser(InputStream stream, String encoding)
/* 146:    */   {
/* 147:    */     try
/* 148:    */     {
/* 149:142 */       this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
/* 150:    */     }
/* 151:    */     catch (UnsupportedEncodingException e)
/* 152:    */     {
/* 153:142 */       throw new RuntimeException(e);
/* 154:    */     }
/* 155:143 */     this.token_source = new ContentDispositionParserTokenManager(this.jj_input_stream);
/* 156:144 */     this.token = new Token();
/* 157:145 */     this.jj_ntk = -1;
/* 158:146 */     this.jj_gen = 0;
/* 159:147 */     for (int i = 0; i < 3; i++) {
/* 160:147 */       this.jj_la1[i] = -1;
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void ReInit(InputStream stream)
/* 165:    */   {
/* 166:151 */     ReInit(stream, null);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void ReInit(InputStream stream, String encoding)
/* 170:    */   {
/* 171:    */     try
/* 172:    */     {
/* 173:154 */       this.jj_input_stream.ReInit(stream, encoding, 1, 1);
/* 174:    */     }
/* 175:    */     catch (UnsupportedEncodingException e)
/* 176:    */     {
/* 177:154 */       throw new RuntimeException(e);
/* 178:    */     }
/* 179:155 */     this.token_source.ReInit(this.jj_input_stream);
/* 180:156 */     this.token = new Token();
/* 181:157 */     this.jj_ntk = -1;
/* 182:158 */     this.jj_gen = 0;
/* 183:159 */     for (int i = 0; i < 3; i++) {
/* 184:159 */       this.jj_la1[i] = -1;
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public ContentDispositionParser(Reader stream)
/* 189:    */   {
/* 190:163 */     this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
/* 191:164 */     this.token_source = new ContentDispositionParserTokenManager(this.jj_input_stream);
/* 192:165 */     this.token = new Token();
/* 193:166 */     this.jj_ntk = -1;
/* 194:167 */     this.jj_gen = 0;
/* 195:168 */     for (int i = 0; i < 3; i++) {
/* 196:168 */       this.jj_la1[i] = -1;
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void ReInit(Reader stream)
/* 201:    */   {
/* 202:172 */     this.jj_input_stream.ReInit(stream, 1, 1);
/* 203:173 */     this.token_source.ReInit(this.jj_input_stream);
/* 204:174 */     this.token = new Token();
/* 205:175 */     this.jj_ntk = -1;
/* 206:176 */     this.jj_gen = 0;
/* 207:177 */     for (int i = 0; i < 3; i++) {
/* 208:177 */       this.jj_la1[i] = -1;
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public ContentDispositionParser(ContentDispositionParserTokenManager tm)
/* 213:    */   {
/* 214:181 */     this.token_source = tm;
/* 215:182 */     this.token = new Token();
/* 216:183 */     this.jj_ntk = -1;
/* 217:184 */     this.jj_gen = 0;
/* 218:185 */     for (int i = 0; i < 3; i++) {
/* 219:185 */       this.jj_la1[i] = -1;
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void ReInit(ContentDispositionParserTokenManager tm)
/* 224:    */   {
/* 225:189 */     this.token_source = tm;
/* 226:190 */     this.token = new Token();
/* 227:191 */     this.jj_ntk = -1;
/* 228:192 */     this.jj_gen = 0;
/* 229:193 */     for (int i = 0; i < 3; i++) {
/* 230:193 */       this.jj_la1[i] = -1;
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   private final Token jj_consume_token(int kind)
/* 235:    */     throws ParseException
/* 236:    */   {
/* 237:    */     Token oldToken;
/* 238:198 */     if ((oldToken = this.token).next != null) {
/* 239:198 */       this.token = this.token.next;
/* 240:    */     } else {
/* 241:199 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 242:    */     }
/* 243:200 */     this.jj_ntk = -1;
/* 244:201 */     if (this.token.kind == kind)
/* 245:    */     {
/* 246:202 */       this.jj_gen += 1;
/* 247:203 */       return this.token;
/* 248:    */     }
/* 249:205 */     this.token = oldToken;
/* 250:206 */     this.jj_kind = kind;
/* 251:207 */     throw generateParseException();
/* 252:    */   }
/* 253:    */   
/* 254:    */   public final Token getNextToken()
/* 255:    */   {
/* 256:211 */     if (this.token.next != null) {
/* 257:211 */       this.token = this.token.next;
/* 258:    */     } else {
/* 259:212 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 260:    */     }
/* 261:213 */     this.jj_ntk = -1;
/* 262:214 */     this.jj_gen += 1;
/* 263:215 */     return this.token;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public final Token getToken(int index)
/* 267:    */   {
/* 268:219 */     Token t = this.token;
/* 269:220 */     for (int i = 0; i < index; i++) {
/* 270:221 */       if (t.next != null) {
/* 271:221 */         t = t.next;
/* 272:    */       } else {
/* 273:222 */         t = t.next = this.token_source.getNextToken();
/* 274:    */       }
/* 275:    */     }
/* 276:224 */     return t;
/* 277:    */   }
/* 278:    */   
/* 279:    */   private final int jj_ntk()
/* 280:    */   {
/* 281:228 */     if ((this.jj_nt = this.token.next) == null) {
/* 282:229 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* 283:    */     }
/* 284:231 */     return this.jj_ntk = this.jj_nt.kind;
/* 285:    */   }
/* 286:    */   
/* 287:234 */   private Vector<int[]> jj_expentries = new Vector();
/* 288:    */   private int[] jj_expentry;
/* 289:236 */   private int jj_kind = -1;
/* 290:    */   
/* 291:    */   public ParseException generateParseException()
/* 292:    */   {
/* 293:239 */     this.jj_expentries.removeAllElements();
/* 294:240 */     boolean[] la1tokens = new boolean[23];
/* 295:241 */     for (int i = 0; i < 23; i++) {
/* 296:242 */       la1tokens[i] = false;
/* 297:    */     }
/* 298:244 */     if (this.jj_kind >= 0)
/* 299:    */     {
/* 300:245 */       la1tokens[this.jj_kind] = true;
/* 301:246 */       this.jj_kind = -1;
/* 302:    */     }
/* 303:248 */     for (int i = 0; i < 3; i++) {
/* 304:249 */       if (this.jj_la1[i] == this.jj_gen) {
/* 305:250 */         for (int j = 0; j < 32; j++) {
/* 306:251 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 307:252 */             la1tokens[j] = true;
/* 308:    */           }
/* 309:    */         }
/* 310:    */       }
/* 311:    */     }
/* 312:257 */     for (int i = 0; i < 23; i++) {
/* 313:258 */       if (la1tokens[i] != 0)
/* 314:    */       {
/* 315:259 */         this.jj_expentry = new int[1];
/* 316:260 */         this.jj_expentry[0] = i;
/* 317:261 */         this.jj_expentries.addElement(this.jj_expentry);
/* 318:    */       }
/* 319:    */     }
/* 320:264 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 321:265 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 322:266 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* 323:    */     }
/* 324:268 */     return new ParseException(this.token, exptokseq, tokenImage);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public final void enable_tracing() {}
/* 328:    */   
/* 329:    */   public final void disable_tracing() {}
/* 330:    */   
/* 331:    */   static {}
/* 332:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.contentdisposition.parser.ContentDispositionParser
 * JD-Core Version:    0.7.0.1
 */