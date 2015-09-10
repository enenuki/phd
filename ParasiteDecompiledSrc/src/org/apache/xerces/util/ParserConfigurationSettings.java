package org.apache.xerces.util;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;

public class ParserConfigurationSettings
  implements XMLComponentManager
{
  protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
  protected ArrayList fRecognizedProperties = new ArrayList();
  protected HashMap fProperties = new HashMap();
  protected ArrayList fRecognizedFeatures = new ArrayList();
  protected HashMap fFeatures = new HashMap();
  protected XMLComponentManager fParentSettings;
  
  public ParserConfigurationSettings()
  {
    this(null);
  }
  
  public ParserConfigurationSettings(XMLComponentManager paramXMLComponentManager)
  {
    this.fParentSettings = paramXMLComponentManager;
  }
  
  public void addRecognizedFeatures(String[] paramArrayOfString)
  {
    int i = paramArrayOfString != null ? paramArrayOfString.length : 0;
    for (int j = 0; j < i; j++)
    {
      String str = paramArrayOfString[j];
      if (!this.fRecognizedFeatures.contains(str)) {
        this.fRecognizedFeatures.add(str);
      }
    }
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws XMLConfigurationException
  {
    checkFeature(paramString);
    this.fFeatures.put(paramString, paramBoolean ? Boolean.TRUE : Boolean.FALSE);
  }
  
  public void addRecognizedProperties(String[] paramArrayOfString)
  {
    int i = paramArrayOfString != null ? paramArrayOfString.length : 0;
    for (int j = 0; j < i; j++)
    {
      String str = paramArrayOfString[j];
      if (!this.fRecognizedProperties.contains(str)) {
        this.fRecognizedProperties.add(str);
      }
    }
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws XMLConfigurationException
  {
    checkProperty(paramString);
    this.fProperties.put(paramString, paramObject);
  }
  
  public boolean getFeature(String paramString)
    throws XMLConfigurationException
  {
    Boolean localBoolean = (Boolean)this.fFeatures.get(paramString);
    if (localBoolean == null)
    {
      checkFeature(paramString);
      return false;
    }
    return localBoolean.booleanValue();
  }
  
  public Object getProperty(String paramString)
    throws XMLConfigurationException
  {
    Object localObject = this.fProperties.get(paramString);
    if (localObject == null) {
      checkProperty(paramString);
    }
    return localObject;
  }
  
  protected void checkFeature(String paramString)
    throws XMLConfigurationException
  {
    if (!this.fRecognizedFeatures.contains(paramString)) {
      if (this.fParentSettings != null)
      {
        this.fParentSettings.getFeature(paramString);
      }
      else
      {
        short s = 0;
        throw new XMLConfigurationException(s, paramString);
      }
    }
  }
  
  protected void checkProperty(String paramString)
    throws XMLConfigurationException
  {
    if (!this.fRecognizedProperties.contains(paramString)) {
      if (this.fParentSettings != null)
      {
        this.fParentSettings.getProperty(paramString);
      }
      else
      {
        short s = 0;
        throw new XMLConfigurationException(s, paramString);
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.ParserConfigurationSettings
 * JD-Core Version:    0.7.0.1
 */