/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ 
/*   6:    */ public class Schema
/*   7:    */ {
/*   8:    */   private final Name name;
/*   9: 36 */   private Map<String, InLineView> inLineViews = new HashMap();
/*  10: 37 */   private Map<Identifier, Table> tables = new HashMap();
/*  11:    */   
/*  12:    */   public Schema(Name name)
/*  13:    */   {
/*  14: 40 */     this.name = name;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Schema(Identifier schema, Identifier catalog)
/*  18:    */   {
/*  19: 44 */     this(new Name(schema, catalog));
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Name getName()
/*  23:    */   {
/*  24: 48 */     return this.name;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Table locateTable(Identifier name)
/*  28:    */   {
/*  29: 52 */     return (Table)this.tables.get(name);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Table createTable(Identifier name)
/*  33:    */   {
/*  34: 56 */     Table table = new Table(this, name);
/*  35: 57 */     this.tables.put(name, table);
/*  36: 58 */     return table;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Table locateOrCreateTable(Identifier name)
/*  40:    */   {
/*  41: 62 */     Table existing = locateTable(name);
/*  42: 63 */     if (existing == null) {
/*  43: 64 */       return createTable(name);
/*  44:    */     }
/*  45: 66 */     return existing;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Iterable<Table> getTables()
/*  49:    */   {
/*  50: 70 */     return this.tables.values();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public InLineView getInLineView(String logicalName)
/*  54:    */   {
/*  55: 74 */     return (InLineView)this.inLineViews.get(logicalName);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public InLineView createInLineView(String logicalName, String subSelect)
/*  59:    */   {
/*  60: 78 */     InLineView inLineView = new InLineView(this, logicalName, subSelect);
/*  61: 79 */     this.inLineViews.put(logicalName, inLineView);
/*  62: 80 */     return inLineView;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String toString()
/*  66:    */   {
/*  67: 85 */     StringBuilder sb = new StringBuilder();
/*  68: 86 */     sb.append("Schema");
/*  69: 87 */     sb.append("{name=").append(this.name);
/*  70: 88 */     sb.append('}');
/*  71: 89 */     return sb.toString();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean equals(Object o)
/*  75:    */   {
/*  76: 94 */     if (this == o) {
/*  77: 95 */       return true;
/*  78:    */     }
/*  79: 97 */     if ((o == null) || (getClass() != o.getClass())) {
/*  80: 98 */       return false;
/*  81:    */     }
/*  82:101 */     Schema schema = (Schema)o;
/*  83:103 */     if (this.name != null ? !this.name.equals(schema.name) : schema.name != null) {
/*  84:104 */       return false;
/*  85:    */     }
/*  86:107 */     return true;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int hashCode()
/*  90:    */   {
/*  91:112 */     return this.name != null ? this.name.hashCode() : 0;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static class Name
/*  95:    */   {
/*  96:    */     private final Identifier schema;
/*  97:    */     private final Identifier catalog;
/*  98:    */     
/*  99:    */     public Name(Identifier schema, Identifier catalog)
/* 100:    */     {
/* 101:120 */       this.schema = schema;
/* 102:121 */       this.catalog = catalog;
/* 103:    */     }
/* 104:    */     
/* 105:    */     public Name(String schema, String catalog)
/* 106:    */     {
/* 107:125 */       this(Identifier.toIdentifier(schema), Identifier.toIdentifier(catalog));
/* 108:    */     }
/* 109:    */     
/* 110:    */     public Identifier getSchema()
/* 111:    */     {
/* 112:129 */       return this.schema;
/* 113:    */     }
/* 114:    */     
/* 115:    */     public Identifier getCatalog()
/* 116:    */     {
/* 117:133 */       return this.catalog;
/* 118:    */     }
/* 119:    */     
/* 120:    */     public String toString()
/* 121:    */     {
/* 122:138 */       StringBuilder sb = new StringBuilder();
/* 123:139 */       sb.append("Name");
/* 124:140 */       sb.append("{schema=").append(this.schema);
/* 125:141 */       sb.append(", catalog=").append(this.catalog);
/* 126:142 */       sb.append('}');
/* 127:143 */       return sb.toString();
/* 128:    */     }
/* 129:    */     
/* 130:    */     public boolean equals(Object o)
/* 131:    */     {
/* 132:148 */       if (this == o) {
/* 133:149 */         return true;
/* 134:    */       }
/* 135:151 */       if ((o == null) || (getClass() != o.getClass())) {
/* 136:152 */         return false;
/* 137:    */       }
/* 138:155 */       Name name = (Name)o;
/* 139:157 */       if (this.catalog != null ? !this.catalog.equals(name.catalog) : name.catalog != null) {
/* 140:158 */         return false;
/* 141:    */       }
/* 142:160 */       if (this.schema != null ? !this.schema.equals(name.schema) : name.schema != null) {
/* 143:161 */         return false;
/* 144:    */       }
/* 145:164 */       return true;
/* 146:    */     }
/* 147:    */     
/* 148:    */     public int hashCode()
/* 149:    */     {
/* 150:169 */       int result = this.schema != null ? this.schema.hashCode() : 0;
/* 151:170 */       result = 31 * result + (this.catalog != null ? this.catalog.hashCode() : 0);
/* 152:171 */       return result;
/* 153:    */     }
/* 154:    */   }
/* 155:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Schema
 * JD-Core Version:    0.7.0.1
 */