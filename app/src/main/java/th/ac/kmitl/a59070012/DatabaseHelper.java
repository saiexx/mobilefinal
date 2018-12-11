package th.ac.kmitl.a59070012;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_USERNAME = "user_username";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_AGE = "user_age";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_USERNAME + " TEXT," + COLUMN_USER_NAME + " TEXT," + COLUMN_USER_AGE + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME,user.getUsername());
        values.put(COLUMN_USER_NAME,user.getName());
        values.put(COLUMN_USER_AGE,user.getAge());
        values.put(COLUMN_USER_PASSWORD,user.getPassword());

        db.insert(TABLE_USER,null,values);
        db.close();
    }
    public List<User> getAllUser(){
        String[] columns = {COLUMN_USER_ID,COLUMN_USER_USERNAME,COLUMN_USER_NAME,COLUMN_USER_AGE,COLUMN_USER_PASSWORD};
        String sortOrder = COLUMN_USER_USERNAME + " ASC";
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,columns,null,null,null,null,sortOrder);

        if (cursor.moveToFirst()) {
            do{
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_AGE))));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                userList.add(user);
            }while(cursor.moveToNext());
        }cursor.close();
        db.close();
        return userList;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME,user.getUsername());
        values.put(COLUMN_USER_NAME,user.getName());
        values.put(COLUMN_USER_AGE,user.getAge());
        values.put(COLUMN_USER_PASSWORD,user.getPassword());

        db.update(TABLE_USER,values,COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER,COLUMN_USER_ID + " = ?",new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String username){
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USER,columns,selection,selectionArgs,null,null,null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount>0){
            return true;
        }
        return false;
    }
    public boolean checkUser(String username, String password){
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {username,password};

        Cursor cursor = db.query(TABLE_USER,columns,selection,selectionArgs,null,null,null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if(cursorCount>0){
            return true;
        }
        return false;
    }
}
