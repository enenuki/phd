/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class InnerClasses
/*   8:    */   extends Attribute
/*   9:    */ {
/*  10:    */   private InnerClass[] inner_classes;
/*  11:    */   private int number_of_classes;
/*  12:    */   
/*  13:    */   public InnerClasses(InnerClasses c)
/*  14:    */   {
/*  15: 79 */     this(c.getNameIndex(), c.getLength(), c.getInnerClasses(), c.getConstantPool());
/*  16:    */   }
/*  17:    */   
/*  18:    */   public InnerClasses(int name_index, int length, InnerClass[] inner_classes, ConstantPool constant_pool)
/*  19:    */   {
/*  20: 94 */     super((byte)6, name_index, length, constant_pool);
/*  21: 95 */     setInnerClasses(inner_classes);
/*  22:    */   }
/*  23:    */   
/*  24:    */   InnerClasses(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27:110 */     this(name_index, length, (InnerClass[])null, constant_pool);
/*  28:    */     
/*  29:112 */     this.number_of_classes = file.readUnsignedShort();
/*  30:113 */     this.inner_classes = new InnerClass[this.number_of_classes];
/*  31:115 */     for (int i = 0; i < this.number_of_classes; i++) {
/*  32:116 */       this.inner_classes[i] = new InnerClass(file);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void accept(Visitor v)
/*  37:    */   {
/*  38:126 */     v.visitInnerClasses(this);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final void dump(DataOutputStream file)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:136 */     super.dump(file);
/*  45:137 */     file.writeShort(this.number_of_classes);
/*  46:139 */     for (int i = 0; i < this.number_of_classes; i++) {
/*  47:140 */       this.inner_classes[i].dump(file);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final InnerClass[] getInnerClasses()
/*  52:    */   {
/*  53:146 */     return this.inner_classes;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public final void setInnerClasses(InnerClass[] inner_classes)
/*  57:    */   {
/*  58:152 */     this.inner_classes = inner_classes;
/*  59:153 */     this.number_of_classes = (inner_classes == null ? 0 : inner_classes.length);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final String toString()
/*  63:    */   {
/*  64:160 */     StringBuffer buf = new StringBuffer();
/*  65:162 */     for (int i = 0; i < this.number_of_classes; i++) {
/*  66:163 */       buf.append(this.inner_classes[i].toString(this.constant_pool) + "\n");
/*  67:    */     }
/*  68:165 */     return buf.toString();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Attribute copy(ConstantPool constant_pool)
/*  72:    */   {
/*  73:172 */     InnerClasses c = (InnerClasses)clone();
/*  74:    */     
/*  75:174 */     c.inner_classes = new InnerClass[this.number_of_classes];
/*  76:175 */     for (int i = 0; i < this.number_of_classes; i++) {
/*  77:176 */       c.inner_classes[i] = this.inner_classes[i].copy();
/*  78:    */     }
/*  79:178 */     c.constant_pool = constant_pool;
/*  80:179 */     return c;
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.InnerClasses
 * JD-Core Version:    0.7.0.1
 */