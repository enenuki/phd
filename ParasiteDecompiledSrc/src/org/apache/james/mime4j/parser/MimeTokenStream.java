/*   1:    */ package org.apache.james.mime4j.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.Reader;
/*   7:    */ import java.nio.charset.Charset;
/*   8:    */ import java.util.LinkedList;
/*   9:    */ import org.apache.james.mime4j.MimeException;
/*  10:    */ import org.apache.james.mime4j.codec.Base64InputStream;
/*  11:    */ import org.apache.james.mime4j.codec.QuotedPrintableInputStream;
/*  12:    */ import org.apache.james.mime4j.descriptor.BodyDescriptor;
/*  13:    */ import org.apache.james.mime4j.io.BufferedLineReaderInputStream;
/*  14:    */ import org.apache.james.mime4j.io.LineNumberInputStream;
/*  15:    */ import org.apache.james.mime4j.io.LineNumberSource;
/*  16:    */ import org.apache.james.mime4j.util.CharsetUtil;
/*  17:    */ import org.apache.james.mime4j.util.MimeUtil;
/*  18:    */ 
/*  19:    */ public class MimeTokenStream
/*  20:    */   implements EntityStates, RecursionMode
/*  21:    */ {
/*  22:    */   private final MimeEntityConfig config;
/*  23:    */   
/*  24:    */   public static final MimeTokenStream createMaximalDescriptorStream()
/*  25:    */   {
/*  26: 86 */     MimeEntityConfig config = new MimeEntityConfig();
/*  27: 87 */     config.setMaximalBodyDescriptor(true);
/*  28: 88 */     return new MimeTokenStream(config);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static final MimeTokenStream createStrictValidationStream()
/*  32:    */   {
/*  33: 98 */     MimeEntityConfig config = new MimeEntityConfig();
/*  34: 99 */     config.setStrictParsing(true);
/*  35:100 */     return new MimeTokenStream(config);
/*  36:    */   }
/*  37:    */   
/*  38:104 */   private final LinkedList<EntityStateMachine> entities = new LinkedList();
/*  39:106 */   private int state = -1;
/*  40:    */   private EntityStateMachine currentStateMachine;
/*  41:108 */   private int recursionMode = 0;
/*  42:    */   private BufferedLineReaderInputStream inbuffer;
/*  43:    */   
/*  44:    */   public MimeTokenStream()
/*  45:    */   {
/*  46:118 */     this(new MimeEntityConfig());
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected MimeTokenStream(MimeEntityConfig config)
/*  50:    */   {
/*  51:123 */     this.config = config;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void parse(InputStream stream)
/*  55:    */   {
/*  56:131 */     doParse(stream, null);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void parseHeadless(InputStream stream, String contentType)
/*  60:    */   {
/*  61:144 */     if (contentType == null) {
/*  62:145 */       throw new IllegalArgumentException("Content type may not be null");
/*  63:    */     }
/*  64:147 */     doParse(stream, contentType);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void doParse(InputStream stream, String contentType)
/*  68:    */   {
/*  69:151 */     this.entities.clear();
/*  70:    */     
/*  71:153 */     LineNumberSource lineSource = null;
/*  72:154 */     if (this.config.isCountLineNumbers())
/*  73:    */     {
/*  74:155 */       LineNumberInputStream lineInput = new LineNumberInputStream(stream);
/*  75:156 */       lineSource = lineInput;
/*  76:157 */       stream = lineInput;
/*  77:    */     }
/*  78:160 */     this.inbuffer = new BufferedLineReaderInputStream(stream, 4096, this.config.getMaxLineLen());
/*  79:164 */     switch (this.recursionMode)
/*  80:    */     {
/*  81:    */     case 2: 
/*  82:166 */       RawEntity rawentity = new RawEntity(this.inbuffer);
/*  83:167 */       this.currentStateMachine = rawentity;
/*  84:168 */       break;
/*  85:    */     case 0: 
/*  86:    */     case 1: 
/*  87:    */     case 3: 
/*  88:173 */       MimeEntity mimeentity = new MimeEntity(lineSource, this.inbuffer, null, 0, 1, this.config);
/*  89:    */       
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:180 */       mimeentity.setRecursionMode(this.recursionMode);
/*  96:181 */       if (contentType != null) {
/*  97:182 */         mimeentity.skipHeader(contentType);
/*  98:    */       }
/*  99:184 */       this.currentStateMachine = mimeentity;
/* 100:    */     }
/* 101:187 */     this.entities.add(this.currentStateMachine);
/* 102:188 */     this.state = this.currentStateMachine.getState();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean isRaw()
/* 106:    */   {
/* 107:199 */     return this.recursionMode == 2;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getRecursionMode()
/* 111:    */   {
/* 112:212 */     return this.recursionMode;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setRecursionMode(int mode)
/* 116:    */   {
/* 117:225 */     this.recursionMode = mode;
/* 118:226 */     if (this.currentStateMachine != null) {
/* 119:227 */       this.currentStateMachine.setRecursionMode(mode);
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void stop()
/* 124:    */   {
/* 125:244 */     this.inbuffer.truncate();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getState()
/* 129:    */   {
/* 130:251 */     return this.state;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public InputStream getInputStream()
/* 134:    */   {
/* 135:265 */     return this.currentStateMachine.getContentStream();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public InputStream getDecodedInputStream()
/* 139:    */   {
/* 140:280 */     BodyDescriptor bodyDescriptor = getBodyDescriptor();
/* 141:281 */     String transferEncoding = bodyDescriptor.getTransferEncoding();
/* 142:282 */     InputStream dataStream = this.currentStateMachine.getContentStream();
/* 143:283 */     if (MimeUtil.isBase64Encoding(transferEncoding)) {
/* 144:284 */       dataStream = new Base64InputStream(dataStream);
/* 145:285 */     } else if (MimeUtil.isQuotedPrintableEncoded(transferEncoding)) {
/* 146:286 */       dataStream = new QuotedPrintableInputStream(dataStream);
/* 147:    */     }
/* 148:288 */     return dataStream;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Reader getReader()
/* 152:    */   {
/* 153:309 */     BodyDescriptor bodyDescriptor = getBodyDescriptor();
/* 154:310 */     String mimeCharset = bodyDescriptor.getCharset();
/* 155:    */     Charset charset;
/* 156:    */     Charset charset;
/* 157:312 */     if ((mimeCharset == null) || ("".equals(mimeCharset))) {
/* 158:313 */       charset = CharsetUtil.US_ASCII;
/* 159:    */     } else {
/* 160:315 */       charset = Charset.forName(mimeCharset);
/* 161:    */     }
/* 162:317 */     InputStream instream = getDecodedInputStream();
/* 163:318 */     return new InputStreamReader(instream, charset);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public BodyDescriptor getBodyDescriptor()
/* 167:    */   {
/* 168:333 */     return this.currentStateMachine.getBodyDescriptor();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Field getField()
/* 172:    */   {
/* 173:343 */     return this.currentStateMachine.getField();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int next()
/* 177:    */     throws IOException, MimeException
/* 178:    */   {
/* 179:352 */     if ((this.state == -1) || (this.currentStateMachine == null)) {
/* 180:353 */       throw new IllegalStateException("No more tokens are available.");
/* 181:    */     }
/* 182:355 */     while (this.currentStateMachine != null)
/* 183:    */     {
/* 184:356 */       EntityStateMachine next = this.currentStateMachine.advance();
/* 185:357 */       if (next != null)
/* 186:    */       {
/* 187:358 */         this.entities.add(next);
/* 188:359 */         this.currentStateMachine = next;
/* 189:    */       }
/* 190:361 */       this.state = this.currentStateMachine.getState();
/* 191:362 */       if (this.state != -1) {
/* 192:363 */         return this.state;
/* 193:    */       }
/* 194:365 */       this.entities.removeLast();
/* 195:366 */       if (this.entities.isEmpty())
/* 196:    */       {
/* 197:367 */         this.currentStateMachine = null;
/* 198:    */       }
/* 199:    */       else
/* 200:    */       {
/* 201:369 */         this.currentStateMachine = ((EntityStateMachine)this.entities.getLast());
/* 202:370 */         this.currentStateMachine.setRecursionMode(this.recursionMode);
/* 203:    */       }
/* 204:    */     }
/* 205:373 */     this.state = -1;
/* 206:374 */     return this.state;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static final String stateToString(int state)
/* 210:    */   {
/* 211:383 */     return AbstractEntity.stateToString(state);
/* 212:    */   }
/* 213:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.parser.MimeTokenStream
 * JD-Core Version:    0.7.0.1
 */