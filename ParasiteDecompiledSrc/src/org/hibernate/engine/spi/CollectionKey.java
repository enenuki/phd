/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import org.hibernate.EntityMode;
/*   8:    */ import org.hibernate.persister.collection.CollectionPersister;
/*   9:    */ import org.hibernate.persister.entity.EntityPersister;
/*  10:    */ import org.hibernate.pretty.MessageHelper;
/*  11:    */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  12:    */ import org.hibernate.type.Type;
/*  13:    */ 
/*  14:    */ public final class CollectionKey
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:    */   private final String role;
/*  18:    */   private final Serializable key;
/*  19:    */   private final Type keyType;
/*  20:    */   private final SessionFactoryImplementor factory;
/*  21:    */   private final int hashCode;
/*  22:    */   private EntityMode entityMode;
/*  23:    */   
/*  24:    */   public CollectionKey(CollectionPersister persister, Serializable key)
/*  25:    */   {
/*  26: 50 */     this(persister.getRole(), key, persister.getKeyType(), persister.getOwnerEntityPersister().getEntityMetamodel().getEntityMode(), persister.getFactory());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public CollectionKey(CollectionPersister persister, Serializable key, EntityMode em)
/*  30:    */   {
/*  31: 60 */     this(persister.getRole(), key, persister.getKeyType(), em, persister.getFactory());
/*  32:    */   }
/*  33:    */   
/*  34:    */   private CollectionKey(String role, Serializable key, Type keyType, EntityMode entityMode, SessionFactoryImplementor factory)
/*  35:    */   {
/*  36: 69 */     this.role = role;
/*  37: 70 */     this.key = key;
/*  38: 71 */     this.keyType = keyType;
/*  39: 72 */     this.entityMode = entityMode;
/*  40: 73 */     this.factory = factory;
/*  41: 74 */     this.hashCode = generateHashCode();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean equals(Object other)
/*  45:    */   {
/*  46: 78 */     CollectionKey that = (CollectionKey)other;
/*  47: 79 */     return (that.role.equals(this.role)) && (this.keyType.isEqual(that.key, this.key, this.factory));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int generateHashCode()
/*  51:    */   {
/*  52: 84 */     int result = 17;
/*  53: 85 */     result = 37 * result + this.role.hashCode();
/*  54: 86 */     result = 37 * result + this.keyType.getHashCode(this.key, this.factory);
/*  55: 87 */     return result;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int hashCode()
/*  59:    */   {
/*  60: 91 */     return this.hashCode;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getRole()
/*  64:    */   {
/*  65: 95 */     return this.role;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Serializable getKey()
/*  69:    */   {
/*  70: 99 */     return this.key;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String toString()
/*  74:    */   {
/*  75:103 */     return "CollectionKey" + MessageHelper.collectionInfoString(this.factory.getCollectionPersister(this.role), this.key, this.factory);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void serialize(ObjectOutputStream oos)
/*  79:    */     throws IOException
/*  80:    */   {
/*  81:115 */     oos.writeObject(this.role);
/*  82:116 */     oos.writeObject(this.key);
/*  83:117 */     oos.writeObject(this.keyType);
/*  84:118 */     oos.writeObject(this.entityMode.toString());
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static CollectionKey deserialize(ObjectInputStream ois, SessionImplementor session)
/*  88:    */     throws IOException, ClassNotFoundException
/*  89:    */   {
/*  90:134 */     return new CollectionKey((String)ois.readObject(), (Serializable)ois.readObject(), (Type)ois.readObject(), EntityMode.parse((String)ois.readObject()), session == null ? null : session.getFactory());
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.CollectionKey
 * JD-Core Version:    0.7.0.1
 */