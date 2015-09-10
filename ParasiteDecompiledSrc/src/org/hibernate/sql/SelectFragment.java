/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import org.hibernate.internal.util.StringHelper;
/*   9:    */ 
/*  10:    */ public class SelectFragment
/*  11:    */ {
/*  12:    */   private String suffix;
/*  13: 42 */   private List columns = new ArrayList();
/*  14: 44 */   private List columnAliases = new ArrayList();
/*  15:    */   private String extraSelectList;
/*  16:    */   private String[] usedAliases;
/*  17:    */   
/*  18:    */   public List getColumns()
/*  19:    */   {
/*  20: 51 */     return this.columns;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String getExtraSelectList()
/*  24:    */   {
/*  25: 55 */     return this.extraSelectList;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public SelectFragment setUsedAliases(String[] aliases)
/*  29:    */   {
/*  30: 59 */     this.usedAliases = aliases;
/*  31: 60 */     return this;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public SelectFragment setExtraSelectList(String extraSelectList)
/*  35:    */   {
/*  36: 64 */     this.extraSelectList = extraSelectList;
/*  37: 65 */     return this;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public SelectFragment setExtraSelectList(CaseFragment caseFragment, String fragmentAlias)
/*  41:    */   {
/*  42: 69 */     setExtraSelectList(caseFragment.setReturnColumnName(fragmentAlias, this.suffix).toFragmentString());
/*  43: 70 */     return this;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public SelectFragment setSuffix(String suffix)
/*  47:    */   {
/*  48: 74 */     this.suffix = suffix;
/*  49: 75 */     return this;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public SelectFragment addColumn(String columnName)
/*  53:    */   {
/*  54: 79 */     addColumn(null, columnName);
/*  55: 80 */     return this;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public SelectFragment addColumns(String[] columnNames)
/*  59:    */   {
/*  60: 84 */     for (int i = 0; i < columnNames.length; i++) {
/*  61: 84 */       addColumn(columnNames[i]);
/*  62:    */     }
/*  63: 85 */     return this;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public SelectFragment addColumn(String tableAlias, String columnName)
/*  67:    */   {
/*  68: 89 */     return addColumn(tableAlias, columnName, columnName);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public SelectFragment addColumn(String tableAlias, String columnName, String columnAlias)
/*  72:    */   {
/*  73: 93 */     this.columns.add(StringHelper.qualify(tableAlias, columnName));
/*  74:    */     
/*  75:    */ 
/*  76: 96 */     this.columnAliases.add(columnAlias);
/*  77: 97 */     return this;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public SelectFragment addColumns(String tableAlias, String[] columnNames)
/*  81:    */   {
/*  82:101 */     for (int i = 0; i < columnNames.length; i++) {
/*  83:101 */       addColumn(tableAlias, columnNames[i]);
/*  84:    */     }
/*  85:102 */     return this;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public SelectFragment addColumns(String tableAlias, String[] columnNames, String[] columnAliases)
/*  89:    */   {
/*  90:106 */     for (int i = 0; i < columnNames.length; i++) {
/*  91:107 */       if (columnNames[i] != null) {
/*  92:107 */         addColumn(tableAlias, columnNames[i], columnAliases[i]);
/*  93:    */       }
/*  94:    */     }
/*  95:109 */     return this;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public SelectFragment addFormulas(String tableAlias, String[] formulas, String[] formulaAliases)
/*  99:    */   {
/* 100:113 */     for (int i = 0; i < formulas.length; i++) {
/* 101:114 */       if (formulas[i] != null) {
/* 102:114 */         addFormula(tableAlias, formulas[i], formulaAliases[i]);
/* 103:    */       }
/* 104:    */     }
/* 105:116 */     return this;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public SelectFragment addFormula(String tableAlias, String formula, String formulaAlias)
/* 109:    */   {
/* 110:120 */     this.columns.add(StringHelper.replace(formula, "$PlaceHolder$", tableAlias));
/* 111:121 */     this.columnAliases.add(formulaAlias);
/* 112:122 */     return this;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public SelectFragment addColumnTemplate(String tableAlias, String columnTemplate, String columnAlias)
/* 116:    */   {
/* 117:127 */     return addFormula(tableAlias, columnTemplate, columnAlias);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public SelectFragment addColumnTemplates(String tableAlias, String[] columnTemplates, String[] columnAliases)
/* 121:    */   {
/* 122:132 */     return addFormulas(tableAlias, columnTemplates, columnAliases);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String toFragmentString()
/* 126:    */   {
/* 127:136 */     StringBuffer buf = new StringBuffer(this.columns.size() * 10);
/* 128:137 */     Iterator iter = this.columns.iterator();
/* 129:138 */     Iterator columnAliasIter = this.columnAliases.iterator();
/* 130:    */     
/* 131:140 */     HashSet columnsUnique = new HashSet();
/* 132:141 */     if (this.usedAliases != null) {
/* 133:141 */       columnsUnique.addAll(Arrays.asList(this.usedAliases));
/* 134:    */     }
/* 135:142 */     while (iter.hasNext())
/* 136:    */     {
/* 137:143 */       String column = (String)iter.next();
/* 138:144 */       String columnAlias = (String)columnAliasIter.next();
/* 139:150 */       if (columnsUnique.add(columnAlias))
/* 140:    */       {
/* 141:151 */         buf.append(", ").append(column).append(" as ");
/* 142:154 */         if (this.suffix == null) {
/* 143:155 */           buf.append(columnAlias);
/* 144:    */         } else {
/* 145:158 */           buf.append(new Alias(this.suffix).toAliasString(columnAlias));
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:162 */     if (this.extraSelectList != null) {
/* 150:163 */       buf.append(", ").append(this.extraSelectList);
/* 151:    */     }
/* 152:166 */     return buf.toString();
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.SelectFragment
 * JD-Core Version:    0.7.0.1
 */