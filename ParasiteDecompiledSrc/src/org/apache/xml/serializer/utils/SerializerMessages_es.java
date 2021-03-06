/*   1:    */ package org.apache.xml.serializer.utils;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ 
/*   5:    */ public class SerializerMessages_es
/*   6:    */   extends ListResourceBundle
/*   7:    */ {
/*   8:    */   public Object[][] getContents()
/*   9:    */   {
/*  10: 73 */     Object[][] contents = { { "BAD_MSGKEY", "La clave de mensaje ''{0}'' no está en la clase de mensaje ''{1}''" }, { "BAD_MSGFORMAT", "Se ha producido un error en el formato de mensaje ''{0}'' de la clase de mensaje ''{1}''." }, { "ER_SERIALIZER_NOT_CONTENTHANDLER", "La clase serializer ''{0}'' no implementa org.xml.sax.ContentHandler." }, { "ER_RESOURCE_COULD_NOT_FIND", "No se ha podido encontrar el recurso [ {0} ].\n {1}" }, { "ER_RESOURCE_COULD_NOT_LOAD", "No se ha podido cargar el recurso [ {0} ]: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Tamaño de almacenamiento intermedio <=0" }, { "ER_INVALID_UTF16_SURROGATE", "¿Se ha detectado un sustituto UTF-16 no válido: {0}?" }, { "ER_OIERROR", "Error de ES" }, { "ER_ILLEGAL_ATTRIBUTE_POSITION", "No se puede añadir el atributo {0} después de nodos hijo o antes de que se produzca un elemento.  Se ignorará el atributo." }, { "ER_NAMESPACE_PREFIX", "No se ha declarado el espacio de nombres para el prefijo ''{0}''." }, { "ER_STRAY_ATTRIBUTE", "Atributo ''{0}'' fuera del elemento." }, { "ER_STRAY_NAMESPACE", "Declaración del espacio de nombres ''{0}''=''{1}'' fuera del elemento." }, { "ER_COULD_NOT_LOAD_RESOURCE", "No se ha podido cargar ''{0}'' (compruebe la CLASSPATH), ahora sólo se están utilizando los valores predeterminados" }, { "ER_ILLEGAL_CHARACTER", "Se ha intentado dar salida a un carácter del valor integral {0} que no está representado en la codificación de salida especificada de {1}." }, { "ER_COULD_NOT_LOAD_METHOD_PROPERTY", "No se ha podido cargar el archivo de propiedades ''{0}'' para el método de salida ''{1}'' (compruebe la CLASSPATH)" }, { "ER_INVALID_PORT", "Número de puerto no válido" }, { "ER_PORT_WHEN_HOST_NULL", "No se puede establecer el puerto si el sistema principal es nulo" }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "El sistema principal no es una dirección bien formada" }, { "ER_SCHEME_NOT_CONFORMANT", "El esquema no es compatible." }, { "ER_SCHEME_FROM_NULL_STRING", "No se puede establecer un esquema de una serie nula" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "La vía de acceso contiene una secuencia de escape no válida" }, { "ER_PATH_INVALID_CHAR", "La vía de acceso contiene un carácter no válido: {0}" }, { "ER_FRAG_INVALID_CHAR", "El fragmento contiene un carácter no válido" }, { "ER_FRAG_WHEN_PATH_NULL", "No se puede establecer el fragmento si la vía de acceso es nula" }, { "ER_FRAG_FOR_GENERIC_URI", "Sólo se puede establecer el fragmento para un URI genérico" }, { "ER_NO_SCHEME_IN_URI", "No se ha encontrado un esquema en el URI" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "No se puede inicializar el URI con parámetros vacíos" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "No se puede especificar el fragmento en la vía de acceso y en el fragmento" }, { "ER_NO_QUERY_STRING_IN_PATH", "No se puede especificar la serie de consulta en la vía de acceso y en la serie de consulta" }, { "ER_NO_PORT_IF_NO_HOST", "No se puede especificar el puerto si no se ha especificado el sistema principal" }, { "ER_NO_USERINFO_IF_NO_HOST", "No se puede especificar la información de usuario si no se ha especificado el sistema principal" }, { "ER_XML_VERSION_NOT_SUPPORTED", "Aviso: la versión del documento de salida tiene que ser ''{0}''.  No se admite esta versión de XML.  La versión del documento de salida será ''1.0''." }, { "ER_SCHEME_REQUIRED", "¡Se necesita un esquema!" }, { "ER_FACTORY_PROPERTY_MISSING", "El objeto Properties pasado a SerializerFactory no tiene una propiedad ''{0}''." }, { "ER_ENCODING_NOT_SUPPORTED", "Aviso: La codificación ''{0}'' no está soportada por Java Runtime." }, { "FEATURE_NOT_FOUND", "El parámetro ''{0}'' no se reconoce." }, { "FEATURE_NOT_SUPPORTED", "Se reconoce el parámetro ''{0}'' pero no puede establecerse el valor solicitado." }, { "DOMSTRING_SIZE_ERR", "La serie producida es demasiado larga para ajustarse a DOMString: ''{0}''." }, { "TYPE_MISMATCH_ERR", "El tipo de valor para este nombre de parámetro es incompatible con el tipo de valor esperado." }, { "no-output-specified", "El destino de salida de escritura de los datos es nulo." }, { "unsupported-encoding", "Se ha encontrado una codificación no soportada." }, { "ER_UNABLE_TO_SERIALIZE_NODE", "No se ha podido serializar el nodo." }, { "cdata-sections-splitted", "La sección CDATA contiene uno o más marcadores ']]>' de terminación." }, { "ER_WARNING_WF_NOT_CHECKED", "No se ha podido crear una instancia del comprobador de gramática correcta.  El parámetro well-formed se ha establecido en true pero no se puede realizar la comprobación de gramática correcta." }, { "wf-invalid-character", "El nodo ''{0}'' contiene caracteres XML no válidos." }, { "ER_WF_INVALID_CHARACTER_IN_COMMENT", "Se ha encontrado un carácter XML no válido (Unicode: 0x{0}) en el comentario." }, { "ER_WF_INVALID_CHARACTER_IN_PI", "Se ha encontrado un carácter XML no válido (Unicode: 0x{0}) en los datos de la instrucción de proceso." }, { "ER_WF_INVALID_CHARACTER_IN_CDATA", "Se ha encontrado un carácter XML no válido (Unicode: 0x{0}) en el contenido de CDATASection." }, { "ER_WF_INVALID_CHARACTER_IN_TEXT", "Se ha encontrado un carácter XML no válido (Unicode: 0x{0}) en el contenido de datos de caracteres del nodo." }, { "wf-invalid-character-in-node-name", "Se ha encontrado un carácter o caracteres XML no válidos en el nodo {0} denominado ''{1}''." }, { "ER_WF_DASH_IN_COMMENT", "No se permite la serie \"--\" dentro de los comentarios." }, { "ER_WF_LT_IN_ATTVAL", "El valor del atributo \"{1}\" asociado a un tipo de elemento \"{0}\" no debe contener el carácter ''''<''''." }, { "ER_WF_REF_TO_UNPARSED_ENT", "No se permite la referencia de entidad no analizada \"&{0};\"." }, { "ER_WF_REF_TO_EXTERNAL_ENT", "La referencia de entidad externa \"&{0};\" no está permitida en un valor de atributo." }, { "ER_NS_PREFIX_CANNOT_BE_BOUND", "No se puede encontrar el prefijo \"{0}\" en el espacio de nombres \"{1}\"." }, { "ER_NULL_LOCAL_ELEMENT_NAME", "El nombre local del elemento \"{0}\" es null." }, { "ER_NULL_LOCAL_ATTR_NAME", "El nombre local del atributo \"{0}\" es null." }, { "unbound-prefix-in-entity-reference", "El texto de sustitución del nodo de entidad \"{0}\" contiene un nodo de elemento \"{1}\" con un prefijo no enlazado \"{2}\"." }, { "unbound-prefix-in-entity-reference", "El texto de sustitución del nodo de entidad \"{0}\" contiene un nodo de atributo \"{1}\" con un prefijo no enlazado \"{2}\"." } };
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
 * Qualified Name:     org.apache.xml.serializer.utils.SerializerMessages_es
 * JD-Core Version:    0.7.0.1
 */