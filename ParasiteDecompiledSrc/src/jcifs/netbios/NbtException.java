/*   1:    */ package jcifs.netbios;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ 
/*   5:    */ public class NbtException
/*   6:    */   extends IOException
/*   7:    */ {
/*   8:    */   public static final int SUCCESS = 0;
/*   9:    */   public static final int ERR_NAM_SRVC = 1;
/*  10:    */   public static final int ERR_SSN_SRVC = 2;
/*  11:    */   public static final int FMT_ERR = 1;
/*  12:    */   public static final int SRV_ERR = 2;
/*  13:    */   public static final int IMP_ERR = 4;
/*  14:    */   public static final int RFS_ERR = 5;
/*  15:    */   public static final int ACT_ERR = 6;
/*  16:    */   public static final int CFT_ERR = 7;
/*  17:    */   public static final int CONNECTION_REFUSED = -1;
/*  18:    */   public static final int NOT_LISTENING_CALLED = 128;
/*  19:    */   public static final int NOT_LISTENING_CALLING = 129;
/*  20:    */   public static final int CALLED_NOT_PRESENT = 130;
/*  21:    */   public static final int NO_RESOURCES = 131;
/*  22:    */   public static final int UNSPECIFIED = 143;
/*  23:    */   public int errorClass;
/*  24:    */   public int errorCode;
/*  25:    */   
/*  26:    */   public static String getErrorString(int errorClass, int errorCode)
/*  27:    */   {
/*  28: 50 */     String result = "";
/*  29: 51 */     switch (errorClass)
/*  30:    */     {
/*  31:    */     case 0: 
/*  32: 53 */       result = result + "SUCCESS";
/*  33: 54 */       break;
/*  34:    */     case 1: 
/*  35: 56 */       result = result + "ERR_NAM_SRVC/";
/*  36: 57 */       switch (errorCode)
/*  37:    */       {
/*  38:    */       case 1: 
/*  39: 59 */         result = result + "FMT_ERR: Format Error";
/*  40:    */       }
/*  41: 61 */       result = result + "Unknown error code: " + errorCode;
/*  42:    */       
/*  43: 63 */       break;
/*  44:    */     case 2: 
/*  45: 65 */       result = result + "ERR_SSN_SRVC/";
/*  46: 66 */       switch (errorCode)
/*  47:    */       {
/*  48:    */       case -1: 
/*  49: 68 */         result = result + "Connection refused";
/*  50: 69 */         break;
/*  51:    */       case 128: 
/*  52: 71 */         result = result + "Not listening on called name";
/*  53: 72 */         break;
/*  54:    */       case 129: 
/*  55: 74 */         result = result + "Not listening for calling name";
/*  56: 75 */         break;
/*  57:    */       case 130: 
/*  58: 77 */         result = result + "Called name not present";
/*  59: 78 */         break;
/*  60:    */       case 131: 
/*  61: 80 */         result = result + "Called name present, but insufficient resources";
/*  62: 81 */         break;
/*  63:    */       case 143: 
/*  64: 83 */         result = result + "Unspecified error";
/*  65: 84 */         break;
/*  66:    */       default: 
/*  67: 86 */         result = result + "Unknown error code: " + errorCode;
/*  68:    */       }
/*  69: 88 */       break;
/*  70:    */     default: 
/*  71: 90 */       result = result + "unknown error class: " + errorClass;
/*  72:    */     }
/*  73: 92 */     return result;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public NbtException(int errorClass, int errorCode)
/*  77:    */   {
/*  78: 96 */     super(getErrorString(errorClass, errorCode));
/*  79: 97 */     this.errorClass = errorClass;
/*  80: 98 */     this.errorCode = errorCode;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String toString()
/*  84:    */   {
/*  85:101 */     return new String("errorClass=" + this.errorClass + ",errorCode=" + this.errorCode + ",errorString=" + getErrorString(this.errorClass, this.errorCode));
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.netbios.NbtException
 * JD-Core Version:    0.7.0.1
 */