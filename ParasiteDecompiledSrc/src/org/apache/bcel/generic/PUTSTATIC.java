/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class PUTSTATIC
/*   6:    */   extends FieldInstruction
/*   7:    */   implements ExceptionThrower, PopInstruction
/*   8:    */ {
/*   9:    */   PUTSTATIC() {}
/*  10:    */   
/*  11:    */   public PUTSTATIC(int index)
/*  12:    */   {
/*  13: 78 */     super((short)179, index);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public int consumeStack(ConstantPoolGen cpg)
/*  17:    */   {
/*  18: 81 */     return getFieldSize(cpg);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Class[] getExceptions()
/*  22:    */   {
/*  23: 84 */     Class[] cs = new Class[1 + ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length];
/*  24:    */     
/*  25: 86 */     System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length);
/*  26:    */     
/*  27: 88 */     cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
/*  28:    */     
/*  29:    */ 
/*  30: 91 */     return cs;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void accept(Visitor v)
/*  34:    */   {
/*  35:104 */     v.visitExceptionThrower(this);
/*  36:105 */     v.visitStackConsumer(this);
/*  37:106 */     v.visitPopInstruction(this);
/*  38:107 */     v.visitTypedInstruction(this);
/*  39:108 */     v.visitLoadClass(this);
/*  40:109 */     v.visitCPInstruction(this);
/*  41:110 */     v.visitFieldOrMethod(this);
/*  42:111 */     v.visitFieldInstruction(this);
/*  43:112 */     v.visitPUTSTATIC(this);
/*  44:    */   }
/*  45:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.PUTSTATIC
 * JD-Core Version:    0.7.0.1
 */