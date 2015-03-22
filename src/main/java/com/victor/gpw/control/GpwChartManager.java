package com.victor.gpw.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.victor.entity.GpwAlgorithm;
import com.victor.entity.GpwCompany;
import com.victor.entity.Quotation;
import com.victor.entity.Value;
import com.victor.service.QuotationService;
import com.victor.utils.ColorUtils;
import com.victor.utils.DateUtils;

/**
 * @author Wiktor Rosinski
 *
 */
@Controller
public class GpwChartManager {
	
	@Autowired
	private QuotationService quotationService;
	
	private int lenghtToLoadAlgorithm;
	
	/** tworzy panel wykresu liniowego dla funduszu za zadany okres wraz z przestawionymi okresami sygnalow */
	public JPanel showChartForFundWithSignals(GpwCompany company, GregorianCalendar dateFrom, GregorianCalendar dateUntil) {
		GregorianCalendar newStartDate=changeStartDateForLoadAlgorithm(company, dateFrom);	
		List<Quotation> valueList=quotationService.findByGpwCompanyWithDateLimit(company, newStartDate, dateUntil);
		return drawChartForFundWithSignals(valueList);
		
	}
	
	
	/** tworzy panel z wykresem liniowym funduszu za zadany okres wraz z kresami sygnalow i wykresami obu algorytmow */
	public JPanel showChartForFundAlgorithms(GpwCompany company, GregorianCalendar startDate, GregorianCalendar endDate){
		GregorianCalendar newStartDate=changeStartDateForLoadAlgorithm(company, startDate);	
		List<Quotation> valueList=quotationService.findByGpwCompanyWithDateLimit(company, newStartDate, endDate);
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.add(drawChartForFundWithSignals(valueList));
		panel.add(drawChartForOpenAlgorithm(valueList));
		panel.add(new JPanel());
		panel.add(drawChartForCloseAlgorithm(valueList));
		
		return panel;
	}
	
	
	/** przygotowuje panel z wykresem dla algorytmu otwierajacego */
	private JPanel drawChartForOpenAlgorithm(List<Quotation> valueList){
		if(valueList==null || valueList.size()<1)
			returnNoResultPanel();
		StringBuffer buffer=new StringBuffer();
		buffer.append(((Quotation) valueList.get(0)).getCompany().getOpeningAlgorithm().getName()).append(" ");
		buffer.append(((Quotation) valueList.get(0)).getCompany().getOpeningAlgorithm().getParameter());
		TimeSeriesCollection dataSet=new TimeSeriesCollection();
		List<TimeSeries> seriesList=createTimeSeriesForOpenAlgorithm(valueList);
		for(TimeSeries series: seriesList){
			dataSet.addSeries(series);
		}
		
		JFreeChart chart= createChartWithTooltip(dataSet, buffer.toString(), "data", "wartoœci algrytmu");
		List<Marker> markerList=generateSignalMarkers((List<Quotation>) valueList);
		addMarkersToChart(chart, markerList);
		ChartPanel chartPanel=new ChartPanel(chart);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setMouseZoomable(true, true);
		return chartPanel;
	}
	
	
	/** przygotowuje serie danych dla paramatrow algorytmu otwierajacego */
	private List<TimeSeries> createTimeSeriesForOpenAlgorithm(List<Quotation> valueList){
		GpwAlgorithm algorithm=valueList.get(0).getCompany().getOpeningAlgorithm();
		algorithm.loadAlgorithm();
		int paramsNumber=algorithm.getParamNumber();
		
		List<TimeSeries> timeSeriesList=new ArrayList<TimeSeries>();
		for(int i=0;i<paramsNumber;i++){
			timeSeriesList.add(new TimeSeries(i));
		}
//		System.out.println(timeSeriesList.size());
		
		BigDecimal[] params;
		for(Quotation value: valueList){
			algorithm.putPrice(value);
			params= algorithm.getParamsValues();
			Date date=value.getDate().getTime();
			for(int i=0; i<paramsNumber;i++){
				if(params[i]!=null)
					timeSeriesList.get(i).add(new TimeSeriesDataItem(new Day(date), params[i]));
			}
		}

		return timeSeriesList;
	}
	
	/** przygotowuje serie danych dla parametrow algorytmu zamykajacego */
	private List<TimeSeries> createTimeSeriesForCloseAlgorithm(List<Quotation> valueList){
		GpwAlgorithm algorithm=valueList.get(0).getCompany().getClosingAlgorithm();
		algorithm.loadAlgorithm();
		int paramsNumber=algorithm.getParamNumber();
		
		List<TimeSeries> timeSeriesList=new ArrayList<TimeSeries>();
		for(int i=0;i<paramsNumber;i++){
			timeSeriesList.add(new TimeSeries(i));
		}
		
		
		BigDecimal[] params;
		for(Quotation value: valueList){
			algorithm.putPrice(value);
			params= algorithm.getParamsValues();
			Date date=value.getDate().getTime();
			for(int i=0; i<paramsNumber;i++){
				timeSeriesList.get(i).add(new TimeSeriesDataItem(new Day(date), params[i]));
			}
		}

		return timeSeriesList;
	}
	
	/** przygotowuje panel z wykresem dla algorytmu zamykajacego */
	private JPanel drawChartForCloseAlgorithm(List<Quotation> valueList){
		if(valueList==null || valueList.size()<1)
			returnNoResultPanel();
		StringBuffer buffer=new StringBuffer();
		buffer.append(((Quotation) valueList.get(0)).getCompany().getClosingAlgorithm().getName()).append(" ");
		buffer.append(((Quotation) valueList.get(0)).getCompany().getClosingAlgorithm().getParameter());
		TimeSeriesCollection dataSet=new TimeSeriesCollection();
		List<TimeSeries> seriesList=createTimeSeriesForCloseAlgorithm(valueList);
		for(TimeSeries series: seriesList){
			dataSet.addSeries(series);
		}
		
		JFreeChart chart= createChartWithTooltip(dataSet, buffer.toString(), "data", "wartoœci algrytmu");
		List<Marker> markerList=generateSignalMarkers((List<Quotation>) valueList);
		addMarkersToChart(chart, markerList);
		ChartPanel chartPanel=new ChartPanel(chart);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setMouseZoomable(true, true);
		return chartPanel;
	}
	
	
	

	/** tworzy wykres na podstawie listy kursow */
	private JPanel drawChartForFundWithSignals(List<? extends Value> valueList){
		if(valueList==null || valueList.size()<1)
			returnNoResultPanel();
		
		String fundName=((Quotation) valueList.get(0)).getCompany().getName();
		TimeSeriesCollection dataSet=new TimeSeriesCollection(createTimeSeries(valueList, fundName));
		JFreeChart chart= createChartWithTooltip(dataSet, fundName, "data", "ceny jednostek");
		
		@SuppressWarnings("unchecked")
		List<Marker> markerList=generateSignalMarkers((List<Quotation>) valueList);
		addMarkersToChart(chart, markerList);
		ChartPanel chartPanel=new ChartPanel(chart);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setMouseZoomable(true, true);
		return chartPanel;
	}
	
	/** dodaje markery do wykresu - okresy sygnalow otwarcia i zamkniecia */
	private void addMarkersToChart(JFreeChart chart, List<Marker> markerList){
		XYPlot plot=chart.getXYPlot();
		for(Marker marker: markerList){
			plot.addDomainMarker(marker);
		}
	}
	
	/** tworzy markerow - sygnaly otwarcia i zamkniecia */
	private List<Marker> generateSignalMarkers(List<Quotation> valueList){
		List<Marker> markerList=new ArrayList<Marker>();
		List<GregorianCalendar[]>[] signalsListTable=findMarkersRangesOfSignals(valueList);
		List<GregorianCalendar[]> openSignals=signalsListTable[0];
		List<GregorianCalendar[]> closeSignals=signalsListTable[1];
		
		//przygotowanie koloru markerow otwarcia
		Color color=ColorUtils.getGreen1();
		int red=color.getRed();
		int green=color.getGreen();
		int blue=color.getBlue();
		color=new Color(red, green, blue, 80);
		//tworzenie markerow otwarcia
		Marker marker;
		for(GregorianCalendar[] range: openSignals){
			marker=new IntervalMarker(range[0].getTimeInMillis(), range[1].getTimeInMillis(), color);
			markerList.add(marker);
		}
		
		//przygotowania koloru markerow zamkniecia
		color=ColorUtils.getRed1();
		red=color.getRed();
		green=color.getGreen();
		blue=color.getBlue();
		color=new Color(red, green, blue, 80);
		//tworzenie markerow zamkniecia
		for(GregorianCalendar[] range: closeSignals){
			marker=new IntervalMarker(range[0].getTimeInMillis(), range[1].getTimeInMillis(), color);
			markerList.add(marker);
		}
		
		return markerList;
	}
	
	/** przygotowuje zakresy sygnalow - tablica list sygnalow 0: sygnaly otwarcia, 1: sygnaly zamkniecia */
	private List<GregorianCalendar[]>[] findMarkersRangesOfSignals(List<Quotation> valueList){
		GpwAlgorithm[] algorithms=new GpwAlgorithm[2];
		algorithms[0]=valueList.get(0).getCompany().getOpeningAlgorithm();		
		algorithms[1]=valueList.get(0).getCompany().getClosingAlgorithm();
		algorithms[0].loadAlgorithm();
		algorithms[1].loadAlgorithm();
		
		List<GregorianCalendar[]> openRangeList=new ArrayList<GregorianCalendar[]>();
		List<GregorianCalendar[]> closeRangeList=new ArrayList<GregorianCalendar[]>();
		GregorianCalendar[] range=new GregorianCalendar[2];
		boolean whichList=false;
		boolean openedRange=false;
		Boolean signal;
		
		for(Quotation value: valueList){
			putPrice(algorithms, value);
			signal=isSignal(algorithms);
			
			if(signal==null && openedRange){
				range[1]=value.getDate();
				openedRange=false;
				if(whichList)
					openRangeList.add(range);
				else
					closeRangeList.add(range);
				range=new GregorianCalendar[2];
			}else if(signal==null){
				//do nothing
			}else if(signal && !openedRange){
				range[0]=value.getDate();
				openedRange=true;
				whichList=true;
			}else if(! signal && !openedRange){
				range[0]=value.getDate();
				openedRange=true;
				whichList=false;
			}
		}
		//jezeli zakres jest otwarty po zakonczeniu petli to dodaje otwarty zakres z ostatnia data na liscie
		if(openedRange){
			range[1]=valueList.get(valueList.size()-1).getDate();
			openedRange=false;
			if(whichList)
				openRangeList.add(range);
			else
				closeRangeList.add(range);
		}
		
		List<GregorianCalendar[]>[] result=new ArrayList[2];
		result[0]=openRangeList;
		result[1]=closeRangeList;
		return result;
	}
	
	/** dodaje cene do algorytmow (algorytmy automatycznie sie przeliczaja) */
	private void putPrice(GpwAlgorithm[] algorithms, Quotation value){
		algorithms[0].putPrice(value);
		algorithms[1].putPrice(value);
	}
	
	/** sprawdza czy jest sygnal dla algorytmow przekazanych w argumencie {algorytmOtwarcia, algorytmZamkniecia} 
	 * @return true - sygnal otwarcia, false - sygnal zamkniecia, null - brak sygnalu */
	private Boolean isSignal(GpwAlgorithm[] algorithms){
		if(algorithms[0].openTransaction()&& !algorithms[1].closeTransaction())
			return true;
		else if(algorithms[1].closeTransaction())
			return false;
		else
			return null;
	}
	
	/** tworzy serie danych do data set dla listy kursow danego funduszu */
	private TimeSeries createTimeSeries(List<? extends Value> valueList, String seriesName){
		TimeSeries series=new TimeSeries(seriesName);
		Date date;
		for(Value value: valueList){
			date=new Date(value.getDate().getTimeInMillis());
			series.add(new TimeSeriesDataItem(new Day(date), value.getValue().doubleValue()));
		}
		return series;
	}
	
	/** tworzy wykres bez legendy */
	private JFreeChart createChartWithTooltip(XYDataset dataSet, String chartTitle, String xAxisLabel, String yAxisLabel){
		return ChartFactory.createTimeSeriesChart(
				chartTitle, 
				xAxisLabel,
				yAxisLabel, 
				dataSet, 
				false, 
				true, 
				false);

	}
	
	/** przesuwa date rozpoczocia o liczbe dni potrzebna do zaladowania algorytmu */
	private GregorianCalendar changeStartDateForLoadAlgorithm(GpwCompany company, GregorianCalendar startDate){
		GpwAlgorithm openAlgo=company.getOpeningAlgorithm();
		openAlgo.loadAlgorithm();
		GpwAlgorithm closeAlgo=company.getClosingAlgorithm();
		closeAlgo.loadAlgorithm();
		
		if(openAlgo.getLengthListToLoad()>closeAlgo.getLengthListToLoad())
			lenghtToLoadAlgorithm=openAlgo.getLengthListToLoad();
		else
			lenghtToLoadAlgorithm=closeAlgo.getLengthListToLoad();
	
		for(int i=0;i<lenghtToLoadAlgorithm;i++){
			startDate.add(GregorianCalendar.DAY_OF_MONTH, -1);
			if(DateUtils.isWeekendDay(startDate))
				i--;
		}
		return startDate;
	}
	
	/** zwraca panel gdy brak danych wejsciowych */
	private JPanel returnNoResultPanel(){
		JPanel panel =new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel label=new JLabel("Brak wyników");
		panel.add(label, BorderLayout.CENTER);
		return panel;
	}
		
}