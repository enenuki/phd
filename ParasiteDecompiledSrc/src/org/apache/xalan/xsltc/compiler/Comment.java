/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.GETFIELD;
/*   6:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   7:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.bcel.generic.PUSH;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  16:    */ 
/*  17:    */ final class Comment
/*  18:    */   extends Instruction
/*  19:    */ {
/*  20:    */   public void parseContents(Parser parser)
/*  21:    */   {
/*  22: 43 */     parseChildren(parser);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Type typeCheck(SymbolTable stable)
/*  26:    */     throws TypeCheckError
/*  27:    */   {
/*  28: 47 */     typeCheckContents(stable);
/*  29: 48 */     return Type.String;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  33:    */   {
/*  34: 52 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  35: 53 */     InstructionList il = methodGen.getInstructionList();
/*  36:    */     
/*  37:    */ 
/*  38: 56 */     Text rawText = null;
/*  39: 57 */     if (elementCount() == 1)
/*  40:    */     {
/*  41: 58 */       Object content = elementAt(0);
/*  42: 59 */       if ((content instanceof Text)) {
/*  43: 60 */         rawText = (Text)content;
/*  44:    */       }
/*  45:    */     }
/*  46: 68 */     if (rawText != null)
/*  47:    */     {
/*  48: 69 */       il.append(methodGen.loadHandler());
/*  49: 71 */       if (rawText.canLoadAsArrayOffsetLength())
/*  50:    */       {
/*  51: 72 */         rawText.loadAsArrayOffsetLength(classGen, methodGen);
/*  52: 73 */         int comment = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "comment", "([CII)V");
/*  53:    */         
/*  54:    */ 
/*  55:    */ 
/*  56: 77 */         il.append(new INVOKEINTERFACE(comment, 4));
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60: 79 */         il.append(new PUSH(cpg, rawText.getText()));
/*  61: 80 */         int comment = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "comment", "(Ljava/lang/String;)V");
/*  62:    */         
/*  63:    */ 
/*  64:    */ 
/*  65: 84 */         il.append(new INVOKEINTERFACE(comment, 2));
/*  66:    */       }
/*  67:    */     }
/*  68:    */     else
/*  69:    */     {
/*  70: 88 */       il.append(methodGen.loadHandler());
/*  71: 89 */       il.append(InstructionConstants.DUP);
/*  72:    */       
/*  73:    */ 
/*  74: 92 */       il.append(classGen.loadTranslet());
/*  75: 93 */       il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "stringValueHandler", "Lorg/apache/xalan/xsltc/runtime/StringValueHandler;")));
/*  76:    */       
/*  77:    */ 
/*  78: 96 */       il.append(InstructionConstants.DUP);
/*  79: 97 */       il.append(methodGen.storeHandler());
/*  80:    */       
/*  81:    */ 
/*  82:100 */       translateContents(classGen, methodGen);
/*  83:    */       
/*  84:    */ 
/*  85:103 */       il.append(new INVOKEVIRTUAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.StringValueHandler", "getValue", "()Ljava/lang/String;")));
/*  86:    */       
/*  87:    */ 
/*  88:    */ 
/*  89:107 */       int comment = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "comment", "(Ljava/lang/String;)V");
/*  90:    */       
/*  91:    */ 
/*  92:    */ 
/*  93:111 */       il.append(new INVOKEINTERFACE(comment, 2));
/*  94:    */       
/*  95:113 */       il.append(methodGen.storeHandler());
/*  96:    */     }
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Comment
 * JD-Core Version:    0.7.0.1
 */