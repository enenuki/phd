/*  1:   */ package org.apache.log4j.chainsaw;
/*  2:   */ 
/*  3:   */ import java.awt.event.ActionEvent;
/*  4:   */ import javax.swing.AbstractAction;
/*  5:   */ import org.apache.log4j.Category;
/*  6:   */ import org.apache.log4j.Logger;
/*  7:   */ 
/*  8:   */ class ExitAction
/*  9:   */   extends AbstractAction
/* 10:   */ {
/* 11:33 */   private static final Logger LOG = Logger.getLogger(ExitAction.class);
/* 12:35 */   public static final ExitAction INSTANCE = new ExitAction();
/* 13:   */   
/* 14:   */   public void actionPerformed(ActionEvent aIgnore)
/* 15:   */   {
/* 16:45 */     LOG.info("shutting down");
/* 17:46 */     System.exit(0);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.chainsaw.ExitAction
 * JD-Core Version:    0.7.0.1
 */