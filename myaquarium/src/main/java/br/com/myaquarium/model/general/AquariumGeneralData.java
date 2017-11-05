package br.com.myaquarium.model.general;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.DoubleStream;

import br.com.myaquarium.model.AquariumData;

public class AquariumGeneralData {

	private List<AquariumData> collection;
	private Double maxTemp;
	private Double minTemp;
	private Double averageTemp;
	private String lastUpdate;
	private Integer dataNumber;

	public AquariumGeneralData(Collection<AquariumData> collection) {
		this.collection = (List<AquariumData>) collection;
	}

	public void calculate() {
		if (collection != null && collection.size() > 0) {
			this.dataNumber = collection.size();
			this.lastUpdate = collection.get(0).getDateInString();
			List<Double> tempList = new ArrayList<>();
			collection.forEach(data -> tempList.add(data.getTemperature()));
			tempList.sort((a, b) -> {
				if (a < b) {
					return 1;
				}
				if (a > b) {
					return -1;
				}
				return 0;
			});
			this.minTemp = tempList.get(0);
			this.maxTemp = tempList.get(tempList.size() - 1);
			DoubleStream mapToDouble = tempList.stream().mapToDouble(a -> a);
			this.averageTemp = mapToDouble.average().getAsDouble();
			DecimalFormat df = new DecimalFormat("#.##");
			this.averageTemp = Double.valueOf(df.format(this.averageTemp).replaceAll(",", "."));
		}
	}

	public Double getMaxTemp() {
		return maxTemp;
	}

	public Double getMinTemp() {
		return minTemp;
	}

	public Double getAverageTemp() {
		return averageTemp;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public Integer getDataNumber() {
		return dataNumber;
	}

}
