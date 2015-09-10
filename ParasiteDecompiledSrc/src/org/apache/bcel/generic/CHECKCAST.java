/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.ExceptionConstants;
/*   4:    */ 
/*   5:    */ public class CHECKCAST
/*   6:    */   extends CPInstruction
/*   7:    */   implements LoadClass, ExceptionThrower, StackProducer, StackConsumer
/*   8:    */ {
/*   9:    */   CHECKCAST() {}
/*  10:    */   
/*  11:    */   public CHECKCAST(int index)
/*  12:    */   {
/*  13: 76 */     super((short)192, index);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public Class[] getExceptions()
/*  17:    */   {
/*  18: 82 */     Class[] cs = new Class[1 + ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length];
/*  19:    */     
/*  20: 84 */     System.arraycopy(ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length);
/*  21:    */     
/*  22: 86 */     cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length] = ExceptionConstants.CLASS_CAST_EXCEPTION;
/*  23:    */     
/*  24: 88 */     return cs;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ObjectType getLoadClassType(ConstantPoolGen cpg)
/*  28:    */   {
/*  29: 92 */     Type t = getType(cpg);
/*  30: 94 */     if ((t instanceof ArrayType)) {
/*  31: 95 */       t = ((ArrayType)t).getBasicType();
/*  32:    */     }
/*  33: 97 */     return (t instanceof ObjectType) ? (ObjectType)t : null;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void accept(Visitor v)
/*  37:    */   {
/*  38:109 */     v.visitLoadClass(this);
/*  39:110 */     v.visitExceptionThrower(this);
/*  40:111 */     v.visitStackProducer(this);
/*  41:112 */     v.visitStackConsumer(this);
/*  42:113 */     v.visitTypedInstruction(this);
/*  43:114 */     v.visitCPInstruction(this);
/*  44:115 */     v.visitCHECKCAST(this);
/*  45:    */   }
/*  46:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.CHECKCAST
 * JD-Core Version:    0.7.0.1
 */