/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.io.OutputStreamWriter;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.util.Properties;
/*   6:    */ import java.util.StringTokenizer;
/*   7:    */ import org.apache.bcel.generic.ClassGen;
/*   8:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   9:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  10:    */ import org.apache.bcel.generic.InstructionConstants;
/*  11:    */ import org.apache.bcel.generic.InstructionList;
/*  12:    */ import org.apache.bcel.generic.MethodGen;
/*  13:    */ import org.apache.bcel.generic.PUSH;
/*  14:    */ import org.apache.bcel.generic.PUTFIELD;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  19:    */ import org.apache.xml.serializer.Encodings;
/*  20:    */ import org.apache.xml.utils.XML11Char;
/*  21:    */ 
/*  22:    */ final class Output
/*  23:    */   extends TopLevelElement
/*  24:    */ {
/*  25:    */   private String _version;
/*  26:    */   private String _method;
/*  27:    */   private String _encoding;
/*  28: 56 */   private boolean _omitHeader = false;
/*  29:    */   private String _standalone;
/*  30:    */   private String _doctypePublic;
/*  31:    */   private String _doctypeSystem;
/*  32:    */   private String _cdata;
/*  33: 61 */   private boolean _indent = false;
/*  34:    */   private String _mediaType;
/*  35:    */   private String _indentamount;
/*  36: 66 */   private boolean _disabled = false;
/*  37:    */   private static final String STRING_SIG = "Ljava/lang/String;";
/*  38:    */   private static final String XML_VERSION = "1.0";
/*  39:    */   private static final String HTML_VERSION = "4.0";
/*  40:    */   
/*  41:    */   public void display(int indent)
/*  42:    */   {
/*  43: 77 */     indent(indent);
/*  44: 78 */     Util.println("Output " + this._method);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void disable()
/*  48:    */   {
/*  49: 87 */     this._disabled = true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean enabled()
/*  53:    */   {
/*  54: 91 */     return !this._disabled;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getCdata()
/*  58:    */   {
/*  59: 95 */     return this._cdata;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getOutputMethod()
/*  63:    */   {
/*  64: 99 */     return this._method;
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void transferAttribute(Output previous, String qname)
/*  68:    */   {
/*  69:103 */     if ((!hasAttribute(qname)) && (previous.hasAttribute(qname))) {
/*  70:104 */       addAttribute(qname, previous.getAttribute(qname));
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void mergeOutput(Output previous)
/*  75:    */   {
/*  76:110 */     transferAttribute(previous, "version");
/*  77:111 */     transferAttribute(previous, "method");
/*  78:112 */     transferAttribute(previous, "encoding");
/*  79:113 */     transferAttribute(previous, "doctype-system");
/*  80:114 */     transferAttribute(previous, "doctype-public");
/*  81:115 */     transferAttribute(previous, "media-type");
/*  82:116 */     transferAttribute(previous, "indent");
/*  83:117 */     transferAttribute(previous, "omit-xml-declaration");
/*  84:118 */     transferAttribute(previous, "standalone");
/*  85:121 */     if (previous.hasAttribute("cdata-section-elements")) {
/*  86:123 */       addAttribute("cdata-section-elements", previous.getAttribute("cdata-section-elements") + ' ' + getAttribute("cdata-section-elements"));
/*  87:    */     }
/*  88:129 */     String prefix = lookupPrefix("http://xml.apache.org/xalan");
/*  89:130 */     if (prefix != null) {
/*  90:131 */       transferAttribute(previous, prefix + ':' + "indent-amount");
/*  91:    */     }
/*  92:133 */     prefix = lookupPrefix("http://xml.apache.org/xslt");
/*  93:134 */     if (prefix != null) {
/*  94:135 */       transferAttribute(previous, prefix + ':' + "indent-amount");
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void parseContents(Parser parser)
/*  99:    */   {
/* 100:143 */     Properties outputProperties = new Properties();
/* 101:    */     
/* 102:    */ 
/* 103:146 */     parser.setOutput(this);
/* 104:149 */     if (this._disabled) {
/* 105:149 */       return;
/* 106:    */     }
/* 107:151 */     String attrib = null;
/* 108:    */     
/* 109:    */ 
/* 110:154 */     this._version = getAttribute("version");
/* 111:155 */     if (this._version.equals("")) {
/* 112:156 */       this._version = null;
/* 113:    */     } else {
/* 114:159 */       outputProperties.setProperty("version", this._version);
/* 115:    */     }
/* 116:163 */     this._method = getAttribute("method");
/* 117:164 */     if (this._method.equals("")) {
/* 118:165 */       this._method = null;
/* 119:    */     }
/* 120:167 */     if (this._method != null)
/* 121:    */     {
/* 122:168 */       this._method = this._method.toLowerCase();
/* 123:169 */       if ((this._method.equals("xml")) || (this._method.equals("html")) || (this._method.equals("text")) || ((XML11Char.isXML11ValidQName(this._method)) && (this._method.indexOf(":") > 0))) {
/* 124:173 */         outputProperties.setProperty("method", this._method);
/* 125:    */       } else {
/* 126:175 */         reportError(this, parser, "INVALID_METHOD_IN_OUTPUT", this._method);
/* 127:    */       }
/* 128:    */     }
/* 129:180 */     this._encoding = getAttribute("encoding");
/* 130:181 */     if (this._encoding.equals(""))
/* 131:    */     {
/* 132:182 */       this._encoding = null;
/* 133:    */     }
/* 134:    */     else
/* 135:    */     {
/* 136:    */       try
/* 137:    */       {
/* 138:188 */         String canonicalEncoding = Encodings.convertMime2JavaEncoding(this._encoding);
/* 139:189 */         writer = new OutputStreamWriter(System.out, canonicalEncoding);
/* 140:    */       }
/* 141:    */       catch (UnsupportedEncodingException e)
/* 142:    */       {
/* 143:    */         OutputStreamWriter writer;
/* 144:193 */         ErrorMsg msg = new ErrorMsg("UNSUPPORTED_ENCODING", this._encoding, this);
/* 145:    */         
/* 146:195 */         parser.reportError(4, msg);
/* 147:    */       }
/* 148:197 */       outputProperties.setProperty("encoding", this._encoding);
/* 149:    */     }
/* 150:201 */     attrib = getAttribute("omit-xml-declaration");
/* 151:202 */     if (!attrib.equals(""))
/* 152:    */     {
/* 153:203 */       if (attrib.equals("yes")) {
/* 154:204 */         this._omitHeader = true;
/* 155:    */       }
/* 156:206 */       outputProperties.setProperty("omit-xml-declaration", attrib);
/* 157:    */     }
/* 158:210 */     this._standalone = getAttribute("standalone");
/* 159:211 */     if (this._standalone.equals("")) {
/* 160:212 */       this._standalone = null;
/* 161:    */     } else {
/* 162:215 */       outputProperties.setProperty("standalone", this._standalone);
/* 163:    */     }
/* 164:219 */     this._doctypeSystem = getAttribute("doctype-system");
/* 165:220 */     if (this._doctypeSystem.equals("")) {
/* 166:221 */       this._doctypeSystem = null;
/* 167:    */     } else {
/* 168:224 */       outputProperties.setProperty("doctype-system", this._doctypeSystem);
/* 169:    */     }
/* 170:228 */     this._doctypePublic = getAttribute("doctype-public");
/* 171:229 */     if (this._doctypePublic.equals("")) {
/* 172:230 */       this._doctypePublic = null;
/* 173:    */     } else {
/* 174:233 */       outputProperties.setProperty("doctype-public", this._doctypePublic);
/* 175:    */     }
/* 176:237 */     this._cdata = getAttribute("cdata-section-elements");
/* 177:238 */     if (this._cdata.equals(""))
/* 178:    */     {
/* 179:239 */       this._cdata = null;
/* 180:    */     }
/* 181:    */     else
/* 182:    */     {
/* 183:242 */       StringBuffer expandedNames = new StringBuffer();
/* 184:243 */       StringTokenizer tokens = new StringTokenizer(this._cdata);
/* 185:246 */       while (tokens.hasMoreTokens())
/* 186:    */       {
/* 187:247 */         String qname = tokens.nextToken();
/* 188:248 */         if (!XML11Char.isXML11ValidQName(qname))
/* 189:    */         {
/* 190:249 */           ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", qname, this);
/* 191:250 */           parser.reportError(3, err);
/* 192:    */         }
/* 193:252 */         expandedNames.append(parser.getQName(qname).toString()).append(' ');
/* 194:    */       }
/* 195:255 */       this._cdata = expandedNames.toString();
/* 196:256 */       outputProperties.setProperty("cdata-section-elements", this._cdata);
/* 197:    */     }
/* 198:261 */     attrib = getAttribute("indent");
/* 199:262 */     if (!attrib.equals(""))
/* 200:    */     {
/* 201:263 */       if (attrib.equals("yes")) {
/* 202:264 */         this._indent = true;
/* 203:    */       }
/* 204:266 */       outputProperties.setProperty("indent", attrib);
/* 205:    */     }
/* 206:268 */     else if ((this._method != null) && (this._method.equals("html")))
/* 207:    */     {
/* 208:269 */       this._indent = true;
/* 209:    */     }
/* 210:273 */     this._indentamount = getAttribute(lookupPrefix("http://xml.apache.org/xalan"), "indent-amount");
/* 211:276 */     if (this._indentamount.equals("")) {
/* 212:277 */       this._indentamount = getAttribute(lookupPrefix("http://xml.apache.org/xslt"), "indent-amount");
/* 213:    */     }
/* 214:280 */     if (!this._indentamount.equals("")) {
/* 215:281 */       outputProperties.setProperty("indent_amount", this._indentamount);
/* 216:    */     }
/* 217:285 */     this._mediaType = getAttribute("media-type");
/* 218:286 */     if (this._mediaType.equals("")) {
/* 219:287 */       this._mediaType = null;
/* 220:    */     } else {
/* 221:290 */       outputProperties.setProperty("media-type", this._mediaType);
/* 222:    */     }
/* 223:294 */     if (this._method != null) {
/* 224:295 */       if (this._method.equals("html"))
/* 225:    */       {
/* 226:296 */         if (this._version == null) {
/* 227:297 */           this._version = "4.0";
/* 228:    */         }
/* 229:299 */         if (this._mediaType == null) {
/* 230:300 */           this._mediaType = "text/html";
/* 231:    */         }
/* 232:    */       }
/* 233:303 */       else if ((this._method.equals("text")) && 
/* 234:304 */         (this._mediaType == null))
/* 235:    */       {
/* 236:305 */         this._mediaType = "text/plain";
/* 237:    */       }
/* 238:    */     }
/* 239:311 */     parser.getCurrentStylesheet().setOutputProperties(outputProperties);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 243:    */   {
/* 244:321 */     if (this._disabled) {
/* 245:321 */       return;
/* 246:    */     }
/* 247:323 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 248:324 */     InstructionList il = methodGen.getInstructionList();
/* 249:    */     
/* 250:326 */     int field = 0;
/* 251:327 */     il.append(classGen.loadTranslet());
/* 252:330 */     if ((this._version != null) && (!this._version.equals("1.0")))
/* 253:    */     {
/* 254:331 */       field = cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_version", "Ljava/lang/String;");
/* 255:332 */       il.append(InstructionConstants.DUP);
/* 256:333 */       il.append(new PUSH(cpg, this._version));
/* 257:334 */       il.append(new PUTFIELD(field));
/* 258:    */     }
/* 259:338 */     if (this._method != null)
/* 260:    */     {
/* 261:339 */       field = cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_method", "Ljava/lang/String;");
/* 262:340 */       il.append(InstructionConstants.DUP);
/* 263:341 */       il.append(new PUSH(cpg, this._method));
/* 264:342 */       il.append(new PUTFIELD(field));
/* 265:    */     }
/* 266:346 */     if (this._encoding != null)
/* 267:    */     {
/* 268:347 */       field = cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_encoding", "Ljava/lang/String;");
/* 269:348 */       il.append(InstructionConstants.DUP);
/* 270:349 */       il.append(new PUSH(cpg, this._encoding));
/* 271:350 */       il.append(new PUTFIELD(field));
/* 272:    */     }
/* 273:354 */     if (this._omitHeader)
/* 274:    */     {
/* 275:355 */       field = cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_omitHeader", "Z");
/* 276:356 */       il.append(InstructionConstants.DUP);
/* 277:357 */       il.append(new PUSH(cpg, this._omitHeader));
/* 278:358 */       il.append(new PUTFIELD(field));
/* 279:    */     }
/* 280:362 */     if (this._standalone != null)
/* 281:    */     {
/* 282:363 */       field = cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_standalone", "Ljava/lang/String;");
/* 283:364 */       il.append(InstructionConstants.DUP);
/* 284:365 */       il.append(new PUSH(cpg, this._standalone));
/* 285:366 */       il.append(new PUTFIELD(field));
/* 286:    */     }
/* 287:370 */     field = cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_doctypeSystem", "Ljava/lang/String;");
/* 288:371 */     il.append(InstructionConstants.DUP);
/* 289:372 */     il.append(new PUSH(cpg, this._doctypeSystem));
/* 290:373 */     il.append(new PUTFIELD(field));
/* 291:374 */     field = cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_doctypePublic", "Ljava/lang/String;");
/* 292:375 */     il.append(InstructionConstants.DUP);
/* 293:376 */     il.append(new PUSH(cpg, this._doctypePublic));
/* 294:377 */     il.append(new PUTFIELD(field));
/* 295:380 */     if (this._mediaType != null)
/* 296:    */     {
/* 297:381 */       field = cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_mediaType", "Ljava/lang/String;");
/* 298:382 */       il.append(InstructionConstants.DUP);
/* 299:383 */       il.append(new PUSH(cpg, this._mediaType));
/* 300:384 */       il.append(new PUTFIELD(field));
/* 301:    */     }
/* 302:388 */     if (this._indent)
/* 303:    */     {
/* 304:389 */       field = cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_indent", "Z");
/* 305:390 */       il.append(InstructionConstants.DUP);
/* 306:391 */       il.append(new PUSH(cpg, this._indent));
/* 307:392 */       il.append(new PUTFIELD(field));
/* 308:    */     }
/* 309:396 */     if ((this._indentamount != null) && (!this._indentamount.equals("")))
/* 310:    */     {
/* 311:397 */       field = cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "_indentamount", "I");
/* 312:398 */       il.append(InstructionConstants.DUP);
/* 313:399 */       il.append(new PUSH(cpg, Integer.parseInt(this._indentamount)));
/* 314:400 */       il.append(new PUTFIELD(field));
/* 315:    */     }
/* 316:404 */     if (this._cdata != null)
/* 317:    */     {
/* 318:405 */       int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "addCdataElement", "(Ljava/lang/String;)V");
/* 319:    */       
/* 320:    */ 
/* 321:    */ 
/* 322:409 */       StringTokenizer tokens = new StringTokenizer(this._cdata);
/* 323:410 */       while (tokens.hasMoreTokens())
/* 324:    */       {
/* 325:411 */         il.append(InstructionConstants.DUP);
/* 326:412 */         il.append(new PUSH(cpg, tokens.nextToken()));
/* 327:413 */         il.append(new INVOKEVIRTUAL(index));
/* 328:    */       }
/* 329:    */     }
/* 330:416 */     il.append(InstructionConstants.POP);
/* 331:    */   }
/* 332:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Output
 * JD-Core Version:    0.7.0.1
 */