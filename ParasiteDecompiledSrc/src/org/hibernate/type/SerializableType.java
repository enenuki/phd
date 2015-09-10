/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.type.descriptor.java.SerializableTypeDescriptor;
/*  5:   */ import org.hibernate.type.descriptor.sql.VarbinaryTypeDescriptor;
/*  6:   */ 
/*  7:   */ public class SerializableType<T extends Serializable>
/*  8:   */   extends AbstractSingleColumnStandardBasicType<T>
/*  9:   */ {
/* 10:46 */   public static final SerializableType<Serializable> INSTANCE = new SerializableType(Serializable.class);
/* 11:   */   private final Class<T> serializableClass;
/* 12:   */   
/* 13:   */   public SerializableType(Class<T> serializableClass)
/* 14:   */   {
/* 15:51 */     super(VarbinaryTypeDescriptor.INSTANCE, new SerializableTypeDescriptor(serializableClass));
/* 16:52 */     this.serializableClass = serializableClass;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:56 */     return this.serializableClass == Serializable.class ? "serializable" : this.serializableClass.getName();
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.SerializableType
 * JD-Core Version:    0.7.0.1
 */