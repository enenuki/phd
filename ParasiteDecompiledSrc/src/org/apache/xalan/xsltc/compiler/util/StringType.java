/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ASTORE;
/*   5:    */ import org.apache.bcel.generic.BranchHandle;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.GOTO;
/*   9:    */ import org.apache.bcel.generic.IFEQ;
/*  10:    */ import org.apache.bcel.generic.IFNONNULL;
/*  11:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*  12:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  13:    */ import org.apache.bcel.generic.Instruction;
/*  14:    */ import org.apache.bcel.generic.InstructionConstants;
/*  15:    */ import org.apache.bcel.generic.InstructionList;
/*  16:    */ import org.apache.bcel.generic.MethodGen;
/*  17:    */ import org.apache.bcel.generic.PUSH;
/*  18:    */ import org.apache.xalan.xsltc.compiler.FlowList;
/*  19:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*  20:    */ 
/*  21:    */ public class StringType
/*  22:    */   extends Type
/*  23:    */ {
/*  24:    */   public String toString()
/*  25:    */   {
/*  26: 47 */     return "string";
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean identicalTo(Type other)
/*  30:    */   {
/*  31: 51 */     return this == other;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toSignature()
/*  35:    */   {
/*  36: 55 */     return "Ljava/lang/String;";
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean isSimple()
/*  40:    */   {
/*  41: 59 */     return true;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public org.apache.bcel.generic.Type toJCType()
/*  45:    */   {
/*  46: 63 */     return org.apache.bcel.generic.Type.STRING;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  50:    */   {
/*  51: 75 */     if (type == Type.Boolean)
/*  52:    */     {
/*  53: 76 */       translateTo(classGen, methodGen, (BooleanType)type);
/*  54:    */     }
/*  55: 78 */     else if (type == Type.Real)
/*  56:    */     {
/*  57: 79 */       translateTo(classGen, methodGen, (RealType)type);
/*  58:    */     }
/*  59: 81 */     else if (type == Type.Reference)
/*  60:    */     {
/*  61: 82 */       translateTo(classGen, methodGen, (ReferenceType)type);
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65: 85 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/*  66:    */       
/*  67: 87 */       classGen.getParser().reportError(2, err);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/*  72:    */   {
/*  73: 98 */     InstructionList il = methodGen.getInstructionList();
/*  74: 99 */     FlowList falsel = translateToDesynthesized(classGen, methodGen, type);
/*  75:100 */     il.append(InstructionConstants.ICONST_1);
/*  76:101 */     BranchHandle truec = il.append(new GOTO(null));
/*  77:102 */     falsel.backPatch(il.append(InstructionConstants.ICONST_0));
/*  78:103 */     truec.setTarget(il.append(InstructionConstants.NOP));
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, RealType type)
/*  82:    */   {
/*  83:114 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  84:115 */     InstructionList il = methodGen.getInstructionList();
/*  85:116 */     il.append(new INVOKESTATIC(cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "stringToReal", "(Ljava/lang/String;)D")));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/*  89:    */   {
/*  90:131 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  91:132 */     InstructionList il = methodGen.getInstructionList();
/*  92:    */     
/*  93:134 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("java.lang.String", "length", "()I")));
/*  94:    */     
/*  95:136 */     return new FlowList(il.append(new IFEQ(null)));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ReferenceType type)
/*  99:    */   {
/* 100:147 */     methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 104:    */   {
/* 105:159 */     if (clazz.isAssignableFrom(String.class))
/* 106:    */     {
/* 107:160 */       methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:163 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getName());
/* 112:    */       
/* 113:165 */       classGen.getParser().reportError(2, err);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 118:    */   {
/* 119:177 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 120:178 */     InstructionList il = methodGen.getInstructionList();
/* 121:180 */     if (clazz.getName().equals("java.lang.String"))
/* 122:    */     {
/* 123:182 */       il.append(InstructionConstants.DUP);
/* 124:183 */       BranchHandle ifNonNull = il.append(new IFNONNULL(null));
/* 125:184 */       il.append(InstructionConstants.POP);
/* 126:185 */       il.append(new PUSH(cpg, ""));
/* 127:186 */       ifNonNull.setTarget(il.append(InstructionConstants.NOP));
/* 128:    */     }
/* 129:    */     else
/* 130:    */     {
/* 131:189 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getName());
/* 132:    */       
/* 133:191 */       classGen.getParser().reportError(2, err);
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 138:    */   {
/* 139:200 */     translateTo(classGen, methodGen, Type.Reference);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 143:    */   {
/* 144:208 */     methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getClassName()
/* 148:    */   {
/* 149:215 */     return "java.lang.String";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Instruction LOAD(int slot)
/* 153:    */   {
/* 154:220 */     return new ALOAD(slot);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Instruction STORE(int slot)
/* 158:    */   {
/* 159:224 */     return new ASTORE(slot);
/* 160:    */   }
/* 161:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.StringType
 * JD-Core Version:    0.7.0.1
 */