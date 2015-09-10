/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.sql.Clob;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.dialect.LobMergeStrategy;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ 
/*  9:   */ public class ClobType
/* 10:   */   extends AbstractSingleColumnStandardBasicType<Clob>
/* 11:   */ {
/* 12:37 */   public static final ClobType INSTANCE = new ClobType();
/* 13:   */   
/* 14:   */   public ClobType()
/* 15:   */   {
/* 16:40 */     super(org.hibernate.type.descriptor.sql.ClobTypeDescriptor.DEFAULT, org.hibernate.type.descriptor.java.ClobTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:44 */     return "clob";
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected boolean registerUnderJavaType()
/* 25:   */   {
/* 26:49 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected Clob getReplacement(Clob original, Clob target, SessionImplementor session)
/* 30:   */   {
/* 31:54 */     return session.getFactory().getDialect().getLobMergeStrategy().mergeClob(original, target, session);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ClobType
 * JD-Core Version:    0.7.0.1
 */