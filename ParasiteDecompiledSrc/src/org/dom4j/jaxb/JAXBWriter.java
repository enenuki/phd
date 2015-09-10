/*   1:    */ package org.dom4j.jaxb;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import java.io.Writer;
/*   8:    */ import javax.xml.bind.JAXBException;
/*   9:    */ import org.dom4j.io.OutputFormat;
/*  10:    */ import org.dom4j.io.XMLWriter;
/*  11:    */ import org.xml.sax.SAXException;
/*  12:    */ 
/*  13:    */ public class JAXBWriter
/*  14:    */   extends JAXBSupport
/*  15:    */ {
/*  16:    */   private XMLWriter xmlWriter;
/*  17:    */   private OutputFormat outputFormat;
/*  18:    */   
/*  19:    */   public JAXBWriter(String contextPath)
/*  20:    */   {
/*  21: 50 */     super(contextPath);
/*  22: 51 */     this.outputFormat = new OutputFormat();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public JAXBWriter(String contextPath, OutputFormat outputFormat)
/*  26:    */   {
/*  27: 67 */     super(contextPath);
/*  28: 68 */     this.outputFormat = outputFormat;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public JAXBWriter(String contextPath, ClassLoader classloader)
/*  32:    */   {
/*  33: 85 */     super(contextPath, classloader);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public JAXBWriter(String contextPath, ClassLoader classloader, OutputFormat outputFormat)
/*  37:    */   {
/*  38:104 */     super(contextPath, classloader);
/*  39:105 */     this.outputFormat = outputFormat;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public OutputFormat getOutputFormat()
/*  43:    */   {
/*  44:114 */     return this.outputFormat;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setOutput(File file)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:128 */     getWriter().setOutputStream(new FileOutputStream(file));
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setOutput(OutputStream outputStream)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56:142 */     getWriter().setOutputStream(outputStream);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setOutput(Writer writer)
/*  60:    */     throws IOException
/*  61:    */   {
/*  62:154 */     getWriter().setWriter(writer);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void startDocument()
/*  66:    */     throws IOException, SAXException
/*  67:    */   {
/*  68:167 */     getWriter().startDocument();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void endDocument()
/*  72:    */     throws IOException, SAXException
/*  73:    */   {
/*  74:180 */     getWriter().endDocument();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void write(javax.xml.bind.Element jaxbObject)
/*  78:    */     throws IOException, JAXBException
/*  79:    */   {
/*  80:197 */     getWriter().write(marshal(jaxbObject));
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void writeClose(javax.xml.bind.Element jaxbObject)
/*  84:    */     throws IOException, JAXBException
/*  85:    */   {
/*  86:216 */     getWriter().writeClose(marshal(jaxbObject));
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void writeOpen(javax.xml.bind.Element jaxbObject)
/*  90:    */     throws IOException, JAXBException
/*  91:    */   {
/*  92:234 */     getWriter().writeOpen(marshal(jaxbObject));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void writeElement(org.dom4j.Element element)
/*  96:    */     throws IOException
/*  97:    */   {
/*  98:247 */     getWriter().write(element);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void writeCloseElement(org.dom4j.Element element)
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:261 */     getWriter().writeClose(element);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void writeOpenElement(org.dom4j.Element element)
/* 108:    */     throws IOException
/* 109:    */   {
/* 110:275 */     getWriter().writeOpen(element);
/* 111:    */   }
/* 112:    */   
/* 113:    */   private XMLWriter getWriter()
/* 114:    */     throws IOException
/* 115:    */   {
/* 116:279 */     if (this.xmlWriter == null) {
/* 117:280 */       if (this.outputFormat != null) {
/* 118:281 */         this.xmlWriter = new XMLWriter(this.outputFormat);
/* 119:    */       } else {
/* 120:283 */         this.xmlWriter = new XMLWriter();
/* 121:    */       }
/* 122:    */     }
/* 123:287 */     return this.xmlWriter;
/* 124:    */   }
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.jaxb.JAXBWriter
 * JD-Core Version:    0.7.0.1
 */