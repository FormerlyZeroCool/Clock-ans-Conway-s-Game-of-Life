import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class AccessFileSystem {
	public AccessFileSystem()
	{
		
	}

	
	public String readFile(Path path) 
			  throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(path);
	  return new String(encoded, StandardCharsets.UTF_8);
	}
	
	public ArrayList<Path> getFileListInPath(String fp)
	{
		String name,nameArray[];
		ArrayList<Path> results=new ArrayList<Path>();
		  URI uri;
		try {
			uri = AccessFileSystem.class.getResource(fp).toURI();
	        Path myPath;
	        if (uri.getScheme().equals("jar")) {
				fp = AccessFileSystem.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				uri = AccessFileSystem.class.getResource(fp).toURI();
	            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
	            myPath = fileSystem.getPath(fp);
	            
	        } else {

	            myPath = Paths.get(uri);
	        }
	        Stream<Path> walk = Files.walk(myPath, 1);
	        for (Iterator<Path> it = walk.iterator(); it.hasNext();)
	        {
	        		results.add(it.next());
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		results.remove(0);
		return results;
	}
	private URI getURIPath(String fp)
	{
		URL packageURL;
		packageURL = this.getClass().getResource(fp); 
		URI uri = null;
		try {
			uri = new URI(packageURL.toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uri;
	}
	public ArrayList<boolean[][]> getCharArrays()
	{
		
			int j;
			ArrayList<Path> charNameArray=getFileListInPath("charLookups/");
			ArrayList<boolean[][]> charArrays=new ArrayList<boolean[][]>();
			StringUtil sUtil=new StringUtil();
			for(j=0;j<charNameArray.size();j++)
			{
				try {
					charArrays.add(sUtil.convertJsonToBin(readFile(charNameArray.get(j))));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return charArrays;
	}
}
