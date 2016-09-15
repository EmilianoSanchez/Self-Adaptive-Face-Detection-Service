package com.emiliano.safdService.context;

import java.util.LinkedList;
import java.util.List;

import com.emiliano.safdService.context.ContextState.AndroidSOVersion;
import com.emiliano.safdService.context.ContextState.CPUArchitecture;
import com.emiliano.safdService.context.ContextState.ConnectionState;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

public class ContextManager extends BroadcastReceiver{
	
	private Context androidContext;
	private List<OnContextChangeListener> listeners;
	private ContextState currentContextState;

	private ConnectivityManager connectivityManager;
	
	public ContextManager(Context androidContext){
		this.androidContext=androidContext;
		this.listeners=new LinkedList<OnContextChangeListener>();
		
		this.connectivityManager=(ConnectivityManager) this.androidContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		this.androidContext.registerReceiver(this, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
		Log.i("SAFD","ContextManager was created");
		this.currentContextState=new ContextState(getCPUArchitecture(),getAndroidSOVersion(), getConnectionState());
	}
	private ConnectionState getConnectionState() {
		return ConnectionState.NO_CONNECTION;
	}
	private AndroidSOVersion getAndroidSOVersion() {
		return AndroidSOVersion.valueOf(Build.VERSION.SDK_INT);
	}
	private CPUArchitecture getCPUArchitecture() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1)
			return CPUArchitecture.valueOf(Build.SUPPORTED_ABIS[0]);
		else
			return CPUArchitecture.valueOf(Build.CPU_ABI);
	}
	public ContextState getContextState(){
		return currentContextState;
	};
	
	public void addOnContextChangeListener(OnContextChangeListener listener){
		listeners.add(listener);
	}
	
	private void updateAndNotify(ConnectionState newConnectionState){
		this.currentContextState.connection=newConnectionState;
		for(OnContextChangeListener listener:listeners)
			listener.onContextStateChange(currentContextState);
	}
	
	//TO FIX
	@Override
	public void onReceive(Context context, Intent intent) {
        Log.d("SAFD", "Network connectivity change");

        if (intent.getExtras() != null) {
            final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

            if (ni != null && ni.isConnectedOrConnecting()) {
                Log.i("SAFD", "Network " + ni.getTypeName() + " connected");
                updateAndNotify(ConnectionState.WIFI_CONNECTION);
            } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                Log.d("SAFD", "There's no network connectivity");
                updateAndNotify(ConnectionState.NO_CONNECTION);
            }
        }
	};
}
