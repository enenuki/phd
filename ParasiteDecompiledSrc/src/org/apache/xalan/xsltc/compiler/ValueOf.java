/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   6:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   7:    */ import org.apache.bcel.generic.InstructionConstants;
/*   8:    */ import org.apache.bcel.generic.InstructionList;
/*   9:    */ import org.apache.bcel.generic.MethodGen;
/*  10:    */ import org.apache.bcel.generic.PUSH;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  16:    */ 
/*  17:    */ final class ValueOf
/*  18:    */   extends Instruction
/*  19:    */ {
/*  20:    */   private Expression _select;
/*  21: 43 */   private boolean _escaping = true;
/*  22: 44 */   private boolean _isString = false;
/*  23:    */   
/*  24:    */   public void display(int indent)
/*  25:    */   {
/*  26: 47 */     indent(indent);
/*  27: 48 */     Util.println("ValueOf");
/*  28: 49 */     indent(indent + 4);
/*  29: 50 */     Util.println("select " + this._select.toString());
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void parseContents(Parser parser)
/*  33:    */   {
/*  34: 54 */     this._select = parser.parseExpression(this, "select", null);
/*  35: 57 */     if (this._select.isDummy())
/*  36:    */     {
/*  37: 58 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "select");
/*  38: 59 */       return;
/*  39:    */     }
/*  40: 61 */     String str = getAttribute("disable-output-escaping");
/*  41: 62 */     if ((str != null) && (str.equals("yes"))) {
/*  42: 62 */       this._escaping = false;
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Type typeCheck(SymbolTable stable)
/*  47:    */     throws TypeCheckError
/*  48:    */   {
/*  49: 66 */     Type type = this._select.typeCheck(stable);
/*  50: 69 */     if ((type != null) && (!type.identicalTo(Type.Node))) {
/*  51: 79 */       if (type.identicalTo(Type.NodeSet))
/*  52:    */       {
/*  53: 80 */         this._select = new CastExpr(this._select, Type.Node);
/*  54:    */       }
/*  55:    */       else
/*  56:    */       {
/*  57: 82 */         this._isString = true;
/*  58: 83 */         if (!type.identicalTo(Type.String)) {
/*  59: 84 */           this._select = new CastExpr(this._select, Type.String);
/*  60:    */         }
/*  61: 86 */         this._isString = true;
/*  62:    */       }
/*  63:    */     }
/*  64: 89 */     return Type.Void;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  68:    */   {
/*  69: 93 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  70: 94 */     InstructionList il = methodGen.getInstructionList();
/*  71: 95 */     int setEscaping = cpg.addInterfaceMethodref(Constants.OUTPUT_HANDLER, "setEscaping", "(Z)Z");
/*  72: 99 */     if (!this._escaping)
/*  73:    */     {
/*  74:100 */       il.append(methodGen.loadHandler());
/*  75:101 */       il.append(new PUSH(cpg, false));
/*  76:102 */       il.append(new INVOKEINTERFACE(setEscaping, 2));
/*  77:    */     }
/*  78:110 */     if (this._isString)
/*  79:    */     {
/*  80:111 */       int characters = cpg.addMethodref("org.apache.xalan.xsltc.runtime.AbstractTranslet", "characters", Constants.CHARACTERSW_SIG);
/*  81:    */       
/*  82:    */ 
/*  83:    */ 
/*  84:115 */       il.append(classGen.loadTranslet());
/*  85:116 */       this._select.translate(classGen, methodGen);
/*  86:117 */       il.append(methodGen.loadHandler());
/*  87:118 */       il.append(new INVOKEVIRTUAL(characters));
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:120 */       int characters = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "characters", Constants.CHARACTERS_SIG);
/*  92:    */       
/*  93:    */ 
/*  94:    */ 
/*  95:124 */       il.append(methodGen.loadDOM());
/*  96:125 */       this._select.translate(classGen, methodGen);
/*  97:126 */       il.append(methodGen.loadHandler());
/*  98:127 */       il.append(new INVOKEINTERFACE(characters, 3));
/*  99:    */     }
/* 100:131 */     if (!this._escaping)
/* 101:    */     {
/* 102:132 */       il.append(methodGen.loadHandler());
/* 103:133 */       il.append(InstructionConstants.SWAP);
/* 104:134 */       il.append(new INVOKEINTERFACE(setEscaping, 2));
/* 105:135 */       il.append(InstructionConstants.POP);
/* 106:    */     }
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ValueOf
 * JD-Core Version:    0.7.0.1
 */