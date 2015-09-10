/*   1:    */ package org.hibernate.persister.entity;
/*   2:    */ 
/*   3:    */ import org.hibernate.sql.SelectFragment;
/*   4:    */ 
/*   5:    */ public abstract interface Queryable
/*   6:    */   extends Loadable, PropertyMapping, Joinable
/*   7:    */ {
/*   8:    */   public abstract boolean isAbstract();
/*   9:    */   
/*  10:    */   public abstract boolean isExplicitPolymorphism();
/*  11:    */   
/*  12:    */   public abstract String getMappedSuperclass();
/*  13:    */   
/*  14:    */   public abstract String getDiscriminatorSQLValue();
/*  15:    */   
/*  16:    */   public abstract String identifierSelectFragment(String paramString1, String paramString2);
/*  17:    */   
/*  18:    */   public abstract String propertySelectFragment(String paramString1, String paramString2, boolean paramBoolean);
/*  19:    */   
/*  20:    */   public abstract SelectFragment propertySelectFragmentFragment(String paramString1, String paramString2, boolean paramBoolean);
/*  21:    */   
/*  22:    */   public abstract String[] getIdentifierColumnNames();
/*  23:    */   
/*  24:    */   public abstract boolean isMultiTable();
/*  25:    */   
/*  26:    */   public abstract String[] getConstraintOrderedTableNameClosure();
/*  27:    */   
/*  28:    */   public abstract String[][] getContraintOrderedTableKeyColumnClosure();
/*  29:    */   
/*  30:    */   public abstract String getTemporaryIdTableName();
/*  31:    */   
/*  32:    */   public abstract String getTemporaryIdTableDDL();
/*  33:    */   
/*  34:    */   public abstract int getSubclassPropertyTableNumber(String paramString);
/*  35:    */   
/*  36:    */   public abstract Declarer getSubclassPropertyDeclarer(String paramString);
/*  37:    */   
/*  38:    */   public abstract String getSubclassTableName(int paramInt);
/*  39:    */   
/*  40:    */   public abstract boolean isVersionPropertyInsertable();
/*  41:    */   
/*  42:    */   public abstract String generateFilterConditionAlias(String paramString);
/*  43:    */   
/*  44:    */   public abstract DiscriminatorMetadata getTypeDiscriminatorMetadata();
/*  45:    */   
/*  46:    */   public static class Declarer
/*  47:    */   {
/*  48:174 */     public static final Declarer CLASS = new Declarer("class");
/*  49:175 */     public static final Declarer SUBCLASS = new Declarer("subclass");
/*  50:176 */     public static final Declarer SUPERCLASS = new Declarer("superclass");
/*  51:    */     private final String name;
/*  52:    */     
/*  53:    */     public Declarer(String name)
/*  54:    */     {
/*  55:179 */       this.name = name;
/*  56:    */     }
/*  57:    */     
/*  58:    */     public String toString()
/*  59:    */     {
/*  60:182 */       return this.name;
/*  61:    */     }
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.Queryable
 * JD-Core Version:    0.7.0.1
 */