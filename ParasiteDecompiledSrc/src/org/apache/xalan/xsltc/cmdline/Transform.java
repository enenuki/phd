/*   1:    */ package org.apache.xalan.xsltc.cmdline;
/*   2:    */ 
/*   3:    */ import java.io.FileNotFoundException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.net.MalformedURLException;
/*   6:    */ import java.net.UnknownHostException;
/*   7:    */ import java.util.Vector;
/*   8:    */ import javax.xml.parsers.SAXParser;
/*   9:    */ import javax.xml.parsers.SAXParserFactory;
/*  10:    */ import javax.xml.transform.sax.SAXSource;
/*  11:    */ import org.apache.xalan.xsltc.DOMEnhancedForDTM;
/*  12:    */ import org.apache.xalan.xsltc.StripFilter;
/*  13:    */ import org.apache.xalan.xsltc.TransletException;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  15:    */ import org.apache.xalan.xsltc.dom.DOMWSFilter;
/*  16:    */ import org.apache.xalan.xsltc.dom.XSLTCDTMManager;
/*  17:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*  18:    */ import org.apache.xalan.xsltc.runtime.Parameter;
/*  19:    */ import org.apache.xalan.xsltc.runtime.output.TransletOutputHandlerFactory;
/*  20:    */ import org.apache.xml.dtm.DTMWSFilter;
/*  21:    */ import org.apache.xml.serializer.SerializationHandler;
/*  22:    */ import org.xml.sax.InputSource;
/*  23:    */ import org.xml.sax.SAXException;
/*  24:    */ import org.xml.sax.XMLReader;
/*  25:    */ 
/*  26:    */ public final class Transform
/*  27:    */ {
/*  28:    */   private SerializationHandler _handler;
/*  29:    */   private String _fileName;
/*  30:    */   private String _className;
/*  31:    */   private String _jarFileSrc;
/*  32: 64 */   private boolean _isJarFileSpecified = false;
/*  33: 65 */   private Vector _params = null;
/*  34:    */   private boolean _uri;
/*  35:    */   private boolean _debug;
/*  36:    */   private int _iterations;
/*  37:    */   
/*  38:    */   public Transform(String className, String fileName, boolean uri, boolean debug, int iterations)
/*  39:    */   {
/*  40: 71 */     this._fileName = fileName;
/*  41: 72 */     this._className = className;
/*  42: 73 */     this._uri = uri;
/*  43: 74 */     this._debug = debug;
/*  44: 75 */     this._iterations = iterations;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getFileName()
/*  48:    */   {
/*  49: 78 */     return this._fileName;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getClassName()
/*  53:    */   {
/*  54: 79 */     return this._className;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setParameters(Vector params)
/*  58:    */   {
/*  59: 82 */     this._params = params;
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void setJarFileInputSrc(boolean flag, String jarFile)
/*  63:    */   {
/*  64: 91 */     this._isJarFileSpecified = flag;
/*  65:    */     
/*  66: 93 */     this._jarFileSrc = jarFile;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private void doTransform()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73: 98 */       Class clazz = ObjectFactory.findProviderClass(this._className, ObjectFactory.findClassLoader(), true);
/*  74:    */       
/*  75:100 */       AbstractTranslet translet = (AbstractTranslet)clazz.newInstance();
/*  76:101 */       translet.postInitialization();
/*  77:    */       
/*  78:    */ 
/*  79:104 */       SAXParserFactory factory = SAXParserFactory.newInstance();
/*  80:    */       try
/*  81:    */       {
/*  82:106 */         factory.setFeature("http://xml.org/sax/features/namespaces", true);
/*  83:    */       }
/*  84:    */       catch (Exception e)
/*  85:    */       {
/*  86:109 */         factory.setNamespaceAware(true);
/*  87:    */       }
/*  88:111 */       SAXParser parser = factory.newSAXParser();
/*  89:112 */       XMLReader reader = parser.getXMLReader();
/*  90:    */       
/*  91:    */ 
/*  92:115 */       XSLTCDTMManager dtmManager = (XSLTCDTMManager)XSLTCDTMManager.getDTMManagerClass().newInstance();
/*  93:    */       DTMWSFilter wsfilter;
/*  94:120 */       if ((translet != null) && ((translet instanceof StripFilter))) {
/*  95:121 */         wsfilter = new DOMWSFilter(translet);
/*  96:    */       } else {
/*  97:123 */         wsfilter = null;
/*  98:    */       }
/*  99:126 */       DOMEnhancedForDTM dom = (DOMEnhancedForDTM)dtmManager.getDTM(new SAXSource(reader, new InputSource(this._fileName)), false, wsfilter, true, false, translet.hasIdCall());
/* 100:    */       
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:131 */       dom.setDocumentURI(this._fileName);
/* 105:132 */       translet.prepassDocument(dom);
/* 106:    */       
/* 107:    */ 
/* 108:135 */       int n = this._params.size();
/* 109:136 */       for (int i = 0; i < n; i++)
/* 110:    */       {
/* 111:137 */         Parameter param = (Parameter)this._params.elementAt(i);
/* 112:138 */         translet.addParameter(param._name, param._value);
/* 113:    */       }
/* 114:142 */       TransletOutputHandlerFactory tohFactory = TransletOutputHandlerFactory.newInstance();
/* 115:    */       
/* 116:144 */       tohFactory.setOutputType(0);
/* 117:145 */       tohFactory.setEncoding(translet._encoding);
/* 118:146 */       tohFactory.setOutputMethod(translet._method);
/* 119:148 */       if (this._iterations == -1)
/* 120:    */       {
/* 121:149 */         translet.transform(dom, tohFactory.getSerializationHandler());
/* 122:    */       }
/* 123:151 */       else if (this._iterations > 0)
/* 124:    */       {
/* 125:152 */         long mm = System.currentTimeMillis();
/* 126:153 */         for (int i = 0; i < this._iterations; i++) {
/* 127:154 */           translet.transform(dom, tohFactory.getSerializationHandler());
/* 128:    */         }
/* 129:157 */         mm = System.currentTimeMillis() - mm;
/* 130:    */         
/* 131:159 */         System.err.println("\n<!--");
/* 132:160 */         System.err.println("  transform  = " + mm / this._iterations + " ms");
/* 133:    */         
/* 134:    */ 
/* 135:163 */         System.err.println("  throughput = " + 1000.0D / (mm / this._iterations) + " tps");
/* 136:    */         
/* 137:    */ 
/* 138:    */ 
/* 139:167 */         System.err.println("-->");
/* 140:    */       }
/* 141:    */     }
/* 142:    */     catch (TransletException e)
/* 143:    */     {
/* 144:171 */       if (this._debug) {
/* 145:171 */         e.printStackTrace();
/* 146:    */       }
/* 147:172 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + e.getMessage());
/* 148:    */     }
/* 149:    */     catch (RuntimeException e)
/* 150:    */     {
/* 151:176 */       if (this._debug) {
/* 152:176 */         e.printStackTrace();
/* 153:    */       }
/* 154:177 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + e.getMessage());
/* 155:    */     }
/* 156:    */     catch (FileNotFoundException e)
/* 157:    */     {
/* 158:181 */       if (this._debug) {
/* 159:181 */         e.printStackTrace();
/* 160:    */       }
/* 161:182 */       ErrorMsg err = new ErrorMsg("FILE_NOT_FOUND_ERR", this._fileName);
/* 162:183 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + err.toString());
/* 163:    */     }
/* 164:    */     catch (MalformedURLException e)
/* 165:    */     {
/* 166:187 */       if (this._debug) {
/* 167:187 */         e.printStackTrace();
/* 168:    */       }
/* 169:188 */       ErrorMsg err = new ErrorMsg("INVALID_URI_ERR", this._fileName);
/* 170:189 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + err.toString());
/* 171:    */     }
/* 172:    */     catch (ClassNotFoundException e)
/* 173:    */     {
/* 174:193 */       if (this._debug) {
/* 175:193 */         e.printStackTrace();
/* 176:    */       }
/* 177:194 */       ErrorMsg err = new ErrorMsg("CLASS_NOT_FOUND_ERR", this._className);
/* 178:195 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + err.toString());
/* 179:    */     }
/* 180:    */     catch (UnknownHostException e)
/* 181:    */     {
/* 182:199 */       if (this._debug) {
/* 183:199 */         e.printStackTrace();
/* 184:    */       }
/* 185:200 */       ErrorMsg err = new ErrorMsg("INVALID_URI_ERR", this._fileName);
/* 186:201 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + err.toString());
/* 187:    */     }
/* 188:    */     catch (SAXException e)
/* 189:    */     {
/* 190:205 */       Exception ex = e.getException();
/* 191:206 */       if (this._debug)
/* 192:    */       {
/* 193:207 */         if (ex != null) {
/* 194:207 */           ex.printStackTrace();
/* 195:    */         }
/* 196:208 */         e.printStackTrace();
/* 197:    */       }
/* 198:210 */       System.err.print(new ErrorMsg("RUNTIME_ERROR_KEY"));
/* 199:211 */       if (ex != null) {
/* 200:212 */         System.err.println(ex.getMessage());
/* 201:    */       } else {
/* 202:214 */         System.err.println(e.getMessage());
/* 203:    */       }
/* 204:    */     }
/* 205:    */     catch (Exception e)
/* 206:    */     {
/* 207:217 */       if (this._debug) {
/* 208:217 */         e.printStackTrace();
/* 209:    */       }
/* 210:218 */       System.err.println(new ErrorMsg("RUNTIME_ERROR_KEY") + e.getMessage());
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static void printUsage()
/* 215:    */   {
/* 216:224 */     System.err.println(new ErrorMsg("TRANSFORM_USAGE_STR"));
/* 217:    */   }
/* 218:    */   
/* 219:    */   public static void main(String[] args)
/* 220:    */   {
/* 221:    */     try
/* 222:    */     {
/* 223:229 */       if (args.length > 0)
/* 224:    */       {
/* 225:231 */         int iterations = -1;
/* 226:232 */         boolean uri = false;boolean debug = false;
/* 227:233 */         boolean isJarFileSpecified = false;
/* 228:234 */         String jarFile = null;
/* 229:237 */         for (int i = 0; (i < args.length) && (args[i].charAt(0) == '-'); i++) {
/* 230:238 */           if (args[i].equals("-u"))
/* 231:    */           {
/* 232:239 */             uri = true;
/* 233:    */           }
/* 234:241 */           else if (args[i].equals("-x"))
/* 235:    */           {
/* 236:242 */             debug = true;
/* 237:    */           }
/* 238:244 */           else if (args[i].equals("-j"))
/* 239:    */           {
/* 240:245 */             isJarFileSpecified = true;
/* 241:246 */             jarFile = args[(++i)];
/* 242:    */           }
/* 243:248 */           else if (args[i].equals("-n"))
/* 244:    */           {
/* 245:    */             try
/* 246:    */             {
/* 247:250 */               iterations = Integer.parseInt(args[(++i)]);
/* 248:    */             }
/* 249:    */             catch (NumberFormatException e) {}
/* 250:    */           }
/* 251:    */           else
/* 252:    */           {
/* 253:257 */             printUsage();
/* 254:    */           }
/* 255:    */         }
/* 256:262 */         if (args.length - i < 2) {
/* 257:262 */           printUsage();
/* 258:    */         }
/* 259:265 */         Transform handler = new Transform(args[(i + 1)], args[i], uri, debug, iterations);
/* 260:    */         
/* 261:267 */         handler.setJarFileInputSrc(isJarFileSpecified, jarFile);
/* 262:    */         
/* 263:    */ 
/* 264:270 */         Vector params = new Vector();
/* 265:271 */         for (i += 2; i < args.length; i++)
/* 266:    */         {
/* 267:272 */           int equal = args[i].indexOf('=');
/* 268:273 */           if (equal > 0)
/* 269:    */           {
/* 270:274 */             String name = args[i].substring(0, equal);
/* 271:275 */             String value = args[i].substring(equal + 1);
/* 272:276 */             params.addElement(new Parameter(name, value));
/* 273:    */           }
/* 274:    */           else
/* 275:    */           {
/* 276:279 */             printUsage();
/* 277:    */           }
/* 278:    */         }
/* 279:283 */         if (i == args.length)
/* 280:    */         {
/* 281:284 */           handler.setParameters(params);
/* 282:285 */           handler.doTransform();
/* 283:    */         }
/* 284:    */       }
/* 285:    */       else
/* 286:    */       {
/* 287:288 */         printUsage();
/* 288:    */       }
/* 289:    */     }
/* 290:    */     catch (Exception e)
/* 291:    */     {
/* 292:292 */       e.printStackTrace();
/* 293:    */     }
/* 294:    */   }
/* 295:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.cmdline.Transform
 * JD-Core Version:    0.7.0.1
 */