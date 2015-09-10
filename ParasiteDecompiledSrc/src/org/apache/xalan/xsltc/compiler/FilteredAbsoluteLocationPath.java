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
/*  21:    */ final class FilteredAbsoluteLocationPath
/*  22:    */   extends Expression
/*  23:    */ {
/*  24:    */   private Expression _path;
/*  25:    */   
/*  26:    */   public FilteredAbsoluteLocationPath()
/*  27:    */   {
/*  28: 46 */     this._path = null;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public FilteredAbsoluteLocationPath(Expression path)
/*  32:    */   {
/*  33: 50 */     this._path = path;
/*  34: 51 */     if (path != null) {
/*  35: 52 */       this._path.setParent(this);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setParser(Parser parser)
/*  40:    */   {
/*  41: 57 */     super.setParser(parser);
/*  42: 58 */     if (this._path != null) {
/*  43: 59 */       this._path.setParser(parser);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Expression getPath()
/*  48:    */   {
/*  49: 64 */     return this._path;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String toString()
/*  53:    */   {
/*  54: 68 */     return "FilteredAbsoluteLocationPath(" + (this._path != null ? this._path.toString() : "null") + ')';
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Type typeCheck(SymbolTable stable)
/*  58:    */     throws TypeCheckError
/*  59:    */   {
/*  60: 73 */     if (this._path != null)
/*  61:    */     {
/*  62: 74 */       Type ptype = this._path.typeCheck(stable);
/*  63: 75 */       if ((ptype instanceof NodeType)) {
/*  64: 76 */         this._path = new CastExpr(this._path, Type.NodeSet);
/*  65:    */       }
/*  66:    */     }
/*  67: 79 */     return this._type = Type.NodeSet;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  71:    */   {
/*  72: 83 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  73: 84 */     InstructionList il = methodGen.getInstructionList();
/*  74: 85 */     if (this._path != null)
/*  75:    */     {
/*  76: 86 */       int initDFI = cpg.addMethodref("org.apache.xalan.xsltc.dom.DupFilterIterator", "<init>", "(Lorg/apache/xml/dtm/DTMAxisIterator;)V");
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
/*  92:102 */       LocalVariableGen pathTemp = methodGen.addLocalVariable("filtered_absolute_location_path_tmp", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/*  93:    */       
/*  94:    */ 
/*  95:    */ 
/*  96:106 */       this._path.translate(classGen, methodGen);
/*  97:107 */       pathTemp.setStart(il.append(new ASTORE(pathTemp.getIndex())));
/*  98:    */       
/*  99:    */ 
/* 100:110 */       il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.DupFilterIterator")));
/* 101:111 */       il.append(InstructionConstants.DUP);
/* 102:112 */       pathTemp.setEnd(il.append(new ALOAD(pathTemp.getIndex())));
/* 103:    */       
/* 104:    */ 
/* 105:115 */       il.append(new INVOKESPECIAL(initDFI));
/* 106:    */     }
/* 107:    */     else
/* 108:    */     {
/* 109:118 */       int git = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getIterator", "()Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 110:    */       
/* 111:    */ 
/* 112:121 */       il.append(methodGen.loadDOM());
/* 113:122 */       il.append(new INVOKEINTERFACE(git, 1));
/* 114:    */     }
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.FilteredAbsoluteLocationPath
 * JD-Core Version:    0.7.0.1
 */