
package CIS484.sprocket;


public class CeramicSprocket extends Sprocket implements Rotate {
    
    public String ceramicColor;
    
    public CeramicSprocket(String label, double weight, String ceramicColor)
    {
        //inheriting public data fields
        super(label, weight);
        this.ceramicColor = ceramicColor;
        
        this.IDNum = "painted_" + this.IDNum;
    }
    
    public String getCeramicColor()
    {
        return this.ceramicColor;
    }
    
    public void setCeramicColor(String ceramicColor)
    {
        this.ceramicColor = ceramicColor;
    }
    
    public String toString()
    {
        return super.toString() + ", Paint Color: " + this.getCeramicColor();
    }
    
    public String howToRotate()
    {
        return "[Spin me fast!]";
    }
}
