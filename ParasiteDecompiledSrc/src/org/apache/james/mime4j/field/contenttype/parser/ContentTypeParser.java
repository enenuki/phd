/*   1:    */ package org.apache.james.mime4j.field.contenttype.parser;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.io.UnsupportedEncodingException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Vector;
/*   9:    */ 
/*  10:    */ public class ContentTypeParser
/*  11:    */   implements ContentTypeParserConstants
/*  12:    */ {
/*  13:    */   private String type;
/*  14:    */   private String subtype;
/*  15: 29 */   private List<String> paramNames = new ArrayList();
/*  16: 30 */   private List<String> paramValues = new ArrayList();
/*  17:    */   public ContentTypeParserTokenManager token_source;
/*  18:    */   SimpleCharStream jj_input_stream;
/*  19:    */   public Token token;
/*  20:    */   public Token jj_nt;
/*  21:    */   private int jj_ntk;
/*  22:    */   private int jj_gen;
/*  23:    */   
/*  24:    */   public String getType()
/*  25:    */   {
/*  26: 32 */     return this.type;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getSubType()
/*  30:    */   {
/*  31: 33 */     return this.subtype;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<String> getParamNames()
/*  35:    */   {
/*  36: 34 */     return this.paramNames;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<String> getParamValues()
/*  40:    */   {
/*  41: 35 */     return this.paramValues;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void main(String[] args)
/*  45:    */     throws ParseException
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49:    */       for (;;)
/*  50:    */       {
/*  51: 40 */         ContentTypeParser parser = new ContentTypeParser(System.in);
/*  52: 41 */         parser.parseLine();
/*  53:    */       }
/*  54: 44 */       return;
/*  55:    */     }
/*  56:    */     catch (Exception x)
/*  57:    */     {
/*  58: 43 */       x.printStackTrace();
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final void parseLine()
/*  63:    */     throws ParseException
/*  64:    */   {
/*  65: 50 */     parse();
/*  66: 51 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  67:    */     {
/*  68:    */     case 1: 
/*  69: 53 */       jj_consume_token(1);
/*  70: 54 */       break;
/*  71:    */     default: 
/*  72: 56 */       this.jj_la1[0] = this.jj_gen;
/*  73:    */     }
/*  74: 59 */     jj_consume_token(2);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public final void parseAll()
/*  78:    */     throws ParseException
/*  79:    */   {
/*  80: 63 */     parse();
/*  81: 64 */     jj_consume_token(0);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public final void parse()
/*  85:    */     throws ParseException
/*  86:    */   {
/*  87: 70 */     Token type = jj_consume_token(21);
/*  88: 71 */     jj_consume_token(3);
/*  89: 72 */     Token subtype = jj_consume_token(21);
/*  90: 73 */     this.type = type.image;
/*  91: 74 */     this.subtype = subtype.image;
/*  92:    */     for (;;)
/*  93:    */     {
/*  94: 77 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  95:    */       {
/*  96:    */       case 4: 
/*  97:    */         break;
/*  98:    */       default: 
/*  99: 82 */         this.jj_la1[1] = this.jj_gen;
/* 100: 83 */         break;
/* 101:    */       }
/* 102: 85 */       jj_consume_token(4);
/* 103: 86 */       parameter();
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final void parameter()
/* 108:    */     throws ParseException
/* 109:    */   {
/* 110: 93 */     Token attrib = jj_consume_token(21);
/* 111: 94 */     jj_consume_token(5);
/* 112: 95 */     String val = value();
/* 113: 96 */     this.paramNames.add(attrib.image);
/* 114: 97 */     this.paramValues.add(val);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public final String value()
/* 118:    */     throws ParseException
/* 119:    */   {
/* 120:    */     Token t;
/* 121:102 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 122:    */     {
/* 123:    */     case 21: 
/* 124:104 */       t = jj_consume_token(21);
/* 125:105 */       break;
/* 126:    */     case 20: 
/* 127:107 */       t = jj_consume_token(20);
/* 128:108 */       break;
/* 129:    */     case 19: 
/* 130:110 */       t = jj_consume_token(19);
/* 131:111 */       break;
/* 132:    */     default: 
/* 133:113 */       this.jj_la1[2] = this.jj_gen;
/* 134:114 */       jj_consume_token(-1);
/* 135:115 */       throw new ParseException();
/* 136:    */     }
/* 137:117 */     return t.image;
/* 138:    */   }
/* 139:    */   
/* 140:126 */   private final int[] jj_la1 = new int[3];
/* 141:    */   private static int[] jj_la1_0;
/* 142:    */   
/* 143:    */   private static void jj_la1_0()
/* 144:    */   {
/* 145:132 */     jj_la1_0 = new int[] { 2, 16, 3670016 };
/* 146:    */   }
/* 147:    */   
/* 148:    */   public ContentTypeParser(InputStream stream)
/* 149:    */   {
/* 150:136 */     this(stream, null);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public ContentTypeParser(InputStream stream, String encoding)
/* 154:    */   {
/* 155:    */     try
/* 156:    */     {
/* 157:139 */       this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
/* 158:    */     }
/* 159:    */     catch (UnsupportedEncodingException e)
/* 160:    */     {
/* 161:139 */       throw new RuntimeException(e);
/* 162:    */     }
/* 163:140 */     this.token_source = new ContentTypeParserTokenManager(this.jj_input_stream);
/* 164:141 */     this.token = new Token();
/* 165:142 */     this.jj_ntk = -1;
/* 166:143 */     this.jj_gen = 0;
/* 167:144 */     for (int i = 0; i < 3; i++) {
/* 168:144 */       this.jj_la1[i] = -1;
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void ReInit(InputStream stream)
/* 173:    */   {
/* 174:148 */     ReInit(stream, null);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void ReInit(InputStream stream, String encoding)
/* 178:    */   {
/* 179:    */     try
/* 180:    */     {
/* 181:151 */       this.jj_input_stream.ReInit(stream, encoding, 1, 1);
/* 182:    */     }
/* 183:    */     catch (UnsupportedEncodingException e)
/* 184:    */     {
/* 185:151 */       throw new RuntimeException(e);
/* 186:    */     }
/* 187:152 */     this.token_source.ReInit(this.jj_input_stream);
/* 188:153 */     this.token = new Token();
/* 189:154 */     this.jj_ntk = -1;
/* 190:155 */     this.jj_gen = 0;
/* 191:156 */     for (int i = 0; i < 3; i++) {
/* 192:156 */       this.jj_la1[i] = -1;
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public ContentTypeParser(Reader stream)
/* 197:    */   {
/* 198:160 */     this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
/* 199:161 */     this.token_source = new ContentTypeParserTokenManager(this.jj_input_stream);
/* 200:162 */     this.token = new Token();
/* 201:163 */     this.jj_ntk = -1;
/* 202:164 */     this.jj_gen = 0;
/* 203:165 */     for (int i = 0; i < 3; i++) {
/* 204:165 */       this.jj_la1[i] = -1;
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void ReInit(Reader stream)
/* 209:    */   {
/* 210:169 */     this.jj_input_stream.ReInit(stream, 1, 1);
/* 211:170 */     this.token_source.ReInit(this.jj_input_stream);
/* 212:171 */     this.token = new Token();
/* 213:172 */     this.jj_ntk = -1;
/* 214:173 */     this.jj_gen = 0;
/* 215:174 */     for (int i = 0; i < 3; i++) {
/* 216:174 */       this.jj_la1[i] = -1;
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public ContentTypeParser(ContentTypeParserTokenManager tm)
/* 221:    */   {
/* 222:178 */     this.token_source = tm;
/* 223:179 */     this.token = new Token();
/* 224:180 */     this.jj_ntk = -1;
/* 225:181 */     this.jj_gen = 0;
/* 226:182 */     for (int i = 0; i < 3; i++) {
/* 227:182 */       this.jj_la1[i] = -1;
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void ReInit(ContentTypeParserTokenManager tm)
/* 232:    */   {
/* 233:186 */     this.token_source = tm;
/* 234:187 */     this.token = new Token();
/* 235:188 */     this.jj_ntk = -1;
/* 236:189 */     this.jj_gen = 0;
/* 237:190 */     for (int i = 0; i < 3; i++) {
/* 238:190 */       this.jj_la1[i] = -1;
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   private final Token jj_consume_token(int kind)
/* 243:    */     throws ParseException
/* 244:    */   {
/* 245:    */     Token oldToken;
/* 246:195 */     if ((oldToken = this.token).next != null) {
/* 247:195 */       this.token = this.token.next;
/* 248:    */     } else {
/* 249:196 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 250:    */     }
/* 251:197 */     this.jj_ntk = -1;
/* 252:198 */     if (this.token.kind == kind)
/* 253:    */     {
/* 254:199 */       this.jj_gen += 1;
/* 255:200 */       return this.token;
/* 256:    */     }
/* 257:202 */     this.token = oldToken;
/* 258:203 */     this.jj_kind = kind;
/* 259:204 */     throw generateParseException();
/* 260:    */   }
/* 261:    */   
/* 262:    */   public final Token getNextToken()
/* 263:    */   {
/* 264:208 */     if (this.token.next != null) {
/* 265:208 */       this.token = this.token.next;
/* 266:    */     } else {
/* 267:209 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 268:    */     }
/* 269:210 */     this.jj_ntk = -1;
/* 270:211 */     this.jj_gen += 1;
/* 271:212 */     return this.token;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public final Token getToken(int index)
/* 275:    */   {
/* 276:216 */     Token t = this.token;
/* 277:217 */     for (int i = 0; i < index; i++) {
/* 278:218 */       if (t.next != null) {
/* 279:218 */         t = t.next;
/* 280:    */       } else {
/* 281:219 */         t = t.next = this.token_source.getNextToken();
/* 282:    */       }
/* 283:    */     }
/* 284:221 */     return t;
/* 285:    */   }
/* 286:    */   
/* 287:    */   private final int jj_ntk()
/* 288:    */   {
/* 289:225 */     if ((this.jj_nt = this.token.next) == null) {
/* 290:226 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* 291:    */     }
/* 292:228 */     return this.jj_ntk = this.jj_nt.kind;
/* 293:    */   }
/* 294:    */   
/* 295:231 */   private Vector<int[]> jj_expentries = new Vector();
/* 296:    */   private int[] jj_expentry;
/* 297:233 */   private int jj_kind = -1;
/* 298:    */   
/* 299:    */   public ParseException generateParseException()
/* 300:    */   {
/* 301:236 */     this.jj_expentries.removeAllElements();
/* 302:237 */     boolean[] la1tokens = new boolean[24];
/* 303:238 */     for (int i = 0; i < 24; i++) {
/* 304:239 */       la1tokens[i] = false;
/* 305:    */     }
/* 306:241 */     if (this.jj_kind >= 0)
/* 307:    */     {
/* 308:242 */       la1tokens[this.jj_kind] = true;
/* 309:243 */       this.jj_kind = -1;
/* 310:    */     }
/* 311:245 */     for (int i = 0; i < 3; i++) {
/* 312:246 */       if (this.jj_la1[i] == this.jj_gen) {
/* 313:247 */         for (int j = 0; j < 32; j++) {
/* 314:248 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 315:249 */             la1tokens[j] = true;
/* 316:    */           }
/* 317:    */         }
/* 318:    */       }
/* 319:    */     }
/* 320:254 */     for (int i = 0; i < 24; i++) {
/* 321:255 */       if (la1tokens[i] != 0)
/* 322:    */       {
/* 323:256 */         this.jj_expentry = new int[1];
/* 324:257 */         this.jj_expentry[0] = i;
/* 325:258 */         this.jj_expentries.addElement(this.jj_expentry);
/* 326:    */       }
/* 327:    */     }
/* 328:261 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 329:262 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 330:263 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* 331:    */     }
/* 332:265 */     return new ParseException(this.token, exptokseq, tokenImage);
/* 333:    */   }
/* 334:    */   
/* 335:    */   public final void enable_tracing() {}
/* 336:    */   
/* 337:    */   public final void disable_tracing() {}
/* 338:    */   
/* 339:    */   static {}
/* 340:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.contenttype.parser.ContentTypeParser
 * JD-Core Version:    0.7.0.1
 */