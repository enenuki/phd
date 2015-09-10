/*   1:    */ package org.apache.xml.serializer.dom3;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.xml.serializer.DOM3Serializer;
/*   5:    */ import org.apache.xml.serializer.SerializationHandler;
/*   6:    */ import org.apache.xml.serializer.utils.WrappedRuntimeException;
/*   7:    */ import org.w3c.dom.DOMErrorHandler;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ import org.w3c.dom.ls.LSSerializerFilter;
/*  10:    */ import org.xml.sax.SAXException;
/*  11:    */ 
/*  12:    */ public final class DOM3SerializerImpl
/*  13:    */   implements DOM3Serializer
/*  14:    */ {
/*  15:    */   private DOMErrorHandler fErrorHandler;
/*  16:    */   private LSSerializerFilter fSerializerFilter;
/*  17:    */   private String fNewLine;
/*  18:    */   private SerializationHandler fSerializationHandler;
/*  19:    */   
/*  20:    */   public DOM3SerializerImpl(SerializationHandler handler)
/*  21:    */   {
/*  22: 61 */     this.fSerializationHandler = handler;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public DOMErrorHandler getErrorHandler()
/*  26:    */   {
/*  27: 74 */     return this.fErrorHandler;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public LSSerializerFilter getNodeFilter()
/*  31:    */   {
/*  32: 86 */     return this.fSerializerFilter;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public char[] getNewLine()
/*  36:    */   {
/*  37: 93 */     return this.fNewLine != null ? this.fNewLine.toCharArray() : null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void serializeDOM3(Node node)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:    */     try
/*  44:    */     {
/*  45:108 */       DOM3TreeWalker walker = new DOM3TreeWalker(this.fSerializationHandler, this.fErrorHandler, this.fSerializerFilter, this.fNewLine);
/*  46:    */       
/*  47:    */ 
/*  48:111 */       walker.traverse(node);
/*  49:    */     }
/*  50:    */     catch (SAXException se)
/*  51:    */     {
/*  52:113 */       throw new WrappedRuntimeException(se);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setErrorHandler(DOMErrorHandler handler)
/*  57:    */   {
/*  58:125 */     this.fErrorHandler = handler;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setNodeFilter(LSSerializerFilter filter)
/*  62:    */   {
/*  63:137 */     this.fSerializerFilter = filter;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setSerializationHandler(SerializationHandler handler)
/*  67:    */   {
/*  68:148 */     this.fSerializationHandler = handler;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setNewLine(char[] newLine)
/*  72:    */   {
/*  73:156 */     this.fNewLine = (newLine != null ? new String(newLine) : null);
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.dom3.DOM3SerializerImpl
 * JD-Core Version:    0.7.0.1
 */