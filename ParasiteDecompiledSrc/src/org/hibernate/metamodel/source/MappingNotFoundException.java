/*  1:   */ package org.hibernate.metamodel.source;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.jaxb.Origin;
/*  4:   */ 
/*  5:   */ public class MappingNotFoundException
/*  6:   */   extends MappingException
/*  7:   */ {
/*  8:   */   public MappingNotFoundException(String message, Origin origin)
/*  9:   */   {
/* 10:34 */     super(message, origin);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public MappingNotFoundException(Origin origin)
/* 14:   */   {
/* 15:38 */     super(String.format("Mapping (%s) not found : %s", new Object[] { origin.getType(), origin.getName() }), origin);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public MappingNotFoundException(String message, Throwable root, Origin origin)
/* 19:   */   {
/* 20:42 */     super(message, root, origin);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public MappingNotFoundException(Throwable root, Origin origin)
/* 24:   */   {
/* 25:46 */     super(String.format("Mapping (%s) not found : %s", new Object[] { origin.getType(), origin.getName() }), root, origin);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.MappingNotFoundException
 * JD-Core Version:    0.7.0.1
 */