/*  1:   */ package org.hibernate.engine.jdbc;
/*  2:   */ 
/*  3:   */ import java.sql.Blob;
/*  4:   */ import java.sql.Clob;
/*  5:   */ 
/*  6:   */ public abstract class AbstractLobCreator
/*  7:   */   implements LobCreator
/*  8:   */ {
/*  9:   */   public Blob wrap(Blob blob)
/* 10:   */   {
/* 11:38 */     return SerializableBlobProxy.generateProxy(blob);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Clob wrap(Clob clob)
/* 15:   */   {
/* 16:45 */     if (SerializableNClobProxy.isNClob(clob)) {
/* 17:46 */       return SerializableNClobProxy.generateProxy(clob);
/* 18:   */     }
/* 19:49 */     return SerializableClobProxy.generateProxy(clob);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.AbstractLobCreator
 * JD-Core Version:    0.7.0.1
 */