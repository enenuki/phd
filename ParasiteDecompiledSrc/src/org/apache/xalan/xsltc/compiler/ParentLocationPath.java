/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ASTORE;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   8:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   9:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  10:    */ import org.apache.bcel.generic.InstructionConstants;
/*  11:    */ import org.apache.bcel.generic.InstructionList;
/*  12:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  13:    */ import org.apache.bcel.generic.MethodGen;
/*  14:    */ import org.apache.bcel.generic.NEW;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  20:    */ 
/*  21:    */ final class ParentLocationPath
/*  22:    */   extends RelativeLocationPath
/*  23:    */ {
/*  24:    */   private Expression _step;
/*  25:    */   private final RelativeLocationPath _path;
/*  26:    */   private Type stype;
/*  27: 49 */   private boolean _orderNodes = false;
/*  28: 50 */   private boolean _axisMismatch = false;
/*  29:    */   
/*  30:    */   public ParentLocationPath(RelativeLocationPath path, Expression step)
/*  31:    */   {
/*  32: 53 */     this._path = path;
/*  33: 54 */     this._step = step;
/*  34: 55 */     this._path.setParent(this);
/*  35: 56 */     this._step.setParent(this);
/*  36: 58 */     if ((this._step instanceof Step)) {
/*  37: 59 */       this._axisMismatch = checkAxisMismatch();
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setAxis(int axis)
/*  42:    */   {
/*  43: 64 */     this._path.setAxis(axis);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getAxis()
/*  47:    */   {
/*  48: 68 */     return this._path.getAxis();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public RelativeLocationPath getPath()
/*  52:    */   {
/*  53: 72 */     return this._path;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Expression getStep()
/*  57:    */   {
/*  58: 76 */     return this._step;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setParser(Parser parser)
/*  62:    */   {
/*  63: 80 */     super.setParser(parser);
/*  64: 81 */     this._step.setParser(parser);
/*  65: 82 */     this._path.setParser(parser);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String toString()
/*  69:    */   {
/*  70: 86 */     return "ParentLocationPath(" + this._path + ", " + this._step + ')';
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Type typeCheck(SymbolTable stable)
/*  74:    */     throws TypeCheckError
/*  75:    */   {
/*  76: 90 */     this.stype = this._step.typeCheck(stable);
/*  77: 91 */     this._path.typeCheck(stable);
/*  78: 93 */     if (this._axisMismatch) {
/*  79: 93 */       enableNodeOrdering();
/*  80:    */     }
/*  81: 95 */     return this._type = Type.NodeSet;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void enableNodeOrdering()
/*  85:    */   {
/*  86: 99 */     SyntaxTreeNode parent = getParent();
/*  87:100 */     if ((parent instanceof ParentLocationPath)) {
/*  88:101 */       ((ParentLocationPath)parent).enableNodeOrdering();
/*  89:    */     } else {
/*  90:103 */       this._orderNodes = true;
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean checkAxisMismatch()
/*  95:    */   {
/*  96:114 */     int left = this._path.getAxis();
/*  97:115 */     int right = ((Step)this._step).getAxis();
/*  98:117 */     if (((left == 0) || (left == 1)) && ((right == 3) || (right == 4) || (right == 5) || (right == 10) || (right == 11) || (right == 12))) {
/*  99:124 */       return true;
/* 100:    */     }
/* 101:126 */     if (((left == 3) && (right == 0)) || (right == 1) || (right == 10) || (right == 11)) {
/* 102:131 */       return true;
/* 103:    */     }
/* 104:133 */     if ((left == 4) || (left == 5)) {
/* 105:134 */       return true;
/* 106:    */     }
/* 107:136 */     if (((left == 6) || (left == 7)) && ((right == 6) || (right == 10) || (right == 11) || (right == 12))) {
/* 108:141 */       return true;
/* 109:    */     }
/* 110:143 */     if (((left == 11) || (left == 12)) && ((right == 4) || (right == 5) || (right == 6) || (right == 7) || (right == 10) || (right == 11) || (right == 12))) {
/* 111:151 */       return true;
/* 112:    */     }
/* 113:153 */     if ((right == 6) && (left == 3)) {
/* 114:158 */       if ((this._path instanceof Step))
/* 115:    */       {
/* 116:159 */         int type = ((Step)this._path).getNodeType();
/* 117:160 */         if (type == 2) {
/* 118:160 */           return true;
/* 119:    */         }
/* 120:    */       }
/* 121:    */     }
/* 122:164 */     return false;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 126:    */   {
/* 127:168 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 128:169 */     InstructionList il = methodGen.getInstructionList();
/* 129:    */     
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:181 */     this._path.translate(classGen, methodGen);
/* 141:182 */     LocalVariableGen pathTemp = methodGen.addLocalVariable("parent_location_path_tmp1", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 142:    */     
/* 143:    */ 
/* 144:    */ 
/* 145:186 */     pathTemp.setStart(il.append(new ASTORE(pathTemp.getIndex())));
/* 146:    */     
/* 147:188 */     this._step.translate(classGen, methodGen);
/* 148:189 */     LocalVariableGen stepTemp = methodGen.addLocalVariable("parent_location_path_tmp2", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 149:    */     
/* 150:    */ 
/* 151:    */ 
/* 152:193 */     stepTemp.setStart(il.append(new ASTORE(stepTemp.getIndex())));
/* 153:    */     
/* 154:    */ 
/* 155:196 */     int initSI = cpg.addMethodref("org.apache.xalan.xsltc.dom.StepIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;Lorg/apache/xml/dtm/DTMAxisIterator;)V");
/* 156:    */     
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:202 */     il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.StepIterator")));
/* 162:203 */     il.append(InstructionConstants.DUP);
/* 163:    */     
/* 164:205 */     pathTemp.setEnd(il.append(new ALOAD(pathTemp.getIndex())));
/* 165:206 */     stepTemp.setEnd(il.append(new ALOAD(stepTemp.getIndex())));
/* 166:    */     
/* 167:    */ 
/* 168:209 */     il.append(new INVOKESPECIAL(initSI));
/* 169:    */     
/* 170:    */ 
/* 171:212 */     Expression stp = this._step;
/* 172:213 */     if ((stp instanceof ParentLocationPath)) {
/* 173:214 */       stp = ((ParentLocationPath)stp).getStep();
/* 174:    */     }
/* 175:216 */     if (((this._path instanceof Step)) && ((stp instanceof Step)))
/* 176:    */     {
/* 177:217 */       int path = ((Step)this._path).getAxis();
/* 178:218 */       int step = ((Step)stp).getAxis();
/* 179:219 */       if (((path == 5) && (step == 3)) || ((path == 11) && (step == 10)))
/* 180:    */       {
/* 181:221 */         int incl = cpg.addMethodref("org.apache.xml.dtm.ref.DTMAxisIteratorBase", "includeSelf", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 182:    */         
/* 183:    */ 
/* 184:224 */         il.append(new INVOKEVIRTUAL(incl));
/* 185:    */       }
/* 186:    */     }
/* 187:234 */     if (this._orderNodes)
/* 188:    */     {
/* 189:235 */       int order = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "orderNodes", "(Lorg/apache/xml/dtm/DTMAxisIterator;I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 190:    */       
/* 191:    */ 
/* 192:238 */       il.append(methodGen.loadDOM());
/* 193:239 */       il.append(InstructionConstants.SWAP);
/* 194:240 */       il.append(methodGen.loadContextNode());
/* 195:241 */       il.append(new INVOKEINTERFACE(order, 3));
/* 196:    */     }
/* 197:    */   }
/* 198:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ParentLocationPath
 * JD-Core Version:    0.7.0.1
 */