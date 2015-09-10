/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ALOAD;
/*  4:   */ import org.apache.bcel.generic.ASTORE;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.Instruction;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.Type;
/*  9:   */ 
/* 10:   */ public final class NamedMethodGenerator
/* 11:   */   extends MethodGenerator
/* 12:   */ {
/* 13:   */   protected static final int CURRENT_INDEX = 4;
/* 14:   */   private static final int PARAM_START_INDEX = 5;
/* 15:   */   
/* 16:   */   public NamedMethodGenerator(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cp)
/* 17:   */   {
/* 18:47 */     super(access_flags, return_type, arg_types, arg_names, method_name, class_name, il, cp);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getLocalIndex(String name)
/* 22:   */   {
/* 23:52 */     if (name.equals("current")) {
/* 24:53 */       return 4;
/* 25:   */     }
/* 26:55 */     return super.getLocalIndex(name);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Instruction loadParameter(int index)
/* 30:   */   {
/* 31:59 */     return new ALOAD(index + 5);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Instruction storeParameter(int index)
/* 35:   */   {
/* 36:63 */     return new ASTORE(index + 5);
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.NamedMethodGenerator
 * JD-Core Version:    0.7.0.1
 */