package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import domainModel.Installment;
import domainModel.Movement;

public interface IInstallmentsDao
{
	public boolean generate(int loanId) throws SQLException;
	public Installment read(int installmentId) throws SQLException;
	public Installment read(Movement movement) throws SQLException;
	public ArrayList<Installment> listByLoanId(int loanId) throws SQLException;
	public ArrayList<Installment> listPendingsByLoanId(int loanId) throws SQLException;
	public boolean update(Installment installment) throws SQLException;
}
