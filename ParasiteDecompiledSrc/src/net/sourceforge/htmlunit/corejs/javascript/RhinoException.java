/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.CharArrayWriter;
/*   4:    */ import java.io.FilenameFilter;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.regex.Matcher;
/*  10:    */ import java.util.regex.Pattern;
/*  11:    */ 
/*  12:    */ public abstract class RhinoException
/*  13:    */   extends RuntimeException
/*  14:    */ {
/*  15:    */   RhinoException()
/*  16:    */   {
/*  17: 62 */     Evaluator e = Context.createInterpreter();
/*  18: 63 */     if (e != null) {
/*  19: 64 */       e.captureStackInfo(this);
/*  20:    */     }
/*  21:    */   }
/*  22:    */   
/*  23:    */   RhinoException(String details)
/*  24:    */   {
/*  25: 69 */     super(details);
/*  26: 70 */     Evaluator e = Context.createInterpreter();
/*  27: 71 */     if (e != null) {
/*  28: 72 */       e.captureStackInfo(this);
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public final String getMessage()
/*  33:    */   {
/*  34: 78 */     String details = details();
/*  35: 79 */     if ((this.sourceName == null) || (this.lineNumber <= 0)) {
/*  36: 80 */       return details;
/*  37:    */     }
/*  38: 82 */     StringBuffer buf = new StringBuffer(details);
/*  39: 83 */     buf.append(" (");
/*  40: 84 */     if (this.sourceName != null) {
/*  41: 85 */       buf.append(this.sourceName);
/*  42:    */     }
/*  43: 87 */     if (this.lineNumber > 0)
/*  44:    */     {
/*  45: 88 */       buf.append('#');
/*  46: 89 */       buf.append(this.lineNumber);
/*  47:    */     }
/*  48: 91 */     buf.append(')');
/*  49: 92 */     return buf.toString();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String details()
/*  53:    */   {
/*  54: 97 */     return super.getMessage();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public final String sourceName()
/*  58:    */   {
/*  59:106 */     return this.sourceName;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final void initSourceName(String sourceName)
/*  63:    */   {
/*  64:119 */     if (sourceName == null) {
/*  65:119 */       throw new IllegalArgumentException();
/*  66:    */     }
/*  67:120 */     if (this.sourceName != null) {
/*  68:120 */       throw new IllegalStateException();
/*  69:    */     }
/*  70:121 */     this.sourceName = sourceName;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final int lineNumber()
/*  74:    */   {
/*  75:130 */     return this.lineNumber;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final void initLineNumber(int lineNumber)
/*  79:    */   {
/*  80:143 */     if (lineNumber <= 0) {
/*  81:143 */       throw new IllegalArgumentException(String.valueOf(lineNumber));
/*  82:    */     }
/*  83:144 */     if (this.lineNumber > 0) {
/*  84:144 */       throw new IllegalStateException();
/*  85:    */     }
/*  86:145 */     this.lineNumber = lineNumber;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public final int columnNumber()
/*  90:    */   {
/*  91:153 */     return this.columnNumber;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public final void initColumnNumber(int columnNumber)
/*  95:    */   {
/*  96:166 */     if (columnNumber <= 0) {
/*  97:166 */       throw new IllegalArgumentException(String.valueOf(columnNumber));
/*  98:    */     }
/*  99:167 */     if (this.columnNumber > 0) {
/* 100:167 */       throw new IllegalStateException();
/* 101:    */     }
/* 102:168 */     this.columnNumber = columnNumber;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public final String lineSource()
/* 106:    */   {
/* 107:176 */     return this.lineSource;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public final void initLineSource(String lineSource)
/* 111:    */   {
/* 112:189 */     if (lineSource == null) {
/* 113:189 */       throw new IllegalArgumentException();
/* 114:    */     }
/* 115:190 */     if (this.lineSource != null) {
/* 116:190 */       throw new IllegalStateException();
/* 117:    */     }
/* 118:191 */     this.lineSource = lineSource;
/* 119:    */   }
/* 120:    */   
/* 121:    */   final void recordErrorOrigin(String sourceName, int lineNumber, String lineSource, int columnNumber)
/* 122:    */   {
/* 123:198 */     if (lineNumber == -1) {
/* 124:199 */       lineNumber = 0;
/* 125:    */     }
/* 126:202 */     if (sourceName != null) {
/* 127:203 */       initSourceName(sourceName);
/* 128:    */     }
/* 129:205 */     if (lineNumber != 0) {
/* 130:206 */       initLineNumber(lineNumber);
/* 131:    */     }
/* 132:208 */     if (lineSource != null) {
/* 133:209 */       initLineSource(lineSource);
/* 134:    */     }
/* 135:211 */     if (columnNumber != 0) {
/* 136:212 */       initColumnNumber(columnNumber);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   private String generateStackTrace()
/* 141:    */   {
/* 142:219 */     CharArrayWriter writer = new CharArrayWriter();
/* 143:220 */     super.printStackTrace(new PrintWriter(writer));
/* 144:221 */     String origStackTrace = writer.toString();
/* 145:222 */     Evaluator e = Context.createInterpreter();
/* 146:223 */     if (e != null) {
/* 147:224 */       return e.getPatchedStack(this, origStackTrace);
/* 148:    */     }
/* 149:225 */     return null;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getScriptStackTrace()
/* 153:    */   {
/* 154:238 */     StringBuilder buffer = new StringBuilder();
/* 155:239 */     String lineSeparator = SecurityUtilities.getSystemProperty("line.separator");
/* 156:240 */     ScriptStackElement[] stack = getScriptStack();
/* 157:241 */     for (ScriptStackElement elem : stack)
/* 158:    */     {
/* 159:242 */       if (useMozillaStackStyle) {
/* 160:243 */         elem.renderMozillaStyle(buffer);
/* 161:    */       } else {
/* 162:245 */         elem.renderJavaStyle(buffer);
/* 163:    */       }
/* 164:247 */       buffer.append(lineSeparator);
/* 165:    */     }
/* 166:249 */     return buffer.toString();
/* 167:    */   }
/* 168:    */   
/* 169:    */   /**
/* 170:    */    * @deprecated
/* 171:    */    */
/* 172:    */   public String getScriptStackTrace(FilenameFilter filter)
/* 173:    */   {
/* 174:263 */     return getScriptStackTrace();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public ScriptStackElement[] getScriptStack()
/* 178:    */   {
/* 179:276 */     List<ScriptStackElement> list = new ArrayList();
/* 180:277 */     ScriptStackElement[][] interpreterStack = (ScriptStackElement[][])null;
/* 181:278 */     if (this.interpreterStackInfo != null)
/* 182:    */     {
/* 183:279 */       Evaluator interpreter = Context.createInterpreter();
/* 184:280 */       if ((interpreter instanceof Interpreter)) {
/* 185:281 */         interpreterStack = ((Interpreter)interpreter).getScriptStackElements(this);
/* 186:    */       }
/* 187:    */     }
/* 188:283 */     int interpreterStackIndex = 0;
/* 189:284 */     StackTraceElement[] stack = getStackTrace();
/* 190:    */     
/* 191:    */ 
/* 192:    */ 
/* 193:288 */     Pattern pattern = Pattern.compile("_c_(.*)_\\d+");
/* 194:289 */     for (StackTraceElement e : stack)
/* 195:    */     {
/* 196:290 */       String fileName = e.getFileName();
/* 197:291 */       if ((e.getMethodName().startsWith("_c_")) && (e.getLineNumber() > -1) && (fileName != null) && (!fileName.endsWith(".java")))
/* 198:    */       {
/* 199:295 */         String methodName = e.getMethodName();
/* 200:296 */         Matcher match = pattern.matcher(methodName);
/* 201:    */         
/* 202:    */ 
/* 203:299 */         methodName = (!"_c_script_0".equals(methodName)) && (match.find()) ? match.group(1) : null;
/* 204:    */         
/* 205:301 */         list.add(new ScriptStackElement(fileName, methodName, e.getLineNumber()));
/* 206:    */       }
/* 207:302 */       else if (("net.sourceforge.htmlunit.corejs.javascript.Interpreter".equals(e.getClassName())) && ("interpretLoop".equals(e.getMethodName())) && (interpreterStack != null) && (interpreterStack.length > interpreterStackIndex))
/* 208:    */       {
/* 209:306 */         for (ScriptStackElement elem : interpreterStack[(interpreterStackIndex++)]) {
/* 210:307 */           list.add(elem);
/* 211:    */         }
/* 212:    */       }
/* 213:    */     }
/* 214:311 */     return (ScriptStackElement[])list.toArray(new ScriptStackElement[list.size()]);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void printStackTrace(PrintWriter s)
/* 218:    */   {
/* 219:318 */     if (this.interpreterStackInfo == null) {
/* 220:319 */       super.printStackTrace(s);
/* 221:    */     } else {
/* 222:321 */       s.print(generateStackTrace());
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void printStackTrace(PrintStream s)
/* 227:    */   {
/* 228:328 */     if (this.interpreterStackInfo == null) {
/* 229:329 */       super.printStackTrace(s);
/* 230:    */     } else {
/* 231:331 */       s.print(generateStackTrace());
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public static boolean usesMozillaStackStyle()
/* 236:    */   {
/* 237:346 */     return useMozillaStackStyle;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public static void useMozillaStackStyle(boolean flag)
/* 241:    */   {
/* 242:360 */     useMozillaStackStyle = flag;
/* 243:    */   }
/* 244:    */   
/* 245:363 */   private static boolean useMozillaStackStyle = false;
/* 246:    */   private String sourceName;
/* 247:    */   private int lineNumber;
/* 248:    */   private String lineSource;
/* 249:    */   private int columnNumber;
/* 250:    */   Object interpreterStackInfo;
/* 251:    */   int[] interpreterLineData;
/* 252:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.RhinoException
 * JD-Core Version:    0.7.0.1
 */