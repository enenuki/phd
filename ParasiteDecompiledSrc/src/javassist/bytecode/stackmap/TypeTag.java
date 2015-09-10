/*  1:   */ package javassist.bytecode.stackmap;
/*  2:   */ 
/*  3:   */ public abstract interface TypeTag
/*  4:   */ {
/*  5:21 */   public static final TypeData TOP = null;
/*  6:22 */   public static final TypeData INTEGER = new TypeData.BasicType("int", 1);
/*  7:23 */   public static final TypeData FLOAT = new TypeData.BasicType("float", 2);
/*  8:24 */   public static final TypeData DOUBLE = new TypeData.BasicType("double", 3);
/*  9:25 */   public static final TypeData LONG = new TypeData.BasicType("long", 4);
/* 10:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.stackmap.TypeTag
 * JD-Core Version:    0.7.0.1
 */