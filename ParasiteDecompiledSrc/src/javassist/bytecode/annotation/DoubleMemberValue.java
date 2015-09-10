/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ 
/*   8:    */ public class DoubleMemberValue
/*   9:    */   extends MemberValue
/*  10:    */ {
/*  11:    */   int valueIndex;
/*  12:    */   
/*  13:    */   public DoubleMemberValue(int index, ConstPool cp)
/*  14:    */   {
/*  15: 40 */     super('D', cp);
/*  16: 41 */     this.valueIndex = index;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public DoubleMemberValue(double d, ConstPool cp)
/*  20:    */   {
/*  21: 50 */     super('D', cp);
/*  22: 51 */     setValue(d);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public DoubleMemberValue(ConstPool cp)
/*  26:    */   {
/*  27: 58 */     super('D', cp);
/*  28: 59 */     setValue(0.0D);
/*  29:    */   }
/*  30:    */   
/*  31:    */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/*  32:    */   {
/*  33: 63 */     return new Double(getValue());
/*  34:    */   }
/*  35:    */   
/*  36:    */   Class getType(ClassLoader cl)
/*  37:    */   {
/*  38: 67 */     return Double.TYPE;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public double getValue()
/*  42:    */   {
/*  43: 74 */     return this.cp.getDoubleInfo(this.valueIndex);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setValue(double newValue)
/*  47:    */   {
/*  48: 81 */     this.valueIndex = this.cp.addDoubleInfo(newValue);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53: 88 */     return Double.toString(getValue());
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
/*  64:102 */     visitor.visitDoubleMemberValue(this);
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.DoubleMemberValue
 * JD-Core Version:    0.7.0.1
 */