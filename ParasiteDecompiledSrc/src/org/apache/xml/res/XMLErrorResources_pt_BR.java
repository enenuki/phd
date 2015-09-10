/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_pt_BR
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Função não suportada!" }, { "ER_CANNOT_OVERWRITE_CAUSE", "Impossível sobrepor causa" }, { "ER_NO_DEFAULT_IMPL", "Nenhuma implementação padrão encontrada" }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) não suportado atualmente" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Deslocamento maior que slot" }, { "ER_COROUTINE_NOT_AVAIL", "Co-rotina não disponível, id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager recebido para pedido co_exit()" }, { "ER_COJOINROUTINESET_FAILED", "Falha de co_joinCoroutineSet()" }, { "ER_COROUTINE_PARAM", "Erro de parâmetro coroutine ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nINESPERADO: doTerminate do analisador respondeu {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "parse não pode ser chamado durante análise" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Erro: digitado repetidor para eixo {0} não implementado" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Erro: repetidor para eixo {0} não implementado" }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Clone de repetidor não suportado" }, { "ER_UNKNOWN_AXIS_TYPE", "Tipo de passagem de eixo desconhecida: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Atravessador de eixo não suportado: {0}" }, { "ER_NO_DTMIDS_AVAIL", "Não existem mais IDs de DTM disponíveis" }, { "ER_NOT_SUPPORTED", "Não suportado: {0}" }, { "ER_NODE_NON_NULL", "O nó não deve ser nulo para getDTMHandleFromNode" }, { "ER_COULD_NOT_RESOLVE_NODE", "Não foi possível resolver o nó para um identificador" }, { "ER_STARTPARSE_WHILE_PARSING", "startParse não pode ser chamado durante análise" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse precisa de um SAXParser não-nulo" }, { "ER_COULD_NOT_INIT_PARSER", "não foi possível inicializar analisador com" }, { "ER_EXCEPTION_CREATING_POOL", "exceção ao criar nova instância para o conjunto" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "O caminho contém seqüência de escape inválida" }, { "ER_SCHEME_REQUIRED", "O esquema é obrigatório!" }, { "ER_NO_SCHEME_IN_URI", "Nenhum esquema encontrado no URI: {0}" }, { "ER_NO_SCHEME_INURI", "Nenhum esquema encontrado no URI" }, { "ER_PATH_INVALID_CHAR", "O caminho contém caractere inválido: {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "Impossível definir esquema a partir da cadeia nula" }, { "ER_SCHEME_NOT_CONFORMANT", "O esquema não está em conformidade." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "O host não é um endereço formado corretamente" }, { "ER_PORT_WHEN_HOST_NULL", "A porta não pode ser definida quando o host é nulo" }, { "ER_INVALID_PORT", "Número de porta inválido" }, { "ER_FRAG_FOR_GENERIC_URI", "O fragmento só pode ser definido para um URI genérico" }, { "ER_FRAG_WHEN_PATH_NULL", "O fragmento não pode ser definido quando o caminho é nulo" }, { "ER_FRAG_INVALID_CHAR", "O fragmento contém caractere inválido" }, { "ER_PARSER_IN_USE", "O analisador já está sendo utilizado" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "Impossível alterar {0} {1} durante análise" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Auto-causação não permitida" }, { "ER_NO_USERINFO_IF_NO_HOST", "Userinfo não pode ser especificado se host não for especificado" }, { "ER_NO_PORT_IF_NO_HOST", "Port não pode ser especificado se host não for especificado" }, { "ER_NO_QUERY_STRING_IN_PATH", "A cadeia de consulta não pode ser especificada na cadeia de consulta e caminho" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "O fragmento não pode ser especificado no caminho e fragmento" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Impossível inicializar URI com parâmetros vazios" }, { "ER_METHOD_NOT_SUPPORTED", "Método ainda não suportado" }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "IncrementalSAXSource_Filter atualmente não reinicializável" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader não antes do pedido startParse" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Atravessador de eixo não suportado: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "ListingErrorHandler criado com nulo PrintWriter!" }, { "ER_SYSTEMID_UNKNOWN", "SystemId Desconhecido" }, { "ER_LOCATION_UNKNOWN", "Localização de erro desconhecido" }, { "ER_PREFIX_MUST_RESOLVE", "O prefixo deve ser resolvido para um espaço de nomes: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "createDocument() não suportado em XPathContext!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "O atributo child não possui um documento do proprietário!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "O atributo child não possui um elemento de documento do proprietário!" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Aviso: impossível emitir texto antes do elemento document! Ignorando..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "Impossível ter mais de uma raiz em um DOM!" }, { "ER_ARG_LOCALNAME_NULL", "O argumento 'localName' é nulo" }, { "ER_ARG_LOCALNAME_INVALID", "Localname em QNAME deve ser um NCName válido" }, { "ER_ARG_PREFIX_INVALID", "O prefixo em QNAME deve ser um NCName válido" }, { "ER_NAME_CANT_START_WITH_COLON", "O nome não pode começar com um caractere de dois pontos (:)" }, { "BAD_CODE", "O parâmetro para createMessage estava fora dos limites" }, { "FORMAT_FAILED", "Exceção emitida durante chamada messageFormat" }, { "line", "Linha n°" }, { "column", "Coluna n°" } };
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
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("pt", "BR"));
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_pt_BR
 * JD-Core Version:    0.7.0.1
 */