/*  1:   */ package com.steadystate.css.parser.selectors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.css.sac.AttributeCondition;
/*  5:   */ 
/*  6:   */ public class AttributeConditionImpl
/*  7:   */   implements AttributeCondition, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 9035418830958954213L;
/* 10:   */   private String localName;
/* 11:   */   private String value;
/* 12:   */   
/* 13:   */   public void setLocaleName(String localName)
/* 14:   */   {
/* 15:47 */     this.localName = localName;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setValue(String value)
/* 19:   */   {
/* 20:52 */     this.value = value;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public AttributeConditionImpl(String localName, String value)
/* 24:   */   {
/* 25:57 */     this.localName = localName;
/* 26:58 */     this.value = value;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public AttributeConditionImpl() {}
/* 30:   */   
/* 31:   */   public short getConditionType()
/* 32:   */   {
/* 33:67 */     return 4;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getNamespaceURI()
/* 37:   */   {
/* 38:71 */     return null;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String getLocalName()
/* 42:   */   {
/* 43:75 */     return this.localName;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean getSpecified()
/* 47:   */   {
/* 48:79 */     return true;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getValue()
/* 52:   */   {
/* 53:83 */     return this.value;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public String toString()
/* 57:   */   {
/* 58:87 */     if (getValue() != null) {
/* 59:88 */       return "[" + getLocalName() + "=\"" + getValue() + "\"]";
/* 60:   */     }
/* 61:90 */     return "[" + getLocalName() + "]";
/* 62:   */   }
/* 63:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.AttributeConditionImpl
 * JD-Core Version:    0.7.0.1
 */