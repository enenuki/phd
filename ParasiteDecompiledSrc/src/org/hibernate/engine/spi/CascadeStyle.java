/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   8:    */ 
/*   9:    */ public abstract class CascadeStyle
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   public boolean reallyDoCascade(CascadingAction action)
/*  13:    */   {
/*  14: 65 */     return doCascade(action);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public boolean hasOrphanDelete()
/*  18:    */   {
/*  19: 75 */     return false;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static final class MultipleCascadeStyle
/*  23:    */     extends CascadeStyle
/*  24:    */   {
/*  25:    */     private final CascadeStyle[] styles;
/*  26:    */     
/*  27:    */     public MultipleCascadeStyle(CascadeStyle[] styles)
/*  28:    */     {
/*  29: 82 */       this.styles = styles;
/*  30:    */     }
/*  31:    */     
/*  32:    */     public boolean doCascade(CascadingAction action)
/*  33:    */     {
/*  34: 86 */       for (CascadeStyle style : this.styles) {
/*  35: 87 */         if (style.doCascade(action)) {
/*  36: 88 */           return true;
/*  37:    */         }
/*  38:    */       }
/*  39: 91 */       return false;
/*  40:    */     }
/*  41:    */     
/*  42:    */     public boolean reallyDoCascade(CascadingAction action)
/*  43:    */     {
/*  44: 95 */       for (CascadeStyle style : this.styles) {
/*  45: 96 */         if (style.reallyDoCascade(action)) {
/*  46: 97 */           return true;
/*  47:    */         }
/*  48:    */       }
/*  49:100 */       return false;
/*  50:    */     }
/*  51:    */     
/*  52:    */     public boolean hasOrphanDelete()
/*  53:    */     {
/*  54:104 */       for (CascadeStyle style : this.styles) {
/*  55:105 */         if (style.hasOrphanDelete()) {
/*  56:106 */           return true;
/*  57:    */         }
/*  58:    */       }
/*  59:109 */       return false;
/*  60:    */     }
/*  61:    */     
/*  62:    */     public String toString()
/*  63:    */     {
/*  64:113 */       return ArrayHelper.toString(this.styles);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:120 */   public static final CascadeStyle ALL_DELETE_ORPHAN = new CascadeStyle()
/*  69:    */   {
/*  70:    */     public boolean doCascade(CascadingAction action)
/*  71:    */     {
/*  72:122 */       return true;
/*  73:    */     }
/*  74:    */     
/*  75:    */     public boolean hasOrphanDelete()
/*  76:    */     {
/*  77:126 */       return true;
/*  78:    */     }
/*  79:    */     
/*  80:    */     public String toString()
/*  81:    */     {
/*  82:130 */       return "STYLE_ALL_DELETE_ORPHAN";
/*  83:    */     }
/*  84:    */   };
/*  85:137 */   public static final CascadeStyle ALL = new CascadeStyle()
/*  86:    */   {
/*  87:    */     public boolean doCascade(CascadingAction action)
/*  88:    */     {
/*  89:139 */       return true;
/*  90:    */     }
/*  91:    */     
/*  92:    */     public String toString()
/*  93:    */     {
/*  94:143 */       return "STYLE_ALL";
/*  95:    */     }
/*  96:    */   };
/*  97:150 */   public static final CascadeStyle UPDATE = new CascadeStyle()
/*  98:    */   {
/*  99:    */     public boolean doCascade(CascadingAction action)
/* 100:    */     {
/* 101:152 */       return action == CascadingAction.SAVE_UPDATE;
/* 102:    */     }
/* 103:    */     
/* 104:    */     public String toString()
/* 105:    */     {
/* 106:156 */       return "STYLE_SAVE_UPDATE";
/* 107:    */     }
/* 108:    */   };
/* 109:163 */   public static final CascadeStyle LOCK = new CascadeStyle()
/* 110:    */   {
/* 111:    */     public boolean doCascade(CascadingAction action)
/* 112:    */     {
/* 113:165 */       return action == CascadingAction.LOCK;
/* 114:    */     }
/* 115:    */     
/* 116:    */     public String toString()
/* 117:    */     {
/* 118:169 */       return "STYLE_LOCK";
/* 119:    */     }
/* 120:    */   };
/* 121:176 */   public static final CascadeStyle REFRESH = new CascadeStyle()
/* 122:    */   {
/* 123:    */     public boolean doCascade(CascadingAction action)
/* 124:    */     {
/* 125:178 */       return action == CascadingAction.REFRESH;
/* 126:    */     }
/* 127:    */     
/* 128:    */     public String toString()
/* 129:    */     {
/* 130:182 */       return "STYLE_REFRESH";
/* 131:    */     }
/* 132:    */   };
/* 133:189 */   public static final CascadeStyle EVICT = new CascadeStyle()
/* 134:    */   {
/* 135:    */     public boolean doCascade(CascadingAction action)
/* 136:    */     {
/* 137:191 */       return action == CascadingAction.EVICT;
/* 138:    */     }
/* 139:    */     
/* 140:    */     public String toString()
/* 141:    */     {
/* 142:195 */       return "STYLE_EVICT";
/* 143:    */     }
/* 144:    */   };
/* 145:202 */   public static final CascadeStyle REPLICATE = new CascadeStyle()
/* 146:    */   {
/* 147:    */     public boolean doCascade(CascadingAction action)
/* 148:    */     {
/* 149:204 */       return action == CascadingAction.REPLICATE;
/* 150:    */     }
/* 151:    */     
/* 152:    */     public String toString()
/* 153:    */     {
/* 154:208 */       return "STYLE_REPLICATE";
/* 155:    */     }
/* 156:    */   };
/* 157:214 */   public static final CascadeStyle MERGE = new CascadeStyle()
/* 158:    */   {
/* 159:    */     public boolean doCascade(CascadingAction action)
/* 160:    */     {
/* 161:216 */       return action == CascadingAction.MERGE;
/* 162:    */     }
/* 163:    */     
/* 164:    */     public String toString()
/* 165:    */     {
/* 166:220 */       return "STYLE_MERGE";
/* 167:    */     }
/* 168:    */   };
/* 169:227 */   public static final CascadeStyle PERSIST = new CascadeStyle()
/* 170:    */   {
/* 171:    */     public boolean doCascade(CascadingAction action)
/* 172:    */     {
/* 173:229 */       return (action == CascadingAction.PERSIST) || (action == CascadingAction.PERSIST_ON_FLUSH);
/* 174:    */     }
/* 175:    */     
/* 176:    */     public String toString()
/* 177:    */     {
/* 178:234 */       return "STYLE_PERSIST";
/* 179:    */     }
/* 180:    */   };
/* 181:241 */   public static final CascadeStyle DELETE = new CascadeStyle()
/* 182:    */   {
/* 183:    */     public boolean doCascade(CascadingAction action)
/* 184:    */     {
/* 185:243 */       return action == CascadingAction.DELETE;
/* 186:    */     }
/* 187:    */     
/* 188:    */     public String toString()
/* 189:    */     {
/* 190:247 */       return "STYLE_DELETE";
/* 191:    */     }
/* 192:    */   };
/* 193:254 */   public static final CascadeStyle DELETE_ORPHAN = new CascadeStyle()
/* 194:    */   {
/* 195:    */     public boolean doCascade(CascadingAction action)
/* 196:    */     {
/* 197:256 */       return (action == CascadingAction.DELETE) || (action == CascadingAction.SAVE_UPDATE);
/* 198:    */     }
/* 199:    */     
/* 200:    */     public boolean reallyDoCascade(CascadingAction action)
/* 201:    */     {
/* 202:260 */       return action == CascadingAction.DELETE;
/* 203:    */     }
/* 204:    */     
/* 205:    */     public boolean hasOrphanDelete()
/* 206:    */     {
/* 207:264 */       return true;
/* 208:    */     }
/* 209:    */     
/* 210:    */     public String toString()
/* 211:    */     {
/* 212:268 */       return "STYLE_DELETE_ORPHAN";
/* 213:    */     }
/* 214:    */   };
/* 215:275 */   public static final CascadeStyle NONE = new CascadeStyle()
/* 216:    */   {
/* 217:    */     public boolean doCascade(CascadingAction action)
/* 218:    */     {
/* 219:277 */       return false;
/* 220:    */     }
/* 221:    */     
/* 222:    */     public String toString()
/* 223:    */     {
/* 224:281 */       return "STYLE_NONE";
/* 225:    */     }
/* 226:    */   };
/* 227:288 */   static final Map<String, CascadeStyle> STYLES = new HashMap();
/* 228:    */   
/* 229:    */   static
/* 230:    */   {
/* 231:291 */     STYLES.put("all", ALL);
/* 232:292 */     STYLES.put("all-delete-orphan", ALL_DELETE_ORPHAN);
/* 233:293 */     STYLES.put("save-update", UPDATE);
/* 234:294 */     STYLES.put("persist", PERSIST);
/* 235:295 */     STYLES.put("merge", MERGE);
/* 236:296 */     STYLES.put("lock", LOCK);
/* 237:297 */     STYLES.put("refresh", REFRESH);
/* 238:298 */     STYLES.put("replicate", REPLICATE);
/* 239:299 */     STYLES.put("evict", EVICT);
/* 240:300 */     STYLES.put("delete", DELETE);
/* 241:301 */     STYLES.put("remove", DELETE);
/* 242:302 */     STYLES.put("delete-orphan", DELETE_ORPHAN);
/* 243:303 */     STYLES.put("none", NONE);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public static CascadeStyle getCascadeStyle(String cascade)
/* 247:    */   {
/* 248:314 */     CascadeStyle style = (CascadeStyle)STYLES.get(cascade);
/* 249:315 */     if (style == null) {
/* 250:316 */       throw new MappingException("Unsupported cascade style: " + cascade);
/* 251:    */     }
/* 252:319 */     return style;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public abstract boolean doCascade(CascadingAction paramCascadingAction);
/* 256:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.CascadeStyle
 * JD-Core Version:    0.7.0.1
 */