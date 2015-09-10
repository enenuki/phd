/*   1:    */ package org.apache.commons.lang.builder;
/*   2:    */ 
/*   3:    */ public class StandardToStringStyle
/*   4:    */   extends ToStringStyle
/*   5:    */ {
/*   6:    */   private static final long serialVersionUID = 1L;
/*   7:    */   
/*   8:    */   public boolean isUseClassName()
/*   9:    */   {
/*  10: 58 */     return super.isUseClassName();
/*  11:    */   }
/*  12:    */   
/*  13:    */   public void setUseClassName(boolean useClassName)
/*  14:    */   {
/*  15: 67 */     super.setUseClassName(useClassName);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public boolean isUseShortClassName()
/*  19:    */   {
/*  20: 79 */     return super.isUseShortClassName();
/*  21:    */   }
/*  22:    */   
/*  23:    */   /**
/*  24:    */    * @deprecated
/*  25:    */    */
/*  26:    */   public boolean isShortClassName()
/*  27:    */   {
/*  28: 90 */     return super.isUseShortClassName();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setUseShortClassName(boolean useShortClassName)
/*  32:    */   {
/*  33:100 */     super.setUseShortClassName(useShortClassName);
/*  34:    */   }
/*  35:    */   
/*  36:    */   /**
/*  37:    */    * @deprecated
/*  38:    */    */
/*  39:    */   public void setShortClassName(boolean shortClassName)
/*  40:    */   {
/*  41:111 */     super.setUseShortClassName(shortClassName);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isUseIdentityHashCode()
/*  45:    */   {
/*  46:121 */     return super.isUseIdentityHashCode();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setUseIdentityHashCode(boolean useIdentityHashCode)
/*  50:    */   {
/*  51:130 */     super.setUseIdentityHashCode(useIdentityHashCode);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isUseFieldNames()
/*  55:    */   {
/*  56:141 */     return super.isUseFieldNames();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setUseFieldNames(boolean useFieldNames)
/*  60:    */   {
/*  61:150 */     super.setUseFieldNames(useFieldNames);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isDefaultFullDetail()
/*  65:    */   {
/*  66:162 */     return super.isDefaultFullDetail();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setDefaultFullDetail(boolean defaultFullDetail)
/*  70:    */   {
/*  71:172 */     super.setDefaultFullDetail(defaultFullDetail);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean isArrayContentDetail()
/*  75:    */   {
/*  76:183 */     return super.isArrayContentDetail();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setArrayContentDetail(boolean arrayContentDetail)
/*  80:    */   {
/*  81:192 */     super.setArrayContentDetail(arrayContentDetail);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getArrayStart()
/*  85:    */   {
/*  86:203 */     return super.getArrayStart();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setArrayStart(String arrayStart)
/*  90:    */   {
/*  91:215 */     super.setArrayStart(arrayStart);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getArrayEnd()
/*  95:    */   {
/*  96:226 */     return super.getArrayEnd();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setArrayEnd(String arrayEnd)
/* 100:    */   {
/* 101:238 */     super.setArrayEnd(arrayEnd);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getArraySeparator()
/* 105:    */   {
/* 106:249 */     return super.getArraySeparator();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setArraySeparator(String arraySeparator)
/* 110:    */   {
/* 111:261 */     super.setArraySeparator(arraySeparator);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getContentStart()
/* 115:    */   {
/* 116:272 */     return super.getContentStart();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setContentStart(String contentStart)
/* 120:    */   {
/* 121:284 */     super.setContentStart(contentStart);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getContentEnd()
/* 125:    */   {
/* 126:295 */     return super.getContentEnd();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setContentEnd(String contentEnd)
/* 130:    */   {
/* 131:307 */     super.setContentEnd(contentEnd);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getFieldNameValueSeparator()
/* 135:    */   {
/* 136:318 */     return super.getFieldNameValueSeparator();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setFieldNameValueSeparator(String fieldNameValueSeparator)
/* 140:    */   {
/* 141:330 */     super.setFieldNameValueSeparator(fieldNameValueSeparator);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getFieldSeparator()
/* 145:    */   {
/* 146:341 */     return super.getFieldSeparator();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setFieldSeparator(String fieldSeparator)
/* 150:    */   {
/* 151:353 */     super.setFieldSeparator(fieldSeparator);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isFieldSeparatorAtStart()
/* 155:    */   {
/* 156:366 */     return super.isFieldSeparatorAtStart();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setFieldSeparatorAtStart(boolean fieldSeparatorAtStart)
/* 160:    */   {
/* 161:377 */     super.setFieldSeparatorAtStart(fieldSeparatorAtStart);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean isFieldSeparatorAtEnd()
/* 165:    */   {
/* 166:390 */     return super.isFieldSeparatorAtEnd();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setFieldSeparatorAtEnd(boolean fieldSeparatorAtEnd)
/* 170:    */   {
/* 171:401 */     super.setFieldSeparatorAtEnd(fieldSeparatorAtEnd);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String getNullText()
/* 175:    */   {
/* 176:412 */     return super.getNullText();
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setNullText(String nullText)
/* 180:    */   {
/* 181:424 */     super.setNullText(nullText);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String getSizeStartText()
/* 185:    */   {
/* 186:438 */     return super.getSizeStartText();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setSizeStartText(String sizeStartText)
/* 190:    */   {
/* 191:453 */     super.setSizeStartText(sizeStartText);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String getSizeEndText()
/* 195:    */   {
/* 196:467 */     return super.getSizeEndText();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setSizeEndText(String sizeEndText)
/* 200:    */   {
/* 201:482 */     super.setSizeEndText(sizeEndText);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String getSummaryObjectStartText()
/* 205:    */   {
/* 206:496 */     return super.getSummaryObjectStartText();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setSummaryObjectStartText(String summaryObjectStartText)
/* 210:    */   {
/* 211:511 */     super.setSummaryObjectStartText(summaryObjectStartText);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String getSummaryObjectEndText()
/* 215:    */   {
/* 216:525 */     return super.getSummaryObjectEndText();
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setSummaryObjectEndText(String summaryObjectEndText)
/* 220:    */   {
/* 221:540 */     super.setSummaryObjectEndText(summaryObjectEndText);
/* 222:    */   }
/* 223:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.builder.StandardToStringStyle
 * JD-Core Version:    0.7.0.1
 */