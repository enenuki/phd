/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.w3c.css.sac.LexicalUnit;
/*   5:    */ import org.w3c.dom.DOMException;
/*   6:    */ import org.w3c.dom.css.Counter;
/*   7:    */ 
/*   8:    */ public class CounterImpl
/*   9:    */   implements Counter, Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 7996279151817598904L;
/*  12:    */   private String identifier;
/*  13:    */   private String listStyle;
/*  14:    */   private String separator;
/*  15:    */   
/*  16:    */   public void setIdentifier(String identifier)
/*  17:    */   {
/*  18: 53 */     this.identifier = identifier;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void setListStyle(String listStyle)
/*  22:    */   {
/*  23: 58 */     this.listStyle = listStyle;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setSeparator(String separator)
/*  27:    */   {
/*  28: 63 */     this.separator = separator;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public CounterImpl(boolean separatorSpecified, LexicalUnit lu)
/*  32:    */     throws DOMException
/*  33:    */   {
/*  34: 71 */     LexicalUnit next = lu;
/*  35: 72 */     this.identifier = next.getStringValue();
/*  36: 73 */     next = next.getNextLexicalUnit();
/*  37: 74 */     if (next != null)
/*  38:    */     {
/*  39: 76 */       if (next.getLexicalUnitType() != 0) {
/*  40: 79 */         throw new DOMException((short)12, "Counter parameters must be separated by ','.");
/*  41:    */       }
/*  42: 82 */       next = next.getNextLexicalUnit();
/*  43: 83 */       if ((separatorSpecified) && (next != null))
/*  44:    */       {
/*  45: 84 */         this.separator = next.getStringValue();
/*  46: 85 */         next = next.getNextLexicalUnit();
/*  47: 86 */         if (next != null)
/*  48:    */         {
/*  49: 88 */           if (next.getLexicalUnitType() != 0) {
/*  50: 91 */             throw new DOMException((short)12, "Counter parameters must be separated by ','.");
/*  51:    */           }
/*  52: 94 */           next = next.getNextLexicalUnit();
/*  53:    */         }
/*  54:    */       }
/*  55: 97 */       if (next != null)
/*  56:    */       {
/*  57: 98 */         this.listStyle = next.getStringValue();
/*  58: 99 */         if ((next = next.getNextLexicalUnit()) != null) {
/*  59:102 */           throw new DOMException((short)12, "Too many parameters for counter function.");
/*  60:    */         }
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public CounterImpl() {}
/*  66:    */   
/*  67:    */   public String getIdentifier()
/*  68:    */   {
/*  69:115 */     return this.identifier;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getListStyle()
/*  73:    */   {
/*  74:119 */     return this.listStyle;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getSeparator()
/*  78:    */   {
/*  79:123 */     return this.separator;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String toString()
/*  83:    */   {
/*  84:127 */     StringBuilder sb = new StringBuilder();
/*  85:128 */     if (this.separator == null) {
/*  86:130 */       sb.append("counter(");
/*  87:    */     } else {
/*  88:133 */       sb.append("counters(");
/*  89:    */     }
/*  90:135 */     sb.append(this.identifier);
/*  91:136 */     if (this.separator != null) {
/*  92:137 */       sb.append(", \"").append(this.separator).append("\"");
/*  93:    */     }
/*  94:139 */     if (this.listStyle != null) {
/*  95:140 */       sb.append(", ").append(this.listStyle);
/*  96:    */     }
/*  97:142 */     sb.append(")");
/*  98:143 */     return sb.toString();
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CounterImpl
 * JD-Core Version:    0.7.0.1
 */