/*   1:    */ package org.apache.xml.serializer.utils;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ 
/*   5:    */ public class SerializerMessages_hu
/*   6:    */   extends ListResourceBundle
/*   7:    */ {
/*   8:    */   public Object[][] getContents()
/*   9:    */   {
/*  10: 73 */     Object[][] contents = { { "BAD_MSGKEY", "A(z) ''{0}'' üzenetkulcs nem található a(z) ''{1}'' üzenetosztályban." }, { "BAD_MSGFORMAT", "A(z) ''{1}'' üzenetosztály ''{0}'' üzenetének formátuma hibás." }, { "ER_SERIALIZER_NOT_CONTENTHANDLER", "A(z) ''{0}'' példányosító osztály nem valósítja meg az org.xml.sax.ContentHandler függvényt." }, { "ER_RESOURCE_COULD_NOT_FIND", "A(z) [ {0} ] erőforrás nem található.\n {1}" }, { "ER_RESOURCE_COULD_NOT_LOAD", "A(z) [ {0} ] erőforrást nem lehet betölteni: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Pufferméret <= 0" }, { "ER_INVALID_UTF16_SURROGATE", "Érvénytelen UTF-16 helyettesítés: {0} ?" }, { "ER_OIERROR", "IO hiba" }, { "ER_ILLEGAL_ATTRIBUTE_POSITION", "Nem lehet {0} attribútumot hozzáadni utód csomópontok után vagy egy elem előállítása előtt.  Az attribútum figyelmen kívül marad." }, { "ER_NAMESPACE_PREFIX", "A(z) ''{0}'' előtag névtere nincs deklarálva." }, { "ER_STRAY_ATTRIBUTE", "A(z) ''{0}'' attribútum kívül esik az elemen." }, { "ER_STRAY_NAMESPACE", "A(z) ''{0}''=''{1}'' névtérdeklaráció kívül esik az elemen." }, { "ER_COULD_NOT_LOAD_RESOURCE", "Nem lehet betölteni ''{0}'' erőforrást (ellenőrizze a CLASSPATH beállítást), a rendszer az alapértelmezéseket használja." }, { "ER_ILLEGAL_CHARACTER", "Kísérletet tett {0} értékének karakteres kiírására, de nem jeleníthető meg a megadott {1} kimeneti kódolással." }, { "ER_COULD_NOT_LOAD_METHOD_PROPERTY", "Nem lehet betölteni a(z) ''{0}'' tulajdonságfájlt a(z) ''{1}'' metódushoz (ellenőrizze a CLASSPATH beállítást)" }, { "ER_INVALID_PORT", "Érvénytelen portszám" }, { "ER_PORT_WHEN_HOST_NULL", "A portot nem állíthatja be, ha a hoszt null" }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "A hoszt nem jól formázott cím" }, { "ER_SCHEME_NOT_CONFORMANT", "A séma nem megfelelő." }, { "ER_SCHEME_FROM_NULL_STRING", "Nem lehet beállítani a sémát null karaktersorozatból" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Az elérési út érvénytelen vezérlő jelsorozatot tartalmaz" }, { "ER_PATH_INVALID_CHAR", "Az elérési út érvénytelen karaktert tartalmaz: {0}" }, { "ER_FRAG_INVALID_CHAR", "A töredék érvénytelen karaktert tartalmaz" }, { "ER_FRAG_WHEN_PATH_NULL", "A töredéket nem állíthatja be, ha az elérési út null" }, { "ER_FRAG_FOR_GENERIC_URI", "Csak általános URI-hoz állíthat be töredéket" }, { "ER_NO_SCHEME_IN_URI", "Nem található séma az URI-ban" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Az URI nem inicializálható üres paraméterekkel" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Nem adhat meg töredéket az elérési útban és a töredékben is" }, { "ER_NO_QUERY_STRING_IN_PATH", "Nem adhat meg lekérdezési karaktersorozatot az elérési útban és a lekérdezési karaktersorozatban" }, { "ER_NO_PORT_IF_NO_HOST", "Nem adhatja meg a portot, ha nincs megadva hoszt" }, { "ER_NO_USERINFO_IF_NO_HOST", "Nem adhatja meg a felhasználói információkat, ha nincs megadva hoszt" }, { "ER_XML_VERSION_NOT_SUPPORTED", "Figyelmeztetés: A kimeneti dokumentum kért verziója ''{0}''.  Az XML ezen verziója nem támogatott.  A kimeneti dokumentum verziója ''1.0'' lesz." }, { "ER_SCHEME_REQUIRED", "Sémára van szükség!" }, { "ER_FACTORY_PROPERTY_MISSING", "A SerializerFactory osztálynak átadott Properties objektumnak nincs ''{0}'' tulajdonsága." }, { "ER_ENCODING_NOT_SUPPORTED", "Figyelmeztetés: A(z) ''{0}'' kódolást nem támogatja a Java futási környezet." }, { "FEATURE_NOT_FOUND", "A(z) ''{0}'' paraméter nem ismerhető fel." }, { "FEATURE_NOT_SUPPORTED", "A(z) ''{0}'' paraméter ismert, de a kért érték nem állítható be." }, { "DOMSTRING_SIZE_ERR", "A létrejövő karaktersorozat túl hosszú, nem fér el egy DOMString-ben: ''{0}''." }, { "TYPE_MISMATCH_ERR", "A paraméternév értékének típusa nem kompatibilis a várt típussal." }, { "no-output-specified", "Az adatkiírás céljaként megadott érték üres volt." }, { "unsupported-encoding", "Nem támogatott kódolás." }, { "ER_UNABLE_TO_SERIALIZE_NODE", "A csomópont nem példányosítható." }, { "cdata-sections-splitted", "A CDATA szakasz legalább egy ']]>' lezáró jelzőt tartalmaz." }, { "ER_WARNING_WF_NOT_CHECKED", "A szabályos formázást ellenőrző példányt nem sikerült létrehozni.  A well-formed paraméter értéke true, de a szabályos formázást nem lehet ellenőrizni." }, { "wf-invalid-character", "A(z) ''{0}'' csomópont érvénytelen XML karaktereket tartalmaz." }, { "ER_WF_INVALID_CHARACTER_IN_COMMENT", "Érvénytelen XML karakter (Unicode: 0x{0}) szerepelt a megjegyzésben." }, { "ER_WF_INVALID_CHARACTER_IN_PI", "Érvénytelen XML karakter (Unicode: 0x{0}) szerepelt a feldolgozási utasításadatokban." }, { "ER_WF_INVALID_CHARACTER_IN_CDATA", "Érvénytelen XML karakter (Unicode: 0x{0}) szerepelt a CDATASection tartalmában." }, { "ER_WF_INVALID_CHARACTER_IN_TEXT", "Érvénytelen XML karakter (Unicode: 0x{0}) szerepelt a csomópont karakteradat tartalmában." }, { "wf-invalid-character-in-node-name", "Érvénytelen XML karakter található a(z) ''{1}'' nevű {0} csomópontban." }, { "ER_WF_DASH_IN_COMMENT", "A \"--\" karaktersorozat nem megengedett a megjegyzésekben." }, { "ER_WF_LT_IN_ATTVAL", "A(z) \"{0}\" elemtípussal társított \"{1}\" attribútum értéke nem tartalmazhat ''<'' karaktert." }, { "ER_WF_REF_TO_UNPARSED_ENT", "Az értelmezés nélküli \"&{0};\" entitáshivatkozás nem megengedett." }, { "ER_WF_REF_TO_EXTERNAL_ENT", "A(z) \"&{0};\" külső entitáshivatkozás nem megengedett egy attribútumértékben." }, { "ER_NS_PREFIX_CANNOT_BE_BOUND", "A(z) \"{0}\" előtag nem köthető a(z) \"{1}\" névtérhez." }, { "ER_NULL_LOCAL_ELEMENT_NAME", "A(z) \"{0}\" elem helyi neve null." }, { "ER_NULL_LOCAL_ATTR_NAME", "A(z) \"{0}\" attribútum helyi neve null." }, { "unbound-prefix-in-entity-reference", "A(z) \"{0}\" entitáscsomópont helyettesítő szövege a(z) \"{1}\" elemcsomópontot tartalmazza, amelynek nem kötött előtagja \"{2}\"." }, { "unbound-prefix-in-entity-reference", "A(z) \"{0}\" entitáscsomópont helyettesítő szövege a(z) \"{1}\" attribútum-csomópontot tartalmazza, amelynek nem kötött előtagja \"{2}\"." } };
/*  11:    */     
/*  12:    */ 
/*  13:    */ 
/*  14:    */ 
/*  15:    */ 
/*  16:    */ 
/*  17:    */ 
/*  18:    */ 
/*  19:    */ 
/*  20:    */ 
/*  21:    */ 
/*  22:    */ 
/*  23:    */ 
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:291 */     return contents;
/* 229:    */   }
/* 230:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.utils.SerializerMessages_hu
 * JD-Core Version:    0.7.0.1
 */