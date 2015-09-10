/*   1:    */ package org.apache.james.mime4j.field.language.parser;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.io.UnsupportedEncodingException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Vector;
/*   9:    */ 
/*  10:    */ public class ContentLanguageParser
/*  11:    */   implements ContentLanguageParserConstants
/*  12:    */ {
/*  13: 26 */   private List<String> languages = new ArrayList();
/*  14:    */   public ContentLanguageParserTokenManager token_source;
/*  15:    */   SimpleCharStream jj_input_stream;
/*  16:    */   public Token token;
/*  17:    */   public Token jj_nt;
/*  18:    */   private int jj_ntk;
/*  19:    */   private int jj_gen;
/*  20:    */   
/*  21:    */   public List<String> parse()
/*  22:    */     throws ParseException
/*  23:    */   {
/*  24:    */     try
/*  25:    */     {
/*  26: 34 */       return doParse();
/*  27:    */     }
/*  28:    */     catch (TokenMgrError e)
/*  29:    */     {
/*  30: 39 */       throw new ParseException(e);
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   private final List<String> doParse()
/*  35:    */     throws ParseException
/*  36:    */   {
/*  37: 44 */     language();
/*  38:    */     for (;;)
/*  39:    */     {
/*  40: 47 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  41:    */       {
/*  42:    */       case 1: 
/*  43:    */         break;
/*  44:    */       default: 
/*  45: 52 */         this.jj_la1[0] = this.jj_gen;
/*  46: 53 */         break;
/*  47:    */       }
/*  48: 55 */       jj_consume_token(1);
/*  49: 56 */       language();
/*  50:    */     }
/*  51: 58 */     return this.languages;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final String language()
/*  55:    */     throws ParseException
/*  56:    */   {
/*  57: 64 */     StringBuffer languageTag = new StringBuffer();
/*  58:    */     
/*  59: 66 */     Token token = jj_consume_token(18);
/*  60: 67 */     languageTag.append(token.image);
/*  61:    */     for (;;)
/*  62:    */     {
/*  63: 70 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  64:    */       {
/*  65:    */       case 2: 
/*  66:    */       case 19: 
/*  67:    */         break;
/*  68:    */       default: 
/*  69: 76 */         this.jj_la1[1] = this.jj_gen;
/*  70: 77 */         break;
/*  71:    */       }
/*  72: 79 */       switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  73:    */       {
/*  74:    */       case 2: 
/*  75: 81 */         jj_consume_token(2);
/*  76:    */         
/*  77: 83 */         token = jj_consume_token(18);
/*  78: 84 */         languageTag.append('-');
/*  79: 85 */         languageTag.append(token.image);
/*  80: 86 */         break;
/*  81:    */       case 19: 
/*  82: 88 */         token = jj_consume_token(19);
/*  83: 89 */         languageTag.append('-');
/*  84: 90 */         languageTag.append(token.image);
/*  85:    */       }
/*  86:    */     }
/*  87: 93 */     this.jj_la1[2] = this.jj_gen;
/*  88: 94 */     jj_consume_token(-1);
/*  89: 95 */     throw new ParseException();
/*  90:    */     
/*  91:    */ 
/*  92: 98 */     String result = languageTag.toString();
/*  93: 99 */     this.languages.add(result);
/*  94:100 */     return result;
/*  95:    */   }
/*  96:    */   
/*  97:109 */   private final int[] jj_la1 = new int[3];
/*  98:    */   private static int[] jj_la1_0;
/*  99:    */   
/* 100:    */   private static void jj_la1_0()
/* 101:    */   {
/* 102:115 */     jj_la1_0 = new int[] { 2, 524292, 524292 };
/* 103:    */   }
/* 104:    */   
/* 105:    */   public ContentLanguageParser(InputStream stream)
/* 106:    */   {
/* 107:119 */     this(stream, null);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public ContentLanguageParser(InputStream stream, String encoding)
/* 111:    */   {
/* 112:    */     try
/* 113:    */     {
/* 114:122 */       this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
/* 115:    */     }
/* 116:    */     catch (UnsupportedEncodingException e)
/* 117:    */     {
/* 118:122 */       throw new RuntimeException(e);
/* 119:    */     }
/* 120:123 */     this.token_source = new ContentLanguageParserTokenManager(this.jj_input_stream);
/* 121:124 */     this.token = new Token();
/* 122:125 */     this.jj_ntk = -1;
/* 123:126 */     this.jj_gen = 0;
/* 124:127 */     for (int i = 0; i < 3; i++) {
/* 125:127 */       this.jj_la1[i] = -1;
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void ReInit(InputStream stream)
/* 130:    */   {
/* 131:131 */     ReInit(stream, null);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void ReInit(InputStream stream, String encoding)
/* 135:    */   {
/* 136:    */     try
/* 137:    */     {
/* 138:134 */       this.jj_input_stream.ReInit(stream, encoding, 1, 1);
/* 139:    */     }
/* 140:    */     catch (UnsupportedEncodingException e)
/* 141:    */     {
/* 142:134 */       throw new RuntimeException(e);
/* 143:    */     }
/* 144:135 */     this.token_source.ReInit(this.jj_input_stream);
/* 145:136 */     this.token = new Token();
/* 146:137 */     this.jj_ntk = -1;
/* 147:138 */     this.jj_gen = 0;
/* 148:139 */     for (int i = 0; i < 3; i++) {
/* 149:139 */       this.jj_la1[i] = -1;
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public ContentLanguageParser(Reader stream)
/* 154:    */   {
/* 155:143 */     this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
/* 156:144 */     this.token_source = new ContentLanguageParserTokenManager(this.jj_input_stream);
/* 157:145 */     this.token = new Token();
/* 158:146 */     this.jj_ntk = -1;
/* 159:147 */     this.jj_gen = 0;
/* 160:148 */     for (int i = 0; i < 3; i++) {
/* 161:148 */       this.jj_la1[i] = -1;
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void ReInit(Reader stream)
/* 166:    */   {
/* 167:152 */     this.jj_input_stream.ReInit(stream, 1, 1);
/* 168:153 */     this.token_source.ReInit(this.jj_input_stream);
/* 169:154 */     this.token = new Token();
/* 170:155 */     this.jj_ntk = -1;
/* 171:156 */     this.jj_gen = 0;
/* 172:157 */     for (int i = 0; i < 3; i++) {
/* 173:157 */       this.jj_la1[i] = -1;
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public ContentLanguageParser(ContentLanguageParserTokenManager tm)
/* 178:    */   {
/* 179:161 */     this.token_source = tm;
/* 180:162 */     this.token = new Token();
/* 181:163 */     this.jj_ntk = -1;
/* 182:164 */     this.jj_gen = 0;
/* 183:165 */     for (int i = 0; i < 3; i++) {
/* 184:165 */       this.jj_la1[i] = -1;
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void ReInit(ContentLanguageParserTokenManager tm)
/* 189:    */   {
/* 190:169 */     this.token_source = tm;
/* 191:170 */     this.token = new Token();
/* 192:171 */     this.jj_ntk = -1;
/* 193:172 */     this.jj_gen = 0;
/* 194:173 */     for (int i = 0; i < 3; i++) {
/* 195:173 */       this.jj_la1[i] = -1;
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   private final Token jj_consume_token(int kind)
/* 200:    */     throws ParseException
/* 201:    */   {
/* 202:    */     Token oldToken;
/* 203:178 */     if ((oldToken = this.token).next != null) {
/* 204:178 */       this.token = this.token.next;
/* 205:    */     } else {
/* 206:179 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 207:    */     }
/* 208:180 */     this.jj_ntk = -1;
/* 209:181 */     if (this.token.kind == kind)
/* 210:    */     {
/* 211:182 */       this.jj_gen += 1;
/* 212:183 */       return this.token;
/* 213:    */     }
/* 214:185 */     this.token = oldToken;
/* 215:186 */     this.jj_kind = kind;
/* 216:187 */     throw generateParseException();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public final Token getNextToken()
/* 220:    */   {
/* 221:191 */     if (this.token.next != null) {
/* 222:191 */       this.token = this.token.next;
/* 223:    */     } else {
/* 224:192 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 225:    */     }
/* 226:193 */     this.jj_ntk = -1;
/* 227:194 */     this.jj_gen += 1;
/* 228:195 */     return this.token;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public final Token getToken(int index)
/* 232:    */   {
/* 233:199 */     Token t = this.token;
/* 234:200 */     for (int i = 0; i < index; i++) {
/* 235:201 */       if (t.next != null) {
/* 236:201 */         t = t.next;
/* 237:    */       } else {
/* 238:202 */         t = t.next = this.token_source.getNextToken();
/* 239:    */       }
/* 240:    */     }
/* 241:204 */     return t;
/* 242:    */   }
/* 243:    */   
/* 244:    */   private final int jj_ntk()
/* 245:    */   {
/* 246:208 */     if ((this.jj_nt = this.token.next) == null) {
/* 247:209 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* 248:    */     }
/* 249:211 */     return this.jj_ntk = this.jj_nt.kind;
/* 250:    */   }
/* 251:    */   
/* 252:214 */   private Vector<int[]> jj_expentries = new Vector();
/* 253:    */   private int[] jj_expentry;
/* 254:216 */   private int jj_kind = -1;
/* 255:    */   
/* 256:    */   public ParseException generateParseException()
/* 257:    */   {
/* 258:219 */     this.jj_expentries.removeAllElements();
/* 259:220 */     boolean[] la1tokens = new boolean[23];
/* 260:221 */     for (int i = 0; i < 23; i++) {
/* 261:222 */       la1tokens[i] = false;
/* 262:    */     }
/* 263:224 */     if (this.jj_kind >= 0)
/* 264:    */     {
/* 265:225 */       la1tokens[this.jj_kind] = true;
/* 266:226 */       this.jj_kind = -1;
/* 267:    */     }
/* 268:228 */     for (int i = 0; i < 3; i++) {
/* 269:229 */       if (this.jj_la1[i] == this.jj_gen) {
/* 270:230 */         for (int j = 0; j < 32; j++) {
/* 271:231 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 272:232 */             la1tokens[j] = true;
/* 273:    */           }
/* 274:    */         }
/* 275:    */       }
/* 276:    */     }
/* 277:237 */     for (int i = 0; i < 23; i++) {
/* 278:238 */       if (la1tokens[i] != 0)
/* 279:    */       {
/* 280:239 */         this.jj_expentry = new int[1];
/* 281:240 */         this.jj_expentry[0] = i;
/* 282:241 */         this.jj_expentries.addElement(this.jj_expentry);
/* 283:    */       }
/* 284:    */     }
/* 285:244 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 286:245 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 287:246 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* 288:    */     }
/* 289:248 */     return new ParseException(this.token, exptokseq, tokenImage);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public final void enable_tracing() {}
/* 293:    */   
/* 294:    */   public final void disable_tracing() {}
/* 295:    */   
/* 296:    */   static {}
/* 297:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.language.parser.ContentLanguageParser
 * JD-Core Version:    0.7.0.1
 */