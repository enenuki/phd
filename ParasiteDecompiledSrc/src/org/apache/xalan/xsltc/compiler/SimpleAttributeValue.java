/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ClassGen;
/*  4:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  5:   */ import org.apache.bcel.generic.InstructionList;
/*  6:   */ import org.apache.bcel.generic.MethodGen;
/*  7:   */ import org.apache.bcel.generic.PUSH;
/*  8:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 12:   */ 
/* 13:   */ final class SimpleAttributeValue
/* 14:   */   extends AttributeValue
/* 15:   */ {
/* 16:   */   private String _value;
/* 17:   */   
/* 18:   */   public SimpleAttributeValue(String value)
/* 19:   */   {
/* 20:45 */     this._value = value;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Type typeCheck(SymbolTable stable)
/* 24:   */     throws TypeCheckError
/* 25:   */   {
/* 26:53 */     return this._type = Type.String;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String toString()
/* 30:   */   {
/* 31:57 */     return this._value;
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected boolean contextDependent()
/* 35:   */   {
/* 36:61 */     return false;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 40:   */   {
/* 41:71 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 42:72 */     InstructionList il = methodGen.getInstructionList();
/* 43:73 */     il.append(new PUSH(cpg, this._value));
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.SimpleAttributeValue
 * JD-Core Version:    0.7.0.1
 */