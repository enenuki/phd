/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ 
/*   6:    */ public final class ConstantMethodref
/*   7:    */   extends ConstantCP
/*   8:    */ {
/*   9:    */   public ConstantMethodref(ConstantMethodref c)
/*  10:    */   {
/*  11: 71 */     super((byte)10, c.getClassIndex(), c.getNameAndTypeIndex());
/*  12:    */   }
/*  13:    */   
/*  14:    */   ConstantMethodref(DataInputStream file)
/*  15:    */     throws IOException
/*  16:    */   {
/*  17: 82 */     super((byte)10, file);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ConstantMethodref(int class_index, int name_and_type_index)
/*  21:    */   {
/*  22: 91 */     super((byte)10, class_index, name_and_type_index);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void accept(Visitor v)
/*  26:    */   {
/*  27:102 */     v.visitConstantMethodref(this);
/*  28:    */   }
/*  29:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantMethodref
 * JD-Core Version:    0.7.0.1
 */