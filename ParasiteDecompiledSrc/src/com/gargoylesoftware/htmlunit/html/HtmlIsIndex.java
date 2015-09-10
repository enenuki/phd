/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   5:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*   6:    */ import java.util.Map;
/*   7:    */ 
/*   8:    */ public class HtmlIsIndex
/*   9:    */   extends HtmlElement
/*  10:    */   implements SubmittableElement
/*  11:    */ {
/*  12:    */   public static final String TAG_NAME = "isindex";
/*  13: 39 */   private String value_ = "";
/*  14:    */   
/*  15:    */   HtmlIsIndex(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  16:    */   {
/*  17: 51 */     super(namespaceURI, qualifiedName, page, attributes);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setValue(String newValue)
/*  21:    */   {
/*  22: 60 */     WebAssert.notNull("newValue", newValue);
/*  23: 61 */     this.value_ = newValue;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getValue()
/*  27:    */   {
/*  28: 70 */     return this.value_;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public NameValuePair[] getSubmitKeyValuePairs()
/*  32:    */   {
/*  33: 77 */     return new NameValuePair[] { new NameValuePair(getPromptAttribute(), getValue()) };
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void reset()
/*  37:    */   {
/*  38: 85 */     this.value_ = "";
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setDefaultValue(String defaultValue) {}
/*  42:    */   
/*  43:    */   public String getDefaultValue()
/*  44:    */   {
/*  45:102 */     return "";
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setDefaultChecked(boolean defaultChecked) {}
/*  49:    */   
/*  50:    */   public boolean isDefaultChecked()
/*  51:    */   {
/*  52:124 */     return false;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final String getPromptAttribute()
/*  56:    */   {
/*  57:135 */     return getAttribute("prompt");
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlIsIndex
 * JD-Core Version:    0.7.0.1
 */