/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public abstract class FieldOrMethod
/*   8:    */   extends AccessFlags
/*   9:    */   implements Cloneable, Node
/*  10:    */ {
/*  11:    */   protected int name_index;
/*  12:    */   protected int signature_index;
/*  13:    */   protected int attributes_count;
/*  14:    */   protected Attribute[] attributes;
/*  15:    */   protected ConstantPool constant_pool;
/*  16:    */   
/*  17:    */   FieldOrMethod() {}
/*  18:    */   
/*  19:    */   protected FieldOrMethod(FieldOrMethod c)
/*  20:    */   {
/*  21: 79 */     this(c.getAccessFlags(), c.getNameIndex(), c.getSignatureIndex(), c.getAttributes(), c.getConstantPool());
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected FieldOrMethod(DataInputStream file, ConstantPool constant_pool)
/*  25:    */     throws IOException, ClassFormatError
/*  26:    */   {
/*  27: 92 */     this(file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort(), null, constant_pool);
/*  28:    */     
/*  29:    */ 
/*  30: 95 */     this.attributes_count = file.readUnsignedShort();
/*  31: 96 */     this.attributes = new Attribute[this.attributes_count];
/*  32: 97 */     for (int i = 0; i < this.attributes_count; i++) {
/*  33: 98 */       this.attributes[i] = Attribute.readAttribute(file, constant_pool);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected FieldOrMethod(int access_flags, int name_index, int signature_index, Attribute[] attributes, ConstantPool constant_pool)
/*  38:    */   {
/*  39:111 */     this.access_flags = access_flags;
/*  40:112 */     this.name_index = name_index;
/*  41:113 */     this.signature_index = signature_index;
/*  42:114 */     this.constant_pool = constant_pool;
/*  43:    */     
/*  44:116 */     setAttributes(attributes);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final void dump(DataOutputStream file)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:127 */     file.writeShort(this.access_flags);
/*  51:128 */     file.writeShort(this.name_index);
/*  52:129 */     file.writeShort(this.signature_index);
/*  53:130 */     file.writeShort(this.attributes_count);
/*  54:132 */     for (int i = 0; i < this.attributes_count; i++) {
/*  55:133 */       this.attributes[i].dump(file);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final Attribute[] getAttributes()
/*  60:    */   {
/*  61:139 */     return this.attributes;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final void setAttributes(Attribute[] attributes)
/*  65:    */   {
/*  66:145 */     this.attributes = attributes;
/*  67:146 */     this.attributes_count = (attributes == null ? 0 : attributes.length);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final ConstantPool getConstantPool()
/*  71:    */   {
/*  72:152 */     return this.constant_pool;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public final void setConstantPool(ConstantPool constant_pool)
/*  76:    */   {
/*  77:158 */     this.constant_pool = constant_pool;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public final int getNameIndex()
/*  81:    */   {
/*  82:164 */     return this.name_index;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public final void setNameIndex(int name_index)
/*  86:    */   {
/*  87:170 */     this.name_index = name_index;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public final int getSignatureIndex()
/*  91:    */   {
/*  92:176 */     return this.signature_index;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public final void setSignatureIndex(int signature_index)
/*  96:    */   {
/*  97:182 */     this.signature_index = signature_index;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public final String getName()
/* 101:    */   {
/* 102:190 */     ConstantUtf8 c = (ConstantUtf8)this.constant_pool.getConstant(this.name_index, (byte)1);
/* 103:    */     
/* 104:192 */     return c.getBytes();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final String getSignature()
/* 108:    */   {
/* 109:200 */     ConstantUtf8 c = (ConstantUtf8)this.constant_pool.getConstant(this.signature_index, (byte)1);
/* 110:    */     
/* 111:202 */     return c.getBytes();
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected FieldOrMethod copy_(ConstantPool constant_pool)
/* 115:    */   {
/* 116:209 */     FieldOrMethod c = null;
/* 117:    */     try
/* 118:    */     {
/* 119:212 */       c = (FieldOrMethod)clone();
/* 120:    */     }
/* 121:    */     catch (CloneNotSupportedException e) {}
/* 122:215 */     c.constant_pool = constant_pool;
/* 123:216 */     c.attributes = new Attribute[this.attributes_count];
/* 124:218 */     for (int i = 0; i < this.attributes_count; i++) {
/* 125:219 */       c.attributes[i] = this.attributes[i].copy(constant_pool);
/* 126:    */     }
/* 127:221 */     return c;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public abstract void accept(Visitor paramVisitor);
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.FieldOrMethod
 * JD-Core Version:    0.7.0.1
 */