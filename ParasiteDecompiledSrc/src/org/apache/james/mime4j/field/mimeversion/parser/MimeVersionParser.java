/*   1:    */ package org.apache.james.mime4j.field.mimeversion.parser;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.io.UnsupportedEncodingException;
/*   6:    */ import java.util.Vector;
/*   7:    */ 
/*   8:    */ public class MimeVersionParser
/*   9:    */   implements MimeVersionParserConstants
/*  10:    */ {
/*  11:    */   public static final int INITIAL_VERSION_VALUE = -1;
/*  12: 24 */   private int major = -1;
/*  13: 25 */   private int minor = -1;
/*  14:    */   public MimeVersionParserTokenManager token_source;
/*  15:    */   SimpleCharStream jj_input_stream;
/*  16:    */   public Token token;
/*  17:    */   public Token jj_nt;
/*  18:    */   private int jj_ntk;
/*  19:    */   private int jj_gen;
/*  20:    */   
/*  21:    */   public int getMinorVersion()
/*  22:    */   {
/*  23: 28 */     return this.minor;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getMajorVersion()
/*  27:    */   {
/*  28: 32 */     return this.major;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public final void parseLine()
/*  32:    */     throws ParseException
/*  33:    */   {
/*  34: 36 */     parse();
/*  35: 37 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/*  36:    */     {
/*  37:    */     case 1: 
/*  38: 39 */       jj_consume_token(1);
/*  39: 40 */       break;
/*  40:    */     default: 
/*  41: 42 */       this.jj_la1[0] = this.jj_gen;
/*  42:    */     }
/*  43: 45 */     jj_consume_token(2);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public final void parseAll()
/*  47:    */     throws ParseException
/*  48:    */   {
/*  49: 49 */     parse();
/*  50: 50 */     jj_consume_token(0);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final void parse()
/*  54:    */     throws ParseException
/*  55:    */   {
/*  56: 56 */     Token major = jj_consume_token(17);
/*  57: 57 */     jj_consume_token(18);
/*  58: 58 */     Token minor = jj_consume_token(17);
/*  59:    */     try
/*  60:    */     {
/*  61: 60 */       this.major = Integer.parseInt(major.image);
/*  62: 61 */       this.minor = Integer.parseInt(minor.image);
/*  63:    */     }
/*  64:    */     catch (NumberFormatException e)
/*  65:    */     {
/*  66: 63 */       throw new ParseException(e.getMessage());
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70: 72 */   private final int[] jj_la1 = new int[1];
/*  71:    */   private static int[] jj_la1_0;
/*  72:    */   
/*  73:    */   private static void jj_la1_0()
/*  74:    */   {
/*  75: 78 */     jj_la1_0 = new int[] { 2 };
/*  76:    */   }
/*  77:    */   
/*  78:    */   public MimeVersionParser(InputStream stream)
/*  79:    */   {
/*  80: 82 */     this(stream, null);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public MimeVersionParser(InputStream stream, String encoding)
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87: 85 */       this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
/*  88:    */     }
/*  89:    */     catch (UnsupportedEncodingException e)
/*  90:    */     {
/*  91: 85 */       throw new RuntimeException(e);
/*  92:    */     }
/*  93: 86 */     this.token_source = new MimeVersionParserTokenManager(this.jj_input_stream);
/*  94: 87 */     this.token = new Token();
/*  95: 88 */     this.jj_ntk = -1;
/*  96: 89 */     this.jj_gen = 0;
/*  97: 90 */     for (int i = 0; i < 1; i++) {
/*  98: 90 */       this.jj_la1[i] = -1;
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void ReInit(InputStream stream)
/* 103:    */   {
/* 104: 94 */     ReInit(stream, null);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void ReInit(InputStream stream, String encoding)
/* 108:    */   {
/* 109:    */     try
/* 110:    */     {
/* 111: 97 */       this.jj_input_stream.ReInit(stream, encoding, 1, 1);
/* 112:    */     }
/* 113:    */     catch (UnsupportedEncodingException e)
/* 114:    */     {
/* 115: 97 */       throw new RuntimeException(e);
/* 116:    */     }
/* 117: 98 */     this.token_source.ReInit(this.jj_input_stream);
/* 118: 99 */     this.token = new Token();
/* 119:100 */     this.jj_ntk = -1;
/* 120:101 */     this.jj_gen = 0;
/* 121:102 */     for (int i = 0; i < 1; i++) {
/* 122:102 */       this.jj_la1[i] = -1;
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public MimeVersionParser(Reader stream)
/* 127:    */   {
/* 128:106 */     this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
/* 129:107 */     this.token_source = new MimeVersionParserTokenManager(this.jj_input_stream);
/* 130:108 */     this.token = new Token();
/* 131:109 */     this.jj_ntk = -1;
/* 132:110 */     this.jj_gen = 0;
/* 133:111 */     for (int i = 0; i < 1; i++) {
/* 134:111 */       this.jj_la1[i] = -1;
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void ReInit(Reader stream)
/* 139:    */   {
/* 140:115 */     this.jj_input_stream.ReInit(stream, 1, 1);
/* 141:116 */     this.token_source.ReInit(this.jj_input_stream);
/* 142:117 */     this.token = new Token();
/* 143:118 */     this.jj_ntk = -1;
/* 144:119 */     this.jj_gen = 0;
/* 145:120 */     for (int i = 0; i < 1; i++) {
/* 146:120 */       this.jj_la1[i] = -1;
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public MimeVersionParser(MimeVersionParserTokenManager tm)
/* 151:    */   {
/* 152:124 */     this.token_source = tm;
/* 153:125 */     this.token = new Token();
/* 154:126 */     this.jj_ntk = -1;
/* 155:127 */     this.jj_gen = 0;
/* 156:128 */     for (int i = 0; i < 1; i++) {
/* 157:128 */       this.jj_la1[i] = -1;
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void ReInit(MimeVersionParserTokenManager tm)
/* 162:    */   {
/* 163:132 */     this.token_source = tm;
/* 164:133 */     this.token = new Token();
/* 165:134 */     this.jj_ntk = -1;
/* 166:135 */     this.jj_gen = 0;
/* 167:136 */     for (int i = 0; i < 1; i++) {
/* 168:136 */       this.jj_la1[i] = -1;
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   private final Token jj_consume_token(int kind)
/* 173:    */     throws ParseException
/* 174:    */   {
/* 175:    */     Token oldToken;
/* 176:141 */     if ((oldToken = this.token).next != null) {
/* 177:141 */       this.token = this.token.next;
/* 178:    */     } else {
/* 179:142 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 180:    */     }
/* 181:143 */     this.jj_ntk = -1;
/* 182:144 */     if (this.token.kind == kind)
/* 183:    */     {
/* 184:145 */       this.jj_gen += 1;
/* 185:146 */       return this.token;
/* 186:    */     }
/* 187:148 */     this.token = oldToken;
/* 188:149 */     this.jj_kind = kind;
/* 189:150 */     throw generateParseException();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public final Token getNextToken()
/* 193:    */   {
/* 194:154 */     if (this.token.next != null) {
/* 195:154 */       this.token = this.token.next;
/* 196:    */     } else {
/* 197:155 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 198:    */     }
/* 199:156 */     this.jj_ntk = -1;
/* 200:157 */     this.jj_gen += 1;
/* 201:158 */     return this.token;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public final Token getToken(int index)
/* 205:    */   {
/* 206:162 */     Token t = this.token;
/* 207:163 */     for (int i = 0; i < index; i++) {
/* 208:164 */       if (t.next != null) {
/* 209:164 */         t = t.next;
/* 210:    */       } else {
/* 211:165 */         t = t.next = this.token_source.getNextToken();
/* 212:    */       }
/* 213:    */     }
/* 214:167 */     return t;
/* 215:    */   }
/* 216:    */   
/* 217:    */   private final int jj_ntk()
/* 218:    */   {
/* 219:171 */     if ((this.jj_nt = this.token.next) == null) {
/* 220:172 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* 221:    */     }
/* 222:174 */     return this.jj_ntk = this.jj_nt.kind;
/* 223:    */   }
/* 224:    */   
/* 225:177 */   private Vector<int[]> jj_expentries = new Vector();
/* 226:    */   private int[] jj_expentry;
/* 227:179 */   private int jj_kind = -1;
/* 228:    */   
/* 229:    */   public ParseException generateParseException()
/* 230:    */   {
/* 231:182 */     this.jj_expentries.removeAllElements();
/* 232:183 */     boolean[] la1tokens = new boolean[21];
/* 233:184 */     for (int i = 0; i < 21; i++) {
/* 234:185 */       la1tokens[i] = false;
/* 235:    */     }
/* 236:187 */     if (this.jj_kind >= 0)
/* 237:    */     {
/* 238:188 */       la1tokens[this.jj_kind] = true;
/* 239:189 */       this.jj_kind = -1;
/* 240:    */     }
/* 241:191 */     for (int i = 0; i < 1; i++) {
/* 242:192 */       if (this.jj_la1[i] == this.jj_gen) {
/* 243:193 */         for (int j = 0; j < 32; j++) {
/* 244:194 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 245:195 */             la1tokens[j] = true;
/* 246:    */           }
/* 247:    */         }
/* 248:    */       }
/* 249:    */     }
/* 250:200 */     for (int i = 0; i < 21; i++) {
/* 251:201 */       if (la1tokens[i] != 0)
/* 252:    */       {
/* 253:202 */         this.jj_expentry = new int[1];
/* 254:203 */         this.jj_expentry[0] = i;
/* 255:204 */         this.jj_expentries.addElement(this.jj_expentry);
/* 256:    */       }
/* 257:    */     }
/* 258:207 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 259:208 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 260:209 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* 261:    */     }
/* 262:211 */     return new ParseException(this.token, exptokseq, tokenImage);
/* 263:    */   }
/* 264:    */   
/* 265:    */   public final void enable_tracing() {}
/* 266:    */   
/* 267:    */   public final void disable_tracing() {}
/* 268:    */   
/* 269:    */   static {}
/* 270:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.mimeversion.parser.MimeVersionParser
 * JD-Core Version:    0.7.0.1
 */