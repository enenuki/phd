/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import org.hibernate.MappingException;
/*   5:    */ import org.hibernate.cfg.Mappings;
/*   6:    */ import org.hibernate.engine.spi.Mapping;
/*   7:    */ import org.hibernate.type.Type;
/*   8:    */ 
/*   9:    */ public abstract class IndexedCollection
/*  10:    */   extends Collection
/*  11:    */ {
/*  12:    */   public static final String DEFAULT_INDEX_COLUMN_NAME = "idx";
/*  13:    */   private Value index;
/*  14:    */   private String indexNodeName;
/*  15:    */   
/*  16:    */   public IndexedCollection(Mappings mappings, PersistentClass owner)
/*  17:    */   {
/*  18: 44 */     super(mappings, owner);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Value getIndex()
/*  22:    */   {
/*  23: 48 */     return this.index;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setIndex(Value index)
/*  27:    */   {
/*  28: 51 */     this.index = index;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public final boolean isIndexed()
/*  32:    */   {
/*  33: 54 */     return true;
/*  34:    */   }
/*  35:    */   
/*  36:    */   void createPrimaryKey()
/*  37:    */   {
/*  38: 58 */     if (!isOneToMany())
/*  39:    */     {
/*  40: 59 */       PrimaryKey pk = new PrimaryKey();
/*  41: 60 */       pk.addColumns(getKey().getColumnIterator());
/*  42:    */       
/*  43:    */ 
/*  44: 63 */       boolean isFormula = false;
/*  45: 64 */       Iterator iter = getIndex().getColumnIterator();
/*  46: 65 */       while (iter.hasNext()) {
/*  47: 66 */         if (((Selectable)iter.next()).isFormula()) {
/*  48: 66 */           isFormula = true;
/*  49:    */         }
/*  50:    */       }
/*  51: 68 */       if (isFormula) {
/*  52: 70 */         pk.addColumns(getElement().getColumnIterator());
/*  53:    */       } else {
/*  54: 73 */         pk.addColumns(getIndex().getColumnIterator());
/*  55:    */       }
/*  56: 75 */       getCollectionTable().setPrimaryKey(pk);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void validate(Mapping mapping)
/*  61:    */     throws MappingException
/*  62:    */   {
/*  63: 89 */     super.validate(mapping);
/*  64: 90 */     if (!getIndex().isValid(mapping)) {
/*  65: 91 */       throw new MappingException("collection index mapping has wrong number of columns: " + getRole() + " type: " + getIndex().getType().getName());
/*  66:    */     }
/*  67: 98 */     if ((this.indexNodeName != null) && (!this.indexNodeName.startsWith("@"))) {
/*  68: 99 */       throw new MappingException("index node must be an attribute: " + this.indexNodeName);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean isList()
/*  73:    */   {
/*  74:104 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getIndexNodeName()
/*  78:    */   {
/*  79:108 */     return this.indexNodeName;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIndexNodeName(String indexNodeName)
/*  83:    */   {
/*  84:112 */     this.indexNodeName = indexNodeName;
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.IndexedCollection
 * JD-Core Version:    0.7.0.1
 */