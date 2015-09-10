/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.host.MouseEvent;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.host.css.ComputedCSSStyleDeclaration;
/*  11:    */ import java.util.List;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  13:    */ 
/*  14:    */ public class HTMLTableCellElement
/*  15:    */   extends HTMLTableComponent
/*  16:    */ {
/*  17:    */   public void jsxFunction_setAttribute(String name, String value)
/*  18:    */   {
/*  19: 45 */     if (("noWrap".equals(name)) && (value != null) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_92))) {
/*  20: 47 */       value = "true";
/*  21:    */     }
/*  22: 49 */     super.jsxFunction_setAttribute(name, value);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int jsxGet_offsetHeight()
/*  26:    */   {
/*  27: 57 */     MouseEvent event = MouseEvent.getCurrentMouseEvent();
/*  28: 58 */     if (isAncestorOfEventTarget(event)) {
/*  29: 59 */       return super.jsxGet_offsetHeight();
/*  30:    */     }
/*  31: 62 */     ComputedCSSStyleDeclaration style = jsxGet_currentStyle();
/*  32: 63 */     boolean includeBorder = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_93);
/*  33: 64 */     return style.getCalculatedHeight(includeBorder, true);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int jsxGet_offsetWidth()
/*  37:    */   {
/*  38: 72 */     float w = super.jsxGet_offsetWidth();
/*  39: 73 */     MouseEvent event = MouseEvent.getCurrentMouseEvent();
/*  40: 74 */     if (isAncestorOfEventTarget(event)) {
/*  41: 75 */       return (int)w;
/*  42:    */     }
/*  43: 78 */     ComputedCSSStyleDeclaration style = jsxGet_currentStyle();
/*  44: 79 */     if ("collapse".equals(style.jsxGet_borderCollapse()))
/*  45:    */     {
/*  46: 80 */       HtmlTableRow row = getRow();
/*  47: 81 */       if (row != null)
/*  48:    */       {
/*  49: 82 */         HtmlElement thiz = getDomNodeOrDie();
/*  50: 83 */         List<HtmlTableCell> cells = row.getCells();
/*  51: 84 */         boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_94);
/*  52: 85 */         boolean leftmost = cells.indexOf(thiz) == 0;
/*  53: 86 */         boolean rightmost = cells.indexOf(thiz) == cells.size() - 1;
/*  54: 87 */         w = (float)(w - ((ie) && (leftmost) ? 0.0D : 0.5D) * style.getBorderLeft());
/*  55: 88 */         w = (float)(w - ((ie) && (rightmost) ? 0.0D : 0.5D) * style.getBorderRight());
/*  56:    */       }
/*  57:    */     }
/*  58: 92 */     return (int)w;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Integer jsxGet_cellIndex()
/*  62:    */   {
/*  63:101 */     HtmlTableCell cell = (HtmlTableCell)getDomNodeOrDie();
/*  64:102 */     HtmlTableRow row = cell.getEnclosingRow();
/*  65:103 */     if (row == null) {
/*  66:104 */       return Integer.valueOf(-1);
/*  67:    */     }
/*  68:106 */     return Integer.valueOf(row.getCells().indexOf(cell));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String jsxGet_abbr()
/*  72:    */   {
/*  73:114 */     return getDomNodeOrDie().getAttribute("abbr");
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void jsxSet_abbr(String abbr)
/*  77:    */   {
/*  78:122 */     getDomNodeOrDie().setAttribute("abbr", abbr);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String jsxGet_axis()
/*  82:    */   {
/*  83:130 */     return getDomNodeOrDie().getAttribute("axis");
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void jsxSet_axis(String axis)
/*  87:    */   {
/*  88:138 */     getDomNodeOrDie().setAttribute("axis", axis);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String jsxGet_bgColor()
/*  92:    */   {
/*  93:147 */     return getDomNodeOrDie().getAttribute("bgColor");
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void jsxSet_bgColor(String bgColor)
/*  97:    */   {
/*  98:156 */     setColorAttribute("bgColor", bgColor);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public int jsxGet_colSpan()
/* 102:    */   {
/* 103:164 */     String s = getDomNodeOrDie().getAttribute("colSpan");
/* 104:    */     try
/* 105:    */     {
/* 106:166 */       return Integer.parseInt(s);
/* 107:    */     }
/* 108:    */     catch (NumberFormatException e) {}
/* 109:169 */     return 1;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void jsxSet_colSpan(String colSpan)
/* 113:    */   {
/* 114:    */     String s;
/* 115:    */     try
/* 116:    */     {
/* 117:180 */       int i = (int)Double.parseDouble(colSpan);
/* 118:    */       String s;
/* 119:181 */       if (i > 0) {
/* 120:182 */         s = Integer.toString(i);
/* 121:    */       } else {
/* 122:185 */         throw new NumberFormatException(colSpan);
/* 123:    */       }
/* 124:    */     }
/* 125:    */     catch (NumberFormatException e)
/* 126:    */     {
/* 127:189 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_95)) {
/* 128:190 */         throw Context.throwAsScriptRuntimeEx(e);
/* 129:    */       }
/* 130:192 */       s = "1";
/* 131:    */     }
/* 132:194 */     getDomNodeOrDie().setAttribute("colSpan", s);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int jsxGet_rowSpan()
/* 136:    */   {
/* 137:202 */     String s = getDomNodeOrDie().getAttribute("rowSpan");
/* 138:    */     try
/* 139:    */     {
/* 140:204 */       return Integer.parseInt(s);
/* 141:    */     }
/* 142:    */     catch (NumberFormatException e) {}
/* 143:207 */     return 1;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void jsxSet_rowSpan(String rowSpan)
/* 147:    */   {
/* 148:    */     String s;
/* 149:    */     try
/* 150:    */     {
/* 151:218 */       int i = (int)Double.parseDouble(rowSpan);
/* 152:    */       String s;
/* 153:219 */       if (i > 0) {
/* 154:220 */         s = Integer.toString(i);
/* 155:    */       } else {
/* 156:223 */         throw new NumberFormatException(rowSpan);
/* 157:    */       }
/* 158:    */     }
/* 159:    */     catch (NumberFormatException e)
/* 160:    */     {
/* 161:227 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_96)) {
/* 162:228 */         throw Context.throwAsScriptRuntimeEx(e);
/* 163:    */       }
/* 164:230 */       s = "1";
/* 165:    */     }
/* 166:232 */     getDomNodeOrDie().setAttribute("rowSpan", s);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean jsxGet_noWrap()
/* 170:    */   {
/* 171:241 */     return getDomNodeOrDie().hasAttribute("noWrap");
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void jsxSet_noWrap(boolean noWrap)
/* 175:    */   {
/* 176:250 */     if (noWrap)
/* 177:    */     {
/* 178:251 */       String value = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_97) ? "true" : "";
/* 179:252 */       getDomNodeOrDie().setAttribute("noWrap", value);
/* 180:    */     }
/* 181:    */     else
/* 182:    */     {
/* 183:255 */       getDomNodeOrDie().removeAttribute("noWrap");
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   private HtmlTableRow getRow()
/* 188:    */   {
/* 189:264 */     DomNode node = getDomNodeOrDie();
/* 190:265 */     while ((node != null) && (!(node instanceof HtmlTableRow))) {
/* 191:266 */       node = node.getParentNode();
/* 192:    */     }
/* 193:268 */     return (HtmlTableRow)node;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String jsxGet_width()
/* 197:    */   {
/* 198:276 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_98);
/* 199:277 */     Boolean returnNegativeValues = ie ? Boolean.TRUE : null;
/* 200:278 */     return getWidthOrHeight("width", returnNegativeValues);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void jsxSet_width(String width)
/* 204:    */   {
/* 205:286 */     setWidthOrHeight("width", width, Boolean.valueOf(!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_99)));
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String jsxGet_height()
/* 209:    */   {
/* 210:294 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_100);
/* 211:295 */     Boolean returnNegativeValues = ie ? Boolean.TRUE : null;
/* 212:296 */     return getWidthOrHeight("height", returnNegativeValues);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void jsxSet_height(String width)
/* 216:    */   {
/* 217:304 */     setWidthOrHeight("height", width, Boolean.valueOf(!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_101)));
/* 218:    */   }
/* 219:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTableCellElement
 * JD-Core Version:    0.7.0.1
 */