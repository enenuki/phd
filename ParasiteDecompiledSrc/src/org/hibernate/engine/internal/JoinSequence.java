/*   1:    */ package org.hibernate.engine.internal;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   9:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  10:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  11:    */ import org.hibernate.persister.entity.Joinable;
/*  12:    */ import org.hibernate.sql.JoinFragment;
/*  13:    */ import org.hibernate.sql.JoinType;
/*  14:    */ import org.hibernate.sql.QueryJoinFragment;
/*  15:    */ import org.hibernate.type.AssociationType;
/*  16:    */ 
/*  17:    */ public class JoinSequence
/*  18:    */ {
/*  19:    */   private final SessionFactoryImplementor factory;
/*  20: 47 */   private final List<Join> joins = new ArrayList();
/*  21: 48 */   private boolean useThetaStyle = false;
/*  22: 49 */   private final StringBuffer conditions = new StringBuffer();
/*  23:    */   private String rootAlias;
/*  24:    */   private Joinable rootJoinable;
/*  25:    */   private Selector selector;
/*  26:    */   private JoinSequence next;
/*  27: 54 */   private boolean isFromPart = false;
/*  28:    */   
/*  29:    */   public String toString()
/*  30:    */   {
/*  31: 58 */     StringBuilder buf = new StringBuilder();
/*  32: 59 */     buf.append("JoinSequence{");
/*  33: 60 */     if (this.rootJoinable != null) {
/*  34: 61 */       buf.append(this.rootJoinable).append('[').append(this.rootAlias).append(']');
/*  35:    */     }
/*  36: 66 */     for (int i = 0; i < this.joins.size(); i++) {
/*  37: 67 */       buf.append("->").append(this.joins.get(i));
/*  38:    */     }
/*  39: 69 */     return '}';
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static abstract interface Selector
/*  43:    */   {
/*  44:    */     public abstract boolean includeSubclasses(String paramString);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final class Join
/*  48:    */   {
/*  49:    */     private final AssociationType associationType;
/*  50:    */     private final Joinable joinable;
/*  51:    */     private final JoinType joinType;
/*  52:    */     private final String alias;
/*  53:    */     private final String[] lhsColumns;
/*  54:    */     
/*  55:    */     Join(AssociationType associationType, String alias, JoinType joinType, String[] lhsColumns)
/*  56:    */       throws MappingException
/*  57:    */     {
/*  58: 82 */       this.associationType = associationType;
/*  59: 83 */       this.joinable = associationType.getAssociatedJoinable(JoinSequence.this.factory);
/*  60: 84 */       this.alias = alias;
/*  61: 85 */       this.joinType = joinType;
/*  62: 86 */       this.lhsColumns = lhsColumns;
/*  63:    */     }
/*  64:    */     
/*  65:    */     public String getAlias()
/*  66:    */     {
/*  67: 90 */       return this.alias;
/*  68:    */     }
/*  69:    */     
/*  70:    */     public AssociationType getAssociationType()
/*  71:    */     {
/*  72: 94 */       return this.associationType;
/*  73:    */     }
/*  74:    */     
/*  75:    */     public Joinable getJoinable()
/*  76:    */     {
/*  77: 98 */       return this.joinable;
/*  78:    */     }
/*  79:    */     
/*  80:    */     public JoinType getJoinType()
/*  81:    */     {
/*  82:102 */       return this.joinType;
/*  83:    */     }
/*  84:    */     
/*  85:    */     public String[] getLHSColumns()
/*  86:    */     {
/*  87:106 */       return this.lhsColumns;
/*  88:    */     }
/*  89:    */     
/*  90:    */     public String toString()
/*  91:    */     {
/*  92:111 */       return this.joinable.toString() + '[' + this.alias + ']';
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public JoinSequence(SessionFactoryImplementor factory)
/*  97:    */   {
/*  98:116 */     this.factory = factory;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public JoinSequence getFromPart()
/* 102:    */   {
/* 103:120 */     JoinSequence fromPart = new JoinSequence(this.factory);
/* 104:121 */     fromPart.joins.addAll(this.joins);
/* 105:122 */     fromPart.useThetaStyle = this.useThetaStyle;
/* 106:123 */     fromPart.rootAlias = this.rootAlias;
/* 107:124 */     fromPart.rootJoinable = this.rootJoinable;
/* 108:125 */     fromPart.selector = this.selector;
/* 109:126 */     fromPart.next = (this.next == null ? null : this.next.getFromPart());
/* 110:127 */     fromPart.isFromPart = true;
/* 111:128 */     return fromPart;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public JoinSequence copy()
/* 115:    */   {
/* 116:132 */     JoinSequence copy = new JoinSequence(this.factory);
/* 117:133 */     copy.joins.addAll(this.joins);
/* 118:134 */     copy.useThetaStyle = this.useThetaStyle;
/* 119:135 */     copy.rootAlias = this.rootAlias;
/* 120:136 */     copy.rootJoinable = this.rootJoinable;
/* 121:137 */     copy.selector = this.selector;
/* 122:138 */     copy.next = (this.next == null ? null : this.next.copy());
/* 123:139 */     copy.isFromPart = this.isFromPart;
/* 124:140 */     copy.conditions.append(this.conditions.toString());
/* 125:141 */     return copy;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public JoinSequence addJoin(AssociationType associationType, String alias, JoinType joinType, String[] referencingKey)
/* 129:    */     throws MappingException
/* 130:    */   {
/* 131:146 */     this.joins.add(new Join(associationType, alias, joinType, referencingKey));
/* 132:147 */     return this;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public JoinFragment toJoinFragment()
/* 136:    */     throws MappingException
/* 137:    */   {
/* 138:151 */     return toJoinFragment(CollectionHelper.EMPTY_MAP, true);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public JoinFragment toJoinFragment(Map enabledFilters, boolean includeExtraJoins)
/* 142:    */     throws MappingException
/* 143:    */   {
/* 144:155 */     return toJoinFragment(enabledFilters, includeExtraJoins, null, null);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public JoinFragment toJoinFragment(Map enabledFilters, boolean includeExtraJoins, String withClauseFragment, String withClauseJoinAlias)
/* 148:    */     throws MappingException
/* 149:    */   {
/* 150:163 */     QueryJoinFragment joinFragment = new QueryJoinFragment(this.factory.getDialect(), this.useThetaStyle);
/* 151:164 */     if (this.rootJoinable != null)
/* 152:    */     {
/* 153:165 */       joinFragment.addCrossJoin(this.rootJoinable.getTableName(), this.rootAlias);
/* 154:166 */       String filterCondition = this.rootJoinable.filterFragment(this.rootAlias, enabledFilters);
/* 155:    */       
/* 156:    */ 
/* 157:    */ 
/* 158:170 */       joinFragment.setHasFilterCondition(joinFragment.addCondition(filterCondition));
/* 159:171 */       if (includeExtraJoins) {
/* 160:172 */         addExtraJoins(joinFragment, this.rootAlias, this.rootJoinable, true);
/* 161:    */       }
/* 162:    */     }
/* 163:176 */     Joinable last = this.rootJoinable;
/* 164:178 */     for (Join join : this.joins)
/* 165:    */     {
/* 166:179 */       String on = join.getAssociationType().getOnCondition(join.getAlias(), this.factory, enabledFilters);
/* 167:180 */       String condition = null;
/* 168:181 */       if ((last != null) && (isManyToManyRoot(last)) && (((QueryableCollection)last).getElementType() == join.getAssociationType()))
/* 169:    */       {
/* 170:187 */         String manyToManyFilter = ((QueryableCollection)last).getManyToManyFilterFragment(join.getAlias(), enabledFilters);
/* 171:    */         
/* 172:189 */         condition = on + " and " + manyToManyFilter;
/* 173:    */       }
/* 174:    */       else
/* 175:    */       {
/* 176:196 */         condition = on;
/* 177:    */       }
/* 178:198 */       if ((withClauseFragment != null) && 
/* 179:199 */         (join.getAlias().equals(withClauseJoinAlias))) {
/* 180:200 */         condition = condition + " and " + withClauseFragment;
/* 181:    */       }
/* 182:203 */       joinFragment.addJoin(join.getJoinable().getTableName(), join.getAlias(), join.getLHSColumns(), JoinHelper.getRHSColumnNames(join.getAssociationType(), this.factory), join.joinType, condition);
/* 183:211 */       if (includeExtraJoins) {
/* 184:212 */         addExtraJoins(joinFragment, join.getAlias(), join.getJoinable(), join.joinType == JoinType.INNER_JOIN);
/* 185:    */       }
/* 186:214 */       last = join.getJoinable();
/* 187:    */     }
/* 188:216 */     if (this.next != null) {
/* 189:217 */       joinFragment.addFragment(this.next.toJoinFragment(enabledFilters, includeExtraJoins));
/* 190:    */     }
/* 191:219 */     joinFragment.addCondition(this.conditions.toString());
/* 192:220 */     if (this.isFromPart) {
/* 193:220 */       joinFragment.clearWherePart();
/* 194:    */     }
/* 195:221 */     return joinFragment;
/* 196:    */   }
/* 197:    */   
/* 198:    */   private boolean isManyToManyRoot(Joinable joinable)
/* 199:    */   {
/* 200:225 */     if ((joinable != null) && (joinable.isCollection()))
/* 201:    */     {
/* 202:226 */       QueryableCollection persister = (QueryableCollection)joinable;
/* 203:227 */       return persister.isManyToMany();
/* 204:    */     }
/* 205:229 */     return false;
/* 206:    */   }
/* 207:    */   
/* 208:    */   private boolean isIncluded(String alias)
/* 209:    */   {
/* 210:233 */     return (this.selector != null) && (this.selector.includeSubclasses(alias));
/* 211:    */   }
/* 212:    */   
/* 213:    */   private void addExtraJoins(JoinFragment joinFragment, String alias, Joinable joinable, boolean innerJoin)
/* 214:    */   {
/* 215:237 */     boolean include = isIncluded(alias);
/* 216:238 */     joinFragment.addJoins(joinable.fromJoinFragment(alias, innerJoin, include), joinable.whereJoinFragment(alias, innerJoin, include));
/* 217:    */   }
/* 218:    */   
/* 219:    */   public JoinSequence addCondition(String condition)
/* 220:    */   {
/* 221:243 */     if (condition.trim().length() != 0)
/* 222:    */     {
/* 223:244 */       if (!condition.startsWith(" and ")) {
/* 224:244 */         this.conditions.append(" and ");
/* 225:    */       }
/* 226:245 */       this.conditions.append(condition);
/* 227:    */     }
/* 228:247 */     return this;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public JoinSequence addCondition(String alias, String[] columns, String condition)
/* 232:    */   {
/* 233:251 */     for (int i = 0; i < columns.length; i++) {
/* 234:252 */       this.conditions.append(" and ").append(alias).append('.').append(columns[i]).append(condition);
/* 235:    */     }
/* 236:258 */     return this;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public JoinSequence setRoot(Joinable joinable, String alias)
/* 240:    */   {
/* 241:262 */     this.rootAlias = alias;
/* 242:263 */     this.rootJoinable = joinable;
/* 243:264 */     return this;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public JoinSequence setNext(JoinSequence next)
/* 247:    */   {
/* 248:268 */     this.next = next;
/* 249:269 */     return this;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public JoinSequence setSelector(Selector s)
/* 253:    */   {
/* 254:273 */     this.selector = s;
/* 255:274 */     return this;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public JoinSequence setUseThetaStyle(boolean useThetaStyle)
/* 259:    */   {
/* 260:278 */     this.useThetaStyle = useThetaStyle;
/* 261:279 */     return this;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public boolean isThetaStyle()
/* 265:    */   {
/* 266:283 */     return this.useThetaStyle;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public int getJoinCount()
/* 270:    */   {
/* 271:287 */     return this.joins.size();
/* 272:    */   }
/* 273:    */   
/* 274:    */   public Iterator iterateJoins()
/* 275:    */   {
/* 276:291 */     return this.joins.iterator();
/* 277:    */   }
/* 278:    */   
/* 279:    */   public Join getFirstJoin()
/* 280:    */   {
/* 281:295 */     return (Join)this.joins.get(0);
/* 282:    */   }
/* 283:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.JoinSequence
 * JD-Core Version:    0.7.0.1
 */