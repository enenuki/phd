/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ASTORE;
/*   5:    */ import org.apache.bcel.generic.BranchHandle;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.GOTO;
/*   9:    */ import org.apache.bcel.generic.IFNULL;
/*  10:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  11:    */ import org.apache.bcel.generic.Instruction;
/*  12:    */ import org.apache.bcel.generic.InstructionConstants;
/*  13:    */ import org.apache.bcel.generic.InstructionList;
/*  14:    */ import org.apache.bcel.generic.MethodGen;
/*  15:    */ import org.apache.bcel.generic.PUSH;
/*  16:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*  17:    */ 
/*  18:    */ public final class ObjectType
/*  19:    */   extends Type
/*  20:    */ {
/*  21: 42 */   private String _javaClassName = "java.lang.Object";
/*  22: 43 */   private Class _clazz = Object.class;
/*  23:    */   
/*  24:    */   protected ObjectType(String javaClassName)
/*  25:    */   {
/*  26: 51 */     this._javaClassName = javaClassName;
/*  27:    */     try
/*  28:    */     {
/*  29: 54 */       this._clazz = ObjectFactory.findProviderClass(javaClassName, ObjectFactory.findClassLoader(), true);
/*  30:    */     }
/*  31:    */     catch (ClassNotFoundException e)
/*  32:    */     {
/*  33: 58 */       this._clazz = null;
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected ObjectType(Class clazz)
/*  38:    */   {
/*  39: 63 */     this._clazz = clazz;
/*  40: 64 */     this._javaClassName = clazz.getName();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int hashCode()
/*  44:    */   {
/*  45: 72 */     return Object.class.hashCode();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean equals(Object obj)
/*  49:    */   {
/*  50: 76 */     return obj instanceof ObjectType;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getJavaClassName()
/*  54:    */   {
/*  55: 80 */     return this._javaClassName;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Class getJavaClass()
/*  59:    */   {
/*  60: 84 */     return this._clazz;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String toString()
/*  64:    */   {
/*  65: 88 */     return this._javaClassName;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean identicalTo(Type other)
/*  69:    */   {
/*  70: 92 */     return this == other;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String toSignature()
/*  74:    */   {
/*  75: 96 */     StringBuffer result = new StringBuffer("L");
/*  76: 97 */     result.append(this._javaClassName.replace('.', '/')).append(';');
/*  77: 98 */     return result.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public org.apache.bcel.generic.Type toJCType()
/*  81:    */   {
/*  82:102 */     return Util.getJCRefType(toSignature());
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  86:    */   {
/*  87:114 */     if (type == Type.String)
/*  88:    */     {
/*  89:115 */       translateTo(classGen, methodGen, (StringType)type);
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:118 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/*  94:    */       
/*  95:120 */       classGen.getParser().reportError(2, err);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type)
/* 100:    */   {
/* 101:132 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 102:133 */     InstructionList il = methodGen.getInstructionList();
/* 103:    */     
/* 104:135 */     il.append(InstructionConstants.DUP);
/* 105:136 */     BranchHandle ifNull = il.append(new IFNULL(null));
/* 106:137 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref(this._javaClassName, "toString", "()Ljava/lang/String;")));
/* 107:    */     
/* 108:    */ 
/* 109:140 */     BranchHandle gotobh = il.append(new GOTO(null));
/* 110:141 */     ifNull.setTarget(il.append(InstructionConstants.POP));
/* 111:142 */     il.append(new PUSH(cpg, ""));
/* 112:143 */     gotobh.setTarget(il.append(InstructionConstants.NOP));
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 116:    */   {
/* 117:153 */     if (clazz.isAssignableFrom(this._clazz))
/* 118:    */     {
/* 119:154 */       methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 120:    */     }
/* 121:    */     else
/* 122:    */     {
/* 123:156 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getClass().toString());
/* 124:    */       
/* 125:158 */       classGen.getParser().reportError(2, err);
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 130:    */   {
/* 131:167 */     methodGen.getInstructionList().append(InstructionConstants.NOP);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Instruction LOAD(int slot)
/* 135:    */   {
/* 136:171 */     return new ALOAD(slot);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Instruction STORE(int slot)
/* 140:    */   {
/* 141:175 */     return new ASTORE(slot);
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.ObjectType
 * JD-Core Version:    0.7.0.1
 */