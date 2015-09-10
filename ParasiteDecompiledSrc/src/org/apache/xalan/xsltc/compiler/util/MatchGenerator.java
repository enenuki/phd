/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.ILOAD;
/*   6:    */ import org.apache.bcel.generic.ISTORE;
/*   7:    */ import org.apache.bcel.generic.Instruction;
/*   8:    */ import org.apache.bcel.generic.InstructionList;
/*   9:    */ import org.apache.bcel.generic.Type;
/*  10:    */ 
/*  11:    */ public final class MatchGenerator
/*  12:    */   extends MethodGenerator
/*  13:    */ {
/*  14: 37 */   private static int CURRENT_INDEX = 1;
/*  15: 39 */   private int _iteratorIndex = -1;
/*  16:    */   private final Instruction _iloadCurrent;
/*  17:    */   private final Instruction _istoreCurrent;
/*  18:    */   private Instruction _aloadDom;
/*  19:    */   
/*  20:    */   public MatchGenerator(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cp)
/*  21:    */   {
/*  22: 49 */     super(access_flags, return_type, arg_types, arg_names, method_name, class_name, il, cp);
/*  23:    */     
/*  24:    */ 
/*  25: 52 */     this._iloadCurrent = new ILOAD(CURRENT_INDEX);
/*  26: 53 */     this._istoreCurrent = new ISTORE(CURRENT_INDEX);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Instruction loadCurrentNode()
/*  30:    */   {
/*  31: 57 */     return this._iloadCurrent;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Instruction storeCurrentNode()
/*  35:    */   {
/*  36: 61 */     return this._istoreCurrent;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getHandlerIndex()
/*  40:    */   {
/*  41: 65 */     return -1;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Instruction loadDOM()
/*  45:    */   {
/*  46: 72 */     return this._aloadDom;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setDomIndex(int domIndex)
/*  50:    */   {
/*  51: 79 */     this._aloadDom = new ALOAD(domIndex);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getIteratorIndex()
/*  55:    */   {
/*  56: 86 */     return this._iteratorIndex;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIteratorIndex(int iteratorIndex)
/*  60:    */   {
/*  61: 93 */     this._iteratorIndex = iteratorIndex;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getLocalIndex(String name)
/*  65:    */   {
/*  66: 97 */     if (name.equals("current")) {
/*  67: 98 */       return CURRENT_INDEX;
/*  68:    */     }
/*  69:100 */     return super.getLocalIndex(name);
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.MatchGenerator
 * JD-Core Version:    0.7.0.1
 */