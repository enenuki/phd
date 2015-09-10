/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ 
/*   8:    */ public class LongMemberValue
/*   9:    */   extends MemberValue
/*  10:    */ {
/*  11:    */   int valueIndex;
/*  12:    */   
/*  13:    */   public LongMemberValue(int index, ConstPool cp)
/*  14:    */   {
/*  15: 39 */     super('J', cp);
/*  16: 40 */     this.valueIndex = index;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public LongMemberValue(long j, ConstPool cp)
/*  20:    */   {
/*  21: 49 */     super('J', cp);
/*  22: 50 */     setValue(j);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public LongMemberValue(ConstPool cp)
/*  26:    */   {
/*  27: 57 */     super('J', cp);
/*  28: 58 */     setValue(0L);
/*  29:    */   }
/*  30:    */   
/*  31:    */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/*  32:    */   {
/*  33: 62 */     return new Long(getValue());
/*  34:    */   }
/*  35:    */   
/*  36:    */   Class getType(ClassLoader cl)
/*  37:    */   {
/*  38: 66 */     return Long.TYPE;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public long getValue()
/*  42:    */   {
/*  43: 73 */     return this.cp.getLongInfo(this.valueIndex);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setValue(long newValue)
/*  47:    */   {
/*  48: 80 */     this.valueIndex = this.cp.addLongInfo(newValue);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53: 87 */     return Long.toString(getValue());
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
/*  64:101 */     visitor.visitLongMemberValue(this);
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.LongMemberValue
 * JD-Core Version:    0.7.0.1
 */