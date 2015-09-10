/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.GETSTATIC;
/*   6:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   7:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.bcel.generic.NEW;
/*  12:    */ import org.apache.bcel.generic.PUSH;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  18:    */ import org.apache.xalan.xsltc.runtime.AttributeList;
/*  19:    */ import org.apache.xml.utils.XML11Char;
/*  20:    */ 
/*  21:    */ final class DecimalFormatting
/*  22:    */   extends TopLevelElement
/*  23:    */ {
/*  24:    */   private static final String DFS_CLASS = "java.text.DecimalFormatSymbols";
/*  25:    */   private static final String DFS_SIG = "Ljava/text/DecimalFormatSymbols;";
/*  26: 48 */   private QName _name = null;
/*  27:    */   
/*  28:    */   public Type typeCheck(SymbolTable stable)
/*  29:    */     throws TypeCheckError
/*  30:    */   {
/*  31: 54 */     return Type.Void;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void parseContents(Parser parser)
/*  35:    */   {
/*  36: 62 */     String name = getAttribute("name");
/*  37: 63 */     if ((name.length() > 0) && 
/*  38: 64 */       (!XML11Char.isXML11ValidQName(name)))
/*  39:    */     {
/*  40: 65 */       ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", name, this);
/*  41: 66 */       parser.reportError(3, err);
/*  42:    */     }
/*  43: 69 */     this._name = parser.getQNameIgnoreDefaultNs(name);
/*  44: 70 */     if (this._name == null) {
/*  45: 71 */       this._name = parser.getQNameIgnoreDefaultNs("");
/*  46:    */     }
/*  47: 75 */     SymbolTable stable = parser.getSymbolTable();
/*  48: 76 */     if (stable.getDecimalFormatting(this._name) != null) {
/*  49: 77 */       reportWarning(this, parser, "SYMBOLS_REDEF_ERR", this._name.toString());
/*  50:    */     } else {
/*  51: 81 */       stable.addDecimalFormatting(this._name, this);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  56:    */   {
/*  57: 91 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  58: 92 */     InstructionList il = methodGen.getInstructionList();
/*  59:    */     
/*  60:    */ 
/*  61:    */ 
/*  62: 96 */     int init = cpg.addMethodref("java.text.DecimalFormatSymbols", "<init>", "(Ljava/util/Locale;)V");
/*  63:    */     
/*  64:    */ 
/*  65:    */ 
/*  66:100 */     il.append(classGen.loadTranslet());
/*  67:101 */     il.append(new PUSH(cpg, this._name.toString()));
/*  68:    */     
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:107 */     il.append(new NEW(cpg.addClass("java.text.DecimalFormatSymbols")));
/*  74:108 */     il.append(InstructionConstants.DUP);
/*  75:109 */     il.append(new GETSTATIC(cpg.addFieldref("java.util.Locale", "US", "Ljava/util/Locale;")));
/*  76:    */     
/*  77:111 */     il.append(new INVOKESPECIAL(init));
/*  78:    */     
/*  79:113 */     String tmp = getAttribute("NaN");
/*  80:114 */     if ((tmp == null) || (tmp.equals("")))
/*  81:    */     {
/*  82:115 */       int nan = cpg.addMethodref("java.text.DecimalFormatSymbols", "setNaN", "(Ljava/lang/String;)V");
/*  83:    */       
/*  84:117 */       il.append(InstructionConstants.DUP);
/*  85:118 */       il.append(new PUSH(cpg, "NaN"));
/*  86:119 */       il.append(new INVOKEVIRTUAL(nan));
/*  87:    */     }
/*  88:122 */     tmp = getAttribute("infinity");
/*  89:123 */     if ((tmp == null) || (tmp.equals("")))
/*  90:    */     {
/*  91:124 */       int inf = cpg.addMethodref("java.text.DecimalFormatSymbols", "setInfinity", "(Ljava/lang/String;)V");
/*  92:    */       
/*  93:    */ 
/*  94:127 */       il.append(InstructionConstants.DUP);
/*  95:128 */       il.append(new PUSH(cpg, "Infinity"));
/*  96:129 */       il.append(new INVOKEVIRTUAL(inf));
/*  97:    */     }
/*  98:132 */     int nAttributes = this._attributes.getLength();
/*  99:133 */     for (int i = 0; i < nAttributes; i++)
/* 100:    */     {
/* 101:134 */       String name = this._attributes.getQName(i);
/* 102:135 */       String value = this._attributes.getValue(i);
/* 103:    */       
/* 104:137 */       boolean valid = true;
/* 105:138 */       int method = 0;
/* 106:140 */       if (name.equals("decimal-separator"))
/* 107:    */       {
/* 108:142 */         method = cpg.addMethodref("java.text.DecimalFormatSymbols", "setDecimalSeparator", "(C)V");
/* 109:    */       }
/* 110:145 */       else if (name.equals("grouping-separator"))
/* 111:    */       {
/* 112:146 */         method = cpg.addMethodref("java.text.DecimalFormatSymbols", "setGroupingSeparator", "(C)V");
/* 113:    */       }
/* 114:149 */       else if (name.equals("minus-sign"))
/* 115:    */       {
/* 116:150 */         method = cpg.addMethodref("java.text.DecimalFormatSymbols", "setMinusSign", "(C)V");
/* 117:    */       }
/* 118:153 */       else if (name.equals("percent"))
/* 119:    */       {
/* 120:154 */         method = cpg.addMethodref("java.text.DecimalFormatSymbols", "setPercent", "(C)V");
/* 121:    */       }
/* 122:157 */       else if (name.equals("per-mille"))
/* 123:    */       {
/* 124:158 */         method = cpg.addMethodref("java.text.DecimalFormatSymbols", "setPerMill", "(C)V");
/* 125:    */       }
/* 126:161 */       else if (name.equals("zero-digit"))
/* 127:    */       {
/* 128:162 */         method = cpg.addMethodref("java.text.DecimalFormatSymbols", "setZeroDigit", "(C)V");
/* 129:    */       }
/* 130:165 */       else if (name.equals("digit"))
/* 131:    */       {
/* 132:166 */         method = cpg.addMethodref("java.text.DecimalFormatSymbols", "setDigit", "(C)V");
/* 133:    */       }
/* 134:169 */       else if (name.equals("pattern-separator"))
/* 135:    */       {
/* 136:170 */         method = cpg.addMethodref("java.text.DecimalFormatSymbols", "setPatternSeparator", "(C)V");
/* 137:    */       }
/* 138:173 */       else if (name.equals("NaN"))
/* 139:    */       {
/* 140:174 */         method = cpg.addMethodref("java.text.DecimalFormatSymbols", "setNaN", "(Ljava/lang/String;)V");
/* 141:    */         
/* 142:176 */         il.append(InstructionConstants.DUP);
/* 143:177 */         il.append(new PUSH(cpg, value));
/* 144:178 */         il.append(new INVOKEVIRTUAL(method));
/* 145:179 */         valid = false;
/* 146:    */       }
/* 147:181 */       else if (name.equals("infinity"))
/* 148:    */       {
/* 149:182 */         method = cpg.addMethodref("java.text.DecimalFormatSymbols", "setInfinity", "(Ljava/lang/String;)V");
/* 150:    */         
/* 151:    */ 
/* 152:185 */         il.append(InstructionConstants.DUP);
/* 153:186 */         il.append(new PUSH(cpg, value));
/* 154:187 */         il.append(new INVOKEVIRTUAL(method));
/* 155:188 */         valid = false;
/* 156:    */       }
/* 157:    */       else
/* 158:    */       {
/* 159:191 */         valid = false;
/* 160:    */       }
/* 161:194 */       if (valid)
/* 162:    */       {
/* 163:195 */         il.append(InstructionConstants.DUP);
/* 164:196 */         il.append(new PUSH(cpg, value.charAt(0)));
/* 165:197 */         il.append(new INVOKEVIRTUAL(method));
/* 166:    */       }
/* 167:    */     }
/* 168:202 */     int put = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "addDecimalFormat", "(Ljava/lang/String;Ljava/text/DecimalFormatSymbols;)V");
/* 169:    */     
/* 170:    */ 
/* 171:205 */     il.append(new INVOKEVIRTUAL(put));
/* 172:    */   }
/* 173:    */   
/* 174:    */   public static void translateDefaultDFS(ClassGenerator classGen, MethodGenerator methodGen)
/* 175:    */   {
/* 176:217 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 177:218 */     InstructionList il = methodGen.getInstructionList();
/* 178:219 */     int init = cpg.addMethodref("java.text.DecimalFormatSymbols", "<init>", "(Ljava/util/Locale;)V");
/* 179:    */     
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:224 */     il.append(classGen.loadTranslet());
/* 184:225 */     il.append(new PUSH(cpg, ""));
/* 185:    */     
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:232 */     il.append(new NEW(cpg.addClass("java.text.DecimalFormatSymbols")));
/* 192:233 */     il.append(InstructionConstants.DUP);
/* 193:234 */     il.append(new GETSTATIC(cpg.addFieldref("java.util.Locale", "US", "Ljava/util/Locale;")));
/* 194:    */     
/* 195:236 */     il.append(new INVOKESPECIAL(init));
/* 196:    */     
/* 197:238 */     int nan = cpg.addMethodref("java.text.DecimalFormatSymbols", "setNaN", "(Ljava/lang/String;)V");
/* 198:    */     
/* 199:240 */     il.append(InstructionConstants.DUP);
/* 200:241 */     il.append(new PUSH(cpg, "NaN"));
/* 201:242 */     il.append(new INVOKEVIRTUAL(nan));
/* 202:    */     
/* 203:244 */     int inf = cpg.addMethodref("java.text.DecimalFormatSymbols", "setInfinity", "(Ljava/lang/String;)V");
/* 204:    */     
/* 205:    */ 
/* 206:247 */     il.append(InstructionConstants.DUP);
/* 207:248 */     il.append(new PUSH(cpg, "Infinity"));
/* 208:249 */     il.append(new INVOKEVIRTUAL(inf));
/* 209:    */     
/* 210:251 */     int put = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "addDecimalFormat", "(Ljava/lang/String;Ljava/text/DecimalFormatSymbols;)V");
/* 211:    */     
/* 212:    */ 
/* 213:254 */     il.append(new INVOKEVIRTUAL(put));
/* 214:    */   }
/* 215:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.DecimalFormatting
 * JD-Core Version:    0.7.0.1
 */