/*   1:    */ package org.apache.log4j.lf5.util;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.InputStreamReader;
/*   5:    */ import java.net.URL;
/*   6:    */ 
/*   7:    */ public class Resource
/*   8:    */ {
/*   9:    */   protected String _name;
/*  10:    */   
/*  11:    */   public Resource() {}
/*  12:    */   
/*  13:    */   public Resource(String name)
/*  14:    */   {
/*  15: 63 */     this._name = name;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void setName(String name)
/*  19:    */   {
/*  20: 84 */     this._name = name;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String getName()
/*  24:    */   {
/*  25: 94 */     return this._name;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public InputStream getInputStream()
/*  29:    */   {
/*  30:105 */     InputStream in = ResourceUtils.getResourceAsStream(this, this);
/*  31:    */     
/*  32:107 */     return in;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public InputStreamReader getInputStreamReader()
/*  36:    */   {
/*  37:118 */     InputStream in = ResourceUtils.getResourceAsStream(this, this);
/*  38:120 */     if (in == null) {
/*  39:121 */       return null;
/*  40:    */     }
/*  41:124 */     InputStreamReader reader = new InputStreamReader(in);
/*  42:    */     
/*  43:126 */     return reader;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public URL getURL()
/*  47:    */   {
/*  48:135 */     return ResourceUtils.getResourceAsURL(this, this);
/*  49:    */   }
/*  50:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.util.Resource
 * JD-Core Version:    0.7.0.1
 */