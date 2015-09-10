/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.HashMap;
/*   7:    */ 
/*   8:    */ public abstract class Attribute
/*   9:    */   implements Cloneable, Node
/*  10:    */ {
/*  11:    */   protected int name_index;
/*  12:    */   protected int length;
/*  13:    */   protected byte tag;
/*  14:    */   protected ConstantPool constant_pool;
/*  15:    */   
/*  16:    */   Attribute(byte tag, int name_index, int length, ConstantPool constant_pool)
/*  17:    */   {
/*  18: 89 */     this.tag = tag;
/*  19: 90 */     this.name_index = name_index;
/*  20: 91 */     this.length = length;
/*  21: 92 */     this.constant_pool = constant_pool;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public abstract void accept(Visitor paramVisitor);
/*  25:    */   
/*  26:    */   public void dump(DataOutputStream file)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29:112 */     file.writeShort(this.name_index);
/*  30:113 */     file.writeInt(this.length);
/*  31:    */   }
/*  32:    */   
/*  33:116 */   private static HashMap readers = new HashMap();
/*  34:    */   
/*  35:    */   public static void addAttributeReader(String name, AttributeReader r)
/*  36:    */   {
/*  37:126 */     readers.put(name, r);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static void removeAttributeReader(String name)
/*  41:    */   {
/*  42:134 */     readers.remove(name);
/*  43:    */   }
/*  44:    */   
/*  45:    */   static final Attribute readAttribute(DataInputStream file, ConstantPool constant_pool)
/*  46:    */     throws IOException, ClassFormatError, InternalError
/*  47:    */   {
/*  48:158 */     byte tag = -1;
/*  49:    */     
/*  50:    */ 
/*  51:161 */     int name_index = file.readUnsignedShort();
/*  52:162 */     ConstantUtf8 c = (ConstantUtf8)constant_pool.getConstant(name_index, (byte)1);
/*  53:    */     
/*  54:164 */     String name = c.getBytes();
/*  55:    */     
/*  56:    */ 
/*  57:167 */     int length = file.readInt();
/*  58:170 */     for (byte i = 0; i < 12; i = (byte)(i + 1)) {
/*  59:171 */       if (name.equals(org.apache.bcel.Constants.ATTRIBUTE_NAMES[i]))
/*  60:    */       {
/*  61:172 */         tag = i;
/*  62:173 */         break;
/*  63:    */       }
/*  64:    */     }
/*  65:178 */     switch (tag)
/*  66:    */     {
/*  67:    */     case -1: 
/*  68:180 */       AttributeReader r = (AttributeReader)readers.get(name);
/*  69:182 */       if (r != null) {
/*  70:183 */         return r.createAttribute(name_index, length, file, constant_pool);
/*  71:    */       }
/*  72:185 */       return new Unknown(name_index, length, file, constant_pool);
/*  73:    */     case 1: 
/*  74:188 */       return new ConstantValue(name_index, length, file, constant_pool);
/*  75:    */     case 0: 
/*  76:191 */       return new SourceFile(name_index, length, file, constant_pool);
/*  77:    */     case 2: 
/*  78:194 */       return new Code(name_index, length, file, constant_pool);
/*  79:    */     case 3: 
/*  80:197 */       return new ExceptionTable(name_index, length, file, constant_pool);
/*  81:    */     case 4: 
/*  82:200 */       return new LineNumberTable(name_index, length, file, constant_pool);
/*  83:    */     case 5: 
/*  84:203 */       return new LocalVariableTable(name_index, length, file, constant_pool);
/*  85:    */     case 6: 
/*  86:206 */       return new InnerClasses(name_index, length, file, constant_pool);
/*  87:    */     case 7: 
/*  88:209 */       return new Synthetic(name_index, length, file, constant_pool);
/*  89:    */     case 8: 
/*  90:212 */       return new Deprecated(name_index, length, file, constant_pool);
/*  91:    */     case 9: 
/*  92:215 */       return new PMGClass(name_index, length, file, constant_pool);
/*  93:    */     case 10: 
/*  94:218 */       return new Signature(name_index, length, file, constant_pool);
/*  95:    */     case 11: 
/*  96:221 */       return new StackMap(name_index, length, file, constant_pool);
/*  97:    */     }
/*  98:224 */     throw new InternalError("Ooops! default case reached.");
/*  99:    */   }
/* 100:    */   
/* 101:    */   public final int getLength()
/* 102:    */   {
/* 103:231 */     return this.length;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public final void setLength(int length)
/* 107:    */   {
/* 108:237 */     this.length = length;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public final void setNameIndex(int name_index)
/* 112:    */   {
/* 113:244 */     this.name_index = name_index;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public final int getNameIndex()
/* 117:    */   {
/* 118:250 */     return this.name_index;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public final byte getTag()
/* 122:    */   {
/* 123:256 */     return this.tag;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public final ConstantPool getConstantPool()
/* 127:    */   {
/* 128:262 */     return this.constant_pool;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public final void setConstantPool(ConstantPool constant_pool)
/* 132:    */   {
/* 133:269 */     this.constant_pool = constant_pool;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Object clone()
/* 137:    */   {
/* 138:279 */     Object o = null;
/* 139:    */     try
/* 140:    */     {
/* 141:282 */       o = super.clone();
/* 142:    */     }
/* 143:    */     catch (CloneNotSupportedException e)
/* 144:    */     {
/* 145:284 */       e.printStackTrace();
/* 146:    */     }
/* 147:287 */     return o;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public abstract Attribute copy(ConstantPool paramConstantPool);
/* 151:    */   
/* 152:    */   public String toString()
/* 153:    */   {
/* 154:299 */     return org.apache.bcel.Constants.ATTRIBUTE_NAMES[this.tag];
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.Attribute
 * JD-Core Version:    0.7.0.1
 */