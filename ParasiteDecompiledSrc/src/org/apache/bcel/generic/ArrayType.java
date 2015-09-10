/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public final class ArrayType
/*   4:    */   extends ReferenceType
/*   5:    */ {
/*   6:    */   private int dimensions;
/*   7:    */   private Type basic_type;
/*   8:    */   
/*   9:    */   public ArrayType(byte type, int dimensions)
/*  10:    */   {
/*  11: 74 */     this(BasicType.getType(type), dimensions);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public ArrayType(String class_name, int dimensions)
/*  15:    */   {
/*  16: 83 */     this(new ObjectType(class_name), dimensions);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ArrayType(Type type, int dimensions)
/*  20:    */   {
/*  21: 92 */     super((byte)13, "<dummy>");
/*  22: 94 */     if ((dimensions < 1) || (dimensions > 255)) {
/*  23: 95 */       throw new ClassGenException("Invalid number of dimensions: " + dimensions);
/*  24:    */     }
/*  25: 97 */     switch (type.getType())
/*  26:    */     {
/*  27:    */     case 13: 
/*  28: 99 */       ArrayType array = (ArrayType)type;
/*  29:100 */       this.dimensions = (dimensions + array.dimensions);
/*  30:101 */       this.basic_type = array.basic_type;
/*  31:102 */       break;
/*  32:    */     case 12: 
/*  33:105 */       throw new ClassGenException("Invalid type: void[]");
/*  34:    */     default: 
/*  35:108 */       this.dimensions = dimensions;
/*  36:109 */       this.basic_type = type;
/*  37:    */     }
/*  38:113 */     StringBuffer buf = new StringBuffer();
/*  39:114 */     for (int i = 0; i < this.dimensions; i++) {
/*  40:115 */       buf.append('[');
/*  41:    */     }
/*  42:117 */     buf.append(this.basic_type.getSignature());
/*  43:    */     
/*  44:119 */     this.signature = buf.toString();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Type getBasicType()
/*  48:    */   {
/*  49:126 */     return this.basic_type;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Type getElementType()
/*  53:    */   {
/*  54:133 */     if (this.dimensions == 1) {
/*  55:134 */       return this.basic_type;
/*  56:    */     }
/*  57:136 */     return new ArrayType(this.basic_type, this.dimensions - 1);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getDimensions()
/*  61:    */   {
/*  62:141 */     return this.dimensions;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int hashcode()
/*  66:    */   {
/*  67:145 */     return this.basic_type.hashCode() ^ this.dimensions;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean equals(Object type)
/*  71:    */   {
/*  72:150 */     if ((type instanceof ArrayType))
/*  73:    */     {
/*  74:151 */       ArrayType array = (ArrayType)type;
/*  75:152 */       return (array.dimensions == this.dimensions) && (array.basic_type.equals(this.basic_type));
/*  76:    */     }
/*  77:154 */     return false;
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ArrayType
 * JD-Core Version:    0.7.0.1
 */