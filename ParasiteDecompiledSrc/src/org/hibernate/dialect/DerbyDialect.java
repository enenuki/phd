/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import org.hibernate.MappingException;
/*   5:    */ import org.hibernate.dialect.function.AnsiTrimFunction;
/*   6:    */ import org.hibernate.dialect.function.DerbyConcatFunction;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.hibernate.internal.util.ReflectHelper;
/*   9:    */ import org.hibernate.sql.CaseFragment;
/*  10:    */ import org.hibernate.sql.DerbyCaseFragment;
/*  11:    */ import org.jboss.logging.Logger;
/*  12:    */ 
/*  13:    */ @Deprecated
/*  14:    */ public class DerbyDialect
/*  15:    */   extends DB2Dialect
/*  16:    */ {
/*  17: 52 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DerbyDialect.class.getName());
/*  18:    */   private int driverVersionMajor;
/*  19:    */   private int driverVersionMinor;
/*  20:    */   
/*  21:    */   public DerbyDialect()
/*  22:    */   {
/*  23: 59 */     LOG.deprecatedDerbyDialect();
/*  24: 60 */     registerFunction("concat", new DerbyConcatFunction());
/*  25: 61 */     registerFunction("trim", new AnsiTrimFunction());
/*  26: 62 */     registerColumnType(2004, "blob");
/*  27: 63 */     determineDriverVersion();
/*  28: 65 */     if ((this.driverVersionMajor > 10) || ((this.driverVersionMajor == 10) && (this.driverVersionMinor >= 7))) {
/*  29: 66 */       registerColumnType(16, "boolean");
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   private void determineDriverVersion()
/*  34:    */   {
/*  35:    */     try
/*  36:    */     {
/*  37: 74 */       Class sysinfoClass = ReflectHelper.classForName("org.apache.derby.tools.sysinfo", getClass());
/*  38: 75 */       Method majorVersionGetter = sysinfoClass.getMethod("getMajorVersion", ReflectHelper.NO_PARAM_SIGNATURE);
/*  39: 76 */       Method minorVersionGetter = sysinfoClass.getMethod("getMinorVersion", ReflectHelper.NO_PARAM_SIGNATURE);
/*  40: 77 */       this.driverVersionMajor = ((Integer)majorVersionGetter.invoke(null, ReflectHelper.NO_PARAMS)).intValue();
/*  41: 78 */       this.driverVersionMinor = ((Integer)minorVersionGetter.invoke(null, ReflectHelper.NO_PARAMS)).intValue();
/*  42:    */     }
/*  43:    */     catch (Exception e)
/*  44:    */     {
/*  45: 81 */       LOG.unableToLoadDerbyDriver(e.getMessage());
/*  46: 82 */       this.driverVersionMajor = -1;
/*  47: 83 */       this.driverVersionMinor = -1;
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   private boolean isTenPointFiveReleaseOrNewer()
/*  52:    */   {
/*  53: 88 */     return (this.driverVersionMajor > 10) || ((this.driverVersionMajor == 10) && (this.driverVersionMinor >= 5));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getCrossJoinSeparator()
/*  57:    */   {
/*  58: 93 */     return ", ";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public CaseFragment createCaseFragment()
/*  62:    */   {
/*  63:101 */     return new DerbyCaseFragment();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean dropConstraints()
/*  67:    */   {
/*  68:106 */     return true;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean supportsSequences()
/*  72:    */   {
/*  73:121 */     return (this.driverVersionMajor > 10) || ((this.driverVersionMajor == 10) && (this.driverVersionMinor >= 6));
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getSequenceNextValString(String sequenceName)
/*  77:    */   {
/*  78:126 */     if (supportsSequences()) {
/*  79:127 */       return "values next value for " + sequenceName;
/*  80:    */     }
/*  81:130 */     throw new MappingException("Derby does not support sequence prior to release 10.6.1.0");
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean supportsLimit()
/*  85:    */   {
/*  86:136 */     return isTenPointFiveReleaseOrNewer();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean supportsCommentOn()
/*  90:    */   {
/*  91:142 */     return false;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean supportsLimitOffset()
/*  95:    */   {
/*  96:147 */     return isTenPointFiveReleaseOrNewer();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getForUpdateString()
/* 100:    */   {
/* 101:152 */     return " for update with rs";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getWriteLockString(int timeout)
/* 105:    */   {
/* 106:157 */     return " for update with rs";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getReadLockString(int timeout)
/* 110:    */   {
/* 111:162 */     return " for read only with rs";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getLimitString(String query, int offset, int limit)
/* 115:    */   {
/* 116:181 */     StringBuffer sb = new StringBuffer(query.length() + 50);
/* 117:    */     
/* 118:183 */     String normalizedSelect = query.toLowerCase().trim();
/* 119:184 */     int forUpdateIndex = normalizedSelect.lastIndexOf("for update");
/* 120:186 */     if (hasForUpdateClause(forUpdateIndex)) {
/* 121:187 */       sb.append(query.substring(0, forUpdateIndex - 1));
/* 122:189 */     } else if (hasWithClause(normalizedSelect)) {
/* 123:190 */       sb.append(query.substring(0, getWithIndex(query) - 1));
/* 124:    */     } else {
/* 125:193 */       sb.append(query);
/* 126:    */     }
/* 127:196 */     if (offset == 0) {
/* 128:197 */       sb.append(" fetch first ");
/* 129:    */     } else {
/* 130:200 */       sb.append(" offset ").append(offset).append(" rows fetch next ");
/* 131:    */     }
/* 132:203 */     sb.append(limit).append(" rows only");
/* 133:205 */     if (hasForUpdateClause(forUpdateIndex))
/* 134:    */     {
/* 135:206 */       sb.append(' ');
/* 136:207 */       sb.append(query.substring(forUpdateIndex));
/* 137:    */     }
/* 138:209 */     else if (hasWithClause(normalizedSelect))
/* 139:    */     {
/* 140:210 */       sb.append(' ').append(query.substring(getWithIndex(query)));
/* 141:    */     }
/* 142:212 */     return sb.toString();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean supportsVariableLimit()
/* 146:    */   {
/* 147:218 */     return false;
/* 148:    */   }
/* 149:    */   
/* 150:    */   private boolean hasForUpdateClause(int forUpdateIndex)
/* 151:    */   {
/* 152:222 */     return forUpdateIndex >= 0;
/* 153:    */   }
/* 154:    */   
/* 155:    */   private boolean hasWithClause(String normalizedSelect)
/* 156:    */   {
/* 157:226 */     return normalizedSelect.startsWith("with ", normalizedSelect.length() - 7);
/* 158:    */   }
/* 159:    */   
/* 160:    */   private int getWithIndex(String querySelect)
/* 161:    */   {
/* 162:230 */     int i = querySelect.lastIndexOf("with ");
/* 163:231 */     if (i < 0) {
/* 164:232 */       i = querySelect.lastIndexOf("WITH ");
/* 165:    */     }
/* 166:234 */     return i;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String getQuerySequencesString()
/* 170:    */   {
/* 171:239 */     return null;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean supportsLobValueChangePropogation()
/* 175:    */   {
/* 176:247 */     return false;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean supportsUnboundedLobLocatorMaterialization()
/* 180:    */   {
/* 181:252 */     return false;
/* 182:    */   }
/* 183:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.DerbyDialect
 * JD-Core Version:    0.7.0.1
 */