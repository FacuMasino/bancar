package utils;

import domainModel.Address;
import domainModel.Client;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Validator
{

	public static List<String> validateClientFields(Client client)
	{
		List<String> invalidFields = new ArrayList<>();

		// DNI
		if (client.getDni() == null || client.getDni().trim().isEmpty())
		{
			invalidFields.add("El DNI es requerido");
		} else if (!hasOnlyNumbers(client.getDni()))
		{
			invalidFields.add("El DNI solo puede contener números");
		}

		// CUIL
		if (client.getCuil() == null || client.getCuil().trim().isEmpty())
		{
			invalidFields.add("El CUIL es requerido");
		} else if (!hasOnlyNumbers(client.getCuil()))
		{
			invalidFields.add("El CUIL solo puede contener números");
		}

		// Teléfono
		if (client.getPhone() == null || client.getPhone().trim().isEmpty())
		{
			invalidFields.add("El Teléfono es requerido");
		} else if (!hasOnlyNumbers(client.getPhone()))
		{
			invalidFields.add("El Teléfono solo puede contener números");
		} /*
			 * //NO VALIDAMOZ EL LARGO...DEJAD LIBRE LA CANTIDAD... else if
			 * (client.getPhone().length() != 10) { invalidFields
			 * .add("El Teléfono es inválido, debe contener 10 dígitos"); }
			 */

		// Nombre
		if (client.getFirstName() == null
				|| client.getFirstName().trim().isEmpty())
		{
			invalidFields.add("El Nombre es requerido");
		} else if (!hasOnlyLetters(client.getFirstName()))
		{
			invalidFields.add("El Nombre solo puede contener letras");
		}

		// Apellido
		if (client.getLastName() == null
				|| client.getLastName().trim().isEmpty())
		{
			invalidFields.add("El Apellido es requerido");
		} else if (!hasOnlyLetters(client.getLastName()))
		{
			invalidFields.add("El Apellido solo puede contener letras");
		}

		// Email
		if (client.getEmail() == null || client.getEmail().trim().isEmpty())
		{
			invalidFields.add("El Email es requerido");
		} else if (!isValidEmail(client.getEmail()))
		{
			invalidFields.add("El formato del Email no es válido");
		}

		// Fecha de nacimiento
		if (client.getBirthDate() == null)
		{
			invalidFields.add("La Fecha de Nacimiento es requerida");
		} else if (!isAdult(client.getBirthDate()))
		{
			invalidFields.add("El cliente debe ser mayor de edad");
		}

		// Se suman los posibles campos invalidos de dirección
		invalidFields.addAll(validateAddressFields(client.getAddress()));

		return invalidFields;
	}

	public static List<String> validateAddressFields(Address address)
	{
		List<String> invalidFields = new ArrayList<String>();

		// Calle
		if (address.getStreetName() == null
				|| address.getStreetName().trim().isEmpty())
		{
			invalidFields.add("El Nombre de la Calle es requerido");
		} else if (!hasOnlyLettersAndNumbers(address.getStreetName()))
		{
			invalidFields.add(
					"El Nombre de la Calle solo puede contener letras y números");
		}

		// Número de calle
		if (address.getStreetNumber() == null
				|| address.getStreetNumber().trim().isEmpty())
		{
			invalidFields.add("El Número de la Calle es requerido");
		} else if (!hasOnlyNumbers(address.getStreetNumber()))
		{
			invalidFields
					.add("El Número de la Calle solo puede contener números");
		}

		// Piso
		if (address.getFlat() != null && (!address.getFlat().trim().isEmpty()
				&& !hasOnlyLettersAndNumbers(address.getFlat())))
		{
			invalidFields.add("El Piso solo puede contener letras y números");
		}

		// Ciudad
		if (address.getCity() == null || address.getCity().getName() == null
				|| address.getCity().getName().trim().isEmpty())
		{
			invalidFields.add("El Nombre de la Ciudad es requerido");
		} else if (!hasOnlyLettersAndNumbers(address.getCity().getName()))
		{
			invalidFields
					.add("El Nombre de la Ciudad solo puede contener letras");
		}

		// Código Postal
		if (address.getCity() == null || address.getCity().getZipCode() == null
				|| address.getCity().getZipCode().trim().isEmpty())
		{
			invalidFields.add("El Código Postal es requerido");
		} else if (!hasOnlyLettersAndNumbers(address.getCity().getZipCode()))
		{
			invalidFields.add(
					"El Código Postal solo puede contener letras y números");
		}

		// País
		if (address.getCountry() == null	|| address.getCountry().getName() == null	|| address.getCountry().getName().trim().isEmpty())
		{
			invalidFields.add("La Nacionalidad es requerida");
		} else if (!hasOnlyLetters(address.getCountry().getName()))
		{
			invalidFields.add("La Nacionalidad solo puede contener letras");
		}

		if (address.getProvince().getName() == null
				|| address.getProvince().getName().trim().isEmpty())
		{
			invalidFields.add("La provincia es un campo requerido");
		} else if (!hasOnlyLetters(address.getProvince().getName()))
		{
			invalidFields.add("La provincia solo puede contener letras");
		}

		return invalidFields;
	}

	private static boolean hasOnlyLettersAndNumbers(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			if (!(Character.isLetterOrDigit(c) || str.charAt(i) == ' '))
			{
				return false;
			}
		}
		return true;
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
			if (!(Character.isLetter(str.charAt(i)) || str.charAt(i) == ' '))
			{
				return false;
			}
		}
		return true;
	}

	private static boolean isAdult(Date birthDate)
	{

		LocalDate today = LocalDate.now();
		String birthStr = new SimpleDateFormat("yyyy-MM-dd").format(birthDate);
		LocalDate birthLocalDate = LocalDate.parse(birthStr);
		Period age = Period.between(birthLocalDate, today);
		return age.getYears() >= 18;
	}

	// Regexp tomada de https://stackoverflow.com/a/8204716
	private static boolean isValidEmail(String email)
	{
		Pattern emailRegex = Pattern.compile(
				"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		return emailRegex.matcher(email).matches();
	}
}
