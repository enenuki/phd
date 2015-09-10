/*  1:   */ package org.hibernate.engine.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.EntityMode;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public final class TypedValue
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private final Type type;
/* 11:   */   private final Object value;
/* 12:   */   private final EntityMode entityMode;
/* 13:   */   
/* 14:   */   public TypedValue(Type type, Object value)
/* 15:   */   {
/* 16:43 */     this(type, value, EntityMode.POJO);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public TypedValue(Type type, Object value, EntityMode entityMode)
/* 20:   */   {
/* 21:47 */     this.type = type;
/* 22:48 */     this.value = value;
/* 23:49 */     this.entityMode = entityMode;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object getValue()
/* 27:   */   {
/* 28:53 */     return this.value;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Type getType()
/* 32:   */   {
/* 33:57 */     return this.type;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String toString()
/* 37:   */   {
/* 38:61 */     return this.value == null ? "null" : this.value.toString();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int hashCode()
/* 42:   */   {
/* 43:69 */     return this.value == null ? 0 : this.type.getHashCode(this.value);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean equals(Object other)
/* 47:   */   {
/* 48:73 */     if (!(other instanceof TypedValue)) {
/* 49:73 */       return false;
/* 50:   */     }
/* 51:74 */     TypedValue that = (TypedValue)other;
/* 52:   */     
/* 53:   */ 
/* 54:77 */     return (this.type.getReturnedClass() == that.type.getReturnedClass()) && (this.type.isEqual(that.value, this.value));
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.TypedValue
 * JD-Core Version:    0.7.0.1
 */