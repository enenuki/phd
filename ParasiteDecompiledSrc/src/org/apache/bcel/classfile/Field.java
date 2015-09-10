/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ 
/*   6:    */ public final class Field
/*   7:    */   extends FieldOrMethod
/*   8:    */ {
/*   9:    */   public Field(Field c)
/*  10:    */   {
/*  11: 72 */     super(c);
/*  12:    */   }
/*  13:    */   
/*  14:    */   Field(DataInputStream file, ConstantPool constant_pool)
/*  15:    */     throws IOException, ClassFormatError
/*  16:    */   {
/*  17: 82 */     super(file, constant_pool);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Field(int access_flags, int name_index, int signature_index, Attribute[] attributes, ConstantPool constant_pool)
/*  21:    */   {
/*  22: 95 */     super(access_flags, name_index, signature_index, attributes, constant_pool);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void accept(Visitor v)
/*  26:    */   {
/*  27:106 */     v.visitField(this);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public final ConstantValue getConstantValue()
/*  31:    */   {
/*  32:113 */     for (int i = 0; i < this.attributes_count; i++) {
/*  33:114 */       if (this.attributes[i].getTag() == 1) {
/*  34:115 */         return (ConstantValue)this.attributes[i];
/*  35:    */       }
/*  36:    */     }
/*  37:117 */     return null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final String toString()
/*  41:    */   {
/*  42:130 */     String access = Utility.accessToString(this.access_flags);
/*  43:131 */     access = access + " ";
/*  44:132 */     String signature = Utility.signatureToString(getSignature());
/*  45:133 */     String name = getName();
/*  46:    */     
/*  47:135 */     StringBuffer buf = new StringBuffer(access + signature + " " + name);
/*  48:136 */     ConstantValue cv = getConstantValue();
/*  49:138 */     if (cv != null) {
/*  50:139 */       buf.append(" = " + cv);
/*  51:    */     }
/*  52:141 */     for (int i = 0; i < this.attributes_count; i++)
/*  53:    */     {
/*  54:142 */       Attribute a = this.attributes[i];
/*  55:144 */       if (!(a instanceof ConstantValue)) {
/*  56:145 */         buf.append(" [" + a.toString() + "]");
/*  57:    */       }
/*  58:    */     }
/*  59:148 */     return buf.toString();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final Field copy(ConstantPool constant_pool)
/*  63:    */   {
/*  64:155 */     return (Field)copy_(constant_pool);
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.Field
 * JD-Core Version:    0.7.0.1
 */