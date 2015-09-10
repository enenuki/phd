/*  1:   */ package org.hibernate.engine.jdbc;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ import java.io.Reader;
/*  5:   */ import java.sql.Blob;
/*  6:   */ import java.sql.Clob;
/*  7:   */ import java.sql.NClob;
/*  8:   */ 
/*  9:   */ public class NonContextualLobCreator
/* 10:   */   extends AbstractLobCreator
/* 11:   */   implements LobCreator
/* 12:   */ {
/* 13:39 */   public static final NonContextualLobCreator INSTANCE = new NonContextualLobCreator();
/* 14:   */   
/* 15:   */   public Blob createBlob(byte[] bytes)
/* 16:   */   {
/* 17:48 */     return BlobProxy.generateProxy(bytes);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Blob createBlob(InputStream stream, long length)
/* 21:   */   {
/* 22:55 */     return BlobProxy.generateProxy(stream, length);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Clob createClob(String string)
/* 26:   */   {
/* 27:62 */     return ClobProxy.generateProxy(string);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Clob createClob(Reader reader, long length)
/* 31:   */   {
/* 32:69 */     return ClobProxy.generateProxy(reader, length);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public NClob createNClob(String string)
/* 36:   */   {
/* 37:76 */     return NClobProxy.generateProxy(string);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public NClob createNClob(Reader reader, long length)
/* 41:   */   {
/* 42:83 */     return NClobProxy.generateProxy(reader, length);
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.NonContextualLobCreator
 * JD-Core Version:    0.7.0.1
 */