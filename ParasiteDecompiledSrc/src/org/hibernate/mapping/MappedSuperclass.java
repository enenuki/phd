/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class MappedSuperclass
/*   8:    */ {
/*   9:    */   private final MappedSuperclass superMappedSuperclass;
/*  10:    */   private final PersistentClass superPersistentClass;
/*  11:    */   private final List declaredProperties;
/*  12:    */   private Class mappedClass;
/*  13:    */   private Property identifierProperty;
/*  14:    */   private Property version;
/*  15:    */   private Component identifierMapper;
/*  16:    */   
/*  17:    */   public MappedSuperclass(MappedSuperclass superMappedSuperclass, PersistentClass superPersistentClass)
/*  18:    */   {
/*  19: 56 */     this.superMappedSuperclass = superMappedSuperclass;
/*  20: 57 */     this.superPersistentClass = superPersistentClass;
/*  21: 58 */     this.declaredProperties = new ArrayList();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public MappedSuperclass getSuperMappedSuperclass()
/*  25:    */   {
/*  26: 69 */     return this.superMappedSuperclass;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean hasIdentifierProperty()
/*  30:    */   {
/*  31: 73 */     return getIdentifierProperty() != null;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean isVersioned()
/*  35:    */   {
/*  36: 77 */     return getVersion() != null;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public PersistentClass getSuperPersistentClass()
/*  40:    */   {
/*  41: 87 */     return this.superPersistentClass;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Iterator getDeclaredPropertyIterator()
/*  45:    */   {
/*  46: 91 */     return this.declaredProperties.iterator();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void addDeclaredProperty(Property p)
/*  50:    */   {
/*  51: 97 */     String name = p.getName();
/*  52: 98 */     Iterator it = this.declaredProperties.iterator();
/*  53: 99 */     while (it.hasNext()) {
/*  54:100 */       if (name.equals(((Property)it.next()).getName())) {
/*  55:101 */         return;
/*  56:    */       }
/*  57:    */     }
/*  58:104 */     this.declaredProperties.add(p);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Class getMappedClass()
/*  62:    */   {
/*  63:108 */     return this.mappedClass;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setMappedClass(Class mappedClass)
/*  67:    */   {
/*  68:112 */     this.mappedClass = mappedClass;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Property getIdentifierProperty()
/*  72:    */   {
/*  73:118 */     Property propagatedIdentifierProp = this.identifierProperty;
/*  74:119 */     if (propagatedIdentifierProp == null)
/*  75:    */     {
/*  76:120 */       if (this.superMappedSuperclass != null) {
/*  77:121 */         propagatedIdentifierProp = this.superMappedSuperclass.getIdentifierProperty();
/*  78:    */       }
/*  79:123 */       if ((propagatedIdentifierProp == null) && (this.superPersistentClass != null)) {
/*  80:124 */         propagatedIdentifierProp = this.superPersistentClass.getIdentifierProperty();
/*  81:    */       }
/*  82:    */     }
/*  83:127 */     return propagatedIdentifierProp;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Property getDeclaredIdentifierProperty()
/*  87:    */   {
/*  88:131 */     return this.identifierProperty;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setDeclaredIdentifierProperty(Property prop)
/*  92:    */   {
/*  93:135 */     this.identifierProperty = prop;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Property getVersion()
/*  97:    */   {
/*  98:141 */     Property propagatedVersion = this.version;
/*  99:142 */     if (propagatedVersion == null)
/* 100:    */     {
/* 101:143 */       if (this.superMappedSuperclass != null) {
/* 102:144 */         propagatedVersion = this.superMappedSuperclass.getVersion();
/* 103:    */       }
/* 104:146 */       if ((propagatedVersion == null) && (this.superPersistentClass != null)) {
/* 105:147 */         propagatedVersion = this.superPersistentClass.getVersion();
/* 106:    */       }
/* 107:    */     }
/* 108:150 */     return propagatedVersion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Property getDeclaredVersion()
/* 112:    */   {
/* 113:154 */     return this.version;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setDeclaredVersion(Property prop)
/* 117:    */   {
/* 118:158 */     this.version = prop;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Component getIdentifierMapper()
/* 122:    */   {
/* 123:164 */     Component propagatedMapper = this.identifierMapper;
/* 124:165 */     if (propagatedMapper == null)
/* 125:    */     {
/* 126:166 */       if (this.superMappedSuperclass != null) {
/* 127:167 */         propagatedMapper = this.superMappedSuperclass.getIdentifierMapper();
/* 128:    */       }
/* 129:169 */       if ((propagatedMapper == null) && (this.superPersistentClass != null)) {
/* 130:170 */         propagatedMapper = this.superPersistentClass.getIdentifierMapper();
/* 131:    */       }
/* 132:    */     }
/* 133:173 */     return propagatedMapper;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Component getDeclaredIdentifierMapper()
/* 137:    */   {
/* 138:177 */     return this.identifierMapper;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDeclaredIdentifierMapper(Component identifierMapper)
/* 142:    */   {
/* 143:181 */     this.identifierMapper = identifierMapper;
/* 144:    */   }
/* 145:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.MappedSuperclass
 * JD-Core Version:    0.7.0.1
 */