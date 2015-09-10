/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.w3c.css.sac.LexicalUnit;
/*   5:    */ import org.w3c.dom.DOMException;
/*   6:    */ import org.w3c.dom.css.CSSPrimitiveValue;
/*   7:    */ import org.w3c.dom.css.RGBColor;
/*   8:    */ 
/*   9:    */ public class RGBColorImpl
/*  10:    */   implements RGBColor, Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 8152675334081993160L;
/*  13: 47 */   private CSSPrimitiveValue red = null;
/*  14: 48 */   private CSSPrimitiveValue green = null;
/*  15: 49 */   private CSSPrimitiveValue blue = null;
/*  16:    */   
/*  17:    */   protected RGBColorImpl(LexicalUnit lu)
/*  18:    */     throws DOMException
/*  19:    */   {
/*  20: 52 */     LexicalUnit next = lu;
/*  21: 53 */     this.red = new CSSValueImpl(next, true);
/*  22: 54 */     next = next.getNextLexicalUnit();
/*  23: 55 */     if (next != null)
/*  24:    */     {
/*  25: 57 */       if (next.getLexicalUnitType() != 0) {
/*  26: 60 */         throw new DOMException((short)12, "rgb parameters must be separated by ','.");
/*  27:    */       }
/*  28: 63 */       next = next.getNextLexicalUnit();
/*  29: 64 */       if (next != null)
/*  30:    */       {
/*  31: 66 */         this.green = new CSSValueImpl(next, true);
/*  32: 67 */         next = next.getNextLexicalUnit();
/*  33: 68 */         if (next != null)
/*  34:    */         {
/*  35: 70 */           if (next.getLexicalUnitType() != 0) {
/*  36: 73 */             throw new DOMException((short)12, "rgb parameters must be separated by ','.");
/*  37:    */           }
/*  38: 76 */           next = next.getNextLexicalUnit();
/*  39: 77 */           this.blue = new CSSValueImpl(next, true);
/*  40: 78 */           if ((next = next.getNextLexicalUnit()) != null) {
/*  41: 81 */             throw new DOMException((short)12, "Too many parameters for rgb function.");
/*  42:    */           }
/*  43:    */         }
/*  44:    */       }
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public RGBColorImpl() {}
/*  49:    */   
/*  50:    */   public CSSPrimitiveValue getRed()
/*  51:    */   {
/*  52: 93 */     return this.red;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setRed(CSSPrimitiveValue red)
/*  56:    */   {
/*  57: 97 */     this.red = red;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public CSSPrimitiveValue getGreen()
/*  61:    */   {
/*  62:101 */     return this.green;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setGreen(CSSPrimitiveValue green)
/*  66:    */   {
/*  67:105 */     this.green = green;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public CSSPrimitiveValue getBlue()
/*  71:    */   {
/*  72:109 */     return this.blue;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setBlue(CSSPrimitiveValue blue)
/*  76:    */   {
/*  77:113 */     this.blue = blue;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String toString()
/*  81:    */   {
/*  82:117 */     return "rgb(" + this.red.toString() + ", " + this.green.toString() + ", " + this.blue.toString() + ")";
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.RGBColorImpl
 * JD-Core Version:    0.7.0.1
 */