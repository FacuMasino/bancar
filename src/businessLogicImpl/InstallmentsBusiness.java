package businessLogicImpl;

import java.sql.SQLException;

import businessLogic.IInstallmentsBusiness;
import dataAccess.IInstallmentsDao;
import dataAccessImpl.InstallmentsDao;
import domainModel.Installment;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class InstallmentsBusiness implements IInstallmentsBusiness
{
	private IInstallmentsDao installmentsDao;
	
	InstallmentsBusiness() 
	{
		installmentsDao = new InstallmentsDao();
	}
	
	@Override
	public boolean update(Installment installment) throws BusinessException
	{
		try {
			return installmentsDao.update(installment);
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al actualizar la cuota.");
		}
	}

	@Override
	public boolean generate(int loanId) throws BusinessException
	{
		try {
			return installmentsDao.generate(loanId);
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al generar las cuotas.");
		}
	}

}
