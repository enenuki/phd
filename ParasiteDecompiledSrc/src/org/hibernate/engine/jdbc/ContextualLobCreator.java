/*   1:    */ package org.hibernate.engine.jdbc;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.io.Reader;
/*   7:    */ import java.io.Writer;
/*   8:    */ import java.sql.Blob;
/*   9:    */ import java.sql.Clob;
/*  10:    */ import java.sql.Connection;
/*  11:    */ import java.sql.NClob;
/*  12:    */ import java.sql.SQLException;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.JDBCException;
/*  15:    */ 
/*  16:    */ public class ContextualLobCreator
/*  17:    */   extends AbstractLobCreator
/*  18:    */   implements LobCreator
/*  19:    */ {
/*  20:    */   private LobCreationContext lobCreationContext;
/*  21:    */   
/*  22:    */   public ContextualLobCreator(LobCreationContext lobCreationContext)
/*  23:    */   {
/*  24: 50 */     this.lobCreationContext = lobCreationContext;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Blob createBlob()
/*  28:    */   {
/*  29: 59 */     return (Blob)this.lobCreationContext.execute(CREATE_BLOB_CALLBACK);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Blob createBlob(byte[] bytes)
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 67 */       Blob blob = createBlob();
/*  37: 68 */       blob.setBytes(1L, bytes);
/*  38: 69 */       return blob;
/*  39:    */     }
/*  40:    */     catch (SQLException e)
/*  41:    */     {
/*  42: 72 */       throw new JDBCException("Unable to set BLOB bytes after creation", e);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Blob createBlob(InputStream inputStream, long length)
/*  47:    */   {
/*  48:    */     try
/*  49:    */     {
/*  50: 81 */       Blob blob = createBlob();
/*  51: 82 */       OutputStream byteStream = blob.setBinaryStream(1L);
/*  52: 83 */       StreamUtils.copy(inputStream, byteStream);
/*  53: 84 */       byteStream.flush();
/*  54: 85 */       byteStream.close();
/*  55:    */       
/*  56: 87 */       return blob;
/*  57:    */     }
/*  58:    */     catch (SQLException e)
/*  59:    */     {
/*  60: 90 */       throw new JDBCException("Unable to prepare BLOB binary stream for writing", e);
/*  61:    */     }
/*  62:    */     catch (IOException e)
/*  63:    */     {
/*  64: 93 */       throw new HibernateException("Unable to write stream contents to BLOB", e);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Clob createClob()
/*  69:    */   {
/*  70:103 */     return (Clob)this.lobCreationContext.execute(CREATE_CLOB_CALLBACK);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Clob createClob(String string)
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:111 */       Clob clob = createClob();
/*  78:112 */       clob.setString(1L, string);
/*  79:113 */       return clob;
/*  80:    */     }
/*  81:    */     catch (SQLException e)
/*  82:    */     {
/*  83:116 */       throw new JDBCException("Unable to set CLOB string after creation", e);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Clob createClob(Reader reader, long length)
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:125 */       Clob clob = createClob();
/*  92:126 */       Writer writer = clob.setCharacterStream(1L);
/*  93:127 */       StreamUtils.copy(reader, writer);
/*  94:128 */       writer.flush();
/*  95:129 */       writer.close();
/*  96:130 */       return clob;
/*  97:    */     }
/*  98:    */     catch (SQLException e)
/*  99:    */     {
/* 100:133 */       throw new JDBCException("Unable to prepare CLOB stream for writing", e);
/* 101:    */     }
/* 102:    */     catch (IOException e)
/* 103:    */     {
/* 104:136 */       throw new HibernateException("Unable to write CLOB stream content", e);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public NClob createNClob()
/* 109:    */   {
/* 110:146 */     return (NClob)this.lobCreationContext.execute(CREATE_NCLOB_CALLBACK);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public NClob createNClob(String string)
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:154 */       NClob nclob = createNClob();
/* 118:155 */       nclob.setString(1L, string);
/* 119:156 */       return nclob;
/* 120:    */     }
/* 121:    */     catch (SQLException e)
/* 122:    */     {
/* 123:159 */       throw new JDBCException("Unable to set NCLOB string after creation", e);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public NClob createNClob(Reader reader, long length)
/* 128:    */   {
/* 129:    */     try
/* 130:    */     {
/* 131:168 */       NClob nclob = createNClob();
/* 132:169 */       Writer writer = nclob.setCharacterStream(1L);
/* 133:170 */       StreamUtils.copy(reader, writer);
/* 134:171 */       writer.flush();
/* 135:172 */       writer.close();
/* 136:173 */       return nclob;
/* 137:    */     }
/* 138:    */     catch (SQLException e)
/* 139:    */     {
/* 140:176 */       throw new JDBCException("Unable to prepare NCLOB stream for writing", e);
/* 141:    */     }
/* 142:    */     catch (IOException e)
/* 143:    */     {
/* 144:179 */       throw new HibernateException("Unable to write NCLOB stream content", e);
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:183 */   public static final LobCreationContext.Callback<Blob> CREATE_BLOB_CALLBACK = new LobCreationContext.Callback()
/* 149:    */   {
/* 150:    */     public Blob executeOnConnection(Connection connection)
/* 151:    */       throws SQLException
/* 152:    */     {
/* 153:185 */       return connection.createBlob();
/* 154:    */     }
/* 155:    */   };
/* 156:189 */   public static final LobCreationContext.Callback<Clob> CREATE_CLOB_CALLBACK = new LobCreationContext.Callback()
/* 157:    */   {
/* 158:    */     public Clob executeOnConnection(Connection connection)
/* 159:    */       throws SQLException
/* 160:    */     {
/* 161:191 */       return connection.createClob();
/* 162:    */     }
/* 163:    */   };
/* 164:195 */   public static final LobCreationContext.Callback<NClob> CREATE_NCLOB_CALLBACK = new LobCreationContext.Callback()
/* 165:    */   {
/* 166:    */     public NClob executeOnConnection(Connection connection)
/* 167:    */       throws SQLException
/* 168:    */     {
/* 169:197 */       return connection.createNClob();
/* 170:    */     }
/* 171:    */   };
/* 172:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.ContextualLobCreator
 * JD-Core Version:    0.7.0.1
 */