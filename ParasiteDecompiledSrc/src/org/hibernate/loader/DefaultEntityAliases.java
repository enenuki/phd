/*   1:    */ package org.hibernate.loader;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   5:    */ import org.hibernate.internal.util.StringHelper;
/*   6:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*   7:    */ import org.hibernate.persister.entity.Loadable;
/*   8:    */ 
/*   9:    */ public class DefaultEntityAliases
/*  10:    */   implements EntityAliases
/*  11:    */ {
/*  12:    */   private final String[] suffixedKeyColumns;
/*  13:    */   private final String[] suffixedVersionColumn;
/*  14:    */   private final String[][] suffixedPropertyColumns;
/*  15:    */   private final String suffixedDiscriminatorColumn;
/*  16:    */   private final String suffix;
/*  17:    */   private final String rowIdAlias;
/*  18:    */   private final Map userProvidedAliases;
/*  19:    */   
/*  20:    */   public DefaultEntityAliases(Map userProvidedAliases, Loadable persister, String suffix)
/*  21:    */   {
/*  22: 61 */     this.suffix = suffix;
/*  23: 62 */     this.userProvidedAliases = userProvidedAliases;
/*  24:    */     
/*  25: 64 */     this.suffixedKeyColumns = determineKeyAlias(persister, suffix);
/*  26: 65 */     this.suffixedPropertyColumns = determinePropertyAliases(persister);
/*  27: 66 */     this.suffixedDiscriminatorColumn = determineDiscriminatorAlias(persister, suffix);
/*  28: 67 */     this.suffixedVersionColumn = determineVersionAlias(persister);
/*  29: 68 */     this.rowIdAlias = ("rowid_" + suffix);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public DefaultEntityAliases(Loadable persister, String suffix)
/*  33:    */   {
/*  34: 72 */     this(CollectionHelper.EMPTY_MAP, persister, suffix);
/*  35:    */   }
/*  36:    */   
/*  37:    */   private String[] determineKeyAlias(Loadable persister, String suffix)
/*  38:    */   {
/*  39: 77 */     String[] keyColumnsCandidates = getUserProvidedAliases(persister.getIdentifierPropertyName(), null);
/*  40:    */     String[] aliases;
/*  41:    */     String[] aliases;
/*  42: 78 */     if (keyColumnsCandidates == null) {
/*  43: 79 */       aliases = getUserProvidedAliases("id", getIdentifierAliases(persister, suffix));
/*  44:    */     } else {
/*  45: 85 */       aliases = keyColumnsCandidates;
/*  46:    */     }
/*  47: 87 */     String[] rtn = StringHelper.unquote(aliases, persister.getFactory().getDialect());
/*  48: 88 */     intern(rtn);
/*  49: 89 */     return rtn;
/*  50:    */   }
/*  51:    */   
/*  52:    */   private String[][] determinePropertyAliases(Loadable persister)
/*  53:    */   {
/*  54: 93 */     return getSuffixedPropertyAliases(persister);
/*  55:    */   }
/*  56:    */   
/*  57:    */   private String determineDiscriminatorAlias(Loadable persister, String suffix)
/*  58:    */   {
/*  59: 97 */     String alias = getUserProvidedAlias("class", getDiscriminatorAlias(persister, suffix));
/*  60: 98 */     return StringHelper.unquote(alias, persister.getFactory().getDialect());
/*  61:    */   }
/*  62:    */   
/*  63:    */   private String[] determineVersionAlias(Loadable persister)
/*  64:    */   {
/*  65:102 */     return persister.isVersioned() ? this.suffixedPropertyColumns[persister.getVersionProperty()] : null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected String getDiscriminatorAlias(Loadable persister, String suffix)
/*  69:    */   {
/*  70:108 */     return persister.getDiscriminatorAlias(suffix);
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected String[] getIdentifierAliases(Loadable persister, String suffix)
/*  74:    */   {
/*  75:112 */     return persister.getIdentifierAliases(suffix);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected String[] getPropertyAliases(Loadable persister, int j)
/*  79:    */   {
/*  80:116 */     return persister.getPropertyAliases(this.suffix, j);
/*  81:    */   }
/*  82:    */   
/*  83:    */   private String[] getUserProvidedAliases(String propertyPath, String[] defaultAliases)
/*  84:    */   {
/*  85:120 */     String[] result = (String[])this.userProvidedAliases.get(propertyPath);
/*  86:121 */     if (result == null) {
/*  87:122 */       return defaultAliases;
/*  88:    */     }
/*  89:125 */     return result;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private String getUserProvidedAlias(String propertyPath, String defaultAlias)
/*  93:    */   {
/*  94:130 */     String[] columns = (String[])this.userProvidedAliases.get(propertyPath);
/*  95:131 */     if (columns == null) {
/*  96:132 */       return defaultAlias;
/*  97:    */     }
/*  98:135 */     return columns[0];
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String[][] getSuffixedPropertyAliases(Loadable persister)
/* 102:    */   {
/* 103:143 */     int size = persister.getPropertyNames().length;
/* 104:144 */     String[][] suffixedPropertyAliases = new String[size][];
/* 105:145 */     for (int j = 0; j < size; j++)
/* 106:    */     {
/* 107:146 */       suffixedPropertyAliases[j] = getUserProvidedAliases(persister.getPropertyNames()[j], getPropertyAliases(persister, j));
/* 108:    */       
/* 109:    */ 
/* 110:    */ 
/* 111:150 */       suffixedPropertyAliases[j] = StringHelper.unquote(suffixedPropertyAliases[j], persister.getFactory().getDialect());
/* 112:151 */       intern(suffixedPropertyAliases[j]);
/* 113:    */     }
/* 114:153 */     return suffixedPropertyAliases;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String[] getSuffixedVersionAliases()
/* 118:    */   {
/* 119:160 */     return this.suffixedVersionColumn;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String[][] getSuffixedPropertyAliases()
/* 123:    */   {
/* 124:167 */     return this.suffixedPropertyColumns;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getSuffixedDiscriminatorAlias()
/* 128:    */   {
/* 129:174 */     return this.suffixedDiscriminatorColumn;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String[] getSuffixedKeyAliases()
/* 133:    */   {
/* 134:181 */     return this.suffixedKeyColumns;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getRowIdAlias()
/* 138:    */   {
/* 139:188 */     return this.rowIdAlias;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private static void intern(String[] strings)
/* 143:    */   {
/* 144:192 */     for (int i = 0; i < strings.length; i++) {
/* 145:193 */       strings[i] = strings[i].intern();
/* 146:    */     }
/* 147:    */   }
/* 148:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.DefaultEntityAliases
 * JD-Core Version:    0.7.0.1
 */