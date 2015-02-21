package com.crowdconquer.crowdconquer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crowdconquer.crowdconquer.data.StaticData;

public class WelcomeActivity extends Activity {
    //views
    Button buttonGotIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initViews();
        startListeners();
        if (checkGoogle()) {
            showGoogleSuccess();
        }
        else {
            showGoogleError();
        }
    }

    private void initViews() {
        buttonGotIt = (Button)findViewById(R.id.btngotit);
    }

    private void startListeners() {

    }

    //UI
    private void showGoogleSuccess() {
        buttonGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadConquer();
            }
        });
    }

    private void showGoogleError() {
        buttonGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Boolean checkGoogle() {
        String email = getAccountInfo();
        if (email != null) {
            StaticData.user.setEmail(email);
            return true;
        }
        return false;
    }

    private String getAccountInfo() {
        AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();

        String currentAccount = null;
        for (Account account: list) {
            currentAccount = account.name;
            if (currentAccount.contains("@gmail.com"))
                break;
            else currentAccount = null;
        }
        return currentAccount;
    }

    //Activity
    private void loadConquer() {
        Intent intent = new Intent(WelcomeActivity.this, ConquerActivity.class);
        WelcomeActivity.this.startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
