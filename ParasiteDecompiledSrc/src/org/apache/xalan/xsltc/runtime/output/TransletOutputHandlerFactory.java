/*   1:    */ package org.apache.xalan.xsltc.runtime.output;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ import javax.xml.parsers.ParserConfigurationException;
/*   7:    */ import org.apache.xalan.xsltc.trax.SAX2DOM;
/*   8:    */ import org.apache.xml.serializer.SerializationHandler;
/*   9:    */ import org.apache.xml.serializer.Serializer;
/*  10:    */ import org.apache.xml.serializer.ToHTMLStream;
/*  11:    */ import org.apache.xml.serializer.ToTextStream;
/*  12:    */ import org.apache.xml.serializer.ToUnknownStream;
/*  13:    */ import org.apache.xml.serializer.ToXMLSAXHandler;
/*  14:    */ import org.apache.xml.serializer.ToXMLStream;
/*  15:    */ import org.w3c.dom.Node;
/*  16:    */ import org.xml.sax.ContentHandler;
/*  17:    */ import org.xml.sax.ext.LexicalHandler;
/*  18:    */ 
/*  19:    */ public class TransletOutputHandlerFactory
/*  20:    */ {
/*  21:    */   public static final int STREAM = 0;
/*  22:    */   public static final int SAX = 1;
/*  23:    */   public static final int DOM = 2;
/*  24: 51 */   private String _encoding = "utf-8";
/*  25: 52 */   private String _method = null;
/*  26: 53 */   private int _outputType = 0;
/*  27: 54 */   private OutputStream _ostream = System.out;
/*  28: 55 */   private Writer _writer = null;
/*  29: 56 */   private Node _node = null;
/*  30: 57 */   private Node _nextSibling = null;
/*  31: 58 */   private int _indentNumber = -1;
/*  32: 59 */   private ContentHandler _handler = null;
/*  33: 60 */   private LexicalHandler _lexHandler = null;
/*  34:    */   
/*  35:    */   public static TransletOutputHandlerFactory newInstance()
/*  36:    */   {
/*  37: 63 */     return new TransletOutputHandlerFactory();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setOutputType(int outputType)
/*  41:    */   {
/*  42: 67 */     this._outputType = outputType;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setEncoding(String encoding)
/*  46:    */   {
/*  47: 71 */     if (encoding != null) {
/*  48: 72 */       this._encoding = encoding;
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setOutputMethod(String method)
/*  53:    */   {
/*  54: 77 */     this._method = method;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setOutputStream(OutputStream ostream)
/*  58:    */   {
/*  59: 81 */     this._ostream = ostream;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setWriter(Writer writer)
/*  63:    */   {
/*  64: 85 */     this._writer = writer;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setHandler(ContentHandler handler)
/*  68:    */   {
/*  69: 89 */     this._handler = handler;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setLexicalHandler(LexicalHandler lex)
/*  73:    */   {
/*  74: 93 */     this._lexHandler = lex;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setNode(Node node)
/*  78:    */   {
/*  79: 97 */     this._node = node;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Node getNode()
/*  83:    */   {
/*  84:101 */     return (this._handler instanceof SAX2DOM) ? ((SAX2DOM)this._handler).getDOM() : null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setNextSibling(Node nextSibling)
/*  88:    */   {
/*  89:106 */     this._nextSibling = nextSibling;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIndentNumber(int value)
/*  93:    */   {
/*  94:110 */     this._indentNumber = value;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public SerializationHandler getSerializationHandler()
/*  98:    */     throws IOException, ParserConfigurationException
/*  99:    */   {
/* 100:116 */     SerializationHandler result = null;
/* 101:117 */     switch (this._outputType)
/* 102:    */     {
/* 103:    */     case 0: 
/* 104:121 */       if (this._method == null) {
/* 105:123 */         result = new ToUnknownStream();
/* 106:125 */       } else if (this._method.equalsIgnoreCase("xml")) {
/* 107:128 */         result = new ToXMLStream();
/* 108:131 */       } else if (this._method.equalsIgnoreCase("html")) {
/* 109:134 */         result = new ToHTMLStream();
/* 110:137 */       } else if (this._method.equalsIgnoreCase("text")) {
/* 111:140 */         result = new ToTextStream();
/* 112:    */       }
/* 113:144 */       if ((result != null) && (this._indentNumber >= 0)) {
/* 114:146 */         result.setIndentAmount(this._indentNumber);
/* 115:    */       }
/* 116:149 */       result.setEncoding(this._encoding);
/* 117:151 */       if (this._writer != null) {
/* 118:153 */         result.setWriter(this._writer);
/* 119:    */       } else {
/* 120:157 */         result.setOutputStream(this._ostream);
/* 121:    */       }
/* 122:159 */       return result;
/* 123:    */     case 2: 
/* 124:162 */       this._handler = (this._node != null ? new SAX2DOM(this._node, this._nextSibling) : new SAX2DOM());
/* 125:163 */       this._lexHandler = ((LexicalHandler)this._handler);
/* 126:    */     case 1: 
/* 127:166 */       if (this._method == null) {
/* 128:168 */         this._method = "xml";
/* 129:    */       }
/* 130:171 */       if (this._lexHandler == null) {
/* 131:173 */         result = new ToXMLSAXHandler(this._handler, this._encoding);
/* 132:    */       } else {
/* 133:177 */         result = new ToXMLSAXHandler(this._handler, this._lexHandler, this._encoding);
/* 134:    */       }
/* 135:184 */       return result;
/* 136:    */     }
/* 137:186 */     return null;
/* 138:    */   }
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.output.TransletOutputHandlerFactory
 * JD-Core Version:    0.7.0.1
 */