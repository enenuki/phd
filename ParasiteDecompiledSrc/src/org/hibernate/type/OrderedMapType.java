/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.LinkedHashMap;
/*  4:   */ 
/*  5:   */ public class OrderedMapType
/*  6:   */   extends MapType
/*  7:   */ {
/*  8:   */   public OrderedMapType(TypeFactory.TypeScope typeScope, String role, String propertyRef, boolean isEmbeddedInXML)
/*  9:   */   {
/* 10:41 */     super(typeScope, role, propertyRef, isEmbeddedInXML);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Object instantiate(int anticipatedSize)
/* 14:   */   {
/* 15:48 */     return anticipatedSize > 0 ? new LinkedHashMap(anticipatedSize) : new LinkedHashMap();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.OrderedMapType
 * JD-Core Version:    0.7.0.1
 */