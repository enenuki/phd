/*  1:   */ package org.apache.log4j;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ 
/*  5:   */ class ProvisionNode
/*  6:   */   extends Vector
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = -4479121426311014469L;
/*  9:   */   
/* 10:   */   ProvisionNode(Logger logger)
/* 11:   */   {
/* 12:27 */     addElement(logger);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.ProvisionNode
 * JD-Core Version:    0.7.0.1
 */