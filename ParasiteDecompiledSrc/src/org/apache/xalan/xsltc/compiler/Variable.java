/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.classfile.Field;
/*   5:    */ import org.apache.bcel.generic.ACONST_NULL;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.DCONST;
/*   9:    */ import org.apache.bcel.generic.ICONST;
/*  10:    */ import org.apache.bcel.generic.InstructionHandle;
/*  11:    */ import org.apache.bcel.generic.InstructionList;
/*  12:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  13:    */ import org.apache.bcel.generic.MethodGen;
/*  14:    */ import org.apache.bcel.generic.PUTFIELD;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.BooleanType;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.IntType;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.NodeType;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.RealType;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  23:    */ 
/*  24:    */ final class Variable
/*  25:    */   extends VariableBase
/*  26:    */ {
/*  27:    */   public int getIndex()
/*  28:    */   {
/*  29: 45 */     return this._local != null ? this._local.getIndex() : -1;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void parseContents(Parser parser)
/*  33:    */   {
/*  34: 53 */     super.parseContents(parser);
/*  35:    */     
/*  36:    */ 
/*  37: 56 */     SyntaxTreeNode parent = getParent();
/*  38: 57 */     if ((parent instanceof Stylesheet))
/*  39:    */     {
/*  40: 59 */       this._isLocal = false;
/*  41:    */       
/*  42: 61 */       Variable var = parser.getSymbolTable().lookupVariable(this._name);
/*  43: 63 */       if (var != null)
/*  44:    */       {
/*  45: 64 */         int us = getImportPrecedence();
/*  46: 65 */         int them = var.getImportPrecedence();
/*  47: 67 */         if (us == them)
/*  48:    */         {
/*  49: 68 */           String name = this._name.toString();
/*  50: 69 */           reportError(this, parser, "VARIABLE_REDEF_ERR", name);
/*  51:    */         }
/*  52:    */         else
/*  53:    */         {
/*  54: 72 */           if (them > us)
/*  55:    */           {
/*  56: 73 */             this._ignore = true;
/*  57: 74 */             return;
/*  58:    */           }
/*  59: 77 */           var.disable();
/*  60:    */         }
/*  61:    */       }
/*  62: 81 */       ((Stylesheet)parent).addVariable(this);
/*  63: 82 */       parser.getSymbolTable().addVariable(this);
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67: 85 */       this._isLocal = true;
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Type typeCheck(SymbolTable stable)
/*  72:    */     throws TypeCheckError
/*  73:    */   {
/*  74: 96 */     if (this._select != null)
/*  75:    */     {
/*  76: 97 */       this._type = this._select.typeCheck(stable);
/*  77:    */     }
/*  78:100 */     else if (hasContents())
/*  79:    */     {
/*  80:101 */       typeCheckContents(stable);
/*  81:102 */       this._type = Type.ResultTree;
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:105 */       this._type = Type.Reference;
/*  86:    */     }
/*  87:110 */     return Type.Void;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void initialize(ClassGenerator classGen, MethodGenerator methodGen)
/*  91:    */   {
/*  92:119 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  93:120 */     InstructionList il = methodGen.getInstructionList();
/*  94:123 */     if ((isLocal()) && (!this._refs.isEmpty()))
/*  95:    */     {
/*  96:125 */       if (this._local == null) {
/*  97:126 */         this._local = methodGen.addLocalVariable2(getEscapedName(), this._type.toJCType(), null);
/*  98:    */       }
/*  99:131 */       if (((this._type instanceof IntType)) || ((this._type instanceof NodeType)) || ((this._type instanceof BooleanType))) {
/* 100:134 */         il.append(new ICONST(0));
/* 101:135 */       } else if ((this._type instanceof RealType)) {
/* 102:136 */         il.append(new DCONST(0.0D));
/* 103:    */       } else {
/* 104:138 */         il.append(new ACONST_NULL());
/* 105:    */       }
/* 106:141 */       this._local.setStart(il.append(this._type.STORE(this._local.getIndex())));
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 111:    */   {
/* 112:146 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 113:147 */     InstructionList il = methodGen.getInstructionList();
/* 114:150 */     if (this._refs.isEmpty()) {
/* 115:151 */       this._ignore = true;
/* 116:    */     }
/* 117:155 */     if (this._ignore) {
/* 118:155 */       return;
/* 119:    */     }
/* 120:156 */     this._ignore = true;
/* 121:    */     
/* 122:158 */     String name = getEscapedName();
/* 123:160 */     if (isLocal())
/* 124:    */     {
/* 125:162 */       translateValue(classGen, methodGen);
/* 126:    */       
/* 127:    */ 
/* 128:165 */       boolean createLocal = this._local == null;
/* 129:166 */       if (createLocal) {
/* 130:167 */         mapRegister(methodGen);
/* 131:    */       }
/* 132:169 */       InstructionHandle storeInst = il.append(this._type.STORE(this._local.getIndex()));
/* 133:176 */       if (createLocal) {
/* 134:177 */         this._local.setStart(storeInst);
/* 135:    */       }
/* 136:    */     }
/* 137:    */     else
/* 138:    */     {
/* 139:181 */       String signature = this._type.toSignature();
/* 140:184 */       if (classGen.containsField(name) == null)
/* 141:    */       {
/* 142:185 */         classGen.addField(new Field(1, cpg.addUtf8(name), cpg.addUtf8(signature), null, cpg.getConstantPool()));
/* 143:    */         
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:191 */         il.append(classGen.loadTranslet());
/* 149:    */         
/* 150:193 */         translateValue(classGen, methodGen);
/* 151:    */         
/* 152:195 */         il.append(new PUTFIELD(cpg.addFieldref(classGen.getClassName(), name, signature)));
/* 153:    */       }
/* 154:    */     }
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Variable
 * JD-Core Version:    0.7.0.1
 */