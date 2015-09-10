/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.Repository;
/*   4:    */ import org.apache.bcel.classfile.AccessFlags;
/*   5:    */ import org.apache.bcel.classfile.JavaClass;
/*   6:    */ 
/*   7:    */ public final class ObjectType
/*   8:    */   extends ReferenceType
/*   9:    */ {
/*  10:    */   private String class_name;
/*  11:    */   
/*  12:    */   public ObjectType(String class_name)
/*  13:    */   {
/*  14: 73 */     super((byte)14, "L" + class_name.replace('.', '/') + ";");
/*  15: 74 */     this.class_name = class_name.replace('/', '.');
/*  16:    */   }
/*  17:    */   
/*  18:    */   public String getClassName()
/*  19:    */   {
/*  20: 79 */     return this.class_name;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int hashCode()
/*  24:    */   {
/*  25: 83 */     return this.class_name.hashCode();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean equals(Object type)
/*  29:    */   {
/*  30: 88 */     return (type instanceof ObjectType) ? ((ObjectType)type).class_name.equals(this.class_name) : false;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean referencesClass()
/*  34:    */   {
/*  35: 97 */     JavaClass jc = Repository.lookupClass(this.class_name);
/*  36: 98 */     if (jc == null) {
/*  37: 99 */       return false;
/*  38:    */     }
/*  39:101 */     return jc.isClass();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean referencesInterface()
/*  43:    */   {
/*  44:109 */     JavaClass jc = Repository.lookupClass(this.class_name);
/*  45:110 */     if (jc == null) {
/*  46:111 */       return false;
/*  47:    */     }
/*  48:113 */     return !jc.isClass();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean subclassOf(ObjectType superclass)
/*  52:    */   {
/*  53:117 */     if ((referencesInterface()) || (superclass.referencesInterface())) {
/*  54:118 */       return false;
/*  55:    */     }
/*  56:120 */     return Repository.instanceOf(this.class_name, superclass.class_name);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean accessibleTo(ObjectType accessor)
/*  60:    */   {
/*  61:127 */     JavaClass jc = Repository.lookupClass(this.class_name);
/*  62:129 */     if (jc.isPublic()) {
/*  63:130 */       return true;
/*  64:    */     }
/*  65:132 */     JavaClass acc = Repository.lookupClass(accessor.class_name);
/*  66:133 */     return acc.getPackageName().equals(jc.getPackageName());
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ObjectType
 * JD-Core Version:    0.7.0.1
 */