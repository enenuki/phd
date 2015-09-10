/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*   6:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   7:    */ import org.apache.bcel.generic.InstructionList;
/*   8:    */ import org.apache.bcel.generic.MethodGen;
/*   9:    */ import org.apache.bcel.generic.PUSH;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.StringType;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  16:    */ 
/*  17:    */ final class TransletOutput
/*  18:    */   extends Instruction
/*  19:    */ {
/*  20:    */   private Expression _filename;
/*  21:    */   private boolean _append;
/*  22:    */   
/*  23:    */   public void display(int indent)
/*  24:    */   {
/*  25: 49 */     indent(indent);
/*  26: 50 */     Util.println("TransletOutput: " + this._filename);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void parseContents(Parser parser)
/*  30:    */   {
/*  31: 59 */     String filename = getAttribute("file");
/*  32:    */     
/*  33:    */ 
/*  34:    */ 
/*  35: 63 */     String append = getAttribute("append");
/*  36: 66 */     if ((filename == null) || (filename.equals(""))) {
/*  37: 67 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "file");
/*  38:    */     }
/*  39: 71 */     this._filename = AttributeValue.create(this, filename, parser);
/*  40: 73 */     if ((append != null) && ((append.toLowerCase().equals("yes")) || (append.toLowerCase().equals("true")))) {
/*  41: 75 */       this._append = true;
/*  42:    */     } else {
/*  43: 78 */       this._append = false;
/*  44:    */     }
/*  45: 80 */     parseChildren(parser);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Type typeCheck(SymbolTable stable)
/*  49:    */     throws TypeCheckError
/*  50:    */   {
/*  51: 87 */     Type type = this._filename.typeCheck(stable);
/*  52: 88 */     if (!(type instanceof StringType)) {
/*  53: 89 */       this._filename = new CastExpr(this._filename, Type.String);
/*  54:    */     }
/*  55: 91 */     typeCheckContents(stable);
/*  56: 92 */     return Type.Void;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  60:    */   {
/*  61:100 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  62:101 */     InstructionList il = methodGen.getInstructionList();
/*  63:102 */     boolean isSecureProcessing = classGen.getParser().getXSLTC().isSecureProcessing();
/*  64:105 */     if (isSecureProcessing)
/*  65:    */     {
/*  66:106 */       int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "unallowed_extension_elementF", "(Ljava/lang/String;)V");
/*  67:    */       
/*  68:    */ 
/*  69:109 */       il.append(new PUSH(cpg, "redirect"));
/*  70:110 */       il.append(new INVOKESTATIC(index));
/*  71:111 */       return;
/*  72:    */     }
/*  73:115 */     il.append(methodGen.loadHandler());
/*  74:    */     
/*  75:117 */     int open = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "openOutputHandler", "(Ljava/lang/String;Z)" + Constants.TRANSLET_OUTPUT_SIG);
/*  76:    */     
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:122 */     int close = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "closeOutputHandler", "(" + Constants.TRANSLET_OUTPUT_SIG + ")V");
/*  81:    */     
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:127 */     il.append(classGen.loadTranslet());
/*  86:128 */     this._filename.translate(classGen, methodGen);
/*  87:129 */     il.append(new PUSH(cpg, this._append));
/*  88:130 */     il.append(new INVOKEVIRTUAL(open));
/*  89:    */     
/*  90:    */ 
/*  91:133 */     il.append(methodGen.storeHandler());
/*  92:    */     
/*  93:    */ 
/*  94:136 */     translateContents(classGen, methodGen);
/*  95:    */     
/*  96:    */ 
/*  97:139 */     il.append(classGen.loadTranslet());
/*  98:140 */     il.append(methodGen.loadHandler());
/*  99:141 */     il.append(new INVOKEVIRTUAL(close));
/* 100:    */     
/* 101:    */ 
/* 102:144 */     il.append(methodGen.storeHandler());
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.TransletOutput
 * JD-Core Version:    0.7.0.1
 */