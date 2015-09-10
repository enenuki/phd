/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*   6:    */ import com.gargoylesoftware.htmlunit.Page;
/*   7:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   9:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.Map;
/*  12:    */ import org.apache.commons.lang.StringUtils;
/*  13:    */ 
/*  14:    */ public class HtmlImageInput
/*  15:    */   extends HtmlInput
/*  16:    */ {
/*  17:    */   private boolean wasPositionSpecified_;
/*  18:    */   private int xPosition_;
/*  19:    */   private int yPosition_;
/*  20:    */   
/*  21:    */   HtmlImageInput(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  22:    */   {
/*  23: 56 */     super(namespaceURI, qualifiedName, page, attributes);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public NameValuePair[] getSubmitKeyValuePairs()
/*  27:    */   {
/*  28: 64 */     String name = getNameAttribute();
/*  29:    */     String prefix;
/*  30:    */     String prefix;
/*  31: 67 */     if (StringUtils.isEmpty(name)) {
/*  32: 68 */       prefix = "";
/*  33:    */     } else {
/*  34: 71 */       prefix = name + ".";
/*  35:    */     }
/*  36: 74 */     if (this.wasPositionSpecified_)
/*  37:    */     {
/*  38: 75 */       NameValuePair valueX = new NameValuePair(prefix + 'x', Integer.toString(this.xPosition_));
/*  39: 76 */       NameValuePair valueY = new NameValuePair(prefix + 'y', Integer.toString(this.yPosition_));
/*  40: 77 */       if ((prefix.length() > 0) && (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLIMAGE_NAME_VALUE_PARAMS)) && (getValueAttribute().length() > 0)) {
/*  41: 80 */         return new NameValuePair[] { valueX, valueY, new NameValuePair(getNameAttribute(), getValueAttribute()) };
/*  42:    */       }
/*  43: 83 */       return new NameValuePair[] { valueX, valueY };
/*  44:    */     }
/*  45: 85 */     return new NameValuePair[] { new NameValuePair(getNameAttribute(), getValueAttribute()) };
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Page click()
/*  49:    */     throws IOException
/*  50:    */   {
/*  51: 99 */     return click(0, 0);
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected void doClickAction()
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:113 */     HtmlForm form = getEnclosingForm();
/*  58:114 */     if (form != null)
/*  59:    */     {
/*  60:115 */       form.submit(this);
/*  61:116 */       return;
/*  62:    */     }
/*  63:118 */     super.doClickAction();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public <P extends Page> P click(int x, int y)
/*  67:    */     throws IOException, ElementNotFoundException
/*  68:    */   {
/*  69:135 */     this.wasPositionSpecified_ = true;
/*  70:136 */     this.xPosition_ = x;
/*  71:137 */     this.yPosition_ = y;
/*  72:138 */     return super.click();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setDefaultValue(String defaultValue)
/*  76:    */   {
/*  77:147 */     super.setDefaultValue(defaultValue);
/*  78:148 */     setValueAttribute(defaultValue);
/*  79:    */   }
/*  80:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlImageInput
 * JD-Core Version:    0.7.0.1
 */