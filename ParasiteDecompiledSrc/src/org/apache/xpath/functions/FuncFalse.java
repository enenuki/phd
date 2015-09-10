/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import javax.xml.transform.TransformerException;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XBoolean;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class FuncFalse
/* 10:   */   extends Function
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = 6150918062759769887L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:45 */     return XBoolean.S_FALSE;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void fixupVariables(Vector vars, int globalsSize) {}
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncFalse
 * JD-Core Version:    0.7.0.1
 */