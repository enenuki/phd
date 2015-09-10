/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class StackMap
/*   8:    */   extends Attribute
/*   9:    */   implements Node
/*  10:    */ {
/*  11:    */   private int map_length;
/*  12:    */   private StackMapEntry[] map;
/*  13:    */   
/*  14:    */   public StackMap(int name_index, int length, StackMapEntry[] map, ConstantPool constant_pool)
/*  15:    */   {
/*  16: 88 */     super((byte)11, name_index, length, constant_pool);
/*  17:    */     
/*  18: 90 */     setStackMap(map);
/*  19:    */   }
/*  20:    */   
/*  21:    */   StackMap(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24:104 */     this(name_index, length, (StackMapEntry[])null, constant_pool);
/*  25:    */     
/*  26:106 */     this.map_length = file.readUnsignedShort();
/*  27:107 */     this.map = new StackMapEntry[this.map_length];
/*  28:109 */     for (int i = 0; i < this.map_length; i++) {
/*  29:110 */       this.map[i] = new StackMapEntry(file, constant_pool);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public final void dump(DataOutputStream file)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36:121 */     super.dump(file);
/*  37:122 */     file.writeShort(this.map_length);
/*  38:123 */     for (int i = 0; i < this.map_length; i++) {
/*  39:124 */       this.map[i].dump(file);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final StackMapEntry[] getStackMap()
/*  44:    */   {
/*  45:130 */     return this.map;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final void setStackMap(StackMapEntry[] map)
/*  49:    */   {
/*  50:136 */     this.map = map;
/*  51:    */     
/*  52:138 */     this.map_length = (map == null ? 0 : map.length);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final String toString()
/*  56:    */   {
/*  57:145 */     StringBuffer buf = new StringBuffer("StackMap(");
/*  58:147 */     for (int i = 0; i < this.map_length; i++)
/*  59:    */     {
/*  60:148 */       buf.append(this.map[i].toString());
/*  61:150 */       if (i < this.map_length - 1) {
/*  62:151 */         buf.append(", ");
/*  63:    */       }
/*  64:    */     }
/*  65:154 */     buf.append(')');
/*  66:    */     
/*  67:156 */     return buf.toString();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Attribute copy(ConstantPool constant_pool)
/*  71:    */   {
/*  72:163 */     StackMap c = (StackMap)clone();
/*  73:    */     
/*  74:165 */     c.map = new StackMapEntry[this.map_length];
/*  75:166 */     for (int i = 0; i < this.map_length; i++) {
/*  76:167 */       c.map[i] = this.map[i].copy();
/*  77:    */     }
/*  78:169 */     c.constant_pool = constant_pool;
/*  79:170 */     return c;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void accept(Visitor v)
/*  83:    */   {
/*  84:181 */     v.visitStackMap(this);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final int getMapLength()
/*  88:    */   {
/*  89:184 */     return this.map_length;
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.StackMap
 * JD-Core Version:    0.7.0.1
 */