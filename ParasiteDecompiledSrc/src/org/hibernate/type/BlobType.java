/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.sql.Blob;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.dialect.LobMergeStrategy;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ 
/*  9:   */ public class BlobType
/* 10:   */   extends AbstractSingleColumnStandardBasicType<Blob>
/* 11:   */ {
/* 12:37 */   public static final BlobType INSTANCE = new BlobType();
/* 13:   */   
/* 14:   */   public BlobType()
/* 15:   */   {
/* 16:40 */     super(org.hibernate.type.descriptor.sql.BlobTypeDescriptor.DEFAULT, org.hibernate.type.descriptor.java.BlobTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:47 */     return "blob";
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected boolean registerUnderJavaType()
/* 25:   */   {
/* 26:52 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected Blob getReplacement(Blob original, Blob target, SessionImplementor session)
/* 30:   */   {
/* 31:57 */     return session.getFactory().getDialect().getLobMergeStrategy().mergeBlob(original, target, session);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.BlobType
 * JD-Core Version:    0.7.0.1
 */