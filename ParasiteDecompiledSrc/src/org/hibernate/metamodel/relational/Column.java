/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import org.hibernate.MappingException;
/*   4:    */ import org.hibernate.dialect.Dialect;
/*   5:    */ import org.hibernate.internal.util.StringHelper;
/*   6:    */ import org.hibernate.metamodel.relational.state.ColumnRelationalState;
/*   7:    */ 
/*   8:    */ public class Column
/*   9:    */   extends AbstractSimpleValue
/*  10:    */ {
/*  11:    */   private final Identifier columnName;
/*  12:    */   private boolean nullable;
/*  13:    */   private boolean unique;
/*  14:    */   private String defaultValue;
/*  15:    */   private String checkCondition;
/*  16:    */   private String sqlType;
/*  17:    */   private String readFragment;
/*  18:    */   private String writeFragment;
/*  19:    */   private String comment;
/*  20: 51 */   private Size size = new Size();
/*  21:    */   
/*  22:    */   protected Column(TableSpecification table, int position, String name)
/*  23:    */   {
/*  24: 54 */     this(table, position, Identifier.toIdentifier(name));
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected Column(TableSpecification table, int position, Identifier name)
/*  28:    */   {
/*  29: 58 */     super(table, position);
/*  30: 59 */     this.columnName = name;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void initialize(ColumnRelationalState state, boolean forceNonNullable, boolean forceUnique)
/*  34:    */   {
/*  35: 63 */     this.size.initialize(state.getSize());
/*  36: 64 */     this.nullable = ((!forceNonNullable) && (state.isNullable()));
/*  37: 65 */     this.unique = ((!forceUnique) && (state.isUnique()));
/*  38: 66 */     this.checkCondition = state.getCheckCondition();
/*  39: 67 */     this.defaultValue = state.getDefault();
/*  40: 68 */     this.sqlType = state.getSqlType();
/*  41:    */     
/*  42:    */ 
/*  43: 71 */     this.writeFragment = state.getCustomWriteFragment();
/*  44: 72 */     this.readFragment = state.getCustomReadFragment();
/*  45: 73 */     this.comment = state.getComment();
/*  46: 74 */     for (String uniqueKey : state.getUniqueKeys()) {
/*  47: 75 */       getTable().getOrCreateUniqueKey(uniqueKey).addColumn(this);
/*  48:    */     }
/*  49: 77 */     for (String index : state.getIndexes()) {
/*  50: 78 */       getTable().getOrCreateIndex(index).addColumn(this);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Identifier getColumnName()
/*  55:    */   {
/*  56: 83 */     return this.columnName;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isNullable()
/*  60:    */   {
/*  61: 87 */     return this.nullable;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setNullable(boolean nullable)
/*  65:    */   {
/*  66: 91 */     this.nullable = nullable;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean isUnique()
/*  70:    */   {
/*  71: 95 */     return this.unique;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setUnique(boolean unique)
/*  75:    */   {
/*  76: 99 */     this.unique = unique;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getDefaultValue()
/*  80:    */   {
/*  81:103 */     return this.defaultValue;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setDefaultValue(String defaultValue)
/*  85:    */   {
/*  86:107 */     this.defaultValue = defaultValue;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getCheckCondition()
/*  90:    */   {
/*  91:111 */     return this.checkCondition;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setCheckCondition(String checkCondition)
/*  95:    */   {
/*  96:115 */     this.checkCondition = checkCondition;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getSqlType()
/* 100:    */   {
/* 101:119 */     return this.sqlType;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setSqlType(String sqlType)
/* 105:    */   {
/* 106:123 */     this.sqlType = sqlType;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getReadFragment()
/* 110:    */   {
/* 111:127 */     return this.readFragment;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setReadFragment(String readFragment)
/* 115:    */   {
/* 116:131 */     this.readFragment = readFragment;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getWriteFragment()
/* 120:    */   {
/* 121:135 */     return this.writeFragment;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setWriteFragment(String writeFragment)
/* 125:    */   {
/* 126:139 */     this.writeFragment = writeFragment;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getComment()
/* 130:    */   {
/* 131:143 */     return this.comment;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setComment(String comment)
/* 135:    */   {
/* 136:147 */     this.comment = comment;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Size getSize()
/* 140:    */   {
/* 141:151 */     return this.size;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setSize(Size size)
/* 145:    */   {
/* 146:155 */     this.size = size;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String toLoggableString()
/* 150:    */   {
/* 151:160 */     return getTable().getLoggableValueQualifier() + '.' + getColumnName();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String getAlias(Dialect dialect)
/* 155:    */   {
/* 156:165 */     String alias = this.columnName.getName();
/* 157:166 */     int lastLetter = StringHelper.lastIndexOfLetter(this.columnName.getName());
/* 158:167 */     if (lastLetter == -1) {
/* 159:168 */       alias = "column";
/* 160:    */     }
/* 161:170 */     boolean useRawName = (this.columnName.getName().equals(alias)) && (alias.length() <= dialect.getMaxAliasLength()) && (!this.columnName.isQuoted()) && (!this.columnName.getName().toLowerCase().equals("rowid"));
/* 162:175 */     if (!useRawName)
/* 163:    */     {
/* 164:176 */       String unique = getPosition() + '_' + getTable().getTableNumber() + '_';
/* 165:183 */       if (unique.length() >= dialect.getMaxAliasLength()) {
/* 166:184 */         throw new MappingException("Unique suffix [" + unique + "] length must be less than maximum [" + dialect.getMaxAliasLength() + "]");
/* 167:    */       }
/* 168:188 */       if (alias.length() + unique.length() > dialect.getMaxAliasLength()) {
/* 169:189 */         alias = alias.substring(0, dialect.getMaxAliasLength() - unique.length());
/* 170:    */       }
/* 171:191 */       alias = alias + unique;
/* 172:    */     }
/* 173:193 */     return alias;
/* 174:    */   }
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Column
 * JD-Core Version:    0.7.0.1
 */