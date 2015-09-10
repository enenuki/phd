/*   1:    */ package org.apache.xml.serializer.dom3;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.DOMLocator;
/*   4:    */ import org.w3c.dom.Node;
/*   5:    */ 
/*   6:    */ final class DOMLocatorImpl
/*   7:    */   implements DOMLocator
/*   8:    */ {
/*   9:    */   private final int fColumnNumber;
/*  10:    */   private final int fLineNumber;
/*  11:    */   private final Node fRelatedNode;
/*  12:    */   private final String fUri;
/*  13:    */   private final int fByteOffset;
/*  14:    */   private final int fUtf16Offset;
/*  15:    */   
/*  16:    */   DOMLocatorImpl()
/*  17:    */   {
/*  18: 84 */     this.fColumnNumber = -1;
/*  19: 85 */     this.fLineNumber = -1;
/*  20: 86 */     this.fRelatedNode = null;
/*  21: 87 */     this.fUri = null;
/*  22: 88 */     this.fByteOffset = -1;
/*  23: 89 */     this.fUtf16Offset = -1;
/*  24:    */   }
/*  25:    */   
/*  26:    */   DOMLocatorImpl(int lineNumber, int columnNumber, String uri)
/*  27:    */   {
/*  28: 93 */     this.fLineNumber = lineNumber;
/*  29: 94 */     this.fColumnNumber = columnNumber;
/*  30: 95 */     this.fUri = uri;
/*  31:    */     
/*  32: 97 */     this.fRelatedNode = null;
/*  33: 98 */     this.fByteOffset = -1;
/*  34: 99 */     this.fUtf16Offset = -1;
/*  35:    */   }
/*  36:    */   
/*  37:    */   DOMLocatorImpl(int lineNumber, int columnNumber, int utf16Offset, String uri)
/*  38:    */   {
/*  39:103 */     this.fLineNumber = lineNumber;
/*  40:104 */     this.fColumnNumber = columnNumber;
/*  41:105 */     this.fUri = uri;
/*  42:106 */     this.fUtf16Offset = utf16Offset;
/*  43:    */     
/*  44:    */ 
/*  45:109 */     this.fRelatedNode = null;
/*  46:110 */     this.fByteOffset = -1;
/*  47:    */   }
/*  48:    */   
/*  49:    */   DOMLocatorImpl(int lineNumber, int columnNumber, int byteoffset, Node relatedData, String uri)
/*  50:    */   {
/*  51:114 */     this.fLineNumber = lineNumber;
/*  52:115 */     this.fColumnNumber = columnNumber;
/*  53:116 */     this.fByteOffset = byteoffset;
/*  54:117 */     this.fRelatedNode = relatedData;
/*  55:118 */     this.fUri = uri;
/*  56:    */     
/*  57:120 */     this.fUtf16Offset = -1;
/*  58:    */   }
/*  59:    */   
/*  60:    */   DOMLocatorImpl(int lineNumber, int columnNumber, int byteoffset, Node relatedData, String uri, int utf16Offset)
/*  61:    */   {
/*  62:124 */     this.fLineNumber = lineNumber;
/*  63:125 */     this.fColumnNumber = columnNumber;
/*  64:126 */     this.fByteOffset = byteoffset;
/*  65:127 */     this.fRelatedNode = relatedData;
/*  66:128 */     this.fUri = uri;
/*  67:129 */     this.fUtf16Offset = utf16Offset;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getLineNumber()
/*  71:    */   {
/*  72:138 */     return this.fLineNumber;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getColumnNumber()
/*  76:    */   {
/*  77:146 */     return this.fColumnNumber;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getUri()
/*  81:    */   {
/*  82:154 */     return this.fUri;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Node getRelatedNode()
/*  86:    */   {
/*  87:159 */     return this.fRelatedNode;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getByteOffset()
/*  91:    */   {
/*  92:168 */     return this.fByteOffset;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getUtf16Offset()
/*  96:    */   {
/*  97:177 */     return this.fUtf16Offset;
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.dom3.DOMLocatorImpl
 * JD-Core Version:    0.7.0.1
 */