/*   1:    */ package org.apache.log4j.jdbc;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.DriverManager;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.sql.Statement;
/*   7:    */ import java.util.AbstractCollection;
/*   8:    */ import java.util.AbstractList;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Enumeration;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import org.apache.log4j.Appender;
/*  13:    */ import org.apache.log4j.AppenderSkeleton;
/*  14:    */ import org.apache.log4j.Layout;
/*  15:    */ import org.apache.log4j.PatternLayout;
/*  16:    */ import org.apache.log4j.spi.ErrorHandler;
/*  17:    */ import org.apache.log4j.spi.LoggingEvent;
/*  18:    */ 
/*  19:    */ public class JDBCAppender
/*  20:    */   extends AppenderSkeleton
/*  21:    */   implements Appender
/*  22:    */ {
/*  23: 84 */   protected String databaseURL = "jdbc:odbc:myDB";
/*  24: 89 */   protected String databaseUser = "me";
/*  25: 94 */   protected String databasePassword = "mypassword";
/*  26:103 */   protected Connection connection = null;
/*  27:114 */   protected String sqlStatement = "";
/*  28:120 */   protected int bufferSize = 1;
/*  29:    */   protected ArrayList buffer;
/*  30:    */   protected ArrayList removes;
/*  31:132 */   private boolean locationInfo = false;
/*  32:    */   
/*  33:    */   public JDBCAppender()
/*  34:    */   {
/*  35:136 */     this.buffer = new ArrayList(this.bufferSize);
/*  36:137 */     this.removes = new ArrayList(this.bufferSize);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean getLocationInfo()
/*  40:    */   {
/*  41:148 */     return this.locationInfo;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setLocationInfo(boolean flag)
/*  45:    */   {
/*  46:166 */     this.locationInfo = flag;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void append(LoggingEvent event)
/*  50:    */   {
/*  51:174 */     event.getNDC();
/*  52:175 */     event.getThreadName();
/*  53:    */     
/*  54:177 */     event.getMDCCopy();
/*  55:178 */     if (this.locationInfo) {
/*  56:179 */       event.getLocationInformation();
/*  57:    */     }
/*  58:181 */     event.getRenderedMessage();
/*  59:182 */     event.getThrowableStrRep();
/*  60:183 */     this.buffer.add(event);
/*  61:185 */     if (this.buffer.size() >= this.bufferSize) {
/*  62:186 */       flushBuffer();
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected String getLogStatement(LoggingEvent event)
/*  67:    */   {
/*  68:198 */     return getLayout().format(event);
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void execute(String sql)
/*  72:    */     throws SQLException
/*  73:    */   {
/*  74:211 */     Connection con = null;
/*  75:212 */     Statement stmt = null;
/*  76:    */     try
/*  77:    */     {
/*  78:215 */       con = getConnection();
/*  79:    */       
/*  80:217 */       stmt = con.createStatement();
/*  81:218 */       stmt.executeUpdate(sql);
/*  82:    */     }
/*  83:    */     catch (SQLException e)
/*  84:    */     {
/*  85:220 */       if (stmt != null) {
/*  86:221 */         stmt.close();
/*  87:    */       }
/*  88:222 */       throw e;
/*  89:    */     }
/*  90:224 */     stmt.close();
/*  91:225 */     closeConnection(con);
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected void closeConnection(Connection con) {}
/*  95:    */   
/*  96:    */   protected Connection getConnection()
/*  97:    */     throws SQLException
/*  98:    */   {
/*  99:248 */     if (!DriverManager.getDrivers().hasMoreElements()) {
/* 100:249 */       setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
/* 101:    */     }
/* 102:251 */     if (this.connection == null) {
/* 103:252 */       this.connection = DriverManager.getConnection(this.databaseURL, this.databaseUser, this.databasePassword);
/* 104:    */     }
/* 105:256 */     return this.connection;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void close()
/* 109:    */   {
/* 110:265 */     flushBuffer();
/* 111:    */     try
/* 112:    */     {
/* 113:268 */       if ((this.connection != null) && (!this.connection.isClosed())) {
/* 114:269 */         this.connection.close();
/* 115:    */       }
/* 116:    */     }
/* 117:    */     catch (SQLException e)
/* 118:    */     {
/* 119:271 */       this.errorHandler.error("Error closing connection", e, 0);
/* 120:    */     }
/* 121:273 */     this.closed = true;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void flushBuffer()
/* 125:    */   {
/* 126:285 */     this.removes.ensureCapacity(this.buffer.size());
/* 127:286 */     for (Iterator i = this.buffer.iterator(); i.hasNext();) {
/* 128:    */       try
/* 129:    */       {
/* 130:288 */         LoggingEvent logEvent = (LoggingEvent)i.next();
/* 131:289 */         String sql = getLogStatement(logEvent);
/* 132:290 */         execute(sql);
/* 133:291 */         this.removes.add(logEvent);
/* 134:    */       }
/* 135:    */       catch (SQLException e)
/* 136:    */       {
/* 137:294 */         this.errorHandler.error("Failed to excute sql", e, 2);
/* 138:    */       }
/* 139:    */     }
/* 140:300 */     this.buffer.removeAll(this.removes);
/* 141:    */     
/* 142:    */ 
/* 143:303 */     this.removes.clear();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void finalize()
/* 147:    */   {
/* 148:309 */     close();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean requiresLayout()
/* 152:    */   {
/* 153:317 */     return true;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setSql(String s)
/* 157:    */   {
/* 158:325 */     this.sqlStatement = s;
/* 159:326 */     if (getLayout() == null) {
/* 160:327 */       setLayout(new PatternLayout(s));
/* 161:    */     } else {
/* 162:330 */       ((PatternLayout)getLayout()).setConversionPattern(s);
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getSql()
/* 167:    */   {
/* 168:339 */     return this.sqlStatement;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setUser(String user)
/* 172:    */   {
/* 173:344 */     this.databaseUser = user;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setURL(String url)
/* 177:    */   {
/* 178:349 */     this.databaseURL = url;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setPassword(String password)
/* 182:    */   {
/* 183:354 */     this.databasePassword = password;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setBufferSize(int newBufferSize)
/* 187:    */   {
/* 188:359 */     this.bufferSize = newBufferSize;
/* 189:360 */     this.buffer.ensureCapacity(this.bufferSize);
/* 190:361 */     this.removes.ensureCapacity(this.bufferSize);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String getUser()
/* 194:    */   {
/* 195:366 */     return this.databaseUser;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String getURL()
/* 199:    */   {
/* 200:371 */     return this.databaseURL;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String getPassword()
/* 204:    */   {
/* 205:376 */     return this.databasePassword;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public int getBufferSize()
/* 209:    */   {
/* 210:381 */     return this.bufferSize;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setDriver(String driverClass)
/* 214:    */   {
/* 215:    */     try
/* 216:    */     {
/* 217:391 */       Class.forName(driverClass);
/* 218:    */     }
/* 219:    */     catch (Exception e)
/* 220:    */     {
/* 221:393 */       this.errorHandler.error("Failed to load driver", e, 0);
/* 222:    */     }
/* 223:    */   }
/* 224:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.jdbc.JDBCAppender
 * JD-Core Version:    0.7.0.1
 */