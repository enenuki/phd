/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.Filter;
/*  10:    */ import org.hibernate.UnknownProfileException;
/*  11:    */ import org.hibernate.internal.FilterImpl;
/*  12:    */ import org.hibernate.type.Type;
/*  13:    */ 
/*  14:    */ public class LoadQueryInfluencers
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17: 54 */   public static LoadQueryInfluencers NONE = new LoadQueryInfluencers();
/*  18:    */   private final SessionFactoryImplementor sessionFactory;
/*  19:    */   private String internalFetchProfile;
/*  20:    */   private Map<String, Filter> enabledFilters;
/*  21:    */   private Set<String> enabledFetchProfileNames;
/*  22:    */   
/*  23:    */   public LoadQueryInfluencers()
/*  24:    */   {
/*  25: 62 */     this(null, Collections.emptyMap(), Collections.emptySet());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public LoadQueryInfluencers(SessionFactoryImplementor sessionFactory)
/*  29:    */   {
/*  30: 66 */     this(sessionFactory, new HashMap(), new HashSet());
/*  31:    */   }
/*  32:    */   
/*  33:    */   private LoadQueryInfluencers(SessionFactoryImplementor sessionFactory, Map<String, Filter> enabledFilters, Set<String> enabledFetchProfileNames)
/*  34:    */   {
/*  35: 70 */     this.sessionFactory = sessionFactory;
/*  36: 71 */     this.enabledFilters = enabledFilters;
/*  37: 72 */     this.enabledFetchProfileNames = enabledFetchProfileNames;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public SessionFactoryImplementor getSessionFactory()
/*  41:    */   {
/*  42: 76 */     return this.sessionFactory;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getInternalFetchProfile()
/*  46:    */   {
/*  47: 83 */     return this.internalFetchProfile;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setInternalFetchProfile(String internalFetchProfile)
/*  51:    */   {
/*  52: 87 */     if (this.sessionFactory == null) {
/*  53: 90 */       throw new IllegalStateException("Cannot modify context-less LoadQueryInfluencers");
/*  54:    */     }
/*  55: 92 */     this.internalFetchProfile = internalFetchProfile;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean hasEnabledFilters()
/*  59:    */   {
/*  60: 99 */     return (this.enabledFilters != null) && (!this.enabledFilters.isEmpty());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Map<String, Filter> getEnabledFilters()
/*  64:    */   {
/*  65:105 */     for (Filter filter : this.enabledFilters.values()) {
/*  66:106 */       filter.validate();
/*  67:    */     }
/*  68:108 */     return this.enabledFilters;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Set<String> getEnabledFilterNames()
/*  72:    */   {
/*  73:116 */     return Collections.unmodifiableSet(this.enabledFilters.keySet());
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Filter getEnabledFilter(String filterName)
/*  77:    */   {
/*  78:120 */     return (Filter)this.enabledFilters.get(filterName);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Filter enableFilter(String filterName)
/*  82:    */   {
/*  83:124 */     FilterImpl filter = new FilterImpl(this.sessionFactory.getFilterDefinition(filterName));
/*  84:125 */     this.enabledFilters.put(filterName, filter);
/*  85:126 */     return filter;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void disableFilter(String filterName)
/*  89:    */   {
/*  90:130 */     this.enabledFilters.remove(filterName);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Object getFilterParameterValue(String filterParameterName)
/*  94:    */   {
/*  95:134 */     String[] parsed = parseFilterParameterName(filterParameterName);
/*  96:135 */     FilterImpl filter = (FilterImpl)this.enabledFilters.get(parsed[0]);
/*  97:136 */     if (filter == null) {
/*  98:137 */       throw new IllegalArgumentException("Filter [" + parsed[0] + "] currently not enabled");
/*  99:    */     }
/* 100:139 */     return filter.getParameter(parsed[1]);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Type getFilterParameterType(String filterParameterName)
/* 104:    */   {
/* 105:143 */     String[] parsed = parseFilterParameterName(filterParameterName);
/* 106:144 */     FilterDefinition filterDef = this.sessionFactory.getFilterDefinition(parsed[0]);
/* 107:145 */     if (filterDef == null) {
/* 108:146 */       throw new IllegalArgumentException("Filter [" + parsed[0] + "] not defined");
/* 109:    */     }
/* 110:148 */     Type type = filterDef.getParameterType(parsed[1]);
/* 111:149 */     if (type == null) {
/* 112:151 */       throw new InternalError("Unable to locate type for filter parameter");
/* 113:    */     }
/* 114:153 */     return type;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static String[] parseFilterParameterName(String filterParameterName)
/* 118:    */   {
/* 119:157 */     int dot = filterParameterName.indexOf('.');
/* 120:158 */     if (dot <= 0) {
/* 121:159 */       throw new IllegalArgumentException("Invalid filter-parameter name format");
/* 122:    */     }
/* 123:161 */     String filterName = filterParameterName.substring(0, dot);
/* 124:162 */     String parameterName = filterParameterName.substring(dot + 1);
/* 125:163 */     return new String[] { filterName, parameterName };
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean hasEnabledFetchProfiles()
/* 129:    */   {
/* 130:170 */     return (this.enabledFetchProfileNames != null) && (!this.enabledFetchProfileNames.isEmpty());
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Set getEnabledFetchProfileNames()
/* 134:    */   {
/* 135:174 */     return this.enabledFetchProfileNames;
/* 136:    */   }
/* 137:    */   
/* 138:    */   private void checkFetchProfileName(String name)
/* 139:    */   {
/* 140:178 */     if (!this.sessionFactory.containsFetchProfileDefinition(name)) {
/* 141:179 */       throw new UnknownProfileException(name);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isFetchProfileEnabled(String name)
/* 146:    */     throws UnknownProfileException
/* 147:    */   {
/* 148:184 */     checkFetchProfileName(name);
/* 149:185 */     return this.enabledFetchProfileNames.contains(name);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void enableFetchProfile(String name)
/* 153:    */     throws UnknownProfileException
/* 154:    */   {
/* 155:189 */     checkFetchProfileName(name);
/* 156:190 */     this.enabledFetchProfileNames.add(name);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void disableFetchProfile(String name)
/* 160:    */     throws UnknownProfileException
/* 161:    */   {
/* 162:194 */     checkFetchProfileName(name);
/* 163:195 */     this.enabledFetchProfileNames.remove(name);
/* 164:    */   }
/* 165:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.LoadQueryInfluencers
 * JD-Core Version:    0.7.0.1
 */