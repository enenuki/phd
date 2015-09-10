/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ public final class CtPrimitiveType
/*   4:    */   extends CtClass
/*   5:    */ {
/*   6:    */   private char descriptor;
/*   7:    */   private String wrapperName;
/*   8:    */   private String getMethodName;
/*   9:    */   private String mDescriptor;
/*  10:    */   private int returnOp;
/*  11:    */   private int arrayType;
/*  12:    */   private int dataSize;
/*  13:    */   
/*  14:    */   CtPrimitiveType(String name, char desc, String wrapper, String methodName, String mDesc, int opcode, int atype, int size)
/*  15:    */   {
/*  16: 34 */     super(name);
/*  17: 35 */     this.descriptor = desc;
/*  18: 36 */     this.wrapperName = wrapper;
/*  19: 37 */     this.getMethodName = methodName;
/*  20: 38 */     this.mDescriptor = mDesc;
/*  21: 39 */     this.returnOp = opcode;
/*  22: 40 */     this.arrayType = atype;
/*  23: 41 */     this.dataSize = size;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean isPrimitive()
/*  27:    */   {
/*  28: 49 */     return true;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getModifiers()
/*  32:    */   {
/*  33: 58 */     return 17;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public char getDescriptor()
/*  37:    */   {
/*  38: 65 */     return this.descriptor;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getWrapperName()
/*  42:    */   {
/*  43: 72 */     return this.wrapperName;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getGetMethodName()
/*  47:    */   {
/*  48: 80 */     return this.getMethodName;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getGetMethodDescriptor()
/*  52:    */   {
/*  53: 88 */     return this.mDescriptor;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getReturnOp()
/*  57:    */   {
/*  58: 95 */     return this.returnOp;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getArrayType()
/*  62:    */   {
/*  63:103 */     return this.arrayType;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getDataSize()
/*  67:    */   {
/*  68:110 */     return this.dataSize;
/*  69:    */   }
/*  70:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtPrimitiveType
 * JD-Core Version:    0.7.0.1
 */