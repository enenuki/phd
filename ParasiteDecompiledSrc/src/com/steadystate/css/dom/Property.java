/*  1:   */ package com.steadystate.css.dom;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.dom.css.CSSValue;
/*  5:   */ 
/*  6:   */ public class Property
/*  7:   */   extends CSSOMObjectImpl
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 8720637891949104989L;
/* 11:   */   private String name;
/* 12:   */   private CSSValue value;
/* 13:   */   private boolean important;
/* 14:   */   
/* 15:   */   public void setName(String name)
/* 16:   */   {
/* 17:48 */     this.name = name;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Property(String name, CSSValue value, boolean important)
/* 21:   */   {
/* 22:53 */     this.name = name;
/* 23:54 */     this.value = value;
/* 24:55 */     this.important = important;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Property() {}
/* 28:   */   
/* 29:   */   public String getName()
/* 30:   */   {
/* 31:64 */     return this.name;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public CSSValue getValue()
/* 35:   */   {
/* 36:68 */     return this.value;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean isImportant()
/* 40:   */   {
/* 41:72 */     return this.important;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setValue(CSSValue value)
/* 45:   */   {
/* 46:76 */     this.value = value;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setImportant(boolean important)
/* 50:   */   {
/* 51:80 */     this.important = important;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String toString()
/* 55:   */   {
/* 56:84 */     return this.name + ": " + this.value.toString() + (this.important ? " !important" : "");
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.Property
 * JD-Core Version:    0.7.0.1
 */