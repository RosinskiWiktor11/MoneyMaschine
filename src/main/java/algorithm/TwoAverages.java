package algorithm;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import com.victor.entity.Value;
import com.victor.utils.Algorithm;




public class TwoAverages extends Algorithm {

	private static int minShortRange=3;
	private static int maxShortRange=50;
	private static int minLongRange=5;
	private static int maxLongRange=100;
	private static int maxAmountAlgorithmPopulation=3430;
	
	/** sredna kroczaca krotka */
	private BigDecimal shortAverage;
	/** srednia kroczaca dluga */
	private BigDecimal longAverage;
	/** kolejka kursow */
	private Value[] queue;
	/** wskaznik dla kolejki */
	private int index;
	/** dlugosc sredniej kroczacej krotkiej */
	private int shortRange;
	/** dlugosc sredniej kroczacej dlugiej */
	private int longRange;
	
	 /**
	 * {@link TwoAverages}
	 */
	public TwoAverages() {
		super();
		index=0;
		name="TwoAverages";
	}
	
	/**
	 * {@link TwoAverages}
	 */
	public TwoAverages(int shortRange, int longRange){
		this();
		this.shortRange=shortRange;
		this.longRange=longRange;
	}
	

	/* (non-Javadoc) @see com.victor.utils.Algorithm#loadParameter(java.lang.String) */
	@Override
	public void loadParameter(String params) {
		String paramtemp=params.substring(0,params.indexOf(";"));
		shortRange=Integer.parseInt(paramtemp);
		paramtemp=params.substring(params.indexOf(";")+1, params.length());
		longRange=Integer.parseInt(paramtemp);
		paramsDescription=params;
		index=-1;
		queue=new Value[longRange]; 
	}
	

	/* (non-Javadoc) @see com.victor.utils.Algorithm#openLongTransaction() */
	@Override
	public boolean openLongTransaction() {
		
		if(shortAverage.compareTo(longAverage)>0){
//			System.out.println("2 Avr");
//			System.out.println("OPEN: "+shortAverage+">"+longAverage+"="+(shortAverage.compareTo(longAverage)>0));
			return true;
		}
			
		else
			return false;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#closeLongTransaction() */
	@Override
	public boolean closeLongTransaction() {
		
		if(shortAverage.compareTo(longAverage)<0){
//			System.out.println("2 Avr");
//			System.out.println("CLOSE: "+shortAverage+"<"+longAverage+"="+(shortAverage.compareTo(longAverage)<0));
			return true;
		}
		else 
			return false;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#openShortTransaction() */
	@Override
	public boolean openShortTransaction() {
		if(shortAverage.compareTo(longAverage)<0)
			return true;
		else 
			return false;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#closeShortTransaction() */
	@Override
	public boolean closeShortTransaction() {
		if(shortAverage.compareTo(longAverage)>0)
			return true;
		else
			return false;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#getLengthListToLoad() */
	@Override
	public int getLengthListToLoad() {
		return longRange;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#loadAlgorithm(java.util.List) */
	@Override
	public boolean loadAlgorithmToTest(List<? extends Value> priceListToLoad) {
		if(longRange>=priceListToLoad.size())
			return false;
		queue=new Value[longRange];
		index=0;
		int i=0;
		for(;i<queue.length-1;i++){
			queue[i]=priceListToLoad.get(i);
			index++;
		}
		index--;
		if(i<priceListToLoad.size()){
			putPrice(priceListToLoad.get(i));
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#putPrice(com.victor.skandia.entity.Value) */
	@Override
	public void putPrice(Value value) {
		index++;
		
		if(! (index<queue.length)){
			index=0;
		}
		queue[index]=value;
		calculateAverages();
	}

	
	/* (non-Javadoc) @see com.victor.utils.Algorithm#getRandomAlgorithmVersion(int) */
	@Override
	public List<? extends Algorithm> getRandomAlgorithmVersion(int numberOfVersions) {

		int amount;
		if(numberOfVersions>maxAmountAlgorithmPopulation)
			amount=maxAmountAlgorithmPopulation;
		else
			amount=numberOfVersions;
		return prepareRandomAlorithmRegressive(amount);
	}
	
	/** metoda tworzenia listy nowych algorytmow losujac algorytm i po sprawdzeniu, ze nie istnieje taki juz na liscie dodaniu go do listy */
	private  List<? extends Algorithm> prepareRandomAlorithmProgressive(int numberOfVersions){
		List<TwoAverages> versionList=new ArrayList<TwoAverages>();
		TwoAverages algorithm;
		Random random = new Random();
		int randomRange;
		boolean algorithmExist;
		
		for(int i=0;i<numberOfVersions;i++){
			algorithm=new TwoAverages();
			randomRange= random.nextInt(maxLongRange - minLongRange) + minLongRange;
			algorithm.longRange=randomRange;
			algorithm.shortRange=randomRange;
			//dopoki krotki krok nie bedzie krotszy od dlugiego
			while(! (algorithm.longRange>algorithm.shortRange)){
				randomRange= random.nextInt(maxShortRange - minShortRange) + minShortRange;
				algorithm.shortRange=randomRange;
			}
			
			algorithmExist=false;
			//sprawdza czy istnieje juz taki algorytm
			for(TwoAverages twoAverages: versionList){
				if(twoAverages.equals(algorithm)){
					i--;
					algorithmExist=true;
					break;
				}
			}
			//jezeli nie istnieje, dodaje do listy
			if(!algorithmExist){
				algorithm.setParamsDescription(algorithm.shortRange+";"+algorithm.longRange);
				versionList.add(algorithm);
			}
		}
		return versionList;
	}
	
	/** metoda tworzenia listy nowych algorytmow polegajaca na stworzeniu wszystkich mozliwosci algorytmow, 
	 * a nastepnie losowo usuwanych do momentu az dlugosc listy bedzie odpowiadac liczbie potrzebnych algorytmow */
	private  List<? extends Algorithm> prepareRandomAlorithmRegressive(int numberOfVersions){
		List<TwoAverages> versionList=new ArrayList<TwoAverages>();
		Random random = new Random();
		int deletedIndex;
		TwoAverages algorithm;
		for(int longIndex=minLongRange; longIndex<maxLongRange;longIndex++)
			for(int shortIndex=minShortRange; shortIndex<maxShortRange && shortIndex<longIndex; shortIndex++){
				algorithm=new TwoAverages(shortIndex, longIndex);
				algorithm.setParamsDescription(algorithm.shortRange+";"+algorithm.longRange);
				versionList.add(algorithm);
			}
		
		while(versionList.size()>numberOfVersions){
			deletedIndex=random.nextInt(versionList.size());
			versionList.remove(deletedIndex);
		}
		
		return versionList;
	}
	


	/* (non-Javadoc) @see com.victor.utils.Algorithm#equals(java.lang.Object) */
	@Override
	public boolean equals(Object o) {
		if(o==null)
			return false;
		
		if (o instanceof TwoAverages) {
			TwoAverages algorithm = (TwoAverages) o;

			if(shortRange==algorithm.shortRange && longRange==algorithm.longRange)
				return true;
			else 
				return false;
		}
		return false;
	}

	
	/** przelicza srednie */
	private void calculateAverages(){
		int tempIndex=index;
		if(queue[queue.length-1]==null){
			longAverage=BigDecimal.ZERO;
			shortAverage=BigDecimal.ZERO;
			return;
		}

		BigDecimal sum=BigDecimal.ZERO;
		//oblicza dluga srednia kroczaca
		for(int i=0;i<longRange;i++){
			sum=sum.add(queue[tempIndex].getValue());
			tempIndex--;
			if(tempIndex<0)
				tempIndex=queue.length-1;
		}
		longAverage=sum.divide(new BigDecimal(longRange), MathContext.DECIMAL32);
		
		//oblicza krotka srednia kroczaca
		sum=BigDecimal.ZERO;
		tempIndex=index;
		for(int i=0;i<shortRange;i++){
			sum=sum.add(queue[tempIndex].getValue());
			tempIndex--;
			if(tempIndex<0)
				tempIndex=queue.length-1;
		}
		shortAverage=sum.divide(new BigDecimal(shortRange), MathContext.DECIMAL32);
	}

	public static void main(String... at){
		System.out.println("ok");
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#getParamsValues() */
	@Override
	public BigDecimal[] getParamsValues() {
		BigDecimal[] paramTable=new BigDecimal[2];
		paramTable[0]=shortAverage;
		paramTable[1]=longAverage;
		return paramTable;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#getParamNumber() */
	@Override
	public int getParamNumber() {
		return 2;
	}





}
