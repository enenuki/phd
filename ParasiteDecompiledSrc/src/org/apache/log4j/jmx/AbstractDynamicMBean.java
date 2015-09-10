/*   1:    */ package org.apache.log4j.jmx;
/*   2:    */ 
/*   3:    */ import java.util.AbstractList;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Enumeration;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Vector;
/*   8:    */ import javax.management.Attribute;
/*   9:    */ import javax.management.AttributeList;
/*  10:    */ import javax.management.AttributeNotFoundException;
/*  11:    */ import javax.management.DynamicMBean;
/*  12:    */ import javax.management.InstanceAlreadyExistsException;
/*  13:    */ import javax.management.InstanceNotFoundException;
/*  14:    */ import javax.management.InvalidAttributeValueException;
/*  15:    */ import javax.management.JMException;
/*  16:    */ import javax.management.MBeanException;
/*  17:    */ import javax.management.MBeanInfo;
/*  18:    */ import javax.management.MBeanRegistration;
/*  19:    */ import javax.management.MBeanRegistrationException;
/*  20:    */ import javax.management.MBeanServer;
/*  21:    */ import javax.management.NotCompliantMBeanException;
/*  22:    */ import javax.management.ObjectName;
/*  23:    */ import javax.management.ReflectionException;
/*  24:    */ import javax.management.RuntimeOperationsException;
/*  25:    */ import org.apache.log4j.Appender;
/*  26:    */ import org.apache.log4j.Category;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ 
/*  29:    */ public abstract class AbstractDynamicMBean
/*  30:    */   implements DynamicMBean, MBeanRegistration
/*  31:    */ {
/*  32:    */   String dClassName;
/*  33:    */   MBeanServer server;
/*  34: 45 */   private final Vector mbeanList = new Vector();
/*  35:    */   
/*  36:    */   protected static String getAppenderName(Appender appender)
/*  37:    */   {
/*  38: 54 */     String name = appender.getName();
/*  39: 55 */     if ((name == null) || (name.trim().length() == 0)) {
/*  40: 57 */       name = appender.toString();
/*  41:    */     }
/*  42: 59 */     return name;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public AttributeList getAttributes(String[] attributeNames)
/*  46:    */   {
/*  47: 70 */     if (attributeNames == null) {
/*  48: 71 */       throw new RuntimeOperationsException(new IllegalArgumentException("attributeNames[] cannot be null"), "Cannot invoke a getter of " + this.dClassName);
/*  49:    */     }
/*  50: 76 */     AttributeList resultList = new AttributeList();
/*  51: 79 */     if (attributeNames.length == 0) {
/*  52: 80 */       return resultList;
/*  53:    */     }
/*  54: 83 */     for (int i = 0; i < attributeNames.length; i++) {
/*  55:    */       try
/*  56:    */       {
/*  57: 85 */         Object value = getAttribute(attributeNames[i]);
/*  58: 86 */         resultList.add(new Attribute(attributeNames[i], value));
/*  59:    */       }
/*  60:    */       catch (JMException e)
/*  61:    */       {
/*  62: 88 */         e.printStackTrace();
/*  63:    */       }
/*  64:    */       catch (RuntimeException e)
/*  65:    */       {
/*  66: 90 */         e.printStackTrace();
/*  67:    */       }
/*  68:    */     }
/*  69: 93 */     return resultList;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public AttributeList setAttributes(AttributeList attributes)
/*  73:    */   {
/*  74:103 */     if (attributes == null) {
/*  75:104 */       throw new RuntimeOperationsException(new IllegalArgumentException("AttributeList attributes cannot be null"), "Cannot invoke a setter of " + this.dClassName);
/*  76:    */     }
/*  77:108 */     AttributeList resultList = new AttributeList();
/*  78:111 */     if (attributes.isEmpty()) {
/*  79:112 */       return resultList;
/*  80:    */     }
/*  81:115 */     for (Iterator i = attributes.iterator(); i.hasNext();)
/*  82:    */     {
/*  83:116 */       Attribute attr = (Attribute)i.next();
/*  84:    */       try
/*  85:    */       {
/*  86:118 */         setAttribute(attr);
/*  87:119 */         String name = attr.getName();
/*  88:120 */         Object value = getAttribute(name);
/*  89:121 */         resultList.add(new Attribute(name, value));
/*  90:    */       }
/*  91:    */       catch (JMException e)
/*  92:    */       {
/*  93:123 */         e.printStackTrace();
/*  94:    */       }
/*  95:    */       catch (RuntimeException e)
/*  96:    */       {
/*  97:125 */         e.printStackTrace();
/*  98:    */       }
/*  99:    */     }
/* 100:128 */     return resultList;
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected abstract Logger getLogger();
/* 104:    */   
/* 105:    */   public void postDeregister()
/* 106:    */   {
/* 107:137 */     getLogger().debug("postDeregister is called.");
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void postRegister(Boolean registrationDone) {}
/* 111:    */   
/* 112:    */   public ObjectName preRegister(MBeanServer server, ObjectName name)
/* 113:    */   {
/* 114:148 */     getLogger().debug("preRegister called. Server=" + server + ", name=" + name);
/* 115:149 */     this.server = server;
/* 116:150 */     return name;
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected void registerMBean(Object mbean, ObjectName objectName)
/* 120:    */     throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
/* 121:    */   {
/* 122:160 */     this.server.registerMBean(mbean, objectName);
/* 123:161 */     this.mbeanList.add(objectName);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void preDeregister()
/* 127:    */   {
/* 128:171 */     getLogger().debug("preDeregister called.");
/* 129:    */     
/* 130:173 */     Enumeration iterator = this.mbeanList.elements();
/* 131:174 */     while (iterator.hasMoreElements())
/* 132:    */     {
/* 133:175 */       ObjectName name = (ObjectName)iterator.nextElement();
/* 134:    */       try
/* 135:    */       {
/* 136:177 */         this.server.unregisterMBean(name);
/* 137:    */       }
/* 138:    */       catch (InstanceNotFoundException e)
/* 139:    */       {
/* 140:179 */         getLogger().warn("Missing MBean " + name.getCanonicalName());
/* 141:    */       }
/* 142:    */       catch (MBeanRegistrationException e)
/* 143:    */       {
/* 144:181 */         getLogger().warn("Failed unregistering " + name.getCanonicalName());
/* 145:    */       }
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public abstract MBeanInfo getMBeanInfo();
/* 150:    */   
/* 151:    */   public abstract Object invoke(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString)
/* 152:    */     throws MBeanException, ReflectionException;
/* 153:    */   
/* 154:    */   public abstract void setAttribute(Attribute paramAttribute)
/* 155:    */     throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException;
/* 156:    */   
/* 157:    */   public abstract Object getAttribute(String paramString)
/* 158:    */     throws AttributeNotFoundException, MBeanException, ReflectionException;
/* 159:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.jmx.AbstractDynamicMBean
 * JD-Core Version:    0.7.0.1
 */