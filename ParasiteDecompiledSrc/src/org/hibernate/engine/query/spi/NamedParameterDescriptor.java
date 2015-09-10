/*  1:   */ package org.hibernate.engine.query.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ public class NamedParameterDescriptor
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private final String name;
/* 10:   */   private Type expectedType;
/* 11:   */   private final int[] sourceLocations;
/* 12:   */   private final boolean jpaStyle;
/* 13:   */   
/* 14:   */   public NamedParameterDescriptor(String name, Type expectedType, int[] sourceLocations, boolean jpaStyle)
/* 15:   */   {
/* 16:42 */     this.name = name;
/* 17:43 */     this.expectedType = expectedType;
/* 18:44 */     this.sourceLocations = sourceLocations;
/* 19:45 */     this.jpaStyle = jpaStyle;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getName()
/* 23:   */   {
/* 24:49 */     return this.name;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Type getExpectedType()
/* 28:   */   {
/* 29:53 */     return this.expectedType;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int[] getSourceLocations()
/* 33:   */   {
/* 34:57 */     return this.sourceLocations;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean isJpaStyle()
/* 38:   */   {
/* 39:61 */     return this.jpaStyle;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void resetExpectedType(Type type)
/* 43:   */   {
/* 44:65 */     this.expectedType = type;
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.NamedParameterDescriptor
 * JD-Core Version:    0.7.0.1
 */