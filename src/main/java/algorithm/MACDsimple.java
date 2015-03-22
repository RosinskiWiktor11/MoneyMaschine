package algorithm;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.victor.entity.Value;
import com.victor.utils.Algorithm;



public class MACDsimple extends Algorithm
{
	/** maksymalna liczebnosc populacji przy parametrach 3-50;5-100;3-30 */
	private static int maxAmountAlgorithmPopulation=27945;
	private static int minShortRange=3;
	private static int maxShortRange=50;
	private static int minLongRange=5;
	private static int maxLongRange=100;
	private static int minSignalRange=3;
	private static int maxSignalRange=30;
	
	public static void main (String ...a){
		long i=(maxLongRange-minLongRange)*(maxShortRange-minShortRange)*(maxSignalRange-minSignalRange);
		System.out.println(i);
	}
	
//	List<BigDecimal> MACDlist;
//	BigDecimal signal;
	
	int longRange;
	int shortRange;
	int signalRange;
	/** wskaznik kolejki - wskazuje na pierwszy element w kolejce */
	private int index;
	private Value[] queue;
	
	/** wskaznik kolejki MACD - wskazuje na pierwszy element w kolejce */
	private int indexMACD;
	private BigDecimal[] queueMACD;
	
	private BigDecimal MACD;
	private BigDecimal signal;
	
//	private BigDecimal longAverage;
//	private BigDecimal shortAverage;

	/** {@link MACDsimple} */
	public MACDsimple() {
		super();
		index=0;
		indexMACD=0;
		name="MACDsimple";
	}
	
	/** {@link MACDsimple} */
	public MACDsimple(int shortRange, int longRange, int signalRange){
		this();
		this.shortRange=shortRange;
		this.longRange=longRange;
		this.signalRange =signalRange;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#loadParameter(java.lang.String) */
	@Override
	public void loadParameter(String params) {
		String paramtemp=params.substring(0,params.indexOf(";"));
		shortRange=Integer.parseInt(paramtemp);
		paramtemp=params.substring(params.indexOf(";")+1,params.lastIndexOf(";"));
		longRange=Integer.parseInt(paramtemp);
		paramtemp=params.substring(params.lastIndexOf(";")+1, params.length());
		signalRange=Integer.parseInt(paramtemp);
		paramsDescription=params;
		index=-1;
		indexMACD=-1;
		queue=new Value[longRange]; 
		queueMACD=new BigDecimal[signalRange];
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#openLongTransaction() */
	@Override
	public boolean openLongTransaction() {
		if(MACD.compareTo(signal)>0){
//			System.out.println("MACD");
//			System.out.println("OPEN: "+MACD+">"+signal+"="+(MACD.compareTo(signal)>0));
			return true;
		}
			
		else
			return false;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#closeLongTransaction() */
	@Override
	public boolean closeLongTransaction() {

		if(MACD.compareTo(signal)<0){
//			System.out.println("MACD");
//			System.out.println("CLOSE: "+MACD+"<"+signal+"="+(MACD.compareTo(signal)<0));
			return true;
		}
			
		else
			return false;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#openShortTransaction() */
	@Override
	public boolean openShortTransaction() {
		if(MACD.compareTo(signal)<0)
			return true;
		else
			return false;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#closeShortTransaction() */
	@Override
	public boolean closeShortTransaction() {
		if(MACD.compareTo(signal)>0)
			return true;
		else
			return false;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#getLengthListToLoad() */
	@Override
	public int getLengthListToLoad() {
		return longRange;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#loadAlgorithmToTest(java.util.List) */
	@Override
	public boolean loadAlgorithmToTest(List<? extends Value> priceListToLoad) {
		if(longRange>=priceListToLoad.size())
			return false;
		queue=new Value[longRange];
		queueMACD=new BigDecimal[signalRange];
		int i=0;
		index=-1;
		indexMACD=-1;
		while(queueMACD[queueMACD.length-1]==null){
			putPrice(priceListToLoad.get(i));
			
			if(i<priceListToLoad.size()){
				putPrice(priceListToLoad.get(i));
				return true;
			}else{
				return false;
			}
		}
		return true;
		
//		index=0;
//		int i=0;
//		for(;i<queue.length-1;i++){
//			queue[i]=priceListToLoad.get(i);
//			index++;
//		}
//		index--;
		
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#putPrice(com.victor.skandia.entity.Value) */
	@Override
	public void putPrice(Value value) {
		index++;
		
		if(! (index<queue.length)){
			index=0;
		}
		queue[index]=value;
		calculateAlgorithm();
	}
	
	public void putMACD(BigDecimal macd){
		indexMACD++;
		if(!(indexMACD<queueMACD.length)){
			indexMACD=0;
		}
		queueMACD[indexMACD]=macd;
		//TODO obliczenie sygnalu?
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#getRandomAlgorithmVersion(int) */
	@Override
	public List<? extends Algorithm> getRandomAlgorithmVersion(
			int numberOfVersions) {
		
		int amount;
		if(numberOfVersions>maxAmountAlgorithmPopulation)
			amount=maxAmountAlgorithmPopulation;
		else
			amount=numberOfVersions;
		return prepareRandomAlorithmRegressive(amount);
	}
	

	private  List<? extends Algorithm> prepareRandomAlorithmRegressive(int numberOfVersions){
		Random random = new Random();
		int deletedIndex;
		List<MACDsimple> resultList=new ArrayList<MACDsimple>();
		MACDsimple algorithm;
		
		for(int longIndex=minLongRange;longIndex<maxLongRange;longIndex++)
			for(int shortIndex=minShortRange<longIndex?longIndex:minShortRange ;shortIndex<maxShortRange;shortIndex++)
				for(int signalIndex=minSignalRange;signalIndex<maxSignalRange;signalIndex++){
//					System.out.println(signalIndex);
					algorithm=new MACDsimple(longIndex, shortIndex, signalIndex);
					algorithm.setParamsDescription(algorithm.shortRange+";"+algorithm.longRange+";"+algorithm.signalRange);
					resultList.add(algorithm);
				}
		System.out.println(resultList.size());
		while(resultList.size()>numberOfVersions){
			deletedIndex=random.nextInt(resultList.size());
			resultList.remove(deletedIndex);
		}
		
		return resultList; 
	}
	
	private void calculateAlgorithm(){
		int tempIndex=index;
		if(queue[queue.length-1]==null){
			MACD=BigDecimal.ZERO;
			signal=BigDecimal.ZERO;
			return;
		}
		
		//obliczanie dlugiej sredniej
		BigDecimal sum=BigDecimal.ZERO;
		for(int i=0;i<longRange;i++){
			sum=sum.add(queue[tempIndex].getValue());
			tempIndex--;
			if(tempIndex<0){
				tempIndex=queue.length-1;
			}
		}
		//dluga srednia
		BigDecimal longEma=sum.divide(new BigDecimal(longRange), MathContext.DECIMAL32);
		
		//obliczanie krotkiej sredniej
		tempIndex=index;
		sum=BigDecimal.ZERO;
		for(int i=0;i<shortRange;i++){
			sum=sum.add(queue[tempIndex].getValue());
			tempIndex--;
			if(tempIndex<0){
				tempIndex=queue.length-1;
			}
		}
		//krotka srednia
		BigDecimal shortEma=sum.divide(new BigDecimal(shortRange), MathContext.DECIMAL32);
		
		//obliczanie wartosci MACD
		MACD=shortEma.subtract(longEma);
		//dodanie MACD do kolejki
		putMACD(MACD);
		
		if(queueMACD[queueMACD.length-1]==null){
			MACD=BigDecimal.ZERO;
			signal=BigDecimal.ZERO;
			return;
		}
		
		//obliczanie sredniej MACD - sygnal
		tempIndex=indexMACD;
		sum=BigDecimal.ZERO;
		for(int i=0;i<signalRange;i++){
			sum=sum.add(queueMACD[tempIndex]);
			tempIndex--;
			if(tempIndex<0){
				tempIndex=queueMACD.length-1;
			}
		}
		//sygnal
		signal=sum.divide(new BigDecimal(signalRange), MathContext.DECIMAL32);
	}
	
	
	/* (non-Javadoc) @see com.victor.utils.Algorithm#equals(java.lang.Object) */
	@Override
	public boolean equals(Object o) {
		if(o==null)
			return false;
		
		if (o instanceof MACDsimple) {
			MACDsimple algorithm = (MACDsimple) o;

			if(shortRange==algorithm.shortRange && longRange==algorithm.longRange)
				return true;
			else 
				return false;
		}
		return false;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#getParamsValues() */
	@Override
	public BigDecimal[] getParamsValues() {
		BigDecimal[] paramTable=new BigDecimal[2];
		paramTable[0] =MACD;
		paramTable[1]=signal;
		return paramTable;
	}

	/* (non-Javadoc) @see com.victor.utils.Algorithm#getParamNumber() */
	@Override
	public int getParamNumber() {
		return 2;
	}
	
	

//	public MACDsimple()
//	{
//		kolejkaDlugaMACD=new ArrayList<Float>();
//		MACD=new ArrayList<Float>();
//
//		dlugiKrokMACD=5;
//		krotkiKrokMACD=3;
//		signal=0;
//	}
//
//	public MACDsimple(int krotkiKrokMACD, int dlugiKrokMACD, int krokSignal)
//	{
//		MACD=new ArrayList<Float>();
//		this.krokSignal=krokSignal;
//		this.dlugiKrokMACD=dlugiKrokMACD;
//		this.krotkiKrokMACD=krotkiKrokMACD;
//		signal=0;
//	}
//
//	public MACDsimple(String algorytm)
//	{
//		StringTokenizer tokens=new StringTokenizer(algorytm, ";", false);
//		String temp=tokens.nextToken();
//
//		temp=tokens.nextToken();
//		krotkiKrokMACD=Integer.parseInt(temp);
//		
//		temp=tokens.nextToken();
//		dlugiKrokMACD=Integer.parseInt(temp);
//		
//		temp=tokens.nextToken();
//		krokSignal=Integer.parseInt(temp);
//		
//		kolejkaDlugaMACD=new ArrayList<Float>();
//		MACD=new ArrayList<Float>();
//		signal=0f;
//	}
//	
//	public WynikAlgorytm ustawAlgorytm(ArrayList<Float> kursy, float spread, float dzwignia, int krokStawka, boolean czyPozycjeDlugie) 
//	{
//		
//		WynikAlgorytm najlepszyAlgorytm=new WynikAlgorytm();
//		
//		for(krokSignal=startkrokSignal;krokSignal<maksKrokSignal;krokSignal++)
//		{
////			krokSignal=s;
//			for(dlugiKrokMACD=startdlugiKrokMACD;dlugiKrokMACD<maksDlugiKrokMACD;dlugiKrokMACD++)
//			{
////				dlugiKrokMACD=d;
//				for(krotkiKrokMACD=startkrotkiKrokMACD;krotkiKrokMACD<maksKrotkiKrokMACD;krotkiKrokMACD++)
//				{
////					krotkiKrokMACD=k;
//					if(krotkiKrokMACD>dlugiKrokMACD)
//						break;
//					
//					MACD=new ArrayList<Float>();
//					signal=0;
//					double wynikTestu=testuj(kursy, spread, dzwignia, krokStawka, czyPozycjeDlugie);
//					if(wynikTestu>najlepszyAlgorytm.getWynikTestu())
//					{
//						najlepszyAlgorytm=new WynikAlgorytm(wynikTestu, toString());
//					}
//					
//				}
//			}
//		}
//		
//		return najlepszyAlgorytm;
//	}
//
//	@Override
//	public boolean przeliczKurs(float kurs) {
//		if(kolejkaDlugaMACD.size()<dlugiKrokMACD)
//		{
//			kolejkaDlugaMACD.add(kurs);
//			return false;
//		}
//		else
//		{
//			kolejkaDlugaMACD.remove(0);
//			kolejkaDlugaMACD.add(kurs);
//			przeliczSrednie(kolejkaDlugaMACD, true);
//			return true;
//		}
//	}
//
//	@Override
//	public boolean przeliczKurs(List<Float> kursy, boolean nowaSwieczka) {
//		if(kursy.size()>=dlugiKrokMACD)
//		{
//			przeliczSrednie(kursy, nowaSwieczka);
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public boolean czyOtworzyc(boolean czyDluga) 
//	{
//		boolean odp=false;
//		
////		if(czyWystarczajacoDanych())
//		{	
//			if(MACD.get(MACD.size()-1)>signal)
//			{
//				if(czyDluga)
//				{
//					odp= true;
//				}
//				else
//					odp= false;
//			}
//			if(MACD.get(MACD.size()-1)<signal)
//			{
//				if(czyDluga)
//					odp= false;
//				else
//					odp= true;
//			}
//		}
//		return odp;
//	}
//
//
//	@Override
//	public boolean czyZamknac(boolean czyDluga) 
//	{
//		boolean odp=false;
////		if(czyWystarczajacoDanych())
//		{
//			if(MACD.get(MACD.size()-1)>signal)
//			{
//				if(czyDluga)
//					odp= false;
//				else
//					odp= true;
//			}
//			if(MACD.get(MACD.size()-1)<signal)
//			{
//				if(czyDluga)
//				{
//					odp=true;
//				}
//				else
//					odp= false;
//			}
//		}
//		
//	return odp;
//	}
//
//	@Override
//	public int podajMaksymalnaIloscDanych() {
//		return dlugiKrokMACD;
//	}
//
//	private void przeliczSrednie(List<Float> kursy, boolean nowaSwieczka)
//	{
//		if(kursy.size()<dlugiKrokMACD)
//			return;
//		else
//		{
//			float sredniaDlugaMACD;
//			float sredniaKrotkaMACD;
//			float sumaKrotka=0;
//			float sumaDluga=0;
//			int licznikKrotki=0;
//			int licznikDlugi=0;
//			
//			for(int i=(kursy.size()-1);i>=0;i--)
//			{
//				if(licznikDlugi<dlugiKrokMACD)
//				{
//					sumaDluga+=kursy.get(i);
//					licznikDlugi++;
//					if(licznikKrotki<krotkiKrokMACD)
//					{
//						sumaKrotka+=kursy.get(i);
//						licznikKrotki++;
//					}
//				}
//				else
//					break;
//			}
//			
//			sredniaDlugaMACD=sumaDluga/(float)dlugiKrokMACD;
//			sredniaKrotkaMACD=sumaKrotka/(float)krotkiKrokMACD;
//			if(nowaSwieczka)
//				dodajWartoscMACD(sredniaKrotkaMACD-sredniaDlugaMACD);
//			else if(MACD.size()>0)
//				MACD.set(MACD.size()-1, sredniaKrotkaMACD-sredniaDlugaMACD);
//			obliczSignal();
//		}
//	}
//	
//	private void dodajWartoscMACD(float wartosc)
//	{
//		if(MACD.size()>krokSignal)
//			MACD.remove(0);
//		MACD.add(wartosc);
//	}
//	
//	private void obliczSignal()
//	{
//		if(MACD.size()<krokSignal)
//			signal=0;
//		else
//		{
//			float temp=0;
//			int licznik=0;
//			for(int i=(MACD.size()-1);i>=0;i--)
//			{
//				if(licznik<krokSignal)
//				{
//					temp=MACD.get(i)+temp;
//					licznik++;
//				}
//				else
//					break;
//			}
//			signal=temp/(float)krokSignal;
//		}
//	}
//	
//	private boolean czyWystarczajacoDanych()
//	{
//		if(signal!=0 && MACD.size()>=dlugiKrokMACD)
//			return true;
//		else
//			return false;
//	}
//	
//	public String toString()
//	{
//		StringBuffer sb=new StringBuffer();
//		sb.append("MACDsimple;").append(krotkiKrokMACD).append(";").append(dlugiKrokMACD).append(";").append(krokSignal);
//		return sb.toString();
//	}
//	
//	public static void main(String[] args) 
//	{
//		String nazwaRynku="";
//		BazaDanych bazaDanych= new BazaDanych();
//		WynikAlgorytm wynik;
//		Algorytm algorytm;
//		List<RynekTable> rynki=bazaDanych.pobierzQueryByNazwa(Klasa.RYNEK, "");
//		GregorianCalendar start=new GregorianCalendar();
//		
//		
//		for(RynekTable rynek:rynki)
//		{
//			algorytm=new MACDsimple();
//			nazwaRynku=rynek.getNazwa();
//			
//			
//			ArrayList <Float> kursy=(ArrayList<Float>) bazaDanych.pobierzQueryByNazwa(Klasa.KURS, nazwaRynku);
//			wynik=algorytm.ustawAlgorytm(kursy, rynek.getSpread(), rynek.getDzwignia(), rynek.getKrokStawka(), true);
//			System.out.println(nazwaRynku+":");
//			System.out.println(wynik.getStringAlgorytm()+"\t"+wynik.getWynikTestu()+"\n");
//		}
//		
//		
//		for(RynekTable rynek:rynki)
//		{
//			algorytm=new MACDsimple();
//			nazwaRynku=rynek.getNazwa();
//			
//			
//			ArrayList <Float> kursy=(ArrayList<Float>) bazaDanych.pobierzQueryByNazwa(Klasa.KURS, nazwaRynku);
//			wynik=algorytm.ustawAlgorytm(kursy, rynek.getSpread(), rynek.getDzwignia(), rynek.getKrokStawka(), false);
//			System.out.println(nazwaRynku+":");
//			System.out.println(wynik.getStringAlgorytm()+"\t"+wynik.getWynikTestu()+"\n");
//		}
//		
//		
//		GregorianCalendar stop= new GregorianCalendar();
//		
//		long czasReakcji=stop.getTimeInMillis()-start.getTimeInMillis();
//		double wSec=czasReakcji/1000.0;
//		System.out.println("Liczba rynkow: "+rynki.size());
//		System.out.println("Czas wykonania: "+czasReakcji+"ms\t"+wSec+"s");
//	}
	
	
}
