/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_ca
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Aquesta funció no té suport." }, { "ER_CANNOT_OVERWRITE_CAUSE", "No es pot sobreescriure una causa" }, { "ER_NO_DEFAULT_IMPL", "No s'ha trobat cap implementació per defecte " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "Actualment no es dóna suport a ChunkedIntArray({0})" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "El desplaçament és més gran que la ranura" }, { "ER_COROUTINE_NOT_AVAIL", "Coroutine no està disponible, id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager ha rebut una sol·licitud co_exit()" }, { "ER_COJOINROUTINESET_FAILED", "S'ha produït un error a co_joinCoroutineSet()" }, { "ER_COROUTINE_PARAM", "Error de paràmetre coroutine ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nUNEXPECTED: doTerminate de l''analitzador respon {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "L'anàlisi no es pot cridar mentre s'està duent a terme" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Error: l''iterador de tipus de l''eix {0} no s''ha implementat" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Error: l''iterador de l''eix {0} no s''ha implementat " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "El clonatge de l'iterador no té suport" }, { "ER_UNKNOWN_AXIS_TYPE", "Tipus de commutació de l''eix desconeguda: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "La commutació de l''eix no té suport: {0}" }, { "ER_NO_DTMIDS_AVAIL", "No hi ha més ID de DTM disponibles" }, { "ER_NOT_SUPPORTED", "No té suport: {0}" }, { "ER_NODE_NON_NULL", "El node no ha de ser nul per a getDTMHandleFromNode" }, { "ER_COULD_NOT_RESOLVE_NODE", "No s'ha pogut resoldre el node en un manejador" }, { "ER_STARTPARSE_WHILE_PARSING", "startParse no es pot cridar mentre s'està duent a terme l'anàlisi" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse necessita un SAXParser que no sigui nul" }, { "ER_COULD_NOT_INIT_PARSER", "No s'ha pogut inicialitzar l'analitzador amb" }, { "ER_EXCEPTION_CREATING_POOL", "S'ha produït una excepció en crear una nova instància de l'agrupació" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "La via d'accés conté una seqüència d'escapament no vàlida" }, { "ER_SCHEME_REQUIRED", "Es necessita l'esquema" }, { "ER_NO_SCHEME_IN_URI", "No s''ha trobat cap esquema a l''URI: {0}" }, { "ER_NO_SCHEME_INURI", "No s'ha trobat cap esquema a l'URI" }, { "ER_PATH_INVALID_CHAR", "La via d''accés conté un caràcter no vàlid {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "No es pot establir un esquema des d'una cadena nul·la" }, { "ER_SCHEME_NOT_CONFORMANT", "L'esquema no té conformitat." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "El format de l'adreça del sistema principal no és el correcte" }, { "ER_PORT_WHEN_HOST_NULL", "El port no es pot establir quan el sistema principal és nul" }, { "ER_INVALID_PORT", "Número de port no vàlid" }, { "ER_FRAG_FOR_GENERIC_URI", "El fragment només es pot establir per a un URI genèric" }, { "ER_FRAG_WHEN_PATH_NULL", "El fragment no es pot establir si la via d'accés és nul·la" }, { "ER_FRAG_INVALID_CHAR", "El fragment conté un caràcter no vàlid" }, { "ER_PARSER_IN_USE", "L'analitzador ja s'està utilitzant" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "No es pot modificar {0} {1} mentre es duu a terme l''anàlisi" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "La causalitat pròpia no està permesa." }, { "ER_NO_USERINFO_IF_NO_HOST", "No es pot especificar informació de l'usuari si no s'especifica el sistema principal" }, { "ER_NO_PORT_IF_NO_HOST", "No es pot especificar el port si no s'especifica el sistema principal" }, { "ER_NO_QUERY_STRING_IN_PATH", "No es pot especificar una cadena de consulta en la via d'accés i la cadena de consulta" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "No es pot especificar un fragment tant en la via d'accés com en el fragment" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "No es pot inicialitzar l'URI amb paràmetres buits" }, { "ER_METHOD_NOT_SUPPORTED", "Aquest mètode encara no té suport " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "Ara mateix no es pot reiniciar IncrementalSAXSource_Filter" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader no es pot produir abans de la sol·licitud d'startParse" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "La commutació de l''eix no té suport: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "S'ha creat ListingErrorHandler amb PrintWriter nul" }, { "ER_SYSTEMID_UNKNOWN", "ID del sistema (SystemId) desconegut" }, { "ER_LOCATION_UNKNOWN", "Ubicació de l'error desconeguda" }, { "ER_PREFIX_MUST_RESOLVE", "El prefix s''ha de resoldre en un espai de noms: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "createDocument() no té suport a XPathContext" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "El subordinat de l'atribut no té un document de propietari." }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "El subordinat de l'atribut no té un element de document de propietari." }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Avís: no es pot produir text abans de l'element de document. Es passa per alt." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "No hi pot haver més d'una arrel en un DOM." }, { "ER_ARG_LOCALNAME_NULL", "L'argument 'localName' és nul." }, { "ER_ARG_LOCALNAME_INVALID", "El nom local de QNAME ha de ser un NCName vàlid." }, { "ER_ARG_PREFIX_INVALID", "El prefix de QNAME ha de ser un NCName vàlid." }, { "ER_NAME_CANT_START_WITH_COLON", "El nom no pot començar amb dos punts. " }, { "BAD_CODE", "El paràmetre de createMessage estava fora dels límits." }, { "FORMAT_FAILED", "S'ha generat una excepció durant la crida messageFormat." }, { "line", "Línia núm." }, { "column", "Columna núm." } };
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
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("ca", "ES"));
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_ca
 * JD-Core Version:    0.7.0.1
 */