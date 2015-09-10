/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public abstract class Constant
/*   8:    */   implements Cloneable, Node
/*   9:    */ {
/*  10:    */   protected byte tag;
/*  11:    */   
/*  12:    */   Constant(byte tag)
/*  13:    */   {
/*  14: 79 */     this.tag = tag;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public abstract void accept(Visitor paramVisitor);
/*  18:    */   
/*  19:    */   public abstract void dump(DataOutputStream paramDataOutputStream)
/*  20:    */     throws IOException;
/*  21:    */   
/*  22:    */   public final byte getTag()
/*  23:    */   {
/*  24: 96 */     return this.tag;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String toString()
/*  28:    */   {
/*  29:102 */     return org.apache.bcel.Constants.CONSTANT_NAMES[this.tag] + "[" + this.tag + "]";
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Constant copy()
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36:110 */       return (Constant)super.clone();
/*  37:    */     }
/*  38:    */     catch (CloneNotSupportedException e) {}
/*  39:113 */     return null;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object clone()
/*  43:    */     throws CloneNotSupportedException
/*  44:    */   {
/*  45:117 */     return super.clone();
/*  46:    */   }
/*  47:    */   
/*  48:    */   static final Constant readConstant(DataInputStream file)
/*  49:    */     throws IOException, ClassFormatError
/*  50:    */   {
/*  51:129 */     byte b = file.readByte();
/*  52:131 */     switch (b)
/*  53:    */     {
/*  54:    */     case 7: 
/*  55:132 */       return new ConstantClass(file);
/*  56:    */     case 9: 
/*  57:133 */       return new ConstantFieldref(file);
/*  58:    */     case 10: 
/*  59:134 */       return new ConstantMethodref(file);
/*  60:    */     case 11: 
/*  61:135 */       return new ConstantInterfaceMethodref(file);
/*  62:    */     case 8: 
/*  63:137 */       return new ConstantString(file);
/*  64:    */     case 3: 
/*  65:138 */       return new ConstantInteger(file);
/*  66:    */     case 4: 
/*  67:139 */       return new ConstantFloat(file);
/*  68:    */     case 5: 
/*  69:140 */       return new ConstantLong(file);
/*  70:    */     case 6: 
/*  71:141 */       return new ConstantDouble(file);
/*  72:    */     case 12: 
/*  73:142 */       return new ConstantNameAndType(file);
/*  74:    */     case 1: 
/*  75:143 */       return new ConstantUtf8(file);
/*  76:    */     }
/*  77:145 */     throw new ClassFormatError("Invalid byte tag in constant pool: " + b);
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.Constant
 * JD-Core Version:    0.7.0.1
 */