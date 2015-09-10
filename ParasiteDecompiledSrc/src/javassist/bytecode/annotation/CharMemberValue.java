/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ 
/*   8:    */ public class CharMemberValue
/*   9:    */   extends MemberValue
/*  10:    */ {
/*  11:    */   int valueIndex;
/*  12:    */   
/*  13:    */   public CharMemberValue(int index, ConstPool cp)
/*  14:    */   {
/*  15: 39 */     super('C', cp);
/*  16: 40 */     this.valueIndex = index;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public CharMemberValue(char c, ConstPool cp)
/*  20:    */   {
/*  21: 49 */     super('C', cp);
/*  22: 50 */     setValue(c);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public CharMemberValue(ConstPool cp)
/*  26:    */   {
/*  27: 57 */     super('C', cp);
/*  28: 58 */     setValue('\000');
/*  29:    */   }
/*  30:    */   
/*  31:    */   Object getValue(ClassLoader cl, ClassPool cp, Method m)
/*  32:    */   {
/*  33: 62 */     return new Character(getValue());
/*  34:    */   }
/*  35:    */   
/*  36:    */   Class getType(ClassLoader cl)
/*  37:    */   {
/*  38: 66 */     return Character.TYPE;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public char getValue()
/*  42:    */   {
/*  43: 73 */     return (char)this.cp.getIntegerInfo(this.valueIndex);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setValue(char newValue)
/*  47:    */   {
/*  48: 80 */     this.valueIndex = this.cp.addIntegerInfo(newValue);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53: 87 */     return Character.toString(getValue());
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
/*  64:101 */     visitor.visitCharMemberValue(this);
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.CharMemberValue
 * JD-Core Version:    0.7.0.1
 */