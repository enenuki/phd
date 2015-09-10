/*  1:   */ package org.cyberneko.html.parsers;
/*  2:   */ 
/*  3:   */ import org.apache.xerces.parsers.AbstractSAXParser;
/*  4:   */ import org.cyberneko.html.HTMLConfiguration;
/*  5:   */ 
/*  6:   */ public class SAXParser
/*  7:   */   extends AbstractSAXParser
/*  8:   */ {
/*  9:   */   public SAXParser()
/* 10:   */   {
/* 11:38 */     super(new HTMLConfiguration());
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.parsers.SAXParser
 * JD-Core Version:    0.7.0.1
 */