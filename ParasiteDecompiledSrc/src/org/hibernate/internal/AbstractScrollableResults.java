/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ import java.sql.Blob;
/*   6:    */ import java.sql.Clob;
/*   7:    */ import java.sql.PreparedStatement;
/*   8:    */ import java.sql.ResultSet;
/*   9:    */ import java.sql.SQLException;
/*  10:    */ import java.util.Calendar;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.Locale;
/*  13:    */ import java.util.TimeZone;
/*  14:    */ import org.hibernate.HibernateException;
/*  15:    */ import org.hibernate.MappingException;
/*  16:    */ import org.hibernate.ScrollableResults;
/*  17:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  18:    */ import org.hibernate.engine.loading.internal.LoadContexts;
/*  19:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  20:    */ import org.hibernate.engine.spi.QueryParameters;
/*  21:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  22:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  23:    */ import org.hibernate.hql.internal.HolderInstantiator;
/*  24:    */ import org.hibernate.loader.Loader;
/*  25:    */ import org.hibernate.type.StandardBasicTypes;
/*  26:    */ import org.hibernate.type.Type;
/*  27:    */ import org.jboss.logging.Logger;
/*  28:    */ 
/*  29:    */ public abstract class AbstractScrollableResults
/*  30:    */   implements ScrollableResults
/*  31:    */ {
/*  32: 57 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractScrollableResults.class.getName());
/*  33:    */   private final ResultSet resultSet;
/*  34:    */   private final PreparedStatement ps;
/*  35:    */   private final SessionImplementor session;
/*  36:    */   private final Loader loader;
/*  37:    */   private final QueryParameters queryParameters;
/*  38:    */   private final Type[] types;
/*  39:    */   private HolderInstantiator holderInstantiator;
/*  40:    */   
/*  41:    */   public AbstractScrollableResults(ResultSet rs, PreparedStatement ps, SessionImplementor sess, Loader loader, QueryParameters queryParameters, Type[] types, HolderInstantiator holderInstantiator)
/*  42:    */     throws MappingException
/*  43:    */   {
/*  44: 76 */     this.resultSet = rs;
/*  45: 77 */     this.ps = ps;
/*  46: 78 */     this.session = sess;
/*  47: 79 */     this.loader = loader;
/*  48: 80 */     this.queryParameters = queryParameters;
/*  49: 81 */     this.types = types;
/*  50: 82 */     this.holderInstantiator = ((holderInstantiator != null) && (holderInstantiator.isRequired()) ? holderInstantiator : null);
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected abstract Object[] getCurrentRow();
/*  54:    */   
/*  55:    */   protected ResultSet getResultSet()
/*  56:    */   {
/*  57: 90 */     return this.resultSet;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected PreparedStatement getPs()
/*  61:    */   {
/*  62: 94 */     return this.ps;
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected SessionImplementor getSession()
/*  66:    */   {
/*  67: 98 */     return this.session;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected Loader getLoader()
/*  71:    */   {
/*  72:102 */     return this.loader;
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected QueryParameters getQueryParameters()
/*  76:    */   {
/*  77:106 */     return this.queryParameters;
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected Type[] getTypes()
/*  81:    */   {
/*  82:110 */     return this.types;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected HolderInstantiator getHolderInstantiator()
/*  86:    */   {
/*  87:114 */     return this.holderInstantiator;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public final void close()
/*  91:    */     throws HibernateException
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:121 */       this.ps.close(); return;
/*  96:    */     }
/*  97:    */     catch (SQLException sqle)
/*  98:    */     {
/*  99:124 */       throw this.session.getFactory().getSQLExceptionHelper().convert(sqle, "could not close results");
/* 100:    */     }
/* 101:    */     finally
/* 102:    */     {
/* 103:    */       try
/* 104:    */       {
/* 105:131 */         this.session.getPersistenceContext().getLoadContexts().cleanup(this.resultSet);
/* 106:    */       }
/* 107:    */       catch (Throwable ignore)
/* 108:    */       {
/* 109:135 */         if (LOG.isTraceEnabled()) {
/* 110:136 */           LOG.tracev("Exception trying to cleanup load context : {0}", ignore.getMessage());
/* 111:    */         }
/* 112:    */       }
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public final Object[] get()
/* 117:    */     throws HibernateException
/* 118:    */   {
/* 119:143 */     return getCurrentRow();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final Object get(int col)
/* 123:    */     throws HibernateException
/* 124:    */   {
/* 125:147 */     return getCurrentRow()[col];
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected final Object getFinal(int col, Type returnType)
/* 129:    */     throws HibernateException
/* 130:    */   {
/* 131:159 */     if (this.holderInstantiator != null) {
/* 132:160 */       throw new HibernateException("query specifies a holder class");
/* 133:    */     }
/* 134:163 */     if (returnType.getReturnedClass() == this.types[col].getReturnedClass()) {
/* 135:164 */       return get(col);
/* 136:    */     }
/* 137:167 */     return throwInvalidColumnTypeException(col, this.types[col], returnType);
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected final Object getNonFinal(int col, Type returnType)
/* 141:    */     throws HibernateException
/* 142:    */   {
/* 143:180 */     if (this.holderInstantiator != null) {
/* 144:181 */       throw new HibernateException("query specifies a holder class");
/* 145:    */     }
/* 146:184 */     if (returnType.getReturnedClass().isAssignableFrom(this.types[col].getReturnedClass())) {
/* 147:185 */       return get(col);
/* 148:    */     }
/* 149:188 */     return throwInvalidColumnTypeException(col, this.types[col], returnType);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public final BigDecimal getBigDecimal(int col)
/* 153:    */     throws HibernateException
/* 154:    */   {
/* 155:193 */     return (BigDecimal)getFinal(col, StandardBasicTypes.BIG_DECIMAL);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public final BigInteger getBigInteger(int col)
/* 159:    */     throws HibernateException
/* 160:    */   {
/* 161:197 */     return (BigInteger)getFinal(col, StandardBasicTypes.BIG_INTEGER);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public final byte[] getBinary(int col)
/* 165:    */     throws HibernateException
/* 166:    */   {
/* 167:201 */     return (byte[])getFinal(col, StandardBasicTypes.BINARY);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public final String getText(int col)
/* 171:    */     throws HibernateException
/* 172:    */   {
/* 173:205 */     return (String)getFinal(col, StandardBasicTypes.TEXT);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public final Blob getBlob(int col)
/* 177:    */     throws HibernateException
/* 178:    */   {
/* 179:209 */     return (Blob)getNonFinal(col, StandardBasicTypes.BLOB);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public final Clob getClob(int col)
/* 183:    */     throws HibernateException
/* 184:    */   {
/* 185:213 */     return (Clob)getNonFinal(col, StandardBasicTypes.CLOB);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public final Boolean getBoolean(int col)
/* 189:    */     throws HibernateException
/* 190:    */   {
/* 191:217 */     return (Boolean)getFinal(col, StandardBasicTypes.BOOLEAN);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public final Byte getByte(int col)
/* 195:    */     throws HibernateException
/* 196:    */   {
/* 197:221 */     return (Byte)getFinal(col, StandardBasicTypes.BYTE);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public final Character getCharacter(int col)
/* 201:    */     throws HibernateException
/* 202:    */   {
/* 203:225 */     return (Character)getFinal(col, StandardBasicTypes.CHARACTER);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public final Date getDate(int col)
/* 207:    */     throws HibernateException
/* 208:    */   {
/* 209:229 */     return (Date)getNonFinal(col, StandardBasicTypes.TIMESTAMP);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public final Calendar getCalendar(int col)
/* 213:    */     throws HibernateException
/* 214:    */   {
/* 215:233 */     return (Calendar)getNonFinal(col, StandardBasicTypes.CALENDAR);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public final Double getDouble(int col)
/* 219:    */     throws HibernateException
/* 220:    */   {
/* 221:237 */     return (Double)getFinal(col, StandardBasicTypes.DOUBLE);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public final Float getFloat(int col)
/* 225:    */     throws HibernateException
/* 226:    */   {
/* 227:241 */     return (Float)getFinal(col, StandardBasicTypes.FLOAT);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public final Integer getInteger(int col)
/* 231:    */     throws HibernateException
/* 232:    */   {
/* 233:245 */     return (Integer)getFinal(col, StandardBasicTypes.INTEGER);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public final Long getLong(int col)
/* 237:    */     throws HibernateException
/* 238:    */   {
/* 239:249 */     return (Long)getFinal(col, StandardBasicTypes.LONG);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public final Short getShort(int col)
/* 243:    */     throws HibernateException
/* 244:    */   {
/* 245:253 */     return (Short)getFinal(col, StandardBasicTypes.SHORT);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public final String getString(int col)
/* 249:    */     throws HibernateException
/* 250:    */   {
/* 251:257 */     return (String)getFinal(col, StandardBasicTypes.STRING);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public final Locale getLocale(int col)
/* 255:    */     throws HibernateException
/* 256:    */   {
/* 257:261 */     return (Locale)getFinal(col, StandardBasicTypes.LOCALE);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public final TimeZone getTimeZone(int col)
/* 261:    */     throws HibernateException
/* 262:    */   {
/* 263:269 */     return (TimeZone)getNonFinal(col, StandardBasicTypes.TIMEZONE);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public final Type getType(int i)
/* 267:    */   {
/* 268:273 */     return this.types[i];
/* 269:    */   }
/* 270:    */   
/* 271:    */   private Object throwInvalidColumnTypeException(int i, Type type, Type returnType)
/* 272:    */     throws HibernateException
/* 273:    */   {
/* 274:280 */     throw new HibernateException("incompatible column types: " + type.getName() + ", " + returnType.getName());
/* 275:    */   }
/* 276:    */   
/* 277:    */   protected void afterScrollOperation()
/* 278:    */   {
/* 279:289 */     this.session.afterScrollOperation();
/* 280:    */   }
/* 281:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.AbstractScrollableResults
 * JD-Core Version:    0.7.0.1
 */