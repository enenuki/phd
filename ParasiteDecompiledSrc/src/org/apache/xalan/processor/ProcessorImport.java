/*  1:   */ package org.apache.xalan.processor;
/*  2:   */ 
/*  3:   */ public class ProcessorImport
/*  4:   */   extends ProcessorInclude
/*  5:   */ {
/*  6:   */   static final long serialVersionUID = -8247537698214245237L;
/*  7:   */   
/*  8:   */   protected int getStylesheetType()
/*  9:   */   {
/* 10:43 */     return 3;
/* 11:   */   }
/* 12:   */   
/* 13:   */   protected String getStylesheetInclErr()
/* 14:   */   {
/* 15:53 */     return "ER_IMPORTING_ITSELF";
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorImport
 * JD-Core Version:    0.7.0.1
 */