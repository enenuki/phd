package org.apache.xerces.impl.dtd;

import java.util.Vector;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.parser.XMLInputSource;

public class XMLDTDDescription
  extends XMLResourceIdentifierImpl
  implements org.apache.xerces.xni.grammars.XMLDTDDescription
{
  protected String fRootName = null;
  protected Vector fPossibleRoots = null;
  
  public XMLDTDDescription(XMLResourceIdentifier paramXMLResourceIdentifier, String paramString)
  {
    setValues(paramXMLResourceIdentifier.getPublicId(), paramXMLResourceIdentifier.getLiteralSystemId(), paramXMLResourceIdentifier.getBaseSystemId(), paramXMLResourceIdentifier.getExpandedSystemId());
    this.fRootName = paramString;
    this.fPossibleRoots = null;
  }
  
  public XMLDTDDescription(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    setValues(paramString1, paramString2, paramString3, paramString4);
    this.fRootName = paramString5;
    this.fPossibleRoots = null;
  }
  
  public XMLDTDDescription(XMLInputSource paramXMLInputSource)
  {
    setValues(paramXMLInputSource.getPublicId(), null, paramXMLInputSource.getBaseSystemId(), paramXMLInputSource.getSystemId());
    this.fRootName = null;
    this.fPossibleRoots = null;
  }
  
  public String getGrammarType()
  {
    return "http://www.w3.org/TR/REC-xml";
  }
  
  public String getRootName()
  {
    return this.fRootName;
  }
  
  public void setRootName(String paramString)
  {
    this.fRootName = paramString;
    this.fPossibleRoots = null;
  }
  
  public void setPossibleRoots(Vector paramVector)
  {
    this.fPossibleRoots = paramVector;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof XMLGrammarDescription)) {
      return false;
    }
    if (!getGrammarType().equals(((XMLGrammarDescription)paramObject).getGrammarType())) {
      return false;
    }
    XMLDTDDescription localXMLDTDDescription = (XMLDTDDescription)paramObject;
    if (this.fRootName != null)
    {
      if ((localXMLDTDDescription.fRootName != null) && (!localXMLDTDDescription.fRootName.equals(this.fRootName))) {
        return false;
      }
      if ((localXMLDTDDescription.fPossibleRoots != null) && (!localXMLDTDDescription.fPossibleRoots.contains(this.fRootName))) {
        return false;
      }
    }
    else if (this.fPossibleRoots != null)
    {
      if (localXMLDTDDescription.fRootName != null)
      {
        if (!this.fPossibleRoots.contains(localXMLDTDDescription.fRootName)) {
          return false;
        }
      }
      else
      {
        if (localXMLDTDDescription.fPossibleRoots == null) {
          return false;
        }
        boolean bool = false;
        for (int i = 0; i < this.fPossibleRoots.size(); i++)
        {
          String str = (String)this.fPossibleRoots.elementAt(i);
          bool = localXMLDTDDescription.fPossibleRoots.contains(str);
          if (bool) {
            break;
          }
        }
        if (!bool) {
          return false;
        }
      }
    }
    if (this.fExpandedSystemId != null)
    {
      if (!this.fExpandedSystemId.equals(localXMLDTDDescription.fExpandedSystemId)) {
        return false;
      }
    }
    else if (localXMLDTDDescription.fExpandedSystemId != null) {
      return false;
    }
    if (this.fPublicId != null)
    {
      if (!this.fPublicId.equals(localXMLDTDDescription.fPublicId)) {
        return false;
      }
    }
    else if (localXMLDTDDescription.fPublicId != null) {
      return false;
    }
    return true;
  }
  
  public int hashCode()
  {
    if (this.fExpandedSystemId != null) {
      return this.fExpandedSystemId.hashCode();
    }
    if (this.fPublicId != null) {
      return this.fPublicId.hashCode();
    }
    return 0;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.XMLDTDDescription
 * JD-Core Version:    0.7.0.1
 */