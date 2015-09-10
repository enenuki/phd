/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.LinkedHashSet;
/*  4:   */ 
/*  5:   */ public class OrderedSetType
/*  6:   */   extends SetType
/*  7:   */ {
/*  8:   */   public OrderedSetType(TypeFactory.TypeScope typeScope, String role, String propertyRef, boolean isEmbeddedInXML)
/*  9:   */   {
/* 10:42 */     super(typeScope, role, propertyRef, isEmbeddedInXML);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Object instantiate(int anticipatedSize)
/* 14:   */   {
/* 15:49 */     return anticipatedSize > 0 ? new LinkedHashSet(anticipatedSize) : new LinkedHashSet();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.OrderedSetType
 * JD-Core Version:    0.7.0.1
 */