package vvp.diplom.draft2.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import vvp.diplom.draft2.model.Player;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class PlayersDAO extends BaseDaoImpl<Player, String>{

    public static final String ID = "id";
    public static final String NAME = "name";

    protected PlayersDAO(ConnectionSource connectionSource, Class<Player> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public Player getById(String id) throws SQLException {
        QueryBuilder<Player, String> queryBuilder = queryBuilder();
        queryBuilder.where().eq(ID, id);
        PreparedQuery<Player> preparedQuery = queryBuilder.prepare();
        List<Player> list = query(preparedQuery);
        return list.get(0);
    }
}
