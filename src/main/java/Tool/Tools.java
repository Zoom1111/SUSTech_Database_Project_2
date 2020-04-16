package Tool;

public class Tools
{
    public String getSeparator()
    {
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win"))
            return "\\\\";
        else
            return "/";
    }

    public String getLineSeparator()
    {
        return  System.lineSeparator();
    }
}
