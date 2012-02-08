
public class CelciusFahrConvert {

	public double cel;
	public double far;
	
	public CelciusFahrConvert ( double _cel, double _far)
	{
		this.cel = _cel;
		this.far = _far;
	}
	
	public void setCel(double _cel)
	{
		this.cel = _cel;
	}
	
	public void setFar(double _far)
	{
		this.far = _far;
	}
	
	public void convertCel()
	{
		this.cel = (this.far-32)/(9.0/5.0);
	}
	
	public void convertFar()
	{
		
		this.far = (9.0/5.0)*this.cel+32;
	}
	
	public double getCel()
	{
		return this.cel;
	}
	
	public double getFar()
	{
		
		return this.far;
		
	}
	
}
