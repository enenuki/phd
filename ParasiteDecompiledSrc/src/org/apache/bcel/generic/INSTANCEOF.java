/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class INSTANCEOF
/*   6:    */   extends CPInstruction
/*   7:    */   implements LoadClass, ExceptionThrower, StackProducer, StackConsumer
/*   8:    */ {
/*   9:    */   INSTANCEOF() {}
/*  10:    */   
/*  11:    */   public INSTANCEOF(int index)
/*  12:    */   {
/*  13: 73 */     super((short)193, index);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public Class[] getExceptions()
/*  17:    */   {
/*  18: 77 */     return ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public ObjectType getLoadClassType(ConstantPoolGen cpg)
/*  22:    */   {
/*  23: 81 */     Type t = getType(cpg);
/*  24: 83 */     if ((t instanceof ArrayType)) {
/*  25: 84 */       t = ((ArrayType)t).getBasicType();
/*  26:    */     }
/*  27: 86 */     return (t instanceof ObjectType) ? (ObjectType)t : null;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void accept(Visitor v)
/*  31:    */   {
/*  32: 98 */     v.visitLoadClass(this);
/*  33: 99 */     v.visitExceptionThrower(this);
/*  34:100 */     v.visitStackProducer(this);
/*  35:101 */     v.visitStackConsumer(this);
/*  36:102 */     v.visitTypedInstruction(this);
/*  37:103 */     v.visitCPInstruction(this);
/*  38:104 */     v.visitINSTANCEOF(this);
/*  39:    */   }
/*  40:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.INSTANCEOF
 * JD-Core Version:    0.7.0.1
 */