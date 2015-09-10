/*   1:    */ package org.dom4j.dtd;
/*   2:    */ 
/*   3:    */ public class AttributeDecl
/*   4:    */ {
/*   5:    */   private String elementName;
/*   6:    */   private String attributeName;
/*   7:    */   private String type;
/*   8:    */   private String value;
/*   9:    */   private String valueDefault;
/*  10:    */   
/*  11:    */   public AttributeDecl() {}
/*  12:    */   
/*  13:    */   public AttributeDecl(String elementName, String attributeName, String type, String valueDefault, String value)
/*  14:    */   {
/*  15: 39 */     this.elementName = elementName;
/*  16: 40 */     this.attributeName = attributeName;
/*  17: 41 */     this.type = type;
/*  18: 42 */     this.value = value;
/*  19: 43 */     this.valueDefault = valueDefault;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getElementName()
/*  23:    */   {
/*  24: 52 */     return this.elementName;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setElementName(String elementName)
/*  28:    */   {
/*  29: 62 */     this.elementName = elementName;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getAttributeName()
/*  33:    */   {
/*  34: 71 */     return this.attributeName;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setAttributeName(String attributeName)
/*  38:    */   {
/*  39: 81 */     this.attributeName = attributeName;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getType()
/*  43:    */   {
/*  44: 90 */     return this.type;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setType(String type)
/*  48:    */   {
/*  49:100 */     this.type = type;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getValue()
/*  53:    */   {
/*  54:109 */     return this.value;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setValue(String value)
/*  58:    */   {
/*  59:119 */     this.value = value;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getValueDefault()
/*  63:    */   {
/*  64:128 */     return this.valueDefault;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setValueDefault(String valueDefault)
/*  68:    */   {
/*  69:138 */     this.valueDefault = valueDefault;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String toString()
/*  73:    */   {
/*  74:142 */     StringBuffer buffer = new StringBuffer("<!ATTLIST ");
/*  75:143 */     buffer.append(this.elementName);
/*  76:144 */     buffer.append(" ");
/*  77:145 */     buffer.append(this.attributeName);
/*  78:146 */     buffer.append(" ");
/*  79:147 */     buffer.append(this.type);
/*  80:148 */     buffer.append(" ");
/*  81:150 */     if (this.valueDefault != null)
/*  82:    */     {
/*  83:151 */       buffer.append(this.valueDefault);
/*  84:153 */       if (this.valueDefault.equals("#FIXED"))
/*  85:    */       {
/*  86:154 */         buffer.append(" \"");
/*  87:155 */         buffer.append(this.value);
/*  88:156 */         buffer.append("\"");
/*  89:    */       }
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:159 */       buffer.append("\"");
/*  94:160 */       buffer.append(this.value);
/*  95:161 */       buffer.append("\"");
/*  96:    */     }
/*  97:164 */     buffer.append(">");
/*  98:    */     
/*  99:166 */     return buffer.toString();
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dtd.AttributeDecl
 * JD-Core Version:    0.7.0.1
 */