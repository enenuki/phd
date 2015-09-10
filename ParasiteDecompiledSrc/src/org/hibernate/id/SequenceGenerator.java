/*   1:    */ package org.hibernate.id;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Properties;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.cfg.ObjectNameNormalizer;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  12:    */ import org.hibernate.mapping.Table;
/*  13:    */ import org.hibernate.type.Type;
/*  14:    */ import org.jboss.logging.Logger;
/*  15:    */ 
/*  16:    */ public class SequenceGenerator
/*  17:    */   implements PersistentIdentifierGenerator, Configurable
/*  18:    */ {
/*  19: 57 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SequenceGenerator.class.getName());
/*  20:    */   public static final String SEQUENCE = "sequence";
/*  21:    */   public static final String PARAMETERS = "parameters";
/*  22:    */   private String sequenceName;
/*  23:    */   private String parameters;
/*  24:    */   private Type identifierType;
/*  25:    */   private String sql;
/*  26:    */   
/*  27:    */   protected Type getIdentifierType()
/*  28:    */   {
/*  29: 76 */     return this.identifierType;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void configure(Type type, Properties params, Dialect dialect)
/*  33:    */     throws MappingException
/*  34:    */   {
/*  35: 80 */     ObjectNameNormalizer normalizer = (ObjectNameNormalizer)params.get("identifier_normalizer");
/*  36: 81 */     this.sequenceName = normalizer.normalizeIdentifierQuoting(ConfigurationHelper.getString("sequence", params, "hibernate_sequence"));
/*  37:    */     
/*  38:    */ 
/*  39: 84 */     this.parameters = params.getProperty("parameters");
/*  40: 86 */     if (this.sequenceName.indexOf('.') < 0)
/*  41:    */     {
/*  42: 87 */       String schemaName = normalizer.normalizeIdentifierQuoting(params.getProperty("schema"));
/*  43: 88 */       String catalogName = normalizer.normalizeIdentifierQuoting(params.getProperty("catalog"));
/*  44: 89 */       this.sequenceName = Table.qualify(dialect.quote(catalogName), dialect.quote(schemaName), dialect.quote(this.sequenceName));
/*  45:    */     }
/*  46:100 */     this.identifierType = type;
/*  47:101 */     this.sql = dialect.getSequenceNextValString(this.sequenceName);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Serializable generate(SessionImplementor session, Object obj)
/*  51:    */   {
/*  52:105 */     return generateHolder(session).makeValue();
/*  53:    */   }
/*  54:    */   
/*  55:    */   /* Error */
/*  56:    */   protected IntegralDataTypeHolder generateHolder(SessionImplementor session)
/*  57:    */   {
/*  58:    */     // Byte code:
/*  59:    */     //   0: aload_1
/*  60:    */     //   1: invokeinterface 23 1 0
/*  61:    */     //   6: invokeinterface 24 1 0
/*  62:    */     //   11: invokeinterface 25 1 0
/*  63:    */     //   16: aload_0
/*  64:    */     //   17: getfield 20	org/hibernate/id/SequenceGenerator:sql	Ljava/lang/String;
/*  65:    */     //   20: invokeinterface 26 2 0
/*  66:    */     //   25: astore_2
/*  67:    */     //   26: aload_2
/*  68:    */     //   27: invokeinterface 27 1 0
/*  69:    */     //   32: astore_3
/*  70:    */     //   33: aload_3
/*  71:    */     //   34: invokeinterface 28 1 0
/*  72:    */     //   39: pop
/*  73:    */     //   40: aload_0
/*  74:    */     //   41: invokevirtual 29	org/hibernate/id/SequenceGenerator:buildHolder	()Lorg/hibernate/id/IntegralDataTypeHolder;
/*  75:    */     //   44: astore 4
/*  76:    */     //   46: aload 4
/*  77:    */     //   48: aload_3
/*  78:    */     //   49: lconst_1
/*  79:    */     //   50: invokeinterface 30 4 0
/*  80:    */     //   55: pop
/*  81:    */     //   56: getstatic 31	org/hibernate/id/SequenceGenerator:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/*  82:    */     //   59: ldc 32
/*  83:    */     //   61: aload 4
/*  84:    */     //   63: invokeinterface 33 3 0
/*  85:    */     //   68: aload 4
/*  86:    */     //   70: astore 5
/*  87:    */     //   72: aload_3
/*  88:    */     //   73: invokeinterface 34 1 0
/*  89:    */     //   78: aload_2
/*  90:    */     //   79: invokeinterface 35 1 0
/*  91:    */     //   84: aload 5
/*  92:    */     //   86: areturn
/*  93:    */     //   87: astore 6
/*  94:    */     //   89: aload_3
/*  95:    */     //   90: invokeinterface 34 1 0
/*  96:    */     //   95: aload 6
/*  97:    */     //   97: athrow
/*  98:    */     //   98: astore 7
/*  99:    */     //   100: aload_2
/* 100:    */     //   101: invokeinterface 35 1 0
/* 101:    */     //   106: aload 7
/* 102:    */     //   108: athrow
/* 103:    */     //   109: astore_2
/* 104:    */     //   110: aload_1
/* 105:    */     //   111: invokeinterface 37 1 0
/* 106:    */     //   116: invokeinterface 38 1 0
/* 107:    */     //   121: aload_2
/* 108:    */     //   122: ldc 39
/* 109:    */     //   124: aload_0
/* 110:    */     //   125: getfield 20	org/hibernate/id/SequenceGenerator:sql	Ljava/lang/String;
/* 111:    */     //   128: invokevirtual 40	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 112:    */     //   131: athrow
/* 113:    */     // Line number table:
/* 114:    */     //   Java source line #110	-> byte code offset #0
/* 115:    */     //   Java source line #112	-> byte code offset #26
/* 116:    */     //   Java source line #114	-> byte code offset #33
/* 117:    */     //   Java source line #115	-> byte code offset #40
/* 118:    */     //   Java source line #116	-> byte code offset #46
/* 119:    */     //   Java source line #117	-> byte code offset #56
/* 120:    */     //   Java source line #118	-> byte code offset #68
/* 121:    */     //   Java source line #121	-> byte code offset #72
/* 122:    */     //   Java source line #125	-> byte code offset #78
/* 123:    */     //   Java source line #121	-> byte code offset #87
/* 124:    */     //   Java source line #125	-> byte code offset #98
/* 125:    */     //   Java source line #129	-> byte code offset #109
/* 126:    */     //   Java source line #130	-> byte code offset #110
/* 127:    */     // Local variable table:
/* 128:    */     //   start	length	slot	name	signature
/* 129:    */     //   0	132	0	this	SequenceGenerator
/* 130:    */     //   0	132	1	session	SessionImplementor
/* 131:    */     //   25	76	2	st	java.sql.PreparedStatement
/* 132:    */     //   109	13	2	sqle	java.sql.SQLException
/* 133:    */     //   32	58	3	rs	java.sql.ResultSet
/* 134:    */     //   44	25	4	result	IntegralDataTypeHolder
/* 135:    */     //   87	9	6	localObject1	Object
/* 136:    */     //   98	9	7	localObject2	Object
/* 137:    */     // Exception table:
/* 138:    */     //   from	to	target	type
/* 139:    */     //   33	72	87	finally
/* 140:    */     //   87	89	87	finally
/* 141:    */     //   26	78	98	finally
/* 142:    */     //   87	100	98	finally
/* 143:    */     //   0	84	109	java/sql/SQLException
/* 144:    */     //   87	109	109	java/sql/SQLException
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected IntegralDataTypeHolder buildHolder()
/* 148:    */   {
/* 149:139 */     return IdentifierGeneratorHelper.getIntegralDataTypeHolder(this.identifierType.getReturnedClass());
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String[] sqlCreateStrings(Dialect dialect)
/* 153:    */     throws HibernateException
/* 154:    */   {
/* 155:143 */     String[] ddl = dialect.getCreateSequenceStrings(this.sequenceName);
/* 156:144 */     if (this.parameters != null)
/* 157:    */     {
/* 158:145 */       int tmp28_27 = (ddl.length - 1); String[] tmp28_23 = ddl;tmp28_23[tmp28_27] = (tmp28_23[tmp28_27] + ' ' + this.parameters);
/* 159:    */     }
/* 160:147 */     return ddl;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String[] sqlDropStrings(Dialect dialect)
/* 164:    */     throws HibernateException
/* 165:    */   {
/* 166:151 */     return dialect.getDropSequenceStrings(this.sequenceName);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Object generatorKey()
/* 170:    */   {
/* 171:155 */     return this.sequenceName;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String getSequenceName()
/* 175:    */   {
/* 176:159 */     return this.sequenceName;
/* 177:    */   }
/* 178:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.SequenceGenerator
 * JD-Core Version:    0.7.0.1
 */