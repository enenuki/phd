/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ 
/*   8:    */ public class ByteMemberValue
/*   9:    */   extends MemberValue
/*  10:    */ {
/*  11:    */   int valueIndex;
/*  12:    */   
/*  13:    */   public ByteMemberValue(int index, ConstPool cp)
/*  14:    */   {
/*  15: 38 */     super('B', cp);
/*  16: 39 */     this.valueIndex = index;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ByteMemberValue(byte b, ConstPool cp)
/*  20:    */   {
/*  21: 48 */     super('B', cp);
/*  22: 49 */     setValue(b);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public ByteMemberValue(ConstPool cp)
/*  26:    */   {
/*  27: 56 */     super('B', cp);
/*  28: 57 */     setValue((byte)0);
/*  29:    */   }
/*  30:    */   
/*  31:    */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/*  32:    */   {
/*  33: 61 */     return new Byte(getValue());
/*  34:    */   }
/*  35:    */   
/*  36:    */   Class getType(ClassLoader cl)
/*  37:    */   {
/*  38: 65 */     return Byte.TYPE;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public byte getValue()
/*  42:    */   {
/*  43: 72 */     return (byte)this.cp.getIntegerInfo(this.valueIndex);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setValue(byte newValue)
/*  47:    */   {
/*  48: 79 */     this.valueIndex = this.cp.addIntegerInfo(newValue);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53: 86 */     return Byte.toString(getValue());
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
/*  64:100 */     visitor.visitByteMemberValue(this);
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.ByteMemberValue
 * JD-Core Version:    0.7.0.1
 */