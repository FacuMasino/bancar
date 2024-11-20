package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.IMovementsDao;
import domainModel.Account;
import domainModel.AccountType;
import domainModel.Movement;
import domainModel.MovementType;

public class MovementsDao implements IMovementsDao
{
	private Database db;
	private MovementTypesDao movementTypeDao;

	// TODO: revisar SP, es necesario el OUT?

	public MovementsDao()
	{
		db = new Database();
		movementTypeDao = new MovementTypesDao();
	}

	@Override
	public boolean create(Movement movement) throws SQLException
	{
		int rows = 0;

		try
		{
			MovementType auxMovementType = new MovementType(); // creo un objeto
																// auxMovementType
																// auxiliar.
			auxMovementType = movementTypeDao
					.readByName(movement.getMovementType().getName());

			movement.setMovementType(auxMovementType); // seteo el movementType
														// completo

			db.setPreparedStatement("{CALL insert_movement(?, ?, ?, ?)}");
			setParameters(movement);
			rows = db.getPreparedStatement().executeUpdate();
		} catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}

	@Override
	public Movement read(int movementId) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	// TODO: un movimiento tendrá update? o es algo inalterable una vez
	// realizado? en ese caso, herada DAO<Movement>?
	@Override
	public boolean update(Movement movemen) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int movementId) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Movement> list() throws SQLException
	{
		ResultSet rsMovements;
		ArrayList<Movement> movements = new ArrayList<Movement>();

		try {
			db.setPreparedStatement("SELECT * FROM Movements");
			rsMovements = db.getPreparedStatement().executeQuery();

			while (rsMovements.next()) {
				
				movements.add(getMovement(rsMovements));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return movements;
	}

	@Override
	public int getId(Movement movement) throws SQLException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Movement> listByIdAccount(int accountId) throws SQLException
	{
		ResultSet rsMovement;
		// El negocio debe verificar que lo devuelto != null
		ArrayList<Movement> auxMovementList = new ArrayList<Movement>();

		try {
			db.setPreparedStatement("SELECT * FROM Movements WHERE AccountId = ?;");
			db.getPreparedStatement().setInt(1, accountId);
			rsMovement = db.getPreparedStatement().executeQuery();

			while (rsMovement.next()) {
				auxMovementList.add(getMovement(rsMovement));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxMovementList;
	}

	private void setParameters(Movement movement) throws SQLException
	{
		db.getPreparedStatement().setString(1, movement.getDetails());
		db.getPreparedStatement().setBigDecimal(2, movement.getAmount());
		db.getPreparedStatement().setInt(3, movement.getMovementType().getId());
		db.getPreparedStatement().setInt(4, movement.getAccountId());
	}
	
	private Movement getMovement(ResultSet rs) throws SQLException {
		Movement auxMovement= new Movement();

		try {
			auxMovement.setMovementId(rs.getInt("MovementId"));
			auxMovement.setMovementDate(rs.getDate("MovementDate"));
			auxMovement.setDetails(rs.getString("Details"));
			auxMovement.setAmount(rs.getBigDecimal("Amount"));
			auxMovement.setAccountId(rs.getInt("AccountId"));
			
			MovementType auxMovementType = new MovementType();
			auxMovementType = movementTypeDao.read(rs.getInt("MovementTypeId"));

			auxMovement.setMovementType(auxMovementType);


		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxMovement;
	}
}
