/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbCollectionTable;
/*   4:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbJoinTable;
/*   5:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbSecondaryTable;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbTable;
/*   7:    */ 
/*   8:    */ abstract interface SchemaAware
/*   9:    */ {
/*  10:    */   public abstract String getSchema();
/*  11:    */   
/*  12:    */   public abstract void setSchema(String paramString);
/*  13:    */   
/*  14:    */   public abstract String getCatalog();
/*  15:    */   
/*  16:    */   public abstract void setCatalog(String paramString);
/*  17:    */   
/*  18:    */   public static class SecondaryTableSchemaAware
/*  19:    */     implements SchemaAware
/*  20:    */   {
/*  21:    */     private JaxbSecondaryTable table;
/*  22:    */     
/*  23:    */     SecondaryTableSchemaAware(JaxbSecondaryTable table)
/*  24:    */     {
/*  25: 47 */       this.table = table;
/*  26:    */     }
/*  27:    */     
/*  28:    */     public String getCatalog()
/*  29:    */     {
/*  30: 52 */       return this.table.getCatalog();
/*  31:    */     }
/*  32:    */     
/*  33:    */     public String getSchema()
/*  34:    */     {
/*  35: 57 */       return this.table.getSchema();
/*  36:    */     }
/*  37:    */     
/*  38:    */     public void setSchema(String schema)
/*  39:    */     {
/*  40: 62 */       this.table.setSchema(schema);
/*  41:    */     }
/*  42:    */     
/*  43:    */     public void setCatalog(String catalog)
/*  44:    */     {
/*  45: 67 */       this.table.setCatalog(catalog);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static class TableSchemaAware
/*  50:    */     implements SchemaAware
/*  51:    */   {
/*  52:    */     private JaxbTable table;
/*  53:    */     
/*  54:    */     public TableSchemaAware(JaxbTable table)
/*  55:    */     {
/*  56: 75 */       this.table = table;
/*  57:    */     }
/*  58:    */     
/*  59:    */     public String getCatalog()
/*  60:    */     {
/*  61: 80 */       return this.table.getCatalog();
/*  62:    */     }
/*  63:    */     
/*  64:    */     public String getSchema()
/*  65:    */     {
/*  66: 85 */       return this.table.getSchema();
/*  67:    */     }
/*  68:    */     
/*  69:    */     public void setSchema(String schema)
/*  70:    */     {
/*  71: 90 */       this.table.setSchema(schema);
/*  72:    */     }
/*  73:    */     
/*  74:    */     public void setCatalog(String catalog)
/*  75:    */     {
/*  76: 95 */       this.table.setCatalog(catalog);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static class JoinTableSchemaAware
/*  81:    */     implements SchemaAware
/*  82:    */   {
/*  83:    */     private JaxbJoinTable table;
/*  84:    */     
/*  85:    */     public JoinTableSchemaAware(JaxbJoinTable table)
/*  86:    */     {
/*  87:103 */       this.table = table;
/*  88:    */     }
/*  89:    */     
/*  90:    */     public String getCatalog()
/*  91:    */     {
/*  92:108 */       return this.table.getCatalog();
/*  93:    */     }
/*  94:    */     
/*  95:    */     public String getSchema()
/*  96:    */     {
/*  97:113 */       return this.table.getSchema();
/*  98:    */     }
/*  99:    */     
/* 100:    */     public void setSchema(String schema)
/* 101:    */     {
/* 102:118 */       this.table.setSchema(schema);
/* 103:    */     }
/* 104:    */     
/* 105:    */     public void setCatalog(String catalog)
/* 106:    */     {
/* 107:123 */       this.table.setCatalog(catalog);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static class CollectionTableSchemaAware
/* 112:    */     implements SchemaAware
/* 113:    */   {
/* 114:    */     private JaxbCollectionTable table;
/* 115:    */     
/* 116:    */     public CollectionTableSchemaAware(JaxbCollectionTable table)
/* 117:    */     {
/* 118:131 */       this.table = table;
/* 119:    */     }
/* 120:    */     
/* 121:    */     public String getCatalog()
/* 122:    */     {
/* 123:136 */       return this.table.getCatalog();
/* 124:    */     }
/* 125:    */     
/* 126:    */     public String getSchema()
/* 127:    */     {
/* 128:141 */       return this.table.getSchema();
/* 129:    */     }
/* 130:    */     
/* 131:    */     public void setSchema(String schema)
/* 132:    */     {
/* 133:146 */       this.table.setSchema(schema);
/* 134:    */     }
/* 135:    */     
/* 136:    */     public void setCatalog(String catalog)
/* 137:    */     {
/* 138:151 */       this.table.setCatalog(catalog);
/* 139:    */     }
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.SchemaAware
 * JD-Core Version:    0.7.0.1
 */