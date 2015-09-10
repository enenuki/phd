/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_ja
/*    9:     */   extends ListResourceBundle
/*   10:     */ {
/*   11:     */   public static final int MAX_CODE = 201;
/*   12:     */   public static final int MAX_WARNING = 29;
/*   13:     */   public static final int MAX_OTHERS = 55;
/*   14:     */   public static final int MAX_MESSAGES = 231;
/*   15:     */   public static final String ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX = "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX";
/*   16:     */   public static final String ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT = "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT";
/*   17:     */   public static final String ER_NO_CURLYBRACE = "ER_NO_CURLYBRACE";
/*   18:     */   public static final String ER_FUNCTION_NOT_SUPPORTED = "ER_FUNCTION_NOT_SUPPORTED";
/*   19:     */   public static final String ER_ILLEGAL_ATTRIBUTE = "ER_ILLEGAL_ATTRIBUTE";
/*   20:     */   public static final String ER_NULL_SOURCENODE_APPLYIMPORTS = "ER_NULL_SOURCENODE_APPLYIMPORTS";
/*   21:     */   public static final String ER_CANNOT_ADD = "ER_CANNOT_ADD";
/*   22:     */   public static final String ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES = "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES";
/*   23:     */   public static final String ER_NO_NAME_ATTRIB = "ER_NO_NAME_ATTRIB";
/*   24:     */   public static final String ER_TEMPLATE_NOT_FOUND = "ER_TEMPLATE_NOT_FOUND";
/*   25:     */   public static final String ER_CANT_RESOLVE_NAME_AVT = "ER_CANT_RESOLVE_NAME_AVT";
/*   26:     */   public static final String ER_REQUIRES_ATTRIB = "ER_REQUIRES_ATTRIB";
/*   27:     */   public static final String ER_MUST_HAVE_TEST_ATTRIB = "ER_MUST_HAVE_TEST_ATTRIB";
/*   28:     */   public static final String ER_BAD_VAL_ON_LEVEL_ATTRIB = "ER_BAD_VAL_ON_LEVEL_ATTRIB";
/*   29:     */   public static final String ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML = "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML";
/*   30:     */   public static final String ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME = "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME";
/*   31:     */   public static final String ER_NEED_MATCH_ATTRIB = "ER_NEED_MATCH_ATTRIB";
/*   32:     */   public static final String ER_NEED_NAME_OR_MATCH_ATTRIB = "ER_NEED_NAME_OR_MATCH_ATTRIB";
/*   33:     */   public static final String ER_CANT_RESOLVE_NSPREFIX = "ER_CANT_RESOLVE_NSPREFIX";
/*   34:     */   public static final String ER_ILLEGAL_VALUE = "ER_ILLEGAL_VALUE";
/*   35:     */   public static final String ER_NO_OWNERDOC = "ER_NO_OWNERDOC";
/*   36:     */   public static final String ER_ELEMTEMPLATEELEM_ERR = "ER_ELEMTEMPLATEELEM_ERR";
/*   37:     */   public static final String ER_NULL_CHILD = "ER_NULL_CHILD";
/*   38:     */   public static final String ER_NEED_SELECT_ATTRIB = "ER_NEED_SELECT_ATTRIB";
/*   39:     */   public static final String ER_NEED_TEST_ATTRIB = "ER_NEED_TEST_ATTRIB";
/*   40:     */   public static final String ER_NEED_NAME_ATTRIB = "ER_NEED_NAME_ATTRIB";
/*   41:     */   public static final String ER_NO_CONTEXT_OWNERDOC = "ER_NO_CONTEXT_OWNERDOC";
/*   42:     */   public static final String ER_COULD_NOT_CREATE_XML_PROC_LIAISON = "ER_COULD_NOT_CREATE_XML_PROC_LIAISON";
/*   43:     */   public static final String ER_PROCESS_NOT_SUCCESSFUL = "ER_PROCESS_NOT_SUCCESSFUL";
/*   44:     */   public static final String ER_NOT_SUCCESSFUL = "ER_NOT_SUCCESSFUL";
/*   45:     */   public static final String ER_ENCODING_NOT_SUPPORTED = "ER_ENCODING_NOT_SUPPORTED";
/*   46:     */   public static final String ER_COULD_NOT_CREATE_TRACELISTENER = "ER_COULD_NOT_CREATE_TRACELISTENER";
/*   47:     */   public static final String ER_KEY_REQUIRES_NAME_ATTRIB = "ER_KEY_REQUIRES_NAME_ATTRIB";
/*   48:     */   public static final String ER_KEY_REQUIRES_MATCH_ATTRIB = "ER_KEY_REQUIRES_MATCH_ATTRIB";
/*   49:     */   public static final String ER_KEY_REQUIRES_USE_ATTRIB = "ER_KEY_REQUIRES_USE_ATTRIB";
/*   50:     */   public static final String ER_REQUIRES_ELEMENTS_ATTRIB = "ER_REQUIRES_ELEMENTS_ATTRIB";
/*   51:     */   public static final String ER_MISSING_PREFIX_ATTRIB = "ER_MISSING_PREFIX_ATTRIB";
/*   52:     */   public static final String ER_BAD_STYLESHEET_URL = "ER_BAD_STYLESHEET_URL";
/*   53:     */   public static final String ER_FILE_NOT_FOUND = "ER_FILE_NOT_FOUND";
/*   54:     */   public static final String ER_IOEXCEPTION = "ER_IOEXCEPTION";
/*   55:     */   public static final String ER_NO_HREF_ATTRIB = "ER_NO_HREF_ATTRIB";
/*   56:     */   public static final String ER_STYLESHEET_INCLUDES_ITSELF = "ER_STYLESHEET_INCLUDES_ITSELF";
/*   57:     */   public static final String ER_PROCESSINCLUDE_ERROR = "ER_PROCESSINCLUDE_ERROR";
/*   58:     */   public static final String ER_MISSING_LANG_ATTRIB = "ER_MISSING_LANG_ATTRIB";
/*   59:     */   public static final String ER_MISSING_CONTAINER_ELEMENT_COMPONENT = "ER_MISSING_CONTAINER_ELEMENT_COMPONENT";
/*   60:     */   public static final String ER_CAN_ONLY_OUTPUT_TO_ELEMENT = "ER_CAN_ONLY_OUTPUT_TO_ELEMENT";
/*   61:     */   public static final String ER_PROCESS_ERROR = "ER_PROCESS_ERROR";
/*   62:     */   public static final String ER_UNIMPLNODE_ERROR = "ER_UNIMPLNODE_ERROR";
/*   63:     */   public static final String ER_NO_SELECT_EXPRESSION = "ER_NO_SELECT_EXPRESSION";
/*   64:     */   public static final String ER_CANNOT_SERIALIZE_XSLPROCESSOR = "ER_CANNOT_SERIALIZE_XSLPROCESSOR";
/*   65:     */   public static final String ER_NO_INPUT_STYLESHEET = "ER_NO_INPUT_STYLESHEET";
/*   66:     */   public static final String ER_FAILED_PROCESS_STYLESHEET = "ER_FAILED_PROCESS_STYLESHEET";
/*   67:     */   public static final String ER_COULDNT_PARSE_DOC = "ER_COULDNT_PARSE_DOC";
/*   68:     */   public static final String ER_COULDNT_FIND_FRAGMENT = "ER_COULDNT_FIND_FRAGMENT";
/*   69:     */   public static final String ER_NODE_NOT_ELEMENT = "ER_NODE_NOT_ELEMENT";
/*   70:     */   public static final String ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB = "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB";
/*   71:     */   public static final String ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB = "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB";
/*   72:     */   public static final String ER_NO_CLONE_OF_DOCUMENT_FRAG = "ER_NO_CLONE_OF_DOCUMENT_FRAG";
/*   73:     */   public static final String ER_CANT_CREATE_ITEM = "ER_CANT_CREATE_ITEM";
/*   74:     */   public static final String ER_XMLSPACE_ILLEGAL_VALUE = "ER_XMLSPACE_ILLEGAL_VALUE";
/*   75:     */   public static final String ER_NO_XSLKEY_DECLARATION = "ER_NO_XSLKEY_DECLARATION";
/*   76:     */   public static final String ER_CANT_CREATE_URL = "ER_CANT_CREATE_URL";
/*   77:     */   public static final String ER_XSLFUNCTIONS_UNSUPPORTED = "ER_XSLFUNCTIONS_UNSUPPORTED";
/*   78:     */   public static final String ER_PROCESSOR_ERROR = "ER_PROCESSOR_ERROR";
/*   79:     */   public static final String ER_NOT_ALLOWED_INSIDE_STYLESHEET = "ER_NOT_ALLOWED_INSIDE_STYLESHEET";
/*   80:     */   public static final String ER_RESULTNS_NOT_SUPPORTED = "ER_RESULTNS_NOT_SUPPORTED";
/*   81:     */   public static final String ER_DEFAULTSPACE_NOT_SUPPORTED = "ER_DEFAULTSPACE_NOT_SUPPORTED";
/*   82:     */   public static final String ER_INDENTRESULT_NOT_SUPPORTED = "ER_INDENTRESULT_NOT_SUPPORTED";
/*   83:     */   public static final String ER_ILLEGAL_ATTRIB = "ER_ILLEGAL_ATTRIB";
/*   84:     */   public static final String ER_UNKNOWN_XSL_ELEM = "ER_UNKNOWN_XSL_ELEM";
/*   85:     */   public static final String ER_BAD_XSLSORT_USE = "ER_BAD_XSLSORT_USE";
/*   86:     */   public static final String ER_MISPLACED_XSLWHEN = "ER_MISPLACED_XSLWHEN";
/*   87:     */   public static final String ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE = "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE";
/*   88:     */   public static final String ER_MISPLACED_XSLOTHERWISE = "ER_MISPLACED_XSLOTHERWISE";
/*   89:     */   public static final String ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE = "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE";
/*   90:     */   public static final String ER_NOT_ALLOWED_INSIDE_TEMPLATE = "ER_NOT_ALLOWED_INSIDE_TEMPLATE";
/*   91:     */   public static final String ER_UNKNOWN_EXT_NS_PREFIX = "ER_UNKNOWN_EXT_NS_PREFIX";
/*   92:     */   public static final String ER_IMPORTS_AS_FIRST_ELEM = "ER_IMPORTS_AS_FIRST_ELEM";
/*   93:     */   public static final String ER_IMPORTING_ITSELF = "ER_IMPORTING_ITSELF";
/*   94:     */   public static final String ER_XMLSPACE_ILLEGAL_VAL = "ER_XMLSPACE_ILLEGAL_VAL";
/*   95:     */   public static final String ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL = "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL";
/*   96:     */   public static final String ER_SAX_EXCEPTION = "ER_SAX_EXCEPTION";
/*   97:     */   public static final String ER_XSLT_ERROR = "ER_XSLT_ERROR";
/*   98:     */   public static final String ER_CURRENCY_SIGN_ILLEGAL = "ER_CURRENCY_SIGN_ILLEGAL";
/*   99:     */   public static final String ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM = "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM";
/*  100:     */   public static final String ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER = "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER";
/*  101:     */   public static final String ER_REDIRECT_COULDNT_GET_FILENAME = "ER_REDIRECT_COULDNT_GET_FILENAME";
/*  102:     */   public static final String ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT = "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT";
/*  103:     */   public static final String ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX = "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX";
/*  104:     */   public static final String ER_MISSING_NS_URI = "ER_MISSING_NS_URI";
/*  105:     */   public static final String ER_MISSING_ARG_FOR_OPTION = "ER_MISSING_ARG_FOR_OPTION";
/*  106:     */   public static final String ER_INVALID_OPTION = "ER_INVALID_OPTION";
/*  107:     */   public static final String ER_MALFORMED_FORMAT_STRING = "ER_MALFORMED_FORMAT_STRING";
/*  108:     */   public static final String ER_STYLESHEET_REQUIRES_VERSION_ATTRIB = "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB";
/*  109:     */   public static final String ER_ILLEGAL_ATTRIBUTE_VALUE = "ER_ILLEGAL_ATTRIBUTE_VALUE";
/*  110:     */   public static final String ER_CHOOSE_REQUIRES_WHEN = "ER_CHOOSE_REQUIRES_WHEN";
/*  111:     */   public static final String ER_NO_APPLY_IMPORT_IN_FOR_EACH = "ER_NO_APPLY_IMPORT_IN_FOR_EACH";
/*  112:     */   public static final String ER_CANT_USE_DTM_FOR_OUTPUT = "ER_CANT_USE_DTM_FOR_OUTPUT";
/*  113:     */   public static final String ER_CANT_USE_DTM_FOR_INPUT = "ER_CANT_USE_DTM_FOR_INPUT";
/*  114:     */   public static final String ER_CALL_TO_EXT_FAILED = "ER_CALL_TO_EXT_FAILED";
/*  115:     */   public static final String ER_PREFIX_MUST_RESOLVE = "ER_PREFIX_MUST_RESOLVE";
/*  116:     */   public static final String ER_INVALID_UTF16_SURROGATE = "ER_INVALID_UTF16_SURROGATE";
/*  117:     */   public static final String ER_XSLATTRSET_USED_ITSELF = "ER_XSLATTRSET_USED_ITSELF";
/*  118:     */   public static final String ER_CANNOT_MIX_XERCESDOM = "ER_CANNOT_MIX_XERCESDOM";
/*  119:     */   public static final String ER_TOO_MANY_LISTENERS = "ER_TOO_MANY_LISTENERS";
/*  120:     */   public static final String ER_IN_ELEMTEMPLATEELEM_READOBJECT = "ER_IN_ELEMTEMPLATEELEM_READOBJECT";
/*  121:     */   public static final String ER_DUPLICATE_NAMED_TEMPLATE = "ER_DUPLICATE_NAMED_TEMPLATE";
/*  122:     */   public static final String ER_INVALID_KEY_CALL = "ER_INVALID_KEY_CALL";
/*  123:     */   public static final String ER_REFERENCING_ITSELF = "ER_REFERENCING_ITSELF";
/*  124:     */   public static final String ER_ILLEGAL_DOMSOURCE_INPUT = "ER_ILLEGAL_DOMSOURCE_INPUT";
/*  125:     */   public static final String ER_CLASS_NOT_FOUND_FOR_OPTION = "ER_CLASS_NOT_FOUND_FOR_OPTION";
/*  126:     */   public static final String ER_REQUIRED_ELEM_NOT_FOUND = "ER_REQUIRED_ELEM_NOT_FOUND";
/*  127:     */   public static final String ER_INPUT_CANNOT_BE_NULL = "ER_INPUT_CANNOT_BE_NULL";
/*  128:     */   public static final String ER_URI_CANNOT_BE_NULL = "ER_URI_CANNOT_BE_NULL";
/*  129:     */   public static final String ER_FILE_CANNOT_BE_NULL = "ER_FILE_CANNOT_BE_NULL";
/*  130:     */   public static final String ER_SOURCE_CANNOT_BE_NULL = "ER_SOURCE_CANNOT_BE_NULL";
/*  131:     */   public static final String ER_CANNOT_INIT_BSFMGR = "ER_CANNOT_INIT_BSFMGR";
/*  132:     */   public static final String ER_CANNOT_CMPL_EXTENSN = "ER_CANNOT_CMPL_EXTENSN";
/*  133:     */   public static final String ER_CANNOT_CREATE_EXTENSN = "ER_CANNOT_CREATE_EXTENSN";
/*  134:     */   public static final String ER_INSTANCE_MTHD_CALL_REQUIRES = "ER_INSTANCE_MTHD_CALL_REQUIRES";
/*  135:     */   public static final String ER_INVALID_ELEMENT_NAME = "ER_INVALID_ELEMENT_NAME";
/*  136:     */   public static final String ER_ELEMENT_NAME_METHOD_STATIC = "ER_ELEMENT_NAME_METHOD_STATIC";
/*  137:     */   public static final String ER_EXTENSION_FUNC_UNKNOWN = "ER_EXTENSION_FUNC_UNKNOWN";
/*  138:     */   public static final String ER_MORE_MATCH_CONSTRUCTOR = "ER_MORE_MATCH_CONSTRUCTOR";
/*  139:     */   public static final String ER_MORE_MATCH_METHOD = "ER_MORE_MATCH_METHOD";
/*  140:     */   public static final String ER_MORE_MATCH_ELEMENT = "ER_MORE_MATCH_ELEMENT";
/*  141:     */   public static final String ER_INVALID_CONTEXT_PASSED = "ER_INVALID_CONTEXT_PASSED";
/*  142:     */   public static final String ER_POOL_EXISTS = "ER_POOL_EXISTS";
/*  143:     */   public static final String ER_NO_DRIVER_NAME = "ER_NO_DRIVER_NAME";
/*  144:     */   public static final String ER_NO_URL = "ER_NO_URL";
/*  145:     */   public static final String ER_POOL_SIZE_LESSTHAN_ONE = "ER_POOL_SIZE_LESSTHAN_ONE";
/*  146:     */   public static final String ER_INVALID_DRIVER = "ER_INVALID_DRIVER";
/*  147:     */   public static final String ER_NO_STYLESHEETROOT = "ER_NO_STYLESHEETROOT";
/*  148:     */   public static final String ER_ILLEGAL_XMLSPACE_VALUE = "ER_ILLEGAL_XMLSPACE_VALUE";
/*  149:     */   public static final String ER_PROCESSFROMNODE_FAILED = "ER_PROCESSFROMNODE_FAILED";
/*  150:     */   public static final String ER_RESOURCE_COULD_NOT_LOAD = "ER_RESOURCE_COULD_NOT_LOAD";
/*  151:     */   public static final String ER_BUFFER_SIZE_LESSTHAN_ZERO = "ER_BUFFER_SIZE_LESSTHAN_ZERO";
/*  152:     */   public static final String ER_UNKNOWN_ERROR_CALLING_EXTENSION = "ER_UNKNOWN_ERROR_CALLING_EXTENSION";
/*  153:     */   public static final String ER_NO_NAMESPACE_DECL = "ER_NO_NAMESPACE_DECL";
/*  154:     */   public static final String ER_ELEM_CONTENT_NOT_ALLOWED = "ER_ELEM_CONTENT_NOT_ALLOWED";
/*  155:     */   public static final String ER_STYLESHEET_DIRECTED_TERMINATION = "ER_STYLESHEET_DIRECTED_TERMINATION";
/*  156:     */   public static final String ER_ONE_OR_TWO = "ER_ONE_OR_TWO";
/*  157:     */   public static final String ER_TWO_OR_THREE = "ER_TWO_OR_THREE";
/*  158:     */   public static final String ER_COULD_NOT_LOAD_RESOURCE = "ER_COULD_NOT_LOAD_RESOURCE";
/*  159:     */   public static final String ER_CANNOT_INIT_DEFAULT_TEMPLATES = "ER_CANNOT_INIT_DEFAULT_TEMPLATES";
/*  160:     */   public static final String ER_RESULT_NULL = "ER_RESULT_NULL";
/*  161:     */   public static final String ER_RESULT_COULD_NOT_BE_SET = "ER_RESULT_COULD_NOT_BE_SET";
/*  162:     */   public static final String ER_NO_OUTPUT_SPECIFIED = "ER_NO_OUTPUT_SPECIFIED";
/*  163:     */   public static final String ER_CANNOT_TRANSFORM_TO_RESULT_TYPE = "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE";
/*  164:     */   public static final String ER_CANNOT_TRANSFORM_SOURCE_TYPE = "ER_CANNOT_TRANSFORM_SOURCE_TYPE";
/*  165:     */   public static final String ER_NULL_CONTENT_HANDLER = "ER_NULL_CONTENT_HANDLER";
/*  166:     */   public static final String ER_NULL_ERROR_HANDLER = "ER_NULL_ERROR_HANDLER";
/*  167:     */   public static final String ER_CANNOT_CALL_PARSE = "ER_CANNOT_CALL_PARSE";
/*  168:     */   public static final String ER_NO_PARENT_FOR_FILTER = "ER_NO_PARENT_FOR_FILTER";
/*  169:     */   public static final String ER_NO_STYLESHEET_IN_MEDIA = "ER_NO_STYLESHEET_IN_MEDIA";
/*  170:     */   public static final String ER_NO_STYLESHEET_PI = "ER_NO_STYLESHEET_PI";
/*  171:     */   public static final String ER_NOT_SUPPORTED = "ER_NOT_SUPPORTED";
/*  172:     */   public static final String ER_PROPERTY_VALUE_BOOLEAN = "ER_PROPERTY_VALUE_BOOLEAN";
/*  173:     */   public static final String ER_COULD_NOT_FIND_EXTERN_SCRIPT = "ER_COULD_NOT_FIND_EXTERN_SCRIPT";
/*  174:     */   public static final String ER_RESOURCE_COULD_NOT_FIND = "ER_RESOURCE_COULD_NOT_FIND";
/*  175:     */   public static final String ER_OUTPUT_PROPERTY_NOT_RECOGNIZED = "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED";
/*  176:     */   public static final String ER_FAILED_CREATING_ELEMLITRSLT = "ER_FAILED_CREATING_ELEMLITRSLT";
/*  177:     */   public static final String ER_VALUE_SHOULD_BE_NUMBER = "ER_VALUE_SHOULD_BE_NUMBER";
/*  178:     */   public static final String ER_VALUE_SHOULD_EQUAL = "ER_VALUE_SHOULD_EQUAL";
/*  179:     */   public static final String ER_FAILED_CALLING_METHOD = "ER_FAILED_CALLING_METHOD";
/*  180:     */   public static final String ER_FAILED_CREATING_ELEMTMPL = "ER_FAILED_CREATING_ELEMTMPL";
/*  181:     */   public static final String ER_CHARS_NOT_ALLOWED = "ER_CHARS_NOT_ALLOWED";
/*  182:     */   public static final String ER_ATTR_NOT_ALLOWED = "ER_ATTR_NOT_ALLOWED";
/*  183:     */   public static final String ER_BAD_VALUE = "ER_BAD_VALUE";
/*  184:     */   public static final String ER_ATTRIB_VALUE_NOT_FOUND = "ER_ATTRIB_VALUE_NOT_FOUND";
/*  185:     */   public static final String ER_ATTRIB_VALUE_NOT_RECOGNIZED = "ER_ATTRIB_VALUE_NOT_RECOGNIZED";
/*  186:     */   public static final String ER_NULL_URI_NAMESPACE = "ER_NULL_URI_NAMESPACE";
/*  187:     */   public static final String ER_NUMBER_TOO_BIG = "ER_NUMBER_TOO_BIG";
/*  188:     */   public static final String ER_CANNOT_FIND_SAX1_DRIVER = "ER_CANNOT_FIND_SAX1_DRIVER";
/*  189:     */   public static final String ER_SAX1_DRIVER_NOT_LOADED = "ER_SAX1_DRIVER_NOT_LOADED";
/*  190:     */   public static final String ER_SAX1_DRIVER_NOT_INSTANTIATED = "ER_SAX1_DRIVER_NOT_INSTANTIATED";
/*  191:     */   public static final String ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER = "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER";
/*  192:     */   public static final String ER_PARSER_PROPERTY_NOT_SPECIFIED = "ER_PARSER_PROPERTY_NOT_SPECIFIED";
/*  193:     */   public static final String ER_PARSER_ARG_CANNOT_BE_NULL = "ER_PARSER_ARG_CANNOT_BE_NULL";
/*  194:     */   public static final String ER_FEATURE = "ER_FEATURE";
/*  195:     */   public static final String ER_PROPERTY = "ER_PROPERTY";
/*  196:     */   public static final String ER_NULL_ENTITY_RESOLVER = "ER_NULL_ENTITY_RESOLVER";
/*  197:     */   public static final String ER_NULL_DTD_HANDLER = "ER_NULL_DTD_HANDLER";
/*  198:     */   public static final String ER_NO_DRIVER_NAME_SPECIFIED = "ER_NO_DRIVER_NAME_SPECIFIED";
/*  199:     */   public static final String ER_NO_URL_SPECIFIED = "ER_NO_URL_SPECIFIED";
/*  200:     */   public static final String ER_POOLSIZE_LESS_THAN_ONE = "ER_POOLSIZE_LESS_THAN_ONE";
/*  201:     */   public static final String ER_INVALID_DRIVER_NAME = "ER_INVALID_DRIVER_NAME";
/*  202:     */   public static final String ER_ERRORLISTENER = "ER_ERRORLISTENER";
/*  203:     */   public static final String ER_ASSERT_NO_TEMPLATE_PARENT = "ER_ASSERT_NO_TEMPLATE_PARENT";
/*  204:     */   public static final String ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR = "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR";
/*  205:     */   public static final String ER_NOT_ALLOWED_IN_POSITION = "ER_NOT_ALLOWED_IN_POSITION";
/*  206:     */   public static final String ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION = "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION";
/*  207:     */   public static final String ER_NAMESPACE_CONTEXT_NULL_NAMESPACE = "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE";
/*  208:     */   public static final String ER_NAMESPACE_CONTEXT_NULL_PREFIX = "ER_NAMESPACE_CONTEXT_NULL_PREFIX";
/*  209:     */   public static final String ER_XPATH_RESOLVER_NULL_QNAME = "ER_XPATH_RESOLVER_NULL_QNAME";
/*  210:     */   public static final String ER_XPATH_RESOLVER_NEGATIVE_ARITY = "ER_XPATH_RESOLVER_NEGATIVE_ARITY";
/*  211:     */   public static final String INVALID_TCHAR = "INVALID_TCHAR";
/*  212:     */   public static final String INVALID_QNAME = "INVALID_QNAME";
/*  213:     */   public static final String INVALID_ENUM = "INVALID_ENUM";
/*  214:     */   public static final String INVALID_NMTOKEN = "INVALID_NMTOKEN";
/*  215:     */   public static final String INVALID_NCNAME = "INVALID_NCNAME";
/*  216:     */   public static final String INVALID_BOOLEAN = "INVALID_BOOLEAN";
/*  217:     */   public static final String INVALID_NUMBER = "INVALID_NUMBER";
/*  218:     */   public static final String ER_ARG_LITERAL = "ER_ARG_LITERAL";
/*  219:     */   public static final String ER_DUPLICATE_GLOBAL_VAR = "ER_DUPLICATE_GLOBAL_VAR";
/*  220:     */   public static final String ER_DUPLICATE_VAR = "ER_DUPLICATE_VAR";
/*  221:     */   public static final String ER_TEMPLATE_NAME_MATCH = "ER_TEMPLATE_NAME_MATCH";
/*  222:     */   public static final String ER_INVALID_PREFIX = "ER_INVALID_PREFIX";
/*  223:     */   public static final String ER_NO_ATTRIB_SET = "ER_NO_ATTRIB_SET";
/*  224:     */   public static final String ER_FUNCTION_NOT_FOUND = "ER_FUNCTION_NOT_FOUND";
/*  225:     */   public static final String ER_CANT_HAVE_CONTENT_AND_SELECT = "ER_CANT_HAVE_CONTENT_AND_SELECT";
/*  226:     */   public static final String ER_INVALID_SET_PARAM_VALUE = "ER_INVALID_SET_PARAM_VALUE";
/*  227:     */   public static final String ER_SET_FEATURE_NULL_NAME = "ER_SET_FEATURE_NULL_NAME";
/*  228:     */   public static final String ER_GET_FEATURE_NULL_NAME = "ER_GET_FEATURE_NULL_NAME";
/*  229:     */   public static final String ER_UNSUPPORTED_FEATURE = "ER_UNSUPPORTED_FEATURE";
/*  230:     */   public static final String ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING = "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING";
/*  231:     */   public static final String WG_FOUND_CURLYBRACE = "WG_FOUND_CURLYBRACE";
/*  232:     */   public static final String WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR = "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR";
/*  233:     */   public static final String WG_EXPR_ATTRIB_CHANGED_TO_SELECT = "WG_EXPR_ATTRIB_CHANGED_TO_SELECT";
/*  234:     */   public static final String WG_NO_LOCALE_IN_FORMATNUMBER = "WG_NO_LOCALE_IN_FORMATNUMBER";
/*  235:     */   public static final String WG_LOCALE_NOT_FOUND = "WG_LOCALE_NOT_FOUND";
/*  236:     */   public static final String WG_CANNOT_MAKE_URL_FROM = "WG_CANNOT_MAKE_URL_FROM";
/*  237:     */   public static final String WG_CANNOT_LOAD_REQUESTED_DOC = "WG_CANNOT_LOAD_REQUESTED_DOC";
/*  238:     */   public static final String WG_CANNOT_FIND_COLLATOR = "WG_CANNOT_FIND_COLLATOR";
/*  239:     */   public static final String WG_FUNCTIONS_SHOULD_USE_URL = "WG_FUNCTIONS_SHOULD_USE_URL";
/*  240:     */   public static final String WG_ENCODING_NOT_SUPPORTED_USING_UTF8 = "WG_ENCODING_NOT_SUPPORTED_USING_UTF8";
/*  241:     */   public static final String WG_ENCODING_NOT_SUPPORTED_USING_JAVA = "WG_ENCODING_NOT_SUPPORTED_USING_JAVA";
/*  242:     */   public static final String WG_SPECIFICITY_CONFLICTS = "WG_SPECIFICITY_CONFLICTS";
/*  243:     */   public static final String WG_PARSING_AND_PREPARING = "WG_PARSING_AND_PREPARING";
/*  244:     */   public static final String WG_ATTR_TEMPLATE = "WG_ATTR_TEMPLATE";
/*  245:     */   public static final String WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESPACE = "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP";
/*  246:     */   public static final String WG_ATTRIB_NOT_HANDLED = "WG_ATTRIB_NOT_HANDLED";
/*  247:     */   public static final String WG_NO_DECIMALFORMAT_DECLARATION = "WG_NO_DECIMALFORMAT_DECLARATION";
/*  248:     */   public static final String WG_OLD_XSLT_NS = "WG_OLD_XSLT_NS";
/*  249:     */   public static final String WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED = "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED";
/*  250:     */   public static final String WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE = "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE";
/*  251:     */   public static final String WG_ILLEGAL_ATTRIBUTE = "WG_ILLEGAL_ATTRIBUTE";
/*  252:     */   public static final String WG_COULD_NOT_RESOLVE_PREFIX = "WG_COULD_NOT_RESOLVE_PREFIX";
/*  253:     */   public static final String WG_STYLESHEET_REQUIRES_VERSION_ATTRIB = "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB";
/*  254:     */   public static final String WG_ILLEGAL_ATTRIBUTE_NAME = "WG_ILLEGAL_ATTRIBUTE_NAME";
/*  255:     */   public static final String WG_ILLEGAL_ATTRIBUTE_VALUE = "WG_ILLEGAL_ATTRIBUTE_VALUE";
/*  256:     */   public static final String WG_EMPTY_SECOND_ARG = "WG_EMPTY_SECOND_ARG";
/*  257:     */   public static final String WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML = "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML";
/*  258:     */   public static final String WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME = "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME";
/*  259:     */   public static final String WG_ILLEGAL_ATTRIBUTE_POSITION = "WG_ILLEGAL_ATTRIBUTE_POSITION";
/*  260:     */   public static final String NO_MODIFICATION_ALLOWED_ERR = "NO_MODIFICATION_ALLOWED_ERR";
/*  261:     */   public static final String BAD_CODE = "BAD_CODE";
/*  262:     */   public static final String FORMAT_FAILED = "FORMAT_FAILED";
/*  263:     */   public static final String ERROR_STRING = "#error";
/*  264:     */   public static final String ERROR_HEADER = "エラー: ";
/*  265:     */   public static final String WARNING_HEADER = "警告: ";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "パターン ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 491 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "エラー: 式内では '{' を使用できません。" }, { "ER_ILLEGAL_ATTRIBUTE", "{0} に正しくない属性があります: {1}" }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "xsl:apply-imports 内の sourceNode がヌルです。" }, { "ER_CANNOT_ADD", "{0} を {1} に追加できません。" }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "handleApplyTemplatesInstruction 内の sourceNode がヌルです。" }, { "ER_NO_NAME_ATTRIB", "{0} には name 属性が必要です。" }, { "ER_TEMPLATE_NOT_FOUND", "{0} という名前のテンプレートが見つかりませんでした。" }, { "ER_CANT_RESOLVE_NAME_AVT", "xsl:call-template 内の名前 AVT を解決できませんでした。" }, { "ER_REQUIRES_ATTRIB", "{0} には属性が必要です: {1}" }, { "ER_MUST_HAVE_TEST_ATTRIB", "{0} には ''test'' 属性が必要です。" }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "level 属性で値が間違っています: {0}" }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "処理命令の名前は 'xml' にはできません。" }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "処理命令の名前は有効な NCName でなければなりません: {0}" }, { "ER_NEED_MATCH_ATTRIB", "{0} にモードがある場合は、match 属性が必要です。" }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "{0} には name または match のいずれかの属性が必要です。" }, { "ER_CANT_RESOLVE_NSPREFIX", "名前空間接頭部を解決できません: {0}" }, { "ER_ILLEGAL_VALUE", "xml:space には正しくない値があります: {0}" }, { "ER_NO_OWNERDOC", "下位ノードに所有者文書がありません。" }, { "ER_ELEMTEMPLATEELEM_ERR", "ElemTemplateElement エラー: {0}" }, { "ER_NULL_CHILD", "ヌルの子を追加しようとしています。" }, { "ER_NEED_SELECT_ATTRIB", "{0} には select 属性が必要です。" }, { "ER_NEED_TEST_ATTRIB", "xsl:when には 'test' 属性が必要です。" }, { "ER_NEED_NAME_ATTRIB", "xsl:with-param には 'name' 属性が必要です。" }, { "ER_NO_CONTEXT_OWNERDOC", "コンテキストに所有者文書がありません。" }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "XML TransformerFactory Liaison を作成できませんでした: {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Xalan: 処理は成功しませんでした。" }, { "ER_NOT_SUCCESSFUL", "Xalan: は成功しませんでした。" }, { "ER_ENCODING_NOT_SUPPORTED", "エンコードはサポートされていません: {0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "TraceListener を作成できませんでした: {0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "xsl:key には 'name' 属性が必要です。" }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "xsl:key には 'match' 属性が必要です。" }, { "ER_KEY_REQUIRES_USE_ATTRIB", "xsl:key には 'use' 属性が必要です。" }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) {0} には ''elements'' 属性が必要です。" }, { "ER_MISSING_PREFIX_ATTRIB", "(StylesheetHandler) {0} に属性 ''prefix'' がありません。" }, { "ER_BAD_STYLESHEET_URL", "スタイルシート URL が間違っています: {0}" }, { "ER_FILE_NOT_FOUND", "スタイルシート・ファイルが見つかりませんでした: {0}" }, { "ER_IOEXCEPTION", "スタイルシート・ファイルによる入出力例外が起こりました: {0}" }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) {0} の href 属性が見つかりませんでした。" }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) {0} が自分自身を直接的または間接的に組み込もうとしています。" }, { "ER_PROCESSINCLUDE_ERROR", "StylesheetHandler.processInclude エラー、{0}" }, { "ER_MISSING_LANG_ATTRIB", "(StylesheetHandler) {0} に属性 ''lang'' がありません。" }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) {0} 要素の場所を間違えた可能性があります。コンテナー要素 ''component'' がありません。" }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "Element、DocumentFragment、Document、または PrintWriter への出力しかできません。" }, { "ER_PROCESS_ERROR", "StylesheetRoot.処理エラー" }, { "ER_UNIMPLNODE_ERROR", "UnImplNode エラー: {0}" }, { "ER_NO_SELECT_EXPRESSION", "エラー:  xpath select 式 (-select) が見つかりませんでした。" }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "XSLProcessor をシリアライズできません。" }, { "ER_NO_INPUT_STYLESHEET", "スタイルシート入力が指定されていませんでした。" }, { "ER_FAILED_PROCESS_STYLESHEET", "スタイルシートを処理することに失敗しました。" }, { "ER_COULDNT_PARSE_DOC", "{0} 文書を構文解析できませんでした。" }, { "ER_COULDNT_FIND_FRAGMENT", "フラグメントが見つかりませんでした: {0}" }, { "ER_NODE_NOT_ELEMENT", "フラグメント ID により指されているノードが要素でありませんでした: {0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "for-each には match または name のいずれかの属性が必要です。" }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "テンプレートには match または name のいずれかの属性が必要です。" }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "文書フラグメントの複製がありません。" }, { "ER_CANT_CREATE_ITEM", "項目を結果ツリーに作成できません: {0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "ソース XML 内の xml:space には正しくない値があります: {0}" }, { "ER_NO_XSLKEY_DECLARATION", "{0} の xsl:key 宣言がありません。" }, { "ER_CANT_CREATE_URL", "エラー:  {0} の URL を作成できません。" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "xsl:functions はサポートされていません。" }, { "ER_PROCESSOR_ERROR", "XSLT TransformerFactory エラー" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) {0} はスタイルシートの内部では許可されていません。" }, { "ER_RESULTNS_NOT_SUPPORTED", "result-ns はもうサポートされていません。  代りに xsl:output を使用してください。" }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "default-space はもうサポートされていません。  代りに xsl:strip-space または xsl:preserve-space を使用してください。" }, { "ER_INDENTRESULT_NOT_SUPPORTED", "indent-result はもうサポートされていません。  代りに xsl:output を使用してください。" }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) {0} には正しくない属性があります: {1}" }, { "ER_UNKNOWN_XSL_ELEM", "不明の XSL 要素: {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) xsl:sort は xsl:apply-templates または xsl:for-each としか使用できません。" }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) xsl:when の場所を誤っていました。" }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:when が xsl:choose により親になっていませんでした。" }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) xsl:otherwise の場所を誤っていました。" }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:otherwise が xsl:choose により親になっていませんでした。" }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) {0} はテンプレートの内部では許可されていません。" }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) {0} 拡張名前空間接頭部 {1} が不明です。" }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) インポートは、スタイルシート内の先頭要素としてのみ入れることができます。" }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) {0} が自分自身を直接的または間接的にインポートしようとしています。" }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) xml:space に正しくない値があります: {0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "processStylesheet は成功していません。" }, { "ER_SAX_EXCEPTION", "SAX 例外" }, { "ER_FUNCTION_NOT_SUPPORTED", "機能はサポートされていません。" }, { "ER_XSLT_ERROR", "XSLT エラー" }, { "ER_CURRENCY_SIGN_ILLEGAL", "通貨記号は書式パターン・ストリング内で許可されていません。" }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "文書機能はスタイルシート DOM ではサポートされていません。" }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "非接頭部リゾルバーの接頭部を解決できません。" }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "リダイレクト拡張: ファイル名を取得できませんでした。file または select 属性は有効なストリングを戻さなければなりません。" }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "FormatterListener はリダイレクト拡張内にビルドできません。" }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "exclude-result-prefixes 内の接頭部が無効です: {0}" }, { "ER_MISSING_NS_URI", "指定された接頭部の名前空間 URI がありません。" }, { "ER_MISSING_ARG_FOR_OPTION", "オプションの引数がありません: {0}" }, { "ER_INVALID_OPTION", "無効なオプション: {0}" }, { "ER_MALFORMED_FORMAT_STRING", "誤った形式の書式ストリング: {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet には 'version' 属性が必要です。" }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "属性: {0} には正しくない値: {1} があります。" }, { "ER_CHOOSE_REQUIRES_WHEN", "xsl:choose には xsl:when が必要です。" }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:apply-imports は xsl:for-each 内では許可されていません。" }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "DTMLiaison は出力 DOM ノードに使用できません... 代りに org.apache.xpath.DOM2Helper を渡してください。" }, { "ER_CANT_USE_DTM_FOR_INPUT", "DTMLiaison は入力 DOM ノードに使用できません... 代りに org.apache.xpath.DOM2Helper を渡してください。" }, { "ER_CALL_TO_EXT_FAILED", "拡張要素への呼び出しが失敗しました: {0}" }, { "ER_PREFIX_MUST_RESOLVE", "接頭部は名前空間に解決されなければなりません: {0}" }, { "ER_INVALID_UTF16_SURROGATE", "無効な UTF-16 サロゲートが検出されました: {0} ?" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0} が自身を使用しているため、無限ループの原因となります。" }, { "ER_CANNOT_MIX_XERCESDOM", "非 Xerces-DOM 入力と Xerces-DOM 出力は混用できません。" }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "ElemTemplateElement.readObject 内: {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "次の名前のテンプレートが複数見つかりました: {0}" }, { "ER_INVALID_KEY_CALL", "無効な関数呼び出し: 再帰的 key() 呼び出しは許可されていません。" }, { "ER_REFERENCING_ITSELF", "変数 {0} が直接的または間接的に自分自身に参照づけています。" }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "newTemplates の DOMSource の入力ノードをヌルにはできません。" }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "オプション {0} のクラス・ファイルが見つかりません。" }, { "ER_REQUIRED_ELEM_NOT_FOUND", "必要な要素が見つかりません: {0}" }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream をヌルにはできません。" }, { "ER_URI_CANNOT_BE_NULL", "URI をヌルにはできません。" }, { "ER_FILE_CANNOT_BE_NULL", "ファイルをヌルにはできません。" }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource をヌルにはできません。" }, { "ER_CANNOT_INIT_BSFMGR", "BSF マネージャーを初期化できませんでした。" }, { "ER_CANNOT_CMPL_EXTENSN", "拡張機能をコンパイルできませんでした。" }, { "ER_CANNOT_CREATE_EXTENSN", "原因: {1} のために拡張機能: {0} を作成できませんでした。" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "メソッド {0} へのインスタンス・メソッド呼び出しにはオブジェクト・インスタンスが最初の引数として必要です。" }, { "ER_INVALID_ELEMENT_NAME", "無効な要素名が指定されました: {0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "要素名メソッドは静的でなければなりません: {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "拡張機能 {0} : {1} が不明です。" }, { "ER_MORE_MATCH_CONSTRUCTOR", "{0} のコンストラクターの最適一致が複数あります。" }, { "ER_MORE_MATCH_METHOD", "メソッド {0} の最適一致が複数あります。" }, { "ER_MORE_MATCH_ELEMENT", "要素メソッド {0} の最適一致が複数あります。" }, { "ER_INVALID_CONTEXT_PASSED", "{0} を評価するために渡されたコンテキストが無効です。" }, { "ER_POOL_EXISTS", "プールはすでに存在しています。" }, { "ER_NO_DRIVER_NAME", "ドライバー名が指定されていません。" }, { "ER_NO_URL", "URL が指定されていません。" }, { "ER_POOL_SIZE_LESSTHAN_ONE", "プール・サイズが 1 より小です。" }, { "ER_INVALID_DRIVER", "無効なドライバー名が指定されました。" }, { "ER_NO_STYLESHEETROOT", "スタイルシートのルートが見つかりませんでした。" }, { "ER_ILLEGAL_XMLSPACE_VALUE", "xml:space の値が正しくありません。" }, { "ER_PROCESSFROMNODE_FAILED", "processFromNode が失敗しました。" }, { "ER_RESOURCE_COULD_NOT_LOAD", "リソース [ {0} ] をロードできませんでした: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "バッファー・サイズ <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "エクステンションを呼び出し時に不明エラー" }, { "ER_NO_NAMESPACE_DECL", "接頭部 {0} には対応している名前空間宣言がありません。" }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "要素の内容は lang=javaclass {0} の場合は許可されていません。" }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "スタイルシートで終了が指図されました。" }, { "ER_ONE_OR_TWO", "1 または 2" }, { "ER_TWO_OR_THREE", "2 または 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "{0} をロードできませんでした (CLASSPATH を調べてください)。現在はまさにデフォルトを使用中です。" }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "デフォルト・テンプレートを初期化できません。" }, { "ER_RESULT_NULL", "結果はヌルにはならないはずです。" }, { "ER_RESULT_COULD_NOT_BE_SET", "結果を設定できませんでした。" }, { "ER_NO_OUTPUT_SPECIFIED", "出力が指定されていません。" }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "型 {0} の Result に変換できません。" }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "型 {0} の Source を変換できません。" }, { "ER_NULL_CONTENT_HANDLER", "ヌルのコンテンツ・ハンドラー" }, { "ER_NULL_ERROR_HANDLER", "ヌルのエラー・ハンドラー" }, { "ER_CANNOT_CALL_PARSE", "ContentHandler が未設定の場合は parse の呼び出しはできません。" }, { "ER_NO_PARENT_FOR_FILTER", "フィルターの親がありません。" }, { "ER_NO_STYLESHEET_IN_MEDIA", "スタイルシートが {0}、メディア= {1} に見つかりません。" }, { "ER_NO_STYLESHEET_PI", "XML スタイルシート PI が {0} に見つかりません。" }, { "ER_NOT_SUPPORTED", "サポートされていません: {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "プロパティー {0} の値はブール・インスタンスにする必要があります。" }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "{0} の外部スクリプトへ到達できませんでした。" }, { "ER_RESOURCE_COULD_NOT_FIND", "リソース [ {0} ] は見つかりませんでした。\n {1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "出力プロパティーは認識されていません: {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "ElemLiteralResult インスタンスの作成が失敗しました。" }, { "ER_VALUE_SHOULD_BE_NUMBER", "{0} の値には構文解析可能番号が含まれているはずです。" }, { "ER_VALUE_SHOULD_EQUAL", "{0} の値は yes または no と等しくなければなりません。" }, { "ER_FAILED_CALLING_METHOD", "{0} メソッドの呼び出しが失敗しました。" }, { "ER_FAILED_CREATING_ELEMTMPL", "ElemTemplateElement インスタンスの作成が失敗しました。" }, { "ER_CHARS_NOT_ALLOWED", "文字は文書内のこのポイントでは許可されていません。" }, { "ER_ATTR_NOT_ALLOWED", "\"{0}\" 属性は {1} 要素では許可されていません。" }, { "ER_BAD_VALUE", "{0} の間違った値 {1} " }, { "ER_ATTRIB_VALUE_NOT_FOUND", "{0} 属性値が見つかりません。 " }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "{0} 属性値は認識されません。 " }, { "ER_NULL_URI_NAMESPACE", "名前空間接頭部をヌルの URI で生成しようとしています。" }, { "ER_NUMBER_TOO_BIG", "最大 Long 整数より大きい数をフォーマットしようとしています。" }, { "ER_CANNOT_FIND_SAX1_DRIVER", "SAX1 ドライバー・クラス {0} が見つかりません。" }, { "ER_SAX1_DRIVER_NOT_LOADED", "SAX1 ドライバー・クラス {0} が見つかりましたがロードできません。" }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "SAX1 ドライバー・クラス {0} がロードされましたがインスタンス生成できません。" }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "SAX1 ドライバー・クラス {0} が org.xml.sax.Parser を実装していません。" }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "システム・プロパティー org.xml.sax.parser は指定されていません。" }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "パーサーの引数をヌルにしてはなりません。" }, { "ER_FEATURE", "機能: {0}" }, { "ER_PROPERTY", "プロパティー: {0}" }, { "ER_NULL_ENTITY_RESOLVER", "ヌル実体リゾルバー" }, { "ER_NULL_DTD_HANDLER", "ヌル DTD ハンドラー" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "ドライバー名が指定されていません。" }, { "ER_NO_URL_SPECIFIED", "URL が指定されていません。" }, { "ER_POOLSIZE_LESS_THAN_ONE", "プール・サイズが 1 より小さくなっています。" }, { "ER_INVALID_DRIVER_NAME", "無効なドライバー名が指定されました。" }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "プログラマーのエラー:  式に ElemTemplateElement 親がありません。" }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "RedundentExprEliminator 内のプログラマーのアサーション: {0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "{0} はスタイルシートのこの位置では許可されません。" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "空白文字以外のテキストはスタイルシートのこの位置では許可されません。" }, { "INVALID_TCHAR", "正しくない値: {1} が CHAR 属性: {0} に使用されました。CHAR 型の属性は 1 文字でなければなりません。" }, { "INVALID_QNAME", "正しくない値: {1} が QNAME 属性: {0} に使用されました。" }, { "INVALID_ENUM", "正しくない値: {1} が ENUM 属性: {0} に使用されました。有効値: {2}。" }, { "INVALID_NMTOKEN", "正しくない値: {1} が NMTOKEN 属性: {0} に使用されました。" }, { "INVALID_NCNAME", "正しくない値: {1} が NCNAME 属性: {0} に使用されました。" }, { "INVALID_BOOLEAN", "正しくない値: {1} が boolean 属性: {0} に使用されました。" }, { "INVALID_NUMBER", "正しくない値: {1} が number 属性: {0} に使用されました。" }, { "ER_ARG_LITERAL", "マッチング・パターンの {0} への引数はリテラルでなければなりません。" }, { "ER_DUPLICATE_GLOBAL_VAR", "グローバル変数宣言が重複しています。" }, { "ER_DUPLICATE_VAR", "変数宣言が重複しています。" }, { "ER_TEMPLATE_NAME_MATCH", "xsl:template には name または match 属性 (あるいはその両方) が必要です。" }, { "ER_INVALID_PREFIX", "exclude-result-prefixes 内の接頭部が無効です: {0}" }, { "ER_NO_ATTRIB_SET", "{0} という名前の attribute-set が存在していません。" }, { "ER_FUNCTION_NOT_FOUND", "{0} という名前の関数が存在していません。" }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "{0} 要素に内容および select 属性の両方があってはなりません。" }, { "ER_INVALID_SET_PARAM_VALUE", "param {0} の値は有効な Java オブジェクトである必要があります" }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "xsl:namespace-alias 要素の result-prefix 属性の値が '#default' になっていますが、この要素のスコープ内にはデフォルト名前空間の宣言はありません。" }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "xsl:namespace-alias 要素の result-prefix 属性の値が ''{0}'' になっていますが、この要素のスコープ内には接頭部 ''{0}'' の名前空間宣言はありません。" }, { "ER_SET_FEATURE_NULL_NAME", "TransformerFactory.setFeature(String name, boolean value) の機能名をヌルにはできません。" }, { "ER_GET_FEATURE_NULL_NAME", "TransformerFactory.getFeature(String name) の機能名をヌルにはできません。" }, { "ER_UNSUPPORTED_FEATURE", "機能 ''{0}'' はこの TransformerFactory に設定できません。" }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "セキュリティー保護された処理機能が true に設定されているときに、拡張要素 ''{0}'' を使用することはできません。" }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "ヌル名前空間 URI の接頭部は取得できません。" }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "ヌル接頭部の名前空間 URI は取得できません。" }, { "ER_XPATH_RESOLVER_NULL_QNAME", "関数名 はヌルにできません。" }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "アリティー (引数の数) は負の値にできません。" }, { "WG_FOUND_CURLYBRACE", "'}' が見つかりましたが、オープンされた属性テンプレートがありません。" }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "警告: count 属性が xsl:number 内の上位と一致しません。 ターゲット = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "旧構文: 'expr' 属性の名前が 'select' に変更されています。" }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan はフォーマット番号関数内でまだロケール名を処理しません。" }, { "WG_LOCALE_NOT_FOUND", "警告: xml:lang={0} のロケールが見つかりませんでした。" }, { "WG_CANNOT_MAKE_URL_FROM", "URL を {0} から作成できません。" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "要求された doc: {0} をロードできません。" }, { "WG_CANNOT_FIND_COLLATOR", "<sort xml:lang={0} のコレーターが見つかりませんでした。" }, { "WG_FUNCTIONS_SHOULD_USE_URL", "旧構文: 関数命令では {0} の URL を使用する必要があります。" }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "エンコードはサポートされません: {0} は UTF-8 を使用しています。" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "エンコードはサポートされません: {0} は Java {1} を使用しています。" }, { "WG_SPECIFICITY_CONFLICTS", "限定性の矛盾が検出されました: {0} スタイルシート内で最後に検出されたものが使用されます。" }, { "WG_PARSING_AND_PREPARING", "========= {0} を構文解析中および準備中 ==========" }, { "WG_ATTR_TEMPLATE", "属性テンプレート {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "xsl:strip-space と xsl:preserve-space の間のマッチングの矛盾" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan はまだ {0} 属性を処理しません。" }, { "WG_NO_DECIMALFORMAT_DECLARATION", "10 進数形式の宣言が見つかりません: {0}" }, { "WG_OLD_XSLT_NS", "XSLT 名前空間がないか誤っています。 " }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "デフォルトの xsl:decimal-format 宣言は 1 つしか許可されていません。" }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "xsl:decimal-format 名は固有でなければなりません。名前 \"{0}\" が重複していました。" }, { "WG_ILLEGAL_ATTRIBUTE", "{0} に正しくない属性があります: {1}" }, { "WG_COULD_NOT_RESOLVE_PREFIX", "名前空間接頭部を解決できませんでした: {0} - ノードは無視されます。" }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet には 'version' 属性が必要です。" }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "正しくない属性名: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "属性 {0}: {1} に使用された値は正しくありません。" }, { "WG_EMPTY_SECOND_ARG", "文書機能の 2 番目の引数から得られた nodeset が空です。空の node-set を戻します。" }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "xsl:processing-instruction 名の 'name' 属性の値は 'xml' であってはなりません。" }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "xsl:processing-instruction の ''name'' 属性の値は有効な NCName でなければなりません: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "下位ノードの後または要素が生成される前に属性 {0} は追加できません。属性は無視されます。" }, { "NO_MODIFICATION_ALLOWED_ERR", "変更できないオブジェクトを変更しようとしています。" }, { "ui_language", "en" }, { "help_language", "en" }, { "language", "en" }, { "BAD_CODE", "createMessage へのパラメーターが範囲外でした。" }, { "FORMAT_FAILED", "messageFormat 呼び出し中に例外がスローされました。" }, { "version", ">>>>>>> Xalan バージョン " }, { "version2", "<<<<<<<" }, { "yes", "はい (y)" }, { "line", "行 #" }, { "column", "桁 #" }, { "xsldone", "XSLProcessor: 完了" }, { "xslProc_option", "Xalan-J コマンド行 Process クラス・オプション" }, { "xslProc_option", "Xalan-J コマンド行 Process クラス・オプション:" }, { "xslProc_invalid_xsltc_option", "オプション {0} は XSLTC モードではサポートされていません。" }, { "xslProc_invalid_xalan_option", "オプション {0} は -XSLTC と一緒にしか使用できません。" }, { "xslProc_no_input", "エラー: スタイルシートがないか入力 xml が指定されていません。使用法の説明については、オプションなしでこのコマンドを実行してください。" }, { "xslProc_common_options", "-共通オプション-" }, { "xslProc_xalan_options", "-Xalan 用オプション-" }, { "xslProc_xsltc_options", "-XSLTC 用オプション-" }, { "xslProc_return_to_continue", "(続けるには <return> を押してください)" }, { "optionXSLTC", "   [-XSLTC (変換に XSLTC を使用)]" }, { "optionIN", "   [-IN inputXMLURL]" }, { "optionXSL", "   [-XSL XSLTransformationURL]" }, { "optionOUT", "   [-OUT outputFileName]" }, { "optionLXCIN", "   [-LXCIN compiledStylesheetFileNameIn]" }, { "optionLXCOUT", "   [-LXCOUT compiledStylesheetFileNameOutOut]" }, { "optionPARSER", "   [-PARSER parser liaison の完全修飾クラス名]" }, { "optionE", "   [-E (実体参照を展開しない)]" }, { "optionV", "   [-E (実体参照を展開しない)]" }, { "optionQC", "   [-QC (静止パターン矛盾警告)]" }, { "optionQ", "   [-Q  (静止モード)]" }, { "optionLF", "   [-LF (LF (改行) を出力時のみに使用  {デフォルトは CR/LF})]" }, { "optionCR", "   [-CR (CR (復帰) を出力時のみに使用 {デフォルトは CR/LF})]" }, { "optionESCAPE", "   [-ESCAPE (エスケープする文字 {デフォルトは <>&\"'\\r\\n}]" }, { "optionINDENT", "   [-INDENT (字下げするスペースを制御 {デフォルトは 0})]" }, { "optionTT", "   [-TT (テンプレートを呼び出し中にトレース。)]" }, { "optionTG", "   [-TG (各生成イベントをトレース。)]" }, { "optionTS", "   [-TS (各選択イベントをトレース。)]" }, { "optionTTC", "   [-TTC (テンプレートの子を呼び出し中にトレース。)]" }, { "optionTCLASS", "   [-TCLASS (トレース拡張機能の TraceListener クラス。)]" }, { "optionVALIDATE", "   [-VALIDATE (妥当性検査を実行するかどうかを設定。  デフォルトでは、妥当性検査はオフです。)]" }, { "optionEDUMP", "   [-EDUMP {optional filename} (エラー時に stackdump を実行。)]" }, { "optionXML", "   [-XML (XML フォーマッターを使用および XML ヘッダーを追加。)]" }, { "optionTEXT", "   [-TEXT (シンプル・テキスト・フォーマッターを使用。)]" }, { "optionHTML", "   [-HTML (HTML フォーマッターを使用。)]" }, { "optionPARAM", "   [-PARAM 名前式 (stylesheet パラメーターを設定。)]" }, { "noParsermsg1", "XSL 処理は成功しませんでした。" }, { "noParsermsg2", "** パーサーが見つかりませんでした **" }, { "noParsermsg3", "クラスパスを調べてください。" }, { "noParsermsg4", "IBM の XML Parser for Java がない場合は、次のサイトからダウンロードできます:" }, { "noParsermsg5", "IBM AlphaWorks: http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "   [-URIRESOLVER 絶対クラス名 (URI を解決するために使用する URIResolver)]" }, { "optionENTITYRESOLVER", "   [-ENTITYRESOLVER 絶対クラス名 (実体を解決するために使用する EntityResolver)]" }, { "optionCONTENTHANDLER", "   [-CONTENTHANDLER 絶対クラス名 (出力をシリアライズするために使用する ContentHandler)]" }, { "optionLINENUMBERS", "   [-L ソース・ドキュメントの行番号を使用]" }, { "optionSECUREPROCESSING", "   [-SECURE (セキュリティー保護された処理機能を true に設定)]" }, { "optionMEDIA", "   [-MEDIA mediaType (文書と関連したスタイルシートを検索するメディア属性を使用。)]" }, { "optionFLAVOR", "   [-FLAVOR flavorName (変換を実行するために s2s=SAX または d2d=DOM を明示的に使用。)] " }, { "optionDIAG", "   [-DIAG (変換にかかった全ミリ秒を印刷。)]" }, { "optionINCREMENTAL", "   [-INCREMENTAL (http://xml.apache.org/xalan/features/incremental を true に設定することにより増分 DTM 構造を要求。)]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE (http://xml.apache.org/xalan/features/optimize を false に設定することによりスタイルシート最適化処理なしを要求。)]" }, { "optionRL", "   [-RL recursionlimit (スタイルシートの再帰の深さについての数値限界を指定。)]" }, { "optionXO", "   [-XO [transletName] (名前を生成後の translet に割り当て)]" }, { "optionXD", "   [-XD destinationDirectory (宛先ディレクトリーを translet に指定)]" }, { "optionXJ", "   [-XJ jarfile (translet クラスを名前 <jarfile> の JAR ファイルにパッケージします)]" }, { "optionXP", "   [-XP package (パッケージ名接頭部をすべての生成後の translet クラスに指定します)]" }, { "optionXN", "   [-XN (テンプレートをインラインで使用可能にします)]" }, { "optionXX", "   [-XX (追加のデバッグ・メッセージ出力をオンにします)]" }, { "optionXT", "   [-XT (可能な場合は translet を使用して変換)]" }, { "diagTiming", " --------- {0} の {1} による変換には {2} ミリ秒かかりました" }, { "recursionTooDeep", "テンプレートのネストが深すぎます。 ネスト = {0}、テンプレート {1} {2}" }, { "nameIs", "名前は" }, { "matchPatternIs", "マッチング・パターンは" } };
/*  276:     */   }
/*  277:     */   
/*  278:     */   public static final XSLTErrorResources loadResourceBundle(String className)
/*  279:     */     throws MissingResourceException
/*  280:     */   {
/*  281:1478 */     Locale locale = Locale.getDefault();
/*  282:1479 */     String suffix = getResourceSuffix(locale);
/*  283:     */     try
/*  284:     */     {
/*  285:1485 */       return (XSLTErrorResources)ResourceBundle.getBundle(className + suffix, locale);
/*  286:     */     }
/*  287:     */     catch (MissingResourceException e)
/*  288:     */     {
/*  289:     */       try
/*  290:     */       {
/*  291:1495 */         return (XSLTErrorResources)ResourceBundle.getBundle(className, new Locale("en", "US"));
/*  292:     */       }
/*  293:     */       catch (MissingResourceException e2)
/*  294:     */       {
/*  295:1503 */         throw new MissingResourceException("Could not load any resource bundles.", className, "");
/*  296:     */       }
/*  297:     */     }
/*  298:     */   }
/*  299:     */   
/*  300:     */   private static final String getResourceSuffix(Locale locale)
/*  301:     */   {
/*  302:1520 */     String suffix = "_" + locale.getLanguage();
/*  303:1521 */     String country = locale.getCountry();
/*  304:1523 */     if (country.equals("TW")) {
/*  305:1524 */       suffix = suffix + "_" + country;
/*  306:     */     }
/*  307:1526 */     return suffix;
/*  308:     */   }
/*  309:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_ja
 * JD-Core Version:    0.7.0.1
 */