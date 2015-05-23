package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.network.Network;
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 07.04.2015.
 */
public class LoginActivity extends Activity {

    private static final String TAG = Util.BASE_TAG + "LoginAct";

    EditText mEditLogin;
    EditText mEditPassword;
    ProgressDialog mProgressDialog;
    Exception mNetworkException;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditLogin = (EditText) findViewById(R.id.edit_text_login);
        mEditPassword = (EditText) findViewById(R.id.edit_text_password);
        mEditPassword.setOnEditorActionListener(new loginOnKeyboardEnterPressed());
    }

    public void login(View view){
        mProgressDialog = ProgressDialog.show(this,"", "", true);
        String login = mEditLogin.getText().toString();
        String password = mEditPassword.getText().toString();
        new HttpLoginTask().execute(login, password);
    }

    private class HttpLoginTask extends AsyncTask<String, Void, List<Tournament>> {
        @Override
        protected List<Tournament> doInBackground(String... params) {
            String login = params[0];
            String password = params[1];
            try {
                Network.login(login, password);
                List<Tournament> tournaments = Network.loadMyTournaments();
                DB.init(LoginActivity.this);
                DB.tournaments.insert(tournaments);
                return tournaments;
            } catch (Exception e) {
                mNetworkException = e;
            }
            finally {
                mProgressDialog.dismiss();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Tournament> tournaments) {
            if(mNetworkException != null) {
                handleNetworkException(mNetworkException);
                mNetworkException = null;
            }
            if(tournaments != null) {
                startTournamentsActivity(null);
            }
        }
    }

    private void startTournamentsActivity(List<Tournament> tournaments){
        Intent intent = new Intent(this, TournamentsActivity.class);
//        intent.putParcelableArrayListExtra(Exstras.TOURNAMENTS, (ArrayList) tournaments);
        startActivity(intent);
    }

    private void handleNetworkException(Exception e){
        if(e instanceof ResourceAccessException){
            Util.showAlertDialog(this,
                    R.string.dialog_no_connection_title,
                    R.string.dialog_no_connection_message);
        }
        else if(e instanceof HttpClientErrorException)
        {
            switch (((HttpClientErrorException) e).getStatusCode()){
                case UNAUTHORIZED:
                    Util.showAlertDialog(this,
                            R.string.dialog_login_fail_title,
                            R.string.dialog_login_fail_message);
                    break;
            }
        } else
        Log.e(TAG, e.getMessage(), e);
    }


    private class loginOnKeyboardEnterPressed implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                login(null);
                return true;
            }
            return false;
        }
    };

    public void fillLoginAndPassword(View view){
        EditText editLogin = (EditText) findViewById(R.id.edit_text_login);
        EditText editPassword = (EditText) findViewById(R.id.edit_text_password);
        editLogin.setText("podoknom@gmail.com");
        editPassword.setText("yaduhes");
//        editLogin.setText("threadend@gmail.com");
//        editPassword.setText("144df9e9");
    }
}
