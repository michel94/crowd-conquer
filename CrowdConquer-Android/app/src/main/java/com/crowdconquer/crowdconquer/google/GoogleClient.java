package com.crowdconquer.crowdconquer.google;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.crowdconquer.crowdconquer.utils.Callback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

public class GoogleClient implements GoogleApiClient.ConnectionCallbacks,  GoogleApiClient.OnConnectionFailedListener {
    /* Request code used to invoke sign in user interactions. */
    public final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    public GoogleApiClient mGoogleApiClient;

    /* Is there a ConnectionResult resolution in progress? */
    public boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    public boolean mShouldResolve = false;

    private Activity activity;

    private Callback onConnectionCallback = null;

    public GoogleClient(Activity activity) {
        this.activity = activity;
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.EMAIL))
                .addScope(new Scope(Scopes.PROFILE))
                .build();
    }

    public void onConnectedCallback(Callback callback){
        this.onConnectionCallback = callback;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("Google Api Client", "Connected ");
        mShouldResolve = false;
        if(onConnectionCallback != null)
            onConnectionCallback.action();
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Google Api Client", "Failed " + connectionResult.toString());
        signIn(connectionResult);
    }

    public void signIn(ConnectionResult connectionResult) {
        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this.activity, RC_SIGN_IN);
                    mIsResolving = true;
                    Log.d("Google Api Client", "Resolving");
                } catch (IntentSender.SendIntentException e) {
                    Log.d("Google Api Client", "Could not resolve", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // TODO Could not resolve the connection result, show the user an error dialog
            }
        } else {
            // Show the signed-out UI
        }
    }

    public void signOut() {
        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }
    }

    public void startSignIn() {
        mShouldResolve = true;
        mGoogleApiClient.connect();
    }

    public void processResolution(int resultCode, int RESULT_OK) {
        // If the error resolution was not successful we should not resolve further.
        if (resultCode != RESULT_OK) {
            mShouldResolve = false;
        }
        mIsResolving = false;
        mGoogleApiClient.connect();
    }
}
