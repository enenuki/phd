/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.GETSTATIC;
/*   6:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   7:    */ import org.apache.bcel.generic.InstructionConstants;
/*   8:    */ import org.apache.bcel.generic.InstructionList;
/*   9:    */ import org.apache.bcel.generic.MethodGen;
/*  10:    */ import org.apache.bcel.generic.PUSH;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  14:    */ 
/*  15:    */ final class Text
/*  16:    */   extends Instruction
/*  17:    */ {
/*  18:    */   private String _text;
/*  19: 41 */   private boolean _escaping = true;
/*  20: 42 */   private boolean _ignore = false;
/*  21: 43 */   private boolean _textElement = false;
/*  22:    */   
/*  23:    */   public Text()
/*  24:    */   {
/*  25: 49 */     this._textElement = true;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Text(String text)
/*  29:    */   {
/*  30: 57 */     this._text = text;
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected String getText()
/*  34:    */   {
/*  35: 65 */     return this._text;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected void setText(String text)
/*  39:    */   {
/*  40: 74 */     if (this._text == null) {
/*  41: 75 */       this._text = text;
/*  42:    */     } else {
/*  43: 77 */       this._text += text;
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void display(int indent)
/*  48:    */   {
/*  49: 81 */     indent(indent);
/*  50: 82 */     Util.println("Text");
/*  51: 83 */     indent(indent + 4);
/*  52: 84 */     Util.println(this._text);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void parseContents(Parser parser)
/*  56:    */   {
/*  57: 88 */     String str = getAttribute("disable-output-escaping");
/*  58: 89 */     if ((str != null) && (str.equals("yes"))) {
/*  59: 89 */       this._escaping = false;
/*  60:    */     }
/*  61: 91 */     parseChildren(parser);
/*  62: 93 */     if (this._text == null)
/*  63:    */     {
/*  64: 94 */       if (this._textElement) {
/*  65: 95 */         this._text = "";
/*  66:    */       } else {
/*  67: 98 */         this._ignore = true;
/*  68:    */       }
/*  69:    */     }
/*  70:101 */     else if (this._textElement)
/*  71:    */     {
/*  72:102 */       if (this._text.length() == 0) {
/*  73:102 */         this._ignore = true;
/*  74:    */       }
/*  75:    */     }
/*  76:104 */     else if ((getParent() instanceof LiteralElement))
/*  77:    */     {
/*  78:105 */       LiteralElement element = (LiteralElement)getParent();
/*  79:106 */       String space = element.getAttribute("xml:space");
/*  80:107 */       if ((space == null) || (!space.equals("preserve")))
/*  81:    */       {
/*  82:110 */         int textLength = this._text.length();
/*  83:111 */         for (int i = 0; i < textLength; i++)
/*  84:    */         {
/*  85:112 */           char c = this._text.charAt(i);
/*  86:113 */           if (!isWhitespace(c)) {
/*  87:    */             break;
/*  88:    */           }
/*  89:    */         }
/*  90:116 */         if (i == textLength) {
/*  91:117 */           this._ignore = true;
/*  92:    */         }
/*  93:    */       }
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:122 */       int textLength = this._text.length();
/*  98:123 */       for (int i = 0; i < textLength; i++)
/*  99:    */       {
/* 100:125 */         char c = this._text.charAt(i);
/* 101:126 */         if (!isWhitespace(c)) {
/* 102:    */           break;
/* 103:    */         }
/* 104:    */       }
/* 105:129 */       if (i == textLength) {
/* 106:130 */         this._ignore = true;
/* 107:    */       }
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void ignore()
/* 112:    */   {
/* 113:135 */     this._ignore = true;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isIgnore()
/* 117:    */   {
/* 118:139 */     return this._ignore;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isTextElement()
/* 122:    */   {
/* 123:143 */     return this._textElement;
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected boolean contextDependent()
/* 127:    */   {
/* 128:147 */     return false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   private static boolean isWhitespace(char c)
/* 132:    */   {
/* 133:152 */     return (c == ' ') || (c == '\t') || (c == '\n') || (c == '\r');
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 137:    */   {
/* 138:156 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 139:157 */     InstructionList il = methodGen.getInstructionList();
/* 140:159 */     if (!this._ignore)
/* 141:    */     {
/* 142:161 */       int esc = cpg.addInterfaceMethodref(Constants.OUTPUT_HANDLER, "setEscaping", "(Z)Z");
/* 143:163 */       if (!this._escaping)
/* 144:    */       {
/* 145:164 */         il.append(methodGen.loadHandler());
/* 146:165 */         il.append(new PUSH(cpg, false));
/* 147:166 */         il.append(new INVOKEINTERFACE(esc, 2));
/* 148:    */       }
/* 149:169 */       il.append(methodGen.loadHandler());
/* 150:173 */       if (!canLoadAsArrayOffsetLength())
/* 151:    */       {
/* 152:174 */         int characters = cpg.addInterfaceMethodref(Constants.OUTPUT_HANDLER, "characters", "(Ljava/lang/String;)V");
/* 153:    */         
/* 154:    */ 
/* 155:177 */         il.append(new PUSH(cpg, this._text));
/* 156:178 */         il.append(new INVOKEINTERFACE(characters, 2));
/* 157:    */       }
/* 158:    */       else
/* 159:    */       {
/* 160:180 */         int characters = cpg.addInterfaceMethodref(Constants.OUTPUT_HANDLER, "characters", "([CII)V");
/* 161:    */         
/* 162:    */ 
/* 163:183 */         loadAsArrayOffsetLength(classGen, methodGen);
/* 164:184 */         il.append(new INVOKEINTERFACE(characters, 4));
/* 165:    */       }
/* 166:189 */       if (!this._escaping)
/* 167:    */       {
/* 168:190 */         il.append(methodGen.loadHandler());
/* 169:191 */         il.append(InstructionConstants.SWAP);
/* 170:192 */         il.append(new INVOKEINTERFACE(esc, 2));
/* 171:193 */         il.append(InstructionConstants.POP);
/* 172:    */       }
/* 173:    */     }
/* 174:196 */     translateContents(classGen, methodGen);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean canLoadAsArrayOffsetLength()
/* 178:    */   {
/* 179:214 */     return this._text.length() <= 21845;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void loadAsArrayOffsetLength(ClassGenerator classGen, MethodGenerator methodGen)
/* 183:    */   {
/* 184:228 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 185:229 */     InstructionList il = methodGen.getInstructionList();
/* 186:230 */     XSLTC xsltc = classGen.getParser().getXSLTC();
/* 187:    */     
/* 188:    */ 
/* 189:    */ 
/* 190:234 */     int offset = xsltc.addCharacterData(this._text);
/* 191:235 */     int length = this._text.length();
/* 192:236 */     String charDataFieldName = "_scharData" + (xsltc.getCharacterDataCount() - 1);
/* 193:    */     
/* 194:    */ 
/* 195:239 */     il.append(new GETSTATIC(cpg.addFieldref(xsltc.getClassName(), charDataFieldName, "[C")));
/* 196:    */     
/* 197:    */ 
/* 198:242 */     il.append(new PUSH(cpg, offset));
/* 199:243 */     il.append(new PUSH(cpg, this._text.length()));
/* 200:    */   }
/* 201:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Text
 * JD-Core Version:    0.7.0.1
 */