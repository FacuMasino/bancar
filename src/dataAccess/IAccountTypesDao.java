package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import domainModel.AccountType;

public interface IAccountTypesDao
{
	public boolean create(AccountType accountType) throws SQLException;
	public AccountType read(int accountTypeId) throws SQLException;
	public AccountType readByName(String accountTypeName) throws SQLException;
	public ArrayList<AccountType> list() throws SQLException;
	public int getId(AccountType accountType) throws SQLException;

}
