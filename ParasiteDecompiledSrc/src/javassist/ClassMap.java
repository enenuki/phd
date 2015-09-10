/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import javassist.bytecode.Descriptor;
/*   5:    */ 
/*   6:    */ public class ClassMap
/*   7:    */   extends HashMap
/*   8:    */ {
/*   9:    */   private ClassMap parent;
/*  10:    */   
/*  11:    */   public ClassMap()
/*  12:    */   {
/*  13: 53 */     this.parent = null;
/*  14:    */   }
/*  15:    */   
/*  16:    */   ClassMap(ClassMap map)
/*  17:    */   {
/*  18: 55 */     this.parent = map;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void put(CtClass oldname, CtClass newname)
/*  22:    */   {
/*  23: 68 */     put(oldname.getName(), newname.getName());
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void put(String oldname, String newname)
/*  27:    */   {
/*  28: 90 */     if (oldname == newname) {
/*  29: 91 */       return;
/*  30:    */     }
/*  31: 93 */     String oldname2 = toJvmName(oldname);
/*  32: 94 */     String s = (String)get(oldname2);
/*  33: 95 */     if ((s == null) || (!s.equals(oldname2))) {
/*  34: 96 */       super.put(oldname2, toJvmName(newname));
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void putIfNone(String oldname, String newname)
/*  39:    */   {
/*  40:109 */     if (oldname == newname) {
/*  41:110 */       return;
/*  42:    */     }
/*  43:112 */     String oldname2 = toJvmName(oldname);
/*  44:113 */     String s = (String)get(oldname2);
/*  45:114 */     if (s == null) {
/*  46:115 */       super.put(oldname2, toJvmName(newname));
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected final void put0(Object oldname, Object newname)
/*  51:    */   {
/*  52:119 */     super.put(oldname, newname);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object get(Object jvmClassName)
/*  56:    */   {
/*  57:133 */     Object found = super.get(jvmClassName);
/*  58:134 */     if ((found == null) && (this.parent != null)) {
/*  59:135 */       return this.parent.get(jvmClassName);
/*  60:    */     }
/*  61:137 */     return found;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void fix(CtClass clazz)
/*  65:    */   {
/*  66:144 */     fix(clazz.getName());
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void fix(String name)
/*  70:    */   {
/*  71:151 */     String name2 = toJvmName(name);
/*  72:152 */     super.put(name2, name2);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static String toJvmName(String classname)
/*  76:    */   {
/*  77:160 */     return Descriptor.toJvmName(classname);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static String toJavaName(String classname)
/*  81:    */   {
/*  82:168 */     return Descriptor.toJavaName(classname);
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.ClassMap
 * JD-Core Version:    0.7.0.1
 */