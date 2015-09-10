/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ALOAD;
/*   5:    */ import org.apache.bcel.generic.ASTORE;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.ILOAD;
/*   9:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  10:    */ import org.apache.bcel.generic.ISTORE;
/*  11:    */ import org.apache.bcel.generic.InstructionConstants;
/*  12:    */ import org.apache.bcel.generic.InstructionList;
/*  13:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  14:    */ import org.apache.bcel.generic.MethodGen;
/*  15:    */ import org.apache.bcel.generic.NEW;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  23:    */ 
/*  24:    */ class FilterExpr
/*  25:    */   extends Expression
/*  26:    */ {
/*  27:    */   private Expression _primary;
/*  28:    */   private final Vector _predicates;
/*  29:    */   
/*  30:    */   public FilterExpr(Expression primary, Vector predicates)
/*  31:    */   {
/*  32: 63 */     this._primary = primary;
/*  33: 64 */     this._predicates = predicates;
/*  34: 65 */     primary.setParent(this);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected Expression getExpr()
/*  38:    */   {
/*  39: 69 */     if ((this._primary instanceof CastExpr)) {
/*  40: 70 */       return ((CastExpr)this._primary).getExpr();
/*  41:    */     }
/*  42: 72 */     return this._primary;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setParser(Parser parser)
/*  46:    */   {
/*  47: 76 */     super.setParser(parser);
/*  48: 77 */     this._primary.setParser(parser);
/*  49: 78 */     if (this._predicates != null)
/*  50:    */     {
/*  51: 79 */       int n = this._predicates.size();
/*  52: 80 */       for (int i = 0; i < n; i++)
/*  53:    */       {
/*  54: 81 */         Expression exp = (Expression)this._predicates.elementAt(i);
/*  55: 82 */         exp.setParser(parser);
/*  56: 83 */         exp.setParent(this);
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String toString()
/*  62:    */   {
/*  63: 89 */     return "filter-expr(" + this._primary + ", " + this._predicates + ")";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Type typeCheck(SymbolTable stable)
/*  67:    */     throws TypeCheckError
/*  68:    */   {
/*  69:100 */     Type ptype = this._primary.typeCheck(stable);
/*  70:101 */     boolean canOptimize = this._primary instanceof KeyCall;
/*  71:103 */     if (!(ptype instanceof NodeSetType)) {
/*  72:104 */       if ((ptype instanceof ReferenceType)) {
/*  73:105 */         this._primary = new CastExpr(this._primary, Type.NodeSet);
/*  74:    */       } else {
/*  75:108 */         throw new TypeCheckError(this);
/*  76:    */       }
/*  77:    */     }
/*  78:113 */     int n = this._predicates.size();
/*  79:114 */     for (int i = 0; i < n; i++)
/*  80:    */     {
/*  81:115 */       Predicate pred = (Predicate)this._predicates.elementAt(i);
/*  82:117 */       if (!canOptimize) {
/*  83:118 */         pred.dontOptimize();
/*  84:    */       }
/*  85:120 */       pred.typeCheck(stable);
/*  86:    */     }
/*  87:122 */     return this._type = Type.NodeSet;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  91:    */   {
/*  92:130 */     if (this._predicates.size() > 0) {
/*  93:131 */       translatePredicates(classGen, methodGen);
/*  94:    */     } else {
/*  95:134 */       this._primary.translate(classGen, methodGen);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void translatePredicates(ClassGenerator classGen, MethodGenerator methodGen)
/* 100:    */   {
/* 101:146 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 102:147 */     InstructionList il = methodGen.getInstructionList();
/* 103:150 */     if (this._predicates.size() == 0)
/* 104:    */     {
/* 105:151 */       translate(classGen, methodGen);
/* 106:    */     }
/* 107:    */     else
/* 108:    */     {
/* 109:154 */       Predicate predicate = (Predicate)this._predicates.lastElement();
/* 110:155 */       this._predicates.remove(predicate);
/* 111:    */       
/* 112:    */ 
/* 113:158 */       translatePredicates(classGen, methodGen);
/* 114:160 */       if (predicate.isNthPositionFilter())
/* 115:    */       {
/* 116:161 */         int nthIteratorIdx = cpg.addMethodref("org.apache.xalan.xsltc.dom.NthIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;I)V");
/* 117:    */         
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:176 */         LocalVariableGen iteratorTemp = methodGen.addLocalVariable("filter_expr_tmp1", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 132:    */         
/* 133:    */ 
/* 134:    */ 
/* 135:180 */         iteratorTemp.setStart(il.append(new ASTORE(iteratorTemp.getIndex())));
/* 136:    */         
/* 137:    */ 
/* 138:183 */         predicate.translate(classGen, methodGen);
/* 139:184 */         LocalVariableGen predicateValueTemp = methodGen.addLocalVariable("filter_expr_tmp2", Util.getJCRefType("I"), null, null);
/* 140:    */         
/* 141:    */ 
/* 142:    */ 
/* 143:188 */         predicateValueTemp.setStart(il.append(new ISTORE(predicateValueTemp.getIndex())));
/* 144:    */         
/* 145:    */ 
/* 146:191 */         il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.NthIterator")));
/* 147:192 */         il.append(InstructionConstants.DUP);
/* 148:193 */         iteratorTemp.setEnd(il.append(new ALOAD(iteratorTemp.getIndex())));
/* 149:    */         
/* 150:195 */         predicateValueTemp.setEnd(il.append(new ILOAD(predicateValueTemp.getIndex())));
/* 151:    */         
/* 152:197 */         il.append(new INVOKESPECIAL(nthIteratorIdx));
/* 153:    */       }
/* 154:    */       else
/* 155:    */       {
/* 156:200 */         int initCNLI = cpg.addMethodref("org.apache.xalan.xsltc.dom.CurrentNodeListIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;ZLorg/apache/xalan/xsltc/dom/CurrentNodeListFilter;ILorg/apache/xalan/xsltc/runtime/AbstractTranslet;)V");
/* 157:    */         
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:218 */         LocalVariableGen nodeIteratorTemp = methodGen.addLocalVariable("filter_expr_tmp1", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 175:    */         
/* 176:    */ 
/* 177:    */ 
/* 178:222 */         nodeIteratorTemp.setStart(il.append(new ASTORE(nodeIteratorTemp.getIndex())));
/* 179:    */         
/* 180:    */ 
/* 181:225 */         predicate.translate(classGen, methodGen);
/* 182:226 */         LocalVariableGen filterTemp = methodGen.addLocalVariable("filter_expr_tmp2", Util.getJCRefType("Lorg/apache/xalan/xsltc/dom/CurrentNodeListFilter;"), null, null);
/* 183:    */         
/* 184:    */ 
/* 185:    */ 
/* 186:230 */         filterTemp.setStart(il.append(new ASTORE(filterTemp.getIndex())));
/* 187:    */         
/* 188:    */ 
/* 189:    */ 
/* 190:234 */         il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.CurrentNodeListIterator")));
/* 191:235 */         il.append(InstructionConstants.DUP);
/* 192:    */         
/* 193:    */ 
/* 194:238 */         nodeIteratorTemp.setEnd(il.append(new ALOAD(nodeIteratorTemp.getIndex())));
/* 195:    */         
/* 196:240 */         il.append(InstructionConstants.ICONST_1);
/* 197:241 */         filterTemp.setEnd(il.append(new ALOAD(filterTemp.getIndex())));
/* 198:242 */         il.append(methodGen.loadCurrentNode());
/* 199:243 */         il.append(classGen.loadTranslet());
/* 200:244 */         il.append(new INVOKESPECIAL(initCNLI));
/* 201:    */       }
/* 202:    */     }
/* 203:    */   }
/* 204:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.FilterExpr
 * JD-Core Version:    0.7.0.1
 */