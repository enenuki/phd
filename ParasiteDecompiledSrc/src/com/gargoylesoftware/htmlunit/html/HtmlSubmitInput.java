/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   7:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*   8:    */ import com.gargoylesoftware.htmlunit.util.StringUtils;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.PrintWriter;
/*  11:    */ import java.util.Map;
/*  12:    */ 
/*  13:    */ public class HtmlSubmitInput
/*  14:    */   extends HtmlInput
/*  15:    */ {
/*  16:    */   private static final String DEFAULT_VALUE = "Submit Query";
/*  17:    */   
/*  18:    */   HtmlSubmitInput(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  19:    */   {
/*  20: 54 */     super(namespaceURI, qualifiedName, page, attributes);
/*  21: 55 */     if ((getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.SUBMITINPUT_DEFAULT_VALUE_IF_VALUE_NOT_DEFINED)) && (!hasAttribute("value"))) {
/*  22: 58 */       setAttribute("value", "Submit Query");
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected void doClickAction()
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 73 */     HtmlForm form = getEnclosingForm();
/*  30: 74 */     if (form != null)
/*  31:    */     {
/*  32: 75 */       form.submit(this);
/*  33: 76 */       return;
/*  34:    */     }
/*  35: 78 */     super.doClickAction();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void reset() {}
/*  39:    */   
/*  40:    */   public String asText()
/*  41:    */   {
/*  42: 96 */     String text = getValueAttribute();
/*  43: 97 */     if (text == ATTRIBUTE_NOT_DEFINED) {
/*  44: 98 */       text = "Submit Query";
/*  45:    */     }
/*  46:100 */     return text;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void printOpeningTagContentAsXml(PrintWriter printWriter)
/*  50:    */   {
/*  51:108 */     printWriter.print(getTagName());
/*  52:110 */     for (DomAttr attribute : getAttributesMap().values())
/*  53:    */     {
/*  54:111 */       String name = attribute.getNodeName();
/*  55:112 */       String value = attribute.getValue();
/*  56:113 */       if ((!"value".equals(name)) || (!"Submit Query".equals(value)))
/*  57:    */       {
/*  58:114 */         printWriter.print(" ");
/*  59:115 */         printWriter.print(name);
/*  60:116 */         printWriter.print("=\"");
/*  61:117 */         printWriter.print(StringUtils.escapeXmlAttributeValue(value));
/*  62:118 */         printWriter.print("\"");
/*  63:    */       }
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public NameValuePair[] getSubmitKeyValuePairs()
/*  68:    */   {
/*  69:130 */     if ((getNameAttribute().length() != 0) && (!hasAttribute("value"))) {
/*  70:131 */       return new NameValuePair[] { new NameValuePair(getNameAttribute(), "Submit Query") };
/*  71:    */     }
/*  72:133 */     return super.getSubmitKeyValuePairs();
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlSubmitInput
 * JD-Core Version:    0.7.0.1
 */