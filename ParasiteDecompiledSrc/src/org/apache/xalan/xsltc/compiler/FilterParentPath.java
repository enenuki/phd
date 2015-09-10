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
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.NodeType;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  23:    */ 
/*  24:    */ final class FilterParentPath
/*  25:    */   extends Expression
/*  26:    */ {
/*  27:    */   private Expression _filterExpr;
/*  28:    */   private Expression _path;
/*  29: 50 */   private boolean _hasDescendantAxis = false;
/*  30:    */   
/*  31:    */   public FilterParentPath(Expression filterExpr, Expression path)
/*  32:    */   {
/*  33: 53 */     (this._path = path).setParent(this);
/*  34: 54 */     (this._filterExpr = filterExpr).setParent(this);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setParser(Parser parser)
/*  38:    */   {
/*  39: 58 */     super.setParser(parser);
/*  40: 59 */     this._filterExpr.setParser(parser);
/*  41: 60 */     this._path.setParser(parser);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String toString()
/*  45:    */   {
/*  46: 64 */     return "FilterParentPath(" + this._filterExpr + ", " + this._path + ')';
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setDescendantAxis()
/*  50:    */   {
/*  51: 68 */     this._hasDescendantAxis = true;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Type typeCheck(SymbolTable stable)
/*  55:    */     throws TypeCheckError
/*  56:    */   {
/*  57: 77 */     Type ftype = this._filterExpr.typeCheck(stable);
/*  58: 78 */     if (!(ftype instanceof NodeSetType)) {
/*  59: 79 */       if ((ftype instanceof ReferenceType)) {
/*  60: 80 */         this._filterExpr = new CastExpr(this._filterExpr, Type.NodeSet);
/*  61: 87 */       } else if ((ftype instanceof NodeType)) {
/*  62: 88 */         this._filterExpr = new CastExpr(this._filterExpr, Type.NodeSet);
/*  63:    */       } else {
/*  64: 91 */         throw new TypeCheckError(this);
/*  65:    */       }
/*  66:    */     }
/*  67: 96 */     Type ptype = this._path.typeCheck(stable);
/*  68: 97 */     if (!(ptype instanceof NodeSetType)) {
/*  69: 98 */       this._path = new CastExpr(this._path, Type.NodeSet);
/*  70:    */     }
/*  71:101 */     return this._type = Type.NodeSet;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  75:    */   {
/*  76:105 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  77:106 */     InstructionList il = methodGen.getInstructionList();
/*  78:    */     
/*  79:108 */     int initSI = cpg.addMethodref("org.apache.xalan.xsltc.dom.StepIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;Lorg/apache/xml/dtm/DTMAxisIterator;)V");
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:125 */     this._filterExpr.translate(classGen, methodGen);
/*  97:126 */     LocalVariableGen filterTemp = methodGen.addLocalVariable("filter_parent_path_tmp1", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101:130 */     filterTemp.setStart(il.append(new ASTORE(filterTemp.getIndex())));
/* 102:    */     
/* 103:132 */     this._path.translate(classGen, methodGen);
/* 104:133 */     LocalVariableGen pathTemp = methodGen.addLocalVariable("filter_parent_path_tmp2", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/* 105:    */     
/* 106:    */ 
/* 107:    */ 
/* 108:137 */     pathTemp.setStart(il.append(new ASTORE(pathTemp.getIndex())));
/* 109:    */     
/* 110:139 */     il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.StepIterator")));
/* 111:140 */     il.append(InstructionConstants.DUP);
/* 112:141 */     filterTemp.setEnd(il.append(new ALOAD(filterTemp.getIndex())));
/* 113:142 */     pathTemp.setEnd(il.append(new ALOAD(pathTemp.getIndex())));
/* 114:    */     
/* 115:    */ 
/* 116:145 */     il.append(new INVOKESPECIAL(initSI));
/* 117:148 */     if (this._hasDescendantAxis)
/* 118:    */     {
/* 119:149 */       int incl = cpg.addMethodref("org.apache.xml.dtm.ref.DTMAxisIteratorBase", "includeSelf", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 120:    */       
/* 121:    */ 
/* 122:152 */       il.append(new INVOKEVIRTUAL(incl));
/* 123:    */     }
/* 124:155 */     SyntaxTreeNode parent = getParent();
/* 125:    */     
/* 126:157 */     boolean parentAlreadyOrdered = ((parent instanceof RelativeLocationPath)) || ((parent instanceof FilterParentPath)) || ((parent instanceof KeyCall)) || ((parent instanceof CurrentCall)) || ((parent instanceof DocumentCall));
/* 127:164 */     if (!parentAlreadyOrdered)
/* 128:    */     {
/* 129:165 */       int order = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "orderNodes", "(Lorg/apache/xml/dtm/DTMAxisIterator;I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 130:    */       
/* 131:    */ 
/* 132:168 */       il.append(methodGen.loadDOM());
/* 133:169 */       il.append(InstructionConstants.SWAP);
/* 134:170 */       il.append(methodGen.loadContextNode());
/* 135:171 */       il.append(new INVOKEINTERFACE(order, 3));
/* 136:    */     }
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.FilterParentPath
 * JD-Core Version:    0.7.0.1
 */