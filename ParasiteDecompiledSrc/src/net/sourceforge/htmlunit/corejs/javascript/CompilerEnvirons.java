/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.util.Set;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.ErrorCollector;
/*   5:    */ 
/*   6:    */ public class CompilerEnvirons
/*   7:    */ {
/*   8:    */   private ErrorReporter errorReporter;
/*   9:    */   private int languageVersion;
/*  10:    */   private boolean generateDebugInfo;
/*  11:    */   private boolean useDynamicScope;
/*  12:    */   private boolean reservedKeywordAsIdentifier;
/*  13:    */   private boolean allowMemberExprAsFunctionName;
/*  14:    */   private boolean xmlAvailable;
/*  15:    */   private int optimizationLevel;
/*  16:    */   private boolean generatingSource;
/*  17:    */   private boolean strictMode;
/*  18:    */   private boolean warningAsError;
/*  19:    */   private boolean generateObserverCount;
/*  20:    */   private boolean recordingComments;
/*  21:    */   private boolean recordingLocalJsDocComments;
/*  22:    */   private boolean recoverFromErrors;
/*  23:    */   private boolean warnTrailingComma;
/*  24:    */   private boolean ideMode;
/*  25:    */   private boolean allowSharpComments;
/*  26:    */   Set<String> activationNames;
/*  27:    */   
/*  28:    */   public CompilerEnvirons()
/*  29:    */   {
/*  30: 51 */     this.errorReporter = DefaultErrorReporter.instance;
/*  31: 52 */     this.languageVersion = 0;
/*  32: 53 */     this.generateDebugInfo = true;
/*  33: 54 */     this.useDynamicScope = false;
/*  34: 55 */     this.reservedKeywordAsIdentifier = false;
/*  35: 56 */     this.allowMemberExprAsFunctionName = false;
/*  36: 57 */     this.xmlAvailable = true;
/*  37: 58 */     this.optimizationLevel = 0;
/*  38: 59 */     this.generatingSource = true;
/*  39: 60 */     this.strictMode = false;
/*  40: 61 */     this.warningAsError = false;
/*  41: 62 */     this.generateObserverCount = false;
/*  42: 63 */     this.allowSharpComments = false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void initFromContext(Context cx)
/*  46:    */   {
/*  47: 68 */     setErrorReporter(cx.getErrorReporter());
/*  48: 69 */     this.languageVersion = cx.getLanguageVersion();
/*  49: 70 */     this.useDynamicScope = cx.compileFunctionsWithDynamicScopeFlag;
/*  50: 71 */     this.generateDebugInfo = ((!cx.isGeneratingDebugChanged()) || (cx.isGeneratingDebug()));
/*  51:    */     
/*  52: 73 */     this.reservedKeywordAsIdentifier = cx.hasFeature(3);
/*  53:    */     
/*  54: 75 */     this.allowMemberExprAsFunctionName = cx.hasFeature(2);
/*  55:    */     
/*  56: 77 */     this.strictMode = cx.hasFeature(11);
/*  57:    */     
/*  58: 79 */     this.warningAsError = cx.hasFeature(12);
/*  59: 80 */     this.xmlAvailable = cx.hasFeature(6);
/*  60:    */     
/*  61:    */ 
/*  62: 83 */     this.optimizationLevel = cx.getOptimizationLevel();
/*  63:    */     
/*  64: 85 */     this.generatingSource = cx.isGeneratingSource();
/*  65: 86 */     this.activationNames = cx.activationNames;
/*  66:    */     
/*  67:    */ 
/*  68: 89 */     this.generateObserverCount = cx.generateObserverCount;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public final ErrorReporter getErrorReporter()
/*  72:    */   {
/*  73: 94 */     return this.errorReporter;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setErrorReporter(ErrorReporter errorReporter)
/*  77:    */   {
/*  78: 99 */     if (errorReporter == null) {
/*  79: 99 */       throw new IllegalArgumentException();
/*  80:    */     }
/*  81:100 */     this.errorReporter = errorReporter;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public final int getLanguageVersion()
/*  85:    */   {
/*  86:105 */     return this.languageVersion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setLanguageVersion(int languageVersion)
/*  90:    */   {
/*  91:110 */     Context.checkLanguageVersion(languageVersion);
/*  92:111 */     this.languageVersion = languageVersion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public final boolean isGenerateDebugInfo()
/*  96:    */   {
/*  97:116 */     return this.generateDebugInfo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setGenerateDebugInfo(boolean flag)
/* 101:    */   {
/* 102:121 */     this.generateDebugInfo = flag;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public final boolean isUseDynamicScope()
/* 106:    */   {
/* 107:126 */     return this.useDynamicScope;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public final boolean isReservedKeywordAsIdentifier()
/* 111:    */   {
/* 112:131 */     return this.reservedKeywordAsIdentifier;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setReservedKeywordAsIdentifier(boolean flag)
/* 116:    */   {
/* 117:136 */     this.reservedKeywordAsIdentifier = flag;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public final boolean isAllowMemberExprAsFunctionName()
/* 121:    */   {
/* 122:145 */     return this.allowMemberExprAsFunctionName;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setAllowMemberExprAsFunctionName(boolean flag)
/* 126:    */   {
/* 127:150 */     this.allowMemberExprAsFunctionName = flag;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public final boolean isXmlAvailable()
/* 131:    */   {
/* 132:155 */     return this.xmlAvailable;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setXmlAvailable(boolean flag)
/* 136:    */   {
/* 137:160 */     this.xmlAvailable = flag;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public final int getOptimizationLevel()
/* 141:    */   {
/* 142:165 */     return this.optimizationLevel;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setOptimizationLevel(int level)
/* 146:    */   {
/* 147:170 */     Context.checkOptimizationLevel(level);
/* 148:171 */     this.optimizationLevel = level;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public final boolean isGeneratingSource()
/* 152:    */   {
/* 153:176 */     return this.generatingSource;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean getWarnTrailingComma()
/* 157:    */   {
/* 158:180 */     return this.warnTrailingComma;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setWarnTrailingComma(boolean warn)
/* 162:    */   {
/* 163:184 */     this.warnTrailingComma = warn;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public final boolean isStrictMode()
/* 167:    */   {
/* 168:189 */     return this.strictMode;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setStrictMode(boolean strict)
/* 172:    */   {
/* 173:194 */     this.strictMode = strict;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public final boolean reportWarningAsError()
/* 177:    */   {
/* 178:199 */     return this.warningAsError;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setGeneratingSource(boolean generatingSource)
/* 182:    */   {
/* 183:213 */     this.generatingSource = generatingSource;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean isGenerateObserverCount()
/* 187:    */   {
/* 188:221 */     return this.generateObserverCount;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setGenerateObserverCount(boolean generateObserverCount)
/* 192:    */   {
/* 193:236 */     this.generateObserverCount = generateObserverCount;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean isRecordingComments()
/* 197:    */   {
/* 198:240 */     return this.recordingComments;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setRecordingComments(boolean record)
/* 202:    */   {
/* 203:244 */     this.recordingComments = record;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean isRecordingLocalJsDocComments()
/* 207:    */   {
/* 208:248 */     return this.recordingLocalJsDocComments;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setRecordingLocalJsDocComments(boolean record)
/* 212:    */   {
/* 213:252 */     this.recordingLocalJsDocComments = record;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setRecoverFromErrors(boolean recover)
/* 217:    */   {
/* 218:261 */     this.recoverFromErrors = recover;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean recoverFromErrors()
/* 222:    */   {
/* 223:265 */     return this.recoverFromErrors;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setIdeMode(boolean ide)
/* 227:    */   {
/* 228:273 */     this.ideMode = ide;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean isIdeMode()
/* 232:    */   {
/* 233:277 */     return this.ideMode;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Set<String> getActivationNames()
/* 237:    */   {
/* 238:281 */     return this.activationNames;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setActivationNames(Set<String> activationNames)
/* 242:    */   {
/* 243:285 */     this.activationNames = activationNames;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setAllowSharpComments(boolean allow)
/* 247:    */   {
/* 248:292 */     this.allowSharpComments = allow;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean getAllowSharpComments()
/* 252:    */   {
/* 253:296 */     return this.allowSharpComments;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public static CompilerEnvirons ideEnvirons()
/* 257:    */   {
/* 258:305 */     CompilerEnvirons env = new CompilerEnvirons();
/* 259:306 */     env.setRecoverFromErrors(true);
/* 260:307 */     env.setRecordingComments(true);
/* 261:308 */     env.setStrictMode(true);
/* 262:309 */     env.setWarnTrailingComma(true);
/* 263:310 */     env.setLanguageVersion(170);
/* 264:311 */     env.setReservedKeywordAsIdentifier(true);
/* 265:312 */     env.setIdeMode(true);
/* 266:313 */     env.setErrorReporter(new ErrorCollector());
/* 267:314 */     return env;
/* 268:    */   }
/* 269:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.CompilerEnvirons
 * JD-Core Version:    0.7.0.1
 */