/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class NEW
/*   6:    */   extends CPInstruction
/*   7:    */   implements LoadClass, AllocationInstruction, ExceptionThrower, StackProducer
/*   8:    */ {
/*   9:    */   NEW() {}
/*  10:    */   
/*  11:    */   public NEW(int index)
/*  12:    */   {
/*  13: 75 */     super((short)187, index);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public Class[] getExceptions()
/*  17:    */   {
/*  18: 79 */     Class[] cs = new Class[2 + ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length];
/*  19:    */     
/*  20: 81 */     System.arraycopy(ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length);
/*  21:    */     
/*  22:    */ 
/*  23: 84 */     cs[(ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length + 1)] = ExceptionConstants.INSTANTIATION_ERROR;
/*  24: 85 */     cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length] = ExceptionConstants.ILLEGAL_ACCESS_ERROR;
/*  25:    */     
/*  26: 87 */     return cs;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ObjectType getLoadClassType(ConstantPoolGen cpg)
/*  30:    */   {
/*  31: 91 */     return (ObjectType)getType(cpg);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void accept(Visitor v)
/*  35:    */   {
/*  36:103 */     v.visitLoadClass(this);
/*  37:104 */     v.visitAllocationInstruction(this);
/*  38:105 */     v.visitExceptionThrower(this);
/*  39:106 */     v.visitStackProducer(this);
/*  40:107 */     v.visitTypedInstruction(this);
/*  41:108 */     v.visitCPInstruction(this);
/*  42:109 */     v.visitNEW(this);
/*  43:    */   }
/*  44:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.NEW
 * JD-Core Version:    0.7.0.1
 */