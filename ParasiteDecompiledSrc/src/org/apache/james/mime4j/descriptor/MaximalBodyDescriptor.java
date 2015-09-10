/*   1:    */ package org.apache.james.mime4j.descriptor;
/*   2:    */ 
/*   3:    */ import java.io.StringReader;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.apache.james.mime4j.MimeException;
/*   8:    */ import org.apache.james.mime4j.field.datetime.DateTime;
/*   9:    */ import org.apache.james.mime4j.field.datetime.parser.DateTimeParser;
/*  10:    */ import org.apache.james.mime4j.field.datetime.parser.ParseException;
/*  11:    */ import org.apache.james.mime4j.field.language.parser.ContentLanguageParser;
/*  12:    */ import org.apache.james.mime4j.field.mimeversion.parser.MimeVersionParser;
/*  13:    */ import org.apache.james.mime4j.field.structured.parser.StructuredFieldParser;
/*  14:    */ import org.apache.james.mime4j.parser.Field;
/*  15:    */ import org.apache.james.mime4j.util.MimeUtil;
/*  16:    */ 
/*  17:    */ public class MaximalBodyDescriptor
/*  18:    */   extends DefaultBodyDescriptor
/*  19:    */ {
/*  20:    */   private static final int DEFAULT_MINOR_VERSION = 0;
/*  21:    */   private static final int DEFAULT_MAJOR_VERSION = 1;
/*  22:    */   private boolean isMimeVersionSet;
/*  23:    */   private int mimeMinorVersion;
/*  24:    */   private int mimeMajorVersion;
/*  25:    */   private MimeException mimeVersionException;
/*  26:    */   private String contentId;
/*  27:    */   private boolean isContentIdSet;
/*  28:    */   private String contentDescription;
/*  29:    */   private boolean isContentDescriptionSet;
/*  30:    */   private String contentDispositionType;
/*  31:    */   private Map<String, String> contentDispositionParameters;
/*  32:    */   private DateTime contentDispositionModificationDate;
/*  33:    */   private MimeException contentDispositionModificationDateParseException;
/*  34:    */   private DateTime contentDispositionCreationDate;
/*  35:    */   private MimeException contentDispositionCreationDateParseException;
/*  36:    */   private DateTime contentDispositionReadDate;
/*  37:    */   private MimeException contentDispositionReadDateParseException;
/*  38:    */   private long contentDispositionSize;
/*  39:    */   private MimeException contentDispositionSizeParseException;
/*  40:    */   private boolean isContentDispositionSet;
/*  41:    */   private List<String> contentLanguage;
/*  42:    */   private MimeException contentLanguageParseException;
/*  43:    */   private boolean isContentLanguageSet;
/*  44:    */   private MimeException contentLocationParseException;
/*  45:    */   private String contentLocation;
/*  46:    */   private boolean isContentLocationSet;
/*  47:    */   private String contentMD5Raw;
/*  48:    */   private boolean isContentMD5Set;
/*  49:    */   
/*  50:    */   protected MaximalBodyDescriptor()
/*  51:    */   {
/*  52: 74 */     this(null);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public MaximalBodyDescriptor(BodyDescriptor parent)
/*  56:    */   {
/*  57: 78 */     super(parent);
/*  58: 79 */     this.isMimeVersionSet = false;
/*  59: 80 */     this.mimeMajorVersion = 1;
/*  60: 81 */     this.mimeMinorVersion = 0;
/*  61: 82 */     this.contentId = null;
/*  62: 83 */     this.isContentIdSet = false;
/*  63: 84 */     this.contentDescription = null;
/*  64: 85 */     this.isContentDescriptionSet = false;
/*  65: 86 */     this.contentDispositionType = null;
/*  66: 87 */     this.contentDispositionParameters = Collections.emptyMap();
/*  67: 88 */     this.contentDispositionModificationDate = null;
/*  68: 89 */     this.contentDispositionModificationDateParseException = null;
/*  69: 90 */     this.contentDispositionCreationDate = null;
/*  70: 91 */     this.contentDispositionCreationDateParseException = null;
/*  71: 92 */     this.contentDispositionReadDate = null;
/*  72: 93 */     this.contentDispositionReadDateParseException = null;
/*  73: 94 */     this.contentDispositionSize = -1L;
/*  74: 95 */     this.contentDispositionSizeParseException = null;
/*  75: 96 */     this.isContentDispositionSet = false;
/*  76: 97 */     this.contentLanguage = null;
/*  77: 98 */     this.contentLanguageParseException = null;
/*  78: 99 */     this.isContentIdSet = false;
/*  79:100 */     this.contentLocation = null;
/*  80:101 */     this.contentLocationParseException = null;
/*  81:102 */     this.isContentLocationSet = false;
/*  82:103 */     this.contentMD5Raw = null;
/*  83:104 */     this.isContentMD5Set = false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void addField(Field field)
/*  87:    */   {
/*  88:109 */     String name = field.getName();
/*  89:110 */     String value = field.getBody();
/*  90:111 */     name = name.trim().toLowerCase();
/*  91:112 */     if (("mime-version".equals(name)) && (!this.isMimeVersionSet)) {
/*  92:113 */       parseMimeVersion(value);
/*  93:114 */     } else if (("content-id".equals(name)) && (!this.isContentIdSet)) {
/*  94:115 */       parseContentId(value);
/*  95:116 */     } else if (("content-description".equals(name)) && (!this.isContentDescriptionSet)) {
/*  96:117 */       parseContentDescription(value);
/*  97:118 */     } else if (("content-disposition".equals(name)) && (!this.isContentDispositionSet)) {
/*  98:119 */       parseContentDisposition(value);
/*  99:120 */     } else if (("content-language".equals(name)) && (!this.isContentLanguageSet)) {
/* 100:121 */       parseLanguage(value);
/* 101:122 */     } else if (("content-location".equals(name)) && (!this.isContentLocationSet)) {
/* 102:123 */       parseLocation(value);
/* 103:124 */     } else if (("content-md5".equals(name)) && (!this.isContentMD5Set)) {
/* 104:125 */       parseMD5(value);
/* 105:    */     } else {
/* 106:127 */       super.addField(field);
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void parseMD5(String value)
/* 111:    */   {
/* 112:132 */     this.isContentMD5Set = true;
/* 113:133 */     if (value != null) {
/* 114:134 */       this.contentMD5Raw = value.trim();
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void parseLocation(String value)
/* 119:    */   {
/* 120:139 */     this.isContentLocationSet = true;
/* 121:140 */     if (value != null)
/* 122:    */     {
/* 123:141 */       StringReader stringReader = new StringReader(value);
/* 124:142 */       StructuredFieldParser parser = new StructuredFieldParser(stringReader);
/* 125:143 */       parser.setFoldingPreserved(false);
/* 126:    */       try
/* 127:    */       {
/* 128:145 */         this.contentLocation = parser.parse();
/* 129:    */       }
/* 130:    */       catch (MimeException e)
/* 131:    */       {
/* 132:147 */         this.contentLocationParseException = e;
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   private void parseLanguage(String value)
/* 138:    */   {
/* 139:153 */     this.isContentLanguageSet = true;
/* 140:154 */     if (value != null) {
/* 141:    */       try
/* 142:    */       {
/* 143:156 */         ContentLanguageParser parser = new ContentLanguageParser(new StringReader(value));
/* 144:157 */         this.contentLanguage = parser.parse();
/* 145:    */       }
/* 146:    */       catch (MimeException e)
/* 147:    */       {
/* 148:159 */         this.contentLanguageParseException = e;
/* 149:    */       }
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   private void parseContentDisposition(String value)
/* 154:    */   {
/* 155:165 */     this.isContentDispositionSet = true;
/* 156:166 */     this.contentDispositionParameters = MimeUtil.getHeaderParams(value);
/* 157:167 */     this.contentDispositionType = ((String)this.contentDispositionParameters.get(""));
/* 158:    */     
/* 159:169 */     String contentDispositionModificationDate = (String)this.contentDispositionParameters.get("modification-date");
/* 160:171 */     if (contentDispositionModificationDate != null) {
/* 161:    */       try
/* 162:    */       {
/* 163:173 */         this.contentDispositionModificationDate = parseDate(contentDispositionModificationDate);
/* 164:    */       }
/* 165:    */       catch (ParseException e)
/* 166:    */       {
/* 167:175 */         this.contentDispositionModificationDateParseException = e;
/* 168:    */       }
/* 169:    */     }
/* 170:179 */     String contentDispositionCreationDate = (String)this.contentDispositionParameters.get("creation-date");
/* 171:181 */     if (contentDispositionCreationDate != null) {
/* 172:    */       try
/* 173:    */       {
/* 174:183 */         this.contentDispositionCreationDate = parseDate(contentDispositionCreationDate);
/* 175:    */       }
/* 176:    */       catch (ParseException e)
/* 177:    */       {
/* 178:185 */         this.contentDispositionCreationDateParseException = e;
/* 179:    */       }
/* 180:    */     }
/* 181:189 */     String contentDispositionReadDate = (String)this.contentDispositionParameters.get("read-date");
/* 182:191 */     if (contentDispositionReadDate != null) {
/* 183:    */       try
/* 184:    */       {
/* 185:193 */         this.contentDispositionReadDate = parseDate(contentDispositionReadDate);
/* 186:    */       }
/* 187:    */       catch (ParseException e)
/* 188:    */       {
/* 189:195 */         this.contentDispositionReadDateParseException = e;
/* 190:    */       }
/* 191:    */     }
/* 192:199 */     String size = (String)this.contentDispositionParameters.get("size");
/* 193:200 */     if (size != null) {
/* 194:    */       try
/* 195:    */       {
/* 196:202 */         this.contentDispositionSize = Long.parseLong(size);
/* 197:    */       }
/* 198:    */       catch (NumberFormatException e)
/* 199:    */       {
/* 200:204 */         this.contentDispositionSizeParseException = ((MimeException)new MimeException(e.getMessage(), e).fillInStackTrace());
/* 201:    */       }
/* 202:    */     }
/* 203:207 */     this.contentDispositionParameters.remove("");
/* 204:    */   }
/* 205:    */   
/* 206:    */   private DateTime parseDate(String date)
/* 207:    */     throws ParseException
/* 208:    */   {
/* 209:211 */     StringReader stringReader = new StringReader(date);
/* 210:212 */     DateTimeParser parser = new DateTimeParser(stringReader);
/* 211:213 */     DateTime result = parser.date_time();
/* 212:214 */     return result;
/* 213:    */   }
/* 214:    */   
/* 215:    */   private void parseContentDescription(String value)
/* 216:    */   {
/* 217:218 */     if (value == null) {
/* 218:219 */       this.contentDescription = "";
/* 219:    */     } else {
/* 220:221 */       this.contentDescription = value.trim();
/* 221:    */     }
/* 222:223 */     this.isContentDescriptionSet = true;
/* 223:    */   }
/* 224:    */   
/* 225:    */   private void parseContentId(String value)
/* 226:    */   {
/* 227:227 */     if (value == null) {
/* 228:228 */       this.contentId = "";
/* 229:    */     } else {
/* 230:230 */       this.contentId = value.trim();
/* 231:    */     }
/* 232:232 */     this.isContentIdSet = true;
/* 233:    */   }
/* 234:    */   
/* 235:    */   private void parseMimeVersion(String value)
/* 236:    */   {
/* 237:236 */     StringReader reader = new StringReader(value);
/* 238:237 */     MimeVersionParser parser = new MimeVersionParser(reader);
/* 239:    */     try
/* 240:    */     {
/* 241:239 */       parser.parse();
/* 242:240 */       int major = parser.getMajorVersion();
/* 243:241 */       if (major != -1) {
/* 244:242 */         this.mimeMajorVersion = major;
/* 245:    */       }
/* 246:244 */       int minor = parser.getMinorVersion();
/* 247:245 */       if (minor != -1) {
/* 248:246 */         this.mimeMinorVersion = minor;
/* 249:    */       }
/* 250:    */     }
/* 251:    */     catch (MimeException e)
/* 252:    */     {
/* 253:249 */       this.mimeVersionException = e;
/* 254:    */     }
/* 255:251 */     this.isMimeVersionSet = true;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public int getMimeMajorVersion()
/* 259:    */   {
/* 260:262 */     return this.mimeMajorVersion;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public int getMimeMinorVersion()
/* 264:    */   {
/* 265:273 */     return this.mimeMinorVersion;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public MimeException getMimeVersionParseException()
/* 269:    */   {
/* 270:284 */     return this.mimeVersionException;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public String getContentDescription()
/* 274:    */   {
/* 275:294 */     return this.contentDescription;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String getContentId()
/* 279:    */   {
/* 280:304 */     return this.contentId;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public String getContentDispositionType()
/* 284:    */   {
/* 285:315 */     return this.contentDispositionType;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public Map<String, String> getContentDispositionParameters()
/* 289:    */   {
/* 290:325 */     return this.contentDispositionParameters;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public String getContentDispositionFilename()
/* 294:    */   {
/* 295:335 */     return (String)this.contentDispositionParameters.get("filename");
/* 296:    */   }
/* 297:    */   
/* 298:    */   public DateTime getContentDispositionModificationDate()
/* 299:    */   {
/* 300:345 */     return this.contentDispositionModificationDate;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public MimeException getContentDispositionModificationDateParseException()
/* 304:    */   {
/* 305:354 */     return this.contentDispositionModificationDateParseException;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public DateTime getContentDispositionCreationDate()
/* 309:    */   {
/* 310:364 */     return this.contentDispositionCreationDate;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public MimeException getContentDispositionCreationDateParseException()
/* 314:    */   {
/* 315:373 */     return this.contentDispositionCreationDateParseException;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public DateTime getContentDispositionReadDate()
/* 319:    */   {
/* 320:383 */     return this.contentDispositionReadDate;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public MimeException getContentDispositionReadDateParseException()
/* 324:    */   {
/* 325:392 */     return this.contentDispositionReadDateParseException;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public long getContentDispositionSize()
/* 329:    */   {
/* 330:402 */     return this.contentDispositionSize;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public MimeException getContentDispositionSizeParseException()
/* 334:    */   {
/* 335:411 */     return this.contentDispositionSizeParseException;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public List<String> getContentLanguage()
/* 339:    */   {
/* 340:423 */     return this.contentLanguage;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public MimeException getContentLanguageParseException()
/* 344:    */   {
/* 345:432 */     return this.contentLanguageParseException;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public String getContentLocation()
/* 349:    */   {
/* 350:443 */     return this.contentLocation;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public MimeException getContentLocationParseException()
/* 354:    */   {
/* 355:452 */     return this.contentLocationParseException;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public String getContentMD5Raw()
/* 359:    */   {
/* 360:463 */     return this.contentMD5Raw;
/* 361:    */   }
/* 362:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.descriptor.MaximalBodyDescriptor
 * JD-Core Version:    0.7.0.1
 */