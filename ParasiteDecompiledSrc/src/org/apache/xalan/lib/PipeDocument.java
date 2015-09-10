/*   1:    */ package org.apache.xalan.lib;
/*   2:    */ 
/*   3:    */ import java.io.FileNotFoundException;
/*   4:    */ import java.io.FileOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Properties;
/*   7:    */ import java.util.Vector;
/*   8:    */ import javax.xml.transform.Templates;
/*   9:    */ import javax.xml.transform.Transformer;
/*  10:    */ import javax.xml.transform.TransformerConfigurationException;
/*  11:    */ import javax.xml.transform.TransformerException;
/*  12:    */ import javax.xml.transform.TransformerFactory;
/*  13:    */ import javax.xml.transform.sax.SAXResult;
/*  14:    */ import javax.xml.transform.sax.SAXTransformerFactory;
/*  15:    */ import javax.xml.transform.sax.TransformerHandler;
/*  16:    */ import javax.xml.transform.stream.StreamSource;
/*  17:    */ import org.apache.xalan.extensions.XSLProcessorContext;
/*  18:    */ import org.apache.xalan.templates.AVT;
/*  19:    */ import org.apache.xalan.templates.ElemExtensionCall;
/*  20:    */ import org.apache.xalan.templates.ElemLiteralResult;
/*  21:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*  22:    */ import org.apache.xalan.transformer.TransformerImpl;
/*  23:    */ import org.apache.xml.serializer.Serializer;
/*  24:    */ import org.apache.xml.serializer.SerializerFactory;
/*  25:    */ import org.apache.xml.utils.SystemIDResolver;
/*  26:    */ import org.apache.xpath.XPathContext;
/*  27:    */ import org.w3c.dom.Element;
/*  28:    */ import org.w3c.dom.Node;
/*  29:    */ import org.w3c.dom.NodeList;
/*  30:    */ import org.xml.sax.SAXException;
/*  31:    */ import org.xml.sax.SAXNotRecognizedException;
/*  32:    */ import org.xml.sax.XMLReader;
/*  33:    */ import org.xml.sax.helpers.XMLReaderFactory;
/*  34:    */ 
/*  35:    */ public class PipeDocument
/*  36:    */ {
/*  37:    */   public void pipeDocument(XSLProcessorContext context, ElemExtensionCall elem)
/*  38:    */     throws TransformerException, TransformerConfigurationException, SAXException, IOException, FileNotFoundException
/*  39:    */   {
/*  40:113 */     SAXTransformerFactory saxTFactory = (SAXTransformerFactory)TransformerFactory.newInstance();
/*  41:    */     
/*  42:    */ 
/*  43:116 */     String source = elem.getAttribute("source", context.getContextNode(), context.getTransformer());
/*  44:    */     
/*  45:    */ 
/*  46:119 */     TransformerImpl transImpl = context.getTransformer();
/*  47:    */     
/*  48:    */ 
/*  49:122 */     String baseURLOfSource = transImpl.getBaseURLOfSource();
/*  50:    */     
/*  51:124 */     String absSourceURL = SystemIDResolver.getAbsoluteURI(source, baseURLOfSource);
/*  52:    */     
/*  53:    */ 
/*  54:127 */     String target = elem.getAttribute("target", context.getContextNode(), context.getTransformer());
/*  55:    */     
/*  56:    */ 
/*  57:    */ 
/*  58:131 */     XPathContext xctxt = context.getTransformer().getXPathContext();
/*  59:132 */     int xt = xctxt.getDTMHandleFromNode(context.getContextNode());
/*  60:    */     
/*  61:    */ 
/*  62:135 */     String sysId = elem.getSystemId();
/*  63:    */     
/*  64:137 */     NodeList ssNodes = null;
/*  65:138 */     NodeList paramNodes = null;
/*  66:139 */     Node ssNode = null;
/*  67:140 */     Node paramNode = null;
/*  68:141 */     if (elem.hasChildNodes())
/*  69:    */     {
/*  70:143 */       ssNodes = elem.getChildNodes();
/*  71:    */       
/*  72:145 */       Vector vTHandler = new Vector(ssNodes.getLength());
/*  73:151 */       for (int i = 0; i < ssNodes.getLength(); i++)
/*  74:    */       {
/*  75:153 */         ssNode = ssNodes.item(i);
/*  76:154 */         if ((ssNode.getNodeType() == 1) && (((Element)ssNode).getTagName().equals("stylesheet")) && ((ssNode instanceof ElemLiteralResult)))
/*  77:    */         {
/*  78:158 */           AVT avt = ((ElemLiteralResult)ssNode).getLiteralResultAttribute("href");
/*  79:159 */           String href = avt.evaluate(xctxt, xt, elem);
/*  80:160 */           String absURI = SystemIDResolver.getAbsoluteURI(href, sysId);
/*  81:161 */           Templates tmpl = saxTFactory.newTemplates(new StreamSource(absURI));
/*  82:162 */           TransformerHandler tHandler = saxTFactory.newTransformerHandler(tmpl);
/*  83:163 */           Transformer trans = tHandler.getTransformer();
/*  84:    */           
/*  85:    */ 
/*  86:166 */           vTHandler.addElement(tHandler);
/*  87:    */           
/*  88:168 */           paramNodes = ssNode.getChildNodes();
/*  89:169 */           for (int j = 0; j < paramNodes.getLength(); j++)
/*  90:    */           {
/*  91:171 */             paramNode = paramNodes.item(j);
/*  92:172 */             if ((paramNode.getNodeType() == 1) && (((Element)paramNode).getTagName().equals("param")) && ((paramNode instanceof ElemLiteralResult)))
/*  93:    */             {
/*  94:176 */               avt = ((ElemLiteralResult)paramNode).getLiteralResultAttribute("name");
/*  95:177 */               String pName = avt.evaluate(xctxt, xt, elem);
/*  96:178 */               avt = ((ElemLiteralResult)paramNode).getLiteralResultAttribute("value");
/*  97:179 */               String pValue = avt.evaluate(xctxt, xt, elem);
/*  98:180 */               trans.setParameter(pName, pValue);
/*  99:    */             }
/* 100:    */           }
/* 101:    */         }
/* 102:    */       }
/* 103:185 */       usePipe(vTHandler, absSourceURL, target);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void usePipe(Vector vTHandler, String source, String target)
/* 108:    */     throws TransformerException, TransformerConfigurationException, FileNotFoundException, IOException, SAXException, SAXNotRecognizedException
/* 109:    */   {
/* 110:200 */     XMLReader reader = XMLReaderFactory.createXMLReader();
/* 111:201 */     TransformerHandler tHFirst = (TransformerHandler)vTHandler.firstElement();
/* 112:202 */     reader.setContentHandler(tHFirst);
/* 113:203 */     reader.setProperty("http://xml.org/sax/properties/lexical-handler", tHFirst);
/* 114:204 */     for (int i = 1; i < vTHandler.size(); i++)
/* 115:    */     {
/* 116:206 */       TransformerHandler tHFrom = (TransformerHandler)vTHandler.elementAt(i - 1);
/* 117:207 */       TransformerHandler tHTo = (TransformerHandler)vTHandler.elementAt(i);
/* 118:208 */       tHFrom.setResult(new SAXResult(tHTo));
/* 119:    */     }
/* 120:210 */     TransformerHandler tHLast = (TransformerHandler)vTHandler.lastElement();
/* 121:211 */     Transformer trans = tHLast.getTransformer();
/* 122:212 */     Properties outputProps = trans.getOutputProperties();
/* 123:213 */     Serializer serializer = SerializerFactory.getSerializer(outputProps);
/* 124:    */     
/* 125:215 */     FileOutputStream out = new FileOutputStream(target);
/* 126:    */     try
/* 127:    */     {
/* 128:218 */       serializer.setOutputStream(out);
/* 129:219 */       tHLast.setResult(new SAXResult(serializer.asContentHandler()));
/* 130:220 */       reader.parse(source);
/* 131:    */     }
/* 132:    */     finally
/* 133:    */     {
/* 134:226 */       if (out != null) {
/* 135:227 */         out.close();
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.PipeDocument
 * JD-Core Version:    0.7.0.1
 */