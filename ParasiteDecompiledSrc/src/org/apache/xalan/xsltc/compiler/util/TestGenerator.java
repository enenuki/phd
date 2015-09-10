/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ALOAD;
/*   4:    */ import org.apache.bcel.generic.ASTORE;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.ILOAD;
/*   7:    */ import org.apache.bcel.generic.ISTORE;
/*   8:    */ import org.apache.bcel.generic.Instruction;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.Type;
/*  11:    */ 
/*  12:    */ public final class TestGenerator
/*  13:    */   extends MethodGenerator
/*  14:    */ {
/*  15: 39 */   private static int CONTEXT_NODE_INDEX = 1;
/*  16: 40 */   private static int CURRENT_NODE_INDEX = 4;
/*  17: 41 */   private static int ITERATOR_INDEX = 6;
/*  18:    */   private Instruction _aloadDom;
/*  19:    */   private final Instruction _iloadCurrent;
/*  20:    */   private final Instruction _iloadContext;
/*  21:    */   private final Instruction _istoreCurrent;
/*  22:    */   private final Instruction _istoreContext;
/*  23:    */   private final Instruction _astoreIterator;
/*  24:    */   private final Instruction _aloadIterator;
/*  25:    */   
/*  26:    */   public TestGenerator(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cp)
/*  27:    */   {
/*  28: 55 */     super(access_flags, return_type, arg_types, arg_names, method_name, class_name, il, cp);
/*  29:    */     
/*  30:    */ 
/*  31: 58 */     this._iloadCurrent = new ILOAD(CURRENT_NODE_INDEX);
/*  32: 59 */     this._istoreCurrent = new ISTORE(CURRENT_NODE_INDEX);
/*  33: 60 */     this._iloadContext = new ILOAD(CONTEXT_NODE_INDEX);
/*  34: 61 */     this._istoreContext = new ILOAD(CONTEXT_NODE_INDEX);
/*  35: 62 */     this._astoreIterator = new ASTORE(ITERATOR_INDEX);
/*  36: 63 */     this._aloadIterator = new ALOAD(ITERATOR_INDEX);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getHandlerIndex()
/*  40:    */   {
/*  41: 67 */     return -1;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getIteratorIndex()
/*  45:    */   {
/*  46: 71 */     return ITERATOR_INDEX;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setDomIndex(int domIndex)
/*  50:    */   {
/*  51: 75 */     this._aloadDom = new ALOAD(domIndex);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Instruction loadDOM()
/*  55:    */   {
/*  56: 79 */     return this._aloadDom;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Instruction loadCurrentNode()
/*  60:    */   {
/*  61: 83 */     return this._iloadCurrent;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Instruction loadContextNode()
/*  65:    */   {
/*  66: 88 */     return this._iloadContext;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Instruction storeContextNode()
/*  70:    */   {
/*  71: 92 */     return this._istoreContext;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Instruction storeCurrentNode()
/*  75:    */   {
/*  76: 96 */     return this._istoreCurrent;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Instruction storeIterator()
/*  80:    */   {
/*  81:100 */     return this._astoreIterator;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Instruction loadIterator()
/*  85:    */   {
/*  86:104 */     return this._aloadIterator;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getLocalIndex(String name)
/*  90:    */   {
/*  91:108 */     if (name.equals("current")) {
/*  92:109 */       return CURRENT_NODE_INDEX;
/*  93:    */     }
/*  94:112 */     return super.getLocalIndex(name);
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.TestGenerator
 * JD-Core Version:    0.7.0.1
 */