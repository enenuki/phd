package org.hibernate.metamodel.source.annotations;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.ResolvedTypeWithMembers;
import org.hibernate.metamodel.source.BindingContext;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.Index;

public abstract interface AnnotationBindingContext
  extends BindingContext
{
  public abstract Index getIndex();
  
  public abstract ClassInfo getClassInfo(String paramString);
  
  public abstract void resolveAllTypes(String paramString);
  
  public abstract ResolvedType getResolvedType(Class<?> paramClass);
  
  public abstract ResolvedTypeWithMembers resolveMemberTypes(ResolvedType paramResolvedType);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.AnnotationBindingContext
 * JD-Core Version:    0.7.0.1
 */