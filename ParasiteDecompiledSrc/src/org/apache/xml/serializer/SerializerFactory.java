/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Properties;
/*   5:    */ import org.apache.xml.serializer.utils.Messages;
/*   6:    */ import org.apache.xml.serializer.utils.Utils;
/*   7:    */ import org.apache.xml.serializer.utils.WrappedRuntimeException;
/*   8:    */ import org.xml.sax.ContentHandler;
/*   9:    */ 
/*  10:    */ public final class SerializerFactory
/*  11:    */ {
/*  12: 73 */   private static Hashtable m_formats = new Hashtable();
/*  13:    */   
/*  14:    */   public static Serializer getSerializer(Properties format)
/*  15:    */   {
/*  16:    */     Serializer ser;
/*  17:    */     try
/*  18:    */     {
/*  19: 96 */       String method = format.getProperty("method");
/*  20: 98 */       if (method == null)
/*  21:    */       {
/*  22: 99 */         String msg = Utils.messages.createMessage("ER_FACTORY_PROPERTY_MISSING", new Object[] { "method" });
/*  23:    */         
/*  24:    */ 
/*  25:102 */         throw new IllegalArgumentException(msg);
/*  26:    */       }
/*  27:105 */       String className = format.getProperty("{http://xml.apache.org/xalan}content-handler");
/*  28:109 */       if (null == className)
/*  29:    */       {
/*  30:112 */         Properties methodDefaults = OutputPropertiesFactory.getDefaultMethodProperties(method);
/*  31:    */         
/*  32:114 */         className = methodDefaults.getProperty("{http://xml.apache.org/xalan}content-handler");
/*  33:116 */         if (null == className)
/*  34:    */         {
/*  35:117 */           String msg = Utils.messages.createMessage("ER_FACTORY_PROPERTY_MISSING", new Object[] { "{http://xml.apache.org/xalan}content-handler" });
/*  36:    */           
/*  37:    */ 
/*  38:120 */           throw new IllegalArgumentException(msg);
/*  39:    */         }
/*  40:    */       }
/*  41:127 */       ClassLoader loader = ObjectFactory.findClassLoader();
/*  42:    */       
/*  43:129 */       Class cls = ObjectFactory.findProviderClass(className, loader, true);
/*  44:    */       
/*  45:    */ 
/*  46:    */ 
/*  47:133 */       Object obj = cls.newInstance();
/*  48:135 */       if ((obj instanceof SerializationHandler))
/*  49:    */       {
/*  50:138 */         ser = (Serializer)cls.newInstance();
/*  51:139 */         ser.setOutputFormat(format);
/*  52:    */       }
/*  53:147 */       else if ((obj instanceof ContentHandler))
/*  54:    */       {
/*  55:156 */         className = SerializerConstants.DEFAULT_SAX_SERIALIZER;
/*  56:157 */         cls = ObjectFactory.findProviderClass(className, loader, true);
/*  57:158 */         SerializationHandler sh = (SerializationHandler)cls.newInstance();
/*  58:    */         
/*  59:160 */         sh.setContentHandler((ContentHandler)obj);
/*  60:161 */         sh.setOutputFormat(format);
/*  61:    */         
/*  62:163 */         ser = sh;
/*  63:    */       }
/*  64:    */       else
/*  65:    */       {
/*  66:169 */         throw new Exception(Utils.messages.createMessage("ER_SERIALIZER_NOT_CONTENTHANDLER", new Object[] { className }));
/*  67:    */       }
/*  68:    */     }
/*  69:    */     catch (Exception e)
/*  70:    */     {
/*  71:179 */       throw new WrappedRuntimeException(e);
/*  72:    */     }
/*  73:183 */     return ser;
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.SerializerFactory
 * JD-Core Version:    0.7.0.1
 */