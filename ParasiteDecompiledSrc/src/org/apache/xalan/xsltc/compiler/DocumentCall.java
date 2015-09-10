/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.GETFIELD;
/*   7:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.bcel.generic.PUSH;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  17:    */ 
/*  18:    */ final class DocumentCall
/*  19:    */   extends FunctionCall
/*  20:    */ {
/*  21: 44 */   private Expression _arg1 = null;
/*  22: 45 */   private Expression _arg2 = null;
/*  23:    */   private Type _arg1Type;
/*  24:    */   
/*  25:    */   public DocumentCall(QName fname, Vector arguments)
/*  26:    */   {
/*  27: 52 */     super(fname, arguments);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Type typeCheck(SymbolTable stable)
/*  31:    */     throws TypeCheckError
/*  32:    */   {
/*  33: 62 */     int ac = argumentCount();
/*  34: 63 */     if ((ac < 1) || (ac > 2))
/*  35:    */     {
/*  36: 64 */       ErrorMsg msg = new ErrorMsg("ILLEGAL_ARG_ERR", this);
/*  37: 65 */       throw new TypeCheckError(msg);
/*  38:    */     }
/*  39: 67 */     if (getStylesheet() == null)
/*  40:    */     {
/*  41: 68 */       ErrorMsg msg = new ErrorMsg("ILLEGAL_ARG_ERR", this);
/*  42: 69 */       throw new TypeCheckError(msg);
/*  43:    */     }
/*  44: 73 */     this._arg1 = argument(0);
/*  45: 75 */     if (this._arg1 == null)
/*  46:    */     {
/*  47: 76 */       ErrorMsg msg = new ErrorMsg("DOCUMENT_ARG_ERR", this);
/*  48: 77 */       throw new TypeCheckError(msg);
/*  49:    */     }
/*  50: 80 */     this._arg1Type = this._arg1.typeCheck(stable);
/*  51: 81 */     if ((this._arg1Type != Type.NodeSet) && (this._arg1Type != Type.String)) {
/*  52: 82 */       this._arg1 = new CastExpr(this._arg1, Type.String);
/*  53:    */     }
/*  54: 86 */     if (ac == 2)
/*  55:    */     {
/*  56: 87 */       this._arg2 = argument(1);
/*  57: 89 */       if (this._arg2 == null)
/*  58:    */       {
/*  59: 90 */         ErrorMsg msg = new ErrorMsg("DOCUMENT_ARG_ERR", this);
/*  60: 91 */         throw new TypeCheckError(msg);
/*  61:    */       }
/*  62: 94 */       Type arg2Type = this._arg2.typeCheck(stable);
/*  63: 96 */       if (arg2Type.identicalTo(Type.Node))
/*  64:    */       {
/*  65: 97 */         this._arg2 = new CastExpr(this._arg2, Type.NodeSet);
/*  66:    */       }
/*  67: 98 */       else if (!arg2Type.identicalTo(Type.NodeSet))
/*  68:    */       {
/*  69:101 */         ErrorMsg msg = new ErrorMsg("DOCUMENT_ARG_ERR", this);
/*  70:102 */         throw new TypeCheckError(msg);
/*  71:    */       }
/*  72:    */     }
/*  73:106 */     return this._type = Type.NodeSet;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  77:    */   {
/*  78:114 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  79:115 */     InstructionList il = methodGen.getInstructionList();
/*  80:116 */     int ac = argumentCount();
/*  81:    */     
/*  82:118 */     int domField = cpg.addFieldref(classGen.getClassName(), "_dom", "Lorg/apache/xalan/xsltc/DOM;");
/*  83:    */     
/*  84:    */ 
/*  85:    */ 
/*  86:122 */     String docParamList = null;
/*  87:123 */     if (ac == 1) {
/*  88:125 */       docParamList = "(Ljava/lang/Object;Ljava/lang/String;Lorg/apache/xalan/xsltc/runtime/AbstractTranslet;Lorg/apache/xalan/xsltc/DOM;)Lorg/apache/xml/dtm/DTMAxisIterator;";
/*  89:    */     } else {
/*  90:129 */       docParamList = "(Ljava/lang/Object;Lorg/apache/xml/dtm/DTMAxisIterator;Ljava/lang/String;Lorg/apache/xalan/xsltc/runtime/AbstractTranslet;Lorg/apache/xalan/xsltc/DOM;)Lorg/apache/xml/dtm/DTMAxisIterator;";
/*  91:    */     }
/*  92:132 */     int docIdx = cpg.addMethodref("org.apache.xalan.xsltc.dom.LoadDocument", "documentF", docParamList);
/*  93:    */     
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:137 */     this._arg1.translate(classGen, methodGen);
/*  98:138 */     if (this._arg1Type == Type.NodeSet) {
/*  99:139 */       this._arg1.startIterator(classGen, methodGen);
/* 100:    */     }
/* 101:142 */     if (ac == 2)
/* 102:    */     {
/* 103:144 */       this._arg2.translate(classGen, methodGen);
/* 104:145 */       this._arg2.startIterator(classGen, methodGen);
/* 105:    */     }
/* 106:149 */     il.append(new PUSH(cpg, getStylesheet().getSystemId()));
/* 107:150 */     il.append(classGen.loadTranslet());
/* 108:151 */     il.append(InstructionConstants.DUP);
/* 109:152 */     il.append(new GETFIELD(domField));
/* 110:153 */     il.append(new INVOKESTATIC(docIdx));
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.DocumentCall
 * JD-Core Version:    0.7.0.1
 */