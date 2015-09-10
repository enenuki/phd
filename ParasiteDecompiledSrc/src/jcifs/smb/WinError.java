/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ public abstract interface WinError
/*  4:   */ {
/*  5:   */   public static final int ERROR_SUCCESS = 0;
/*  6:   */   public static final int ERROR_ACCESS_DENIED = 5;
/*  7:   */   public static final int ERROR_REQ_NOT_ACCEP = 71;
/*  8:   */   public static final int ERROR_BAD_PIPE = 230;
/*  9:   */   public static final int ERROR_PIPE_BUSY = 231;
/* 10:   */   public static final int ERROR_NO_DATA = 232;
/* 11:   */   public static final int ERROR_PIPE_NOT_CONNECTED = 233;
/* 12:   */   public static final int ERROR_MORE_DATA = 234;
/* 13:   */   public static final int ERROR_NO_BROWSER_SERVERS_FOUND = 6118;
/* 14:37 */   public static final int[] WINERR_CODES = { 0, 5, 71, 230, 231, 232, 233, 234, 6118 };
/* 15:49 */   public static final String[] WINERR_MESSAGES = { "The operation completed successfully.", "Access is denied.", "No more connections can be made to this remote computer at this time because there are already as many connections as the computer can accept.", "The pipe state is invalid.", "All pipe instances are busy.", "The pipe is being closed.", "No process is on the other end of the pipe.", "More data is available.", "The list of servers for this workgroup is not currently available." };
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.WinError
 * JD-Core Version:    0.7.0.1
 */