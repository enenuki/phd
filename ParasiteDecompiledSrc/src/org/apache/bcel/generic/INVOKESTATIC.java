/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class INVOKESTATIC
/*   6:    */   extends InvokeInstruction
/*   7:    */ {
/*   8:    */   INVOKESTATIC() {}
/*   9:    */   
/*  10:    */   public INVOKESTATIC(int index)
/*  11:    */   {
/*  12: 75 */     super((short)184, index);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public Class[] getExceptions()
/*  16:    */   {
/*  17: 79 */     Class[] cs = new Class[2 + ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length];
/*  18:    */     
/*  19: 81 */     System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length);
/*  20:    */     
/*  21:    */ 
/*  22: 84 */     cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.UNSATISFIED_LINK_ERROR;
/*  23: 85 */     cs[(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 1)] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
/*  24:    */     
/*  25: 87 */     return cs;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void accept(Visitor v)
/*  29:    */   {
/*  30:100 */     v.visitExceptionThrower(this);
/*  31:101 */     v.visitTypedInstruction(this);
/*  32:102 */     v.visitStackConsumer(this);
/*  33:103 */     v.visitStackProducer(this);
/*  34:104 */     v.visitLoadClass(this);
/*  35:105 */     v.visitCPInstruction(this);
/*  36:106 */     v.visitFieldOrMethod(this);
/*  37:107 */     v.visitInvokeInstruction(this);
/*  38:108 */     v.visitINVOKESTATIC(this);
/*  39:    */   }
/*  40:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.INVOKESTATIC
 * JD-Core Version:    0.7.0.1
 */