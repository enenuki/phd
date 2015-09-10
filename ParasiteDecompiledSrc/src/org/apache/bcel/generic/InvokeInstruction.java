/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import org.apache.bcel.classfile.Constant;
/*   5:    */ import org.apache.bcel.classfile.ConstantPool;
/*   6:    */ 
/*   7:    */ public abstract class InvokeInstruction
/*   8:    */   extends FieldOrMethod
/*   9:    */   implements ExceptionThrower, TypedInstruction, StackConsumer, StackProducer
/*  10:    */ {
/*  11:    */   InvokeInstruction() {}
/*  12:    */   
/*  13:    */   protected InvokeInstruction(short opcode, int index)
/*  14:    */   {
/*  15: 78 */     super(opcode, index);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public String toString(ConstantPool cp)
/*  19:    */   {
/*  20: 85 */     Constant c = cp.getConstant(this.index);
/*  21: 86 */     StringTokenizer tok = new StringTokenizer(cp.constantToString(c));
/*  22:    */     
/*  23: 88 */     return org.apache.bcel.Constants.OPCODE_NAMES[this.opcode] + " " + tok.nextToken().replace('.', '/') + tok.nextToken();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int consumeStack(ConstantPoolGen cpg)
/*  27:    */   {
/*  28: 98 */     String signature = getSignature(cpg);
/*  29: 99 */     Type[] args = Type.getArgumentTypes(signature);
/*  30:    */     int sum;
/*  31:102 */     if (this.opcode == 184) {
/*  32:103 */       sum = 0;
/*  33:    */     } else {
/*  34:105 */       sum = 1;
/*  35:    */     }
/*  36:107 */     int n = args.length;
/*  37:108 */     for (int i = 0; i < n; i++) {
/*  38:109 */       sum += args[i].getSize();
/*  39:    */     }
/*  40:111 */     return sum;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int produceStack(ConstantPoolGen cpg)
/*  44:    */   {
/*  45:120 */     return getReturnType(cpg).getSize();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Type getType(ConstantPoolGen cpg)
/*  49:    */   {
/*  50:126 */     return getReturnType(cpg);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getMethodName(ConstantPoolGen cpg)
/*  54:    */   {
/*  55:132 */     return getName(cpg);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Type getReturnType(ConstantPoolGen cpg)
/*  59:    */   {
/*  60:138 */     return Type.getReturnType(getSignature(cpg));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Type[] getArgumentTypes(ConstantPoolGen cpg)
/*  64:    */   {
/*  65:144 */     return Type.getArgumentTypes(getSignature(cpg));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public abstract Class[] getExceptions();
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.InvokeInstruction
 * JD-Core Version:    0.7.0.1
 */