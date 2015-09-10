/*  1:   */ package javax.persistence.metamodel;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Member;
/*  4:   */ 
/*  5:   */ public abstract interface Attribute<X, Y>
/*  6:   */ {
/*  7:   */   public abstract String getName();
/*  8:   */   
/*  9:   */   public abstract PersistentAttributeType getPersistentAttributeType();
/* 10:   */   
/* 11:   */   public abstract ManagedType<X> getDeclaringType();
/* 12:   */   
/* 13:   */   public abstract Class<Y> getJavaType();
/* 14:   */   
/* 15:   */   public abstract Member getJavaMember();
/* 16:   */   
/* 17:   */   public abstract boolean isAssociation();
/* 18:   */   
/* 19:   */   public abstract boolean isCollection();
/* 20:   */   
/* 21:   */   public static enum PersistentAttributeType
/* 22:   */   {
/* 23:32 */     MANY_TO_ONE,  ONE_TO_ONE,  BASIC,  EMBEDDED,  MANY_TO_MANY,  ONE_TO_MANY,  ELEMENT_COLLECTION;
/* 24:   */     
/* 25:   */     private PersistentAttributeType() {}
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.metamodel.Attribute
 * JD-Core Version:    0.7.0.1
 */