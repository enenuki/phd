/*  1:   */ package org.apache.xml.dtm;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.SourceLocator;
/*  4:   */ 
/*  5:   */ public class DTMConfigurationException
/*  6:   */   extends DTMException
/*  7:   */ {
/*  8:   */   static final long serialVersionUID = -4607874078818418046L;
/*  9:   */   
/* 10:   */   public DTMConfigurationException()
/* 11:   */   {
/* 12:36 */     super("Configuration Error");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public DTMConfigurationException(String msg)
/* 16:   */   {
/* 17:46 */     super(msg);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public DTMConfigurationException(Throwable e)
/* 21:   */   {
/* 22:57 */     super(e);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public DTMConfigurationException(String msg, Throwable e)
/* 26:   */   {
/* 27:68 */     super(msg, e);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public DTMConfigurationException(String message, SourceLocator locator)
/* 31:   */   {
/* 32:83 */     super(message, locator);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public DTMConfigurationException(String message, SourceLocator locator, Throwable e)
/* 36:   */   {
/* 37:97 */     super(message, locator, e);
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.DTMConfigurationException
 * JD-Core Version:    0.7.0.1
 */