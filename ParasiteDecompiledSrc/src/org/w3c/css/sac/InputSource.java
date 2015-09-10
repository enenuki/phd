/*   1:    */ package org.w3c.css.sac;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Reader;
/*   5:    */ 
/*   6:    */ public class InputSource
/*   7:    */ {
/*   8:    */   private String uri;
/*   9:    */   private InputStream byteStream;
/*  10:    */   private String encoding;
/*  11:    */   private Reader characterStream;
/*  12:    */   private String title;
/*  13:    */   private String media;
/*  14:    */   
/*  15:    */   public InputSource() {}
/*  16:    */   
/*  17:    */   public InputSource(String paramString)
/*  18:    */   {
/*  19: 70 */     setURI(paramString);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public InputSource(Reader paramReader)
/*  23:    */   {
/*  24: 87 */     setCharacterStream(paramReader);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setURI(String paramString)
/*  28:    */   {
/*  29:112 */     this.uri = paramString;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getURI()
/*  33:    */   {
/*  34:128 */     return this.uri;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setByteStream(InputStream paramInputStream)
/*  38:    */   {
/*  39:148 */     this.byteStream = paramInputStream;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public InputStream getByteStream()
/*  43:    */   {
/*  44:162 */     return this.byteStream;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setEncoding(String paramString)
/*  48:    */   {
/*  49:181 */     this.encoding = paramString;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getEncoding()
/*  53:    */   {
/*  54:193 */     return this.encoding;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setCharacterStream(Reader paramReader)
/*  58:    */   {
/*  59:208 */     this.characterStream = paramReader;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Reader getCharacterStream()
/*  63:    */   {
/*  64:218 */     return this.characterStream;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setTitle(String paramString)
/*  68:    */   {
/*  69:229 */     this.title = paramString;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getTitle()
/*  73:    */   {
/*  74:236 */     return this.title;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setMedia(String paramString)
/*  78:    */   {
/*  79:244 */     this.media = paramString;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getMedia()
/*  83:    */   {
/*  84:253 */     if (this.media == null) {
/*  85:254 */       return "all";
/*  86:    */     }
/*  87:256 */     return this.media;
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.css.sac.InputSource
 * JD-Core Version:    0.7.0.1
 */