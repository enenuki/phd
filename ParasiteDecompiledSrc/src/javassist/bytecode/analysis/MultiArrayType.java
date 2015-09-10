/*   1:    */ package javassist.bytecode.analysis;
/*   2:    */ 
/*   3:    */ import javassist.ClassPool;
/*   4:    */ import javassist.CtClass;
/*   5:    */ import javassist.NotFoundException;
/*   6:    */ 
/*   7:    */ public class MultiArrayType
/*   8:    */   extends Type
/*   9:    */ {
/*  10:    */   private MultiType component;
/*  11:    */   private int dims;
/*  12:    */   
/*  13:    */   public MultiArrayType(MultiType component, int dims)
/*  14:    */   {
/*  15: 31 */     super(null);
/*  16: 32 */     this.component = component;
/*  17: 33 */     this.dims = dims;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public CtClass getCtClass()
/*  21:    */   {
/*  22: 37 */     CtClass clazz = this.component.getCtClass();
/*  23: 38 */     if (clazz == null) {
/*  24: 39 */       return null;
/*  25:    */     }
/*  26: 41 */     ClassPool pool = clazz.getClassPool();
/*  27: 42 */     if (pool == null) {
/*  28: 43 */       pool = ClassPool.getDefault();
/*  29:    */     }
/*  30: 45 */     String name = arrayName(clazz.getName(), this.dims);
/*  31:    */     try
/*  32:    */     {
/*  33: 48 */       return pool.get(name);
/*  34:    */     }
/*  35:    */     catch (NotFoundException e)
/*  36:    */     {
/*  37: 50 */       throw new RuntimeException(e);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   boolean popChanged()
/*  42:    */   {
/*  43: 55 */     return this.component.popChanged();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getDimensions()
/*  47:    */   {
/*  48: 59 */     return this.dims;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Type getComponent()
/*  52:    */   {
/*  53: 63 */     return this.dims == 1 ? this.component : new MultiArrayType(this.component, this.dims - 1);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getSize()
/*  57:    */   {
/*  58: 67 */     return 1;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean isArray()
/*  62:    */   {
/*  63: 71 */     return true;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isAssignableFrom(Type type)
/*  67:    */   {
/*  68: 75 */     throw new UnsupportedOperationException("Not implemented");
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isReference()
/*  72:    */   {
/*  73: 79 */     return true;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean isAssignableTo(Type type)
/*  77:    */   {
/*  78: 83 */     if (eq(type.getCtClass(), Type.OBJECT.getCtClass())) {
/*  79: 84 */       return true;
/*  80:    */     }
/*  81: 86 */     if (eq(type.getCtClass(), Type.CLONEABLE.getCtClass())) {
/*  82: 87 */       return true;
/*  83:    */     }
/*  84: 89 */     if (eq(type.getCtClass(), Type.SERIALIZABLE.getCtClass())) {
/*  85: 90 */       return true;
/*  86:    */     }
/*  87: 92 */     if (!type.isArray()) {
/*  88: 93 */       return false;
/*  89:    */     }
/*  90: 95 */     Type typeRoot = getRootComponent(type);
/*  91: 96 */     int typeDims = type.getDimensions();
/*  92: 98 */     if (typeDims > this.dims) {
/*  93: 99 */       return false;
/*  94:    */     }
/*  95:101 */     if (typeDims < this.dims)
/*  96:    */     {
/*  97:102 */       if (eq(typeRoot.getCtClass(), Type.OBJECT.getCtClass())) {
/*  98:103 */         return true;
/*  99:    */       }
/* 100:105 */       if (eq(typeRoot.getCtClass(), Type.CLONEABLE.getCtClass())) {
/* 101:106 */         return true;
/* 102:    */       }
/* 103:108 */       if (eq(typeRoot.getCtClass(), Type.SERIALIZABLE.getCtClass())) {
/* 104:109 */         return true;
/* 105:    */       }
/* 106:111 */       return false;
/* 107:    */     }
/* 108:114 */     return this.component.isAssignableTo(typeRoot);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean equals(Object o)
/* 112:    */   {
/* 113:118 */     if (!(o instanceof MultiArrayType)) {
/* 114:119 */       return false;
/* 115:    */     }
/* 116:120 */     MultiArrayType multi = (MultiArrayType)o;
/* 117:    */     
/* 118:122 */     return (this.component.equals(multi.component)) && (this.dims == multi.dims);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String toString()
/* 122:    */   {
/* 123:127 */     return arrayName(this.component.toString(), this.dims);
/* 124:    */   }
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.MultiArrayType
 * JD-Core Version:    0.7.0.1
 */