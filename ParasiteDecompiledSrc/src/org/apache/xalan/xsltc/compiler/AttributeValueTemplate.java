/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ import java.util.StringTokenizer;
/*   6:    */ import java.util.Vector;
/*   7:    */ import org.apache.bcel.generic.ClassGen;
/*   8:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   9:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  10:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  11:    */ import org.apache.bcel.generic.Instruction;
/*  12:    */ import org.apache.bcel.generic.InstructionConstants;
/*  13:    */ import org.apache.bcel.generic.InstructionList;
/*  14:    */ import org.apache.bcel.generic.MethodGen;
/*  15:    */ import org.apache.bcel.generic.NEW;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  20:    */ 
/*  21:    */ final class AttributeValueTemplate
/*  22:    */   extends AttributeValue
/*  23:    */ {
/*  24:    */   static final int OUT_EXPR = 0;
/*  25:    */   static final int IN_EXPR = 1;
/*  26:    */   static final int IN_EXPR_SQUOTES = 2;
/*  27:    */   static final int IN_EXPR_DQUOTES = 3;
/*  28:    */   static final String DELIMITER = "￾";
/*  29:    */   
/*  30:    */   public AttributeValueTemplate(String value, Parser parser, SyntaxTreeNode parent)
/*  31:    */   {
/*  32: 56 */     setParent(parent);
/*  33: 57 */     setParser(parser);
/*  34:    */     try
/*  35:    */     {
/*  36: 60 */       parseAVTemplate(value, parser);
/*  37:    */     }
/*  38:    */     catch (NoSuchElementException e)
/*  39:    */     {
/*  40: 63 */       reportError(parent, parser, "ATTR_VAL_TEMPLATE_ERR", value);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void parseAVTemplate(String text, Parser parser)
/*  45:    */   {
/*  46: 75 */     StringTokenizer tokenizer = new StringTokenizer(text, "{}\"'", true);
/*  47:    */     
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54: 83 */     String t = null;
/*  55: 84 */     String lookahead = null;
/*  56: 85 */     StringBuffer buffer = new StringBuffer();
/*  57: 86 */     int state = 0;
/*  58: 88 */     while (tokenizer.hasMoreTokens())
/*  59:    */     {
/*  60: 90 */       if (lookahead != null)
/*  61:    */       {
/*  62: 91 */         t = lookahead;
/*  63: 92 */         lookahead = null;
/*  64:    */       }
/*  65:    */       else
/*  66:    */       {
/*  67: 95 */         t = tokenizer.nextToken();
/*  68:    */       }
/*  69: 98 */       if (t.length() == 1) {
/*  70: 99 */         switch (t.charAt(0))
/*  71:    */         {
/*  72:    */         case '{': 
/*  73:101 */           switch (state)
/*  74:    */           {
/*  75:    */           case 0: 
/*  76:103 */             lookahead = tokenizer.nextToken();
/*  77:104 */             if (lookahead.equals("{"))
/*  78:    */             {
/*  79:105 */               buffer.append(lookahead);
/*  80:106 */               lookahead = null;
/*  81:    */             }
/*  82:    */             else
/*  83:    */             {
/*  84:109 */               buffer.append("￾");
/*  85:110 */               state = 1;
/*  86:    */             }
/*  87:112 */             break;
/*  88:    */           case 1: 
/*  89:    */           case 2: 
/*  90:    */           case 3: 
/*  91:116 */             reportError(getParent(), parser, "ATTR_VAL_TEMPLATE_ERR", text);
/*  92:    */           }
/*  93:120 */           break;
/*  94:    */         case '}': 
/*  95:122 */           switch (state)
/*  96:    */           {
/*  97:    */           case 0: 
/*  98:124 */             lookahead = tokenizer.nextToken();
/*  99:125 */             if (lookahead.equals("}"))
/* 100:    */             {
/* 101:126 */               buffer.append(lookahead);
/* 102:127 */               lookahead = null;
/* 103:    */             }
/* 104:    */             else
/* 105:    */             {
/* 106:130 */               reportError(getParent(), parser, "ATTR_VAL_TEMPLATE_ERR", text);
/* 107:    */             }
/* 108:133 */             break;
/* 109:    */           case 1: 
/* 110:135 */             buffer.append("￾");
/* 111:136 */             state = 0;
/* 112:137 */             break;
/* 113:    */           case 2: 
/* 114:    */           case 3: 
/* 115:140 */             buffer.append(t);
/* 116:    */           }
/* 117:143 */           break;
/* 118:    */         case '\'': 
/* 119:145 */           switch (state)
/* 120:    */           {
/* 121:    */           case 1: 
/* 122:147 */             state = 2;
/* 123:148 */             break;
/* 124:    */           case 2: 
/* 125:150 */             state = 1;
/* 126:151 */             break;
/* 127:    */           }
/* 128:156 */           buffer.append(t);
/* 129:157 */           break;
/* 130:    */         case '"': 
/* 131:159 */           switch (state)
/* 132:    */           {
/* 133:    */           case 1: 
/* 134:161 */             state = 3;
/* 135:162 */             break;
/* 136:    */           case 3: 
/* 137:164 */             state = 1;
/* 138:165 */             break;
/* 139:    */           }
/* 140:170 */           buffer.append(t);
/* 141:171 */           break;
/* 142:    */         default: 
/* 143:173 */           buffer.append(t);
/* 144:174 */           break;
/* 145:    */         }
/* 146:    */       } else {
/* 147:178 */         buffer.append(t);
/* 148:    */       }
/* 149:    */     }
/* 150:183 */     if (state != 0) {
/* 151:184 */       reportError(getParent(), parser, "ATTR_VAL_TEMPLATE_ERR", text);
/* 152:    */     }
/* 153:191 */     tokenizer = new StringTokenizer(buffer.toString(), "￾", true);
/* 154:193 */     while (tokenizer.hasMoreTokens())
/* 155:    */     {
/* 156:194 */       t = tokenizer.nextToken();
/* 157:196 */       if (t.equals("￾"))
/* 158:    */       {
/* 159:197 */         addElement(parser.parseExpression(this, tokenizer.nextToken()));
/* 160:198 */         tokenizer.nextToken();
/* 161:    */       }
/* 162:    */       else
/* 163:    */       {
/* 164:201 */         addElement(new LiteralExpr(t));
/* 165:    */       }
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Type typeCheck(SymbolTable stable)
/* 170:    */     throws TypeCheckError
/* 171:    */   {
/* 172:207 */     Vector contents = getContents();
/* 173:208 */     int n = contents.size();
/* 174:209 */     for (int i = 0; i < n; i++)
/* 175:    */     {
/* 176:210 */       Expression exp = (Expression)contents.elementAt(i);
/* 177:211 */       if (!exp.typeCheck(stable).identicalTo(Type.String)) {
/* 178:212 */         contents.setElementAt(new CastExpr(exp, Type.String), i);
/* 179:    */       }
/* 180:    */     }
/* 181:215 */     return this._type = Type.String;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String toString()
/* 185:    */   {
/* 186:219 */     StringBuffer buffer = new StringBuffer("AVT:[");
/* 187:220 */     int count = elementCount();
/* 188:221 */     for (int i = 0; i < count; i++)
/* 189:    */     {
/* 190:222 */       buffer.append(elementAt(i).toString());
/* 191:223 */       if (i < count - 1) {
/* 192:224 */         buffer.append(' ');
/* 193:    */       }
/* 194:    */     }
/* 195:226 */     return ']';
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 199:    */   {
/* 200:230 */     if (elementCount() == 1)
/* 201:    */     {
/* 202:231 */       Expression exp = (Expression)elementAt(0);
/* 203:232 */       exp.translate(classGen, methodGen);
/* 204:    */     }
/* 205:    */     else
/* 206:    */     {
/* 207:235 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 208:236 */       InstructionList il = methodGen.getInstructionList();
/* 209:237 */       int initBuffer = cpg.addMethodref("java.lang.StringBuffer", "<init>", "()V");
/* 210:    */       
/* 211:239 */       Instruction append = new INVOKEVIRTUAL(cpg.addMethodref("java.lang.StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;"));
/* 212:    */       
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:245 */       int toString = cpg.addMethodref("java.lang.StringBuffer", "toString", "()Ljava/lang/String;");
/* 218:    */       
/* 219:    */ 
/* 220:248 */       il.append(new NEW(cpg.addClass("java.lang.StringBuffer")));
/* 221:249 */       il.append(InstructionConstants.DUP);
/* 222:250 */       il.append(new INVOKESPECIAL(initBuffer));
/* 223:    */       
/* 224:252 */       Enumeration elements = elements();
/* 225:253 */       while (elements.hasMoreElements())
/* 226:    */       {
/* 227:254 */         Expression exp = (Expression)elements.nextElement();
/* 228:255 */         exp.translate(classGen, methodGen);
/* 229:256 */         il.append(append);
/* 230:    */       }
/* 231:258 */       il.append(new INVOKEVIRTUAL(toString));
/* 232:    */     }
/* 233:    */   }
/* 234:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.AttributeValueTemplate
 * JD-Core Version:    0.7.0.1
 */