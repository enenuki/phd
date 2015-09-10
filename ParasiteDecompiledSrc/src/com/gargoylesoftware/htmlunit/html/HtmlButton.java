/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   7:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.Map;
/*  13:    */ import org.apache.commons.logging.Log;
/*  14:    */ import org.apache.commons.logging.LogFactory;
/*  15:    */ 
/*  16:    */ public class HtmlButton
/*  17:    */   extends HtmlElement
/*  18:    */   implements DisabledElement, SubmittableElement, FormFieldWithNameHistory
/*  19:    */ {
/*  20: 44 */   private static final Log LOG = LogFactory.getLog(HtmlButton.class);
/*  21:    */   public static final String TAG_NAME = "button";
/*  22:    */   private String originalName_;
/*  23: 49 */   private Collection<String> previousNames_ = Collections.emptySet();
/*  24:    */   
/*  25:    */   HtmlButton(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  26:    */   {
/*  27: 61 */     super(namespaceURI, qualifiedName, page, attributes);
/*  28: 62 */     this.originalName_ = getNameAttribute();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setValueAttribute(String newValue)
/*  32:    */   {
/*  33: 71 */     setAttribute("value", newValue);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void doClickAction()
/*  37:    */     throws IOException
/*  38:    */   {
/*  39: 79 */     String type = getTypeAttribute().toLowerCase();
/*  40:    */     
/*  41: 81 */     HtmlForm form = getEnclosingForm();
/*  42: 82 */     if (form != null)
/*  43:    */     {
/*  44: 83 */       if ("submit".equals(type)) {
/*  45: 84 */         form.submit(this);
/*  46: 86 */       } else if ("reset".equals(type)) {
/*  47: 87 */         form.reset();
/*  48:    */       }
/*  49: 89 */       return;
/*  50:    */     }
/*  51: 91 */     super.doClickAction();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final boolean isDisabled()
/*  55:    */   {
/*  56: 98 */     return hasAttribute("disabled");
/*  57:    */   }
/*  58:    */   
/*  59:    */   public NameValuePair[] getSubmitKeyValuePairs()
/*  60:    */   {
/*  61:105 */     return new NameValuePair[] { new NameValuePair(getNameAttribute(), getValueAttribute()) };
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void reset()
/*  65:    */   {
/*  66:114 */     if (LOG.isDebugEnabled()) {
/*  67:115 */       LOG.debug("reset() not implemented for this element");
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setDefaultValue(String defaultValue)
/*  72:    */   {
/*  73:125 */     if (LOG.isDebugEnabled()) {
/*  74:126 */       LOG.debug("setDefaultValue() not implemented for this element");
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getDefaultValue()
/*  79:    */   {
/*  80:136 */     if (LOG.isDebugEnabled()) {
/*  81:137 */       LOG.debug("getDefaultValue() not implemented for this element");
/*  82:    */     }
/*  83:139 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setDefaultChecked(boolean defaultChecked) {}
/*  87:    */   
/*  88:    */   public boolean isDefaultChecked()
/*  89:    */   {
/*  90:167 */     return false;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public final String getNameAttribute()
/*  94:    */   {
/*  95:178 */     return getAttribute("name");
/*  96:    */   }
/*  97:    */   
/*  98:    */   public final String getValueAttribute()
/*  99:    */   {
/* 100:189 */     return getAttribute("value");
/* 101:    */   }
/* 102:    */   
/* 103:    */   public final String getTypeAttribute()
/* 104:    */   {
/* 105:202 */     String type = getAttribute("type");
/* 106:203 */     if (type == DomElement.ATTRIBUTE_NOT_DEFINED) {
/* 107:204 */       if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.BUTTON_EMPTY_TYPE_BUTTON)) {
/* 108:206 */         type = "button";
/* 109:    */       } else {
/* 110:209 */         type = "submit";
/* 111:    */       }
/* 112:    */     }
/* 113:212 */     return type;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public final String getDisabledAttribute()
/* 117:    */   {
/* 118:223 */     return getAttribute("disabled");
/* 119:    */   }
/* 120:    */   
/* 121:    */   public final String getTabIndexAttribute()
/* 122:    */   {
/* 123:234 */     return getAttribute("tabindex");
/* 124:    */   }
/* 125:    */   
/* 126:    */   public final String getAccessKeyAttribute()
/* 127:    */   {
/* 128:245 */     return getAttribute("accesskey");
/* 129:    */   }
/* 130:    */   
/* 131:    */   public final String getOnFocusAttribute()
/* 132:    */   {
/* 133:256 */     return getAttribute("onfocus");
/* 134:    */   }
/* 135:    */   
/* 136:    */   public final String getOnBlurAttribute()
/* 137:    */   {
/* 138:267 */     return getAttribute("onblur");
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String attributeValue)
/* 142:    */   {
/* 143:275 */     if ("name".equals(qualifiedName))
/* 144:    */     {
/* 145:276 */       if (this.previousNames_.isEmpty()) {
/* 146:277 */         this.previousNames_ = new HashSet();
/* 147:    */       }
/* 148:279 */       this.previousNames_.add(attributeValue);
/* 149:    */     }
/* 150:281 */     super.setAttributeNS(namespaceURI, qualifiedName, attributeValue);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getOriginalName()
/* 154:    */   {
/* 155:288 */     return this.originalName_;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Collection<String> getPreviousNames()
/* 159:    */   {
/* 160:295 */     return this.previousNames_;
/* 161:    */   }
/* 162:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlButton
 * JD-Core Version:    0.7.0.1
 */