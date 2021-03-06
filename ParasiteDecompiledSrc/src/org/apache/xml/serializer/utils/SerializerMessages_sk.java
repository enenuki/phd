/*   1:    */ package org.apache.xml.serializer.utils;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ 
/*   5:    */ public class SerializerMessages_sk
/*   6:    */   extends ListResourceBundle
/*   7:    */ {
/*   8:    */   public Object[][] getContents()
/*   9:    */   {
/*  10: 73 */     Object[][] contents = { { "BAD_MSGKEY", "Kľúč správy ''{0}'' sa nenachádza v triede správ ''{1}''" }, { "BAD_MSGFORMAT", "Zlyhal formát správy ''{0}'' v triede správ ''{1}''." }, { "ER_SERIALIZER_NOT_CONTENTHANDLER", "Trieda serializátora ''{0}'' neimplementuje org.xml.sax.ContentHandler." }, { "ER_RESOURCE_COULD_NOT_FIND", "Prostriedok [ {0} ] nemohol byť nájdený.\n {1}" }, { "ER_RESOURCE_COULD_NOT_LOAD", "Prostriedok [ {0} ] sa nedal načítať: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Veľkosť vyrovnávacej pamäte <=0" }, { "ER_INVALID_UTF16_SURROGATE", "Bolo zistené neplatné nahradenie UTF-16: {0} ?" }, { "ER_OIERROR", "chyba IO" }, { "ER_ILLEGAL_ATTRIBUTE_POSITION", "Nie je možné pridať atribút {0} po uzloch potomka alebo pred vytvorením elementu.  Atribút bude ignorovaný." }, { "ER_NAMESPACE_PREFIX", "Názvový priestor pre predponu ''{0}'' nebol deklarovaný." }, { "ER_STRAY_ATTRIBUTE", "Atribút ''{0}'' je mimo prvku." }, { "ER_STRAY_NAMESPACE", "Deklarácia názvového priestoru ''{0}''=''{1}'' je mimo prvku." }, { "ER_COULD_NOT_LOAD_RESOURCE", "Nebolo možné zaviesť ''{0}'' (skontrolujte CLASSPATH), teraz sa používajú iba štandardné nastavenia" }, { "ER_ILLEGAL_CHARACTER", "Pokus o výstup znaku integrálnej hodnoty {0}, ktorá nie je reprezentovaná v zadanom výstupnom kódovaní {1}." }, { "ER_COULD_NOT_LOAD_METHOD_PROPERTY", "Nebolo možné zaviesť súbor vlastností ''{0}'' pre výstupnú metódu ''{1}'' (skontrolujte CLASSPATH)" }, { "ER_INVALID_PORT", "Neplatné číslo portu" }, { "ER_PORT_WHEN_HOST_NULL", "Nemôže byť stanovený port, ak je hostiteľ nulový" }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "Hostiteľ nie je správne formátovaná adresa" }, { "ER_SCHEME_NOT_CONFORMANT", "Nezhodná schéma." }, { "ER_SCHEME_FROM_NULL_STRING", "Nie je možné stanoviť schému z nulového reťazca" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Cesta obsahuje neplatnú únikovú sekvenciu" }, { "ER_PATH_INVALID_CHAR", "Cesta obsahuje neplatný znak: {0}" }, { "ER_FRAG_INVALID_CHAR", "Fragment obsahuje neplatný znak" }, { "ER_FRAG_WHEN_PATH_NULL", "Ak je cesta nulová, nemôže byť stanovený fragment" }, { "ER_FRAG_FOR_GENERIC_URI", "Fragment môže byť stanovený len pre všeobecné URI" }, { "ER_NO_SCHEME_IN_URI", "V URI nebola nájdená žiadna schéma" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Nie je možné inicializovať URI s prázdnymi parametrami" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Fragment nemôže byť zadaný v ceste, ani vo fragmente" }, { "ER_NO_QUERY_STRING_IN_PATH", "Reťazec dotazu nemôže byť zadaný v ceste a reťazci dotazu" }, { "ER_NO_PORT_IF_NO_HOST", "Ak nebol zadaný hostiteľ, možno nebol zadaný port" }, { "ER_NO_USERINFO_IF_NO_HOST", "Ak nebol zadaný hostiteľ, možno nebolo zadané userinfo" }, { "ER_XML_VERSION_NOT_SUPPORTED", "Varovanie:  Verzia výstupného dokumentu musí byť povinne ''{0}''.  Táto verzia XML nie je podporovaná.  Verzia výstupného dokumentu bude ''1.0''." }, { "ER_SCHEME_REQUIRED", "Je požadovaná schéma!" }, { "ER_FACTORY_PROPERTY_MISSING", "Objekt Properties, ktorý prešiel do SerializerFactory, nemá vlastnosť ''{0}''." }, { "ER_ENCODING_NOT_SUPPORTED", "Varovanie:  Java runtime nepodporuje kódovanie ''{0}''." }, { "FEATURE_NOT_FOUND", "Parameter ''{0}'' nebol rozpoznaný." }, { "FEATURE_NOT_SUPPORTED", "Parameter ''{0}'' bol rozpoznaný, ale vyžadovaná hodnota sa nedá nastaviť." }, { "DOMSTRING_SIZE_ERR", "Výsledný reťazec je príliš dlhý a nezmestí sa do DOMString: ''{0}''." }, { "TYPE_MISMATCH_ERR", "Typ hodnoty pre tento názov parametra je nekompatibilný s očakávaným typom hodnoty." }, { "no-output-specified", "Cieľ výstupu pre zapísanie údajov bol null." }, { "unsupported-encoding", "Bolo zaznamenané nepodporované kódovanie." }, { "ER_UNABLE_TO_SERIALIZE_NODE", "Uzol nebolo možné serializovať." }, { "cdata-sections-splitted", "Časť CDATA obsahuje jeden alebo viaceré označovače konca ']]>'." }, { "ER_WARNING_WF_NOT_CHECKED", "Nebolo možné vytvoriť inštanciu kontrolóra Well-Formedness.  Parameter well-formed bol nastavený na hodnotu true, ale kontrola well-formedness sa nedá vykonať." }, { "wf-invalid-character", "Uzol ''{0}'' obsahuje neplatné znaky XML." }, { "ER_WF_INVALID_CHARACTER_IN_COMMENT", "V komentári bol nájdený neplatný znak XML (Unicode: 0x{0})." }, { "ER_WF_INVALID_CHARACTER_IN_PI", "Pri spracovaní dát inštrukcií sa našiel neplatný znak XML (Unicode: 0x{0})." }, { "ER_WF_INVALID_CHARACTER_IN_CDATA", "V obsahu CDATASection sa našiel neplatný znak XML (Unicode: 0x{0})." }, { "ER_WF_INVALID_CHARACTER_IN_TEXT", "V obsahu znakových dát uzla sa našiel neplatný znak XML (Unicode: 0x{0})." }, { "wf-invalid-character-in-node-name", "V uzle {0} s názvom ''{1}'' sa našiel neplatný znak XML." }, { "ER_WF_DASH_IN_COMMENT", "Reťazec \"--\" nie je povolený v rámci komentárov." }, { "ER_WF_LT_IN_ATTVAL", "Hodnota atribútu \"{1}\", ktorá je priradená k prvku typu \"{0}\", nesmie obsahovať znak ''<''." }, { "ER_WF_REF_TO_UNPARSED_ENT", "Neanalyzovaný odkaz na entitu \"&{0};\" nie je povolený." }, { "ER_WF_REF_TO_EXTERNAL_ENT", "Odkaz na externú entitu \"&{0};\" nie je povolený v hodnote atribútu." }, { "ER_NS_PREFIX_CANNOT_BE_BOUND", "Predpona \"{0}\" nemôže byť naviazaná na názvový priestor \"{1}\"." }, { "ER_NULL_LOCAL_ELEMENT_NAME", "Lokálny názov prvku \"{0}\" je null." }, { "ER_NULL_LOCAL_ATTR_NAME", "Lokálny názov atribútu \"{0}\" je null." }, { "unbound-prefix-in-entity-reference", "Náhradný text pre uzol entity \"{0}\" obsahuje uzol prvku \"{1}\" s nenaviazanou predponou \"{2}\"." }, { "unbound-prefix-in-entity-reference", "Náhradný text uzla entity \"{0}\" obsahuje uzol atribútu \"{1}\" s nenaviazanou predponou \"{2}\"." } };
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
 * Qualified Name:     org.apache.xml.serializer.utils.SerializerMessages_sk
 * JD-Core Version:    0.7.0.1
 */