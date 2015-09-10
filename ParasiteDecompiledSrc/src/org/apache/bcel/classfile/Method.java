/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ 
/*   6:    */ public final class Method
/*   7:    */   extends FieldOrMethod
/*   8:    */ {
/*   9:    */   public Method() {}
/*  10:    */   
/*  11:    */   public Method(Method c)
/*  12:    */   {
/*  13: 79 */     super(c);
/*  14:    */   }
/*  15:    */   
/*  16:    */   Method(DataInputStream file, ConstantPool constant_pool)
/*  17:    */     throws IOException, ClassFormatError
/*  18:    */   {
/*  19: 91 */     super(file, constant_pool);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Method(int access_flags, int name_index, int signature_index, Attribute[] attributes, ConstantPool constant_pool)
/*  23:    */   {
/*  24:104 */     super(access_flags, name_index, signature_index, attributes, constant_pool);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void accept(Visitor v)
/*  28:    */   {
/*  29:115 */     v.visitMethod(this);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public final Code getCode()
/*  33:    */   {
/*  34:122 */     for (int i = 0; i < this.attributes_count; i++) {
/*  35:123 */       if ((this.attributes[i] instanceof Code)) {
/*  36:124 */         return (Code)this.attributes[i];
/*  37:    */       }
/*  38:    */     }
/*  39:126 */     return null;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final ExceptionTable getExceptionTable()
/*  43:    */   {
/*  44:134 */     for (int i = 0; i < this.attributes_count; i++) {
/*  45:135 */       if ((this.attributes[i] instanceof ExceptionTable)) {
/*  46:136 */         return (ExceptionTable)this.attributes[i];
/*  47:    */       }
/*  48:    */     }
/*  49:138 */     return null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final LocalVariableTable getLocalVariableTable()
/*  53:    */   {
/*  54:145 */     Code code = getCode();
/*  55:147 */     if (code != null) {
/*  56:148 */       return code.getLocalVariableTable();
/*  57:    */     }
/*  58:150 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public final LineNumberTable getLineNumberTable()
/*  62:    */   {
/*  63:157 */     Code code = getCode();
/*  64:159 */     if (code != null) {
/*  65:160 */       return code.getLineNumberTable();
/*  66:    */     }
/*  67:162 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final String toString()
/*  71:    */   {
/*  72:179 */     String access = Utility.accessToString(this.access_flags);
/*  73:    */     
/*  74:    */ 
/*  75:182 */     ConstantUtf8 c = (ConstantUtf8)this.constant_pool.getConstant(this.signature_index, (byte)1);
/*  76:    */     
/*  77:184 */     String signature = c.getBytes();
/*  78:    */     
/*  79:186 */     c = (ConstantUtf8)this.constant_pool.getConstant(this.name_index, (byte)1);
/*  80:187 */     String name = c.getBytes();
/*  81:    */     
/*  82:189 */     signature = Utility.methodSignatureToString(signature, name, access, true, getLocalVariableTable());
/*  83:    */     
/*  84:191 */     StringBuffer buf = new StringBuffer(signature);
/*  85:193 */     for (int i = 0; i < this.attributes_count; i++)
/*  86:    */     {
/*  87:194 */       Attribute a = this.attributes[i];
/*  88:196 */       if ((!(a instanceof Code)) && (!(a instanceof ExceptionTable))) {
/*  89:197 */         buf.append(" [" + a.toString() + "]");
/*  90:    */       }
/*  91:    */     }
/*  92:200 */     ExceptionTable e = getExceptionTable();
/*  93:201 */     if (e != null)
/*  94:    */     {
/*  95:202 */       String str = e.toString();
/*  96:203 */       if (!str.equals("")) {
/*  97:204 */         buf.append("\n\t\tthrows " + str);
/*  98:    */       }
/*  99:    */     }
/* 100:207 */     return buf.toString();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public final Method copy(ConstantPool constant_pool)
/* 104:    */   {
/* 105:214 */     return (Method)copy_(constant_pool);
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.Method
 * JD-Core Version:    0.7.0.1
 */