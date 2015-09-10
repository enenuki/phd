/*   1:    */ package org.apache.xalan.lib.sql;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Properties;
/*   7:    */ import javax.naming.InitialContext;
/*   8:    */ import javax.naming.NamingException;
/*   9:    */ 
/*  10:    */ public class JNDIConnectionPool
/*  11:    */   implements ConnectionPool
/*  12:    */ {
/*  13: 48 */   protected Object jdbcSource = null;
/*  14: 56 */   private Method getConnectionWithArgs = null;
/*  15: 57 */   private Method getConnection = null;
/*  16: 63 */   protected String jndiPath = null;
/*  17: 68 */   protected String user = null;
/*  18: 73 */   protected String pwd = null;
/*  19:    */   
/*  20:    */   public JNDIConnectionPool() {}
/*  21:    */   
/*  22:    */   public JNDIConnectionPool(String jndiDatasourcePath)
/*  23:    */   {
/*  24: 87 */     this.jndiPath = jndiDatasourcePath.trim();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setJndiPath(String jndiPath)
/*  28:    */   {
/*  29: 96 */     this.jndiPath = jndiPath;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getJndiPath()
/*  33:    */   {
/*  34:105 */     return this.jndiPath;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean isEnabled()
/*  38:    */   {
/*  39:116 */     return true;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setDriver(String d)
/*  43:    */   {
/*  44:127 */     throw new Error("This method is not supported. All connection information is handled by the JDBC datasource provider");
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setURL(String url)
/*  48:    */   {
/*  49:140 */     throw new Error("This method is not supported. All connection information is handled by the JDBC datasource provider");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void freeUnused() {}
/*  53:    */   
/*  54:    */   public boolean hasActiveConnections()
/*  55:    */   {
/*  56:161 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setPassword(String p)
/*  60:    */   {
/*  61:173 */     if (p != null) {
/*  62:173 */       p = p.trim();
/*  63:    */     }
/*  64:174 */     if ((p != null) && (p.length() == 0)) {
/*  65:174 */       p = null;
/*  66:    */     }
/*  67:176 */     this.pwd = p;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setUser(String u)
/*  71:    */   {
/*  72:188 */     if (u != null) {
/*  73:188 */       u = u.trim();
/*  74:    */     }
/*  75:189 */     if ((u != null) && (u.length() == 0)) {
/*  76:189 */       u = null;
/*  77:    */     }
/*  78:191 */     this.user = u;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Connection getConnection()
/*  82:    */     throws SQLException
/*  83:    */   {
/*  84:203 */     if (this.jdbcSource == null) {
/*  85:    */       try
/*  86:    */       {
/*  87:207 */         findDatasource();
/*  88:    */       }
/*  89:    */       catch (NamingException ne)
/*  90:    */       {
/*  91:211 */         throw new SQLException("Could not create jndi context for " + this.jndiPath + " - " + ne.getLocalizedMessage());
/*  92:    */       }
/*  93:    */     }
/*  94:    */     try
/*  95:    */     {
/*  96:219 */       if ((this.user != null) || (this.pwd != null))
/*  97:    */       {
/*  98:221 */         Object[] arglist = { this.user, this.pwd };
/*  99:222 */         return (Connection)this.getConnectionWithArgs.invoke(this.jdbcSource, arglist);
/* 100:    */       }
/* 101:226 */       Object[] arglist = new Object[0];
/* 102:227 */       return (Connection)this.getConnection.invoke(this.jdbcSource, arglist);
/* 103:    */     }
/* 104:    */     catch (Exception e)
/* 105:    */     {
/* 106:232 */       throw new SQLException("Could not create jndi connection for " + this.jndiPath + " - " + e.getLocalizedMessage());
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void findDatasource()
/* 111:    */     throws NamingException
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:247 */       InitialContext context = new InitialContext();
/* 116:248 */       this.jdbcSource = context.lookup(this.jndiPath);
/* 117:    */       
/* 118:250 */       Class[] withArgs = { String.class, String.class };
/* 119:251 */       this.getConnectionWithArgs = this.jdbcSource.getClass().getDeclaredMethod("getConnection", withArgs);
/* 120:    */       
/* 121:    */ 
/* 122:254 */       Class[] noArgs = new Class[0];
/* 123:255 */       this.getConnection = this.jdbcSource.getClass().getDeclaredMethod("getConnection", noArgs);
/* 124:    */     }
/* 125:    */     catch (NamingException e)
/* 126:    */     {
/* 127:261 */       throw e;
/* 128:    */     }
/* 129:    */     catch (NoSuchMethodException e)
/* 130:    */     {
/* 131:267 */       throw new NamingException("Unable to resolve JNDI DataSource - " + e);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void releaseConnection(Connection con)
/* 136:    */     throws SQLException
/* 137:    */   {
/* 138:273 */     con.close();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void releaseConnectionOnError(Connection con)
/* 142:    */     throws SQLException
/* 143:    */   {
/* 144:278 */     con.close();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setPoolEnabled(boolean flag)
/* 148:    */   {
/* 149:291 */     if (!flag) {
/* 150:291 */       this.jdbcSource = null;
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setProtocol(Properties p) {}
/* 155:    */   
/* 156:    */   public void setMinConnections(int n) {}
/* 157:    */   
/* 158:    */   public boolean testConnection()
/* 159:    */   {
/* 160:320 */     if (this.jdbcSource == null) {
/* 161:    */       try
/* 162:    */       {
/* 163:324 */         findDatasource();
/* 164:    */       }
/* 165:    */       catch (NamingException ne)
/* 166:    */       {
/* 167:328 */         return false;
/* 168:    */       }
/* 169:    */     }
/* 170:332 */     return true;
/* 171:    */   }
/* 172:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.JNDIConnectionPool
 * JD-Core Version:    0.7.0.1
 */