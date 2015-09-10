package org.apache.xerces.impl.xs.identity;

import org.apache.xerces.impl.xpath.XPath;
import org.apache.xerces.impl.xpath.XPath.Axis;
import org.apache.xerces.impl.xpath.XPath.LocationPath;
import org.apache.xerces.impl.xpath.XPath.NodeTest;
import org.apache.xerces.impl.xpath.XPath.Step;
import org.apache.xerces.util.IntStack;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ItemPSVI;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSTypeDefinition;

public class XPathMatcher
{
  protected static final boolean DEBUG_ALL = false;
  protected static final boolean DEBUG_METHODS = false;
  protected static final boolean DEBUG_METHODS2 = false;
  protected static final boolean DEBUG_METHODS3 = false;
  protected static final boolean DEBUG_MATCH = false;
  protected static final boolean DEBUG_STACK = false;
  protected static final boolean DEBUG_ANY = false;
  protected static final int MATCHED = 1;
  protected static final int MATCHED_ATTRIBUTE = 3;
  protected static final int MATCHED_DESCENDANT = 5;
  protected static final int MATCHED_DESCENDANT_PREVIOUS = 13;
  private final XPath.LocationPath[] fLocationPaths;
  private final int[] fMatched;
  protected Object fMatchedString;
  private final IntStack[] fStepIndexes;
  private final int[] fCurrentStep;
  private final int[] fNoMatchDepth;
  final QName fQName = new QName();
  
  public XPathMatcher(XPath paramXPath)
  {
    this.fLocationPaths = paramXPath.getLocationPaths();
    this.fStepIndexes = new IntStack[this.fLocationPaths.length];
    for (int i = 0; i < this.fStepIndexes.length; i++) {
      this.fStepIndexes[i] = new IntStack();
    }
    this.fCurrentStep = new int[this.fLocationPaths.length];
    this.fNoMatchDepth = new int[this.fLocationPaths.length];
    this.fMatched = new int[this.fLocationPaths.length];
  }
  
  public boolean isMatched()
  {
    for (int i = 0; i < this.fLocationPaths.length; i++) {
      if (((this.fMatched[i] & 0x1) == 1) && ((this.fMatched[i] & 0xD) != 13) && ((this.fNoMatchDepth[i] == 0) || ((this.fMatched[i] & 0x5) == 5))) {
        return true;
      }
    }
    return false;
  }
  
  protected void handleContent(XSTypeDefinition paramXSTypeDefinition, boolean paramBoolean, Object paramObject, short paramShort, ShortList paramShortList) {}
  
  protected void matched(Object paramObject, short paramShort, ShortList paramShortList, boolean paramBoolean) {}
  
  public void startDocumentFragment()
  {
    this.fMatchedString = null;
    for (int i = 0; i < this.fLocationPaths.length; i++)
    {
      this.fStepIndexes[i].clear();
      this.fCurrentStep[i] = 0;
      this.fNoMatchDepth[i] = 0;
      this.fMatched[i] = 0;
    }
  }
  
  public void startElement(QName paramQName, XMLAttributes paramXMLAttributes)
  {
    for (int i = 0; i < this.fLocationPaths.length; i++)
    {
      int j = this.fCurrentStep[i];
      this.fStepIndexes[i].push(j);
      if (((this.fMatched[i] & 0x5) == 1) || (this.fNoMatchDepth[i] > 0))
      {
        this.fNoMatchDepth[i] += 1;
      }
      else
      {
        if ((this.fMatched[i] & 0x5) == 5) {
          this.fMatched[i] = 13;
        }
        XPath.Step[] arrayOfStep = this.fLocationPaths[i].steps;
        while ((this.fCurrentStep[i] < arrayOfStep.length) && (arrayOfStep[this.fCurrentStep[i]].axis.type == 3)) {
          this.fCurrentStep[i] += 1;
        }
        if (this.fCurrentStep[i] == arrayOfStep.length)
        {
          this.fMatched[i] = 1;
        }
        else
        {
          int k = this.fCurrentStep[i];
          while ((this.fCurrentStep[i] < arrayOfStep.length) && (arrayOfStep[this.fCurrentStep[i]].axis.type == 4)) {
            this.fCurrentStep[i] += 1;
          }
          int m = this.fCurrentStep[i] > k ? 1 : 0;
          if (this.fCurrentStep[i] == arrayOfStep.length)
          {
            this.fNoMatchDepth[i] += 1;
          }
          else
          {
            XPath.NodeTest localNodeTest;
            if (((this.fCurrentStep[i] == j) || (this.fCurrentStep[i] > k)) && (arrayOfStep[this.fCurrentStep[i]].axis.type == 1))
            {
              XPath.Step localStep = arrayOfStep[this.fCurrentStep[i]];
              localNodeTest = localStep.nodeTest;
              if (!matches(localNodeTest, paramQName))
              {
                if (this.fCurrentStep[i] > k) {
                  this.fCurrentStep[i] = k;
                } else {
                  this.fNoMatchDepth[i] += 1;
                }
              }
              else {
                this.fCurrentStep[i] += 1;
              }
            }
            else if (this.fCurrentStep[i] == arrayOfStep.length)
            {
              if (m != 0)
              {
                this.fCurrentStep[i] = k;
                this.fMatched[i] = 5;
              }
              else
              {
                this.fMatched[i] = 1;
              }
            }
            else if ((this.fCurrentStep[i] < arrayOfStep.length) && (arrayOfStep[this.fCurrentStep[i]].axis.type == 2))
            {
              int n = paramXMLAttributes.getLength();
              if (n > 0)
              {
                localNodeTest = arrayOfStep[this.fCurrentStep[i]].nodeTest;
                for (int i1 = 0; i1 < n; i1++)
                {
                  paramXMLAttributes.getName(i1, this.fQName);
                  if (matches(localNodeTest, this.fQName))
                  {
                    this.fCurrentStep[i] += 1;
                    if (this.fCurrentStep[i] != arrayOfStep.length) {
                      break;
                    }
                    this.fMatched[i] = 3;
                    for (int i2 = 0; (i2 < i) && ((this.fMatched[i2] & 0x1) != 1); i2++) {}
                    if (i2 != i) {
                      break;
                    }
                    AttributePSVI localAttributePSVI = (AttributePSVI)paramXMLAttributes.getAugmentations(i1).getItem("ATTRIBUTE_PSVI");
                    this.fMatchedString = localAttributePSVI.getActualNormalizedValue();
                    matched(this.fMatchedString, localAttributePSVI.getActualNormalizedValueType(), localAttributePSVI.getItemValueTypes(), false);
                    break;
                  }
                }
              }
              if ((this.fMatched[i] & 0x1) != 1) {
                if (this.fCurrentStep[i] > k) {
                  this.fCurrentStep[i] = k;
                } else {
                  this.fNoMatchDepth[i] += 1;
                }
              }
            }
          }
        }
      }
    }
  }
  
  public void endElement(QName paramQName, XSTypeDefinition paramXSTypeDefinition, boolean paramBoolean, Object paramObject, short paramShort, ShortList paramShortList)
  {
    for (int i = 0; i < this.fLocationPaths.length; i++)
    {
      this.fCurrentStep[i] = this.fStepIndexes[i].pop();
      if (this.fNoMatchDepth[i] > 0)
      {
        this.fNoMatchDepth[i] -= 1;
      }
      else
      {
        for (int j = 0; (j < i) && ((this.fMatched[j] & 0x1) != 1); j++) {}
        if ((j >= i) && (this.fMatched[j] != 0)) {
          if ((this.fMatched[j] & 0x3) == 3)
          {
            this.fMatched[i] = 0;
          }
          else
          {
            handleContent(paramXSTypeDefinition, paramBoolean, paramObject, paramShort, paramShortList);
            this.fMatched[i] = 0;
          }
        }
      }
    }
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = super.toString();
    int i = str.lastIndexOf('.');
    if (i != -1) {
      str = str.substring(i + 1);
    }
    localStringBuffer.append(str);
    for (int j = 0; j < this.fLocationPaths.length; j++)
    {
      localStringBuffer.append('[');
      XPath.Step[] arrayOfStep = this.fLocationPaths[j].steps;
      for (int k = 0; k < arrayOfStep.length; k++)
      {
        if (k == this.fCurrentStep[j]) {
          localStringBuffer.append('^');
        }
        localStringBuffer.append(arrayOfStep[k].toString());
        if (k < arrayOfStep.length - 1) {
          localStringBuffer.append('/');
        }
      }
      if (this.fCurrentStep[j] == arrayOfStep.length) {
        localStringBuffer.append('^');
      }
      localStringBuffer.append(']');
      localStringBuffer.append(',');
    }
    return localStringBuffer.toString();
  }
  
  private String normalize(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      switch (c)
      {
      case '\n': 
        localStringBuffer.append("\\n");
        break;
      default: 
        localStringBuffer.append(c);
      }
    }
    return localStringBuffer.toString();
  }
  
  private static boolean matches(XPath.NodeTest paramNodeTest, QName paramQName)
  {
    if (paramNodeTest.type == 1) {
      return paramNodeTest.name.equals(paramQName);
    }
    if (paramNodeTest.type == 4) {
      return paramNodeTest.name.uri == paramQName.uri;
    }
    return true;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.identity.XPathMatcher
 * JD-Core Version:    0.7.0.1
 */