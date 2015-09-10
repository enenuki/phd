/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ import java.util.ListResourceBundle;
/*  4:   */ 
/*  5:   */ public class ErrorMessages_hu
/*  6:   */   extends ListResourceBundle
/*  7:   */ {
/*  8:   */   public Object[][] getContents()
/*  9:   */   {
/* 10:88 */     return new Object[][] { { "RUN_TIME_INTERNAL_ERR", "Futás közbeni belső hiba a(z) ''{0}'' osztályban. " }, { "RUN_TIME_COPY_ERR", "Futás közbeni belső hiba az <xsl:copy> végrehajtásakor." }, { "DATA_CONVERSION_ERR", "Érvénytelen átalakítás ''{0}'' típusról ''{1}'' típusra." }, { "EXTERNAL_FUNC_ERR", "A(z) ''{0}'' külső függvényt az XSLTC nem támogatja." }, { "EQUALITY_EXPR_ERR", "Ismeretlen argumentumtípus található az egyenlőségi kifejezésben." }, { "INVALID_ARGUMENT_ERR", "A(z) ''{0}'' érvénytelen argumentumtípus a(z) ''{1}'' hívásához " }, { "FORMAT_NUMBER_ERR", "Kísérlet a(z) ''{0}'' formázására a(z) ''{1}'' mintával." }, { "ITERATOR_CLONE_ERR", "A(z) ''{0}'' iterátor nem klónozható." }, { "AXIS_SUPPORT_ERR", "A(z) ''{0}'' tengelyre az iterátor nem támogatott." }, { "TYPED_AXIS_SUPPORT_ERR", "A tipizált ''{0}'' tengelyre az iterátor nem támogatott." }, { "STRAY_ATTRIBUTE_ERR", "A(z) ''{0}'' attribútum kívül esik az elemen." }, { "STRAY_NAMESPACE_ERR", "A(z) ''{0}''=''{1}'' névtérdeklaráció kívül esik az elemen." }, { "NAMESPACE_PREFIX_ERR", "A(z) ''{0}'' előtag névtere nincs deklarálva." }, { "DOM_ADAPTER_INIT_ERR", "Nem megfelelő típusú forrás DOM használatával jött létre a DOMAdapter." }, { "PARSER_DTD_SUPPORT_ERR", "A használt SAX értelmező nem kezeli a DTD deklarációs eseményeket." }, { "NAMESPACES_SUPPORT_ERR", "A használt SAX értelmező nem támogatja az XML névtereket." }, { "CANT_RESOLVE_RELATIVE_URI_ERR", "Nem lehet feloldani a(z) ''{0}'' URI hivatkozást." }, { "UNSUPPORTED_XSL_ERR", "Nem támogatott XSL elem: ''{0}''" }, { "UNSUPPORTED_EXT_ERR", "Ismeretlen XSLTC kiterjesztés: ''{0}''" }, { "UNKNOWN_TRANSLET_VERSION_ERR", "A megadott ''{0}'' translet az XSLTC egy újabb verziójával készült, mint a használatban lévő XSLTC verzió. Újra kell fordítania a stíluslapot, vagy a translet futtatásához az XSLTC újabb verzióját kell használnia." }, { "INVALID_QNAME_ERR", "Egy olyan attribútum, aminek az értéke csak QName lehet, ''{0}'' értékkel rendelkezett." }, { "INVALID_NCNAME_ERR", "Egy olyan attribútum, amelynek értéke csak NCName lehet, ''{0}'' értékkel rendelkezett." }, { "UNALLOWED_EXTENSION_FUNCTION_ERR", "A(z) ''{0}'' kiterjesztési függvény használata nem megengedett, ha biztonságos feldolgozás be van kapcsolva. " }, { "UNALLOWED_EXTENSION_ELEMENT_ERR", "A(z) ''{0}'' kiterjesztési elem használata nem megengedett, ha biztonságos feldolgozás be van kapcsolva. " } };
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.ErrorMessages_hu
 * JD-Core Version:    0.7.0.1
 */