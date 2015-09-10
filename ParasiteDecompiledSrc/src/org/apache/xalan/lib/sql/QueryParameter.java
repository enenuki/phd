/*   1:    */ package org.apache.xalan.lib.sql;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ 
/*   5:    */ public class QueryParameter
/*   6:    */ {
/*   7:    */   private int m_type;
/*   8:    */   private String m_name;
/*   9:    */   private String m_value;
/*  10:    */   private boolean m_output;
/*  11:    */   private String m_typeName;
/*  12: 38 */   private static Hashtable m_Typetable = null;
/*  13:    */   
/*  14:    */   public QueryParameter()
/*  15:    */   {
/*  16: 42 */     this.m_type = -1;
/*  17: 43 */     this.m_name = null;
/*  18: 44 */     this.m_value = null;
/*  19: 45 */     this.m_output = false;
/*  20: 46 */     this.m_typeName = null;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public QueryParameter(String v, String t)
/*  24:    */   {
/*  25: 55 */     this.m_name = null;
/*  26: 56 */     this.m_value = v;
/*  27: 57 */     this.m_output = false;
/*  28: 58 */     setTypeName(t);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public QueryParameter(String name, String value, String type, boolean out_flag)
/*  32:    */   {
/*  33: 63 */     this.m_name = name;
/*  34: 64 */     this.m_value = value;
/*  35: 65 */     this.m_output = out_flag;
/*  36: 66 */     setTypeName(type);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getValue()
/*  40:    */   {
/*  41: 73 */     return this.m_value;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setValue(String newValue)
/*  45:    */   {
/*  46: 81 */     this.m_value = newValue;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setTypeName(String newType)
/*  50:    */   {
/*  51: 90 */     this.m_type = map_type(newType);
/*  52: 91 */     this.m_typeName = newType;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getTypeName()
/*  56:    */   {
/*  57: 99 */     return this.m_typeName;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getType()
/*  61:    */   {
/*  62:107 */     return this.m_type;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getName()
/*  66:    */   {
/*  67:115 */     return this.m_name;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setName(String n)
/*  71:    */   {
/*  72:125 */     this.m_name = n;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isOutput()
/*  76:    */   {
/*  77:133 */     return this.m_output;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIsOutput(boolean flag)
/*  81:    */   {
/*  82:143 */     this.m_output = flag;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private static int map_type(String typename)
/*  86:    */   {
/*  87:148 */     if (m_Typetable == null)
/*  88:    */     {
/*  89:151 */       m_Typetable = new Hashtable();
/*  90:152 */       m_Typetable.put("BIGINT", new Integer(-5));
/*  91:153 */       m_Typetable.put("BINARY", new Integer(-2));
/*  92:154 */       m_Typetable.put("BIT", new Integer(-7));
/*  93:155 */       m_Typetable.put("CHAR", new Integer(1));
/*  94:156 */       m_Typetable.put("DATE", new Integer(91));
/*  95:157 */       m_Typetable.put("DECIMAL", new Integer(3));
/*  96:158 */       m_Typetable.put("DOUBLE", new Integer(8));
/*  97:159 */       m_Typetable.put("FLOAT", new Integer(6));
/*  98:160 */       m_Typetable.put("INTEGER", new Integer(4));
/*  99:161 */       m_Typetable.put("LONGVARBINARY", new Integer(-4));
/* 100:162 */       m_Typetable.put("LONGVARCHAR", new Integer(-1));
/* 101:163 */       m_Typetable.put("NULL", new Integer(0));
/* 102:164 */       m_Typetable.put("NUMERIC", new Integer(2));
/* 103:165 */       m_Typetable.put("OTHER", new Integer(1111));
/* 104:166 */       m_Typetable.put("REAL", new Integer(7));
/* 105:167 */       m_Typetable.put("SMALLINT", new Integer(5));
/* 106:168 */       m_Typetable.put("TIME", new Integer(92));
/* 107:169 */       m_Typetable.put("TIMESTAMP", new Integer(93));
/* 108:170 */       m_Typetable.put("TINYINT", new Integer(-6));
/* 109:171 */       m_Typetable.put("VARBINARY", new Integer(-3));
/* 110:172 */       m_Typetable.put("VARCHAR", new Integer(12));
/* 111:    */       
/* 112:    */ 
/* 113:175 */       m_Typetable.put("STRING", new Integer(12));
/* 114:176 */       m_Typetable.put("BIGDECIMAL", new Integer(2));
/* 115:177 */       m_Typetable.put("BOOLEAN", new Integer(-7));
/* 116:178 */       m_Typetable.put("BYTES", new Integer(-4));
/* 117:179 */       m_Typetable.put("LONG", new Integer(-5));
/* 118:180 */       m_Typetable.put("SHORT", new Integer(5));
/* 119:    */     }
/* 120:183 */     Integer type = (Integer)m_Typetable.get(typename.toUpperCase());
/* 121:    */     int rtype;
/* 122:185 */     if (type == null) {
/* 123:186 */       rtype = 1111;
/* 124:    */     } else {
/* 125:188 */       rtype = type.intValue();
/* 126:    */     }
/* 127:190 */     return rtype;
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.QueryParameter
 * JD-Core Version:    0.7.0.1
 */