/*  1:   */ package org.apache.xml.utils;
/*  2:   */ 
/*  3:   */ import org.xml.sax.SAXException;
/*  4:   */ 
/*  5:   */ public class StopParseException
/*  6:   */   extends SAXException
/*  7:   */ {
/*  8:   */   static final long serialVersionUID = 210102479218258961L;
/*  9:   */   
/* 10:   */   StopParseException()
/* 11:   */   {
/* 12:38 */     super("Stylesheet PIs found, stop the parse");
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.StopParseException
 * JD-Core Version:    0.7.0.1
 */