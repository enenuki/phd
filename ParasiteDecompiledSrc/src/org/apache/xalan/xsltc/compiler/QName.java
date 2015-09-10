/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ final class QName
/*  4:   */ {
/*  5:   */   private final String _localname;
/*  6:   */   private String _prefix;
/*  7:   */   private String _namespace;
/*  8:   */   private String _stringRep;
/*  9:   */   private int _hashCode;
/* 10:   */   
/* 11:   */   public QName(String namespace, String prefix, String localname)
/* 12:   */   {
/* 13:37 */     this._namespace = namespace;
/* 14:38 */     this._prefix = prefix;
/* 15:39 */     this._localname = localname;
/* 16:   */     
/* 17:41 */     this._stringRep = ((namespace != null) && (!namespace.equals("")) ? namespace + ':' + localname : localname);
/* 18:   */     
/* 19:   */ 
/* 20:   */ 
/* 21:45 */     this._hashCode = (this._stringRep.hashCode() + 19);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void clearNamespace()
/* 25:   */   {
/* 26:49 */     this._namespace = "";
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String toString()
/* 30:   */   {
/* 31:53 */     return this._stringRep;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getStringRep()
/* 35:   */   {
/* 36:57 */     return this._stringRep;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean equals(Object other)
/* 40:   */   {
/* 41:61 */     return this == other;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getLocalPart()
/* 45:   */   {
/* 46:65 */     return this._localname;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String getNamespace()
/* 50:   */   {
/* 51:69 */     return this._namespace;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String getPrefix()
/* 55:   */   {
/* 56:73 */     return this._prefix;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public int hashCode()
/* 60:   */   {
/* 61:77 */     return this._hashCode;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public String dump()
/* 65:   */   {
/* 66:81 */     return "QName: " + this._namespace + "(" + this._prefix + "):" + this._localname;
/* 67:   */   }
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.QName
 * JD-Core Version:    0.7.0.1
 */