/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ public class ObjectType
/*  4:   */   extends AnyType
/*  5:   */   implements BasicType
/*  6:   */ {
/*  7:34 */   public static final ObjectType INSTANCE = new ObjectType();
/*  8:   */   
/*  9:   */   public ObjectType()
/* 10:   */   {
/* 11:37 */     super(StringType.INSTANCE, SerializableType.INSTANCE);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getName()
/* 15:   */   {
/* 16:41 */     return "object";
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String[] getRegistrationKeys()
/* 20:   */   {
/* 21:45 */     return new String[] { getName(), Object.class.getName() };
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ObjectType
 * JD-Core Version:    0.7.0.1
 */