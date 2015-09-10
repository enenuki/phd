package javassist.bytecode.annotation;

public abstract interface MemberValueVisitor
{
  public abstract void visitAnnotationMemberValue(AnnotationMemberValue paramAnnotationMemberValue);
  
  public abstract void visitArrayMemberValue(ArrayMemberValue paramArrayMemberValue);
  
  public abstract void visitBooleanMemberValue(BooleanMemberValue paramBooleanMemberValue);
  
  public abstract void visitByteMemberValue(ByteMemberValue paramByteMemberValue);
  
  public abstract void visitCharMemberValue(CharMemberValue paramCharMemberValue);
  
  public abstract void visitDoubleMemberValue(DoubleMemberValue paramDoubleMemberValue);
  
  public abstract void visitEnumMemberValue(EnumMemberValue paramEnumMemberValue);
  
  public abstract void visitFloatMemberValue(FloatMemberValue paramFloatMemberValue);
  
  public abstract void visitIntegerMemberValue(IntegerMemberValue paramIntegerMemberValue);
  
  public abstract void visitLongMemberValue(LongMemberValue paramLongMemberValue);
  
  public abstract void visitShortMemberValue(ShortMemberValue paramShortMemberValue);
  
  public abstract void visitStringMemberValue(StringMemberValue paramStringMemberValue);
  
  public abstract void visitClassMemberValue(ClassMemberValue paramClassMemberValue);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.MemberValueVisitor
 * JD-Core Version:    0.7.0.1
 */