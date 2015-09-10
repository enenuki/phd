/*  1:   */ package org.dom4j;
/*  2:   */ 
/*  3:   */ public class IllegalAddException
/*  4:   */   extends IllegalArgumentException
/*  5:   */ {
/*  6:   */   public IllegalAddException(String reason)
/*  7:   */   {
/*  8:20 */     super(reason);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public IllegalAddException(Element parent, Node node, String reason)
/* 12:   */   {
/* 13:24 */     super("The node \"" + node.toString() + "\" could not be added to the element \"" + parent.getQualifiedName() + "\" because: " + reason);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public IllegalAddException(Branch parent, Node node, String reason)
/* 17:   */   {
/* 18:30 */     super("The node \"" + node.toString() + "\" could not be added to the branch \"" + parent.getName() + "\" because: " + reason);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.IllegalAddException
 * JD-Core Version:    0.7.0.1
 */