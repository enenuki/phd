/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.impl.SimpleRange;
/*   7:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   8:    */ import java.util.List;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  10:    */ 
/*  11:    */ public class Selection
/*  12:    */   extends SimpleScriptable
/*  13:    */ {
/*  14: 39 */   private String type_ = "None";
/*  15:    */   
/*  16:    */   public Object getDefaultValue(Class<?> hint)
/*  17:    */   {
/*  18: 46 */     boolean ff = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_176);
/*  19: 47 */     if ((ff) && ((String.class.equals(hint)) || (hint == null)))
/*  20:    */     {
/*  21: 48 */       StringBuilder sb = new StringBuilder();
/*  22: 49 */       for (org.w3c.dom.ranges.Range r : getRanges()) {
/*  23: 50 */         sb.append(r.toString());
/*  24:    */       }
/*  25: 52 */       return sb.toString();
/*  26:    */     }
/*  27: 54 */     return super.getDefaultValue(hint);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Node jsxGet_anchorNode()
/*  31:    */   {
/*  32: 62 */     org.w3c.dom.ranges.Range last = getLastRange();
/*  33: 63 */     if (last == null) {
/*  34: 64 */       return null;
/*  35:    */     }
/*  36: 66 */     return (Node)getScriptableNullSafe(last.getStartContainer());
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int jsxGet_anchorOffset()
/*  40:    */   {
/*  41: 74 */     org.w3c.dom.ranges.Range last = getLastRange();
/*  42: 75 */     if (last == null) {
/*  43: 76 */       return 0;
/*  44:    */     }
/*  45: 78 */     return last.getStartOffset();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Node jsxGet_focusNode()
/*  49:    */   {
/*  50: 86 */     org.w3c.dom.ranges.Range last = getLastRange();
/*  51: 87 */     if (last == null) {
/*  52: 88 */       return null;
/*  53:    */     }
/*  54: 90 */     return (Node)getScriptableNullSafe(last.getEndContainer());
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int jsxGet_focusOffset()
/*  58:    */   {
/*  59: 98 */     org.w3c.dom.ranges.Range last = getLastRange();
/*  60: 99 */     if (last == null) {
/*  61:100 */       return 0;
/*  62:    */     }
/*  63:102 */     return last.getEndOffset();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean jsxGet_isCollapsed()
/*  67:    */   {
/*  68:110 */     List<org.w3c.dom.ranges.Range> ranges = getRanges();
/*  69:111 */     return (ranges.isEmpty()) || ((ranges.size() == 1) && (((org.w3c.dom.ranges.Range)ranges.get(0)).getCollapsed()));
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int jsxGet_rangeCount()
/*  73:    */   {
/*  74:119 */     return getRanges().size();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String jsxGet_type()
/*  78:    */   {
/*  79:127 */     return this.type_;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public TextRange jsxFunction_createRange()
/*  83:    */   {
/*  84:136 */     org.w3c.dom.ranges.Range first = getFirstRange();
/*  85:    */     TextRange range;
/*  86:    */     TextRange range;
/*  87:137 */     if (first != null) {
/*  88:138 */       range = new TextRange(first);
/*  89:    */     } else {
/*  90:141 */       range = new TextRange(new SimpleRange());
/*  91:    */     }
/*  92:143 */     range.setParentScope(getParentScope());
/*  93:144 */     range.setPrototype(getPrototype(range.getClass()));
/*  94:145 */     return range;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void jsxFunction_addRange(Range range)
/*  98:    */   {
/*  99:153 */     getRanges().add(range.toW3C());
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void jsxFunction_removeRange(Range range)
/* 103:    */   {
/* 104:161 */     getRanges().remove(range.toW3C());
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void jsxFunction_removeAllRanges()
/* 108:    */   {
/* 109:168 */     getRanges().clear();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Range jsxFunction_getRangeAt(int index)
/* 113:    */   {
/* 114:178 */     List<org.w3c.dom.ranges.Range> ranges = getRanges();
/* 115:179 */     if ((index < 0) || (index >= ranges.size())) {
/* 116:180 */       throw Context.reportRuntimeError("Invalid range index: " + index);
/* 117:    */     }
/* 118:182 */     org.w3c.dom.ranges.Range range = (org.w3c.dom.ranges.Range)ranges.get(index);
/* 119:183 */     Range jsRange = new Range(range);
/* 120:    */     
/* 121:185 */     jsRange.setParentScope(getWindow());
/* 122:186 */     jsRange.setPrototype(getPrototype(Range.class));
/* 123:    */     
/* 124:188 */     return jsRange;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void jsxFunction_collapse(Node parentNode, int offset)
/* 128:    */   {
/* 129:197 */     List<org.w3c.dom.ranges.Range> ranges = getRanges();
/* 130:198 */     ranges.clear();
/* 131:199 */     ranges.add(new SimpleRange(parentNode.getDomNodeOrDie(), offset));
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void jsxFunction_collapseToEnd()
/* 135:    */   {
/* 136:206 */     org.w3c.dom.ranges.Range last = getLastRange();
/* 137:207 */     if (last != null)
/* 138:    */     {
/* 139:208 */       List<org.w3c.dom.ranges.Range> ranges = getRanges();
/* 140:209 */       ranges.clear();
/* 141:210 */       ranges.add(last);
/* 142:211 */       last.collapse(false);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void jsxFunction_collapseToStart()
/* 147:    */   {
/* 148:219 */     org.w3c.dom.ranges.Range first = getFirstRange();
/* 149:220 */     if (first != null)
/* 150:    */     {
/* 151:221 */       List<org.w3c.dom.ranges.Range> ranges = getRanges();
/* 152:222 */       ranges.clear();
/* 153:223 */       ranges.add(first);
/* 154:224 */       first.collapse(true);
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void jsxFunction_empty()
/* 159:    */   {
/* 160:232 */     this.type_ = "None";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void jsxFunction_extend(Node parentNode, int offset)
/* 164:    */   {
/* 165:241 */     org.w3c.dom.ranges.Range last = getLastRange();
/* 166:242 */     if (last != null) {
/* 167:243 */       last.setEnd(parentNode.getDomNodeOrDie(), offset);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void jsxFunction_selectAllChildren(Node parentNode)
/* 172:    */   {
/* 173:252 */     List<org.w3c.dom.ranges.Range> ranges = getRanges();
/* 174:253 */     ranges.clear();
/* 175:254 */     ranges.add(new SimpleRange(parentNode.getDomNodeOrDie()));
/* 176:    */   }
/* 177:    */   
/* 178:    */   private List<org.w3c.dom.ranges.Range> getRanges()
/* 179:    */   {
/* 180:262 */     HtmlPage page = (HtmlPage)getWindow().getDomNodeOrDie();
/* 181:263 */     return page.getSelectionRanges();
/* 182:    */   }
/* 183:    */   
/* 184:    */   private org.w3c.dom.ranges.Range getFirstRange()
/* 185:    */   {
/* 186:271 */     org.w3c.dom.ranges.Range first = null;
/* 187:272 */     for (org.w3c.dom.ranges.Range range : getRanges()) {
/* 188:273 */       if (first == null)
/* 189:    */       {
/* 190:274 */         first = range;
/* 191:    */       }
/* 192:    */       else
/* 193:    */       {
/* 194:277 */         org.w3c.dom.Node firstStart = first.getStartContainer();
/* 195:278 */         org.w3c.dom.Node rangeStart = range.getStartContainer();
/* 196:279 */         if ((firstStart.compareDocumentPosition(rangeStart) & 0x2) != 0) {
/* 197:280 */           first = range;
/* 198:    */         }
/* 199:    */       }
/* 200:    */     }
/* 201:284 */     return first;
/* 202:    */   }
/* 203:    */   
/* 204:    */   private org.w3c.dom.ranges.Range getLastRange()
/* 205:    */   {
/* 206:292 */     org.w3c.dom.ranges.Range last = null;
/* 207:293 */     for (org.w3c.dom.ranges.Range range : getRanges()) {
/* 208:294 */       if (last == null)
/* 209:    */       {
/* 210:295 */         last = range;
/* 211:    */       }
/* 212:    */       else
/* 213:    */       {
/* 214:298 */         org.w3c.dom.Node lastStart = last.getStartContainer();
/* 215:299 */         org.w3c.dom.Node rangeStart = range.getStartContainer();
/* 216:300 */         if ((lastStart.compareDocumentPosition(rangeStart) & 0x4) != 0) {
/* 217:301 */           last = range;
/* 218:    */         }
/* 219:    */       }
/* 220:    */     }
/* 221:305 */     return last;
/* 222:    */   }
/* 223:    */   
/* 224:    */   private SimpleScriptable getScriptableNullSafe(Object object)
/* 225:    */   {
/* 226:    */     SimpleScriptable scriptable;
/* 227:    */     SimpleScriptable scriptable;
/* 228:316 */     if (object != null) {
/* 229:317 */       scriptable = getScriptableFor(object);
/* 230:    */     } else {
/* 231:320 */       scriptable = null;
/* 232:    */     }
/* 233:322 */     return scriptable;
/* 234:    */   }
/* 235:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Selection
 * JD-Core Version:    0.7.0.1
 */