/*   1:    */ package org.hibernate.tool.hbm2ddl;
/*   2:    */ 
/*   3:    */ import java.sql.DatabaseMetaData;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.mapping.ForeignKey;
/*  12:    */ import org.jboss.logging.Logger;
/*  13:    */ 
/*  14:    */ public class TableMetadata
/*  15:    */ {
/*  16: 45 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TableMetadata.class.getName());
/*  17:    */   private final String catalog;
/*  18:    */   private final String schema;
/*  19:    */   private final String name;
/*  20: 50 */   private final Map columns = new HashMap();
/*  21: 51 */   private final Map foreignKeys = new HashMap();
/*  22: 52 */   private final Map indexes = new HashMap();
/*  23:    */   
/*  24:    */   TableMetadata(ResultSet rs, DatabaseMetaData meta, boolean extras)
/*  25:    */     throws SQLException
/*  26:    */   {
/*  27: 55 */     this.catalog = rs.getString("TABLE_CAT");
/*  28: 56 */     this.schema = rs.getString("TABLE_SCHEM");
/*  29: 57 */     this.name = rs.getString("TABLE_NAME");
/*  30: 58 */     initColumns(meta);
/*  31: 59 */     if (extras)
/*  32:    */     {
/*  33: 60 */       initForeignKeys(meta);
/*  34: 61 */       initIndexes(meta);
/*  35:    */     }
/*  36: 63 */     String cat = this.catalog + '.';
/*  37: 64 */     String schem = this.schema + '.';
/*  38: 65 */     LOG.tableFound(cat + schem + this.name);
/*  39: 66 */     LOG.columns(this.columns.keySet());
/*  40: 67 */     if (extras)
/*  41:    */     {
/*  42: 68 */       LOG.foreignKeys(this.foreignKeys.keySet());
/*  43: 69 */       LOG.indexes(this.indexes.keySet());
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getName()
/*  48:    */   {
/*  49: 74 */     return this.name;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getCatalog()
/*  53:    */   {
/*  54: 78 */     return this.catalog;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getSchema()
/*  58:    */   {
/*  59: 82 */     return this.schema;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String toString()
/*  63:    */   {
/*  64: 87 */     return "TableMetadata(" + this.name + ')';
/*  65:    */   }
/*  66:    */   
/*  67:    */   public ColumnMetadata getColumnMetadata(String columnName)
/*  68:    */   {
/*  69: 91 */     return (ColumnMetadata)this.columns.get(columnName.toLowerCase());
/*  70:    */   }
/*  71:    */   
/*  72:    */   public ForeignKeyMetadata getForeignKeyMetadata(String keyName)
/*  73:    */   {
/*  74: 95 */     return (ForeignKeyMetadata)this.foreignKeys.get(keyName.toLowerCase());
/*  75:    */   }
/*  76:    */   
/*  77:    */   public ForeignKeyMetadata getForeignKeyMetadata(ForeignKey fk)
/*  78:    */   {
/*  79: 99 */     Iterator it = this.foreignKeys.values().iterator();
/*  80:100 */     while (it.hasNext())
/*  81:    */     {
/*  82:101 */       ForeignKeyMetadata existingFk = (ForeignKeyMetadata)it.next();
/*  83:102 */       if (existingFk.matches(fk)) {
/*  84:103 */         return existingFk;
/*  85:    */       }
/*  86:    */     }
/*  87:106 */     return null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public IndexMetadata getIndexMetadata(String indexName)
/*  91:    */   {
/*  92:110 */     return (IndexMetadata)this.indexes.get(indexName.toLowerCase());
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void addForeignKey(ResultSet rs)
/*  96:    */     throws SQLException
/*  97:    */   {
/*  98:114 */     String fk = rs.getString("FK_NAME");
/*  99:116 */     if (fk == null) {
/* 100:117 */       return;
/* 101:    */     }
/* 102:120 */     ForeignKeyMetadata info = getForeignKeyMetadata(fk);
/* 103:121 */     if (info == null)
/* 104:    */     {
/* 105:122 */       info = new ForeignKeyMetadata(rs);
/* 106:123 */       this.foreignKeys.put(info.getName().toLowerCase(), info);
/* 107:    */     }
/* 108:126 */     info.addReference(rs);
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void addIndex(ResultSet rs)
/* 112:    */     throws SQLException
/* 113:    */   {
/* 114:130 */     String index = rs.getString("INDEX_NAME");
/* 115:132 */     if (index == null) {
/* 116:133 */       return;
/* 117:    */     }
/* 118:136 */     IndexMetadata info = getIndexMetadata(index);
/* 119:137 */     if (info == null)
/* 120:    */     {
/* 121:138 */       info = new IndexMetadata(rs);
/* 122:139 */       this.indexes.put(info.getName().toLowerCase(), info);
/* 123:    */     }
/* 124:142 */     info.addColumn(getColumnMetadata(rs.getString("COLUMN_NAME")));
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void addColumn(ResultSet rs)
/* 128:    */     throws SQLException
/* 129:    */   {
/* 130:146 */     String column = rs.getString("COLUMN_NAME");
/* 131:148 */     if (column == null) {
/* 132:149 */       return;
/* 133:    */     }
/* 134:152 */     if (getColumnMetadata(column) == null)
/* 135:    */     {
/* 136:153 */       ColumnMetadata info = new ColumnMetadata(rs);
/* 137:154 */       this.columns.put(info.getName().toLowerCase(), info);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void initForeignKeys(DatabaseMetaData meta)
/* 142:    */     throws SQLException
/* 143:    */   {
/* 144:159 */     ResultSet rs = null;
/* 145:    */     try
/* 146:    */     {
/* 147:162 */       rs = meta.getImportedKeys(this.catalog, this.schema, this.name);
/* 148:163 */       while (rs.next()) {
/* 149:164 */         addForeignKey(rs);
/* 150:    */       }
/* 151:    */     }
/* 152:    */     finally
/* 153:    */     {
/* 154:168 */       if (rs != null) {
/* 155:169 */         rs.close();
/* 156:    */       }
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   private void initIndexes(DatabaseMetaData meta)
/* 161:    */     throws SQLException
/* 162:    */   {
/* 163:175 */     ResultSet rs = null;
/* 164:    */     try
/* 165:    */     {
/* 166:178 */       rs = meta.getIndexInfo(this.catalog, this.schema, this.name, false, true);
/* 167:180 */       while (rs.next()) {
/* 168:181 */         if (rs.getShort("TYPE") != 0) {
/* 169:184 */           addIndex(rs);
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:    */     finally
/* 174:    */     {
/* 175:188 */       if (rs != null) {
/* 176:189 */         rs.close();
/* 177:    */       }
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   private void initColumns(DatabaseMetaData meta)
/* 182:    */     throws SQLException
/* 183:    */   {
/* 184:195 */     ResultSet rs = null;
/* 185:    */     try
/* 186:    */     {
/* 187:198 */       rs = meta.getColumns(this.catalog, this.schema, this.name, "%");
/* 188:199 */       while (rs.next()) {
/* 189:200 */         addColumn(rs);
/* 190:    */       }
/* 191:    */     }
/* 192:    */     finally
/* 193:    */     {
/* 194:204 */       if (rs != null) {
/* 195:205 */         rs.close();
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.TableMetadata
 * JD-Core Version:    0.7.0.1
 */