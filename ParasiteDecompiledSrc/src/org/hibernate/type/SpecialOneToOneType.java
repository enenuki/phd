/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import org.hibernate.AssertionFailure;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.engine.internal.ForeignKeys;
/*  10:    */ import org.hibernate.engine.spi.Mapping;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.metamodel.relational.Size;
/*  13:    */ 
/*  14:    */ public class SpecialOneToOneType
/*  15:    */   extends OneToOneType
/*  16:    */ {
/*  17:    */   public SpecialOneToOneType(TypeFactory.TypeScope scope, String referencedEntityName, ForeignKeyDirection foreignKeyType, String uniqueKeyPropertyName, boolean lazy, boolean unwrapProxy, String entityName, String propertyName)
/*  18:    */   {
/*  19: 55 */     super(scope, referencedEntityName, foreignKeyType, uniqueKeyPropertyName, lazy, unwrapProxy, true, entityName, propertyName);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getColumnSpan(Mapping mapping)
/*  23:    */     throws MappingException
/*  24:    */   {
/*  25: 69 */     return super.getIdentifierOrUniqueKeyType(mapping).getColumnSpan(mapping);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int[] sqlTypes(Mapping mapping)
/*  29:    */     throws MappingException
/*  30:    */   {
/*  31: 73 */     return super.getIdentifierOrUniqueKeyType(mapping).sqlTypes(mapping);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Size[] dictatedSizes(Mapping mapping)
/*  35:    */     throws MappingException
/*  36:    */   {
/*  37: 78 */     return super.getIdentifierOrUniqueKeyType(mapping).dictatedSizes(mapping);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Size[] defaultSizes(Mapping mapping)
/*  41:    */     throws MappingException
/*  42:    */   {
/*  43: 83 */     return super.getIdentifierOrUniqueKeyType(mapping).defaultSizes(mapping);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean useLHSPrimaryKey()
/*  47:    */   {
/*  48: 87 */     return false;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Object hydrate(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/*  52:    */     throws HibernateException, SQLException
/*  53:    */   {
/*  54: 92 */     return super.getIdentifierOrUniqueKeyType(session.getFactory()).nullSafeGet(rs, names, session, owner);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Serializable disassemble(Object value, SessionImplementor session, Object owner)
/*  58:    */     throws HibernateException
/*  59:    */   {
/*  60:101 */     if (isNotEmbedded(session)) {
/*  61:102 */       return getIdentifierType(session).disassemble(value, session, owner);
/*  62:    */     }
/*  63:105 */     if (value == null) {
/*  64:106 */       return null;
/*  65:    */     }
/*  66:111 */     Object id = ForeignKeys.getEntityIdentifierIfNotUnsaved(getAssociatedEntityName(), value, session);
/*  67:112 */     if (id == null) {
/*  68:113 */       throw new AssertionFailure("cannot cache a reference to an object with a null id: " + getAssociatedEntityName());
/*  69:    */     }
/*  70:118 */     return getIdentifierType(session).disassemble(id, session, owner);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Object assemble(Serializable oid, SessionImplementor session, Object owner)
/*  74:    */     throws HibernateException
/*  75:    */   {
/*  76:126 */     Serializable id = (Serializable)getIdentifierType(session).assemble(oid, session, null);
/*  77:128 */     if (isNotEmbedded(session)) {
/*  78:128 */       return id;
/*  79:    */     }
/*  80:130 */     if (id == null) {
/*  81:131 */       return null;
/*  82:    */     }
/*  83:134 */     return resolveIdentifier(id, session);
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.SpecialOneToOneType
 * JD-Core Version:    0.7.0.1
 */