package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import domainModel.Address;
import domainModel.Client;

public class Validator
{

	public static List<String> validateClientFields(Client client)
	{
		List<String> errors = new ArrayList<>();

		// DNI
		if (client.getDni() == null || client.getDni().trim().isEmpty())
		{
			errors.add("El DNI es requerido");
		} else if (!hasOnlyNumbers(client.getDni()))
		{
			errors.add("El DNI solo puede contener números");
		}

		// CUIL
		if (client.getCuil() == null || client.getCuil().trim().isEmpty())
		{
			errors.add("El CUIL es requerido");
		} else if (!hasOnlyNumbers(client.getCuil()))
		{
			errors.add("El CUIL solo puede contener números");
		}

		// Teléfono
		if (client.getPhone() == null || client.getPhone().trim().isEmpty())
		{
			errors.add("El Teléfono es requerido");
		} else if (!hasOnlyNumbers(client.getPhone()))
		{
			errors.add("El Teléfono solo puede contener números");
		}
		
		// Nombre
		if (client.getFirstName() == null
				|| client.getFirstName().trim().isEmpty())
		{
			errors.add("El Nombre es requerido");
		} else if (!hasOnlyLetters(client.getFirstName()))
		{
			errors.add("El Nombre solo puede contener letras");
		}

		// Apellido
		if (client.getLastName() == null
				|| client.getLastName().trim().isEmpty())
		{
			errors.add("El Apellido es requerido");
		} else if (!hasOnlyLetters(client.getLastName()))
		{
			errors.add("El Apellido solo puede contener letras");
		}

		// Email
		if (client.getEmail() == null || client.getEmail().trim().isEmpty())
		{
			errors.add("El Email es requerido");
		} else if (!isValidEmail(client.getEmail()))
		{
			errors.add("El formato del Email no es válido");
		}

		return errors;
	}

	public static List<String> validateAddressFields(Address address)
	{
		List<String> errors = new ArrayList<String>();
		
		
		// TODO: validaciones de direcciones
		
		return errors;
	}
	
	private static boolean hasOnlyNumbers(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	private static boolean hasOnlyLetters(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			if (!Character.isLetter(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	// Regexp tomada de https://stackoverflow.com/a/8204716
	private static boolean isValidEmail(String email)
	{
		Pattern emailRegex = 
			    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		return emailRegex.matcher(email).matches();
	}

}
