package ca.bcit.project.findwater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;


/**
 * Created by Dan on 2017-11-28.
 */

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Fountains11.sqlite";
    private static final int DB_VERSION = 2;
    private Context context;

    public MyDbHelper(Context context) {
        // The 3'rd parameter (null) is an advanced feature relating to cursors
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, i, i1);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 1) {
                db.execSQL(getCreateFountainsTableSql());




            }
        } catch (SQLException sqle) {
            String msg = "[MyDbHelper / updateMyDatabase] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }

    public String getCreateFountainsTableSql(){
        String sql = "";
        sql += "CREATE TABLE Fountains (";
        sql += "ParkName TEXT, ";
        sql += "X TEXT, ";
        sql += "Y TEXT, ";
        sql += "Favorite INTEGER,";
        sql += "Distance REAL,";
        sql += "PRIMARY KEY(X, Y));";
        return sql;
    }

    public void insertFountain(SQLiteDatabase db, Fountain fountain) {
        ContentValues values = new ContentValues();
        values.put("ParkName", fountain.getParkName());
        values.put("X", fountain.getX());
        values.put("Y", fountain.getY());
        values.put("Favorite", fountain.getFavorite());
        values.put("Distance", fountain.getDistance());

        db.insert("Fountains", null, values);
    }

    public void updateFavorite(String X, String Y, int favorite) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "";
        sql += "UPDATE Fountains SET ";
        sql += "Favorite = " + favorite;
        sql += " WHERE X = '" + X;
        sql += "' AND Y = '" + Y;
        sql += "';";

        db.execSQL(sql);
    }

    public int getFavoriteStatus(String X, String Y) {
        int FavoriteStatus = -1;
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select Favorite from Fountains where X = '" + X
                    + "' AND Y = '" + Y + "';", null);

            if (cursor.moveToFirst()) {
                do {
                    FavoriteStatus = cursor.getInt(0);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MyDbHelper / getFavoriteStatus] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
        return FavoriteStatus;
    }

    public double getDistance(String X, String Y) {
        double distance = -1;
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select Distance from Fountains where X = '" + X
                    + "' AND Y = '" + Y + "';", null);

            if (cursor.moveToFirst()) {
                do {
                    distance = cursor.getDouble(0);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MyDbHelper / getFavoriteStatus] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
        return distance;
    }

    public void getFountains(ArrayList<Fountain> fountains) {

        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor= db.rawQuery("select distinct * from Fountains where Favorite = 1", null);

            if (cursor.moveToFirst()) {
                do {
                    Fountain fountain = new Fountain(cursor.getString(0));
                    fountain.setX(cursor.getString(1));
                    fountain.setY(cursor.getString(2));
                    fountain.setFavorite(cursor.getInt(3));
                    fountain.setDistance(cursor.getDouble(4));
                    fountains.add(fountain);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MyDbHelper / getFountains] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }
}
