/*   1:    */ package org.apache.james.mime4j.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.BitSet;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.james.mime4j.MimeException;
/*   8:    */ import org.apache.james.mime4j.descriptor.BodyDescriptor;
/*   9:    */ import org.apache.james.mime4j.descriptor.DefaultBodyDescriptor;
/*  10:    */ import org.apache.james.mime4j.descriptor.MaximalBodyDescriptor;
/*  11:    */ import org.apache.james.mime4j.descriptor.MutableBodyDescriptor;
/*  12:    */ import org.apache.james.mime4j.io.LineReaderInputStream;
/*  13:    */ import org.apache.james.mime4j.io.MaxHeaderLimitException;
/*  14:    */ import org.apache.james.mime4j.io.MaxLineLimitException;
/*  15:    */ import org.apache.james.mime4j.util.ByteArrayBuffer;
/*  16:    */ 
/*  17:    */ public abstract class AbstractEntity
/*  18:    */   implements EntityStateMachine
/*  19:    */ {
/*  20:    */   protected final Log log;
/*  21:    */   protected final BodyDescriptor parent;
/*  22:    */   protected final int startState;
/*  23:    */   protected final int endState;
/*  24:    */   protected final MimeEntityConfig config;
/*  25:    */   protected final MutableBodyDescriptor body;
/*  26:    */   protected int state;
/*  27:    */   private final ByteArrayBuffer linebuf;
/*  28:    */   private int lineCount;
/*  29:    */   private Field field;
/*  30:    */   private boolean endOfHeader;
/*  31:    */   private int headerCount;
/*  32: 60 */   private static final BitSet fieldChars = new BitSet();
/*  33:    */   private static final int T_IN_BODYPART = -2;
/*  34:    */   private static final int T_IN_MESSAGE = -3;
/*  35:    */   
/*  36:    */   static
/*  37:    */   {
/*  38: 63 */     for (int i = 33; i <= 57; i++) {
/*  39: 64 */       fieldChars.set(i);
/*  40:    */     }
/*  41: 66 */     for (int i = 59; i <= 126; i++) {
/*  42: 67 */       fieldChars.set(i);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   AbstractEntity(BodyDescriptor parent, int startState, int endState, MimeEntityConfig config)
/*  47:    */   {
/*  48: 85 */     this.log = LogFactory.getLog(getClass());
/*  49: 86 */     this.parent = parent;
/*  50: 87 */     this.state = startState;
/*  51: 88 */     this.startState = startState;
/*  52: 89 */     this.endState = endState;
/*  53: 90 */     this.config = config;
/*  54: 91 */     this.body = newBodyDescriptor(parent);
/*  55: 92 */     this.linebuf = new ByteArrayBuffer(64);
/*  56: 93 */     this.lineCount = 0;
/*  57: 94 */     this.endOfHeader = false;
/*  58: 95 */     this.headerCount = 0;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getState()
/*  62:    */   {
/*  63: 99 */     return this.state;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected MutableBodyDescriptor newBodyDescriptor(BodyDescriptor pParent)
/*  67:    */   {
/*  68:    */     MutableBodyDescriptor result;
/*  69:    */     MutableBodyDescriptor result;
/*  70:109 */     if (this.config.isMaximalBodyDescriptor()) {
/*  71:110 */       result = new MaximalBodyDescriptor(pParent);
/*  72:    */     } else {
/*  73:112 */       result = new DefaultBodyDescriptor(pParent);
/*  74:    */     }
/*  75:114 */     return result;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private ByteArrayBuffer fillFieldBuffer()
/*  79:    */     throws IOException, MimeException
/*  80:    */   {
/*  81:126 */     if (this.endOfHeader) {
/*  82:127 */       throw new IllegalStateException();
/*  83:    */     }
/*  84:129 */     int maxLineLen = this.config.getMaxLineLen();
/*  85:130 */     LineReaderInputStream instream = getDataStream();
/*  86:131 */     ByteArrayBuffer fieldbuf = new ByteArrayBuffer(64);
/*  87:    */     for (;;)
/*  88:    */     {
/*  89:136 */       int len = this.linebuf.length();
/*  90:137 */       if ((maxLineLen > 0) && (fieldbuf.length() + len >= maxLineLen)) {
/*  91:138 */         throw new MaxLineLimitException("Maximum line length limit exceeded");
/*  92:    */       }
/*  93:140 */       if (len > 0) {
/*  94:141 */         fieldbuf.append(this.linebuf.buffer(), 0, len);
/*  95:    */       }
/*  96:143 */       this.linebuf.clear();
/*  97:144 */       if (instream.readLine(this.linebuf) == -1)
/*  98:    */       {
/*  99:145 */         monitor(Event.HEADERS_PREMATURE_END);
/* 100:146 */         this.endOfHeader = true;
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104:149 */         len = this.linebuf.length();
/* 105:150 */         if ((len > 0) && (this.linebuf.byteAt(len - 1) == 10)) {
/* 106:151 */           len--;
/* 107:    */         }
/* 108:153 */         if ((len > 0) && (this.linebuf.byteAt(len - 1) == 13)) {
/* 109:154 */           len--;
/* 110:    */         }
/* 111:156 */         if (len == 0)
/* 112:    */         {
/* 113:158 */           this.endOfHeader = true;
/* 114:    */         }
/* 115:    */         else
/* 116:    */         {
/* 117:161 */           this.lineCount += 1;
/* 118:162 */           if (this.lineCount > 1)
/* 119:    */           {
/* 120:163 */             int ch = this.linebuf.byteAt(0);
/* 121:164 */             if ((ch != 32) && (ch != 9)) {
/* 122:    */               break;
/* 123:    */             }
/* 124:    */           }
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:171 */     return fieldbuf;
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected boolean parseField()
/* 132:    */     throws MimeException, IOException
/* 133:    */   {
/* 134:175 */     int maxHeaderLimit = this.config.getMaxHeaderCount();
/* 135:    */     for (;;)
/* 136:    */     {
/* 137:177 */       if (this.endOfHeader) {
/* 138:178 */         return false;
/* 139:    */       }
/* 140:180 */       if (this.headerCount >= maxHeaderLimit) {
/* 141:181 */         throw new MaxHeaderLimitException("Maximum header limit exceeded");
/* 142:    */       }
/* 143:184 */       ByteArrayBuffer fieldbuf = fillFieldBuffer();
/* 144:185 */       this.headerCount += 1;
/* 145:    */       
/* 146:    */ 
/* 147:188 */       int len = fieldbuf.length();
/* 148:189 */       if ((len > 0) && (fieldbuf.byteAt(len - 1) == 10)) {
/* 149:190 */         len--;
/* 150:    */       }
/* 151:192 */       if ((len > 0) && (fieldbuf.byteAt(len - 1) == 13)) {
/* 152:193 */         len--;
/* 153:    */       }
/* 154:195 */       fieldbuf.setLength(len);
/* 155:    */       
/* 156:197 */       boolean valid = true;
/* 157:    */       
/* 158:199 */       int pos = fieldbuf.indexOf((byte)58);
/* 159:200 */       if (pos <= 0)
/* 160:    */       {
/* 161:201 */         monitor(Event.INALID_HEADER);
/* 162:202 */         valid = false;
/* 163:    */       }
/* 164:    */       else
/* 165:    */       {
/* 166:204 */         for (int i = 0; i < pos; i++) {
/* 167:205 */           if (!fieldChars.get(fieldbuf.byteAt(i) & 0xFF))
/* 168:    */           {
/* 169:206 */             monitor(Event.INALID_HEADER);
/* 170:207 */             valid = false;
/* 171:208 */             break;
/* 172:    */           }
/* 173:    */         }
/* 174:    */       }
/* 175:212 */       if (valid)
/* 176:    */       {
/* 177:213 */         this.field = new RawField(fieldbuf, pos);
/* 178:214 */         this.body.addField(this.field);
/* 179:215 */         return true;
/* 180:    */       }
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public BodyDescriptor getBodyDescriptor()
/* 185:    */   {
/* 186:232 */     switch (getState())
/* 187:    */     {
/* 188:    */     case -1: 
/* 189:    */     case 6: 
/* 190:    */     case 8: 
/* 191:    */     case 9: 
/* 192:    */     case 12: 
/* 193:238 */       return this.body;
/* 194:    */     }
/* 195:240 */     throw new IllegalStateException("Invalid state :" + stateToString(this.state));
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Field getField()
/* 199:    */   {
/* 200:251 */     switch (getState())
/* 201:    */     {
/* 202:    */     case 4: 
/* 203:253 */       return this.field;
/* 204:    */     }
/* 205:255 */     throw new IllegalStateException("Invalid state :" + stateToString(this.state));
/* 206:    */   }
/* 207:    */   
/* 208:    */   protected void monitor(Event event)
/* 209:    */     throws MimeException, IOException
/* 210:    */   {
/* 211:269 */     if (this.config.isStrictParsing()) {
/* 212:270 */       throw new MimeParseEventException(event);
/* 213:    */     }
/* 214:272 */     warn(event);
/* 215:    */   }
/* 216:    */   
/* 217:    */   protected String message(Event event)
/* 218:    */   {
/* 219:    */     String message;
/* 220:    */     String message;
/* 221:285 */     if (event == null) {
/* 222:286 */       message = "Event is unexpectedly null.";
/* 223:    */     } else {
/* 224:288 */       message = event.toString();
/* 225:    */     }
/* 226:291 */     int lineNumber = getLineNumber();
/* 227:292 */     if (lineNumber <= 0) {
/* 228:293 */       return message;
/* 229:    */     }
/* 230:295 */     return "Line " + lineNumber + ": " + message;
/* 231:    */   }
/* 232:    */   
/* 233:    */   protected void warn(Event event)
/* 234:    */   {
/* 235:304 */     if (this.log.isWarnEnabled()) {
/* 236:305 */       this.log.warn(message(event));
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   protected void debug(Event event)
/* 241:    */   {
/* 242:315 */     if (this.log.isDebugEnabled()) {
/* 243:316 */       this.log.debug(message(event));
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String toString()
/* 248:    */   {
/* 249:322 */     return getClass().getName() + " [" + stateToString(this.state) + "][" + this.body.getMimeType() + "][" + this.body.getBoundary() + "]";
/* 250:    */   }
/* 251:    */   
/* 252:    */   public static final String stateToString(int state)
/* 253:    */   {
/* 254:    */     String result;
/* 255:333 */     switch (state)
/* 256:    */     {
/* 257:    */     case -1: 
/* 258:335 */       result = "End of stream";
/* 259:336 */       break;
/* 260:    */     case 0: 
/* 261:338 */       result = "Start message";
/* 262:339 */       break;
/* 263:    */     case 1: 
/* 264:341 */       result = "End message";
/* 265:342 */       break;
/* 266:    */     case 2: 
/* 267:344 */       result = "Raw entity";
/* 268:345 */       break;
/* 269:    */     case 3: 
/* 270:347 */       result = "Start header";
/* 271:348 */       break;
/* 272:    */     case 4: 
/* 273:350 */       result = "Field";
/* 274:351 */       break;
/* 275:    */     case 5: 
/* 276:353 */       result = "End header";
/* 277:354 */       break;
/* 278:    */     case 6: 
/* 279:356 */       result = "Start multipart";
/* 280:357 */       break;
/* 281:    */     case 7: 
/* 282:359 */       result = "End multipart";
/* 283:360 */       break;
/* 284:    */     case 8: 
/* 285:362 */       result = "Preamble";
/* 286:363 */       break;
/* 287:    */     case 9: 
/* 288:365 */       result = "Epilogue";
/* 289:366 */       break;
/* 290:    */     case 10: 
/* 291:368 */       result = "Start bodypart";
/* 292:369 */       break;
/* 293:    */     case 11: 
/* 294:371 */       result = "End bodypart";
/* 295:372 */       break;
/* 296:    */     case 12: 
/* 297:374 */       result = "Body";
/* 298:375 */       break;
/* 299:    */     case -2: 
/* 300:377 */       result = "Bodypart";
/* 301:378 */       break;
/* 302:    */     case -3: 
/* 303:380 */       result = "In message";
/* 304:381 */       break;
/* 305:    */     default: 
/* 306:383 */       result = "Unknown";
/* 307:    */     }
/* 308:386 */     return result;
/* 309:    */   }
/* 310:    */   
/* 311:    */   protected abstract int getLineNumber();
/* 312:    */   
/* 313:    */   protected abstract LineReaderInputStream getDataStream();
/* 314:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.parser.AbstractEntity
 * JD-Core Version:    0.7.0.1
 */