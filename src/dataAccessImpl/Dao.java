package dataAccessImpl;

import java.sql.SQLException;
import domainModel.Identifiable;

public abstract class Dao<T extends Identifiable>
{
    protected Database db;

    public Dao()
    {
        db = new Database();
    }

    public void handleId(T entity) throws SQLException
    {
        try
        {
            if (entity != null)
            {
                int foundId = findId(entity);

                if (foundId == 0)
                {
                    entity.setId(create(entity));
                }
                else if (foundId == entity.getId())
                {
                    update(entity);
                }
                else
                {
                    entity.setId(foundId);
                    update(entity);
                }
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw ex;
        }
    }

    protected abstract int create(T entity) throws SQLException;
    protected abstract boolean update(T entity) throws SQLException;
    protected abstract int findId(T entity) throws SQLException;
}
