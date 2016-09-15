package com.emiliano.safdService.context;

import com.emiliano.fmframework.building.FMBuilder;
import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.operations.ConfOperations;
import com.emiliano.safdService.planning.ModelableElement;

public class ContextState implements ModelableElement{

	public enum CPUArchitecture{
		armeabi,
		armeabi_v7a,
		arm64_v8a,
		x86,
		x86_64,
		mips,
		mips64
	}
	public enum AndroidSOVersion{
		APIv8,
		APIv9,
		APIv10,
		APIv11,
		APIv12,
		APIv13,
		APIv14,
		APIv15,
		APIv16,
		APIv17,
		APIv18,
		APIv19,
		APIv20,
		APIv21,
		APIv22,
		APIv23;

		public static AndroidSOVersion valueOf(int sdkInt) {
			return valueOf("APIv"+sdkInt);
		}
	}
	public enum ConnectionState{
		NO_CONNECTION,
		_2G_CONNECTION,
		_3G_CONNECTION,
		_4G_CONNECTION,
		WIFI_CONNECTION
	}
	
	public ContextState(CPUArchitecture arch,AndroidSOVersion soVersion,ConnectionState connection){
		this.arch=arch;
		this.soVersion=soVersion;
		this.connection=connection;
	}
	
	public CPUArchitecture arch;
	public AndroidSOVersion soVersion;
	public ConnectionState connection;
	
	private static FeatureModel model;

	public FeatureModel getFeatureModel() {
		if (model == null) {
			model = new FMBuilder(ContextState.class.getSimpleName())
					.addMandatoryFeature(
							new FMBuilder(ConnectionState.class.getSimpleName())
							.addAlternativeGroup(
									new FMBuilder(ConnectionState.NO_CONNECTION.name()),
									new FMBuilder(ConnectionState._2G_CONNECTION.name()),
									new FMBuilder(ConnectionState._3G_CONNECTION.name()),
									new FMBuilder(ConnectionState._4G_CONNECTION.name()),
									new FMBuilder(ConnectionState.WIFI_CONNECTION.name())))
					.addMandatoryFeature(
							new FMBuilder(CPUArchitecture.class.getSimpleName())
							.addAlternativeGroup(
									new FMBuilder(CPUArchitecture.armeabi.name()),
									new FMBuilder(CPUArchitecture.armeabi_v7a.name()),
									new FMBuilder(CPUArchitecture.arm64_v8a.name()),
									new FMBuilder(CPUArchitecture.x86.name()),
									new FMBuilder(CPUArchitecture.x86_64.name()),
									new FMBuilder(CPUArchitecture.mips.name()),
									new FMBuilder(CPUArchitecture.mips64.name())))
					.addMandatoryFeature(
							new FMBuilder(AndroidSOVersion.class.getSimpleName())
							.addAlternativeGroup(
							new FMBuilder(AndroidSOVersion.APIv8.name()),
							new FMBuilder(AndroidSOVersion.APIv9.name()),
							new FMBuilder(AndroidSOVersion.APIv10.name()),
							new FMBuilder(AndroidSOVersion.APIv11.name()),
							new FMBuilder(AndroidSOVersion.APIv12.name()),
							new FMBuilder(AndroidSOVersion.APIv13.name()),
							new FMBuilder(AndroidSOVersion.APIv14.name()),
							new FMBuilder(AndroidSOVersion.APIv15.name()),
							new FMBuilder(AndroidSOVersion.APIv16.name()),
							new FMBuilder(AndroidSOVersion.APIv17.name()),
							new FMBuilder(AndroidSOVersion.APIv18.name()),
							new FMBuilder(AndroidSOVersion.APIv19.name()),
							new FMBuilder(AndroidSOVersion.APIv20.name()),
							new FMBuilder(AndroidSOVersion.APIv21.name()),
							new FMBuilder(AndroidSOVersion.APIv22.name()),
							new FMBuilder(AndroidSOVersion.APIv23.name()))).buildModel();		
		}
		return model;
	}

	public Configuration getConfiguration(){
		Configuration conf=ConfOperations.getPartialConfiguration(getFeatureModel());
		ConfOperations.selectFeature(conf, connection.name());
		ConfOperations.selectFeature(conf, arch.name());
		ConfOperations.selectFeature(conf, soVersion.name());
		return conf;
	}
}
