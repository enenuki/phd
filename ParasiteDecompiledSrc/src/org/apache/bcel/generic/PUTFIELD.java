/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class PUTFIELD
/*   6:    */   extends FieldInstruction
/*   7:    */   implements PopInstruction, ExceptionThrower
/*   8:    */ {
/*   9:    */   PUTFIELD() {}
/*  10:    */   
/*  11:    */   public PUTFIELD(int index)
/*  12:    */   {
/*  13: 79 */     super((short)181, index);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public int consumeStack(ConstantPoolGen cpg)
/*  17:    */   {
/*  18: 82 */     return getFieldSize(cpg) + 1;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Class[] getExceptions()
/*  22:    */   {
/*  23: 85 */     Class[] cs = new Class[2 + ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length];
/*  24:    */     
/*  25: 87 */     System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length);
/*  26:    */     
/*  27:    */ 
/*  28: 90 */     cs[(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 1)] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
/*  29:    */     
/*  30: 92 */     cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.NULL_POINTER_EXCEPTION;
/*  31:    */     
/*  32:    */ 
/*  33: 95 */     return cs;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void accept(Visitor v)
/*  37:    */   {
/*  38:108 */     v.visitExceptionThrower(this);
/*  39:109 */     v.visitStackConsumer(this);
/*  40:110 */     v.visitPopInstruction(this);
/*  41:111 */     v.visitTypedInstruction(this);
/*  42:112 */     v.visitLoadClass(this);
/*  43:113 */     v.visitCPInstruction(this);
/*  44:114 */     v.visitFieldOrMethod(this);
/*  45:115 */     v.visitFieldInstruction(this);
/*  46:116 */     v.visitPUTFIELD(this);
/*  47:    */   }
/*  48:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.PUTFIELD
 * JD-Core Version:    0.7.0.1
 */