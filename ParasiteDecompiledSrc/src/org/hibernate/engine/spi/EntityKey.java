/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import org.hibernate.AssertionFailure;
/*   8:    */ import org.hibernate.internal.util.compare.EqualsHelper;
/*   9:    */ import org.hibernate.persister.entity.EntityPersister;
/*  10:    */ import org.hibernate.pretty.MessageHelper;
/*  11:    */ import org.hibernate.type.Type;
/*  12:    */ 
/*  13:    */ public final class EntityKey
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16:    */   private final Serializable identifier;
/*  17:    */   private final String entityName;
/*  18:    */   private final String rootEntityName;
/*  19:    */   private final String tenantId;
/*  20:    */   private final int hashCode;
/*  21:    */   private final Type identifierType;
/*  22:    */   private final boolean isBatchLoadable;
/*  23:    */   private final SessionFactoryImplementor factory;
/*  24:    */   
/*  25:    */   public EntityKey(Serializable id, EntityPersister persister, String tenantId)
/*  26:    */   {
/*  27: 68 */     if (id == null) {
/*  28: 69 */       throw new AssertionFailure("null identifier");
/*  29:    */     }
/*  30: 71 */     this.identifier = id;
/*  31: 72 */     this.rootEntityName = persister.getRootEntityName();
/*  32: 73 */     this.entityName = persister.getEntityName();
/*  33: 74 */     this.tenantId = tenantId;
/*  34:    */     
/*  35: 76 */     this.identifierType = persister.getIdentifierType();
/*  36: 77 */     this.isBatchLoadable = persister.isBatchLoadable();
/*  37: 78 */     this.factory = persister.getFactory();
/*  38: 79 */     this.hashCode = generateHashCode();
/*  39:    */   }
/*  40:    */   
/*  41:    */   private EntityKey(Serializable identifier, String rootEntityName, String entityName, Type identifierType, boolean batchLoadable, SessionFactoryImplementor factory, String tenantId)
/*  42:    */   {
/*  43:101 */     this.identifier = identifier;
/*  44:102 */     this.rootEntityName = rootEntityName;
/*  45:103 */     this.entityName = entityName;
/*  46:104 */     this.identifierType = identifierType;
/*  47:105 */     this.isBatchLoadable = batchLoadable;
/*  48:106 */     this.factory = factory;
/*  49:107 */     this.tenantId = tenantId;
/*  50:108 */     this.hashCode = generateHashCode();
/*  51:    */   }
/*  52:    */   
/*  53:    */   private int generateHashCode()
/*  54:    */   {
/*  55:112 */     int result = 17;
/*  56:113 */     result = 37 * result + this.rootEntityName.hashCode();
/*  57:114 */     result = 37 * result + this.identifierType.getHashCode(this.identifier, this.factory);
/*  58:115 */     return result;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean isBatchLoadable()
/*  62:    */   {
/*  63:119 */     return this.isBatchLoadable;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Serializable getIdentifier()
/*  67:    */   {
/*  68:123 */     return this.identifier;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getEntityName()
/*  72:    */   {
/*  73:127 */     return this.entityName;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean equals(Object other)
/*  77:    */   {
/*  78:132 */     EntityKey otherKey = (EntityKey)other;
/*  79:133 */     return (otherKey.rootEntityName.equals(this.rootEntityName)) && (this.identifierType.isEqual(otherKey.identifier, this.identifier, this.factory)) && (EqualsHelper.equals(this.tenantId, otherKey.tenantId));
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int hashCode()
/*  83:    */   {
/*  84:140 */     return this.hashCode;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String toString()
/*  88:    */   {
/*  89:145 */     return "EntityKey" + MessageHelper.infoString(this.factory.getEntityPersister(this.entityName), this.identifier, this.factory);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void serialize(ObjectOutputStream oos)
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:158 */     oos.writeObject(this.identifier);
/*  96:159 */     oos.writeObject(this.rootEntityName);
/*  97:160 */     oos.writeObject(this.entityName);
/*  98:161 */     oos.writeObject(this.identifierType);
/*  99:162 */     oos.writeBoolean(this.isBatchLoadable);
/* 100:163 */     oos.writeObject(this.tenantId);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static EntityKey deserialize(ObjectInputStream ois, SessionImplementor session)
/* 104:    */     throws IOException, ClassNotFoundException
/* 105:    */   {
/* 106:181 */     return new EntityKey((Serializable)ois.readObject(), (String)ois.readObject(), (String)ois.readObject(), (Type)ois.readObject(), ois.readBoolean(), session == null ? null : session.getFactory(), (String)ois.readObject());
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.EntityKey
 * JD-Core Version:    0.7.0.1
 */