/*  1:   */ package org.hibernate.engine;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;
/*  7:   */ 
/*  8:   */ public class ResultSetMappingDefinition
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private final String name;
/* 12:40 */   private final List<NativeSQLQueryReturn> queryReturns = new ArrayList();
/* 13:   */   
/* 14:   */   public ResultSetMappingDefinition(String name)
/* 15:   */   {
/* 16:43 */     this.name = name;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:47 */     return this.name;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void addQueryReturn(NativeSQLQueryReturn queryReturn)
/* 25:   */   {
/* 26:51 */     this.queryReturns.add(queryReturn);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public NativeSQLQueryReturn[] getQueryReturns()
/* 30:   */   {
/* 31:65 */     return (NativeSQLQueryReturn[])this.queryReturns.toArray(new NativeSQLQueryReturn[this.queryReturns.size()]);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.ResultSetMappingDefinition
 * JD-Core Version:    0.7.0.1
 */