/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlInput;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.InputElementFactory;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.impl.SelectableTextInput;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.host.FormField;
/*  12:    */ import org.apache.commons.lang.math.NumberUtils;
/*  13:    */ import org.xml.sax.helpers.AttributesImpl;
/*  14:    */ 
/*  15:    */ public class HTMLInputElement
/*  16:    */   extends FormField
/*  17:    */ {
/*  18:    */   public void jsConstructor() {}
/*  19:    */   
/*  20:    */   public void jsxSet_type(String newType)
/*  21:    */   {
/*  22: 61 */     HtmlInput input = getHtmlInputOrDie();
/*  23: 63 */     if (!input.getTypeAttribute().equalsIgnoreCase(newType))
/*  24:    */     {
/*  25: 64 */       AttributesImpl attributes = readAttributes(input);
/*  26: 65 */       int index = attributes.getIndex("type");
/*  27: 66 */       attributes.setValue(index, newType);
/*  28:    */       
/*  29: 68 */       HtmlInput newInput = (HtmlInput)InputElementFactory.instance.createElement(input.getPage(), "input", attributes);
/*  30: 71 */       if (input.getParentNode() != null) {
/*  31: 72 */         input.getParentNode().replaceChild(newInput, input);
/*  32:    */       } else {
/*  33: 78 */         input = newInput;
/*  34:    */       }
/*  35: 81 */       input.setScriptObject(null);
/*  36: 82 */       setDomNode(newInput, true);
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void jsxSet_checked(boolean checked)
/*  41:    */   {
/*  42: 95 */     ((HtmlInput)getDomNodeOrDie()).setChecked(checked);
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected HtmlInput getHtmlInputOrDie()
/*  46:    */   {
/*  47:103 */     return (HtmlInput)getDomNodeOrDie();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean jsxGet_checked()
/*  51:    */   {
/*  52:115 */     return ((HtmlInput)getDomNodeOrDie()).isChecked();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void jsxFunction_select()
/*  56:    */   {
/*  57:122 */     HtmlInput input = getHtmlInputOrDie();
/*  58:123 */     if ((input instanceof HtmlTextInput)) {
/*  59:124 */       ((HtmlTextInput)getDomNodeOrDie()).select();
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void jsxFunction_setAttribute(String name, String value)
/*  64:    */   {
/*  65:136 */     if ("type".equals(name)) {
/*  66:137 */       jsxSet_type(value);
/*  67:    */     } else {
/*  68:140 */       super.jsxFunction_setAttribute(name, value);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String jsxGet_defaultValue()
/*  73:    */   {
/*  74:150 */     return ((HtmlInput)getDomNodeOrDie()).getDefaultValue();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void jsxSet_defaultValue(String defaultValue)
/*  78:    */   {
/*  79:159 */     ((HtmlInput)getDomNodeOrDie()).setDefaultValue(defaultValue);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean jsxGet_defaultChecked()
/*  83:    */   {
/*  84:168 */     return ((HtmlInput)getDomNodeOrDie()).isDefaultChecked();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void jsxSet_defaultChecked(boolean defaultChecked)
/*  88:    */   {
/*  89:177 */     ((HtmlInput)getDomNodeOrDie()).setDefaultChecked(defaultChecked);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int jsxGet_textLength()
/*  93:    */   {
/*  94:185 */     return jsxGet_value().length();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int jsxGet_selectionStart()
/*  98:    */   {
/*  99:193 */     return ((SelectableTextInput)getDomNodeOrDie()).getSelectionStart();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void jsxSet_selectionStart(int start)
/* 103:    */   {
/* 104:201 */     ((SelectableTextInput)getDomNodeOrDie()).setSelectionStart(start);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int jsxGet_selectionEnd()
/* 108:    */   {
/* 109:209 */     return ((SelectableTextInput)getDomNodeOrDie()).getSelectionEnd();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void jsxSet_selectionEnd(int end)
/* 113:    */   {
/* 114:217 */     ((SelectableTextInput)getDomNodeOrDie()).setSelectionEnd(end);
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected boolean isAttributeName(String name)
/* 118:    */   {
/* 119:225 */     if ("maxlength".equals(name.toLowerCase())) {
/* 120:226 */       return "maxLength".equals(name);
/* 121:    */     }
/* 122:229 */     if ("readOnly".equals(name.toLowerCase())) {
/* 123:230 */       return "readOnly".equals(name);
/* 124:    */     }
/* 125:232 */     return super.isAttributeName(name);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int jsxGet_maxLength()
/* 129:    */   {
/* 130:240 */     String attrValue = getDomNodeOrDie().getAttribute("maxLength");
/* 131:241 */     return NumberUtils.toInt(attrValue, -1);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void jsxSet_maxLength(int length)
/* 135:    */   {
/* 136:249 */     getDomNodeOrDie().setAttribute("maxLength", Integer.toString(length));
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean jsxGet_readOnly()
/* 140:    */   {
/* 141:257 */     return ((HtmlInput)getDomNodeOrDie()).isReadOnly();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void jsxSet_readOnly(boolean readOnly)
/* 145:    */   {
/* 146:265 */     ((HtmlInput)getDomNodeOrDie()).setReadOnly(readOnly);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void jsxFunction_setSelectionRange(int start, int end)
/* 150:    */   {
/* 151:274 */     jsxSet_selectionStart(start);
/* 152:275 */     jsxSet_selectionEnd(end);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String jsxGet_alt()
/* 156:    */   {
/* 157:283 */     String alt = getDomNodeOrDie().getAttribute("alt");
/* 158:284 */     return alt;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void jsxSet_alt(String alt)
/* 162:    */   {
/* 163:292 */     getDomNodeOrDie().setAttribute("alt", alt);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String jsxGet_border()
/* 167:    */   {
/* 168:300 */     String border = getDomNodeOrDie().getAttribute("border");
/* 169:301 */     return border;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void jsxSet_border(String border)
/* 173:    */   {
/* 174:309 */     getDomNodeOrDie().setAttribute("border", border);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String jsxGet_align()
/* 178:    */   {
/* 179:317 */     return getAlign(true);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void jsxSet_align(String align)
/* 183:    */   {
/* 184:325 */     boolean ignoreIfNoError = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_84);
/* 185:326 */     setAlign(align, ignoreIfNoError);
/* 186:    */   }
/* 187:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement
 * JD-Core Version:    0.7.0.1
 */