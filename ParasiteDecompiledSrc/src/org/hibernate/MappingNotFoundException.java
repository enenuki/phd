/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class MappingNotFoundException
/*  4:   */   extends MappingException
/*  5:   */ {
/*  6:   */   private final String path;
/*  7:   */   private final String type;
/*  8:   */   
/*  9:   */   public MappingNotFoundException(String customMessage, String type, String path, Throwable cause)
/* 10:   */   {
/* 11:39 */     super(customMessage, cause);
/* 12:40 */     this.type = type;
/* 13:41 */     this.path = path;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public MappingNotFoundException(String customMessage, String type, String path)
/* 17:   */   {
/* 18:45 */     super(customMessage);
/* 19:46 */     this.type = type;
/* 20:47 */     this.path = path;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public MappingNotFoundException(String type, String path)
/* 24:   */   {
/* 25:51 */     this(type + ": " + path + " not found", type, path);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public MappingNotFoundException(String type, String path, Throwable cause)
/* 29:   */   {
/* 30:55 */     this(type + ": " + path + " not found", type, path, cause);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getType()
/* 34:   */   {
/* 35:59 */     return this.type;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getPath()
/* 39:   */   {
/* 40:63 */     return this.path;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.MappingNotFoundException
 * JD-Core Version:    0.7.0.1
 */