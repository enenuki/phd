/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class DuplicateMappingException
/*  4:   */   extends MappingException
/*  5:   */ {
/*  6:   */   private final String name;
/*  7:   */   private final String type;
/*  8:   */   
/*  9:   */   public static enum Type
/* 10:   */   {
/* 11:35 */     ENTITY,  TABLE,  PROPERTY,  COLUMN;
/* 12:   */     
/* 13:   */     private Type() {}
/* 14:   */   }
/* 15:   */   
/* 16:   */   public DuplicateMappingException(Type type, String name)
/* 17:   */   {
/* 18:45 */     this(type.name(), name);
/* 19:   */   }
/* 20:   */   
/* 21:   */   @Deprecated
/* 22:   */   public DuplicateMappingException(String type, String name)
/* 23:   */   {
/* 24:50 */     this("Duplicate " + type + " mapping " + name, type, name);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public DuplicateMappingException(String customMessage, Type type, String name)
/* 28:   */   {
/* 29:54 */     this(customMessage, type.name(), name);
/* 30:   */   }
/* 31:   */   
/* 32:   */   @Deprecated
/* 33:   */   public DuplicateMappingException(String customMessage, String type, String name)
/* 34:   */   {
/* 35:59 */     super(customMessage);
/* 36:60 */     this.type = type;
/* 37:61 */     this.name = name;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getType()
/* 41:   */   {
/* 42:65 */     return this.type;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String getName()
/* 46:   */   {
/* 47:69 */     return this.name;
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.DuplicateMappingException
 * JD-Core Version:    0.7.0.1
 */