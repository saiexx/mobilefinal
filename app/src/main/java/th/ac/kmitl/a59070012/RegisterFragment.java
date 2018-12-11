package th.ac.kmitl.a59070012;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    EditText username;
    EditText name;
    EditText age;
    EditText password;
    SQLiteDatabase myDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRegist();

    }

    public void initRegist(){
        Button registButton = getView().findViewById(R.id.register_btn);
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();
            }
        });
    }

    private void postData(){
        username = getView().findViewById(R.id.user_text);
        name = getView().findViewById(R.id.name_text);
        age = getView().findViewById(R.id.age_text);
        password = getView().findViewById(R.id.password_text);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        User user = new User();

        if(!databaseHelper.checkUser(username.getText().toString().trim())){
            user.setName(name.getText().toString().trim());
            user.setAge(Integer.parseInt(age.getText().toString().trim()));
            user.setPassword(password.getText().toString().trim());

            databaseHelper.addUser(user);
            Toast.makeText(getActivity(),"sucess",Toast.LENGTH_SHORT);
        }else{
            Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT);
        }
    }
}
