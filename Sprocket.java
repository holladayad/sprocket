
package CIS484.sprocket;


public class Sprocket implements Rotate, Comparable<Sprocket> {
    
    // Data Fields (non-static and Static)
    public String IDNum;
    public String label;
    public double weight;
    
    public static int sprocketCount = 0;
    
    // Constructor
    public Sprocket(String label, double weight)
    {
        // All constructors should initialize all data fields
        // even the ones you dont receive information for from
        // the constructors parameters
        this.label = label;
        this.weight = weight;
        this.IDNum = "sprocket" + sprocketCount++;
    }
    
    // Methods (non-static and static)
    // Getters and Setters
    public String getLabel()
    {
        return this.label;
    }
    
    public void setLabel(String label)
    {
        this.label = label;
    }
    
    public double getWeight()
    {
        return this.weight;
    }
    
    public void setWeight(double weight)
    {
        this.weight=weight;
    }
    
    public String getIDNum()
    {
        return this.IDNum;
    }
    
    public String toString()
    {
        return "Type: " + this.label + ",weight: " + this.weight 
                + "oz, ID: " + this.IDNum;
    }
    
    public String howToRotate()
    {
        return "[Rotate Me Very Carefully!]";
    }
    
    public int compareTo(Sprocket o)
    {
        int result = 0;
        if (this.weight < o.weight)
            result = -1;
        else if(this.weight > o.weight)
            result = 1;
        else
            result = 0;
        
        return result;
    }
}
