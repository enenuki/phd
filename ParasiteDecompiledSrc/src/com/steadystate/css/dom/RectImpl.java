/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.w3c.css.sac.LexicalUnit;
/*   5:    */ import org.w3c.dom.DOMException;
/*   6:    */ import org.w3c.dom.css.CSSPrimitiveValue;
/*   7:    */ import org.w3c.dom.css.Rect;
/*   8:    */ 
/*   9:    */ public class RectImpl
/*  10:    */   implements Rect, Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -7031248513917920621L;
/*  13:    */   private CSSPrimitiveValue left;
/*  14:    */   private CSSPrimitiveValue top;
/*  15:    */   private CSSPrimitiveValue right;
/*  16:    */   private CSSPrimitiveValue bottom;
/*  17:    */   
/*  18:    */   public void setLeft(CSSPrimitiveValue left)
/*  19:    */   {
/*  20: 55 */     this.left = left;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setTop(CSSPrimitiveValue top)
/*  24:    */   {
/*  25: 60 */     this.top = top;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setRight(CSSPrimitiveValue right)
/*  29:    */   {
/*  30: 65 */     this.right = right;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setBottom(CSSPrimitiveValue bottom)
/*  34:    */   {
/*  35: 70 */     this.bottom = bottom;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public RectImpl(LexicalUnit lu)
/*  39:    */     throws DOMException
/*  40:    */   {
/*  41: 76 */     LexicalUnit next = lu;
/*  42: 77 */     this.left = new CSSValueImpl(next, true);
/*  43: 78 */     next = next.getNextLexicalUnit();
/*  44: 79 */     if (next != null)
/*  45:    */     {
/*  46: 81 */       if (next.getLexicalUnitType() != 0) {
/*  47: 84 */         throw new DOMException((short)12, "Rect parameters must be separated by ','.");
/*  48:    */       }
/*  49: 87 */       next = next.getNextLexicalUnit();
/*  50: 88 */       if (next != null)
/*  51:    */       {
/*  52: 90 */         this.top = new CSSValueImpl(next, true);
/*  53: 91 */         next = next.getNextLexicalUnit();
/*  54: 92 */         if (next != null)
/*  55:    */         {
/*  56: 94 */           if (next.getLexicalUnitType() != 0) {
/*  57: 97 */             throw new DOMException((short)12, "Rect parameters must be separated by ','.");
/*  58:    */           }
/*  59:100 */           next = next.getNextLexicalUnit();
/*  60:101 */           if (next != null)
/*  61:    */           {
/*  62:103 */             this.right = new CSSValueImpl(next, true);
/*  63:104 */             next = next.getNextLexicalUnit();
/*  64:105 */             if (next != null)
/*  65:    */             {
/*  66:107 */               if (next.getLexicalUnitType() != 0) {
/*  67:110 */                 throw new DOMException((short)12, "Rect parameters must be separated by ','.");
/*  68:    */               }
/*  69:113 */               next = next.getNextLexicalUnit();
/*  70:114 */               if (next != null)
/*  71:    */               {
/*  72:116 */                 this.bottom = new CSSValueImpl(next, true);
/*  73:117 */                 if ((next = next.getNextLexicalUnit()) != null) {
/*  74:120 */                   throw new DOMException((short)12, "Too many parameters for rect function.");
/*  75:    */                 }
/*  76:    */               }
/*  77:    */             }
/*  78:    */           }
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public RectImpl() {}
/*  85:    */   
/*  86:    */   public CSSPrimitiveValue getTop()
/*  87:    */   {
/*  88:137 */     return this.top;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public CSSPrimitiveValue getRight()
/*  92:    */   {
/*  93:141 */     return this.right;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public CSSPrimitiveValue getBottom()
/*  97:    */   {
/*  98:145 */     return this.bottom;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public CSSPrimitiveValue getLeft()
/* 102:    */   {
/* 103:149 */     return this.left;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String toString()
/* 107:    */   {
/* 108:153 */     return "rect(" + this.left + ", " + this.top + ", " + this.right + ", " + this.bottom + ")";
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.RectImpl
 * JD-Core Version:    0.7.0.1
 */