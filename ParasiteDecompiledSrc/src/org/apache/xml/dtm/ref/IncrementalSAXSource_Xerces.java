/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.io.Reader;
/*   7:    */ import java.lang.reflect.Constructor;
/*   8:    */ import java.lang.reflect.InvocationTargetException;
/*   9:    */ import java.lang.reflect.Method;
/*  10:    */ import org.apache.xerces.parsers.SAXParser;
/*  11:    */ import org.apache.xml.res.XMLMessages;
/*  12:    */ import org.apache.xml.serialize.XMLSerializer;
/*  13:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  14:    */ import org.xml.sax.ContentHandler;
/*  15:    */ import org.xml.sax.DTDHandler;
/*  16:    */ import org.xml.sax.InputSource;
/*  17:    */ import org.xml.sax.SAXException;
/*  18:    */ import org.xml.sax.SAXNotRecognizedException;
/*  19:    */ import org.xml.sax.SAXNotSupportedException;
/*  20:    */ import org.xml.sax.XMLReader;
/*  21:    */ import org.xml.sax.ext.LexicalHandler;
/*  22:    */ 
/*  23:    */ public class IncrementalSAXSource_Xerces
/*  24:    */   implements IncrementalSAXSource
/*  25:    */ {
/*  26: 54 */   Method fParseSomeSetup = null;
/*  27: 55 */   Method fParseSome = null;
/*  28: 56 */   Object fPullParserConfig = null;
/*  29: 57 */   Method fConfigSetInput = null;
/*  30: 58 */   Method fConfigParse = null;
/*  31: 59 */   Method fSetInputSource = null;
/*  32: 60 */   Constructor fConfigInputSourceCtor = null;
/*  33: 61 */   Method fConfigSetByteStream = null;
/*  34: 62 */   Method fConfigSetCharStream = null;
/*  35: 63 */   Method fConfigSetEncoding = null;
/*  36: 64 */   Method fReset = null;
/*  37:    */   SAXParser fIncrementalParser;
/*  38: 70 */   private boolean fParseInProgress = false;
/*  39:    */   
/*  40:    */   public IncrementalSAXSource_Xerces()
/*  41:    */     throws NoSuchMethodException
/*  42:    */   {
/*  43:    */     try
/*  44:    */     {
/*  45:100 */       Class xniConfigClass = ObjectFactory.findProviderClass("org.apache.xerces.xni.parser.XMLParserConfiguration", ObjectFactory.findClassLoader(), true);
/*  46:    */       
/*  47:    */ 
/*  48:103 */       Class[] args1 = { xniConfigClass };
/*  49:104 */       Constructor ctor = SAXParser.class.getConstructor(args1);
/*  50:    */       
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:109 */       Class xniStdConfigClass = ObjectFactory.findProviderClass("org.apache.xerces.parsers.StandardParserConfiguration", ObjectFactory.findClassLoader(), true);
/*  55:    */       
/*  56:    */ 
/*  57:112 */       this.fPullParserConfig = xniStdConfigClass.newInstance();
/*  58:113 */       Object[] args2 = { this.fPullParserConfig };
/*  59:114 */       this.fIncrementalParser = ((SAXParser)ctor.newInstance(args2));
/*  60:    */       
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:119 */       Class fXniInputSourceClass = ObjectFactory.findProviderClass("org.apache.xerces.xni.parser.XMLInputSource", ObjectFactory.findClassLoader(), true);
/*  65:    */       
/*  66:    */ 
/*  67:122 */       Class[] args3 = { fXniInputSourceClass };
/*  68:123 */       this.fConfigSetInput = xniStdConfigClass.getMethod("setInputSource", args3);
/*  69:    */       
/*  70:125 */       Class[] args4 = { String.class, String.class, String.class };
/*  71:126 */       this.fConfigInputSourceCtor = fXniInputSourceClass.getConstructor(args4);
/*  72:127 */       Class[] args5 = { InputStream.class };
/*  73:128 */       this.fConfigSetByteStream = fXniInputSourceClass.getMethod("setByteStream", args5);
/*  74:129 */       Class[] args6 = { Reader.class };
/*  75:130 */       this.fConfigSetCharStream = fXniInputSourceClass.getMethod("setCharacterStream", args6);
/*  76:131 */       Class[] args7 = { String.class };
/*  77:132 */       this.fConfigSetEncoding = fXniInputSourceClass.getMethod("setEncoding", args7);
/*  78:    */       
/*  79:134 */       Class[] argsb = { Boolean.TYPE };
/*  80:135 */       this.fConfigParse = xniStdConfigClass.getMethod("parse", argsb);
/*  81:136 */       Class[] noargs = new Class[0];
/*  82:137 */       this.fReset = this.fIncrementalParser.getClass().getMethod("reset", noargs);
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86:145 */       IncrementalSAXSource_Xerces dummy = new IncrementalSAXSource_Xerces(new SAXParser());
/*  87:146 */       this.fParseSomeSetup = dummy.fParseSomeSetup;
/*  88:147 */       this.fParseSome = dummy.fParseSome;
/*  89:148 */       this.fIncrementalParser = dummy.fIncrementalParser;
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public IncrementalSAXSource_Xerces(SAXParser parser)
/*  94:    */     throws NoSuchMethodException
/*  95:    */   {
/*  96:171 */     this.fIncrementalParser = parser;
/*  97:172 */     Class me = parser.getClass();
/*  98:173 */     Class[] parms = { InputSource.class };
/*  99:174 */     this.fParseSomeSetup = me.getMethod("parseSomeSetup", parms);
/* 100:175 */     parms = new Class[0];
/* 101:176 */     this.fParseSome = me.getMethod("parseSome", parms);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static IncrementalSAXSource createIncrementalSAXSource()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:188 */       return new IncrementalSAXSource_Xerces();
/* 109:    */     }
/* 110:    */     catch (NoSuchMethodException e)
/* 111:    */     {
/* 112:194 */       IncrementalSAXSource_Filter iss = new IncrementalSAXSource_Filter();
/* 113:195 */       iss.setXMLReader(new SAXParser());
/* 114:196 */       return iss;
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static IncrementalSAXSource createIncrementalSAXSource(SAXParser parser)
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:204 */       return new IncrementalSAXSource_Xerces(parser);
/* 123:    */     }
/* 124:    */     catch (NoSuchMethodException e)
/* 125:    */     {
/* 126:210 */       IncrementalSAXSource_Filter iss = new IncrementalSAXSource_Filter();
/* 127:211 */       iss.setXMLReader(parser);
/* 128:212 */       return iss;
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setContentHandler(ContentHandler handler)
/* 133:    */   {
/* 134:225 */     this.fIncrementalParser.setContentHandler(handler);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setLexicalHandler(LexicalHandler handler)
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:236 */       this.fIncrementalParser.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
/* 142:    */     }
/* 143:    */     catch (SAXNotRecognizedException e) {}catch (SAXNotSupportedException e) {}
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setDTDHandler(DTDHandler handler)
/* 147:    */   {
/* 148:254 */     this.fIncrementalParser.setDTDHandler(handler);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void startParse(InputSource source)
/* 152:    */     throws SAXException
/* 153:    */   {
/* 154:266 */     if (this.fIncrementalParser == null) {
/* 155:267 */       throw new SAXException(XMLMessages.createXMLMessage("ER_STARTPARSE_NEEDS_SAXPARSER", null));
/* 156:    */     }
/* 157:268 */     if (this.fParseInProgress) {
/* 158:269 */       throw new SAXException(XMLMessages.createXMLMessage("ER_STARTPARSE_WHILE_PARSING", null));
/* 159:    */     }
/* 160:271 */     boolean ok = false;
/* 161:    */     try
/* 162:    */     {
/* 163:275 */       ok = parseSomeSetup(source);
/* 164:    */     }
/* 165:    */     catch (Exception ex)
/* 166:    */     {
/* 167:279 */       throw new SAXException(ex);
/* 168:    */     }
/* 169:282 */     if (!ok) {
/* 170:283 */       throw new SAXException(XMLMessages.createXMLMessage("ER_COULD_NOT_INIT_PARSER", null));
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Object deliverMoreNodes(boolean parsemore)
/* 175:    */   {
/* 176:301 */     if (!parsemore)
/* 177:    */     {
/* 178:303 */       this.fParseInProgress = false;
/* 179:304 */       return Boolean.FALSE;
/* 180:    */     }
/* 181:    */     Object arg;
/* 182:    */     try
/* 183:    */     {
/* 184:309 */       boolean keepgoing = parseSome();
/* 185:310 */       arg = keepgoing ? Boolean.TRUE : Boolean.FALSE;
/* 186:    */     }
/* 187:    */     catch (SAXException ex)
/* 188:    */     {
/* 189:312 */       arg = ex;
/* 190:    */     }
/* 191:    */     catch (IOException ex)
/* 192:    */     {
/* 193:314 */       arg = ex;
/* 194:    */     }
/* 195:    */     catch (Exception ex)
/* 196:    */     {
/* 197:316 */       arg = new SAXException(ex);
/* 198:    */     }
/* 199:318 */     return arg;
/* 200:    */   }
/* 201:    */   
/* 202:    */   private boolean parseSomeSetup(InputSource source)
/* 203:    */     throws SAXException, IOException, IllegalAccessException, InvocationTargetException, InstantiationException
/* 204:    */   {
/* 205:327 */     if (this.fConfigSetInput != null)
/* 206:    */     {
/* 207:331 */       Object[] parms1 = { source.getPublicId(), source.getSystemId(), null };
/* 208:332 */       Object xmlsource = this.fConfigInputSourceCtor.newInstance(parms1);
/* 209:333 */       Object[] parmsa = { source.getByteStream() };
/* 210:334 */       this.fConfigSetByteStream.invoke(xmlsource, parmsa);
/* 211:335 */       parmsa[0] = source.getCharacterStream();
/* 212:336 */       this.fConfigSetCharStream.invoke(xmlsource, parmsa);
/* 213:337 */       parmsa[0] = source.getEncoding();
/* 214:338 */       this.fConfigSetEncoding.invoke(xmlsource, parmsa);
/* 215:    */       
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:344 */       Object[] noparms = new Object[0];
/* 221:345 */       this.fReset.invoke(this.fIncrementalParser, noparms);
/* 222:    */       
/* 223:347 */       parmsa[0] = xmlsource;
/* 224:348 */       this.fConfigSetInput.invoke(this.fPullParserConfig, parmsa);
/* 225:    */       
/* 226:    */ 
/* 227:351 */       return parseSome();
/* 228:    */     }
/* 229:355 */     Object[] parm = { source };
/* 230:356 */     Object ret = this.fParseSomeSetup.invoke(this.fIncrementalParser, parm);
/* 231:357 */     return ((Boolean)ret).booleanValue();
/* 232:    */   }
/* 233:    */   
/* 234:361 */   private static final Object[] noparms = new Object[0];
/* 235:362 */   private static final Object[] parmsfalse = { Boolean.FALSE };
/* 236:    */   
/* 237:    */   private boolean parseSome()
/* 238:    */     throws SAXException, IOException, IllegalAccessException, InvocationTargetException
/* 239:    */   {
/* 240:368 */     if (this.fConfigSetInput != null)
/* 241:    */     {
/* 242:370 */       Object ret = (Boolean)this.fConfigParse.invoke(this.fPullParserConfig, parmsfalse);
/* 243:371 */       return ((Boolean)ret).booleanValue();
/* 244:    */     }
/* 245:375 */     Object ret = this.fParseSome.invoke(this.fIncrementalParser, noparms);
/* 246:376 */     return ((Boolean)ret).booleanValue();
/* 247:    */   }
/* 248:    */   
/* 249:    */   public static void main(String[] args)
/* 250:    */   {
/* 251:387 */     System.out.println("Starting...");
/* 252:    */     
/* 253:389 */     CoroutineManager co = new CoroutineManager();
/* 254:390 */     int appCoroutineID = co.co_joinCoroutineSet(-1);
/* 255:391 */     if (appCoroutineID == -1)
/* 256:    */     {
/* 257:393 */       System.out.println("ERROR: Couldn't allocate coroutine number.\n");
/* 258:394 */       return;
/* 259:    */     }
/* 260:396 */     IncrementalSAXSource parser = createIncrementalSAXSource();
/* 261:    */     
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:401 */     XMLSerializer trace = new XMLSerializer(System.out, null);
/* 266:402 */     parser.setContentHandler(trace);
/* 267:403 */     parser.setLexicalHandler(trace);
/* 268:407 */     for (int arg = 0; arg < args.length; arg++) {
/* 269:    */       try
/* 270:    */       {
/* 271:411 */         InputSource source = new InputSource(args[arg]);
/* 272:412 */         Object result = null;
/* 273:413 */         boolean more = true;
/* 274:414 */         parser.startParse(source);
/* 275:415 */         for (result = parser.deliverMoreNodes(more); result == Boolean.TRUE; result = parser.deliverMoreNodes(more))
/* 276:    */         {
/* 277:419 */           System.out.println("\nSome parsing successful, trying more.\n");
/* 278:422 */           if ((arg + 1 < args.length) && ("!".equals(args[(arg + 1)])))
/* 279:    */           {
/* 280:424 */             arg++;
/* 281:425 */             more = false;
/* 282:    */           }
/* 283:    */         }
/* 284:430 */         if (((result instanceof Boolean)) && ((Boolean)result == Boolean.FALSE)) {
/* 285:432 */           System.out.println("\nParser ended (EOF or on request).\n");
/* 286:434 */         } else if (result == null) {
/* 287:435 */           System.out.println("\nUNEXPECTED: Parser says shut down prematurely.\n");
/* 288:437 */         } else if ((result instanceof Exception)) {
/* 289:438 */           throw new WrappedRuntimeException((Exception)result);
/* 290:    */         }
/* 291:    */       }
/* 292:    */       catch (SAXException e)
/* 293:    */       {
/* 294:447 */         e.printStackTrace();
/* 295:    */       }
/* 296:    */     }
/* 297:    */   }
/* 298:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.IncrementalSAXSource_Xerces
 * JD-Core Version:    0.7.0.1
 */