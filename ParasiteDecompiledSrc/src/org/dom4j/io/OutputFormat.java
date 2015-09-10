/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ public class OutputFormat
/*   4:    */   implements Cloneable
/*   5:    */ {
/*   6:    */   protected static final String STANDARD_INDENT = "  ";
/*   7: 27 */   private boolean suppressDeclaration = false;
/*   8: 33 */   private boolean newLineAfterDeclaration = true;
/*   9: 36 */   private String encoding = "UTF-8";
/*  10: 42 */   private boolean omitEncoding = false;
/*  11: 45 */   private String indent = null;
/*  12: 51 */   private boolean expandEmptyElements = false;
/*  13: 57 */   private boolean newlines = false;
/*  14: 60 */   private String lineSeparator = "\n";
/*  15: 63 */   private boolean trimText = false;
/*  16: 66 */   private boolean padText = false;
/*  17: 69 */   private boolean doXHTML = false;
/*  18: 75 */   private int newLineAfterNTags = 0;
/*  19: 78 */   private char attributeQuoteChar = '"';
/*  20:    */   
/*  21:    */   public OutputFormat() {}
/*  22:    */   
/*  23:    */   public OutputFormat(String indent)
/*  24:    */   {
/*  25: 97 */     this.indent = indent;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public OutputFormat(String indent, boolean newlines)
/*  29:    */   {
/*  30:112 */     this.indent = indent;
/*  31:113 */     this.newlines = newlines;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public OutputFormat(String indent, boolean newlines, String encoding)
/*  35:    */   {
/*  36:129 */     this.indent = indent;
/*  37:130 */     this.newlines = newlines;
/*  38:131 */     this.encoding = encoding;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getLineSeparator()
/*  42:    */   {
/*  43:135 */     return this.lineSeparator;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setLineSeparator(String separator)
/*  47:    */   {
/*  48:152 */     this.lineSeparator = separator;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean isNewlines()
/*  52:    */   {
/*  53:156 */     return this.newlines;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setNewlines(boolean newlines)
/*  57:    */   {
/*  58:169 */     this.newlines = newlines;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getEncoding()
/*  62:    */   {
/*  63:173 */     return this.encoding;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setEncoding(String encoding)
/*  67:    */   {
/*  68:183 */     if (encoding != null) {
/*  69:184 */       this.encoding = encoding;
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean isOmitEncoding()
/*  74:    */   {
/*  75:189 */     return this.omitEncoding;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setOmitEncoding(boolean omitEncoding)
/*  79:    */   {
/*  80:205 */     this.omitEncoding = omitEncoding;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setSuppressDeclaration(boolean suppressDeclaration)
/*  84:    */   {
/*  85:221 */     this.suppressDeclaration = suppressDeclaration;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isSuppressDeclaration()
/*  89:    */   {
/*  90:232 */     return this.suppressDeclaration;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setNewLineAfterDeclaration(boolean newLineAfterDeclaration)
/*  94:    */   {
/*  95:246 */     this.newLineAfterDeclaration = newLineAfterDeclaration;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isNewLineAfterDeclaration()
/*  99:    */   {
/* 100:255 */     return this.newLineAfterDeclaration;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isExpandEmptyElements()
/* 104:    */   {
/* 105:259 */     return this.expandEmptyElements;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setExpandEmptyElements(boolean expandEmptyElements)
/* 109:    */   {
/* 110:274 */     this.expandEmptyElements = expandEmptyElements;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isTrimText()
/* 114:    */   {
/* 115:278 */     return this.trimText;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setTrimText(boolean trimText)
/* 119:    */   {
/* 120:300 */     this.trimText = trimText;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isPadText()
/* 124:    */   {
/* 125:304 */     return this.padText;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setPadText(boolean padText)
/* 129:    */   {
/* 130:332 */     this.padText = padText;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getIndent()
/* 134:    */   {
/* 135:336 */     return this.indent;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setIndent(String indent)
/* 139:    */   {
/* 140:352 */     if ((indent != null) && (indent.length() <= 0)) {
/* 141:353 */       indent = null;
/* 142:    */     }
/* 143:356 */     this.indent = indent;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setIndent(boolean doIndent)
/* 147:    */   {
/* 148:367 */     if (doIndent) {
/* 149:368 */       this.indent = "  ";
/* 150:    */     } else {
/* 151:370 */       this.indent = null;
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setIndentSize(int indentSize)
/* 156:    */   {
/* 157:385 */     StringBuffer indentBuffer = new StringBuffer();
/* 158:387 */     for (int i = 0; i < indentSize; i++) {
/* 159:388 */       indentBuffer.append(" ");
/* 160:    */     }
/* 161:391 */     this.indent = indentBuffer.toString();
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean isXHTML()
/* 165:    */   {
/* 166:410 */     return this.doXHTML;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setXHTML(boolean xhtml)
/* 170:    */   {
/* 171:431 */     this.doXHTML = xhtml;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public int getNewLineAfterNTags()
/* 175:    */   {
/* 176:435 */     return this.newLineAfterNTags;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setNewLineAfterNTags(int tagCount)
/* 180:    */   {
/* 181:451 */     this.newLineAfterNTags = tagCount;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public char getAttributeQuoteCharacter()
/* 185:    */   {
/* 186:455 */     return this.attributeQuoteChar;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setAttributeQuoteCharacter(char quoteChar)
/* 190:    */   {
/* 191:471 */     if ((quoteChar == '\'') || (quoteChar == '"')) {
/* 192:472 */       this.attributeQuoteChar = quoteChar;
/* 193:    */     } else {
/* 194:474 */       throw new IllegalArgumentException("Invalid attribute quote character (" + quoteChar + ")");
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public int parseOptions(String[] args, int i)
/* 199:    */   {
/* 200:491 */     for (int size = args.length; i < size; i++) {
/* 201:492 */       if (args[i].equals("-suppressDeclaration")) {
/* 202:493 */         setSuppressDeclaration(true);
/* 203:494 */       } else if (args[i].equals("-omitEncoding")) {
/* 204:495 */         setOmitEncoding(true);
/* 205:496 */       } else if (args[i].equals("-indent")) {
/* 206:497 */         setIndent(args[(++i)]);
/* 207:498 */       } else if (args[i].equals("-indentSize")) {
/* 208:499 */         setIndentSize(Integer.parseInt(args[(++i)]));
/* 209:500 */       } else if (args[i].startsWith("-expandEmpty")) {
/* 210:501 */         setExpandEmptyElements(true);
/* 211:502 */       } else if (args[i].equals("-encoding")) {
/* 212:503 */         setEncoding(args[(++i)]);
/* 213:504 */       } else if (args[i].equals("-newlines")) {
/* 214:505 */         setNewlines(true);
/* 215:506 */       } else if (args[i].equals("-lineSeparator")) {
/* 216:507 */         setLineSeparator(args[(++i)]);
/* 217:508 */       } else if (args[i].equals("-trimText")) {
/* 218:509 */         setTrimText(true);
/* 219:510 */       } else if (args[i].equals("-padText")) {
/* 220:511 */         setPadText(true);
/* 221:512 */       } else if (args[i].startsWith("-xhtml")) {
/* 222:513 */         setXHTML(true);
/* 223:    */       } else {
/* 224:515 */         return i;
/* 225:    */       }
/* 226:    */     }
/* 227:519 */     return i;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public static OutputFormat createPrettyPrint()
/* 231:    */   {
/* 232:530 */     OutputFormat format = new OutputFormat();
/* 233:531 */     format.setIndentSize(2);
/* 234:532 */     format.setNewlines(true);
/* 235:533 */     format.setTrimText(true);
/* 236:534 */     format.setPadText(true);
/* 237:    */     
/* 238:536 */     return format;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public static OutputFormat createCompactFormat()
/* 242:    */   {
/* 243:547 */     OutputFormat format = new OutputFormat();
/* 244:548 */     format.setIndent(false);
/* 245:549 */     format.setNewlines(false);
/* 246:550 */     format.setTrimText(true);
/* 247:    */     
/* 248:552 */     return format;
/* 249:    */   }
/* 250:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.OutputFormat
 * JD-Core Version:    0.7.0.1
 */