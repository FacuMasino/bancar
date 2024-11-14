package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Account;

public interface IAccountsDao
{
	public boolean create(Account account) throws SQLException;
	public Account read(int accountId) throws SQLException;
	public boolean update(Account account) throws SQLException;
	public boolean delete(int accountId) throws SQLException;
	public ArrayList<Account> list() throws SQLException;
	public int getId(Account account) throws SQLException;
	public int getLastId() throws SQLException;
	public ArrayList<Account> listByIdClient(int clientId) throws SQLException;
}
