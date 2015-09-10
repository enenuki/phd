/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.CSSOMParser;
/*   4:    */ import com.steadystate.css.parser.LexicalUnitImpl;
/*   5:    */ import com.steadystate.css.userdata.UserDataConstants;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.io.StringReader;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import org.w3c.css.sac.InputSource;
/*  12:    */ import org.w3c.css.sac.LexicalUnit;
/*  13:    */ import org.w3c.css.sac.Locator;
/*  14:    */ import org.w3c.dom.DOMException;
/*  15:    */ import org.w3c.dom.css.CSSPrimitiveValue;
/*  16:    */ import org.w3c.dom.css.CSSValue;
/*  17:    */ import org.w3c.dom.css.CSSValueList;
/*  18:    */ import org.w3c.dom.css.Counter;
/*  19:    */ import org.w3c.dom.css.RGBColor;
/*  20:    */ import org.w3c.dom.css.Rect;
/*  21:    */ 
/*  22:    */ public class CSSValueImpl
/*  23:    */   extends CSSOMObjectImpl
/*  24:    */   implements CSSPrimitiveValue, CSSValueList, Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 406281136418322579L;
/*  27: 69 */   private Object _value = null;
/*  28:    */   
/*  29:    */   public Object getValue()
/*  30:    */   {
/*  31: 73 */     return this._value;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setValue(Object value)
/*  35:    */   {
/*  36: 78 */     this._value = value;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public CSSValueImpl(LexicalUnit value, boolean forcePrimitive)
/*  40:    */   {
/*  41: 84 */     if ((!forcePrimitive) && (value.getNextLexicalUnit() != null)) {
/*  42: 86 */       this._value = getValues(value);
/*  43: 88 */     } else if (value.getParameters() != null)
/*  44:    */     {
/*  45: 89 */       if (value.getLexicalUnitType() == 38) {
/*  46: 91 */         this._value = new RectImpl(value.getParameters());
/*  47: 92 */       } else if (value.getLexicalUnitType() == 27) {
/*  48: 94 */         this._value = new RGBColorImpl(value.getParameters());
/*  49: 95 */       } else if (value.getLexicalUnitType() == 25) {
/*  50: 97 */         this._value = new CounterImpl(false, value.getParameters());
/*  51: 98 */       } else if (value.getLexicalUnitType() == 26) {
/*  52:100 */         this._value = new CounterImpl(true, value.getParameters());
/*  53:    */       } else {
/*  54:102 */         this._value = value;
/*  55:    */       }
/*  56:    */     }
/*  57:    */     else {
/*  58:106 */       this._value = value;
/*  59:    */     }
/*  60:108 */     if ((value instanceof LexicalUnitImpl))
/*  61:    */     {
/*  62:110 */       Locator locator = ((LexicalUnitImpl)value).getLocator();
/*  63:111 */       if (locator != null) {
/*  64:113 */         setUserData(UserDataConstants.KEY_LOCATOR, locator);
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public CSSValueImpl() {}
/*  70:    */   
/*  71:    */   private List<CSSValueImpl> getValues(LexicalUnit value)
/*  72:    */   {
/*  73:128 */     List<CSSValueImpl> values = new ArrayList();
/*  74:129 */     LexicalUnit lu = value;
/*  75:130 */     while (lu != null)
/*  76:    */     {
/*  77:131 */       if ((lu.getLexicalUnitType() != 0) && (lu.getLexicalUnitType() != 4)) {
/*  78:133 */         values.add(new CSSValueImpl(lu, true));
/*  79:    */       }
/*  80:135 */       lu = lu.getNextLexicalUnit();
/*  81:    */     }
/*  82:137 */     return values;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public CSSValueImpl(LexicalUnit value)
/*  86:    */   {
/*  87:141 */     this(value, false);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getCssText()
/*  91:    */   {
/*  92:145 */     if (getCssValueType() == 2)
/*  93:    */     {
/*  94:149 */       StringBuilder sb = new StringBuilder();
/*  95:150 */       List list = (List)this._value;
/*  96:151 */       Iterator it = list.iterator();
/*  97:152 */       while (it.hasNext())
/*  98:    */       {
/*  99:154 */         Object o = it.next();
/* 100:155 */         if ((o instanceof LexicalUnit))
/* 101:    */         {
/* 102:157 */           LexicalUnit lu = (LexicalUnit)((CSSValueImpl)list.get(0))._value;
/* 103:158 */           while (lu != null)
/* 104:    */           {
/* 105:159 */             sb.append(lu.toString());
/* 106:    */             
/* 107:    */ 
/* 108:    */ 
/* 109:163 */             LexicalUnit prev = lu;
/* 110:164 */             lu = lu.getNextLexicalUnit();
/* 111:165 */             if ((lu != null) && (lu.getLexicalUnitType() != 0) && (lu.getLexicalUnitType() != 4) && (prev.getLexicalUnitType() != 4)) {
/* 112:169 */               sb.append(" ");
/* 113:    */             }
/* 114:    */           }
/* 115:    */         }
/* 116:    */         else
/* 117:    */         {
/* 118:175 */           sb.append(o);
/* 119:    */         }
/* 120:177 */         if (it.hasNext()) {
/* 121:179 */           sb.append(" ");
/* 122:    */         }
/* 123:    */       }
/* 124:182 */       return sb.toString();
/* 125:    */     }
/* 126:184 */     return this._value.toString();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setCssText(String cssText)
/* 130:    */     throws DOMException
/* 131:    */   {
/* 132:    */     try
/* 133:    */     {
/* 134:189 */       InputSource is = new InputSource(new StringReader(cssText));
/* 135:190 */       CSSOMParser parser = new CSSOMParser();
/* 136:191 */       CSSValueImpl v2 = (CSSValueImpl)parser.parsePropertyValue(is);
/* 137:192 */       this._value = v2._value;
/* 138:    */     }
/* 139:    */     catch (Exception e)
/* 140:    */     {
/* 141:194 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public short getCssValueType()
/* 146:    */   {
/* 147:202 */     if ((this._value instanceof List)) {
/* 148:204 */       return 2;
/* 149:    */     }
/* 150:206 */     if (((this._value instanceof LexicalUnit)) && (((LexicalUnit)this._value).getLexicalUnitType() == 12)) {
/* 151:209 */       return 0;
/* 152:    */     }
/* 153:211 */     return 1;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public short getPrimitiveType()
/* 157:    */   {
/* 158:215 */     if ((this._value instanceof LexicalUnit))
/* 159:    */     {
/* 160:216 */       LexicalUnit lu = (LexicalUnit)this._value;
/* 161:217 */       switch (lu.getLexicalUnitType())
/* 162:    */       {
/* 163:    */       case 12: 
/* 164:219 */         return 21;
/* 165:    */       case 13: 
/* 166:    */       case 14: 
/* 167:222 */         return 1;
/* 168:    */       case 15: 
/* 169:224 */         return 3;
/* 170:    */       case 16: 
/* 171:226 */         return 4;
/* 172:    */       case 17: 
/* 173:228 */         return 5;
/* 174:    */       case 18: 
/* 175:230 */         return 8;
/* 176:    */       case 19: 
/* 177:232 */         return 6;
/* 178:    */       case 20: 
/* 179:234 */         return 7;
/* 180:    */       case 21: 
/* 181:236 */         return 9;
/* 182:    */       case 22: 
/* 183:238 */         return 10;
/* 184:    */       case 23: 
/* 185:240 */         return 2;
/* 186:    */       case 24: 
/* 187:242 */         return 20;
/* 188:    */       case 28: 
/* 189:249 */         return 11;
/* 190:    */       case 29: 
/* 191:251 */         return 13;
/* 192:    */       case 30: 
/* 193:253 */         return 12;
/* 194:    */       case 31: 
/* 195:255 */         return 14;
/* 196:    */       case 32: 
/* 197:257 */         return 15;
/* 198:    */       case 33: 
/* 199:259 */         return 17;
/* 200:    */       case 34: 
/* 201:261 */         return 16;
/* 202:    */       case 35: 
/* 203:263 */         return 21;
/* 204:    */       case 36: 
/* 205:265 */         return 19;
/* 206:    */       case 37: 
/* 207:267 */         return 22;
/* 208:    */       case 39: 
/* 209:    */       case 40: 
/* 210:    */       case 41: 
/* 211:273 */         return 19;
/* 212:    */       case 42: 
/* 213:275 */         return 18;
/* 214:    */       }
/* 215:    */     }
/* 216:    */     else
/* 217:    */     {
/* 218:277 */       if ((this._value instanceof RectImpl)) {
/* 219:278 */         return 24;
/* 220:    */       }
/* 221:279 */       if ((this._value instanceof RGBColorImpl)) {
/* 222:280 */         return 25;
/* 223:    */       }
/* 224:281 */       if ((this._value instanceof CounterImpl)) {
/* 225:282 */         return 23;
/* 226:    */       }
/* 227:    */     }
/* 228:284 */     return 0;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setFloatValue(short unitType, float floatValue)
/* 232:    */     throws DOMException
/* 233:    */   {
/* 234:288 */     this._value = LexicalUnitImpl.createNumber(null, floatValue);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public float getFloatValue(short unitType)
/* 238:    */     throws DOMException
/* 239:    */   {
/* 240:292 */     if ((this._value instanceof LexicalUnit))
/* 241:    */     {
/* 242:293 */       LexicalUnit lu = (LexicalUnit)this._value;
/* 243:294 */       return lu.getFloatValue();
/* 244:    */     }
/* 245:296 */     throw new DOMExceptionImpl((short)15, 10);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setStringValue(short stringType, String stringValue)
/* 249:    */     throws DOMException
/* 250:    */   {
/* 251:305 */     switch (stringType)
/* 252:    */     {
/* 253:    */     case 19: 
/* 254:307 */       this._value = LexicalUnitImpl.createString(null, stringValue);
/* 255:308 */       break;
/* 256:    */     case 20: 
/* 257:310 */       this._value = LexicalUnitImpl.createURI(null, stringValue);
/* 258:311 */       break;
/* 259:    */     case 21: 
/* 260:313 */       this._value = LexicalUnitImpl.createIdent(null, stringValue);
/* 261:314 */       break;
/* 262:    */     case 22: 
/* 263:318 */       throw new DOMExceptionImpl((short)9, 19);
/* 264:    */     default: 
/* 265:322 */       throw new DOMExceptionImpl((short)15, 11);
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getStringValue()
/* 270:    */     throws DOMException
/* 271:    */   {
/* 272:332 */     if ((this._value instanceof LexicalUnit))
/* 273:    */     {
/* 274:333 */       LexicalUnit lu = (LexicalUnit)this._value;
/* 275:334 */       if ((lu.getLexicalUnitType() == 35) || (lu.getLexicalUnitType() == 36) || (lu.getLexicalUnitType() == 24) || (lu.getLexicalUnitType() == 12)) {
/* 276:338 */         return lu.getStringValue();
/* 277:    */       }
/* 278:340 */       if (lu.getLexicalUnitType() == 37) {
/* 279:342 */         return lu.getParameters().getStringValue();
/* 280:    */       }
/* 281:    */     }
/* 282:344 */     else if ((this._value instanceof List))
/* 283:    */     {
/* 284:345 */       return null;
/* 285:    */     }
/* 286:348 */     throw new DOMExceptionImpl((short)15, 11);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public Counter getCounterValue()
/* 290:    */     throws DOMException
/* 291:    */   {
/* 292:354 */     if (!(this._value instanceof Counter)) {
/* 293:355 */       throw new DOMExceptionImpl((short)15, 12);
/* 294:    */     }
/* 295:359 */     return (Counter)this._value;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Rect getRectValue()
/* 299:    */     throws DOMException
/* 300:    */   {
/* 301:363 */     if (!(this._value instanceof Rect)) {
/* 302:364 */       throw new DOMExceptionImpl((short)15, 13);
/* 303:    */     }
/* 304:368 */     return (Rect)this._value;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public RGBColor getRGBColorValue()
/* 308:    */     throws DOMException
/* 309:    */   {
/* 310:372 */     if (!(this._value instanceof RGBColor)) {
/* 311:373 */       throw new DOMExceptionImpl((short)15, 14);
/* 312:    */     }
/* 313:377 */     return (RGBColor)this._value;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public int getLength()
/* 317:    */   {
/* 318:381 */     return (this._value instanceof List) ? ((List)this._value).size() : 0;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public CSSValue item(int index)
/* 322:    */   {
/* 323:385 */     return (this._value instanceof List) ? (CSSValue)((List)this._value).get(index) : null;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public String toString()
/* 327:    */   {
/* 328:391 */     return getCssText();
/* 329:    */   }
/* 330:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSValueImpl
 * JD-Core Version:    0.7.0.1
 */