/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ 
/*  10:    */ public final class Unknown
/*  11:    */   extends Attribute
/*  12:    */ {
/*  13:    */   private byte[] bytes;
/*  14:    */   private String name;
/*  15: 73 */   private static HashMap unknown_attributes = new HashMap();
/*  16:    */   
/*  17:    */   static Unknown[] getUnknownAttributes()
/*  18:    */   {
/*  19: 78 */     Unknown[] unknowns = new Unknown[unknown_attributes.size()];
/*  20: 79 */     Iterator entries = unknown_attributes.values().iterator();
/*  21: 81 */     for (int i = 0; entries.hasNext(); i++) {
/*  22: 82 */       unknowns[i] = ((Unknown)entries.next());
/*  23:    */     }
/*  24: 84 */     unknown_attributes.clear();
/*  25: 85 */     return unknowns;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Unknown(Unknown c)
/*  29:    */   {
/*  30: 93 */     this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Unknown(int name_index, int length, byte[] bytes, ConstantPool constant_pool)
/*  34:    */   {
/*  35:107 */     super((byte)-1, name_index, length, constant_pool);
/*  36:108 */     this.bytes = bytes;
/*  37:    */     
/*  38:110 */     this.name = ((ConstantUtf8)constant_pool.getConstant(name_index, (byte)1)).getBytes();
/*  39:    */     
/*  40:112 */     unknown_attributes.put(this.name, this);
/*  41:    */   }
/*  42:    */   
/*  43:    */   Unknown(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46:127 */     this(name_index, length, (byte[])null, constant_pool);
/*  47:129 */     if (length > 0)
/*  48:    */     {
/*  49:130 */       this.bytes = new byte[length];
/*  50:131 */       file.readFully(this.bytes);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void accept(Visitor v)
/*  55:    */   {
/*  56:143 */     v.visitUnknown(this);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final void dump(DataOutputStream file)
/*  60:    */     throws IOException
/*  61:    */   {
/*  62:153 */     super.dump(file);
/*  63:154 */     if (this.length > 0) {
/*  64:155 */       file.write(this.bytes, 0, this.length);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public final byte[] getBytes()
/*  69:    */   {
/*  70:160 */     return this.bytes;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final String getName()
/*  74:    */   {
/*  75:165 */     return this.name;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final void setBytes(byte[] bytes)
/*  79:    */   {
/*  80:171 */     this.bytes = bytes;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public final String toString()
/*  84:    */   {
/*  85:178 */     if ((this.length == 0) || (this.bytes == null)) {
/*  86:179 */       return "(Unknown attribute " + this.name + ")";
/*  87:    */     }
/*  88:    */     String hex;
/*  89:182 */     if (this.length > 10)
/*  90:    */     {
/*  91:183 */       byte[] tmp = new byte[10];
/*  92:184 */       System.arraycopy(this.bytes, 0, tmp, 0, 10);
/*  93:185 */       hex = Utility.toHexString(tmp) + "... (truncated)";
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:188 */       hex = Utility.toHexString(this.bytes);
/*  98:    */     }
/*  99:190 */     return "(Unknown attribute " + this.name + ": " + hex + ")";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Attribute copy(ConstantPool constant_pool)
/* 103:    */   {
/* 104:197 */     Unknown c = (Unknown)clone();
/* 105:199 */     if (this.bytes != null) {
/* 106:200 */       c.bytes = ((byte[])this.bytes.clone());
/* 107:    */     }
/* 108:202 */     c.constant_pool = constant_pool;
/* 109:203 */     return c;
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.Unknown
 * JD-Core Version:    0.7.0.1
 */