/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ 
/*   8:    */ public class IntegerMemberValue
/*   9:    */   extends MemberValue
/*  10:    */ {
/*  11:    */   int valueIndex;
/*  12:    */   
/*  13:    */   public IntegerMemberValue(int index, ConstPool cp)
/*  14:    */   {
/*  15: 39 */     super('I', cp);
/*  16: 40 */     this.valueIndex = index;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public IntegerMemberValue(ConstPool cp, int value)
/*  20:    */   {
/*  21: 55 */     super('I', cp);
/*  22: 56 */     setValue(value);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public IntegerMemberValue(ConstPool cp)
/*  26:    */   {
/*  27: 63 */     super('I', cp);
/*  28: 64 */     setValue(0);
/*  29:    */   }
/*  30:    */   
/*  31:    */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/*  32:    */   {
/*  33: 68 */     return new Integer(getValue());
/*  34:    */   }
/*  35:    */   
/*  36:    */   Class getType(ClassLoader cl)
/*  37:    */   {
/*  38: 72 */     return Integer.TYPE;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getValue()
/*  42:    */   {
/*  43: 79 */     return this.cp.getIntegerInfo(this.valueIndex);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setValue(int newValue)
/*  47:    */   {
/*  48: 86 */     this.valueIndex = this.cp.addIntegerInfo(newValue);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53: 93 */     return Integer.toString(getValue());
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void write(AnnotationsWriter writer)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:100 */     writer.constValueIndex(getValue());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void accept(MemberValueVisitor visitor)
/*  63:    */   {
/*  64:107 */     visitor.visitIntegerMemberValue(this);
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.IntegerMemberValue
 * JD-Core Version:    0.7.0.1
 */