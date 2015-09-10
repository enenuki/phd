/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ public final class Version
/*   6:    */ {
/*   7:    */   public static String getVersion()
/*   8:    */   {
/*   9: 45 */     return getProduct() + " " + getImplementationLanguage() + " " + getMajorVersionNum() + "." + getReleaseVersionNum() + "." + (getDevelopmentVersionNum() > 0 ? "D" + getDevelopmentVersionNum() : new StringBuffer().append("").append(getMaintenanceVersionNum()).toString());
/*  10:    */   }
/*  11:    */   
/*  12:    */   public static void main(String[] argv)
/*  13:    */   {
/*  14: 58 */     System.out.println(getVersion());
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static String getProduct()
/*  18:    */   {
/*  19: 66 */     return "Serializer";
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static String getImplementationLanguage()
/*  23:    */   {
/*  24: 74 */     return "Java";
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static int getMajorVersionNum()
/*  28:    */   {
/*  29: 91 */     return 2;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static int getReleaseVersionNum()
/*  33:    */   {
/*  34:105 */     return 7;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static int getMaintenanceVersionNum()
/*  38:    */   {
/*  39:119 */     return 1;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static int getDevelopmentVersionNum()
/*  43:    */   {
/*  44:    */     try
/*  45:    */     {
/*  46:142 */       if (new String("").length() == 0) {
/*  47:143 */         return 0;
/*  48:    */       }
/*  49:145 */       return Integer.parseInt("");
/*  50:    */     }
/*  51:    */     catch (NumberFormatException nfe) {}
/*  52:147 */     return 0;
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.Version
 * JD-Core Version:    0.7.0.1
 */