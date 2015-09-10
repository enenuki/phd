/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlTable;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.host.RowContainer;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  13:    */ 
/*  14:    */ public class HTMLTableElement
/*  15:    */   extends RowContainer
/*  16:    */ {
/*  17:    */   private HTMLCollection tBodies_;
/*  18:    */   
/*  19:    */   public Object jsxGet_caption()
/*  20:    */   {
/*  21: 56 */     List<HtmlElement> captions = getDomNodeOrDie().getHtmlElementsByTagName("caption");
/*  22: 57 */     if (captions.isEmpty()) {
/*  23: 58 */       return null;
/*  24:    */     }
/*  25: 60 */     return getScriptableFor(captions.get(0));
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void jsxSet_caption(Object o)
/*  29:    */   {
/*  30: 68 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_105)) {
/*  31: 69 */       throw Context.reportRuntimeError("Can't set caption");
/*  32:    */     }
/*  33: 71 */     if (!(o instanceof HTMLTableCaptionElement)) {
/*  34: 72 */       throw Context.reportRuntimeError("Not a caption");
/*  35:    */     }
/*  36: 76 */     jsxFunction_deleteCaption();
/*  37:    */     
/*  38: 78 */     HTMLTableCaptionElement caption = (HTMLTableCaptionElement)o;
/*  39: 79 */     getDomNodeOrDie().appendChild(caption.getDomNodeOrDie());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object jsxGet_tFoot()
/*  43:    */   {
/*  44: 88 */     List<HtmlElement> tfoots = getDomNodeOrDie().getHtmlElementsByTagName("tfoot");
/*  45: 89 */     if (tfoots.isEmpty()) {
/*  46: 90 */       return null;
/*  47:    */     }
/*  48: 92 */     return getScriptableFor(tfoots.get(0));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void jsxSet_tFoot(Object o)
/*  52:    */   {
/*  53:100 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_106)) {
/*  54:101 */       throw Context.reportRuntimeError("Can't set tFoot");
/*  55:    */     }
/*  56:103 */     if ((!(o instanceof HTMLTableSectionElement)) || (!"TFOOT".equals(((HTMLTableSectionElement)o).jsxGet_tagName()))) {
/*  57:105 */       throw Context.reportRuntimeError("Not a tFoot");
/*  58:    */     }
/*  59:109 */     jsxFunction_deleteTFoot();
/*  60:    */     
/*  61:111 */     HTMLTableSectionElement tfoot = (HTMLTableSectionElement)o;
/*  62:112 */     getDomNodeOrDie().appendChild(tfoot.getDomNodeOrDie());
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object jsxGet_tHead()
/*  66:    */   {
/*  67:121 */     List<HtmlElement> theads = getDomNodeOrDie().getHtmlElementsByTagName("thead");
/*  68:122 */     if (theads.isEmpty()) {
/*  69:123 */       return null;
/*  70:    */     }
/*  71:125 */     return getScriptableFor(theads.get(0));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void jsxSet_tHead(Object o)
/*  75:    */   {
/*  76:133 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_107)) {
/*  77:134 */       throw Context.reportRuntimeError("Can't set tHead");
/*  78:    */     }
/*  79:136 */     if ((!(o instanceof HTMLTableSectionElement)) || (!"THEAD".equals(((HTMLTableSectionElement)o).jsxGet_tagName()))) {
/*  80:138 */       throw Context.reportRuntimeError("Not a tHead");
/*  81:    */     }
/*  82:142 */     jsxFunction_deleteTHead();
/*  83:    */     
/*  84:144 */     HTMLTableSectionElement thead = (HTMLTableSectionElement)o;
/*  85:145 */     getDomNodeOrDie().appendChild(thead.getDomNodeOrDie());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Object jsxGet_tBodies()
/*  89:    */   {
/*  90:153 */     if (this.tBodies_ == null)
/*  91:    */     {
/*  92:154 */       final HtmlTable table = (HtmlTable)getDomNodeOrDie();
/*  93:155 */       this.tBodies_ = new HTMLCollection(table, false, "HTMLTableElement.tBodies")
/*  94:    */       {
/*  95:    */         protected List<Object> computeElements()
/*  96:    */         {
/*  97:158 */           return new ArrayList(table.getBodies());
/*  98:    */         }
/*  99:    */       };
/* 100:    */     }
/* 101:162 */     return this.tBodies_;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Object jsxFunction_createCaption()
/* 105:    */   {
/* 106:173 */     return getScriptableFor(getDomNodeOrDie().appendChildIfNoneExists("caption"));
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Object jsxFunction_createTFoot()
/* 110:    */   {
/* 111:184 */     return getScriptableFor(getDomNodeOrDie().appendChildIfNoneExists("tfoot"));
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Object jsxFunction_createTHead()
/* 115:    */   {
/* 116:195 */     return getScriptableFor(getDomNodeOrDie().appendChildIfNoneExists("thead"));
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void jsxFunction_deleteCaption()
/* 120:    */   {
/* 121:205 */     getDomNodeOrDie().removeChild("caption", 0);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void jsxFunction_deleteTFoot()
/* 125:    */   {
/* 126:215 */     getDomNodeOrDie().removeChild("tfoot", 0);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void jsxFunction_deleteTHead()
/* 130:    */   {
/* 131:225 */     getDomNodeOrDie().removeChild("thead", 0);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void jsxFunction_refresh() {}
/* 135:    */   
/* 136:    */   protected boolean isContainedRow(HtmlTableRow row)
/* 137:    */   {
/* 138:243 */     DomNode parent = row.getParentNode();
/* 139:244 */     return (parent != null) && (parent.getParentNode() == getDomNodeOrDie());
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Object insertRow(int index)
/* 143:    */   {
/* 144:254 */     List<?> rowContainers = getDomNodeOrDie().getByXPath("//tbody | //thead | //tfoot");
/* 145:256 */     if ((rowContainers.isEmpty()) || (index == 0))
/* 146:    */     {
/* 147:257 */       HtmlElement tBody = getDomNodeOrDie().appendChildIfNoneExists("tbody");
/* 148:258 */       return ((RowContainer)getScriptableFor(tBody)).insertRow(0);
/* 149:    */     }
/* 150:260 */     return super.insertRow(index);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String jsxGet_width()
/* 154:    */   {
/* 155:268 */     return getDomNodeOrDie().getAttribute("width");
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void jsxSet_width(String width)
/* 159:    */   {
/* 160:276 */     getDomNodeOrDie().setAttribute("width", width);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String jsxGet_cellSpacing()
/* 164:    */   {
/* 165:284 */     return getDomNodeOrDie().getAttribute("cellspacing");
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void jsxSet_cellSpacing(String cellSpacing)
/* 169:    */   {
/* 170:292 */     getDomNodeOrDie().setAttribute("cellspacing", cellSpacing);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String jsxGet_cellPadding()
/* 174:    */   {
/* 175:300 */     return getDomNodeOrDie().getAttribute("cellpadding");
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void jsxSet_cellPadding(String cellPadding)
/* 179:    */   {
/* 180:308 */     getDomNodeOrDie().setAttribute("cellpadding", cellPadding);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String jsxGet_border()
/* 184:    */   {
/* 185:316 */     String border = getDomNodeOrDie().getAttribute("border");
/* 186:317 */     return border;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void jsxSet_border(String border)
/* 190:    */   {
/* 191:325 */     getDomNodeOrDie().setAttribute("border", border);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String jsxGet_bgColor()
/* 195:    */   {
/* 196:334 */     return getDomNodeOrDie().getAttribute("bgColor");
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void jsxSet_bgColor(String bgColor)
/* 200:    */   {
/* 201:343 */     setColorAttribute("bgColor", bgColor);
/* 202:    */   }
/* 203:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTableElement
 * JD-Core Version:    0.7.0.1
 */