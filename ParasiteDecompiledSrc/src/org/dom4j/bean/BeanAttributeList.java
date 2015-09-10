/*   1:    */ package org.dom4j.bean;
/*   2:    */ 
/*   3:    */ import java.util.AbstractList;
/*   4:    */ import org.dom4j.Attribute;
/*   5:    */ import org.dom4j.QName;
/*   6:    */ 
/*   7:    */ public class BeanAttributeList
/*   8:    */   extends AbstractList
/*   9:    */ {
/*  10:    */   private BeanElement parent;
/*  11:    */   private BeanMetaData beanMetaData;
/*  12:    */   private BeanAttribute[] attributes;
/*  13:    */   
/*  14:    */   public BeanAttributeList(BeanElement parent, BeanMetaData beanMetaData)
/*  15:    */   {
/*  16: 35 */     this.parent = parent;
/*  17: 36 */     this.beanMetaData = beanMetaData;
/*  18: 37 */     this.attributes = new BeanAttribute[beanMetaData.attributeCount()];
/*  19:    */   }
/*  20:    */   
/*  21:    */   public BeanAttributeList(BeanElement parent)
/*  22:    */   {
/*  23: 41 */     this.parent = parent;
/*  24:    */     
/*  25: 43 */     Object data = parent.getData();
/*  26: 44 */     Class beanClass = data != null ? data.getClass() : null;
/*  27: 45 */     this.beanMetaData = BeanMetaData.get(beanClass);
/*  28: 46 */     this.attributes = new BeanAttribute[this.beanMetaData.attributeCount()];
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Attribute attribute(String name)
/*  32:    */   {
/*  33: 50 */     int index = this.beanMetaData.getIndex(name);
/*  34:    */     
/*  35: 52 */     return attribute(index);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Attribute attribute(QName qname)
/*  39:    */   {
/*  40: 56 */     int index = this.beanMetaData.getIndex(qname);
/*  41:    */     
/*  42: 58 */     return attribute(index);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public BeanAttribute attribute(int index)
/*  46:    */   {
/*  47: 62 */     if ((index >= 0) && (index <= this.attributes.length))
/*  48:    */     {
/*  49: 63 */       BeanAttribute attribute = this.attributes[index];
/*  50: 65 */       if (attribute == null)
/*  51:    */       {
/*  52: 66 */         attribute = createAttribute(this.parent, index);
/*  53: 67 */         this.attributes[index] = attribute;
/*  54:    */       }
/*  55: 70 */       return attribute;
/*  56:    */     }
/*  57: 73 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public BeanElement getParent()
/*  61:    */   {
/*  62: 77 */     return this.parent;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public QName getQName(int index)
/*  66:    */   {
/*  67: 81 */     return this.beanMetaData.getQName(index);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Object getData(int index)
/*  71:    */   {
/*  72: 85 */     return this.beanMetaData.getData(index, this.parent.getData());
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setData(int index, Object data)
/*  76:    */   {
/*  77: 89 */     this.beanMetaData.setData(index, this.parent.getData(), data);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int size()
/*  81:    */   {
/*  82: 95 */     return this.attributes.length;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Object get(int index)
/*  86:    */   {
/*  87: 99 */     BeanAttribute attribute = this.attributes[index];
/*  88:101 */     if (attribute == null)
/*  89:    */     {
/*  90:102 */       attribute = createAttribute(this.parent, index);
/*  91:103 */       this.attributes[index] = attribute;
/*  92:    */     }
/*  93:106 */     return attribute;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean add(Object object)
/*  97:    */   {
/*  98:110 */     throw new UnsupportedOperationException("add(Object) unsupported");
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void add(int index, Object object)
/* 102:    */   {
/* 103:114 */     throw new UnsupportedOperationException("add(int,Object) unsupported");
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Object set(int index, Object object)
/* 107:    */   {
/* 108:118 */     throw new UnsupportedOperationException("set(int,Object) unsupported");
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean remove(Object object)
/* 112:    */   {
/* 113:122 */     return false;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Object remove(int index)
/* 117:    */   {
/* 118:126 */     BeanAttribute attribute = (BeanAttribute)get(index);
/* 119:127 */     Object oldValue = attribute.getValue();
/* 120:128 */     attribute.setValue(null);
/* 121:    */     
/* 122:130 */     return oldValue;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void clear()
/* 126:    */   {
/* 127:134 */     int i = 0;
/* 128:134 */     for (int size = this.attributes.length; i < size; i++)
/* 129:    */     {
/* 130:135 */       BeanAttribute attribute = this.attributes[i];
/* 131:137 */       if (attribute != null) {
/* 132:138 */         attribute.setValue(null);
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected BeanAttribute createAttribute(BeanElement element, int index)
/* 138:    */   {
/* 139:146 */     return new BeanAttribute(this, index);
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.bean.BeanAttributeList
 * JD-Core Version:    0.7.0.1
 */