/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import java.util.Map;
/*   5:    */ 
/*   6:    */ public class HtmlParameter
/*   7:    */   extends HtmlElement
/*   8:    */ {
/*   9:    */   public static final String TAG_NAME = "param";
/*  10:    */   
/*  11:    */   HtmlParameter(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  12:    */   {
/*  13: 44 */     super(namespaceURI, qualifiedName, page, attributes);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public final String getIdAttribute()
/*  17:    */   {
/*  18: 56 */     return getAttribute("id");
/*  19:    */   }
/*  20:    */   
/*  21:    */   public final String getNameAttribute()
/*  22:    */   {
/*  23: 68 */     return getAttribute("name");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public final String getValueAttribute()
/*  27:    */   {
/*  28: 80 */     return getAttribute("value");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public final String getValueTypeAttribute()
/*  32:    */   {
/*  33: 92 */     return getAttribute("valuetype");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public final String getTypeAttribute()
/*  37:    */   {
/*  38:104 */     return getAttribute("type");
/*  39:    */   }
/*  40:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlParameter
 * JD-Core Version:    0.7.0.1
 */