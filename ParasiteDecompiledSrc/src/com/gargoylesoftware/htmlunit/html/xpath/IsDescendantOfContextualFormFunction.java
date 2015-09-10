/*  1:   */ package com.gargoylesoftware.htmlunit.html.xpath;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlForm;
/*  4:   */ import java.util.List;
/*  5:   */ import javax.xml.transform.TransformerException;
/*  6:   */ import org.apache.xml.dtm.DTM;
/*  7:   */ import org.apache.xml.utils.IntStack;
/*  8:   */ import org.apache.xpath.XPathContext;
/*  9:   */ import org.apache.xpath.functions.FunctionDef1Arg;
/* 10:   */ import org.apache.xpath.objects.XBoolean;
/* 11:   */ import org.apache.xpath.objects.XObject;
/* 12:   */ import org.w3c.dom.Node;
/* 13:   */ 
/* 14:   */ @Deprecated
/* 15:   */ public class IsDescendantOfContextualFormFunction
/* 16:   */   extends FunctionDef1Arg
/* 17:   */ {
/* 18:   */   public XObject execute(XPathContext ctx)
/* 19:   */     throws TransformerException
/* 20:   */   {
/* 21:47 */     boolean descendant = false;
/* 22:   */     
/* 23:   */ 
/* 24:50 */     IntStack nodeStack = ctx.getCurrentNodeStack();
/* 25:   */     int possibleAncestor;
/* 26:   */     int possibleAncestor;
/* 27:51 */     if (nodeStack.size() > 1) {
/* 28:52 */       possibleAncestor = nodeStack.elementAt(1);
/* 29:   */     } else {
/* 30:55 */       possibleAncestor = -1;
/* 31:   */     }
/* 32:58 */     if (-1 != possibleAncestor)
/* 33:   */     {
/* 34:59 */       int currentNode = ctx.getContextNode();
/* 35:60 */       DTM dtm = ctx.getDTM(currentNode);
/* 36:61 */       for (int ancestor = dtm.getParent(currentNode); ancestor != -1; ancestor = dtm.getParent(ancestor)) {
/* 37:62 */         if (ancestor == possibleAncestor)
/* 38:   */         {
/* 39:63 */           descendant = true;
/* 40:64 */           break;
/* 41:   */         }
/* 42:   */       }
/* 43:67 */       if (!descendant)
/* 44:   */       {
/* 45:68 */         Node n = dtm.getNode(possibleAncestor);
/* 46:69 */         if ((n instanceof HtmlForm))
/* 47:   */         {
/* 48:70 */           HtmlForm f = (HtmlForm)n;
/* 49:71 */           descendant = f.getLostChildren().contains(dtm.getNode(currentNode));
/* 50:   */         }
/* 51:   */       }
/* 52:   */     }
/* 53:76 */     return new XBoolean(descendant);
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.xpath.IsDescendantOfContextualFormFunction
 * JD-Core Version:    0.7.0.1
 */