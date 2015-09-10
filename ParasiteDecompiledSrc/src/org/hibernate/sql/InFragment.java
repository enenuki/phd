/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.internal.util.StringHelper;
/*   6:    */ 
/*   7:    */ public class InFragment
/*   8:    */ {
/*   9:    */   public static final String NULL = "null";
/*  10:    */   public static final String NOT_NULL = "not null";
/*  11:    */   private String columnName;
/*  12: 45 */   private List values = new ArrayList();
/*  13:    */   
/*  14:    */   public InFragment addValue(Object value)
/*  15:    */   {
/*  16: 51 */     this.values.add(value);
/*  17: 52 */     return this;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public InFragment setColumn(String columnName)
/*  21:    */   {
/*  22: 56 */     this.columnName = columnName;
/*  23: 57 */     return this;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public InFragment setColumn(String alias, String columnName)
/*  27:    */   {
/*  28: 61 */     this.columnName = StringHelper.qualify(alias, columnName);
/*  29: 62 */     return setColumn(this.columnName);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public InFragment setFormula(String alias, String formulaTemplate)
/*  33:    */   {
/*  34: 66 */     this.columnName = StringHelper.replace(formulaTemplate, "$PlaceHolder$", alias);
/*  35: 67 */     return setColumn(this.columnName);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String toFragmentString()
/*  39:    */   {
/*  40: 72 */     if (this.values.size() == 0) {
/*  41: 73 */       return "1=2";
/*  42:    */     }
/*  43: 76 */     StringBuilder buf = new StringBuilder(this.values.size() * 5);
/*  44: 78 */     if (this.values.size() == 1)
/*  45:    */     {
/*  46: 79 */       Object value = this.values.get(0);
/*  47: 80 */       buf.append(this.columnName);
/*  48: 82 */       if ("null".equals(value)) {
/*  49: 83 */         buf.append(" is null");
/*  50: 85 */       } else if ("not null".equals(value)) {
/*  51: 86 */         buf.append(" is not null");
/*  52:    */       } else {
/*  53: 88 */         buf.append('=').append(value);
/*  54:    */       }
/*  55: 91 */       return buf.toString();
/*  56:    */     }
/*  57: 94 */     boolean allowNull = false;
/*  58: 96 */     for (Object value : this.values) {
/*  59: 97 */       if ("null".equals(value)) {
/*  60: 98 */         allowNull = true;
/*  61:100 */       } else if ("not null".equals(value)) {
/*  62:101 */         throw new IllegalArgumentException("not null makes no sense for in expression");
/*  63:    */       }
/*  64:    */     }
/*  65:106 */     if (allowNull) {
/*  66:107 */       buf.append('(').append(this.columnName).append(" is null or ").append(this.columnName).append(" in (");
/*  67:    */     } else {
/*  68:109 */       buf.append(this.columnName).append(" in (");
/*  69:    */     }
/*  70:112 */     for (Object value : this.values) {
/*  71:113 */       if (!"null".equals(value))
/*  72:    */       {
/*  73:116 */         buf.append(value);
/*  74:117 */         buf.append(", ");
/*  75:    */       }
/*  76:    */     }
/*  77:121 */     buf.setLength(buf.length() - 2);
/*  78:123 */     if (allowNull) {
/*  79:124 */       buf.append("))");
/*  80:    */     } else {
/*  81:126 */       buf.append(')');
/*  82:    */     }
/*  83:129 */     return buf.toString();
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.InFragment
 * JD-Core Version:    0.7.0.1
 */