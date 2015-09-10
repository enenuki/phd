/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ALOAD;
/*  4:   */ import org.apache.bcel.generic.ASTORE;
/*  5:   */ import org.apache.bcel.generic.ClassGen;
/*  6:   */ import org.apache.bcel.generic.Instruction;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.Type;
/*  9:   */ import org.apache.xalan.xsltc.compiler.Constants;
/* 10:   */ 
/* 11:   */ public final class AttributeSetMethodGenerator
/* 12:   */   extends MethodGenerator
/* 13:   */ {
/* 14:   */   private static final int DOM_INDEX = 1;
/* 15:   */   private static final int ITERATOR_INDEX = 2;
/* 16:   */   private static final int HANDLER_INDEX = 3;
/* 17:39 */   private static final Type[] argTypes = new Type[3];
/* 18:41 */   private static final String[] argNames = new String[3];
/* 19:   */   private final Instruction _aloadDom;
/* 20:   */   private final Instruction _astoreDom;
/* 21:   */   private final Instruction _astoreIterator;
/* 22:   */   private final Instruction _aloadIterator;
/* 23:   */   private final Instruction _astoreHandler;
/* 24:   */   private final Instruction _aloadHandler;
/* 25:   */   
/* 26:   */   static
/* 27:   */   {
/* 28:44 */     argTypes[0] = Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;");
/* 29:45 */     argNames[0] = "dom";
/* 30:46 */     argTypes[1] = Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 31:47 */     argNames[1] = "iterator";
/* 32:48 */     argTypes[2] = Util.getJCRefType(Constants.TRANSLET_OUTPUT_SIG);
/* 33:49 */     argNames[2] = "handler";
/* 34:   */   }
/* 35:   */   
/* 36:   */   public AttributeSetMethodGenerator(String methodName, ClassGen classGen)
/* 37:   */   {
/* 38:61 */     super(2, Type.VOID, argTypes, argNames, methodName, classGen.getClassName(), new InstructionList(), classGen.getConstantPool());
/* 39:   */     
/* 40:   */ 
/* 41:   */ 
/* 42:   */ 
/* 43:   */ 
/* 44:   */ 
/* 45:68 */     this._aloadDom = new ALOAD(1);
/* 46:69 */     this._astoreDom = new ASTORE(1);
/* 47:70 */     this._astoreIterator = new ASTORE(2);
/* 48:71 */     this._aloadIterator = new ALOAD(2);
/* 49:72 */     this._astoreHandler = new ASTORE(3);
/* 50:73 */     this._aloadHandler = new ALOAD(3);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public Instruction storeIterator()
/* 54:   */   {
/* 55:77 */     return this._astoreIterator;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public Instruction loadIterator()
/* 59:   */   {
/* 60:81 */     return this._aloadIterator;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public int getIteratorIndex()
/* 64:   */   {
/* 65:85 */     return 2;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public Instruction storeHandler()
/* 69:   */   {
/* 70:89 */     return this._astoreHandler;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public Instruction loadHandler()
/* 74:   */   {
/* 75:93 */     return this._aloadHandler;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public int getLocalIndex(String name)
/* 79:   */   {
/* 80:97 */     return -1;
/* 81:   */   }
/* 82:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.AttributeSetMethodGenerator
 * JD-Core Version:    0.7.0.1
 */