/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*   4:    */ 
/*   5:    */ public class CSSPrimitiveValue
/*   6:    */   extends CSSValue
/*   7:    */ {
/*   8:    */   public static final short CSS_UNKNOWN = 0;
/*   9:    */   public static final short CSS_NUMBER = 1;
/*  10:    */   public static final short CSS_PERCENTAGE = 2;
/*  11:    */   public static final short CSS_EMS = 3;
/*  12:    */   public static final short CSS_EXS = 4;
/*  13:    */   public static final short CSS_PX = 5;
/*  14:    */   public static final short CSS_CM = 6;
/*  15:    */   public static final short CSS_MM = 7;
/*  16:    */   public static final short CSS_IN = 8;
/*  17:    */   public static final short CSS_PT = 9;
/*  18:    */   public static final short CSS_PC = 10;
/*  19:    */   public static final short CSS_DEG = 11;
/*  20:    */   public static final short CSS_RAD = 12;
/*  21:    */   public static final short CSS_GRAD = 13;
/*  22:    */   public static final short CSS_MS = 14;
/*  23:    */   public static final short CSS_S = 15;
/*  24:    */   public static final short CSS_HZ = 16;
/*  25:    */   public static final short CSS_KHZ = 17;
/*  26:    */   public static final short CSS_DIMENSION = 18;
/*  27:    */   public static final short CSS_STRING = 19;
/*  28:    */   public static final short CSS_URI = 20;
/*  29:    */   public static final short CSS_IDENT = 21;
/*  30:    */   public static final short CSS_ATTR = 22;
/*  31:    */   public static final short CSS_COUNTER = 23;
/*  32:    */   public static final short CSS_RECT = 24;
/*  33:    */   public static final short CSS_RGBCOLOR = 25;
/*  34:    */   private org.w3c.dom.css.CSSPrimitiveValue wrappedCssPrimitiveValue_;
/*  35:    */   
/*  36:    */   public CSSPrimitiveValue() {}
/*  37:    */   
/*  38:    */   CSSPrimitiveValue(HTMLElement element, org.w3c.dom.css.CSSPrimitiveValue cssValue)
/*  39:    */   {
/*  40:198 */     super(element, cssValue);
/*  41:199 */     setParentScope(element.getParentScope());
/*  42:200 */     setPrototype(getPrototype(getClass()));
/*  43:201 */     setDomNode(element.getDomNodeOrNull(), false);
/*  44:202 */     this.wrappedCssPrimitiveValue_ = cssValue;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public double jsxFunction_getFloatValue(int unitType)
/*  48:    */   {
/*  49:211 */     return this.wrappedCssPrimitiveValue_.getFloatValue((short)unitType);
/*  50:    */   }
/*  51:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.CSSPrimitiveValue
 * JD-Core Version:    0.7.0.1
 */