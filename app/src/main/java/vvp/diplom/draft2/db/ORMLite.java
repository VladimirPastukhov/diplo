package vvp.diplom.draft2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.model.Goal;
import vvp.diplom.draft2.model.Player;
import vvp.diplom.draft2.model.Round;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class ORMLite extends OrmLiteSqliteOpenHelper {

    private static final String TAG = Util.BASE_TAG + "ORMLite";

    static PlayersDAO players;
    static GoalsDAO goals;
    static RoundsDAO2 rounds;

    public ORMLite(Context context){
        super(context, DB.NAME, null, DB.VERSION);
    }

    void init(){
        try {
            players = new PlayersDAO(getConnectionSource(), Player.class);
            goals = new GoalsDAO(getConnectionSource(), Goal.class);
            rounds = new RoundsDAO2(getConnectionSource(), Round.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    void clear(){
        try {
            ConnectionSource cs = getConnectionSource();
            TableUtils.dropTable(cs, Player.class, true);
            TableUtils.dropTable(cs, Goal.class, true);
            TableUtils.dropTable(cs, Round.class, true);
            createTables(cs);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    void createTables(ConnectionSource cs) throws SQLException {
        TableUtils.createTable(cs, Player.class);
        TableUtils.createTable(cs, Goal.class);
        TableUtils.createTable(cs, Round.class);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            createTables(connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        clear();
    }
}
