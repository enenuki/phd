/*   1:    */ package org.apache.xml.dtm;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import javax.xml.transform.SourceLocator;
/*   8:    */ import org.apache.xml.res.XMLMessages;
/*   9:    */ 
/*  10:    */ public class DTMException
/*  11:    */   extends RuntimeException
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -775576419181334734L;
/*  14:    */   SourceLocator locator;
/*  15:    */   Throwable containedException;
/*  16:    */   
/*  17:    */   public SourceLocator getLocator()
/*  18:    */   {
/*  19: 51 */     return this.locator;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void setLocator(SourceLocator location)
/*  23:    */   {
/*  24: 61 */     this.locator = location;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Throwable getException()
/*  28:    */   {
/*  29: 75 */     return this.containedException;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Throwable getCause()
/*  33:    */   {
/*  34: 85 */     return this.containedException == this ? null : this.containedException;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public synchronized Throwable initCause(Throwable cause)
/*  38:    */   {
/*  39:116 */     if ((this.containedException == null) && (cause != null)) {
/*  40:117 */       throw new IllegalStateException(XMLMessages.createXMLMessage("ER_CANNOT_OVERWRITE_CAUSE", null));
/*  41:    */     }
/*  42:120 */     if (cause == this) {
/*  43:121 */       throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_SELF_CAUSATION_NOT_PERMITTED", null));
/*  44:    */     }
/*  45:125 */     this.containedException = cause;
/*  46:    */     
/*  47:127 */     return this;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public DTMException(String message)
/*  51:    */   {
/*  52:137 */     super(message);
/*  53:    */     
/*  54:139 */     this.containedException = null;
/*  55:140 */     this.locator = null;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public DTMException(Throwable e)
/*  59:    */   {
/*  60:150 */     super(e.getMessage());
/*  61:    */     
/*  62:152 */     this.containedException = e;
/*  63:153 */     this.locator = null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public DTMException(String message, Throwable e)
/*  67:    */   {
/*  68:168 */     super((message == null) || (message.length() == 0) ? e.getMessage() : message);
/*  69:    */     
/*  70:    */ 
/*  71:    */ 
/*  72:172 */     this.containedException = e;
/*  73:173 */     this.locator = null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public DTMException(String message, SourceLocator locator)
/*  77:    */   {
/*  78:188 */     super(message);
/*  79:    */     
/*  80:190 */     this.containedException = null;
/*  81:191 */     this.locator = locator;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public DTMException(String message, SourceLocator locator, Throwable e)
/*  85:    */   {
/*  86:205 */     super(message);
/*  87:    */     
/*  88:207 */     this.containedException = e;
/*  89:208 */     this.locator = locator;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getMessageAndLocation()
/*  93:    */   {
/*  94:217 */     StringBuffer sbuffer = new StringBuffer();
/*  95:218 */     String message = super.getMessage();
/*  96:220 */     if (null != message) {
/*  97:221 */       sbuffer.append(message);
/*  98:    */     }
/*  99:224 */     if (null != this.locator)
/* 100:    */     {
/* 101:225 */       String systemID = this.locator.getSystemId();
/* 102:226 */       int line = this.locator.getLineNumber();
/* 103:227 */       int column = this.locator.getColumnNumber();
/* 104:229 */       if (null != systemID)
/* 105:    */       {
/* 106:230 */         sbuffer.append("; SystemID: ");
/* 107:231 */         sbuffer.append(systemID);
/* 108:    */       }
/* 109:234 */       if (0 != line)
/* 110:    */       {
/* 111:235 */         sbuffer.append("; Line#: ");
/* 112:236 */         sbuffer.append(line);
/* 113:    */       }
/* 114:239 */       if (0 != column)
/* 115:    */       {
/* 116:240 */         sbuffer.append("; Column#: ");
/* 117:241 */         sbuffer.append(column);
/* 118:    */       }
/* 119:    */     }
/* 120:245 */     return sbuffer.toString();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getLocationAsString()
/* 124:    */   {
/* 125:256 */     if (null != this.locator)
/* 126:    */     {
/* 127:257 */       StringBuffer sbuffer = new StringBuffer();
/* 128:258 */       String systemID = this.locator.getSystemId();
/* 129:259 */       int line = this.locator.getLineNumber();
/* 130:260 */       int column = this.locator.getColumnNumber();
/* 131:262 */       if (null != systemID)
/* 132:    */       {
/* 133:263 */         sbuffer.append("; SystemID: ");
/* 134:264 */         sbuffer.append(systemID);
/* 135:    */       }
/* 136:267 */       if (0 != line)
/* 137:    */       {
/* 138:268 */         sbuffer.append("; Line#: ");
/* 139:269 */         sbuffer.append(line);
/* 140:    */       }
/* 141:272 */       if (0 != column)
/* 142:    */       {
/* 143:273 */         sbuffer.append("; Column#: ");
/* 144:274 */         sbuffer.append(column);
/* 145:    */       }
/* 146:277 */       return sbuffer.toString();
/* 147:    */     }
/* 148:279 */     return null;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void printStackTrace()
/* 152:    */   {
/* 153:289 */     printStackTrace(new PrintWriter(System.err, true));
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void printStackTrace(PrintStream s)
/* 157:    */   {
/* 158:299 */     printStackTrace(new PrintWriter(s));
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void printStackTrace(PrintWriter s)
/* 162:    */   {
/* 163:310 */     if (s == null) {
/* 164:311 */       s = new PrintWriter(System.err, true);
/* 165:    */     }
/* 166:    */     try
/* 167:    */     {
/* 168:315 */       String locInfo = getLocationAsString();
/* 169:317 */       if (null != locInfo) {
/* 170:318 */         s.println(locInfo);
/* 171:    */       }
/* 172:321 */       super.printStackTrace(s);
/* 173:    */     }
/* 174:    */     catch (Throwable e) {}
/* 175:324 */     boolean isJdk14OrHigher = false;
/* 176:    */     try
/* 177:    */     {
/* 178:326 */       Throwable.class.getMethod("getCause", null);
/* 179:327 */       isJdk14OrHigher = true;
/* 180:    */     }
/* 181:    */     catch (NoSuchMethodException nsme) {}
/* 182:335 */     if (!isJdk14OrHigher)
/* 183:    */     {
/* 184:336 */       Throwable exception = getException();
/* 185:338 */       for (int i = 0; (i < 10) && (null != exception); i++)
/* 186:    */       {
/* 187:339 */         s.println("---------");
/* 188:    */         try
/* 189:    */         {
/* 190:342 */           if ((exception instanceof DTMException))
/* 191:    */           {
/* 192:343 */             String locInfo = ((DTMException)exception).getLocationAsString();
/* 193:347 */             if (null != locInfo) {
/* 194:348 */               s.println(locInfo);
/* 195:    */             }
/* 196:    */           }
/* 197:352 */           exception.printStackTrace(s);
/* 198:    */         }
/* 199:    */         catch (Throwable e)
/* 200:    */         {
/* 201:354 */           s.println("Could not print stack trace...");
/* 202:    */         }
/* 203:    */         try
/* 204:    */         {
/* 205:358 */           Method meth = exception.getClass().getMethod("getException", null);
/* 206:362 */           if (null != meth)
/* 207:    */           {
/* 208:363 */             Throwable prev = exception;
/* 209:    */             
/* 210:365 */             exception = (Throwable)meth.invoke(exception, null);
/* 211:367 */             if (prev == exception) {
/* 212:    */               break;
/* 213:    */             }
/* 214:    */           }
/* 215:    */           else
/* 216:    */           {
/* 217:371 */             exception = null;
/* 218:    */           }
/* 219:    */         }
/* 220:    */         catch (InvocationTargetException ite)
/* 221:    */         {
/* 222:374 */           exception = null;
/* 223:    */         }
/* 224:    */         catch (IllegalAccessException iae)
/* 225:    */         {
/* 226:376 */           exception = null;
/* 227:    */         }
/* 228:    */         catch (NoSuchMethodException nsme)
/* 229:    */         {
/* 230:378 */           exception = null;
/* 231:    */         }
/* 232:    */       }
/* 233:    */     }
/* 234:    */   }
/* 235:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.DTMException
 * JD-Core Version:    0.7.0.1
 */