/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   6:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   7:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.bcel.generic.NEW;
/*  12:    */ import org.apache.bcel.generic.PUSH;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  17:    */ 
/*  18:    */ final class Message
/*  19:    */   extends Instruction
/*  20:    */ {
/*  21: 42 */   private boolean _terminate = false;
/*  22:    */   
/*  23:    */   public void parseContents(Parser parser)
/*  24:    */   {
/*  25: 45 */     String termstr = getAttribute("terminate");
/*  26: 46 */     if (termstr != null) {
/*  27: 47 */       this._terminate = termstr.equals("yes");
/*  28:    */     }
/*  29: 49 */     parseChildren(parser);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Type typeCheck(SymbolTable stable)
/*  33:    */     throws TypeCheckError
/*  34:    */   {
/*  35: 53 */     typeCheckContents(stable);
/*  36: 54 */     return Type.Void;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  40:    */   {
/*  41: 58 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  42: 59 */     InstructionList il = methodGen.getInstructionList();
/*  43:    */     
/*  44:    */ 
/*  45: 62 */     il.append(classGen.loadTranslet());
/*  46: 64 */     switch (elementCount())
/*  47:    */     {
/*  48:    */     case 0: 
/*  49: 66 */       il.append(new PUSH(cpg, ""));
/*  50: 67 */       break;
/*  51:    */     case 1: 
/*  52: 69 */       SyntaxTreeNode child = (SyntaxTreeNode)elementAt(0);
/*  53: 70 */       if ((child instanceof Text)) {
/*  54: 71 */         il.append(new PUSH(cpg, ((Text)child).getText()));
/*  55:    */       }
/*  56: 72 */       break;
/*  57:    */     }
/*  58: 77 */     il.append(methodGen.loadHandler());
/*  59:    */     
/*  60:    */ 
/*  61: 80 */     il.append(new NEW(cpg.addClass(Constants.STREAM_XML_OUTPUT)));
/*  62: 81 */     il.append(methodGen.storeHandler());
/*  63:    */     
/*  64:    */ 
/*  65: 84 */     il.append(new NEW(cpg.addClass("java.io.StringWriter")));
/*  66: 85 */     il.append(InstructionConstants.DUP);
/*  67: 86 */     il.append(InstructionConstants.DUP);
/*  68: 87 */     il.append(new INVOKESPECIAL(cpg.addMethodref("java.io.StringWriter", "<init>", "()V")));
/*  69:    */     
/*  70:    */ 
/*  71:    */ 
/*  72: 91 */     il.append(methodGen.loadHandler());
/*  73: 92 */     il.append(new INVOKESPECIAL(cpg.addMethodref(Constants.STREAM_XML_OUTPUT, "<init>", "()V")));
/*  74:    */     
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78: 97 */     il.append(methodGen.loadHandler());
/*  79: 98 */     il.append(InstructionConstants.SWAP);
/*  80: 99 */     il.append(new INVOKEINTERFACE(cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "setWriter", "(Ljava/io/Writer;)V"), 2));
/*  81:    */     
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:105 */     il.append(methodGen.loadHandler());
/*  87:106 */     il.append(new PUSH(cpg, "UTF-8"));
/*  88:107 */     il.append(new INVOKEINTERFACE(cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "setEncoding", "(Ljava/lang/String;)V"), 2));
/*  89:    */     
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:113 */     il.append(methodGen.loadHandler());
/*  95:114 */     il.append(InstructionConstants.ICONST_1);
/*  96:115 */     il.append(new INVOKEINTERFACE(cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "setOmitXMLDeclaration", "(Z)V"), 2));
/*  97:    */     
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:120 */     il.append(methodGen.loadHandler());
/* 102:121 */     il.append(new INVOKEINTERFACE(cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "startDocument", "()V"), 1));
/* 103:    */     
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:127 */     translateContents(classGen, methodGen);
/* 109:    */     
/* 110:129 */     il.append(methodGen.loadHandler());
/* 111:130 */     il.append(new INVOKEINTERFACE(cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "endDocument", "()V"), 1));
/* 112:    */     
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:136 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("java.io.StringWriter", "toString", "()Ljava/lang/String;")));
/* 118:    */     
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:141 */     il.append(InstructionConstants.SWAP);
/* 123:142 */     il.append(methodGen.storeHandler());
/* 124:    */     
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:147 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "displayMessage", "(Ljava/lang/String;)V")));
/* 129:153 */     if (this._terminate == true)
/* 130:    */     {
/* 131:155 */       int einit = cpg.addMethodref("java.lang.RuntimeException", "<init>", "(Ljava/lang/String;)V");
/* 132:    */       
/* 133:    */ 
/* 134:158 */       il.append(new NEW(cpg.addClass("java.lang.RuntimeException")));
/* 135:159 */       il.append(InstructionConstants.DUP);
/* 136:160 */       il.append(new PUSH(cpg, "Termination forced by an xsl:message instruction"));
/* 137:    */       
/* 138:162 */       il.append(new INVOKESPECIAL(einit));
/* 139:163 */       il.append(InstructionConstants.ATHROW);
/* 140:    */     }
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Message
 * JD-Core Version:    0.7.0.1
 */