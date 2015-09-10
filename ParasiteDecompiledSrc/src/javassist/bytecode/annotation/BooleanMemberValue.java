/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ 
/*   8:    */ public class BooleanMemberValue
/*   9:    */   extends MemberValue
/*  10:    */ {
/*  11:    */   int valueIndex;
/*  12:    */   
/*  13:    */   public BooleanMemberValue(int index, ConstPool cp)
/*  14:    */   {
/*  15: 38 */     super('Z', cp);
/*  16: 39 */     this.valueIndex = index;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public BooleanMemberValue(boolean b, ConstPool cp)
/*  20:    */   {
/*  21: 48 */     super('Z', cp);
/*  22: 49 */     setValue(b);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public BooleanMemberValue(ConstPool cp)
/*  26:    */   {
/*  27: 56 */     super('Z', cp);
/*  28: 57 */     setValue(false);
/*  29:    */   }
/*  30:    */   
/*  31:    */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/*  32:    */   {
/*  33: 61 */     return new Boolean(getValue());
/*  34:    */   }
/*  35:    */   
/*  36:    */   Class getType(ClassLoader cl)
/*  37:    */   {
/*  38: 65 */     return Boolean.TYPE;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean getValue()
/*  42:    */   {
/*  43: 72 */     return this.cp.getIntegerInfo(this.valueIndex) != 0;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setValue(boolean newValue)
/*  47:    */   {
/*  48: 79 */     this.valueIndex = this.cp.addIntegerInfo(newValue ? 1 : 0);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53: 86 */     return getValue() ? "true" : "false";
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void write(AnnotationsWriter writer)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59: 93 */     writer.constValueIndex(getValue());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void accept(MemberValueVisitor visitor)
/*  63:    */   {
/*  64:100 */     visitor.visitBooleanMemberValue(this);
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.BooleanMemberValue
 * JD-Core Version:    0.7.0.1
 */