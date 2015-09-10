/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import javax.persistence.DiscriminatorColumn;
/*   4:    */ import javax.persistence.DiscriminatorType;
/*   5:    */ import org.hibernate.AssertionFailure;
/*   6:    */ import org.hibernate.annotations.DiscriminatorFormula;
/*   7:    */ 
/*   8:    */ public class Ejb3DiscriminatorColumn
/*   9:    */   extends Ejb3Column
/*  10:    */ {
/*  11:    */   private static final String DEFAULT_DISCRIMINATOR_COLUMN_NAME = "DTYPE";
/*  12:    */   private static final String DEFAULT_DISCRIMINATOR_TYPE = "string";
/*  13:    */   private static final int DEFAULT_DISCRIMINATOR_LENGTH = 31;
/*  14:    */   private String discriminatorTypeName;
/*  15:    */   
/*  16:    */   public Ejb3DiscriminatorColumn()
/*  17:    */   {
/*  18: 46 */     setLogicalColumnName("DTYPE");
/*  19: 47 */     setNullable(false);
/*  20: 48 */     setDiscriminatorTypeName("string");
/*  21: 49 */     setLength(31);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getDiscriminatorTypeName()
/*  25:    */   {
/*  26: 53 */     return this.discriminatorTypeName;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setDiscriminatorTypeName(String discriminatorTypeName)
/*  30:    */   {
/*  31: 57 */     this.discriminatorTypeName = discriminatorTypeName;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Ejb3DiscriminatorColumn buildDiscriminatorColumn(DiscriminatorType type, DiscriminatorColumn discAnn, DiscriminatorFormula discFormulaAnn, Mappings mappings)
/*  35:    */   {
/*  36: 64 */     Ejb3DiscriminatorColumn discriminatorColumn = new Ejb3DiscriminatorColumn();
/*  37: 65 */     discriminatorColumn.setMappings(mappings);
/*  38: 66 */     discriminatorColumn.setImplicit(true);
/*  39: 67 */     if (discFormulaAnn != null)
/*  40:    */     {
/*  41: 68 */       discriminatorColumn.setImplicit(false);
/*  42: 69 */       discriminatorColumn.setFormula(discFormulaAnn.value());
/*  43:    */     }
/*  44: 71 */     else if (discAnn != null)
/*  45:    */     {
/*  46: 72 */       discriminatorColumn.setImplicit(false);
/*  47: 73 */       if (!BinderHelper.isEmptyAnnotationValue(discAnn.columnDefinition())) {
/*  48: 74 */         discriminatorColumn.setSqlType(discAnn.columnDefinition());
/*  49:    */       }
/*  50: 78 */       if (!BinderHelper.isEmptyAnnotationValue(discAnn.name())) {
/*  51: 79 */         discriminatorColumn.setLogicalColumnName(discAnn.name());
/*  52:    */       }
/*  53: 81 */       discriminatorColumn.setNullable(false);
/*  54:    */     }
/*  55: 83 */     if (DiscriminatorType.CHAR.equals(type))
/*  56:    */     {
/*  57: 84 */       discriminatorColumn.setDiscriminatorTypeName("character");
/*  58: 85 */       discriminatorColumn.setImplicit(false);
/*  59:    */     }
/*  60: 87 */     else if (DiscriminatorType.INTEGER.equals(type))
/*  61:    */     {
/*  62: 88 */       discriminatorColumn.setDiscriminatorTypeName("integer");
/*  63: 89 */       discriminatorColumn.setImplicit(false);
/*  64:    */     }
/*  65: 91 */     else if ((DiscriminatorType.STRING.equals(type)) || (type == null))
/*  66:    */     {
/*  67: 92 */       if (discAnn != null) {
/*  68: 92 */         discriminatorColumn.setLength(discAnn.length());
/*  69:    */       }
/*  70: 93 */       discriminatorColumn.setDiscriminatorTypeName("string");
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 96 */       throw new AssertionFailure("Unknown discriminator type: " + type);
/*  75:    */     }
/*  76: 98 */     discriminatorColumn.bind();
/*  77: 99 */     return discriminatorColumn;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String toString()
/*  81:    */   {
/*  82:104 */     StringBuilder sb = new StringBuilder();
/*  83:105 */     sb.append("Ejb3DiscriminatorColumn");
/*  84:106 */     sb.append("{logicalColumnName'").append(getLogicalColumnName()).append('\'');
/*  85:107 */     sb.append(", discriminatorTypeName='").append(this.discriminatorTypeName).append('\'');
/*  86:108 */     sb.append('}');
/*  87:109 */     return sb.toString();
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.Ejb3DiscriminatorColumn
 * JD-Core Version:    0.7.0.1
 */