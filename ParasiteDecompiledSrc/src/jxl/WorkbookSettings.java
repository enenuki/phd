/*   1:    */ package jxl;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Locale;
/*   6:    */ import jxl.biff.CountryCode;
/*   7:    */ import jxl.biff.formula.FunctionNames;
/*   8:    */ import jxl.common.Logger;
/*   9:    */ 
/*  10:    */ public final class WorkbookSettings
/*  11:    */ {
/*  12: 42 */   private static Logger logger = Logger.getLogger(WorkbookSettings.class);
/*  13:    */   private int initialFileSize;
/*  14:    */   private int arrayGrowSize;
/*  15:    */   private boolean drawingsDisabled;
/*  16:    */   private boolean namesDisabled;
/*  17:    */   private boolean formulaReferenceAdjustDisabled;
/*  18:    */   private boolean gcDisabled;
/*  19:    */   private boolean rationalizationDisabled;
/*  20:    */   private boolean mergedCellCheckingDisabled;
/*  21:    */   private boolean propertySetsDisabled;
/*  22:    */   private boolean cellValidationDisabled;
/*  23:    */   private boolean ignoreBlankCells;
/*  24:    */   private boolean autoFilterDisabled;
/*  25:    */   private boolean useTemporaryFileDuringWrite;
/*  26:    */   private File temporaryFileDuringWriteDirectory;
/*  27:    */   private Locale locale;
/*  28:    */   private FunctionNames functionNames;
/*  29:    */   private String encoding;
/*  30:    */   private int characterSet;
/*  31:    */   private String excelDisplayLanguage;
/*  32:    */   private String excelRegionalSettings;
/*  33:    */   private HashMap localeFunctionNames;
/*  34:    */   private boolean refreshAll;
/*  35:    */   private boolean template;
/*  36:207 */   private boolean excel9file = false;
/*  37:    */   private boolean windowProtected;
/*  38:    */   private String writeAccess;
/*  39:    */   private int hideobj;
/*  40:    */   public static final int HIDEOBJ_HIDE_ALL = 2;
/*  41:    */   public static final int HIDEOBJ_SHOW_PLACEHOLDERS = 1;
/*  42:    */   public static final int HIDEOBJ_SHOW_ALL = 0;
/*  43:    */   private static final int DEFAULT_INITIAL_FILE_SIZE = 5242880;
/*  44:    */   private static final int DEFAULT_ARRAY_GROW_SIZE = 1048576;
/*  45:    */   
/*  46:    */   public WorkbookSettings()
/*  47:    */   {
/*  48:254 */     this.initialFileSize = 5242880;
/*  49:255 */     this.arrayGrowSize = 1048576;
/*  50:256 */     this.localeFunctionNames = new HashMap();
/*  51:257 */     this.excelDisplayLanguage = CountryCode.USA.getCode();
/*  52:258 */     this.excelRegionalSettings = CountryCode.UK.getCode();
/*  53:259 */     this.refreshAll = false;
/*  54:260 */     this.template = false;
/*  55:261 */     this.excel9file = false;
/*  56:262 */     this.windowProtected = false;
/*  57:263 */     this.hideobj = 0;
/*  58:    */     try
/*  59:    */     {
/*  60:268 */       boolean suppressWarnings = Boolean.getBoolean("jxl.nowarnings");
/*  61:269 */       setSuppressWarnings(suppressWarnings);
/*  62:270 */       this.drawingsDisabled = Boolean.getBoolean("jxl.nodrawings");
/*  63:271 */       this.namesDisabled = Boolean.getBoolean("jxl.nonames");
/*  64:272 */       this.gcDisabled = Boolean.getBoolean("jxl.nogc");
/*  65:273 */       this.rationalizationDisabled = Boolean.getBoolean("jxl.norat");
/*  66:274 */       this.mergedCellCheckingDisabled = Boolean.getBoolean("jxl.nomergedcellchecks");
/*  67:    */       
/*  68:276 */       this.formulaReferenceAdjustDisabled = Boolean.getBoolean("jxl.noformulaadjust");
/*  69:    */       
/*  70:278 */       this.propertySetsDisabled = Boolean.getBoolean("jxl.nopropertysets");
/*  71:279 */       this.ignoreBlankCells = Boolean.getBoolean("jxl.ignoreblanks");
/*  72:280 */       this.cellValidationDisabled = Boolean.getBoolean("jxl.nocellvalidation");
/*  73:281 */       this.autoFilterDisabled = (!Boolean.getBoolean("jxl.autofilter"));
/*  74:    */       
/*  75:283 */       this.useTemporaryFileDuringWrite = Boolean.getBoolean("jxl.usetemporaryfileduringwrite");
/*  76:    */       
/*  77:285 */       String tempdir = System.getProperty("jxl.temporaryfileduringwritedirectory");
/*  78:288 */       if (tempdir != null) {
/*  79:290 */         this.temporaryFileDuringWriteDirectory = new File(tempdir);
/*  80:    */       }
/*  81:293 */       this.encoding = System.getProperty("file.encoding");
/*  82:    */     }
/*  83:    */     catch (SecurityException e)
/*  84:    */     {
/*  85:297 */       logger.warn("Error accessing system properties.", e);
/*  86:    */     }
/*  87:    */     try
/*  88:    */     {
/*  89:303 */       if ((System.getProperty("jxl.lang") == null) || (System.getProperty("jxl.country") == null)) {
/*  90:306 */         this.locale = Locale.getDefault();
/*  91:    */       } else {
/*  92:310 */         this.locale = new Locale(System.getProperty("jxl.lang"), System.getProperty("jxl.country"));
/*  93:    */       }
/*  94:314 */       if (System.getProperty("jxl.encoding") != null) {
/*  95:316 */         this.encoding = System.getProperty("jxl.encoding");
/*  96:    */       }
/*  97:    */     }
/*  98:    */     catch (SecurityException e)
/*  99:    */     {
/* 100:321 */       logger.warn("Error accessing system properties.", e);
/* 101:322 */       this.locale = Locale.getDefault();
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setArrayGrowSize(int sz)
/* 106:    */   {
/* 107:337 */     this.arrayGrowSize = sz;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getArrayGrowSize()
/* 111:    */   {
/* 112:347 */     return this.arrayGrowSize;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setInitialFileSize(int sz)
/* 116:    */   {
/* 117:360 */     this.initialFileSize = sz;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getInitialFileSize()
/* 121:    */   {
/* 122:370 */     return this.initialFileSize;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean getDrawingsDisabled()
/* 126:    */   {
/* 127:380 */     return this.drawingsDisabled;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean getGCDisabled()
/* 131:    */   {
/* 132:390 */     return this.gcDisabled;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean getNamesDisabled()
/* 136:    */   {
/* 137:400 */     return this.namesDisabled;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setNamesDisabled(boolean b)
/* 141:    */   {
/* 142:410 */     this.namesDisabled = b;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setDrawingsDisabled(boolean b)
/* 146:    */   {
/* 147:420 */     this.drawingsDisabled = b;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setRationalization(boolean r)
/* 151:    */   {
/* 152:431 */     this.rationalizationDisabled = (!r);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean getRationalizationDisabled()
/* 156:    */   {
/* 157:441 */     return this.rationalizationDisabled;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean getMergedCellCheckingDisabled()
/* 161:    */   {
/* 162:451 */     return this.mergedCellCheckingDisabled;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setMergedCellChecking(boolean b)
/* 166:    */   {
/* 167:461 */     this.mergedCellCheckingDisabled = (!b);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setPropertySets(boolean r)
/* 171:    */   {
/* 172:474 */     this.propertySetsDisabled = (!r);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean getPropertySetsDisabled()
/* 176:    */   {
/* 177:484 */     return this.propertySetsDisabled;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setSuppressWarnings(boolean w)
/* 181:    */   {
/* 182:496 */     logger.setSuppressWarnings(w);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean getFormulaAdjust()
/* 186:    */   {
/* 187:507 */     return !this.formulaReferenceAdjustDisabled;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setFormulaAdjust(boolean b)
/* 191:    */   {
/* 192:517 */     this.formulaReferenceAdjustDisabled = (!b);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setLocale(Locale l)
/* 196:    */   {
/* 197:529 */     this.locale = l;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Locale getLocale()
/* 201:    */   {
/* 202:539 */     return this.locale;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String getEncoding()
/* 206:    */   {
/* 207:549 */     return this.encoding;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setEncoding(String enc)
/* 211:    */   {
/* 212:559 */     this.encoding = enc;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public FunctionNames getFunctionNames()
/* 216:    */   {
/* 217:571 */     if (this.functionNames == null)
/* 218:    */     {
/* 219:573 */       this.functionNames = ((FunctionNames)this.localeFunctionNames.get(this.locale));
/* 220:577 */       if (this.functionNames == null)
/* 221:    */       {
/* 222:579 */         this.functionNames = new FunctionNames(this.locale);
/* 223:580 */         this.localeFunctionNames.put(this.locale, this.functionNames);
/* 224:    */       }
/* 225:    */     }
/* 226:584 */     return this.functionNames;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public int getCharacterSet()
/* 230:    */   {
/* 231:595 */     return this.characterSet;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setCharacterSet(int cs)
/* 235:    */   {
/* 236:606 */     this.characterSet = cs;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setGCDisabled(boolean disabled)
/* 240:    */   {
/* 241:616 */     this.gcDisabled = disabled;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setIgnoreBlanks(boolean ignoreBlanks)
/* 245:    */   {
/* 246:626 */     this.ignoreBlankCells = ignoreBlanks;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public boolean getIgnoreBlanks()
/* 250:    */   {
/* 251:636 */     return this.ignoreBlankCells;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setCellValidationDisabled(boolean cv)
/* 255:    */   {
/* 256:646 */     this.cellValidationDisabled = cv;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean getCellValidationDisabled()
/* 260:    */   {
/* 261:656 */     return this.cellValidationDisabled;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String getExcelDisplayLanguage()
/* 265:    */   {
/* 266:666 */     return this.excelDisplayLanguage;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getExcelRegionalSettings()
/* 270:    */   {
/* 271:676 */     return this.excelRegionalSettings;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setExcelDisplayLanguage(String code)
/* 275:    */   {
/* 276:686 */     this.excelDisplayLanguage = code;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setExcelRegionalSettings(String code)
/* 280:    */   {
/* 281:696 */     this.excelRegionalSettings = code;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public boolean getAutoFilterDisabled()
/* 285:    */   {
/* 286:706 */     return this.autoFilterDisabled;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setAutoFilterDisabled(boolean disabled)
/* 290:    */   {
/* 291:716 */     this.autoFilterDisabled = disabled;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public boolean getUseTemporaryFileDuringWrite()
/* 295:    */   {
/* 296:731 */     return this.useTemporaryFileDuringWrite;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setUseTemporaryFileDuringWrite(boolean temp)
/* 300:    */   {
/* 301:746 */     this.useTemporaryFileDuringWrite = temp;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setTemporaryFileDuringWriteDirectory(File dir)
/* 305:    */   {
/* 306:760 */     this.temporaryFileDuringWriteDirectory = dir;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public File getTemporaryFileDuringWriteDirectory()
/* 310:    */   {
/* 311:774 */     return this.temporaryFileDuringWriteDirectory;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void setRefreshAll(boolean refreshAll)
/* 315:    */   {
/* 316:786 */     this.refreshAll = refreshAll;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public boolean getRefreshAll()
/* 320:    */   {
/* 321:797 */     return this.refreshAll;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public boolean getTemplate()
/* 325:    */   {
/* 326:806 */     return this.template;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void setTemplate(boolean template)
/* 330:    */   {
/* 331:815 */     this.template = template;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public boolean getExcel9File()
/* 335:    */   {
/* 336:825 */     return this.excel9file;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public void setExcel9File(boolean excel9file)
/* 340:    */   {
/* 341:833 */     this.excel9file = excel9file;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public boolean getWindowProtected()
/* 345:    */   {
/* 346:841 */     return this.windowProtected;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void setWindowProtected(boolean windowprotected)
/* 350:    */   {
/* 351:849 */     this.windowProtected = this.windowProtected;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public int getHideobj()
/* 355:    */   {
/* 356:861 */     return this.hideobj;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void setHideobj(int hideobj)
/* 360:    */   {
/* 361:873 */     this.hideobj = hideobj;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public String getWriteAccess()
/* 365:    */   {
/* 366:881 */     return this.writeAccess;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void setWriteAccess(String writeAccess)
/* 370:    */   {
/* 371:889 */     this.writeAccess = writeAccess;
/* 372:    */   }
/* 373:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.WorkbookSettings
 * JD-Core Version:    0.7.0.1
 */