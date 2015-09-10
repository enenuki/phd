/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   7:    */ import com.gargoylesoftware.htmlunit.util.KeyDataPair;
/*   8:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*   9:    */ import java.io.File;
/*  10:    */ import java.net.URI;
/*  11:    */ import java.net.URISyntaxException;
/*  12:    */ import java.util.Map;
/*  13:    */ import org.apache.commons.lang.StringUtils;
/*  14:    */ 
/*  15:    */ public class HtmlFileInput
/*  16:    */   extends HtmlInput
/*  17:    */ {
/*  18:    */   private String contentType_;
/*  19:    */   private byte[] data_;
/*  20:    */   private String valueAtFocus_;
/*  21:    */   
/*  22:    */   HtmlFileInput(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  23:    */   {
/*  24: 55 */     super(namespaceURI, qualifiedName, page, attributes);
/*  25: 56 */     setAttribute("value", "");
/*  26: 57 */     if (page.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.FILEINPUT_EMPTY_DEFAULT_VALUE)) {
/*  27: 58 */       setDefaultValue("");
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public final byte[] getData()
/*  32:    */   {
/*  33: 67 */     return this.data_;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public final void setData(byte[] data)
/*  37:    */   {
/*  38: 81 */     this.data_ = data;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public NameValuePair[] getSubmitKeyValuePairs()
/*  42:    */   {
/*  43: 89 */     String value = getValueAttribute();
/*  44: 91 */     if (StringUtils.isEmpty(value)) {
/*  45: 92 */       return new NameValuePair[] { new KeyDataPair(getNameAttribute(), new File(""), null, null) };
/*  46:    */     }
/*  47: 95 */     File file = null;
/*  48: 97 */     if (value.startsWith("file:/"))
/*  49:    */     {
/*  50: 98 */       if ((value.startsWith("file://")) && (!value.startsWith("file:///"))) {
/*  51: 99 */         value = "file:///" + value.substring(7);
/*  52:    */       }
/*  53:    */       try
/*  54:    */       {
/*  55:102 */         file = new File(new URI(value));
/*  56:    */       }
/*  57:    */       catch (URISyntaxException e) {}
/*  58:    */     }
/*  59:109 */     if (file == null) {
/*  60:110 */       file = new File(value);
/*  61:    */     }
/*  62:    */     String contentType;
/*  63:    */     String contentType;
/*  64:117 */     if (this.contentType_ == null) {
/*  65:118 */       contentType = getPage().getWebClient().guessContentType(file);
/*  66:    */     } else {
/*  67:121 */       contentType = this.contentType_;
/*  68:    */     }
/*  69:123 */     String charset = getPage().getPageEncoding();
/*  70:124 */     KeyDataPair keyDataPair = new KeyDataPair(getNameAttribute(), file, contentType, charset);
/*  71:125 */     keyDataPair.setData(this.data_);
/*  72:126 */     return new NameValuePair[] { keyDataPair };
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void reset() {}
/*  76:    */   
/*  77:    */   public void setDefaultValue(String defaultValue)
/*  78:    */   {
/*  79:145 */     setDefaultValue(defaultValue, false);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setContentType(String contentType)
/*  83:    */   {
/*  84:154 */     this.contentType_ = contentType;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getContentType()
/*  88:    */   {
/*  89:163 */     return this.contentType_;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void focus()
/*  93:    */   {
/*  94:171 */     super.focus();
/*  95:    */     
/*  96:173 */     this.valueAtFocus_ = getValueAttribute();
/*  97:    */   }
/*  98:    */   
/*  99:    */   void removeFocus()
/* 100:    */   {
/* 101:178 */     super.removeFocus();
/* 102:180 */     if (!this.valueAtFocus_.equals(getValueAttribute())) {
/* 103:181 */       executeOnChangeHandlerIfAppropriate(this);
/* 104:    */     }
/* 105:183 */     this.valueAtFocus_ = null;
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlFileInput
 * JD-Core Version:    0.7.0.1
 */