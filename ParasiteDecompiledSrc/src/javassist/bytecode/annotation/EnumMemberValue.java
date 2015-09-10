/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import javassist.ClassPool;
/*   7:    */ import javassist.bytecode.ConstPool;
/*   8:    */ import javassist.bytecode.Descriptor;
/*   9:    */ 
/*  10:    */ public class EnumMemberValue
/*  11:    */   extends MemberValue
/*  12:    */ {
/*  13:    */   int typeIndex;
/*  14:    */   int valueIndex;
/*  15:    */   
/*  16:    */   public EnumMemberValue(int type, int value, ConstPool cp)
/*  17:    */   {
/*  18: 44 */     super('e', cp);
/*  19: 45 */     this.typeIndex = type;
/*  20: 46 */     this.valueIndex = value;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public EnumMemberValue(ConstPool cp)
/*  24:    */   {
/*  25: 54 */     super('e', cp);
/*  26: 55 */     this.typeIndex = (this.valueIndex = 0);
/*  27:    */   }
/*  28:    */   
/*  29:    */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/*  30:    */     throws ClassNotFoundException
/*  31:    */   {
/*  32:    */     try
/*  33:    */     {
/*  34: 62 */       return getType(cl).getField(getValue()).get(null);
/*  35:    */     }
/*  36:    */     catch (NoSuchFieldException e)
/*  37:    */     {
/*  38: 65 */       throw new ClassNotFoundException(getType() + "." + getValue());
/*  39:    */     }
/*  40:    */     catch (IllegalAccessException e)
/*  41:    */     {
/*  42: 68 */       throw new ClassNotFoundException(getType() + "." + getValue());
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   Class getType(ClassLoader cl)
/*  47:    */     throws ClassNotFoundException
/*  48:    */   {
/*  49: 73 */     return loadClass(cl, getType());
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getType()
/*  53:    */   {
/*  54: 82 */     return Descriptor.toClassName(this.cp.getUtf8Info(this.typeIndex));
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setType(String typename)
/*  58:    */   {
/*  59: 91 */     this.typeIndex = this.cp.addUtf8Info(Descriptor.of(typename));
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getValue()
/*  63:    */   {
/*  64: 98 */     return this.cp.getUtf8Info(this.valueIndex);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setValue(String name)
/*  68:    */   {
/*  69:105 */     this.valueIndex = this.cp.addUtf8Info(name);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String toString()
/*  73:    */   {
/*  74:109 */     return getType() + "." + getValue();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void write(AnnotationsWriter writer)
/*  78:    */     throws IOException
/*  79:    */   {
/*  80:116 */     writer.enumConstValue(this.cp.getUtf8Info(this.typeIndex), getValue());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void accept(MemberValueVisitor visitor)
/*  84:    */   {
/*  85:123 */     visitor.visitEnumMemberValue(this);
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.EnumMemberValue
 * JD-Core Version:    0.7.0.1
 */