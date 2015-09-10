/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ 
/*  11:    */ public abstract class TableRowGroup
/*  12:    */   extends HtmlElement
/*  13:    */ {
/*  14:    */   protected TableRowGroup(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  15:    */   {
/*  16: 46 */     super(namespaceURI, qualifiedName, page, attributes);
/*  17: 47 */     boolean invalidAlign = getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLELEMENT_ALIGN_INVALID);
/*  18: 49 */     if ((invalidAlign) && (!hasAttribute("align"))) {
/*  19: 50 */       setAttribute("align", "left");
/*  20:    */     }
/*  21:    */   }
/*  22:    */   
/*  23:    */   public final List<HtmlTableRow> getRows()
/*  24:    */   {
/*  25: 60 */     List<HtmlTableRow> resultList = new ArrayList();
/*  26: 62 */     for (HtmlElement element : getChildElements()) {
/*  27: 63 */       if ((element instanceof HtmlTableRow)) {
/*  28: 64 */         resultList.add((HtmlTableRow)element);
/*  29:    */       }
/*  30:    */     }
/*  31: 68 */     return resultList;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final String getAlignAttribute()
/*  35:    */   {
/*  36: 80 */     return getAttribute("align");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final String getCharAttribute()
/*  40:    */   {
/*  41: 92 */     return getAttribute("char");
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final String getCharoffAttribute()
/*  45:    */   {
/*  46:104 */     return getAttribute("charoff");
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final String getValignAttribute()
/*  50:    */   {
/*  51:116 */     return getAttribute("valign");
/*  52:    */   }
/*  53:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.TableRowGroup
 * JD-Core Version:    0.7.0.1
 */