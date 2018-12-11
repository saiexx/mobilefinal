package th.ac.kmitl.a59070012;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends android.support.v4.app.Fragment {

    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRegisterButton();
        initLoginButton();
    }

    public void initRegisterButton(){
        TextView registerButton = getView().findViewById(R.id.login_regist_btn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).addToBackStack(null).commit();
            }
        });
    }

    public void initLoginButton(){
        Button loginButton = getView().findViewById(R.id.login_login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = getView().findViewById(R.id.login_user_id);
                EditText password = getView().findViewById(R.id.login_password);
                if(databaseHelper.checkUser(username.getText().toString().trim() , password.getText().toString().trim())){
                    Toast.makeText(getActivity(),"success",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
