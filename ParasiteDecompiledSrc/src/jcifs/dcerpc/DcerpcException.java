/*  1:   */ package jcifs.dcerpc;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.PrintWriter;
/*  5:   */ import java.io.StringWriter;
/*  6:   */ import jcifs.smb.WinError;
/*  7:   */ import jcifs.util.Hexdump;
/*  8:   */ 
/*  9:   */ public class DcerpcException
/* 10:   */   extends IOException
/* 11:   */   implements DcerpcError, WinError
/* 12:   */ {
/* 13:   */   private int error;
/* 14:   */   private Throwable rootCause;
/* 15:   */   
/* 16:   */   static String getMessageByDcerpcError(int errcode)
/* 17:   */   {
/* 18:30 */     int min = 0;
/* 19:31 */     int max = DcerpcError.DCERPC_FAULT_CODES.length;
/* 20:33 */     while (max >= min)
/* 21:   */     {
/* 22:34 */       int mid = (min + max) / 2;
/* 23:36 */       if (errcode > DcerpcError.DCERPC_FAULT_CODES[mid]) {
/* 24:37 */         min = mid + 1;
/* 25:38 */       } else if (errcode < DcerpcError.DCERPC_FAULT_CODES[mid]) {
/* 26:39 */         max = mid - 1;
/* 27:   */       } else {
/* 28:41 */         return DcerpcError.DCERPC_FAULT_MESSAGES[mid];
/* 29:   */       }
/* 30:   */     }
/* 31:45 */     return "0x" + Hexdump.toHexString(errcode, 8);
/* 32:   */   }
/* 33:   */   
/* 34:   */   DcerpcException(int error)
/* 35:   */   {
/* 36:52 */     super(getMessageByDcerpcError(error));
/* 37:53 */     this.error = error;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public DcerpcException(String msg)
/* 41:   */   {
/* 42:56 */     super(msg);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public DcerpcException(String msg, Throwable rootCause)
/* 46:   */   {
/* 47:59 */     super(msg);
/* 48:60 */     this.rootCause = rootCause;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int getErrorCode()
/* 52:   */   {
/* 53:63 */     return this.error;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public Throwable getRootCause()
/* 57:   */   {
/* 58:66 */     return this.rootCause;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String toString()
/* 62:   */   {
/* 63:69 */     if (this.rootCause != null)
/* 64:   */     {
/* 65:70 */       StringWriter sw = new StringWriter();
/* 66:71 */       PrintWriter pw = new PrintWriter(sw);
/* 67:72 */       this.rootCause.printStackTrace(pw);
/* 68:73 */       return super.toString() + "\n" + sw;
/* 69:   */     }
/* 70:75 */     return super.toString();
/* 71:   */   }
/* 72:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.DcerpcException
 * JD-Core Version:    0.7.0.1
 */