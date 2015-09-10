/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.cfg.Mappings;
/*  5:   */ import org.hibernate.engine.spi.Mapping;
/*  6:   */ import org.hibernate.type.Type;
/*  7:   */ 
/*  8:   */ public abstract class IdentifierCollection
/*  9:   */   extends Collection
/* 10:   */ {
/* 11:   */   public static final String DEFAULT_IDENTIFIER_COLUMN_NAME = "id";
/* 12:   */   private KeyValue identifier;
/* 13:   */   
/* 14:   */   public IdentifierCollection(Mappings mappings, PersistentClass owner)
/* 15:   */   {
/* 16:39 */     super(mappings, owner);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public KeyValue getIdentifier()
/* 20:   */   {
/* 21:43 */     return this.identifier;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setIdentifier(KeyValue identifier)
/* 25:   */   {
/* 26:46 */     this.identifier = identifier;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public final boolean isIdentified()
/* 30:   */   {
/* 31:49 */     return true;
/* 32:   */   }
/* 33:   */   
/* 34:   */   void createPrimaryKey()
/* 35:   */   {
/* 36:53 */     if (!isOneToMany())
/* 37:   */     {
/* 38:54 */       PrimaryKey pk = new PrimaryKey();
/* 39:55 */       pk.addColumns(getIdentifier().getColumnIterator());
/* 40:56 */       getCollectionTable().setPrimaryKey(pk);
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void validate(Mapping mapping)
/* 45:   */     throws MappingException
/* 46:   */   {
/* 47:68 */     super.validate(mapping);
/* 48:69 */     if (!getIdentifier().isValid(mapping)) {
/* 49:70 */       throw new MappingException("collection id mapping has wrong number of columns: " + getRole() + " type: " + getIdentifier().getType().getName());
/* 50:   */     }
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.IdentifierCollection
 * JD-Core Version:    0.7.0.1
 */