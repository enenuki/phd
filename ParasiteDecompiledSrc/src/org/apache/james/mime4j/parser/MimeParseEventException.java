/*  1:   */ package org.apache.james.mime4j.parser;
/*  2:   */ 
/*  3:   */ import org.apache.james.mime4j.MimeException;
/*  4:   */ 
/*  5:   */ public class MimeParseEventException
/*  6:   */   extends MimeException
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 4632991604246852302L;
/*  9:   */   private final Event event;
/* 10:   */   
/* 11:   */   public MimeParseEventException(Event event)
/* 12:   */   {
/* 13:39 */     super(event.toString());
/* 14:40 */     this.event = event;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Event getEvent()
/* 18:   */   {
/* 19:48 */     return this.event;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.parser.MimeParseEventException
 * JD-Core Version:    0.7.0.1
 */