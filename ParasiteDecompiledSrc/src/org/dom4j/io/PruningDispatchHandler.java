/*  1:   */ package org.dom4j.io;
/*  2:   */ 
/*  3:   */ import org.dom4j.Element;
/*  4:   */ import org.dom4j.ElementPath;
/*  5:   */ 
/*  6:   */ class PruningDispatchHandler
/*  7:   */   extends DispatchHandler
/*  8:   */ {
/*  9:   */   public void onEnd(ElementPath elementPath)
/* 10:   */   {
/* 11:21 */     super.onEnd(elementPath);
/* 12:23 */     if (getActiveHandlerCount() == 0) {
/* 13:24 */       elementPath.getCurrent().detach();
/* 14:   */     }
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.PruningDispatchHandler
 * JD-Core Version:    0.7.0.1
 */