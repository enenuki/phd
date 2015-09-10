/*   1:    */ package org.apache.xalan.serialize;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.w3c.dom.Node;
/*   8:    */ import org.xml.sax.ContentHandler;
/*   9:    */ 
/*  10:    */ /**
/*  11:    */  * @deprecated
/*  12:    */  */
/*  13:    */ public abstract class SerializerFactory
/*  14:    */ {
/*  15:    */   /**
/*  16:    */    * @deprecated
/*  17:    */    */
/*  18:    */   public static Serializer getSerializer(Properties format)
/*  19:    */   {
/*  20: 59 */     org.apache.xml.serializer.Serializer ser = org.apache.xml.serializer.SerializerFactory.getSerializer(format);
/*  21: 60 */     SerializerWrapper si = new SerializerWrapper(ser);
/*  22: 61 */     return si;
/*  23:    */   }
/*  24:    */   
/*  25:    */   /**
/*  26:    */    * @deprecated
/*  27:    */    */
/*  28:    */   private static class SerializerWrapper
/*  29:    */     implements Serializer
/*  30:    */   {
/*  31:    */     private final org.apache.xml.serializer.Serializer m_serializer;
/*  32:    */     private DOMSerializer m_old_DOMSerializer;
/*  33:    */     
/*  34:    */     SerializerWrapper(org.apache.xml.serializer.Serializer ser)
/*  35:    */     {
/*  36: 78 */       this.m_serializer = ser;
/*  37:    */     }
/*  38:    */     
/*  39:    */     public void setOutputStream(OutputStream output)
/*  40:    */     {
/*  41: 84 */       this.m_serializer.setOutputStream(output);
/*  42:    */     }
/*  43:    */     
/*  44:    */     public OutputStream getOutputStream()
/*  45:    */     {
/*  46: 89 */       return this.m_serializer.getOutputStream();
/*  47:    */     }
/*  48:    */     
/*  49:    */     public void setWriter(Writer writer)
/*  50:    */     {
/*  51: 94 */       this.m_serializer.setWriter(writer);
/*  52:    */     }
/*  53:    */     
/*  54:    */     public Writer getWriter()
/*  55:    */     {
/*  56: 99 */       return this.m_serializer.getWriter();
/*  57:    */     }
/*  58:    */     
/*  59:    */     public void setOutputFormat(Properties format)
/*  60:    */     {
/*  61:104 */       this.m_serializer.setOutputFormat(format);
/*  62:    */     }
/*  63:    */     
/*  64:    */     public Properties getOutputFormat()
/*  65:    */     {
/*  66:109 */       return this.m_serializer.getOutputFormat();
/*  67:    */     }
/*  68:    */     
/*  69:    */     public ContentHandler asContentHandler()
/*  70:    */       throws IOException
/*  71:    */     {
/*  72:114 */       return this.m_serializer.asContentHandler();
/*  73:    */     }
/*  74:    */     
/*  75:    */     public DOMSerializer asDOMSerializer()
/*  76:    */       throws IOException
/*  77:    */     {
/*  78:123 */       if (this.m_old_DOMSerializer == null) {
/*  79:125 */         this.m_old_DOMSerializer = new SerializerFactory.DOMSerializerWrapper(this.m_serializer.asDOMSerializer());
/*  80:    */       }
/*  81:128 */       return this.m_old_DOMSerializer;
/*  82:    */     }
/*  83:    */     
/*  84:    */     public boolean reset()
/*  85:    */     {
/*  86:135 */       return this.m_serializer.reset();
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   private static class DOMSerializerWrapper
/*  91:    */     implements DOMSerializer
/*  92:    */   {
/*  93:    */     private final org.apache.xml.serializer.DOMSerializer m_dom;
/*  94:    */     
/*  95:    */     DOMSerializerWrapper(org.apache.xml.serializer.DOMSerializer domser)
/*  96:    */     {
/*  97:150 */       this.m_dom = domser;
/*  98:    */     }
/*  99:    */     
/* 100:    */     public void serialize(Node node)
/* 101:    */       throws IOException
/* 102:    */     {
/* 103:155 */       this.m_dom.serialize(node);
/* 104:    */     }
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.serialize.SerializerFactory
 * JD-Core Version:    0.7.0.1
 */