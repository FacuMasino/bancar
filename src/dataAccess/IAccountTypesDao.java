package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.AccountType;

public interface IAccountTypesDao
{
	public AccountType read(int accountTypeId) throws SQLException;
	public AccountType read(String accountTypeName) throws SQLException;
	public ArrayList<AccountType> list() throws SQLException;
}
