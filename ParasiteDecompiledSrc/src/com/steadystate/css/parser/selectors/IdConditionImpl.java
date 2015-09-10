/*  1:   */ package com.steadystate.css.parser.selectors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.css.sac.AttributeCondition;
/*  5:   */ 
/*  6:   */ public class IdConditionImpl
/*  7:   */   implements AttributeCondition, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 5955662524656167683L;
/* 10:   */   private String value;
/* 11:   */   
/* 12:   */   public void setValue(String value)
/* 13:   */   {
/* 14:46 */     this.value = value;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public IdConditionImpl(String value)
/* 18:   */   {
/* 19:51 */     this.value = value;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public IdConditionImpl() {}
/* 23:   */   
/* 24:   */   public short getConditionType()
/* 25:   */   {
/* 26:60 */     return 5;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getNamespaceURI()
/* 30:   */   {
/* 31:64 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getLocalName()
/* 35:   */   {
/* 36:68 */     return null;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean getSpecified()
/* 40:   */   {
/* 41:72 */     return true;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getValue()
/* 45:   */   {
/* 46:76 */     return this.value;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toString()
/* 50:   */   {
/* 51:80 */     return "#" + getValue();
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.IdConditionImpl
 * JD-Core Version:    0.7.0.1
 */