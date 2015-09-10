/*   1:    */ package antlr.preprocessor;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.IndexedVector;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ 
/*   6:    */ class Rule
/*   7:    */ {
/*   8:    */   protected String name;
/*   9:    */   protected String block;
/*  10:    */   protected String args;
/*  11:    */   protected String returnValue;
/*  12:    */   protected String throwsSpec;
/*  13:    */   protected String initAction;
/*  14:    */   protected IndexedVector options;
/*  15:    */   protected String visibility;
/*  16:    */   protected Grammar enclosingGrammar;
/*  17: 25 */   protected boolean bang = false;
/*  18:    */   
/*  19:    */   public Rule(String paramString1, String paramString2, IndexedVector paramIndexedVector, Grammar paramGrammar)
/*  20:    */   {
/*  21: 28 */     this.name = paramString1;
/*  22: 29 */     this.block = paramString2;
/*  23: 30 */     this.options = paramIndexedVector;
/*  24: 31 */     setEnclosingGrammar(paramGrammar);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getArgs()
/*  28:    */   {
/*  29: 35 */     return this.args;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean getBang()
/*  33:    */   {
/*  34: 39 */     return this.bang;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getName()
/*  38:    */   {
/*  39: 43 */     return this.name;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getReturnValue()
/*  43:    */   {
/*  44: 47 */     return this.returnValue;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getVisibility()
/*  48:    */   {
/*  49: 51 */     return this.visibility;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean narrowerVisibility(Rule paramRule)
/*  53:    */   {
/*  54: 60 */     if (this.visibility.equals("public"))
/*  55:    */     {
/*  56: 61 */       if (!paramRule.equals("public")) {
/*  57: 62 */         return true;
/*  58:    */       }
/*  59: 64 */       return false;
/*  60:    */     }
/*  61: 66 */     if (this.visibility.equals("protected"))
/*  62:    */     {
/*  63: 67 */       if (paramRule.equals("private")) {
/*  64: 68 */         return true;
/*  65:    */       }
/*  66: 70 */       return false;
/*  67:    */     }
/*  68: 72 */     if (this.visibility.equals("private")) {
/*  69: 73 */       return false;
/*  70:    */     }
/*  71: 75 */     return false;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean sameSignature(Rule paramRule)
/*  75:    */   {
/*  76: 87 */     boolean bool1 = true;
/*  77: 88 */     boolean bool2 = true;
/*  78: 89 */     boolean bool3 = true;
/*  79:    */     
/*  80: 91 */     bool1 = this.name.equals(paramRule.getName());
/*  81: 92 */     if (this.args != null) {
/*  82: 93 */       bool2 = this.args.equals(paramRule.getArgs());
/*  83:    */     }
/*  84: 95 */     if (this.returnValue != null) {
/*  85: 96 */       bool3 = this.returnValue.equals(paramRule.getReturnValue());
/*  86:    */     }
/*  87: 98 */     return (bool1) && (bool2) && (bool3);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setArgs(String paramString)
/*  91:    */   {
/*  92:102 */     this.args = paramString;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setBang()
/*  96:    */   {
/*  97:106 */     this.bang = true;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setEnclosingGrammar(Grammar paramGrammar)
/* 101:    */   {
/* 102:110 */     this.enclosingGrammar = paramGrammar;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setInitAction(String paramString)
/* 106:    */   {
/* 107:114 */     this.initAction = paramString;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setOptions(IndexedVector paramIndexedVector)
/* 111:    */   {
/* 112:118 */     this.options = paramIndexedVector;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setReturnValue(String paramString)
/* 116:    */   {
/* 117:122 */     this.returnValue = paramString;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setThrowsSpec(String paramString)
/* 121:    */   {
/* 122:126 */     this.throwsSpec = paramString;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setVisibility(String paramString)
/* 126:    */   {
/* 127:130 */     this.visibility = paramString;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String toString()
/* 131:    */   {
/* 132:134 */     String str1 = "";
/* 133:135 */     String str2 = "returns " + this.returnValue;
/* 134:136 */     String str3 = this.args == null ? "" : this.args;
/* 135:137 */     String str4 = getBang() ? "!" : "";
/* 136:    */     
/* 137:139 */     str1 = str1 + (this.visibility == null ? "" : new StringBuffer().append(this.visibility).append(" ").toString());
/* 138:140 */     str1 = str1 + this.name + str4 + str3 + " " + str2 + this.throwsSpec;
/* 139:141 */     if (this.options != null)
/* 140:    */     {
/* 141:142 */       str1 = str1 + System.getProperty("line.separator") + "options {" + System.getProperty("line.separator");
/* 142:145 */       for (Enumeration localEnumeration = this.options.elements(); localEnumeration.hasMoreElements();) {
/* 143:146 */         str1 = str1 + (Option)localEnumeration.nextElement() + System.getProperty("line.separator");
/* 144:    */       }
/* 145:148 */       str1 = str1 + "}" + System.getProperty("line.separator");
/* 146:    */     }
/* 147:150 */     if (this.initAction != null) {
/* 148:151 */       str1 = str1 + this.initAction + System.getProperty("line.separator");
/* 149:    */     }
/* 150:153 */     str1 = str1 + this.block;
/* 151:154 */     return str1;
/* 152:    */   }
/* 153:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.preprocessor.Rule
 * JD-Core Version:    0.7.0.1
 */