package org.home;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import org.htmlunit.scripter.HtmlPageSaver;
import org.htmlunit.scripter.MissingPropertyException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlImage;

public class SourceForgeTest
{
     public static void main(String args[])
     {
          HtmlPage page = null;
          boolean savePagesLocally = false;
          String url = "http://www.google.com/";

          WebClient webClient = new WebClient( BrowserVersion.FIREFOX_3 );
          webClient.setThrowExceptionOnScriptError(false);

          String savePagesLocallyString = System.getProperty("savePagesLocally");
          if(savePagesLocallyString != null )
          { savePagesLocally = Boolean.valueOf(savePagesLocallyString); }

          HtmlPageSaver pageSaver = null;
          if(savePagesLocally)
          {
               String localFilePath = System.getProperty("localFilePath");

               if(localFilePath == null)
               {
                    System.err.println( "localFilePath property needs to be specified on command line, like so:" +
                         "-DlocalFilePath=somefilepath");
                    throw new MissingPropertyException("localFilePath property was not specified");
               }
               else
               { pageSaver = new HtmlPageSaver(localFilePath); }
          }

          try
          {
               page = webClient.getPage( url );

               HtmlTextInput textField1 = (HtmlTextInput) page.getElementByName("q");
               textField1.setValueAttribute("HtmlUnit");

               if( savePagesLocally )
               {
                    url = "http://www.google.com/";
                    String fullPath = pageSaver.savePageLocally(page, url);
                    System.out.println("Page with title '" + page.getTitleText() + "' saved to " + fullPath);
               }

               HtmlElement theElement2 = (HtmlElement) page.getElementByName("btnG");               
               page = theElement2.click();

               if( savePagesLocally )
               {
                    url = "http://www.google.com/#hl=en&source=hp&q=HtmlUnit&btnG=Google+Search&aq=f&aqi=g10&aql=&oq=HtmlUnit&gs_rfai=C1GzO-3WATI7ZMIzmNKmtjdYBAAAAqgQFT9BUmVA&pbx=1&fp=b567883b9d1b1766";
                    String fullPath = pageSaver.savePageLocally(page, url);
                    System.out.println("Page with title '" + page.getTitleText() + "' saved to " + fullPath);
               }

               List<HtmlAnchor> anchors3 =  page.getAnchors();
               HtmlAnchor link4 = null;
               for(HtmlAnchor anchor: anchors3)
               {
                    if(anchor.asText().indexOf("HtmlUnit") > -1 )
                    {
                         link4 = anchor;
                         break;
                    }
               }
               page = link4.click();

               System.out.println("Current page: HtmlUnit - Welcome to HtmlUnit");

               // Current page:
               // Title=HtmlUnit - Welcome to HtmlUnit
               // URL=http://htmlunit.sourceforge.net/

               if( savePagesLocally )
               {
                    url = "http://htmlunit.sourceforge.net/";
                    String fullPath = pageSaver.savePageLocally(page, url);
                    System.out.println("Page with title '" + page.getTitleText() + "' saved to " + fullPath);
               }


               System.out.println("Test has completed successfully");
          }
          catch ( FailingHttpStatusCodeException e1 )
          {
               System.err.println( "FailingHttpStatusCodeException thrown:" + e1.getMessage() );
               e1.printStackTrace();

               if( savePagesLocally )
               {
                    String fullPath = pageSaver.savePageLocally(page, "error_page.html", url);
                    System.out.println("Page with title '" + page.getTitleText() + "' saved to " + fullPath);
               }

          }
          catch ( MalformedURLException e1 )
          {
               System.err.println( "MalformedURLException thrown:" + e1.getMessage() );
               e1.printStackTrace();

               if( savePagesLocally )
               {
                    String fullPath = pageSaver.savePageLocally(page, "error_page.html", url);
                    System.out.println("Page with title '" + page.getTitleText() + "' saved to " + fullPath);
               }

           }
          catch ( IOException e1 )
          {
               System.err.println( "IOException thrown:" + e1.getMessage() );
               e1.printStackTrace();

               if( savePagesLocally )
               {
                    String fullPath = pageSaver.savePageLocally(page, "error_page.html", url);
                    System.out.println("Page with title '" + page.getTitleText() + "' saved to " + fullPath);
               }

          }
          catch( Exception e )
          {
               System.err.println( "General exception thrown:" + e.getMessage() );
               e.printStackTrace();

               if( savePagesLocally )
               {
                    String fullPath = pageSaver.savePageLocally(page, "error_page.html", url);
                    System.out.println("Page with title '" + page.getTitleText() + "' saved to " + fullPath);
               }

          }
     }
}
