/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class GETFIELD
/*   6:    */   extends FieldInstruction
/*   7:    */   implements ExceptionThrower, StackConsumer, StackProducer
/*   8:    */ {
/*   9:    */   GETFIELD() {}
/*  10:    */   
/*  11:    */   public GETFIELD(int index)
/*  12:    */   {
/*  13: 78 */     super((short)180, index);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public int produceStack(ConstantPoolGen cpg)
/*  17:    */   {
/*  18: 81 */     return getFieldSize(cpg);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Class[] getExceptions()
/*  22:    */   {
/*  23: 84 */     Class[] cs = new Class[2 + ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length];
/*  24:    */     
/*  25: 86 */     System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length);
/*  26:    */     
/*  27:    */ 
/*  28: 89 */     cs[(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length + 1)] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
/*  29:    */     
/*  30: 91 */     cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.NULL_POINTER_EXCEPTION;
/*  31:    */     
/*  32:    */ 
/*  33: 94 */     return cs;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void accept(Visitor v)
/*  37:    */   {
/*  38:107 */     v.visitExceptionThrower(this);
/*  39:108 */     v.visitStackConsumer(this);
/*  40:109 */     v.visitStackProducer(this);
/*  41:110 */     v.visitTypedInstruction(this);
/*  42:111 */     v.visitLoadClass(this);
/*  43:112 */     v.visitCPInstruction(this);
/*  44:113 */     v.visitFieldOrMethod(this);
/*  45:114 */     v.visitFieldInstruction(this);
/*  46:115 */     v.visitGETFIELD(this);
/*  47:    */   }
/*  48:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.GETFIELD
 * JD-Core Version:    0.7.0.1
 */