/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import java.util.Map;
/*   5:    */ 
/*   6:    */ public class HtmlTableColumnGroup
/*   7:    */   extends HtmlElement
/*   8:    */ {
/*   9:    */   public static final String TAG_NAME = "colgroup";
/*  10:    */   
/*  11:    */   HtmlTableColumnGroup(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  12:    */   {
/*  13: 45 */     super(namespaceURI, qualifiedName, page, attributes);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public final String getSpanAttribute()
/*  17:    */   {
/*  18: 57 */     return getAttribute("span");
/*  19:    */   }
/*  20:    */   
/*  21:    */   public final String getWidthAttribute()
/*  22:    */   {
/*  23: 69 */     return getAttribute("width");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public final String getAlignAttribute()
/*  27:    */   {
/*  28: 81 */     return getAttribute("align");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public final String getCharAttribute()
/*  32:    */   {
/*  33: 93 */     return getAttribute("char");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public final String getCharoffAttribute()
/*  37:    */   {
/*  38:105 */     return getAttribute("charoff");
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final String getValignAttribute()
/*  42:    */   {
/*  43:117 */     return getAttribute("valign");
/*  44:    */   }
/*  45:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlTableColumnGroup
 * JD-Core Version:    0.7.0.1
 */