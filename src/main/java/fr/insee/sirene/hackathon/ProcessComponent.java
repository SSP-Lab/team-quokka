package fr.insee.sirene.hackathon;

public abstract class ProcessComponent {

	String inData = null;
	String outData = null;
	String inParam = null;
	String outParam = null;

	public ProcessComponent() {

	}

	public ProcessComponent(String inData, String outData, String inParam, String outParam) {
		super();
		this.inData = inData;
		this.outData = outData;
		this.inParam = inParam;
		this.outParam = outParam;
	}

	public void execute() throws Exception {}
}
