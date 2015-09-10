/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomText;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HTMLParser;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlOption;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlSelect;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.IElementFactory;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.ScriptableWithFallbackGetter;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  16:    */ 
/*  17:    */ public class HTMLOptionsCollection
/*  18:    */   extends SimpleScriptable
/*  19:    */   implements ScriptableWithFallbackGetter
/*  20:    */ {
/*  21:    */   private HtmlSelect htmlSelect_;
/*  22:    */   
/*  23:    */   public HTMLOptionsCollection() {}
/*  24:    */   
/*  25:    */   public HTMLOptionsCollection(SimpleScriptable parentScope)
/*  26:    */   {
/*  27: 57 */     setParentScope(parentScope);
/*  28: 58 */     setPrototype(getPrototype(getClass()));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void initialize(HtmlSelect select)
/*  32:    */   {
/*  33: 66 */     WebAssert.notNull("select", select);
/*  34: 67 */     this.htmlSelect_ = select;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Object get(int index, Scriptable start)
/*  38:    */   {
/*  39: 80 */     if (index < 0) {
/*  40: 81 */       throw Context.reportRuntimeError("Index or size is negative");
/*  41:    */     }
/*  42:    */     Object response;
/*  43:    */     Object response;
/*  44: 83 */     if (index >= this.htmlSelect_.getOptionSize()) {
/*  45: 84 */       response = Context.getUndefinedValue();
/*  46:    */     } else {
/*  47: 87 */       response = getScriptableFor(this.htmlSelect_.getOption(index));
/*  48:    */     }
/*  49: 90 */     return response;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void put(String name, Scriptable start, Object value)
/*  53:    */   {
/*  54:106 */     if (this.htmlSelect_ == null)
/*  55:    */     {
/*  56:109 */       super.put(name, start, value);
/*  57:110 */       return;
/*  58:    */     }
/*  59:113 */     HTMLSelectElement parent = (HTMLSelectElement)this.htmlSelect_.getScriptObject();
/*  60:115 */     if ((!has(name, start)) && (ScriptableObject.hasProperty(parent, name))) {
/*  61:116 */       ScriptableObject.putProperty(parent, name, value);
/*  62:    */     } else {
/*  63:119 */       super.put(name, start, value);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Object getWithFallback(String name)
/*  68:    */   {
/*  69:132 */     if ((!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_87)) && ("childNodes".equals(name))) {
/*  70:133 */       return NOT_FOUND;
/*  71:    */     }
/*  72:137 */     HTMLSelectElement select = (HTMLSelectElement)this.htmlSelect_.getScriptObject();
/*  73:138 */     return ScriptableObject.getProperty(select, name);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object jsxFunction_item(int index)
/*  77:    */   {
/*  78:148 */     return get(index, null);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void put(int index, Scriptable start, Object newValue)
/*  82:    */   {
/*  83:159 */     if (newValue == null)
/*  84:    */     {
/*  85:161 */       this.htmlSelect_.removeOption(index);
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89:164 */       HTMLOptionElement option = (HTMLOptionElement)newValue;
/*  90:165 */       HtmlOption htmlOption = option.getDomNodeOrNull();
/*  91:166 */       if (index >= jsxGet_length()) {
/*  92:168 */         this.htmlSelect_.appendOption(htmlOption);
/*  93:    */       } else {
/*  94:172 */         this.htmlSelect_.replaceOption(index, htmlOption);
/*  95:    */       }
/*  96:    */     }
/*  97:175 */     if ((jsxGet_length() == 1) && (!this.htmlSelect_.isMultipleSelectEnabled())) {
/*  98:176 */       ((HTMLSelectElement)this.htmlSelect_.getScriptObject()).jsxSet_selectedIndex(0);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int jsxGet_length()
/* 103:    */   {
/* 104:186 */     return this.htmlSelect_.getOptionSize();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void jsxSet_length(int newLength)
/* 108:    */   {
/* 109:196 */     int currentLength = this.htmlSelect_.getOptionSize();
/* 110:197 */     if (currentLength > newLength) {
/* 111:198 */       this.htmlSelect_.setOptionSize(newLength);
/* 112:    */     } else {
/* 113:201 */       for (int i = currentLength; i < newLength; i++)
/* 114:    */       {
/* 115:202 */         HtmlOption option = (HtmlOption)HTMLParser.getFactory("option").createElement(this.htmlSelect_.getPage(), "option", null);
/* 116:    */         
/* 117:204 */         this.htmlSelect_.appendOption(option);
/* 118:205 */         if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_88)) {
/* 119:206 */           option.appendChild(new DomText(option.getPage(), ""));
/* 120:    */         }
/* 121:    */       }
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void jsxFunction_add(Object newOptionObject, Object newIndex)
/* 126:    */   {
/* 127:252 */     int index = jsxGet_length();
/* 128:255 */     if ((newIndex instanceof Number)) {
/* 129:256 */       index = ((Number)newIndex).intValue();
/* 130:    */     }
/* 131:261 */     put(index, null, newOptionObject);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void jsxFunction_remove(int index)
/* 135:    */   {
/* 136:269 */     if (index < 0) {
/* 137:270 */       Context.reportRuntimeError("Invalid index: " + index);
/* 138:    */     }
/* 139:273 */     if (index < jsxGet_length()) {
/* 140:274 */       this.htmlSelect_.removeOption(index);
/* 141:    */     }
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLOptionsCollection
 * JD-Core Version:    0.7.0.1
 */