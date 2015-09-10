/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.regex.Matcher;
/*   8:    */ import java.util.regex.Pattern;
/*   9:    */ import org.apache.commons.lang.StringUtils;
/*  10:    */ 
/*  11:    */ class HtmlSerializer
/*  12:    */ {
/*  13: 32 */   private final StringBuilder buffer_ = new StringBuilder();
/*  14:    */   protected static final String AS_TEXT_BLOCK_SEPARATOR = "§bs§";
/*  15:    */   protected static final String AS_TEXT_NEW_LINE = "§nl§";
/*  16:    */   protected static final String AS_TEXT_BLANK = "§blank§";
/*  17:    */   protected static final String AS_TEXT_TAB = "§tab§";
/*  18: 42 */   private static final Pattern CLEAN_UP_PATTERN = Pattern.compile("(?:§bs§)+");
/*  19: 43 */   private static final Pattern REDUCE_WHITESPACE_PATTERN = Pattern.compile("\\s*§bs§\\s*");
/*  20: 44 */   private static final Pattern TEXT_AREA_PATTERN = Pattern.compile("\r?\n");
/*  21:    */   private boolean appletEnabled_;
/*  22: 47 */   private boolean ignoreMaskedElements_ = true;
/*  23:    */   
/*  24:    */   public String asText(DomNode node)
/*  25:    */   {
/*  26: 55 */     this.appletEnabled_ = node.getPage().getWebClient().isAppletEnabled();
/*  27: 56 */     this.buffer_.setLength(0);
/*  28: 57 */     appendNode(node);
/*  29: 58 */     String response = this.buffer_.toString();
/*  30: 59 */     this.buffer_.setLength(0);
/*  31: 60 */     return cleanUp(response);
/*  32:    */   }
/*  33:    */   
/*  34:    */   private String cleanUp(String text)
/*  35:    */   {
/*  36: 65 */     text = StringUtils.replace(text, "§nl§§bs§", "§bs§");
/*  37: 66 */     text = reduceWhitespace(text);
/*  38: 67 */     text = StringUtils.replace(text, "§blank§", " ");
/*  39: 68 */     String ls = System.getProperty("line.separator");
/*  40: 69 */     text = StringUtils.replace(text, "§nl§", ls);
/*  41: 70 */     text = CLEAN_UP_PATTERN.matcher(text).replaceAll(ls);
/*  42: 71 */     text = StringUtils.replace(text, "§tab§", "\t");
/*  43:    */     
/*  44: 73 */     return text;
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected String reduceWhitespace(String text)
/*  48:    */   {
/*  49: 77 */     text = text.trim();
/*  50:    */     
/*  51:    */ 
/*  52: 80 */     text = REDUCE_WHITESPACE_PATTERN.matcher(text).replaceAll("§bs§");
/*  53: 83 */     while (text.startsWith("§bs§")) {
/*  54: 84 */       text = text.substring("§bs§".length());
/*  55:    */     }
/*  56: 88 */     while (text.endsWith("§bs§")) {
/*  57: 89 */       text = text.substring(0, text.length() - "§bs§".length());
/*  58:    */     }
/*  59: 91 */     text = text.trim();
/*  60:    */     
/*  61: 93 */     StringBuilder buffer = new StringBuilder(text.length());
/*  62:    */     
/*  63: 95 */     boolean whitespace = false;
/*  64: 96 */     for (char ch : text.toCharArray()) {
/*  65: 99 */       if (ch == ' ')
/*  66:    */       {
/*  67:100 */         buffer.append(' ');
/*  68:101 */         whitespace = false;
/*  69:    */       }
/*  70:104 */       else if (whitespace)
/*  71:    */       {
/*  72:105 */         if (!Character.isWhitespace(ch))
/*  73:    */         {
/*  74:106 */           buffer.append(ch);
/*  75:107 */           whitespace = false;
/*  76:    */         }
/*  77:    */       }
/*  78:111 */       else if (Character.isWhitespace(ch))
/*  79:    */       {
/*  80:112 */         whitespace = true;
/*  81:113 */         buffer.append(' ');
/*  82:    */       }
/*  83:    */       else
/*  84:    */       {
/*  85:116 */         buffer.append(ch);
/*  86:    */       }
/*  87:    */     }
/*  88:121 */     return buffer.toString();
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected void appendNode(DomNode node)
/*  92:    */   {
/*  93:125 */     if ((node instanceof DomText)) {
/*  94:126 */       appendText((DomText)node);
/*  95:128 */     } else if (!(node instanceof DomComment)) {
/*  96:131 */       if ((!(node instanceof HtmlApplet)) || (!this.appletEnabled_)) {
/*  97:134 */         if ((node instanceof HtmlBreak)) {
/*  98:135 */           doAppendNewLine();
/*  99:137 */         } else if ((!(node instanceof HtmlHiddenInput)) && (!(node instanceof HtmlScript)) && (!(node instanceof HtmlStyle)) && (!(node instanceof HtmlNoFrames))) {
/* 100:143 */           if ((node instanceof HtmlTextArea))
/* 101:    */           {
/* 102:144 */             appendHtmlTextArea((HtmlTextArea)node);
/* 103:    */           }
/* 104:146 */           else if ((node instanceof HtmlTitle))
/* 105:    */           {
/* 106:147 */             appendHtmlTitle((HtmlTitle)node);
/* 107:    */           }
/* 108:149 */           else if ((node instanceof HtmlTableRow))
/* 109:    */           {
/* 110:150 */             appendHtmlTableRow((HtmlTableRow)node);
/* 111:    */           }
/* 112:152 */           else if ((node instanceof HtmlSelect))
/* 113:    */           {
/* 114:153 */             appendHtmlSelect((HtmlSelect)node);
/* 115:    */           }
/* 116:155 */           else if ((node instanceof HtmlSubmitInput))
/* 117:    */           {
/* 118:156 */             appendHtmlSubmitInput((HtmlSubmitInput)node);
/* 119:    */           }
/* 120:158 */           else if ((node instanceof HtmlCheckBoxInput))
/* 121:    */           {
/* 122:    */             String str;
/* 123:    */             String str;
/* 124:160 */             if (((HtmlCheckBoxInput)node).isChecked()) {
/* 125:161 */               str = "checked";
/* 126:    */             } else {
/* 127:164 */               str = "unchecked";
/* 128:    */             }
/* 129:166 */             doAppend(str);
/* 130:    */           }
/* 131:168 */           else if ((node instanceof HtmlRadioButtonInput))
/* 132:    */           {
/* 133:    */             String str;
/* 134:    */             String str;
/* 135:170 */             if (((HtmlRadioButtonInput)node).isChecked()) {
/* 136:171 */               str = "checked";
/* 137:    */             } else {
/* 138:174 */               str = "unchecked";
/* 139:    */             }
/* 140:176 */             doAppend(str);
/* 141:    */           }
/* 142:178 */           else if ((node instanceof HtmlInput))
/* 143:    */           {
/* 144:179 */             doAppend(((HtmlInput)node).getValueAttribute());
/* 145:    */           }
/* 146:181 */           else if ((node instanceof HtmlTable))
/* 147:    */           {
/* 148:182 */             appendHtmlTable((HtmlTable)node);
/* 149:    */           }
/* 150:184 */           else if ((node instanceof HtmlOrderedList))
/* 151:    */           {
/* 152:185 */             appendHtmlOrderedList((HtmlOrderedList)node);
/* 153:    */           }
/* 154:187 */           else if ((node instanceof HtmlUnorderedList))
/* 155:    */           {
/* 156:188 */             appendHtmlUnorderedList((HtmlUnorderedList)node);
/* 157:    */           }
/* 158:    */           else
/* 159:    */           {
/* 160:190 */             if (((node instanceof HtmlNoScript)) && (node.getPage().getWebClient().isJavaScriptEnabled())) {
/* 161:191 */               return;
/* 162:    */             }
/* 163:194 */             boolean block = node.isBlock();
/* 164:195 */             if (block) {
/* 165:196 */               doAppendBlockSeparator();
/* 166:    */             }
/* 167:198 */             appendChildren(node);
/* 168:199 */             if (block) {
/* 169:200 */               doAppendBlockSeparator();
/* 170:    */             }
/* 171:    */           }
/* 172:    */         }
/* 173:    */       }
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   private void doAppendBlockSeparator()
/* 178:    */   {
/* 179:206 */     this.buffer_.append("§bs§");
/* 180:    */   }
/* 181:    */   
/* 182:    */   private void doAppend(String str)
/* 183:    */   {
/* 184:210 */     this.buffer_.append(str);
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void doAppendNewLine()
/* 188:    */   {
/* 189:214 */     this.buffer_.append("§nl§");
/* 190:    */   }
/* 191:    */   
/* 192:    */   private void doAppendTab()
/* 193:    */   {
/* 194:218 */     this.buffer_.append("§tab§");
/* 195:    */   }
/* 196:    */   
/* 197:    */   private void appendHtmlUnorderedList(HtmlUnorderedList htmlUnorderedList)
/* 198:    */   {
/* 199:222 */     doAppendBlockSeparator();
/* 200:223 */     boolean first = true;
/* 201:224 */     for (DomNode item : htmlUnorderedList.getChildren())
/* 202:    */     {
/* 203:225 */       if (!first) {
/* 204:226 */         doAppendBlockSeparator();
/* 205:    */       }
/* 206:228 */       first = false;
/* 207:229 */       appendNode(item);
/* 208:    */     }
/* 209:231 */     doAppendBlockSeparator();
/* 210:    */   }
/* 211:    */   
/* 212:    */   private void appendHtmlTitle(HtmlTitle htmlTitle)
/* 213:    */   {
/* 214:239 */     DomNode child = htmlTitle.getFirstChild();
/* 215:240 */     if ((child instanceof DomText))
/* 216:    */     {
/* 217:241 */       doAppend(((DomText)child).getData());
/* 218:242 */       doAppendBlockSeparator();
/* 219:243 */       return;
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   private void appendChildren(DomNode node)
/* 224:    */   {
/* 225:248 */     for (DomNode child : node.getChildren()) {
/* 226:249 */       appendNode(child);
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   private void appendHtmlTableRow(HtmlTableRow htmlTableRow)
/* 231:    */   {
/* 232:254 */     boolean first = true;
/* 233:255 */     for (HtmlTableCell cell : htmlTableRow.getCells())
/* 234:    */     {
/* 235:256 */       if (!first) {
/* 236:257 */         doAppendTab();
/* 237:    */       } else {
/* 238:260 */         first = false;
/* 239:    */       }
/* 240:262 */       appendChildren(cell);
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   private void appendHtmlTextArea(HtmlTextArea htmlTextArea)
/* 245:    */   {
/* 246:267 */     if (isVisible(htmlTextArea))
/* 247:    */     {
/* 248:268 */       String text = htmlTextArea.getText();
/* 249:269 */       text = StringUtils.replace(text, " ", "§blank§");
/* 250:270 */       text = TEXT_AREA_PATTERN.matcher(text).replaceAll("§nl§");
/* 251:271 */       text = StringUtils.replace(text, "\r", "§nl§");
/* 252:272 */       doAppend(text);
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   private void appendHtmlTable(HtmlTable htmlTable)
/* 257:    */   {
/* 258:277 */     doAppendBlockSeparator();
/* 259:278 */     String caption = htmlTable.getCaptionText();
/* 260:279 */     if (caption != null)
/* 261:    */     {
/* 262:280 */       doAppend(caption);
/* 263:281 */       doAppendBlockSeparator();
/* 264:    */     }
/* 265:284 */     boolean first = true;
/* 266:    */     
/* 267:    */ 
/* 268:287 */     HtmlTableHeader tableHeader = htmlTable.getHeader();
/* 269:288 */     if (tableHeader != null) {
/* 270:289 */       first = appendHtmlTableRows(tableHeader.getRows(), true, null, null);
/* 271:    */     }
/* 272:291 */     HtmlTableFooter tableFooter = htmlTable.getFooter();
/* 273:    */     
/* 274:293 */     first = appendHtmlTableRows(htmlTable.getRows(), first, tableHeader, tableFooter);
/* 275:295 */     if (tableFooter != null) {
/* 276:296 */       first = appendHtmlTableRows(tableFooter.getRows(), first, null, null);
/* 277:    */     }
/* 278:299 */     doAppendBlockSeparator();
/* 279:    */   }
/* 280:    */   
/* 281:    */   private boolean appendHtmlTableRows(List<HtmlTableRow> rows, boolean first, TableRowGroup skipParent1, TableRowGroup skipParent2)
/* 282:    */   {
/* 283:304 */     for (HtmlTableRow row : rows) {
/* 284:305 */       if ((row.getParentNode() != skipParent1) && (row.getParentNode() != skipParent2))
/* 285:    */       {
/* 286:308 */         if (!first) {
/* 287:309 */           doAppendBlockSeparator();
/* 288:    */         }
/* 289:311 */         first = false;
/* 290:312 */         appendHtmlTableRow(row);
/* 291:    */       }
/* 292:    */     }
/* 293:314 */     return first;
/* 294:    */   }
/* 295:    */   
/* 296:    */   private void appendHtmlSubmitInput(HtmlSubmitInput htmlSubmitInput)
/* 297:    */   {
/* 298:318 */     String value = htmlSubmitInput.getValueAttribute();
/* 299:319 */     if (value == DomElement.ATTRIBUTE_NOT_DEFINED) {
/* 300:320 */       value = "Submit Query";
/* 301:    */     }
/* 302:323 */     doAppend(value);
/* 303:    */   }
/* 304:    */   
/* 305:    */   private void appendHtmlSelect(HtmlSelect htmlSelect)
/* 306:    */   {
/* 307:    */     List<HtmlOption> options;
/* 308:    */     List<HtmlOption> options;
/* 309:331 */     if (htmlSelect.isMultipleSelectEnabled()) {
/* 310:332 */       options = htmlSelect.getOptions();
/* 311:    */     } else {
/* 312:335 */       options = htmlSelect.getSelectedOptions();
/* 313:    */     }
/* 314:338 */     for (Iterator<HtmlOption> i = options.iterator(); i.hasNext();)
/* 315:    */     {
/* 316:339 */       HtmlOption currentOption = (HtmlOption)i.next();
/* 317:340 */       appendNode(currentOption);
/* 318:341 */       if (i.hasNext()) {
/* 319:342 */         doAppendBlockSeparator();
/* 320:    */       }
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   private void appendHtmlOrderedList(HtmlOrderedList htmlOrderedList)
/* 325:    */   {
/* 326:352 */     doAppendBlockSeparator();
/* 327:353 */     boolean first = true;
/* 328:354 */     int i = 1;
/* 329:355 */     for (DomNode item : htmlOrderedList.getChildren()) {
/* 330:356 */       if ((item instanceof HtmlListItem))
/* 331:    */       {
/* 332:359 */         if (!first) {
/* 333:360 */           doAppendBlockSeparator();
/* 334:    */         }
/* 335:362 */         first = false;
/* 336:363 */         doAppend(Integer.toString(i++));
/* 337:364 */         doAppend(". ");
/* 338:365 */         appendChildren(item);
/* 339:    */       }
/* 340:    */     }
/* 341:367 */     doAppendBlockSeparator();
/* 342:    */   }
/* 343:    */   
/* 344:    */   private void appendText(DomText domText)
/* 345:    */   {
/* 346:371 */     if (isVisible(domText.getParentNode())) {
/* 347:372 */       append(domText.getData());
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   private boolean isVisible(DomNode node)
/* 352:    */   {
/* 353:377 */     return (!this.ignoreMaskedElements_) || (node.isDisplayed());
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setIgnoreMaskedElements(boolean ignore)
/* 357:    */   {
/* 358:386 */     this.ignoreMaskedElements_ = ignore;
/* 359:    */   }
/* 360:    */   
/* 361:    */   private void append(String text)
/* 362:    */   {
/* 363:390 */     doAppend(text);
/* 364:    */   }
/* 365:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlSerializer
 * JD-Core Version:    0.7.0.1
 */