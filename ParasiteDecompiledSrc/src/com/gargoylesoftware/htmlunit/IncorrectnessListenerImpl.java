/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.logging.Log;
/*  5:   */ import org.apache.commons.logging.LogFactory;
/*  6:   */ 
/*  7:   */ public class IncorrectnessListenerImpl
/*  8:   */   implements IncorrectnessListener, Serializable
/*  9:   */ {
/* 10:32 */   private static final Log LOG = LogFactory.getLog(IncorrectnessListenerImpl.class);
/* 11:   */   
/* 12:   */   public void notify(String message, Object origin)
/* 13:   */   {
/* 14:38 */     LOG.warn(message);
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.IncorrectnessListenerImpl
 * JD-Core Version:    0.7.0.1
 */