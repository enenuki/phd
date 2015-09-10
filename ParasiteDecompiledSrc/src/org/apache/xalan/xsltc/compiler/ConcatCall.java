/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.ClassGen;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  7:   */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  8:   */ import org.apache.bcel.generic.Instruction;
/*  9:   */ import org.apache.bcel.generic.InstructionConstants;
/* 10:   */ import org.apache.bcel.generic.InstructionList;
/* 11:   */ import org.apache.bcel.generic.MethodGen;
/* 12:   */ import org.apache.bcel.generic.NEW;
/* 13:   */ import org.apache.bcel.generic.PUSH;
/* 14:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 15:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 16:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 17:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 18:   */ 
/* 19:   */ final class ConcatCall
/* 20:   */   extends FunctionCall
/* 21:   */ {
/* 22:   */   public ConcatCall(QName fname, Vector arguments)
/* 23:   */   {
/* 24:44 */     super(fname, arguments);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Type typeCheck(SymbolTable stable)
/* 28:   */     throws TypeCheckError
/* 29:   */   {
/* 30:48 */     for (int i = 0; i < argumentCount(); i++)
/* 31:   */     {
/* 32:49 */       Expression exp = argument(i);
/* 33:50 */       if (!exp.typeCheck(stable).identicalTo(Type.String)) {
/* 34:51 */         setArgument(i, new CastExpr(exp, Type.String));
/* 35:   */       }
/* 36:   */     }
/* 37:54 */     return this._type = Type.String;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 41:   */   {
/* 42:59 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 43:60 */     InstructionList il = methodGen.getInstructionList();
/* 44:61 */     int nArgs = argumentCount();
/* 45:63 */     switch (nArgs)
/* 46:   */     {
/* 47:   */     case 0: 
/* 48:65 */       il.append(new PUSH(cpg, ""));
/* 49:66 */       break;
/* 50:   */     case 1: 
/* 51:69 */       argument().translate(classGen, methodGen);
/* 52:70 */       break;
/* 53:   */     default: 
/* 54:73 */       int initBuffer = cpg.addMethodref("java.lang.StringBuffer", "<init>", "()V");
/* 55:   */       
/* 56:75 */       Instruction append = new INVOKEVIRTUAL(cpg.addMethodref("java.lang.StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;"));
/* 57:   */       
/* 58:   */ 
/* 59:   */ 
/* 60:   */ 
/* 61:   */ 
/* 62:81 */       int toString = cpg.addMethodref("java.lang.StringBuffer", "toString", "()Ljava/lang/String;");
/* 63:   */       
/* 64:   */ 
/* 65:   */ 
/* 66:85 */       il.append(new NEW(cpg.addClass("java.lang.StringBuffer")));
/* 67:86 */       il.append(InstructionConstants.DUP);
/* 68:87 */       il.append(new INVOKESPECIAL(initBuffer));
/* 69:88 */       for (int i = 0; i < nArgs; i++)
/* 70:   */       {
/* 71:89 */         argument(i).translate(classGen, methodGen);
/* 72:90 */         il.append(append);
/* 73:   */       }
/* 74:92 */       il.append(new INVOKEVIRTUAL(toString));
/* 75:   */     }
/* 76:   */   }
/* 77:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ConcatCall
 * JD-Core Version:    0.7.0.1
 */