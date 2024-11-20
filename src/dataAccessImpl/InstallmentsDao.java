package dataAccessImpl;

import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.IInstallmentsDao;
import domainModel.Installment;

public class InstallmentsDao implements IInstallmentsDao
{

	public InstallmentsDao()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Installment installment) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Installment read(int installmentId) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Installment installment) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Installment> listByLoanId(int loanId) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
