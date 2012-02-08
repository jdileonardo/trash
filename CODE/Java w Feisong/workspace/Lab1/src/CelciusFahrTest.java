
public class CelciusFahrTest {
	public static void main(String args[] ){
		
		CelciusFahrConvert myTemp = new CelciusFahrConvert(0,0);
		myTemp.setCel(10);
		myTemp.convertFar();
		
		System.out.println(" Far " + myTemp.getFar() + " Cel  " + myTemp.getCel() );
		
		myTemp.setFar(32);
		myTemp.convertCel();
		
		System.out.println(" Far " + myTemp.getFar() + " Cel  " + myTemp.getCel() );		
	}

}
