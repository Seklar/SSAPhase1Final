package staysafe.andy;

public class Journey implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String startingloc;
	private String destination;
	private double interval;
	
	public Journey(String name, String startingloc, String destination, double interval)
	{
		this.name = name;
		this.startingloc = startingloc;
		this.destination = destination;
		this.interval = interval;
	}
	
	public String getName() {return name;}
	public String getStart() {return startingloc;}
	public String getEnd() {return destination;}
	public double getInterval() {return interval;}
	public String getIntervalString() {return Double.toString(interval);}
	
	public void setName(String name){this.name = name;}
	public void setStart(String startingloc){this.startingloc = startingloc;}
	public void setEnd(String destination){this.destination = destination;}
	public void setInterval(double interval){this.interval = interval;}
	
}
