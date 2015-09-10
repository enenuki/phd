/*  1:   */ package org.apache.xalan.xsltc;
/*  2:   */ 
/*  3:   */ import org.xml.sax.SAXException;
/*  4:   */ 
/*  5:   */ public final class TransletException
/*  6:   */   extends SAXException
/*  7:   */ {
/*  8:   */   static final long serialVersionUID = -878916829521217293L;
/*  9:   */   
/* 10:   */   public TransletException()
/* 11:   */   {
/* 12:35 */     super("Translet error");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public TransletException(Exception e)
/* 16:   */   {
/* 17:39 */     super(e.toString());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public TransletException(String message)
/* 21:   */   {
/* 22:43 */     super(message);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.TransletException
 * JD-Core Version:    0.7.0.1
 */