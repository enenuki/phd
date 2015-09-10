/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.ExceptionConstants;
/*   7:    */ import org.apache.bcel.classfile.ConstantPool;
/*   8:    */ import org.apache.bcel.util.ByteSequence;
/*   9:    */ 
/*  10:    */ public class MULTIANEWARRAY
/*  11:    */   extends CPInstruction
/*  12:    */   implements LoadClass, AllocationInstruction, ExceptionThrower
/*  13:    */ {
/*  14:    */   private short dimensions;
/*  15:    */   
/*  16:    */   MULTIANEWARRAY() {}
/*  17:    */   
/*  18:    */   public MULTIANEWARRAY(int index, short dimensions)
/*  19:    */   {
/*  20: 78 */     super((short)197, index);
/*  21: 80 */     if (dimensions < 1) {
/*  22: 81 */       throw new ClassGenException("Invalid dimensions value: " + dimensions);
/*  23:    */     }
/*  24: 83 */     this.dimensions = dimensions;
/*  25: 84 */     this.length = 4;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void dump(DataOutputStream out)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 92 */     out.writeByte(this.opcode);
/*  32: 93 */     out.writeShort(this.index);
/*  33: 94 */     out.writeByte(this.dimensions);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39:103 */     super.initFromFile(bytes, wide);
/*  40:104 */     this.dimensions = ((short)bytes.readByte());
/*  41:105 */     this.length = 4;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final short getDimensions()
/*  45:    */   {
/*  46:111 */     return this.dimensions;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String toString(boolean verbose)
/*  50:    */   {
/*  51:117 */     return super.toString(verbose) + " " + this.index + " " + this.dimensions;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String toString(ConstantPool cp)
/*  55:    */   {
/*  56:124 */     return super.toString(cp) + " " + this.dimensions;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int consumeStack(ConstantPoolGen cpg)
/*  60:    */   {
/*  61:132 */     return this.dimensions;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Class[] getExceptions()
/*  65:    */   {
/*  66:135 */     Class[] cs = new Class[2 + ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length];
/*  67:    */     
/*  68:137 */     System.arraycopy(ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION, 0, cs, 0, ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length);
/*  69:    */     
/*  70:    */ 
/*  71:140 */     cs[(ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length + 1)] = ExceptionConstants.NEGATIVE_ARRAY_SIZE_EXCEPTION;
/*  72:141 */     cs[ExceptionConstants.EXCS_CLASS_AND_INTERFACE_RESOLUTION.length] = ExceptionConstants.ILLEGAL_ACCESS_ERROR;
/*  73:    */     
/*  74:143 */     return cs;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public ObjectType getLoadClassType(ConstantPoolGen cpg)
/*  78:    */   {
/*  79:147 */     Type t = getType(cpg);
/*  80:149 */     if ((t instanceof ArrayType)) {
/*  81:150 */       t = ((ArrayType)t).getBasicType();
/*  82:    */     }
/*  83:153 */     return (t instanceof ObjectType) ? (ObjectType)t : null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void accept(Visitor v)
/*  87:    */   {
/*  88:165 */     v.visitLoadClass(this);
/*  89:166 */     v.visitAllocationInstruction(this);
/*  90:167 */     v.visitExceptionThrower(this);
/*  91:168 */     v.visitTypedInstruction(this);
/*  92:169 */     v.visitCPInstruction(this);
/*  93:170 */     v.visitMULTIANEWARRAY(this);
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.MULTIANEWARRAY
 * JD-Core Version:    0.7.0.1
 */