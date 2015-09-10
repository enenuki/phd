/*   1:    */ package org.apache.james.mime4j.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.james.mime4j.MimeException;
/*   7:    */ import org.apache.james.mime4j.codec.Base64InputStream;
/*   8:    */ import org.apache.james.mime4j.codec.QuotedPrintableInputStream;
/*   9:    */ import org.apache.james.mime4j.descriptor.BodyDescriptor;
/*  10:    */ import org.apache.james.mime4j.descriptor.MutableBodyDescriptor;
/*  11:    */ import org.apache.james.mime4j.io.BufferedLineReaderInputStream;
/*  12:    */ import org.apache.james.mime4j.io.LimitedInputStream;
/*  13:    */ import org.apache.james.mime4j.io.LineNumberSource;
/*  14:    */ import org.apache.james.mime4j.io.LineReaderInputStream;
/*  15:    */ import org.apache.james.mime4j.io.LineReaderInputStreamAdaptor;
/*  16:    */ import org.apache.james.mime4j.io.MimeBoundaryInputStream;
/*  17:    */ import org.apache.james.mime4j.util.ByteSequence;
/*  18:    */ import org.apache.james.mime4j.util.ContentUtil;
/*  19:    */ import org.apache.james.mime4j.util.MimeUtil;
/*  20:    */ 
/*  21:    */ public class MimeEntity
/*  22:    */   extends AbstractEntity
/*  23:    */ {
/*  24:    */   private static final int T_IN_BODYPART = -2;
/*  25:    */   private static final int T_IN_MESSAGE = -3;
/*  26:    */   private final LineNumberSource lineSource;
/*  27:    */   private final BufferedLineReaderInputStream inbuffer;
/*  28:    */   private int recursionMode;
/*  29:    */   private MimeBoundaryInputStream mimeStream;
/*  30:    */   private LineReaderInputStreamAdaptor dataStream;
/*  31:    */   private boolean skipHeader;
/*  32:    */   private byte[] tmpbuf;
/*  33:    */   
/*  34:    */   public MimeEntity(LineNumberSource lineSource, BufferedLineReaderInputStream inbuffer, BodyDescriptor parent, int startState, int endState, MimeEntityConfig config)
/*  35:    */   {
/*  36: 67 */     super(parent, startState, endState, config);
/*  37: 68 */     this.lineSource = lineSource;
/*  38: 69 */     this.inbuffer = inbuffer;
/*  39: 70 */     this.dataStream = new LineReaderInputStreamAdaptor(inbuffer, config.getMaxLineLen());
/*  40:    */     
/*  41:    */ 
/*  42: 73 */     this.skipHeader = false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public MimeEntity(LineNumberSource lineSource, BufferedLineReaderInputStream inbuffer, BodyDescriptor parent, int startState, int endState)
/*  46:    */   {
/*  47: 82 */     this(lineSource, inbuffer, parent, startState, endState, new MimeEntityConfig());
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getRecursionMode()
/*  51:    */   {
/*  52: 87 */     return this.recursionMode;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setRecursionMode(int recursionMode)
/*  56:    */   {
/*  57: 91 */     this.recursionMode = recursionMode;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void skipHeader(String contentType)
/*  61:    */   {
/*  62: 95 */     if (this.state != 0) {
/*  63: 96 */       throw new IllegalStateException("Invalid state: " + stateToString(this.state));
/*  64:    */     }
/*  65: 98 */     this.skipHeader = true;
/*  66: 99 */     ByteSequence raw = ContentUtil.encode("Content-Type: " + contentType);
/*  67:100 */     this.body.addField(new RawField(raw, 12));
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected int getLineNumber()
/*  71:    */   {
/*  72:105 */     if (this.lineSource == null) {
/*  73:106 */       return -1;
/*  74:    */     }
/*  75:108 */     return this.lineSource.getLineNumber();
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected LineReaderInputStream getDataStream()
/*  79:    */   {
/*  80:113 */     return this.dataStream;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public EntityStateMachine advance()
/*  84:    */     throws IOException, MimeException
/*  85:    */   {
/*  86:117 */     switch (this.state)
/*  87:    */     {
/*  88:    */     case 0: 
/*  89:119 */       if (this.skipHeader) {
/*  90:120 */         this.state = 5;
/*  91:    */       } else {
/*  92:122 */         this.state = 3;
/*  93:    */       }
/*  94:124 */       break;
/*  95:    */     case 10: 
/*  96:126 */       this.state = 3;
/*  97:127 */       break;
/*  98:    */     case 3: 
/*  99:    */     case 4: 
/* 100:130 */       this.state = (parseField() ? 4 : 5);
/* 101:131 */       break;
/* 102:    */     case 5: 
/* 103:133 */       String mimeType = this.body.getMimeType();
/* 104:134 */       if (this.recursionMode == 3)
/* 105:    */       {
/* 106:135 */         this.state = 12;
/* 107:    */       }
/* 108:136 */       else if (MimeUtil.isMultipart(mimeType))
/* 109:    */       {
/* 110:137 */         this.state = 6;
/* 111:138 */         clearMimeStream();
/* 112:    */       }
/* 113:    */       else
/* 114:    */       {
/* 115:139 */         if ((this.recursionMode != 1) && (MimeUtil.isMessage(mimeType)))
/* 116:    */         {
/* 117:141 */           this.state = -3;
/* 118:142 */           return nextMessage();
/* 119:    */         }
/* 120:144 */         this.state = 12;
/* 121:    */       }
/* 122:146 */       break;
/* 123:    */     case 6: 
/* 124:148 */       if (this.dataStream.isUsed())
/* 125:    */       {
/* 126:149 */         advanceToBoundary();
/* 127:150 */         this.state = 7;
/* 128:    */       }
/* 129:    */       else
/* 130:    */       {
/* 131:152 */         createMimeStream();
/* 132:153 */         this.state = 8;
/* 133:    */       }
/* 134:155 */       break;
/* 135:    */     case 8: 
/* 136:157 */       advanceToBoundary();
/* 137:158 */       if (this.mimeStream.isLastPart())
/* 138:    */       {
/* 139:159 */         clearMimeStream();
/* 140:160 */         this.state = 7;
/* 141:    */       }
/* 142:    */       else
/* 143:    */       {
/* 144:162 */         clearMimeStream();
/* 145:163 */         createMimeStream();
/* 146:164 */         this.state = -2;
/* 147:165 */         return nextMimeEntity();
/* 148:    */       }
/* 149:    */       break;
/* 150:    */     case -2: 
/* 151:169 */       advanceToBoundary();
/* 152:170 */       if ((this.mimeStream.eof()) && (!this.mimeStream.isLastPart()))
/* 153:    */       {
/* 154:171 */         monitor(Event.MIME_BODY_PREMATURE_END);
/* 155:    */       }
/* 156:173 */       else if (!this.mimeStream.isLastPart())
/* 157:    */       {
/* 158:174 */         clearMimeStream();
/* 159:175 */         createMimeStream();
/* 160:176 */         this.state = -2;
/* 161:177 */         return nextMimeEntity();
/* 162:    */       }
/* 163:180 */       clearMimeStream();
/* 164:181 */       this.state = 9;
/* 165:182 */       break;
/* 166:    */     case 9: 
/* 167:184 */       this.state = 7;
/* 168:185 */       break;
/* 169:    */     case -3: 
/* 170:    */     case 7: 
/* 171:    */     case 12: 
/* 172:189 */       this.state = this.endState;
/* 173:190 */       break;
/* 174:    */     case -1: 
/* 175:    */     case 1: 
/* 176:    */     case 2: 
/* 177:    */     case 11: 
/* 178:    */     default: 
/* 179:192 */       if (this.state == this.endState) {
/* 180:193 */         this.state = -1;
/* 181:    */       } else {
/* 182:196 */         throw new IllegalStateException("Invalid state: " + stateToString(this.state));
/* 183:    */       }
/* 184:    */       break;
/* 185:    */     }
/* 186:198 */     return null;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private void createMimeStream()
/* 190:    */     throws MimeException, IOException
/* 191:    */   {
/* 192:202 */     String boundary = this.body.getBoundary();
/* 193:203 */     int bufferSize = 2 * boundary.length();
/* 194:204 */     if (bufferSize < 4096) {
/* 195:205 */       bufferSize = 4096;
/* 196:    */     }
/* 197:    */     try
/* 198:    */     {
/* 199:208 */       if (this.mimeStream != null)
/* 200:    */       {
/* 201:209 */         this.mimeStream = new MimeBoundaryInputStream(new BufferedLineReaderInputStream(this.mimeStream, bufferSize, this.config.getMaxLineLen()), boundary);
/* 202:    */       }
/* 203:    */       else
/* 204:    */       {
/* 205:216 */         this.inbuffer.ensureCapacity(bufferSize);
/* 206:217 */         this.mimeStream = new MimeBoundaryInputStream(this.inbuffer, boundary);
/* 207:    */       }
/* 208:    */     }
/* 209:    */     catch (IllegalArgumentException e)
/* 210:    */     {
/* 211:221 */       throw new MimeException(e.getMessage(), e);
/* 212:    */     }
/* 213:223 */     this.dataStream = new LineReaderInputStreamAdaptor(this.mimeStream, this.config.getMaxLineLen());
/* 214:    */   }
/* 215:    */   
/* 216:    */   private void clearMimeStream()
/* 217:    */   {
/* 218:229 */     this.mimeStream = null;
/* 219:230 */     this.dataStream = new LineReaderInputStreamAdaptor(this.inbuffer, this.config.getMaxLineLen());
/* 220:    */   }
/* 221:    */   
/* 222:    */   private void advanceToBoundary()
/* 223:    */     throws IOException
/* 224:    */   {
/* 225:236 */     if (!this.dataStream.eof())
/* 226:    */     {
/* 227:237 */       if (this.tmpbuf == null) {
/* 228:238 */         this.tmpbuf = new byte[2048];
/* 229:    */       }
/* 230:240 */       InputStream instream = getLimitedContentStream();
/* 231:241 */       while (instream.read(this.tmpbuf) != -1) {}
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   private EntityStateMachine nextMessage()
/* 236:    */   {
/* 237:247 */     String transferEncoding = this.body.getTransferEncoding();
/* 238:    */     InputStream instream;
/* 239:    */     InputStream instream;
/* 240:249 */     if (MimeUtil.isBase64Encoding(transferEncoding))
/* 241:    */     {
/* 242:250 */       this.log.debug("base64 encoded message/rfc822 detected");
/* 243:251 */       instream = new Base64InputStream(this.dataStream);
/* 244:    */     }
/* 245:    */     else
/* 246:    */     {
/* 247:    */       InputStream instream;
/* 248:252 */       if (MimeUtil.isQuotedPrintableEncoded(transferEncoding))
/* 249:    */       {
/* 250:253 */         this.log.debug("quoted-printable encoded message/rfc822 detected");
/* 251:254 */         instream = new QuotedPrintableInputStream(this.dataStream);
/* 252:    */       }
/* 253:    */       else
/* 254:    */       {
/* 255:256 */         instream = this.dataStream;
/* 256:    */       }
/* 257:    */     }
/* 258:259 */     if (this.recursionMode == 2)
/* 259:    */     {
/* 260:260 */       RawEntity message = new RawEntity(instream);
/* 261:261 */       return message;
/* 262:    */     }
/* 263:263 */     MimeEntity message = new MimeEntity(this.lineSource, new BufferedLineReaderInputStream(instream, 4096, this.config.getMaxLineLen()), this.body, 0, 1, this.config);
/* 264:    */     
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:273 */     message.setRecursionMode(this.recursionMode);
/* 274:274 */     return message;
/* 275:    */   }
/* 276:    */   
/* 277:    */   private EntityStateMachine nextMimeEntity()
/* 278:    */   {
/* 279:279 */     if (this.recursionMode == 2)
/* 280:    */     {
/* 281:280 */       RawEntity message = new RawEntity(this.mimeStream);
/* 282:281 */       return message;
/* 283:    */     }
/* 284:283 */     BufferedLineReaderInputStream stream = new BufferedLineReaderInputStream(this.mimeStream, 4096, this.config.getMaxLineLen());
/* 285:    */     
/* 286:    */ 
/* 287:    */ 
/* 288:287 */     MimeEntity mimeentity = new MimeEntity(this.lineSource, stream, this.body, 10, 11, this.config);
/* 289:    */     
/* 290:    */ 
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:294 */     mimeentity.setRecursionMode(this.recursionMode);
/* 296:295 */     return mimeentity;
/* 297:    */   }
/* 298:    */   
/* 299:    */   private InputStream getLimitedContentStream()
/* 300:    */   {
/* 301:300 */     long maxContentLimit = this.config.getMaxContentLen();
/* 302:301 */     if (maxContentLimit >= 0L) {
/* 303:302 */       return new LimitedInputStream(this.dataStream, maxContentLimit);
/* 304:    */     }
/* 305:304 */     return this.dataStream;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public InputStream getContentStream()
/* 309:    */   {
/* 310:309 */     switch (this.state)
/* 311:    */     {
/* 312:    */     case 6: 
/* 313:    */     case 8: 
/* 314:    */     case 9: 
/* 315:    */     case 12: 
/* 316:314 */       return getLimitedContentStream();
/* 317:    */     }
/* 318:316 */     throw new IllegalStateException("Invalid state: " + stateToString(this.state));
/* 319:    */   }
/* 320:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.parser.MimeEntity
 * JD-Core Version:    0.7.0.1
 */