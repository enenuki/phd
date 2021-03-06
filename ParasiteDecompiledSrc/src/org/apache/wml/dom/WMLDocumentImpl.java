package org.apache.wml.dom;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import org.apache.wml.WMLDocument;
import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class WMLDocumentImpl
  extends DocumentImpl
  implements WMLDocument
{
  private static final long serialVersionUID = -6582904849512384104L;
  private static Hashtable _elementTypesWML;
  private static final Class[] _elemClassSigWML = { WMLDocumentImpl.class, String.class };
  
  public Element createElement(String paramString)
    throws DOMException
  {
    Class localClass = (Class)_elementTypesWML.get(paramString);
    if (localClass != null) {
      try
      {
        Constructor localConstructor = localClass.getConstructor(_elemClassSigWML);
        return (Element)localConstructor.newInstance(new Object[] { this, paramString });
      }
      catch (Exception localException)
      {
        Object localObject;
        if ((localException instanceof InvocationTargetException)) {
          localObject = ((InvocationTargetException)localException).getTargetException();
        } else {
          localObject = localException;
        }
        System.out.println("Exception " + localObject.getClass().getName());
        System.out.println(((Throwable)localObject).getMessage());
        throw new IllegalStateException("Tag '" + paramString + "' associated with an Element class that failed to construct.");
      }
    }
    return new WMLElementImpl(this, paramString);
  }
  
  public WMLDocumentImpl(DocumentType paramDocumentType)
  {
    super(paramDocumentType, false);
  }
  
  static
  {
    _elementTypesWML = new Hashtable();
    _elementTypesWML.put("b", WMLBElementImpl.class);
    _elementTypesWML.put("noop", WMLNoopElementImpl.class);
    _elementTypesWML.put("a", WMLAElementImpl.class);
    _elementTypesWML.put("setvar", WMLSetvarElementImpl.class);
    _elementTypesWML.put("access", WMLAccessElementImpl.class);
    _elementTypesWML.put("strong", WMLStrongElementImpl.class);
    _elementTypesWML.put("postfield", WMLPostfieldElementImpl.class);
    _elementTypesWML.put("do", WMLDoElementImpl.class);
    _elementTypesWML.put("wml", WMLWmlElementImpl.class);
    _elementTypesWML.put("tr", WMLTrElementImpl.class);
    _elementTypesWML.put("go", WMLGoElementImpl.class);
    _elementTypesWML.put("big", WMLBigElementImpl.class);
    _elementTypesWML.put("anchor", WMLAnchorElementImpl.class);
    _elementTypesWML.put("timer", WMLTimerElementImpl.class);
    _elementTypesWML.put("small", WMLSmallElementImpl.class);
    _elementTypesWML.put("optgroup", WMLOptgroupElementImpl.class);
    _elementTypesWML.put("head", WMLHeadElementImpl.class);
    _elementTypesWML.put("td", WMLTdElementImpl.class);
    _elementTypesWML.put("fieldset", WMLFieldsetElementImpl.class);
    _elementTypesWML.put("img", WMLImgElementImpl.class);
    _elementTypesWML.put("refresh", WMLRefreshElementImpl.class);
    _elementTypesWML.put("onevent", WMLOneventElementImpl.class);
    _elementTypesWML.put("input", WMLInputElementImpl.class);
    _elementTypesWML.put("prev", WMLPrevElementImpl.class);
    _elementTypesWML.put("table", WMLTableElementImpl.class);
    _elementTypesWML.put("meta", WMLMetaElementImpl.class);
    _elementTypesWML.put("template", WMLTemplateElementImpl.class);
    _elementTypesWML.put("br", WMLBrElementImpl.class);
    _elementTypesWML.put("option", WMLOptionElementImpl.class);
    _elementTypesWML.put("u", WMLUElementImpl.class);
    _elementTypesWML.put("p", WMLPElementImpl.class);
    _elementTypesWML.put("select", WMLSelectElementImpl.class);
    _elementTypesWML.put("em", WMLEmElementImpl.class);
    _elementTypesWML.put("i", WMLIElementImpl.class);
    _elementTypesWML.put("card", WMLCardElementImpl.class);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.dom.WMLDocumentImpl
 * JD-Core Version:    0.7.0.1
 */