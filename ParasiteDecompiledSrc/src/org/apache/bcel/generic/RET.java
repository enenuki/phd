/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.util.ByteSequence;
/*   7:    */ 
/*   8:    */ public class RET
/*   9:    */   extends Instruction
/*  10:    */   implements IndexedInstruction, TypedInstruction
/*  11:    */ {
/*  12:    */   private boolean wide;
/*  13:    */   private int index;
/*  14:    */   
/*  15:    */   RET() {}
/*  16:    */   
/*  17:    */   public RET(int index)
/*  18:    */   {
/*  19: 78 */     super((short)169, (short)2);
/*  20: 79 */     setIndex(index);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void dump(DataOutputStream out)
/*  24:    */     throws IOException
/*  25:    */   {
/*  26: 87 */     if (this.wide) {
/*  27: 88 */       out.writeByte(196);
/*  28:    */     }
/*  29: 90 */     out.writeByte(this.opcode);
/*  30: 92 */     if (this.wide) {
/*  31: 93 */       out.writeShort(this.index);
/*  32:    */     } else {
/*  33: 95 */       out.writeByte(this.index);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   private final void setWide()
/*  38:    */   {
/*  39: 99 */     if ((this.wide = this.index > 255 ? 1 : 0) != 0) {
/*  40:100 */       this.length = 4;
/*  41:    */     } else {
/*  42:102 */       this.length = 2;
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  47:    */     throws IOException
/*  48:    */   {
/*  49:110 */     this.wide = wide;
/*  50:112 */     if (wide)
/*  51:    */     {
/*  52:113 */       this.index = bytes.readUnsignedShort();
/*  53:114 */       this.length = 4;
/*  54:    */     }
/*  55:    */     else
/*  56:    */     {
/*  57:116 */       this.index = bytes.readUnsignedByte();
/*  58:117 */       this.length = 2;
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final int getIndex()
/*  63:    */   {
/*  64:124 */     return this.index;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public final void setIndex(int n)
/*  68:    */   {
/*  69:130 */     if (n < 0) {
/*  70:131 */       throw new ClassGenException("Negative index value: " + n);
/*  71:    */     }
/*  72:133 */     this.index = n;
/*  73:134 */     setWide();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String toString(boolean verbose)
/*  77:    */   {
/*  78:141 */     return super.toString(verbose) + " " + this.index;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Type getType(ConstantPoolGen cp)
/*  82:    */   {
/*  83:147 */     return ReturnaddressType.NO_TARGET;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void accept(Visitor v)
/*  87:    */   {
/*  88:159 */     v.visitRET(this);
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.RET
 * JD-Core Version:    0.7.0.1
 */