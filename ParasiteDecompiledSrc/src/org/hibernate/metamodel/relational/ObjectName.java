/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import org.hibernate.HibernateException;
/*   4:    */ import org.hibernate.dialect.Dialect;
/*   5:    */ 
/*   6:    */ public class ObjectName
/*   7:    */ {
/*   8: 40 */   private static String SEPARATOR = ".";
/*   9:    */   private final Identifier schema;
/*  10:    */   private final Identifier catalog;
/*  11:    */   private final Identifier name;
/*  12:    */   private final String identifier;
/*  13:    */   private final int hashCode;
/*  14:    */   
/*  15:    */   public ObjectName(String objectName)
/*  16:    */   {
/*  17: 55 */     this(extractSchema(objectName), extractCatalog(objectName), extractName(objectName));
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ObjectName(Identifier name)
/*  21:    */   {
/*  22: 63 */     this(null, null, name);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public ObjectName(Schema schema, String name)
/*  26:    */   {
/*  27: 67 */     this(schema.getName().getSchema(), schema.getName().getCatalog(), Identifier.toIdentifier(name));
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ObjectName(Schema schema, Identifier name)
/*  31:    */   {
/*  32: 71 */     this(schema.getName().getSchema(), schema.getName().getCatalog(), name);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ObjectName(String schemaName, String catalogName, String name)
/*  36:    */   {
/*  37: 75 */     this(Identifier.toIdentifier(schemaName), Identifier.toIdentifier(catalogName), Identifier.toIdentifier(name));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ObjectName(Identifier schema, Identifier catalog, Identifier name)
/*  41:    */   {
/*  42: 90 */     if (name == null) {
/*  43: 92 */       throw new IllegalIdentifierException("Object name must be specified");
/*  44:    */     }
/*  45: 94 */     this.name = name;
/*  46: 95 */     this.schema = schema;
/*  47: 96 */     this.catalog = catalog;
/*  48:    */     
/*  49: 98 */     this.identifier = qualify(schema == null ? null : schema.toString(), catalog == null ? null : catalog.toString(), name.toString());
/*  50:    */     
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:104 */     int tmpHashCode = schema != null ? schema.hashCode() : 0;
/*  56:105 */     tmpHashCode = 31 * tmpHashCode + (catalog != null ? catalog.hashCode() : 0);
/*  57:106 */     tmpHashCode = 31 * tmpHashCode + name.hashCode();
/*  58:107 */     this.hashCode = tmpHashCode;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Identifier getSchema()
/*  62:    */   {
/*  63:111 */     return this.schema;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Identifier getCatalog()
/*  67:    */   {
/*  68:115 */     return this.catalog;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Identifier getName()
/*  72:    */   {
/*  73:119 */     return this.name;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String toText()
/*  77:    */   {
/*  78:123 */     return this.identifier;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String toText(Dialect dialect)
/*  82:    */   {
/*  83:127 */     if (dialect == null) {
/*  84:128 */       throw new IllegalArgumentException("dialect must be non-null.");
/*  85:    */     }
/*  86:130 */     return qualify(encloseInQuotesIfQuoted(this.schema, dialect), encloseInQuotesIfQuoted(this.catalog, dialect), encloseInQuotesIfQuoted(this.name, dialect));
/*  87:    */   }
/*  88:    */   
/*  89:    */   private static String encloseInQuotesIfQuoted(Identifier identifier, Dialect dialect)
/*  90:    */   {
/*  91:138 */     return identifier == null ? null : identifier.encloseInQuotesIfQuoted(dialect);
/*  92:    */   }
/*  93:    */   
/*  94:    */   private static String qualify(String schema, String catalog, String name)
/*  95:    */   {
/*  96:144 */     StringBuilder buff = new StringBuilder(name);
/*  97:145 */     if (catalog != null) {
/*  98:146 */       buff.insert(0, catalog + '.');
/*  99:    */     }
/* 100:148 */     if (schema != null) {
/* 101:149 */       buff.insert(0, schema + '.');
/* 102:    */     }
/* 103:151 */     return buff.toString();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean equals(Object o)
/* 107:    */   {
/* 108:156 */     if (this == o) {
/* 109:157 */       return true;
/* 110:    */     }
/* 111:159 */     if ((o == null) || (getClass() != o.getClass())) {
/* 112:160 */       return false;
/* 113:    */     }
/* 114:163 */     ObjectName that = (ObjectName)o;
/* 115:    */     
/* 116:165 */     return (this.name.equals(that.name)) && (areEqual(this.catalog, that.catalog)) && (areEqual(this.schema, that.schema));
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int hashCode()
/* 120:    */   {
/* 121:172 */     return this.hashCode;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String toString()
/* 125:    */   {
/* 126:178 */     return "ObjectName{name='" + this.name + '\'' + ", schema='" + this.schema + '\'' + ", catalog='" + this.catalog + '\'' + '}';
/* 127:    */   }
/* 128:    */   
/* 129:    */   private boolean areEqual(Identifier one, Identifier other)
/* 130:    */   {
/* 131:186 */     return one == null ? false : other == null ? true : one.equals(other);
/* 132:    */   }
/* 133:    */   
/* 134:    */   private static String extractSchema(String qualifiedName)
/* 135:    */   {
/* 136:192 */     if (qualifiedName == null) {
/* 137:193 */       return null;
/* 138:    */     }
/* 139:195 */     String[] tokens = qualifiedName.split(SEPARATOR);
/* 140:196 */     if ((tokens.length == 0) || (tokens.length == 1)) {
/* 141:197 */       return null;
/* 142:    */     }
/* 143:199 */     if (tokens.length == 2) {
/* 144:201 */       return null;
/* 145:    */     }
/* 146:203 */     if (tokens.length == 3) {
/* 147:204 */       return tokens[0];
/* 148:    */     }
/* 149:207 */     throw new HibernateException("Unable to parse object name: " + qualifiedName);
/* 150:    */   }
/* 151:    */   
/* 152:    */   private static String extractCatalog(String qualifiedName)
/* 153:    */   {
/* 154:212 */     if (qualifiedName == null) {
/* 155:213 */       return null;
/* 156:    */     }
/* 157:215 */     String[] tokens = qualifiedName.split(SEPARATOR);
/* 158:216 */     if ((tokens.length == 0) || (tokens.length == 1)) {
/* 159:217 */       return null;
/* 160:    */     }
/* 161:219 */     if (tokens.length == 2) {
/* 162:221 */       return null;
/* 163:    */     }
/* 164:223 */     if (tokens.length == 3) {
/* 165:224 */       return tokens[1];
/* 166:    */     }
/* 167:227 */     throw new HibernateException("Unable to parse object name: " + qualifiedName);
/* 168:    */   }
/* 169:    */   
/* 170:    */   private static String extractName(String qualifiedName)
/* 171:    */   {
/* 172:232 */     if (qualifiedName == null) {
/* 173:233 */       return null;
/* 174:    */     }
/* 175:235 */     String[] tokens = qualifiedName.split(SEPARATOR);
/* 176:236 */     if (tokens.length == 0) {
/* 177:237 */       return qualifiedName;
/* 178:    */     }
/* 179:240 */     return tokens[(tokens.length - 1)];
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.ObjectName
 * JD-Core Version:    0.7.0.1
 */