/*  1:   */ package org.hibernate.tuple;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ public abstract class Property
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private String name;
/* 10:   */   private String node;
/* 11:   */   private Type type;
/* 12:   */   
/* 13:   */   protected Property(String name, String node, Type type)
/* 14:   */   {
/* 15:50 */     this.name = name;
/* 16:51 */     this.node = node;
/* 17:52 */     this.type = type;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getName()
/* 21:   */   {
/* 22:56 */     return this.name;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getNode()
/* 26:   */   {
/* 27:60 */     return this.node;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Type getType()
/* 31:   */   {
/* 32:64 */     return this.type;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String toString()
/* 36:   */   {
/* 37:68 */     return "Property(" + this.name + ':' + this.type.getName() + ')';
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.Property
 * JD-Core Version:    0.7.0.1
 */