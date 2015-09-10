/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ 
/*   6:    */ public class HTMLTableComponent
/*   7:    */   extends HTMLElement
/*   8:    */ {
/*   9: 28 */   private static final String[] VALIGN_VALID_VALUES_IE = { "top", "bottom", "middle", "baseline" };
/*  10:    */   private static final String VALIGN_DEFAULT_VALUE = "top";
/*  11:    */   
/*  12:    */   public String jsxGet_align()
/*  13:    */   {
/*  14: 45 */     boolean invalidValues = getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLELEMENT_ALIGN_INVALID);
/*  15: 46 */     return getAlign(invalidValues);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void jsxSet_align(String align)
/*  19:    */   {
/*  20: 54 */     setAlign(align, false);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String jsxGet_vAlign()
/*  24:    */   {
/*  25: 62 */     return getVAlign(getValidVAlignValues(), "top");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void jsxSet_vAlign(Object vAlign)
/*  29:    */   {
/*  30: 70 */     setVAlign(vAlign, getValidVAlignValues());
/*  31:    */   }
/*  32:    */   
/*  33:    */   private String[] getValidVAlignValues()
/*  34:    */   {
/*  35:    */     String[] valid;
/*  36:    */     String[] valid;
/*  37: 79 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_104)) {
/*  38: 80 */       valid = VALIGN_VALID_VALUES_IE;
/*  39:    */     } else {
/*  40: 83 */       valid = null;
/*  41:    */     }
/*  42: 85 */     return valid;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String jsxGet_ch()
/*  46:    */   {
/*  47: 93 */     return getCh();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void jsxSet_ch(String ch)
/*  51:    */   {
/*  52:101 */     setCh(ch);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String jsxGet_chOff()
/*  56:    */   {
/*  57:109 */     return getChOff();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void jsxSet_chOff(String chOff)
/*  61:    */   {
/*  62:117 */     setChOff(chOff);
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTableComponent
 * JD-Core Version:    0.7.0.1
 */