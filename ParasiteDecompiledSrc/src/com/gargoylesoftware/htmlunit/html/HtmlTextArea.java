/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.impl.SelectableTextInput;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.impl.SelectionDelegate;
/*   9:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*  10:    */ import java.io.PrintWriter;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.Collections;
/*  13:    */ import java.util.HashSet;
/*  14:    */ import java.util.Map;
/*  15:    */ import org.apache.commons.lang.StringEscapeUtils;
/*  16:    */ 
/*  17:    */ public class HtmlTextArea
/*  18:    */   extends HtmlElement
/*  19:    */   implements DisabledElement, SubmittableElement, SelectableTextInput, FormFieldWithNameHistory
/*  20:    */ {
/*  21:    */   public static final String TAG_NAME = "textarea";
/*  22:    */   private String defaultValue_;
/*  23:    */   private String valueAtFocus_;
/*  24:    */   private String originalName_;
/*  25: 54 */   private Collection<String> previousNames_ = Collections.emptySet();
/*  26: 56 */   private final SelectionDelegate selectionDelegate_ = new SelectionDelegate(this);
/*  27: 58 */   private final DoTypeProcessor doTypeProcessor_ = new DoTypeProcessor()
/*  28:    */   {
/*  29:    */     void typeDone(String newValue, int newCursorPosition)
/*  30:    */     {
/*  31: 61 */       HtmlTextArea.this.setTextInternal(newValue);
/*  32: 62 */       HtmlTextArea.this.setSelectionStart(newCursorPosition);
/*  33: 63 */       HtmlTextArea.this.setSelectionEnd(newCursorPosition);
/*  34:    */     }
/*  35:    */     
/*  36:    */     protected boolean acceptChar(char c)
/*  37:    */     {
/*  38: 67 */       return (super.acceptChar(c)) || (c == '\n') || (c == '\r');
/*  39:    */     }
/*  40:    */   };
/*  41:    */   
/*  42:    */   HtmlTextArea(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  43:    */   {
/*  44: 81 */     super(namespaceURI, qualifiedName, page, attributes);
/*  45: 82 */     this.originalName_ = getNameAttribute();
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void initDefaultValue()
/*  49:    */   {
/*  50: 91 */     if (this.defaultValue_ == null) {
/*  51: 92 */       this.defaultValue_ = readValue();
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final String getText()
/*  56:    */   {
/*  57:102 */     return readValue();
/*  58:    */   }
/*  59:    */   
/*  60:    */   private String readValue()
/*  61:    */   {
/*  62:106 */     StringBuilder buffer = new StringBuilder();
/*  63:107 */     for (DomNode node : getChildren()) {
/*  64:108 */       if ((node instanceof DomText)) {
/*  65:109 */         buffer.append(((DomText)node).getData());
/*  66:    */       }
/*  67:    */     }
/*  68:113 */     if ((buffer.length() > 0) && (buffer.charAt(0) == '\n')) {
/*  69:114 */       buffer.deleteCharAt(0);
/*  70:    */     }
/*  71:116 */     return buffer.toString();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final void setText(String newValue)
/*  75:    */   {
/*  76:128 */     initDefaultValue();
/*  77:129 */     setTextInternal(newValue);
/*  78:    */     
/*  79:131 */     HtmlInput.executeOnChangeHandlerIfAppropriate(this);
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void setTextInternal(String newValue)
/*  83:    */   {
/*  84:135 */     initDefaultValue();
/*  85:136 */     DomText child = (DomText)getFirstChild();
/*  86:137 */     if (child == null)
/*  87:    */     {
/*  88:138 */       DomText newChild = new DomText(getPage(), newValue);
/*  89:139 */       appendChild(newChild);
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:142 */       child.setData(newValue);
/*  94:    */     }
/*  95:145 */     setSelectionStart(newValue.length());
/*  96:146 */     setSelectionEnd(newValue.length());
/*  97:    */   }
/*  98:    */   
/*  99:    */   public NameValuePair[] getSubmitKeyValuePairs()
/* 100:    */   {
/* 101:153 */     String text = getText();
/* 102:154 */     text = text.replace("\r\n", "\n").replace("\n", "\r\n");
/* 103:    */     
/* 104:156 */     return new NameValuePair[] { new NameValuePair(getNameAttribute(), text) };
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void reset()
/* 108:    */   {
/* 109:164 */     initDefaultValue();
/* 110:165 */     setText(this.defaultValue_);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDefaultValue(String defaultValue)
/* 114:    */   {
/* 115:173 */     initDefaultValue();
/* 116:174 */     if (defaultValue == null) {
/* 117:175 */       defaultValue = "";
/* 118:    */     }
/* 119:179 */     if ((getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLINPUT_DEFAULT_IS_CHECKED)) && (getText().equals(getDefaultValue()))) {
/* 120:182 */       setTextInternal(defaultValue);
/* 121:    */     }
/* 122:184 */     this.defaultValue_ = defaultValue;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getDefaultValue()
/* 126:    */   {
/* 127:192 */     initDefaultValue();
/* 128:193 */     return this.defaultValue_;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setDefaultChecked(boolean defaultChecked) {}
/* 132:    */   
/* 133:    */   public boolean isDefaultChecked()
/* 134:    */   {
/* 135:215 */     return false;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public final String getNameAttribute()
/* 139:    */   {
/* 140:226 */     return getAttribute("name");
/* 141:    */   }
/* 142:    */   
/* 143:    */   public final String getRowsAttribute()
/* 144:    */   {
/* 145:237 */     return getAttribute("rows");
/* 146:    */   }
/* 147:    */   
/* 148:    */   public final String getColumnsAttribute()
/* 149:    */   {
/* 150:248 */     return getAttribute("cols");
/* 151:    */   }
/* 152:    */   
/* 153:    */   public final boolean isDisabled()
/* 154:    */   {
/* 155:255 */     return hasAttribute("disabled");
/* 156:    */   }
/* 157:    */   
/* 158:    */   public final String getDisabledAttribute()
/* 159:    */   {
/* 160:262 */     return getAttribute("disabled");
/* 161:    */   }
/* 162:    */   
/* 163:    */   public final String getReadOnlyAttribute()
/* 164:    */   {
/* 165:273 */     return getAttribute("readonly");
/* 166:    */   }
/* 167:    */   
/* 168:    */   public final String getTabIndexAttribute()
/* 169:    */   {
/* 170:284 */     return getAttribute("tabindex");
/* 171:    */   }
/* 172:    */   
/* 173:    */   public final String getAccessKeyAttribute()
/* 174:    */   {
/* 175:295 */     return getAttribute("accesskey");
/* 176:    */   }
/* 177:    */   
/* 178:    */   public final String getOnFocusAttribute()
/* 179:    */   {
/* 180:306 */     return getAttribute("onfocus");
/* 181:    */   }
/* 182:    */   
/* 183:    */   public final String getOnBlurAttribute()
/* 184:    */   {
/* 185:317 */     return getAttribute("onblur");
/* 186:    */   }
/* 187:    */   
/* 188:    */   public final String getOnSelectAttribute()
/* 189:    */   {
/* 190:328 */     return getAttribute("onselect");
/* 191:    */   }
/* 192:    */   
/* 193:    */   public final String getOnChangeAttribute()
/* 194:    */   {
/* 195:339 */     return getAttribute("onchange");
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void select()
/* 199:    */   {
/* 200:346 */     this.selectionDelegate_.select();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String getSelectedText()
/* 204:    */   {
/* 205:353 */     return this.selectionDelegate_.getSelectedText();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public int getSelectionStart()
/* 209:    */   {
/* 210:360 */     return this.selectionDelegate_.getSelectionStart();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setSelectionStart(int selectionStart)
/* 214:    */   {
/* 215:367 */     this.selectionDelegate_.setSelectionStart(selectionStart);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public int getSelectionEnd()
/* 219:    */   {
/* 220:374 */     return this.selectionDelegate_.getSelectionEnd();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setSelectionEnd(int selectionEnd)
/* 224:    */   {
/* 225:381 */     this.selectionDelegate_.setSelectionEnd(selectionEnd);
/* 226:    */   }
/* 227:    */   
/* 228:    */   protected void printXml(String indent, PrintWriter printWriter)
/* 229:    */   {
/* 230:392 */     printWriter.print(indent + "<");
/* 231:393 */     printOpeningTagContentAsXml(printWriter);
/* 232:    */     
/* 233:395 */     printWriter.print(">");
/* 234:396 */     printWriter.print(StringEscapeUtils.escapeXml(getText()));
/* 235:397 */     printWriter.print("</textarea>");
/* 236:    */   }
/* 237:    */   
/* 238:    */   protected void doType(char c, boolean shiftKey, boolean ctrlKey, boolean altKey)
/* 239:    */   {
/* 240:405 */     this.doTypeProcessor_.doType(getText(), getSelectionStart(), getSelectionEnd(), c, shiftKey, ctrlKey, altKey);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void focus()
/* 244:    */   {
/* 245:414 */     super.focus();
/* 246:415 */     this.valueAtFocus_ = getText();
/* 247:    */   }
/* 248:    */   
/* 249:    */   void removeFocus()
/* 250:    */   {
/* 251:423 */     super.removeFocus();
/* 252:424 */     if (!this.valueAtFocus_.equals(getText())) {
/* 253:425 */       HtmlInput.executeOnChangeHandlerIfAppropriate(this);
/* 254:    */     }
/* 255:427 */     this.valueAtFocus_ = null;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setReadOnly(boolean isReadOnly)
/* 259:    */   {
/* 260:436 */     if (isReadOnly) {
/* 261:437 */       setAttribute("readOnly", "readOnly");
/* 262:    */     } else {
/* 263:440 */       removeAttribute("readOnly");
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean isReadOnly()
/* 268:    */   {
/* 269:449 */     return hasAttribute("readOnly");
/* 270:    */   }
/* 271:    */   
/* 272:    */   protected Object clone()
/* 273:    */     throws CloneNotSupportedException
/* 274:    */   {
/* 275:457 */     return new HtmlTextArea(getNamespaceURI(), getQualifiedName(), getPage(), getAttributesMap());
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String attributeValue)
/* 279:    */   {
/* 280:465 */     if ("name".equals(qualifiedName))
/* 281:    */     {
/* 282:466 */       if (this.previousNames_.isEmpty()) {
/* 283:467 */         this.previousNames_ = new HashSet();
/* 284:    */       }
/* 285:469 */       this.previousNames_.add(attributeValue);
/* 286:    */     }
/* 287:471 */     super.setAttributeNS(namespaceURI, qualifiedName, attributeValue);
/* 288:    */   }
/* 289:    */   
/* 290:    */   public String getOriginalName()
/* 291:    */   {
/* 292:478 */     return this.originalName_;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Collection<String> getPreviousNames()
/* 296:    */   {
/* 297:485 */     return this.previousNames_;
/* 298:    */   }
/* 299:    */   
/* 300:    */   protected boolean isEmptyXmlTagExpanded()
/* 301:    */   {
/* 302:494 */     return true;
/* 303:    */   }
/* 304:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlTextArea
 * JD-Core Version:    0.7.0.1
 */