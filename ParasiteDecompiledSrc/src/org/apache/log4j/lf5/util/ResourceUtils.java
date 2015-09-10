/*   1:    */ package org.apache.log4j.lf5.util;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.net.URL;
/*   5:    */ 
/*   6:    */ public class ResourceUtils
/*   7:    */ {
/*   8:    */   public static InputStream getResourceAsStream(Object object, Resource resource)
/*   9:    */   {
/*  10: 73 */     ClassLoader loader = object.getClass().getClassLoader();
/*  11:    */     
/*  12: 75 */     InputStream in = null;
/*  13: 77 */     if (loader != null) {
/*  14: 78 */       in = loader.getResourceAsStream(resource.getName());
/*  15:    */     } else {
/*  16: 80 */       in = ClassLoader.getSystemResourceAsStream(resource.getName());
/*  17:    */     }
/*  18: 83 */     return in;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static URL getResourceAsURL(Object object, Resource resource)
/*  22:    */   {
/*  23:102 */     ClassLoader loader = object.getClass().getClassLoader();
/*  24:    */     
/*  25:104 */     URL url = null;
/*  26:106 */     if (loader != null) {
/*  27:107 */       url = loader.getResource(resource.getName());
/*  28:    */     } else {
/*  29:109 */       url = ClassLoader.getSystemResource(resource.getName());
/*  30:    */     }
/*  31:112 */     return url;
/*  32:    */   }
/*  33:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.util.ResourceUtils
 * JD-Core Version:    0.7.0.1
 */