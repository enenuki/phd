/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.BranchHandle;
/*   4:    */ import org.apache.bcel.generic.BranchInstruction;
/*   5:    */ import org.apache.bcel.generic.CHECKCAST;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.GOTO;
/*   9:    */ import org.apache.bcel.generic.IFEQ;
/*  10:    */ import org.apache.bcel.generic.IFGE;
/*  11:    */ import org.apache.bcel.generic.IFGT;
/*  12:    */ import org.apache.bcel.generic.IFLE;
/*  13:    */ import org.apache.bcel.generic.IFLT;
/*  14:    */ import org.apache.bcel.generic.IF_ICMPGE;
/*  15:    */ import org.apache.bcel.generic.IF_ICMPGT;
/*  16:    */ import org.apache.bcel.generic.IF_ICMPLE;
/*  17:    */ import org.apache.bcel.generic.IF_ICMPLT;
/*  18:    */ import org.apache.bcel.generic.ILOAD;
/*  19:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  20:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  21:    */ import org.apache.bcel.generic.ISTORE;
/*  22:    */ import org.apache.bcel.generic.Instruction;
/*  23:    */ import org.apache.bcel.generic.InstructionConstants;
/*  24:    */ import org.apache.bcel.generic.InstructionList;
/*  25:    */ import org.apache.bcel.generic.MethodGen;
/*  26:    */ import org.apache.bcel.generic.NEW;
/*  27:    */ import org.apache.bcel.generic.PUSH;
/*  28:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*  29:    */ 
/*  30:    */ public final class BooleanType
/*  31:    */   extends Type
/*  32:    */ {
/*  33:    */   public String toString()
/*  34:    */   {
/*  35: 56 */     return "boolean";
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean identicalTo(Type other)
/*  39:    */   {
/*  40: 60 */     return this == other;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String toSignature()
/*  44:    */   {
/*  45: 64 */     return "Z";
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean isSimple()
/*  49:    */   {
/*  50: 68 */     return true;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public org.apache.bcel.generic.Type toJCType()
/*  54:    */   {
/*  55: 72 */     return org.apache.bcel.generic.Type.BOOLEAN;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  59:    */   {
/*  60: 84 */     if (type == Type.String)
/*  61:    */     {
/*  62: 85 */       translateTo(classGen, methodGen, (StringType)type);
/*  63:    */     }
/*  64: 87 */     else if (type == Type.Real)
/*  65:    */     {
/*  66: 88 */       translateTo(classGen, methodGen, (RealType)type);
/*  67:    */     }
/*  68: 90 */     else if (type == Type.Reference)
/*  69:    */     {
/*  70: 91 */       translateTo(classGen, methodGen, (ReferenceType)type);
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 94 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/*  75:    */       
/*  76: 96 */       classGen.getParser().reportError(2, err);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type)
/*  81:    */   {
/*  82:109 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  83:110 */     InstructionList il = methodGen.getInstructionList();
/*  84:111 */     BranchHandle falsec = il.append(new IFEQ(null));
/*  85:112 */     il.append(new PUSH(cpg, "true"));
/*  86:113 */     BranchHandle truec = il.append(new GOTO(null));
/*  87:114 */     falsec.setTarget(il.append(new PUSH(cpg, "false")));
/*  88:115 */     truec.setTarget(il.append(InstructionConstants.NOP));
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, RealType type)
/*  92:    */   {
/*  93:126 */     methodGen.getInstructionList().append(InstructionConstants.I2D);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ReferenceType type)
/*  97:    */   {
/*  98:138 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  99:139 */     InstructionList il = methodGen.getInstructionList();
/* 100:140 */     il.append(new NEW(cpg.addClass("java.lang.Boolean")));
/* 101:141 */     il.append(InstructionConstants.DUP_X1);
/* 102:142 */     il.append(InstructionConstants.SWAP);
/* 103:143 */     il.append(new INVOKESPECIAL(cpg.addMethodref("java.lang.Boolean", "<init>", "(Z)V")));
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 107:    */   {
/* 108:153 */     if (clazz == Boolean.TYPE)
/* 109:    */     {
/* 110:154 */       methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 111:    */     }
/* 112:157 */     else if (clazz.isAssignableFrom(Boolean.class))
/* 113:    */     {
/* 114:158 */       translateTo(classGen, methodGen, Type.Reference);
/* 115:    */     }
/* 116:    */     else
/* 117:    */     {
/* 118:161 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getName());
/* 119:    */       
/* 120:163 */       classGen.getParser().reportError(2, err);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 125:    */   {
/* 126:172 */     translateTo(classGen, methodGen, clazz);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 130:    */   {
/* 131:180 */     translateTo(classGen, methodGen, Type.Reference);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 135:    */   {
/* 136:188 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 137:189 */     InstructionList il = methodGen.getInstructionList();
/* 138:190 */     il.append(new CHECKCAST(cpg.addClass("java.lang.Boolean")));
/* 139:191 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("java.lang.Boolean", "booleanValue", "()Z")));
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Instruction LOAD(int slot)
/* 143:    */   {
/* 144:197 */     return new ILOAD(slot);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Instruction STORE(int slot)
/* 148:    */   {
/* 149:201 */     return new ISTORE(slot);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public BranchInstruction GT(boolean tozero)
/* 153:    */   {
/* 154:205 */     return tozero ? new IFGT(null) : new IF_ICMPGT(null);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public BranchInstruction GE(boolean tozero)
/* 158:    */   {
/* 159:210 */     return tozero ? new IFGE(null) : new IF_ICMPGE(null);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public BranchInstruction LT(boolean tozero)
/* 163:    */   {
/* 164:215 */     return tozero ? new IFLT(null) : new IF_ICMPLT(null);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public BranchInstruction LE(boolean tozero)
/* 168:    */   {
/* 169:220 */     return tozero ? new IFLE(null) : new IF_ICMPLE(null);
/* 170:    */   }
/* 171:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.BooleanType
 * JD-Core Version:    0.7.0.1
 */