/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.classfile.Constant;
/*   7:    */ import org.apache.bcel.classfile.ConstantClass;
/*   8:    */ import org.apache.bcel.classfile.ConstantPool;
/*   9:    */ import org.apache.bcel.util.ByteSequence;
/*  10:    */ 
/*  11:    */ public abstract class CPInstruction
/*  12:    */   extends Instruction
/*  13:    */   implements TypedInstruction, IndexedInstruction
/*  14:    */ {
/*  15:    */   protected int index;
/*  16:    */   
/*  17:    */   CPInstruction() {}
/*  18:    */   
/*  19:    */   protected CPInstruction(short opcode, int index)
/*  20:    */   {
/*  21: 88 */     super(opcode, (short)3);
/*  22: 89 */     setIndex(index);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void dump(DataOutputStream out)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 97 */     out.writeByte(this.opcode);
/*  29: 98 */     out.writeShort(this.index);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String toString(boolean verbose)
/*  33:    */   {
/*  34:111 */     return super.toString(verbose) + " " + this.index;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String toString(ConstantPool cp)
/*  38:    */   {
/*  39:118 */     Constant c = cp.getConstant(this.index);
/*  40:119 */     String str = cp.constantToString(c);
/*  41:121 */     if ((c instanceof ConstantClass)) {
/*  42:122 */       str = str.replace('.', '/');
/*  43:    */     }
/*  44:124 */     return org.apache.bcel.Constants.OPCODE_NAMES[this.opcode] + " " + str;
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:135 */     setIndex(bytes.readUnsignedShort());
/*  51:136 */     this.length = 3;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final int getIndex()
/*  55:    */   {
/*  56:142 */     return this.index;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIndex(int index)
/*  60:    */   {
/*  61:149 */     if (index < 0) {
/*  62:150 */       throw new ClassGenException("Negative index value: " + index);
/*  63:    */     }
/*  64:152 */     this.index = index;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Type getType(ConstantPoolGen cpg)
/*  68:    */   {
/*  69:158 */     ConstantPool cp = cpg.getConstantPool();
/*  70:159 */     String name = cp.getConstantString(this.index, (byte)7);
/*  71:161 */     if (!name.startsWith("[")) {
/*  72:162 */       name = "L" + name + ";";
/*  73:    */     }
/*  74:164 */     return Type.getType(name);
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.CPInstruction
 * JD-Core Version:    0.7.0.1
 */