/*   1:    */ package org.dom4j;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import org.dom4j.tree.QNameCache;
/*   8:    */ import org.dom4j.util.SingletonStrategy;
/*   9:    */ 
/*  10:    */ public class QName
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13: 29 */   private static SingletonStrategy singleton = null;
/*  14:    */   private String name;
/*  15:    */   private String qualifiedName;
/*  16:    */   private transient Namespace namespace;
/*  17:    */   private int hashCode;
/*  18:    */   private DocumentFactory documentFactory;
/*  19:    */   
/*  20:    */   static
/*  21:    */   {
/*  22:    */     try
/*  23:    */     {
/*  24: 33 */       String defaultSingletonClass = "org.dom4j.util.SimpleSingleton";
/*  25: 34 */       Class clazz = null;
/*  26:    */       try
/*  27:    */       {
/*  28: 36 */         String singletonClass = defaultSingletonClass;
/*  29: 37 */         singletonClass = System.getProperty("org.dom4j.QName.singleton.strategy", singletonClass);
/*  30:    */         
/*  31: 39 */         clazz = Class.forName(singletonClass);
/*  32:    */       }
/*  33:    */       catch (Exception exc1)
/*  34:    */       {
/*  35:    */         try
/*  36:    */         {
/*  37: 42 */           String singletonClass = defaultSingletonClass;
/*  38: 43 */           clazz = Class.forName(singletonClass);
/*  39:    */         }
/*  40:    */         catch (Exception exc2) {}
/*  41:    */       }
/*  42: 47 */       singleton = (SingletonStrategy)clazz.newInstance();
/*  43: 48 */       singleton.setSingletonClassName(QNameCache.class.getName());
/*  44:    */     }
/*  45:    */     catch (Exception exc3) {}
/*  46:    */   }
/*  47:    */   
/*  48:    */   public QName(String name)
/*  49:    */   {
/*  50: 69 */     this(name, Namespace.NO_NAMESPACE);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public QName(String name, Namespace namespace)
/*  54:    */   {
/*  55: 73 */     this.name = (name == null ? "" : name);
/*  56: 74 */     this.namespace = (namespace == null ? Namespace.NO_NAMESPACE : namespace);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public QName(String name, Namespace namespace, String qualifiedName)
/*  60:    */   {
/*  61: 79 */     this.name = (name == null ? "" : name);
/*  62: 80 */     this.qualifiedName = qualifiedName;
/*  63: 81 */     this.namespace = (namespace == null ? Namespace.NO_NAMESPACE : namespace);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static QName get(String name)
/*  67:    */   {
/*  68: 86 */     return getCache().get(name);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static QName get(String name, Namespace namespace)
/*  72:    */   {
/*  73: 90 */     return getCache().get(name, namespace);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static QName get(String name, String prefix, String uri)
/*  77:    */   {
/*  78: 94 */     if (((prefix == null) || (prefix.length() == 0)) && (uri == null)) {
/*  79: 95 */       return get(name);
/*  80:    */     }
/*  81: 96 */     if ((prefix == null) || (prefix.length() == 0)) {
/*  82: 97 */       return getCache().get(name, Namespace.get(uri));
/*  83:    */     }
/*  84: 98 */     if (uri == null) {
/*  85: 99 */       return get(name);
/*  86:    */     }
/*  87:101 */     return getCache().get(name, Namespace.get(prefix, uri));
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static QName get(String qualifiedName, String uri)
/*  91:    */   {
/*  92:106 */     if (uri == null) {
/*  93:107 */       return getCache().get(qualifiedName);
/*  94:    */     }
/*  95:109 */     return getCache().get(qualifiedName, uri);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static QName get(String localName, Namespace namespace, String qualifiedName)
/*  99:    */   {
/* 100:115 */     return getCache().get(localName, namespace, qualifiedName);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getName()
/* 104:    */   {
/* 105:124 */     return this.name;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getQualifiedName()
/* 109:    */   {
/* 110:133 */     if (this.qualifiedName == null)
/* 111:    */     {
/* 112:134 */       String prefix = getNamespacePrefix();
/* 113:136 */       if ((prefix != null) && (prefix.length() > 0)) {
/* 114:137 */         this.qualifiedName = (prefix + ":" + this.name);
/* 115:    */       } else {
/* 116:139 */         this.qualifiedName = this.name;
/* 117:    */       }
/* 118:    */     }
/* 119:143 */     return this.qualifiedName;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Namespace getNamespace()
/* 123:    */   {
/* 124:152 */     return this.namespace;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getNamespacePrefix()
/* 128:    */   {
/* 129:161 */     if (this.namespace == null) {
/* 130:162 */       return "";
/* 131:    */     }
/* 132:165 */     return this.namespace.getPrefix();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getNamespaceURI()
/* 136:    */   {
/* 137:174 */     if (this.namespace == null) {
/* 138:175 */       return "";
/* 139:    */     }
/* 140:178 */     return this.namespace.getURI();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int hashCode()
/* 144:    */   {
/* 145:188 */     if (this.hashCode == 0)
/* 146:    */     {
/* 147:189 */       this.hashCode = (getName().hashCode() ^ getNamespaceURI().hashCode());
/* 148:191 */       if (this.hashCode == 0) {
/* 149:192 */         this.hashCode = 47806;
/* 150:    */       }
/* 151:    */     }
/* 152:196 */     return this.hashCode;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean equals(Object object)
/* 156:    */   {
/* 157:200 */     if (this == object) {
/* 158:201 */       return true;
/* 159:    */     }
/* 160:202 */     if ((object instanceof QName))
/* 161:    */     {
/* 162:203 */       QName that = (QName)object;
/* 163:206 */       if (hashCode() == that.hashCode()) {
/* 164:207 */         return (getName().equals(that.getName())) && (getNamespaceURI().equals(that.getNamespaceURI()));
/* 165:    */       }
/* 166:    */     }
/* 167:212 */     return false;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String toString()
/* 171:    */   {
/* 172:216 */     return super.toString() + " [name: " + getName() + " namespace: \"" + getNamespace() + "\"]";
/* 173:    */   }
/* 174:    */   
/* 175:    */   public DocumentFactory getDocumentFactory()
/* 176:    */   {
/* 177:226 */     return this.documentFactory;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setDocumentFactory(DocumentFactory documentFactory)
/* 181:    */   {
/* 182:230 */     this.documentFactory = documentFactory;
/* 183:    */   }
/* 184:    */   
/* 185:    */   private void writeObject(ObjectOutputStream out)
/* 186:    */     throws IOException
/* 187:    */   {
/* 188:236 */     out.writeObject(this.namespace.getPrefix());
/* 189:237 */     out.writeObject(this.namespace.getURI());
/* 190:    */     
/* 191:239 */     out.defaultWriteObject();
/* 192:    */   }
/* 193:    */   
/* 194:    */   private void readObject(ObjectInputStream in)
/* 195:    */     throws IOException, ClassNotFoundException
/* 196:    */   {
/* 197:244 */     String prefix = (String)in.readObject();
/* 198:245 */     String uri = (String)in.readObject();
/* 199:    */     
/* 200:247 */     in.defaultReadObject();
/* 201:    */     
/* 202:249 */     this.namespace = Namespace.get(prefix, uri);
/* 203:    */   }
/* 204:    */   
/* 205:    */   private static QNameCache getCache()
/* 206:    */   {
/* 207:253 */     QNameCache cache = (QNameCache)singleton.instance();
/* 208:254 */     return cache;
/* 209:    */   }
/* 210:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.QName
 * JD-Core Version:    0.7.0.1
 */