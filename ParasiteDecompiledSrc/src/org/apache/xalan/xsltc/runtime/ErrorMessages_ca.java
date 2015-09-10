/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ import java.util.ListResourceBundle;
/*  4:   */ 
/*  5:   */ public class ErrorMessages_ca
/*  6:   */   extends ListResourceBundle
/*  7:   */ {
/*  8:   */   public Object[][] getContents()
/*  9:   */   {
/* 10:88 */     return new Object[][] { { "RUN_TIME_INTERNAL_ERR", "S''ha produït un error intern de temps d''execució a ''{0}''" }, { "RUN_TIME_COPY_ERR", "Es produeix un error de temps d'execució en executar <xsl:copy>." }, { "DATA_CONVERSION_ERR", "Conversió no vàlida de ''{0}'' a ''{1}''." }, { "EXTERNAL_FUNC_ERR", "XSLTC no dóna suport a la funció externa ''{0}''. " }, { "EQUALITY_EXPR_ERR", "L'expressió d'igualtat conté un tipus d'argument desconegut." }, { "INVALID_ARGUMENT_ERR", "El tipus d''argument ''{0}'' a la crida de ''{1}'' no és vàlid " }, { "FORMAT_NUMBER_ERR", "S''ha intentat formatar el número ''{0}'' fent servir el patró ''{1}''." }, { "ITERATOR_CLONE_ERR", "No es pot clonar l''iterador ''{0}''." }, { "AXIS_SUPPORT_ERR", "No està suportat l''iterador de l''eix ''{0}''. " }, { "TYPED_AXIS_SUPPORT_ERR", "No està suportat l''iterador de l''eix escrit ''{0}''. " }, { "STRAY_ATTRIBUTE_ERR", "L''atribut ''{0}'' es troba fora de l''element. " }, { "STRAY_NAMESPACE_ERR", "La declaració de l''espai de noms ''{0}''=''{1}'' es troba fora de l''element. " }, { "NAMESPACE_PREFIX_ERR", "L''espai de noms del prefix ''{0}'' no s''ha declarat. " }, { "DOM_ADAPTER_INIT_ERR", "DOMAdapter s'ha creat mitjançant un tipus incorrecte de DOM d'origen." }, { "PARSER_DTD_SUPPORT_ERR", "L'analitzador SAX que feu servir no gestiona esdeveniments de declaració de DTD." }, { "NAMESPACES_SUPPORT_ERR", "L'analitzador SAX que feu servir no dóna suport a espais de noms XML." }, { "CANT_RESOLVE_RELATIVE_URI_ERR", "No s''ha pogut resoldre la referència d''URI ''{0}''." }, { "UNSUPPORTED_XSL_ERR", "L''element XSL ''{0}'' no té suport " }, { "UNSUPPORTED_EXT_ERR", "No es reconeix l''extensió XSLTC ''{0}''" }, { "UNKNOWN_TRANSLET_VERSION_ERR", "La classe translet especificada, ''{0}'', es va crear fent servir una versió d''XSLTC més recent que la versió del temps d''execució d''XSLTC que ja s''està utilitzant. Heu de recompilar el full d''estil o fer servir una versió més recent d''XSLTC per executar aquesta classe translet." }, { "INVALID_QNAME_ERR", "Un atribut, que ha de tenir el valor QName, tenia el valor ''{0}''" }, { "INVALID_NCNAME_ERR", "Un atribut, que ha de tenir el valor NCName, tenia el valor ''{0}''" }, { "UNALLOWED_EXTENSION_FUNCTION_ERR", "L''ús de la funció d''extensió ''{0}'' no està permès, si la característica de procés segur s''ha establert en true." }, { "UNALLOWED_EXTENSION_ELEMENT_ERR", "L''ús de l''element d''extensió ''{0}'' no està permès, si la característica de procés segur s''ha establert en true." } };
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.ErrorMessages_ca
 * JD-Core Version:    0.7.0.1
 */