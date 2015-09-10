/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.AnnotationException;
/*   7:    */ import org.hibernate.AssertionFailure;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.mapping.Column;
/*  10:    */ import org.hibernate.mapping.Component;
/*  11:    */ import org.hibernate.mapping.PersistentClass;
/*  12:    */ import org.hibernate.mapping.Property;
/*  13:    */ import org.hibernate.mapping.SimpleValue;
/*  14:    */ 
/*  15:    */ public class CopyIdentifierComponentSecondPass
/*  16:    */   implements SecondPass
/*  17:    */ {
/*  18:    */   private final String referencedEntityName;
/*  19:    */   private final Component component;
/*  20:    */   private final Mappings mappings;
/*  21:    */   private final Ejb3JoinColumn[] joinColumns;
/*  22:    */   
/*  23:    */   public CopyIdentifierComponentSecondPass(Component comp, String referencedEntityName, Ejb3JoinColumn[] joinColumns, Mappings mappings)
/*  24:    */   {
/*  25: 52 */     this.component = comp;
/*  26: 53 */     this.referencedEntityName = referencedEntityName;
/*  27: 54 */     this.mappings = mappings;
/*  28: 55 */     this.joinColumns = joinColumns;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void doSecondPass(Map persistentClasses)
/*  32:    */     throws MappingException
/*  33:    */   {
/*  34: 60 */     PersistentClass referencedPersistentClass = (PersistentClass)persistentClasses.get(this.referencedEntityName);
/*  35: 62 */     if (referencedPersistentClass == null) {
/*  36: 63 */       throw new AnnotationException("Unknown entity name: " + this.referencedEntityName);
/*  37:    */     }
/*  38: 65 */     if (!(referencedPersistentClass.getIdentifier() instanceof Component)) {
/*  39: 66 */       throw new AssertionFailure("Unexpected identifier type on the referenced entity when mapping a @MapsId: " + this.referencedEntityName);
/*  40:    */     }
/*  41: 71 */     Component referencedComponent = (Component)referencedPersistentClass.getIdentifier();
/*  42: 72 */     Iterator<Property> properties = referencedComponent.getPropertyIterator();
/*  43:    */     
/*  44:    */ 
/*  45:    */ 
/*  46: 76 */     boolean isExplicitReference = true;
/*  47: 77 */     Map<String, Ejb3JoinColumn> columnByReferencedName = new HashMap(this.joinColumns.length);
/*  48: 78 */     for (Ejb3JoinColumn joinColumn : this.joinColumns)
/*  49:    */     {
/*  50: 79 */       String referencedColumnName = joinColumn.getReferencedColumn();
/*  51: 80 */       if ((referencedColumnName == null) || (BinderHelper.isEmptyAnnotationValue(referencedColumnName))) {
/*  52:    */         break;
/*  53:    */       }
/*  54: 84 */       columnByReferencedName.put(referencedColumnName.toLowerCase(), joinColumn);
/*  55:    */     }
/*  56: 87 */     int index = 0;
/*  57: 88 */     if (columnByReferencedName.isEmpty())
/*  58:    */     {
/*  59: 89 */       isExplicitReference = false;
/*  60: 90 */       for (Ejb3JoinColumn joinColumn : this.joinColumns)
/*  61:    */       {
/*  62: 91 */         columnByReferencedName.put("" + index, joinColumn);
/*  63: 92 */         index++;
/*  64:    */       }
/*  65: 94 */       index = 0;
/*  66:    */     }
/*  67: 97 */     while (properties.hasNext())
/*  68:    */     {
/*  69: 98 */       Property referencedProperty = (Property)properties.next();
/*  70: 99 */       if (referencedProperty.isComposite()) {
/*  71:100 */         throw new AssertionFailure("Unexpected nested component on the referenced entity when mapping a @MapsId: " + this.referencedEntityName);
/*  72:    */       }
/*  73:104 */       Property property = new Property();
/*  74:105 */       property.setName(referencedProperty.getName());
/*  75:106 */       property.setNodeName(referencedProperty.getNodeName());
/*  76:    */       
/*  77:    */ 
/*  78:109 */       property.setPersistentClass(this.component.getOwner());
/*  79:110 */       property.setPropertyAccessorName(referencedProperty.getPropertyAccessorName());
/*  80:111 */       SimpleValue value = new SimpleValue(this.mappings, this.component.getTable());
/*  81:112 */       property.setValue(value);
/*  82:113 */       SimpleValue referencedValue = (SimpleValue)referencedProperty.getValue();
/*  83:114 */       value.setTypeName(referencedValue.getTypeName());
/*  84:115 */       value.setTypeParameters(referencedValue.getTypeParameters());
/*  85:116 */       Iterator<Column> columns = referencedValue.getColumnIterator();
/*  86:118 */       if (this.joinColumns[0].isNameDeferred()) {
/*  87:119 */         this.joinColumns[0].copyReferencedStructureAndCreateDefaultJoinColumns(referencedPersistentClass, columns, value);
/*  88:    */       } else {
/*  89:126 */         while (columns.hasNext())
/*  90:    */         {
/*  91:127 */           Column column = (Column)columns.next();
/*  92:    */           
/*  93:129 */           String logicalColumnName = null;
/*  94:    */           Ejb3JoinColumn joinColumn;
/*  95:    */           Ejb3JoinColumn joinColumn;
/*  96:130 */           if (isExplicitReference)
/*  97:    */           {
/*  98:131 */             String columnName = column.getName();
/*  99:132 */             logicalColumnName = this.mappings.getLogicalColumnName(columnName, referencedPersistentClass.getTable());
/* 100:    */             
/* 101:134 */             joinColumn = (Ejb3JoinColumn)columnByReferencedName.get(logicalColumnName.toLowerCase());
/* 102:    */           }
/* 103:    */           else
/* 104:    */           {
/* 105:137 */             joinColumn = (Ejb3JoinColumn)columnByReferencedName.get("" + index);
/* 106:138 */             index++;
/* 107:    */           }
/* 108:140 */           if ((joinColumn == null) && (!this.joinColumns[0].isNameDeferred())) {
/* 109:141 */             throw new AnnotationException("Implicit column reference in the @MapsId mapping fails, try to use explicit referenceColumnNames: " + this.referencedEntityName);
/* 110:    */           }
/* 111:147 */           String columnName = (joinColumn == null) || (joinColumn.isNameDeferred()) ? "tata_" + column.getName() : joinColumn.getName();
/* 112:    */           
/* 113:149 */           value.addColumn(new Column(columnName));
/* 114:150 */           column.setValue(value);
/* 115:    */         }
/* 116:    */       }
/* 117:153 */       this.component.addProperty(property);
/* 118:    */     }
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.CopyIdentifierComponentSecondPass
 * JD-Core Version:    0.7.0.1
 */