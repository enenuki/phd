/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*   7:    */ import org.apache.bcel.generic.InstructionList;
/*   8:    */ import org.apache.bcel.generic.MethodGen;
/*   9:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  13:    */ 
/*  14:    */ class NameBase
/*  15:    */   extends FunctionCall
/*  16:    */ {
/*  17: 40 */   private Expression _param = null;
/*  18: 41 */   private Type _paramType = Type.Node;
/*  19:    */   
/*  20:    */   public NameBase(QName fname)
/*  21:    */   {
/*  22: 47 */     super(fname);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public NameBase(QName fname, Vector arguments)
/*  26:    */   {
/*  27: 54 */     super(fname, arguments);
/*  28: 55 */     this._param = argument(0);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Type typeCheck(SymbolTable stable)
/*  32:    */     throws TypeCheckError
/*  33:    */   {
/*  34: 66 */     switch (argumentCount())
/*  35:    */     {
/*  36:    */     case 0: 
/*  37: 68 */       this._paramType = Type.Node;
/*  38: 69 */       break;
/*  39:    */     case 1: 
/*  40: 71 */       this._paramType = this._param.typeCheck(stable);
/*  41: 72 */       break;
/*  42:    */     default: 
/*  43: 74 */       throw new TypeCheckError(this);
/*  44:    */     }
/*  45: 78 */     if ((this._paramType != Type.NodeSet) && (this._paramType != Type.Node) && (this._paramType != Type.Reference)) {
/*  46: 81 */       throw new TypeCheckError(this);
/*  47:    */     }
/*  48: 84 */     return this._type = Type.String;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Type getType()
/*  52:    */   {
/*  53: 88 */     return this._type;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  57:    */   {
/*  58: 97 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  59: 98 */     InstructionList il = methodGen.getInstructionList();
/*  60:    */     
/*  61:100 */     il.append(methodGen.loadDOM());
/*  62:103 */     if (argumentCount() == 0)
/*  63:    */     {
/*  64:104 */       il.append(methodGen.loadContextNode());
/*  65:    */     }
/*  66:107 */     else if (this._paramType == Type.Node)
/*  67:    */     {
/*  68:108 */       this._param.translate(classGen, methodGen);
/*  69:    */     }
/*  70:110 */     else if (this._paramType == Type.Reference)
/*  71:    */     {
/*  72:111 */       this._param.translate(classGen, methodGen);
/*  73:112 */       il.append(new INVOKESTATIC(cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "referenceToNodeSet", "(Ljava/lang/Object;)Lorg/apache/xml/dtm/DTMAxisIterator;")));
/*  74:    */       
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:119 */       il.append(methodGen.nextNode());
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:123 */       this._param.translate(classGen, methodGen);
/*  85:124 */       this._param.startIterator(classGen, methodGen);
/*  86:125 */       il.append(methodGen.nextNode());
/*  87:    */     }
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.NameBase
 * JD-Core Version:    0.7.0.1
 */