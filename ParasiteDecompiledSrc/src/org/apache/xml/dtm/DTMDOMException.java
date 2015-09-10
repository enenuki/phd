/*  1:   */ package org.apache.xml.dtm;
/*  2:   */ 
/*  3:   */ import org.w3c.dom.DOMException;
/*  4:   */ 
/*  5:   */ public class DTMDOMException
/*  6:   */   extends DOMException
/*  7:   */ {
/*  8:   */   static final long serialVersionUID = 1895654266613192414L;
/*  9:   */   
/* 10:   */   public DTMDOMException(short code, String message)
/* 11:   */   {
/* 12:41 */     super(code, message);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public DTMDOMException(short code)
/* 16:   */   {
/* 17:52 */     super(code, "");
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.DTMDOMException
 * JD-Core Version:    0.7.0.1
 */