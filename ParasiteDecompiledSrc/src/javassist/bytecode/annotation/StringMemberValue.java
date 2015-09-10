/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ 
/*   8:    */ public class StringMemberValue
/*   9:    */   extends MemberValue
/*  10:    */ {
/*  11:    */   int valueIndex;
/*  12:    */   
/*  13:    */   public StringMemberValue(int index, ConstPool cp)
/*  14:    */   {
/*  15: 39 */     super('s', cp);
/*  16: 40 */     this.valueIndex = index;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public StringMemberValue(String str, ConstPool cp)
/*  20:    */   {
/*  21: 49 */     super('s', cp);
/*  22: 50 */     setValue(str);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public StringMemberValue(ConstPool cp)
/*  26:    */   {
/*  27: 57 */     super('s', cp);
/*  28: 58 */     setValue("");
/*  29:    */   }
/*  30:    */   
/*  31:    */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/*  32:    */   {
/*  33: 62 */     return getValue();
/*  34:    */   }
/*  35:    */   
/*  36:    */   Class getType(ClassLoader cl)
/*  37:    */   {
/*  38: 66 */     return String.class;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getValue()
/*  42:    */   {
/*  43: 73 */     return this.cp.getUtf8Info(this.valueIndex);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setValue(String newValue)
/*  47:    */   {
/*  48: 80 */     this.valueIndex = this.cp.addUtf8Info(newValue);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53: 87 */     return "\"" + getValue() + "\"";
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void write(AnnotationsWriter writer)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59: 94 */     writer.constValueIndex(getValue());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void accept(MemberValueVisitor visitor)
/*  63:    */   {
/*  64:101 */     visitor.visitStringMemberValue(this);
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.StringMemberValue
 * JD-Core Version:    0.7.0.1
 */