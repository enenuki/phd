/*  1:   */ package org.hibernate.engine.spi;
/*  2:   */ 
/*  3:   */ public final class RowSelection
/*  4:   */ {
/*  5:   */   private Integer firstRow;
/*  6:   */   private Integer maxRows;
/*  7:   */   private Integer timeout;
/*  8:   */   private Integer fetchSize;
/*  9:   */   
/* 10:   */   public void setFirstRow(Integer firstRow)
/* 11:   */   {
/* 12:38 */     this.firstRow = firstRow;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Integer getFirstRow()
/* 16:   */   {
/* 17:42 */     return this.firstRow;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setMaxRows(Integer maxRows)
/* 21:   */   {
/* 22:46 */     this.maxRows = maxRows;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Integer getMaxRows()
/* 26:   */   {
/* 27:50 */     return this.maxRows;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setTimeout(Integer timeout)
/* 31:   */   {
/* 32:54 */     this.timeout = timeout;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Integer getTimeout()
/* 36:   */   {
/* 37:58 */     return this.timeout;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Integer getFetchSize()
/* 41:   */   {
/* 42:62 */     return this.fetchSize;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setFetchSize(Integer fetchSize)
/* 46:   */   {
/* 47:66 */     this.fetchSize = fetchSize;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean definesLimits()
/* 51:   */   {
/* 52:70 */     return (this.maxRows != null) || ((this.firstRow != null) && (this.firstRow.intValue() <= 0));
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.RowSelection
 * JD-Core Version:    0.7.0.1
 */