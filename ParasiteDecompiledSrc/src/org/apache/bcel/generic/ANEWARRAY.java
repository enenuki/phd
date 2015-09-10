/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class ANEWARRAY
/*   6:    */   extends CPInstruction
/*   7:    */   implements LoadClass, AllocationInstruction, ExceptionThrower, StackProducer
/*   8:    */ {
/*   9:    */   ANEWARRAY() {}
/*  10:    */   
/*  11:    */   public ANEWARRAY(int index)
/*  12:    */   {
/*  13: 74 */     super((short)189, index);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public Class[] getExceptions()
/*  17:    */   {
/*  18: 78 */     Class[] cs = new Class[1 + ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length];
/*  19:    */     
/*  20: 80 */     System.arraycopy(ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length);
/*  21:    */     
/*  22: 82 */     cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length] = ExceptionConstants.NEGATIVE_ARRAY_SIZE_EXCEPTION;
/*  23:    */     
/*  24: 84 */     return cs;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void accept(Visitor v)
/*  28:    */   {
/*  29: 96 */     v.visitLoadClass(this);
/*  30: 97 */     v.visitAllocationInstruction(this);
/*  31: 98 */     v.visitExceptionThrower(this);
/*  32: 99 */     v.visitStackProducer(this);
/*  33:100 */     v.visitTypedInstruction(this);
/*  34:101 */     v.visitCPInstruction(this);
/*  35:102 */     v.visitANEWARRAY(this);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ObjectType getLoadClassType(ConstantPoolGen cpg)
/*  39:    */   {
/*  40:106 */     Type t = getType(cpg);
/*  41:108 */     if ((t instanceof ArrayType)) {
/*  42:109 */       t = ((ArrayType)t).getBasicType();
/*  43:    */     }
/*  44:112 */     return (t instanceof ObjectType) ? (ObjectType)t : null;
/*  45:    */   }
/*  46:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ANEWARRAY
 * JD-Core Version:    0.7.0.1
 */