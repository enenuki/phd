/*  1:   */ package org.hibernate.loader.collection;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ import org.hibernate.loader.JoinWalker;
/*  7:   */ 
/*  8:   */ public abstract class CollectionJoinWalker
/*  9:   */   extends JoinWalker
/* 10:   */ {
/* 11:   */   public CollectionJoinWalker(SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/* 12:   */   {
/* 13:42 */     super(factory, loadQueryInfluencers);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected StringBuffer whereString(String alias, String[] columnNames, String subselect, int batchSize)
/* 17:   */   {
/* 18:46 */     if (subselect == null) {
/* 19:47 */       return super.whereString(alias, columnNames, batchSize);
/* 20:   */     }
/* 21:50 */     StringBuffer buf = new StringBuffer();
/* 22:51 */     if (columnNames.length > 1) {
/* 23:51 */       buf.append('(');
/* 24:   */     }
/* 25:52 */     buf.append(StringHelper.join(", ", StringHelper.qualify(alias, columnNames)));
/* 26:53 */     if (columnNames.length > 1) {
/* 27:53 */       buf.append(')');
/* 28:   */     }
/* 29:54 */     buf.append(" in ").append('(').append(subselect).append(')');
/* 30:   */     
/* 31:   */ 
/* 32:   */ 
/* 33:58 */     return buf;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.collection.CollectionJoinWalker
 * JD-Core Version:    0.7.0.1
 */