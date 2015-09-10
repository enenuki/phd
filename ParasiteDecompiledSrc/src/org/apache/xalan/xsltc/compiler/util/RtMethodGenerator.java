/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ALOAD;
/*  4:   */ import org.apache.bcel.generic.ASTORE;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.Instruction;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.Type;
/*  9:   */ 
/* 10:   */ public final class RtMethodGenerator
/* 11:   */   extends MethodGenerator
/* 12:   */ {
/* 13:   */   private static final int HANDLER_INDEX = 2;
/* 14:   */   private final Instruction _astoreHandler;
/* 15:   */   private final Instruction _aloadHandler;
/* 16:   */   
/* 17:   */   public RtMethodGenerator(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cp)
/* 18:   */   {
/* 19:46 */     super(access_flags, return_type, arg_types, arg_names, method_name, class_name, il, cp);
/* 20:   */     
/* 21:   */ 
/* 22:49 */     this._astoreHandler = new ASTORE(2);
/* 23:50 */     this._aloadHandler = new ALOAD(2);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getIteratorIndex()
/* 27:   */   {
/* 28:54 */     return -1;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public final Instruction storeHandler()
/* 32:   */   {
/* 33:58 */     return this._astoreHandler;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public final Instruction loadHandler()
/* 37:   */   {
/* 38:62 */     return this._aloadHandler;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int getLocalIndex(String name)
/* 42:   */   {
/* 43:66 */     return -1;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.RtMethodGenerator
 * JD-Core Version:    0.7.0.1
 */