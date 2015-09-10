/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.Constants;
/*   7:    */ 
/*   8:    */ public final class LocalVariable
/*   9:    */   implements Constants, Cloneable, Node
/*  10:    */ {
/*  11:    */   private int start_pc;
/*  12:    */   private int length;
/*  13:    */   private int name_index;
/*  14:    */   private int signature_index;
/*  15:    */   private int index;
/*  16:    */   private ConstantPool constant_pool;
/*  17:    */   
/*  18:    */   public LocalVariable(LocalVariable c)
/*  19:    */   {
/*  20: 84 */     this(c.getStartPC(), c.getLength(), c.getNameIndex(), c.getSignatureIndex(), c.getIndex(), c.getConstantPool());
/*  21:    */   }
/*  22:    */   
/*  23:    */   LocalVariable(DataInputStream file, ConstantPool constant_pool)
/*  24:    */     throws IOException
/*  25:    */   {
/*  26: 96 */     this(file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort(), constant_pool);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public LocalVariable(int start_pc, int length, int name_index, int signature_index, int index, ConstantPool constant_pool)
/*  30:    */   {
/*  31:113 */     this.start_pc = start_pc;
/*  32:114 */     this.length = length;
/*  33:115 */     this.name_index = name_index;
/*  34:116 */     this.signature_index = signature_index;
/*  35:117 */     this.index = index;
/*  36:118 */     this.constant_pool = constant_pool;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void accept(Visitor v)
/*  40:    */   {
/*  41:129 */     v.visitLocalVariable(this);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final void dump(DataOutputStream file)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47:140 */     file.writeShort(this.start_pc);
/*  48:141 */     file.writeShort(this.length);
/*  49:142 */     file.writeShort(this.name_index);
/*  50:143 */     file.writeShort(this.signature_index);
/*  51:144 */     file.writeShort(this.index);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final ConstantPool getConstantPool()
/*  55:    */   {
/*  56:150 */     return this.constant_pool;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final int getLength()
/*  60:    */   {
/*  61:155 */     return this.length;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final String getName()
/*  65:    */   {
/*  66:163 */     ConstantUtf8 c = (ConstantUtf8)this.constant_pool.getConstant(this.name_index, (byte)1);
/*  67:164 */     return c.getBytes();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final int getNameIndex()
/*  71:    */   {
/*  72:170 */     return this.name_index;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public final String getSignature()
/*  76:    */   {
/*  77:177 */     ConstantUtf8 c = (ConstantUtf8)this.constant_pool.getConstant(this.signature_index, (byte)1);
/*  78:    */     
/*  79:179 */     return c.getBytes();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public final int getSignatureIndex()
/*  83:    */   {
/*  84:185 */     return this.signature_index;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final int getIndex()
/*  88:    */   {
/*  89:190 */     return this.index;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final int getStartPC()
/*  93:    */   {
/*  94:195 */     return this.start_pc;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final void setConstantPool(ConstantPool constant_pool)
/*  98:    */   {
/*  99:201 */     this.constant_pool = constant_pool;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public final void setLength(int length)
/* 103:    */   {
/* 104:208 */     this.length = length;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final void setNameIndex(int name_index)
/* 108:    */   {
/* 109:215 */     this.name_index = name_index;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public final void setSignatureIndex(int signature_index)
/* 113:    */   {
/* 114:222 */     this.signature_index = signature_index;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public final void setIndex(int index)
/* 118:    */   {
/* 119:228 */     this.index = index;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final void setStartPC(int start_pc)
/* 123:    */   {
/* 124:234 */     this.start_pc = start_pc;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public final String toString()
/* 128:    */   {
/* 129:241 */     String name = getName();String signature = Utility.signatureToString(getSignature());
/* 130:    */     
/* 131:243 */     return "LocalVariable(start_pc = " + this.start_pc + ", length = " + this.length + ", index = " + this.index + ":" + signature + " " + name + ")";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public LocalVariable copy()
/* 135:    */   {
/* 136:    */     try
/* 137:    */     {
/* 138:252 */       return (LocalVariable)clone();
/* 139:    */     }
/* 140:    */     catch (CloneNotSupportedException e) {}
/* 141:255 */     return null;
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.LocalVariable
 * JD-Core Version:    0.7.0.1
 */