/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.jaxb.SourceType;
/*  4:   */ import org.hibernate.internal.util.xml.XmlDocument;
/*  5:   */ 
/*  6:   */ public class InvalidMappingException
/*  7:   */   extends MappingException
/*  8:   */ {
/*  9:   */   private final String path;
/* 10:   */   private final String type;
/* 11:   */   
/* 12:   */   public InvalidMappingException(String customMessage, String type, String path, Throwable cause)
/* 13:   */   {
/* 14:41 */     super(customMessage, cause);
/* 15:42 */     this.type = type;
/* 16:43 */     this.path = path;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public InvalidMappingException(String customMessage, String type, String path)
/* 20:   */   {
/* 21:47 */     super(customMessage);
/* 22:48 */     this.type = type;
/* 23:49 */     this.path = path;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public InvalidMappingException(String customMessage, XmlDocument xmlDocument, Throwable cause)
/* 27:   */   {
/* 28:53 */     this(customMessage, xmlDocument.getOrigin().getType(), xmlDocument.getOrigin().getName(), cause);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public InvalidMappingException(String customMessage, XmlDocument xmlDocument)
/* 32:   */   {
/* 33:57 */     this(customMessage, xmlDocument.getOrigin().getType(), xmlDocument.getOrigin().getName());
/* 34:   */   }
/* 35:   */   
/* 36:   */   public InvalidMappingException(String customMessage, org.hibernate.internal.jaxb.Origin origin)
/* 37:   */   {
/* 38:61 */     this(customMessage, origin.getType().toString(), origin.getName());
/* 39:   */   }
/* 40:   */   
/* 41:   */   public InvalidMappingException(String type, String path)
/* 42:   */   {
/* 43:65 */     this("Could not parse mapping document from " + type + (path == null ? "" : new StringBuilder().append(" ").append(path).toString()), type, path);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public InvalidMappingException(String type, String path, Throwable cause)
/* 47:   */   {
/* 48:69 */     this("Could not parse mapping document from " + type + (path == null ? "" : new StringBuilder().append(" ").append(path).toString()), type, path, cause);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getType()
/* 52:   */   {
/* 53:73 */     return this.type;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public String getPath()
/* 57:   */   {
/* 58:77 */     return this.path;
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.InvalidMappingException
 * JD-Core Version:    0.7.0.1
 */