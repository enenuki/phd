/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.ExceptionConstants;
/*   7:    */ import org.apache.bcel.classfile.ConstantPool;
/*   8:    */ import org.apache.bcel.util.ByteSequence;
/*   9:    */ 
/*  10:    */ public final class INVOKEINTERFACE
/*  11:    */   extends InvokeInstruction
/*  12:    */ {
/*  13:    */   private int nargs;
/*  14:    */   
/*  15:    */   INVOKEINTERFACE() {}
/*  16:    */   
/*  17:    */   public INVOKEINTERFACE(int index, int nargs)
/*  18:    */   {
/*  19: 80 */     super((short)185, index);
/*  20: 81 */     this.length = 5;
/*  21: 83 */     if (nargs < 1) {
/*  22: 84 */       throw new ClassGenException("Number of arguments must be > 0 " + nargs);
/*  23:    */     }
/*  24: 86 */     this.nargs = nargs;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void dump(DataOutputStream out)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 94 */     out.writeByte(this.opcode);
/*  31: 95 */     out.writeShort(this.index);
/*  32: 96 */     out.writeByte(this.nargs);
/*  33: 97 */     out.writeByte(0);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getCount()
/*  37:    */   {
/*  38:104 */     return this.nargs;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:112 */     super.initFromFile(bytes, wide);
/*  45:    */     
/*  46:114 */     this.length = 5;
/*  47:115 */     this.nargs = bytes.readUnsignedByte();
/*  48:116 */     bytes.readByte();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString(ConstantPool cp)
/*  52:    */   {
/*  53:123 */     return super.toString(cp) + " " + this.nargs;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int consumeStack(ConstantPoolGen cpg)
/*  57:    */   {
/*  58:127 */     return this.nargs;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Class[] getExceptions()
/*  62:    */   {
/*  63:131 */     Class[] cs = new Class[4 + ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length];
/*  64:    */     
/*  65:133 */     System.arraycopy(ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length);
/*  66:    */     
/*  67:    */ 
/*  68:136 */     cs[(ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length + 3)] = ExceptionConstants.INCOMPATIBLE_CLASS_CHANGE_ERROR;
/*  69:137 */     cs[(ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length + 2)] = ExceptionConstants.ILLEGAL_ACCESS_ERROR;
/*  70:138 */     cs[(ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length + 1)] = ExceptionConstants.ABSTRACT_METHOD_ERROR;
/*  71:139 */     cs[ExceptionConstants.EXCS_INTERFACE_METHOD_RESOLUTION.length] = ExceptionConstants.UNSATISFIED_LINK_ERROR;
/*  72:    */     
/*  73:141 */     return cs;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void accept(Visitor v)
/*  77:    */   {
/*  78:153 */     v.visitExceptionThrower(this);
/*  79:154 */     v.visitTypedInstruction(this);
/*  80:155 */     v.visitStackConsumer(this);
/*  81:156 */     v.visitStackProducer(this);
/*  82:157 */     v.visitLoadClass(this);
/*  83:158 */     v.visitCPInstruction(this);
/*  84:159 */     v.visitFieldOrMethod(this);
/*  85:160 */     v.visitInvokeInstruction(this);
/*  86:161 */     v.visitINVOKEINTERFACE(this);
/*  87:    */   }
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.INVOKEINTERFACE
 * JD-Core Version:    0.7.0.1
 */