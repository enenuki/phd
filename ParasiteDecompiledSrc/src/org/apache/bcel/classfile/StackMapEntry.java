/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class StackMapEntry
/*   8:    */   implements Cloneable
/*   9:    */ {
/*  10:    */   private int byte_code_offset;
/*  11:    */   private int number_of_locals;
/*  12:    */   private StackMapType[] types_of_locals;
/*  13:    */   private int number_of_stack_items;
/*  14:    */   private StackMapType[] types_of_stack_items;
/*  15:    */   private ConstantPool constant_pool;
/*  16:    */   
/*  17:    */   StackMapEntry(DataInputStream file, ConstantPool constant_pool)
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 85 */     this(file.readShort(), file.readShort(), null, -1, null, constant_pool);
/*  21:    */     
/*  22: 87 */     this.types_of_locals = new StackMapType[this.number_of_locals];
/*  23: 88 */     for (int i = 0; i < this.number_of_locals; i++) {
/*  24: 89 */       this.types_of_locals[i] = new StackMapType(file, constant_pool);
/*  25:    */     }
/*  26: 91 */     this.number_of_stack_items = file.readShort();
/*  27: 92 */     this.types_of_stack_items = new StackMapType[this.number_of_stack_items];
/*  28: 93 */     for (int i = 0; i < this.number_of_stack_items; i++) {
/*  29: 94 */       this.types_of_stack_items[i] = new StackMapType(file, constant_pool);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public StackMapEntry(int byte_code_offset, int number_of_locals, StackMapType[] types_of_locals, int number_of_stack_items, StackMapType[] types_of_stack_items, ConstantPool constant_pool)
/*  34:    */   {
/*  35:102 */     this.byte_code_offset = byte_code_offset;
/*  36:103 */     this.number_of_locals = number_of_locals;
/*  37:104 */     this.types_of_locals = types_of_locals;
/*  38:105 */     this.number_of_stack_items = number_of_stack_items;
/*  39:106 */     this.types_of_stack_items = types_of_stack_items;
/*  40:107 */     this.constant_pool = constant_pool;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final void dump(DataOutputStream file)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46:118 */     file.writeShort(this.byte_code_offset);
/*  47:    */     
/*  48:120 */     file.writeShort(this.number_of_locals);
/*  49:121 */     for (int i = 0; i < this.number_of_locals; i++) {
/*  50:122 */       this.types_of_locals[i].dump(file);
/*  51:    */     }
/*  52:124 */     file.writeShort(this.number_of_stack_items);
/*  53:125 */     for (int i = 0; i < this.number_of_stack_items; i++) {
/*  54:126 */       this.types_of_stack_items[i].dump(file);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final String toString()
/*  59:    */   {
/*  60:133 */     StringBuffer buf = new StringBuffer("(offset=" + this.byte_code_offset);
/*  61:135 */     if (this.number_of_locals > 0)
/*  62:    */     {
/*  63:136 */       buf.append(", locals={");
/*  64:138 */       for (int i = 0; i < this.number_of_locals; i++)
/*  65:    */       {
/*  66:139 */         buf.append(this.types_of_locals[i]);
/*  67:140 */         if (i < this.number_of_locals - 1) {
/*  68:141 */           buf.append(", ");
/*  69:    */         }
/*  70:    */       }
/*  71:144 */       buf.append("}");
/*  72:    */     }
/*  73:147 */     if (this.number_of_stack_items > 0)
/*  74:    */     {
/*  75:148 */       buf.append(", stack items={");
/*  76:150 */       for (int i = 0; i < this.number_of_stack_items; i++)
/*  77:    */       {
/*  78:151 */         buf.append(this.types_of_stack_items[i]);
/*  79:152 */         if (i < this.number_of_stack_items - 1) {
/*  80:153 */           buf.append(", ");
/*  81:    */         }
/*  82:    */       }
/*  83:156 */       buf.append("}");
/*  84:    */     }
/*  85:159 */     buf.append(")");
/*  86:    */     
/*  87:161 */     return buf.toString();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setByteCodeOffset(int b)
/*  91:    */   {
/*  92:165 */     this.byte_code_offset = b;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getByteCodeOffset()
/*  96:    */   {
/*  97:166 */     return this.byte_code_offset;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setNumberOfLocals(int n)
/* 101:    */   {
/* 102:167 */     this.number_of_locals = n;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getNumberOfLocals()
/* 106:    */   {
/* 107:168 */     return this.number_of_locals;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setTypesOfLocals(StackMapType[] t)
/* 111:    */   {
/* 112:169 */     this.types_of_locals = t;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public StackMapType[] getTypesOfLocals()
/* 116:    */   {
/* 117:170 */     return this.types_of_locals;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setNumberOfStackItems(int n)
/* 121:    */   {
/* 122:171 */     this.number_of_stack_items = n;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getNumberOfStackItems()
/* 126:    */   {
/* 127:172 */     return this.number_of_stack_items;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setTypesOfStackItems(StackMapType[] t)
/* 131:    */   {
/* 132:173 */     this.types_of_stack_items = t;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public StackMapType[] getTypesOfStackItems()
/* 136:    */   {
/* 137:174 */     return this.types_of_stack_items;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public StackMapEntry copy()
/* 141:    */   {
/* 142:    */     try
/* 143:    */     {
/* 144:181 */       return (StackMapEntry)clone();
/* 145:    */     }
/* 146:    */     catch (CloneNotSupportedException e) {}
/* 147:184 */     return null;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void accept(Visitor v)
/* 151:    */   {
/* 152:195 */     v.visitStackMapEntry(this);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public final ConstantPool getConstantPool()
/* 156:    */   {
/* 157:201 */     return this.constant_pool;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public final void setConstantPool(ConstantPool constant_pool)
/* 161:    */   {
/* 162:207 */     this.constant_pool = constant_pool;
/* 163:    */   }
/* 164:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.StackMapEntry
 * JD-Core Version:    0.7.0.1
 */