/*  1:   */ package org.hibernate.metamodel.source;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class XsdException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   private final String xsdName;
/*  9:   */   
/* 10:   */   public XsdException(String message, String xsdName)
/* 11:   */   {
/* 12:38 */     super(message);
/* 13:39 */     this.xsdName = xsdName;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public XsdException(String message, Throwable root, String xsdName)
/* 17:   */   {
/* 18:43 */     super(message, root);
/* 19:44 */     this.xsdName = xsdName;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getXsdName()
/* 23:   */   {
/* 24:48 */     return this.xsdName;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.XsdException
 * JD-Core Version:    0.7.0.1
 */