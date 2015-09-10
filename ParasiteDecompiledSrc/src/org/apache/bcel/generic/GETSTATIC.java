/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class GETSTATIC
/*   6:    */   extends FieldInstruction
/*   7:    */   implements PushInstruction, ExceptionThrower
/*   8:    */ {
/*   9:    */   GETSTATIC() {}
/*  10:    */   
/*  11:    */   public GETSTATIC(int index)
/*  12:    */   {
/*  13: 77 */     super((short)178, index);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public int produceStack(ConstantPoolGen cpg)
/*  17:    */   {
/*  18: 80 */     return getFieldSize(cpg);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Class[] getExceptions()
/*  22:    */   {
/*  23: 83 */     Class[] cs = new Class[1 + ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length];
/*  24:    */     
/*  25: 85 */     System.arraycopy(ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length);
/*  26:    */     
/*  27: 87 */     cs[ExceptionConstants.EXCS_FIELD_AND_METHOD_RESOLUTION.length] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
/*  28:    */     
/*  29:    */ 
/*  30: 90 */     return cs;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void accept(Visitor v)
/*  34:    */   {
/*  35:103 */     v.visitStackProducer(this);
/*  36:104 */     v.visitPushInstruction(this);
/*  37:105 */     v.visitExceptionThrower(this);
/*  38:106 */     v.visitTypedInstruction(this);
/*  39:107 */     v.visitLoadClass(this);
/*  40:108 */     v.visitCPInstruction(this);
/*  41:109 */     v.visitFieldOrMethod(this);
/*  42:110 */     v.visitFieldInstruction(this);
/*  43:111 */     v.visitGETSTATIC(this);
/*  44:    */   }
/*  45:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.GETSTATIC
 * JD-Core Version:    0.7.0.1
 */