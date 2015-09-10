/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ 
/*   8:    */ public class FloatMemberValue
/*   9:    */   extends MemberValue
/*  10:    */ {
/*  11:    */   int valueIndex;
/*  12:    */   
/*  13:    */   public FloatMemberValue(int index, ConstPool cp)
/*  14:    */   {
/*  15: 40 */     super('F', cp);
/*  16: 41 */     this.valueIndex = index;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public FloatMemberValue(float f, ConstPool cp)
/*  20:    */   {
/*  21: 50 */     super('F', cp);
/*  22: 51 */     setValue(f);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public FloatMemberValue(ConstPool cp)
/*  26:    */   {
/*  27: 58 */     super('F', cp);
/*  28: 59 */     setValue(0.0F);
/*  29:    */   }
/*  30:    */   
/*  31:    */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/*  32:    */   {
/*  33: 63 */     return new Float(getValue());
/*  34:    */   }
/*  35:    */   
/*  36:    */   Class getType(ClassLoader cl)
/*  37:    */   {
/*  38: 67 */     return Float.TYPE;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public float getValue()
/*  42:    */   {
/*  43: 74 */     return this.cp.getFloatInfo(this.valueIndex);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setValue(float newValue)
/*  47:    */   {
/*  48: 81 */     this.valueIndex = this.cp.addFloatInfo(newValue);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53: 88 */     return Float.toString(getValue());
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void write(AnnotationsWriter writer)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59: 95 */     writer.constValueIndex(getValue());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void accept(MemberValueVisitor visitor)
/*  63:    */   {
/*  64:102 */     visitor.visitFloatMemberValue(this);
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.FloatMemberValue
 * JD-Core Version:    0.7.0.1
 */