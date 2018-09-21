package divascion.marfiandhi.footballmatchschedule.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by Marfiandhi on 9/19/2018.
 */
class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteEvent.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("TABLE_FAVORITE", true,
                "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "HOME_ID" to TEXT + UNIQUE,
                "HOME_NAME" to TEXT,
                "HOME_SCORE" to TEXT,
                "AWAY_ID" to TEXT + UNIQUE,
                "AWAY_NAME" to TEXT,
                "AWAY_SCORE" to TEXT,
                "DATE" to TEXT,
                "HOME_GOAL_DETAIL" to TEXT,
                "AWAY_GOAL_DETAIL" to TEXT,
                "HOME_SHOT" to TEXT,
                "AWAY_SHOT" to TEXT,
                "HOME_GOAL_KEEPER" to TEXT,
                "HOME_DEFENSE" to TEXT,
                "HOME_MIDFIELD" to TEXT,
                "HOME_FORWARD" to TEXT,
                "HOME_SUBSTITUTES" to TEXT,
                "AWAY_GOAL_KEEPER" to TEXT,
                "AWAY_DEFENSE" to TEXT,
                "AWAY_MIDFIELD" to TEXT,
                "AWAY_FORWARD" to TEXT,
                "AWAY_SUBSTITUTES" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("TABLE_FAVORITE", true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)