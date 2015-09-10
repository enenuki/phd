/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.Criteria;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   8:    */ import org.hibernate.type.Type;
/*   9:    */ 
/*  10:    */ public class ProjectionList
/*  11:    */   implements EnhancedProjection
/*  12:    */ {
/*  13: 39 */   private List elements = new ArrayList();
/*  14:    */   
/*  15:    */   public ProjectionList create()
/*  16:    */   {
/*  17: 44 */     return new ProjectionList();
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ProjectionList add(Projection proj)
/*  21:    */   {
/*  22: 48 */     this.elements.add(proj);
/*  23: 49 */     return this;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ProjectionList add(Projection projection, String alias)
/*  27:    */   {
/*  28: 53 */     return add(Projections.alias(projection, alias));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
/*  32:    */     throws HibernateException
/*  33:    */   {
/*  34: 58 */     List types = new ArrayList(getLength());
/*  35: 59 */     for (int i = 0; i < getLength(); i++)
/*  36:    */     {
/*  37: 60 */       Type[] elemTypes = getProjection(i).getTypes(criteria, criteriaQuery);
/*  38: 61 */       ArrayHelper.addAll(types, elemTypes);
/*  39:    */     }
/*  40: 63 */     return ArrayHelper.toTypeArray(types);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String toSqlString(Criteria criteria, int loc, CriteriaQuery criteriaQuery)
/*  44:    */     throws HibernateException
/*  45:    */   {
/*  46: 68 */     StringBuffer buf = new StringBuffer();
/*  47: 69 */     for (int i = 0; i < getLength(); i++)
/*  48:    */     {
/*  49: 70 */       Projection proj = getProjection(i);
/*  50: 71 */       buf.append(proj.toSqlString(criteria, loc, criteriaQuery));
/*  51: 72 */       loc += getColumnAliases(loc, criteria, criteriaQuery, proj).length;
/*  52: 73 */       if (i < this.elements.size() - 1) {
/*  53: 73 */         buf.append(", ");
/*  54:    */       }
/*  55:    */     }
/*  56: 75 */     return buf.toString();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/*  60:    */     throws HibernateException
/*  61:    */   {
/*  62: 80 */     StringBuffer buf = new StringBuffer();
/*  63: 81 */     for (int i = 0; i < getLength(); i++)
/*  64:    */     {
/*  65: 82 */       Projection proj = getProjection(i);
/*  66: 83 */       if (proj.isGrouped()) {
/*  67: 84 */         buf.append(proj.toGroupSqlString(criteria, criteriaQuery)).append(", ");
/*  68:    */       }
/*  69:    */     }
/*  70: 88 */     if (buf.length() > 2) {
/*  71: 88 */       buf.setLength(buf.length() - 2);
/*  72:    */     }
/*  73: 89 */     return buf.toString();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String[] getColumnAliases(int loc)
/*  77:    */   {
/*  78: 93 */     List result = new ArrayList(getLength());
/*  79: 94 */     for (int i = 0; i < getLength(); i++)
/*  80:    */     {
/*  81: 95 */       String[] colAliases = getProjection(i).getColumnAliases(loc);
/*  82: 96 */       ArrayHelper.addAll(result, colAliases);
/*  83: 97 */       loc += colAliases.length;
/*  84:    */     }
/*  85: 99 */     return ArrayHelper.toStringArray(result);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String[] getColumnAliases(int loc, Criteria criteria, CriteriaQuery criteriaQuery)
/*  89:    */   {
/*  90:103 */     List result = new ArrayList(getLength());
/*  91:104 */     for (int i = 0; i < getLength(); i++)
/*  92:    */     {
/*  93:105 */       String[] colAliases = getColumnAliases(loc, criteria, criteriaQuery, getProjection(i));
/*  94:106 */       ArrayHelper.addAll(result, colAliases);
/*  95:107 */       loc += colAliases.length;
/*  96:    */     }
/*  97:109 */     return ArrayHelper.toStringArray(result);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String[] getColumnAliases(String alias, int loc)
/* 101:    */   {
/* 102:113 */     for (int i = 0; i < getLength(); i++)
/* 103:    */     {
/* 104:114 */       String[] result = getProjection(i).getColumnAliases(alias, loc);
/* 105:115 */       if (result != null) {
/* 106:115 */         return result;
/* 107:    */       }
/* 108:116 */       loc += getProjection(i).getColumnAliases(loc).length;
/* 109:    */     }
/* 110:118 */     return null;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String[] getColumnAliases(String alias, int loc, Criteria criteria, CriteriaQuery criteriaQuery)
/* 114:    */   {
/* 115:122 */     for (int i = 0; i < getLength(); i++)
/* 116:    */     {
/* 117:123 */       String[] result = getColumnAliases(alias, loc, criteria, criteriaQuery, getProjection(i));
/* 118:124 */       if (result != null) {
/* 119:124 */         return result;
/* 120:    */       }
/* 121:125 */       loc += getColumnAliases(loc, criteria, criteriaQuery, getProjection(i)).length;
/* 122:    */     }
/* 123:127 */     return null;
/* 124:    */   }
/* 125:    */   
/* 126:    */   private static String[] getColumnAliases(int loc, Criteria criteria, CriteriaQuery criteriaQuery, Projection projection)
/* 127:    */   {
/* 128:131 */     return (projection instanceof EnhancedProjection) ? ((EnhancedProjection)projection).getColumnAliases(loc, criteria, criteriaQuery) : projection.getColumnAliases(loc);
/* 129:    */   }
/* 130:    */   
/* 131:    */   private static String[] getColumnAliases(String alias, int loc, Criteria criteria, CriteriaQuery criteriaQuery, Projection projection)
/* 132:    */   {
/* 133:137 */     return (projection instanceof EnhancedProjection) ? ((EnhancedProjection)projection).getColumnAliases(alias, loc, criteria, criteriaQuery) : projection.getColumnAliases(alias, loc);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Type[] getTypes(String alias, Criteria criteria, CriteriaQuery criteriaQuery)
/* 137:    */   {
/* 138:143 */     for (int i = 0; i < getLength(); i++)
/* 139:    */     {
/* 140:144 */       Type[] result = getProjection(i).getTypes(alias, criteria, criteriaQuery);
/* 141:145 */       if (result != null) {
/* 142:145 */         return result;
/* 143:    */       }
/* 144:    */     }
/* 145:147 */     return null;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String[] getAliases()
/* 149:    */   {
/* 150:151 */     List result = new ArrayList(getLength());
/* 151:152 */     for (int i = 0; i < getLength(); i++)
/* 152:    */     {
/* 153:153 */       String[] aliases = getProjection(i).getAliases();
/* 154:154 */       ArrayHelper.addAll(result, aliases);
/* 155:    */     }
/* 156:156 */     return ArrayHelper.toStringArray(result);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Projection getProjection(int i)
/* 160:    */   {
/* 161:161 */     return (Projection)this.elements.get(i);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public int getLength()
/* 165:    */   {
/* 166:165 */     return this.elements.size();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String toString()
/* 170:    */   {
/* 171:169 */     return this.elements.toString();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean isGrouped()
/* 175:    */   {
/* 176:173 */     for (int i = 0; i < getLength(); i++) {
/* 177:174 */       if (getProjection(i).isGrouped()) {
/* 178:174 */         return true;
/* 179:    */       }
/* 180:    */     }
/* 181:176 */     return false;
/* 182:    */   }
/* 183:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.ProjectionList
 * JD-Core Version:    0.7.0.1
 */