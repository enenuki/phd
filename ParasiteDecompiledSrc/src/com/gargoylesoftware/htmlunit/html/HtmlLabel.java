/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*   4:    */ import com.gargoylesoftware.htmlunit.Page;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.Map;
/*   8:    */ 
/*   9:    */ public class HtmlLabel
/*  10:    */   extends HtmlElement
/*  11:    */ {
/*  12:    */   public static final String TAG_NAME = "label";
/*  13:    */   
/*  14:    */   HtmlLabel(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  15:    */   {
/*  16: 49 */     super(namespaceURI, qualifiedName, page, attributes);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public final String getForAttribute()
/*  20:    */   {
/*  21: 61 */     return getAttribute("for");
/*  22:    */   }
/*  23:    */   
/*  24:    */   public final String getAccessKeyAttribute()
/*  25:    */   {
/*  26: 73 */     return getAttribute("accesskey");
/*  27:    */   }
/*  28:    */   
/*  29:    */   public final String getOnFocusAttribute()
/*  30:    */   {
/*  31: 85 */     return getAttribute("onfocus");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final String getOnBlurAttribute()
/*  35:    */   {
/*  36: 97 */     return getAttribute("onblur");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void blur()
/*  40:    */   {
/*  41:105 */     HtmlElement element = getReferencedElement();
/*  42:106 */     if (element != null) {
/*  43:107 */       element.blur();
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void focus()
/*  48:    */   {
/*  49:116 */     HtmlElement element = getReferencedElement();
/*  50:117 */     if (element != null) {
/*  51:118 */       element.focus();
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public HtmlElement getReferencedElement()
/*  56:    */   {
/*  57:128 */     String elementId = getForAttribute();
/*  58:129 */     if (!ATTRIBUTE_NOT_DEFINED.equals(elementId)) {
/*  59:    */       try
/*  60:    */       {
/*  61:131 */         return getElementById(elementId);
/*  62:    */       }
/*  63:    */       catch (ElementNotFoundException e)
/*  64:    */       {
/*  65:134 */         return null;
/*  66:    */       }
/*  67:    */     }
/*  68:137 */     for (DomNode element : getChildren()) {
/*  69:138 */       if ((element instanceof HtmlInput)) {
/*  70:139 */         return (HtmlInput)element;
/*  71:    */       }
/*  72:    */     }
/*  73:142 */     return null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Page click()
/*  77:    */     throws IOException
/*  78:    */   {
/*  79:153 */     Page page = super.click();
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:159 */     HtmlElement element = getReferencedElement();
/*  86:    */     Page response;
/*  87:    */     Page response;
/*  88:160 */     if (element != null) {
/*  89:161 */       response = element.click();
/*  90:    */     } else {
/*  91:164 */       response = page;
/*  92:    */     }
/*  93:167 */     return response;
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlLabel
 * JD-Core Version:    0.7.0.1
 */