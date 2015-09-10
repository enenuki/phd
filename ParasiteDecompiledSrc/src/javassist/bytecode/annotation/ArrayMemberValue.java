/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Array;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import javassist.ClassPool;
/*   7:    */ import javassist.bytecode.ConstPool;
/*   8:    */ 
/*   9:    */ public class ArrayMemberValue
/*  10:    */   extends MemberValue
/*  11:    */ {
/*  12:    */   MemberValue type;
/*  13:    */   MemberValue[] values;
/*  14:    */   
/*  15:    */   public ArrayMemberValue(ConstPool cp)
/*  16:    */   {
/*  17: 37 */     super('[', cp);
/*  18: 38 */     this.type = null;
/*  19: 39 */     this.values = null;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ArrayMemberValue(MemberValue t, ConstPool cp)
/*  23:    */   {
/*  24: 48 */     super('[', cp);
/*  25: 49 */     this.type = t;
/*  26: 50 */     this.values = null;
/*  27:    */   }
/*  28:    */   
/*  29:    */   Object getValue(ClassLoader cl, ClassPool cp, Method method)
/*  30:    */     throws ClassNotFoundException
/*  31:    */   {
/*  32: 56 */     if (this.values == null) {
/*  33: 57 */       throw new ClassNotFoundException("no array elements found: " + method.getName());
/*  34:    */     }
/*  35: 60 */     int size = this.values.length;
/*  36:    */     Class clazz;
/*  37: 62 */     if (this.type == null)
/*  38:    */     {
/*  39: 63 */       Class clazz = method.getReturnType().getComponentType();
/*  40: 64 */       if ((clazz == null) || (size > 0)) {
/*  41: 65 */         throw new ClassNotFoundException("broken array type: " + method.getName());
/*  42:    */       }
/*  43:    */     }
/*  44:    */     else
/*  45:    */     {
/*  46: 69 */       clazz = this.type.getType(cl);
/*  47:    */     }
/*  48: 71 */     Object a = Array.newInstance(clazz, size);
/*  49: 72 */     for (int i = 0; i < size; i++) {
/*  50: 73 */       Array.set(a, i, this.values[i].getValue(cl, cp, method));
/*  51:    */     }
/*  52: 75 */     return a;
/*  53:    */   }
/*  54:    */   
/*  55:    */   Class getType(ClassLoader cl)
/*  56:    */     throws ClassNotFoundException
/*  57:    */   {
/*  58: 79 */     if (this.type == null) {
/*  59: 80 */       throw new ClassNotFoundException("no array type specified");
/*  60:    */     }
/*  61: 82 */     Object a = Array.newInstance(this.type.getType(cl), 0);
/*  62: 83 */     return a.getClass();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public MemberValue getType()
/*  66:    */   {
/*  67: 92 */     return this.type;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public MemberValue[] getValue()
/*  71:    */   {
/*  72: 99 */     return this.values;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setValue(MemberValue[] elements)
/*  76:    */   {
/*  77:106 */     this.values = elements;
/*  78:107 */     if ((elements != null) && (elements.length > 0)) {
/*  79:108 */       this.type = elements[0];
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String toString()
/*  84:    */   {
/*  85:115 */     StringBuffer buf = new StringBuffer("{");
/*  86:116 */     if (this.values != null) {
/*  87:117 */       for (int i = 0; i < this.values.length; i++)
/*  88:    */       {
/*  89:118 */         buf.append(this.values[i].toString());
/*  90:119 */         if (i + 1 < this.values.length) {
/*  91:120 */           buf.append(", ");
/*  92:    */         }
/*  93:    */       }
/*  94:    */     }
/*  95:124 */     buf.append("}");
/*  96:125 */     return buf.toString();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void write(AnnotationsWriter writer)
/* 100:    */     throws IOException
/* 101:    */   {
/* 102:132 */     int num = this.values.length;
/* 103:133 */     writer.arrayValue(num);
/* 104:134 */     for (int i = 0; i < num; i++) {
/* 105:135 */       this.values[i].write(writer);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void accept(MemberValueVisitor visitor)
/* 110:    */   {
/* 111:142 */     visitor.visitArrayMemberValue(this);
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.ArrayMemberValue
 * JD-Core Version:    0.7.0.1
 */