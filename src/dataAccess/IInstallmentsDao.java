package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import domainModel.Installment;

public interface IInstallmentsDao
{
	public boolean create(Installment installment) throws SQLException;
	public Installment read(int installmentId) throws SQLException;
	public boolean update(Installment installment) throws SQLException;
	public ArrayList<Installment> listByLoanId(int loanId) throws SQLException;
}
