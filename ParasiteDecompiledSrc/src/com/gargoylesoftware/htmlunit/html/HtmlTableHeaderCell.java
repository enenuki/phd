/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import java.util.Map;
/*   5:    */ 
/*   6:    */ public class HtmlTableHeaderCell
/*   7:    */   extends HtmlTableCell
/*   8:    */ {
/*   9:    */   public static final String TAG_NAME = "th";
/*  10:    */   
/*  11:    */   HtmlTableHeaderCell(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  12:    */   {
/*  13: 45 */     super(namespaceURI, qualifiedName, page, attributes);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public final String getAbbrAttribute()
/*  17:    */   {
/*  18: 57 */     return getAttribute("abbr");
/*  19:    */   }
/*  20:    */   
/*  21:    */   public final String getAxisAttribute()
/*  22:    */   {
/*  23: 69 */     return getAttribute("axis");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public final String getHeadersAttribute()
/*  27:    */   {
/*  28: 81 */     return getAttribute("headers");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public final String getScopeAttribute()
/*  32:    */   {
/*  33: 93 */     return getAttribute("scope");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public final String getRowSpanAttribute()
/*  37:    */   {
/*  38:105 */     return getAttribute("rowspan");
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final String getColumnSpanAttribute()
/*  42:    */   {
/*  43:117 */     return getAttribute("colspan");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public final String getAlignAttribute()
/*  47:    */   {
/*  48:129 */     return getAttribute("align");
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final String getCharAttribute()
/*  52:    */   {
/*  53:141 */     return getAttribute("char");
/*  54:    */   }
/*  55:    */   
/*  56:    */   public final String getCharoffAttribute()
/*  57:    */   {
/*  58:153 */     return getAttribute("charoff");
/*  59:    */   }
/*  60:    */   
/*  61:    */   public final String getValignAttribute()
/*  62:    */   {
/*  63:165 */     return getAttribute("valign");
/*  64:    */   }
/*  65:    */   
/*  66:    */   public final String getNoWrapAttribute()
/*  67:    */   {
/*  68:177 */     return getAttribute("nowrap");
/*  69:    */   }
/*  70:    */   
/*  71:    */   public final String getBgcolorAttribute()
/*  72:    */   {
/*  73:189 */     return getAttribute("bgcolor");
/*  74:    */   }
/*  75:    */   
/*  76:    */   public final String getWidthAttribute()
/*  77:    */   {
/*  78:201 */     return getAttribute("width");
/*  79:    */   }
/*  80:    */   
/*  81:    */   public final String getHeightAttribute()
/*  82:    */   {
/*  83:213 */     return getAttribute("height");
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlTableHeaderCell
 * JD-Core Version:    0.7.0.1
 */