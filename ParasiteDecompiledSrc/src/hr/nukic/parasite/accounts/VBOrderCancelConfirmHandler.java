/*    1:     */ package hr.nukic.parasite.accounts;
/*    2:     */ 
/*    3:     */ import com.gargoylesoftware.htmlunit.ConfirmHandler;
/*    4:     */ import com.gargoylesoftware.htmlunit.Page;
/*    5:     */ import nukic.parasite.utils.MainLogger;
/*    6:     */ 
/*    7:     */ class VBOrderCancelConfirmHandler
/*    8:     */   implements ConfirmHandler
/*    9:     */ {
/*   10:     */   public boolean handleConfirm(Page page, String str)
/*   11:     */   {
/*   12:1310 */     MainLogger.debug("Confirmation handler passed string: " + str);
/*   13:     */     
/*   14:1312 */     return true;
/*   15:     */   }
/*   16:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.VBOrderCancelConfirmHandler
 * JD-Core Version:    0.7.0.1
 */