/*  1:   */ package jxl.write.biff;
/*  2:   */ 
/*  3:   */ import jxl.write.WriteException;
/*  4:   */ 
/*  5:   */ public class JxlWriteException
/*  6:   */   extends WriteException
/*  7:   */ {
/*  8:   */   private static class WriteMessage
/*  9:   */   {
/* 10:   */     public String message;
/* 11:   */     
/* 12:   */     WriteMessage(String m)
/* 13:   */     {
/* 14:39 */       this.message = m;
/* 15:   */     }
/* 16:   */   }
/* 17:   */   
/* 18:44 */   static WriteMessage formatInitialized = new WriteMessage("Attempt to modify a referenced format");
/* 19:48 */   static WriteMessage cellReferenced = new WriteMessage("Cell has already been added to a worksheet");
/* 20:51 */   static WriteMessage maxRowsExceeded = new WriteMessage("The maximum number of rows permitted on a worksheet been exceeded");
/* 21:55 */   static WriteMessage maxColumnsExceeded = new WriteMessage("The maximum number of columns permitted on a worksheet has been exceeded");
/* 22:59 */   static WriteMessage copyPropertySets = new WriteMessage("Error encounted when copying additional property sets");
/* 23:   */   
/* 24:   */   public JxlWriteException(WriteMessage m)
/* 25:   */   {
/* 26:69 */     super(m.message);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.JxlWriteException
 * JD-Core Version:    0.7.0.1
 */