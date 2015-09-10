/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class INVOKESPECIAL
/*   6:    */   extends InvokeInstruction
/*   7:    */ {
/*   8:    */   INVOKESPECIAL() {}
/*   9:    */   
/*  10:    */   public INVOKESPECIAL(int index)
/*  11:    */   {
/*  12: 76 */     super((short)183, index);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public Class[] getExceptions()
/*  16:    */   {
/*  17: 80 */     Class[] cs = new Class[4 + ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length];
/*  18:    */     
/*  19: 82 */     System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length);
/*  20:    */     
/*  21:    */ 
/*  22: 85 */     cs[(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 3)] = ExceptionConstants.UNSATISFIED_LINK_ERROR;
/*  23: 86 */     cs[(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 2)] = ExceptionConstants.ABSTRACT_METHOD_ERROR;
/*  24: 87 */     cs[(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 1)] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
/*  25: 88 */     cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.NULL_POINTER_EXCEPTION;
/*  26:    */     
/*  27: 90 */     return cs;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void accept(Visitor v)
/*  31:    */   {
/*  32:103 */     v.visitExceptionThrower(this);
/*  33:104 */     v.visitTypedInstruction(this);
/*  34:105 */     v.visitStackConsumer(this);
/*  35:106 */     v.visitStackProducer(this);
/*  36:107 */     v.visitLoadClass(this);
/*  37:108 */     v.visitCPInstruction(this);
/*  38:109 */     v.visitFieldOrMethod(this);
/*  39:110 */     v.visitInvokeInstruction(this);
/*  40:111 */     v.visitINVOKESPECIAL(this);
/*  41:    */   }
/*  42:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.INVOKESPECIAL
 * JD-Core Version:    0.7.0.1
 */