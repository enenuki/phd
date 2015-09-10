/*   1:    */ package org.apache.xalan;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ public class Version
/*   6:    */ {
/*   7:    */   public static String getVersion()
/*   8:    */   {
/*   9: 48 */     return getProduct() + " " + getImplementationLanguage() + " " + getMajorVersionNum() + "." + getReleaseVersionNum() + "." + (getDevelopmentVersionNum() > 0 ? "D" + getDevelopmentVersionNum() : new StringBuffer().append("").append(getMaintenanceVersionNum()).toString());
/*  10:    */   }
/*  11:    */   
/*  12:    */   public static void main(String[] argv)
/*  13:    */   {
/*  14: 61 */     System.out.println(getVersion());
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static String getProduct()
/*  18:    */   {
/*  19: 69 */     return "Xalan";
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static String getImplementationLanguage()
/*  23:    */   {
/*  24: 77 */     return "Java";
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static int getMajorVersionNum()
/*  28:    */   {
/*  29: 94 */     return 2;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static int getReleaseVersionNum()
/*  33:    */   {
/*  34:108 */     return 7;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static int getMaintenanceVersionNum()
/*  38:    */   {
/*  39:122 */     return 1;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static int getDevelopmentVersionNum()
/*  43:    */   {
/*  44:    */     try
/*  45:    */     {
/*  46:145 */       if (new String("").length() == 0) {
/*  47:146 */         return 0;
/*  48:    */       }
/*  49:148 */       return Integer.parseInt("");
/*  50:    */     }
/*  51:    */     catch (NumberFormatException nfe) {}
/*  52:150 */     return 0;
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.Version
 * JD-Core Version:    0.7.0.1
 */