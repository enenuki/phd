/*   1:    */ package org.dom4j.dtd;
/*   2:    */ 
/*   3:    */ public class InternalEntityDecl
/*   4:    */ {
/*   5:    */   private String name;
/*   6:    */   private String value;
/*   7:    */   
/*   8:    */   public InternalEntityDecl() {}
/*   9:    */   
/*  10:    */   public InternalEntityDecl(String name, String value)
/*  11:    */   {
/*  12: 30 */     this.name = name;
/*  13: 31 */     this.value = value;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public String getName()
/*  17:    */   {
/*  18: 40 */     return this.name;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void setName(String name)
/*  22:    */   {
/*  23: 50 */     this.name = name;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getValue()
/*  27:    */   {
/*  28: 59 */     return this.value;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setValue(String value)
/*  32:    */   {
/*  33: 69 */     this.value = value;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String toString()
/*  37:    */   {
/*  38: 73 */     StringBuffer buffer = new StringBuffer("<!ENTITY ");
/*  39: 75 */     if (this.name.startsWith("%"))
/*  40:    */     {
/*  41: 76 */       buffer.append("% ");
/*  42: 77 */       buffer.append(this.name.substring(1));
/*  43:    */     }
/*  44:    */     else
/*  45:    */     {
/*  46: 79 */       buffer.append(this.name);
/*  47:    */     }
/*  48: 82 */     buffer.append(" \"");
/*  49: 83 */     buffer.append(escapeEntityValue(this.value));
/*  50: 84 */     buffer.append("\">");
/*  51:    */     
/*  52: 86 */     return buffer.toString();
/*  53:    */   }
/*  54:    */   
/*  55:    */   private String escapeEntityValue(String text)
/*  56:    */   {
/*  57: 90 */     StringBuffer result = new StringBuffer();
/*  58: 92 */     for (int i = 0; i < text.length(); i++)
/*  59:    */     {
/*  60: 93 */       char c = text.charAt(i);
/*  61: 95 */       switch (c)
/*  62:    */       {
/*  63:    */       case '<': 
/*  64: 97 */         result.append("&#38;#60;");
/*  65:    */         
/*  66: 99 */         break;
/*  67:    */       case '>': 
/*  68:102 */         result.append("&#62;");
/*  69:    */         
/*  70:104 */         break;
/*  71:    */       case '&': 
/*  72:107 */         result.append("&#38;#38;");
/*  73:    */         
/*  74:109 */         break;
/*  75:    */       case '\'': 
/*  76:112 */         result.append("&#39;");
/*  77:    */         
/*  78:114 */         break;
/*  79:    */       case '"': 
/*  80:117 */         result.append("&#34;");
/*  81:    */         
/*  82:119 */         break;
/*  83:    */       default: 
/*  84:123 */         if (c < ' ') {
/*  85:124 */           result.append("&#" + c + ";");
/*  86:    */         } else {
/*  87:126 */           result.append(c);
/*  88:    */         }
/*  89:    */         break;
/*  90:    */       }
/*  91:    */     }
/*  92:133 */     return result.toString();
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dtd.InternalEntityDecl
 * JD-Core Version:    0.7.0.1
 */