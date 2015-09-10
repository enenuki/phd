/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public abstract class ConstantCP
/*   8:    */   extends Constant
/*   9:    */ {
/*  10:    */   protected int class_index;
/*  11:    */   protected int name_and_type_index;
/*  12:    */   
/*  13:    */   public ConstantCP(ConstantCP c)
/*  14:    */   {
/*  15: 77 */     this(c.getTag(), c.getClassIndex(), c.getNameAndTypeIndex());
/*  16:    */   }
/*  17:    */   
/*  18:    */   ConstantCP(byte tag, DataInputStream file)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 89 */     this(tag, file.readUnsignedShort(), file.readUnsignedShort());
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected ConstantCP(byte tag, int class_index, int name_and_type_index)
/*  25:    */   {
/*  26: 98 */     super(tag);
/*  27: 99 */     this.class_index = class_index;
/*  28:100 */     this.name_and_type_index = name_and_type_index;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public final void dump(DataOutputStream file)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34:111 */     file.writeByte(this.tag);
/*  35:112 */     file.writeShort(this.class_index);
/*  36:113 */     file.writeShort(this.name_and_type_index);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final int getClassIndex()
/*  40:    */   {
/*  41:119 */     return this.class_index;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final int getNameAndTypeIndex()
/*  45:    */   {
/*  46:124 */     return this.name_and_type_index;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final void setClassIndex(int class_index)
/*  50:    */   {
/*  51:130 */     this.class_index = class_index;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getClass(ConstantPool cp)
/*  55:    */   {
/*  56:137 */     return cp.constantToString(this.class_index, (byte)7);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final void setNameAndTypeIndex(int name_and_type_index)
/*  60:    */   {
/*  61:144 */     this.name_and_type_index = name_and_type_index;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final String toString()
/*  65:    */   {
/*  66:151 */     return super.toString() + "(class_index = " + this.class_index + ", name_and_type_index = " + this.name_and_type_index + ")";
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantCP
 * JD-Core Version:    0.7.0.1
 */