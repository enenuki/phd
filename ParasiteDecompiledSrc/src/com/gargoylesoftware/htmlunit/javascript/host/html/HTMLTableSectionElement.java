/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.javascript.host.RowContainer;
/*   6:    */ 
/*   7:    */ public class HTMLTableSectionElement
/*   8:    */   extends RowContainer
/*   9:    */ {
/*  10: 33 */   private static final String[] VALIGN_VALID_VALUES_IE = { "top", "bottom", "middle", "baseline" };
/*  11:    */   private static final String VALIGN_DEFAULT_VALUE = "top";
/*  12:    */   
/*  13:    */   public String jsxGet_vAlign()
/*  14:    */   {
/*  15: 50 */     return getVAlign(getValidVAlignValues(), "top");
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void jsxSet_vAlign(Object vAlign)
/*  19:    */   {
/*  20: 58 */     setVAlign(vAlign, getValidVAlignValues());
/*  21:    */   }
/*  22:    */   
/*  23:    */   private String[] getValidVAlignValues()
/*  24:    */   {
/*  25:    */     String[] valid;
/*  26:    */     String[] valid;
/*  27: 67 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_109)) {
/*  28: 68 */       valid = VALIGN_VALID_VALUES_IE;
/*  29:    */     } else {
/*  30: 71 */       valid = null;
/*  31:    */     }
/*  32: 73 */     return valid;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String jsxGet_ch()
/*  36:    */   {
/*  37: 81 */     return getCh();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void jsxSet_ch(String ch)
/*  41:    */   {
/*  42: 89 */     setCh(ch);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String jsxGet_chOff()
/*  46:    */   {
/*  47: 97 */     return getChOff();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void jsxSet_chOff(String chOff)
/*  51:    */   {
/*  52:105 */     setChOff(chOff);
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTableSectionElement
 * JD-Core Version:    0.7.0.1
 */