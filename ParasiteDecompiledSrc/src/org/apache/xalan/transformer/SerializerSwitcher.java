/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ import java.util.Properties;
/*   8:    */ import javax.xml.transform.TransformerException;
/*   9:    */ import org.apache.xalan.templates.OutputProperties;
/*  10:    */ import org.apache.xml.serializer.Serializer;
/*  11:    */ import org.apache.xml.serializer.SerializerFactory;
/*  12:    */ import org.xml.sax.ContentHandler;
/*  13:    */ 
/*  14:    */ public class SerializerSwitcher
/*  15:    */ {
/*  16:    */   public static void switchSerializerIfHTML(TransformerImpl transformer, String ns, String localName)
/*  17:    */     throws TransformerException
/*  18:    */   {
/*  19: 59 */     if (null == transformer) {
/*  20: 60 */       return;
/*  21:    */     }
/*  22: 62 */     if (((null == ns) || (ns.length() == 0)) && (localName.equalsIgnoreCase("html")))
/*  23:    */     {
/*  24: 68 */       if (null != transformer.getOutputPropertyNoDefault("method")) {
/*  25: 69 */         return;
/*  26:    */       }
/*  27: 73 */       Properties prevProperties = transformer.getOutputFormat().getProperties();
/*  28:    */       
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32: 78 */       OutputProperties htmlOutputProperties = new OutputProperties("html");
/*  33:    */       
/*  34: 80 */       htmlOutputProperties.copyFrom(prevProperties, true);
/*  35: 81 */       Properties htmlProperties = htmlOutputProperties.getProperties();
/*  36:    */       try
/*  37:    */       {
/*  38: 86 */         Serializer oldSerializer = null;
/*  39: 88 */         if (null != oldSerializer)
/*  40:    */         {
/*  41: 90 */           Serializer serializer = SerializerFactory.getSerializer(htmlProperties);
/*  42:    */           
/*  43:    */ 
/*  44: 93 */           Writer writer = oldSerializer.getWriter();
/*  45: 95 */           if (null != writer)
/*  46:    */           {
/*  47: 96 */             serializer.setWriter(writer);
/*  48:    */           }
/*  49:    */           else
/*  50:    */           {
/*  51: 99 */             OutputStream os = oldSerializer.getOutputStream();
/*  52:101 */             if (null != os) {
/*  53:102 */               serializer.setOutputStream(os);
/*  54:    */             }
/*  55:    */           }
/*  56:107 */           ContentHandler ch = serializer.asContentHandler();
/*  57:    */           
/*  58:109 */           transformer.setContentHandler(ch);
/*  59:    */         }
/*  60:    */       }
/*  61:    */       catch (IOException e)
/*  62:    */       {
/*  63:114 */         throw new TransformerException(e);
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   private static String getOutputPropertyNoDefault(String qnameString, Properties props)
/*  69:    */     throws IllegalArgumentException
/*  70:    */   {
/*  71:134 */     String value = (String)props.get(qnameString);
/*  72:    */     
/*  73:136 */     return value;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static Serializer switchSerializerIfHTML(String ns, String localName, Properties props, Serializer oldSerializer)
/*  77:    */     throws TransformerException
/*  78:    */   {
/*  79:153 */     Serializer newSerializer = oldSerializer;
/*  80:155 */     if (((null == ns) || (ns.length() == 0)) && (localName.equalsIgnoreCase("html")))
/*  81:    */     {
/*  82:161 */       if (null != getOutputPropertyNoDefault("method", props)) {
/*  83:162 */         return newSerializer;
/*  84:    */       }
/*  85:166 */       Properties prevProperties = props;
/*  86:    */       
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:171 */       OutputProperties htmlOutputProperties = new OutputProperties("html");
/*  91:    */       
/*  92:173 */       htmlOutputProperties.copyFrom(prevProperties, true);
/*  93:174 */       Properties htmlProperties = htmlOutputProperties.getProperties();
/*  94:178 */       if (null != oldSerializer)
/*  95:    */       {
/*  96:180 */         Serializer serializer = SerializerFactory.getSerializer(htmlProperties);
/*  97:    */         
/*  98:    */ 
/*  99:183 */         Writer writer = oldSerializer.getWriter();
/* 100:185 */         if (null != writer)
/* 101:    */         {
/* 102:186 */           serializer.setWriter(writer);
/* 103:    */         }
/* 104:    */         else
/* 105:    */         {
/* 106:189 */           OutputStream os = serializer.getOutputStream();
/* 107:191 */           if (null != os) {
/* 108:192 */             serializer.setOutputStream(os);
/* 109:    */           }
/* 110:    */         }
/* 111:194 */         newSerializer = serializer;
/* 112:    */       }
/* 113:    */     }
/* 114:202 */     return newSerializer;
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.SerializerSwitcher
 * JD-Core Version:    0.7.0.1
 */