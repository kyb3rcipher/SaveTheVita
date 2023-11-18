package com.kyb3r.asistem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LivesDatabaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_LIVES = "lives";
    private static final String COLUMN_LIVES_COUNT = "lives_count";

    public LivesDatabaseHelper(Context context) {
        super(context, "ASISTEM.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableLivesQuery = "CREATE TABLE " + TABLE_LIVES + " (" +
                COLUMN_LIVES_COUNT + " INTEGER)";
        db.execSQL(createTableLivesQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVES);
        onCreate(db);
    }

    public void setLivesCount(int livesCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LIVES_COUNT, livesCount);
        db.insertWithOnConflict(TABLE_LIVES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public int getLivesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LIVES, null);

        int livesCount = 0;
        if (cursor.moveToFirst()) {
            livesCount = cursor.getInt(cursor.getColumnIndex(COLUMN_LIVES_COUNT));
        } else {
            livesCount = 5;
        }

        cursor.close();
        db.close();
        return livesCount;
    }
}
