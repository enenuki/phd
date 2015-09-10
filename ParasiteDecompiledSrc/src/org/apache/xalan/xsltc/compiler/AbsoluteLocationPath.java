/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ASTORE;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   8:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   9:    */ import org.apache.bcel.generic.InstructionConstants;
/*  10:    */ import org.apache.bcel.generic.InstructionList;
/*  11:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  12:    */ import org.apache.bcel.generic.MethodGen;
/*  13:    */ import org.apache.bcel.generic.NEW;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.NodeType;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  20:    */ 
/*  21:    */ final class AbsoluteLocationPath
/*  22:    */   extends Expression
/*  23:    */ {
/*  24:    */   private Expression _path;
/*  25:    */   
/*  26:    */   public AbsoluteLocationPath()
/*  27:    */   {
/*  28: 47 */     this._path = null;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public AbsoluteLocationPath(Expression path)
/*  32:    */   {
/*  33: 51 */     this._path = path;
/*  34: 52 */     if (path != null) {
/*  35: 53 */       this._path.setParent(this);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setParser(Parser parser)
/*  40:    */   {
/*  41: 58 */     super.setParser(parser);
/*  42: 59 */     if (this._path != null) {
/*  43: 60 */       this._path.setParser(parser);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Expression getPath()
/*  48:    */   {
/*  49: 65 */     return this._path;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String toString()
/*  53:    */   {
/*  54: 69 */     return "AbsoluteLocationPath(" + (this._path != null ? this._path.toString() : "null") + ')';
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Type typeCheck(SymbolTable stable)
/*  58:    */     throws TypeCheckError
/*  59:    */   {
/*  60: 74 */     if (this._path != null)
/*  61:    */     {
/*  62: 75 */       Type ptype = this._path.typeCheck(stable);
/*  63: 76 */       if ((ptype instanceof NodeType)) {
/*  64: 77 */         this._path = new CastExpr(this._path, Type.NodeSet);
/*  65:    */       }
/*  66:    */     }
/*  67: 80 */     return this._type = Type.NodeSet;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  71:    */   {
/*  72: 84 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  73: 85 */     InstructionList il = methodGen.getInstructionList();
/*  74: 86 */     if (this._path != null)
/*  75:    */     {
/*  76: 87 */       int initAI = cpg.addMethodref("org.apache.xalan.xsltc.dom.AbsoluteIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;)V");
/*  77:    */       
/*  78:    */ 
/*  79:    */ 
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
/*  92:103 */       this._path.translate(classGen, methodGen);
/*  93:104 */       LocalVariableGen relPathIterator = methodGen.addLocalVariable("abs_location_path_tmp", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/*  94:    */       
/*  95:    */ 
/*  96:    */ 
/*  97:108 */       relPathIterator.setStart(il.append(new ASTORE(relPathIterator.getIndex())));
/*  98:    */       
/*  99:    */ 
/* 100:    */ 
/* 101:112 */       il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.AbsoluteIterator")));
/* 102:113 */       il.append(InstructionConstants.DUP);
/* 103:114 */       relPathIterator.setEnd(il.append(new ALOAD(relPathIterator.getIndex())));
/* 104:    */       
/* 105:    */ 
/* 106:    */ 
/* 107:118 */       il.append(new INVOKESPECIAL(initAI));
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:121 */       int gitr = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 112:    */       
/* 113:    */ 
/* 114:124 */       il.append(methodGen.loadDOM());
/* 115:125 */       il.append(new INVOKEINTERFACE(gitr, 1));
/* 116:    */     }
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.AbsoluteLocationPath
 * JD-Core Version:    0.7.0.1
 */