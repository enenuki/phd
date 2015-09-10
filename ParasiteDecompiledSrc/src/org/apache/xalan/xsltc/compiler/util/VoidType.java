/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ClassGen;
/*  4:   */ import org.apache.bcel.generic.Instruction;
/*  5:   */ import org.apache.bcel.generic.InstructionConstants;
/*  6:   */ import org.apache.bcel.generic.InstructionList;
/*  7:   */ import org.apache.bcel.generic.MethodGen;
/*  8:   */ import org.apache.bcel.generic.PUSH;
/*  9:   */ import org.apache.xalan.xsltc.compiler.Parser;
/* 10:   */ 
/* 11:   */ public final class VoidType
/* 12:   */   extends Type
/* 13:   */ {
/* 14:   */   public String toString()
/* 15:   */   {
/* 16:37 */     return "void";
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean identicalTo(Type other)
/* 20:   */   {
/* 21:41 */     return this == other;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String toSignature()
/* 25:   */   {
/* 26:45 */     return "V";
/* 27:   */   }
/* 28:   */   
/* 29:   */   public org.apache.bcel.generic.Type toJCType()
/* 30:   */   {
/* 31:49 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Instruction POP()
/* 35:   */   {
/* 36:53 */     return InstructionConstants.NOP;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/* 40:   */   {
/* 41:65 */     if (type == Type.String)
/* 42:   */     {
/* 43:66 */       translateTo(classGen, methodGen, (StringType)type);
/* 44:   */     }
/* 45:   */     else
/* 46:   */     {
/* 47:69 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/* 48:   */       
/* 49:71 */       classGen.getParser().reportError(2, err);
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type)
/* 54:   */   {
/* 55:82 */     InstructionList il = methodGen.getInstructionList();
/* 56:83 */     il.append(new PUSH(classGen.getConstantPool(), ""));
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 60:   */   {
/* 61:92 */     if (!clazz.getName().equals("void"))
/* 62:   */     {
/* 63:93 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getName());
/* 64:   */       
/* 65:95 */       classGen.getParser().reportError(2, err);
/* 66:   */     }
/* 67:   */   }
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.VoidType
 * JD-Core Version:    0.7.0.1
 */