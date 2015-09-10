/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_es
/*   9:    */   extends ListResourceBundle
/*  10:    */ {
/*  11:    */   public static final String ER_FUNCTION_NOT_SUPPORTED = "ER_FUNCTION_NOT_SUPPORTED";
/*  12:    */   public static final String ER_CANNOT_OVERWRITE_CAUSE = "ER_CANNOT_OVERWRITE_CAUSE";
/*  13:    */   public static final String ER_NO_DEFAULT_IMPL = "ER_NO_DEFAULT_IMPL";
/*  14:    */   public static final String ER_CHUNKEDINTARRAY_NOT_SUPPORTED = "ER_CHUNKEDINTARRAY_NOT_SUPPORTED";
/*  15:    */   public static final String ER_OFFSET_BIGGER_THAN_SLOT = "ER_OFFSET_BIGGER_THAN_SLOT";
/*  16:    */   public static final String ER_COROUTINE_NOT_AVAIL = "ER_COROUTINE_NOT_AVAIL";
/*  17:    */   public static final String ER_COROUTINE_CO_EXIT = "ER_COROUTINE_CO_EXIT";
/*  18:    */   public static final String ER_COJOINROUTINESET_FAILED = "ER_COJOINROUTINESET_FAILED";
/*  19:    */   public static final String ER_COROUTINE_PARAM = "ER_COROUTINE_PARAM";
/*  20:    */   public static final String ER_PARSER_DOTERMINATE_ANSWERS = "ER_PARSER_DOTERMINATE_ANSWERS";
/*  21:    */   public static final String ER_NO_PARSE_CALL_WHILE_PARSING = "ER_NO_PARSE_CALL_WHILE_PARSING";
/*  22:    */   public static final String ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED = "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED";
/*  23:    */   public static final String ER_ITERATOR_AXIS_NOT_IMPLEMENTED = "ER_ITERATOR_AXIS_NOT_IMPLEMENTED";
/*  24:    */   public static final String ER_ITERATOR_CLONE_NOT_SUPPORTED = "ER_ITERATOR_CLONE_NOT_SUPPORTED";
/*  25:    */   public static final String ER_UNKNOWN_AXIS_TYPE = "ER_UNKNOWN_AXIS_TYPE";
/*  26:    */   public static final String ER_AXIS_NOT_SUPPORTED = "ER_AXIS_NOT_SUPPORTED";
/*  27:    */   public static final String ER_NO_DTMIDS_AVAIL = "ER_NO_DTMIDS_AVAIL";
/*  28:    */   public static final String ER_NOT_SUPPORTED = "ER_NOT_SUPPORTED";
/*  29:    */   public static final String ER_NODE_NON_NULL = "ER_NODE_NON_NULL";
/*  30:    */   public static final String ER_COULD_NOT_RESOLVE_NODE = "ER_COULD_NOT_RESOLVE_NODE";
/*  31:    */   public static final String ER_STARTPARSE_WHILE_PARSING = "ER_STARTPARSE_WHILE_PARSING";
/*  32:    */   public static final String ER_STARTPARSE_NEEDS_SAXPARSER = "ER_STARTPARSE_NEEDS_SAXPARSER";
/*  33:    */   public static final String ER_COULD_NOT_INIT_PARSER = "ER_COULD_NOT_INIT_PARSER";
/*  34:    */   public static final String ER_EXCEPTION_CREATING_POOL = "ER_EXCEPTION_CREATING_POOL";
/*  35:    */   public static final String ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE = "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE";
/*  36:    */   public static final String ER_SCHEME_REQUIRED = "ER_SCHEME_REQUIRED";
/*  37:    */   public static final String ER_NO_SCHEME_IN_URI = "ER_NO_SCHEME_IN_URI";
/*  38:    */   public static final String ER_NO_SCHEME_INURI = "ER_NO_SCHEME_INURI";
/*  39:    */   public static final String ER_PATH_INVALID_CHAR = "ER_PATH_INVALID_CHAR";
/*  40:    */   public static final String ER_SCHEME_FROM_NULL_STRING = "ER_SCHEME_FROM_NULL_STRING";
/*  41:    */   public static final String ER_SCHEME_NOT_CONFORMANT = "ER_SCHEME_NOT_CONFORMANT";
/*  42:    */   public static final String ER_HOST_ADDRESS_NOT_WELLFORMED = "ER_HOST_ADDRESS_NOT_WELLFORMED";
/*  43:    */   public static final String ER_PORT_WHEN_HOST_NULL = "ER_PORT_WHEN_HOST_NULL";
/*  44:    */   public static final String ER_INVALID_PORT = "ER_INVALID_PORT";
/*  45:    */   public static final String ER_FRAG_FOR_GENERIC_URI = "ER_FRAG_FOR_GENERIC_URI";
/*  46:    */   public static final String ER_FRAG_WHEN_PATH_NULL = "ER_FRAG_WHEN_PATH_NULL";
/*  47:    */   public static final String ER_FRAG_INVALID_CHAR = "ER_FRAG_INVALID_CHAR";
/*  48:    */   public static final String ER_PARSER_IN_USE = "ER_PARSER_IN_USE";
/*  49:    */   public static final String ER_CANNOT_CHANGE_WHILE_PARSING = "ER_CANNOT_CHANGE_WHILE_PARSING";
/*  50:    */   public static final String ER_SELF_CAUSATION_NOT_PERMITTED = "ER_SELF_CAUSATION_NOT_PERMITTED";
/*  51:    */   public static final String ER_NO_USERINFO_IF_NO_HOST = "ER_NO_USERINFO_IF_NO_HOST";
/*  52:    */   public static final String ER_NO_PORT_IF_NO_HOST = "ER_NO_PORT_IF_NO_HOST";
/*  53:    */   public static final String ER_NO_QUERY_STRING_IN_PATH = "ER_NO_QUERY_STRING_IN_PATH";
/*  54:    */   public static final String ER_NO_FRAGMENT_STRING_IN_PATH = "ER_NO_FRAGMENT_STRING_IN_PATH";
/*  55:    */   public static final String ER_CANNOT_INIT_URI_EMPTY_PARMS = "ER_CANNOT_INIT_URI_EMPTY_PARMS";
/*  56:    */   public static final String ER_METHOD_NOT_SUPPORTED = "ER_METHOD_NOT_SUPPORTED";
/*  57:    */   public static final String ER_INCRSAXSRCFILTER_NOT_RESTARTABLE = "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE";
/*  58:    */   public static final String ER_XMLRDR_NOT_BEFORE_STARTPARSE = "ER_XMLRDR_NOT_BEFORE_STARTPARSE";
/*  59:    */   public static final String ER_AXIS_TRAVERSER_NOT_SUPPORTED = "ER_AXIS_TRAVERSER_NOT_SUPPORTED";
/*  60:    */   public static final String ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER = "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER";
/*  61:    */   public static final String ER_SYSTEMID_UNKNOWN = "ER_SYSTEMID_UNKNOWN";
/*  62:    */   public static final String ER_LOCATION_UNKNOWN = "ER_LOCATION_UNKNOWN";
/*  63:    */   public static final String ER_PREFIX_MUST_RESOLVE = "ER_PREFIX_MUST_RESOLVE";
/*  64:    */   public static final String ER_CREATEDOCUMENT_NOT_SUPPORTED = "ER_CREATEDOCUMENT_NOT_SUPPORTED";
/*  65:    */   public static final String ER_CHILD_HAS_NO_OWNER_DOCUMENT = "ER_CHILD_HAS_NO_OWNER_DOCUMENT";
/*  66:    */   public static final String ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT = "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT";
/*  67:    */   public static final String ER_CANT_OUTPUT_TEXT_BEFORE_DOC = "ER_CANT_OUTPUT_TEXT_BEFORE_DOC";
/*  68:    */   public static final String ER_CANT_HAVE_MORE_THAN_ONE_ROOT = "ER_CANT_HAVE_MORE_THAN_ONE_ROOT";
/*  69:    */   public static final String ER_ARG_LOCALNAME_NULL = "ER_ARG_LOCALNAME_NULL";
/*  70:    */   public static final String ER_ARG_LOCALNAME_INVALID = "ER_ARG_LOCALNAME_INVALID";
/*  71:    */   public static final String ER_ARG_PREFIX_INVALID = "ER_ARG_PREFIX_INVALID";
/*  72:    */   public static final String ER_NAME_CANT_START_WITH_COLON = "ER_NAME_CANT_START_WITH_COLON";
/*  73:    */   
/*  74:    */   public Object[][] getContents()
/*  75:    */   {
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "¡Función no soportada!" }, { "ER_CANNOT_OVERWRITE_CAUSE", "No se puede escribir encima de la causa" }, { "ER_NO_DEFAULT_IMPL", "No se ha encontrado una implementación predeterminada " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) no soportada actualmente" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "El desplazamiento es mayor que el espacio" }, { "ER_COROUTINE_NOT_AVAIL", "Corrutina no disponible, id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager ha recibido una petición co_exit()" }, { "ER_COJOINROUTINESET_FAILED", "Anomalía de co_joinCoroutineSet()" }, { "ER_COROUTINE_PARAM", "Error del parámetro de corrutina ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nINESPERADO: Respuestas doTerminate del analizador {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "No se puede llamar a parse mientras se está analizando" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Error: El iterador escrito para el eje {0} no está implementado" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Error: El iterador para el eje {0} no está implementado " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "La réplica del iterador no está soportada" }, { "ER_UNKNOWN_AXIS_TYPE", "Tipo de cruce de eje desconocido: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Cruzador de eje no soportado: {0}" }, { "ER_NO_DTMIDS_AVAIL", "No hay más ID de DTM disponibles" }, { "ER_NOT_SUPPORTED", "No soportado: {0}" }, { "ER_NODE_NON_NULL", "El nodo no debe ser nulo para getDTMHandleFromNode" }, { "ER_COULD_NOT_RESOLVE_NODE", "No se puede resolver el nodo como un manejador" }, { "ER_STARTPARSE_WHILE_PARSING", "No se puede llamar a startParse mientras se está analizando" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse necesita un SAXParser no nulo" }, { "ER_COULD_NOT_INIT_PARSER", "No se ha podido inicializar el analizador con" }, { "ER_EXCEPTION_CREATING_POOL", "Se ha producido una excepción al crear la nueva instancia de la agrupación" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "La vía de acceso contiene una secuencia de escape no válida" }, { "ER_SCHEME_REQUIRED", "¡Se necesita un esquema!" }, { "ER_NO_SCHEME_IN_URI", "No se ha encontrado un esquema en el URI: {0}" }, { "ER_NO_SCHEME_INURI", "No se ha encontrado un esquema en el URI" }, { "ER_PATH_INVALID_CHAR", "La vía de acceso contiene un carácter no válido: {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "No se puede establecer un esquema de una serie nula" }, { "ER_SCHEME_NOT_CONFORMANT", "El esquema no es compatible." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "El sistema principal no es una dirección bien formada" }, { "ER_PORT_WHEN_HOST_NULL", "No se puede establecer el puerto si el sistema principal es nulo" }, { "ER_INVALID_PORT", "Número de puerto no válido" }, { "ER_FRAG_FOR_GENERIC_URI", "Sólo se puede establecer el fragmento para un URI genérico" }, { "ER_FRAG_WHEN_PATH_NULL", "No se puede establecer el fragmento si la vía de acceso es nula" }, { "ER_FRAG_INVALID_CHAR", "El fragmento contiene un carácter no válido" }, { "ER_PARSER_IN_USE", "El analizador ya está en uso" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "No se puede cambiar {0} {1} mientras se analiza" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Autocausalidad no permitida" }, { "ER_NO_USERINFO_IF_NO_HOST", "No se puede especificar la información de usuario si no se ha especificado el sistema principal" }, { "ER_NO_PORT_IF_NO_HOST", "No se puede especificar el puerto si no se ha especificado el sistema principal" }, { "ER_NO_QUERY_STRING_IN_PATH", "No se puede especificar la serie de consulta en la vía de acceso y en la serie de consulta" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "No se puede especificar el fragmento en la vía de acceso y en el fragmento" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "No se puede inicializar el URI con parámetros vacíos" }, { "ER_METHOD_NOT_SUPPORTED", "El método no está aún soportado " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "IncrementalSAXSource_Filter no es actualmente reiniciable" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader no debe ir antes que la petición startParse" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Cruzador de eje no soportado: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "¡Se ha creado ListingErrorHandler con PrintWriter nulo!" }, { "ER_SYSTEMID_UNKNOWN", "SystemId desconocido" }, { "ER_LOCATION_UNKNOWN", "Ubicación del error desconocida" }, { "ER_PREFIX_MUST_RESOLVE", "El prefijo debe resolverse como un espacio de nombres: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "¡createDocument() no soportada en XPathContext!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "¡El hijo atributo no tiene un documento propietario!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "¡El hijo atributo no tiene un elemento documento propietario!" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "¡Aviso: no puede haber salida de texto antes del elemento documento!  Ignorando..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "¡No puede haber más de una raíz en DOM!" }, { "ER_ARG_LOCALNAME_NULL", "El argumento 'localName' es nulo" }, { "ER_ARG_LOCALNAME_INVALID", "Localname en QNAME debe ser un NCName válido" }, { "ER_ARG_PREFIX_INVALID", "El prefijo en QNAME debe ser un NCName válido" }, { "ER_NAME_CANT_START_WITH_COLON", "El nombre no puede empezar con dos puntos" }, { "BAD_CODE", "El parámetro para createMessage estaba fuera de los límites" }, { "FORMAT_FAILED", "Se ha generado una excepción durante la llamada messageFormat" }, { "line", "Línea núm." }, { "column", "Columna núm." } };
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static final XMLErrorResources loadResourceBundle(String className)
/*  80:    */     throws MissingResourceException
/*  81:    */   {
/*  82:379 */     Locale locale = Locale.getDefault();
/*  83:380 */     String suffix = getResourceSuffix(locale);
/*  84:    */     try
/*  85:    */     {
/*  86:386 */       return (XMLErrorResources)ResourceBundle.getBundle(className + suffix, locale);
/*  87:    */     }
/*  88:    */     catch (MissingResourceException e)
/*  89:    */     {
/*  90:    */       try
/*  91:    */       {
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("es", "ES"));
/*  93:    */       }
/*  94:    */       catch (MissingResourceException e2)
/*  95:    */       {
/*  96:404 */         throw new MissingResourceException("Could not load any resource bundles.", className, "");
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   private static final String getResourceSuffix(Locale locale)
/* 102:    */   {
/* 103:421 */     String suffix = "_" + locale.getLanguage();
/* 104:422 */     String country = locale.getCountry();
/* 105:424 */     if (country.equals("TW")) {
/* 106:425 */       suffix = suffix + "_" + country;
/* 107:    */     }
/* 108:427 */     return suffix;
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_es
 * JD-Core Version:    0.7.0.1
 */