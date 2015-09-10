/*   1:    */ package org.apache.xalan.lib.sql;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.Driver;
/*   5:    */ import java.sql.DriverManager;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.AbstractList;
/*   8:    */ import java.util.Enumeration;
/*   9:    */ import java.util.Hashtable;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.Properties;
/*  12:    */ import java.util.Vector;
/*  13:    */ import org.apache.xalan.res.XSLMessages;
/*  14:    */ 
/*  15:    */ public class DefaultConnectionPool
/*  16:    */   implements ConnectionPool
/*  17:    */ {
/*  18: 46 */   private Driver m_Driver = null;
/*  19:    */   private static final boolean DEBUG = false;
/*  20: 54 */   private String m_driver = new String("");
/*  21: 57 */   private String m_url = new String("");
/*  22: 66 */   private int m_PoolMinSize = 1;
/*  23: 74 */   private Properties m_ConnectionProtocol = new Properties();
/*  24: 79 */   private Vector m_pool = new Vector();
/*  25: 84 */   private boolean m_IsActive = false;
/*  26:    */   
/*  27:    */   public boolean isEnabled()
/*  28:    */   {
/*  29: 97 */     return this.m_IsActive;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setDriver(String d)
/*  33:    */   {
/*  34:107 */     this.m_driver = d;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setURL(String url)
/*  38:    */   {
/*  39:117 */     this.m_url = url;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void freeUnused()
/*  43:    */   {
/*  44:129 */     Iterator i = this.m_pool.iterator();
/*  45:130 */     while (i.hasNext())
/*  46:    */     {
/*  47:132 */       PooledConnection pcon = (PooledConnection)i.next();
/*  48:136 */       if (!pcon.inUse())
/*  49:    */       {
/*  50:143 */         pcon.close();
/*  51:144 */         i.remove();
/*  52:    */       }
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean hasActiveConnections()
/*  57:    */   {
/*  58:156 */     return this.m_pool.size() > 0;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setPassword(String p)
/*  62:    */   {
/*  63:167 */     this.m_ConnectionProtocol.put("password", p);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setUser(String u)
/*  67:    */   {
/*  68:177 */     this.m_ConnectionProtocol.put("user", u);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setProtocol(Properties p)
/*  72:    */   {
/*  73:189 */     Enumeration e = p.keys();
/*  74:190 */     while (e.hasMoreElements())
/*  75:    */     {
/*  76:192 */       String key = (String)e.nextElement();
/*  77:193 */       this.m_ConnectionProtocol.put(key, p.getProperty(key));
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setMinConnections(int n)
/*  82:    */   {
/*  83:207 */     this.m_PoolMinSize = n;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean testConnection()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:225 */       Connection conn = getConnection();
/*  91:237 */       if (conn == null) {
/*  92:237 */         return false;
/*  93:    */       }
/*  94:239 */       releaseConnection(conn);
/*  95:    */       
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:246 */       return true;
/* 102:    */     }
/* 103:    */     catch (Exception e) {}
/* 104:256 */     return false;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public synchronized Connection getConnection()
/* 108:    */     throws IllegalArgumentException, SQLException
/* 109:    */   {
/* 110:271 */     PooledConnection pcon = null;
/* 111:277 */     if (this.m_pool.size() < this.m_PoolMinSize) {
/* 112:277 */       initializePool();
/* 113:    */     }
/* 114:280 */     for (int x = 0; x < this.m_pool.size(); x++)
/* 115:    */     {
/* 116:283 */       pcon = (PooledConnection)this.m_pool.elementAt(x);
/* 117:286 */       if (!pcon.inUse())
/* 118:    */       {
/* 119:289 */         pcon.setInUse(true);
/* 120:    */         
/* 121:    */ 
/* 122:292 */         return pcon.getConnection();
/* 123:    */       }
/* 124:    */     }
/* 125:300 */     Connection con = createConnection();
/* 126:    */     
/* 127:    */ 
/* 128:    */ 
/* 129:304 */     pcon = new PooledConnection(con);
/* 130:    */     
/* 131:    */ 
/* 132:307 */     pcon.setInUse(true);
/* 133:    */     
/* 134:    */ 
/* 135:310 */     this.m_pool.addElement(pcon);
/* 136:    */     
/* 137:    */ 
/* 138:313 */     return pcon.getConnection();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public synchronized void releaseConnection(Connection con)
/* 142:    */     throws SQLException
/* 143:    */   {
/* 144:325 */     for (int x = 0; x < this.m_pool.size(); x++)
/* 145:    */     {
/* 146:328 */       PooledConnection pcon = (PooledConnection)this.m_pool.elementAt(x);
/* 147:332 */       if (pcon.getConnection() == con)
/* 148:    */       {
/* 149:339 */         if (!isEnabled())
/* 150:    */         {
/* 151:341 */           con.close();
/* 152:342 */           this.m_pool.removeElementAt(x);
/* 153:343 */           break;
/* 154:    */         }
/* 155:353 */         pcon.setInUse(false);
/* 156:    */         
/* 157:    */ 
/* 158:356 */         break;
/* 159:    */       }
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public synchronized void releaseConnectionOnError(Connection con)
/* 164:    */     throws SQLException
/* 165:    */   {
/* 166:371 */     for (int x = 0; x < this.m_pool.size(); x++)
/* 167:    */     {
/* 168:374 */       PooledConnection pcon = (PooledConnection)this.m_pool.elementAt(x);
/* 169:378 */       if (pcon.getConnection() == con)
/* 170:    */       {
/* 171:385 */         con.close();
/* 172:386 */         this.m_pool.removeElementAt(x);
/* 173:387 */         break;
/* 174:    */       }
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   private Connection createConnection()
/* 179:    */     throws SQLException
/* 180:    */   {
/* 181:403 */     Connection con = null;
/* 182:    */     
/* 183:    */ 
/* 184:    */ 
/* 185:407 */     con = this.m_Driver.connect(this.m_url, this.m_ConnectionProtocol);
/* 186:    */     
/* 187:409 */     return con;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public synchronized void initializePool()
/* 191:    */     throws IllegalArgumentException, SQLException
/* 192:    */   {
/* 193:422 */     if (this.m_driver == null) {
/* 194:424 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_NO_DRIVER_NAME_SPECIFIED", null));
/* 195:    */     }
/* 196:428 */     if (this.m_url == null) {
/* 197:430 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_NO_URL_SPECIFIED", null));
/* 198:    */     }
/* 199:434 */     if (this.m_PoolMinSize < 1) {
/* 200:436 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_POOLSIZE_LESS_THAN_ONE", null));
/* 201:    */     }
/* 202:    */     try
/* 203:    */     {
/* 204:447 */       this.m_Driver = ((Driver)ObjectFactory.newInstance(this.m_driver, ObjectFactory.findClassLoader(), true));
/* 205:    */       
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:453 */       DriverManager.registerDriver(this.m_Driver);
/* 211:    */     }
/* 212:    */     catch (ObjectFactory.ConfigurationError e)
/* 213:    */     {
/* 214:457 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_INVALID_DRIVER_NAME", null));
/* 215:    */     }
/* 216:    */     catch (Exception e)
/* 217:    */     {
/* 218:462 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_INVALID_DRIVER_NAME", null));
/* 219:    */     }
/* 220:467 */     if (!this.m_IsActive) {
/* 221:    */       return;
/* 222:    */     }
/* 223:    */     do
/* 224:    */     {
/* 225:473 */       Connection con = createConnection();
/* 226:475 */       if (con != null)
/* 227:    */       {
/* 228:480 */         PooledConnection pcon = new PooledConnection(con);
/* 229:    */         
/* 230:    */ 
/* 231:483 */         addConnection(pcon);
/* 232:    */       }
/* 233:488 */     } while (this.m_pool.size() < this.m_PoolMinSize);
/* 234:    */   }
/* 235:    */   
/* 236:    */   private void addConnection(PooledConnection value)
/* 237:    */   {
/* 238:499 */     this.m_pool.addElement(value);
/* 239:    */   }
/* 240:    */   
/* 241:    */   protected void finalize()
/* 242:    */     throws Throwable
/* 243:    */   {
/* 244:516 */     for (int x = 0; x < this.m_pool.size(); x++)
/* 245:    */     {
/* 246:524 */       PooledConnection pcon = (PooledConnection)this.m_pool.elementAt(x);
/* 247:528 */       if (!pcon.inUse()) {
/* 248:528 */         pcon.close();
/* 249:    */       } else {
/* 250:    */         try
/* 251:    */         {
/* 252:540 */           Thread.sleep(30000L);
/* 253:541 */           pcon.close();
/* 254:    */         }
/* 255:    */         catch (InterruptedException ie) {}
/* 256:    */       }
/* 257:    */     }
/* 258:555 */     super.finalize();
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setPoolEnabled(boolean flag)
/* 262:    */   {
/* 263:573 */     this.m_IsActive = flag;
/* 264:574 */     if (!flag) {
/* 265:575 */       freeUnused();
/* 266:    */     }
/* 267:    */   }
/* 268:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.DefaultConnectionPool
 * JD-Core Version:    0.7.0.1
 */