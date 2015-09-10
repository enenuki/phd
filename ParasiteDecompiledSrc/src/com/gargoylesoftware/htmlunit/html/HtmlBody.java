/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import java.util.Map;
/*   5:    */ 
/*   6:    */ public class HtmlBody
/*   7:    */   extends HtmlElement
/*   8:    */ {
/*   9:    */   public static final String TAG_NAME = "body";
/*  10:    */   private final boolean temporary_;
/*  11:    */   
/*  12:    */   public HtmlBody(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes, boolean temporary)
/*  13:    */   {
/*  14: 51 */     super(namespaceURI, qualifiedName, page, attributes);
/*  15:    */     
/*  16: 53 */     this.temporary_ = temporary;
/*  17: 56 */     if ((getOwnerDocument() instanceof HtmlPage)) {
/*  18: 57 */       getScriptObject();
/*  19:    */     }
/*  20:    */   }
/*  21:    */   
/*  22:    */   public final String getOnLoadAttribute()
/*  23:    */   {
/*  24: 69 */     return getAttribute("onload");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public final String getOnUnloadAttribute()
/*  28:    */   {
/*  29: 80 */     return getAttribute("onunload");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public final String getBackgroundAttribute()
/*  33:    */   {
/*  34: 91 */     return getAttribute("background");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public final String getBgcolorAttribute()
/*  38:    */   {
/*  39:102 */     return getAttribute("bgcolor");
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final String getTextAttribute()
/*  43:    */   {
/*  44:113 */     return getAttribute("text");
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final String getLinkAttribute()
/*  48:    */   {
/*  49:124 */     return getAttribute("link");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final String getVlinkAttribute()
/*  53:    */   {
/*  54:135 */     return getAttribute("vlink");
/*  55:    */   }
/*  56:    */   
/*  57:    */   public final String getAlinkAttribute()
/*  58:    */   {
/*  59:146 */     return getAttribute("alink");
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final boolean isTemporary()
/*  63:    */   {
/*  64:157 */     return this.temporary_;
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlBody
 * JD-Core Version:    0.7.0.1
 */