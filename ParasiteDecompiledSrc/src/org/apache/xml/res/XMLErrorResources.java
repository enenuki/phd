/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Function not supported!" }, { "ER_CANNOT_OVERWRITE_CAUSE", "Cannot overwrite cause" }, { "ER_NO_DEFAULT_IMPL", "No default implementation found " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) not currently supported" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Offset bigger than slot" }, { "ER_COROUTINE_NOT_AVAIL", "Coroutine not available, id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager received co_exit() request" }, { "ER_COJOINROUTINESET_FAILED", "co_joinCoroutineSet() failed" }, { "ER_COROUTINE_PARAM", "Coroutine parameter error ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nUNEXPECTED: Parser doTerminate answers {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "parse may not be called while parsing" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Error: typed iterator for axis  {0} not implemented" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Error: iterator for axis {0} not implemented " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Iterator clone not supported" }, { "ER_UNKNOWN_AXIS_TYPE", "Unknown axis traversal type: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Axis traverser not supported: {0}" }, { "ER_NO_DTMIDS_AVAIL", "No more DTM IDs are available" }, { "ER_NOT_SUPPORTED", "Not supported: {0}" }, { "ER_NODE_NON_NULL", "Node must be non-null for getDTMHandleFromNode" }, { "ER_COULD_NOT_RESOLVE_NODE", "Could not resolve the node to a handle" }, { "ER_STARTPARSE_WHILE_PARSING", "startParse may not be called while parsing" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse needs a non-null SAXParser" }, { "ER_COULD_NOT_INIT_PARSER", "could not initialize parser with" }, { "ER_EXCEPTION_CREATING_POOL", "exception creating new instance for pool" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Path contains invalid escape sequence" }, { "ER_SCHEME_REQUIRED", "Scheme is required!" }, { "ER_NO_SCHEME_IN_URI", "No scheme found in URI: {0}" }, { "ER_NO_SCHEME_INURI", "No scheme found in URI" }, { "ER_PATH_INVALID_CHAR", "Path contains invalid character: {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "Cannot set scheme from null string" }, { "ER_SCHEME_NOT_CONFORMANT", "The scheme is not conformant." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "Host is not a well formed address" }, { "ER_PORT_WHEN_HOST_NULL", "Port cannot be set when host is null" }, { "ER_INVALID_PORT", "Invalid port number" }, { "ER_FRAG_FOR_GENERIC_URI", "Fragment can only be set for a generic URI" }, { "ER_FRAG_WHEN_PATH_NULL", "Fragment cannot be set when path is null" }, { "ER_FRAG_INVALID_CHAR", "Fragment contains invalid character" }, { "ER_PARSER_IN_USE", "Parser is already in use" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "Cannot change {0} {1} while parsing" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Self-causation not permitted" }, { "ER_NO_USERINFO_IF_NO_HOST", "Userinfo may not be specified if host is not specified" }, { "ER_NO_PORT_IF_NO_HOST", "Port may not be specified if host is not specified" }, { "ER_NO_QUERY_STRING_IN_PATH", "Query string cannot be specified in path and query string" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Fragment cannot be specified in both the path and fragment" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Cannot initialize URI with empty parameters" }, { "ER_METHOD_NOT_SUPPORTED", "Method not yet supported " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "IncrementalSAXSource_Filter not currently restartable" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader not before startParse request" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Axis traverser not supported: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "ListingErrorHandler created with null PrintWriter!" }, { "ER_SYSTEMID_UNKNOWN", "SystemId Unknown" }, { "ER_LOCATION_UNKNOWN", "Location of error unknown" }, { "ER_PREFIX_MUST_RESOLVE", "Prefix must resolve to a namespace: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "createDocument() not supported in XPathContext!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "Attribute child does not have an owner document!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "Attribute child does not have an owner document element!" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Warning: can't output text before document element!  Ignoring..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "Can't have more than one root on a DOM!" }, { "ER_ARG_LOCALNAME_NULL", "Argument 'localName' is null" }, { "ER_ARG_LOCALNAME_INVALID", "Localname in QNAME should be a valid NCName" }, { "ER_ARG_PREFIX_INVALID", "Prefix in QNAME should be a valid NCName" }, { "ER_NAME_CANT_START_WITH_COLON", "Name cannot start with a colon" }, { "BAD_CODE", "Parameter to createMessage was out of bounds" }, { "FORMAT_FAILED", "Exception thrown during messageFormat call" }, { "line", "Line #" }, { "column", "Column #" } };
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
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("en", "US"));
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources
 * JD-Core Version:    0.7.0.1
 */