/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.impl.SelectableTextInput;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.impl.SimpleRange;
/*   6:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   7:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*  10:    */ import org.apache.commons.logging.Log;
/*  11:    */ import org.apache.commons.logging.LogFactory;
/*  12:    */ import org.w3c.dom.Node;
/*  13:    */ import org.w3c.dom.ranges.Range;
/*  14:    */ 
/*  15:    */ public class TextRange
/*  16:    */   extends SimpleScriptable
/*  17:    */ {
/*  18: 41 */   private static final Log LOG = LogFactory.getLog(TextRange.class);
/*  19:    */   private Range range_;
/*  20:    */   
/*  21:    */   public TextRange() {}
/*  22:    */   
/*  23:    */   public TextRange(HTMLElement elt)
/*  24:    */   {
/*  25: 58 */     this.range_ = new SimpleRange(elt.getDomNodeOrDie());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public TextRange(Range range)
/*  29:    */   {
/*  30: 66 */     this.range_ = range.cloneRange();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String jsxGet_text()
/*  34:    */   {
/*  35: 74 */     return this.range_.toString();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void jsxSet_text(String text)
/*  39:    */   {
/*  40: 82 */     if ((this.range_.getStartContainer() == this.range_.getEndContainer()) && ((this.range_.getStartContainer() instanceof SelectableTextInput)))
/*  41:    */     {
/*  42: 84 */       SelectableTextInput input = (SelectableTextInput)this.range_.getStartContainer();
/*  43: 85 */       String oldValue = input.getText();
/*  44: 86 */       input.setText(oldValue.substring(0, input.getSelectionStart()) + text + oldValue.substring(input.getSelectionEnd()));
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String jsxGet_htmlText()
/*  49:    */   {
/*  50: 96 */     Node node = this.range_.getCommonAncestorContainer();
/*  51: 97 */     HTMLElement element = (HTMLElement)getScriptableFor(node);
/*  52: 98 */     return element.jsxGet_outerHTML();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object jsxFunction_duplicate()
/*  56:    */   {
/*  57:107 */     TextRange range = new TextRange(this.range_.cloneRange());
/*  58:108 */     range.setParentScope(getParentScope());
/*  59:109 */     range.setPrototype(getPrototype());
/*  60:110 */     return range;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Object jsxFunction_parentElement()
/*  64:    */   {
/*  65:124 */     Node parent = this.range_.getCommonAncestorContainer();
/*  66:125 */     return getScriptableFor(parent);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void jsxFunction_collapse(boolean toStart)
/*  70:    */   {
/*  71:134 */     this.range_.collapse(toStart);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void jsxFunction_select()
/*  75:    */   {
/*  76:143 */     HtmlPage page = (HtmlPage)getWindow().getDomNodeOrDie();
/*  77:144 */     page.setSelectionRange(this.range_);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int jsxFunction_moveStart(String unit, Object count)
/*  81:    */   {
/*  82:154 */     if (!"character".equals(unit))
/*  83:    */     {
/*  84:155 */       LOG.warn("moveStart('" + unit + "') is not yet supported");
/*  85:156 */       return 0;
/*  86:    */     }
/*  87:158 */     int c = 1;
/*  88:159 */     if (count != Undefined.instance) {
/*  89:160 */       c = (int)Context.toNumber(count);
/*  90:    */     }
/*  91:162 */     if ((this.range_.getStartContainer() == this.range_.getEndContainer()) && ((this.range_.getStartContainer() instanceof SelectableTextInput)))
/*  92:    */     {
/*  93:164 */       SelectableTextInput input = (SelectableTextInput)this.range_.getStartContainer();
/*  94:165 */       this.range_.setStart(input, this.range_.getStartOffset() + c);
/*  95:    */     }
/*  96:167 */     return c;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int jsxFunction_moveEnd(String unit, Object count)
/* 100:    */   {
/* 101:177 */     if (!"character".equals(unit))
/* 102:    */     {
/* 103:178 */       LOG.warn("moveEnd('" + unit + "') is not yet supported");
/* 104:179 */       return 0;
/* 105:    */     }
/* 106:181 */     int c = 1;
/* 107:182 */     if (count != Undefined.instance) {
/* 108:183 */       c = (int)Context.toNumber(count);
/* 109:    */     }
/* 110:185 */     if ((this.range_.getStartContainer() == this.range_.getEndContainer()) && ((this.range_.getStartContainer() instanceof SelectableTextInput)))
/* 111:    */     {
/* 112:187 */       SelectableTextInput input = (SelectableTextInput)this.range_.getStartContainer();
/* 113:188 */       this.range_.setEnd(input, this.range_.getEndOffset() + c);
/* 114:    */     }
/* 115:190 */     return c;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void jsxFunction_moveToElementText(HTMLElement element)
/* 119:    */   {
/* 120:200 */     this.range_.selectNode(element.getDomNodeOrDie());
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean jsxFunction_inRange(TextRange other)
/* 124:    */   {
/* 125:210 */     Range otherRange = other.range_;
/* 126:    */     
/* 127:212 */     Node start = this.range_.getStartContainer();
/* 128:213 */     Node otherStart = otherRange.getStartContainer();
/* 129:214 */     if (otherStart == null) {
/* 130:215 */       return false;
/* 131:    */     }
/* 132:217 */     short startComparison = start.compareDocumentPosition(otherStart);
/* 133:218 */     boolean startNodeBefore = (startComparison == 0) || ((startComparison & 0x8) != 0) || ((startComparison & 0x2) != 0);
/* 134:221 */     if ((startNodeBefore) && ((start != otherStart) || (this.range_.getStartOffset() <= otherRange.getStartOffset())))
/* 135:    */     {
/* 136:222 */       Node end = this.range_.getEndContainer();
/* 137:223 */       Node otherEnd = otherRange.getEndContainer();
/* 138:224 */       short endComparison = end.compareDocumentPosition(otherEnd);
/* 139:225 */       boolean endNodeAfter = (endComparison == 0) || ((endComparison & 0x8) != 0) || ((endComparison & 0x4) != 0);
/* 140:228 */       if ((endNodeAfter) && ((end != otherEnd) || (this.range_.getEndOffset() >= otherRange.getEndOffset()))) {
/* 141:229 */         return true;
/* 142:    */       }
/* 143:    */     }
/* 144:233 */     return false;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void jsxFunction_setEndPoint(String type, TextRange other)
/* 148:    */   {
/* 149:243 */     Range otherRange = other.range_;
/* 150:    */     int offset;
/* 151:    */     Node target;
/* 152:    */     int offset;
/* 153:247 */     if (type.endsWith("ToStart"))
/* 154:    */     {
/* 155:248 */       Node target = otherRange.getStartContainer();
/* 156:249 */       offset = otherRange.getStartOffset();
/* 157:    */     }
/* 158:    */     else
/* 159:    */     {
/* 160:252 */       target = otherRange.getEndContainer();
/* 161:253 */       offset = otherRange.getEndOffset();
/* 162:    */     }
/* 163:256 */     if (type.startsWith("Start")) {
/* 164:257 */       this.range_.setStart(target, offset);
/* 165:    */     } else {
/* 166:260 */       this.range_.setEnd(target, offset);
/* 167:    */     }
/* 168:    */   }
/* 169:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.TextRange
 * JD-Core Version:    0.7.0.1
 */