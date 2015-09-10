/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ final class CtArray
/*   4:    */   extends CtClass
/*   5:    */ {
/*   6:    */   protected ClassPool pool;
/*   7:    */   
/*   8:    */   CtArray(String name, ClassPool cp)
/*   9:    */   {
/*  10: 26 */     super(name);
/*  11: 27 */     this.pool = cp;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public ClassPool getClassPool()
/*  15:    */   {
/*  16: 31 */     return this.pool;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean isArray()
/*  20:    */   {
/*  21: 35 */     return true;
/*  22:    */   }
/*  23:    */   
/*  24: 38 */   private CtClass[] interfaces = null;
/*  25:    */   
/*  26:    */   public int getModifiers()
/*  27:    */   {
/*  28: 41 */     int mod = 16;
/*  29:    */     try
/*  30:    */     {
/*  31: 43 */       mod |= getComponentType().getModifiers() & 0x7;
/*  32:    */     }
/*  33:    */     catch (NotFoundException e) {}
/*  34: 47 */     return mod;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public CtClass[] getInterfaces()
/*  38:    */     throws NotFoundException
/*  39:    */   {
/*  40: 51 */     if (this.interfaces == null) {
/*  41: 52 */       this.interfaces = new CtClass[] { this.pool.get("java.lang.Cloneable"), this.pool.get("java.io.Serializable") };
/*  42:    */     }
/*  43: 55 */     return this.interfaces;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean subtypeOf(CtClass clazz)
/*  47:    */     throws NotFoundException
/*  48:    */   {
/*  49: 59 */     if (super.subtypeOf(clazz)) {
/*  50: 60 */       return true;
/*  51:    */     }
/*  52: 62 */     String cname = clazz.getName();
/*  53: 63 */     if ((cname.equals("java.lang.Object")) || (cname.equals("java.lang.Cloneable")) || (cname.equals("java.io.Serializable"))) {
/*  54: 66 */       return true;
/*  55:    */     }
/*  56: 68 */     return (clazz.isArray()) && (getComponentType().subtypeOf(clazz.getComponentType()));
/*  57:    */   }
/*  58:    */   
/*  59:    */   public CtClass getComponentType()
/*  60:    */     throws NotFoundException
/*  61:    */   {
/*  62: 73 */     String name = getName();
/*  63: 74 */     return this.pool.get(name.substring(0, name.length() - 2));
/*  64:    */   }
/*  65:    */   
/*  66:    */   public CtClass getSuperclass()
/*  67:    */     throws NotFoundException
/*  68:    */   {
/*  69: 78 */     return this.pool.get("java.lang.Object");
/*  70:    */   }
/*  71:    */   
/*  72:    */   public CtMethod[] getMethods()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76: 83 */       return getSuperclass().getMethods();
/*  77:    */     }
/*  78:    */     catch (NotFoundException e) {}
/*  79: 86 */     return super.getMethods();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public CtMethod getMethod(String name, String desc)
/*  83:    */     throws NotFoundException
/*  84:    */   {
/*  85: 93 */     return getSuperclass().getMethod(name, desc);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public CtConstructor[] getConstructors()
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92: 98 */       return getSuperclass().getConstructors();
/*  93:    */     }
/*  94:    */     catch (NotFoundException e) {}
/*  95:101 */     return super.getConstructors();
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtArray
 * JD-Core Version:    0.7.0.1
 */