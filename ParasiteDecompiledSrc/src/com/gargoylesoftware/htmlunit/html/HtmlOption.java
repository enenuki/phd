/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.Page;
/*   6:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.PrintWriter;
/*  10:    */ import java.util.Map;
/*  11:    */ 
/*  12:    */ public class HtmlOption
/*  13:    */   extends HtmlElement
/*  14:    */   implements DisabledElement
/*  15:    */ {
/*  16:    */   public static final String TAG_NAME = "option";
/*  17:    */   private final boolean initialSelectedState_;
/*  18:    */   private boolean selected_;
/*  19:    */   
/*  20:    */   HtmlOption(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  21:    */   {
/*  22: 56 */     super(namespaceURI, qualifiedName, page, attributes);
/*  23: 57 */     this.initialSelectedState_ = hasAttribute("selected");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean isSelected()
/*  27:    */   {
/*  28: 65 */     return (hasAttribute("selected")) || (this.selected_);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Page setSelected(boolean selected)
/*  32:    */   {
/*  33: 77 */     if (selected == isSelected()) {
/*  34: 78 */       return getPage();
/*  35:    */     }
/*  36: 80 */     HtmlSelect select = getEnclosingSelect();
/*  37: 81 */     if (select != null)
/*  38:    */     {
/*  39: 82 */       if ((!select.isMultipleSelectEnabled()) && (select.getOptionSize() == 1)) {
/*  40: 83 */         selected = true;
/*  41:    */       }
/*  42: 85 */       return select.setSelectedAttribute(this, selected);
/*  43:    */     }
/*  44: 89 */     setSelectedInternal(selected);
/*  45: 90 */     return getPage();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void insertBefore(DomNode newNode)
/*  49:    */     throws IllegalStateException
/*  50:    */   {
/*  51: 98 */     super.insertBefore(newNode);
/*  52: 99 */     if ((newNode instanceof HtmlOption))
/*  53:    */     {
/*  54:100 */       HtmlOption option = (HtmlOption)newNode;
/*  55:101 */       if (option.isSelected()) {
/*  56:102 */         getEnclosingSelect().setSelectedAttribute(option, true);
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public HtmlSelect getEnclosingSelect()
/*  62:    */   {
/*  63:112 */     return (HtmlSelect)getEnclosingElement("select");
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void reset()
/*  67:    */   {
/*  68:119 */     setSelectedInternal(this.initialSelectedState_);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public final String getSelectedAttribute()
/*  72:    */   {
/*  73:131 */     return getAttribute("selected");
/*  74:    */   }
/*  75:    */   
/*  76:    */   public final boolean isDefaultSelected()
/*  77:    */   {
/*  78:142 */     return this.initialSelectedState_;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public final boolean isDisabled()
/*  82:    */   {
/*  83:154 */     if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLOPTION_PREVENT_DISABLED)) {
/*  84:156 */       return false;
/*  85:    */     }
/*  86:158 */     return hasAttribute("disabled");
/*  87:    */   }
/*  88:    */   
/*  89:    */   public final String getDisabledAttribute()
/*  90:    */   {
/*  91:165 */     return getAttribute("disabled");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public final String getLabelAttribute()
/*  95:    */   {
/*  96:176 */     return getAttribute("label");
/*  97:    */   }
/*  98:    */   
/*  99:    */   public final void setLabelAttribute(String newLabel)
/* 100:    */   {
/* 101:187 */     setAttribute("label", newLabel);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public final String getValueAttribute()
/* 105:    */   {
/* 106:199 */     String value = getAttribute("value");
/* 107:200 */     if (value == ATTRIBUTE_NOT_DEFINED) {
/* 108:201 */       value = getText();
/* 109:    */     }
/* 110:203 */     return value;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public final void setValueAttribute(String newValue)
/* 114:    */   {
/* 115:214 */     setAttribute("value", newValue);
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected void doClickAction()
/* 119:    */     throws IOException
/* 120:    */   {
/* 121:223 */     if (!isSelected()) {
/* 122:224 */       setSelected(true);
/* 123:    */     }
/* 124:226 */     super.doClickAction();
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void printOpeningTagContentAsXml(PrintWriter printWriter)
/* 128:    */   {
/* 129:234 */     super.printOpeningTagContentAsXml(printWriter);
/* 130:235 */     if ((this.selected_) && (getAttribute("selected") == ATTRIBUTE_NOT_DEFINED)) {
/* 131:236 */       printWriter.print(" selected=\"selected\"");
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   void setSelectedInternal(boolean selected)
/* 136:    */   {
/* 137:246 */     this.selected_ = selected;
/* 138:247 */     if (!selected) {
/* 139:248 */       removeAttribute("selected");
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String asText()
/* 144:    */   {
/* 145:260 */     return super.asText();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setText(String text)
/* 149:    */   {
/* 150:268 */     if (((text == null) || (text.length() == 0)) && (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLOPTION_EMPTY_TEXT_IS_NO_CHILDREN)))
/* 151:    */     {
/* 152:271 */       removeAllChildren();
/* 153:    */     }
/* 154:    */     else
/* 155:    */     {
/* 156:274 */       DomNode child = getFirstChild();
/* 157:275 */       if (child == null) {
/* 158:276 */         appendChild(new DomText(getPage(), text));
/* 159:    */       } else {
/* 160:279 */         child.setNodeValue(text);
/* 161:    */       }
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getText()
/* 166:    */   {
/* 167:289 */     HtmlSerializer ser = new HtmlSerializer();
/* 168:290 */     ser.setIgnoreMaskedElements(false);
/* 169:291 */     return ser.asText(this);
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected DomNode getEventTargetElement()
/* 173:    */   {
/* 174:298 */     HtmlSelect select = getEnclosingSelect();
/* 175:299 */     if (select != null) {
/* 176:300 */       return select;
/* 177:    */     }
/* 178:302 */     return super.getEventTargetElement();
/* 179:    */   }
/* 180:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlOption
 * JD-Core Version:    0.7.0.1
 */