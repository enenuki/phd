/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ASTORE;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.GETFIELD;
/*   8:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   9:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*  10:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  11:    */ import org.apache.bcel.generic.InstructionConstants;
/*  12:    */ import org.apache.bcel.generic.InstructionList;
/*  13:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  14:    */ import org.apache.bcel.generic.MethodGen;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  21:    */ import org.apache.xml.utils.XML11Char;
/*  22:    */ 
/*  23:    */ final class ProcessingInstruction
/*  24:    */   extends Instruction
/*  25:    */ {
/*  26:    */   private AttributeValue _name;
/*  27: 48 */   private boolean _isLiteral = false;
/*  28:    */   
/*  29:    */   public void parseContents(Parser parser)
/*  30:    */   {
/*  31: 51 */     String name = getAttribute("name");
/*  32: 53 */     if (name.length() > 0)
/*  33:    */     {
/*  34: 54 */       this._isLiteral = Util.isLiteral(name);
/*  35: 55 */       if ((this._isLiteral) && 
/*  36: 56 */         (!XML11Char.isXML11ValidNCName(name)))
/*  37:    */       {
/*  38: 57 */         ErrorMsg err = new ErrorMsg("INVALID_NCNAME_ERR", name, this);
/*  39: 58 */         parser.reportError(3, err);
/*  40:    */       }
/*  41: 61 */       this._name = AttributeValue.create(this, name, parser);
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45: 64 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "name");
/*  46:    */     }
/*  47: 66 */     if (name.equals("xml")) {
/*  48: 67 */       reportError(this, parser, "ILLEGAL_PI_ERR", "xml");
/*  49:    */     }
/*  50: 69 */     parseChildren(parser);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Type typeCheck(SymbolTable stable)
/*  54:    */     throws TypeCheckError
/*  55:    */   {
/*  56: 73 */     this._name.typeCheck(stable);
/*  57: 74 */     typeCheckContents(stable);
/*  58: 75 */     return Type.Void;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  62:    */   {
/*  63: 79 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  64: 80 */     InstructionList il = methodGen.getInstructionList();
/*  65: 82 */     if (!this._isLiteral)
/*  66:    */     {
/*  67: 84 */       LocalVariableGen nameValue = methodGen.addLocalVariable2("nameValue", Util.getJCRefType("Ljava/lang/String;"), null);
/*  68:    */       
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73: 90 */       this._name.translate(classGen, methodGen);
/*  74: 91 */       nameValue.setStart(il.append(new ASTORE(nameValue.getIndex())));
/*  75: 92 */       il.append(new ALOAD(nameValue.getIndex()));
/*  76:    */       
/*  77:    */ 
/*  78: 95 */       int check = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "checkNCName", "(Ljava/lang/String;)V");
/*  79:    */       
/*  80:    */ 
/*  81:    */ 
/*  82: 99 */       il.append(new INVOKESTATIC(check));
/*  83:    */       
/*  84:    */ 
/*  85:102 */       il.append(methodGen.loadHandler());
/*  86:103 */       il.append(InstructionConstants.DUP);
/*  87:    */       
/*  88:    */ 
/*  89:106 */       nameValue.setEnd(il.append(new ALOAD(nameValue.getIndex())));
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:109 */       il.append(methodGen.loadHandler());
/*  94:110 */       il.append(InstructionConstants.DUP);
/*  95:    */       
/*  96:    */ 
/*  97:113 */       this._name.translate(classGen, methodGen);
/*  98:    */     }
/*  99:117 */     il.append(classGen.loadTranslet());
/* 100:118 */     il.append(new GETFIELD(cpg.addFieldref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "stringValueHandler", "Lorg/apache/xalan/xsltc/runtime/StringValueHandler;")));
/* 101:    */     
/* 102:    */ 
/* 103:121 */     il.append(InstructionConstants.DUP);
/* 104:122 */     il.append(methodGen.storeHandler());
/* 105:    */     
/* 106:    */ 
/* 107:125 */     translateContents(classGen, methodGen);
/* 108:    */     
/* 109:    */ 
/* 110:128 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("org.apache.xalan.xsltc.runtime.StringValueHandler", "getValueOfPI", "()Ljava/lang/String;")));
/* 111:    */     
/* 112:    */ 
/* 113:    */ 
/* 114:132 */     int processingInstruction = cpg.addInterfaceMethodref(Constants.TRANSLET_OUTPUT_INTERFACE, "processingInstruction", "(Ljava/lang/String;Ljava/lang/String;)V");
/* 115:    */     
/* 116:    */ 
/* 117:    */ 
/* 118:136 */     il.append(new INVOKEINTERFACE(processingInstruction, 3));
/* 119:    */     
/* 120:138 */     il.append(methodGen.storeHandler());
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ProcessingInstruction
 * JD-Core Version:    0.7.0.1
 */