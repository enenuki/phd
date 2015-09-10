/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import java.io.ObjectStreamException;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.Map;
/*  7:   */ 
/*  8:   */ public class ResultColumnReferenceStrategy
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:38 */   private static final Map INSTANCES = new HashMap();
/* 12:46 */   public static final ResultColumnReferenceStrategy SOURCE = new ResultColumnReferenceStrategy("source");
/* 13:55 */   public static final ResultColumnReferenceStrategy ALIAS = new ResultColumnReferenceStrategy("alias");
/* 14:64 */   public static final ResultColumnReferenceStrategy ORDINAL = new ResultColumnReferenceStrategy("ordinal");
/* 15:   */   private final String name;
/* 16:   */   
/* 17:   */   static
/* 18:   */   {
/* 19:67 */     INSTANCES.put(SOURCE.name, SOURCE);
/* 20:68 */     INSTANCES.put(ALIAS.name, ALIAS);
/* 21:69 */     INSTANCES.put(ORDINAL.name, ORDINAL);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ResultColumnReferenceStrategy(String name)
/* 25:   */   {
/* 26:75 */     this.name = name;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String toString()
/* 30:   */   {
/* 31:79 */     return this.name;
/* 32:   */   }
/* 33:   */   
/* 34:   */   private Object readResolve()
/* 35:   */     throws ObjectStreamException
/* 36:   */   {
/* 37:83 */     return parse(this.name);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static ResultColumnReferenceStrategy parse(String name)
/* 41:   */   {
/* 42:87 */     return (ResultColumnReferenceStrategy)INSTANCES.get(name);
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.ResultColumnReferenceStrategy
 * JD-Core Version:    0.7.0.1
 */