/*   1:    */ package org.dom4j.bean;
/*   2:    */ 
/*   3:    */ import java.beans.BeanInfo;
/*   4:    */ import java.beans.IntrospectionException;
/*   5:    */ import java.beans.Introspector;
/*   6:    */ import java.beans.PropertyDescriptor;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.dom4j.DocumentFactory;
/*  11:    */ import org.dom4j.QName;
/*  12:    */ 
/*  13:    */ public class BeanMetaData
/*  14:    */ {
/*  15: 31 */   protected static final Object[] NULL_ARGS = new Object[0];
/*  16: 34 */   private static Map singletonCache = new HashMap();
/*  17: 36 */   private static final DocumentFactory DOCUMENT_FACTORY = BeanDocumentFactory.getInstance();
/*  18:    */   private Class beanClass;
/*  19:    */   private PropertyDescriptor[] propertyDescriptors;
/*  20:    */   private QName[] qNames;
/*  21:    */   private Method[] readMethods;
/*  22:    */   private Method[] writeMethods;
/*  23: 55 */   private Map nameMap = new HashMap();
/*  24:    */   
/*  25:    */   public BeanMetaData(Class beanClass)
/*  26:    */   {
/*  27: 58 */     this.beanClass = beanClass;
/*  28: 60 */     if (beanClass != null) {
/*  29:    */       try
/*  30:    */       {
/*  31: 62 */         BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
/*  32: 63 */         this.propertyDescriptors = beanInfo.getPropertyDescriptors();
/*  33:    */       }
/*  34:    */       catch (IntrospectionException e)
/*  35:    */       {
/*  36: 65 */         handleException(e);
/*  37:    */       }
/*  38:    */     }
/*  39: 69 */     if (this.propertyDescriptors == null) {
/*  40: 70 */       this.propertyDescriptors = new PropertyDescriptor[0];
/*  41:    */     }
/*  42: 73 */     int size = this.propertyDescriptors.length;
/*  43: 74 */     this.qNames = new QName[size];
/*  44: 75 */     this.readMethods = new Method[size];
/*  45: 76 */     this.writeMethods = new Method[size];
/*  46: 78 */     for (int i = 0; i < size; i++)
/*  47:    */     {
/*  48: 79 */       PropertyDescriptor propertyDescriptor = this.propertyDescriptors[i];
/*  49: 80 */       String name = propertyDescriptor.getName();
/*  50: 81 */       QName qName = DOCUMENT_FACTORY.createQName(name);
/*  51: 82 */       this.qNames[i] = qName;
/*  52: 83 */       this.readMethods[i] = propertyDescriptor.getReadMethod();
/*  53: 84 */       this.writeMethods[i] = propertyDescriptor.getWriteMethod();
/*  54:    */       
/*  55: 86 */       Integer index = new Integer(i);
/*  56: 87 */       this.nameMap.put(name, index);
/*  57: 88 */       this.nameMap.put(qName, index);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static BeanMetaData get(Class beanClass)
/*  62:    */   {
/*  63:101 */     BeanMetaData answer = (BeanMetaData)singletonCache.get(beanClass);
/*  64:103 */     if (answer == null)
/*  65:    */     {
/*  66:104 */       answer = new BeanMetaData(beanClass);
/*  67:105 */       singletonCache.put(beanClass, answer);
/*  68:    */     }
/*  69:108 */     return answer;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int attributeCount()
/*  73:    */   {
/*  74:117 */     return this.propertyDescriptors.length;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public BeanAttributeList createAttributeList(BeanElement parent)
/*  78:    */   {
/*  79:121 */     return new BeanAttributeList(parent, this);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public QName getQName(int index)
/*  83:    */   {
/*  84:125 */     return this.qNames[index];
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIndex(String name)
/*  88:    */   {
/*  89:129 */     Integer index = (Integer)this.nameMap.get(name);
/*  90:    */     
/*  91:131 */     return index != null ? index.intValue() : -1;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIndex(QName qName)
/*  95:    */   {
/*  96:135 */     Integer index = (Integer)this.nameMap.get(qName);
/*  97:    */     
/*  98:137 */     return index != null ? index.intValue() : -1;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Object getData(int index, Object bean)
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:142 */       Method method = this.readMethods[index];
/* 106:    */       
/* 107:144 */       return method.invoke(bean, NULL_ARGS);
/* 108:    */     }
/* 109:    */     catch (Exception e)
/* 110:    */     {
/* 111:146 */       handleException(e);
/* 112:    */     }
/* 113:148 */     return null;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setData(int index, Object bean, Object data)
/* 117:    */   {
/* 118:    */     try
/* 119:    */     {
/* 120:154 */       Method method = this.writeMethods[index];
/* 121:155 */       Object[] args = { data };
/* 122:156 */       method.invoke(bean, args);
/* 123:    */     }
/* 124:    */     catch (Exception e)
/* 125:    */     {
/* 126:158 */       handleException(e);
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected void handleException(Exception e) {}
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.bean.BeanMetaData
 * JD-Core Version:    0.7.0.1
 */