/*   1:    */ package org.apache.log4j.spi;
/*   2:    */ 
/*   3:    */ import java.io.InterruptedIOException;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringWriter;
/*   7:    */ import java.lang.reflect.InvocationTargetException;
/*   8:    */ import java.lang.reflect.Method;
/*   9:    */ import org.apache.log4j.Layout;
/*  10:    */ import org.apache.log4j.helpers.LogLog;
/*  11:    */ 
/*  12:    */ public class LocationInfo
/*  13:    */   implements Serializable
/*  14:    */ {
/*  15:    */   transient String lineNumber;
/*  16:    */   transient String fileName;
/*  17:    */   transient String className;
/*  18:    */   transient String methodName;
/*  19:    */   public String fullInfo;
/*  20: 60 */   private static StringWriter sw = new StringWriter();
/*  21: 61 */   private static PrintWriter pw = new PrintWriter(sw);
/*  22:    */   private static Method getStackTraceMethod;
/*  23:    */   private static Method getClassNameMethod;
/*  24:    */   private static Method getMethodNameMethod;
/*  25:    */   private static Method getFileNameMethod;
/*  26:    */   private static Method getLineNumberMethod;
/*  27:    */   public static final String NA = "?";
/*  28:    */   static final long serialVersionUID = -1325822038990805636L;
/*  29: 82 */   public static final LocationInfo NA_LOCATION_INFO = new LocationInfo("?", "?", "?", "?");
/*  30: 88 */   static boolean inVisualAge = false;
/*  31:    */   
/*  32:    */   static
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 91 */       inVisualAge = Class.forName("com.ibm.uvm.tools.DebugSupport") != null;
/*  37: 92 */       LogLog.debug("Detected IBM VisualAge environment.");
/*  38:    */     }
/*  39:    */     catch (Throwable e) {}
/*  40:    */     try
/*  41:    */     {
/*  42: 97 */       Class[] noArgs = null;
/*  43: 98 */       getStackTraceMethod = Throwable.class.getMethod("getStackTrace", noArgs);
/*  44: 99 */       Class stackTraceElementClass = Class.forName("java.lang.StackTraceElement");
/*  45:100 */       getClassNameMethod = stackTraceElementClass.getMethod("getClassName", noArgs);
/*  46:101 */       getMethodNameMethod = stackTraceElementClass.getMethod("getMethodName", noArgs);
/*  47:102 */       getFileNameMethod = stackTraceElementClass.getMethod("getFileName", noArgs);
/*  48:103 */       getLineNumberMethod = stackTraceElementClass.getMethod("getLineNumber", noArgs);
/*  49:    */     }
/*  50:    */     catch (ClassNotFoundException ex)
/*  51:    */     {
/*  52:105 */       LogLog.debug("LocationInfo will use pre-JDK 1.4 methods to determine location.");
/*  53:    */     }
/*  54:    */     catch (NoSuchMethodException ex)
/*  55:    */     {
/*  56:107 */       LogLog.debug("LocationInfo will use pre-JDK 1.4 methods to determine location.");
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public LocationInfo(Throwable t, String fqnOfCallingClass)
/*  61:    */   {
/*  62:134 */     if ((t == null) || (fqnOfCallingClass == null)) {
/*  63:135 */       return;
/*  64:    */     }
/*  65:136 */     if (getLineNumberMethod != null) {
/*  66:    */       try
/*  67:    */       {
/*  68:138 */         Object[] noArgs = null;
/*  69:139 */         Object[] elements = (Object[])getStackTraceMethod.invoke(t, noArgs);
/*  70:140 */         String prevClass = "?";
/*  71:141 */         for (int i = elements.length - 1; i >= 0; i--)
/*  72:    */         {
/*  73:142 */           String thisClass = (String)getClassNameMethod.invoke(elements[i], noArgs);
/*  74:143 */           if (fqnOfCallingClass.equals(thisClass))
/*  75:    */           {
/*  76:144 */             int caller = i + 1;
/*  77:145 */             if (caller < elements.length)
/*  78:    */             {
/*  79:146 */               this.className = prevClass;
/*  80:147 */               this.methodName = ((String)getMethodNameMethod.invoke(elements[caller], noArgs));
/*  81:148 */               this.fileName = ((String)getFileNameMethod.invoke(elements[caller], noArgs));
/*  82:149 */               if (this.fileName == null) {
/*  83:150 */                 this.fileName = "?";
/*  84:    */               }
/*  85:152 */               int line = ((Integer)getLineNumberMethod.invoke(elements[caller], noArgs)).intValue();
/*  86:153 */               if (line < 0) {
/*  87:154 */                 this.lineNumber = "?";
/*  88:    */               } else {
/*  89:156 */                 this.lineNumber = String.valueOf(line);
/*  90:    */               }
/*  91:158 */               StringBuffer buf = new StringBuffer();
/*  92:159 */               buf.append(this.className);
/*  93:160 */               buf.append(".");
/*  94:161 */               buf.append(this.methodName);
/*  95:162 */               buf.append("(");
/*  96:163 */               buf.append(this.fileName);
/*  97:164 */               buf.append(":");
/*  98:165 */               buf.append(this.lineNumber);
/*  99:166 */               buf.append(")");
/* 100:167 */               this.fullInfo = buf.toString();
/* 101:    */             }
/* 102:169 */             return;
/* 103:    */           }
/* 104:171 */           prevClass = thisClass;
/* 105:    */         }
/* 106:173 */         return;
/* 107:    */       }
/* 108:    */       catch (IllegalAccessException ex)
/* 109:    */       {
/* 110:175 */         LogLog.debug("LocationInfo failed using JDK 1.4 methods", ex);
/* 111:    */       }
/* 112:    */       catch (InvocationTargetException ex)
/* 113:    */       {
/* 114:177 */         if (((ex.getTargetException() instanceof InterruptedException)) || ((ex.getTargetException() instanceof InterruptedIOException))) {
/* 115:179 */           Thread.currentThread().interrupt();
/* 116:    */         }
/* 117:181 */         LogLog.debug("LocationInfo failed using JDK 1.4 methods", ex);
/* 118:    */       }
/* 119:    */       catch (RuntimeException ex)
/* 120:    */       {
/* 121:183 */         LogLog.debug("LocationInfo failed using JDK 1.4 methods", ex);
/* 122:    */       }
/* 123:    */     }
/* 124:    */     String s;
/* 125:189 */     synchronized (sw)
/* 126:    */     {
/* 127:190 */       t.printStackTrace(pw);
/* 128:191 */       s = sw.toString();
/* 129:192 */       sw.getBuffer().setLength(0);
/* 130:    */     }
/* 131:204 */     int ibegin = s.lastIndexOf(fqnOfCallingClass);
/* 132:205 */     if (ibegin == -1) {
/* 133:206 */       return;
/* 134:    */     }
/* 135:215 */     if ((ibegin + fqnOfCallingClass.length() < s.length()) && (s.charAt(ibegin + fqnOfCallingClass.length()) != '.'))
/* 136:    */     {
/* 137:217 */       int i = s.lastIndexOf(fqnOfCallingClass + ".");
/* 138:218 */       if (i != -1) {
/* 139:219 */         ibegin = i;
/* 140:    */       }
/* 141:    */     }
/* 142:224 */     ibegin = s.indexOf(Layout.LINE_SEP, ibegin);
/* 143:225 */     if (ibegin == -1) {
/* 144:226 */       return;
/* 145:    */     }
/* 146:227 */     ibegin += Layout.LINE_SEP_LEN;
/* 147:    */     
/* 148:    */ 
/* 149:230 */     int iend = s.indexOf(Layout.LINE_SEP, ibegin);
/* 150:231 */     if (iend == -1) {
/* 151:232 */       return;
/* 152:    */     }
/* 153:236 */     if (!inVisualAge)
/* 154:    */     {
/* 155:238 */       ibegin = s.lastIndexOf("at ", iend);
/* 156:239 */       if (ibegin == -1) {
/* 157:240 */         return;
/* 158:    */       }
/* 159:242 */       ibegin += 3;
/* 160:    */     }
/* 161:245 */     this.fullInfo = s.substring(ibegin, iend);
/* 162:    */   }
/* 163:    */   
/* 164:    */   private static final void appendFragment(StringBuffer buf, String fragment)
/* 165:    */   {
/* 166:258 */     if (fragment == null) {
/* 167:259 */       buf.append("?");
/* 168:    */     } else {
/* 169:261 */       buf.append(fragment);
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public LocationInfo(String file, String classname, String method, String line)
/* 174:    */   {
/* 175:279 */     this.fileName = file;
/* 176:280 */     this.className = classname;
/* 177:281 */     this.methodName = method;
/* 178:282 */     this.lineNumber = line;
/* 179:283 */     StringBuffer buf = new StringBuffer();
/* 180:284 */     appendFragment(buf, classname);
/* 181:285 */     buf.append(".");
/* 182:286 */     appendFragment(buf, method);
/* 183:287 */     buf.append("(");
/* 184:288 */     appendFragment(buf, file);
/* 185:289 */     buf.append(":");
/* 186:290 */     appendFragment(buf, line);
/* 187:291 */     buf.append(")");
/* 188:292 */     this.fullInfo = buf.toString();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String getClassName()
/* 192:    */   {
/* 193:301 */     if (this.fullInfo == null) {
/* 194:301 */       return "?";
/* 195:    */     }
/* 196:302 */     if (this.className == null)
/* 197:    */     {
/* 198:305 */       int iend = this.fullInfo.lastIndexOf('(');
/* 199:306 */       if (iend == -1)
/* 200:    */       {
/* 201:307 */         this.className = "?";
/* 202:    */       }
/* 203:    */       else
/* 204:    */       {
/* 205:309 */         iend = this.fullInfo.lastIndexOf('.', iend);
/* 206:    */         
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:320 */         int ibegin = 0;
/* 217:321 */         if (inVisualAge) {
/* 218:322 */           ibegin = this.fullInfo.lastIndexOf(' ', iend) + 1;
/* 219:    */         }
/* 220:325 */         if (iend == -1) {
/* 221:326 */           this.className = "?";
/* 222:    */         } else {
/* 223:328 */           this.className = this.fullInfo.substring(ibegin, iend);
/* 224:    */         }
/* 225:    */       }
/* 226:    */     }
/* 227:331 */     return this.className;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public String getFileName()
/* 231:    */   {
/* 232:341 */     if (this.fullInfo == null) {
/* 233:341 */       return "?";
/* 234:    */     }
/* 235:343 */     if (this.fileName == null)
/* 236:    */     {
/* 237:344 */       int iend = this.fullInfo.lastIndexOf(':');
/* 238:345 */       if (iend == -1)
/* 239:    */       {
/* 240:346 */         this.fileName = "?";
/* 241:    */       }
/* 242:    */       else
/* 243:    */       {
/* 244:348 */         int ibegin = this.fullInfo.lastIndexOf('(', iend - 1);
/* 245:349 */         this.fileName = this.fullInfo.substring(ibegin + 1, iend);
/* 246:    */       }
/* 247:    */     }
/* 248:352 */     return this.fileName;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String getLineNumber()
/* 252:    */   {
/* 253:362 */     if (this.fullInfo == null) {
/* 254:362 */       return "?";
/* 255:    */     }
/* 256:364 */     if (this.lineNumber == null)
/* 257:    */     {
/* 258:365 */       int iend = this.fullInfo.lastIndexOf(')');
/* 259:366 */       int ibegin = this.fullInfo.lastIndexOf(':', iend - 1);
/* 260:367 */       if (ibegin == -1) {
/* 261:368 */         this.lineNumber = "?";
/* 262:    */       } else {
/* 263:370 */         this.lineNumber = this.fullInfo.substring(ibegin + 1, iend);
/* 264:    */       }
/* 265:    */     }
/* 266:372 */     return this.lineNumber;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getMethodName()
/* 270:    */   {
/* 271:380 */     if (this.fullInfo == null) {
/* 272:380 */       return "?";
/* 273:    */     }
/* 274:381 */     if (this.methodName == null)
/* 275:    */     {
/* 276:382 */       int iend = this.fullInfo.lastIndexOf('(');
/* 277:383 */       int ibegin = this.fullInfo.lastIndexOf('.', iend);
/* 278:384 */       if (ibegin == -1) {
/* 279:385 */         this.methodName = "?";
/* 280:    */       } else {
/* 281:387 */         this.methodName = this.fullInfo.substring(ibegin + 1, iend);
/* 282:    */       }
/* 283:    */     }
/* 284:389 */     return this.methodName;
/* 285:    */   }
/* 286:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.LocationInfo
 * JD-Core Version:    0.7.0.1
 */