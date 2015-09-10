/*   1:    */ package org.hibernate.cfg.beanvalidation;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Properties;
/*   8:    */ import javax.validation.groups.Default;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.internal.util.ReflectHelper;
/*  11:    */ 
/*  12:    */ public class GroupsPerOperation
/*  13:    */ {
/*  14:    */   private static final String JPA_GROUP_PREFIX = "javax.persistence.validation.group.";
/*  15:    */   private static final String HIBERNATE_GROUP_PREFIX = "org.hibernate.validator.group.";
/*  16: 43 */   private static final Class<?>[] DEFAULT_GROUPS = { Default.class };
/*  17: 44 */   private static final Class<?>[] EMPTY_GROUPS = new Class[0];
/*  18: 46 */   private Map<Operation, Class<?>[]> groupsPerOperation = new HashMap(4);
/*  19:    */   
/*  20:    */   public GroupsPerOperation(Properties properties)
/*  21:    */   {
/*  22: 49 */     setGroupsForOperation(Operation.INSERT, properties);
/*  23: 50 */     setGroupsForOperation(Operation.UPDATE, properties);
/*  24: 51 */     setGroupsForOperation(Operation.DELETE, properties);
/*  25: 52 */     setGroupsForOperation(Operation.DDL, properties);
/*  26:    */   }
/*  27:    */   
/*  28:    */   private void setGroupsForOperation(Operation operation, Properties properties)
/*  29:    */   {
/*  30: 56 */     Object property = properties.get(operation.getGroupPropertyName());
/*  31:    */     Class<?>[] groups;
/*  32: 59 */     if (property == null)
/*  33:    */     {
/*  34: 60 */       groups = operation == Operation.DELETE ? EMPTY_GROUPS : DEFAULT_GROUPS;
/*  35:    */     }
/*  36:    */     else
/*  37:    */     {
/*  38:    */       Class<?>[] groups;
/*  39: 63 */       if ((property instanceof String))
/*  40:    */       {
/*  41: 64 */         String stringProperty = (String)property;
/*  42: 65 */         String[] groupNames = stringProperty.split(",");
/*  43:    */         Class<?>[] groups;
/*  44: 66 */         if ((groupNames.length == 1) && (groupNames[0].equals("")))
/*  45:    */         {
/*  46: 67 */           groups = EMPTY_GROUPS;
/*  47:    */         }
/*  48:    */         else
/*  49:    */         {
/*  50: 70 */           List<Class<?>> groupsList = new ArrayList(groupNames.length);
/*  51: 71 */           for (String groupName : groupNames)
/*  52:    */           {
/*  53: 72 */             String cleanedGroupName = groupName.trim();
/*  54: 73 */             if (cleanedGroupName.length() > 0) {
/*  55:    */               try
/*  56:    */               {
/*  57: 75 */                 groupsList.add(ReflectHelper.classForName(cleanedGroupName));
/*  58:    */               }
/*  59:    */               catch (ClassNotFoundException e)
/*  60:    */               {
/*  61: 78 */                 throw new HibernateException("Unable to load class " + cleanedGroupName, e);
/*  62:    */               }
/*  63:    */             }
/*  64:    */           }
/*  65: 82 */           groups = (Class[])groupsList.toArray(new Class[groupsList.size()]);
/*  66:    */         }
/*  67:    */       }
/*  68:    */       else
/*  69:    */       {
/*  70:    */         Class<?>[] groups;
/*  71: 85 */         if ((property instanceof Class[])) {
/*  72: 86 */           groups = (Class[])property;
/*  73:    */         } else {
/*  74: 90 */           throw new HibernateException("javax.persistence.validation.group." + operation.getGroupPropertyName() + " is of unknown type: String or Class<?>[] only");
/*  75:    */         }
/*  76:    */       }
/*  77:    */     }
/*  78:    */     Class<?>[] groups;
/*  79: 93 */     this.groupsPerOperation.put(operation, groups);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Class<?>[] get(Operation operation)
/*  83:    */   {
/*  84: 97 */     return (Class[])this.groupsPerOperation.get(operation);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static enum Operation
/*  88:    */   {
/*  89:101 */     INSERT("persist", "javax.persistence.validation.group.pre-persist"),  UPDATE("update", "javax.persistence.validation.group.pre-update"),  DELETE("remove", "javax.persistence.validation.group.pre-remove"),  DDL("ddl", "org.hibernate.validator.group.ddl");
/*  90:    */     
/*  91:    */     private String exposedName;
/*  92:    */     private String groupPropertyName;
/*  93:    */     
/*  94:    */     private Operation(String exposedName, String groupProperty)
/*  95:    */     {
/*  96:111 */       this.exposedName = exposedName;
/*  97:112 */       this.groupPropertyName = groupProperty;
/*  98:    */     }
/*  99:    */     
/* 100:    */     public String getName()
/* 101:    */     {
/* 102:116 */       return this.exposedName;
/* 103:    */     }
/* 104:    */     
/* 105:    */     public String getGroupPropertyName()
/* 106:    */     {
/* 107:120 */       return this.groupPropertyName;
/* 108:    */     }
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.beanvalidation.GroupsPerOperation
 * JD-Core Version:    0.7.0.1
 */