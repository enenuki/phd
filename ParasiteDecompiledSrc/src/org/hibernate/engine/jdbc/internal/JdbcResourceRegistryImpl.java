/*   1:    */ package org.hibernate.engine.jdbc.internal;
/*   2:    */ 
/*   3:    */ import java.sql.ResultSet;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.sql.Statement;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.engine.jdbc.spi.InvalidatableWrapper;
/*  12:    */ import org.hibernate.engine.jdbc.spi.JdbcResourceRegistry;
/*  13:    */ import org.hibernate.engine.jdbc.spi.JdbcWrapper;
/*  14:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  15:    */ import org.hibernate.internal.CoreMessageLogger;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ import org.jboss.logging.Logger.Level;
/*  18:    */ 
/*  19:    */ public class JdbcResourceRegistryImpl
/*  20:    */   implements JdbcResourceRegistry
/*  21:    */ {
/*  22: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JdbcResourceRegistryImpl.class.getName());
/*  23: 53 */   private final HashMap<Statement, Set<ResultSet>> xref = new HashMap();
/*  24: 54 */   private final Set<ResultSet> unassociatedResultSets = new HashSet();
/*  25:    */   private final SqlExceptionHelper exceptionHelper;
/*  26:    */   private Statement lastQuery;
/*  27:    */   
/*  28:    */   public JdbcResourceRegistryImpl(SqlExceptionHelper exceptionHelper)
/*  29:    */   {
/*  30: 60 */     this.exceptionHelper = exceptionHelper;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void register(Statement statement)
/*  34:    */   {
/*  35: 64 */     LOG.tracev("Registering statement [{0}]", statement);
/*  36: 65 */     if (this.xref.containsKey(statement)) {
/*  37: 66 */       throw new HibernateException("statement already registered with JDBCContainer");
/*  38:    */     }
/*  39: 68 */     this.xref.put(statement, null);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void registerLastQuery(Statement statement)
/*  43:    */   {
/*  44: 73 */     LOG.tracev("Registering last query statement [{0}]", statement);
/*  45: 74 */     if ((statement instanceof JdbcWrapper))
/*  46:    */     {
/*  47: 75 */       JdbcWrapper<Statement> wrapper = (JdbcWrapper)statement;
/*  48: 76 */       registerLastQuery((Statement)wrapper.getWrappedObject());
/*  49: 77 */       return;
/*  50:    */     }
/*  51: 79 */     this.lastQuery = statement;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void cancelLastQuery()
/*  55:    */   {
/*  56:    */     try
/*  57:    */     {
/*  58: 84 */       if (this.lastQuery != null) {
/*  59: 85 */         this.lastQuery.cancel();
/*  60:    */       }
/*  61:    */     }
/*  62:    */     catch (SQLException sqle)
/*  63:    */     {
/*  64: 89 */       throw this.exceptionHelper.convert(sqle, "Cannot cancel query");
/*  65:    */     }
/*  66:    */     finally
/*  67:    */     {
/*  68: 95 */       this.lastQuery = null;
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void release(Statement statement)
/*  73:    */   {
/*  74:100 */     LOG.tracev("Releasing statement [{0}]", statement);
/*  75:101 */     Set<ResultSet> resultSets = (Set)this.xref.get(statement);
/*  76:102 */     if (resultSets != null)
/*  77:    */     {
/*  78:103 */       for (ResultSet resultSet : resultSets) {
/*  79:104 */         close(resultSet);
/*  80:    */       }
/*  81:106 */       resultSets.clear();
/*  82:    */     }
/*  83:108 */     this.xref.remove(statement);
/*  84:109 */     close(statement);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void register(ResultSet resultSet)
/*  88:    */   {
/*  89:113 */     LOG.tracev("Registering result set [{0}]", resultSet);
/*  90:    */     Statement statement;
/*  91:    */     try
/*  92:    */     {
/*  93:116 */       statement = resultSet.getStatement();
/*  94:    */     }
/*  95:    */     catch (SQLException e)
/*  96:    */     {
/*  97:119 */       throw this.exceptionHelper.convert(e, "unable to access statement from resultset");
/*  98:    */     }
/*  99:121 */     if (statement != null)
/* 100:    */     {
/* 101:122 */       if ((LOG.isEnabled(Logger.Level.WARN)) && (!this.xref.containsKey(statement))) {
/* 102:123 */         LOG.unregisteredStatement();
/* 103:    */       }
/* 104:125 */       Set<ResultSet> resultSets = (Set)this.xref.get(statement);
/* 105:126 */       if (resultSets == null)
/* 106:    */       {
/* 107:127 */         resultSets = new HashSet();
/* 108:128 */         this.xref.put(statement, resultSets);
/* 109:    */       }
/* 110:130 */       resultSets.add(resultSet);
/* 111:    */     }
/* 112:    */     else
/* 113:    */     {
/* 114:133 */       this.unassociatedResultSets.add(resultSet);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void release(ResultSet resultSet)
/* 119:    */   {
/* 120:138 */     LOG.tracev("Releasing result set [{0}]", resultSet);
/* 121:    */     Statement statement;
/* 122:    */     try
/* 123:    */     {
/* 124:141 */       statement = resultSet.getStatement();
/* 125:    */     }
/* 126:    */     catch (SQLException e)
/* 127:    */     {
/* 128:144 */       throw this.exceptionHelper.convert(e, "unable to access statement from resultset");
/* 129:    */     }
/* 130:146 */     if (statement != null)
/* 131:    */     {
/* 132:147 */       if ((LOG.isEnabled(Logger.Level.WARN)) && (!this.xref.containsKey(statement))) {
/* 133:148 */         LOG.unregisteredStatement();
/* 134:    */       }
/* 135:150 */       Set<ResultSet> resultSets = (Set)this.xref.get(statement);
/* 136:151 */       if (resultSets != null)
/* 137:    */       {
/* 138:152 */         resultSets.remove(resultSet);
/* 139:153 */         if (resultSets.isEmpty()) {
/* 140:154 */           this.xref.remove(statement);
/* 141:    */         }
/* 142:    */       }
/* 143:    */     }
/* 144:    */     else
/* 145:    */     {
/* 146:159 */       boolean removed = this.unassociatedResultSets.remove(resultSet);
/* 147:160 */       if (!removed) {
/* 148:160 */         LOG.unregisteredResultSetWithoutStatement();
/* 149:    */       }
/* 150:    */     }
/* 151:162 */     close(resultSet);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean hasRegisteredResources()
/* 155:    */   {
/* 156:166 */     return (!this.xref.isEmpty()) || (!this.unassociatedResultSets.isEmpty());
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void releaseResources()
/* 160:    */   {
/* 161:170 */     LOG.tracev("Releasing JDBC container resources [{0}]", this);
/* 162:171 */     cleanup();
/* 163:    */   }
/* 164:    */   
/* 165:    */   private void cleanup()
/* 166:    */   {
/* 167:175 */     for (Map.Entry<Statement, Set<ResultSet>> entry : this.xref.entrySet())
/* 168:    */     {
/* 169:176 */       if (entry.getValue() != null)
/* 170:    */       {
/* 171:177 */         for (ResultSet resultSet : (Set)entry.getValue()) {
/* 172:178 */           close(resultSet);
/* 173:    */         }
/* 174:180 */         ((Set)entry.getValue()).clear();
/* 175:    */       }
/* 176:182 */       close((Statement)entry.getKey());
/* 177:    */     }
/* 178:184 */     this.xref.clear();
/* 179:186 */     for (ResultSet resultSet : this.unassociatedResultSets) {
/* 180:187 */       close(resultSet);
/* 181:    */     }
/* 182:189 */     this.unassociatedResultSets.clear();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void close()
/* 186:    */   {
/* 187:246 */     LOG.tracev("Closing JDBC container [{0}]", this);
/* 188:247 */     cleanup();
/* 189:    */   }
/* 190:    */   
/* 191:    */   protected void close(Statement statement)
/* 192:    */   {
/* 193:252 */     LOG.tracev("Closing prepared statement [{0}]", statement);
/* 194:254 */     if ((statement instanceof InvalidatableWrapper))
/* 195:    */     {
/* 196:255 */       InvalidatableWrapper<Statement> wrapper = (InvalidatableWrapper)statement;
/* 197:256 */       close((Statement)wrapper.getWrappedObject());
/* 198:257 */       wrapper.invalidate();
/* 199:258 */       return;
/* 200:    */     }
/* 201:    */     try
/* 202:    */     {
/* 203:    */       try
/* 204:    */       {
/* 205:265 */         if (statement.getMaxRows() != 0) {
/* 206:266 */           statement.setMaxRows(0);
/* 207:    */         }
/* 208:268 */         if (statement.getQueryTimeout() != 0) {
/* 209:269 */           statement.setQueryTimeout(0);
/* 210:    */         }
/* 211:    */       }
/* 212:    */       catch (SQLException sqle)
/* 213:    */       {
/* 214:274 */         if (LOG.isDebugEnabled()) {
/* 215:275 */           LOG.debugf("Exception clearing maxRows/queryTimeout [%s]", sqle.getMessage());
/* 216:    */         }
/* 217:277 */         return;
/* 218:    */       }
/* 219:279 */       statement.close();
/* 220:280 */       if (this.lastQuery == statement) {
/* 221:281 */         this.lastQuery = null;
/* 222:    */       }
/* 223:    */     }
/* 224:    */     catch (SQLException sqle)
/* 225:    */     {
/* 226:285 */       if (LOG.isDebugEnabled()) {
/* 227:286 */         LOG.debugf("Unable to release statement [%s]", sqle.getMessage());
/* 228:    */       }
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   protected void close(ResultSet resultSet)
/* 233:    */   {
/* 234:293 */     LOG.tracev("Closing result set [{0}]", resultSet);
/* 235:295 */     if ((resultSet instanceof InvalidatableWrapper))
/* 236:    */     {
/* 237:296 */       InvalidatableWrapper<ResultSet> wrapper = (InvalidatableWrapper)resultSet;
/* 238:297 */       close((ResultSet)wrapper.getWrappedObject());
/* 239:298 */       wrapper.invalidate();
/* 240:    */     }
/* 241:    */     try
/* 242:    */     {
/* 243:302 */       resultSet.close();
/* 244:    */     }
/* 245:    */     catch (SQLException e)
/* 246:    */     {
/* 247:305 */       if (LOG.isDebugEnabled()) {
/* 248:306 */         LOG.debugf("Unable to release result set [%s]", e.getMessage());
/* 249:    */       }
/* 250:    */     }
/* 251:    */   }
/* 252:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.JdbcResourceRegistryImpl
 * JD-Core Version:    0.7.0.1
 */