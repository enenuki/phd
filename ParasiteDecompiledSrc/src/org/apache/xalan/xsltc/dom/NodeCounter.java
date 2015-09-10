/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.xalan.xsltc.DOM;
/*   5:    */ import org.apache.xalan.xsltc.Translet;
/*   6:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   7:    */ 
/*   8:    */ public abstract class NodeCounter
/*   9:    */ {
/*  10:    */   public static final int END = -1;
/*  11: 40 */   protected int _node = -1;
/*  12: 41 */   protected int _nodeType = -1;
/*  13: 42 */   protected double _value = -2147483648.0D;
/*  14:    */   public final DOM _document;
/*  15:    */   public final DTMAxisIterator _iterator;
/*  16:    */   public final Translet _translet;
/*  17:    */   protected String _format;
/*  18:    */   protected String _lang;
/*  19:    */   protected String _letterValue;
/*  20:    */   protected String _groupSep;
/*  21:    */   protected int _groupSize;
/*  22: 54 */   private boolean _separFirst = true;
/*  23: 55 */   private boolean _separLast = false;
/*  24: 56 */   private Vector _separToks = new Vector();
/*  25: 57 */   private Vector _formatToks = new Vector();
/*  26: 58 */   private int _nSepars = 0;
/*  27: 59 */   private int _nFormats = 0;
/*  28: 61 */   private static final String[] Thousands = { "", "m", "mm", "mmm" };
/*  29: 63 */   private static final String[] Hundreds = { "", "c", "cc", "ccc", "cd", "d", "dc", "dcc", "dccc", "cm" };
/*  30: 65 */   private static final String[] Tens = { "", "x", "xx", "xxx", "xl", "l", "lx", "lxx", "lxxx", "xc" };
/*  31: 67 */   private static final String[] Ones = { "", "i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix" };
/*  32: 70 */   private StringBuffer _tempBuffer = new StringBuffer();
/*  33:    */   
/*  34:    */   protected NodeCounter(Translet translet, DOM document, DTMAxisIterator iterator)
/*  35:    */   {
/*  36: 74 */     this._translet = translet;
/*  37: 75 */     this._document = document;
/*  38: 76 */     this._iterator = iterator;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public abstract NodeCounter setStartNode(int paramInt);
/*  42:    */   
/*  43:    */   public NodeCounter setValue(double value)
/*  44:    */   {
/*  45: 90 */     this._value = value;
/*  46: 91 */     return this;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void setFormatting(String format, String lang, String letterValue, String groupSep, String groupSize)
/*  50:    */   {
/*  51: 99 */     this._lang = lang;
/*  52:100 */     this._groupSep = groupSep;
/*  53:101 */     this._letterValue = letterValue;
/*  54:    */     try
/*  55:    */     {
/*  56:104 */       this._groupSize = Integer.parseInt(groupSize);
/*  57:    */     }
/*  58:    */     catch (NumberFormatException e)
/*  59:    */     {
/*  60:107 */       this._groupSize = 0;
/*  61:    */     }
/*  62:109 */     setTokens(format);
/*  63:    */   }
/*  64:    */   
/*  65:    */   private final void setTokens(String format)
/*  66:    */   {
/*  67:115 */     if ((this._format != null) && (format.equals(this._format))) {
/*  68:116 */       return;
/*  69:    */     }
/*  70:118 */     this._format = format;
/*  71:    */     
/*  72:120 */     int length = this._format.length();
/*  73:121 */     boolean isFirst = true;
/*  74:122 */     this._separFirst = true;
/*  75:123 */     this._separLast = false;
/*  76:124 */     this._nSepars = 0;
/*  77:125 */     this._nFormats = 0;
/*  78:126 */     this._separToks.clear();
/*  79:127 */     this._formatToks.clear();
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:133 */     int j = 0;
/*  86:133 */     for (int i = 0; i < length;)
/*  87:    */     {
/*  88:134 */       char c = format.charAt(i);
/*  89:135 */       for (j = i; Character.isLetterOrDigit(c);)
/*  90:    */       {
/*  91:136 */         i++;
/*  92:136 */         if (i == length) {
/*  93:    */           break;
/*  94:    */         }
/*  95:137 */         c = format.charAt(i);
/*  96:    */       }
/*  97:139 */       if (i > j)
/*  98:    */       {
/*  99:140 */         if (isFirst)
/* 100:    */         {
/* 101:141 */           this._separToks.addElement(".");
/* 102:142 */           isFirst = this._separFirst = 0;
/* 103:    */         }
/* 104:144 */         this._formatToks.addElement(format.substring(j, i));
/* 105:    */       }
/* 106:147 */       if (i == length) {
/* 107:    */         break;
/* 108:    */       }
/* 109:149 */       c = format.charAt(i);
/* 110:150 */       for (j = i; !Character.isLetterOrDigit(c);)
/* 111:    */       {
/* 112:151 */         i++;
/* 113:151 */         if (i == length) {
/* 114:    */           break;
/* 115:    */         }
/* 116:152 */         c = format.charAt(i);
/* 117:153 */         isFirst = false;
/* 118:    */       }
/* 119:155 */       if (i > j) {
/* 120:156 */         this._separToks.addElement(format.substring(j, i));
/* 121:    */       }
/* 122:    */     }
/* 123:160 */     this._nSepars = this._separToks.size();
/* 124:161 */     this._nFormats = this._formatToks.size();
/* 125:162 */     if (this._nSepars > this._nFormats) {
/* 126:162 */       this._separLast = true;
/* 127:    */     }
/* 128:164 */     if (this._separFirst) {
/* 129:164 */       this._nSepars -= 1;
/* 130:    */     }
/* 131:165 */     if (this._separLast) {
/* 132:165 */       this._nSepars -= 1;
/* 133:    */     }
/* 134:166 */     if (this._nSepars == 0)
/* 135:    */     {
/* 136:167 */       this._separToks.insertElementAt(".", 1);
/* 137:168 */       this._nSepars += 1;
/* 138:    */     }
/* 139:170 */     if (this._separFirst) {
/* 140:170 */       this._nSepars += 1;
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public NodeCounter setDefaultFormatting()
/* 145:    */   {
/* 146:177 */     setFormatting("1", "en", "alphabetic", null, null);
/* 147:178 */     return this;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public abstract String getCounter();
/* 151:    */   
/* 152:    */   public String getCounter(String format, String lang, String letterValue, String groupSep, String groupSize)
/* 153:    */   {
/* 154:194 */     setFormatting(format, lang, letterValue, groupSep, groupSize);
/* 155:195 */     return getCounter();
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean matchesCount(int node)
/* 159:    */   {
/* 160:204 */     return this._nodeType == this._document.getExpandedTypeID(node);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean matchesFrom(int node)
/* 164:    */   {
/* 165:212 */     return false;
/* 166:    */   }
/* 167:    */   
/* 168:    */   protected String formatNumbers(int value)
/* 169:    */   {
/* 170:219 */     return formatNumbers(new int[] { value });
/* 171:    */   }
/* 172:    */   
/* 173:    */   protected String formatNumbers(int[] values)
/* 174:    */   {
/* 175:227 */     int nValues = values.length;
/* 176:228 */     int length = this._format.length();
/* 177:    */     
/* 178:230 */     boolean isEmpty = true;
/* 179:231 */     for (int i = 0; i < nValues; i++) {
/* 180:232 */       if (values[i] != -2147483648) {
/* 181:233 */         isEmpty = false;
/* 182:    */       }
/* 183:    */     }
/* 184:234 */     if (isEmpty) {
/* 185:234 */       return "";
/* 186:    */     }
/* 187:237 */     boolean isFirst = true;
/* 188:238 */     int t = 0;int n = 0;int s = 1;
/* 189:239 */     this._tempBuffer.setLength(0);
/* 190:240 */     StringBuffer buffer = this._tempBuffer;
/* 191:243 */     if (this._separFirst) {
/* 192:243 */       buffer.append((String)this._separToks.elementAt(0));
/* 193:    */     }
/* 194:246 */     while (n < nValues)
/* 195:    */     {
/* 196:247 */       int value = values[n];
/* 197:248 */       if (value != -2147483648)
/* 198:    */       {
/* 199:249 */         if (!isFirst) {
/* 200:249 */           buffer.append((String)this._separToks.elementAt(s++));
/* 201:    */         }
/* 202:250 */         formatValue(value, (String)this._formatToks.elementAt(t++), buffer);
/* 203:251 */         if (t == this._nFormats) {
/* 204:251 */           t--;
/* 205:    */         }
/* 206:252 */         if (s >= this._nSepars) {
/* 207:252 */           s--;
/* 208:    */         }
/* 209:253 */         isFirst = false;
/* 210:    */       }
/* 211:255 */       n++;
/* 212:    */     }
/* 213:259 */     if (this._separLast) {
/* 214:259 */       buffer.append((String)this._separToks.lastElement());
/* 215:    */     }
/* 216:260 */     return buffer.toString();
/* 217:    */   }
/* 218:    */   
/* 219:    */   private void formatValue(int value, String format, StringBuffer buffer)
/* 220:    */   {
/* 221:269 */     char c = format.charAt(0);
/* 222:271 */     if (Character.isDigit(c))
/* 223:    */     {
/* 224:272 */       char zero = (char)(c - Character.getNumericValue(c));
/* 225:    */       
/* 226:274 */       StringBuffer temp = buffer;
/* 227:275 */       if (this._groupSize > 0) {
/* 228:276 */         temp = new StringBuffer();
/* 229:    */       }
/* 230:278 */       String s = "";
/* 231:279 */       int n = value;
/* 232:280 */       while (n > 0)
/* 233:    */       {
/* 234:281 */         s = (char)(zero + n % 10) + s;
/* 235:282 */         n /= 10;
/* 236:    */       }
/* 237:285 */       for (int i = 0; i < format.length() - s.length(); i++) {
/* 238:286 */         temp.append(zero);
/* 239:    */       }
/* 240:288 */       temp.append(s);
/* 241:290 */       if (this._groupSize > 0) {
/* 242:291 */         for (int i = 0; i < temp.length(); i++)
/* 243:    */         {
/* 244:292 */           if ((i != 0) && ((temp.length() - i) % this._groupSize == 0)) {
/* 245:293 */             buffer.append(this._groupSep);
/* 246:    */           }
/* 247:295 */           buffer.append(temp.charAt(i));
/* 248:    */         }
/* 249:    */       }
/* 250:    */     }
/* 251:299 */     else if ((c == 'i') && (!this._letterValue.equals("alphabetic")))
/* 252:    */     {
/* 253:300 */       buffer.append(romanValue(value));
/* 254:    */     }
/* 255:302 */     else if ((c == 'I') && (!this._letterValue.equals("alphabetic")))
/* 256:    */     {
/* 257:303 */       buffer.append(romanValue(value).toUpperCase());
/* 258:    */     }
/* 259:    */     else
/* 260:    */     {
/* 261:306 */       int min = c;
/* 262:307 */       int max = c;
/* 263:310 */       if ((c >= 'α') && (c <= 'ω')) {
/* 264:311 */         max = 969;
/* 265:    */       } else {
/* 266:315 */         while (Character.isLetterOrDigit((char)(max + 1))) {
/* 267:316 */           max++;
/* 268:    */         }
/* 269:    */       }
/* 270:319 */       buffer.append(alphaValue(value, min, max));
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   private String alphaValue(int value, int min, int max)
/* 275:    */   {
/* 276:324 */     if (value <= 0) {
/* 277:325 */       return "" + value;
/* 278:    */     }
/* 279:328 */     int range = max - min + 1;
/* 280:329 */     char last = (char)((value - 1) % range + min);
/* 281:330 */     if (value > range) {
/* 282:331 */       return alphaValue((value - 1) / range, min, max) + last;
/* 283:    */     }
/* 284:334 */     return "" + last;
/* 285:    */   }
/* 286:    */   
/* 287:    */   private String romanValue(int n)
/* 288:    */   {
/* 289:339 */     if ((n <= 0) || (n > 4000)) {
/* 290:340 */       return "" + n;
/* 291:    */     }
/* 292:342 */     return Thousands[(n / 1000)] + Hundreds[(n / 100 % 10)] + Tens[(n / 10 % 10)] + Ones[(n % 10)];
/* 293:    */   }
/* 294:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.NodeCounter
 * JD-Core Version:    0.7.0.1
 */