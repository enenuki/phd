/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class InnerClass
/*   8:    */   implements Cloneable, Node
/*   9:    */ {
/*  10:    */   private int inner_class_index;
/*  11:    */   private int outer_class_index;
/*  12:    */   private int inner_name_index;
/*  13:    */   private int inner_access_flags;
/*  14:    */   
/*  15:    */   public InnerClass(InnerClass c)
/*  16:    */   {
/*  17: 79 */     this(c.getInnerClassIndex(), c.getOuterClassIndex(), c.getInnerNameIndex(), c.getInnerAccessFlags());
/*  18:    */   }
/*  19:    */   
/*  20:    */   InnerClass(DataInputStream file)
/*  21:    */     throws IOException
/*  22:    */   {
/*  23: 90 */     this(file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort());
/*  24:    */   }
/*  25:    */   
/*  26:    */   public InnerClass(int inner_class_index, int outer_class_index, int inner_name_index, int inner_access_flags)
/*  27:    */   {
/*  28:103 */     this.inner_class_index = inner_class_index;
/*  29:104 */     this.outer_class_index = outer_class_index;
/*  30:105 */     this.inner_name_index = inner_name_index;
/*  31:106 */     this.inner_access_flags = inner_access_flags;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void accept(Visitor v)
/*  35:    */   {
/*  36:117 */     v.visitInnerClass(this);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final void dump(DataOutputStream file)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42:127 */     file.writeShort(this.inner_class_index);
/*  43:128 */     file.writeShort(this.outer_class_index);
/*  44:129 */     file.writeShort(this.inner_name_index);
/*  45:130 */     file.writeShort(this.inner_access_flags);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final int getInnerAccessFlags()
/*  49:    */   {
/*  50:135 */     return this.inner_access_flags;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final int getInnerClassIndex()
/*  54:    */   {
/*  55:139 */     return this.inner_class_index;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final int getInnerNameIndex()
/*  59:    */   {
/*  60:143 */     return this.inner_name_index;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public final int getOuterClassIndex()
/*  64:    */   {
/*  65:147 */     return this.outer_class_index;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public final void setInnerAccessFlags(int inner_access_flags)
/*  69:    */   {
/*  70:152 */     this.inner_access_flags = inner_access_flags;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final void setInnerClassIndex(int inner_class_index)
/*  74:    */   {
/*  75:158 */     this.inner_class_index = inner_class_index;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final void setInnerNameIndex(int inner_name_index)
/*  79:    */   {
/*  80:164 */     this.inner_name_index = inner_name_index;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public final void setOuterClassIndex(int outer_class_index)
/*  84:    */   {
/*  85:170 */     this.outer_class_index = outer_class_index;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public final String toString()
/*  89:    */   {
/*  90:176 */     return "InnerClass(" + this.inner_class_index + ", " + this.outer_class_index + ", " + this.inner_name_index + ", " + this.inner_access_flags + ")";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public final String toString(ConstantPool constant_pool)
/*  94:    */   {
/*  95:186 */     String inner_class_name = constant_pool.getConstantString(this.inner_class_index, (byte)7);
/*  96:    */     
/*  97:188 */     inner_class_name = Utility.compactClassName(inner_class_name);
/*  98:    */     String outer_class_name;
/*  99:190 */     if (this.outer_class_index != 0)
/* 100:    */     {
/* 101:191 */       outer_class_name = constant_pool.getConstantString(this.outer_class_index, (byte)7);
/* 102:    */       
/* 103:193 */       outer_class_name = Utility.compactClassName(outer_class_name);
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:196 */       outer_class_name = "<not a member>";
/* 108:    */     }
/* 109:    */     String inner_name;
/* 110:198 */     if (this.inner_name_index != 0) {
/* 111:199 */       inner_name = ((ConstantUtf8)constant_pool.getConstant(this.inner_name_index, (byte)1)).getBytes();
/* 112:    */     } else {
/* 113:202 */       inner_name = "<anonymous>";
/* 114:    */     }
/* 115:204 */     String access = Utility.accessToString(this.inner_access_flags, true);
/* 116:205 */     access = access + " ";
/* 117:    */     
/* 118:207 */     return "InnerClass:" + access + inner_class_name + "(\"" + outer_class_name + "\", \"" + inner_name + "\")";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public InnerClass copy()
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:216 */       return (InnerClass)clone();
/* 126:    */     }
/* 127:    */     catch (CloneNotSupportedException e) {}
/* 128:219 */     return null;
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.InnerClass
 * JD-Core Version:    0.7.0.1
 */