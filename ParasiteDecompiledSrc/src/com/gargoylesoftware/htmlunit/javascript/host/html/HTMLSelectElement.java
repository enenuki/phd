/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HTMLParser;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlOption;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlSelect;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.IElementFactory;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.host.FormField;
/*  12:    */ import java.util.List;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.EvaluatorException;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  16:    */ 
/*  17:    */ public class HTMLSelectElement
/*  18:    */   extends FormField
/*  19:    */ {
/*  20:    */   private HTMLOptionsCollection optionsArray_;
/*  21:    */   
/*  22:    */   public void jsConstructor() {}
/*  23:    */   
/*  24:    */   public void initialize()
/*  25:    */   {
/*  26: 62 */     HtmlSelect htmlSelect = getHtmlSelect();
/*  27: 63 */     htmlSelect.setScriptObject(this);
/*  28: 64 */     if (this.optionsArray_ == null)
/*  29:    */     {
/*  30: 65 */       this.optionsArray_ = new HTMLOptionsCollection(this);
/*  31: 66 */       this.optionsArray_.initialize(htmlSelect);
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void jsxFunction_remove(int index)
/*  36:    */   {
/*  37: 75 */     put(index, null, null);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void jsxFunction_add(HTMLOptionElement newOptionObject, Object arg2)
/*  41:    */   {
/*  42: 85 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_89)) {
/*  43: 86 */       add_IE(newOptionObject, arg2);
/*  44:    */     } else {
/*  45: 89 */       add(newOptionObject, arg2);
/*  46:    */     }
/*  47: 91 */     ensureSelectedIndex();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Object jsxFunction_appendChild(Object childObject)
/*  51:    */   {
/*  52: 99 */     Object object = super.jsxFunction_appendChild(childObject);
/*  53:100 */     ensureSelectedIndex();
/*  54:101 */     return object;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object jsxFunction_insertBefore(Object[] args)
/*  58:    */   {
/*  59:109 */     Object object = super.jsxFunction_insertBefore(args);
/*  60:110 */     ensureSelectedIndex();
/*  61:111 */     return object;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public HTMLOptionElement jsxFunction_item(int index)
/*  65:    */   {
/*  66:120 */     if (index < 0)
/*  67:    */     {
/*  68:121 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_SELECT_ITEM_THROWS_IF_NEGATIVE)) {
/*  69:122 */         throw Context.reportRuntimeError("Invalid index for select node: " + index);
/*  70:    */       }
/*  71:124 */       return null;
/*  72:    */     }
/*  73:127 */     int length = jsxGet_length();
/*  74:128 */     if (index > length) {
/*  75:129 */       return null;
/*  76:    */     }
/*  77:132 */     return (HTMLOptionElement)getHtmlSelect().getOption(index).getScriptObject();
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected void add_IE(HTMLOptionElement newOptionObject, Object index)
/*  81:    */   {
/*  82:141 */     HtmlSelect select = getHtmlSelect();
/*  83:143 */     if (index == null) {
/*  84:144 */       throw new EvaluatorException("Null not supported as index.");
/*  85:    */     }
/*  86:    */     HtmlOption beforeOption;
/*  87:    */     HtmlOption beforeOption;
/*  88:147 */     if (Context.getUndefinedValue().equals(index))
/*  89:    */     {
/*  90:148 */       beforeOption = null;
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:151 */       int intIndex = ((Integer)Context.jsToJava(index, Integer.class)).intValue();
/*  95:    */       HtmlOption beforeOption;
/*  96:152 */       if (intIndex >= select.getOptionSize()) {
/*  97:153 */         beforeOption = null;
/*  98:    */       } else {
/*  99:156 */         beforeOption = select.getOption(intIndex);
/* 100:    */       }
/* 101:    */     }
/* 102:160 */     addBefore(newOptionObject, beforeOption);
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected void add(HTMLOptionElement newOptionObject, Object beforeOptionObject)
/* 106:    */   {
/* 107:    */     HtmlOption beforeOption;
/* 108:    */     HtmlOption beforeOption;
/* 109:170 */     if (beforeOptionObject == null)
/* 110:    */     {
/* 111:171 */       beforeOption = null;
/* 112:    */     }
/* 113:    */     else
/* 114:    */     {
/* 115:173 */       if (Context.getUndefinedValue().equals(beforeOptionObject)) {
/* 116:174 */         throw Context.reportRuntimeError("Not enough arguments [SelectElement.add]");
/* 117:    */       }
/* 118:177 */       beforeOption = (HtmlOption)((HTMLOptionElement)beforeOptionObject).getDomNodeOrDie();
/* 119:    */     }
/* 120:179 */     addBefore(newOptionObject, beforeOption);
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected void addBefore(HTMLOptionElement newOptionObject, HtmlOption beforeOption)
/* 124:    */   {
/* 125:189 */     HtmlSelect select = getHtmlSelect();
/* 126:    */     
/* 127:191 */     HtmlOption htmlOption = newOptionObject.getDomNodeOrNull();
/* 128:192 */     if (htmlOption == null) {
/* 129:193 */       htmlOption = (HtmlOption)HTMLParser.getFactory("option").createElement(select.getPage(), "option", null);
/* 130:    */     }
/* 131:197 */     if (beforeOption == null) {
/* 132:198 */       select.appendChild(htmlOption);
/* 133:    */     } else {
/* 134:201 */       beforeOption.insertBefore(htmlOption);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String jsxGet_type()
/* 139:    */   {
/* 140:    */     String type;
/* 141:    */     String type;
/* 142:212 */     if (getHtmlSelect().isMultipleSelectEnabled()) {
/* 143:213 */       type = "select-multiple";
/* 144:    */     } else {
/* 145:216 */       type = "select-one";
/* 146:    */     }
/* 147:218 */     return type;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public HTMLOptionsCollection jsxGet_options()
/* 151:    */   {
/* 152:226 */     if (this.optionsArray_ == null) {
/* 153:227 */       initialize();
/* 154:    */     }
/* 155:229 */     return this.optionsArray_;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int jsxGet_selectedIndex()
/* 159:    */   {
/* 160:237 */     HtmlSelect htmlSelect = getHtmlSelect();
/* 161:238 */     List<HtmlOption> selectedOptions = htmlSelect.getSelectedOptions();
/* 162:239 */     if (selectedOptions.isEmpty()) {
/* 163:240 */       return -1;
/* 164:    */     }
/* 165:242 */     List<HtmlOption> allOptions = htmlSelect.getOptions();
/* 166:243 */     return allOptions.indexOf(selectedOptions.get(0));
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void jsxSet_selectedIndex(int index)
/* 170:    */   {
/* 171:251 */     HtmlSelect htmlSelect = getHtmlSelect();
/* 172:253 */     if ((index != 0) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_SELECT_SELECTED_INDEX_THROWS_IF_BAD)) && ((index < -1) || (index >= htmlSelect.getOptionSize()))) {
/* 173:255 */       throw Context.reportRuntimeError("Invalid index for select node: " + index);
/* 174:    */     }
/* 175:258 */     for (HtmlOption itemToUnSelect : htmlSelect.getSelectedOptions()) {
/* 176:259 */       htmlSelect.setSelectedAttribute(itemToUnSelect, false);
/* 177:    */     }
/* 178:261 */     if (index < 0) {
/* 179:262 */       return;
/* 180:    */     }
/* 181:265 */     List<HtmlOption> allOptions = htmlSelect.getOptions();
/* 182:267 */     if (index < allOptions.size())
/* 183:    */     {
/* 184:268 */       HtmlOption itemToSelect = (HtmlOption)allOptions.get(index);
/* 185:269 */       htmlSelect.setSelectedAttribute(itemToSelect, true, false);
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String jsxGet_value()
/* 190:    */   {
/* 191:279 */     HtmlSelect htmlSelect = getHtmlSelect();
/* 192:280 */     List<HtmlOption> selectedOptions = htmlSelect.getSelectedOptions();
/* 193:281 */     if (selectedOptions.isEmpty()) {
/* 194:282 */       return "";
/* 195:    */     }
/* 196:284 */     return ((HTMLOptionElement)((HtmlOption)selectedOptions.get(0)).getScriptObject()).jsxGet_value();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public int jsxGet_length()
/* 200:    */   {
/* 201:292 */     if (this.optionsArray_ == null) {
/* 202:293 */       initialize();
/* 203:    */     }
/* 204:295 */     return this.optionsArray_.jsxGet_length();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void jsxSet_length(int newLength)
/* 208:    */   {
/* 209:303 */     if (this.optionsArray_ == null) {
/* 210:304 */       initialize();
/* 211:    */     }
/* 212:306 */     this.optionsArray_.jsxSet_length(newLength);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Object get(int index, Scriptable start)
/* 216:    */   {
/* 217:317 */     if (this.optionsArray_ == null) {
/* 218:318 */       initialize();
/* 219:    */     }
/* 220:320 */     return this.optionsArray_.get(index, start);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void put(int index, Scriptable start, Object newValue)
/* 224:    */   {
/* 225:331 */     if (this.optionsArray_ == null) {
/* 226:332 */       initialize();
/* 227:    */     }
/* 228:334 */     this.optionsArray_.put(index, start, newValue);
/* 229:    */   }
/* 230:    */   
/* 231:    */   private HtmlSelect getHtmlSelect()
/* 232:    */   {
/* 233:342 */     return (HtmlSelect)getDomNodeOrDie();
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void jsxSet_value(String newValue)
/* 237:    */   {
/* 238:351 */     getHtmlSelect().setSelectedAttribute(newValue, true);
/* 239:    */   }
/* 240:    */   
/* 241:    */   public int jsxGet_size()
/* 242:    */   {
/* 243:359 */     int size = 0;
/* 244:360 */     String sizeAttribute = getDomNodeOrDie().getAttribute("size");
/* 245:361 */     if ((sizeAttribute != DomElement.ATTRIBUTE_NOT_DEFINED) && (sizeAttribute != DomElement.ATTRIBUTE_VALUE_EMPTY)) {
/* 246:    */       try
/* 247:    */       {
/* 248:363 */         size = Integer.parseInt(sizeAttribute);
/* 249:    */       }
/* 250:    */       catch (Exception e) {}
/* 251:    */     }
/* 252:369 */     return size;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void jsxSet_size(String size)
/* 256:    */   {
/* 257:377 */     getDomNodeOrDie().setAttribute("size", size);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public boolean jsxGet_multiple()
/* 261:    */   {
/* 262:385 */     return getDomNodeOrDie().hasAttribute("multiple");
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void jsxSet_multiple(boolean multiple)
/* 266:    */   {
/* 267:393 */     if (multiple) {
/* 268:394 */       getDomNodeOrDie().setAttribute("multiple", "multiple");
/* 269:    */     } else {
/* 270:397 */       getDomNodeOrDie().removeAttribute("multiple");
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   private void ensureSelectedIndex()
/* 275:    */   {
/* 276:402 */     HtmlSelect select = getHtmlSelect();
/* 277:403 */     if (select.getOptionSize() == 0) {
/* 278:404 */       jsxSet_selectedIndex(-1);
/* 279:406 */     } else if (jsxGet_selectedIndex() == -1) {
/* 280:407 */       jsxSet_selectedIndex(0);
/* 281:    */     }
/* 282:    */   }
/* 283:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLSelectElement
 * JD-Core Version:    0.7.0.1
 */