/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ACONST_NULL;
/*   4:    */ import org.apache.bcel.generic.ALOAD;
/*   5:    */ import org.apache.bcel.generic.ASTORE;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.ILOAD;
/*   8:    */ import org.apache.bcel.generic.ISTORE;
/*   9:    */ import org.apache.bcel.generic.Instruction;
/*  10:    */ import org.apache.bcel.generic.InstructionList;
/*  11:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  12:    */ import org.apache.bcel.generic.Type;
/*  13:    */ 
/*  14:    */ public final class CompareGenerator
/*  15:    */   extends MethodGenerator
/*  16:    */ {
/*  17: 42 */   private static int DOM_INDEX = 1;
/*  18: 43 */   private static int CURRENT_INDEX = 2;
/*  19: 44 */   private static int LEVEL_INDEX = 3;
/*  20: 45 */   private static int TRANSLET_INDEX = 4;
/*  21: 46 */   private static int LAST_INDEX = 5;
/*  22: 47 */   private int ITERATOR_INDEX = 6;
/*  23:    */   private final Instruction _iloadCurrent;
/*  24:    */   private final Instruction _istoreCurrent;
/*  25:    */   private final Instruction _aloadDom;
/*  26:    */   private final Instruction _iloadLast;
/*  27:    */   private final Instruction _aloadIterator;
/*  28:    */   private final Instruction _astoreIterator;
/*  29:    */   
/*  30:    */   public CompareGenerator(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cp)
/*  31:    */   {
/*  32: 60 */     super(access_flags, return_type, arg_types, arg_names, method_name, class_name, il, cp);
/*  33:    */     
/*  34:    */ 
/*  35: 63 */     this._iloadCurrent = new ILOAD(CURRENT_INDEX);
/*  36: 64 */     this._istoreCurrent = new ISTORE(CURRENT_INDEX);
/*  37: 65 */     this._aloadDom = new ALOAD(DOM_INDEX);
/*  38: 66 */     this._iloadLast = new ILOAD(LAST_INDEX);
/*  39:    */     
/*  40: 68 */     LocalVariableGen iterator = addLocalVariable("iterator", Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;"), null, null);
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44: 72 */     this.ITERATOR_INDEX = iterator.getIndex();
/*  45: 73 */     this._aloadIterator = new ALOAD(this.ITERATOR_INDEX);
/*  46: 74 */     this._astoreIterator = new ASTORE(this.ITERATOR_INDEX);
/*  47: 75 */     il.append(new ACONST_NULL());
/*  48: 76 */     il.append(storeIterator());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Instruction loadLastNode()
/*  52:    */   {
/*  53: 80 */     return this._iloadLast;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Instruction loadCurrentNode()
/*  57:    */   {
/*  58: 84 */     return this._iloadCurrent;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Instruction storeCurrentNode()
/*  62:    */   {
/*  63: 88 */     return this._istoreCurrent;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Instruction loadDOM()
/*  67:    */   {
/*  68: 92 */     return this._aloadDom;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getHandlerIndex()
/*  72:    */   {
/*  73: 96 */     return -1;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIteratorIndex()
/*  77:    */   {
/*  78:100 */     return -1;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Instruction storeIterator()
/*  82:    */   {
/*  83:104 */     return this._astoreIterator;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Instruction loadIterator()
/*  87:    */   {
/*  88:108 */     return this._aloadIterator;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getLocalIndex(String name)
/*  92:    */   {
/*  93:113 */     if (name.equals("current")) {
/*  94:114 */       return CURRENT_INDEX;
/*  95:    */     }
/*  96:116 */     return super.getLocalIndex(name);
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.CompareGenerator
 * JD-Core Version:    0.7.0.1
 */