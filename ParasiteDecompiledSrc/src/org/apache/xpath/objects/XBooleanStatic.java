/*  1:   */ package org.apache.xpath.objects;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.utils.WrappedRuntimeException;
/*  5:   */ 
/*  6:   */ public class XBooleanStatic
/*  7:   */   extends XBoolean
/*  8:   */ {
/*  9:   */   static final long serialVersionUID = -8064147275772687409L;
/* 10:   */   private final boolean m_val;
/* 11:   */   
/* 12:   */   public XBooleanStatic(boolean b)
/* 13:   */   {
/* 14:44 */     super(b);
/* 15:   */     
/* 16:46 */     this.m_val = b;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean equals(XObject obj2)
/* 20:   */   {
/* 21:   */     try
/* 22:   */     {
/* 23:62 */       return this.m_val == obj2.bool();
/* 24:   */     }
/* 25:   */     catch (TransformerException te)
/* 26:   */     {
/* 27:66 */       throw new WrappedRuntimeException(te);
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XBooleanStatic
 * JD-Core Version:    0.7.0.1
 */