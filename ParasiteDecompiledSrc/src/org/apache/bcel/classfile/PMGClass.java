/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ 
/*   8:    */ public final class PMGClass
/*   9:    */   extends Attribute
/*  10:    */ {
/*  11:    */   private int pmg_class_index;
/*  12:    */   private int pmg_index;
/*  13:    */   
/*  14:    */   public PMGClass(PMGClass c)
/*  15:    */   {
/*  16: 77 */     this(c.getNameIndex(), c.getLength(), c.getPMGIndex(), c.getPMGClassIndex(), c.getConstantPool());
/*  17:    */   }
/*  18:    */   
/*  19:    */   PMGClass(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22: 92 */     this(name_index, length, file.readUnsignedShort(), file.readUnsignedShort(), constant_pool);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public PMGClass(int name_index, int length, int pmg_index, int pmg_class_index, ConstantPool constant_pool)
/*  26:    */   {
/*  27:105 */     super((byte)9, name_index, length, constant_pool);
/*  28:106 */     this.pmg_index = pmg_index;
/*  29:107 */     this.pmg_class_index = pmg_class_index;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void accept(Visitor v)
/*  33:    */   {
/*  34:118 */     System.err.println("Visiting non-standard PMGClass object");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public final void dump(DataOutputStream file)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40:129 */     super.dump(file);
/*  41:130 */     file.writeShort(this.pmg_index);
/*  42:131 */     file.writeShort(this.pmg_class_index);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public final int getPMGClassIndex()
/*  46:    */   {
/*  47:137 */     return this.pmg_class_index;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public final void setPMGClassIndex(int pmg_class_index)
/*  51:    */   {
/*  52:143 */     this.pmg_class_index = pmg_class_index;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final int getPMGIndex()
/*  56:    */   {
/*  57:149 */     return this.pmg_index;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final void setPMGIndex(int pmg_index)
/*  61:    */   {
/*  62:155 */     this.pmg_index = pmg_index;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public final String getPMGName()
/*  66:    */   {
/*  67:162 */     ConstantUtf8 c = (ConstantUtf8)this.constant_pool.getConstant(this.pmg_index, (byte)1);
/*  68:    */     
/*  69:164 */     return c.getBytes();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public final String getPMGClassName()
/*  73:    */   {
/*  74:171 */     ConstantUtf8 c = (ConstantUtf8)this.constant_pool.getConstant(this.pmg_class_index, (byte)1);
/*  75:    */     
/*  76:173 */     return c.getBytes();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public final String toString()
/*  80:    */   {
/*  81:180 */     return "PMGClass(" + getPMGName() + ", " + getPMGClassName() + ")";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Attribute copy(ConstantPool constant_pool)
/*  85:    */   {
/*  86:187 */     return (PMGClass)clone();
/*  87:    */   }
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.PMGClass
 * JD-Core Version:    0.7.0.1
 */