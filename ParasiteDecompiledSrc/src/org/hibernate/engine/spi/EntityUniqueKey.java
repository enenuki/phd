/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import org.hibernate.EntityMode;
/*   8:    */ import org.hibernate.pretty.MessageHelper;
/*   9:    */ import org.hibernate.type.Type;
/*  10:    */ 
/*  11:    */ public class EntityUniqueKey
/*  12:    */   implements Serializable
/*  13:    */ {
/*  14:    */   private final String uniqueKeyName;
/*  15:    */   private final String entityName;
/*  16:    */   private final Object key;
/*  17:    */   private final Type keyType;
/*  18:    */   private final EntityMode entityMode;
/*  19:    */   private final int hashCode;
/*  20:    */   
/*  21:    */   public EntityUniqueKey(String entityName, String uniqueKeyName, Object semiResolvedKey, Type keyType, EntityMode entityMode, SessionFactoryImplementor factory)
/*  22:    */   {
/*  23: 61 */     this.uniqueKeyName = uniqueKeyName;
/*  24: 62 */     this.entityName = entityName;
/*  25: 63 */     this.key = semiResolvedKey;
/*  26: 64 */     this.keyType = keyType.getSemiResolvedType(factory);
/*  27: 65 */     this.entityMode = entityMode;
/*  28: 66 */     this.hashCode = generateHashCode(factory);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getEntityName()
/*  32:    */   {
/*  33: 70 */     return this.entityName;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Object getKey()
/*  37:    */   {
/*  38: 74 */     return this.key;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getUniqueKeyName()
/*  42:    */   {
/*  43: 78 */     return this.uniqueKeyName;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int generateHashCode(SessionFactoryImplementor factory)
/*  47:    */   {
/*  48: 82 */     int result = 17;
/*  49: 83 */     result = 37 * result + this.entityName.hashCode();
/*  50: 84 */     result = 37 * result + this.uniqueKeyName.hashCode();
/*  51: 85 */     result = 37 * result + this.keyType.getHashCode(this.key, factory);
/*  52: 86 */     return result;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int hashCode()
/*  56:    */   {
/*  57: 90 */     return this.hashCode;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean equals(Object other)
/*  61:    */   {
/*  62: 94 */     EntityUniqueKey that = (EntityUniqueKey)other;
/*  63: 95 */     return (that.entityName.equals(this.entityName)) && (that.uniqueKeyName.equals(this.uniqueKeyName)) && (this.keyType.isEqual(that.key, this.key));
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String toString()
/*  67:    */   {
/*  68:101 */     return "EntityUniqueKey" + MessageHelper.infoString(this.entityName, this.uniqueKeyName, this.key);
/*  69:    */   }
/*  70:    */   
/*  71:    */   private void writeObject(ObjectOutputStream oos)
/*  72:    */     throws IOException
/*  73:    */   {
/*  74:105 */     checkAbilityToSerialize();
/*  75:106 */     oos.defaultWriteObject();
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void checkAbilityToSerialize()
/*  79:    */   {
/*  80:113 */     if ((this.key != null) && (!Serializable.class.isAssignableFrom(this.key.getClass()))) {
/*  81:114 */       throw new IllegalStateException("Cannot serialize an EntityUniqueKey which represents a non serializable property value [" + this.entityName + "." + this.uniqueKeyName + "]");
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void serialize(ObjectOutputStream oos)
/*  86:    */     throws IOException
/*  87:    */   {
/*  88:129 */     checkAbilityToSerialize();
/*  89:130 */     oos.writeObject(this.uniqueKeyName);
/*  90:131 */     oos.writeObject(this.entityName);
/*  91:132 */     oos.writeObject(this.key);
/*  92:133 */     oos.writeObject(this.keyType);
/*  93:134 */     oos.writeObject(this.entityMode);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static EntityUniqueKey deserialize(ObjectInputStream ois, SessionImplementor session)
/*  97:    */     throws IOException, ClassNotFoundException
/*  98:    */   {
/*  99:150 */     return new EntityUniqueKey((String)ois.readObject(), (String)ois.readObject(), ois.readObject(), (Type)ois.readObject(), (EntityMode)ois.readObject(), session.getFactory());
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.EntityUniqueKey
 * JD-Core Version:    0.7.0.1
 */