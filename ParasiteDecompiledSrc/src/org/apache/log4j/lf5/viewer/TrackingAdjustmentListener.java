/*  1:   */ package org.apache.log4j.lf5.viewer;
/*  2:   */ 
/*  3:   */ import java.awt.Adjustable;
/*  4:   */ import java.awt.event.AdjustmentEvent;
/*  5:   */ import java.awt.event.AdjustmentListener;
/*  6:   */ 
/*  7:   */ public class TrackingAdjustmentListener
/*  8:   */   implements AdjustmentListener
/*  9:   */ {
/* 10:46 */   protected int _lastMaximum = -1;
/* 11:   */   
/* 12:   */   public void adjustmentValueChanged(AdjustmentEvent e)
/* 13:   */   {
/* 14:61 */     Adjustable bar = e.getAdjustable();
/* 15:62 */     int currentMaximum = bar.getMaximum();
/* 16:63 */     if (bar.getMaximum() == this._lastMaximum) {
/* 17:64 */       return;
/* 18:   */     }
/* 19:66 */     int bottom = bar.getValue() + bar.getVisibleAmount();
/* 20:68 */     if (bottom + bar.getUnitIncrement() >= this._lastMaximum) {
/* 21:69 */       bar.setValue(bar.getMaximum());
/* 22:   */     }
/* 23:71 */     this._lastMaximum = currentMaximum;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.TrackingAdjustmentListener
 * JD-Core Version:    0.7.0.1
 */