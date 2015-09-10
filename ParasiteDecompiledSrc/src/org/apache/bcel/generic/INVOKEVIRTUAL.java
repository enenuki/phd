/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class INVOKEVIRTUAL
/*   6:    */   extends InvokeInstruction
/*   7:    */ {
/*   8:    */   INVOKEVIRTUAL() {}
/*   9:    */   
/*  10:    */   public INVOKEVIRTUAL(int index)
/*  11:    */   {
/*  12: 75 */     super((short)182, index);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public Class[] getExceptions()
/*  16:    */   {
/*  17: 79 */     Class[] cs = new Class[4 + ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length];
/*  18:    */     
/*  19: 81 */     System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length);
/*  20:    */     
/*  21:    */ 
/*  22: 84 */     cs[(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 3)] = ExceptionConstants.UNSATISFIED_LINK_ERROR;
/*  23: 85 */     cs[(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 2)] = ExceptionConstants.ABSTRACT_METHOD_ERROR;
/*  24: 86 */     cs[(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 1)] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
/*  25: 87 */     cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.NULL_POINTER_EXCEPTION;
/*  26:    */     
/*  27: 89 */     return cs;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void accept(Visitor v)
/*  31:    */   {
/*  32:102 */     v.visitExceptionThrower(this);
/*  33:103 */     v.visitTypedInstruction(this);
/*  34:104 */     v.visitStackConsumer(this);
/*  35:105 */     v.visitStackProducer(this);
/*  36:106 */     v.visitLoadClass(this);
/*  37:107 */     v.visitCPInstruction(this);
/*  38:108 */     v.visitFieldOrMethod(this);
/*  39:109 */     v.visitInvokeInstruction(this);
/*  40:110 */     v.visitINVOKEVIRTUAL(this);
/*  41:    */   }
/*  42:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.INVOKEVIRTUAL
 * JD-Core Version:    0.7.0.1
 */