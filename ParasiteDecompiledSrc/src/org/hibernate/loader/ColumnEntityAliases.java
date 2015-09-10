/*  1:   */ package org.hibernate.loader;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.persister.entity.Loadable;
/*  5:   */ 
/*  6:   */ public class ColumnEntityAliases
/*  7:   */   extends DefaultEntityAliases
/*  8:   */ {
/*  9:   */   public ColumnEntityAliases(Map returnProperties, Loadable persister, String suffix)
/* 10:   */   {
/* 11:43 */     super(returnProperties, persister, suffix);
/* 12:   */   }
/* 13:   */   
/* 14:   */   protected String[] getIdentifierAliases(Loadable persister, String suffix)
/* 15:   */   {
/* 16:47 */     return persister.getIdentifierColumnNames();
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected String getDiscriminatorAlias(Loadable persister, String suffix)
/* 20:   */   {
/* 21:51 */     return persister.getDiscriminatorColumnName();
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected String[] getPropertyAliases(Loadable persister, int j)
/* 25:   */   {
/* 26:55 */     return persister.getPropertyColumnNames(j);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.ColumnEntityAliases
 * JD-Core Version:    0.7.0.1
 */