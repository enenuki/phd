/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.StringWriter;
/*   6:    */ import java.io.UnsupportedEncodingException;
/*   7:    */ import java.io.Writer;
/*   8:    */ import java.util.HashSet;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.Stack;
/*  12:    */ import org.dom4j.Document;
/*  13:    */ import org.dom4j.DocumentException;
/*  14:    */ import org.dom4j.DocumentHelper;
/*  15:    */ import org.dom4j.Element;
/*  16:    */ import org.dom4j.Entity;
/*  17:    */ import org.xml.sax.SAXException;
/*  18:    */ 
/*  19:    */ public class HTMLWriter
/*  20:    */   extends XMLWriter
/*  21:    */ {
/*  22:182 */   private static String lineSeparator = System.getProperty("line.separator");
/*  23:189 */   protected static final HashSet DEFAULT_PREFORMATTED_TAGS = new HashSet();
/*  24:    */   protected static final OutputFormat DEFAULT_HTML_FORMAT;
/*  25:    */   
/*  26:    */   static
/*  27:    */   {
/*  28:190 */     DEFAULT_PREFORMATTED_TAGS.add("PRE");
/*  29:191 */     DEFAULT_PREFORMATTED_TAGS.add("SCRIPT");
/*  30:192 */     DEFAULT_PREFORMATTED_TAGS.add("STYLE");
/*  31:193 */     DEFAULT_PREFORMATTED_TAGS.add("TEXTAREA");
/*  32:    */     
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:199 */     DEFAULT_HTML_FORMAT = new OutputFormat("  ", true);
/*  38:200 */     DEFAULT_HTML_FORMAT.setTrimText(true);
/*  39:201 */     DEFAULT_HTML_FORMAT.setSuppressDeclaration(true);
/*  40:    */   }
/*  41:    */   
/*  42:204 */   private Stack formatStack = new Stack();
/*  43:206 */   private String lastText = "";
/*  44:208 */   private int tagsOuput = 0;
/*  45:211 */   private int newLineAfterNTags = -1;
/*  46:213 */   private HashSet preformattedTags = DEFAULT_PREFORMATTED_TAGS;
/*  47:    */   private HashSet omitElementCloseSet;
/*  48:    */   
/*  49:    */   public HTMLWriter(Writer writer)
/*  50:    */   {
/*  51:222 */     super(writer, DEFAULT_HTML_FORMAT);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public HTMLWriter(Writer writer, OutputFormat format)
/*  55:    */   {
/*  56:226 */     super(writer, format);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public HTMLWriter()
/*  60:    */     throws UnsupportedEncodingException
/*  61:    */   {
/*  62:230 */     super(DEFAULT_HTML_FORMAT);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public HTMLWriter(OutputFormat format)
/*  66:    */     throws UnsupportedEncodingException
/*  67:    */   {
/*  68:234 */     super(format);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public HTMLWriter(OutputStream out)
/*  72:    */     throws UnsupportedEncodingException
/*  73:    */   {
/*  74:238 */     super(out, DEFAULT_HTML_FORMAT);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public HTMLWriter(OutputStream out, OutputFormat format)
/*  78:    */     throws UnsupportedEncodingException
/*  79:    */   {
/*  80:243 */     super(out, format);
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected void writeCDATA(String text)
/*  84:    */     throws IOException
/*  85:    */   {
/*  86:257 */     if (getOutputFormat().isXHTML()) {
/*  87:258 */       super.writeCDATA(text);
/*  88:    */     } else {
/*  89:260 */       this.writer.write(text);
/*  90:    */     }
/*  91:263 */     this.lastOutputNodeType = 4;
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected void writeEntity(Entity entity)
/*  95:    */     throws IOException
/*  96:    */   {
/*  97:267 */     this.writer.write(entity.getText());
/*  98:268 */     this.lastOutputNodeType = 5;
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected void writeString(String text)
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:287 */     if (text.equals("\n"))
/* 105:    */     {
/* 106:288 */       if (!this.formatStack.empty()) {
/* 107:289 */         super.writeString(lineSeparator);
/* 108:    */       }
/* 109:292 */       return;
/* 110:    */     }
/* 111:295 */     this.lastText = text;
/* 112:297 */     if (this.formatStack.empty()) {
/* 113:298 */       super.writeString(text.trim());
/* 114:    */     } else {
/* 115:300 */       super.writeString(text);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected void writeClose(String qualifiedName)
/* 120:    */     throws IOException
/* 121:    */   {
/* 122:315 */     if (!omitElementClose(qualifiedName)) {
/* 123:316 */       super.writeClose(qualifiedName);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void writeEmptyElementClose(String qualifiedName)
/* 128:    */     throws IOException
/* 129:    */   {
/* 130:322 */     if (getOutputFormat().isXHTML())
/* 131:    */     {
/* 132:324 */       if (omitElementClose(qualifiedName)) {
/* 133:330 */         this.writer.write(" />");
/* 134:    */       } else {
/* 135:332 */         super.writeEmptyElementClose(qualifiedName);
/* 136:    */       }
/* 137:    */     }
/* 138:336 */     else if (omitElementClose(qualifiedName)) {
/* 139:338 */       this.writer.write(">");
/* 140:    */     } else {
/* 141:342 */       super.writeEmptyElementClose(qualifiedName);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected boolean omitElementClose(String qualifiedName)
/* 146:    */   {
/* 147:348 */     return internalGetOmitElementCloseSet().contains(qualifiedName.toUpperCase());
/* 148:    */   }
/* 149:    */   
/* 150:    */   private HashSet internalGetOmitElementCloseSet()
/* 151:    */   {
/* 152:353 */     if (this.omitElementCloseSet == null)
/* 153:    */     {
/* 154:354 */       this.omitElementCloseSet = new HashSet();
/* 155:355 */       loadOmitElementCloseSet(this.omitElementCloseSet);
/* 156:    */     }
/* 157:358 */     return this.omitElementCloseSet;
/* 158:    */   }
/* 159:    */   
/* 160:    */   protected void loadOmitElementCloseSet(Set set)
/* 161:    */   {
/* 162:363 */     set.add("AREA");
/* 163:364 */     set.add("BASE");
/* 164:365 */     set.add("BR");
/* 165:366 */     set.add("COL");
/* 166:367 */     set.add("HR");
/* 167:368 */     set.add("IMG");
/* 168:369 */     set.add("INPUT");
/* 169:370 */     set.add("LINK");
/* 170:371 */     set.add("META");
/* 171:372 */     set.add("P");
/* 172:373 */     set.add("PARAM");
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Set getOmitElementCloseSet()
/* 176:    */   {
/* 177:386 */     return (Set)internalGetOmitElementCloseSet().clone();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setOmitElementCloseSet(Set newSet)
/* 181:    */   {
/* 182:407 */     this.omitElementCloseSet = new HashSet();
/* 183:409 */     if (newSet != null)
/* 184:    */     {
/* 185:410 */       this.omitElementCloseSet = new HashSet();
/* 186:    */       
/* 187:    */ 
/* 188:413 */       Iterator iter = newSet.iterator();
/* 189:415 */       while (iter.hasNext())
/* 190:    */       {
/* 191:416 */         Object aTag = iter.next();
/* 192:418 */         if (aTag != null) {
/* 193:419 */           this.omitElementCloseSet.add(aTag.toString().toUpperCase());
/* 194:    */         }
/* 195:    */       }
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Set getPreformattedTags()
/* 200:    */   {
/* 201:429 */     return (Set)this.preformattedTags.clone();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setPreformattedTags(Set newSet)
/* 205:    */   {
/* 206:534 */     this.preformattedTags = new HashSet();
/* 207:536 */     if (newSet != null)
/* 208:    */     {
/* 209:538 */       Iterator iter = newSet.iterator();
/* 210:540 */       while (iter.hasNext())
/* 211:    */       {
/* 212:541 */         Object aTag = iter.next();
/* 213:543 */         if (aTag != null) {
/* 214:544 */           this.preformattedTags.add(aTag.toString().toUpperCase());
/* 215:    */         }
/* 216:    */       }
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean isPreformattedTag(String qualifiedName)
/* 221:    */   {
/* 222:565 */     return (this.preformattedTags != null) && (this.preformattedTags.contains(qualifiedName.toUpperCase()));
/* 223:    */   }
/* 224:    */   
/* 225:    */   protected void writeElement(Element element)
/* 226:    */     throws IOException
/* 227:    */   {
/* 228:584 */     if (this.newLineAfterNTags == -1) {
/* 229:585 */       lazyInitNewLinesAfterNTags();
/* 230:    */     }
/* 231:588 */     if ((this.newLineAfterNTags > 0) && 
/* 232:589 */       (this.tagsOuput > 0) && (this.tagsOuput % this.newLineAfterNTags == 0)) {
/* 233:590 */       this.writer.write(lineSeparator);
/* 234:    */     }
/* 235:594 */     this.tagsOuput += 1;
/* 236:    */     
/* 237:596 */     String qualifiedName = element.getQualifiedName();
/* 238:597 */     String saveLastText = this.lastText;
/* 239:598 */     int size = element.nodeCount();
/* 240:600 */     if (isPreformattedTag(qualifiedName))
/* 241:    */     {
/* 242:601 */       OutputFormat currentFormat = getOutputFormat();
/* 243:602 */       boolean saveNewlines = currentFormat.isNewlines();
/* 244:603 */       boolean saveTrimText = currentFormat.isTrimText();
/* 245:604 */       String currentIndent = currentFormat.getIndent();
/* 246:    */       
/* 247:    */ 
/* 248:    */ 
/* 249:608 */       this.formatStack.push(new FormatState(saveNewlines, saveTrimText, currentIndent));
/* 250:    */       try
/* 251:    */       {
/* 252:614 */         super.writePrintln();
/* 253:616 */         if ((saveLastText.trim().length() == 0) && (currentIndent != null) && (currentIndent.length() > 0)) {
/* 254:624 */           this.writer.write(justSpaces(saveLastText));
/* 255:    */         }
/* 256:629 */         currentFormat.setNewlines(false);
/* 257:630 */         currentFormat.setTrimText(false);
/* 258:631 */         currentFormat.setIndent("");
/* 259:    */         
/* 260:    */ 
/* 261:634 */         super.writeElement(element);
/* 262:    */       }
/* 263:    */       finally
/* 264:    */       {
/* 265:636 */         FormatState state = (FormatState)this.formatStack.pop();
/* 266:637 */         currentFormat.setNewlines(state.isNewlines());
/* 267:638 */         currentFormat.setTrimText(state.isTrimText());
/* 268:639 */         currentFormat.setIndent(state.getIndent());
/* 269:    */       }
/* 270:    */     }
/* 271:    */     else
/* 272:    */     {
/* 273:642 */       super.writeElement(element);
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   private String justSpaces(String text)
/* 278:    */   {
/* 279:647 */     int size = text.length();
/* 280:648 */     StringBuffer res = new StringBuffer(size);
/* 281:651 */     for (int i = 0; i < size; i++)
/* 282:    */     {
/* 283:652 */       char c = text.charAt(i);
/* 284:654 */       switch (c)
/* 285:    */       {
/* 286:    */       case '\n': 
/* 287:    */       case '\r': 
/* 288:    */         break;
/* 289:    */       default: 
/* 290:661 */         res.append(c);
/* 291:    */       }
/* 292:    */     }
/* 293:665 */     return res.toString();
/* 294:    */   }
/* 295:    */   
/* 296:    */   private void lazyInitNewLinesAfterNTags()
/* 297:    */   {
/* 298:669 */     if (getOutputFormat().isNewlines()) {
/* 299:671 */       this.newLineAfterNTags = 0;
/* 300:    */     } else {
/* 301:673 */       this.newLineAfterNTags = getOutputFormat().getNewLineAfterNTags();
/* 302:    */     }
/* 303:    */   }
/* 304:    */   
/* 305:    */   public static String prettyPrintHTML(String html)
/* 306:    */     throws IOException, UnsupportedEncodingException, DocumentException
/* 307:    */   {
/* 308:697 */     return prettyPrintHTML(html, true, true, false, true);
/* 309:    */   }
/* 310:    */   
/* 311:    */   public static String prettyPrintXHTML(String html)
/* 312:    */     throws IOException, UnsupportedEncodingException, DocumentException
/* 313:    */   {
/* 314:719 */     return prettyPrintHTML(html, true, true, true, false);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public static String prettyPrintHTML(String html, boolean newlines, boolean trim, boolean isXHTML, boolean expandEmpty)
/* 318:    */     throws IOException, UnsupportedEncodingException, DocumentException
/* 319:    */   {
/* 320:750 */     StringWriter sw = new StringWriter();
/* 321:751 */     OutputFormat format = OutputFormat.createPrettyPrint();
/* 322:752 */     format.setNewlines(newlines);
/* 323:753 */     format.setTrimText(trim);
/* 324:754 */     format.setXHTML(isXHTML);
/* 325:755 */     format.setExpandEmptyElements(expandEmpty);
/* 326:    */     
/* 327:757 */     HTMLWriter writer = new HTMLWriter(sw, format);
/* 328:758 */     Document document = DocumentHelper.parseText(html);
/* 329:759 */     writer.write(document);
/* 330:760 */     writer.flush();
/* 331:    */     
/* 332:762 */     return sw.toString();
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void startCDATA()
/* 336:    */     throws SAXException
/* 337:    */   {}
/* 338:    */   
/* 339:    */   public void endCDATA()
/* 340:    */     throws SAXException
/* 341:    */   {}
/* 342:    */   
/* 343:    */   protected void writeDeclaration()
/* 344:    */     throws IOException
/* 345:    */   {}
/* 346:    */   
/* 347:    */   private class FormatState
/* 348:    */   {
/* 349:768 */     private boolean newlines = false;
/* 350:770 */     private boolean trimText = false;
/* 351:772 */     private String indent = "";
/* 352:    */     
/* 353:    */     public FormatState(boolean newLines, boolean trimText, String indent)
/* 354:    */     {
/* 355:775 */       this.newlines = newLines;
/* 356:776 */       this.trimText = trimText;
/* 357:777 */       this.indent = indent;
/* 358:    */     }
/* 359:    */     
/* 360:    */     public boolean isNewlines()
/* 361:    */     {
/* 362:781 */       return this.newlines;
/* 363:    */     }
/* 364:    */     
/* 365:    */     public boolean isTrimText()
/* 366:    */     {
/* 367:785 */       return this.trimText;
/* 368:    */     }
/* 369:    */     
/* 370:    */     public String getIndent()
/* 371:    */     {
/* 372:789 */       return this.indent;
/* 373:    */     }
/* 374:    */   }
/* 375:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.HTMLWriter
 * JD-Core Version:    0.7.0.1
 */