/*  1:   */ package org.hibernate.engine.query.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ public class OrdinalParameterDescriptor
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private final int ordinalPosition;
/* 10:   */   private final Type expectedType;
/* 11:   */   private final int sourceLocation;
/* 12:   */   
/* 13:   */   public OrdinalParameterDescriptor(int ordinalPosition, Type expectedType, int sourceLocation)
/* 14:   */   {
/* 15:39 */     this.ordinalPosition = ordinalPosition;
/* 16:40 */     this.expectedType = expectedType;
/* 17:41 */     this.sourceLocation = sourceLocation;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getOrdinalPosition()
/* 21:   */   {
/* 22:45 */     return this.ordinalPosition;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Type getExpectedType()
/* 26:   */   {
/* 27:49 */     return this.expectedType;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getSourceLocation()
/* 31:   */   {
/* 32:53 */     return this.sourceLocation;
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.OrdinalParameterDescriptor
 * JD-Core Version:    0.7.0.1
 */