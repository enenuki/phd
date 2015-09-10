/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class StackMapType
/*   8:    */   implements Cloneable
/*   9:    */ {
/*  10:    */   private byte type;
/*  11: 72 */   private int index = -1;
/*  12:    */   private ConstantPool constant_pool;
/*  13:    */   
/*  14:    */   StackMapType(DataInputStream file, ConstantPool constant_pool)
/*  15:    */     throws IOException
/*  16:    */   {
/*  17: 82 */     this(file.readByte(), -1, constant_pool);
/*  18: 84 */     if (hasIndex()) {
/*  19: 85 */       setIndex(file.readShort());
/*  20:    */     }
/*  21: 87 */     setConstantPool(constant_pool);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public StackMapType(byte type, int index, ConstantPool constant_pool)
/*  25:    */   {
/*  26: 95 */     setType(type);
/*  27: 96 */     setIndex(index);
/*  28: 97 */     setConstantPool(constant_pool);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setType(byte t)
/*  32:    */   {
/*  33:101 */     if ((t < 0) || (t > 8)) {
/*  34:102 */       throw new RuntimeException("Illegal type for StackMapType: " + t);
/*  35:    */     }
/*  36:103 */     this.type = t;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public byte getType()
/*  40:    */   {
/*  41:106 */     return this.type;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setIndex(int t)
/*  45:    */   {
/*  46:107 */     this.index = t;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getIndex()
/*  50:    */   {
/*  51:112 */     return this.index;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final void dump(DataOutputStream file)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:122 */     file.writeByte(this.type);
/*  58:123 */     if (hasIndex()) {
/*  59:124 */       file.writeShort(getIndex());
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public final boolean hasIndex()
/*  64:    */   {
/*  65:130 */     return (this.type == 7) || (this.type == 8);
/*  66:    */   }
/*  67:    */   
/*  68:    */   private String printIndex()
/*  69:    */   {
/*  70:135 */     if (this.type == 7) {
/*  71:136 */       return ", class=" + this.constant_pool.constantToString(this.index, (byte)7);
/*  72:    */     }
/*  73:137 */     if (this.type == 8) {
/*  74:138 */       return ", offset=" + this.index;
/*  75:    */     }
/*  76:140 */     return "";
/*  77:    */   }
/*  78:    */   
/*  79:    */   public final String toString()
/*  80:    */   {
/*  81:147 */     return "(type=" + org.apache.bcel.Constants.ITEM_NAMES[this.type] + printIndex() + ")";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public StackMapType copy()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:155 */       return (StackMapType)clone();
/*  89:    */     }
/*  90:    */     catch (CloneNotSupportedException e) {}
/*  91:158 */     return null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public final ConstantPool getConstantPool()
/*  95:    */   {
/*  96:164 */     return this.constant_pool;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public final void setConstantPool(ConstantPool constant_pool)
/* 100:    */   {
/* 101:170 */     this.constant_pool = constant_pool;
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.StackMapType
 * JD-Core Version:    0.7.0.1
 */