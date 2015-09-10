/*   1:    */ package org.hibernate.annotations.common.reflection.java;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.AccessibleObject;
/*   4:    */ import java.lang.reflect.AnnotatedElement;
/*   5:    */ import java.lang.reflect.Field;
/*   6:    */ import java.lang.reflect.Member;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.lang.reflect.Type;
/*   9:    */ import java.util.Collection;
/*  10:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  11:    */ import org.hibernate.annotations.common.reflection.XMember;
/*  12:    */ import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
/*  13:    */ 
/*  14:    */ public abstract class JavaXMember
/*  15:    */   extends JavaXAnnotatedElement
/*  16:    */   implements XMember
/*  17:    */ {
/*  18:    */   private final Type type;
/*  19:    */   private final TypeEnvironment env;
/*  20:    */   private final JavaXType xType;
/*  21:    */   
/*  22:    */   protected static Type typeOf(Member member, TypeEnvironment env)
/*  23:    */   {
/*  24: 49 */     if ((member instanceof Field)) {
/*  25: 50 */       return env.bind(((Field)member).getGenericType());
/*  26:    */     }
/*  27: 52 */     if ((member instanceof Method)) {
/*  28: 53 */       return env.bind(((Method)member).getGenericReturnType());
/*  29:    */     }
/*  30: 55 */     throw new IllegalArgumentException("Member " + member + " is neither a field nor a method");
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected JavaXMember(Member member, Type type, TypeEnvironment env, JavaReflectionManager factory, JavaXType xType)
/*  34:    */   {
/*  35: 59 */     super((AnnotatedElement)member, factory);
/*  36: 60 */     this.type = type;
/*  37: 61 */     this.env = env;
/*  38: 62 */     this.xType = xType;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public XClass getType()
/*  42:    */   {
/*  43: 66 */     return this.xType.getType();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public abstract String getName();
/*  47:    */   
/*  48:    */   protected Type getJavaType()
/*  49:    */   {
/*  50: 72 */     return this.env.bind(this.type);
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected TypeEnvironment getTypeEnvironment()
/*  54:    */   {
/*  55: 76 */     return this.env;
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected Member getMember()
/*  59:    */   {
/*  60: 80 */     return (Member)toAnnotatedElement();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Class<? extends Collection> getCollectionClass()
/*  64:    */   {
/*  65: 84 */     return this.xType.getCollectionClass();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public XClass getClassOrElementClass()
/*  69:    */   {
/*  70: 88 */     return this.xType.getClassOrElementClass();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public XClass getElementClass()
/*  74:    */   {
/*  75: 92 */     return this.xType.getElementClass();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public XClass getMapKey()
/*  79:    */   {
/*  80: 96 */     return this.xType.getMapKey();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isArray()
/*  84:    */   {
/*  85:100 */     return this.xType.isArray();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isCollection()
/*  89:    */   {
/*  90:104 */     return this.xType.isCollection();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getModifiers()
/*  94:    */   {
/*  95:108 */     return getMember().getModifiers();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public final boolean isTypeResolved()
/*  99:    */   {
/* 100:112 */     return this.xType.isResolved();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setAccessible(boolean accessible)
/* 104:    */   {
/* 105:116 */     ((AccessibleObject)getMember()).setAccessible(accessible);
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaXMember
 * JD-Core Version:    0.7.0.1
 */