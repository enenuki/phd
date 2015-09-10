/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import java.io.StringWriter;
/*   6:    */ import jcifs.util.Hexdump;
/*   7:    */ 
/*   8:    */ public class SmbException
/*   9:    */   extends IOException
/*  10:    */   implements NtStatus, DosError, WinError
/*  11:    */ {
/*  12:    */   private int status;
/*  13:    */   private Throwable rootCause;
/*  14:    */   
/*  15:    */   static String getMessageByCode(int errcode)
/*  16:    */   {
/*  17: 49 */     if (errcode == 0) {
/*  18: 50 */       return "NT_STATUS_SUCCESS";
/*  19:    */     }
/*  20: 52 */     if ((errcode & 0xC0000000) == -1073741824)
/*  21:    */     {
/*  22: 53 */       int min = 1;
/*  23: 54 */       int max = NtStatus.NT_STATUS_CODES.length - 1;
/*  24: 56 */       while (max >= min)
/*  25:    */       {
/*  26: 57 */         int mid = (min + max) / 2;
/*  27: 59 */         if (errcode > NtStatus.NT_STATUS_CODES[mid]) {
/*  28: 60 */           min = mid + 1;
/*  29: 61 */         } else if (errcode < NtStatus.NT_STATUS_CODES[mid]) {
/*  30: 62 */           max = mid - 1;
/*  31:    */         } else {
/*  32: 64 */           return NtStatus.NT_STATUS_MESSAGES[mid];
/*  33:    */         }
/*  34:    */       }
/*  35:    */     }
/*  36:    */     else
/*  37:    */     {
/*  38: 68 */       int min = 0;
/*  39: 69 */       int max = DosError.DOS_ERROR_CODES.length - 1;
/*  40: 71 */       while (max >= min)
/*  41:    */       {
/*  42: 72 */         int mid = (min + max) / 2;
/*  43: 74 */         if (errcode > DosError.DOS_ERROR_CODES[mid][0]) {
/*  44: 75 */           min = mid + 1;
/*  45: 76 */         } else if (errcode < DosError.DOS_ERROR_CODES[mid][0]) {
/*  46: 77 */           max = mid - 1;
/*  47:    */         } else {
/*  48: 79 */           return DosError.DOS_ERROR_MESSAGES[mid];
/*  49:    */         }
/*  50:    */       }
/*  51:    */     }
/*  52: 84 */     return "0x" + Hexdump.toHexString(errcode, 8);
/*  53:    */   }
/*  54:    */   
/*  55:    */   static int getStatusByCode(int errcode)
/*  56:    */   {
/*  57: 87 */     if ((errcode & 0xC0000000) != 0) {
/*  58: 88 */       return errcode;
/*  59:    */     }
/*  60: 90 */     int min = 0;
/*  61: 91 */     int max = DosError.DOS_ERROR_CODES.length - 1;
/*  62: 93 */     while (max >= min)
/*  63:    */     {
/*  64: 94 */       int mid = (min + max) / 2;
/*  65: 96 */       if (errcode > DosError.DOS_ERROR_CODES[mid][0]) {
/*  66: 97 */         min = mid + 1;
/*  67: 98 */       } else if (errcode < DosError.DOS_ERROR_CODES[mid][0]) {
/*  68: 99 */         max = mid - 1;
/*  69:    */       } else {
/*  70:101 */         return DosError.DOS_ERROR_CODES[mid][1];
/*  71:    */       }
/*  72:    */     }
/*  73:106 */     return -1073741823;
/*  74:    */   }
/*  75:    */   
/*  76:    */   static String getMessageByWinerrCode(int errcode)
/*  77:    */   {
/*  78:109 */     int min = 0;
/*  79:110 */     int max = WinError.WINERR_CODES.length - 1;
/*  80:112 */     while (max >= min)
/*  81:    */     {
/*  82:113 */       int mid = (min + max) / 2;
/*  83:115 */       if (errcode > WinError.WINERR_CODES[mid]) {
/*  84:116 */         min = mid + 1;
/*  85:117 */       } else if (errcode < WinError.WINERR_CODES[mid]) {
/*  86:118 */         max = mid - 1;
/*  87:    */       } else {
/*  88:120 */         return WinError.WINERR_MESSAGES[mid];
/*  89:    */       }
/*  90:    */     }
/*  91:124 */     return errcode + "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   SmbException() {}
/*  95:    */   
/*  96:    */   SmbException(int errcode, Throwable rootCause)
/*  97:    */   {
/*  98:134 */     super(getMessageByCode(errcode));
/*  99:135 */     this.status = getStatusByCode(errcode);
/* 100:136 */     this.rootCause = rootCause;
/* 101:    */   }
/* 102:    */   
/* 103:    */   SmbException(String msg)
/* 104:    */   {
/* 105:139 */     super(msg);
/* 106:140 */     this.status = -1073741823;
/* 107:    */   }
/* 108:    */   
/* 109:    */   SmbException(String msg, Throwable rootCause)
/* 110:    */   {
/* 111:143 */     super(msg);
/* 112:144 */     this.rootCause = rootCause;
/* 113:145 */     this.status = -1073741823;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public SmbException(int errcode, boolean winerr)
/* 117:    */   {
/* 118:148 */     super(winerr ? getMessageByWinerrCode(errcode) : getMessageByCode(errcode));
/* 119:149 */     this.status = (winerr ? errcode : getStatusByCode(errcode));
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getNtStatus()
/* 123:    */   {
/* 124:153 */     return this.status;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Throwable getRootCause()
/* 128:    */   {
/* 129:156 */     return this.rootCause;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String toString()
/* 133:    */   {
/* 134:159 */     if (this.rootCause != null)
/* 135:    */     {
/* 136:160 */       StringWriter sw = new StringWriter();
/* 137:161 */       PrintWriter pw = new PrintWriter(sw);
/* 138:162 */       this.rootCause.printStackTrace(pw);
/* 139:163 */       return super.toString() + "\n" + sw;
/* 140:    */     }
/* 141:165 */     return super.toString();
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbException
 * JD-Core Version:    0.7.0.1
 */